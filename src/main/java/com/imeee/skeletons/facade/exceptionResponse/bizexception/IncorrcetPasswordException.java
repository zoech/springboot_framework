package com.imeee.skeletons.facade.exceptionResponse.bizexception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 9:30
 */
public class IncorrcetPasswordException extends Exception {
    public IncorrcetPasswordException(){

    }

    public IncorrcetPasswordException(String msg){
        super(msg);
    }
}
