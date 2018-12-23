package com.imeee.boot.config;

import com.imeee.skeletons.facade.loginInterceptor.LoginInterceptor;
import com.imeee.skeletons.facade.requestTracing.RequestTracingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 所有的拦截器配置都配置到这里
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 10:30
 */
@SpringBootConfiguration
public class InterceptorConfig {

    @Autowired
    LoginInterceptor loginInterceptor; // 登陆拦截器

    @Autowired
    RequestTracingInterceptor requestTracingInterceptor; // 请求跟踪拦截器

    @Bean
    WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                // 配置请求跟踪设置
                registry.addInterceptor(requestTracingInterceptor).addPathPatterns("/**");

                // 配置登陆状态校验
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns("/**");

            }
        };
    }
}
