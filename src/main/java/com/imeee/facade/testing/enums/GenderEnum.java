package com.imeee.facade.testing.enums;

import com.imeee.skeletons.common.enumcheckable.EnumCheckable;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 16:24
 */

@Getter
public enum GenderEnum implements EnumCheckable {
    MALE(0, "男"),
    FEMAL(1, "女"),
    UNKNOWN(2, "保密")

    ;

    Integer code;
    String description;

    GenderEnum(int code, String description){
        this.code = code;
        this.description = description;
    }
    @Override
    public Integer _getCode(){
        return this.code;
    }
}
