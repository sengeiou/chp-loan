package com.creditharmony.loan.common.validate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.common.validate.annotation.InArray;

/**
 * 校验Integer类型数据是不是在数组中, null 返回 false
 * @author 任志远
 * @date 2017年1月12日
 */
public class InStringArrayValidator extends InArrayValidator implements ConstraintValidator<InArray, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if(StringUtils.isEmpty(value)){
			return false;
		}
		
		return super.isValid(value, context);
	}

}
