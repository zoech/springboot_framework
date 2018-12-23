package com.imeee.skeletons.facade.exceptionResponse;

import com.imeee.skeletons.facade.basemodel.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:57
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse defaultExceptionHandler(Exception exception){

        // 先查找BizeErrorCodeEnum是否有对应的预设错误码，有的话直接返回
        BizErrorCodeEnum[] bizErrorCodeEnums = BizErrorCodeEnum.values();
        for(BizErrorCodeEnum errorEnum : bizErrorCodeEnums){
            if(exception.getClass().equals(errorEnum.getExceptionClz())){
                if(errorEnum.getIsError()){
                    // 如果BizErrorCodeEnum 中指定需要打印为ERROR级别
                    log.error("global exception handler: ", exception);
                } else {
                    log.info("global exception handler: ", exception);
                }
                return BaseResponse.buildDefaultResponse(errorEnum, exception.getMessage());
            }
        }


        // 无法根据Exception 定位错误，则返回系统错误
        log.error("global exception handler: ", exception);
        return BaseResponse.buildDefaultFailedResponse(exception.getMessage());
    }

}