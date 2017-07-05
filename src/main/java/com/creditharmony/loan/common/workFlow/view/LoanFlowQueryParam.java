package com.creditharmony.loan.common.workFlow.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 接收页面传递的查询参数
 *
 * @version 1.0
 * @author 张灏
 */
public class LoanFlowQueryParam {
	//借款编号
	private String loanCode;
	// idVal
	private String idVal;
	// 借款编号数组
	private String[] loanCodes;
	// 客户姓名
	private String customerName;
	// 申请产品
	private String applyProductCode;
	// 批复产品(Id)
	private String replyProductCode;
	// 批复产品(名称)
	private String replyProductName;
	// 门店ID
	private String storeOrgId;
	//门店ID数组
	private String[] storeOrgIds;
	// 门店Code
	private String storeCode;
	// 门店名称
	private String storeName;
	// 团队经理(Id)
	private String teamManagerCode;
	// 团队经理(姓名)
	private String teamManagerName;
	// 客户经理（Id）
	private String customerManagerCode;
	// 客户经理（姓名）
	private String customerManagerName;
	// 加急标识
	private String urgentFlag;
	// 是否电销
	private String telesalesFlag;
	// 渠道标识
	private String channelCode;
	// TG标识
	private String tgFlag;
	// 身份证号
	private String identityCode;
	// 是否追加借
	private String additionalFlag;
	// 合同编号
	private String contractCode;
	// 合同编号数组
	private String[] contractCodes;
	// 放款银行
	private String cautionerDepositBank;
	//放款银行数组
	private String[] cautionerDepositBanks;
	// 省份（名字）
	private String provinceName;
	// 省份（Code）
	private String provinceCode;
	// 城市（名字）
	private String cityName;
	// 城市（Code）
	private String cityCode;
	// 借款状态
	private String loanStatusCode;
	// 借款状态(名字)
	private String loanStatusName;
	// 合同金额
	private Double contractMoney;
	// 合同金额上限
	private Double contractMoneyStart;
	// 合同金额下限
	private Double contractMoneyEnd;
	// 放款金额(上、下限)
	private Double lendingMoney;
	// 放款金额上限
	private Double lendingMoneyStart;
	// 放款金额下限
	private Double lendingMoneyEnd;
	// 开户行
	private String depositBank;
	// 开户行数组
	private String[] depositBanks;
	// 借款类型
	private String loanType;
	// 放款途径(Code)
	private String lendingWayCode;
	// 放款途径(Name)
	private String lendingWayName;
	// 放款时间
	private Date lendingTime;
	// 放款时间上限
	private Date lendingTimeStart;
	// 放款时间下限
	private Date lendingTimeEnd;
	// 来源系统
	private String dictSource;
	// 外放人员id
	private String visitUserId;
	// 外访人员名字
	private String visitUserName;
	// 支行名称
	private String bankBranchName;
	// 共借人
	private String coborrowerName;
	// 步骤名 测试用
	private String stepName;
	// 借款ID
	private String applyId;
	// applyIds
	private String[] applyIds;
	// 借款期限
	private BigDecimal contractMonths;
	// 批复金额
	private Integer auditAmount;
	// 信访费
	private Integer feePetition;
	// 加急费
	private Integer feeExpedited;
	// 提交批次
	private String submissionBatch;
	// 提交日期
	private String submissionDate;
	// 提交开始日期
	private Date submissionDateStart;
	// 提交结束日期
	private Date submissionDateEnd;
	

	// 放款批次
	private String grantBatchCode;
	//冻结标识   1冻结    0 为冻结
	private String frozenFlag;
	// 放款回盘结果
	private String receiptResult;
	// 合同版本号
	private String contractVersion;
	// 借款利率
	private String[] monthRate;
	// 提交划扣时间，放款
	private Date submitDeductTime;
	//自动放款结果
    private String autoGrantResult;
    // 是否登记失败
    private String registFlag;
    // 是否加盖失败
    private String signUpFlag; 
    // 是否有保证人
    private String ensureManFlag;
    // 无纸化标识
    private String paperLessFlag;
    // 模式
    private String model;
    // 风险等级
    private String riskLevel;
    // 委托提现
    private String trustCash;
    // 回访状态
    private String[] revisitStatus;
    //回访状态名称
    private String revisitStatusName;
    //回访辅助查询字段
    private String revisitQueryName;
    // 审核日期
    private Date checkTime;
    
    //汇成审批时间 开始
    private String outApproveTimeStart;
    //汇成审批时间 结束
    private String outApproveTimeEnd;
    
    //门店提交时间 开始
    private String lastDealTimeStart;
    //门店提交时间 结束
    private String lastDealTimeEnd;
    
    //确认签署时间 开始
    private String confirmSignDateStart;
    //确认签署时间 结束
    private String confirmSignDateEnd;
    
    // 放款人员
    private String dealUser;
    //金信处理状态
    private String goldCreditStatus;
    // 金信的文件导出状态
    private String trustGrantOutputStatus;
    //申请人
    private String userCode;

    public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
    public String getOutApproveTimeStart() {
		return outApproveTimeStart;
	}

