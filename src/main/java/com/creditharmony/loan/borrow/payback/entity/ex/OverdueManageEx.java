package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 
 * @Class Name OverdueManageEx
 * @author 李强
 * @Create In 2015年12月14日
 */
@SuppressWarnings("serial")
public class OverdueManageEx extends DataEntity<OverdueManageEx> {

	private String id;
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 10, groups = { 2 })
	private String orgName;	 // 门店名称	
	@ExcelField(title = " 客户姓名", type = 0, align = 2, sort = 20, groups = { 2 })
	private String customerName;					// 客户姓名
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 30, groups = { 2 })
	private String contractCode;					// 合同编号
	private String bankName;						// 银行名称
	@ExcelField(title = "银行", type = 0, align = 2, sort = 40)
	private String bankNameLabel;						// 银行名称名字
	@ExcelField(title = "逾期日期", type = 0, align = 2, sort = 50, groups = { 2 })
	private Date monthPayDay; 						// 逾期日期
	@ExcelField(title = "期供金额", type = 0, align = 2, sort = 60, groups = { 2 })
	private BigDecimal contractMonthRepayAmount;	// 期供金额

	private BigDecimal contractMonthRepayAmountLate;    // 逾期期供金额
	@ExcelField(title = "逾期天数", type = 0, align = 2, sort = 70, groups = { 2 })
	private Integer monthOverdueDays; 					// 逾期天数
	@ExcelField(title = "违约金(滞纳金)及罚息总额", type = 0, align = 2, sort = 80, groups = { 2 })
	private BigDecimal penaltyAndShould;			// 违约金及罚息总额
	@ExcelField(title = "实还期供金额", type = 0, align = 2, sort = 90, groups = { 2 })
	private BigDecimal alsocontractMonthRepay;		// 实还期供金额
	@ExcelField(title = "已还违约金(滞纳金)及罚息金额", type = 0, align = 2, sort = 100, groups = { 2 })
	private BigDecimal alsoPenaltyInterest;			// 实还违约金及罚息
	@ExcelField(title = "未还违约金(滞纳金)及罚息金额", type = 0, align = 2, sort = 110, groups = { 2 })
	private BigDecimal noPenaltyInterest;			// 未还违约金罚息金额
	@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 120, groups = { 2 })
	private BigDecimal paybackBuleAmount;			// 蓝补金额
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 130)
	private String dictLoanStatus;					// 借款状态
	private String dictLoanStatusLabel;					// 借款状态名称
	@ExcelField(title = "期供状态", type = 0, align = 2, sort = 140, groups = { 2 })
	private String dictMonthStatus;					// 期供状态
	private String dictMonthStatusLabel;					// 期供状态名称
	@ExcelField(title = "减免人", type = 0, align = 2, sort = 150, groups = { 2 })
	private String reductionBy;	 // 减免人
	@ExcelField(title = "减免天数", type = 0, align = 2, sort = 160, groups = { 2 })
	private Integer monthReductionDay;					// 减免天数
	@ExcelField(title = "减免金额", type = 0, align = 2, sort = 170, groups = { 2 })
	private BigDecimal reductionAmount;				// 减免金额
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 180, groups = { 2 })
	private String customerTelesalesFlag;			// 是否电销
	private String customerTelesalesFlagLabel;			// 是否电销名称
	@ExcelField(title = "标识", type = 0, align = 2, sort = 190, groups = { 2 })
	private String loanMark;						// 标识
	private String loanMarkLabel;						// 标识名称
	private String contractVersion;             // 合同版本号
	
	private BigDecimal monthPayAmount; 				// 应还本金
	private BigDecimal monthInterestBackshould;		// 应还利息
	private BigDecimal monthPenaltyShould; 			// 应还违约金
	private BigDecimal monthInterestPunishshould;	// 应还罚息
	private BigDecimal monthCapitalPayactual;		// 实还本金
	private BigDecimal monthInterestPayactual;		// 实还利息
	private BigDecimal monthPenaltyActual; 			// 实还违约金
	private BigDecimal monthInterestPunishactual;	// 实还罚息
	private BigDecimal monthPenaltyReduction;		// 减免违约金
	private BigDecimal monthPunishReduction;		// 减免罚息
	private BigDecimal monthLateFee;				// 应还滞纳金
	private BigDecimal actualMonthLateFee;			// 实还滞纳金
	private BigDecimal monthLateFeeReduction;		// 减免滞纳金
	private BigDecimal monthFeeService;				//应还分期服务费
	private BigDecimal actualMonthFeeService;		//实还分期服务
	private String orgId;							// 门店id
	private String orgIdyc;						// 门店名称隐藏
	private BigDecimal guaAmount;					// 挂账金额
	private String dateString;						// 替换逾期日期
	private String paybackDay;						// 还款账单日
	private String dictSourceType;					// 来源系统
	private String loanTeamOrgId;					// 团队组织机构ID
	private Date beginDate;
	private Date endDate;
	private String rPaybackId;						// 还款主表ID
	private String bankAccount;						// 银行账号
	private String bankBranch;						// 银行开户行
	private String customerCertNum;					// 借款人身份证号
	private String loanCode;						// 借款编码
	private String modifyBy;						// 修改人
	private String enumOne;
	private String enumTwo;
	private String repaymentDate;					// 页面搜索框还款日
	
	private String bankyc;                           // 银行 隐藏id
	private String BankId;                           // 银行 
	private Integer months;                          // 期数
	private Integer monthOverdueDayMax; 	             // 用于逾期管理列表中批量减免查询小于指定逾期天数的所有数据
	private String reductionReson;                   // 减免原因
	
	private String model; // 模式
	private String modelLabel; 
	
	private Integer limit;
	private Integer offset;
	private Integer cnt;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoanTeamOrgId() {
		return loanTeamOrgId;
	}
	public void setLoanTeamOrgId(String loanTeamOrgId) {
		this.loanTeamOrgId = loanTeamOrgId;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public String getPaybackDay() {
		return paybackDay;
	}
	public void setPaybackDay(String paybackDay) {
		this.paybackDay = paybackDay;
	}
	public String getReductionBy() {
		return reductionBy;
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
	public void setReductionBy(String reductionBy) {
		this.reductionBy = reductionBy;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public String getOrgName() {
		return orgName;
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
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public Integer getMonthOverdueDays() {
		return monthOverdueDays;
	}
	public void setMonthOverdueDays(Integer monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
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
	public BigDecimal getMonthPenaltyActual() {
		return monthPenaltyActual;
	}
	public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}
	public BigDecimal getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}
	public void setMonthInterestPunishactual(BigDecimal monthInterestPunishactual) {
		this.monthInterestPunishactual = monthInterestPunishactual;
	}
	public String getDictMonthStatus() {
		return dictMonthStatus;
	}
	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
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
	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}
	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}
	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}
	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getLoanMark() {
		return loanMark;
	}
	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}
	
	public Integer getMonthReductionDay() {
		return monthReductionDay;
	}
	public void setMonthReductionDay(Integer monthReductionDay) {
		this.monthReductionDay = monthReductionDay;
	}
	public BigDecimal getPenaltyAndShould() {
		return penaltyAndShould;
	}
	public void setPenaltyAndShould(BigDecimal penaltyAndShould) {
		this.penaltyAndShould = penaltyAndShould;
	}
	public BigDecimal getAlsocontractMonthRepay() {
		return alsocontractMonthRepay;
	}
	public void setAlsocontractMonthRepay(BigDecimal alsocontractMonthRepay) {
		this.alsocontractMonthRepay = alsocontractMonthRepay;
	}
	public BigDecimal getAlsoPenaltyInterest() {
		return alsoPenaltyInterest;
	}
	public void setAlsoPenaltyInterest(BigDecimal alsoPenaltyInterest) {
		this.alsoPenaltyInterest = alsoPenaltyInterest;
	}
	public BigDecimal getGuaAmount() {
		return guaAmount;
	}
	public void setGuaAmount(BigDecimal guaAmount) {
		this.guaAmount = guaAmount;
	}
	public BigDecimal getReductionAmount() {
		return reductionAmount;
	}
	public void setReductionAmount(BigDecimal reductionAmount) {
		this.reductionAmount = reductionAmount;
	}
	public String getrPaybackId() {
		return rPaybackId;
	}
	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgIdyc() {
		return orgIdyc;
	}
	public void setOrgIdyc(String orgIdyc) {
		this.orgIdyc = orgIdyc;
	}
	public BigDecimal getNoPenaltyInterest() {
		return noPenaltyInterest;
	}
	public void setNoPenaltyInterest(BigDecimal noPenaltyInterest) {
		this.noPenaltyInterest = noPenaltyInterest;
	}
	public String getBankyc() {
		return bankyc;
	}
	public void setBankyc(String bankyc) {
		this.bankyc = bankyc;
	}
	public String getBankId() {
		return BankId;
	}
	public void setBankId(String bankId) {
		BankId = bankId;
	}

	public String getBankNameLabel() {
		return bankNameLabel;
	}
	public void setBankNameLabel(String bankNameLabel) {
		this.bankNameLabel = bankNameLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getDictMonthStatusLabel() {
		return dictMonthStatusLabel;
	}
	public void setDictMonthStatusLabel(String dictMonthStatusLabel) {
		this.dictMonthStatusLabel = dictMonthStatusLabel;
	}
	public String getCustomerTelesalesFlagLabel() {
		return customerTelesalesFlagLabel;
	}
	public void setCustomerTelesalesFlagLabel(String customerTelesalesFlagLabel) {
		this.customerTelesalesFlagLabel = customerTelesalesFlagLabel;
	}
	public String getLoanMarkLabel() {
		return loanMarkLabel;
	}
	public void setLoanMarkLabel(String loanMarkLabel) {
		this.loanMarkLabel = loanMarkLabel;
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
	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}
	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}
	public BigDecimal getActualMonthFeeService() {
		return actualMonthFeeService;
	}
	public void setActualMonthFeeService(BigDecimal actualMonthFeeService) {
		this.actualMonthFeeService = actualMonthFeeService;
	}
	public BigDecimal getContractMonthRepayAmountLate() {
		return contractMonthRepayAmountLate;
	}
	public void setContractMonthRepayAmountLate(
			BigDecimal contractMonthRepayAmountLate) {
		this.contractMonthRepayAmountLate = contractMonthRepayAmountLate;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public Integer getMonthOverdueDayMax() {
		return monthOverdueDayMax;
	}
	public void setMonthOverdueDayMax(Integer monthOverdueDayMax) {
		this.monthOverdueDayMax = monthOverdueDayMax;
	}
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	public String getReductionReson() {
		return reductionReson;
	}
	public void setReductionReson(String reductionReson) {
		this.reductionReson = reductionReson;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
    
    
	
}
