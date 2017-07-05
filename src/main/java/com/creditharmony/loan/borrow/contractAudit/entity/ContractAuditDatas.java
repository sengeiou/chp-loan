package com.creditharmony.loan.borrow.contractAudit.entity;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

public class ContractAuditDatas  {
	private String flowName = "信借";//
	private String stepName = "合同审核";//
	private String isSplit;//联合放款拆分标识
	private String token;//该任务的令牌
	private String wobNum;//工作项Id(任务Id)
	private String applyId;

	/**
	 * 登陆用户员工编号
	 */
	private String userCode;
	
	/**
	 * 用于数据权限
	 */
	private String ownerTaskCondition;
	
	/*
	 * 客户姓名
	 */
	private String customerName;
	/*
	 * 合同编号
	 */
	private String contractCode;
	/*
	 * 门店名称
	 */
	private String storeName;
	/*
	 * 门店id
	 */
	private String storeOrgId;
	/*
	 * 门店id数组
	 */
	private String[] storeOrgIds;
	/**
	 * 身份证号
	 */
	private String identityCode;
	/**
	 * 模式
	 */
	private String model;
	/**
	 * 渠道
	 */
	private String channelCode;
	/**
	 * 产品
	 */
	private String replyProductCode;
	/**
	 * 是否追加借
	 */
	private String additionalFlag;
	/**
	 * 是否电销
	 */
	private String telesalesFlag;
	/**
	 * 是否加急
	 */
	private String urgentFlag;
	/**
	 * 版本号
	 */
	private String contractVersion;
	/**
	 * 借款状态
	 */
	private String loanStatusCode;
	/*
	 * 风险等级
	 */
	private String riskLevel;
	
	/*
	 * 畅捷实名认证结果
	 */
	private String cjAuthen;
	/*
	 * 畅捷实名认证失败原因
	 */
	private String cjAuthenFailure;
	/*
	 * 申请状态描述
	 */
	private String loanStatusName;
	/*
	 * 合同阶段退回状态
	 */
	private String backFlag;
 
	/*
	 * 渠道名称描述
	 */
	private String channelName;
	  // 共借人
    private String coborrowerName;
    // 省份
    private String provinceName;
    // 城市
    private String cityName;
    // 门店编号
    private String storeCode;
	// 批复产品
    @ExcelField(title = "借款产品", type = 0, align = 2, sort = 60, groups = 1)
    private String replyProductName;
    // 批复金额
    private Double replyMoney;
    // 批复分期(批借期限)
    @ExcelField(title = "批借期限", type = 0, align = 2, sort = 90, groups = 1)
    private Integer replyMonth;
    // 进件时间
    private Date intoLoanTime;
 
    // 上调标识
    private String raiseFlag;
    // 签约平台
    private String signPlatform;
    // 合同金额
    @ExcelField(title = "合同金额", type = 0, align = 2, sort = 70, groups = 1)
    private Double contractMoney;
    // 放款金额
    @ExcelField(title = "放款金额", type = 0, align = 2, sort = 80, groups = 1)
    private Double lendingMoney;
    // 开户行
    @ExcelField(title = "开户行", type = 0, align = 2, sort = 100, groups = 1)
    private String depositBank;
    // 支行名称
    private String bankBranchName;
    // 用户账号
    private String bankAccountNumber;
    // 借款类型
    @ExcelField(title = "借款类型", type = 0, align = 2, sort = 50, groups = 1)
    private String loanType;
    // 回执结果
    private String receiptResult;
    // 失败原因
    private String failReason;
    // 未划金额
    private Double unDeductMoney;
    // 中间人开户行
    private String cautionerDepositBank;
    // 中间人的放款账号
    private String lendingAccount;
    // 中间人的账户姓名
    private String bankAccountName;
    // 催收服务费
    private Double urgeServiceFee;
    // 放款途径
    private String lendingWayName;
    // 客户编号
    private String customerCode;
    // 咨询ID
    private String consultId;
    // 借款利率
    private String monthRate;
    // 申请产品
    private String applyProductName;
    // 申请产品编码
    private String applyProductCode;
    // 退回原因
    private String backReason;
    // 放款失败金额
    private Double grantFailAmount;
    // 借款编号
    private String loanCode;
    // 汇诚审批时间
    private Date outApproveTime;
    // 是否第一单 1 不是 0 是
    private String isOld;
	// 是否冻结 1 冻结 0 未冻结
    private String frozenFlag;
    // 原因描述
    private String frozenReason;
    // 上一个节点操作时间
    private Date lastDealTime;
    // 无纸化标识
    private String paperLessFlag;
    // 是否登记失败
    private String registFlag;
    // 是否加盖失败
    private String signUpFlag; 
    
    // 模式中文名 ("","TG")
    private String modelLabel;
 
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    
    private Date contractDueDay;
    
    private Date startContractDueDay;
    
    private Date endContractDueDay;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getReplyProductCode() {
		return replyProductCode;
	}

	public void setReplyProductCode(String replyProductCode) {
		this.replyProductCode = replyProductCode;
	}

	public String getAdditionalFlag() {
		return additionalFlag;
	}

	public void setAdditionalFlag(String additionalFlag) {
		this.additionalFlag = additionalFlag;
	}

