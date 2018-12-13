package com.imeee.skeletons.facade.inEnumValidation;

import com.imeee.skeletons.common.enumcheckable.EnumCheckInUtils;
import com.imeee.skeletons.common.enumcheckable.EnumCheckable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhangyi
 * Date: 2018-12-13
 * Time: 16:00
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    private Class<? extends EnumCheckable> enumClz = null;
    @Override
    public void initialize(InEnum constraintAnnotation) {
        this.enumClz = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o == null){
            return true;
        } else {
            if(Integer.class.isInstance(o)){
                if(((int)o) < 0){
                    return true;
                }
            }
        }
        try {
            return EnumCheckInUtils.isInEnum(o, this.enumClz);

        } catch (Exception e) {
            return false;
        }
    }
}
