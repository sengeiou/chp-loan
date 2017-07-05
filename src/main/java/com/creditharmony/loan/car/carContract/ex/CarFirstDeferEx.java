package com.creditharmony.loan.car.carContract.ex;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 需展期列表
 * @Class Name CarFirstDeferEx
 * @author ganquan
 * @Create In 2016年3月4日
 */
public class CarFirstDeferEx {
	//applyId
	private String applyId;
	//合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//申请金额
	private BigDecimal loanApplyAmount; 
	//借款期限
	private String contractMonths;
	//产品类型
	private String productType;
	//评估金额
	private String storeAssessAmount;
	//批借金额
	private String auditAmount;
	//申请日期
	private Date loanApplyTime;
	//团队经理
	private String consTeamManagerName;
	//客户经理
	private String managerName;
	//面审人员
	private String reviewMeet;
	//借款状态
	private String dictLoanStatus;
	//合同到期提醒
	private Date contractEndDay;
	//车辆号码
	private String plateNumbers;
	//是否电销
	private String telesalesFlag;
	//标识
	private String conditionalThroughFlag;
	//身份证号
	private String customerCertNum;
	//门店
	private String storeName;
	//已展期次数
    private int extendNum;
    // 渠道
    private String loanFlag;
    // 机构id
    private String storeCode;
    
	public int getExtendNum() {
		return extendNum;
	}
	public void setExtendNum(int extendNum) {
		this.extendNum = extendNum;
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
	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(String storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getConsTeamManagerName() {
		return consTeamManagerName;
	}
	public void setConsTeamManagerName(String consTeamManagerName) {
		this.consTeamManagerName = consTeamManagerName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getReviewMeet() {
		return reviewMeet;
	}
	public void setReviewMeet(String reviewMeet) {
		this.reviewMeet = reviewMeet;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public String getTelesalesFlag() {
		return telesalesFlag;
	}
	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(String auditAmount) {
		this.auditAmount = auditAmount;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
}
