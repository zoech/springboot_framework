package com.imeee.skeletons.facade.exceptionResponse.bizexception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:54
 */
public class NotLoginException extends Exception {
    public NotLoginException(){

    }
    public NotLoginException(String msg){
        super(msg);
    }
}
