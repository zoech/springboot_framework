package com.imeee.facade.testing.model;

import com.imeee.facade.testing.enums.GenderEnum;
import com.imeee.skeletons.facade.inEnumValidation.InEnum;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "请求信息")
    String msg;

    @ApiModelProperty(value = "性别，0男1女2保密", example = "0")
    @InEnum(value = GenderEnum.class, message = "gender 参数不合法")
    Integer gender;

    @ApiModelProperty(value = "请求页大小，不能小于1", example = "10")
    @Positive
    Integer pageSize;

    @ApiModelProperty(value = "请求页码大小，1为第一页", example = "1")
    @PositiveOrZero
    Integer pageNo; // 请求页码
}
