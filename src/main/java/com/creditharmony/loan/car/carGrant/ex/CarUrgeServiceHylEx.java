package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.car.common.util.CryptoUtils;

/**
 * 放款当日待划扣列表好易联平台导出
 * @Class Name UrgeServiceHylEx
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class CarUrgeServiceHylEx extends DataEntity<CarUrgeServiceHylEx> {
	
	//groups 1代表好易联，2代表通联
	// 拆分表id
	private String id;
	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups ={1,2})
	private Integer index;
	// 银联网络用户编号
    @ExcelField(title = "银联网络用户编号", type = 0, align = 2, sort = 10, groups ={1,2})
    private String bankNetworkUserCode;
    // 银行代码
    @ExcelField(title = "银行代码", type = 0, align = 2, sort = 20, groups ={1,2})
    private String bankCode;
    // 账号类型
    @ExcelField(title = "账号类型", type = 0, align = 2, sort = 80, groups ={1,2})
	private String accountType;	
	// 扣款人银行账号
	@ExcelField(title = "账号", type = 0, align = 2, sort = 30, groups ={1,2})
	private String  bankAccount;
	// 户名
	@ExcelField(title = "账户名", type = 0, align = 2, sort = 40, groups ={1,2})
	private String  bankAccountName;
	// 开户行所在省,为门店名称
	@ExcelField(title = "开户行所在省", type = 0, align = 2, sort = 50, groups ={1,2})
	private String  bankProvince;
	// 开户行所在市
	@ExcelField(title = "开户行所在市", type = 0, align = 2, sort = 60, groups ={1,2})
	private String  bankCity;
	// 开户行
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 70, groups ={1,2})
	private String bankName;
	// 金额(单位:元)
	@ExcelField(title = "金额", type = 0, align = 2, sort = 90, groups ={1,2})
	private String splitAmount;
	// 货币类型
	@ExcelField(title = "货币类型", type = 0, align = 2, sort = 100, groups ={1,2})
	private String currencyType;
	// 协议号
	@ExcelField(title = "协议号", type = 0, align = 2, sort = 110, groups ={1,2})
	private String protocolNumber;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 120, groups ={1,2})
	private String protocolNumberCode;
	// 证件类型
	@ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 130, groups ={1,2})
	private String dictertType;
	// 证件号
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 140, groups ={1,2})
	private String customerCertNum;
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 150, groups ={1,2})
	private String customerPhoneFirst;
	// 自定义用户名
	@ExcelField(title = "自定义用户名", type = 0, align = 2, sort = 150, groups ={1,2})
	private String customUserName;
	
	@ExcelField(title = "备注1", type = 0, align = 2, sort = 150, groups ={1})
	private String remarkOne;
	
	@ExcelField(title = "备注2", type = 0, align = 2, sort = 150, groups ={1})
	private String remarkTwo;
	// 备注信息，为企业流水账号，唯一标识
	@ExcelField(title = "备注", type = 0, align = 2, sort = 150, groups ={1,2})
	private String enterpriseSerialno;
	
	@ExcelField(title = "反馈码", type = 0, align = 2, sort = 150, groups ={1,2})
	private String feedbackCode;
	
	@ExcelField(title = "原因", type = 0, align = 2, sort = 150, groups ={1,2})
	private String reason;
	// 合同编号
	private String contractCode;
	
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
		if(customerPhoneFirst != null){
			customerPhoneFirst = CryptoUtils.decryptPhones(customerPhoneFirst, "T_JK_LOAN_CUSTOMER", "customer_phone_first");
		}
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
	
	
	
}