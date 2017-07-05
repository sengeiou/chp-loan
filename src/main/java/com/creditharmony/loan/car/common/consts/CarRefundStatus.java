package com.creditharmony.loan.car.common.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 车借退款状态信息
 * @Class Name CarRefundStatus
 * @author 蒋力
 * @Create In 2016年3月2日
 */
public enum CarRefundStatus {
														
	CAR_AUDIT_STATUS_W("0","待审核"),
	CAR_AUDIT_STATUS_Y("1","待处理"),
	CAR_AUDIT_STATUS_N("2","审核拒绝"),
	CAR_AUDIT_STATUS_Z("3","处理中"),
	CAR_AUDIT_STATUS_P("4","已处理"),
	CAR_RETURN_STATUS_W("0","未退回"),
	CAR_RETURN_STATUS_Y("1","已退回");
	
	private static Map<String, CarRefundStatus> nameMap = new HashMap<String, CarRefundStatus>(
			5);
	private static Map<String, CarRefundStatus> codeMap = new HashMap<String, CarRefundStatus>(
			5);
	
	static {
		CarRefundStatus[] allValues = CarRefundStatus.values();
		for (CarRefundStatus obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}
	
	private String name;
	private String code;
	
	private CarRefundStatus(String code, String name) {
		this.name = name;
		this.code = code;
	}
	
	public static Map<String, CarRefundStatus> getNameMap() {
		return nameMap;
	}
	public static void setNameMap(Map<String, CarRefundStatus> nameMap) {
		CarRefundStatus.nameMap = nameMap;
	}
	public static Map<String, CarRefundStatus> getCodeMap() {
		return codeMap;
	}
	public static void setCodeMap(Map<String, CarRefundStatus> codeMap) {
		CarRefundStatus.codeMap = codeMap;
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
	
	@Override
	public String toString() {
		return this.name;
	}
}