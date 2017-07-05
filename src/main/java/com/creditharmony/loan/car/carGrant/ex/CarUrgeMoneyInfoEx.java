package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class CarUrgeMoneyInfoEx extends DataEntity<CarUrgeMoneyInfoEx> {
	
	//催收服务费id
	private String urgeId;
	//关联放款id
	private String rGrantId;
	// 放款金额
	private double deductAmount;//划扣金额
	private String ruleString;
	private String autoDeductFlag;
	private String dictDealType;

	public String getUrgeId() {
		return urgeId;
	}
	public void setUrgeId(String urgeId) {
		this.urgeId = urgeId;
	}
	public String getrGrantId() {
		return rGrantId;
	}
	public void setrGrantId(String rGrantId) {
		this.rGrantId = rGrantId;
	}
	public double getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(double deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getRuleString() {
		return ruleString;
	}
	public void setRuleString(String ruleString) {
		this.ruleString = ruleString;
	}
	public String getAutoDeductFlag() {
		return autoDeductFlag;
	}
	public void setAutoDeductFlag(String autoDeductFlag) {
		this.autoDeductFlag = autoDeductFlag;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	
}
