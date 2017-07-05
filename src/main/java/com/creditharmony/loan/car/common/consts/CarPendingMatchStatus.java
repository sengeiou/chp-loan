package com.creditharmony.loan.car.common.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 待还款匹配状态信息
 * @Class Name CarPendingMatchStatus
 * @author 蒋力
 * @Create In 2016年3月5日
 */
public enum CarPendingMatchStatus {
	
	REPAY_MATCH_STATUS_WAIT("0","未匹配"),
	REPAY_MATCH_STATUS_SUCCESS("1","匹配成功"),
	REPAY_MATCH_STATUS_BACK("2","匹配退回");
	
	private static Map<String, CarPendingMatchStatus> nameMap = new HashMap<String, CarPendingMatchStatus>(
			5);
	private static Map<String, CarPendingMatchStatus> codeMap = new HashMap<String, CarPendingMatchStatus>(
			5);
	
	static {
		CarPendingMatchStatus[] allValues = CarPendingMatchStatus.values();
		for (CarPendingMatchStatus obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}
	
	private String name;
	private String code;
	
	private CarPendingMatchStatus(String code, String name) {
		this.name = name;
		this.code = code;
	}
	
	public static Map<String, CarPendingMatchStatus> getNameMap() {
		return nameMap;
	}
	public static void setNameMap(Map<String, CarPendingMatchStatus> nameMap) {
		CarPendingMatchStatus.nameMap = nameMap;
	}
	public static Map<String, CarPendingMatchStatus> getCodeMap() {
		return codeMap;
	}
	public static void setCodeMap(Map<String, CarPendingMatchStatus> codeMap) {
		CarPendingMatchStatus.codeMap = codeMap;
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