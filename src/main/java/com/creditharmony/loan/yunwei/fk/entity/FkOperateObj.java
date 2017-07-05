package com.creditharmony.loan.yunwei.fk.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

public class FkOperateObj extends DataEntity<FkOperateObj> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9057361935213680225L;

	//=======================参数=========================//
	private String repaymentDate;
	
	private String contractCodes;
	
	private String errMsg;
	
	private String firstXiuFuDate;
	
	//======================还款明细========================//
	private String paybackMonthId; //还款明细表ID
	private String monthPayDay; //还款日期
	private String contractCode; //合同编号
	private String dictMonthStatus; 
	private String monthOverdueDays; // 逾期天数
	private BigDecimal paybackBuleAmount;//当前蓝补金额
	private BigDecimal monthPayAmount ;// 应还本金,--应还本金
	private BigDecimal monthCapitalPayactual;// 实还本金,--实还本金
	private BigDecimal monthInterestBackshould;// 应还利息,--应还利息
	private BigDecimal monthInterestPayactual;// 实还利息,--实还利息
	private BigDecimal monthFeeService;// 分期服务费,--分期服务费
	private BigDecimal actualMonthFeeService;// 实还分期服务费,--实还分期服务费
	private BigDecimal monthInterestPunishshould;// 应还罚息,--应还罚息(1.3系统单算罚息,1.4罚息为1.3违约金罚息合计)
	private BigDecimal monthInterestPunishactual;// 实还罚息, --实还罚息
	private BigDecimal monthPunishReduction;// 减免罚息, --减免罚息
	private BigDecimal monthPenaltyShould;// 应还违约金, --应还违约金(1.3系统单算罚息,1.4一次性还款违约罚金)
	private BigDecimal monthPenaltyActual;// 实还违约金,--实还违约金
	private BigDecimal monthPenaltyReduction;// 减免违约金,--减免违约金
	private BigDecimal monthLateFee;// 滞纳金,--滞纳金
	private BigDecimal actualMonthLateFee;// 实还滞纳金,--实还滞纳金
	private BigDecimal monthLateFeeReduction;// 减免滞纳金 --减免滞纳金
	
	//==============================================//
	// 期供金额
	private BigDecimal qgMoney;
	// 蓝补金额
	private BigDecimal currentBlueMoney;
	//==============================================//
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}
	
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public String getContractCodes() {
		return contractCodes;
	}

	public void setContractCodes(String contractCodes) {
		this.contractCodes = contractCodes;
	}

	public String getPaybackMonthId() {
		return paybackMonthId;
	}

	public void setPaybackMonthId(String paybackMonthId) {
		this.paybackMonthId = paybackMonthId;
	}

	public String getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(String monthPayDay) {
		this.monthPayDay = monthPayDay;
	}

	public String getDictMonthStatus() {
		return dictMonthStatus;
	}

	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}

	public String getMonthOverdueDays() {
		return monthOverdueDays;
	}

	public void setMonthOverdueDays(String monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}

	public BigDecimal getMonthPayAmount() {
		return monthPayAmount;
	}

	public void setMonthPayAmount(BigDecimal monthPayAmount) {
		this.monthPayAmount = monthPayAmount;
	}

	public BigDecimal getMonthCapitalPayactual() {
		return monthCapitalPayactual;
	}

	public void setMonthCapitalPayactual(BigDecimal monthCapitalPayactual) {
		this.monthCapitalPayactual = monthCapitalPayactual;
	}

	public BigDecimal getMonthInterestBackshould() {
		return monthInterestBackshould;
	}

	public void setMonthInterestBackshould(BigDecimal monthInterestBackshould) {
		this.monthInterestBackshould = monthInterestBackshould;
	}

	public BigDecimal getMonthInterestPayactual() {
		return monthInterestPayactual;
	}

	public void setMonthInterestPayactual(BigDecimal monthInterestPayactual) {
		this.monthInterestPayactual = monthInterestPayactual;
	}

	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}

	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}

	public BigDecimal getActualMonthFeeService() {
		return actualMonthFeeService;
	}

	public void setActualMonthFeeService(BigDecimal actualMonthFeeService) {
		this.actualMonthFeeService = actualMonthFeeService;
	}

	public BigDecimal getMonthInterestPunishshould() {
		return monthInterestPunishshould;
	}

	public void setMonthInterestPunishshould(BigDecimal monthInterestPunishshould) {
		this.monthInterestPunishshould = monthInterestPunishshould;
	}

	public BigDecimal getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}

	public void setMonthInterestPunishactual(BigDecimal monthInterestPunishactual) {
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

	public BigDecimal getMonthLateFee() {
		return monthLateFee;
	}

	public void setMonthLateFee(BigDecimal monthLateFee) {
		this.monthLateFee = monthLateFee;
	}

	public BigDecimal getActualMonthLateFee() {
		return actualMonthLateFee;
	}

	public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
		this.actualMonthLateFee = actualMonthLateFee;
	}

	public BigDecimal getMonthLateFeeReduction() {
		return monthLateFeeReduction;
	}

	public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
		this.monthLateFeeReduction = monthLateFeeReduction;
	}

	public BigDecimal getQgMoney() {
		return qgMoney ==null ? BigDecimal.ZERO : qgMoney;
	}

	public void setQgMoney(BigDecimal qgMoney) {
		this.qgMoney = qgMoney;
	}

	public BigDecimal getCurrentBlueMoney() {
		return currentBlueMoney ==null ? BigDecimal.ZERO : currentBlueMoney;
	}

	public void setCurrentBlueMoney(BigDecimal currentBlueMoney) {
		this.currentBlueMoney = currentBlueMoney;
	}

	public String getFirstXiuFuDate() {
		return firstXiuFuDate;
	}

	public void setFirstXiuFuDate(String firstXiuFuDate) {
		this.firstXiuFuDate = firstXiuFuDate;
	}
	
}