	public String getTelesalesFlag() {
		return telesalesFlag;
	}

	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}

	public String getUrgentFlag() {
		return urgentFlag;
	}

	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getLoanStatusCode() {
		return loanStatusCode;
	}

	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getCjAuthen() {
		return cjAuthen;
	}

	public void setCjAuthen(String cjAuthen) {
		this.cjAuthen = cjAuthen;
	}

	public String getCjAuthenFailure() {
		return cjAuthenFailure;
	}

	public void setCjAuthenFailure(String cjAuthenFailure) {
		this.cjAuthenFailure = cjAuthenFailure;
	}

	public String getLoanStatusName() {
		return loanStatusName;
	}

	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
	}

	public String getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getReplyProductName() {
		return replyProductName;
	}

	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
	}

	public Double getReplyMoney() {
		return replyMoney;
	}

	public void setReplyMoney(Double replyMoney) {
		this.replyMoney = replyMoney;
	}

	public Integer getReplyMonth() {
		return replyMonth;
	}

	public void setReplyMonth(Integer replyMonth) {
		this.replyMonth = replyMonth;
	}

	public Date getIntoLoanTime() {
		return intoLoanTime;
	}

	public void setIntoLoanTime(Date intoLoanTime) {
		this.intoLoanTime = intoLoanTime;
	}

	public String getRaiseFlag() {
		return raiseFlag;
	}

	public void setRaiseFlag(String raiseFlag) {
		this.raiseFlag = raiseFlag;
	}

	public String getSignPlatform() {
		return signPlatform;
	}

	public void setSignPlatform(String signPlatform) {
		this.signPlatform = signPlatform;
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

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getReceiptResult() {
		return receiptResult;
	}

	public void setReceiptResult(String receiptResult) {
		this.receiptResult = receiptResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Double getUnDeductMoney() {
		return unDeductMoney;
	}

	public void setUnDeductMoney(Double unDeductMoney) {
		this.unDeductMoney = unDeductMoney;
	}

	public String getCautionerDepositBank() {
		return cautionerDepositBank;
	}

	public void setCautionerDepositBank(String cautionerDepositBank) {
		this.cautionerDepositBank = cautionerDepositBank;
	}

	public String getLendingAccount() {
		return lendingAccount;
	}

	public void setLendingAccount(String lendingAccount) {
		this.lendingAccount = lendingAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public Double getUrgeServiceFee() {
		return urgeServiceFee;
	}

	public void setUrgeServiceFee(Double urgeServiceFee) {
		this.urgeServiceFee = urgeServiceFee;
	}

	public String getLendingWayName() {
		return lendingWayName;
	}

	public void setLendingWayName(String lendingWayName) {
		this.lendingWayName = lendingWayName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getConsultId() {
		return consultId;
	}

	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}

	public String getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}

	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}

	public String getApplyProductCode() {
		return applyProductCode;
	}

	public void setApplyProductCode(String applyProductCode) {
		this.applyProductCode = applyProductCode;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public Double getGrantFailAmount() {
		return grantFailAmount;
	}

	public void setGrantFailAmount(Double grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}


	public String getIsOld() {
		return isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public String getFrozenFlag() {
		return frozenFlag;
	}

	public void setFrozenFlag(String frozenFlag) {
		this.frozenFlag = frozenFlag;
	}

	public String getFrozenReason() {
		return frozenReason;
	}

	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}


	public String getPaperLessFlag() {
		return paperLessFlag;
	}

	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}

	public String getRegistFlag() {
		return registFlag;
	}

	public void setRegistFlag(String registFlag) {
		this.registFlag = registFlag;
	}

	public String getSignUpFlag() {
		return signUpFlag;
	}

	public void setSignUpFlag(String signUpFlag) {
		this.signUpFlag = signUpFlag;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

	public String[] getStoreOrgIds() {
		return storeOrgIds;
	}

	public void setStoreOrgIds(String[] storeOrgIds) {
		this.storeOrgIds = storeOrgIds;
	}

	public String getOwnerTaskCondition() {
		return ownerTaskCondition;
	}

	public void setOwnerTaskCondition(String ownerTaskCondition) {
		this.ownerTaskCondition = ownerTaskCondition;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWobNum() {
		return wobNum;
	}

	public void setWobNum(String wobNum) {
		this.wobNum = wobNum;
	}

	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Date getOutApproveTime() {
		return outApproveTime;
	}

	public void setOutApproveTime(Date outApproveTime) {
		this.outApproveTime = outApproveTime;
	}

	public Date getLastDealTime() {
		return lastDealTime;
	}

	public void setLastDealTime(Date lastDealTime) {
		this.lastDealTime = lastDealTime;
	}

	public Date getContractDueDay() {
		return contractDueDay;
	}

	public void setContractDueDay(Date contractDueDay) {
		this.contractDueDay = contractDueDay;
	}

	public Date getStartContractDueDay() {
		return startContractDueDay;
	}

	public void setStartContractDueDay(Date startContractDueDay) {
		this.startContractDueDay = startContractDueDay;
	}

	public Date getEndContractDueDay() {
		return endContractDueDay;
	}

	public void setEndContractDueDay(Date endContractDueDay) {
		this.endContractDueDay = endContractDueDay;
	}
	
	
}
