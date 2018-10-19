package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.VolunteerTeamService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Api(tags = "志愿团队模块")
@RestController
@RequestMapping("/volunteerTeam")
public class VolunteerTeamController extends BaseController {
    @Value("${windows_vol_team}")
    private String windows_vol_team;
    @Value("${linux_vol_team}")
    private String linux_vol_team;

    @Autowired
    private EncoderHandler encoderHandler;

    @Autowired
    private VolunteerTeamService volunteerTeamService;

    /**
     * 获取支援团队列表
     * @param title
     * @param areaId
     * @param serviceType
     * @param teamType
     * @param teamManNum
     * @param page
     * @return
     */
    @ApiOperation(value = "获取志愿团队列表",response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题",dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域ID",dataType = "Integer"),
            @ApiImplicitParam(name = "serviceType", value = "服务类别",dataType = "Integer"),
            @ApiImplicitParam(name = "teamType", value = "团队类型",dataType = "Integer"),
            @ApiImplicitParam(name = "teamManNum", value = "团队人数",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getVolunteerList")
    public Pageinfo getVolunteerList(@RequestParam(value = "title",required = false) String title,
                                     @RequestParam(value="areaId",required = false) Integer areaId,
                                     @RequestParam(value="serviceType",required = false) Integer serviceType,
                                     @RequestParam(value="teamType",required = false) Integer teamType,
                                     @RequestParam(value="teamManNum",required = false) Integer teamManNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                     @RequestParam(value = "page",defaultValue = "1") String page){
        VolunteerTeam volunteerTeam=new VolunteerTeam();
        volunteerTeam.setTitle(title);
        volunteerTeam.setAreaId(areaId);
        volunteerTeam.setServiceType(serviceType);
        volunteerTeam.setTeamType(teamType);
        volunteerTeam.setTeamManNum(teamManNum);
        Integer num=volunteerTeamService.getCountOfVolunteerTeam(volunteerTeam);
        Pageinfo pi=initpage(num,page,pageSize);
        pi.setConditionVolunteerTeam(volunteerTeam);
        List<VolunteerTeam> result=volunteerTeamService.getListofVolunteerTeam(pi);
        pi.setResult(result);
        return pi;
    }

    /**
     * 返回团队类型
     * @return
     */
    @ApiOperation(value = "返回团队类型",response = TeamType.class,httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping("/getTeamType")
    public List<TeamType> getTeamType(){
        return volunteerTeamService.getListofTeamType();
    }

    /**
     * 返回服务类别
     * @return
     */
    @ApiOperation(value = "返回服务类型",response = TeamSerciveType.class,httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping("/getteamServiceType")
    public List<TeamSerciveType> getteamServiceType(){
        return volunteerTeamService.getListofTeamServiceType();
    }

    /**
     * 添加志愿团队
     * @param request
     * @param response
     * @param filepath
     */
    @RequestMapping("/addVolunteerTeam")
    public void addVolunteerTeam(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filepath") MultipartFile filepath){
        String title=request.getParameter("title");
        String content=request.getParameter("arttext");
        String outUrl=request.getParameter("outUrl");
        String description=request.getParameter("articelDesc");
        String teamNum=request.getParameter("teamNum");
        String areaId=request.getParameter("modelarea");
        String serviceType=request.getParameter("serviceType");
        String teamType=request.getParameter("teamType");
        String projectMan=request.getParameter("projectMan");
        String projectManTel=request.getParameter("projectManTel");
        String projectManEmail=request.getParameter("projectManEmail");
        String projectAddress=request.getParameter("projectAddress");
        VolunteerTeam volunteerTeam=new VolunteerTeam();
        volunteerTeam.setTitle(title);
        volunteerTeam.setOutUrl(outUrl);
        volunteerTeam.setDescription(description);
        volunteerTeam.setTeamNum(Integer.parseInt(teamNum));
        volunteerTeam.setAreaId(Integer.parseInt(areaId));
        volunteerTeam.setServiceType(Integer.parseInt(serviceType));
        volunteerTeam.setTeamType(Integer.parseInt(teamType));
        volunteerTeam.setTeamMan(projectMan);
        volunteerTeam.setTeamManTel(projectManTel);
        volunteerTeam.setTeamManEmail(projectManEmail);
        volunteerTeam.setTeamAddress(projectAddress);
        volunteerTeam.setContent(content);
        //上传文件处理
        String fileName=filepath.getOriginalFilename();
        if(fileName != null && !"".equals(fileName)){
            String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
            String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
            String fileondesPath=getfilepath()+md5filename+hzname;
            File file=new File(fileondesPath);
            //保存文件
            try {
                saveFile(file,filepath.getInputStream(),getfilepath());
                volunteerTeam.setUrl(fileondesPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        volunteerTeamService.saveVolunteerTeam(volunteerTeam);
        try {
            jumpPage(response,1,"editzytt.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过ID 删除志愿团体
     * @param id
     * @return
     */
    @RequestMapping("/delVolunteerTeam/{id}")
    public Message delVolunteerTeam(@PathVariable(value = "id") Integer id){
        Integer num=volunteerTeamService.delVolunteerTeam(id);
        if(num>0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 获取志愿团体详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取志愿团体详情",response = VolunteerTeam.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "团队文章ID",dataType = "Integer")
    })
    @RequestMapping("/getVolunteerTeamByid/{id}")
    public VolunteerTeam getVolunteerTeamByid(@PathVariable(value = "id") Integer id){
        return volunteerTeamService.getVolunteerTeamByid(id);
    }

    /**
     * 团队报名接口
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取志愿团体详情",response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "Integer"),
            @ApiImplicitParam(name = "teamId",value = "团队ID",dataType = "Integer")
    })
    @RequestMapping("/signVolunteerTeam")
    public Message signVolunteerTeam(@RequestParam(value = "userId") Integer userId,
                                     @RequestParam(value = "teamId") Integer teamId){
        TeamSign teamSign=new TeamSign();
        teamSign.setUserId(userId);
        teamSign.setTeamId(teamId);
        volunteerTeamService.addSignTeam(teamSign);
        return new Message(MessageCode.MSG_SUCCESS);
    }

    /**
     * 搜索资源团体报名信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取资源团体报名信息",response = TeamSign.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "志愿团体ID",dataType = "Integer")
    })
    @RequestMapping("/getListOfTeamSign/{id}")
    public List<TeamSign> getListOfTeamSign(@PathVariable(value = "id") Integer id){
        return volunteerTeamService.getListofTeamSign(id);
    }
    /**
     * 获取文件存储路径
     * @return
     */
    public String getfilepath()
    {
        String sysStr=System.getProperties().getProperty("os.name");
        if(sysStr.indexOf("Windows") != -1)
        {
            return windows_vol_team;
        }else
        {
            return linux_vol_team;
        }
    }
}
