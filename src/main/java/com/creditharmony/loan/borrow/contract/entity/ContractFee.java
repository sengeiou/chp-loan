package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

public class ContractFee extends DataEntity<ContractFee>{
 
    private static final long serialVersionUID = 9126820191034433254L;

    // 合同编号
    private String contractCode;  
    // 总费率
    private BigDecimal feeAllRaio;   
    // 前期信访费 
    private BigDecimal feePetition;
    // 加急费
    private BigDecimal feeExpedited;   
    // 前期咨询费
    private BigDecimal feeConsult;  
    // 前期审核费
    private BigDecimal feeAuditAmount;    
    // 前期居间服务费
    private BigDecimal feeService;  
    // 催收服务费
    private BigDecimal feeUrgedService; 
    // 前期信息服务费
    private BigDecimal feeInfoService;  
    // 前期综合费用
    private BigDecimal feeCount;
    // 前期综合服务费率
    private BigDecimal comprehensiveServiceRate;
    // 实放金额
    private BigDecimal feePaymentAmount;
    // 首次放款金额
    private BigDecimal firstGrantAmount;
    // 尾次放款金额
    private BigDecimal lastGrantAmount;
    // 借款利率
    private BigDecimal feeLoanRate;  
    // 借款利率 
    private Float feeLoanRatef;
    // 月利率
    private BigDecimal feeMonthRate;   
    // 月利率
    private String feeMonthRateS;
    // 分期咨询费
    private BigDecimal monthFeeConsult;
    // 分期居间服务费
    private BigDecimal monthMidFeeService;
    // 分期服务费
    private BigDecimal monthFeeService;
    // 分期服务费率
    private BigDecimal monthRateService;
    
    //月服务费
    private BigDecimal monthServiceFee;
    //月服务费率
    private BigDecimal monthServiceRate;
    //月利率
    private BigDecimal monthRate;
    //月利息
    private BigDecimal monthFee;
    
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public BigDecimal getFeeAllRaio() {
		return feeAllRaio;
	}
	public void setFeeAllRaio(BigDecimal feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}
	public BigDecimal getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}
	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}
	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}
	public BigDecimal getFeeConsult() {
		return feeConsult;
	}
	public void setFeeConsult(BigDecimal feeConsult) {
		this.feeConsult = feeConsult;
	}
	public BigDecimal getFeeAuditAmount() {
		return feeAuditAmount;
	}
	public void setFeeAuditAmount(BigDecimal feeAuditAmount) {
		this.feeAuditAmount = feeAuditAmount;
	}
	public BigDecimal getFeeService() {
		return feeService;
	}
	public void setFeeService(BigDecimal feeService) {
		this.feeService = feeService;
	}
	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public BigDecimal getFeeInfoService() {
		return feeInfoService;
	}
	public void setFeeInfoService(BigDecimal feeInfoService) {
		this.feeInfoService = feeInfoService;
	}
	public BigDecimal getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = feeCount;
	}
	public BigDecimal getComprehensiveServiceRate() {
		return comprehensiveServiceRate;
	}
	public void setComprehensiveServiceRate(BigDecimal comprehensiveServiceRate) {
		this.comprehensiveServiceRate = comprehensiveServiceRate;
	}
	public BigDecimal getFeePaymentAmount() {
		return feePaymentAmount;
	}
	public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}
	public BigDecimal getFeeLoanRate() {
		return feeLoanRate;
	}
	public void setFeeLoanRate(BigDecimal feeLoanRate) {
		this.feeLoanRate = feeLoanRate;
	}
	public Float getFeeLoanRatef() {
		return feeLoanRatef;
	}
	public void setFeeLoanRatef(Float feeLoanRatef) {
		this.feeLoanRatef = feeLoanRatef;
	}
	public BigDecimal getFeeMonthRate() {
		return feeMonthRate;
	}
	public void setFeeMonthRate(BigDecimal feeMonthRate) {
		this.feeMonthRate = feeMonthRate;
	}
	public String getFeeMonthRateS() {
		return feeMonthRateS;
	}
	public void setFeeMonthRateS(String feeMonthRateS) {
		this.feeMonthRateS = feeMonthRateS;
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
	public BigDecimal getMonthRateService() {
		return monthRateService;
	}
	public void setMonthRateService(BigDecimal monthRateService) {
		this.monthRateService = monthRateService;
	}
	public BigDecimal getFirstGrantAmount() {
		return firstGrantAmount;
	}
	public void setFirstGrantAmount(BigDecimal firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}
	public BigDecimal getLastGrantAmount() {
		return lastGrantAmount;
	}
	public void setLastGrantAmount(BigDecimal lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}
	public BigDecimal getMonthServiceFee() {
		return monthServiceFee;
	}
	public void setMonthServiceFee(BigDecimal monthServiceFee) {
		this.monthServiceFee = monthServiceFee;
	}
	public BigDecimal getMonthServiceRate() {
		return monthServiceRate;
	}
	public void setMonthServiceRate(BigDecimal monthServiceRate) {
		this.monthServiceRate = monthServiceRate;
	}
	public BigDecimal getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}
	public BigDecimal getMonthFee() {
		return monthFee;
	}
	public void setMonthFee(BigDecimal monthFee) {
		this.monthFee = monthFee;
	}
	
	
   
}