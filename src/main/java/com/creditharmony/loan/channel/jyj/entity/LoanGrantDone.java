/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.entity.LoanGrantEx.java
 * @Create By 朱静越
 * @Create In 2015年12月1日 下午7:25:19
 */
/**
 * @Class Name LoanGrantEx
 * @author 朱静越
 * @Create In 2015年12月1日
 * 借款记录表的扩展类
 */
package com.creditharmony.loan.channel.jyj.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class LoanGrantDone extends DataEntity<LoanGrantDone> {
	private String[] contractCodes;
	 
	
	public String[] getContractCodes() {
		return contractCodes;
	}

	public void setContractCodes(String[] contractCodes) {
		this.contractCodes = contractCodes;
	}

	//序号
	private String sequenceNumber;
	// 放款id
	private String id;
	// 流程id
	private String applyId;
	// 借款主表id
	private String loanInfoId;
	// 合同编号6
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 60)
	private String contractCode;
	// 借款编号
	private String loanCode;
	// 中间人id
	private String midId;
	//
	private String dictLoanType;
	// 放款金额8
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 80)
	private BigDecimal grantAmount;
	// 放款失败金额
	private BigDecimal grantFailAmount;
	// 放款时间17
	@ExcelField(title = "放款时间", type = 0, align = 2, sort = 170)
	private Date lendingTime;
	// 放款时间上限
	private Date startDate;
	// 放款时间下限
	private Date endDate;
	// 提交时间上限
	private Date subStartDate;
	// 提交时间下限
	private Date subEndDate;
	// 放款人员编号
	private String lendingUserId;
	// 放款回执结果
	private String grantRecepicResult;
	// 放款方式
	private String dictLoanWay;
	// 失败原因
	private String grantFailResult;
	// 退回原因
	private String grantBackMes;
	// 审核专员18
	@ExcelField(title = "放款审核人员", type = 0, align = 2, sort = 180)
	private String checkEmpId;
	// 审核结果
	private String checkResult;
	// 审核时间
	private String checkTime;
	// 客户姓名1
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10)
	private String customerName;
	// 证件号码2
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 20)
	private String customerCertNum;
	// 标识19
	@ExcelField(title = "标识", type = 0, align = 2, sort = 190, dictType = "jk_channel_flag")
	private String loanFlag;
	// 是否电销20，新
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 200, dictType = "jk_telemarketing ")
	private String customerTelesalesFlag;
	// 共借人
	private String coboName;
	// 中间人姓名13
	@ExcelField(title = "放款账户", type = 0, align = 2, sort = 130)
	private String middleName;
	// 中间人开户行14
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 140)
	private String midBankName;
	// 中间人15
	@ExcelField(title = "账号", type = 0, align = 2, sort = 150)
	private String bankCardNo;
	// 用户的账户姓名
	private String bankAccountName;
	// 催收服务费
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 102)
	private BigDecimal urgeMoney;
	// 批借金额9,新
	@ExcelField(title = "批借金额", type = 0, align = 2, sort = 90)
	private BigDecimal auditAmount;
	// 信访费10，新
	@ExcelField(title = "信访费", type = 0, align = 2, sort = 100)
	private BigDecimal feePetition;
	// 产品类型11
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 110)
	private String productType;
	// 批借期限12
	@ExcelField(title = "批借期限", type = 0, align = 2, sort = 120)
	private String contractMonths;
	// 已划金额
	private BigDecimal urgeDecuteMoeny;
	// 已查账金额
	private BigDecimal grantAuditAmount;
	// 合同金额7
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 70)
	private BigDecimal contractAmount;


	
	// 首次放款金额
	@ExcelField(title = "首次放款金额", type = 0, align = 2, sort = 72)
	private BigDecimal firstGrantAmount;
	// 费用总金额
	@ExcelField(title = "费用总金额", type = 0, align = 2, sort = 74)
	private BigDecimal allFee;
	// 前期综合服务费
	@ExcelField(title = "前期综合服务费", type = 0, align = 2, sort = 76)
	private BigDecimal feeCount;
	// 加急费
	@ExcelField(title = "加急费", type = 0, align = 2, sort = 78)
	private BigDecimal feeExpedited;
	// 尾款放款金额
	@ExcelField(title = "尾款放款金额", type = 0, align = 2, sort = 104)
	private BigDecimal lastGrantAmount;
	 
	private BigDecimal totalGrantMoney;
	
	public BigDecimal getFirstGrantAmount() {
		return firstGrantAmount;
	}

	public void setFirstGrantAmount(BigDecimal firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}

	public BigDecimal getAllFee() {
		return allFee;
	}

	public void setAllFee(BigDecimal allFee) {
		this.allFee = allFee;
	}

	public BigDecimal getFeeCount() {
		return feeCount;
	}

	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = feeCount;
	}

	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}

	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}

	public BigDecimal getLastGrantAmount() {
		return lastGrantAmount;
	}

	public void setLastGrantAmount(BigDecimal lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}

	// 团队经理3
	@ExcelField(title = "团队经理", type = 0, align = 2, sort = 30)
	private String loanTeamManagerName;
	// 团队经理编号
	@ExcelField(title = "团队经理编号", type = 0, align = 2, sort = 35)
	private String loanTeamManagercode;
	// 借款状态
	private String dictLoanStatus;
	// 借款状态集合
	private List<String> dictLoanStatusList = new ArrayList<String>();
	// 门店编码16
	@ExcelField(title = "机构", type = 0, align = 2, sort = 160)
	private String storesCode;
	// 借款类型5
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 50)
	private String classType;
	// 客户经理4
	@ExcelField(title = "客户经理", type = 0, align = 2, sort = 40)
	private String loanManagerName;
	// 客户经理编号
	@ExcelField(title = "客户经理编号", type = 0, align = 2, sort = 45)
	private String loanManagercode;
	// 是否追加借
	private String dictIsAdditional;
	// 产品名称
	private String productName;
	// 产品Code
	private String productCode;
	// 是否加急21
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 210, dictType = "jk_urgent_flag")
	private String urgentFlag;

	private String dictStatus;
	// 冻结code
	private String frozenCode;
	// 冻结原因
	private String frozenReason;	
	// 规则
	private String rule;
	// 放款批次22
	@ExcelField(title = "放款批次", type = 0, align = 2, sort = 220, dictType = "jk_urgent_flag")
	private String grantPch;
	// 批次提交时间
	private Date submissionDate;
	// 提交批次23
	private String submissionBatch;
	// 提交时间24
	private Date submissionsDate;
	// TG和信借的区别
	private String tGFlag;

	private String storeName;

	private String coborrowerName;

	private String replyProductName;

	private String identityCode;

	private String replyMonth;

	private String contractMoney;

	private String lendingMoney;

	private String depositBank;

	private String bankBranchName;
	// 账号
	private String bankAccountNumber;

	private String kingStatus;

	private String loanStatusName;

	private String channelName;

	private String telesalesFlag;

	private String[] storeOrgId;
	// 委托提现
	private String trustCash;
	//模型
	private String model;
	
	private Date submitDeductTime;
	
	private Date grantBackDate;
	
	private String urgeDecuteMoney;
	
	private String enterpriseSerialno;
	
	// 新老标识
	private String loanInfoOldOrNewFlag;
	
	private String modifyId;
	
	private Date contractDueDay;
	
	private Date contractFactDay;
	
	private Date contractReplayDay;
	
	private String bankId;//开户行
	
	private String[] bankIds;
	
	private String bankName;//开户行
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	// 合同签订时间上限
	private Date htStartDate;
	// 合同签订时间下限
	private Date htEndDate;
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLoanInfoId() {
		return loanInfoId;
	}

	public void setLoanInfoId(String loanInfoId) {
		this.loanInfoId = loanInfoId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMidId() {
		return midId;
	}

	public void setMidId(String midId) {
		this.midId = midId;
	}

	public String getDictLoanType() {
		return dictLoanType;
	}

	public void setDictLoanType(String dictLoanType) {
		this.dictLoanType = dictLoanType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}

	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}

	public BigDecimal getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getLoanTeamManagercode() {
		return loanTeamManagercode;
	}

	public void setLoanTeamManagercode(String loanTeamManagercode) {
		this.loanTeamManagercode = loanTeamManagercode;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public List<String> getDictLoanStatusList() {
		return dictLoanStatusList;
	}

	public void setDictLoanStatusList(List<String> dictLoanStatusList) {
		this.dictLoanStatusList = dictLoanStatusList;
	}

	public String getStoresCode() {
		return storesCode;
	}

	public void setStoresCode(String storesCode) {
		this.storesCode = storesCode;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}

	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}

	public String getLoanManagerName() {
		return loanManagerName;
	}

	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}
	
	public String getLoanManagercode() {
		return loanManagercode;
	}

	public void setLoanManagercode(String loanManagercode) {
		this.loanManagercode = loanManagercode;
	}

	public String getDictIsAdditional() {
		return dictIsAdditional;
	}

	public void setDictIsAdditional(String dictIsAdditional) {
		this.dictIsAdditional = dictIsAdditional;
	}

	public BigDecimal getUrgeMoney() {
		return urgeMoney;
	}

	public void setUrgeMoney(BigDecimal urgeMoney) {
		this.urgeMoney = urgeMoney;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}

	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public BigDecimal getGrantFailAmount() {
		return grantFailAmount;
	}

	public void setGrantFailAmount(BigDecimal grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getLendingUserId() {
		return lendingUserId;
	}

	public void setLendingUserId(String lendingUserId) {
		this.lendingUserId = lendingUserId;
	}

	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}

	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}

	public String getGrantFailResult() {
		return grantFailResult;
	}

	public void setGrantFailResult(String grantFailResult) {
		this.grantFailResult = grantFailResult;
	}

	public String getGrantBackMes() {
		return grantBackMes;
	}

	public void setGrantBackMes(String grantBackMes) {
		this.grantBackMes = grantBackMes;
	}

	public String getCheckEmpId() {
		return checkEmpId;
	}

	public void setCheckEmpId(String checkEmpId) {
		this.checkEmpId = checkEmpId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getDictLoanWay() {
		return dictLoanWay;
	}

	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public String getFrozenCode() {
		return frozenCode;
	}

	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}

	public String getFrozenReason() {
		return frozenReason;
	}

	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}

	public String getGrantPch() {
		return grantPch;
	}

	public void setGrantPch(String grantPch) {
		this.grantPch = grantPch;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getReplyProductName() {
		return replyProductName;
	}

	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
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

	public String getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}

	public String getLendingMoney() {
		return lendingMoney;
	}

	public void setLendingMoney(String lendingMoney) {
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

	public String getKingStatus() {
		return kingStatus;
	}

	public void setKingStatus(String kingStatus) {
		this.kingStatus = kingStatus;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTelesalesFlag() {
		return telesalesFlag;
	}

	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}

	public BigDecimal getGrantAuditAmount() {
		return grantAuditAmount;
	}

	public void setGrantAuditAmount(BigDecimal grantAuditAmount) {
		this.grantAuditAmount = grantAuditAmount;
	}

	public String[] getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String[] storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}

	public String getSubmissionBatch() {
		return submissionBatch;
	}

	public void setSubmissionBatch(String submissionBatch) {
		this.submissionBatch = submissionBatch;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getSubStartDate() {
		return subStartDate;
	}

	public void setSubStartDate(Date subStartDate) {
		this.subStartDate = subStartDate;
	}

	public Date getSubEndDate() {
		return subEndDate;
	}

	public void setSubEndDate(Date subEndDate) {
		this.subEndDate = subEndDate;
	}

	public String gettGFlag() {
		return tGFlag;
	}

	public void settGFlag(String tGFlag) {
		this.tGFlag = tGFlag;
	}

    public Date getSubmissionsDate() {
		return submissionsDate;
	}

	public void setSubmissionsDate(Date submissionsDate) {
		this.submissionsDate = submissionsDate;
	}

	/**
     * @return the trustCash
     */
    public String getTrustCash() {
        return trustCash;
    }

    /**
     * @param trustCash the String trustCash to set
     */
    public void setTrustCash(String trustCash) {
        this.trustCash = trustCash;
    }

	public Date getSubmitDeductTime() {
		return submitDeductTime;
	}

	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
	}

	public Date getGrantBackDate() {
		return grantBackDate;
	}

	public void setGrantBackDate(Date grantBackDate) {
		this.grantBackDate = grantBackDate;
	}

	public String getUrgeDecuteMoney() {
		return urgeDecuteMoney;
	}

	public void setUrgeDecuteMoney(String urgeDecuteMoney) {
		this.urgeDecuteMoney = urgeDecuteMoney;
	}

	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Date getContractDueDay() {
		return contractDueDay;
	}

	public void setContractDueDay(Date contractDueDay) {
		this.contractDueDay = contractDueDay;
	}

	public Date getHtStartDate() {
		return htStartDate;
	}

	public void setHtStartDate(Date htStartDate) {
		this.htStartDate = htStartDate;
	}

	public Date getHtEndDate() {
		return htEndDate;
	}

	public void setHtEndDate(Date htEndDate) {
		this.htEndDate = htEndDate;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String[] getBankIds() {
		return bankIds;
	}

	public void setBankIds(String[] bankIds) {
		this.bankIds = bankIds;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}

	public BigDecimal getTotalGrantMoney() {
		return totalGrantMoney;
	}

	public void setTotalGrantMoney(BigDecimal totalGrantMoney) {
		this.totalGrantMoney = totalGrantMoney;
	}

}