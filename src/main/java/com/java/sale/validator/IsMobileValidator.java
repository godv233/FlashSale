package com.java.sale.validator;

import com.java.sale.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号的验证规则
 * @author 曾伟
 * @date 2019/11/10 16:00
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    private boolean required=false;//值是否是必须的

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){//是必须的
            return ValidatorUtils.isMobile(s);
        }else{//不是必须的
            if (StringUtils.isEmpty(s)){
                return true;
            }else {
                return ValidatorUtils.isMobile(s);
            }
        }
    }
}
