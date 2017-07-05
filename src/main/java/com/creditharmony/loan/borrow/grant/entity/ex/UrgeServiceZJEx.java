package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表通联平台导出
 * @Class Name UrgeServiceTlEx
 * @author
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class UrgeServiceZJEx extends DataEntity<UrgeServiceZJEx> {
	//明细流水号
	@ExcelField(title = "明细流水号", type = 0, align = 2, sort = 1)
	private String index;
	//金额(元)
	@ExcelField(title = "金额(元)", type = 0, align = 2, sort = 2)
	private BigDecimal splitAmount;
	//银行名称
	@ExcelField(title = "银行名称", type = 0, align = 2, sort = 3,dictType = "jk_open_bank")
	private String bankName;
    // 账号类型
    @ExcelField(title = "账号类型", type = 0, align = 2, sort = 4)
	private String accountType;	
	//账户姓名
	@ExcelField(title = "账户姓名", type = 0, align = 2, sort = 5)
	private String  bankAccountName;
	//账户号码
	@ExcelField(title = "账户号码", type = 0, align = 2, sort = 6)
	private String  bankAccount;
	//分支行
	@ExcelField(title = "分支行", type = 0, align = 2, sort = 7)
	private String  branch;
	//省份
	@ExcelField(title = "省份", type = 0, align = 2, sort = 8)
	private String  bankProvince;
	//城市
	@ExcelField(title = "城市", type = 0, align = 2, sort = 9)
	private String  bankCity;
	//结算标识
	@ExcelField(title = "结算标识", type = 0, align = 2, sort = 10)
	private String  clearingFlag;
	// 备注信息，为企业流水账号，唯一标识
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String enterpriseSerialno;
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 12,dictType = "jk_certificate_type")
	private String dictertType = "身份证";
	//证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 13)
	private String customerCertNum;
	//手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 14)
	private String customerPhoneFirst;
	//电子邮箱
	@ExcelField(title = "电子邮箱", type = 0, align = 2, sort = 15)
	private String email;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 16)
	private String protocolNumberCode;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public BigDecimal getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public String getClearingFlag() {
		return clearingFlag;
	}
	public void setClearingFlag(String clearingFlag) {
		this.clearingFlag = clearingFlag;
	}
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProtocolNumberCode() {
		return protocolNumberCode;
	}
	public void setProtocolNumberCode(String protocolNumberCode) {
		this.protocolNumberCode = protocolNumberCode;
	}
	 
}