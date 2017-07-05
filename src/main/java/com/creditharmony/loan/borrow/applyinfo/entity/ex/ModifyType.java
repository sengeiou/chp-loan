package com.creditharmony.loan.borrow.applyinfo.entity.ex;
import java.util.HashMap;
import java.util.Map;
/**
 * 修改类型
 * @Class Name ModifyType
 * @author lirui
 * @Create In 2016年2月18日
 */
public enum ModifyType {
		
	LOAN_CUSTOMER("0","客户信息"),
	
	LOAN_MATE("1","配偶资料"),
	
	LOAN_APPLY("2","信借申请"),
	
	LOAN_COBO("3","共同借款人"),
	
	LOAN_CREDIT("4","信用资料"),
	
	LOAN_COMPANY("5","职业信息/公司资料"),
	
	LOAN_HOUSE("6","房产资料"),
	
	LOAN_CONTRACT("7","联系人资料"),
	
	LOAN_BANK("8","银行卡资料");
	
	private static Map<String, ModifyType> nameMap = new HashMap<String, ModifyType>(
			100);
	private static Map<String, ModifyType> codeMap = new HashMap<String, ModifyType>(
			100);
	
	static {
		ModifyType[] allValues = ModifyType.values();
		for (ModifyType obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}
	
	private String name;
	private String code;
	private ModifyType(String code, String name) {
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
	
	public static Map<String, ModifyType> getNameMap() {
		return nameMap;
	}

	public static void setNameMap(Map<String, ModifyType> nameMap) {
		ModifyType.nameMap = nameMap;
	}

	public static Map<String, ModifyType> getCodeMap() {
		return codeMap;
	}

	public static void setCodeMap(Map<String, ModifyType> codeMap) {
		ModifyType.codeMap = codeMap;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
