package com.creditharmony.loan.channel.finance.view;

import java.util.Date;

/**
 * 债权结清的查询条件字段
 * 
 * @Class Name SettlementOfClaimsParams
 * @author 张建雄
 * @Create In 2016年2月19日
 * 
 */
public class CarSettlementOfClaimsParams {
	/* 借款ID */
	private String loanCode;
	private String loanCodes;
	public String getLoanCodes() {
		return loanCodes;
	}

	public void setLoanCodes(String loanCodes) {
		this.loanCodes = loanCodes;
	}

	/* 提前结清日期 */
	private Date settleStartDate;
	/* 提前结清日期 */
	private Date settleEndDate;
	// 提前结清状态
	private String status;
	// 用户选中的借款id
	private String id;
	private Date settleConfirmDate;
	private Date creditExportDate;
	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public Date getSettleStartDate() {
		return settleStartDate;
	}

	public void setSettleStartDate(Date settleStartDate) {
		this.settleStartDate = settleStartDate;
	}

	public Date getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(Date settleEndDate) {
		this.settleEndDate = settleEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSettleConfirmDate() {
		return settleConfirmDate;
	}

	public void setSettleConfirmDate(Date settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}

	public Date getCreditExportDate() {
		return creditExportDate;
	}

	public void setCreditExportDate(Date creditExportDate) {
		this.creditExportDate = creditExportDate;
	}

}
