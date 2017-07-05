package com.creditharmony.loan.common.validate.validator;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.ProvinceCityCache;
import com.creditharmony.core.users.entity.ProvinceCity;
import com.creditharmony.loan.common.utils.ProvinceCityUtils;
import com.creditharmony.loan.common.validate.annotation.Address;

/**
 *
 * @author 任志远
 * @date 2017年1月11日
 */
public class AddressValidator implements ConstraintValidator<Address, String> {

	@Override
	public void initialize(Address rightAddress) {}

	@Override
	public boolean isValid(String v, ConstraintValidatorContext context) {
		
		if(v == null){
			return false;
		}
		
		String[] value =  v.split(",");
		
		if(value.length < 3){
			value = ProvinceCityUtils.spiltProvinceCityArea(v);
			if(value == null || StringUtils.isEmpty(value[0]) || StringUtils.isEmpty(value[1]) || StringUtils.isEmpty(value[2])){
				return false;
			}
		}else{
			ProvinceCity area = ProvinceCityCache.getInstance().get(value[2]);
			if(area == null){
				return false;
			}
			ProvinceCity city = ProvinceCityCache.getInstance().get(area.getParentId());
			if(city == null || !city.getId().equals(value[1])){
				return false;
			}
			ProvinceCity province = ProvinceCityCache.getInstance().get(city.getParentId());
			if(province == null || !province.getId().equals(value[0])){
				return false;
			}
		}
		
		return true;
	}

}
