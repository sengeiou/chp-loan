package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.persistence.DataEntity;


/**
 * 导出数据
 * @Class Name ExportTemplateEx 
 * @author 王彬彬
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class ExportTemplateEx extends DataEntity<ExportTemplateEx> {
	// 催收服务费主表id（关联拆分表外键rPaybackApplyId）
	private String rPaybackApplyId;
	//支行
	private String branchBank;
	//省份
	private String province;
	//城市
	private String city;
	//合同编号
	private String contractCode;
	// 序号
	private Integer index;
	// 银联网络用户编号
	private String bankNetworkUserCode;
	// 银行代码
	private String bankCode;
	// 账号类型
	private String accountType;
	// 扣款人银行账号
	private String bankAccount;
	// 户名
	private String bankAccountName;
	// 开户行所在省,为门店名称
	private String bankProvince;
	// 开户行所在市
	private String bankCity;
	// 开户行
	private String bankName;
	// 金额(单位:元)
	private String splitAmount;
	// 货币类型
	private String currencyType;
	// 协议号
	private String protocolNumber;
	// 协议用户编号
	private String protocolNumberCode;
	// 证件类型
	private String dictertType;
	// 证件号
	private String customerCertNum;
	// 手机号
	private String customerPhoneFirst;
	// 自定义用户名
	private String customUserName;
	// 备注1
	private String remarkOne;
	// 备注2
	private String remarkTwo;
	// 备注信息，为企业流水账号，唯一标识
	private String enterpriseSerialno;
	// 反馈码
	private String feedbackCode;
	// 原因
	private String reason;
	
	private String email;

	public String getrPaybackApplyId() {
		return rPaybackApplyId;
	}

	public void setrPaybackApplyId(String rPaybackApplyId) {
		this.rPaybackApplyId = rPaybackApplyId;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getBankNetworkUserCode() {
		return bankNetworkUserCode;
	}

	public void setBankNetworkUserCode(String bankNetworkUserCode) {
		this.bankNetworkUserCode = bankNetworkUserCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(String splitAmount) {
		this.splitAmount = splitAmount;
	}

	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public String getProtocolNumberCode() {
		return protocolNumberCode;
	}

	public void setProtocolNumberCode(String protocolNumberCode) {
		this.protocolNumberCode = protocolNumberCode;
	}

	public String getDictertType() {
		return dictertType;
	}

	public void setDictertType(String dictertType) {
		this.dictertType = dictertType;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}

	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}

	public String getCustomUserName() {
		return customUserName;
	}

	public void setCustomUserName(String customUserName) {
		this.customUserName = customUserName;
	}

	public String getRemarkOne() {
		return remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getRemarkTwo() {
		return remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public String getFeedbackCode() {
		return feedbackCode;
	}

	public void setFeedbackCode(String feedbackCode) {
		this.feedbackCode = feedbackCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}