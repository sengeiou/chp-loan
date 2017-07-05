package com.creditharmony.loan.channel.goldcredit.view;

import java.math.BigDecimal;

/**
 * 用于查询和和展示金信债权退回信息
 * 
 * @author 张建雄
 *
 */
public class CreditorBack {
	// 合同编号
	private String contractCode;
	// 门店名称
	private String storesName;
	// 客户姓名
	private String customerName;
	// 产品
	private String product;
	// 共借人
	private String coboName;
	// 身份证号
	private String customerNum;
	// 期限
	private Integer loanMonths;
	// 合同金额
	private BigDecimal contractAmount;
	// 放款金额
	private BigDecimal grantAmount;
	// 开户行
	private String bankName;
	// 支行名称
	private String branchName;
	// 银行账号
	private String bankAccount;

	// 借款状态
	private Integer loanStatus;
	// 加急标识
	private String urgentLogo;

	// 标识
	private String logo;

	// 是否电销
	private String isElectricityPin;
	// 系统来源
	private String fromSys;
	//是否追加借
	private String additionalBorrow;
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getUrgentLogo() {
		return urgentLogo;
	}

	public void setUrgentLogo(String urgentLogo) {
		this.urgentLogo = urgentLogo;
	}

	public String getChannel() {
		return logo;
	}

	public void setChannel(String logo) {
		this.logo = logo;
	}

	public String getIsElectricityPin() {
		return isElectricityPin;
	}

	public void setIsElectricityPin(String isElectricityPin) {
		this.isElectricityPin = isElectricityPin;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAdditionalBorrow() {
		return additionalBorrow;
	}

	public void setAdditionalBorrow(String additionalBorrow) {
		this.additionalBorrow = additionalBorrow;
	}

}
