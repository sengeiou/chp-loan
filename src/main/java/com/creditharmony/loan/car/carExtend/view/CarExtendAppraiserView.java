package com.creditharmony.loan.car.carExtend.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 展期评估师录入
 * @Class Name CarExtendAppraiserView
 * @author ganquan
 * @Create In 2016年3月7日
 */
public class CarExtendAppraiserView extends CarExtendBaseBusinessView  {
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//客户姓名
	private String customerName;
	//证件类型
	private String dictCertType;
	//批借金额
	private  BigDecimal auditAmount;
	
	private String imageUrl;

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}
	//身份证号码
	private String customerCertNum;
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
	//车年到期日
	private Date annualCheckDate;
	//车架号
	private String frameNumber;
	//车辆厂牌型号
	private String vehiclePlantModel;
	//首次登记日期
	private Date firstRegistrationDate;
	//表征里程
	private double mileage;
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
	//展期申请状态
	private String applyStatusCode;
	//展期评估金额
	private BigDecimal extensionAssessAmount;
	//展期建议借款金额
	private BigDecimal extensionSuggestAmount;
	
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
	private String clivtaCompany;	//交强险保险公司
	private String clivtaNum;	//交强险单号
	private String commericialCompany; 	//商业险保险公司
	private String commericialNum;	//商业险单号
	private String dictProductType;
	
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
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
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public BigDecimal getExtensionAssessAmount() {
		return extensionAssessAmount;
	}
	public void setExtensionAssessAmount(BigDecimal extensionAssessAmount) {
		this.extensionAssessAmount = extensionAssessAmount;
	}
	public BigDecimal getExtensionSuggestAmount() {
		return extensionSuggestAmount;
	}
	public void setExtensionSuggestAmount(BigDecimal extensionSuggestAmount) {
		this.extensionSuggestAmount = extensionSuggestAmount;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	
	
}
