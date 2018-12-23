package com.imeee.skeletons.facade.requestTracing;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-23
 * Time: 11:45
 */

@ControllerAdvice
@Slf4j
public class RequestTracingRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter var1, Type var2, Class<? extends HttpMessageConverter<?>> var3){
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage var1, MethodParameter var2, Type var3, Class<? extends HttpMessageConverter<?>> var4) throws IOException {
        return var1;
    }

    @Override
    public Object afterBodyRead(Object var1, HttpInputMessage var2, MethodParameter var3, Type var4, Class<? extends HttpMessageConverter<?>> var5){
        log.info("request body: " + JSON.toJSONString(var1));
        return var1;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object var1, HttpInputMessage var2, MethodParameter var3, Type var4, Class<? extends HttpMessageConverter<?>> var5){
        return var1;
    }
}
