package com.zx.Controller;
import com.zx.Pojo.*;
import com.zx.Service.UserService;
import com.zx.Service.VolunteerProjectService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Api(tags = "志愿项目")
@RestController
@RequestMapping("/volunteerProject")
public class VolunteerProjectController extends BaseController {
    @Autowired
    private VolunteerProjectService volunteerProjectService;
    @Autowired
    private UserService userService;

    @Value("${windows_vol_pro}")
    private String windows_vol_pro;
    @Value("${linux_vol_pro}")
    private String linux_vol_pro;

    @Autowired
    private EncoderHandler encoderHandler;

    @ApiOperation(value = "获取志愿项目列表",response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题",required = false,dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域ID(见/volunteerProject/getListofArea接口)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "serviceType", value = "服务类别(1演练项目，2培训项目，3活动项目，4师资项目)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "projectStatue", value = "项目状态(1招募待启动，2招募中，3招募已结束，4已结项目)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "signArea", value = "报名范围(1公开招募，2仅招募实名志愿者，3指定志愿团体招募，4设定免审密码招募)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "serviceTo", value = "服务对象(1儿童，2青少年，3孤寡老，4残障人士，5优抚对象，6特困群体，7其他)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "projectManNum", value = "人数范围(1 01-100，2 101-200，3 201-500，4 501-1000 5 1000以上)",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "startDate", value = "开始时间",required = false,dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束时间",required = false,dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getVolunteerProject")
    public Pageinfo getVolunteerProject(@RequestParam(value = "title",required = false) String title,
                                        @RequestParam(value="areaId",required = false) Integer areaId,
                                        @RequestParam(value="serviceType",required = false) Integer serviceType,
                                        @RequestParam(value="projectStatue",required = false) Integer projectStatue,
                                        @RequestParam(value="signArea",required = false) Integer signArea,
                                        @RequestParam(value="serviceTo",required = false) Integer serviceTo,
                                        @RequestParam(value="projectManNum",required = false) Integer projectManNum,
                                        @RequestParam(value="startDate",required = false) String startDate,
                                        @RequestParam(value="endDate",required = false) String endDate,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "page",defaultValue = "1") String page) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        VolunteerProject volunteerProject=new VolunteerProject();
        volunteerProject.setTitle(title);
        volunteerProject.setAreaId(areaId);
        volunteerProject.setServiceType(serviceType);
        volunteerProject.setProjectStatue(projectStatue);
        volunteerProject.setSignArea(signArea);
        volunteerProject.setServiceTo(serviceTo);
        volunteerProject.setProjectManNum(projectManNum);
        volunteerProject.setCreateDate(startDate);
        volunteerProject.setEndDate(endDate);
        Integer num=volunteerProjectService.getNumOfvolunteer(volunteerProject);
        Pageinfo pi=initpage(num,page,pageSize);
        pi.setConditionVolunteerProject(volunteerProject);
        List<VolunteerProject> list=volunteerProjectService.getListVolunteerProject(pi);
        //封装岗位信息
        for (VolunteerProject volunteerProject1:list){
            Calendar startCal=Calendar.getInstance();
            startCal.setTime(simpleDateFormat.parse(volunteerProject1.getCreateDate()));

            Calendar endCal=Calendar.getInstance();
            endCal.setTime(simpleDateFormat.parse(volunteerProject1.getEndDate()));
            long datas=(endCal.getTimeInMillis()-startCal.getTimeInMillis())/(60*60*24);
            volunteerProject1.setLeftTime(datas);
            volunteerProject1.setStations(volunteerProjectService.getStationsByprojectId(volunteerProject1.getId()));
        }
        pi.setResult(list);
        return pi;
    }

    @ApiOperation(value = "获取志愿项目区域",response = ProjectArea.class,httpMethod = "GET")
    @ApiImplicitParams({})
    @RequestMapping("/getListofArea")
    public List<ProjectArea> getListofArea(){
        return volunteerProjectService.getListofArea();
    }

    /**
     * 添加志愿项目
     * @param request
     * @param response
     * @param filepath
     */
    @RequestMapping("/addVolunteerProject")
    public void addVolunteerProject(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "filepath") MultipartFile filepath){
        String articalId=request.getParameter("articalId");
        String title=request.getParameter("title");
        String url=request.getParameter("outUrl");
        String articelDesc=request.getParameter("articelDesc");
        String modelarea=request.getParameter("modelarea");
        Integer serviceType=Integer.parseInt(request.getParameter("serviceType"));
        Integer projectStatue=Integer.parseInt(request.getParameter("projectStatue"));
        Integer signArea=Integer.parseInt(request.getParameter("signArea"));
        Integer serviceTo=Integer.parseInt(request.getParameter("serviceTo"));
        String projectManNum=request.getParameter("projectManNum");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String content=request.getParameter("arttext");
        String createMan=request.getParameter("createMan");
        String projectMan=request.getParameter("projectMan");
        String projectManTel=request.getParameter("projectManTel");
        String projectManEmail=request.getParameter("projectManEmail");
        String projectAddress=request.getParameter("projectAddress");
        VolunteerProject volunteerProject=new VolunteerProject();
        volunteerProject.setTitle(title);
        volunteerProject.setUrl(url);
        volunteerProject.setDescription(articelDesc);
        volunteerProject.setContent(content);
        volunteerProject.setAreaId(Integer.parseInt(modelarea));
        volunteerProject.setServiceType(serviceType);
        switch (serviceType){
            case 1:volunteerProject.setServiceTypeName("演练项目");break;
            case 2:volunteerProject.setServiceTypeName("培训项目");break;
            case 3:volunteerProject.setServiceTypeName("活动项目");break;
            case 4:volunteerProject.setServiceTypeName("师资项目");break;
        }
        volunteerProject.setProjectStatue(projectStatue);
        switch (projectStatue){
            case 1:volunteerProject.setProjectStatueName("招募待启动");break;
            case 2:volunteerProject.setProjectStatueName("招募中");break;
            case 3:volunteerProject.setProjectStatueName("招募已结束");break;
            case 4:volunteerProject.setProjectStatueName("已结项目");break;
        }
        volunteerProject.setSignArea(signArea);
        switch (signArea){
            case 1:volunteerProject.setSignAreaName("公开招募");break;
            case 2:volunteerProject.setSignAreaName("仅招募实名志愿者");break;
            case 3:volunteerProject.setSignAreaName("指定志愿团体招募");break;
            case 4:volunteerProject.setSignAreaName("设定免审密码招募");break;
        }
        volunteerProject.setServiceTo(serviceTo);
        switch (serviceTo){
            case 1:volunteerProject.setServiceToName("儿童");break;
            case 2:volunteerProject.setServiceToName("青少年");break;
            case 3:volunteerProject.setServiceToName("孤寡老");break;
            case 4:volunteerProject.setServiceToName("残障人士");break;
            case 5:volunteerProject.setServiceToName("优抚对象");break;
            case 6:volunteerProject.setServiceToName("特困群体");break;
            case 7:volunteerProject.setServiceToName("其他");break;
        }
        volunteerProject.setProjectManNum(Integer.parseInt(projectManNum));
        volunteerProject.setCreateDate(startDate);
        volunteerProject.setEndDate(endDate);
        volunteerProject.setCreateMan(createMan);
        volunteerProject.setProjectMan(projectMan);
        volunteerProject.setProjectManTel(projectManTel);
        volunteerProject.setProjectManEmail(projectManEmail);
        volunteerProject.setProjectAddress(projectAddress);

        String fileName=filepath.getOriginalFilename();
        if(articalId !=null && !"".equals(articalId)){
            //更新操作 设置ID
            volunteerProject.setId(Integer.parseInt(articalId));
            //检查是否有新上传文件
            VolunteerProject OldVolunteerProject=volunteerProjectService.getProjectInfoByid(volunteerProject.getId());
            if(!"".equals(fileName)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(OldVolunteerProject.getShowImage());
                //重新上传文件并生成文件名
                if(fileName != null && !"".equals(fileName)){
                    String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String fileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(fileondesPath);
                    try {
                        saveFile(file,filepath.getInputStream(),getfilepath());
                        volunteerProject.setShowImage(fileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                volunteerProject.setShowImage(OldVolunteerProject.getShowImage());
            }
            //删除原来岗位重新添加
            volunteerProjectService.delProjectStations(volunteerProject.getId());
            //更新岗位
            if(StringUtils.isNotBlank(request.getParameter("stationStr"))){
                JSONArray jsonArray=JSONArray.fromObject(request.getParameter("stationStr"));
                addProjectStation(jsonArray,volunteerProject.getId());
            }
            volunteerProjectService.updateVolunteerProject(volunteerProject);
        }else{
            //新增操作 设置ID
            Integer id=volunteerProjectService.getNextIdOfVp(); //拿到volunteer_project id
            if(id == null){
                id=1;
            }
            volunteerProject.setId(id);
            //上传文件处理
            if(fileName != null && !"".equals(fileName)){
                String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                String fileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(fileondesPath);
                //保存文件
                try {
                    saveFile(file,filepath.getInputStream(),getfilepath());
                    volunteerProject.setShowImage(fileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String stationStr=request.getParameter("stationStr");
            if(StringUtils.isNotBlank(stationStr)){
                JSONArray jsonArray=JSONArray.fromObject(request.getParameter("stationStr"));
                addProjectStation(jsonArray,id);
            }
            volunteerProjectService.saveVolunteerProject(volunteerProject);
        }
        try {
            jumpPage(response,1,"editzyxm.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改项目状态
     * @param id
     * @param statue
     * @return
     */
    @RequestMapping("/changeVolunteerProjectStatue/{id}/{statue}")
    public Message changeVolunteerProjectStatue(@PathVariable(value = "id") Integer id, @PathVariable(value = "statue") Integer statue){
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("statue",statue);
        Integer num=volunteerProjectService.updateStatue(map);
        if(num > 0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 报名
     * @param userId 用户ID
     * @param projectId 志愿项目ID
     * @return
     */
    @ApiOperation(value = "用户报名参加项目某岗位",response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID",dataType = "Integer"),
            @ApiImplicitParam(name = "projectId", value = "项目ID",dataType = "Integer"),
            @ApiImplicitParam(name = "stationId", value = "岗位ID",dataType = "Integer")
    })
    @RequestMapping("/signProject/{userId}/{projectId}/{stationId}")
    public Message signProject(@PathVariable(value = "userId") Integer userId, @PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "stationId") Integer stationId){
        ProjectSign projectSign=new ProjectSign();
        projectSign.setUserId(userId);
        projectSign.setProjectId(projectId);
        projectSign.setStationId(stationId);
        volunteerProjectService.addProjectSign(projectSign);
        return new Message(MessageCode.MSG_SUCCESS);

    }

    /**
     * 取消报名
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return
     */
    @ApiOperation(value = "用户取消报名",response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID",dataType = "Integer"),
            @ApiImplicitParam(name = "projectId", value = "项目ID",dataType = "Integer"),
            @ApiImplicitParam(name = "stationId", value = "岗位ID",dataType = "Integer")
    })
    @RequestMapping("/cancelSign/{userId}/{projectId}/{stationId}")
    public Message cancelSign(@PathVariable(value = "userId") Integer userId, @PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "stationId") Integer stationId){
        ProjectSign projectSign=new ProjectSign();
        projectSign.setUserId(userId);
        projectSign.setProjectId(projectId);
        projectSign.setStationId(stationId);
        volunteerProjectService.cancelSign(projectSign);
        return new Message(MessageCode.MSG_SUCCESS);
    }

    /**
     * 更具项目ID 获取报名人数
     * @param projectId
     * @return
     */
    @ApiOperation(value = "更具项目ID 获取报名人列表",response = ProjectSign.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目ID",dataType = "Integer")
    })
    @RequestMapping("/getSignListByprojectId/{projectId}")
    public List<ProjectSign> getSignListByprojectId(@PathVariable(value = "projectId") Integer projectId){
        return volunteerProjectService.getProjectList(projectId);
    }

    /**
     * 删除项目
     * @param id
     * @return
     */
    @RequestMapping("/delProject/{id}")
    public Message delProject(@PathVariable(value = "id") Integer id){
        Integer num=volunteerProjectService.delProject(id);
        if(num > 0){
            return new Message(MessageCode.MSG_SUCCESS);
        }else{
            return new Message(MessageCode.MSG_FAIL);
        }
    }

    /**
     * 通过项目ID查询项目信息
     * @param id
     * @return
     */
    @ApiOperation(value = "通过项目ID查询项目信息",response = VolunteerProject.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目ID",dataType = "Integer")
    })
    @RequestMapping("/getProjectByid/{id}")
    public VolunteerProject getProjectByid(@PathVariable(value = "id") Integer id){
        return volunteerProjectService.getProjectInfoByid(id);
    }

    /**
     * 添加岗位
     * @param jsonArray
     * @param id
     */
    public void addProjectStation(JSONArray jsonArray,Integer id) {
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            String stationName = object.optString("stationName");
            String stationDesc = object.optString("stationDesc");
            String stationConds = object.optString("stationConds");
            String planSignNum = object.optString("planSignNum");
            Station station = new Station();
            station.setProjectId(id);
            try {
                station.setPlanSignNum(Integer.parseInt(planSignNum));
            } catch (Exception e) {
            }
            station.setStationName(stationName);
            station.setStationDesc(stationDesc);
            station.setStationConds(stationConds);
            station.setSignNum(0);//设置默认值
            Integer result = volunteerProjectService.addProjectStation(station);
        }
    }

    /**
     * 更新岗位
     * @param jsonArray
     * @param id
     */
    public void updateStations(JSONArray jsonArray,Integer id){
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            String stationId=object.optString("stationId");
            String stationName = object.optString("stationName");
            String stationDesc = object.optString("stationDesc");
            String stationConds = object.optString("stationConds");
            String planSignNum = object.optString("planSignNum");
            Station station = new Station();
            station.setProjectId(id);
            station.setId(Integer.parseInt(stationId));
            try {
                station.setPlanSignNum(Integer.parseInt(planSignNum));
            } catch (Exception e) {
            }
            station.setStationName(stationName);
            station.setStationDesc(stationDesc);
            station.setStationConds(stationConds);
            station.setSignNum(0);//设置默认值
            Integer result = volunteerProjectService.addProjectStation(station);
        }
    }

    /**
     * 项目排班
     * @return
     */
    @RequestMapping("/ProjectArrange")
    public Message ProjectArrange(@RequestParam(value = "signId") String signId,
                                  @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "startDate") String startDate,
                                  @RequestParam(value = "endDate") String endDate) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH");
        ProjectSignArrange signArrange=new ProjectSignArrange();
        signArrange.setProjectSignId(Integer.parseInt(signId));
        signArrange.setUserId(Integer.parseInt(userId));
        signArrange.setUserName(userService.getUserName(Integer.parseInt(userId)));
        signArrange.setStartDate(startDate);
        signArrange.setEndDate(endDate);
        Calendar startcal=Calendar.getInstance();
        startcal.setTime(simpleDateFormat.parse(startDate));
        signArrange.setStartTime(startcal.getTime());

        Calendar endcal=Calendar.getInstance();
        endcal.setTime(simpleDateFormat.parse(endDate));
        signArrange.setEndTime(endcal.getTime());

        long l=endcal.getTimeInMillis()-startcal.getTimeInMillis();
        int hours=new Long(l/(1000*60*60)).intValue();
        signArrange.setHours(hours);

        volunteerProjectService.saveSignArrange(signArrange);
        return new Message(MessageCode.MSG_SUCCESS);
    }

    /**
     * 获取用户排版记录
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户排班记录",response = ProjectSignArrange.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID",dataType = "Integer")
    })
    @RequestMapping("/getProjectArrangeList/{userId}")
    public List<ProjectSignArrange> getProjectArrangeList(@PathVariable(value = "userId") Integer userId){
        return volunteerProjectService.getListOfArrange(userId);
    }

    public String getfilepath()
    {
        String sysStr=System.getProperties().getProperty("os.name");
        if(sysStr.indexOf("Windows") != -1)
        {
            return windows_vol_pro;
        }else
        {
            return linux_vol_pro;
        }
    }


}
