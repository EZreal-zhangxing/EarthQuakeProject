package com.zx.Util;

import com.zx.Service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * @author zhangxing
 * @Date 2018/11/1 10:44
 */
public class UserInterceptor implements HandlerInterceptor {
    @Resource(name = "userService")
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查cookie
        Cookie[] cookies = request.getCookies();
        String userId="";
        String token="";
        Integer num =0;
        if(cookies!=null){
            for (int i=0;i<cookies.length;i++){
                if(StringUtils.equals(cookies[i].getName(),"token")){
                    token = cookies[i].getValue();
                }
                if(StringUtils.equals(cookies[i].getName(),"userId")){
                    userId = cookies[i].getValue();
                }
            }
            num = userService.checkAdminLogin(userId,token);
        }
        return num>0?true:false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(Calendar.getInstance().getTime() + this.getClass().getName() + "[postHandle] run");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(Calendar.getInstance().getTime() + this.getClass().getName() + "[afterCompletion] run");
    }

}
