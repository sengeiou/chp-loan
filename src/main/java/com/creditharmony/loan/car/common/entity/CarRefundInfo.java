package com.creditharmony.loan.car.common.entity;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 催收服务费退回表信息
 * @Class Name CarRefundView
 * @author 蒋力
 * @Create In 2016年2月29日
 */
public class CarRefundInfo extends DataEntity<CarRefundInfo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6836232809549447007L;
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
	private String auditRefuseReason;//审核拒绝原因
	
	private String storeName;
	private String loanCustomerName;
	private String productType;
	private String contractAmount;
	private BigDecimal urgeMoeny;
	private BigDecimal urgeDecuteMoeny;
	private String contractMonths;
	private String cardBank;
	private String dictLoanStatus;
	private String customerTelesalesFlag;
	private String applyId;
	private String loanCode;//借款编码
	private String bankCardNo;//客户卡号
	
	private String staticValue;
	
	public String getStaticValue() {
		return staticValue;
	}
	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	public String getAuditRefuseReason() {
		return auditRefuseReason;
	}
	public void setAuditRefuseReason(String auditRefuseReason) {
		this.auditRefuseReason = auditRefuseReason;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
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
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public Double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
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
	
	
}