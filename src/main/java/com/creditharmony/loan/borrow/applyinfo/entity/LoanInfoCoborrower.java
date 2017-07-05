package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 自然人保证人 借款意愿
 * @author 任志远		2016年9月7日
 */
public class LoanInfoCoborrower extends DataEntity<LoanInfoCoborrower>{

	private static final long serialVersionUID = -5616121635438331853L;

	/**
	 * 自然人保证人ID
	 */
	private String rid;
	/**
	 * 主借人借款编号
	 */
	private String loanCode;
	/**
	 * 产品类别
	 */
	private String productType;
	/**
	 * 申请额度
	 */
	private BigDecimal loanApplyAmount;
	/**
	 * 申请期限
	 */
	private Integer loanMonths;
	/**
	 * 主要借款用途
	 */
	private String borrowingPurposes;
	/**
	 * 其他借款用途
	 */
	private String borrowingPurposesRemark;
	/**
	 * 最高可承受月还
	 */
	private BigDecimal highPaybackMonthMoney;
	/**
	 * 主要还款来源
	 */
	private String mainPaybackResource;
	/**
	 * 其他还款来源
	 */
	private String mainPaybackResourceRemark;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}

	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public String getBorrowingPurposes() {
		return borrowingPurposes;
	}

	public void setBorrowingPurposes(String borrowingPurposes) {
		this.borrowingPurposes = borrowingPurposes;
	}

	public String getBorrowingPurposesRemark() {
		return borrowingPurposesRemark;
	}

	public void setBorrowingPurposesRemark(String borrowingPurposesRemark) {
		this.borrowingPurposesRemark = borrowingPurposesRemark;
	}

	public BigDecimal getHighPaybackMonthMoney() {
		return highPaybackMonthMoney;
	}

	public void setHighPaybackMonthMoney(BigDecimal highPaybackMonthMoney) {
		this.highPaybackMonthMoney = highPaybackMonthMoney;
	}

	public String getMainPaybackResource() {
		return mainPaybackResource;
	}

	public void setMainPaybackResource(String mainPaybackResource) {
		this.mainPaybackResource = mainPaybackResource;
	}

	public String getMainPaybackResourceRemark() {
		return mainPaybackResourceRemark;
	}

	public void setMainPaybackResourceRemark(String mainPaybackResourceRemark) {
		this.mainPaybackResourceRemark = mainPaybackResourceRemark;
	}

}
