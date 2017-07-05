/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackSplit.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * @Class Name PaybackSplit
 * @author zhaojinping
 * @Create In 2015年12月3日
 */
//还款拆分表
@SuppressWarnings("serial")
public class PaybackSplit extends DataEntity<PaybackSplit>{
	
	// 关联还款申请表id
	private String rId;
	// 关联ID2（拆分统计信息）
	private String rId2;
	// 批次号
	private String splitPch;
	// 关联类型（还款/催收服务费）
	private String dictRDeductType;
	// 拆分金额
	private BigDecimal splitAmount;
	// 回盘结果(0:待划扣，1:划扣失败、2:划扣成功、3处理中)
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 170)
	private String splitBackResult;
	// 回盘结果(名字)
	private String splitBackResultLabel;
	// 回盘时间、划扣日期
	private Date splitBackDate;
	// 失败原因
	@ExcelField(title = "失败原因", type = 0, align = 2, sort = 190)
	private String splitFailResult;
	// 是否批量：0是 、 1否
	private String batchFlag;
	// 划扣平台（0：富友，1：好易联，2 中金）
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 220, dictType="jk_deduct_plat")
	private String dictDealType;
	//划扣平台名称
	private String dictDealTypeLabel;
	// 处理状态（0：未处理，1：发送成功，2：发送、处理失败，3：等待平台返回结果中。）
	private String dictDealStatus;
	// 操作类型（0：待集中划扣，1：待还款划扣。）
	private String dictOptType;
	// 是否发送(0：是 1:否)
	private String isSend;
	// 时间标识
	private String timeFlag;
	// 合同表
	private Contract contract;
	// 还款主表
	private Payback payback;
	// 还款申请表
	private PaybackApply paybackApply;
	// 借款客户信息表
	private LoanCustomer loanCustomer;
	// 借款信息表    
	private LoanInfo loanInfo;
	// 借款账户信息表   
	private LoanBank loanBank;
	// 期供表
	private PaybackMonth paybackMonth;
	// 拆分父id
	private String  splitFatherId;
	// 线上 线下
	private String paybackFlag;
	// 借款编码
	private String loanCode;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 客户名称
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String orgName;
	// 开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 40,dictType="jk_open_bank")
	private String applyBankName;
	// 批借期数 
	@ExcelField(title = "批借期数", type = 0, align = 2, sort = 50)
	private int contractMonths;
	// 首期还款日
	@ExcelField(title = "首期还款期", type = 0, align = 2, sort = 60)
	private Date contractReplayDay;
    // 实还金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 70)
	private BigDecimal splitAmounts;
	// 期供
	@ExcelField(title = "期供", type = 0, align = 2, sort = 80)
	private BigDecimal paybackMonthAmount;
	// 还款状态
	@ExcelField(title = "还款状态", type = 0, align = 2, sort = 120,dictType="jk_repay_status")
	private String dictPayStatus;
	
	// 还款类型
	@ExcelField(title = "还款类型", type = 0, align = 2, sort = 130,dictType="jk_repay_type")
	private String huankType;
	// 还款日
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 160)
	private Date monthPayDay;
	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 140,dictType="jk_loan_status")
	private String dictLoanStatus;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 200,dictType="jk_channel_flag")
	private String loanMark;
	// 蓝补金额
	@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 210)
	private BigDecimal paybackBuleAmount;
	// 最长逾期 
	private int paybackMaxOverduedays;
	// 往期是否逾期
	@ExcelField(title = "往期是否逾期", type = 0, align = 2, sort = 180)
	private String whetherOverdue;
	// 实还本金
	private BigDecimal monthCapitalPayactual;
	// 实还利息
	private BigDecimal monthInterestPayactual;
	// 当期已还期供
	@ExcelField(title = "当期已还期供", type = 0, align = 2, sort = 100)
	private BigDecimal alsoPaybackMonthAmount;
	// 当期未还期供
	@ExcelField(title = "当期未还期供", type = 0, align = 2, sort = 90)
	private BigDecimal notPaybackMonthAmount;
	// 还款方式
	private String dictRepayMethod;
	// 申请还款金额
	private BigDecimal applyMoneyPayback;
	// 申请还期总额
	private BigDecimal applyAmountPayback;
	// 申请违约金总额
	private BigDecimal applyAmountViolate;
	// 申请还罚息总额
	private BigDecimal applyAmountPunish;
	// 判断是集中划扣已办或是划扣已办
	private String judge;
	// 证件号码
	private String customerCertNum;
	 // 产品类型
	private String productType;
	// 合同金额
	private BigDecimal contractAmount;
	// 期供金额
	private BigDecimal contractMonthRepayAmount;
	// 申请划扣金额
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 110)
	private BigDecimal applyDeductAmount;
	// 申请还款日期
	@ExcelField(title = "划扣日期", type = 0, align = 2, sort = 150)
	private Date createTimei;
	// 申请还款日期
	@ExcelField(title = "卡号", type = 0, align = 2, sort = 220)
	private String bankAccount;
	// 账号姓名
	private String applyAccountName;
	// 划扣账号
	private String applyDeductAccount;
	// 期供还款日期
	private Date paybackMonthMoneyDate;	
	// 来源系统
	private String dictSourceType;
	// 实还金额search1
	private BigDecimal alsoAmountOne;
	private BigDecimal alsoAmountTwo;
	private String repaymentDate;
	// 企业流水号
	private String enterpriseSerialno;
	// 还款主表ID
	private String rPaybackId;
	private Date beginDate;
	private Date endDate;
	private String enumOne;
	private String enumTwo;
	private String enumThree;
	private String enumFour;
	// 备注
	private String remark;
	// 是否委托充值
	private String trustRecharge;
	// 委托充值状态
	private String trustRechargeResult;
	// 委托充值状态
		private String trustRechargeResultLabel;
	// 委托充值状态
	private String trustRechargeFailReason;
	
	private String  failReason;
	
	// 银行code
	private String bankName;
	
	// 证件类型
	private String dictertType;
	
	// 手机号
	private String customerPhoneFirst;
	
	// 账户姓名
	private String bankAccountName;
	
	// 银行所在省
	private String bankProvince;
	
	// 银行所在市
	private String bankCity;
	
	// 开户行支行
	private String bankBranch;
	
	//总金额
    private BigDecimal  sumAmont;
    // 总数量
    private String sumNumber;
	
    private String verTime;
    
    private String counteroffer;
    private String modelLabel;
    
    private String model;
    
    private String applyReallyAmount;
    
    private int months;
    
    private String loanFlag;
    
    private String currentNotYet;
    
    private String currentAlreadyRepaid;
    
    //逾期天数
    private int overdueDays;
    
    private boolean success;
    
    // 中金划扣次数
    private String cpcnCount;
    // 中金批量签约
    private String tlSign;
    
    private String tlSignLabel;
    //累计逾期数
    private int  overCount;
    
    private String klSign;
    
    private String realAuthen;
    
    private String sumReallyAmont;
    
    private String tlCount;

    // 畅捷签约
    private String cjSign;
    
	public String getTrustRechargeResultLabel() {
		return trustRechargeResultLabel;
	}

	public void setTrustRechargeResultLabel(String trustRechargeResultLabel) {
		this.trustRechargeResultLabel = trustRechargeResultLabel;
	}

	public String getSplitBackResultLabel() {
		return splitBackResultLabel;
	}

	public void setSplitBackResultLabel(String splitBackResultLabel) {
		this.splitBackResultLabel = splitBackResultLabel;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public String getApplyDeductAccount() {
		return applyDeductAccount;
	}

	public void setApplyDeductAccount(String applyDeductAccount) {
		this.applyDeductAccount = applyDeductAccount;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getEnumThree() {
		return enumThree;
	}

	public void setEnumThree(String enumThree) {
		this.enumThree = enumThree;
	}

	public String getEnumFour() {
		return enumFour;
	}

	public BigDecimal getAlsoAmountOne() {
		return alsoAmountOne;
	}

	public void setAlsoAmountOne(BigDecimal alsoAmountOne) {
		this.alsoAmountOne = alsoAmountOne;
	}

	public BigDecimal getAlsoAmountTwo() {
		return alsoAmountTwo;
	}

	public void setAlsoAmountTwo(BigDecimal alsoAmountTwo) {
		this.alsoAmountTwo = alsoAmountTwo;
	}

	public void setEnumFour(String enumFour) {
		this.enumFour = enumFour;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDictSourceType() {
		return dictSourceType;
	}

	public String getrPaybackId() {
		return rPaybackId;
	}

	public String getHuankType() {
		return huankType;
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

	public void setHuankType(String huankType) {
		this.huankType = huankType;
	}

	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}

	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}

	private Org org;
	
	public Date getPaybackMonthMoneyDate() {
		return paybackMonthMoneyDate;
	}

	public void setPaybackMonthMoneyDate(Date paybackMonthMoneyDate) {
		this.paybackMonthMoneyDate = paybackMonthMoneyDate;
	}

	public Org getOrg() {
		return org;
	}

	public String getJudge() {
		return judge;
	}

	public BigDecimal getApplyMoneyPayback() {
		return applyMoneyPayback;
	}

	public void setApplyMoneyPayback(BigDecimal applyMoneyPayback) {
		this.applyMoneyPayback = applyMoneyPayback;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getWhetherOverdue() {
		return whetherOverdue;
	}

	public void setWhetherOverdue(String whetherOverdue) {
		this.whetherOverdue = whetherOverdue;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public LoanCustomer getLoanCustomer() {
		return loanCustomer;
	}

	public void setLoanCustomer(LoanCustomer loanCustomer) {
		this.loanCustomer = loanCustomer;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public LoanBank getLoanBank() {
		return loanBank;
	}

	public void setLoanBank(LoanBank loanBank) {
		this.loanBank = loanBank;
	}

	public Payback getPayback() {
		return payback;
	}

	public void setPayback(Payback payback) {
		this.payback = payback;
	}

	public PaybackApply getPaybackApply() {
		return paybackApply;
	}

	public void setPaybackApply(PaybackApply paybackApply) {
		this.paybackApply = paybackApply;
	}

	public PaybackMonth getPaybackMonth() {
		return paybackMonth;
	}

	public void setPaybackMonth(PaybackMonth paybackMonth) {
		this.paybackMonth = paybackMonth;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getrId2() {
		return rId2;
	}

	public void setrId2(String rId2) {
		this.rId2 = rId2;
	}

	public String getSplitPch() {
		return splitPch;
	}

	public void setSplitPch(String splitPch) {
		this.splitPch = splitPch == null ? null : splitPch.trim();
	}

	public String getDictRDeductType() {
		return dictRDeductType;
	}

	public void setDictRDeductType(String dictRDeductType) {
		this.dictRDeductType = dictRDeductType == null ? null : dictRDeductType
				.trim();
	}


	public BigDecimal getSplitAmounts() {
		return splitAmounts;
	}

	public void setSplitAmounts(BigDecimal splitAmounts) {
		this.splitAmounts = splitAmounts;
	}

	public String getSplitBackResult() {
		return splitBackResult;
	}

	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult == null ? null : splitBackResult
				.trim();
	}
	
	public String getSplitFailResult() {
		return splitFailResult;
	}

	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult == null ? null : splitFailResult
				.trim();
	}


	public Date getSplitBackDate() {
		return splitBackDate;
	}

	public void setSplitBackDate(Date splitBackDate) {
		this.splitBackDate = splitBackDate;
	}

	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType == null ? null : dictDealType.trim();
	}

	public String getDictDealStatus() {
		return dictDealStatus;
	}

	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus == null ? null : dictDealStatus
				.trim();
	}

	public String getDictOptType() {
		return dictOptType;
	}

	public void setDictOptType(String dictOptType) {
		this.dictOptType = dictOptType == null ? null : dictOptType.trim();
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend == null ? null : isSend.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy == null ? null : modifyBy.trim();
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getApplyBankName() {
		return applyBankName;
	}

	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public int getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}

	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}

	public String getDictPayStatus() {
		return dictPayStatus;
	}

	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}

	public Date getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getLoanMark() {
		return loanMark;
	}

	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}

	public int getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
	}

	public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
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

	public BigDecimal getAlsoPaybackMonthAmount() {
		return alsoPaybackMonthAmount;
	}

	public void setAlsoPaybackMonthAmount(BigDecimal alsoPaybackMonthAmount) {
		this.alsoPaybackMonthAmount = alsoPaybackMonthAmount;
	}

	public BigDecimal getNotPaybackMonthAmount() {
		return notPaybackMonthAmount;
	}

	public void setNotPaybackMonthAmount(BigDecimal notPaybackMonthAmount) {
		this.notPaybackMonthAmount = notPaybackMonthAmount;
	}

	public String getDictRepayMethod() {
		return dictRepayMethod;
	}

	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}

	public BigDecimal getApplyAmountPayback() {
		return applyAmountPayback;
	}

	public void setApplyAmountPayback(BigDecimal applyAmountPayback) {
		this.applyAmountPayback = applyAmountPayback;
	}

	public BigDecimal getApplyAmountViolate() {
		return applyAmountViolate;
	}

	public void setApplyAmountViolate(BigDecimal applyAmountViolate) {
		this.applyAmountViolate = applyAmountViolate;
	}

	public BigDecimal getApplyAmountPunish() {
		return applyAmountPunish;
	}

	public void setApplyAmountPunish(BigDecimal applyAmountPunish) {
		this.applyAmountPunish = applyAmountPunish;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public BigDecimal getApplyDeductAmount() {
		return applyDeductAmount;
	}

	public void setApplyDeductAmount(BigDecimal applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
	}

	public Date getCreateTimei() {
		return createTimei;
	}

	public void setCreateTimei(Date createTimei) {
		this.createTimei = createTimei;
	}

	public String getApplyAccountName() {
		return applyAccountName;
	}

	public void setApplyAccountName(String applyAccountName) {
		this.applyAccountName = applyAccountName;
	}

	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public String getPaybackFlag() {
		return paybackFlag;
	}

	public void setPaybackFlag(String paybackFlag) {
		this.paybackFlag = paybackFlag;
	}

	public String getSplitFatherId() {
		return splitFatherId;
	}

	public void setSplitFatherId(String splitFatherId) {
		this.splitFatherId = splitFatherId;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTrustRecharge() {
		return trustRecharge;
	}

	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getTrustRechargeResult() {
		return trustRechargeResult;
	}

	public void setTrustRechargeResult(String trustRechargeResult) {
		this.trustRechargeResult = trustRechargeResult;
	}

	public String getTrustRechargeFailReason() {
		return trustRechargeFailReason;
	}

	public void setTrustRechargeFailReason(String trustRechargeFailReason) {
		this.trustRechargeFailReason = trustRechargeFailReason;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDictertType() {
		return dictertType;
	}

	public void setDictertType(String dictertType) {
		this.dictertType = dictertType;
	}

	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}

	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}

	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}

	public BigDecimal getSumAmont() {
		return sumAmont;
	}

	public void setSumAmont(BigDecimal sumAmont) {
		this.sumAmont = sumAmont;
	}

	public String getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(String sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getVerTime() {
		return verTime;
	}

	public void setVerTime(String verTime) {
		this.verTime = verTime;
	}

	public String getCounteroffer() {
		return counteroffer;
	}

	public void setCounteroffer(String counteroffer) {
		this.counteroffer = counteroffer;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getApplyReallyAmount() {
		return applyReallyAmount;
	}

	public void setApplyReallyAmount(String applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getCurrentNotYet() {
		return currentNotYet;
	}

	public void setCurrentNotYet(String currentNotYet) {
		this.currentNotYet = currentNotYet;
	}

	public String getCurrentAlreadyRepaid() {
		return currentAlreadyRepaid;
	}

	public void setCurrentAlreadyRepaid(String currentAlreadyRepaid) {
		this.currentAlreadyRepaid = currentAlreadyRepaid;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCpcnCount() {
		return cpcnCount;
	}

	public void setCpcnCount(String cpcnCount) {
		this.cpcnCount = cpcnCount;
	}

	public String getTlSign() {
		return tlSign;
	}

	public void setTlSign(String tlSign) {
		this.tlSign = tlSign;
	}

	public String getTlSignLabel() {
		return tlSignLabel;
	}

	public void setTlSignLabel(String tlSignLabel) {
		this.tlSignLabel = tlSignLabel;
	}

	public int getOverCount() {
		return overCount;
	}

	public void setOverCount(int overCount) {
		this.overCount = overCount;
	}

	public String getKlSign() {
		return klSign;
	}

	public void setKlSign(String klSign) {
		this.klSign = klSign;
	}

	public String getRealAuthen() {
		return realAuthen;
	}

	public void setRealAuthen(String realAuthen) {
		this.realAuthen = realAuthen;
	}

	public String getSumReallyAmont() {
		return sumReallyAmont;
	}

	public void setSumReallyAmont(String sumReallyAmont) {
		this.sumReallyAmont = sumReallyAmont;
	}

	public String getTlCount() {
		return tlCount;
	}

	public void setTlCount(String tlCount) {
		this.tlCount = tlCount;
	}

	public String getCjSign() {
		return cjSign;
	}

	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}	
	
}
