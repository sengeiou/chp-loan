/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityCoeffRefer.java
 * @Create By 王彬彬
 * @Create In 2016年4月23日 下午3:48:22
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 系数参照表（期数、费率、前期综合费）
 * 
 * @Class Name CoeffRefer
 * @author 王彬彬
 * @Create In 2016年4月23日
 */
@SuppressWarnings("serial")
public class CoeffRefer extends DataEntity<CoeffRefer> {
	private Integer months;// 期数

	private String systemFlag; // 系统标识（0：汇金，1：汇诚）

	private Double productUsableRate; // 可用产品总费率（%）
	
	private Double comprehensiveFeeCoeff;//前期综合费用系数（%）
	
	private Double monthGatherRation; //分期服务费每月收取比例（%）
	
	private String riskDelFlag;
	
	private String riskLevel;

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public Double getProductUsableRate() {
		return productUsableRate;
	}

	public void setProductUsableRate(Double productUsableRate) {
		this.productUsableRate = productUsableRate;
	}

	public Double getComprehensiveFeeCoeff() {
		return comprehensiveFeeCoeff;
	}

	public void setComprehensiveFeeCoeff(Double comprehensiveFeeCoeff) {
		this.comprehensiveFeeCoeff = comprehensiveFeeCoeff;
	}

	public Double getMonthGatherRation() {
		return monthGatherRation;
	}

	public void setMonthGatherRation(Double monthGatherRation) {
		this.monthGatherRation = monthGatherRation;
	}

	public String getRiskDelFlag() {
		return riskDelFlag;
	}

	public void setRiskDelFlag(String riskDelFlag) {
		this.riskDelFlag = riskDelFlag;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
}
