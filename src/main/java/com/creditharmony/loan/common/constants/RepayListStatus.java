package com.creditharmony.loan.common.constants;

/**
 * 还款状态
 * @Class Name NumberConstants
 * @author 李文勇
 * @Create In 2016年1月5日
 */
public enum RepayListStatus {
	
	PRE_PAYMENT("0", "未申请"),
	REPAYMENT_RETURN("7", "还款退回");
	private String name;
	private String code;
	private RepayListStatus(String code, String name) {
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
	
}
