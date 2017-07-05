package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;

public class DeductCondition {
	
	private String plantCode; // 平台code
	
	private BigDecimal notEnoughProportion;  //余额不足比例

	private Integer  notEnoughBase;  // 余额不足基数
	
	private  BigDecimal    failureRate; // 失败率
	
	private  Integer   failureBase;   // 失败基数
	
	private  Integer  failureNumber;  //失败笔数
	
	private  String moneySymbol1;  // 划扣符号1（1 >,2<=）
	
	private  String   moneySymbol2;// 划扣符号1（1 >,2<=）
	
	private BigDecimal deductMoney1; // 划扣金额
	
	private BigDecimal deductMoney2;  // 划扣金额
	
	private String deductType1;   // 划扣方式1 （0 是实时，1 是批量）
	
	private String deductType2;   // 划扣方式2 （0 是实时，1 是批量）


	public String getPlantCode() {
		return plantCode;
	}

	public BigDecimal getNotEnoughProportion() {
		return notEnoughProportion;
	}

	public Integer getNotEnoughBase() {
		return notEnoughBase;
	}

	public BigDecimal getFailureRate() {
		return failureRate;
	}

	public Integer getFailureBase() {
		return failureBase;
	}

	public Integer getFailureNumber() {
		return failureNumber;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public void setNotEnoughProportion(BigDecimal notEnoughProportion) {
		this.notEnoughProportion = notEnoughProportion;
	}

	public void setNotEnoughBase(Integer notEnoughBase) {
		this.notEnoughBase = notEnoughBase;
	}

	public void setFailureRate(BigDecimal failureRate) {
		this.failureRate = failureRate;
	}

	public void setFailureBase(int failureBase) {
		this.failureBase = failureBase;
	}

	public void setFailureNumber(int failureNumber) {
		this.failureNumber = failureNumber;
	}

	public String getMoneySymbol1() {
		return moneySymbol1;
	}

	public String getMoneySymbol2() {
		return moneySymbol2;
	}

	public BigDecimal getDeductMoney1() {
		return deductMoney1;
	}

	public BigDecimal getDeductMoney2() {
		return deductMoney2;
	}

	public String getDeductType1() {
		return deductType1;
	}

	public String getDeductType2() {
		return deductType2;
	}

	public void setMoneySymbol1(String moneySymbol1) {
		this.moneySymbol1 = moneySymbol1;
	}

	public void setMoneySymbol2(String moneySymbol2) {
		this.moneySymbol2 = moneySymbol2;
	}

	public void setDeductMoney1(BigDecimal deductMoney1) {
		this.deductMoney1 = deductMoney1;
	}

	public void setDeductMoney2(BigDecimal deductMoney2) {
		this.deductMoney2 = deductMoney2;
	}

	public void setDeductType1(String deductType1) {
		this.deductType1 = deductType1;
	}

	public void setDeductType2(String deductType2) {
		this.deductType2 = deductType2;
	}
	
	
}
