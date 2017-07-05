/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.Payback.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 还款主表
 * 
 * @Class Name PayBack
 * @author zhangfeng
 * @Create In 2015年12月4日
 */
@SuppressWarnings("serial")
public class Payback extends DataEntity<Payback> {
	
	// 结清确认，复选框选中Ids
	private String ids;
	 // 设置查询结清列表页面中参数
	public String DICT_PAY_STATUS;
	// 设置查询结清列表页面中合同版本号参数
	public String CONTRACT_VERSION;
	// 置顶静态
	private int TOP_FLAG;

	// 冲抵期供查询静态变量
	private String REPAYMENT_FLAG;
	private String OVERDUE_FLAG;

	// 合同编号
	private String contractCode;
	// 客户编码
	private String customerCode;
	// 当前第几期
	private int paybackCurrentMonth;
	// 期供
	private BigDecimal paybackMonthAmount;
	// 蓝补金额
	private BigDecimal paybackBuleAmount;
	// 是否有效
	private String effectiveFlag;
	// 还款状态
	private String dictPayStatus;
	// 还款状态名称
	private String dictPayStatusLabel;
	// 还款状态(列表查询使用,因为列表查询是 in + $ 查询)
	private String queryDictPayStatus;
	// 账单日
	private Integer paybackDay;
	// 账单日查询
	private String paybackDayNum;
	// 最长逾期天数
	private int paybackMaxOverduedays;
	// 返款金额
	private BigDecimal paybackBackAmount;
	// 减免人
	private String remissionBy;
	// 合同表
	private Contract contract;
	// 客户信息表
	private LoanCustomer loanCustomer;
	// 借款信息表
	private LoanInfo loanInfo;
	// 借款账户信息表
	private LoanBank loanBank;
	// 期供表
	private PaybackMonth paybackMonth;
	// 银行账款信息表
	private PaybackTransferInfo paybackTransferInfo;
	// 还款申请表
	private PaybackApply paybackApply;
	// 放款表
	private LoanGrant loanGrant;
	// 催收服务费
	private UrgeServicesMoney urgeServicesMoney;
	// 产品表
	private JkProducts jkProducts;
	// 冲抵申请表
	private PaybackCharge paybackCharge;
	// POS机刷卡查账从表
	private PosCardInfo posCardInfo;
	//催收管辖
	private String urgeManage;
	//委托充值状态
	private String trustRecharge;
	//借款状态标签
	private String dictLoanStatusLabel;
	//查询条件的开始时间
	private Date beginDate;
	//查询条件的结束时间
	private Date endDate;
	// 模式
	private String model;
	// 模式名称
	private String modelLabel;
    // 数据权限控制
	private String queryRight;
	// 单日限额总计
	private long sumDayLimit;
	
	private Date SettlementDate;
	
	//---结清确认页面字段 ---
	// 是否结清
	private String isConfirm;
	// 结清审核意见
	private String returnReason;
	// 结清金额
	private BigDecimal settleMoney;
	// 中金划扣次数
	private String  zjcnt;

	private BigDecimal publishStart; // 未还违约金罚息区间-开始
	private BigDecimal publishEnd;   // 未还违约金罚息区间-终了
	
	//逾期等级
	private String overdueLevel;
	//逾期天数
	private int overdueDays;
	
	// 提前结清token
	private String finishTokenId;
	private String finishToken;
	
	// applyToken
	private String applyTokenId;
	private String applyToken;
	
	private String[] payStatus;
	private String transAmountFor;
	private String contractCodeLast;
	private BigDecimal paybackBuleAmountLast;
	private BigDecimal transmitBuleAmount;
	private String bankSigningPlatform;
	private String overdueCount;
	public Payback(){
		this.setTOP_FLAG(1);
		this.setREPAYMENT_FLAG("2");
		this.setOVERDUE_FLAG("3");
		this.setDICT_PAY_STATUS("5");
		this.setCONTRACT_VERSION("4");
	}
	
