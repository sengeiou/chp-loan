/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipConstant.java
 * @Create By 王浩
 * @Create In 2015年2月27日 下午2:05:30
 */
package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 上传回执结果
 * @Class Name KingAccountInfo
 * @author 王浩
 * @Create In 2016年3月3日
 */
public class KingImportAccountInfo {
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private String seqId;
	// 借款编码
	private String loanCode;
	// 流水号
	private String applyId;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	@ExcelField(title = "身份证号码", type = 0, align = 2, sort = 30)
	private String customerCertNum;
	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 40)
	private String customerPhoneFirst;
	@ExcelField(title = "邮箱地址", type = 0, align = 2, sort = 50)
	private String customerEmail;	
	@ExcelField(title = "开户行省市", type = 0, align = 2, sort = 60)
	private String bankProvinceCity;
	@ExcelField(title = "开户行区县", type = 0, align = 2, sort = 70)
	private String bankArea;
	@ExcelField(title = "开户行行别", type = 0, align = 2, sort = 80)
	private String bankName;
	@ExcelField(title = "开户行支行名称", type = 0, align = 2, sort = 90)
	private String bankBranch;
	@ExcelField(title = "户名", type = 0, align = 2, sort = 100)
	private String bankAccountName;
	@ExcelField(title = "帐号", type = 0, align = 2, sort = 110)
	private String bankAccount;
	@ExcelField(title = "初始密码", type = 0, align = 2, sort = 120)
	private String pass;
	@ExcelField(title = "备注", type = 0, align = 2, sort = 130)
	private String remark;
	@ExcelField(title = "返回码", type = 0, align = 2, sort = 140)
	private String returnCode;	
	@ExcelField(title = "返回描述", type = 0, align = 2, sort = 150)
	private String returnMsg;	
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
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
	
	public String getBankProvinceCity() {
		return bankProvinceCity;
	}

	public void setBankProvinceCity(String bankProvinceCity) {
		this.bankProvinceCity = bankProvinceCity;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
