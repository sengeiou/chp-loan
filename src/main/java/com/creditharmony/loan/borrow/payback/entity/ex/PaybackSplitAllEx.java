package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;

public class PaybackSplitAllEx {
	
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10, groups = 1)
	private String contractCode;
	// 客户名称
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20, groups = 1)
	private String customerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30, groups = 1)
	private String orgName;
	// 开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 40, groups = 1)
	private String applyBankName;
	// 批借期数 
	@ExcelField(title = "批借期数", type = 0, align = 2, sort = 50, groups = 1)
	private String contractMonths;
	// 首期还款日
	@ExcelField(title = "首期还款期", type = 0, align = 2, sort = 60, groups = 1)
	private Date contractReplayDay;
    // 实还金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 70, groups = 1)
	private BigDecimal splitAmounts;
	// 期供
	@ExcelField(title = "期供", type = 0, align = 2, sort = 80, groups = 1)
	private BigDecimal paybackMonthAmount;
	// 当期未还期供
	@ExcelField(title = "当期未还期供", type = 0, align = 2, sort = 90, groups = 1)
	private BigDecimal notPaybackMonthAmount;
	// 当期已还期供
	@ExcelField(title = "当期已还期供", type = 0, align = 2, sort = 100, groups = 1)
	private BigDecimal alsoPaybackMonthAmount;
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 110, groups = 1)
	private BigDecimal splitAmount;
	// 还款类型
	@ExcelField(title = "还款类型", type = 0, align = 2, sort = 120, groups = 1)
	private String huankType;
	// 还款状态
	@ExcelField(title = "还款状态", type = 0, align = 2, sort = 130, groups = 1, dictType="jk_repay_status")
	private String dictPayStatus;
	// 申请还款日期
	@ExcelField(title = "划扣日期", type = 0, align = 2, sort = 140, groups = 1)
	private Date splitBackDate;
    //还款日
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 150, groups = 1)
	private Date monthPayDay;
	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 160, groups = 1, dictType="jk_loan_status")
	private String dictLoanStatus;
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 170, groups = 1, dictType="jk_counteroffer_result")
	private String splitBackResult;
    // 划扣方式
	@ExcelField(title = "划扣方式", type = 0, align = 2, sort = 180, groups = 1, dictType="jk_deduct_time")
	private String batchFlag;
	// 失败原因
	@ExcelField(title = "失败原因", type = 0, align = 2, sort = 190, groups = 1)
	private String splitFailResult;		
	@ExcelField(title = "标识", type = 0, align = 2, sort = 200, groups = 1, dictType="jk_new_old_sys_flag")
	private String loanFlag;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}
	public BigDecimal getSplitAmounts() {
		return splitAmounts;
	}
	public void setSplitAmounts(BigDecimal splitAmounts) {
		this.splitAmounts = splitAmounts;
	}
	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}
	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}
	public BigDecimal getNotPaybackMonthAmount() {
		return notPaybackMonthAmount;
	}
	public void setNotPaybackMonthAmount(BigDecimal notPaybackMonthAmount) {
		this.notPaybackMonthAmount = notPaybackMonthAmount;
	}
	public BigDecimal getAlsoPaybackMonthAmount() {
		return alsoPaybackMonthAmount;
	}
	public void setAlsoPaybackMonthAmount(BigDecimal alsoPaybackMonthAmount) {
		this.alsoPaybackMonthAmount = alsoPaybackMonthAmount;
	}
	
	public BigDecimal getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}
	public String getHuankType() {
		return huankType;
	}
	public void setHuankType(String huankType) {
		this.huankType = huankType;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public Date getSplitBackDate() {
		return splitBackDate;
	}
	public void setSplitBackDate(Date splitBackDate) {
		this.splitBackDate = splitBackDate;
	}
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getSplitBackResult() {
		return splitBackResult;
	}
	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult;
	}
	public String getBatchFlag() {
		return batchFlag;
	}
	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}
	public String getSplitFailResult() {
		return splitFailResult;
	}
	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
