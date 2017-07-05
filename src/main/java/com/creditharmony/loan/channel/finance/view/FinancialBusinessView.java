package com.creditharmony.loan.channel.finance.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 大金融查询条件
 * 
 * @Class Name FinancialBusinessView
 * @author 张建雄
 * @Create In 2016年2月18日
 */
public class FinancialBusinessView{
	// 借款Id
	private String loanCode;
	private BigDecimal stratContractAmount;
	private BigDecimal endContractAmount;
	// 债权状态
	private Integer creditType;
	/* 债权确认时间 */
	private Date stratCreditorConfirmDate;
	private Date endCreditorConfirmDate;
	/*结清状态  提前结清*/
	private String status;
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

	public Integer getCreditType() {
		return creditType;
	}

	public void setCreditType(Integer creditType) {
		this.creditType = creditType;
	}

	public Date getStratCreditorConfirmDate() {
		return stratCreditorConfirmDate;
	}

	public void setStratCreditorConfirmDate(Date stratCreditorConfirmDate) {
		this.stratCreditorConfirmDate = stratCreditorConfirmDate;
	}

	public Date getEndCreditorConfirmDate() {
		return endCreditorConfirmDate;
	}

	public void setEndCreditorConfirmDate(Date endCreditorConfirmDate) {
		this.endCreditorConfirmDate = endCreditorConfirmDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
