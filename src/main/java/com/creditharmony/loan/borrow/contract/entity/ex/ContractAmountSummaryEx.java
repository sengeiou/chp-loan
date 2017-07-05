/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.exContractAmountSummary.java
 * @Create By 张灏
 * @Create In 2016年4月15日 下午6:10:35
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 合同金额占比信息
 * @Class Name ContractAmountSummary
 * @author 张灏
 * @Create In 2016年4月15日
 */
public class ContractAmountSummaryEx extends DataEntity<ContractAmountSummaryEx> {

    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = -6115146155227621883L;
    // 合同金额总额
    private String contractAmountSum;
    // 百分比
    private String percent;
    // 利率
    private String rate;
    /**
     * @return the contractAmountSum
     */
    public String getContractAmountSum() {
        return contractAmountSum;
    }
    /**
     * @param contractAmountSum the String contractAmountSum to set
     */
    public void setContractAmountSum(String contractAmountSum) {
        this.contractAmountSum = contractAmountSum;
    }
    /**
     * @return the percent
     */
    public String getPercent() {
        return percent;
    }
    /**
     * @param percent the String percent to set
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }
    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }
    /**
     * @param rate the String rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }
}
