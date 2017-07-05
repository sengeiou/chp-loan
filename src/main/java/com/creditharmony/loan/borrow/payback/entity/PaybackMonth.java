/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.PaybackMonth.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;

/**
 * 期供表
 * 
 * @Class Name PayBackMonth
 * @author zhangfeng
 * @Create In 2015年12月4日
 */
@SuppressWarnings("serial")
public class PaybackMonth extends DataEntity<PaybackMonth> {
	//冲抵期供查询静态变量
	public static final String REPAYMENT_FLAG = "2";
	
	public static final String OVERDUE_FLAG = "3";
	// 合同编号
	private String contractCode;
	// 期数
	private int months;
	// 最大期数
	private int maxMonths;
	// 还款日
	private Date monthPayDay;
	// 实际还款日
	private Date monthPayActualday;
	// 应还本金
	private BigDecimal monthPayAmount;
	// 应还利息
	private BigDecimal monthInterestBackshould;
	// 实还本金
	private BigDecimal monthCapitalPayactual;
	// 实还利息
	private BigDecimal monthInterestPayactual;
	// 是否逾期
	private String isOverdue;
	// 一次性应还总额
	private BigDecimal monthBeforeFinishAmount;
	// 提前结清减免金额
	private BigDecimal monthBeforeReductionAmount;
	// 应还罚息
	private BigDecimal monthInterestPunishshould;
	// 实还罚息
	private BigDecimal monthInterestPunishactual;
	// 减免罚息
	private BigDecimal monthPunishReduction;
	// 应还违约金
	private BigDecimal monthPenaltyShould;
	// 实还违约金
	private BigDecimal monthPenaltyActual;
	// 减免违约金
	private BigDecimal monthPenaltyReduction;
	// 滞纳金
	private BigDecimal monthLateFee;
	// 实还滞纳金
	private BigDecimal actualMonthLateFee;
	// 减免滞纳金
	private BigDecimal monthLateFeeReduction;
	// 当前逾期天数
	private int monthOverdueDays;
	// 减免天数
	private int monthReductionDays;
	// 期供状态
	private String dictMonthStatus;
	// 期供状态
	private String dictMonthStatusLabel;
	// 逾期原因
	private String monthOverdueMes;
	// 分期咨询费  
	private BigDecimal monthFeeConsult;
	// 分期居间服务费 
	private BigDecimal monthMidFeeService;
	// 分期服务费   
	private BigDecimal monthFeeService;
	// 应还总额 
	private BigDecimal monthPayTotal;
	// 还款后剩余本金 
	private BigDecimal monthResiduePayactual;
	// 提醒标识
	private BigDecimal remindLogo;
	// 合同表
	private Contract contract;
	//借款信息表
	private LoanInfo loanInfo;
	
	// 最大逾期天数(max)
	private String monthOverdueDaysMax;
	// 分期服务费总额
	private BigDecimal monthFeeServiceSum;
	// 应还本金总额
	private BigDecimal monthPayAmountSum;
	// 应还利息总额
	private BigDecimal monthInterestBackShouldSum;
	// 实还本金总额(sum)
	private BigDecimal monthCapitalPayactualSum;
	// 实还利息总额(sum)
	private BigDecimal monthInterestPayactualSum;
	// 应还罚息总额(sum)
	private BigDecimal monthInterestPunishshouldSum;
	// 实还罚息总额(sum)
	private BigDecimal monthInterestPunishactualSum;
	// 应还违约金总额(sum)
	private BigDecimal monthPenaltyShouldSum;
	// 实还违约金总额(sum)
	private BigDecimal monthPenaltyActualSum;
	// 应还滞纳金总额(sum) 
	private BigDecimal  monthLateFeeSum;
	// 实还滞纳金总额(sum)  
	private BigDecimal  actualMonthLateFeeSum;
	// 减免滞纳金总额(sum) 
	private BigDecimal monthLateFeeReductionSum;
	// 已还期供金额
	private BigDecimal monthsAomuntPaybacked;
	// 未还违约金(滞纳金)及罚息金额
	private BigDecimal overDueAmontNoPaybacked;
	// 已还违约金及罚息总额
	private BigDecimal overDueAmontPaybackedSum;
	// 逾期未还期供总金额(sum)
	private BigDecimal monthAmountSum;
	// 未还违约金(滞纳金)总金额(sum)
	private BigDecimal monthPenaltySum;
	// 未还罚息总金额(sum)
	private BigDecimal monthInterestPunishSum;
	// 未还违约金(滞纳金)及罚息总额
	private BigDecimal penaltyInterest;
	// 减免违约金(sum)
	private BigDecimal monthPenaltyReductionSum;
	// 减免罚息 (sum)
	private BigDecimal monthPunishReductionSum;
	// 减免金额
	private BigDecimal creditAmount;
	// 减免人
	private String reductionBy;
    // 提前结清应还款总额
	private BigDecimal shoudBackAmount;
    // 应还款总额
	private BigDecimal moneyAmount;
	// 实还总额
	private BigDecimal actualAmount;
	// 实还分期服务费
	private BigDecimal actualMonthFeeService; 
	// 实还分期服务费总额
	private BigDecimal actualMonthFeeServiceSum;
	// 1.4期供总额和
	private BigDecimal monthPayTotalSum;
	// 未还违约金(滞纳金)
	private BigDecimal penaltyLateNoPaybacked;
	// 未还罚息
	private BigDecimal interestNoPaybacked;

			
	public String getDictMonthStatusLabel() {
		return dictMonthStatusLabel;
	}

