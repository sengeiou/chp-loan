package com.creditharmony.loan.borrow.grant.entity.ex;

import java.io.Serializable;

import com.creditharmony.bpm.frame.view.WorkItemView;

public class DistachParamEx implements Serializable {
	private static final long serialVersionUID = 1L;
	private WorkItemView workItemView;
	private String  contractCode;
	private String loanCode;
	private String applyId;
	private String channelCode;
	private String loanInfoOldOrNewFlag; // 新旧申请表表标识
	
	public WorkItemView getWorkItemView() {
		return workItemView;
	}
	public void setWorkItemView(WorkItemView workItemView) {
		this.workItemView = workItemView;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
}
