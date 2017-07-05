package com.creditharmony.loan.channel.finance.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 大金融业务列表页面展示实体类
 * 
 * @Class Name FinancialBusinessEntity
 * @author 张建雄
 * @Create In 2016年2月18日
 */
public class CarFinancialBusinessEntity extends
		DataEntity<CarFinancialBusinessEntity> {
	private static final long serialVersionUID = 2994811734409500770L;
	/* 借款Id */
	private String loanCode;
	/* 债务人 */
	private String loanCustomerName;
	/* 身份证号码 */
	private String customerCertNum;
	/* 借款用途 */
	private String dictLoanUse;
	/* 借款类型 */
	private String classType;
	/* 原始期限（月） */
	private Integer loanMonths;
	/* 原始借款开始日期 */
	private Date loanStartDate;
	/* 原始借款到期日期 */
	private Date loanEndDate;
	/* 还款日 */
	private Date replayDay;
	/* 还款金额 */
	private BigDecimal contractMonthRepayAmount;
	/* 债权金额（元） */
	private BigDecimal contractAmount;
	/* 债权月利率（%） */
	private BigDecimal feeMonthRate;
	/* 债权确认时间 */
	private Timestamp creditConfirmDate;
	/* 结清确认时间 */
	private Timestamp settleConfirmDate;
	/* 提前结清日期 */
	private Date settleDate;
	/* 债权状态 */
	private Integer creditType;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getDictLoanUse() {
		return dictLoanUse;
	}

	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public Date getReplayDay() {
		return replayDay;
	}

	public void setReplayDay(Date replayDay) {
		this.replayDay = replayDay;
	}

	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getFeeMonthRate() {
		return feeMonthRate;
	}

	public void setFeeMonthRate(BigDecimal feeMonthRate) {
		this.feeMonthRate = feeMonthRate;
	}

	public Timestamp getCreditConfirmDate() {
		return creditConfirmDate;
	}

	public void setCreditConfirmDate(Timestamp creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}

	public Timestamp getSettleConfirmDate() {
		return settleConfirmDate;
	}

	public void setSettleConfirmDate(Timestamp settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Integer getCreditType() {
		return creditType;
	}

	public void setCreditType(Integer creditType) {
		this.creditType = creditType;
	}

}
