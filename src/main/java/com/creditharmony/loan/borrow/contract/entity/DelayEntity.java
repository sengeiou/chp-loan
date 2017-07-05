package com.creditharmony.loan.borrow.contract.entity;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
@SuppressWarnings("serial")
public class DelayEntity extends DataEntity<DelayEntity>{
	
	private String applyId;

	//借款编号
	private String loanCode;
	
	//合同版本
	@ExcelField(title = "版本号", type = 0, align = 2, sort = 100)
	private String contractVersion;
	//客户姓名 
	@ExcelField(title = "客户姓名 ", type = 0, align = 2, sort = 10)
	private String customerName;
	//身份证号
	@ExcelField(title = "身份证号码 ", type = 0, align = 2, sort = 20)
	private String customerCertNum;
	//合同编号 
	@ExcelField(title = "合同编号 ", type = 0, align = 2, sort = 30)
	private String contractCode;
	//共借人
 	private String coboName;
 	//自然保证人
 	private String bestCoborrower;
 	//省份
 	private String provinceName;
 	//城市
 	private String cityName;
 	//门店
 	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 40)
 	private String storeName;
 	
 	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 50)
 	private String contractAmount;
 	
 	private String storeNames;
 	
 	private String storeOrgIds;
 	
 	private String[] storeOrgIdArray;
 	//产品名称
 	private String productName;
 	//产品
 	private String productType;
 	//状态
 	private String dictLoanStatus;
 	
 	private String[] dictLoanStatusArray = {"60","61","63","64","65"};;
 	
 	private String dictLoanStatusName;
 	//批复金额
 	private String loanAuditAmount;
 	//批复期数
 	private String loanAuditMonths;
 	//是否电销
 	private String customerTelesalesFlag;
 	//进件时间
 	private Date customerIntoTime;
 	//汇诚审批日期
 	private Date loanAuditTime;
 	
 	private Date startLoanAuditTime;
 	
 	private Date endLoanAuditTime;
 	//确认签署日期
 	private Date signconfirmTime;
 	
 	private Date startSignconfirmTime;
 	
 	private Date endSignconfirmTime;
 	
 	@ExcelField(title = "合同签订日期", type = 0, align = 2, sort = 60)
 	private Date contractFactDay;
 	
 	//费率审核日期
 	@ExcelField(title = "费率审核日期", type = 0, align = 2, sort = 70)
 	private Date rateTime;
 	
 	private Date startTateTime;
 	
 	private Date endTateTime;
 	
 	@ExcelField(title = "合同制作日期", type = 0, align = 2, sort = 70)
 	private Date contractMadeTime;
 	
 	//合同审核日期
 	private Date contractTime;
 	
 	private Date startContractTime;
 	
 	private Date endContractTime;
 	
 	//是否冻结
 	private String frozenCode;
 	//加急标识
 	private String loanUrgentFlag;
 	//模式
 	@ExcelField(title = "模式", type = 0, align = 2, sort = 80)
 	private String model;
 	//渠道
 	
 	private String loanFlag;
 	@ExcelField(title = "渠道", type = 0, align = 2, sort = 90)
 	private String loanFlagName;
 	//风险等级
 	private String riskLevel;
 	
 	private String loaninfoOldornewFlag;
 	
 	@ExcelField(title = "延期后截止日期", type = 0, align = 2, sort = 120)
 	private Date postponeTime;
 	
 	private Date timeOutPointTime;
 	
 	
 	@ExcelField(title = "延期天数", type = 0, align = 2, sort = 110)
 	private String postponeDays;
 	
 	private String ids;
 	
 	private String[] contractCodeArray;
 	
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
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getBestCoborrower() {
		return bestCoborrower;
	}
	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getLoanAuditAmount() {
		return loanAuditAmount;
	}
	public void setLoanAuditAmount(String loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}
	public String getLoanAuditMonths() {
		return loanAuditMonths;
	}
	public void setLoanAuditMonths(String loanAuditMonths) {
		this.loanAuditMonths = loanAuditMonths;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public Date getLoanAuditTime() {
		return loanAuditTime;
	}
	public void setLoanAuditTime(Date loanAuditTime) {
		this.loanAuditTime = loanAuditTime;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public Date getRateTime() {
		return rateTime;
	}
	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}
	public Date getContractTime() {
		return contractTime;
	}
	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}
	public String getFrozenCode() {
		return frozenCode;
	}
	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoaninfoOldornewFlag() {
		return loaninfoOldornewFlag;
	}
	public void setLoaninfoOldornewFlag(String loaninfoOldornewFlag) {
		this.loaninfoOldornewFlag = loaninfoOldornewFlag;
	}
	public String getStoreNames() {
		return storeNames;
	}
	public void setStoreNames(String storeNames) {
		this.storeNames = storeNames;
	}
	public String[] getStoreOrgIdArray() {
		return storeOrgIdArray;
	}
	public void setStoreOrgIdArray(String[] storeOrgIdArray) {
		this.storeOrgIdArray = storeOrgIdArray;
	}
	public String getStoreOrgIds() {
		return storeOrgIds;
	}
	public void setStoreOrgIds(String storeOrgIds) {
		this.storeOrgIds = storeOrgIds;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDictLoanStatusName() {
		return dictLoanStatusName;
	}
	public void setDictLoanStatusName(String dictLoanStatusName) {
		this.dictLoanStatusName = dictLoanStatusName;
	}
	public String getLoanFlagName() {
		return loanFlagName;
	}
	public void setLoanFlagName(String loanFlagName) {
		this.loanFlagName = loanFlagName;
	}
	public String[] getDictLoanStatusArray() {
		return dictLoanStatusArray;
	}
	public void setDictLoanStatusArray(String[] dictLoanStatusArray) {
		this.dictLoanStatusArray = dictLoanStatusArray;
	}
	public Date getStartLoanAuditTime() {
		return startLoanAuditTime;
	}
	public void setStartLoanAuditTime(Date startLoanAuditTime) {
		this.startLoanAuditTime = startLoanAuditTime;
	}
	public Date getEndLoanAuditTime() {
		return endLoanAuditTime;
	}
	public void setEndLoanAuditTime(Date endLoanAuditTime) {
		this.endLoanAuditTime = endLoanAuditTime;
	}
	public Date getStartTateTime() {
		return startTateTime;
	}
	public void setStartTateTime(Date startTateTime) {
		this.startTateTime = startTateTime;
	}
	public Date getEndTateTime() {
		return endTateTime;
	}
	public void setEndTateTime(Date endTateTime) {
		this.endTateTime = endTateTime;
	}
	public Date getStartContractTime() {
		return startContractTime;
	}
	public void setStartContractTime(Date startContractTime) {
		this.startContractTime = startContractTime;
	}
	public Date getEndContractTime() {
		return endContractTime;
	}
	public void setEndContractTime(Date endContractTime) {
		this.endContractTime = endContractTime;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public Date getPostponeTime() {
		return postponeTime;
	}
	public void setPostponeTime(Date postponeTime) {
		this.postponeTime = postponeTime;
	}
	public Date getTimeOutPointTime() {
		return timeOutPointTime;
	}
	public void setTimeOutPointTime(Date timeOutPointTime) {
		this.timeOutPointTime = timeOutPointTime;
	}
	public String getPostponeDays() {
		return postponeDays;
	}
	public void setPostponeDays(String postponeDays) {
		this.postponeDays = postponeDays;
	}
	public Date getContractMadeTime() {
		return contractMadeTime;
	}
	public void setContractMadeTime(Date contractMadeTime) {
		this.contractMadeTime = contractMadeTime;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String[] getContractCodeArray() {
		return contractCodeArray;
	}
	public void setContractCodeArray(String[] contractCodeArray) {
		this.contractCodeArray = contractCodeArray;
	}
	public Date getSignconfirmTime() {
		return signconfirmTime;
	}
	public void setSignconfirmTime(Date signconfirmTime) {
		this.signconfirmTime = signconfirmTime;
	}
	public Date getStartSignconfirmTime() {
		return startSignconfirmTime;
	}
	public void setStartSignconfirmTime(Date startSignconfirmTime) {
		this.startSignconfirmTime = startSignconfirmTime;
	}
	public Date getEndSignconfirmTime() {
		return endSignconfirmTime;
	}
	public void setEndSignconfirmTime(Date endSignconfirmTime) {
		this.endSignconfirmTime = endSignconfirmTime;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
}
