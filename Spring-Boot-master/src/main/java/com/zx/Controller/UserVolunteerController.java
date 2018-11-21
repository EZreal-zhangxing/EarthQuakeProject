package com.zx.Controller;

import com.zx.Pojo.Message;
import com.zx.Pojo.MessageCode;
import com.zx.Pojo.UserOrder;
import com.zx.Pojo.UserVolunteer;
import com.zx.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "添加用户信息申请信息",response = Message.class,httpMethod = "POST")
    @RequestMapping("/addUservolunteerInfo")
    public Message addUservolunteerInfo(UserVolunteer userVolunteer){
        Integer num =userService.addUserVolunteer(userVolunteer);
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userVolunteer.getUserId());
        userOrder.setType(1);
        userOrder.setScore(10);
        userOrder.setDesc("用户信息搜集加分！加分[10]");
        userService.addUserOrder(userOrder);
        userService.addUserScore(userOrder);

        addMessageToUser("【积分提示】","认证个人信息奖励积分10分！",userVolunteer.getUserId());
        return num>0?new Message(MessageCode.MSG_SUCCESS):new Message(MessageCode.MSG_FAIL);
    }

    @ApiOperation(value = "检查用户是否提交过信息",response = Message.class,httpMethod = "GET")
    @RequestMapping("/checkUserisCommitInfo/{userId}")
    public Message checkUserisCommitInfo(@PathVariable(value = "userId") Integer userId){
        boolean result=userService.checkUserisCommit(userId);
        return result?new Message(MessageCode.MSG_USERINFO_SUCCESS):new Message(MessageCode.MSG_USERINFO_FAIL);
    }
}
