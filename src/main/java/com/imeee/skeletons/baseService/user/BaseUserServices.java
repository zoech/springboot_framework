package com.imeee.skeletons.baseService.user;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 8:57
 */
public interface BaseUserServices {
    public boolean validateLogin();

    public boolean login(LoginParams loginParams) throws Exception;

    public boolean logout();
}
