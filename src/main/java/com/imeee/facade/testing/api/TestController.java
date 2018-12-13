package com.imeee.facade.testing.api;


import com.imeee.facade.testing.model.ValidationRequest;
import com.imeee.skeletons.facade.basemodel.BaseResponse;
import com.imeee.skeletons.facade.bizexception.NotLoginException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:05
 */

@RestController
@RequestMapping("api/test")
public class TestController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String testGet(){
        return "spring boot framework";
    }


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String testPost(){
        return this.testGet();
    }

    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public String notLogin() throws Exception{

        // 在api接口处理的任何时刻，直接抛出对应的 biz 异常即可，GlobalExceptionHandler 会根据异常类型返回信息到前端
        throw new NotLoginException();
    }

    @RequestMapping(value = "/validation", method = RequestMethod.GET)
    public BaseResponse validation(@Valid ValidationRequest request){
//        BaseResponse response = BaseResponse.buildDefaultSuccessResponse();
//        response.setMsg("参数解析成功");
//        return response;

        return this.validationPost(request);
    }

    @RequestMapping(value = "/validation", method = RequestMethod.POST)
    public BaseResponse validationPost(@Valid ValidationRequest request){
        BaseResponse response = BaseResponse.buildDefaultSuccessResponse();
        response.setMsg("参数解析成功");
        return response;
    }

}
