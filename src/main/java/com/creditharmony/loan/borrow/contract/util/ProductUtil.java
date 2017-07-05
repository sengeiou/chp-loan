package com.creditharmony.loan.borrow.contract.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品-简易借枚举
 * @Class Name FileCategory
 * @author 申阿伟
 * @Create In 2017年5月6日
 */
public enum ProductUtil {
	
	PRODUCT_JYJ("A020", "简易借"),
	PRODUCT_NXD("A021", "农信贷");

	
	
	private static Map<String, ProductUtil> nameMap = new HashMap<String, ProductUtil>(
			100);
	private static Map<String, ProductUtil> codeMap = new HashMap<String, ProductUtil>(
			100);

	static {
		ProductUtil[] allValues = ProductUtil.values();
		for (ProductUtil obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProductUtil(String code, String name) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static ProductUtil parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProductUtil parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
