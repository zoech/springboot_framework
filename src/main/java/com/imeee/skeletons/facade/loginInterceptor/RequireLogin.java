package com.imeee.skeletons.facade.loginInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 8:51
 */

@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface RequireLogin {
    boolean value() default true;
}
