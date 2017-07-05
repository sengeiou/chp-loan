package com.creditharmony.loan.yunwei.fk.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

public class PaybackBlueAmount extends DataEntity<PaybackBlueAmount> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7210334674825219956L;
	
	private String parContractCode;
	
	private String startDate;
	
	private String endDate;
	
	//===================================================//
	private String blueAmountId;

	private String rMonthId;

	private String dealTime;
	// 交易类型 0：转入 1：转出
	private String tradeType;
	// 交易用途字典值
	private String dictDealUse;
	// 交易金额
	private BigDecimal tradeAmount;
	// 蓝补余额
	private BigDecimal surplusBuleAmount;
	//
	private String dictOffsetType;
	//
	private String contractCode;
	// =============================================//
	// 实际还款日期
	private String actualRepayDate;
	// 还款金额
	private BigDecimal actualRepayMoney;
	
	public String getBlueAmountId() {
		return blueAmountId;
	}

	public void setBlueAmountId(String blueAmountId) {
		this.blueAmountId = blueAmountId;
	}

	public String getrMonthId() {
		return rMonthId == null ? "" :rMonthId ;
	}

	public void setrMonthId(String rMonthId) {
		this.rMonthId = rMonthId;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getDictDealUse() {
		return dictDealUse;
	}

	public void setDictDealUse(String dictDealUse) {
		this.dictDealUse = dictDealUse;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getSurplusBuleAmount() {
		return surplusBuleAmount;
	}

	public void setSurplusBuleAmount(BigDecimal surplusBuleAmount) {
		this.surplusBuleAmount = surplusBuleAmount;
	}

	public String getDictOffsetType() {
		return dictOffsetType == null ? "" : dictOffsetType;
	}

	public void setDictOffsetType(String dictOffsetType) {
		this.dictOffsetType = dictOffsetType;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getActualRepayDate() {
		return actualRepayDate;
	}

	public void setActualRepayDate(String actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}

	public BigDecimal getActualRepayMoney() {
		return actualRepayMoney;
	}

	public void setActualRepayMoney(BigDecimal actualRepayMoney) {
		this.actualRepayMoney = actualRepayMoney;
	}

	public String getParContractCode() {
		return parContractCode;
	}

	public void setParContractCode(String parContractCode) {
		this.parContractCode = parContractCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
