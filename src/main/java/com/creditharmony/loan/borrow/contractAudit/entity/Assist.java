package com.creditharmony.loan.borrow.contractAudit.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class Assist extends DataEntity<Assist> {
	private String present_audit_operator;//当前审批操作人
	private String if_dispatch;//是否需要分单
	private String orderby;//排序字段
	private String loan_code;//
	
	private String auditusercode;//审核操作人
	private Date audittime;//审核时间
	private String auditreturnreason;//审核退回的原因
	private String kingstatus;//金账户状态
	private String timeOutFlag;
	
	
	
	public String getTimeOutFlag() {
		return timeOutFlag;
	}
	public void setTimeOutFlag(String timeOutFlag) {
		this.timeOutFlag = timeOutFlag;
	}
	public String getAuditusercode() {
		return auditusercode;
	}
	public void setAuditusercode(String auditusercode) {
		this.auditusercode = auditusercode;
	}
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public String getAuditreturnreason() {
		return auditreturnreason;
	}
	public void setAuditreturnreason(String auditreturnreason) {
		this.auditreturnreason = auditreturnreason;
	}
	public String getKingstatus() {
		return kingstatus;
	}
	public void setKingstatus(String kingstatus) {
		this.kingstatus = kingstatus;
	}
	public String getPresent_audit_operator() {
		return present_audit_operator;
	}
	public void setPresent_audit_operator(String present_audit_operator) {
		this.present_audit_operator = present_audit_operator;
	}
	public String getIf_dispatch() {
		return if_dispatch;
	}
	public void setIf_dispatch(String if_dispatch) {
		this.if_dispatch = if_dispatch;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}
	
}
