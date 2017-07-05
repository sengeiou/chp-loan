/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entityRateInfo.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午9:52:59
 */
package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name RateInfo
 * @author 张灏
 * @Create In 2016年4月13日
 */
public class RateInfo extends DataEntity<RateInfo> {

    private static final long serialVersionUID = 1L;
    // 利率
    private String rate;
    // 是否有效 默认为无效
    private String effectiveFlag;
    // 开始日期
    private String  startDate;
    // 结束日期
    private String endDate;
    // 当前日期
    private String curDate;
    //风险等级
    private String riskLevel;
    
    private BigDecimal loanMonths;
    //
    private List<RateInfo> rateInfoList = new ArrayList<RateInfo>();
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
    /**
     * @return the effectiveFlag
     */
    public String getEffectiveFlag() {
        return effectiveFlag;
    }
    /**
     * @param effectiveFlag the String effectiveFlag to set
     */
    public void setEffectiveFlag(String effectiveFlag) {
        this.effectiveFlag = effectiveFlag;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the Date startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the Date endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the curDate
     */
    public String getCurDate() {
        return curDate;
    }
    /**
     * @param curDate the Date curDate to set
     */
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }
    /**
     * @return the rateInfoList
     */
    public List<RateInfo> getRateInfoList() {
        return rateInfoList;
    }
    /**
     * @param rateInfoList the List<RateInfo> rateInfoList to set
     */
    public void setRateInfoList(List<RateInfo> rateInfoList) {
        this.rateInfoList = rateInfoList;
    }
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public BigDecimal getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(BigDecimal loanMonths) {
		this.loanMonths = loanMonths;
	}
    
}
