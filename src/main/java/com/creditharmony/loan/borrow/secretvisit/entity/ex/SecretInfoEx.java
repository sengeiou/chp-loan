package com.creditharmony.loan.borrow.secretvisit.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 暗访信息
 * @Class Name SecretInfoEx
 * @author lirui
 * @Create In 2015年12月30日
 */
@SuppressWarnings("serial")
public class SecretInfoEx extends DataEntity<SecretInfoEx> {
	// 借款编号
	private String loanCode;
	// 流程id
	private String applyId;
	// 门店名称 
	private String orgName;
	// 借款信息表主键
	private String loanId;
	// 合同编号 
	private String contractCode;
	// 客户姓名
	private String customerName;
	// 共借人
	private String coboName;
	// 借款产品
	private String productName;
	// 借款金额
	private String auditAmount;
	// 借款期限
	private String contractMonths;
	// 期数
	private String months;
	// 月还款金额
	private BigDecimal monthRepayAmount;
	// 还款日
	private Date monthPayDay;
	// 实际还款日
	private Date monthPayActualday;
	// 是否逾期
	private String isOverdue;
	// 访问标识
	private String visitFlag;
	// 影像地址
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getVisitFlag() {
		return visitFlag;
	}
	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public BigDecimal getMonthRepayAmount() {
		return monthRepayAmount;
	}
	public void setMonthRepayAmount(BigDecimal monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public Date getMonthPayActualday() {
		return monthPayActualday;
	}
	public void setMonthPayActualday(Date monthPayActualday) {
		this.monthPayActualday = monthPayActualday;
	}
	public String getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
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
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(String auditAmount) {
		this.auditAmount = auditAmount;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	
}
