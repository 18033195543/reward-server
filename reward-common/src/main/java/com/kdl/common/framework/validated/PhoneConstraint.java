package com.kdl.common.framework.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class PhoneConstraint implements ConstraintValidator<Phone, String> {

	private String regexp;

    @Override
    public void initialize(Phone phoneAnnotation) {
        this.regexp = phoneAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;        
        }
        if (value.matches(regexp)) {
            return true;
        }
        return false;
    }

}
