package com.imeee.skeletons.facade.requestTracing;

import com.alibaba.fastjson.JSON;
import com.imeee.skeletons.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-23
 * Time: 11:24
 */

@Component
@Slf4j
public class RequestTracingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置请求的跟踪号，便于日志排查
        String seq = this.generateSeq();
        MDC.put(Constants.SEQ_ATTRIBUTE, seq);

        String httpMethod = request.getMethod();
        log.info("received " + httpMethod + " request from " + request.getRemoteHost() + ", request path : " + request.getServletPath() + ", assign seq : " + seq);

        if(HttpMethod.GET.name().equals(httpMethod)) {
            log.info("request parameters: " + JSON.toJSONString(request.getParameterMap()));
        }

        long start = System.currentTimeMillis();
        request.setAttribute(Constants.MILLIS_START, start);

//        if(HttpMethod.GET.name().equals(httpMethod)) {
//            log.info("begin to handle request for seq " + seq + ", request: \n" + JSON.toJSONString(request.getParameterMap()));
//        } else if (HttpMethod.POST.name().equals(httpMethod)){
//            Map<String, String> map = new HashMap<>();
//            Enumeration<String> enumeration = request.getAttributeNames();
//
////            byte[] bytesContent = new byte[request.getContentLength()];
//            char[] cbuf = new char[request.getContentLength()];
////            request.getInputStream().read(bytesContent);
//            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
//            streamReader.read(cbuf);
//
//            log.info("begin to handle request for seq " + seq + ", request: \n" + new String(cbuf));
//        } else {
//            log.info("begin to handle request for seq " + seq);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 打印请求处理结束
        try {
            Long startTime = (Long) request.getAttribute(Constants.MILLIS_START);
            Long finishedTime = (Long) request.getAttribute(Constants.MILLIS_FINISHD);

            // 顺便打印处理时长
            log.info("request finished in " + (finishedTime - startTime) + "millis.");
        } catch (Exception e){
            log.error("print process time error", e);
            log.info("request finished.");
        }
    }

    private String generateSeq(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmSS");
        String datePattern = dateFormat.format(cal.getTime());

        String randomPattern = StringUtils.generateRanomString(8);

        return datePattern + randomPattern;
    }
}
