package com.zx.Controller;

import com.zx.Pojo.Message;
import com.zx.Pojo.MessageCode;
import com.zx.Pojo.UrlClickNum;
import com.zx.Service.UrlClickService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxing
 * @Date 2019/1/11
 */
@Api(tags = "请求统计模块")
@RestController
@RequestMapping("/url")
public class UrlController extends BaseController {
    @Autowired
    private UrlClickService urlClickService;

    @ApiOperation(value = "添加模块统计记录" ,response = Message.class,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelUrl", value = "模块连接",required = true,dataType = "string"),
            @ApiImplicitParam(name = "modelName", value = "模块名称",dataType = "string",required = true)
    })
    @RequestMapping("/addUrlClick")
    public Message addUrlClickService(@RequestParam(value = "modelUrl") String modelUrl,
                                      @RequestParam(value = "modelName") String modelName){
        UrlClickNum urlClickNum=new UrlClickNum();
        urlClickNum.setModelUrl(modelUrl);
        urlClickNum.setModelDesc(modelName);
        urlClickService.addNumofClick(urlClickNum);
        return new Message(MessageCode.MSG_SUCCESS);
    }
}
