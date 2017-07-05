package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 还款计划
 * @Class Name RepayPlanNew
 * @author 张灏
 * @Create In 2015年12月4日
 */
public class RepayPlanNew {
   
	  // 期数 
    private int payBackCurrentMonth;
    // 还款日  
    private Date monthPayDay;
    // 应还月还款额   
    private BigDecimal monthPayAmountSum;
    // 初始本金
    private  BigDecimal monthInitPayAmount;  
    // 当期本金 
    private  BigDecimal curPrincipal;   
    // 月还本金
    private BigDecimal monthPayAmount;
    // 应还利息  
    private BigDecimal monthInterestBackshould;
    // 分期服务费
    private BigDecimal stageServiceFee;
    // 分期咨询费
    private BigDecimal stageFeeConsult;
    // 分期居间服务费
    private BigDecimal stageFeeService;
    // 应还款总额
    private BigDecimal monthPaySum;
    // 一次性还款违约罚金
    private BigDecimal oncePayPenalty;
    // 一次性还款总额
    private BigDecimal oncePaySum;
    // 还款后剩余本金
    private BigDecimal residualPrincipal;
    
	public int getPayBackCurrentMonth() {
		return payBackCurrentMonth;
	}
	public void setPayBackCurrentMonth(int payBackCurrentMonth) {
		this.payBackCurrentMonth = payBackCurrentMonth;
	}
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public BigDecimal getMonthPayAmountSum() {
		return monthPayAmountSum;
	}
	public void setMonthPayAmountSum(BigDecimal monthPayAmountSum) {
		this.monthPayAmountSum = monthPayAmountSum;
	}
	public BigDecimal getMonthInitPayAmount() {
		return monthInitPayAmount;
	}
	public void setMonthInitPayAmount(BigDecimal monthInitPayAmount) {
		this.monthInitPayAmount = monthInitPayAmount;
	}
	public BigDecimal getCurPrincipal() {
		return curPrincipal;
	}
	public void setCurPrincipal(BigDecimal curPrincipal) {
		this.curPrincipal = curPrincipal;
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
	public BigDecimal getStageServiceFee() {
		return stageServiceFee;
	}
	public void setStageServiceFee(BigDecimal stageServiceFee) {
		this.stageServiceFee = stageServiceFee;
	}
	public BigDecimal getStageFeeConsult() {
		return stageFeeConsult;
	}
	public void setStageFeeConsult(BigDecimal stageFeeConsult) {
		this.stageFeeConsult = stageFeeConsult;
	}
	public BigDecimal getStageFeeService() {
		return stageFeeService;
	}
	public void setStageFeeService(BigDecimal stageFeeService) {
		this.stageFeeService = stageFeeService;
	}
	public BigDecimal getMonthPaySum() {
		return monthPaySum;
	}
	public void setMonthPaySum(BigDecimal monthPaySum) {
		this.monthPaySum = monthPaySum;
	}
	public BigDecimal getOncePayPenalty() {
		return oncePayPenalty;
	}
	public void setOncePayPenalty(BigDecimal oncePayPenalty) {
		this.oncePayPenalty = oncePayPenalty;
	}
	public BigDecimal getOncePaySum() {
		return oncePaySum;
	}
	public void setOncePaySum(BigDecimal oncePaySum) {
		this.oncePaySum = oncePaySum;
	}
	public BigDecimal getResidualPrincipal() {
		return residualPrincipal;
	}
	public void setResidualPrincipal(BigDecimal residualPrincipal) {
		this.residualPrincipal = residualPrincipal;
	}
}
