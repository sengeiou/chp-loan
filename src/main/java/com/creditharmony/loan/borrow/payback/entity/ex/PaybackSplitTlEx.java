package com.creditharmony.loan.borrow.payback.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.loan.borrow.payback.entity.Decrypt;
/**
 *  导出中金模本
 * @Class Name PaybackSplitZjEx
 * @author wengsi
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class PaybackSplitTlEx extends Decrypt<PaybackSplitTlEx>{
	
	    @ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	    private String serialNumber;
	    @ExcelField(title = "用户编号", type = 0, align = 2, sort = 20)
	    private String userCode;
	    @ExcelField(title = "银行代码", type = 0, align = 2, sort = 30)
	    private String backCode;
	    @ExcelField(title = "账号类型", type = 0, align = 2, sort = 40)
	    private String AccountType;
	    @ExcelField(title = "账户", type = 0, align = 2, sort = 50)
		private String  bankAccount;
		@ExcelField(title = "户名", type = 0, align = 2, sort = 60)
		private String  bankAccountName;
		@ExcelField(title = "省", type = 0, align = 2, sort = 70)
		private String  bankProvince;
		@ExcelField(title = "市", type = 0, align = 2, sort = 80)
		private String bankCity;
	    @ExcelField(title = "开户行名称", type = 0, align = 2, sort = 90,dictType="jk_open_bank")
		private String bankName;
	    @ExcelField(title = "账户类型", type = 0, align = 2, sort = 100,dictType="jk_open_bank")
	    private String AccountType1;
	    @ExcelField(title = "金额", type = 0, align = 2, sort = 110)
	    private String splitAmount;
	    @ExcelField(title = "货币类型", type = 0, align = 2, sort = 120)
	    private String currency;
	    @ExcelField(title = "协议号", type = 0, align = 2, sort = 130)
	    private String protocolNo;
	    @ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 140)
	    private String protocolNoUserCode;
	    @ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 150,dictType="com_certificate_type")
		private String dictertType;
		@ExcelField(title = "证件号码", type = 1, align = 2, sort = 160)
		private String customerCertNum;
		@ExcelField(title = "手机号/小灵通", type = 0, align = 2, sort = 170)
		private String customerPhoneFirst;
		@ExcelField(title = "自定义用户号", type = 0, align = 2, sort = 180)
		private String enterpriseSerialno;
		@ExcelField(title = "备注", type = 0, align = 2, sort = 190)
		private String remark;
		@ExcelField(title = "反馈码", type = 0, align = 2, sort = 200)
		private String feedbackCode;
		@ExcelField(title = "原因", type = 0, align = 2, sort = 210)
		private String reason;
		public String getEnterpriseSerialno() {
			return enterpriseSerialno;
		}
		public void setEnterpriseSerialno(String enterpriseSerialno) {
			this.enterpriseSerialno = enterpriseSerialno;
		}
		public String getUserCode() {
			return userCode;
		}
		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}
		public String getBackCode() {
			return backCode;
		}
		public void setBackCode(String backCode) {
			this.backCode = backCode;
		}
		public String getAccountType() {
			return AccountType;
		}
		public void setAccountType(String accountType) {
			AccountType = accountType;
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
		public String getAccountType1() {
			return AccountType1;
		}
		public void setAccountType1(String accountType1) {
			AccountType1 = accountType1;
		}
		public String getSplitAmount() {
			return splitAmount;
		}
		public void setSplitAmount(String splitAmount) {
			this.splitAmount = splitAmount;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getProtocolNo() {
			return protocolNo;
		}
		public void setProtocolNo(String protocolNo) {
			this.protocolNo = protocolNo;
		}
		public String getProtocolNoUserCode() {
			return protocolNoUserCode;
		}
		public void setProtocolNoUserCode(String protocolNoUserCode) {
			this.protocolNoUserCode = protocolNoUserCode;
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
			return decrypt(customerPhoneFirst);
		}
		public void setCustomerPhoneFirst(String customerPhoneFirst) {
			this.customerPhoneFirst = customerPhoneFirst;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
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
		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
		 
}
