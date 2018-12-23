package com.imeee.skeletons.facade.requestTracing;

import com.imeee.skeletons.facade.basemodel.BaseResponse;
import com.imeee.skeletons.facade.requestTracing.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-23
 * Time: 11:38
 */

@ControllerAdvice
@Slf4j
public class RequestTracingResponseBodyAdvice implements ResponseBodyAdvice<BaseResponse> {

    @Override
    public boolean supports(MethodParameter var1, Class<? extends HttpMessageConverter<?>> var2){

        return true;
    }

    @Override
    public BaseResponse beforeBodyWrite(@Nullable BaseResponse baseResponse, MethodParameter var2, MediaType var3, Class<? extends HttpMessageConverter<?>> var4, ServerHttpRequest var5, ServerHttpResponse var6){
        log.info("in request tracing response body advice");
        String seq = (String) MDC.get(Constants.SEQ_ATTRIBUTE);
        // 返回加上请求序列号
        baseResponse.setSeq(seq);

        // 返回加上处理时间
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        try {
            long finishedTime = System.currentTimeMillis();
            request.setAttribute(Constants.MILLIS_FINISHD, finishedTime); // 写入以便后面打印
            Long startTime = (Long) request.getAttribute(Constants.MILLIS_START);
            baseResponse.setMillis(finishedTime - startTime);
        } catch (Exception e){
            log.error("can not set millis for response.", e);
        }

        return baseResponse;
    }
}
