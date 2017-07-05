/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityRepayPlan.java
 * @Create By 张灏
 * @Create In 2015年12月4日 下午3:29:47
 */
package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Class Name RepayPlan
 * @author 张灏
 * @Create In 2015年12月4日
 */
public class RepayPlan {
  
    // 期数 
    private int payBackCurrentMonth;
    // 还款日  
    private Date monthPayDay;
    // 月还本金
    private BigDecimal monthPayAmount;
    // 应还利息  
    private BigDecimal monthInterestBackshould;
    // 应还月还款额   
    private BigDecimal monthPayAmountSum;
    // 初始本金
    private  BigDecimal monthInitPayAmount;  
    // 当期本金 
    private  BigDecimal curPrincipal;   
    // 当前综合费
    private BigDecimal currTotalFee;
    // 惠民费用
    private BigDecimal huiMinFee;
    // 汇金费用 
    private BigDecimal huiJinFee;
    // 提前结清违约金 
    private BigDecimal penalty;        
    // 修正退费
    private BigDecimal fixBackFee;
    // 修正结清金额
    private BigDecimal earlySettlement;
    // 不算违约金、罚息和期供的当期一次性结清金额
    private BigDecimal settlementOnce;
    
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

    public BigDecimal getCurrTotalFee() {
        return currTotalFee;
    }

    public void setCurrTotalFee(BigDecimal currTotalFee) {
        this.currTotalFee = currTotalFee;
    }

    public BigDecimal getHuiMinFee() {
        return huiMinFee;
    }

    public void setHuiMinFee(BigDecimal huiMinFee) {
        this.huiMinFee = huiMinFee;
    }

    public BigDecimal getHuiJinFee() {
        return huiJinFee;
    }

    public void setHuiJinFee(BigDecimal huiJinFee) {
        this.huiJinFee = huiJinFee;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public BigDecimal getFixBackFee() {
        return fixBackFee;
    }

    public void setFixBackFee(BigDecimal fixBackFee) {
        this.fixBackFee = fixBackFee;
    }

    public BigDecimal getEarlySettlement() {
        return earlySettlement;
    }

    public void setEarlySettlement(BigDecimal earlySettlement) {
        this.earlySettlement = earlySettlement;
    }

    public BigDecimal getSettlementOnce() {
        return settlementOnce;
    }

    public void setSettlementOnce(BigDecimal settlementOnce) {
        this.settlementOnce = settlementOnce;
    }
    
}
