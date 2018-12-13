package com.imeee.skeletons.facade.inEnumValidation;

import com.imeee.skeletons.common.enumcheckable.EnumCheckable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 15:59
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
@Documented
public @interface InEnum {
    String message() default "{com.payeco.oms.platform.facade.constraint.annotation.InEnum}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends EnumCheckable> value();
}