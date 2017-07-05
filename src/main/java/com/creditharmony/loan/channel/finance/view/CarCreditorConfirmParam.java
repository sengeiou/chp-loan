package com.creditharmony.loan.channel.finance.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权确认的查询条件字段
 * 
 * @Class Name CreditorConfirmParam
 * @author 张建雄
 * @Create In 2016年2月19日
 */
public class CarCreditorConfirmParam {
	/* 借款ID */
	private String loanCode;
	private String loanCodes;
	/* 债权金额 */
	private BigDecimal stratContractAmount;
	private BigDecimal endContractAmount;
	/* 原始借款开始日期 */
	private Date loanStartDate;
	private Date loanEndDate;
	/* 债权导出日期 */
	private Date stratCreditExportDate;
	private Date endCreditExportDate;

	// 用户选中的借款id
	private String id;
	private Date creditConfirmDate;
	private Date creditExportDate;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public BigDecimal getStratContractAmount() {
		return stratContractAmount;
	}

	public void setStratContractAmount(BigDecimal stratContractAmount) {
		this.stratContractAmount = stratContractAmount;
	}

	public BigDecimal getEndContractAmount() {
		return endContractAmount;
	}

	public void setEndContractAmount(BigDecimal endContractAmount) {
		this.endContractAmount = endContractAmount;
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

	public Date getStratCreditExportDate() {
		return stratCreditExportDate;
	}

	public void setStratCreditExportDate(Date stratCreditExportDate) {
		this.stratCreditExportDate = stratCreditExportDate;
	}

	public Date getEndCreditExportDate() {
		return endCreditExportDate;
	}

	public void setEndCreditExportDate(Date endCreditExportDate) {
		this.endCreditExportDate = endCreditExportDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreditConfirmDate() {
		return creditConfirmDate;
	}

	public void setCreditConfirmDate(Date creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}

	public Date getCreditExportDate() {
		return creditExportDate;
	}

	public void setCreditExportDate(Date creditExportDate) {
		this.creditExportDate = creditExportDate;
	}

	public String getLoanCodes() {
		return loanCodes;
	}

	public void setLoanCodes(String loanCodes) {
		this.loanCodes = loanCodes;
	}

}