	public void setOutApproveTimeStart(String outApproveTimeStart) {
		this.outApproveTimeStart = outApproveTimeStart;
	}

	public String getOutApproveTimeEnd() {
		return outApproveTimeEnd;
	}

	public void setOutApproveTimeEnd(String outApproveTimeEnd) {
		this.outApproveTimeEnd = outApproveTimeEnd;
	}

	public String getLastDealTimeStart() {
		return lastDealTimeStart;
	}

	public void setLastDealTimeStart(String lastDealTimeStart) {
		this.lastDealTimeStart = lastDealTimeStart;
	}

	public String getLastDealTimeEnd() {
		return lastDealTimeEnd;
	}

	public void setLastDealTimeEnd(String lastDealTimeEnd) {
		this.lastDealTimeEnd = lastDealTimeEnd;
	}

	public String getConfirmSignDateStart() {
		return confirmSignDateStart;
	}

	public void setConfirmSignDateStart(String confirmSignDateStart) {
		this.confirmSignDateStart = confirmSignDateStart;
	}

	public String getConfirmSignDateEnd() {
		return confirmSignDateEnd;
	}

	public void setConfirmSignDateEnd(String confirmSignDateEnd) {
		this.confirmSignDateEnd = confirmSignDateEnd;
	}

	public String getCjAuthenFailure() {
		return cjAuthenFailure;
	}

	public void setCjAuthenFailure(String cjAuthenFailure) {
		this.cjAuthenFailure = cjAuthenFailure;
	}

	public String getCjAuthen() {
		return cjAuthen;
	}

	public void setCjAuthen(String cjAuthen) {
		this.cjAuthen = cjAuthen;
	}

	// 畅捷实名认证失败原因
    private String cjAuthenFailure;
    // 畅捷实名认证结果
    private String cjAuthen;
   
    
	public String getAutoGrantResult() {
		return autoGrantResult;
	}

	public void setAutoGrantResult(String autoGrantResult) {
		this.autoGrantResult = autoGrantResult;
	}

	public String getFrozenFlag() {
		return frozenFlag;
	}

	public void setFrozenFlag(String frozenFlag) {
		this.frozenFlag = frozenFlag;
	}

	public String getSubmissionBatch() {
		return submissionBatch;
	}

	public void setSubmissionBatch(String submissionBatch) {
		this.submissionBatch = submissionBatch;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public Date getSubmissionDateStart() {
		return submissionDateStart;
	}

	public void setSubmissionDateStart(Date submissionDateStart) {
		this.submissionDateStart = submissionDateStart;
	}

	public Date getSubmissionDateEnd() {
		return submissionDateEnd;
	}

	public void setSubmissionDateEnd(Date submissionDateEnd) {
		this.submissionDateEnd = submissionDateEnd;
	}

	// 放款批次
    private String grantPch;

	public String getGrantPch() {
		return grantPch;
	}

	public void setGrantPch(String grantPch) {
		this.grantPch = grantPch;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplyProductCode() {
		return applyProductCode;
	}

	public void setApplyProductCode(String applyProductCode) {
		this.applyProductCode = applyProductCode;
	}

	public String getReplyProductCode() {
		return replyProductCode;
	}

	public void setReplyProductCode(String replyProductCode) {
		this.replyProductCode = replyProductCode;
	}

	public String getReplyProductName() {
		return replyProductName;
	}

	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
	}
	
	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTeamManagerCode() {
		return teamManagerCode;
	}

	public void setTeamManagerCode(String teamManagerCode) {
		this.teamManagerCode = teamManagerCode;
	}

	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public String getUrgentFlag() {
		return urgentFlag;
	}

	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}

	public String getTelesalesFlag() {
		return telesalesFlag;
	}

	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getAdditionalFlag() {
		return additionalFlag;
	}

