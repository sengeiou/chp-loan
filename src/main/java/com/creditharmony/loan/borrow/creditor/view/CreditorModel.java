package com.creditharmony.loan.borrow.creditor.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权录入管理
 * @Class Name CreditorService
 * @author WJJ
 * @Create In 2016年3月11日
 */

public class CreditorModel {
	private String id;//主键
	private String loanCode;//借款编号
	private String loanStatus;//借款人状态
	private String loanName;//借款人姓名
	private String loanPurpose;//借款用途
	private BigDecimal initMoney;//初始借款金额
	private BigDecimal manageMoney;//月账户管理费
	private String initLoanDateStr;
	private Date initLoanDate;//初始借款时间
	private int repaymentDate;//还款期限
	private String surplusDate;//剩余期限
	private int surplusDateInt = 0;
	private String startDateStr;
	private Date startDate;//起始还款日期
	private BigDecimal interestRate;//借款利率%
	private String endDateStr;
	private Date endDate;//还款截止日期
	private String type;//产品类型
	private String occupation;//借款人职业情况
	private String cerNum;//证件号码
	private String operationUser;//操作人
	private String operationDate;//操作时间
	private String status;//状态
	private String createBy;//创建人
	private String createTime;//创建时间
	private String modifyBy;//修改人
	private String modifyTime;//修改时间
	private String cerType;//证件类型
	private String creditor;//债权人
	private String repaymentType;//还款期限类型
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public BigDecimal getInitMoney() {
		return initMoney;
	}
	public void setInitMoney(BigDecimal initMoney) {
		this.initMoney = initMoney;
	}
	public BigDecimal getManageMoney() {
		return manageMoney;
	}
	public void setManageMoney(BigDecimal manageMoney) {
		this.manageMoney = manageMoney;
	}
	public Date getInitLoanDate() {
		return initLoanDate;
	}
	public void setInitLoanDate(Date initLoanDate) {
		this.initLoanDate = initLoanDate;
	}
	public int getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(int repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getSurplusDate() {
		return surplusDate;
	}
	public void setSurplusDate(String surplusDate) {
		this.surplusDate = surplusDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getInitLoanDateStr() {
		return initLoanDateStr;
	}
	public void setInitLoanDateStr(String initLoanDateStr) {
		this.initLoanDateStr = initLoanDateStr;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getCreditor() {
		return creditor;
	}
	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public int getSurplusDateInt() {
		return surplusDateInt;
	}
	public void setSurplusDateInt(int surplusDateInt) {
		this.surplusDateInt = surplusDateInt;
	}
	
}
