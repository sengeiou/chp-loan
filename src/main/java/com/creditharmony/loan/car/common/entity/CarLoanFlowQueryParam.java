package com.creditharmony.loan.car.common.entity;

import java.util.Date;

/**
 *接收页面传递的查询参数 
 *@author 张振强
 */
public class CarLoanFlowQueryParam{
	
		// 客户姓名
		private String customerName;
		//门店名称
		private String storeName;
		//证件号码
		private String certNum;
		//合同编号
		private String contractCode;
		//开户行
		private String cardBank;
		//实放金额下限
		private Double grantAmountStart;
		//实放金额上限
		private Double grantAmountEnd;
		//户籍省
		private String customerRegisterProvince;
		//户籍市
		private String customerRegisterCity;
		//是否电销
		private String loanIsPhone;
		//借款状态
		private String dictStatus;
		//放款银行
		private String midBankName;
		 // 放款日期下限
	    private Date lendingTimeStart;
	    // 放款日期上限
	    private Date lendingTimeEnd;
		//放款途径
	    private String dictLoanWay;
	    //放款途径码
	    private String dictLoanWayCode;
	    // 通过标识
	    private String loanFlag;
		public String getDictLoanWayCode() {
			return dictLoanWayCode;
		}
		public void setDictLoanWayCode(String dictLoanWayCode) {
			this.dictLoanWayCode = dictLoanWayCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getStoreName() {
			return storeName;
		}
		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
		public String getCertNum() {
			return certNum;
		}
		public void setCertNum(String certNum) {
			this.certNum = certNum;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getCardBank() {
			return cardBank;
		}
		public void setCardBank(String cardBank) {
			this.cardBank = cardBank;
		}
		public Double getGrantAmountStart() {
			return grantAmountStart;
		}
		public void setGrantAmountStart(Double grantAmountStart) {
			this.grantAmountStart = grantAmountStart;
		}
		public Double getGrantAmountEnd() {
			return grantAmountEnd;
		}
		public void setGrantAmountEnd(Double grantAmountEnd) {
			this.grantAmountEnd = grantAmountEnd;
		}
		public String getCustomerRegisterProvince() {
			return customerRegisterProvince;
		}
		public void setCustomerRegisterProvince(String customerRegisterProvince) {
			this.customerRegisterProvince = customerRegisterProvince;
		}
		public String getCustomerRegisterCity() {
			return customerRegisterCity;
		}
		public void setCustomerRegisterCity(String customerRegisterCity) {
			this.customerRegisterCity = customerRegisterCity;
		}
		public String getLoanIsPhone() {
			return loanIsPhone;
		}
		public void setLoanIsPhone(String loanIsPhone) {
			this.loanIsPhone = loanIsPhone;
		}
		public String getDictStatus() {
			return dictStatus;
		}
		public void setDictStatus(String dictStatus) {
			this.dictStatus = dictStatus;
		}
		public String getMidBankName() {
			return midBankName;
		}
		public void setMidBankName(String midBankName) {
			this.midBankName = midBankName;
		}
		public Date getLendingTimeStart() {
			return lendingTimeStart;
		}
		public void setLendingTimeStart(Date lendingTimeStart) {
			this.lendingTimeStart = lendingTimeStart;
		}
		public Date getLendingTimeEnd() {
			return lendingTimeEnd;
		}
		public void setLendingTimeEnd(Date lendingTimeEnd) {
			this.lendingTimeEnd = lendingTimeEnd;
		}
		public String getDictLoanWay() {
			return dictLoanWay;
		}
		public void setDictLoanWay(String dictLoanWay) {
			this.dictLoanWay = dictLoanWay;
		}
		public String getLoanFlag() {
			return loanFlag;
		}
		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}
	    
  
}
