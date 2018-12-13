package com.imeee.skeletons.facade;

import com.imeee.skeletons.common.enumcheckable.EnumCheckable;
import com.imeee.skeletons.facade.bizexception.NotLoginException;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:48
 */

@Getter
public enum BizErrorCodeEnum implements EnumCheckable {

    SUCCESS("0000", "请求成功", null),

    UNKNOWN_EXCEPTION("9999", "系统未知错误", null),
    NOT_LOGIN("0001", "未登录或者登录超时", NotLoginException.class),

    REQUEST_PARAM_ILLEGAL_GET("0002", "请求参数非法",BindException .class),
    REQUEST_PARAM_ILLEGAL_POST("0002", "请求参数非法",MethodArgumentNotValidException .class),


//    MM_NO_SUCH_CATEGORY("b001", "Mobile Market 没有对应的内容分类", NoMmCategoryException.class),
//    MM_NO_CFG_4_CATEGORY("b002", "没有对应分类的打分配置", NoEsRelativeCfgForCategoryException.class),
//    ESRESTCLIENT_ERROR("s001", "ElasticSearch rest client 搜索接口错误,", EsRestClientException.class),


    JSON_PARSE_ERROR("s002", "请求无法解释，请查看请求信息格式是否合法",HttpMessageNotReadableException .class)
            ;



    // 返回码
    private final String code;

    // 返回消息
    private final String msg;

    // 返回这个error code对应的异常
    private final Class<? extends Exception> exceptionClz;


    BizErrorCodeEnum(String code, String msg, Class<? extends Exception> exception) {
        this.code = code;
        this.msg = msg;
        this.exceptionClz = exception;
    }

    @Override
    public String _getCode(){
        return this.code;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     */
    public static BizErrorCodeEnum getByCode(String code) {
        for (BizErrorCodeEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
