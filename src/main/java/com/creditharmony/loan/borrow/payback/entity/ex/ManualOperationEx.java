package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 
 * @Class Name ManualOperation
 * @author 李强
 * @Create In 2015年12月17日
 */
@SuppressWarnings("serial")
public class ManualOperationEx extends DataEntity<ManualOperationEx>  {
	
	private String id;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;	
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;	
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String orgName;	
	// 期供金额、账单金额
	@ExcelField(title = "期供金额", type = 0, align = 2, sort = 40)
	private BigDecimal contractMonthRepayAmount;	
	// 当期已还金额
	@ExcelField(title = "当期已还金额", type = 0, align = 2, sort = 50)
	private BigDecimal hisOverpaybackMonthMoney;	
	// 当期未还金额
	@ExcelField(title = "当期未还金额", type = 0, align = 2, sort = 60)
	private BigDecimal notOverpaybackMonthMoney;	
	// 银行
	@ExcelField(title = "银行", type = 0, align = 2, sort = 70,dictType="jk_open_bank")
	private String applyBankName;
	//银行名称
	private String applyBankNameLabel;
	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 140,dictType="jk_loan_status")
	private String dictLoanStatus;
	//借款状态名称
	private String dictLoanStatusLabel;
	// 蓝补金额
	@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 130)
	private BigDecimal paybackBuleAmount;	
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 100,dictType="jk_channel_flag")
	private String loanMark;
	//标识名称
	private String loanMarkLabel;
	// 划扣平台
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 110,dictType="jk_deduct_plat")
	private String dictDealType;
	// 合同版本号
	private String contractVersion;
	//划扣平台名称
	private String dictDealTypeLabel;
	// 减免违约金罚息总额
	private BigDecimal penaltyPunishReductionSum;
	// 实还违约金罚息总额
	private BigDecimal penaltyPunishActualSum;
	// 未还滞纳金及罚息
	private BigDecimal notPenaltyPunishShould;
	
	// 未还滞纳金及罚息
	private BigDecimal notPenaltyPunishShouldSum;
	
	// 实还本金
	private BigDecimal monthCapitalPayactual;
	// 实还利息
	private BigDecimal monthInterestPayactual;		
	// 应还本金
	private BigDecimal monthPayAmount;		
	// 应还利息
	private BigDecimal monthInterestBackshould;	
	// 当前第几期
	private int paybackCurrentMonth;	
	// 账单日
	private Date payBack;
	// 产品类型
	private String productType;	
	// 提前结清一次性金额
	private BigDecimal monthBeforeFinishAmount;	
	// 应还违约金
	private BigDecimal monthPenaltyShould;		
	// 应还罚息
	private BigDecimal monthInterestPunishshould;	
	// 实还罚息
	private BigDecimal monthInterestPunishactual;	
	// 实还违约金
	private BigDecimal monthPenaltyActual;	
	// 当期逾期天数
	private String monthOverdueDays;	
	// 最终期供状态
	private String dictMonthStatus;	
	// 最终期供状态名称
	private String dictMonthStatusLabel;	
	// 应还违约金及罚息
	private BigDecimal interestPenaltyPunishShould;	
	// 减免违约金
	private BigDecimal monthPenaltyReduction;
	// 减免罚息
	private BigDecimal monthPunishReduction;
	
	// 分期服务费   
	private BigDecimal monthFeeService;
	// 分期服务费实还金额 
	private BigDecimal actualmonthFeeService;
	// 滞纳金
	private BigDecimal monthLateFee;
	// 实还滞纳金
	private BigDecimal actualMonthLateFee;
	// 减免滞纳金
	private BigDecimal monthLateFeeReduction;
	// 证件号码
	private String customerCertNum;	
	// 放款金额
	private BigDecimal grantAmount;		
	// 还款状态
	private String dictPayStatus;
	// 还款状态名称
		private String dictPayStatusLabel;
	// 已还期供总额
	private BigDecimal sunAmount;
	// 批借期限
	private String contractMonths;
	// 期数
	private String months;
	// 期供id
	private String monthId;	
	private String chargeId;
	
	private String enumOne;
	private String enumTwo;
	private String enumThree;
	// 置顶
	private int bankTopFlag;
	
	// 是否逾期
	private String isOverdue;
	
	public String getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
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
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getEnumThree() {
		return enumThree;
	}
	public void setEnumThree(String enumThree) {
		this.enumThree = enumThree;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
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
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public BigDecimal getSunAmount() {
		return sunAmount;
	}
	
	public void setSunAmount(BigDecimal sunAmount) {
		this.sunAmount = sunAmount;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public int getBankTopFlag() {
		return bankTopFlag;
	}
	public void setBankTopFlag(int bankTopFlag) {
		this.bankTopFlag = bankTopFlag;
	}
	public int getPaybackCurrentMonth() {
		return paybackCurrentMonth;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public BigDecimal getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public void setPaybackCurrentMonth(int paybackCurrentMonth) {
		this.paybackCurrentMonth = paybackCurrentMonth;
	}
	public String getCustomerName() {
		return customerName;
	}
	
	public Date getPayBack() {
		return payBack;
	}
	public void setPayBack(Date payBack) {
		this.payBack = payBack;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getMonthBeforeFinishAmount() {
		return monthBeforeFinishAmount;
	}
	public void setMonthBeforeFinishAmount(BigDecimal monthBeforeFinishAmount) {
		this.monthBeforeFinishAmount = monthBeforeFinishAmount;
	}
	public BigDecimal getMonthPenaltyShould() {
		return monthPenaltyShould;
	}
	public void setMonthPenaltyShould(BigDecimal monthPenaltyShould) {
		this.monthPenaltyShould = monthPenaltyShould;
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
	public BigDecimal getMonthPenaltyActual() {
		return monthPenaltyActual;
	}
	public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}
	public String getMonthOverdueDays() {
		return monthOverdueDays;
	}
	public void setMonthOverdueDays(String monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
	}
	public String getDictMonthStatus() {
		return dictMonthStatus;
	}
	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}
	public BigDecimal getInterestPenaltyPunishShould() {
		return interestPenaltyPunishShould;
	}
	public void setInterestPenaltyPunishShould(
			BigDecimal interestPenaltyPunishShould) {
		this.interestPenaltyPunishShould = interestPenaltyPunishShould;
	}
	public BigDecimal getNotPenaltyPunishShould() {
		return notPenaltyPunishShould;
	}
	public void setNotPenaltyPunishShould(BigDecimal notPenaltyPunishShould) {
		this.notPenaltyPunishShould = notPenaltyPunishShould;
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
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}
	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}
	public BigDecimal getHisOverpaybackMonthMoney() {
		return hisOverpaybackMonthMoney;
	}
	public void setHisOverpaybackMonthMoney(BigDecimal hisOverpaybackMonthMoney) {
		this.hisOverpaybackMonthMoney = hisOverpaybackMonthMoney;
	}
	public BigDecimal getNotOverpaybackMonthMoney() {
		return notOverpaybackMonthMoney;
	}
	public void setNotOverpaybackMonthMoney(BigDecimal notOverpaybackMonthMoney) {
		this.notOverpaybackMonthMoney = notOverpaybackMonthMoney;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}
	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}
	public String getLoanMark() {
		return loanMark;
	}
	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public BigDecimal getMonthPenaltyReduction() {
		return monthPenaltyReduction;
	}
	public void setMonthPenaltyReduction(BigDecimal monthPenaltyReduction) {
		this.monthPenaltyReduction = monthPenaltyReduction;
	}
	public BigDecimal getMonthPunishReduction() {
		return monthPunishReduction;
	}
	public void setMonthPunishReduction(BigDecimal monthPunishReduction) {
		this.monthPunishReduction = monthPunishReduction;
	}
	public BigDecimal getPenaltyPunishReductionSum() {
		return penaltyPunishReductionSum;
	}
	public void setPenaltyPunishReductionSum(BigDecimal penaltyPunishReductionSum) {
		this.penaltyPunishReductionSum = penaltyPunishReductionSum;
	}
	public BigDecimal getPenaltyPunishActualSum() {
		return penaltyPunishActualSum;
	}
	public void setPenaltyPunishActualSum(BigDecimal penaltyPunishActualSum) {
		this.penaltyPunishActualSum = penaltyPunishActualSum;
	}
	public String getApplyBankNameLabel() {
		return applyBankNameLabel;
	}
	public void setApplyBankNameLabel(String applyBankNameLabel) {
		this.applyBankNameLabel = applyBankNameLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getLoanMarkLabel() {
		return loanMarkLabel;
	}
	public void setLoanMarkLabel(String loanMarkLabel) {
		this.loanMarkLabel = loanMarkLabel;
	}
	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}
	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public String getDictMonthStatusLabel() {
		return dictMonthStatusLabel;
	}
	public void setDictMonthStatusLabel(String dictMonthStatusLabel) {
		this.dictMonthStatusLabel = dictMonthStatusLabel;
	}
	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}
	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}
	public BigDecimal getActualmonthFeeService() {
		return actualmonthFeeService;
	}
	public void setActualmonthFeeService(BigDecimal actualmonthFeeService) {
		this.actualmonthFeeService = actualmonthFeeService;
	}
	public BigDecimal getMonthLateFee() {
		return monthLateFee;
	}
	public void setMonthLateFee(BigDecimal monthLateFee) {
		this.monthLateFee = monthLateFee;
	}
	public BigDecimal getActualMonthLateFee() {
		return actualMonthLateFee;
	}
	public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
		this.actualMonthLateFee = actualMonthLateFee;
	}
	public BigDecimal getMonthLateFeeReduction() {
		return monthLateFeeReduction;
	}
	public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
		this.monthLateFeeReduction = monthLateFeeReduction;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public BigDecimal getNotPenaltyPunishShouldSum() {
		return notPenaltyPunishShouldSum;
	}
	public void setNotPenaltyPunishShouldSum(BigDecimal notPenaltyPunishShouldSum) {
		this.notPenaltyPunishShouldSum = notPenaltyPunishShouldSum;
	}
}
