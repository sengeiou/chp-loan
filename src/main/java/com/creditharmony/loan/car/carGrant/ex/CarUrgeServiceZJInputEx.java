package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.car.common.util.CryptoUtils;

import java.util.Date;

/**
 * 放款当日待划扣列表中金平台导入
 * @Class Name UrgeServiceHylEx
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class CarUrgeServiceZJInputEx extends DataEntity<CarUrgeServiceZJInputEx> {

	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private String index;
	// 交易时间
	@ExcelField(title = "交易时间", type = 0, align = 2, sort = 20)
	private Date transactionTime;
	// 机构名称
	@ExcelField(title = "机构名称", type = 0, align = 2, sort = 30)
	private String mechanismName;
	// 批次号
	@ExcelField(title = "批次号", type = 0, align = 2, sort = 40)
	private String batchumber;
	// 明细号
	@ExcelField(title = "明细号", type = 0, align = 2, sort = 50)
	private String detailedumber;
	// 金额(单位:元)
	@ExcelField(title = "金额", type = 0, align = 2, sort = 60)
	private String splitAmount;
	// 银行ID
	@ExcelField(title = "银行Id", type = 0, align = 2, sort = 70)
	private String bankId;
    // 账户类型
    @ExcelField(title = "账户类型", type = 0, align = 2, sort = 80)
	private String accountType;	
	// 账户号码
	@ExcelField(title = "账户账号", type = 0, align = 2, sort = 90)
	private String  bankAccount;
	// 账户名称
	@ExcelField(title = "账户名称", type = 0, align = 2, sort = 100)
	private String  bankAccountName;
	// 分支行名称
	@ExcelField(title = "分支行名称", type = 0, align = 2, sort = 110)
	private String  branchBankName;
	// 分支行省份
	@ExcelField(title = "分支行省份", type = 0, align = 2, sort = 120)
	private String  branchBankProvince;
	// 分支行城市
	@ExcelField(title = "分支行城市", type = 0, align = 2, sort = 130)
	private String  branchBankCity;
	// 备注信息
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 140)
	private String enterpriseSerialno;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 150)
	private String protocolNumberCode;
	// 协议号
	@ExcelField(title = "协议号", type = 0, align = 2, sort = 160)
	private String protocolCode;
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 170)
	private String customerPhoneFirst;
	// 电子邮件
	@ExcelField(title = "电子邮件", type = 0, align = 2, sort = 180)
	private String email;
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 190)
	private String dictertType;
	// 证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 200)
	private String customerCertNum;
	// 银行代收的时间
	@ExcelField(title = "银行代收的时间", type = 0, align = 2, sort = 210)
	private Date bankCollectionTime;
	// 交易状态
	@ExcelField(title = "交易状态", type = 0, align = 2, sort = 220)
	private String  dictDealStatus;
	// 银行响应码
	@ExcelField(title = "银行响应码", type = 0, align = 2, sort = 230)
	private String  bankResponseCode;
	// 银行响应消息
	@ExcelField(title = "银行响应消息", type = 0, align = 2, sort = 230)
	private String  bankResponseInfo;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getMechanismName() {
		return mechanismName;
	}
	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}
	public String getBatchumber() {
		return batchumber;
	}
	public void setBatchumber(String batchumber) {
		this.batchumber = batchumber;
	}
	public String getDetailedumber() {
		return detailedumber;
	}
	public void setDetailedumber(String detailedumber) {
		this.detailedumber = detailedumber;
	}
	public String getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(String splitAmount) {
		this.splitAmount = splitAmount;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
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
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getBranchBankProvince() {
		return branchBankProvince;
	}
	public void setBranchBankProvince(String branchBankProvince) {
		this.branchBankProvince = branchBankProvince;
	}
	public String getBranchBankCity() {
		return branchBankCity;
	}
	public void setBranchBankCity(String branchBankCity) {
		this.branchBankCity = branchBankCity;
	}
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}
	public String getProtocolNumberCode() {
		return protocolNumberCode;
	}
	public void setProtocolNumberCode(String protocolNumberCode) {
		this.protocolNumberCode = protocolNumberCode;
	}
	public String getProtocolCode() {
		return protocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Date getBankCollectionTime() {
		return bankCollectionTime;
	}
	public void setBankCollectionTime(Date bankCollectionTime) {
		this.bankCollectionTime = bankCollectionTime;
	}
	public String getDictDealStatus() {
		return dictDealStatus;
	}
	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
	}
	public String getBankResponseCode() {
		return bankResponseCode;
	}
	public void setBankResponseCode(String bankResponseCode) {
		this.bankResponseCode = bankResponseCode;
	}
	public String getBankResponseInfo() {
		return bankResponseInfo;
	}
	public void setBankResponseInfo(String bankResponseInfo) {
		this.bankResponseInfo = bankResponseInfo;
	}
	
	
	

}