package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.car.common.util.CryptoUtils;

/**
 * 放款当日待划扣列表中金平台导出
 * @Class Name UrgeServiceHylEx
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class CarUrgeServiceZJEx extends DataEntity<CarUrgeServiceZJEx> {

	// 拆分表id
	private String id;
	// 明细流水号
	@ExcelField(title = "明细流水号", type = 0, align = 2, sort = 1)
	private Integer index;
	// 金额(单位:元)
	@ExcelField(title = "金额", type = 0, align = 2, sort = 20)
	private String splitAmount;
	// 银行名称
	@ExcelField(title = "银行名称", type = 0, align = 2, sort = 30,dictType = "jk_open_bank")
	private String bankName;
    // 账户类型
    @ExcelField(title = "账户类型", type = 0, align = 2, sort = 40)
	private String accountType;	
	// 账户名称
	@ExcelField(title = "账户名称", type = 0, align = 2, sort = 50)
	private String  bankAccountName;
	// 账户号码
	@ExcelField(title = "账户账号", type = 0, align = 2, sort = 60)
	private String  bankAccount;
	// 分支行
	@ExcelField(title = "分支行", type = 0, align = 2, sort = 70)
	private String applyBankName;
	// 省份
	@ExcelField(title = "省份", type = 0, align = 2, sort = 80)
	private String  bankProvince;
	// 城市
	@ExcelField(title = "城市", type = 0, align = 2, sort = 90)
	private String  bankCity;
	//结算标识
	@ExcelField(title = "结算标识", type = 0, align = 2, sort = 100)
	private String settlementFlag;
	// 备注
	@ExcelField(title = "备注", type = 0, align = 2, sort = 110)
	private String enterpriseSerialno;
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 120)
	private String dictertType;
	// 证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 130)
	private String customerCertNum;
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 140)
	private String customerPhoneFirst;
	// 电子邮箱
	@ExcelField(title = "电子邮箱", type = 0, align = 2, sort = 150)
	private String email;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 160)
	private String protocolNumberCode;

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
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getSettlementFlag() {
		return settlementFlag;
	}
	public void setSettlementFlag(String settlementFlag) {
		this.settlementFlag = settlementFlag;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
}