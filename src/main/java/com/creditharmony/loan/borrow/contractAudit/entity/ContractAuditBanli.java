package com.creditharmony.loan.borrow.contractAudit.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;

public class ContractAuditBanli {
	private String imageUrl;//影像平台地址
	private String largeAmountImageUrl;//大额影像平台地址
	private String largeAmountFlag;//大额标识 0 - 非大额。
	private String applyId;// 查
	private String loanCode;//查
	private String contractCode;// 查
	private String frozenCode;//冻结原因字典 loan_info frozen_code 查
	private String upLimit;// 金信 渠道下设置 金信额度上限标识 1 说明达到上限
	private String jxVersion;//金信 渠道下设置 金信额度表 中的版本号 KINNOBU_QUOTA_LIMIT version
	private String limitId;//金信 渠道下设置 金信额度表 中的版本号 KINNOBU_QUOTA_LIMIT id
	private String jxId;// 怎么和 limitId 取值一样？
//	private String contractAmount;//合同金额
//	private String feePaymentAmount;//实放金额 t_jk_contract_fee fee_payment_amount
//	private String midId;//中间人表主键 contract mid_id
//	private String auditAmount;//批复金额 contract audit_amount
//	private String contractMonths;//批复期数 contract contract_months
//	private String contractMonthRepayAmount;//应还本息 contract contract_month_repay_amount(月还)
//	private String contractDueDay;//合同签订日期contract contract_due_day
//	private String monthPayTotalAmount;//月还金额 contract contract_month_repay_total
//	private String contractReplayDay;//起始还款日期contract contract_replay_day
//	private String contractExpectAmount;//预计还款总金额 contract  contract_expect_amount
	
