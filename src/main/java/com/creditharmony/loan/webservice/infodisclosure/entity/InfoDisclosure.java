package com.creditharmony.loan.webservice.infodisclosure.entity;

import java.math.BigDecimal;
import java.util.Date;

public class InfoDisclosure {

	private String contractCode;//合同编号
	private String realyUse;//借款用途	
	private BigDecimal contractAmount;//合同金额	
	private BigDecimal feeAllRaio;//利率	
	private String contractMonths;//审批分期	
	private String customerName;//客户姓名	
	private String customerSex;//客户性别	
	private String customerBirthday;//出生日期	
	private String dictEducation;//学历
	private String dictMarry;//婚姻状况
	private String customerAddress;//居住地址
	private String dictCompIndustry;//行业
	private String compPost;//职务
	private String compAddress;//单位地址
	private String compWorkExperience;//工作年限
	private BigDecimal annualIncome;//年收入
	private String zczmPledgeFlag;//房产是否抵押
	private String pledgeFlag;//车产是否抵押
	private String loanCode;//借款编号
	private BigDecimal loanApplyAmount;//借款金额
	private String dictRepayMethod;//还款方式
	private String dictCustomerDiff;//还款来源
	private Long loanApplicationCount;//申请借款笔数
	private Long successfulLoanCount;//成功借款笔数
	private Long overdueCount;//逾期次数 
	private BigDecimal overduePrincipal;//逾期本金
	private String identityAuthentication;//身份认证
	private String creditReport;//信用报告
	private String bankCardInfo;//银行卡信息
	private String incomeCertification;//收入认证
	private String contactInfo;//联系人资料
	private String fieldCertification;//实地认证	
	private String loanDescription;//借款描述
	private String riskLevel;//风险等级

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getRealyUse() {
		return realyUse;
	}

	public void setRealyUse(String realyUse) {
		this.realyUse = realyUse;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getFeeAllRaio() {
		return feeAllRaio;
	}

	public void setFeeAllRaio(BigDecimal feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}

	public String getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public String getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getDictEducation() {
		return dictEducation;
	}

	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}

	public String getDictMarry() {
		return dictMarry;
	}

	public void setDictMarry(String dictMarry) {
		this.dictMarry = dictMarry;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getDictCompIndustry() {
		return dictCompIndustry;
	}

	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry;
	}

	public String getCompPost() {
		return compPost;
	}

	public void setCompPost(String compPost) {
		this.compPost = compPost;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}

	public String getCompWorkExperience() {
		return compWorkExperience;
	}

	public void setCompWorkExperience(String compWorkExperience) {
		this.compWorkExperience = compWorkExperience;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getZczmPledgeFlag() {
		return zczmPledgeFlag;
	}

	public void setZczmPledgeFlag(String zczmPledgeFlag) {
		this.zczmPledgeFlag = zczmPledgeFlag;
	}

	public String getPledgeFlag() {
		return pledgeFlag;
	}

	public void setPledgeFlag(String pledgeFlag) {
		this.pledgeFlag = pledgeFlag;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}

	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}

	public String getDictRepayMethod() {
		return dictRepayMethod;
	}

	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}

	public String getDictCustomerDiff() {
		return dictCustomerDiff;
	}

	public void setDictCustomerDiff(String dictCustomerDiff) {
		this.dictCustomerDiff = dictCustomerDiff;
	}

	public Long getLoanApplicationCount() {
		return loanApplicationCount;
	}

	public void setLoanApplicationCount(Long loanApplicationCount) {
		this.loanApplicationCount = loanApplicationCount;
	}

	public Long getSuccessfulLoanCount() {
		return successfulLoanCount;
	}

	public void setSuccessfulLoanCount(Long successfulLoanCount) {
		this.successfulLoanCount = successfulLoanCount;
	}

	public Long getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(Long overdueCount) {
		this.overdueCount = overdueCount;
	}

	public BigDecimal getOverduePrincipal() {
		return overduePrincipal;
	}

	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}

	public String getIdentityAuthentication() {
		return identityAuthentication;
	}

	public void setIdentityAuthentication(String identityAuthentication) {
		this.identityAuthentication = identityAuthentication;
	}

	public String getCreditReport() {
		return creditReport;
	}

	public void setCreditReport(String creditReport) {
		this.creditReport = creditReport;
	}

	public String getBankCardInfo() {
		return bankCardInfo;
	}

	public void setBankCardInfo(String bankCardInfo) {
		this.bankCardInfo = bankCardInfo;
	}

	public String getIncomeCertification() {
		return incomeCertification;
	}

	public void setIncomeCertification(String incomeCertification) {
		this.incomeCertification = incomeCertification;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getFieldCertification() {
		return fieldCertification;
	}

	public void setFieldCertification(String fieldCertification) {
		this.fieldCertification = fieldCertification;
	}

	public String getLoanDescription() {
		return loanDescription;
	}

	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
}