	private Integer limit;
	private Integer offset;
	private Integer cnt;
	
	// 发起还款申请的类型 1 电催 2 电销
	private String applyType;
	
	// 电销标志
	private String phoneSaleSign;

	public String getApplyTokenId() {
		return applyTokenId;
	}

	public void setApplyTokenId(String applyTokenId) {
		this.applyTokenId = applyTokenId;
	}

	public String getApplyToken() {
		return applyToken;
	}

	public void setApplyToken(String applyToken) {
		this.applyToken = applyToken;
	}

	public String getFinishTokenId() {
		return finishTokenId;
	}

	public void setFinishTokenId(String finishTokenId) {
		this.finishTokenId = finishTokenId;
	}

	public String getFinishToken() {
		return finishToken;
	}

	public void setFinishToken(String finishToken) {
		this.finishToken = finishToken;
	}

	public BigDecimal getSettleMoney() {
		return settleMoney;
	}
	public void setSettleMoney(BigDecimal settleMoney) {
		this.settleMoney = settleMoney;
	}
	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getUrgeManage() {
		return urgeManage;
	}

	public void setUrgeManage(String urgeManage) {
		this.urgeManage = urgeManage;
	}

	public PosCardInfo getPosCardInfo() {
		return posCardInfo;
	}

	public void setPosCardInfo(PosCardInfo posCardInfo) {
		this.posCardInfo = posCardInfo;
	}

	public JkProducts getJkProducts() {
		return jkProducts;
	}

