package com.creditharmony.loan.borrow.payback.entity;

/**
 * 银行跳转规则
 * @author 翁私
 *
 */
public class BankRule {
	
	private String id;
	private String bankCode;  // 银行code
	
	private String  platformRule;  // 规则code
	
	private String  deductType; //划扣方式

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getDeductType() {
		return deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}

	public String getPlatformRule() {
		return platformRule;
	}

	public void setPlatformRule(String platformRule) {
		this.platformRule = platformRule;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
