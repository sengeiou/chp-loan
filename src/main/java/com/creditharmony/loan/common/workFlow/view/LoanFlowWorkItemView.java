/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.workFlow.viewLoanFlowWorkItemView.java
 * @Create By 张灏
 * @Create In 2016年1月23日 下午4:52:05
 */
package com.creditharmony.loan.common.workFlow.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;

/**
 * @Class Name LoanFlowWorkItemView
 * @author 张灏
 * @Create In 2016年1月23日
 */
public class LoanFlowWorkItemView  extends BaseTaskItemView{
	// 放款id
	private String id;
	// 放款时间
	private Date lendingTime;
	// 线上放款回盘时间
	private Date grantBackDate;
    // 合同版本号
    private String contractVersion;
    // 合同编号
    private String contractCode;
    // 客户姓名
    private String customerName;
    // 共借人
    private String coborrowerName;
    //最优自然保证人
    private String bestCoborrowerName;
    
    private String bestCoborrowerFlag;
    // 自然人保证人
    private String newCoboName;
    // 省份
    private String provinceName;
    // 城市
    private String cityName;
    // 门店编号
    private String storeCode;
    // 门店
    private String storeName;
	// 批复产品
    private String replyProductName;
    // 借款状态
    private String loanStatusName;
    
    private String loanStatusCodeName;
    //借款狀態
    private String loanStatusCode;
    // 批复金额
    private Double replyMoney;
    // 批复分期(批借期限)
    private Integer replyMonth;
    // 加急标识
    private String urgentFlag;
    // 是否电销
    private String telesalesFlag;
    
    private String telesalesFlagName;
    // 团队经理（名称）
    private String teamManagerName;
    // 客户经理
    private String customerManagerName;
    // 进件时间
    private Date intoLoanTime;
    // 渠道标识
    private String channelCode;
   // 渠道标识名称
    private String channelCodeLabel;
    // 渠道标识Name
    private String channelName;
    // 上调标识
    private String raiseFlag;
    // 签约平台
    private String signPlatform;
    // 汇诚审批时间
    private Date intoApproveTime;
    // 证件号码
    private String identityCode;
    // 合同金额
    private Double contractMoney;
    // 放款金额
    private Double lendingMoney;
    // 首次放款金额
    private Double firstLendingMoney;
    // 开户行
    private String depositBank;
    // 支行名称
    private String bankBranchName;
    // 用户账号
    private String bankAccountNumber;
    // 借款类型
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
    // 合同阶段退回状态
    private String backFlag;
    // 退回原因
    private String backReason;
    // 放款失败金额
    private Double grantFailAmount;
    // 借款编号
    private String loanCode;
    // 汇诚审批时间
    private String outApproveTime;
    // 外访开始时间
    private String visitStartTime;
    // 外访结束时间
    private String visitFinishTime;
    // 是否第一单 1 不是 0 是
    private String isOld;
 	// 手机号
	private String mobelPhone;
	// 邮箱地址
	private String email;
	// 开户省编码
	private String bankProvinceCode;
	// 开户市编码
	private String bankCityCode;
	// 开户区县编码
	private String bankAreaCode;
	// 金账户开户返回原因
	private String kingOpenRespReason;
	// 金账户协议库返回原因
	private String kingProctolRespReason;
	// 金账户开户状态
	private String kingStatus;
	// 金账户开户状态名称
	private String kingStatusLabel;
	// 金账户开户省市
	private String kingBankProvinceName;
	// 金账户开户区县
	private String kingBankCityName;
	// 是否冻结 1 冻结 0 未冻结
    private String frozenFlag;
    // 原因描述
    private String frozenReason;
    // 提交批次
    private String submissionBatch;
    // 提交时间
    private String submissionDate;
    // 提交划扣时间
    private Date submitDeductTime;
    // 放款时的手动输入批次
    private String grantBatchCode;
    //委托提现状态
    private String trustCash;
    //委托提现回盘状态
    private String trustCashRtn;
    //委托提现失败原因
    private String trustCashFailure;
    //资金托管待放款文件导出状态
    private String trustGrantOutputStatus;
    // 上一个节点操作时间
    private String lastDealTime;
    // 确认签署时间
    private String confirmSignDate;
    // 无纸化标识
    private String paperLessFlag;
    //自动放款结果
    private String autoGrantResult;
    // 是否登记失败
    private String registFlag;
    // 是否加盖失败
    private String signUpFlag; 
    // 是否有保证人
    private String ensureManFlag;
    // 是否有生僻字
    private String bankIsRareword;
    // 开户姓名
    private String custBankAccountName;
    // 模式
    private String model;
    // 模式中文名
    private String modelLabel;
    //风险等级
    private String riskLevel;
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    
	 // 畅捷实名认证失败原因
    private String cjAuthenFailure;
    // 畅捷实名认证结果
    private String cjAuthen;
    
    // 回访状态
    private String revisitStatus;
    
    //门店组织机构id
    private String storeOrgId;
    
