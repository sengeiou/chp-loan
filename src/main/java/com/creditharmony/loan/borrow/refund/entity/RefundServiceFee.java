
package com.creditharmony.loan.borrow.refund.entity;


import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款失败催收服务费退回
 * @Class Name RefundServiceFee
 * @author WJJ
 * @Create In 2016年4月25日
 */
public class RefundServiceFee extends DataEntity<RefundServiceFee> {
	// 放款id,
	private String id;
	// 催收服务费主表id
	private String urgeId;
	// 合同编号
	private String contractCode; 
	// 客户姓名
	private String  customerName;
	// 产品名称
	private String productName;
	// 证件号码
	private String customerCertNum;
	// 查账金额
	private float auditAmount;
	// 期数11
	private int  contractMonths;
	// 合同金额
	private float contractAmount;
	// 放款金额8
	private float grantAmount;
	// 催收金额
	private float urgeMoeny;
	// 已催收服务费金额
	private float returnAmount;
	// 标识
	private String loanFlag;
	// 放款日期
	private Date lendingTime;
	// 审核日期
	private Date checkTime;
	// 借款类型5，需要从字典表中取值
	private String classType;
	// 门店名称2
	private String name;

	private String dictDealType;

	private String   queryRight;
	private String appStatus;
	private String appStatusTmp;
	private String refundId;
	private String backTime;
	private String urgeDecuteDate;
	// 退款标识
	private String refundFlag;
	
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public String getUrgeId() {
		return urgeId;
	}
	public void setUrgeId(String urgeId) {
		this.urgeId = urgeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
		
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	
	public float getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(float contractAmount) {
		this.contractAmount = contractAmount;
	}
	public float getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(float grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public float getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(float urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	public float getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(float returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(float auditAmount) {
		this.auditAmount = auditAmount;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getAppStatusTmp() {
		return appStatusTmp;
	}
	public void setAppStatusTmp(String appStatusTmp) {
		this.appStatusTmp = appStatusTmp;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getBackTime() {
		return backTime;
	}
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
	public String getUrgeDecuteDate() {
		return urgeDecuteDate;
	}
	public void setUrgeDecuteDate(String urgeDecuteDate) {
		this.urgeDecuteDate = urgeDecuteDate;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	
}