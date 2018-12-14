package com.imeee.facade.base.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-14
 * Time: 9:57
 */

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    String userName;
    @NotBlank
    String passWord;
}
