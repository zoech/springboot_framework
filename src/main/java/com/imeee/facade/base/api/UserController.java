package com.imeee.facade.base.api;

import com.imeee.facade.base.model.LoginRequest;
import com.imeee.skeletons.baseService.user.BaseUserServices;
import com.imeee.skeletons.baseService.user.LoginParams;
import com.imeee.skeletons.facade.basemodel.BaseResponse;
import com.imeee.skeletons.facade.loginInterceptor.RequireLogin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 9:56
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    BaseUserServices baseUserServices;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@Valid @RequestBody LoginRequest request) throws Exception{
        LoginParams params = new LoginParams();
        BeanUtils.copyProperties(request, params);

        baseUserServices.login(params);

        return BaseResponse.buildDefaultSuccessResponse();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @RequireLogin
    public BaseResponse logout(){
        baseUserServices.logout();
        return BaseResponse.buildDefaultSuccessResponse();
    }

}
