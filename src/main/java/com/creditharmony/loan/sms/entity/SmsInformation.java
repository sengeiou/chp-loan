package com.creditharmony.loan.sms.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class SmsInformation extends DataEntity<SmsInformation>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8173124143405746927L;
	private String id;    //   ID
	private String periodId; //  期供ID
	private String customerCode;  //      客户编号
	private String customerName;   //       客户姓名
	private String sex;  //                性别
	private String phone;   //          手机号
	private BigDecimal loanAmount;    //           放款金额
	private BigDecimal monthRepayAmount; //          月还金额
	private BigDecimal contractAmount;   //                     合同金额
	private Date repayDay;   //           还款日
	private String contractCode;//          合同编号
	private String storesName;   //    门店名称
	private String sendStatus;    //     发送状态
	private String loanStatus;  //           借款状态
	private String smsTemplateId;
	private String smsTemplateContent;
	private String onlyFlag;
	private String dictBankId; //             银行id
	private String bankName;   //             银行名称
	private String accountNum;
	public String getId() {
		return id;
	}
	public String getPeriodId() {
		return periodId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getSex() {
		return sex;
	}
	public String getPhone() {
		return phone;
	}
	
	public Date getRepayDay() {
		return repayDay;
	}
	public String getContractCode() {
		return contractCode;
	}
	public String getStoresName() {
		return storesName;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public String getSmsTemplateId() {
		return smsTemplateId;
	}
	public String getSmsTemplateContent() {
		return smsTemplateContent;
	}
	public String getOnlyFlag() {
		return onlyFlag;
	}
	public String getDictBankId() {
		return dictBankId;
	}
	public String getBankName() {
		return bankName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}
	public void setSmsTemplateContent(String smsTemplateContent) {
		this.smsTemplateContent = smsTemplateContent;
	}
	public void setOnlyFlag(String onlyFlag) {
		this.onlyFlag = onlyFlag;
	}
	public void setDictBankId(String dictBankId) {
		this.dictBankId = dictBankId;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public BigDecimal getMonthRepayAmount() {
		return monthRepayAmount;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public void setMonthRepayAmount(BigDecimal monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	
}
