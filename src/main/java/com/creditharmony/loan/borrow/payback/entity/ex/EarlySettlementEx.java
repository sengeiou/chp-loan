package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 	
 * @Class Name EarlySettlement Entity
 * @author 李强
 * @Create 2015年12月01日
 */
@SuppressWarnings("serial")
public class EarlySettlementEx extends DataEntity<EarlySettlementEx> {

	private String rPaybackId;					// 还款主表ID 
	private String paybackMonthId;					// 期供ID
	private String orgName;					 	// 门店名称
	private String contractCode;			 	// 合同编号
	private String customerName;			 	// 客户名称
	private BigDecimal contractMoney;			 	// 合同金额
	private String contractVersion;             // 合同版本号
	private float urgeMoeny;				 	// 催收服务费金额
	private float urgeDecuteMoeny;              // 已收催收服务费金额
	private String returnLogo;                  // 催收服务费中的退回标示
	private float grantAmount;				 	// 放款金额
	private Integer contractMonths;			 	// 批借期数
	private Date contractReplayDate;		 	// 首期还款日
	private int paybackMaxOverduedays;		 	// 最长逾期天数
	private float modifyYingpaybackBackMoney;	// 提前结清应还款总额
	private float applyAmountPaybac;		 	// 申请还款总额
	private String dictPayStatus;			 	// 还款状态
	private String dictPayStatusLabel;			 	// 还款状态名称
	private Date modifyDate;				 	// 提前结清日期
	private String dictLoanStatus;			 	// 借款状态
	private String dictLoanStatusLabel;			 	// 借款状态名称
	private String reductionBy;				 	// 减免人
	private float monthPunishReduction;		 	// 减免罚息
	private float monthPenaltyReduction;	 	// 减免违约金
	private String loanFlag;					// 标识
	private String loanFlagLabel;					// 标识名称
	private String loanIsPhone;				 	// 是否电销
	private String loanIsPhoneLabel;				 	// 是否电销名称
	private String dictSourceType;				// 来源系统
	
	private float actualModifyMoney;		 	// 实际提前结清金额
	private float monthMoney;				 	// 减免金额
	private Date beginDate;						// 开始时间
	private Date endDate;						// 结束时间
	private float paybackBuleAmont;			 	// 蓝补金额
	private String dictPayStatusSF;			 	// 是否还款
	private String paybackBackMoney;		 	// 返款金额
	private String remark;					 	// 审核意见
	private float monthBeforefinishMoney;	 	// 一次性提前结清应还款金额  期供表 提前结清金额
	private float applyAmountPayback;		 	// 申请还款总额
	private float applyAmountViolate;		 	// 申请违约金总额
	private float applyAmountPunish;		 	// 申请还罚息总额
	private float applydictPayMoney;		 	// 申请还款金额
	private float paybackMonthMoney;		 	// 期供金额
	private float monthCapitalPayactual;	 	// 实还本金
	private float monthInterestPayactual;	 	// 实还利息
	private float monthPayMoney;			 	// 应还本金
	private float monthInterestBackshould;	 	// 应还利息
	private float monthPenaltyActual;		 	// 实还违约金
	private float monthInterestPunishactual; 	// 实还罚息
	private BigDecimal hisOverpaybackMonthMoney;	  	// 已还期供金额
	private float notYetLiquidatedPenalty;	 	// 未还违约金及罚息金额
	private BigDecimal hisOverLiquidatedPenalty;	 	// 已还违约金及罚息金额
	private float monthModifyMoney;			 	// 减免提前结清金额
	private BigDecimal monthPunishReductionSum;   // 减免罚息总和
	private BigDecimal monthPenaltyReductionSum;  // 减免违约金总和
	private BigDecimal monthPenaltyPunishReductionSum;//减免违约金(滞纳金)及罚息总额
	private float oneTimeMonthpaybackBackMoney; // 一次性提前结清应还款金额
	private float monthPenaltyShould; 			// 应还违约金
	private float monthInterestPunishshould;	// 应还罚息
	private String chargeStatus;				// 冲抵状态
	private String chargeStatusLabel;				// 冲抵状态
	private float monthBeforeReductionAmount; // 提前结清减免金额 
	
