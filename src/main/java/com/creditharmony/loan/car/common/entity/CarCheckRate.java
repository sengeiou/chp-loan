package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 	  车借_合同费率
 *  @Class Name CarCheckRate
 * 	@author 任亮杰
 * 	@Create In 2016年1月21日
 * 
 */
public class CarCheckRate extends DataEntity<CarCheckRate> {

	private static final long serialVersionUID = -7021110791255326556L;
	//  借款编码
	private String loanCode;
	//  利息率
	private BigDecimal interestRate;
	//  月利息
	private BigDecimal monthlyInterest;
	//  首期服务费率
	private BigDecimal firstServiceTariffingRate;
	//  首期服务费
	private BigDecimal firstServiceTariffing;
	//  合同金额
	private BigDecimal contractAmount;
	//  实放金额
	private BigDecimal feePaymentAmount;
	//  月还金额
	private BigDecimal monthRepayAmount;
	//  综合费用
	private BigDecimal comprehensiveServiceFee;
	//  审核费
	private BigDecimal auditFee;
	//  咨询费
	private BigDecimal consultingFee;
	//  居间服务费
	private BigDecimal intermediaryServiceFee;
	//  信息服务费
	private BigDecimal informationServiceCharge;
	//  违约罚息
	private BigDecimal defaultPenaltyInterest;
	//  违约罚息率
	private BigDecimal defaultPenaltyInterestRate;
	//  违约金利率
	private BigDecimal defaultPenaltyRate;
	//  违约金
	private BigDecimal defaultPenalty;
	//  创建人
	private String createBy;
	//  创建时间
	private Date createTime;
	// 放款金额
    private BigDecimal grantAmount;
    // 划扣金额
    private BigDecimal deductAmount;
    //展期应还总金额
    private BigDecimal extendPayAmount;
    //外访费
    private BigDecimal outVisitFee ;
    
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public BigDecimal getMonthlyInterest() {
		return monthlyInterest;
	}
	public void setMonthlyInterest(BigDecimal monthlyInterest) {
		this.monthlyInterest = monthlyInterest;
	}
	public BigDecimal getFirstServiceTariffingRate() {
		return firstServiceTariffingRate;
	}
	public void setFirstServiceTariffingRate(BigDecimal firstServiceTariffingRate) {
		this.firstServiceTariffingRate = firstServiceTariffingRate;
	}
	public BigDecimal getFirstServiceTariffing() {
		return firstServiceTariffing;
	}
	public void setFirstServiceTariffing(BigDecimal firstServiceTariffing) {
		this.firstServiceTariffing = firstServiceTariffing;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getFeePaymentAmount() {
		return feePaymentAmount;
	}
	public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}
	public BigDecimal getMonthRepayAmount() {
		return monthRepayAmount;
	}
	public void setMonthRepayAmount(BigDecimal monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}
	public BigDecimal getComprehensiveServiceFee() {
		return comprehensiveServiceFee;
	}
	public void setComprehensiveServiceFee(BigDecimal comprehensiveServiceFee) {
		this.comprehensiveServiceFee = comprehensiveServiceFee;
	}
	public BigDecimal getAuditFee() {
		return auditFee;
	}
	public void setAuditFee(BigDecimal auditFee) {
		this.auditFee = auditFee;
	}
	public BigDecimal getConsultingFee() {
		return consultingFee;
	}
	public void setConsultingFee(BigDecimal consultingFee) {
		this.consultingFee = consultingFee;
	}
	public BigDecimal getIntermediaryServiceFee() {
		return intermediaryServiceFee;
	}
	public void setIntermediaryServiceFee(BigDecimal intermediaryServiceFee) {
		this.intermediaryServiceFee = intermediaryServiceFee;
	}
	public BigDecimal getInformationServiceCharge() {
		return informationServiceCharge;
	}
	public void setInformationServiceCharge(BigDecimal informationServiceCharge) {
		this.informationServiceCharge = informationServiceCharge;
	}
	public BigDecimal getDefaultPenaltyInterest() {
		return defaultPenaltyInterest;
	}
	public void setDefaultPenaltyInterest(BigDecimal defaultPenaltyInterest) {
		this.defaultPenaltyInterest = defaultPenaltyInterest;
	}
	public BigDecimal getDefaultPenaltyInterestRate() {
		return defaultPenaltyInterestRate;
	}
	public void setDefaultPenaltyInterestRate(BigDecimal defaultPenaltyInterestRate) {
		this.defaultPenaltyInterestRate = defaultPenaltyInterestRate;
	}
	public BigDecimal getDefaultPenaltyRate() {
		return defaultPenaltyRate;
	}
	public void setDefaultPenaltyRate(BigDecimal defaultPenaltyRate) {
		this.defaultPenaltyRate = defaultPenaltyRate;
	}
	public BigDecimal getDefaultPenalty() {
		return defaultPenalty;
	}
	public void setDefaultPenalty(BigDecimal defaultPenalty) {
		this.defaultPenalty = defaultPenalty;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	public BigDecimal getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}
	public BigDecimal getExtendPayAmount() {
		return extendPayAmount;
	}
	public void setExtendPayAmount(BigDecimal extendPayAmount) {
		this.extendPayAmount = extendPayAmount;
	}
	public BigDecimal getOutVisitFee() {
		return outVisitFee;
	}
	public void setOutVisitFee(BigDecimal outVisitFee) {
		this.outVisitFee = outVisitFee;
	}
	
}
