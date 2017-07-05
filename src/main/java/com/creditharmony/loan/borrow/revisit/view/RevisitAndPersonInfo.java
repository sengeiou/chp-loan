package com.creditharmony.loan.borrow.revisit.view;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;


/**
 * 放款前回访相关信息
 * @Class Name RevisitAndPersonInfo
 * @author songfeng
 * @Create In 2016年10月27日
 */
@SuppressWarnings("serial")
public class RevisitAndPersonInfo  extends DataEntity<RevisitAndPersonInfo>{
	
	//借款编号
	private String loanCode;
	//申请id
	private String applyId;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3, groups = 1)
	private String contractCode;
	// 门店
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 2, groups = 1)
	private String storeName;
	// 客户姓名
	private String customerName;
	// 批复产品(名称)
	private String replyProductName;
	// 共借人
	private String coborrowerName;
	// 身份证号
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 5, groups = 1)
	private String identityCode;
	// 期数
	@ExcelField(title = "期数", type = 0, align = 2, sort = 6, groups = 1)
	private String replyMonth;
	// 借款利率
	@ExcelField(title = "借款利率", type = 0, align = 2, sort = 7, groups = 1)
	private BigDecimal monthRate;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 8, groups = 1)
	private String contractMoneyStr;
	
	private Double contractMoney;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 9, groups = 1)
	private String lendingMoneyStr;
	
	private Double lendingMoney;
	// 催收服务费
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 10, groups = 1)
	private String urgeServiceFeeStr;
	
	private Double urgeServiceFee;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 12, groups = 1,dictType="tz_open_bank")
	private String depositBank;
	// 支行名称
	@ExcelField(title = "支行名称", type = 0, align = 2, sort = 13, groups = 1)
	private String bankBranchName;
	// 账号
	@ExcelField(title = "放款账号", type = 0, align = 2, sort = 11, groups = 1)
	private String bankAccountNumber;
	//回访状态
	private String revisitStatus;
	// 借款状态（客户放弃）
	private String loanStatusName;
	// 加急标识
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 16,groups = 1,dictType="jk_urgent_flag")
	private String urgentFlag;
	//模式
	private String modelLabel;
	//渠道
	@ExcelField(title = "渠道", type = 0, align = 2, sort = 14, groups = 1,dictType="jk_channel_flag")
	private String channelName;
	// 合同版本号
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 15, groups = 1)
	private String contractVersion;
	//回访失败原因
	private String revisitReason;
    // 无纸化标识
	@ExcelField(title = "是否无纸化", type = 0, align = 2, sort = 17, groups = 1,dictType="yes_no")
    private String paperLessFlag;
	// 是否电销
	private String telesalesFlag;
    // 风险等级
    private String riskLevel;
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    //门店id
    private String[] storeOrgId;
    //是否加盖失败
    private String signUpFlag;
    //冻结字段
    private String frozenFlag;
    //是否有保证人
    @ExcelField(title = "是否有保证人", type = 0, align = 2, sort = 18, groups = 1,dictType="yes_no")
    private String ensureManFlag;
    //是否登记失败
    private String registFlag;
    //回访状态
    @ExcelField(title = "回访状态", type = 0, align = 2, sort =19, groups = 1)
    private String revisitFlag;
    //账户姓名
    @ExcelField(title = "账户姓名", type = 0, align = 2, sort =4, groups = 1)
    private String accountName;
    //序号
    @ExcelField(title = "序号", type = 0, align = 2, sort =1, groups = 1)
    private String rowId;
    //利率
    private String[] monthRateAll;
    //自然保证人
    private String bestCoborrower;
    
    
	public String[] getMonthRateAll() {
		return monthRateAll;
	}
	public void setMonthRateAll(String[] monthRateAll) {
		this.monthRateAll = monthRateAll;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getRevisitFlag() {
		return revisitFlag;
	}
	public void setRevisitFlag(String revisitFlag) {
		this.revisitFlag = revisitFlag;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String[] getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String[] storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public String getSignUpFlag() {
		return signUpFlag;
	}
	public void setSignUpFlag(String signUpFlag) {
		this.signUpFlag = signUpFlag;
	}
	public String getFrozenFlag() {
		return frozenFlag;
	}
	public void setFrozenFlag(String frozenFlag) {
		this.frozenFlag = frozenFlag;
	}
	public String getEnsureManFlag() {
		return ensureManFlag;
	}
	public void setEnsureManFlag(String ensureManFlag) {
		this.ensureManFlag = ensureManFlag;
	}
	public String getRegistFlag() {
		return registFlag;
	}
	public void setRegistFlag(String registFlag) {
		this.registFlag = registFlag;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReplyProductName() {
		return replyProductName;
	}
	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
	}
	public String getCoborrowerName() {
		return coborrowerName;
	}
	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public String getReplyMonth() {
		return replyMonth;
	}
	public void setReplyMonth(String replyMonth) {
		this.replyMonth = replyMonth;
	}
	public BigDecimal getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public Double getLendingMoney() {
		return lendingMoney;
	}
	public void setLendingMoney(Double lendingMoney) {
		this.lendingMoney = lendingMoney;
	}
	public Double getUrgeServiceFee() {
		return urgeServiceFee;
	}
	public void setUrgeServiceFee(Double urgeServiceFee) {
		this.urgeServiceFee = urgeServiceFee;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}
	public String getLoanStatusName() {
		return loanStatusName;
	}
	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
	public String getModelLabel() {
		return modelLabel;
	}
	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getRevisitReason() {
		return revisitReason;
	}
	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}
	public String getPaperLessFlag() {
		return paperLessFlag;
	}
	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}
	public String getTelesalesFlag() {
		return telesalesFlag;
	}
	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getUrgeServiceFeeStr() {
		return urgeServiceFeeStr;
	}
	public void setUrgeServiceFeeStr(String urgeServiceFeeStr) {
		this.urgeServiceFeeStr = urgeServiceFeeStr;
	}
	public String getContractMoneyStr() {
		return contractMoneyStr;
	}
	public void setContractMoneyStr(String contractMoneyStr) {
		this.contractMoneyStr = contractMoneyStr;
	}
	public String getLendingMoneyStr() {
		return lendingMoneyStr;
	}
	public void setLendingMoneyStr(String lendingMoneyStr) {
		this.lendingMoneyStr = lendingMoneyStr;
	}
	public String getBestCoborrower() {
		return bestCoborrower;
	}
	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}
    
	
}
