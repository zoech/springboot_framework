package com.imeee.skeletons.facade.basemodel;

import com.imeee.skeletons.facade.BizErrorCodeEnum;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "返回跟踪号，用来定位请求相关的日志")
    String seq;
    @ApiModelProperty(value = "响应码")
    String code;
    @ApiModelProperty(value = "请求状态信息描述")
    String msg;

    @ApiModelProperty(value = "处理请求花费的毫秒数")
    Long millis;

    @ApiModelProperty(value = "额外的返回信息")
    String extraMsg;

    @ApiModelProperty(value = "结果")
    PageData data;

    public BaseResponse(){

    }

    public BaseResponse(PageData data, String code, String msg, String extraMsg){
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
