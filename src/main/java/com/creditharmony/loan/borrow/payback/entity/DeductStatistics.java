package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
@SuppressWarnings("serial")
public class DeductStatistics extends DataEntity<DeductStatistics>{
	
	private String id;
	
	private String plantCode; // 划扣平台
	
	private BigDecimal notEnoughProportion; //余额不足比例
	
	private Integer notEnoughNumber;  // 余额不足条数
	
	private BigDecimal   failureRate;  // 失败率
	
	private  Integer failureNumber; // 失败笔数
	
	private String createDate;// 创建日期
	
	private Integer deductNumber; // 划扣笔数
	
	private Date beginDate;
	private Date endDate;
	
	private String statisticsFlag; // 是否点击了统计，用来判断导出什么
	


	public String getPlantCode() {
		return plantCode;
	}

	public BigDecimal getNotEnoughProportion() {
		return notEnoughProportion;
	}

	public Integer getNotEnoughNumber() {
		return notEnoughNumber;
	}

	public BigDecimal getFailureRate() {
		return failureRate;
	}

	public Integer getFailureNumber() {
		return failureNumber;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public void setNotEnoughProportion(BigDecimal notEnoughProportion) {
		this.notEnoughProportion = notEnoughProportion;
	}

	public void setNotEnoughNumber(Integer notEnoughNumber) {
		this.notEnoughNumber = notEnoughNumber;
	}

	public void setFailureRate(BigDecimal failureRate) {
		this.failureRate = failureRate;
	}

	public void setFailureNumber(Integer failureNumber) {
		this.failureNumber = failureNumber;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getDeductNumber() {
		return deductNumber;
	}

	public void setDeductNumber(Integer deductNumber) {
		this.deductNumber = deductNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatisticsFlag() {
		return statisticsFlag;
	}

	public void setStatisticsFlag(String statisticsFlag) {
		this.statisticsFlag = statisticsFlag;
	}
	
	

}