	private Contract contract;//合同信息
    private LoanBank loanBank; // 开户行    
	private ContractFee ctrFee;//费率信息
	private String item_distance;//外访距离
	private String loanFlag;//渠道标识
	private String trusteeshipFlag;//是否开户  判断 loan_customer trusteeship_no金账户是否为空 空-0-没开户 1-非空-已开户
	private String modelName;//模式 loan_info model = 1  tg else 非 tg
	private String productName;//产品名称
	private String applyOrgName;//门店名称
	private String composePhotoId;//     // 合成图片ID
	private String appSignFlag;//app签字
	private String customerId;//客户表id
	private String mainLoaner;//客户姓名
	private String idValidMessage;
	private String mainCertNum;//证件号码
	private String mainCertSexName;//性别
	private String auditEnsureName;//
	private String auditLegalMan;//法定代表人
	private String ensuremanBusinessPlace;//保证人实际经营场所
	private String maddressName;//待省市区保证人实际经营场所
	private String outside_flag;//外访标识
	private String riskLevel;//风险等级
	private Integer billDay;//账单日
	private Float idValidScore;//公安验证分数
	private String loanStoreOrgId;//门店id
	 private List<LoanCoborrower> coborrowers;//
	 private String loanFlagName;//渠道描述
	 private List<ContractFile> files;
	 private String loanInfoOldOrNewFlag;//申请表新旧 标识  1-新
	 
	 
	 public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public List<ContractFile> getFiles() {
		return files;
	}
	public void setFiles(List<ContractFile> files) {
		this.files = files;
	}
	//	 private String mainCertTypeName;//证明类型描述
//	 
//	 
//	
//	public String getMainCertTypeName() {
//		return mainCertTypeName;
//	}
//	public void setMainCertTypeName(String mainCertTypeName) {
//		this.mainCertTypeName = mainCertTypeName;
//	}
	public String getLoanFlagName() {
		return loanFlagName;
	}
	public void setLoanFlagName(String loanFlagName) {
		this.loanFlagName = loanFlagName;
	}
	public List<LoanCoborrower> getCoborrowers() {
		return coborrowers;
	}
	public void setCoborrowers(List<LoanCoborrower> coborrowers) {
		this.coborrowers = coborrowers;
	}
	public String getLoanStoreOrgId() {
		return loanStoreOrgId;
	}
	public void setLoanStoreOrgId(String loanStoreOrgId) {
		this.loanStoreOrgId = loanStoreOrgId;
	}
	public Float getIdValidScore() {
		return idValidScore;
	}
	public void setIdValidScore(Float idValidScore) {
		this.idValidScore = idValidScore;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLargeAmountImageUrl() {
		return largeAmountImageUrl;
	}
	public void setLargeAmountImageUrl(String largeAmountImageUrl) {
		this.largeAmountImageUrl = largeAmountImageUrl;
	}
	public String getLargeAmountFlag() {
		return largeAmountFlag;
	}
	public void setLargeAmountFlag(String largeAmountFlag) {
		this.largeAmountFlag = largeAmountFlag;
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getFrozenCode() {
		return frozenCode;
	}
	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}
	public String getUpLimit() {
		return upLimit;
	}
	public void setUpLimit(String upLimit) {
		this.upLimit = upLimit;
	}
	public String getJxVersion() {
		return jxVersion;
	}
	public void setJxVersion(String jxVersion) {
		this.jxVersion = jxVersion;
	}
	public String getLimitId() {
		return limitId;
	}
	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}
	public String getJxId() {
		return jxId;
	}
	public void setJxId(String jxId) {
		this.jxId = jxId;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public LoanBank getLoanBank() {
		return loanBank;
	}
	public void setLoanBank(LoanBank loanBank) {
		this.loanBank = loanBank;
	}
	public ContractFee getCtrFee() {
		return ctrFee;
	}
	public void setCtrFee(ContractFee ctrFee) {
		this.ctrFee = ctrFee;
	}
	public String getItem_distance() {
		return item_distance;
	}
	public void setItem_distance(String item_distance) {
		this.item_distance = item_distance;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getTrusteeshipFlag() {
		return trusteeshipFlag;
	}
	public void setTrusteeshipFlag(String trusteeshipFlag) {
		this.trusteeshipFlag = trusteeshipFlag;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getApplyOrgName() {
		return applyOrgName;
	}
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	public String getComposePhotoId() {
		return composePhotoId;
	}
	public void setComposePhotoId(String composePhotoId) {
		this.composePhotoId = composePhotoId;
	}
	public String getAppSignFlag() {
		return appSignFlag;
	}
	public void setAppSignFlag(String appSignFlag) {
		this.appSignFlag = appSignFlag;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMainLoaner() {
		return mainLoaner;
	}
	public void setMainLoaner(String mainLoaner) {
		this.mainLoaner = mainLoaner;
	}
	public String getIdValidMessage() {
		return idValidMessage;
	}
	public void setIdValidMessage(String idValidMessage) {
		this.idValidMessage = idValidMessage;
	}
	public String getMainCertNum() {
		return mainCertNum;
	}
	public void setMainCertNum(String mainCertNum) {
		this.mainCertNum = mainCertNum;
	}
	public String getMainCertSexName() {
		return mainCertSexName;
	}
	public void setMainCertSexName(String mainCertSexName) {
		this.mainCertSexName = mainCertSexName;
	}
	public String getAuditEnsureName() {
		return auditEnsureName;
	}
	public void setAuditEnsureName(String auditEnsureName) {
		this.auditEnsureName = auditEnsureName;
	}
	public String getAuditLegalMan() {
		return auditLegalMan;
	}
	public void setAuditLegalMan(String auditLegalMan) {
		this.auditLegalMan = auditLegalMan;
	}
	public String getEnsuremanBusinessPlace() {
		return ensuremanBusinessPlace;
	}
	public void setEnsuremanBusinessPlace(String ensuremanBusinessPlace) {
		this.ensuremanBusinessPlace = ensuremanBusinessPlace;
	}
	public String getMaddressName() {
		return maddressName;
	}
	public void setMaddressName(String maddressName) {
		this.maddressName = maddressName;
	}
	public String getOutside_flag() {
		return outside_flag;
	}
	public void setOutside_flag(String outside_flag) {
		this.outside_flag = outside_flag;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	 
	
	public Integer getBillDay() {
		return billDay;
	}
	public void setBillDay(Integer billDay) {
		this.billDay = billDay;
	}


	private String oldLoanCode;//拆分前loancode
	public String getOldLoanCode() {
		return oldLoanCode;
	}
	public void setOldLoanCode(String oldLoanCode) {
		this.oldLoanCode = oldLoanCode;
	}
	
	private String isSplit;//是否拆分
	public String getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}
	
}
