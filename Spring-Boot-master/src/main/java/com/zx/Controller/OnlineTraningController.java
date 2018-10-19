package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.TraningService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangxing
 * @Date 2018/10/10 9:03
 */
@Api(tags = "在线培训模块")
@RestController
@RequestMapping("/traning")
public class OnlineTraningController extends BaseController {
    Logger logger=Logger.getLogger(OnlineTraningController.class);

    @Value("${linux_traning}")
    private String LINUX_PATH;

    @Value("${windows_traning}")
    private String WIN_PATH;

    @Autowired
    private TraningService traningService;

    @Autowired
    private EncoderHandler encoderHandler;

    /**
     * 查询课程信息 公用
     * @param title
     * @param type
     * @param pageSize
     * @param page
     * @return
     */
    @RequestMapping("/getListOfClassInfo")
    public Pageinfo getListOfClassInfo(@RequestParam(value = "title",required = false) String title,
                                       @RequestParam(value="type") Integer type,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page",defaultValue = "1") String page){
        OnlineTraning onlineTraning=new OnlineTraning();
        onlineTraning.setTitle(title);
        onlineTraning.setType(type);
        Integer num=traningService.getCountofTraningByCond(onlineTraning);
        Pageinfo pageinfo=initpage(num,page,pageSize);
        pageinfo.setConditionOnlineTraning(onlineTraning);
        List<OnlineTraning> list=traningService.getListofTraning(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    /**
     * 添加课程信息
     * @param request
     * @param response
     * @param imagefilepath 封面图片信息
     * @param videofilepath 视屏信息
     * @param classfilepath 课堂资料信息
     * @param teacherfilepath 老师视屏介绍
     * @return
     */
    @RequestMapping("/addClassInfo")
    public void addClassInfo(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "imagefilepath") MultipartFile imagefilepath,
                                @RequestParam(value = "videofilepath") MultipartFile videofilepath,
                                @RequestParam(value = "classfilepath") MultipartFile classfilepath,
                                @RequestParam(value = "teacherfilepath") MultipartFile teacherfilepath){
        String title=request.getParameter("title");
        String type=request.getParameter("model");
        String outUrl=request.getParameter("outUrl");
        String description=request.getParameter("description");
        String outline=request.getParameter("outline");
        String classfile=request.getParameter("classfile");
        String teacherDesc=request.getParameter("teacherDesc");

        OnlineTraning onlineTraning=new OnlineTraning();
        onlineTraning.setTitle(title);
        onlineTraning.setType(Integer.parseInt(type));
        onlineTraning.setOutUrl(outUrl);
        onlineTraning.setDescription(description);
        onlineTraning.setOutline(outline);
        onlineTraning.setClassfileDesc(classfile);
        onlineTraning.setTeacherDesc(teacherDesc);

        //封面图片处理
        String imagefileName=imagefilepath.getOriginalFilename();
        if(StringUtils.isNotEmpty(imagefileName)){
            String md5Filepath=getMD5Filepath(imagefileName);
            File file=new File(md5Filepath);
            try {
                saveFile(file,imagefilepath.getInputStream(),getfilepath());
                onlineTraning.setImageUrl(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //视频文件处理
        String videofilename=videofilepath.getOriginalFilename();
        if(StringUtils.isNotEmpty(videofilename)){
            String md5Filepath=getMD5Filepath(videofilename);
            File file=new File(md5Filepath);
            try {
                saveFile(file,videofilepath.getInputStream(),getfilepath());
                onlineTraning.setVideoUrl(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //课堂资料信息处理
        String classfileName=classfilepath.getOriginalFilename();
        if(StringUtils.isNotEmpty(classfileName)){
            String md5Filepath=getMD5Filepath(classfileName);
            File file=new File(md5Filepath);
            try {
                saveFile(file,classfilepath.getInputStream(),getfilepath());
                onlineTraning.setClassfile(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //讲师视频介绍
        String teacherfileName=teacherfilepath.getOriginalFilename();
        if(StringUtils.isNotEmpty(teacherfileName)){
            String md5Filepath=getMD5Filepath(teacherfileName);
            File file=new File(md5Filepath);
            try {
                saveFile(file,teacherfilepath.getInputStream(),getfilepath());
                onlineTraning.setTeacherDescVideo(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Integer result=traningService.saveOnlineTraning(onlineTraning);
        try {
            jumpPage(response,result,"editzxpx.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更具type查询各个模块 视频信息
     * @param type
     * @param pageSize
     * @param page
     * @return
     */
    @ApiOperation(value = "更具type查询各个模块" ,response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "练习类别",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getListofTraningInfo")
    public Pageinfo getListofTraningInfo(@RequestParam(value="type") Integer type,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "page",defaultValue = "1") String page){
        OnlineTraning onlineTraning=new OnlineTraning();
        onlineTraning.setType(type);
        Integer num=traningService.getCountofTraningByCond(onlineTraning);
        Pageinfo pageinfo=initpage(num,page,pageSize);
        pageinfo.setConditionOnlineTraning(onlineTraning);
        List<OnlineTraning> list = traningService.getListofTraning(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    /**
     * 通过ID 查询时评详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "通过ID 查询时评详细信息" ,response = OnlineTraning.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章ID",required = true,dataType = "int")
    })
    @RequestMapping("/getTraningInfoById/{id}")
    public OnlineTraning getTraningInfoById(@PathVariable(value = "id") Integer id){
        return traningService.getTraningByid(id);
    }

    /**
     * 添加题目信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addQuestion")
    public void addOnLineQuestion(HttpServletRequest request,HttpServletResponse response){
        String articalId=request.getParameter("articalId");
        String questionJson=request.getParameter("questionjson");
        if(StringUtils.isNotBlank(questionJson)){
            JSONArray jsonArray=JSONArray.fromObject(questionJson);
            for (int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String questionId= UUID.randomUUID().toString().replaceAll("-", "");

                TraningQuestion traningQuestion=new TraningQuestion();

                traningQuestion.setId(questionId);
                traningQuestion.setTraningId(Integer.parseInt(articalId));
                String questioninfo=jsonObject.optString("questioninfo");
                traningQuestion.setTitle(questioninfo);
                String answer=jsonObject.optString("answer");
                traningQuestion.setAnswer(answer);
                traningQuestion.setSeqNo(i+1);

                ArrayList<TraningAnswer> list=new ArrayList<TraningAnswer>();
                JSONArray ja=jsonObject.getJSONArray("selectinfo");
                for(int j=0;j<ja.size();j++){
                    JSONObject object=ja.getJSONObject(j);
                    if(StringUtils.isNotBlank(object.optString("content"))){
                        TraningAnswer traningAnswer=new TraningAnswer();
                        traningAnswer.setQuestionId(questionId);
                        traningAnswer.setSelectName(object.optString("select"));
                        traningAnswer.setSelectContent(object.optString("content"));
                        list.add(traningAnswer);
                    }
                }

                if(list.size()>0){
                    traningQuestion.setType(1);
                    traningService.batchaddTraningAnswer(list);
                }else{
                    traningQuestion.setType(2);
                }
                //保存问题信息
                traningService.addTraningQuestion(traningQuestion);
            }
        }
        try {
            jumpPage(response,1,"adminzxpx.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加在线考试习题
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveQuestion")
    public Message saveQuestion(HttpServletRequest request,HttpServletResponse response){
        String examName=request.getParameter("examName");
        String questionJson=request.getParameter("questionjson");
        String examId=UUID.randomUUID().toString().replaceAll("-","");
        Examination examination=new Examination();
        examination.setExamId(examId);
        examination.setTitle(examName);
        //保存试卷信息
        traningService.saveExamination(examination);

        if(StringUtils.isNotBlank(questionJson)){
            JSONArray jsonArray=JSONArray.fromObject(questionJson);
            for (int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Question question=new Question();
                question.setExamId(examId);
                String questionId= UUID.randomUUID().toString().replaceAll("-", "");
                question.setId(questionId);
                String questioninfo=jsonObject.optString("questioninfo");
                question.setQuestionTitle(questioninfo);
                String answer=jsonObject.optString("answer");
                question.setQuestionAnswer(answer);

                ArrayList<Answer> list=new ArrayList<Answer>();
                JSONArray ja=jsonObject.getJSONArray("selectinfo");
                for(int j=0;j<ja.size();j++){
                    JSONObject object=ja.getJSONObject(j);
                    if(StringUtils.isNotBlank(object.optString("content"))){
                        Answer answerObj=new Answer();
                        answerObj.setQuestionId(questionId);
                        answerObj.setSelectName(object.optString("select"));
                        answerObj.setSelectContent(object.optString("content"));
                        list.add(answerObj);
                    }
                }
                if(list.size()>0){
                    question.setType(1);
                    traningService.batchSaveAnswer(list);
                }else{
                    question.setType(2);
                }
                //保存问题信息
                traningService.saveQuestion(question);
            }
        }
        try {
            jumpPage(response,1,"exitQuestion.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交卷信息
     * name=examStr的表单数据格式为：
     * {"examId":"id",answers:[{"questionId":"id","answer":"A"},{"questionId":"id","answer":"B"}]}
     * @return 得分
     */
    @ApiOperation(value = "交卷信息" ,notes = "name=examStr的表单数据格式为：{\"examId\":\"id\",answers:[{\"questionId\":\"id\",\"answer\":\"A\"},{\"questionId\":\"id\",\"answer\":\"B\"}]}",response = UserExam.class,httpMethod = "POST")
    @RequestMapping(value = "/saveUserExam")
    public UserExam saveUserExam(HttpServletRequest request) {
        String examInfo=request.getParameter("examStr");
        JSONObject examObject=JSONObject.fromObject(examInfo);
        String examId=examObject.optString("examId");

        JSONArray jsonArray=examObject.optJSONArray("answers");
        Map<String,String> questionList= traningService.getQuestionAndAnswer(examId);
        int count=0;
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String questionId = jsonObject.optString("questionId");
            String answer = jsonObject.optString("answer");
            if(StringUtils.equals(answer,questionList.get(questionId))){
                count++;
            }
        }
        UserExam userExam = new UserExam();
        userExam.setExamId(examId);
        userExam.setScore(count*1.0F/questionList.size()*100);
        traningService.saveUserExam(userExam);
        return userExam;
    }

    /**
     * 获取试卷信息列表
     * @return
     */
    @ApiOperation(value = "获取试卷信息列表" ,response = Examination.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题",dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getExaminationList")
    public Pageinfo getExaminationList(@RequestParam(value = "title",required = false) Integer title,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page",defaultValue = "1") String page){

        Integer num=traningService.getCountExam();
        Pageinfo pageinfo=initpage(num,page,pageSize);
        List<Examination> list=traningService.getListofExam(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    /**
     * 更具试卷ID 获取试卷题目信息
     * @param examId
     * @return
     */
    @ApiOperation(value = "更具试卷ID 获取试卷题目信息" ,response = Question.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "试卷ID", dataType = "String")
    })
    @RequestMapping("/getQuestionByExamId/{examId}")
    public List<Question> getQuestionByExamId(@PathVariable(value = "examId") String examId){
        return traningService.getlistofQuestion(examId);
    }


    /**
     * 将文件名 转换成md5文件路径
     * @param filename
     * @return
     */
    public String getMD5Filepath(String filename){
        String hzname=filename.substring(filename.indexOf("."), filename.length()); //后缀名
        String md5filename=encoderHandler.encodeByMD5(filename+System.currentTimeMillis()); //md5 文件名
        String fileondesPath=getfilepath()+md5filename+hzname; //文件路径
        return fileondesPath;
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
            return WIN_PATH;
        }else
        {
            return LINUX_PATH;
        }
    }
}
