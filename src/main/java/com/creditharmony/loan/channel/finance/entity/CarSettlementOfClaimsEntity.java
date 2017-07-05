package com.creditharmony.loan.channel.finance.entity;

import java.math.BigDecimal;
import java.sql.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

public class CarSettlementOfClaimsEntity extends DataEntity<CarSettlementOfClaimsEntity> {

	private static final long serialVersionUID = 1L;
	//借款ID
	@ExcelField(title = "借款ID", type = 0, align = 2, sort = 1)
	private String loanCode;
	/* 债务人 */
	@ExcelField(title = "债务人", type = 0, align = 2, sort = 2)
	private String loanCustomerName;
	/* 原始借款开始日期 */
	@ExcelField(title = "原始借款开始日期 ", type = 0, align = 2, sort = 3)
	private Date loanStartDate;
	/* 债权月利率（%） */
	@ExcelField(title = "债权月利率（%）", type = 0, align = 2, sort = 4)
	private BigDecimal feeMonthRate;
	/* 债权转入金额（元） */
	@ExcelField(title = "债权转入金额（元）", type = 0, align = 2, sort = 5)
	private BigDecimal contractAmount;
	/* 提前结清日期 */
	@ExcelField(title = "提前结清日期", type = 0, align = 2, sort = 6)
	private Date settleDate;
	
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public BigDecimal getFeeMonthRate() {
		return feeMonthRate;
	}
	public void setFeeMonthRate(BigDecimal feeMonthRate) {
		this.feeMonthRate = feeMonthRate;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	
}
