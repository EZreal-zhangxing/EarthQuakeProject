package com.zx.Controller;

import com.zx.Pojo.ForHelp;
import com.zx.Pojo.Message;
import com.zx.Pojo.MessageCode;
import com.zx.Pojo.Pageinfo;
import com.zx.Service.ForhelpService;
import com.zx.Util.EncoderHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxing
 * @Date 2018/10/24 15:24
 */
@Api(tags = "求助中心模块")
@RestController
@RequestMapping("/forhelp")
public class HelpController extends BaseController {

    @Autowired
    private EncoderHandler encoderHandler;

    @Autowired
    private ForhelpService forhelpService;

    @ApiOperation(value = "保存求助信息 参数分别为{helptitle:求助标题，content:内容，area:内容，address：地址，createman:创建人，telphone：电话} 以上参数以form表单提交" ,response = Pageinfo.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helptitle", value = "标题", dataType = "string"),
            @ApiImplicitParam(name = "content", value = "内容",dataType = "string"),
            @ApiImplicitParam(name = "area", value = "区域",dataType = "string"),
            @ApiImplicitParam(name = "address", value = "地址",dataType = "string"),
            @ApiImplicitParam(name = "createman", value = "求助人",dataType = "string"),
            @ApiImplicitParam(name = "telphone", value = "电话", dataType = "string")
    })
    @RequestMapping("/saveHelpinfo")
    public Message saveHelpInfo(HttpServletRequest request, HttpServletResponse response){
        String comefrom=request.getParameter("comefrom");

        String title = request.getParameter("helptitle");
        String content=request.getParameter("content");
        String area=request.getParameter("area");
        String address=request.getParameter("address");
        String createman=request.getParameter("createman");
        String telphone=request.getParameter("telphone");

        ForHelp forHelp=new ForHelp();
        forHelp.setHelpTitle(title);
        forHelp.setHelpContent(content);
        forHelp.setArea(area);
        forHelp.setAddress(address);
        forHelp.setCreateMan(createman);
        forHelp.setTelphone(telphone);
        forhelpService.addForhelpInfo(forHelp);
        if(StringUtils.equals(comefrom,"web")){
            try {
                jumpPage(response,1,"edithelp.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        {
            return new Message(MessageCode.MSG_SUCCESS);
        }
        return null;
    }

    @ApiOperation(value = "查询求助信息",response = Pageinfo.class,httpMethod = "GET")
    @RequestMapping("/getHelpInfo")
    public Pageinfo getHelpInfo(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "page",defaultValue = "1") String page){
        Integer num=forhelpService.getNumofHelpInfo();
        Pageinfo pageinfo=initpage(num,page,pageSize);
        List<ForHelp> list = forhelpService.getListofHelp(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    @RequestMapping("/delHelpInfo/{id}")
    public Message delHelpInfo(@PathVariable(value = "id") Integer id){
        forhelpService.delForhelp(id);
        return new Message(MessageCode.MSG_SUCCESS);
    }
}
