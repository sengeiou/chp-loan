package com.creditharmony.loan.car.carGrant.ex;

import java.io.Serializable;

import com.creditharmony.bpm.frame.view.WorkItemView;

public class CarDistachParamEx implements Serializable {
	private static final long serialVersionUID = 1L;
	private WorkItemView workItemView;
	private String  contractCode;
	private String applyId;
	private String loanCode;
	//放款金额
	private double grantAmount;
	//合同版本号
	private String contractVersion;
	
	public double getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(double grantAmount) {
		this.grantAmount = grantAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
}
