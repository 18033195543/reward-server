package com.kdl.common.framework.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class EmailConstraint implements ConstraintValidator<Email, String> {

	private String regexp;
	
	@Override
	public void initialize(Email constraintAnnotation) {
		this.regexp = constraintAnnotation.regexp();
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