	public BigDecimal getActualMonthFeeService() {
		return actualMonthFeeService;
	}

	public void setActualMonthFeeService(BigDecimal actualMonthFeeService) {
		this.actualMonthFeeService = actualMonthFeeService;
	}

	public void setDictMonthStatusLabel(String dictMonthStatusLabel) {
		this.dictMonthStatusLabel = dictMonthStatusLabel;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getMaxMonths() {
		return maxMonths;
	}

	public void setMaxMonths(int maxMonths) {
		this.maxMonths = maxMonths;
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

	public BigDecimal getMonthPayAmount() {
		return monthPayAmount;
	}

	public void setMonthPayAmount(BigDecimal monthPayAmount) {
		this.monthPayAmount = monthPayAmount;
	}

	public BigDecimal getMonthInterestBackshould() {
		return monthInterestBackshould;
	}

	public void setMonthInterestBackshould(BigDecimal monthInterestBackshould) {
		this.monthInterestBackshould = monthInterestBackshould;
	}

	public BigDecimal getMonthCapitalPayactual() {
		return monthCapitalPayactual;
	}

	public void setMonthCapitalPayactual(BigDecimal monthCapitalPayactual) {
		this.monthCapitalPayactual = monthCapitalPayactual;
	}

	public BigDecimal getMonthInterestPayactual() {
		return monthInterestPayactual;
	}

	public void setMonthInterestPayactual(BigDecimal monthInterestPayactual) {
		this.monthInterestPayactual = monthInterestPayactual;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public BigDecimal getMonthBeforeFinishAmount() {
		return monthBeforeFinishAmount;
	}

	public void setMonthBeforeFinishAmount(BigDecimal monthBeforeFinishAmount) {
		this.monthBeforeFinishAmount = monthBeforeFinishAmount;
	}

	public BigDecimal getMonthBeforeReductionAmount() {
		return monthBeforeReductionAmount;
	}

	public void setMonthBeforeReductionAmount(
			BigDecimal monthBeforeReductionAmount) {
		this.monthBeforeReductionAmount = monthBeforeReductionAmount;
	}

	public BigDecimal getMonthInterestPunishshould() {
		return monthInterestPunishshould;
	}

	public void setMonthInterestPunishshould(
			BigDecimal monthInterestPunishshould) {
		this.monthInterestPunishshould = monthInterestPunishshould;
	}

	public BigDecimal getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}

	public void setMonthInterestPunishactual(
			BigDecimal monthInterestPunishactual) {
		this.monthInterestPunishactual = monthInterestPunishactual;
	}

	public BigDecimal getMonthPunishReduction() {
		return monthPunishReduction;
	}

	public void setMonthPunishReduction(BigDecimal monthPunishReduction) {
		this.monthPunishReduction = monthPunishReduction;
	}

	public BigDecimal getMonthPenaltyShould() {
		return monthPenaltyShould;
	}

	public void setMonthPenaltyShould(BigDecimal monthPenaltyShould) {
		this.monthPenaltyShould = monthPenaltyShould;
	}

	public BigDecimal getMonthPenaltyActual() {
		return monthPenaltyActual;
	}

	public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}

	public BigDecimal getMonthPenaltyReduction() {
		return monthPenaltyReduction;
	}

	public void setMonthPenaltyReduction(BigDecimal monthPenaltyReduction) {
		this.monthPenaltyReduction = monthPenaltyReduction;
	}

	public int getMonthOverdueDays() {
		return monthOverdueDays;
	}

	public void setMonthOverdueDays(int monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
	}

	public int getMonthReductionDays() {
		return monthReductionDays;
	}

	public void setMonthReductionDays(int monthReductionDays) {
		this.monthReductionDays = monthReductionDays;
	}

	public String getDictMonthStatus() {
		return dictMonthStatus;
	}

	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}

