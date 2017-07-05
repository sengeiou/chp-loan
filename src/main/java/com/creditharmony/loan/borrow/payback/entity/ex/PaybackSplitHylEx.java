package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 集中划扣已拆分导出列表（好易联）
 * @Class Name PaybackSplitFyEx
 * @author wengsi
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class PaybackSplitHylEx extends DataEntity<PaybackSplitHylEx>{
	
	    @ExcelField(title = "银联网络用户编号", type = 0, align = 2, sort = 10)
	    private String bankNetworkUserCode;
	    @ExcelField(title = "银行代码", type = 0, align = 2, sort = 20)
	    private String bankCode;
	    @ExcelField(title = "账号类型", type = 0, align = 2, sort = 30)
		private String accountType;	
		//扣款人银行账号
		@ExcelField(title = "账号", type = 0, align = 2, sort = 40)
		private String  bankAccount;
		//户名
		@ExcelField(title = "账户名", type = 0, align = 2, sort = 50)
		private String  bankAccountName;
		@ExcelField(title = "开户行所在省", type = 0, align = 2, sort = 60)
		private String  bankProvince;
		@ExcelField(title = "开户行所在市", type = 0, align = 2, sort = 70)
		private String  bankCity;
		// 开户行
		@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 80,dictType="jk_open_bank")
		private String bankName;
		//金额(单位:元)
		@ExcelField(title = "金额", type = 0, align = 2, sort = 90)
		private BigDecimal splitAmount;
		//货币类型
		@ExcelField(title = "货币类型", type = 0, align = 2, sort = 100)
		private String currencyType;
		//协议号
		@ExcelField(title = "协议号", type = 0, align = 2, sort = 110)
		private String protocolNumber;
		//协议用户编号
		@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 120)
		private String protocolNumberCode;
		//证件类型
		@ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 130,dictType="com_certificate_type")
		private String dictertType;
		@ExcelField(title = "证件号", type = 1, align = 2, sort = 140)
		private String customerCertNum;
		@ExcelField(title = "交易金额", type = 2, align = 2, sort = 140)
		private String transactionAmount;
		@ExcelField(title = "手机号", type = 0, align = 2, sort = 150)
		private String customerPhoneFirst;
		@ExcelField(title = "自定义用户名", type = 0, align = 2, sort = 160)
		private String customUserName;
		@ExcelField(title = "备注1", type = 0, align = 2, sort = 170)
		private String remarkOne;
		@ExcelField(title = "备注2", type = 0, align = 2, sort = 180)
		private String remarkTwo;
		@ExcelField(title = "备注", type = 0, align = 2, sort = 190)
		private String enterpriseSerialno;
		@ExcelField(title = "反馈码", type = 0, align = 2, sort = 200)
		private String feedbackCode;
		@ExcelField(title = "原因", type = 0, align = 2, sort = 210)
		private String reason;
		@ExcelField(title = "帐户类型", type = 2, align = 2, sort = 220)
		private String accountTypeNo;
		@ExcelField(title = "交易结果", type = 1, align = 2, sort = 220)
		private String tradingResults;
		//划扣平台
		private String dictDealType;
		
		private String counteroffer;
		
		private boolean success;
		
		private String returnPost;
		
		private String id;
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
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
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getDictertType() {
			return dictertType;
		}
		public void setDictertType(String dictertType) {
			this.dictertType = dictertType;
		}
		/*public String getCustomerCertNum() {
			return customerCertNum;
		}
		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}*/
		public String getDictDealType() {
			return dictDealType;
		}
		public void setDictDealType(String dictDealType) {
			this.dictDealType = dictDealType;
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
		public String getTradingResults() {
			return tradingResults;
		}
		public void setTradingResults(String tradingResults) {
			this.tradingResults = tradingResults;
		}
		public String getTransactionAmount() {
			return transactionAmount;
		}
		public void setTransactionAmount(String transactionAmount) {
			this.transactionAmount = transactionAmount;
		}
		public String getCustomerCertNum() {
			return customerCertNum;
		}
		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}
		public String getAccountTypeNo() {
			return accountTypeNo;
		}
		public void setAccountTypeNo(String accountTypeNo) {
			this.accountTypeNo = accountTypeNo;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCounteroffer() {
			return counteroffer;
		}
		public void setCounteroffer(String counteroffer) {
			this.counteroffer = counteroffer;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getReturnPost() {
			return returnPost;
		}
		public void setReturnPost(String returnPost) {
			this.returnPost = returnPost;
		}
		
}
