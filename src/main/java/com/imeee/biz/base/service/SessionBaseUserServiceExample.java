package com.imeee.biz.base.service;

import com.imeee.skeletons.baseService.user.BaseUserServices;
import com.imeee.skeletons.baseService.user.LoginParams;
import com.imeee.skeletons.facade.bizexception.DuplicateLoginException;
import com.imeee.skeletons.facade.bizexception.IncorrcetPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 9:17
 */
@Service
public class SessionBaseUserServiceExample implements BaseUserServices {

    static String userName = "admin";
    static String pwd = "123456";

    public boolean validateLogin(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpSession session = attributes.getRequest().getSession(false);
        if(session == null){
            return false;
        }

        String token = (String) (session.getAttribute("loginName"));
        if(token == null || "".equals(token)){
            return false;
        }

        return true;
    }

    public boolean login(LoginParams loginParams) throws Exception{

        if(this.validateLogin()){
            throw new DuplicateLoginException();
        }

        if(!SessionBaseUserServiceExample.userName.equals(loginParams.getUserName()) ||
                !SessionBaseUserServiceExample.pwd.equals(loginParams.getPassWord())){
            throw new IncorrcetPasswordException();
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attributes.getRequest().getSession().setAttribute("loginName", loginParams.getUserName());
        return true;
    }

    public boolean logout(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        if(session != null){
            session.invalidate();
        }

        return true;
    }
}
