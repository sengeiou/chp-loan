/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exTlImport2.java
 * @Create By 王彬彬
 * @Create In 2016年3月1日 下午1:14:14
 */
package com.creditharmony.loan.borrow.payback.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 通联导入字段
 * @Class Name TlImport2
 * @author 王彬彬
 * @Create In 2016年3月1日
 */
public class TlImportOther {
	// 序号*
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10, groups = { 2 })
	private String num;
	// 用户编号
	@ExcelField(title = "用户编号", type = 0, align = 2, sort = 20, groups = { 2 })
	private String userCode;
	// 银行代码*
	@ExcelField(title = "银行代码", type = 0, align = 2, sort = 30, groups = { 2 })
	private String bankCode;
	// 账号类型
	@ExcelField(title = "账号类型", type = 0, align = 2, sort = 40, groups = { 2 })
	private String accountNoType;
	// 账号*
	@ExcelField(title = "账号", type = 0, align = 2, sort = 50, groups = { 2 })
	private String accountNum;
	// 户名*
	@ExcelField(title = "户名", type = 0, align = 2, sort = 60, groups = { 2 })
	private String accountName;
	// 省
	@ExcelField(title = "省", type = 0, align = 2, sort = 70, groups = { 2 })
	private String bankProvince;
	// 市
	@ExcelField(title = "市", type = 0, align = 2, sort = 80, groups = { 2 })
	private String bankCity;
	// 开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 90, groups = { 2 })
	private String bankName;
	// 账户类型
	@ExcelField(title = "账户类型", type = 0, align = 2, sort = 100, groups = { 2 })
	private String accountType;
	// 金额*
	@ExcelField(title = "金额", type = 0, align = 2, sort = 110, groups = { 2 })
	private String amount;
	// 货币类型
	@ExcelField(title = "货币类型", type = 0, align = 2, sort = 120, groups = { 2 })
	private String currency;
	// 协议号
	@ExcelField(title = "协议号", type = 0, align = 2, sort = 130, groups = { 2 })
	private String protocolCode;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 140, groups = { 2 })
	private String protocolUserCode;
	// 开户证件类型
	@ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 150, groups = { 2 })
	private String bankCertType;
	// 证件号
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 160, groups = { 2 })
	private String certNum;
	// 手机号/小灵通
	@ExcelField(title = "手机号/小灵通", type = 0, align = 2, sort = 170, groups = { 2 })
	private String mobileNum;
	// 自定义用户号
	@ExcelField(title = "自定义用户号", type = 0, align = 2, sort = 180, groups = { 2 })
	private String customerNum;
	// 备注
	@ExcelField(title = "备注", type = 0, align = 2, sort = 190, groups = { 2 })
	private String remark;
	// 反馈码
	@ExcelField(title = "反馈码", type = 0, align = 2, sort = 200, groups = { 2 })
	private String returnCode;
	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 210, groups = { 2 })
	private String reason;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNoType() {
		return accountNoType;
	}

	public void setAccountNoType(String accountNoType) {
		this.accountNoType = accountNoType;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getProtocolUserCode() {
		return protocolUserCode;
	}

	public void setProtocolUserCode(String protocolUserCode) {
		this.protocolUserCode = protocolUserCode;
	}

	public String getBankCertType() {
		return bankCertType;
	}

	public void setBankCertType(String bankCertType) {
		this.bankCertType = bankCertType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
