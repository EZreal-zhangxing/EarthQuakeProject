package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.VolunteerPolicyService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 志愿政策
 */
@Api(tags = "志愿政策模块")
@RestController
@RequestMapping("/volunteerPolicy")
public class VolunteerPolicyController extends BaseController {
    @Autowired
    private VolunteerPolicyService volunteerPolicyService;
    @Autowired
    private EncoderHandler encoderHandler;

    @Value("${windows_vol_policy}")
    private String windows_vol_policy;

    @Value("${linux_vol_policy}")
    private String linux_vol_policy;
    /**
     * 获取列表
     * @param title
     * @param modelId
     * @param pageSize
     * @param page
     * @return
     */
    @ApiOperation(value = "获取志愿政策列表",response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",required = false, value = "标题",dataType = "String"),
            @ApiImplicitParam(name = "modelId", value = "模块Id",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getVolunteerPolicy")
    public Pageinfo getVolunteerPolicy(@RequestParam(value = "title",required = false) String title,
                                       @RequestParam(value = "modelId") Integer modelId,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page",defaultValue = "1") String page){
        VolunteerPolicy volunteerPolicy=new VolunteerPolicy();
        volunteerPolicy.setTitle(title);
        volunteerPolicy.setModelId(modelId);
        int num=volunteerPolicyService.getCountOfVolunteerPolicy(volunteerPolicy);
        Pageinfo pageinfo=initpage(num,page,pageSize);
        pageinfo.setConditionVolunteerPolicy(volunteerPolicy);
        List<VolunteerPolicy> result=volunteerPolicyService.getListofVolunteerPolicy(pageinfo);
        pageinfo.setResult(result);
        return pageinfo;
    }

    @RequestMapping("/delVolunteerPolicy/{id}")
    public Message delVolunteerPolicy(@PathVariable(value = "id") Integer id){
        VolunteerPolicy volunteerPolicy=volunteerPolicyService.getVpObjectByid(id);
        //删除文件
        delFileByfilePath(volunteerPolicy.getImageUrl());
        Integer num=volunteerPolicyService.delVolunteerPolicyByid(id);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 添加政策信息
     * @param request
     * @param response
     * @param filepath
     */
    @RequestMapping("/saveVolunteerPolicy")
    public void saveVolunteerPolicy(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filepath") MultipartFile filepath){
        String articalId=request.getParameter("articalId");
        String title=request.getParameter("title");
        String model=request.getParameter("model");
        String outUrl=request.getParameter("outUrl");
        String articelDesc=request.getParameter("articelDesc");
        String content=request.getParameter("arttext");
        VolunteerPolicy volunteerPolicy=new VolunteerPolicy();
        volunteerPolicy.setId((articalId!=null&&!"".equals(articalId))?Integer.parseInt(articalId):null);
        volunteerPolicy.setTitle(title);
        volunteerPolicy.setModelId(Integer.parseInt(model));
        volunteerPolicy.setOutUrl(outUrl);
        volunteerPolicy.setDescription(articelDesc);
        volunteerPolicy.setContent(content);
        volunteerPolicy.setViewNum(0);
        volunteerPolicy.setCommonNum(0);
        //上传文件处理
        int result=0;
        String fileName=filepath.getOriginalFilename();
        if(volunteerPolicy.getId() == null){
            if(fileName != null && !"".equals(fileName)){
                String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                String fileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(fileondesPath);
                //保存文件
                try {
                    saveFile(file,filepath.getInputStream(),getfilepath());
                    volunteerPolicy.setImageUrl(fileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result=volunteerPolicyService.addVolunteerPolicy(volunteerPolicy);
        }else{
            //修改
            //获取原始文件存储路径 并删除
            VolunteerPolicy oldVoluneerPolicy=volunteerPolicyService.getVpObjectByid(volunteerPolicy.getId());
            if(!"".equals(fileName)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(oldVoluneerPolicy.getImageUrl());
                //重新上传文件并生成文件名
                if(fileName != null && !"".equals(fileName)){
                    String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String fileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(fileondesPath);
                    try {
                        saveFile(file,filepath.getInputStream(),getfilepath());
                        volunteerPolicy.setImageUrl(fileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                volunteerPolicy.setImageUrl(oldVoluneerPolicy.getImageUrl());
            }
            //更新操作
            result=volunteerPolicyService.updateVolunteerPolicy(volunteerPolicy);
        }
        try {
            jumpPage(response,result,"editzyzc.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台用 获取文章内容信息 修改的时候查询信息使用
     * @param id
     * @return
     */
    @RequestMapping("/getVpObjectByid/{id}")
    public VolunteerPolicy getVpObjectByid(@PathVariable(value = "id") Integer id){
        return volunteerPolicyService.getVpObjectByid(id);
    }

    /**
     * 获取评论列表
     * @param articalId
     * @return
     */

    @RequestMapping("/getListOfCommon/{articalId}")
    public List<Common> getListOfCommon(@PathVariable(value = "articalId") Integer articalId){
        return volunteerPolicyService.getListofPolicyCommon(articalId);
    }

    /**
     * 获取文章详情+评论信息 前端用
     * @param id
     * @return
     */
    @ApiOperation(value = "获取文章详情+评论信息",response = VolunteerPolicy.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true, value = "标题",dataType = "Integer")
    })
    @RequestMapping("/getVolunteerPolicyById/{id}")
    public VolunteerPolicy getVolunteerPolicyById(@PathVariable(value = "id") Integer id){
        return volunteerPolicyService.getVolunteerPolicyByid(id);
    }

    /**
     * 添加志愿政策评论 前端用
     * @param policyCommon
     * @return
     */
    @ApiOperation(value = "添加志愿政策评论",response = Message.class,httpMethod = "POST")
    @ApiImplicitParams({})
    @RequestMapping(value = "addPolicyCommon",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public Message addPolicyCommon(@RequestBody Common policyCommon){
        policyCommon.setType(4);
        Integer num=volunteerPolicyService.addPolicyCommon(policyCommon);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 删除评论 前端用
     * @param id
     * @param articalId
     * @return
     */
    @ApiOperation(value = "删除评论",response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论ID",dataType = "Integer"),
            @ApiImplicitParam(name = "articalId", value = "文章ID",dataType = "Integer")
    })
    @RequestMapping("/delPolicyCommon/{id}/{articalId}")
    public Message delPolicyCommon(@PathVariable(value = "id") Integer id,@PathVariable(value = "articalId") Integer articalId){
        Integer num=volunteerPolicyService.delPolicyCommon(id,articalId);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }
    /**
     * 获取当前路径下的系统环境
     * @return
     */
    public String getfilepath()
    {
        String sysStr=System.getProperties().getProperty("os.name");
        if(sysStr.indexOf("Windows") != -1)
        {
            return windows_vol_policy;
        }else
        {
            return linux_vol_policy;
        }
    }
}
