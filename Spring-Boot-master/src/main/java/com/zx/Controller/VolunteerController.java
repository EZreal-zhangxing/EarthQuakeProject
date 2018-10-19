package com.zx.Controller;
import com.zx.Pojo.*;
import com.zx.Service.VolunteerService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Api(tags = "志愿故事模块")
@RestController
@RequestMapping("/volunteer")
public class VolunteerController extends BaseController {
    @Autowired
    private VolunteerService volunteerService;

    @Value("${windows_vol}")
    private String windows_vol;
    @Value("${linux_vol}")
    private String linux_vol;

    @Autowired
    private EncoderHandler encoderHandler;

    @ApiOperation(value = "获取用户所属团体信息",response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",required = false, value = "标题",dataType = "String"),
            @ApiImplicitParam(name = "modelId", value = "模块Id",dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getVolunteerInfo")
    public Pageinfo getVolunteerInfo(@RequestParam(value = "title",required = false) String title,
                                     @RequestParam(value="modelId") String modelId,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                     @RequestParam(value = "page",defaultValue = "1") String page){
        Volunteer volunteer=new Volunteer();
        volunteer.setTitle(title);
        volunteer.setType(modelId);
        Integer num=volunteerService.getNumofVolunteer(volunteer);
        Pageinfo pi=initpage(num,page,pageSize);
        pi.setConditionVolunteer(volunteer);
        List<Volunteer> list=volunteerService.getListofVolunteer(pi);
        pi.setResult(list);
        return pi;
    }

    @RequestMapping("/delVolunteerInfo")
    public Message delVolunteerInfo(@RequestParam(value = "id") Integer id){
        Volunteer volunteer=volunteerService.getVolunteerByid(id);
        if(volunteer!=null){
            String imgPath=volunteer.getShowImageUrl();
            if(imgPath!=null && !"".equals(imgPath)){
                delFileByfilePath(imgPath);
            }
            volunteerService.delVolunteerInfo(id);
        }
        return new Message(MessageCode.MSG_SUCCESS);
    }

    /**
     * 志愿故事添加接口
     * @param request
     * @param response
     * @param filepath
     */
    @RequestMapping("/addVolunteerInfo")
    public void addVolunteerInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "filepath") MultipartFile filepath){
        String articalId=request.getParameter("articalId");
        String title=request.getParameter("title");
        String model=request.getParameter("model");
        String outUrl=request.getParameter("outUrl");
        String articelDesc=request.getParameter("articelDesc");
        String content=request.getParameter("arttext");
        Volunteer volunteer=new Volunteer();
        volunteer.setId((articalId!=null && !"".equals(articalId))?Integer.parseInt(articalId):null);
        volunteer.setTitle(title);
        volunteer.setType(model);
        volunteer.setUrl(outUrl);
        volunteer.setDescription(articelDesc);
        volunteer.setContent(content);

        int result=0;
        String fileName=filepath.getOriginalFilename();
        if(volunteer.getId()==null){
            //新增
            if(fileName != null && !"".equals(fileName)){
                String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                String fileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(fileondesPath);
                //保存文件
                try {
                    saveFile(file,filepath.getInputStream(),getfilepath());
                    volunteer.setShowImageUrl(fileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result=volunteerService.saveVolunteerInfo(volunteer);
        }else{
            //修改
            //获取原始文件存储路径 并删除
            Volunteer oldVoluneer=volunteerService.getVolunteerByid(volunteer.getId());
            if(!"".equals(fileName)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(oldVoluneer.getShowImageUrl());
                //重新上传文件并生成文件名
                if(fileName != null && !"".equals(fileName)){
                    String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String fileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(fileondesPath);
                    try {
                        saveFile(file,filepath.getInputStream(),getfilepath());
                        volunteer.setShowImageUrl(fileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                volunteer.setShowImageUrl(oldVoluneer.getShowImageUrl());
            }
            //更新操作
            result=volunteerService.updateVolunteer(volunteer);
        }
        try {
            jumpPage(response,result,"editzygs.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户添加评论 前端用
     * 传参为json 对象 {'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'};
     * @param starCommon
     * @return
     */
    @ApiOperation(value = "用户添加评论信息",notes = "{'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'}",response = Message.class,httpMethod = "POST")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addVolunteerCommon",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addCommonStar(@RequestBody Common starCommon){
        starCommon.setType(2);
        Integer num=volunteerService.addVolunteerCommon(starCommon);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 获取评论信息
     * @param articalId
     * @return
     */
    @ApiOperation(value = "获取评论信息",response = Common.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articalId", value = "文章ID",dataType = "Integer"),
    })
    @RequestMapping("/getListofVolunteerCommons/{articalId}")
    public List<Common> getListofVolunteerCommons(@PathVariable(value = "articalId") Integer articalId){
        return volunteerService.getListofCommons(articalId);
    }


    /**
     * 通过ID 获取内容 前端用
     * @param id
     * @return
     */
    @ApiOperation(value = "通过文章ID获取文章信息",response = Volunteer.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章ID",dataType = "Integer"),
    })
    @RequestMapping("/getVolunteerByid/{id}")
    public Volunteer getVolunteerByid(@PathVariable(value = "id") Integer id){
        return volunteerService.getVolunteerByid(id);
    }

    public String getfilepath()
    {
        String sysStr=System.getProperties().getProperty("os.name");
        if(sysStr.indexOf("Windows") != -1)
        {
            return windows_vol;
        }else
        {
            return linux_vol;
        }
    }

}
