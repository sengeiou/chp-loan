package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;

public class ImportEntity {
	
	// 企业流水号
	private String id;
	// 拆分金额
	private BigDecimal splitAmount;
	// 回盘状态
	private String  tradingStatus;
	// 失败原因
	private String  failReason;
	
	private boolean success;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public String getTradingStatus() {
		return tradingStatus;
	}

	public void setTradingStatus(String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	

}
