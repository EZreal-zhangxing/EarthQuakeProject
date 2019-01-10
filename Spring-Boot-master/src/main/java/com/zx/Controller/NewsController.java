package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.NewsService;
import com.zx.Service.PreviewClassService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Api(tags = "志愿新闻模块")
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private PreviewClassService previewClassService;

    @Autowired
    private EncoderHandler encoderHandler;

    Logger logger=Logger.getLogger(NewsController.class);
    @Value("${linux_news}")
    private String LINUX_PATH;

    @Value("${windows_news}")
    private String WIN_PATH;

    /**
     * 获取文章子模块
     * @param model
     * @return
     */
    @ApiOperation(value = "获取文章模块",response = Model.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "模块",required = true, dataType = "string")
    })
    @RequestMapping(value = "/getArtModel/{model}")
    public List<Model> getModelByid(@PathVariable(value="model") String model){
        return newsService.getmodelByid(model);
    }


    /**
     * 根据标题和所属模块查询新闻
     * http://localhost:8080/news/getNewsInfo?newsType=10&pageSize=100&page=1
     * @param title
     * @param page
     * @return
     */
    @ApiOperation(value = "根据标题和所属模块查询新闻",notes ="http://localhost:9230/news/getNewsInfo?newsType=10&pageSize=100&page=1" ,response = Pageinfo.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题",required = false, dataType = "string"),
            @ApiImplicitParam(name = "newsType", value = "新闻类别",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping(value = "/getNewsInfo")
    public Pageinfo getNewsInfo(@RequestParam(value="title",required = false) String title,
                                @RequestParam(value="newsType") Integer newsType,
                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                @RequestParam(value="page",defaultValue="1") String page){
        News news=new News();
        news.setTitle(title);
        news.setNewsType(newsType);
        Integer count=newsService.getCountOfNews(news);
        Pageinfo pi=initpage(count,page,pageSize);
//        pi.setBegin(pi.getShownum()*(pi.getPagenum()-1));
        pi.setConditionNews(news);
        List<News> result=newsService.getListofNews(pi);
        pi.setResult(result);
        return pi;
    }

    @RequestMapping("/deleteNews/{id}")
    public Message deleteNews(@PathVariable(value = "id") Integer id){
        News news=newsService.getNewsByid(id);
        if(news!=null){
            String imgurl=news.getImageUrl();
            if(imgurl!=null && !"".equals(imgurl)){
                //存在图片文件
                delFileByfilePath(imgurl);
            }
            newsService.delNewsByid(id);
        }
        return new Message(MessageCode.MSG_SUCCESS);
    }

    @RequestMapping("/getOneLevelModel")
    public List<Model> getOneLevelModel(){
        return newsService.getOneLevelModel("0");
    }

    @RequestMapping("/getTwoLevelModel/{OpeModelId}")
    public List<Model> getTwoLevelModel(@PathVariable(value = "OpeModelId") String OpeModelId){
        return newsService.getOneLevelModel(OpeModelId);
    }

    @RequestMapping("/addNews")
    public void addNews(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "filepath") MultipartFile filepath,
                        @RequestParam(value = "filepathVideo") MultipartFile filepathVideo){
        String id=request.getParameter("articalId");
        String title=request.getParameter("title"); //标题
        String modelid=request.getParameter("model");//模块ID
        String url=request.getParameter("outUrl"); //文章外链
//        String articelDesc=request.getParameter("articelDesc"); //文章介绍
        String content=request.getParameter("arttext");//文章内容
        News news=new News();
        //设置ID
        news.setId((id!=null && !"".equals(id))?Integer.parseInt(id):null);
        news.setTitle(title);
        news.setNewsType(Integer.parseInt(modelid));
        news.setUrl(url);
        news.setContent(content);
        int result=0;
        //图片文件处理
        String fileName=filepath.getOriginalFilename();
        String videoFilename=filepathVideo.getOriginalFilename();
        if(news.getId() == null){
            //处理图片文件
            if(fileName != null && !"".equals(fileName)){
                String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                String fileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(fileondesPath);
                try {
                    saveFile(file,filepath.getInputStream(),getfilepath());
                    news.setImageUrl(fileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //处理视频文件
            if(videoFilename != null && !"".equals(videoFilename)){
                String hzname=videoFilename.substring(videoFilename.indexOf("."), videoFilename.length());
                String md5filename=encoderHandler.encodeByMD5(videoFilename+System.currentTimeMillis());
                String VfileondesPath=getfilepath()+md5filename+hzname;
                File file=new File(VfileondesPath);
                try {
                    saveFile(file,filepathVideo.getInputStream(),getfilepath());
                    news.setVideoUrl(VfileondesPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result= newsService.saveNews(news);
        }else{
            //获取原始文件存储路径 并删除
            News oldNews=newsService.getNewsByid(news.getId());
            if(!"".equals(fileName)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(oldNews.getImageUrl());
                //重新上传文件并生成文件名
                if(fileName != null && !"".equals(fileName)){
                    String hzname=fileName.substring(fileName.indexOf("."), fileName.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String fileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(fileondesPath);
                    try {
                        saveFile(file,filepath.getInputStream(),getfilepath());
                        news.setImageUrl(fileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                news.setImageUrl(oldNews.getImageUrl());
            }
            //视屏文件处理
            if(!"".equals(videoFilename)){
                //重新选择文件 需要重新上传
                //删除老文件
                delFileByfilePath(oldNews.getVideoUrl());
                //重新上传文件并生成文件名
                if(videoFilename != null && !"".equals(videoFilename)){
                    String hzname=videoFilename.substring(videoFilename.indexOf("."), videoFilename.length());
                    String md5filename=encoderHandler.encodeByMD5(fileName+System.currentTimeMillis());
                    String VfileondesPath=getfilepath()+md5filename+hzname;
                    File file=new File(VfileondesPath);
                    try {
                        saveFile(file,filepathVideo.getInputStream(),getfilepath());
                        news.setVideoUrl(VfileondesPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                news.setVideoUrl(oldNews.getVideoUrl());
            }
            //更新操作
            result=newsService.updateNews(news);
        }
        try {
            jumpPage(response,result,"editnews.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文章信息 + 评论信息
     * @param id
     * @return
     */

    @ApiOperation(value = "通过新闻ID 获取详细信息" ,response = News.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "新闻ID",dataType = "int")
    })
    @RequestMapping("/getNewsByid/{id}")
    public News getNewsByid(@PathVariable(value = "id") Integer id){
        return newsService.getNewsByid(id);
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

    /**
     * 用户添加评论 前端用
     * 传参为json 对象 {'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'};
     * @param starCommon
     * @return
     */
    @ApiOperation(value = "添加评论",response = Message.class,httpMethod = "POST",notes = "{'userId':'1','articalId':'1','userName':'zx','content':'this is test content!'}")
    @ApiImplicitParams({})
    @RequestMapping(value = "/addNewsCommon",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message addCommonStar(@RequestBody Common starCommon){
        starCommon.setType(1);
        Integer num=newsService.addNewsCommon(starCommon);
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
    @RequestMapping("/getListofNewsCommons/{articalId}")
    public List<Common> getListofNewsCommons(@PathVariable(value = "articalId") Integer articalId){
        return newsService.getListOfNewsCommon(articalId);
    }

    /**
     * 增加推荐课程
     * @param request
     * @param response
     */
    @RequestMapping("/addPreviewClass")
    public void addPreviewClass(HttpServletRequest request,HttpServletResponse response){
        String previewClassStr=request.getParameter("jsonstr");
        JSONArray jsonArray =JSONArray.fromObject(previewClassStr);
        LinkedList<PreviewClass> linkedList=new LinkedList<PreviewClass>();
        for(int i=0;i<jsonArray.size();i++){
            PreviewClass previewClass =new PreviewClass();
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            previewClass.setClassName(jsonObject.optString("title"));
            previewClass.setAddress(jsonObject.optString("address"));
            previewClass.setStartDate(jsonObject.optString("startDate"));
            linkedList.add(previewClass);
        }
        previewClassService.BatchInsertPreviewClass(linkedList);

        try {
            jumpPage(response,1,"editPreviewClass.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "查询课程推荐列表信息",httpMethod = "GET",response = Pageinfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页面条数",defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "标题",defaultValue = "1", dataType = "string")
    })
    @RequestMapping("/getPrevieClassList")
    public Pageinfo getPrevieClassList(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @RequestParam(value="page",defaultValue="1") String page){
        Integer num=previewClassService.getNumofClass();
        Pageinfo pageinfo=initpage(num,page,pageSize);
        List<PreviewClass> list = previewClassService.getListofClass(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    @RequestMapping("/delPreviewClass/{id}")
    public Message delPreviewClass(@PathVariable(value = "id") Integer id){
        previewClassService.delPreviewClass(id);
        return new Message(MessageCode.MSG_SUCCESS);
    }


    //通过流读取文件
    @ApiOperation(value = "通过流读取文件",httpMethod = "GET",notes = "返回文件流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filepath", value = "文件物理路径", dataType = "String")
    })
    @RequestMapping("/readpicFile")
    public void readpicFile(HttpServletResponse response,@RequestParam(value = "filepath")  String filepath) throws IOException
    {
        FileInputStream fis=null;
        OutputStream os=null;
        try {
            fis=new FileInputStream(new File(filepath));
            os=response.getOutputStream();
            byte[] b=new byte[1024*1024];
            int len;
            while((len=fis.read(b))!=-1)
            {
                os.write(b,0,len);
            }
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }finally{
            if(os!=null)
            {
                os.flush();
                os.close();
            }
            if(fis!=null)
            {
                fis.close();
            }
        }
    }

}
