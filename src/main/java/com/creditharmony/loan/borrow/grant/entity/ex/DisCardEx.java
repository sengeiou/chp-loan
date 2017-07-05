package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;

public class DisCardEx {
    // 合同编号
    @ExcelField(title = "信访费", type = 0, align = 2, sort = 150, groups = 1)
	private double feePetition;//信访费
    // 合同编号
    @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 160, groups = 1)
	private double feeUrgedService;//催收服务费
    // 合同编号
    @ExcelField(title = "总费用", type = 0, align = 2, sort = 170, groups = 1)
	private double feeCount;//总费用
	
	
	public double getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(double feePetition) {
		this.feePetition = feePetition;
	}
	public double getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(double feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public double getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(double feeCount) {
		this.feeCount = feeCount;
	}
	// id
	private String id;
	// applyId
	private String applyId;
    // 合同编号
    @ExcelField(title = "合同编号", type = 0, align = 2, sort = 1, groups = 1)
    private String contractCode;
    // 客户姓名
    @ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10, groups = 1)
    private String customerName;
    // 省份
    private String provinceName;
    // 城市
    private String cityName;
    // 门店编号
    private String storeCode;
    // 门店
    @ExcelField(title = "门店名称", type = 0, align = 2, sort = 40, groups = 1)
    private String storeName;
	// 批复产品
    @ExcelField(title = "借款产品", type = 0, align = 2, sort = 60, groups = 1)
    private String replyProductName;
    // 借款状态
    @ExcelField(title = "借款状态", type = 0, align = 2, sort = 120, groups = 1)
    private String loanStatusName;
    
    private String loanStatusCodeName;
    //借款狀態
    private String loanStatusCode;
    // 批复分期(批借期限)
    @ExcelField(title = "批借期限", type = 0, align = 2, sort = 90, groups = 1)
    private Integer replyMonth;
    // 加急标识
    @ExcelField(title = "是否加急", type = 0, align = 2, sort = 130, groups = 1,dictType="jk_urgent_flag")
    private String urgentFlag;
    // 是否电销
    @ExcelField(title = "是否电销", type = 0, align = 2, sort = 110, groups = 1,dictType="jk_telemarketing")
    private String telesalesFlag;
    // 渠道标识
    private String channelCode;
    // 渠道标识Name
    @ExcelField(title = "渠道", type = 0, align = 2, sort = 120, groups = 1)
    private String channelName;
    // 证件号码
    @ExcelField(title = "证件号码", type = 0, align = 2, sort = 20, groups = 1)
    private String identityCode;
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
    // 借款类型
    @ExcelField(title = "借款类型", type = 0, align = 2, sort = 50,value="信借", groups = 1)
    private String loanType;
    // 放款途径
    private String lendingWayName;
    // 退回原因
    private String backReason;
    // 借款编号
    private String loanCode;
	// 是否冻结 1 冻结 0 未冻结
    private String frozenFlag;
    // 原因描述
    private String frozenReason;
    // 提交批次
    private String submissionBatch;
    // 提交时间
    private String submissionDate;
    // 上一个节点操作时间
    private String lastDealTime;
    // 是否加盖失败
    private String signUpFlag; 
    // 是否有保证人
    private String ensureManFlag;
    // 模式
    private String model;
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    // 回访状态
    private String revisitStatus;
    //门店组织机构id
    private String storeOrgId;
    // 放款总金额
    private Double totalGrantMoney;
    //拆分标记
    private String issplit;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	public String getLoanStatusCodeName() {
		return loanStatusCodeName;
	}
	public void setLoanStatusCodeName(String loanStatusCodeName) {
		this.loanStatusCodeName = loanStatusCodeName;
	}
	public String getLoanStatusCode() {
		return loanStatusCode;
	}
	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
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
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLendingWayName() {
		return lendingWayName;
	}
	public void setLendingWayName(String lendingWayName) {
		this.lendingWayName = lendingWayName;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	public String getLastDealTime() {
		return lastDealTime;
	}
	public void setLastDealTime(String lastDealTime) {
		this.lastDealTime = lastDealTime;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}
	public String getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public Double getTotalGrantMoney() {
		return totalGrantMoney;
	}
	public void setTotalGrantMoney(Double totalGrantMoney) {
		this.totalGrantMoney = totalGrantMoney;
	}
	public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
}
