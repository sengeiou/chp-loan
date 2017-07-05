package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表好易联平台导出
 * @Class Name UrgeServiceHylEx
 * @author 朱静越
 * @Create In 2016年1月6日
 */
@SuppressWarnings("serial")
public class UrgeServiceHylEx extends DataEntity<UrgeServiceHylEx> {
	// 拆分表id
	private String id;
	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1)
	private String index;
	// 银联网络用户编号
    @ExcelField(title = "银联网络用户编号", type = 0, align = 2, sort = 10)
    private String bankNetworkUserCode;
    // 银行代码
    @ExcelField(title = "银行代码", type = 0, align = 2, sort = 20)
    private String bankCode;
    // 账号类型
    @ExcelField(title = "账号类型", type = 0, align = 2, sort = 80)
	private String accountType;	
	// 扣款人银行账号
	@ExcelField(title = "账号", type = 0, align = 2, sort = 30)
	private String  bankAccount;
	// 户名
	@ExcelField(title = "账户名", type = 0, align = 2, sort = 40)
	private String  bankAccountName;
	// 开户行所在省,为门店名称
	@ExcelField(title = "开户行所在省", type = 0, align = 2, sort = 50)
	private String  bankProvince;
	// 开户行所在市
	@ExcelField(title = "开户行所在市", type = 0, align = 2, sort = 60)
	private String  bankCity;
	// 开户行
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 70,dictType = "jk_open_bank")
	private String bankName;
	// 金额(单位:元)
	@ExcelField(title = "金额", type = 0, align = 2, sort = 90)
	private BigDecimal splitAmount;
	// 货币类型
	@ExcelField(title = "货币类型", type = 0, align = 2, sort = 100)
	private String currencyType;
	// 协议号
	@ExcelField(title = "协议号", type = 0, align = 2, sort = 110)
	private String protocolNumber;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 120)
	private String protocolNumberCode;
	// 证件类型
	@ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 130,dictType = "jk_certificate_type")
	private String dictertType;
	// 证件号
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 140)
	private String customerCertNum;
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 150)
	private String customerPhoneFirst;
	// 自定义用户名
	@ExcelField(title = "自定义用户名", type = 0, align = 2, sort = 150)
	private String customUserName;
	
	@ExcelField(title = "备注1", type = 0, align = 2, sort = 150)
	private String remarkOne;
	
	@ExcelField(title = "备注2", type = 0, align = 2, sort = 150)
	private String remarkTwo;
	// 备注信息，为企业流水账号，唯一标识
	@ExcelField(title = "备注", type = 0, align = 2, sort = 150)
	private String enterpriseSerialno;
	
	@ExcelField(title = "反馈码", type = 0, align = 2, sort = 150)
	private String feedbackCode;
	
	@ExcelField(title = "原因", type = 0, align = 2, sort = 150)
	private String reason;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
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
	public BigDecimal getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(BigDecimal splitAmount) {
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
	
}