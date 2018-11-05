package com.zx.Controller;

import com.zx.Pojo.Message;
import com.zx.Pojo.MessageCode;
import com.zx.Pojo.Pageinfo;
import com.zx.Pojo.UserMessage;
import com.zx.Service.TraningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxing
 * @Date 2018/11/5 10:28
 */
@Api(tags = "消息模块")
@RestController
@RequestMapping("/userMessage")
public class MessageController extends BaseController {
    @Autowired
    private TraningService traningService;

    @RequestMapping("/AddUserMessage")
    public void AddUserMessage(UserMessage userMessage, HttpServletResponse response){
        Integer num = traningService.addPushMessage(userMessage);
        try {
            jumpPage(response,num,"editMessage.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/showPushMessage")
    public Pageinfo getPagePushMessage(@RequestParam(value = "page",required = false,defaultValue = "1") String page,
                                       @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Integer num = traningService.getCountofPushMessage();
        Pageinfo pageinfo = initpage(num,page,pageSize);
        List<UserMessage> list = traningService.getListofPushMessage(pageinfo);
        pageinfo.setResult(list);
        return pageinfo;
    }

    @ApiOperation(value = "获取推送消息",response = UserMessage.class,httpMethod = "GET")
    @RequestMapping("/getPushMessage")
    public UserMessage getPushMessage(){
        return traningService.getPushMessage();
    }
}
