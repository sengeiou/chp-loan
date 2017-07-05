package com.creditharmony.loan.car.carExtend.view;

import java.util.Date;



/**
 * 车借展期待办列表view
 * 
 * @Class Name CarExtendFlowQueryView
 * @author 陈伟东
 * @Create In 2016年3月1日
 */
public class CarExtendFlowQueryView{
	//原合同编号
	private String originalContractCode;
	//展期合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//门店id
	private String storeId;
	
	private String conditionalThroughFlag;
	//借款期限（申请）
	private String loanMonths;
	//产品类型编码（批复）
	private String auditBorrowProductCode;
	//产品类型名称（批复）
	private String auditBorrowProductName;
	private String loanFlag;
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}

	//产品类型名称（申请）
	private String borrowProductName;
	//产品类型编码（申请）
	private String borrowProductCode;
	//申请日期
	private Date loanApplyTime;
	//终审日期
	private Date finalCheckTime;
	//是否电销（code）
	private String loanIsPhone;
	//开始申请日期
	private Date loanApplyTimeStart;
	//结束申请日期
	private Date loanApplyTimeEnd;
	//开始申请日期
	private Date finalCheckTimeStart;
	//结束申请日期
	private Date finalCheckTimeEnd;
	//借款期限(天)-批复
	private String auditLoanMonths;
	//合同版本号
	private String contractVersion;
	//申请状态
	private String applyStatusCode;
	//合同到期日期
	private Date contractEndDay;
	// 身份证号
	private String certNum;
	//合同签约日期
	private Date contractFactDay;
	//合同 开始签约日期
	private Date contractFactDayStart;
	//合同签约日期
	private Date  contractFactDayEnd;
		
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(String auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
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
	public String getOriginalContractCode() {
		return originalContractCode;
	}
	public void setOriginalContractCode(String originalContractCode) {
		this.originalContractCode = originalContractCode;
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
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(String loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getBorrowProductName() {
		return borrowProductName;
	}
	public void setBorrowProductName(String borrowProductName) {
		this.borrowProductName = borrowProductName;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public Date getFinalCheckTime() {
		return finalCheckTime;
	}
	
	public void setFinalCheckTime(Date finalCheckTime) {
		this.finalCheckTime = finalCheckTime;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
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
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
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
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
