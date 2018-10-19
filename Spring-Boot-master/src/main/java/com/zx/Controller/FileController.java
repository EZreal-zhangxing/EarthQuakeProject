package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.PartFileService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxing
 * @Date 2018/10/19 13:34
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @Value("${linux_file}")
    private String LINUX_PATH;

    @Value("${windows_file}")
    private String WIN_PATH;

    @Autowired
    private EncoderHandler encoderHandler;
    @Autowired
    private PartFileService partFileService;

    @ApiOperation(value = "获取文件列表" ,response = Pageinfo.class,httpMethod = "GET")
    @RequestMapping("/getfilelist")
    public Pageinfo getFileList(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "page",defaultValue = "1") String page){
        Integer num=partFileService.getCountFilelist();
        Pageinfo pageinfo=initpage(num,page,pageSize);
        List<PartFile> list=partFileService.getFilelist(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    @RequestMapping("/addpartFile")
    public void addpartFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "efile") MultipartFile efile){

        String score=request.getParameter("score");
        //封面图片处理
        String imagefileName=efile.getOriginalFilename();
        if(StringUtils.isNotEmpty(imagefileName)){
            PartFile partFile=new PartFile();
            String ffname=imagefileName.substring(imagefileName.lastIndexOf("/"),imagefileName.length());
            partFile.setFileName(ffname);
            partFile.setDownloadScore(Integer.parseInt(score));
            String md5Filepath=getMD5Filepath(imagefileName);
            File file=new File(md5Filepath);
            try {
                saveFile(file,efile.getInputStream(),getfilepath());
                partFile.setFilePath(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            partFileService.savepartFile(partFile);
        }
        try {
            jumpPage(response,1,"editzxpx.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @RequestMapping("/deletePartFile/{id}")
    public Message deletePartFile(@PathVariable(value = "id") Integer id){
        PartFile news=partFileService.getFIleByid(id);
        if(news!=null){
            String imgurl=news.getFilePath();
            if(imgurl!=null && !"".equals(imgurl)){
                //存在图片文件
                delFileByfilePath(imgurl);
            }
            partFileService.deletefileByid(id);
        }
        return new Message(MessageCode.MSG_SUCCESS);
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
