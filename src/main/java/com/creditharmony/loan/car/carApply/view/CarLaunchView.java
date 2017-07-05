package com.creditharmony.loan.car.carApply.view;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
/**
 * 客户信息
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年2月1日
 */
public class CarLaunchView extends BaseBusinessView {
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//客户编码
	private String customerCode;
	//客户姓名
	private String customerName;
	//证件类型
	private String dictCertType;
	//身份证号码
	private String customerCertNum;
	//手机号码
	private String customerMobilePhone;
	//学历
	private String dictEducation;
	//婚姻状况
	private String dictMarryStatus;
	//证件开始日期
	private Date idStartDay;
	//证件有效期
	private Date idEndDay;
	//是否长期
	private String isLongTerm;
	//建议借款金额
	private BigDecimal consLoanAmount;
	//预计借款金额(工作流)
	private Double loanApplyAmount;
	//车辆品牌与型号
	private String vehicleBrandModel;
	//车牌号码
	private String plateNumbers;
	//建议借款金额
	private BigDecimal suggestLoanAmount;
	//评估师姓名
	private String appraiserName;
	//评估金额(工作流)
	private Double storeAssessAmount;
	//商业险到期日
	private Date commercialMaturityDate;
	//同类市场价格
	private BigDecimal similarMarketPrice;
	//出厂日期
	private Date factoryDate;
	//交强险到期日
	private Date strongRiskMaturityDate;
	//交强险保险公司
	private String clivtaCompany;
	//交强险单号
	private String clivtaNum;
	//商业险保险公司
	private String commericialCompany;
	//商业险单号
	private String commericialNum;
	//车年到期日 
	private Date annualCheckDate;
	//车架号
	private String frameNumber;
	//车辆厂牌型号
	private String vehiclePlantModel;
	//首次登记日期
	private Date firstRegistrationDate;
	//表征里程
	private String mileage;
	//排气量
	private BigDecimal displacemint;
	//车身颜色
	private String carBodyColor;
	//变速器
	private String variator;
	//发动机号
	private String engineNumber;
	//过户次数
	private String changeNum;
	//权属证书编号
	private String ownershipCertificateNumber;
	//改装情况
	private String modifiedSituation;
	//外观检测
	private String outerInspection;
	//违章及事故情况
	private String illegalAccident;
	//车辆评估意见
	private String vehicleAssessment;
	//咨询状态（客户咨询中的下一步状态）
	private String dictOperStatus;
	//备注(车辆信息中的备注)
	private String remark;
	//团队经理code
	private String consTeamManagercode;
	//团队经理姓名
	private String loanTeamEmpName;
	//客户经理code
	private String managerCode;
	//客户经理姓名
	private String offendSalesName;
	//门店
	private String storeName;
	//借款状态
	private String dictLoanStatus;
	//是否电销
	private String consTelesalesFlag;
	//门店code
	private String storeCode;
	//申请时间
	private Date loanApplyTime;
	//预计到店时间
	private Date planArrivalTime;
	//进件时间
	private Date customerIntoTime;
	//排序字段
	private String orderField;
	//退回节点记录缓存值
	@Deprecated
	private String backNodeCache;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	
	private String dictProductType;
	
