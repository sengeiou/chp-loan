package com.creditharmony.loan.channel.goldcredit.view;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 汇总表
 * Description:
 * @class Summary
 * @author wengsi 
 * @date 2017年6月5日下午5:59:54
 */
@SuppressWarnings("serial")
public class Summary extends DataEntity<Summary> {
	private String orgName; //门店名称
	private String contractCode; // 合同编号
	private String customerName; //客户姓名
	private String customerCertNum;//身份证号
	private String productName;//产品种类
	private String loanMonths; //期数
	private String feeLoanRate;    //借款利率
	private String feeAllRaio;// 总费率
	private String feeUrgedService; //催收服务费
	private String loanAuditAmount; // 批复金额 
	private String contractAmount; // 合同金额
	private String grantAmount; // 实放金额 
	private String feeConsult;  // 外访费
	private String riskLevel;  //风险等级
	private String bankName; // 开户行 
	private String dictLoanStatus;// 借款状态
	private String loanUrgentFlag;  //加急标识
	private String contractVersion; // 合同版本号      
	private String bestCoborrower;   //是否有保证人
	private String customerTelesalesFlag;// 是否电销 1 是 0 否
	private String loanFlag; // 渠道
	private String model;  //模式
	private String lendingTime;  // 放款日期    
	private String introductionTime;//推送日期  现在是批复日期 
	private String issplit; //1是联合
	private String id; 
	private String applyId; 
	private String loanCode;
	
	private Date referralsDate; // 推送日期
	
	private String referralsDates; //推送日期
	
	private String storesName;
	
	private Date gtantEndDate; //放款结算日期
	private Date grantDate; // 放款开始日期
	
	private String logo; // 渠道
	
	private String  dataStatus;  // 数据状态
	
	private String storeOrgId;
	
	private String loaninfoOldornewFlag;// 判断是否有自然人保证人
	
	private String farenCoborrower; // 法人保证人
	
	private String feeCount; // 前期综合服务费
	
	private String contractMonthRepayTotal; // 月还总金额
	
	private String fenxianChengdu; // 合同日期
	
	private String contractFactDay; // 首期还款日
	
	private String contractReplayDay;// 首期还款日
	
	private String feeExpedited; // 加急费

	public String getOrgName() {
		return orgName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public String getProductName() {
		return productName;
	}
	public String getLoanMonths() {
		return loanMonths;
	}
	public String getFeeLoanRate() {
		return feeLoanRate;
	}
	public String getFeeAllRaio() {
		return feeAllRaio;
	}
	public String getFeeUrgedService() {
		return feeUrgedService;
	}
	public String getLoanAuditAmount() {
		return loanAuditAmount;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public String getGrantAmount() {
		return grantAmount;
	}
	public String getFeeConsult() {
		return feeConsult;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public String getBankName() {
		return bankName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public String getModel() {
		return model;
	}
	public String getLendingTime() {
		return lendingTime;
	}
	public String getIntroductionTime() {
		return introductionTime;
	}
	public String getIssplit() {
		return issplit;
	}
	public String getBestCoborrower() {
		return bestCoborrower;
	}
	public String getId() {
		return id;
	}
	public String getApplyId() {
		return applyId;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setLoanMonths(String loanMonths) {
		this.loanMonths = loanMonths;
	}
	public void setFeeLoanRate(String feeLoanRate) {
		this.feeLoanRate = feeLoanRate;
	}
	public void setFeeAllRaio(String feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}
	public void setFeeUrgedService(String feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public void setLoanAuditAmount(String loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}
	public void setFeeConsult(String feeConsult) {
		this.feeConsult = feeConsult;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}
	public void setIntroductionTime(String introductionTime) {
		this.introductionTime = introductionTime;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Date getGtantEndDate() {
		return gtantEndDate;
	}
	public Date getGrantDate() {
		return grantDate;
	}
	public void setGtantEndDate(Date gtantEndDate) {
		this.gtantEndDate = gtantEndDate;
	}
	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public Date getReferralsDate() {
		return referralsDate;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setReferralsDate(Date referralsDate) {
		this.referralsDate = referralsDate;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public String getLoaninfoOldornewFlag() {
		return loaninfoOldornewFlag;
	}
	public void setLoaninfoOldornewFlag(String loaninfoOldornewFlag) {
		this.loaninfoOldornewFlag = loaninfoOldornewFlag;
	}
	public String getFarenCoborrower() {
		return farenCoborrower;
	}
	public void setFarenCoborrower(String farenCoborrower) {
		this.farenCoborrower = farenCoborrower;
	}
	public String getFeeCount() {
		return feeCount;
	}
	public String getContractMonthRepayTotal() {
		return contractMonthRepayTotal;
	}
	public String getFenxianChengdu() {
		return fenxianChengdu;
	}
	public String getContractFactDay() {
		return contractFactDay;
	}
	public String getContractReplayDay() {
		return contractReplayDay;
	}
	public String getFeeExpedited() {
		return feeExpedited;
	}
	public void setFeeCount(String feeCount) {
		this.feeCount = feeCount;
	}
	public void setContractMonthRepayTotal(String contractMonthRepayTotal) {
		this.contractMonthRepayTotal = contractMonthRepayTotal;
	}
	public void setFenxianChengdu(String fenxianChengdu) {
		this.fenxianChengdu = fenxianChengdu;
	}
	public void setContractFactDay(String contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public void setContractReplayDay(String contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}
	public void setFeeExpedited(String feeExpedited) {
		this.feeExpedited = feeExpedited;
	}
	public String getReferralsDates() {
		return referralsDates;
	}
	public void setReferralsDates(String referralsDates) {
		this.referralsDates = referralsDates;
	}
	
	
}
