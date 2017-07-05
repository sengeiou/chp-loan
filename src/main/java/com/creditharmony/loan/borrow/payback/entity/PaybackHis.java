package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
/**
 * 还款历史明细表
 * @Class Name JkProducts
 * @author wengsi
 * @Create In 2015年12月23日
 */
@SuppressWarnings("serial")
public class PaybackHis extends DataEntity<PaybackHis>{
	 //还款历史明细表主键
	private String id;
	 // 关联ID（期供ID）
	private String rMonthId;
	// 合同编号
	private String contractCode;
	// 还款金额
    private BigDecimal paymentAmount;
    // 还款日期
    private Date paymentDay;
    //还款专员编号
    private String paymentCommissionerCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getrMonthId() {
		return rMonthId;
	}
	public void setrMonthId(String rMonthId) {
		this.rMonthId = rMonthId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Date getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(Date paymentDay) {
		this.paymentDay = paymentDay;
	}
	public String getPaymentCommissionerCode() {
		return paymentCommissionerCode;
	}
	public void setPaymentCommissionerCode(String paymentCommissionerCode) {
		this.paymentCommissionerCode = paymentCommissionerCode;
	}
    
}
