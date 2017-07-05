package com.creditharmony.loan.borrow.pushdata.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaybackMonth {

	// 最大期数
	private Integer maxMonths;
	private String contractVersion;
	private String MonthPayDay2;
    private String id;
    private String contractCode;
    private Integer months;
    private Date monthPayDay;
    private Date monthPayActualday;
    private BigDecimal monthPayAmount;
    private BigDecimal monthInterestBackshould;
    private BigDecimal monthCapitalPayactual;
    private BigDecimal monthInterestPayactual;
    private String isOverdue;
    private BigDecimal monthBeforeFinishAmount;
    private BigDecimal monthBeforeReductionAmount;
    private BigDecimal monthInterestPunishshould;
    private BigDecimal monthInterestPunishactual;
    private BigDecimal monthPunishReduction;
    private BigDecimal monthPenaltyShould;
    private BigDecimal monthPenaltyActual;
    private BigDecimal monthPenaltyReduction;
    private Integer monthOverdueDays;
    private Integer monthReductionDay;
    private String dictMonthStatus;
    private String monthOverdueMes;
    private String createBy;
    private Date createTime;
    private String modifyBy;
    private Date modifyTime;
    private BigDecimal monthFeeConsult;
    private BigDecimal monthMidFeeService;
    private BigDecimal monthFeeService;
    private BigDecimal monthLateFee;
    private BigDecimal monthPayTotal;
    private BigDecimal monthResiduePayactual;
    private BigDecimal actualMonthLateFee;
    private String remindLogo;
    private BigDecimal actualMonthFeeService;
    private BigDecimal monthLateFeeReduction;
    private BigDecimal monthBeforePenaltyShould;
    private BigDecimal monthBeforePenaltyActual;

    // 蓝补金额
    private BigDecimal paybackBuleAmount;
    private Integer maxMonthOverdueDays;
    private Integer paybackMaxOverduedays;
    
	public Integer getMaxMonths() {
		return maxMonths;
	}

	public void setMaxMonths(Integer maxMonths) {
		this.maxMonths = maxMonths;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getMonthPayDay2() {
		return MonthPayDay2;
	}

	public void setMonthPayDay2(String monthPayDay2) {
		MonthPayDay2 = monthPayDay2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Date getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}

	public Date getMonthPayActualday() {
		return monthPayActualday;
	}

	public void setMonthPayActualday(Date monthPayActualday) {
		this.monthPayActualday = monthPayActualday;
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

	public BigDecimal getMonthCapitalPayactual() {
		return monthCapitalPayactual;
	}

	public void setMonthCapitalPayactual(BigDecimal monthCapitalPayactual) {
		this.monthCapitalPayactual = monthCapitalPayactual;
	}

	public BigDecimal getMonthInterestPayactual() {
		return monthInterestPayactual;
	}

	public void setMonthInterestPayactual(BigDecimal monthInterestPayactual) {
		this.monthInterestPayactual = monthInterestPayactual;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public BigDecimal getMonthBeforeFinishAmount() {
		return monthBeforeFinishAmount;
	}

	public void setMonthBeforeFinishAmount(BigDecimal monthBeforeFinishAmount) {
		this.monthBeforeFinishAmount = monthBeforeFinishAmount;
	}

	public BigDecimal getMonthBeforeReductionAmount() {
		return monthBeforeReductionAmount;
	}

	public void setMonthBeforeReductionAmount(BigDecimal monthBeforeReductionAmount) {
		this.monthBeforeReductionAmount = monthBeforeReductionAmount;
	}

	public BigDecimal getMonthInterestPunishshould() {
		return monthInterestPunishshould;
	}

	public void setMonthInterestPunishshould(BigDecimal monthInterestPunishshould) {
		this.monthInterestPunishshould = monthInterestPunishshould;
	}

	public BigDecimal getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}

	public void setMonthInterestPunishactual(BigDecimal monthInterestPunishactual) {
		this.monthInterestPunishactual = monthInterestPunishactual;
	}

	public BigDecimal getMonthPunishReduction() {
		return monthPunishReduction;
	}

	public void setMonthPunishReduction(BigDecimal monthPunishReduction) {
		this.monthPunishReduction = monthPunishReduction;
	}

	public BigDecimal getMonthPenaltyShould() {
		return monthPenaltyShould;
	}

	public void setMonthPenaltyShould(BigDecimal monthPenaltyShould) {
		this.monthPenaltyShould = monthPenaltyShould;
	}

	public BigDecimal getMonthPenaltyActual() {
		return monthPenaltyActual;
	}

	public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}

	public BigDecimal getMonthPenaltyReduction() {
		return monthPenaltyReduction;
	}

	public void setMonthPenaltyReduction(BigDecimal monthPenaltyReduction) {
		this.monthPenaltyReduction = monthPenaltyReduction;
	}

	public Integer getMonthOverdueDays() {
		return monthOverdueDays;
	}

	public void setMonthOverdueDays(Integer monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
	}

	public Integer getMonthReductionDay() {
		return monthReductionDay;
	}

	public void setMonthReductionDay(Integer monthReductionDay) {
		this.monthReductionDay = monthReductionDay;
	}

	public String getDictMonthStatus() {
		return dictMonthStatus;
	}

	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}

	public String getMonthOverdueMes() {
		return monthOverdueMes;
	}

	public void setMonthOverdueMes(String monthOverdueMes) {
		this.monthOverdueMes = monthOverdueMes;
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

	public BigDecimal getMonthLateFee() {
		return monthLateFee;
	}

	public void setMonthLateFee(BigDecimal monthLateFee) {
		this.monthLateFee = monthLateFee;
	}

	public BigDecimal getMonthPayTotal() {
		return monthPayTotal;
	}

	public void setMonthPayTotal(BigDecimal monthPayTotal) {
		this.monthPayTotal = monthPayTotal;
	}

	public BigDecimal getMonthResiduePayactual() {
		return monthResiduePayactual;
	}

	public void setMonthResiduePayactual(BigDecimal monthResiduePayactual) {
		this.monthResiduePayactual = monthResiduePayactual;
	}

	public BigDecimal getActualMonthLateFee() {
		return actualMonthLateFee;
	}

	public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
		this.actualMonthLateFee = actualMonthLateFee;
	}

	public String getRemindLogo() {
		return remindLogo;
	}

	public void setRemindLogo(String remindLogo) {
		this.remindLogo = remindLogo;
	}

	public BigDecimal getActualMonthFeeService() {
		return actualMonthFeeService;
	}

	public void setActualMonthFeeService(BigDecimal actualMonthFeeService) {
		this.actualMonthFeeService = actualMonthFeeService;
	}

	public BigDecimal getMonthLateFeeReduction() {
		return monthLateFeeReduction;
	}

	public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
		this.monthLateFeeReduction = monthLateFeeReduction;
	}

	public BigDecimal getMonthBeforePenaltyShould() {
		return monthBeforePenaltyShould;
	}

	public void setMonthBeforePenaltyShould(BigDecimal monthBeforePenaltyShould) {
		this.monthBeforePenaltyShould = monthBeforePenaltyShould;
	}

	public BigDecimal getMonthBeforePenaltyActual() {
		return monthBeforePenaltyActual;
	}

	public void setMonthBeforePenaltyActual(BigDecimal monthBeforePenaltyActual) {
		this.monthBeforePenaltyActual = monthBeforePenaltyActual;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}

	public Integer getMaxMonthOverdueDays() {
		return maxMonthOverdueDays;
	}

	public void setMaxMonthOverdueDays(Integer maxMonthOverdueDays) {
		this.maxMonthOverdueDays = maxMonthOverdueDays;
	}

	public Integer getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
	}

	public void setPaybackMaxOverduedays(Integer paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
	}
}