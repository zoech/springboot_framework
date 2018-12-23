package com.imeee.skeletons.facade.exceptionResponse.bizexception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 9:46
 */
public class DuplicateLoginException extends Exception {
    public DuplicateLoginException(){

    }

    public DuplicateLoginException(String msg){
        super(msg);
    }
}
