package com.creditharmony.loan.car.carApply.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;

/**
 * 待办列表view
 * @Class Name CarLoanFlowWorkItemView
 * @author 陈伟东
 * @Create In 2016年2月2日
 */
public class CarLoanFlowWorkItemView  extends BaseTaskItemView{
	//合同编号
	private String contractCode;
	//客户姓名,检索条件
	private String customerName;
	//申请金额
	private Double loanApplyAmount;
	//借款期限
	private Integer loanMonths;
	//产品类型,检索条件
	private String borrowProductName;
	//身份证号
	private String certNum;
	//评估金额
	private Double storeAssessAmount;
	//批借金额
	private Double auditAmount;
	//申请日期
	private Date loanApplyTime;
	//团队经理,检索条件
	private String loanTeamEmpCode;
	//客户经理,检索条件
	private String offendSalesName;
	//面审人员
	private String firstCheckName;
	//借款状态
	private String dictStatus;
	//合同到期提醒
	private Date contractExpirationDate;
	//车牌号码
	private String plateNumbers;
	//是否电销
	private String loanIsPhone;
	//标识
	private String borrowTrusteeFlag;
	//产品类型编码（申请）
	private String borrowProductCode;
	//团队经理
	private String loanTeamEmpName;
	//退回数据置顶标识
	private String backTop;
	//附条件标识
	private String conditionalThroughFlag;
	//门店名称
	private String storeName;
	//排序字段
	private String orderField;
	//批借借款期限
	private Integer auditLoanMonths;
	//批借产品类型编码
	private String auditBorrowProductCode;
	
	private String backReason;
	
	private Date timeOutPoint;
	//p2p标识
	private String loanFlag;
	
	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}
	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}
	public String getLoanTeamEmpName() {
		return loanTeamEmpName;
	}
	public void setLoanTeamEmpName(String loanTeamEmpName) {
		this.loanTeamEmpName = loanTeamEmpName;
	}
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public Double getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(Double loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
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
	public Double getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(Double storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}
	public Double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getLoanTeamEmpCode() {
		return loanTeamEmpCode;
	}
	public void setLoanTeamEmpCode(String loanTeamEmpCode) {
		this.loanTeamEmpCode = loanTeamEmpCode;
	}
	public String getOffendSalesName() {
		return offendSalesName;
	}
	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}

	public String getFirstCheckName() {
		return firstCheckName;
	}
	public void setFirstCheckName(String firstCheckName) {
		this.firstCheckName = firstCheckName;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
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
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getBackTop() {
		return backTop;
	}
	public void setBackTop(String backTop) {
		this.backTop = backTop;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	public Date getTimeOutPoint() {
		return timeOutPoint;
	}
	public void setTimeOutPoint(Date timeOutPoint) {
		this.timeOutPoint = timeOutPoint;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
