/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipConstant.java
 * @Create By 王浩
 * @Create In 2015年2月27日 下午2:05:30
 */
package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 协议库导出实体 
 * @Class Name TrusteeshipProctolExport
 * @author 王浩
 * @Create In 2016年3月7日
 */
public class TrusteeshipProtocolExport {
	@ExcelField(title = "业务类型", type = 0, align = 2, sort = 20)
	private String businessType;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 30)
	private String customerName;
	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 40)
	private String customerPhoneFirst;
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 50)
	private String dictCertType;
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 60)
	private String customerCertNum;
	@ExcelField(title = "帐号", type = 0, align = 2, sort = 70)
	private String bankAccount;
	@ExcelField(title = "账户属性", type = 0, align = 2, sort = 80)
	private String bankAccountType;
	@ExcelField(title = "帐号行别", type = 0, align = 2, sort = 90)
	private String bankNameStr;
	@ExcelField(title = "是否需要语音回拨", type = 0, align = 2, sort = 100)
	private String backCall;

	// @ExcelField(title = "备注", type = 0, align = 2, sort = 110)
	// private String remark;

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

	public String getBankNameStr() {
		return bankNameStr;
	}

	public void setBankNameStr(String bankNameStr) {
		this.bankNameStr = bankNameStr;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBackCall() {
		return backCall;
	}

	public void setBackCall(String backCall) {
		this.backCall = backCall;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

}
