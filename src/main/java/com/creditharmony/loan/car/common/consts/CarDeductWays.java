package com.creditharmony.loan.car.common.consts;

import java.util.HashMap;
import java.util.Map;

import com.creditharmony.core.type.SystemFlag;

/**
 * 车借划扣服务用系统处理ID类型
 * 2016年12月12日
 */
public enum CarDeductWays {

	CJ_01(SystemFlag.CARLOAN.value + "05", "车借放款"),
	CJ_02(SystemFlag.CARLOAN.value + "06", "车借催收服务划扣"),
	CJ_03(SystemFlag.CARLOAN.value + "07", "车借服务费退款"),
	CJHK_09(SystemFlag.CARLOAN.value + "09", "车借集中还款划扣"),
	CJHK_10(SystemFlag.CARLOAN.value + "10", "车借非集中还款划扣");

	private static Map<String, CarDeductWays> nameMap = new HashMap<String, CarDeductWays>();
	private static Map<String, CarDeductWays> codeMap = new HashMap<String, CarDeductWays>();
	static {
		CarDeductWays[] allValues = CarDeductWays.values();
		for (CarDeductWays obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}
	private String name;
	private String code;

	private CarDeductWays(String code, String name) {
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

	public static CarDeductWays parseByName(String name) {
		return nameMap.get(name);
	}

	public static CarDeductWays parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
