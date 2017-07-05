package com.creditharmony.loan.common.validate.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.loan.common.validate.annotation.InDict;

/**
 * 字典值校验
 * @author 任志远
 * @date 2017年1月10日
 */
public class IntegerInDictValidator implements ConstraintValidator<InDict, Integer> {

	private String type;
	
	@Override
	public void initialize(InDict inDict) {
		this.type = inDict.type();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if(value == null){
			return false;
		}
		List<Dict> dictList = DictUtils.getNewDictList(type);
		for(Dict dict : dictList){
			if(dict.getValue().equals(value.toString())){
				return true;
			}
		}
		return false;
	}

}