	public String getMonthOverdueMes() {
		return monthOverdueMes;
	}

	public void setMonthOverdueMes(String monthOverdueMes) {
		this.monthOverdueMes = monthOverdueMes;
	}

	public BigDecimal getMonthCapitalPayactualSum() {
		return monthCapitalPayactualSum;
	}

	public void setMonthCapitalPayactualSum(BigDecimal monthCapitalPayactualSum) {
		this.monthCapitalPayactualSum = monthCapitalPayactualSum;
	}
	
	public String getMonthOverdueDaysMax() {
		return monthOverdueDaysMax;
	}

	public void setMonthOverdueDaysMax(String monthOverdueDaysMax) {
		this.monthOverdueDaysMax = monthOverdueDaysMax;
	}

	public BigDecimal getMonthPayAmountSum() {
		return monthPayAmountSum;
	}

	public void setMonthPayAmountSum(BigDecimal monthPayAmountSum) {
		this.monthPayAmountSum = monthPayAmountSum;
	}

	public BigDecimal getMonthInterestBackShouldSum() {
		return monthInterestBackShouldSum;
	}

	public void setMonthInterestBackShouldSum(BigDecimal monthInterestBackShouldSum) {
		this.monthInterestBackShouldSum = monthInterestBackShouldSum;
	}

	public BigDecimal getMonthInterestPayactualSum() {
		return monthInterestPayactualSum;
	}

	public void setMonthInterestPayactualSum(BigDecimal monthInterestPayactualSum) {
		this.monthInterestPayactualSum = monthInterestPayactualSum;
	}

	public BigDecimal getMonthInterestPunishshouldSum() {
		return monthInterestPunishshouldSum;
	}

	public void setMonthInterestPunishshouldSum(
			BigDecimal monthInterestPunishshouldSum) {
		this.monthInterestPunishshouldSum = monthInterestPunishshouldSum;
	}

	public BigDecimal getMonthInterestPunishactualSum() {
		return monthInterestPunishactualSum;
	}

	public void setMonthInterestPunishactualSum(
			BigDecimal monthInterestPunishactualSum) {
		this.monthInterestPunishactualSum = monthInterestPunishactualSum;
	}

	public BigDecimal getMonthPenaltyShouldSum() {
		return monthPenaltyShouldSum;
	}

	public void setMonthPenaltyShouldSum(BigDecimal monthPenaltyShouldSum) {
		this.monthPenaltyShouldSum = monthPenaltyShouldSum;
	}

	public BigDecimal getMonthPenaltyActualSum() {
		return monthPenaltyActualSum;
	}

	public void setMonthPenaltyActualSum(BigDecimal monthPenaltyActualSum) {
		this.monthPenaltyActualSum = monthPenaltyActualSum;
	}

	public BigDecimal getMonthsAomuntPaybacked() {
		return monthsAomuntPaybacked;
	}

	public void setMonthsAomuntPaybacked(BigDecimal monthsAomuntPaybacked) {
		this.monthsAomuntPaybacked = monthsAomuntPaybacked;
	}

	public BigDecimal getOverDueAmontNoPaybacked() {
		return overDueAmontNoPaybacked;
	}

	public void setOverDueAmontNoPaybacked(BigDecimal overDueAmontNoPaybacked) {
		this.overDueAmontNoPaybacked = overDueAmontNoPaybacked;
	}

	public BigDecimal getOverDueAmontPaybackedSum() {
		return overDueAmontPaybackedSum;
	}

	public void setOverDueAmontPaybackedSum(BigDecimal overDueAmontPaybackedSum) {
		this.overDueAmontPaybackedSum = overDueAmontPaybackedSum;
	}

	public BigDecimal getMonthAmountSum() {
		return monthAmountSum;
	}

	public void setMonthAmountSum(BigDecimal monthAmountSum) {
		this.monthAmountSum = monthAmountSum;
	}

	public BigDecimal getMonthPenaltySum() {
		return monthPenaltySum;
	}

	public void setMonthPenaltySum(BigDecimal monthPenaltySum) {
		this.monthPenaltySum = monthPenaltySum;
	}

	public BigDecimal getMonthInterestPunishSum() {
		return monthInterestPunishSum;
	}

	public void setMonthInterestPunishSum(BigDecimal monthInterestPunishSum) {
		this.monthInterestPunishSum = monthInterestPunishSum;
	}

	public BigDecimal getMonthPenaltyReductionSum() {
		return monthPenaltyReductionSum;
	}

	public void setMonthPenaltyReductionSum(BigDecimal monthPenaltyReductionSum) {
		this.monthPenaltyReductionSum = monthPenaltyReductionSum;
	}

	public BigDecimal getMonthPunishReductionSum() {
		return monthPunishReductionSum;
	}

