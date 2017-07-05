package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.loan.borrow.payback.entity.Decrypt;
/**
 *  导出中金模本
 * @Class Name PaybackSplitZjEx
 * @author wengsi
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class PaybackSplitZjEx extends Decrypt<PaybackSplitZjEx>{
	
	    @ExcelField(title = "明细流水号", type = 0, align = 2, sort = 10)
	    private String enterpriseSerialno;
	    @ExcelField(title = "金额(元)", type = 0, align = 2, sort = 20)
	    private String splitAmount;
	    @ExcelField(title = "银行名称", type = 0, align = 2, sort = 30,dictType="jk_open_bank")
		private String bankName;
	    @ExcelField(title = "账户类型", type = 0, align = 2, sort = 40)
	    private String AccountType;
		@ExcelField(title = "账户名称", type = 0, align = 2, sort = 50)
		private String  bankAccountName;
		@ExcelField(title = "账户号码", type = 0, align = 2, sort = 60)
		private String  bankAccount;
		@ExcelField(title = "分支行", type = 0, align = 2, sort = 70)
		private String  bankBranch;
		@ExcelField(title = "省份", type = 0, align = 2, sort = 80)
		private String  bankProvince;
		@ExcelField(title = "城市", type = 0, align = 2, sort = 90)
		private String bankCity;
		@ExcelField(title = "结算标识", type = 0, align = 2, sort = 100)
		private BigDecimal settlementIndicator;
		@ExcelField(title = "备注", type = 0, align = 2, sort = 110)
		private String remark;
		@ExcelField(title = "证件类型", type = 0, align = 2, sort = 120,dictType="com_certificate_type")
		private String dictertType;
		@ExcelField(title = "证件号码", type = 0, align = 2, sort = 130)
		private String customerCertNum;
		@ExcelField(title = "手机号", type = 0, align = 2, sort = 140)
		private String customerPhoneFirst;
		@ExcelField(title = "电子邮箱", type = 0, align = 2, sort = 150)
		private String mailbox;
		@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 150)
		private String protocolNumberCode;
		public String getEnterpriseSerialno() {
			return enterpriseSerialno;
		}
		public void setEnterpriseSerialno(String enterpriseSerialno) {
			this.enterpriseSerialno = enterpriseSerialno;
		}
		public String getSplitAmount() {
			return splitAmount;
		}
		public void setSplitAmount(String splitAmount) {
			this.splitAmount = splitAmount;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getAccountType() {
			return AccountType;
		}
		public void setAccountType(String accountType) {
			AccountType = accountType;
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
		public String getBankBranch() {
			return bankBranch;
		}
		public void setBankBranch(String bankBranch) {
			this.bankBranch = bankBranch;
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
		public BigDecimal getSettlementIndicator() {
			return settlementIndicator;
		}
		public void setSettlementIndicator(BigDecimal settlementIndicator) {
			this.settlementIndicator = settlementIndicator;
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
			String phoneFirst =  decrypt(customerPhoneFirst);
			this.customerPhoneFirst = phoneFirst;
		}
		public String getMailbox() {
			return mailbox;
		}
		public void setMailbox(String mailbox) {
			this.mailbox = mailbox;
		}
		public String getProtocolNumberCode() {
			return protocolNumberCode;
		}
		public void setProtocolNumberCode(String protocolNumberCode) {
			this.protocolNumberCode = protocolNumberCode;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		 
}
