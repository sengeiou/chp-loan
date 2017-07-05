package com.creditharmony.loan.car.creditorRights.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Class Name CarCreditorRightStatus
 * @author 陈伟东
 * @Create In 2016年3月5日
 */
public enum CarCreditorRightStatus{

	PENDING_SEND("0","未发送"),														
	SENDING("1","发送中"),														
	SUCESS("2","成功发送"),
	FAIL("3","发送失败"),
	SETTLE("4","结清"),
	SETTLE_EARLY("5","提前结清");
	
	private static Map<String, CarCreditorRightStatus> nameMap = new HashMap<String, CarCreditorRightStatus>(
			5);
	private static Map<String, CarCreditorRightStatus> codeMap = new HashMap<String, CarCreditorRightStatus>(
			5);

	static {
		CarCreditorRightStatus[] allValues = CarCreditorRightStatus.values();
		for (CarCreditorRightStatus obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private CarCreditorRightStatus(String code, String name) {
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
	
	public static CarCreditorRightStatus parseByName(String name) {
		return nameMap.get(name);
	}

	public static CarCreditorRightStatus parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