    // 合同审核时间
    private Date checkTime;
    
    //复议节点标识
    private String nodeFlag;
    
    //拆分标记
    private String issplit;
    
    // 放款总金额
    private Double totalGrantMoney;
    // 放款失败总金额
    private Double totalGrantFailMoney;
    // 尾款放款金额
    private Double lastGrantAmount;
    // 信访费
    private Double feePetition;
    // 征信费
    private Double feeCredit;
    // 费用总和:催收费+信访费+征信费
    private Double feeSum;
    
    public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}

    public String getNodeFlag() {
		return nodeFlag;
	}
	public void setNodeFlag(String nodeFlag) {
		this.nodeFlag = nodeFlag;
	}
	
    public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

    public String getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public String getModelLabel() {
		return modelLabel;
	}
	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}
	public String getAutoGrantResult() {
		return autoGrantResult;
	}
	public void setAutoGrantResult(String autoGrantResult) {
		this.autoGrantResult = autoGrantResult;
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
    public String getContractVersion() {
        return contractVersion;
    }
    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
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
    public Date getSubmitDeductTime() {
		return submitDeductTime;
	}
	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
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
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getReplyProductName() {
        return replyProductName;
    }
    public void setReplyProductName(String replyProductName) {
        this.replyProductName = replyProductName;
    }
    public String getLoanStatusName() {
        return loanStatusName;
    }
    public void setLoanStatusName(String loanStatusName) {
        this.loanStatusName = loanStatusName;
    }
    public String getLoanStatusCode() {
        return loanStatusCode;
    }
    public void setLoanStatusCode(String loanStatusCode) {
        this.loanStatusCode = loanStatusCode;
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
    public String getTeamManagerName() {
        return teamManagerName;
    }
    public void setTeamManagerName(String teamManagerName) {
        this.teamManagerName = teamManagerName;
    }
    public String getCustomerManagerName() {
        return customerManagerName;
    }
    public void setCustomerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
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
    public Date getIntoApproveTime() {
        return intoApproveTime;
    }
    public void setIntoApproveTime(Date intoApproveTime) {
        this.intoApproveTime = intoApproveTime;
    }
    public String getIdentityCode() {
        return identityCode;
    }
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
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
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
	public String getApplyProductName() {
		return applyProductName;
	}
	public String getApplyProductCode() {
		return applyProductCode;
	}
	public String getBackFlag() {
        return backFlag;
    }
    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }
    public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}
	public void setApplyProductCode(String applyProductCode) {
		this.applyProductCode = applyProductCode;
	}
	public String getVisitStartTime() {
		return visitStartTime;
	}
	public String getVisitFinishTime() {
		return visitFinishTime;
	}
	public void setVisitStartTime(String visitStartTime) {
		this.visitStartTime = visitStartTime;
	}
	public void setVisitFinishTime(String visitFinishTime) {
		this.visitFinishTime = visitFinishTime;
	}
    public String getIsOld() {
        return isOld;
    }
    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }
    public String getLoanCode() {
        return loanCode;
    }
    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }
	public String getOutApproveTime() {
        return outApproveTime;
    }
    public void setOutApproveTime(String outApproveTime) {
        this.outApproveTime = outApproveTime;
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
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	public String getMobelPhone() {
		return mobelPhone;
	}
	public void setMobelPhone(String mobelPhone) {
		this.mobelPhone = mobelPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBankProvinceCode() {
		return bankProvinceCode;
	}
	public void setBankProvinceCode(String bankProvinceCode) {
		this.bankProvinceCode = bankProvinceCode;
	}
	public String getBankCityCode() {
		return bankCityCode;
	}
	public void setBankCityCode(String bankCityCode) {
		this.bankCityCode = bankCityCode;
	}
	public String getBankAreaCode() {
		return bankAreaCode;
	}
	public void setBankAreaCode(String bankAreaCode) {
		this.bankAreaCode = bankAreaCode;
	}
	public String getKingOpenRespReason() {
		return kingOpenRespReason;
	}
	public void setKingOpenRespReason(String kingOpenRespReason) {
		this.kingOpenRespReason = kingOpenRespReason;
	}
	public String getKingProctolRespReason() {
		return kingProctolRespReason;
	}
	public void setKingProctolRespReason(String kingProctolRespReason) {
		this.kingProctolRespReason = kingProctolRespReason;
	}
	public String getKingStatus() {
		return kingStatus;
	}
	public void setKingStatus(String kingStatus) {
		this.kingStatus = kingStatus;
	}	
    public String getKingBankProvinceName() {
		return kingBankProvinceName;
	}
	public void setKingBankProvinceName(String kingBankProvinceName) {
		this.kingBankProvinceName = kingBankProvinceName;
	}
	public String getKingBankCityName() {
		return kingBankCityName;
	}
	public void setKingBankCityName(String kingBankCityName) {
		this.kingBankCityName = kingBankCityName;
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
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getGrantBackDate() {
		return grantBackDate;
	}
	public void setGrantBackDate(Date grantBackDate) {
		this.grantBackDate = grantBackDate;
	}
	public String getGrantBatchCode() {
		return grantBatchCode;
	}
	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}
	public String getTrustCashRtn() {
		return trustCashRtn;
	}
	public void setTrustCashRtn(String trustCashRtn) {
		this.trustCashRtn = trustCashRtn;
	}
	public String getTrustCashFailure() {
		return trustCashFailure;
	}
	public void setTrustCashFailure(String trustCashFailure) {
		this.trustCashFailure = trustCashFailure;
	}
	public String getTrustCash() {
		return trustCash;
	}
	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}
	public String getTrustGrantOutputStatus() {
		return trustGrantOutputStatus;
	}
	public void setTrustGrantOutputStatus(String trustGrantOutputStatus) {
		this.trustGrantOutputStatus = trustGrantOutputStatus;
	}
    /**
     * @return the lastDealTime
     */
    public String getLastDealTime() {
        return lastDealTime;
    }
    /**
     * @param lastDealTime the String lastDealTime to set
     */
    public void setLastDealTime(String lastDealTime) {
        this.lastDealTime = lastDealTime;
    }
	public String getLoanStatusCodeName() {
		return loanStatusCodeName;
	}
	public void setLoanStatusCodeName(String loanStatusCodeName) {
		this.loanStatusCodeName = loanStatusCodeName;
	}
	public String getTelesalesFlagName() {
		return telesalesFlagName;
	}
	public void setTelesalesFlagName(String telesalesFlagName) {
		this.telesalesFlagName = telesalesFlagName;
	}
	public String getKingStatusLabel() {
		return kingStatusLabel;
	}
	public void setKingStatusLabel(String kingStatusLabel) {
		this.kingStatusLabel = kingStatusLabel;
	}
	public String getChannelCodeLabel() {
		return channelCodeLabel;
	}
	public void setChannelCodeLabel(String channelCodeLabel) {
		this.channelCodeLabel = channelCodeLabel;
	}
    /**
     * @return the paperLessFlag
     */
    public String getPaperLessFlag() {
        return paperLessFlag;
    }
    /**
     * @param paperLessFlag the String paperLessFlag to set
     */
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
    public String getEnsureManFlag() {
        return ensureManFlag;
    }
    public void setEnsureManFlag(String ensureManFlag) {
        this.ensureManFlag = ensureManFlag;
    }
    /**
     * @return the bankIsRareword
     */
    public String getBankIsRareword() {
        return bankIsRareword;
    }
    /**
     * @param bankIsRareword the String bankIsRareword to set
     */
    public void setBankIsRareword(String bankIsRareword) {
        this.bankIsRareword = bankIsRareword;
    }

	public String getCustBankAccountName() {
		return custBankAccountName;
	}
	public void setCustBankAccountName(String custBankAccountName) {
		this.custBankAccountName = custBankAccountName;
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
	public String getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
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
    /**
     * @return the confirmSignDate
     */
    public String getConfirmSignDate() {
        return confirmSignDate;
    }
    /**
     * @param confirmSignDate the String confirmSignDate to set
     */
    public void setConfirmSignDate(String confirmSignDate) {
        this.confirmSignDate = confirmSignDate;
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
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Double getTotalGrantMoney() {
		return totalGrantMoney;
	}
	public void setTotalGrantMoney(Double totalGrantMoney) {
		this.totalGrantMoney = totalGrantMoney;
	}
	public Double getTotalGrantFailMoney() {
		return totalGrantFailMoney;
	}
	public void setTotalGrantFailMoney(Double totalGrantFailMoney) {
		this.totalGrantFailMoney = totalGrantFailMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewCoboName() {
		return newCoboName;
	}
	public void setNewCoboName(String newCoboName) {
		this.newCoboName = newCoboName;
	}
	public String getBestCoborrowerName() {
		return bestCoborrowerName;
	}
	public void setBestCoborrowerName(String bestCoborrowerName) {
		this.bestCoborrowerName = bestCoborrowerName;
	}
	public String getBestCoborrowerFlag() {
		return bestCoborrowerFlag;
	}
	public void setBestCoborrowerFlag(String bestCoborrowerFlag) {
		this.bestCoborrowerFlag = bestCoborrowerFlag;
	}
	public Double getFirstLendingMoney() {
		return firstLendingMoney;
	}
	public void setFirstLendingMoney(Double firstLendingMoney) {
		this.firstLendingMoney = firstLendingMoney;
	}
	public Double getLastGrantAmount() {
		return lastGrantAmount;
	}
	public void setLastGrantAmount(Double lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}
	public Double getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(Double feePetition) {
		this.feePetition = feePetition;
	}
	public Double getFeeCredit() {
		return feeCredit;
	}
	public void setFeeCredit(Double feeCredit) {
		this.feeCredit = feeCredit;
	}
	public Double getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(Double feeSum) {
		this.feeSum = feeSum;
	}
}
