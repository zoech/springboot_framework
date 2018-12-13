package com.imeee.skeletons.facade.basemodel;

import com.imeee.skeletons.facade.BizErrorCodeEnum;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:28
 */

@Data
public class BaseResponse {
    String code; // 响应码
    String msg; // 请求结果

    String extraMsg; // 额外的返回信息

    PageData data; // 结果

    public BaseResponse(){

    }

    public BaseResponse(PageData data, String code, String msg, String extraMsg){
//        this.seq = seq;
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.extraMsg = extraMsg;
    }

    public BaseResponse(PageData data, BizErrorCodeEnum errorEnum, String extraMsg){
        this(data,errorEnum.getCode(),errorEnum.getMsg(), extraMsg);
    }

    public static BaseResponse buildSuccessResponse(PageData data){
        return new BaseResponse(data, BizErrorCodeEnum.SUCCESS, null);
    }

    public static BaseResponse buildDefaultSuccessResponse(){
        return new BaseResponse(null, BizErrorCodeEnum.SUCCESS, null);
    }

    public static BaseResponse buildDefaultFailedResponse(String extraMsg){
        return new BaseResponse(null, BizErrorCodeEnum.UNKNOWN_EXCEPTION, extraMsg);
    }

    public static BaseResponse buildDefaultResponse(BizErrorCodeEnum errorCodeEnum, String extraMsg){
        return new BaseResponse(null, errorCodeEnum, extraMsg);
    }

    public void setError(BizErrorCodeEnum bizErrorCodeEnum){
        this.code = bizErrorCodeEnum.getCode();
        this.msg = bizErrorCodeEnum.getMsg();
    }
}
