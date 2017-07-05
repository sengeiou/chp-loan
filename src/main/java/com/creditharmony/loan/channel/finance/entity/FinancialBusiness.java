package com.creditharmony.loan.channel.finance.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class FinancialBusiness extends DataEntity<FinancialBusiness> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loanCode;
	private String creditType;
	private Date creditExportDate;
	private Timestamp creditConfirmDate;
	private Timestamp settleConfirmDate;
	private String createBy;
	private Timestamp createTime;
	private String modifyBy;
	private Timestamp modifyTime;

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

	public Timestamp getCreditConfirmDate() {
		return creditConfirmDate;
	}

	public void setCreditConfirmDate(Timestamp creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}

	public Timestamp getSettleConfirmDate() {
		return settleConfirmDate;
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

	public Timestamp getCreateTime() {
		return createTime;
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

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}
