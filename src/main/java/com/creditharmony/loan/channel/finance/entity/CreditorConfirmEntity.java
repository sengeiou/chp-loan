package com.creditharmony.loan.channel.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

public class CreditorConfirmEntity extends DataEntity<CreditorConfirmEntity> {

	private static final long serialVersionUID = 1L;
	/* 借款Id */
	@ExcelField(title = "借款ID", type = 1, align = 2, sort = 1)
	private String loanCode;
	/* 债务人 */
	@ExcelField(title = "债务人", type = 1, align = 2, sort = 2)
	private String loanCustomerName;
	/* 身份证号码 */
	@ExcelField(title = "身份证号", type = 1, align = 2, sort = 3)
	private String customerCertNum;
	/* 借款用途 */
	@ExcelField(title = "借款用途", type = 1, align = 2, sort  = 4)
	private String dictLoanUse;
	/* 借款类型 */
	@ExcelField(title = "借款类型", type = 1, align = 2, sort = 5)
	private String classType;
	/* 原始期限（月） */
	@ExcelField(title = "原始期限（月）", type = 1, align = 2, sort = 6)
	private Integer loanMonths;
	/* 原始借款开始日期 */
	@ExcelField(title = "原始借款开始日期", type = 1, align = 2, sort = 7)
	private Date loanStartDate;
	/* 原始借款到期日期 */
	@ExcelField(title = "原始借款到期日期", type = 1, align = 2, sort = 8)
	private Date loanEndDate;
	/* 还款方式 */
	@ExcelField(title = "还款方式 ", type = 1, align = 2, sort = 9)
	private String payMethod;
	/* 还款日 */
	@ExcelField(title = "还款日", type = 1, align = 2, sort = 10)
	private String replayDay;
	/* 还款金额 */
	@ExcelField(title = "还款金额", type = 1, align = 2, sort = 11)
	private BigDecimal contractMonthRepayAmount;
	/* 债权金额（元） */
	@ExcelField(title = "债权金额（元） ", type = 1, align = 2, sort = 12)
	private BigDecimal contractAmount;
	/* 债权月利率（%） */
	@ExcelField(title = "债权月利率（%）", type = 1, align = 2, sort = 13)
	private BigDecimal feeMonthRate;
	/* 债权转入金额（元） */
	@ExcelField(title = "债权转入金额（元）", type = 1, align = 2, sort = 15)
	private BigDecimal creditorImportMoney;
	/* 债权转入期限（月） */
	@ExcelField(title = "债权转入期限（月）", type = 1, align = 2, sort = 16)
	private Integer creditorImportMonths;
	/* 债权转出日期 */
	@ExcelField(title = "债权转出日期", type = 1, align = 2, sort = 17)
	private Date creditorOutDate;
	/* 债权导出日期 */
//	@ExcelField(title = "债权导出日期 ", type = 0, align = 2, sort = 100)
	private Date creditorExportDate;
	/* 债权人 */
	@ExcelField(title = "债权人", type = 1, align = 2, sort = 18)
	private String creditor;

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

	public BigDecimal getCreditorImportMoney() {
		return creditorImportMoney;
	}

	public void setCreditorImportMoney(BigDecimal creditorImportMoney) {
		this.creditorImportMoney = creditorImportMoney;
	}

	public Integer getCreditorImportMonths() {
		return creditorImportMonths;
	}

	public void setCreditorImportMonths(Integer creditorImportMonths) {
		this.creditorImportMonths = creditorImportMonths;
	}

	public Date getCreditorOutDate() {
		return creditorOutDate;
	}

	public void setCreditorOutDate(Date creditorOutDate) {
		this.creditorOutDate = creditorOutDate;
	}


}
