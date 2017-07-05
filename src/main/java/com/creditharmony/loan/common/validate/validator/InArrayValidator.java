package com.creditharmony.loan.common.validate.validator;

import javax.validation.ConstraintValidatorContext;

import com.creditharmony.loan.common.validate.annotation.InArray;

/**
 * 校验值在数组中
 * @author 任志远
 * @date 2017年1月12日
 */
public class InArrayValidator {

	private String[] array;
	
	public void initialize(InArray inArray) {
		array = inArray.array();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {

		for(String s : array){
			if(value.equals(s)){
				return true;
			}
		}
		
		return false;
	}

}
