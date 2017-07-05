/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.secretvisit.entity.exSecretVisitEx.java
 * @Create By 王彬彬
 * @Create In 2015年12月26日 上午11:05:09
 */
package com.creditharmony.loan.borrow.secretvisit.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.common.entity.ProductInfo;

/**
 * 暗访信息
 * 
 * @Class Name SecretVisitEx
 * @author 王彬彬
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class SecretVisitEx extends DataEntity<SecretVisitEx> {

	// 合同编号
	private String contractCode;
	// 借款编号
	private String loanCode;
	// 客户姓名
	private String customerName;
	// 共借人
	private LoanCoborrower LoanCoborrower;
	// 门店(系统组织结构)
	private Org store;
	// 产品
	private ProductInfo productinfo;
	// 状态
	private String loanStatus;
	// 批复金额
	private BigDecimal auditAmount;
	// 批复分期
	private Integer contractMonths;
	// 加急标识
	private String loanUrgentFlag;
	// 是否电销
	private String consTelesalesFlag;
	// 销售人员
	private String loanManagerCode;
	// 销售人员姓名
	private String loanManagerName;
	// 团队经理
	private String loanTeamManagerCode;
	// 团队经理姓名
	private String loanTeamManagerName;
	// 进件时间
	private Date customerInfoTime;
	// 暗访状态
	private String visitFlag;
	// 暗访状态
	private String visitFlagLabel;
	// 标识
	private String loanFlag;
	// 借款所在机构ID
	private String loanStoreOrgid;

	
	public String getVisitFlagLabel() {
		return visitFlagLabel;
	}

	public void setVisitFlagLabel(String visitFlagLabel) {
		this.visitFlagLabel = visitFlagLabel;
	}

	public String getContractCode() {
		return contractCode;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public LoanCoborrower getLoanCoborrower() {
		return LoanCoborrower;
	}

	public Org getStore() {
		return store;
	}

	public ProductInfo getProductinfo() {
		return productinfo;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}

	public Integer getContractMonths() {
		return contractMonths;
	}

	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}

	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}

	public String getLoanManagerCode() {
		return loanManagerCode;
	}

	public String getLoanTeamManagerCode() {
		return loanTeamManagerCode;
	}

	public Date getCustomerInfoTime() {
		return customerInfoTime;
	}

	public String getVisitFlag() {
		return visitFlag;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public String getLoanStoreOrgid() {
		return loanStoreOrgid;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setLoanCoborrower(LoanCoborrower loanCoborrower) {
		LoanCoborrower = loanCoborrower;
	}

	public void setStore(Org store) {
		this.store = store;
	}

	public void setProductinfo(ProductInfo productinfo) {
		this.productinfo = productinfo;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}

	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}

	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}

	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}

	public void setLoanManagerCode(String loanManagerCode) {
		this.loanManagerCode = loanManagerCode;
	}

	public void setLoanTeamManagerCode(String loanTeamManagerCode) {
		this.loanTeamManagerCode = loanTeamManagerCode;
	}

	public void setCustomerInfoTime(Date customerInfoTime) {
		this.customerInfoTime = customerInfoTime;
	}

	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public void setLoanStoreOrgid(String loanStoreOrgid) {
		this.loanStoreOrgid = loanStoreOrgid;
	}

	public String getLoanManagerName() {
		return loanManagerName;
	}

	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}

	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}

	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}

}
