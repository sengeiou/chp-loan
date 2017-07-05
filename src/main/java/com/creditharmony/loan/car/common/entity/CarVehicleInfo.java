package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 车辆信息
 * @Class Name CarVehicleInfo
 * @author 安子帅
 * @Create In 2016年1月21日  
 */
public class CarVehicleInfo extends DataEntity<CarVehicleInfo> {


	private static final long serialVersionUID = 7019160865207438404L;
	
	private String id;                     //ID
	private String loanCode ;              //借款编码
	private String plateNumbers ;          //车牌号码
	private BigDecimal suggestLoanAmount ;     // 建议借款金额
	private String appraiserName;          // 评估师姓名(评估师编号)
	private BigDecimal storeAssessAmount;      // 门店评估金额
	private Date commercialMaturityDate;   //商业险到期日
	private String commericialCompany; 	//商业险保险公司
	private String commericialNum;	//商业险单号
	private BigDecimal similarMarketPrice;     // 同类市场价格
	private Date factoryDate;              // 出厂日期
	private Date strongRiskMaturityDate;   // 交强险到期日
	private String clivtaCompany;	//交强险保险公司
	private String clivtaNum;	//交强险单号
	private Date annualCheckDate ;         // 车年到期日
	private String frameNumber ;           //车架号
	private String vehiclePlantModel ;     //车辆厂牌型号
	private String vehicleBrandModel;      // 车辆品牌型号
	private double mileage ;               //表征里程
	private Date firstRegistrationDate;    // 首次登记日期
	private BigDecimal displacemint;              // 排气量
	private String carBodyColor ;          //车身颜色
	private String variator;               // 变速器
	private String engineNumber ;          // 发动机号
	private String changeNum;              // 过户次数
	private String ownershipCertificateNumber;  // 权属证书编号
	private String modifiedSituation ;          //改装情况
	private String outerInspection;             // 外观检测
	private String illegalAccident;             // 违章及事故情况
	private String vehicleAssessment;           //车辆评估意见
	private String dictOperStatus;              // 下一步操作状态
	private String remark;                      // 备注
	private String createBy;                    //创建人
	private Date createTime;                    // 创建时间
	private String modifyBy ;                   //修改人
	private Date modifyTime ;                   //最后修改时间
	private BigDecimal extensionAssessAmount;   //展期评估金额
	private BigDecimal extensionSuggestAmount;  //展期建议金额
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	public BigDecimal getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(BigDecimal storeAssessAmount) {
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
	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}
	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
}