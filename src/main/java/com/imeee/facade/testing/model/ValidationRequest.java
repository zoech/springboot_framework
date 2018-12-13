package com.imeee.facade.testing.model;

import com.imeee.facade.testing.enums.GenderEnum;
import com.imeee.skeletons.facade.inEnumValidation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *   api/test/validation 接口的请求参数
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 16:27
 */

@Data
public class ValidationRequest {
    @NotBlank
    String msg; // 请求信息
    @InEnum(value = GenderEnum.class, message = "gender 参数不合法")
    Integer gender; // 男或女或保密

    @Positive
    Integer pageSize; // 请求页大小
    @PositiveOrZero
    Integer pageNo; // 请求页码
}
