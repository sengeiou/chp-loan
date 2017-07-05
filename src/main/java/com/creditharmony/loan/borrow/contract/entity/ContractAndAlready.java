package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 借款人合同信息-我的已办
 * @Class Name ContractAndAlready
 * @author shenawei
 * @Create In 2016年11月2日
 */
@SuppressWarnings("serial")
public class ContractAndAlready extends DataEntity<ContractAndAlready>{
	// 借款编码
	private String loanCode;
	//合同编号 
	private String contractCode;
	//合同版本号 
 	private String contractVersion;
 	//合同版本号名称
 	private String contractVersionLabel;
	//共借人
 	private String coboName;
 	//客户姓名 
 	private String loanCustomerName;
 	//省份  
 	private String storeProviceName;
 	//城市 
 	private String storeCityName; 
 	// 门店 
 	private String storeCode;
 	// 门店
 	private String[] storeName;
 
 	private String[] storeOrgId;
 	//状态 
 	private String dictLoanStatus;
 	//产品 
 	private String productType;
 	//批复金额 
 	private BigDecimal loanAuditAmount;
 	//批复期数 
 	private Integer loanAuditMonth;
 	//加急标识 
 	private String loanUrgentFlag;
 	//团队经理 
 	private String loanTeamManagerName;
 	//客户经理 
 	private String loanManagerName;
 	//进件时间 
 	private Date customerIntoTime;
 	//标识是否电销 
 	private String loanIsPhone;
    //渠道标示
    private String loanFlag;
    //借款模式
    private String model;
    //借款模式名称
    private String modelLabel;
    //风险等级
    private String riskLevel;
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面   loaninfo 
    private String loanInfoOldOrNewFlag;
    //无纸化标识
    private String paperlessFlag;
    //汇诚审批时间
    private Date loanAuditTime;
    //是否登记失败
    private String isRegister;
    // 是否追加借 loaninfo
    private String dictIsAdditional;
    //身份证号码
    private String customerCertNum;
    //来源系统
    private String dictSourceType;
    //登陆用户
    private String user;
    //门店上传时间
    private String operateTime;
    //退回原因
    private String contractBackResult;
    //上调标识
    private String loanRaiseFlag;
    //签约平台
    private String bankSigningPlatform;
    //最后审核时间
    private String auditingTime;
    
    //最后审核时间 start
    private String auditingTimeStart;
    //最后审核时间 end 
    private String auditingTimeEnd;
    
    
	public String getAuditingTimeStart() {
		return auditingTimeStart;
	}
	public void setAuditingTimeStart(String auditingTimeStart) {
		this.auditingTimeStart = auditingTimeStart;
	}
	public String getAuditingTimeEnd() {
		return auditingTimeEnd;
	}
	public void setAuditingTimeEnd(String auditingTimeEnd) {
		this.auditingTimeEnd = auditingTimeEnd;
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
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getContractVersionLabel() {
		return contractVersionLabel;
	}
	public void setContractVersionLabel(String contractVersionLabel) {
		this.contractVersionLabel = contractVersionLabel;
	}
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getStoreProviceName() {
		return storeProviceName;
	}
	public void setStoreProviceName(String storeProviceName) {
		this.storeProviceName = storeProviceName;
	}
	public String getStoreCityName() {
		return storeCityName;
	}
	public void setStoreCityName(String storeCityName) {
		this.storeCityName = storeCityName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String[] getStoreName() {
		return storeName;
	}
	public void setStoreName(String[] storeName) {
		this.storeName = storeName;
	}
	public String[] getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String[] storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getLoanAuditAmount() {
		return loanAuditAmount;
	}
	public void setLoanAuditAmount(BigDecimal loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}
	public Integer getLoanAuditMonth() {
		return loanAuditMonth;
	}
	public void setLoanAuditMonth(Integer loanAuditMonth) {
		this.loanAuditMonth = loanAuditMonth;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
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
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
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
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getPaperlessFlag() {
		return paperlessFlag;
	}
	public void setPaperlessFlag(String paperlessFlag) {
		this.paperlessFlag = paperlessFlag;
	}
	public Date getLoanAuditTime() {
		return loanAuditTime;
	}
	public void setLoanAuditTime(Date loanAuditTime) {
		this.loanAuditTime = loanAuditTime;
	}
	public String getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}
	public String getDictIsAdditional() {
		return dictIsAdditional;
	}
	public void setDictIsAdditional(String dictIsAdditional) {
		this.dictIsAdditional = dictIsAdditional;
	}
	
	 public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getContractBackResult() {
		return contractBackResult;
	}
	public void setContractBackResult(String contractBackResult) {
		this.contractBackResult = contractBackResult;
	}
	public String getLoanRaiseFlag() {
		return loanRaiseFlag;
	}
	public void setLoanRaiseFlag(String loanRaiseFlag) {
		this.loanRaiseFlag = loanRaiseFlag;
	}
	
	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}
	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}
	
	public String getAuditingTime() {
		return auditingTime;
	}
	public void setAuditingTime(String auditingTime) {
		this.auditingTime = auditingTime;
	}
	@Override
	public String toString() {
		return "ContractAndAlready [contractCode=" + contractCode
				+ ", contractVersion=" + contractVersion + ", coboName="
				+ coboName + ", loanCustomerName=" + loanCustomerName
				+ ", storeProviceName=" + storeProviceName + ", storeCityName="
				+ storeCityName + ", storeCode=" + storeCode
				+ ", dictLoanStatus=" + dictLoanStatus + ", productType="
				+ productType + ", loanAuditAmount=" + loanAuditAmount
				+ ", loanAuditMonth=" + loanAuditMonth + ", loanUrgentFlag="
				+ loanUrgentFlag + ", loanTeamManagerName="
				+ loanTeamManagerName + ", loanManagerName=" + loanManagerName
				+ ", customerIntoTime=" + customerIntoTime + ", loanIsPhone="
				+ loanIsPhone + ", loanFlag=" + loanFlag
				+ ", dictIsAdditional=" + dictIsAdditional+",loanAuditTime="+loanAuditTime+",user="+user
				+",operateTime="+operateTime+",contractBackResult="+contractBackResult
				+",loanRaiseFlag="+loanRaiseFlag+",bankSigningPlatform="+bankSigningPlatform
				+",auditingTime="+auditingTime
				+ "]";
	}
    
}
