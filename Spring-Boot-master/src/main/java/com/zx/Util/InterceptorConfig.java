package com.zx.Util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhangxing
 * @Date 2018/11/1 10:50
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getUserInterceptor()).
//                addPathPatterns("/**").excludePathPatterns("/user/adminlogin/**","/user/checkAdminCode");
        super.addInterceptors(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index.html");
        super.addViewControllers(registry);
    }

    @Bean
    public UserInterceptor getUserInterceptor() {
        return new UserInterceptor();
    }
}
