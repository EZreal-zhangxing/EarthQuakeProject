package com.zx.Controller;

import com.zx.Pojo.Message;
import com.zx.Pojo.MessageCode;
import com.zx.Pojo.UserVolunteer;
import com.zx.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxing
 * @Date 2018/10/25 16:26
 */
@Api(tags = "用户信息收集模块")
@RestController
@RequestMapping("/uservolunteer")
public class UserVolunteerController extends BaseController {


    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户资源申请信息",response = Message.class,httpMethod = "POST")
    @RequestMapping("/addUservolunteerInfo")
    public Message addUservolunteerInfo(UserVolunteer userVolunteer){
        Integer num =userService.addUserVolunteer(userVolunteer);
        return num>0?new Message(MessageCode.MSG_SUCCESS):new Message(MessageCode.MSG_FAIL);
    }
}
