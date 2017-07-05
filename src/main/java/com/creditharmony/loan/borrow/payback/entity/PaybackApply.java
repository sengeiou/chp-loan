package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 还款申请表
 * 
 * @Class Name PayBackApply
 * @author zhangfeng
 * @Create In 2015年12月4日
 */
@SuppressWarnings("serial")
public class PaybackApply extends DataEntity<PaybackApply> {
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
	SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
	public PaybackApply()
	{
		this.setTopMark(1);
	}

	// 没有置顶
	public static final int TOP_FLAG_NO = 0;
	// 置顶
	public static final int TOP_FLAG = 1;
	
	private int topMark;
	
	// 还款申请Id，用于集中划扣申请的期供ID
	private String applyId;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 还款方式
	private String dictRepayMethod;
	// 还款方式名称
	private String dictRepayMethodLabel;
	// 申请状态(还款)
	private String dictPaybackStatus;
	// 存入账号
	private String dictDepositAccount;
	// 申请金额
	private BigDecimal applyAmount;
	// 汇金转账总金额
	private BigDecimal transferAmount;
	// 划扣平台
	private String dictDealType;
	// 划扣平台
	private String dictDealTypeLabel;
	// 划扣金额
	private BigDecimal deductAmount;
	// 划扣类型(0：集中划扣 1：否)
	private String dictDeductType;
	// 还款申请日期
	private Date applyPayDay;
	private String applyPayDayStr;
	// 账号姓名
	private String applyAccountName;
	// 划扣账号
	private String applyDeductAccount;
	// 开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 40,dictType="jk_open_bank")
	private String applyBankName;
	//开户行名字
	private String applyBankNameLabel;
	// 实际到账金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 70)
	private BigDecimal applyReallyAmount;
	// 是否拆分
	private String splitFlag;
	// 是否有效
	private String effectiveFlag;
	// 还款结果
	private String dictPayResult;
	// 还款结果名称
	private String dictPayResultLabel;
	// 还款用途
	private String dictPayUse;
	// 还款用途名称
	private String dictPayUseLabel;
    // 申请还款总额
	private BigDecimal applyBackAmount;
	// 拆分父id
	private String splitFatherId;
	
	// 退回原因
	private String applyBackMes;
	// 发起人
	private String lanuchBy;
	// 发起人机构ID
	private String orgCode;
	//还款主表
	private Payback payback;
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
	// 冲抵申请表
	private PaybackCharge paybackCharge;
	
	// 产品表
	private JkProducts jkProducts;
	
	// 中间人表
	private MiddlePerson middlePerson;
	
	// 借款编码
	private String loanCode;
	// 客户姓名(查询条件)
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	// 还款状态(查询条件)
	@ExcelField(title = "还款状态", type = 0, align = 2, sort = 120,dictType="jk_repay_status")
	private String dictPayStatus;
	//还款状态名称
	private String dictPayStatusLabel;
	//标示(查询条件)
	private String mark;
	// 还款日(查询条件)
	private String paybackDay;
	// 线上 线下
	private String paybackFlag;
	// 回盘结果
	private String splitBackResult;
	// 回盘结果名称
	private String splitBackResultLabel;
	// 划扣已办实体类
	private String enumOne;
	private String enumTwo;
	private String enumThree;
	private String enumFour;

