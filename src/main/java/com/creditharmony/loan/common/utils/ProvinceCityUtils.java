package com.creditharmony.loan.common.utils;

import java.util.Map;

import com.creditharmony.core.cache.ProvinceCityCache;
import com.creditharmony.core.users.entity.ProvinceCity;

/**
 *
 * @author 任志远
 * @date 2017年2月21日
 */
public class ProvinceCityUtils {

	/**
	 * 把省市区地址一整串转换成省市区Code和地址的数组
	 * By 任志远 2017年2月21日
	 *
	 * @param s 省市区地址
	 * @return	new Stirng[]{"省Code","市Code","区Code","地址"}
	 */
	public static String[] spiltProvinceCityArea(String s){
		
		String[] value = s.split("省|市|区|兵团|师|团|农场");
		if(value.length < 3){
			return null;
		}
		String[] result = new String[4];
		Map<String, ProvinceCity> addressMap = ProvinceCityCache.getInstance().getMap();
		for(Map.Entry<String, ProvinceCity> entry : addressMap.entrySet()){
			if(entry.getValue().getParentId().equals("0") && entry.getValue().getAreaName().startsWith(value[0])){
				result[0] = entry.getValue().getAreaCode();
				break;
			}
		}
		for(Map.Entry<String, ProvinceCity> entry : addressMap.entrySet()){
			if(entry.getValue().getParentId().equals(result[0]) && entry.getValue().getAreaName().startsWith(value[1])){
				result[1] = entry.getValue().getAreaCode();
				break;
			}
		}
		for(Map.Entry<String, ProvinceCity> entry : addressMap.entrySet()){
			if(entry.getValue().getParentId().equals(result[1]) && entry.getValue().getAreaName().startsWith(value[2])){
				result[2] = entry.getValue().getAreaCode();
				break;
			}
		}
		
		if(value.length > 3){
			result[3] = "";
			for(int i =3 ; i < value.length; i++){
				result[3] += value[i];
			}
		}
		return result;
	}
}