	private BigDecimal monthLateFeeReductionSum;// 减免滞纳金总额
	private BigDecimal monthCapitalPayactualSum;//实还本金总额
	private BigDecimal monthInterestPayactualSum;// 实还利息总额
	private BigDecimal actualMonthFeeServiceSum; // 实还分期服务费总额
	private BigDecimal monthPenaltyActualSum;    // 实还违约金总额
	private BigDecimal monthInterestPunishActualSum;// 实还罚息总额
	private BigDecimal actualMonthLateFeeSum;  // 实还滞纳金总额
	private String dictOffsetType;// 冲抵方式
	private String dictOffsetTypeLabel;
	
	private String enumOne;	
	private String enumTwo;
	private String enumThree;
	private String store;
	private String storeName;
	private String storeId;
	
	private String model;
	
	private String modelLabel;
	
	private String idVals;
	
	public String getPaybackMonthId() {
		return paybackMonthId;
	}
	public void setPaybackMonthId(String paybackMonthId) {
		this.paybackMonthId = paybackMonthId;
	}
	public BigDecimal getMonthPunishReductionSum() {
		return monthPunishReductionSum;
	}
	public void setMonthPunishReductionSum(BigDecimal monthPunishReductionSum) {
		this.monthPunishReductionSum = monthPunishReductionSum;
	}
	public BigDecimal getMonthPenaltyReductionSum() {
		return monthPenaltyReductionSum;
	}
	public void setMonthPenaltyReductionSum(BigDecimal monthPenaltyReductionSum) {
		this.monthPenaltyReductionSum = monthPenaltyReductionSum;
	}
	public float getMonthBeforeReductionAmount() {
		return monthBeforeReductionAmount;
	}
	public void setMonthBeforeReductionAmount(float monthBeforeReductionAmount) {
		this.monthBeforeReductionAmount = monthBeforeReductionAmount;
	}
	public String getEnumOne() {
		return enumOne;
	}
	public void setEnumOne(String enumOne) {
		this.enumOne = enumOne;
	}
	public String getEnumTwo() {
		return enumTwo;
	}
	public void setEnumTwo(String enumTwo) {
		this.enumTwo = enumTwo;
	}
	public String getOrgName() {
		return orgName;
	}
	
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public String getChargeStatusLabel() {
		return chargeStatusLabel;
	}
	public void setChargeStatusLabel(String chargeStatusLabel) {
		this.chargeStatusLabel = chargeStatusLabel;
	}
	public String getrPaybackId() {
		return rPaybackId;
	}
	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}
	public String getEnumThree() {
		return enumThree;
	}
	public void setEnumThree(String enumThree) {
		this.enumThree = enumThree;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public float getMonthPenaltyShould() {
		return monthPenaltyShould;
	}
	public void setMonthPenaltyShould(float monthPenaltyShould) {
		this.monthPenaltyShould = monthPenaltyShould;
	}
	public float getMonthInterestPunishshould() {
		return monthInterestPunishshould;
	}
	public void setMonthInterestPunishshould(float monthInterestPunishshould) {
		this.monthInterestPunishshould = monthInterestPunishshould;
	}
	public BigDecimal getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}
	public float getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(float urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	public float getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(float grantAmount) {
		this.grantAmount = grantAmount;
	}
	public Integer getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}
	public Date getContractReplayDate() {
		return contractReplayDate;
	}
	public void setContractReplayDate(Date contractReplayDate) {
		this.contractReplayDate = contractReplayDate;
	}
	public int getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
	}
	public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
	}
	public float getModifyYingpaybackBackMoney() {
		return modifyYingpaybackBackMoney;
	}
	public void setModifyYingpaybackBackMoney(float modifyYingpaybackBackMoney) {
		this.modifyYingpaybackBackMoney = modifyYingpaybackBackMoney;
	}
	public float getApplyAmountPaybac() {
		return applyAmountPaybac;
	}
	public void setApplyAmountPaybac(float applyAmountPaybac) {
		this.applyAmountPaybac = applyAmountPaybac;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public float getMonthPunishReduction() {
		return monthPunishReduction;
	}
	public void setMonthPunishReduction(float monthPunishReduction) {
		this.monthPunishReduction = monthPunishReduction;
	}
	public float getMonthPenaltyReduction() {
		return monthPenaltyReduction;
	}
	public void setMonthPenaltyReduction(float monthPenaltyReduction) {
		this.monthPenaltyReduction = monthPenaltyReduction;
	}
	
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public float getActualModifyMoney() {
		return actualModifyMoney;
	}
	public void setActualModifyMoney(float actualModifyMoney) {
		this.actualModifyMoney = actualModifyMoney;
	}
	public float getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(float monthMoney) {
		this.monthMoney = monthMoney;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public float getPaybackBuleAmont() {
		return paybackBuleAmont;
	}
	public void setPaybackBuleAmont(float paybackBuleAmont) {
		this.paybackBuleAmont = paybackBuleAmont;
	}
	public String getDictPayStatusSF() {
		return dictPayStatusSF;
	}
	public void setDictPayStatusSF(String dictPayStatusSF) {
		this.dictPayStatusSF = dictPayStatusSF;
	}
	public String getPaybackBackMoney() {
		return paybackBackMoney;
	}
	public void setPaybackBackMoney(String paybackBackMoney) {
		this.paybackBackMoney = paybackBackMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getMonthBeforefinishMoney() {
		return monthBeforefinishMoney;
	}
	public void setMonthBeforefinishMoney(float monthBeforefinishMoney) {
		this.monthBeforefinishMoney = monthBeforefinishMoney;
	}
	public float getApplyAmountPayback() {
		return applyAmountPayback;
	}
	public void setApplyAmountPayback(float applyAmountPayback) {
		this.applyAmountPayback = applyAmountPayback;
	}
	public float getApplyAmountViolate() {
		return applyAmountViolate;
	}
	public void setApplyAmountViolate(float applyAmountViolate) {
		this.applyAmountViolate = applyAmountViolate;
	}
	public float getApplyAmountPunish() {
		return applyAmountPunish;
	}
	public void setApplyAmountPunish(float applyAmountPunish) {
		this.applyAmountPunish = applyAmountPunish;
	}
	public float getApplydictPayMoney() {
		return applydictPayMoney;
	}
	public void setApplydictPayMoney(float applydictPayMoney) {
		this.applydictPayMoney = applydictPayMoney;
	}
	public float getPaybackMonthMoney() {
		return paybackMonthMoney;
	}
	public void setPaybackMonthMoney(float paybackMonthMoney) {
		this.paybackMonthMoney = paybackMonthMoney;
	}
	public float getMonthCapitalPayactual() {
		return monthCapitalPayactual;
	}
	public void setMonthCapitalPayactual(float monthCapitalPayactual) {
		this.monthCapitalPayactual = monthCapitalPayactual;
	}
	public float getMonthInterestPayactual() {
		return monthInterestPayactual;
	}
	public void setMonthInterestPayactual(float monthInterestPayactual) {
		this.monthInterestPayactual = monthInterestPayactual;
	}
	public float getMonthPayMoney() {
		return monthPayMoney;
	}
	public void setMonthPayMoney(float monthPayMoney) {
		this.monthPayMoney = monthPayMoney;
	}
	public float getMonthInterestBackshould() {
		return monthInterestBackshould;
	}
	public void setMonthInterestBackshould(float monthInterestBackshould) {
		this.monthInterestBackshould = monthInterestBackshould;
	}
	public float getMonthPenaltyActual() {
		return monthPenaltyActual;
	}
	public void setMonthPenaltyActual(float monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}
	public float getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}
	public void setMonthInterestPunishactual(float monthInterestPunishactual) {
		this.monthInterestPunishactual = monthInterestPunishactual;
	}
	public BigDecimal getHisOverpaybackMonthMoney() {
		return hisOverpaybackMonthMoney;
	}
	public void setHisOverpaybackMonthMoney(BigDecimal hisOverpaybackMonthMoney) {
		this.hisOverpaybackMonthMoney = hisOverpaybackMonthMoney;
	}
	public float getNotYetLiquidatedPenalty() {
		return notYetLiquidatedPenalty;
	}
	public void setNotYetLiquidatedPenalty(float notYetLiquidatedPenalty) {
		this.notYetLiquidatedPenalty = notYetLiquidatedPenalty;
	}
	public BigDecimal getHisOverLiquidatedPenalty() {
		return hisOverLiquidatedPenalty;
	}
	public void setHisOverLiquidatedPenalty(BigDecimal hisOverLiquidatedPenalty) {
		this.hisOverLiquidatedPenalty = hisOverLiquidatedPenalty;
	}
	public float getMonthModifyMoney() {
		return monthModifyMoney;
	}
	public void setMonthModifyMoney(float monthModifyMoney) {
		this.monthModifyMoney = monthModifyMoney;
	}
	public float getOneTimeMonthpaybackBackMoney() {
		return oneTimeMonthpaybackBackMoney;
	}
	public void setOneTimeMonthpaybackBackMoney(float oneTimeMonthpaybackBackMoney) {
		this.oneTimeMonthpaybackBackMoney = oneTimeMonthpaybackBackMoney;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}
	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
	}
	public String getLoanIsPhoneLabel() {
		return loanIsPhoneLabel;
	}
	public void setLoanIsPhoneLabel(String loanIsPhoneLabel) {
		this.loanIsPhoneLabel = loanIsPhoneLabel;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public BigDecimal getMonthLateFeeReductionSum() {
		return monthLateFeeReductionSum;
	}
	public void setMonthLateFeeReductionSum(BigDecimal monthLateFeeReductionSum) {
		this.monthLateFeeReductionSum = monthLateFeeReductionSum;
	}
	public BigDecimal getMonthCapitalPayactualSum() {
		return monthCapitalPayactualSum;
	}
	public void setMonthCapitalPayactualSum(BigDecimal monthCapitalPayactualSum) {
		this.monthCapitalPayactualSum = monthCapitalPayactualSum;
	}
	public BigDecimal getMonthInterestPayactualSum() {
		return monthInterestPayactualSum;
	}
	public void setMonthInterestPayactualSum(BigDecimal monthInterestPayactualSum) {
		this.monthInterestPayactualSum = monthInterestPayactualSum;
	}
	public BigDecimal getActualMonthFeeServiceSum() {
		return actualMonthFeeServiceSum;
	}
	public void setActualMonthFeeServiceSum(BigDecimal actualMonthFeeServiceSum) {
		this.actualMonthFeeServiceSum = actualMonthFeeServiceSum;
	}
	public BigDecimal getMonthPenaltyActualSum() {
		return monthPenaltyActualSum;
	}
	public void setMonthPenaltyActualSum(BigDecimal monthPenaltyActualSum) {
		this.monthPenaltyActualSum = monthPenaltyActualSum;
	}
	public BigDecimal getMonthInterestPunishActualSum() {
		return monthInterestPunishActualSum;
	}
	public void setMonthInterestPunishActualSum(
			BigDecimal monthInterestPunishActualSum) {
		this.monthInterestPunishActualSum = monthInterestPunishActualSum;
	}
	public BigDecimal getActualMonthLateFeeSum() {
		return actualMonthLateFeeSum;
	}
	public void setActualMonthLateFeeSum(BigDecimal actualMonthLateFeeSum) {
		this.actualMonthLateFeeSum = actualMonthLateFeeSum;
	}
	public BigDecimal getMonthPenaltyPunishReductionSum() {
		return monthPenaltyPunishReductionSum;
	}
	public void setMonthPenaltyPunishReductionSum(
			BigDecimal monthPenaltyPunishReductionSum) {
		this.monthPenaltyPunishReductionSum = monthPenaltyPunishReductionSum;
	}
	public String getReductionBy() {
		return reductionBy;
	}
	public void setReductionBy(String reductionBy) {
		this.reductionBy = reductionBy;
	}
	public String getDictOffsetType() {
		return dictOffsetType;
	}
	public void setDictOffsetType(String dictOffsetType) {
		this.dictOffsetType = dictOffsetType;
	}
	public String getDictOffsetTypeLabel() {
		return dictOffsetTypeLabel;
	}
	public void setDictOffsetTypeLabel(String dictOffsetTypeLabel) {
		this.dictOffsetTypeLabel = dictOffsetTypeLabel;
	}
	public float getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}
	public void setUrgeDecuteMoeny(float urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}
	public String getReturnLogo() {
		return returnLogo;
	}
	public void setReturnLogo(String returnLogo) {
		this.returnLogo = returnLogo;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModelLabel() {
		return modelLabel;
	}
	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}
	public String getIdVals() {
		return idVals;
	}
	public void setIdVals(String idVals) {
		this.idVals = idVals;
	}
	
	public String[] getIdList(){
		if(this.idVals!=null && "".equals(this.idVals)==false)
			return this.idVals.split(",");
		else
			return null;
	}
}
