/**
 * @Probject Name: chp-core
 * @Path: com.creditharmony.core.loan.MailCompany.java
 * @Create By 张进
 * @Create In 2016年1月6日 
 */
package com.creditharmony.loan.borrow.serve.constants;

import java.util.HashMap;
import java.util.Map;

import com.creditharmony.common.util.StringUtils;

/**
 * 快递公司
 * 
 * @Class Name MailCompany
 * @author 王彬彬
 * @Create In 2016年1月6日
 */
public enum MailCompany {
	ST("0","申通"),
	SF("1","顺丰加急"),
	EMS("2","EMS"),
	YT("3","圆通"),
	ZT("4","中通"),
	YD("5","韵达"),
	QF("6","全峰"),
	TT("7","天天");
	private static Map<String, MailCompany> nameMap = new HashMap<String, MailCompany>(
			10);
	private static Map<String, MailCompany> codeMap = new HashMap<String, MailCompany>(
			10);

	static {
		MailCompany[] allValues = MailCompany.values();
		for (MailCompany obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private MailCompany(String code, String name) {
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

	public static MailCompany parseByName(String name) {
		return nameMap.get(name);
	}

	public static MailCompany parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	// 通过name查询code
	public static String getCodeByname(String name){
		MailCompany[] allValues = MailCompany.values();
		for (MailCompany obj : allValues) {
			if(StringUtils.equals(obj.getName(), name)){
				return obj.getCode();
			}
		}
		return null;
	}
}