	public void setMonthPunishReductionSum(BigDecimal monthPunishReductionSum) {
		this.monthPunishReductionSum = monthPunishReductionSum;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	
	public BigDecimal getShoudBackAmount() {
		return shoudBackAmount;
	}

	public void setShoudBackAmount(BigDecimal shoudBackAmount) {
		this.shoudBackAmount = shoudBackAmount;
	}

	public BigDecimal getMonthFeeConsult() {
		return monthFeeConsult;
	}

	public void setMonthFeeConsult(BigDecimal monthFeeConsult) {
		this.monthFeeConsult = monthFeeConsult;
	}

	public BigDecimal getMonthMidFeeService() {
		return monthMidFeeService;
	}

	public void setMonthMidFeeService(BigDecimal monthMidFeeService) {
		this.monthMidFeeService = monthMidFeeService;
	}

	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}

	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}

	public BigDecimal getMonthLateFee() {
		return monthLateFee;
	}

	public void setMonthLateFee(BigDecimal monthLateFee) {
		this.monthLateFee = monthLateFee;
	}

	public BigDecimal getMonthPayTotal() {
		return monthPayTotal;
	}

	public void setMonthPayTotal(BigDecimal monthPayTotal) {
		this.monthPayTotal = monthPayTotal;
	}

	public BigDecimal getMonthResiduePayactual() {
		return monthResiduePayactual;
	}

	public void setMonthResiduePayactual(BigDecimal monthResiduePayactual) {
		this.monthResiduePayactual = monthResiduePayactual;
	}

	public BigDecimal getActualMonthLateFee() {
		return actualMonthLateFee;
	}

	public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
		this.actualMonthLateFee = actualMonthLateFee;
	}

	public BigDecimal getRemindLogo() {
		return remindLogo;
	}

	public void setRemindLogo(BigDecimal remindLogo) {
		this.remindLogo = remindLogo;
	}

	public BigDecimal getMonthLateFeeReduction() {
		return monthLateFeeReduction;
	}

	public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
		this.monthLateFeeReduction = monthLateFeeReduction;
	}

	public BigDecimal getMonthLateFeeSum() {
		return monthLateFeeSum;
	}

	public void setMonthLateFeeSum(BigDecimal monthLateFeeSum) {
		this.monthLateFeeSum = monthLateFeeSum;
	}

	public BigDecimal getMonthLateFeeReductionSum() {
		return monthLateFeeReductionSum;
	}

	public void setMonthLateFeeReductionSum(BigDecimal monthLateFeeReductionSum) {
		this.monthLateFeeReductionSum = monthLateFeeReductionSum;
	}



	public BigDecimal getActualMonthLateFeeSum() {
		return actualMonthLateFeeSum;
	}

	public void setActualMonthLateFeeSum(BigDecimal actualMonthLateFeeSum) {
		this.actualMonthLateFeeSum = actualMonthLateFeeSum;
	}

	public BigDecimal getActualMonthFeeServiceSum() {
		return actualMonthFeeServiceSum;
	}

	public void setActualMonthFeeServiceSum(BigDecimal actualMonthFeeServiceSum) {
		this.actualMonthFeeServiceSum = actualMonthFeeServiceSum;
	}

	public BigDecimal getMonthPayTotalSum() {
		return monthPayTotalSum;
	}

	public void setMonthPayTotalSum(BigDecimal monthPayTotalSum) {
		this.monthPayTotalSum = monthPayTotalSum;
	}

	public BigDecimal getMonthFeeServiceSum() {
		return monthFeeServiceSum;
	}

	public void setMonthFeeServiceSum(BigDecimal monthFeeServiceSum) {
		this.monthFeeServiceSum = monthFeeServiceSum;
	}

	public BigDecimal getPenaltyLateNoPaybacked() {
		return penaltyLateNoPaybacked;
	}

	public void setPenaltyLateNoPaybacked(BigDecimal penaltyLateNoPaybacked) {
		this.penaltyLateNoPaybacked = penaltyLateNoPaybacked;
	}

	public BigDecimal getInterestNoPaybacked() {
		return interestNoPaybacked;
	}

	public void setInterestNoPaybacked(BigDecimal interestNoPaybacked) {
		this.interestNoPaybacked = interestNoPaybacked;
	}

	public BigDecimal getPenaltyInterest() {
		return penaltyInterest;
	}

	public void setPenaltyInterest(BigDecimal penaltyInterest) {
		this.penaltyInterest = penaltyInterest;
	}

	public String getReductionBy() {
		return reductionBy;
	}

	public void setReductionBy(String reductionBy) {
		this.reductionBy = reductionBy;
	}
    
	
}