/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entityRateInfo.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午9:52:59
 */
package com.creditharmony.loan.borrow.contract.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name RateInfo
 * @author 申阿伟
 * @Create In 2017年5月6日
 */
public class CoeffReferJYJ extends DataEntity<CoeffReferJYJ> {

    private static final long serialVersionUID = 1L;
    //期数
    private Integer months;
    // 月利率
    private double rate;
    //标识、
    private String systemFlag;
    //产品总费率
    private double productRate;
    //前期服务费
    private double comprehensiveFeeCoeff;
    //分期服务费
    private double monthGatherRate;
    //产品类型
    private String productCode;
    
    private String createBy;
    private Date createTime;
    private String modifyBy;
    private Date modifyTime;
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getSystemFlag() {
		return systemFlag;
	}
	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}
	
	public double getProductRate() {
		return productRate;
	}
	public void setProductRate(double productRate) {
		this.productRate = productRate;
	}
	public double getComprehensiveFeeCoeff() {
		return comprehensiveFeeCoeff;
	}
	public void setComprehensiveFeeCoeff(double comprehensiveFeeCoeff) {
		this.comprehensiveFeeCoeff = comprehensiveFeeCoeff;
	}
	public double getMonthGatherRate() {
		return monthGatherRate;
	}
	public void setMonthGatherRate(double monthGatherRate) {
		this.monthGatherRate = monthGatherRate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
    
    
}