	public void setJkProducts(JkProducts jkProducts) {
		this.jkProducts = jkProducts;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public int getPaybackCurrentMonth() {
		return paybackCurrentMonth;
	}

	public void setPaybackCurrentMonth(int paybackCurrentMonth) {
		this.paybackCurrentMonth = paybackCurrentMonth;
	}

	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}

	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}

	public String getPaybackDayNum() {
		return paybackDayNum;
	}

	public void setPaybackDayNum(String paybackDayNum) {
		this.paybackDayNum = paybackDayNum;
	}

	public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public String getDictPayStatus() {
		return dictPayStatus;
	}

	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getQueryDictPayStatus() {
		return queryDictPayStatus;
	}
	public void setQueryDictPayStatus(String queryDictPayStatus) {
		this.queryDictPayStatus = queryDictPayStatus;
	}
	public Integer getPaybackDay() {
		return paybackDay;
	}

	public void setPaybackDay(Integer paybackDay) {
		this.paybackDay = paybackDay;
	}

	public int getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
	}

	public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
	}

	public BigDecimal getPaybackBackAmount() {
		return paybackBackAmount;
	}

	public void setPaybackBackAmount(BigDecimal paybackBackAmount) {
		this.paybackBackAmount = paybackBackAmount;
	}

	public String getRemissionBy() {
		return remissionBy;
	}

	public void setRemissionBy(String remissionBy) {
		this.remissionBy = remissionBy;
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

	public PaybackMonth getPaybackMonth() {
		return paybackMonth;
	}

	public void setPaybackMonth(PaybackMonth paybackMonth) {
		this.paybackMonth = paybackMonth;
	}

	public PaybackTransferInfo getPaybackTransferInfo() {
		return paybackTransferInfo;
	}

	public void setPaybackTransferInfo(PaybackTransferInfo paybackTransferInfo) {
		this.paybackTransferInfo = paybackTransferInfo;
	}

	public PaybackApply getPaybackApply() {
		return paybackApply;
	}

	public void setPaybackApply(PaybackApply paybackApply) {
		this.paybackApply = paybackApply;
	}

	public LoanGrant getLoanGrant() {
		return loanGrant;
	}

	public void setLoanGrant(LoanGrant loanGrant) {
		this.loanGrant = loanGrant;
	}

	public UrgeServicesMoney getUrgeServicesMoney() {
		return urgeServicesMoney;
	}

	public void setUrgeServicesMoney(UrgeServicesMoney urgeServicesMoney) {
		this.urgeServicesMoney = urgeServicesMoney;
	}

	public PaybackCharge getPaybackCharge() {
		return paybackCharge;
	}

	public void setPaybackCharge(PaybackCharge paybackCharge) {
		this.paybackCharge = paybackCharge;
	}
	public int getTOP_FLAG() {
		return TOP_FLAG;
	}
	public void setTOP_FLAG(int tOP_FLAG) {
		TOP_FLAG = tOP_FLAG;
	}
	public String getREPAYMENT_FLAG() {
		return REPAYMENT_FLAG;
	}
	public void setREPAYMENT_FLAG(String rEPAYMENT_FLAG) {
		REPAYMENT_FLAG = rEPAYMENT_FLAG;
	}
	public String getOVERDUE_FLAG() {
		return OVERDUE_FLAG;
	}
	public void setOVERDUE_FLAG(String oVERDUE_FLAG) {
		OVERDUE_FLAG = oVERDUE_FLAG;
	}
	public String getTrustRecharge() {
		return trustRecharge;
	}
	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
	}
    /**
     * @return the queryRight
     */
    public String getQueryRight() {
        return queryRight;
    }
    /**
     * @param queryRight the String queryRight to set
     */
    public void setQueryRight(String queryRight) {
        this.queryRight = queryRight;
    }
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public String getDICT_PAY_STATUS() {
		return DICT_PAY_STATUS;
	}
	public void setDICT_PAY_STATUS(String dICT_PAY_STATUS) {
		DICT_PAY_STATUS = dICT_PAY_STATUS;
	}
	public String getCONTRACT_VERSION() {
		return CONTRACT_VERSION;
	}
	public void setCONTRACT_VERSION(String cONTRACT_VERSION) {
		CONTRACT_VERSION = cONTRACT_VERSION;
	}
	public String[] getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String[] payStatus) {
		this.payStatus = payStatus;
	}
	public String getTransAmountFor() {
		return transAmountFor;
	}
	public void setTransAmountFor(String transAmountFor) {
		this.transAmountFor = transAmountFor;
	}
	public String getContractCodeLast() {
		return contractCodeLast;
	}
	public void setContractCodeLast(String contractCodeLast) {
		this.contractCodeLast = contractCodeLast;
	}
	public BigDecimal getPaybackBuleAmountLast() {
		return paybackBuleAmountLast;
	}
	public void setPaybackBuleAmountLast(BigDecimal paybackBuleAmountLast) {
		this.paybackBuleAmountLast = paybackBuleAmountLast;
	}
	public BigDecimal getTransmitBuleAmount() {
		return transmitBuleAmount;
	}
	public void setTransmitBuleAmount(BigDecimal transmitBuleAmount) {
		this.transmitBuleAmount = transmitBuleAmount;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
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
	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}
	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}
	public long getSumDayLimit() {
		return sumDayLimit;
	}
	public void setSumDayLimit(long sumDayLimit) {
		this.sumDayLimit = sumDayLimit;
	}

	public Date getSettlementDate() {
		return SettlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		SettlementDate = settlementDate;
	}

	public String getZjcnt() {
		return zjcnt;
	}

	public void setZjcnt(String zjcnt) {
		this.zjcnt = zjcnt;
	}

	public BigDecimal getPublishStart() {
		return publishStart;
	}

	public void setPublishStart(BigDecimal publishStart) {
		this.publishStart = publishStart;
	}

	public BigDecimal getPublishEnd() {
		return publishEnd;
	}

	public void setPublishEnd(BigDecimal publishEnd) {
		this.publishEnd = publishEnd;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getPhoneSaleSign() {
		return phoneSaleSign;
	}

	public void setPhoneSaleSign(String phoneSaleSign) {
		this.phoneSaleSign = phoneSaleSign;
	}
	public String getOverdueLevel() {
		return overdueLevel;
	}

	public void setOverdueLevel(String overdueLevel) {
		this.overdueLevel = overdueLevel;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(String overdueCount) {
		this.overdueCount = overdueCount;
	}

	
}
