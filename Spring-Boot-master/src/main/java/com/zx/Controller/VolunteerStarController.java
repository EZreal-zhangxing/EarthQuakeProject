package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.VolunteerStarService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Api(tags = "志愿之星模块")
@RestController
@RequestMapping("/volunteerStar")
public class VolunteerStarController extends BaseController {
    @Autowired
    private VolunteerStarService volunteerStarService;

    @Value("${windows_vol_star}")
    private String windows_vol_star;

    @Value("${linux_vol_star}")
    private String linux_vol_star;

    @Autowired
    private EncoderHandler encoderHandler;

    @ApiOperation(value = "获取志愿之星列表",response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",required = false, value = "标题",dataType = "String"),
            @ApiImplicitParam(name = "modelId", value = "模块Id",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getVolunteerStar")
    public Pageinfo getVolunteerStarList(@RequestParam(value = "title",required = false) String title,
                                         @RequestParam(value = "modelId") Integer modelId,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "page",defaultValue = "1") String page){
        VolunteerStar volunteerStar=new VolunteerStar();
        volunteerStar.setTitle(title);
        volunteerStar.setModelId(modelId);
        Integer recordNum=volunteerStarService.getNumOfVolunteerStar(volunteerStar);
        Pageinfo pageinfo=initpage(recordNum,page,pageSize);
        pageinfo.setConditionVolunteerStar(volunteerStar);
        List<VolunteerStar> result=volunteerStarService.getListOfVolunteerStar(pageinfo);
        pageinfo.setResult(result);
        return pageinfo;
    }

    @RequestMapping("/delVolunteerStar/{id}")
    public Message delVolunteerStar(@PathVariable(value = "id") Integer id){
        //删除文件
        VolunteerStar volunteerStar=volunteerStarService.getVolunteerStarByid(id);
        delFileByfilePath(volunteerStar.getImageUrl());
        //删除文章内容
        int num=volunteerStarService.delVolunteerStar(id);
        if (num > 0) {
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 通过ID查询详情 前端页面使用
     * @param id
     * @return
     */
    @ApiOperation(value = "获取志愿之星详情",response = VolunteerStar.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true, value = "文章ID",dataType = "Integer")
    })
    @RequestMapping("/getVolunteerStarByid/{id}")
    public VolunteerStar getVolunteerStarByid(@PathVariable(value = "id") Integer id){
        return volunteerStarService.getVolunteerStarByid(id);
    }

    @RequestMapping("/addVolunteerStar")
    public void addVolunteerStar(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filepath") MultipartFile filepath){
        String id=request.getParameter("articalId");
        String title=request.getParameter("title");
        String model=request.getParameter("model");
        String outUrl=request.getParameter("outUrl");
        String articelDesc=request.getParameter("articelDesc");
        String content=request.getParameter("arttext");
        String areaId=request.getParameter("modelarea");
        String articalNum=request.getParameter("articalNum");
        VolunteerStar volunteerStar=new VolunteerStar();
        volunteerStar.setId((id!=null && !"".equals(id))?Integer.parseInt(id):null);
        volunteerStar.setTitle(title);
        volunteerStar.setModelId(Integer.parseInt(model));
        volunteerStar.setOutUrl(outUrl);
        volunteerStar.setDescription(articelDesc);
        volunteerStar.setContent(content);
        volunteerStar.setCommonNum(0);
        volunteerStar.setViewNum(0);
        if(areaId!= null && !"".equals(areaId)){
            volunteerStar.setAreaId(Integer.parseInt(areaId));
        }
        if(articalNum!=null && !"".equals(articalNum)){
            volunteerStar.setArticalNum(Integer.parseInt(articalNum));
        }else{
            volunteerStar.setArticalNum(0);
        }
        //上传文件处理
        String fileName=filepath.getOriginalFilename();
        if(volunteerStar.getId() == null){
            if(fileName != null && !"".equals(fileName)){
                String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                String fileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(fileondesPath);
                //保存文件
                try {
                    saveFile(file,filepath.getInputStream(),getfilepath());
                    volunteerStar.setImageUrl(fileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            volunteerStarService.addVolunteerStar(volunteerStar);
        }else{
            //获取原始文件存储路径 并删除
            VolunteerStar oldVolunteerStar=volunteerStarService.getVolunteerStartInfoByid(volunteerStar.getId());
            if(!"".equals(fileName)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(oldVolunteerStar.getImageUrl());
                //重新上传文件并生成文件名
                if(fileName != null && !"".equals(fileName)){
                    String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String fileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(fileondesPath);
                    try {
                        saveFile(file,filepath.getInputStream(),getfilepath());
                        volunteerStar.setImageUrl(fileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                volunteerStar.setImageUrl(oldVolunteerStar.getImageUrl());
            }
            volunteerStarService.updateVolunteerStar(volunteerStar);
        }
        try {
            jumpPage(response,1,"editzyzx.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取区优秀自愿者接口 前端用
     * @return
     */
    @ApiOperation(value = "获取区优秀自愿者接口",response = VolunteerStar.class,httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping("/getPerfectVolunteer")
    public List<VolunteerStar> getPerfectVolunteer(){
        return volunteerStarService.getListofVolunteerbyModelid(18);
    }
    /**
     * 获取公益项目接口 前端用
     * @return
     */
    @ApiOperation(value = "获取公益项目接口",response = VolunteerStar.class,httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping("/getVolunteerProject")
    public List<VolunteerStar> getVolunteerProject(){
        return volunteerStarService.getListofVolunteerbyModelid(23);
    }

    /**
     * 用户添加评论 前端用
     * 传参为json 对象 {'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'};
     * @param starCommon
     * @return
     */
    @ApiOperation(value = "用户添加评论",response = Message.class,httpMethod = "POST",notes = "{'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'}")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addStarCommon",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addCommonStar(@RequestBody Common starCommon){
        starCommon.setType(3);
        Integer num=volunteerStarService.addStarCommon(starCommon);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 删除评论
     * @param id 评论ID
     * @param articalId 文章ID
     * @return
     */
    @ApiOperation(value = "删除评论",response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论ID",dataType = "Integer"),
            @ApiImplicitParam(name = "articalId", value = "文章ID",dataType = "Integer")
    })
    @RequestMapping("/delStarCommon/{id}/{articalId}")
    public Message delCommonStar(@PathVariable(value = "id") Integer id,@PathVariable(value = "articalId") Integer articalId){
        Integer num=volunteerStarService.delStarCommon(id,articalId);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    @ApiOperation(value = "获取文章评论列表",response = Common.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articalId", value = "文章ID",dataType = "Integer")
    })
    @RequestMapping("/getListofStarCommon/{articalId}")
    public List<Common> getListofStarCommon(@PathVariable(value = "articalId") Integer articalId){
        return volunteerStarService.getListOfStarCommon(articalId);
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
            return windows_vol_star;
        }else
        {
            return linux_vol_star;
        }
    }
}