	public void setAdditionalFlag(String additionalFlag) {
		this.additionalFlag = additionalFlag;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String[] getContractCodes() {
		return contractCodes;
	}

	public void setContractCodes(String[] contractCodes) {
		this.contractCodes = contractCodes;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getLoanStatusCode() {
		return loanStatusCode;
	}

	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}

	public String getLoanStatusName() {
		return loanStatusName;
	}

	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
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

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLendingWayCode() {
		return lendingWayCode;
	}

	public void setLendingWayCode(String lendingWayCode) {
		this.lendingWayCode = lendingWayCode;
	}

	public String getLendingWayName() {
		return lendingWayName;
	}

	public void setLendingWayName(String lendingWayName) {
		this.lendingWayName = lendingWayName;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getDictSource() {
		return dictSource;
	}

	public void setDictSource(String dictSource) {
		this.dictSource = dictSource;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String[] getApplyIds() {
		return applyIds;
	}

	public void setApplyIds(String[] applyIds) {
		this.applyIds = applyIds;
	}

	public BigDecimal getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(BigDecimal contractMonths) {
		this.contractMonths = contractMonths;
	}

	public Integer getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(Integer auditAmount) {
		this.auditAmount = auditAmount;
	}

	public Integer getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(Integer feePetition) {
		this.feePetition = feePetition;
	}

	public Integer getFeeExpedited() {
		return feeExpedited;
	}

	public void setFeeExpedited(Integer feeExpedited) {
		this.feeExpedited = feeExpedited;
	}

	public String getCautionerDepositBank() {
		return cautionerDepositBank;
	}

	public void setCautionerDepositBank(String cautionerDepositBank) {
		this.cautionerDepositBank = cautionerDepositBank;
	}

	public Double getLendingMoneyStart() {
		return lendingMoneyStart;
	}

	public void setLendingMoneyStart(Double lendingMoneyStart) {
		this.lendingMoneyStart = lendingMoneyStart;
	}

	public Double getLendingMoneyEnd() {
		return lendingMoneyEnd;
	}

	public void setLendingMoneyEnd(Double lendingMoneyEnd) {
		this.lendingMoneyEnd = lendingMoneyEnd;
	}

	public Double getContractMoneyStart() {
		return contractMoneyStart;
	}

	public void setContractMoneyStart(Double contractMoneyStart) {
		this.contractMoneyStart = contractMoneyStart;
	}

	public Double getContractMoneyEnd() {
		return contractMoneyEnd;
	}

	public void setContractMoneyEnd(Double contractMoneyEnd) {
		this.contractMoneyEnd = contractMoneyEnd;
	}

	public Date getLendingTimeStart() {
		return lendingTimeStart;
	}

	public void setLendingTimeStart(Date lendingTimeStart) {
		this.lendingTimeStart = lendingTimeStart;
	}

	public Date getLendingTimeEnd() {
		return lendingTimeEnd;
	}

	public void setLendingTimeEnd(Date lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}

	public String getVisitUserId() {
		return visitUserId;
	}

	public void setVisitUserId(String visitUserId) {
		this.visitUserId = visitUserId;
	}

	/**
     * @return the visitUserName
     */
    public String getVisitUserName() {
        return visitUserName;
    }

    /**
     * @param visitUserName the String visitUserName to set
     */
    public void setVisitUserName(String visitUserName) {
        this.visitUserName = visitUserName;
    }

    public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getGrantBatchCode() {
		return grantBatchCode;
	}

	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	/**
     * @return the monthRate
     */
    public String[] getMonthRate() {
        return monthRate;
    }

    /**
     * @param monthRate the String monthRate to set
     */
    public void setMonthRate(String[] monthRate) {
        this.monthRate = monthRate;
    }

    public Date getSubmitDeductTime() {
		return submitDeductTime;
	}

	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
	}

	public String getReceiptResult() {
		return receiptResult;
	}

	public void setReceiptResult(String receiptResult) {
		this.receiptResult = receiptResult;
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

    public String getEnsureManFlag() {
        return ensureManFlag;
    }

    public void setEnsureManFlag(String ensureManFlag) {
        this.ensureManFlag = ensureManFlag;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

	public String getPaperLessFlag() {
		return paperLessFlag;
	}

	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}

    /**
     * @return the riskLevel
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * @param riskLevel the String riskLevel to set
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

	public String getTrustCash() {
		return trustCash;
	}

	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}

	public String[] getRevisitStatus() {
		return revisitStatus;
	}

	public void setRevisitStatus(String[] revisitStatus) {
		this.revisitStatus = revisitStatus;
	}

	public String getRevisitStatusName() {
		return revisitStatusName;
	}

	public void setRevisitStatusName(String revisitStatusName) {
		this.revisitStatusName = revisitStatusName;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	

 

	public String getTgFlag() {
		return tgFlag;
	}

	public void setTgFlag(String tGFlag) {
		this.tgFlag = tGFlag;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getTrustGrantOutputStatus() {
		return trustGrantOutputStatus;
	}

	public void setTrustGrantOutputStatus(String trustGrantOutputStatus) {
		this.trustGrantOutputStatus = trustGrantOutputStatus;
	}

	public String getRevisitQueryName() {
		return revisitQueryName;
	}

	public void setRevisitQueryName(String revisitQueryName) {
		this.revisitQueryName = revisitQueryName;
	}

	public String getGoldCreditStatus() {
		return goldCreditStatus;
	}

	public void setGoldCreditStatus(String goldCreditStatus) {
		this.goldCreditStatus = goldCreditStatus;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String[] getStoreOrgIds() {
		return storeOrgIds;
	}

	public void setStoreOrgIds(String[] storeOrgIds) {
		this.storeOrgIds = storeOrgIds;
	}

	public String[] getCautionerDepositBanks() {
		return cautionerDepositBanks;
	}

	public void setCautionerDepositBanks(String[] cautionerDepositBanks) {
		this.cautionerDepositBanks = cautionerDepositBanks;
	}

	public String[] getDepositBanks() {
		return depositBanks;
	}

	public void setDepositBanks(String[] depositBanks) {
		this.depositBanks = depositBanks;
	}

	public String[] getLoanCodes() {
		return loanCodes;
	}

	public void setLoanCodes(String[] loanCodes) {
		this.loanCodes = loanCodes;
	}

	public String getIdVal() {
		return idVal;
	}

	public void setIdVal(String idVal) {
		this.idVal = idVal;
	}
}
