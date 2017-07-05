package com.creditharmony.loan.common.entity;

/**
 * codename实体
 * @Class Name CodeName
 * @author wengsi
 * @Create In 2016年3月30日
 */
public class CodeName {
	
	private String code; // code
	
	private String name; 
 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CodeName(String code,String name){
		this.name = name;
		this.code = code;
	}
	
	

}
