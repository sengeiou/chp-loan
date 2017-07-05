/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipConstant.java
 * @Create By 王浩
 * @Create In 2015年2月27日 下午2:05:30
 */
package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 金账户开户实体
 * @Class Name TrusteeshipAccount
 * @author 王浩
 * @Create In 2016年3月3日
 */
public class TrusteeshipAccount {
	@ExcelField(title = "序号", type = 0, align = 2, sort = 20)
	private String id;
	// 借款编码
	private String loanCode;
	// 流水号
	private String applyId;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 30)
	private String customerName;
	//客户编号
	private String customerCode;
	// 证件类型
	private String dictCertType;
	@ExcelField(title = "身份证号码", type = 0, align = 2, sort = 40)
	private String customerCertNum;
	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 50)
	private String customerPhoneFirst;
	@ExcelField(title = "邮箱地址", type = 0, align = 2, sort = 60)
	private String customerEmail;	
	// 银行卡所在城市省(Code)
	private String bankProvince;
	// 银行卡所在城市省(Name) 
	private String bankProvinceName;
	// 银行卡所在城市市(Code)	
	private String bankCity;
	// 银行卡所在城市市(Name)	
	private String bankCityName;
	// 金账户开户行省市
	@ExcelField(title = "开户行省市", type = 0, align = 2, sort = 70)
	private String bankJzhKhhss;
	// 金账户开户行区县
	@ExcelField(title = "开户行区县", type = 0, align = 2, sort = 80)
	private String bankJzhKhhqx;
	// 开户行(code)
	private String bankCode;
	@ExcelField(title = "开户行行别", type = 0, align = 2, sort = 90)
	private String bankName;
	@ExcelField(title = "开户行支行名称", type = 0, align = 2, sort = 100)
	private String bankBranch;
	@ExcelField(title = "户名", type = 0, align = 2, sort = 110)
	private String bankAccountName;
	@ExcelField(title = "帐号", type = 0, align = 2, sort = 120)
	private String bankAccount;
	@ExcelField(title = "密码", type = 0, align = 2, sort = 130)
	private String pass;
	@ExcelField(title = "备注", type = 0, align = 2, sort = 140)
	private String remark;
	// 签约平台
	private String bankSigningPlatform;	
			
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
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
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
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankProvinceName() {
		return bankProvinceName;
	}
	public void setBankProvinceName(String bankProvinceName) {
		this.bankProvinceName = bankProvinceName;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankCityName() {
		return bankCityName;
	}
	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
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
	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}
	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBankJzhKhhss() {
		return bankJzhKhhss;
	}
	public void setBankJzhKhhss(String bankJzhKhhss) {
		this.bankJzhKhhss = bankJzhKhhss;
	}
	public String getBankJzhKhhqx() {
		return bankJzhKhhqx;
	}
	public void setBankJzhKhhqx(String bankJzhKhhqx) {
		this.bankJzhKhhqx = bankJzhKhhqx;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

}
