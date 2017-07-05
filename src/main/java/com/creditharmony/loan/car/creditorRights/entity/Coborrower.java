package com.creditharmony.loan.car.creditorRights.entity;

import com.creditharmony.core.persistence.DataEntity;

public class Coborrower extends DataEntity<Coborrower> {

	private static final long serialVersionUID = 2620223266064967838L;

	
	//借款人姓名
	private String loanCustomerName;
	
	//证件号码
	private String customerCertNum;
	
	//债权人
	private String creditor;


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

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	
}
