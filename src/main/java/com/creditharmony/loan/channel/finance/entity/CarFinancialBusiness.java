package com.creditharmony.loan.channel.finance.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class CarFinancialBusiness extends DataEntity<CarFinancialBusiness> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loanCode;
	private String creditType;
	private Date creditExportDate;
	private Date creditConfirmDate;
	private Date settleConfirmDate;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public Date getCreditExportDate() {
		return creditExportDate;
	}

	public void setCreditExportDate(Date creditExportDate) {
		this.creditExportDate = creditExportDate;
	}


	public void setCreditConfirmDate(Timestamp creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}


	public void setSettleConfirmDate(Timestamp settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreditConfirmDate() {
		return creditConfirmDate;
	}

	public void setCreditConfirmDate(Date creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}

	public Date getSettleConfirmDate() {
		return settleConfirmDate;
	}

	public void setSettleConfirmDate(Date settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}

}
