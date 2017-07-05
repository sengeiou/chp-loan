package com.creditharmony.loan.channel.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

public class CarCreditorConfirmEntity extends DataEntity<CarCreditorConfirmEntity> {

	private static final long serialVersionUID = 1L;
	/* 借款Id */
	@ExcelField(title = "借款ID", type = 0, align = 2, sort = 60)
	private String loanCode;
	/* 债务人 */
	@ExcelField(title = "债务人", type = 0, align = 2, sort = 100)
	private String loanCustomerName;
	/* 身份证号码 */
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 100)
	private String customerCertNum;
	/* 借款用途 */
	@ExcelField(title = "借款用途", type = 0, align = 2, sort  = 100)
	private String dictLoanUse;
	/* 借款类型 */
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 60)
	private String classType;
	/* 原始期限（月） */
	@ExcelField(title = "原始期限（月）", type = 0, align = 2, sort = 60)
	private Integer loanMonths;
	/* 原始借款开始日期 */
	@ExcelField(title = "原始借款开始日期", type = 0, align = 2, sort = 60)
	private Date loanStartDate;
	/* 原始借款到期日期 */
	@ExcelField(title = "原始借款到期日期", type = 0, align = 2, sort = 60)
	private Date loanEndDate;
	/* 还款日 */
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 60)
	private String replayDay;
	/* 还款方式 */
	@ExcelField(title = "还款方式 ", type = 0, align = 2, sort = 100)
	private String payMethod;
	/* 还款金额 */
	@ExcelField(title = "还款金额", type = 0, align = 2, sort = 60)
	private BigDecimal contractMonthRepayAmount;
	/* 债权金额（元） */
	@ExcelField(title = "债权金额（元） ", type = 0, align = 2, sort = 60)
	private BigDecimal contractAmount;
	/* 债权月利率（%） */
	@ExcelField(title = "债权月利率（%）", type = 0, align = 2, sort = 60)
	private BigDecimal feeMonthRate;

	/* 债权人 */
	@ExcelField(title = "债权人", type = 0, align = 2, sort = 60)
	private String creditor;
	/* 债权导出日期 */
	@ExcelField(title = "债权导出日期 ", type = 0, align = 2, sort = 100)
	private Date creditorExportDate;

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

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getDictLoanUse() {
		return dictLoanUse;
	}

	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getReplayDay() {
		return replayDay;
	}

	public void setReplayDay(String replayDay) {
		this.replayDay = replayDay;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getFeeMonthRate() {
		return feeMonthRate;
	}

	public void setFeeMonthRate(BigDecimal feeMonthRate) {
		this.feeMonthRate = feeMonthRate;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public Date getCreditorExportDate() {
		return creditorExportDate;
	}

	public void setCreditorExportDate(Date creditorExportDate) {
		this.creditorExportDate = creditorExportDate;
	}

}
