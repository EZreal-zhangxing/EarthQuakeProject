package com.zx.Controller;

import com.zx.Pojo.*;
import com.zx.Service.UserCertificateService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @Date 2018/10/23 11:00
 */
@Api(tags = "用户证书模块")
@RequestMapping("/certificate")
@RestController
public class UserCertificateController extends BaseController {
    @Value("${linux_certificate}")
    private String LINUX_PATH;

    @Value("${windows_certificate}")
    private String WIN_PATH;

    @Autowired
    private EncoderHandler encoderHandler;

    @Autowired
    private UserCertificateService userCertificateService;

    @RequestMapping("/addUsercertificate")
    public void addUsercertificate(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "profile") MultipartFile profile){
        String userName=request.getParameter("username");
        String certificateNo=request.getParameter("certificateNo");

        UserCertificate userCertificate= new UserCertificate();
        userCertificate.setUserName(userName);
        userCertificate.setUserIdentify(certificateNo);

        String fileName=profile.getOriginalFilename();
        if(StringUtils.isNotEmpty(fileName)){
            String md5Filepath=getMD5Filepath(fileName);
            File file=new File(md5Filepath);
            try {
                saveFile(file,profile.getInputStream(),getfilepath());
                userCertificate.setFilePath(md5Filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userCertificateService.saveUserCertificate(userCertificate);
        try {
            jumpPage(response,1,"editusercertificate.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取证书列表
     * @param pageSize
     * @param page
     * @return
     */
    @RequestMapping("/getUserCertificateList")
    public Pageinfo getUserCertificateList(@RequestParam(value = "username",required = false,defaultValue = "") String username,
                                           @RequestParam(value = "certificateno",required = false,defaultValue = "") String certificateno,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "page",defaultValue = "1") String page){
        UserCertificate userCertificate=new UserCertificate();
        userCertificate.setUserName(username);
        userCertificate.setUserIdentify(certificateno);
        Integer num=userCertificateService.getCount(userCertificate);
        Pageinfo pageinfo=initpage(num,page,pageSize);
        pageinfo.setConditionUC(userCertificate);
        List<UserCertificate> list = userCertificateService.getList(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    @ApiOperation(value = "查询用户证书信息" ,response = Examination.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名",dataType = "String",required = true),
            @ApiImplicitParam(name = "identifyNo", value = "证书号", dataType = "string",required = true)
    })
    @RequestMapping("/getUserCertificate")
    public UserCertificate getUserCertificate(@RequestParam(value = "username",required = true) String username,
                                              @RequestParam(value = "identifyNo",required = true) String identifyNo) {
        UserCertificate userCertificate = new UserCertificate();
        userCertificate.setUserName(username);
        userCertificate.setUserIdentify(identifyNo);
        UserCertificate uc = userCertificateService.getUserCerificate(userCertificate);
        return uc;
    }

    @RequestMapping("/delUserCertificate/{id}")
    public Message delUserCertificate(@PathVariable(value = "id") Integer id){
        userCertificateService.delUserCertificate(id);
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