	// 产品类型
	private String productType;
	// 来源系统
	private String dictSourceType;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String orgName;
	// 批借期数
	@ExcelField(title = "批借期数", type = 0, align = 2, sort = 50)
	private int contractMonths;
	// 首期还款日
	@ExcelField(title = "首期还款期", type = 0, align = 2, sort = 60)
	private Date contractReplayDay;
	// 期供
	@ExcelField(title = "期供", type = 0, align = 2, sort = 80)
	private BigDecimal paybackMonthAmount;
	// 还款类型
	@ExcelField(title = "还款类型", type = 0, align = 2, sort = 130, dictType = "jk_repay_type")
	private String huankType;
	// 还款日
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 160)
	private Date monthPayDay;
	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 140, dictType = "jk_loan_status")
	private String dictLoanStatus;
	//借款状态
	private String dictLoanStatusLabel;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 200, dictType = "jk_channel_flag")
	private String loanMark;
	//标识名称
	private String loanMarkLabel;
	// 蓝补金额
	@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 210)
	private BigDecimal paybackBuleAmount;
	// 最长逾期 
	private int paybackMaxOverduedays;
	// 往期是否逾期
	@ExcelField(title = "往期是否逾期", type = 0, align = 2, sort = 180)
	private String whetherOverdue;
	// 当期已还期供
	@ExcelField(title = "当期已还期供", type = 0, align = 2, sort = 100)
	private BigDecimal alsoPaybackMonthAmount;
	// 当期未还期供
	@ExcelField(title = "当期未还期供", type = 0, align = 2, sort = 90)
	private BigDecimal notPaybackMonthAmount;
	// 申请划扣金额
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 110)
	private BigDecimal applyDeductAmount;
	// 申请还款日期
	@ExcelField(title = "划扣日期", type = 0, align = 2, sort = 150)
	private Date createTimei;
	// 卡号
	@ExcelField(title = "卡号", type = 0, align = 2, sort = 220)
	private String bankAccount;

	// 关联id
	private String rpaybackId;
	private Date beginDate;
	private Date endDate;
	// 实还金额search1
	private BigDecimal alsoAmountOne;
	private BigDecimal alsoAmountTwo;
	// 证件号码
	private String customerCertNum;
	// 合同金额
	private BigDecimal contractAmount;
	// 期供金额
	private BigDecimal contractMonthRepayAmount;
	
	//POS
	private String dictPosType;
	//POS机刷卡查账刷卡申请金额
	private BigDecimal deductAmountPosCard;
	//POS机刷卡还款申请日期
	private Date applyPayDayPos;
	//POS
	private String posBillCode;
	//查询当天日期用
	private String dateCre;
	//催收管辖
	private String urgeManage;
	//POS刷卡查账实际到账总额
	private BigDecimal transferAmountPosCard;
	//POS存入账户
	private String posAccount;
	//POS刷卡查账的还款申请日期
	private Date applyPayDayPosCard;
	
	// 是否委托充值
	private String trustRecharge;
	// 委托充值状态
	private String trustRechargeResult;
	// 委托充值状态名称
	private String trustRechargeResultLabel;
	// 委托充值状态
	private String trustRechargeFailReason;
	// 银行
	private String bank;
	// 银行id
	private String bankId;
	
	private String stores;
	private String storesName;
	private String storesId;
	// 还款主表
	private String paybackId;
	
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
	
	// 批处理
	private String requestId;
	
	private String isOverdue;
	
	// 失败原因
	
	private String failReason;
	
	//操作用户
	private String operateRole;
	
	/**
	 * 辅助字段，非数据库字段
	 */
	private transient boolean success;
	
	private String sumNumber;
	
	private String[] ids;
	private String sumAmont;
	
	private String sumReallyAmont;
	
	private String queryRight;
	
	// 客户经理
	private String loanManagerName;
	// 团队经理
	private String loanTeamManagerName;
	// 外访人员
	private String loanSurveyEmpName;
	// 客服
	private String loanCustomerService;
	// 批次号
	private String splitPch;
	
	private String model;
	private String modelLabel;
	// 发起申请验证标识
	private String confrimFlag;
	
	private int cpcnCount; 
	
	private String billDay;
	
	// 是否签约中金
	private String tlSign;
	private String tlSignLabel;
	
	private Integer limit;
	private Integer offset;
	private Integer cnt;
	
	private String monthId;
	
	// 自动匹配token
	private String matchingTokenId;
	private String matchingToken;
	
	// 手动匹配token
	private String manualMatchingTokenId;
	private String manualMatchingToken;
	
	// 加锁 修改时间
	private String reqTime;
	
	private String realAuthen;
	private String klSign;
	
	// 待办保存token
	private String doStoreTokenId;
	private String doStoreToken;
	
	// 通联余额不足次数
	private int tlCount;
	
	//回盘结果
	private String[] splitBackResultArray;
	
	private  String cjSign;
	
	private String dictDealTypeId;
	
	private String dictDealTypeName;
	
	//合同类型
	private String channelFlag;
	
	private String enumSix;
	
	// 电销标志
	private String phoneSaleSign;

	//创建人
	private String createBy;
	//创建人姓名
	private String createName;
	
	private String limitFlag;

	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}

	public String getDoStoreTokenId() {
		return doStoreTokenId;
	}

	public void setDoStoreTokenId(String doStoreTokenId) {
		this.doStoreTokenId = doStoreTokenId;
	}

	public String getDoStoreToken() {
		return doStoreToken;
	}

	public void setDoStoreToken(String doStoreToken) {
		this.doStoreToken = doStoreToken;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getManualMatchingTokenId() {
		return manualMatchingTokenId;
	}

	public void setManualMatchingTokenId(String manualMatchingTokenId) {
		this.manualMatchingTokenId = manualMatchingTokenId;
	}

	public String getManualMatchingToken() {
		return manualMatchingToken;
	}

	public void setManualMatchingToken(String manualMatchingToken) {
		this.manualMatchingToken = manualMatchingToken;
	}

	public String getMatchingTokenId() {
		return matchingTokenId;
	}

	public void setMatchingTokenId(String matchingTokenId) {
		this.matchingTokenId = matchingTokenId;
	}

	public String getMatchingToken() {
		return matchingToken;
	}

	public void setMatchingToken(String matchingToken) {
		this.matchingToken = matchingToken;
	}

	public BigDecimal getTransferAmountPosCard() {
		return transferAmountPosCard;
	}

	public void setTransferAmountPosCard(BigDecimal transferAmountPosCard) {
		this.transferAmountPosCard = transferAmountPosCard;
	}

	public String getPosAccount() {
		return posAccount;
	}

	public void setPosAccount(String posAccount) {
		this.posAccount = posAccount;
	}

	public Date getApplyPayDayPosCard() {
		return applyPayDayPosCard;
	}

	public void setApplyPayDayPosCard(Date applyPayDayPosCard) {
		this.applyPayDayPosCard = applyPayDayPosCard;
	}

	public JkProducts getJkProducts() {
		return jkProducts;
	}

	public void setJkProducts(JkProducts jkProducts) {
		this.jkProducts = jkProducts;
	}

	public String getUrgeManage() {
		return urgeManage;
	}

	public void setUrgeManage(String urgeManage) {
		this.urgeManage = urgeManage;
	}

	public String getDictPosType() {
		return dictPosType;
	}

	public void setDictPosType(String dictPosType) {
		this.dictPosType = dictPosType;
	}

	public BigDecimal getDeductAmountPosCard() {
		return deductAmountPosCard;
	}

	public void setDeductAmountPosCard(BigDecimal deductAmountPosCard) {
		this.deductAmountPosCard = deductAmountPosCard;
	}

	public Date getApplyPayDayPos() {
		return applyPayDayPos;
	}

	public void setApplyPayDayPos(Date applyPayDayPos) {
		this.applyPayDayPos = applyPayDayPos;
	}

	public String getPosBillCode() {
		return posBillCode;
	}

	public void setPosBillCode(String posBillCode) {
		this.posBillCode = posBillCode;
	}

	public String getDateCre() {
		return dateCre;
	}

	public void setDateCre(String dateCre) {
		this.dateCre = dateCre;
	}

	public BigDecimal getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
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

	public int getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
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

	public void setEndDate(Date endDate) throws ParseException {
		   
		if(endDate != null){
			String end = df.format(endDate)+" 23:59:59";
			endDate = df1.parse(end);
		}
		this.endDate = endDate;
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

	public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
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

	public String getEnumThree() {
		return enumThree;
	}

	public void setEnumThree(String enumThree) {
		this.enumThree = enumThree;
	}

	public String getEnumFour() {
		return enumFour;
	}

	public void setEnumFour(String enumFour) {
		this.enumFour = enumFour;
	}

	public BigDecimal getApplyBackAmount() {
		return applyBackAmount;
	}

	public void setApplyBackAmount(BigDecimal applyBackAmount) {
		this.applyBackAmount = applyBackAmount;
	}

	private List<PaybackApply> paybackApplyList;
	
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getDictRepayMethod() {
		return dictRepayMethod;
	}

	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	
	public String getDictPaybackStatus() {
		return dictPaybackStatus;
	}

	public void setDictPaybackStatus(String dictPaybackStatus) {
		this.dictPaybackStatus = dictPaybackStatus;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	
	public String getDictDeductType() {
		return dictDeductType;
	}

	public void setDictDeductType(String dictDeductType) {
		this.dictDeductType = dictDeductType;
	}

	public Date getApplyPayDay() {
		return applyPayDay;
	}

	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}

	public String getApplyAccountName() {
		return applyAccountName;
	}

	public void setApplyAccountName(String applyAccountName) {
		this.applyAccountName = applyAccountName;
	}

	public String getApplyDeductAccount() {
		return applyDeductAccount;
	}

	public void setApplyDeductAccount(String applyDeductAccount) {
		this.applyDeductAccount = applyDeductAccount;
	}

	public String getApplyBankName() {
		return applyBankName;
	}

	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}

	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}

	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public String getDictPayResult() {
		return dictPayResult;
	}

	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}

	public String getDictPayUse() {
		return dictPayUse;
	}

	public void setDictPayUse(String dictPayUse) {
		this.dictPayUse = dictPayUse;
	}

	

	public String getApplyBackMes() {
		return applyBackMes;
	}

	public void setApplyBackMes(String applyBackMes) {
		this.applyBackMes = applyBackMes;
	}

	public String getLanuchBy() {
		return lanuchBy;
	}

	public void setLanuchBy(String lanuchBy) {
		this.lanuchBy = lanuchBy;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Payback getPayback() {
		return payback;
	}

	public void setPayback(Payback payback) {
		this.payback = payback;
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

	public List<PaybackApply> getPaybackApplyList() {
		return paybackApplyList;
	}

	public void setPaybackApplyList(List<PaybackApply> paybackApplyList) {
		this.paybackApplyList = paybackApplyList;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getSplitFatherId() {
		return splitFatherId;
	}

	public void setSplitFatherId(String splitFatherId) {
		this.splitFatherId = splitFatherId;
	}

	public PaybackCharge getPaybackCharge() {
		return paybackCharge;
	}

	public void setPaybackCharge(PaybackCharge paybackCharge) {
		this.paybackCharge = paybackCharge;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDictPayStatus() {
		return dictPayStatus;
	}

	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getPaybackDay() {
		return paybackDay;
	}

	public void setPaybackDay(String paybackDay) {
		this.paybackDay = paybackDay;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getPaybackFlag() {
		return paybackFlag;
	}

	public void setPaybackFlag(String paybackFlag) {
		this.paybackFlag = paybackFlag;
	}
	public String getSplitBackResult() {
		return splitBackResult;
	}

	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult;
	}

	public String getRpaybackId() {
		return rpaybackId;
	}

	public void setRpaybackId(String rpaybackId) {
		this.rpaybackId = rpaybackId;
	}
	

	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}

	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}

	public String getDictSourceType() {
		return dictSourceType;
	}

	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}

	public String getHuankType() {
		return huankType;
	}

	public void setHuankType(String huankType) {
		this.huankType = huankType;
	}

	public String getLoanMark() {
		return loanMark;
	}

	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}

	public Date getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getWhetherOverdue() {
		return whetherOverdue;
	}

	public void setWhetherOverdue(String whetherOverdue) {
		this.whetherOverdue = whetherOverdue;
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getTOP_FLAG() {
		return TOP_FLAG;
	}

	public int getTopMark() {
		return topMark;
	}

	public void setTopMark(int topMark) {
		this.topMark = topMark;
	}

	public String getTrustRecharge() {
		return trustRecharge;
	}

	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
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

	public String getDictDepositAccount() {
		return dictDepositAccount;
	}

	public void setDictDepositAccount(String dictDepositAccount) {
		this.dictDepositAccount = dictDepositAccount;
	}

	public MiddlePerson getMiddlePerson() {
		return middlePerson;
	}

	public void setMiddlePerson(MiddlePerson middlePerson) {
		this.middlePerson = middlePerson;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public String getStoresId() {
		return storesId;
	}

	public void setStoresId(String storesId) {
		this.storesId = storesId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getPaybackId() {
		return paybackId;
	}

	public void setPaybackId(String paybackId) {
		this.paybackId = paybackId;
	}

	public String getApplyPayDayStr() {
		return applyPayDayStr;
	}

	public void setApplyPayDayStr(String applyPayDayStr) {
		this.applyPayDayStr = applyPayDayStr;
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

	public String getSplitBackResultLabel() {
		return splitBackResultLabel;
	}

	public void setSplitBackResultLabel(String splitBackResultLabel) {
		this.splitBackResultLabel = splitBackResultLabel;
	}

	public String getTrustRechargeResultLabel() {
		return trustRechargeResultLabel;
	}

	public void setTrustRechargeResultLabel(String trustRechargeResultLabel) {
		this.trustRechargeResultLabel = trustRechargeResultLabel;
	}

	public String getDictPayUseLabel() {
		return dictPayUseLabel;
	}

	public void setDictPayUseLabel(String dictPayUseLabel) {
		this.dictPayUseLabel = dictPayUseLabel;
	}

	public String getDictRepayMethodLabel() {
		return dictRepayMethodLabel;
	}

	public void setDictRepayMethodLabel(String dictRepayMethodLabel) {
		this.dictRepayMethodLabel = dictRepayMethodLabel;
	}

	public String getDictPayResultLabel() {
		return dictPayResultLabel;
	}

	public void setDictPayResultLabel(String dictPayResultLabel) {
		this.dictPayResultLabel = dictPayResultLabel;
	}

	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}

	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}

	public String getApplyBankNameLabel() {
		return applyBankNameLabel;
	}

	public void setApplyBankNameLabel(String applyBankNameLabel) {
		this.applyBankNameLabel = applyBankNameLabel;
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

	public String getLoanMarkLabel() {
		return loanMarkLabel;
	}

	public void setLoanMarkLabel(String loanMarkLabel) {
		this.loanMarkLabel = loanMarkLabel;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(String sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getSumAmont() {
		return sumAmont;
	}

	public void setSumAmont(String sumAmont) {
		this.sumAmont = sumAmont;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getQueryRight() {
		return queryRight;
	}

	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}

	public String loanSurveyEmpName() {
		return loanManagerName;
	}

	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}

	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}

	public String getLoanSurveyEmpName() {
		return loanSurveyEmpName;
	}

	public void setLoanSurveyEmpName(String loanSurveyEmpName) {
		this.loanSurveyEmpName = loanSurveyEmpName;
	}

	public String getLoanCustomerService() {
		return loanCustomerService;
	}

	public void setLoanCustomerService(String loanCustomerService) {
		this.loanCustomerService = loanCustomerService;
	}

	public String getLoanManagerName() {
		return loanManagerName;
	}

	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}

	public String getSplitPch() {
		return splitPch;
	}

	public void setSplitPch(String splitPch) {
		this.splitPch = splitPch;
	}

	public String getConfrimFlag() {
		return confrimFlag;
	}

	public void setConfrimFlag(String confrimFlag) {
		this.confrimFlag = confrimFlag;
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

	public int getCpcnCount() {
		return cpcnCount;
	}

	public void setCpcnCount(int cpcnCount) {
		this.cpcnCount = cpcnCount;
	}

	public String getBillDay() {
		return billDay;
	}

	public void setBillDay(String billDay) {
		this.billDay = billDay;
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

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getRealAuthen() {
		return realAuthen;
	}

	public void setRealAuthen(String realAuthen) {
		this.realAuthen = realAuthen;
	}

	public String getKlSign() {
		return klSign;
	}

	public void setKlSign(String klSign) {
		this.klSign = klSign;
	}

	public String[] getSplitBackResultArray() {
		return splitBackResultArray;
	}

	public void setSplitBackResultArray(String[] splitBackResultArray) {
		this.splitBackResultArray = splitBackResultArray;
	}

	public String getSumReallyAmont() {
		return sumReallyAmont;
	}

	public void setSumReallyAmont(String sumReallyAmont) {
		this.sumReallyAmont = sumReallyAmont;
	}

	public int getTlCount() {
		return tlCount;
	}

	public void setTlCount(int tlCount) {
		this.tlCount = tlCount;
	}

	public String getCjSign() {
		return cjSign;
	}

	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}

	public String getDictDealTypeId() {
		return dictDealTypeId;
	}

	public void setDictDealTypeId(String dictDealTypeId) {
		this.dictDealTypeId = dictDealTypeId;
	}

	public String getDictDealTypeName() {
		return dictDealTypeName;
	}

	public void setDictDealTypeName(String dictDealTypeName) {
		this.dictDealTypeName = dictDealTypeName;
	}

	public String getOperateRole() {
		return operateRole;
	}

	public void setOperateRole(String operateRole) {
		this.operateRole = operateRole;
	}

	public String getEnumSix() {
		return enumSix;
	}

	public void setEnumSix(String enumSix) {
		this.enumSix = enumSix;
	}
	
	public String getPhoneSaleSign() {
		return phoneSaleSign;
	}

	public void setPhoneSaleSign(String phoneSaleSign) {
		this.phoneSaleSign = phoneSaleSign;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}
	
	
}