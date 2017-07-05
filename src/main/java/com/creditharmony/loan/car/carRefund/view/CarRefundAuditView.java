package com.creditharmony.loan.car.carRefund.view;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 页面初始化，修改字段的信息
 * @Class Name CarRefundView
 * @author 蒋力
 * @Create In 2016年2月29日
 */
public class CarRefundAuditView extends BaseBusinessView{
	
	//ID
	private String id;
	//关联催收服务费信息表ID
	private String rChargeId;
	//退回金额
	private Double returnAmount;
	//退回状态
	private String returnStatus;
	//退回时间
	private Date returnTime;
	//退回中间人ID
	private String returnIntermediaryId;
	//合同编号
	private String contractCode;
	//审批状态
	private String auditStatus;
	
	private String storeName;
	private String loanCustomerName;
	private String productType;
	private String contractAmount;
	private BigDecimal urgeMoeny;
	private BigDecimal urgeDecuteMoeny;
	private String contractMonths;
	private String cardBank;
	private String splitFailResult;
	private String customerTelesalesFlag;
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(BigDecimal urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}
	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getSplitFailResult() {
		return splitFailResult;
	}
	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getrChargeId() {
		return rChargeId;
	}
	public void setrChargeId(String rChargeId) {
		this.rChargeId = rChargeId;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getReturnIntermediaryId() {
		return returnIntermediaryId;
	}
	public void setReturnIntermediaryId(String returnIntermediaryId) {
		this.returnIntermediaryId = returnIntermediaryId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	
}