package com.creditharmony.loan.car.common.view;

import java.util.Date;

/**
 * 车借待办列表检索条件view
 * 
 * @Class Name CarLoanFlowQueryView
 * @author 陈伟东
 * @Create In 2016年2月16日
 */
public class CarLoanFlowQueryView{
	//合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//借款期限（申请）
	private Integer loanMonths;
	//借款期限（审批）、批借期限
	private String auditLoanMonths;
	//产品类型(申请)
	private String borrowProductName;
	//产品类型编码（申请）
	private String borrowProductCode;
	//批复产品类型编码
	private String auditBorrowProductCode;
	//身份证号
	private String certNum;
	//评估金额
	private Double storeAssessAmount;
	//是否电销
	private String loanIsPhone;
	//标识
	private String borrowTrusteeFlag;
	//标识（附条件）
	private String conditionalThroughFlag;
	//门店名称
	private String storeName;
	//门店id
	private String storeId;
	//终审日期
	private Date auditTime;
	//签约日期
	private Date contractFactDay;
	//开户行
	private String cardBank;
	//开户行名称(用于回显)
	private String cardBankName;
	//所属省
	private String addrProvince;
	//所属城市
	private String addrCity;
	//借款状态
	private String dictStatus;
	//放款银行
	private String midBankName;
	//放款开始日期
	private Date lendingTimeStart;
	//放款结束日期
	private Date lendingTimeEnd;
	//开始申请日期
	private Date loanApplyTimeStart;
	//结束申请日期
	private Date loanApplyTimeEnd;
	// 放款人员编号
	private String grantPersons;
	//终审开始日期
	private Date finalCheckTimeStart;
	//终审结束日期
	private Date finalCheckTimeEnd;
	//签约日期开始
	private Date contractFactDayStart;
	//签约日期结束
	private Date contractFactDayEnd;
	//是否展期
	private String extensionFlag;
	//P2P标识
	private String loanFlag;
	
	
	public String getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(String extensionFlag) {
		this.extensionFlag = extensionFlag;
	}
	public String getCardBankName() {
		return cardBankName;
	}
	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}
	public Date getContractFactDayStart() {
		return contractFactDayStart;
	}
	public void setContractFactDayStart(Date contractFactDayStart) {
		this.contractFactDayStart = contractFactDayStart;
	}
	public Date getContractFactDayEnd() {
		return contractFactDayEnd;
	}
	public void setContractFactDayEnd(Date contractFactDayEnd) {
		this.contractFactDayEnd = contractFactDayEnd;
	}
	public Date getFinalCheckTimeStart() {
		return finalCheckTimeStart;
	}
	public void setFinalCheckTimeStart(Date finalCheckTimeStart) {
		this.finalCheckTimeStart = finalCheckTimeStart;
	}
	public Date getFinalCheckTimeEnd() {
		return finalCheckTimeEnd;
	}
	public void setFinalCheckTimeEnd(Date finalCheckTimeEnd) {
		this.finalCheckTimeEnd = finalCheckTimeEnd;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Integer getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getBorrowProductName() {
		return borrowProductName;
	}
	public void setBorrowProductName(String borrowProductName) {
		this.borrowProductName = borrowProductName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public Double getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(Double storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}
	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
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
	public Date getLoanApplyTimeStart() {
		return loanApplyTimeStart;
	}
	public void setLoanApplyTimeStart(Date loanApplyTimeStart) {
		this.loanApplyTimeStart = loanApplyTimeStart;
	}
	public Date getLoanApplyTimeEnd() {
		return loanApplyTimeEnd;
	}
	public void setLoanApplyTimeEnd(Date loanApplyTimeEnd) {
		this.loanApplyTimeEnd = loanApplyTimeEnd;
	}
	public String getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(String auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getGrantPersons() {
		return grantPersons;
	}
	public void setGrantPersons(String grantPersons) {
		this.grantPersons = grantPersons;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