	@Deprecated
	public String getBackNodeCache() {
		return backNodeCache;
	}
	@Deprecated
	public void setBackNodeCache(String backNodeCache) {
		this.backNodeCache = backNodeCache;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getIsLongTerm() {
		return isLongTerm;
	}
	public void setIsLongTerm(String isLongTerm) {
		this.isLongTerm = isLongTerm;
	}
	public Date getIdStartDay() {
		return idStartDay;
	}
	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}
	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getConsTeamManagercode() {
		return consTeamManagercode;
	}
	public void setConsTeamManagercode(String consTeamManagercode) {
		this.consTeamManagercode = consTeamManagercode;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public BigDecimal getConsLoanAmount() {
		return consLoanAmount;
	}
	public void setConsLoanAmount(BigDecimal consLoanAmount) {
		this.consLoanAmount = consLoanAmount;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}
	public String getDictEducation() {
		return dictEducation;
	}
	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}
	public String getDictMarryStatus() {
		return dictMarryStatus;
	}
	public void setDictMarryStatus(String dictMarryStatus) {
		this.dictMarryStatus = dictMarryStatus;
	}
	public Date getIdEndDay() {
		return idEndDay;
	}
	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}
	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}
	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public BigDecimal getSuggestLoanAmount() {
		return suggestLoanAmount;
	}
	public void setSuggestLoanAmount(BigDecimal suggestLoanAmount) {
		this.suggestLoanAmount = suggestLoanAmount;
	}
	public String getAppraiserName() {
		return appraiserName;
	}
	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}
	public BigDecimal getSimilarMarketPrice() {
		return similarMarketPrice;
	}
	public void setSimilarMarketPrice(BigDecimal similarMarketPrice) {
		this.similarMarketPrice = similarMarketPrice;
	}
	public Date getFactoryDate() {
		return factoryDate;
	}
	public void setFactoryDate(Date factoryDate) {
		this.factoryDate = factoryDate;
	}
	public Date getStrongRiskMaturityDate() {
		return strongRiskMaturityDate;
	}
	public void setStrongRiskMaturityDate(Date strongRiskMaturityDate) {
		this.strongRiskMaturityDate = strongRiskMaturityDate;
	}
	public Date getAnnualCheckDate() {
		return annualCheckDate;
	}
	public void setAnnualCheckDate(Date annualCheckDate) {
		this.annualCheckDate = annualCheckDate;
	}
	public String getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}
	public String getVehiclePlantModel() {
		return vehiclePlantModel;
	}
	public void setVehiclePlantModel(String vehiclePlantModel) {
		this.vehiclePlantModel = vehiclePlantModel;
	}
	public Date getFirstRegistrationDate() {
		return firstRegistrationDate;
	}
	public void setFirstRegistrationDate(Date firstRegistrationDate) {
		this.firstRegistrationDate = firstRegistrationDate;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public BigDecimal getDisplacemint() {
		return displacemint;
	}
	public void setDisplacemint(BigDecimal displacemint) {
		this.displacemint = displacemint;
	}
	public String getCarBodyColor() {
		return carBodyColor;
	}
	public void setCarBodyColor(String carBodyColor) {
		this.carBodyColor = carBodyColor;
	}
	public String getVariator() {
		return variator;
	}
	public void setVariator(String variator) {
		this.variator = variator;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getChangeNum() {
		return changeNum;
	}
	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
	}
	public String getOwnershipCertificateNumber() {
		return ownershipCertificateNumber;
	}
	public void setOwnershipCertificateNumber(String ownershipCertificateNumber) {
		this.ownershipCertificateNumber = ownershipCertificateNumber;
	}
	public String getModifiedSituation() {
		return modifiedSituation;
	}
	public void setModifiedSituation(String modifiedSituation) {
		this.modifiedSituation = modifiedSituation;
	}
	public String getOuterInspection() {
		return outerInspection;
	}
	public void setOuterInspection(String outerInspection) {
		this.outerInspection = outerInspection;
	}
	public String getIllegalAccident() {
		return illegalAccident;
	}
	public void setIllegalAccident(String illegalAccident) {
		this.illegalAccident = illegalAccident;
	}
	public String getVehicleAssessment() {
		return vehicleAssessment;
	}
	public void setVehicleAssessment(String vehicleAssessment) {
		this.vehicleAssessment = vehicleAssessment;
	}
	public String getDictOperStatus() {
		return dictOperStatus;
	}
	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLoanTeamEmpName() {
		return loanTeamEmpName;
	}
	public void setLoanTeamEmpName(String loanTeamEmpName) {
		this.loanTeamEmpName = loanTeamEmpName;
	}
	public String getOffendSalesName() {
		return offendSalesName;
	}
	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}
	public Date getPlanArrivalTime() {
		return planArrivalTime;
	}
	public void setPlanArrivalTime(Date planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public Double getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(Double loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public Double getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(Double storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}
	public Date getCommercialMaturityDate() {
		return commercialMaturityDate;
	}
	public void setCommercialMaturityDate(Date commercialMaturityDate) {
		this.commercialMaturityDate = commercialMaturityDate;
	}
	public String getFirstBackSourceStep() {
		return firstBackSourceStep;
	}
	public void setFirstBackSourceStep(String firstBackSourceStep) {
		this.firstBackSourceStep = firstBackSourceStep;
	}
	public String getClivtaCompany() {
		return clivtaCompany;
	}
	public void setClivtaCompany(String clivtaCompany) {
		this.clivtaCompany = clivtaCompany;
	}
	public String getClivtaNum() {
		return clivtaNum;
	}
	public void setClivtaNum(String clivtaNum) {
		this.clivtaNum = clivtaNum;
	}
	public String getCommericialCompany() {
		return commericialCompany;
	}
	public void setCommericialCompany(String commericialCompany) {
		this.commericialCompany = commericialCompany;
	}
	public String getCommericialNum() {
		return commericialNum;
	}
	public void setCommericialNum(String commericialNum) {
		this.commericialNum = commericialNum;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}

}
