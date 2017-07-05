package com.creditharmony.loan.car.common.entity.ex;


import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;


public class CarLoanGrantHaveEx extends DataEntity<CarLoanGrantHaveEx> {
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1 )
	private String contractCode;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 2 )
	private String loanCustomerName;
	@ExcelField(title = "机构", type = 0, align = 2, sort = 3 )
	private String storeName;
	@ExcelField(title = "所属业务部", type = 0, align = 2, sort = 4 )
	private String bussinessOrgName;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 5 )
	private Double finalAuditAmount;
	@ExcelField(title = "原放款金额", type = 0, align = 2, sort = 6 )
	private Double grantAmount;
	@ExcelField(title = "团队名称", type = 0, align = 2, sort = 7 )
	private String teamOrgName;
	@ExcelField(title = "团队经理", type = 0, align = 2, sort = 8 )
	private String consTeamManagerName;
	@ExcelField(title = "员工编号", type = 0, align = 2, sort = 9 )
	private String consTeamManagercode;
	@ExcelField(title = "客户经理", type = 0, align = 2, sort = 10 )
	private String managerName;
	@ExcelField(title = "员工编号", type = 0, align = 2, sort = 11 )
	private String managerCode;
	@ExcelField(title = "费率", type = 0, align = 2, sort = 12 )
	private String grossRate;
	@ExcelField(title = "期数", type = 0, align = 2, sort = 13 )
	private Integer contractMonths;
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 14 )
	private Date lendingTime;
	@ExcelField(title = "首次还款日期", type = 0, align = 2, sort = 15 )
	private String contractReplayDay;
	@ExcelField(title = "合同签约日期", type = 0, align = 2, sort = 16 )
	private Date contractFactDay;
	@ExcelField(title = "合同截止日期", type = 0, align = 2, sort = 17 )
	private Date contractEndDay;
	@ExcelField(title = "车牌号", type = 0, align = 2, sort = 18 )
	private String plateNumbers;
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 19 )
	private String  productTypeContract;
	@ExcelField(title = "放款账号", type = 0, align = 2, sort = 20 )
	private String creditBankCardNo;
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 21 )
	private String creditMiDBankName;
	@ExcelField(title = "账号姓名", type = 0, align = 2, sort = 22 )
	private String middleName;
	@ExcelField(title = "操作人", type = 0, align = 2, sort = 23 )
	private String lendingUserName;
	@ExcelField(title = "渠道", type = 0, align = 2, sort = 24 )
	private String loanFlag;
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 25 )
	private String telesalesFlag;
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getContractReplayDay() {
		return contractReplayDay;
	}
	public void setContractReplayDay(String contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Double getFinalAuditAmount() {
		return finalAuditAmount;
	}
	public void setFinalAuditAmount(Double finalAuditAmount) {
		this.finalAuditAmount = finalAuditAmount;
	}

	public String getCreditBankCardNo() {
		return creditBankCardNo;
	}
	public void setCreditBankCardNo(String creditBankCardNo) {
		this.creditBankCardNo = creditBankCardNo;
	}
	public String getCreditMiDBankName() {
		return creditMiDBankName;
	}
	public void setCreditMiDBankName(String creditMiDBankName) {
		this.creditMiDBankName = creditMiDBankName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getLendingUserName() {
		return lendingUserName;
	}
	public void setLendingUserName(String lendingUserName) {
		this.lendingUserName = lendingUserName;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getTelesalesFlag() {
		return telesalesFlag;
	}
	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}
	public String getProductTypeContract() {
		return productTypeContract;
	}
	public void setProductTypeContract(String productTypeContract) {
		this.productTypeContract = productTypeContract;
	}
	public Integer getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getBussinessOrgName() {
		return bussinessOrgName;
	}
	public void setBussinessOrgName(String bussinessOrgName) {
		this.bussinessOrgName = bussinessOrgName;
	}
	public Double getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(Double grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getTeamOrgName() {
		return teamOrgName;
	}
	public void setTeamOrgName(String teamOrgName) {
		this.teamOrgName = teamOrgName;
	}
	public String getConsTeamManagerName() {
		return consTeamManagerName;
	}
	public void setConsTeamManagerName(String consTeamManagerName) {
		this.consTeamManagerName = consTeamManagerName;
	}
	public String getConsTeamManagercode() {
		return consTeamManagercode;
	}
	public void setConsTeamManagercode(String consTeamManagercode) {
		this.consTeamManagercode = consTeamManagercode;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(String grossRate) {
		this.grossRate = grossRate;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	
}