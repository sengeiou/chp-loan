/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsLoanStatus.java
 * @Create By 王彬彬
 * @Create In 2015年12月17日 下午2:31:17
 */
package com.creditharmony.loan.borrow.account.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * （借款申请状态）
 * 
 * @Class Name LoanApplyStatus
 * @author 王彬彬
 * @Create In 2015年12月17日
 */
public enum FileType {
	SETTLED("001", "提前还款申请书模板", "BusinessFile"),

	CF("002", "财富&P2P卡号手机号变更模板", "BusinessFile"),

	JX("003", "金信卡号手机号变更模板", "BusinessFile"),

	TG("004", "金账户（TG)卡号手机号变更模板", "BusinessFile"),

	XT("005", "信托卡号手机号变更模板", "BusinessFile"),
	
	JCRBG("006", "借款人客户信息变更申请", "BusinessFile");

	private static Map<String, FileType> nameMap = new HashMap<String, FileType>(
			10);
	private static Map<String, FileType> codeMap = new HashMap<String, FileType>(
			10);
	private static Map<String, FileType> sysFlagMap = new HashMap<String, FileType>(
			10);

	static {
		FileType[] allValues = FileType.values();
		for (FileType obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
			sysFlagMap.put(obj.getSysFlag(), obj);
		}
	}

	private String name;
	private String code;
	private String sysFlag;

	private FileType(String code, String name, String sysFlag) {
		this.name = name;
		this.code = code;
		this.sysFlag = sysFlag;
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

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public static FileType parseByName(String name) {
		return nameMap.get(name);
	}

	public static FileType parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
