package com.creditharmony.loan.car.creditorRights.view;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class CreditorLog  extends DataEntity<CreditorLog>{

	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//借款编码
	private String loanCode;
	//操作信息
	private String operMsg;
	//操作人
    private String operPeople;
    //操作时间
    private Date operTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getOperMsg() {
		return operMsg;
	}
	public void setOperMsg(String operMsg) {
		this.operMsg = operMsg;
	}
	public String getOperPeople() {
		return operPeople;
	}
	public void setOperPeople(String operPeople) {
		this.operPeople = operPeople;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
}
