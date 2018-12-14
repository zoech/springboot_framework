package com.imeee.skeletons.facade.loginInterceptor;

import com.imeee.skeletons.baseService.user.BaseUserServices;
import com.imeee.skeletons.facade.bizexception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 8:54
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    BaseUserServices baseUserServices;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 只有标记了 @RequireLogin 且value 为true， 才进行登陆验证
        RequireLogin requireLogin = handlerMethod.getMethod().getAnnotation(RequireLogin.class);
        if(requireLogin == null || !requireLogin.value()){
            return true;
        }

        if(!baseUserServices.validateLogin()){
            throw new NotLoginException();
        }

        return true;
    }
}
