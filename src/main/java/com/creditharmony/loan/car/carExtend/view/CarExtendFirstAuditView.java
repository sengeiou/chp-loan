package com.creditharmony.loan.car.carExtend.view;

import java.util.Date;
import java.util.List;

import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.common.entity.CityInfo;

/**
 * 展期初审
 * @Class Name CarExtendFirstAuditView
 * @author ganquan
 * @Create In 2016年3月9日
 */
public class CarExtendFirstAuditView extends CarExtendBaseBusinessView {
	//车辆详细信息
	private CarVehicleInfo carVehicleInfo;
	//借款详细信息
	private CarLoanInfo carLoanInfo;
	//客户详细信息
	private CarCustomer carCustomer;
	//合同信息
	private CarContract carContract;
	//审核结果
	private CarAuditResult carAuditResult;
	//面审信息
	private CarApplicationInterviewInfo carApplicationInterviewInfo;
	//共借人信息
	private List<CarLoanCoborrower> carLoanCoborrowers;
	//产品类型编码（批复）
	private String auditBorrowProductCode;
	//产品类型名称（批复）
	private String auditBorrowProductName;
	//借款期限（批复）
	private Integer auditLoanMonths;
	//批借金额
	private Double auditAmount;
	//申请状态（编码）
	private String applyStatusCode;
	//团队经理
	private String consTeamManagerName;
	//客户经理
	private String managerName;
	//管辖城市
	private String cityName;
	//流程id
	private String applyId;
	//审批结果
	private String auditResult;
	//表征里程
	private Double mileage;
	//展期评估金额
	private Double extensionAssessAmount;
	//展期建议借款金额
	private Double extensionSuggestAmount;
	//退回节点
	private String returnNode;
	//获得省
	private List<CityInfo> provinceList;
	//退回原因
	private String remark;
	//展期合同编号
	private String contractNo;
	
	private String fourLabel;
	private String oneLabel;
	private String threeLabel;
	private String firstCheckName; // 初审人员姓名
	private Double grossRate; // 初审 总费率
	private Date auditTime; // 
	private String imageUrl;
	private String dictProductType;
	
	private String contractVersion; 
	
	
	
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getExtensionAssessAmount() {
		return extensionAssessAmount;
	}
	public void setExtensionAssessAmount(Double extensionAssessAmount) {
		this.extensionAssessAmount = extensionAssessAmount;
	}
	public Double getExtensionSuggestAmount() {
		return extensionSuggestAmount;
	}
	public void setExtensionSuggestAmount(Double extensionSuggestAmount) {
		this.extensionSuggestAmount = extensionSuggestAmount;
	}
	public Double getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(Double grossRate) {
		this.grossRate = grossRate;
	}
	public String getFirstCheckName() {
		return firstCheckName;
	}
	public void setFirstCheckName(String firstCheckName) {
		this.firstCheckName = firstCheckName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getFourLabel() {
		return fourLabel;
	}
	public void setFourLabel(String fourLabel) {
		this.fourLabel = fourLabel;
	}
	public String getOneLabel() {
		return oneLabel;
	}
	public void setOneLabel(String oneLabel) {
		this.oneLabel = oneLabel;
	}
	public String getThreeLabel() {
		return threeLabel;
	}
	public void setThreeLabel(String threeLabel) {
		this.threeLabel = threeLabel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<CityInfo> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<CityInfo> provinceList) {
		this.provinceList = provinceList;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getReturnNode() {
		return returnNode;
	}
	public void setReturnNode(String returnNode) {
		this.returnNode = returnNode;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getConsTeamManagerName() {
		return consTeamManagerName;
	}
	public void setConsTeamManagerName(String consTeamManagerName) {
		this.consTeamManagerName = consTeamManagerName;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public CarContract getCarContract() {
		return carContract;
	}
	public void setCarContract(CarContract carContract) {
		this.carContract = carContract;
	}
	public CarVehicleInfo getCarVehicleInfo() {
		return carVehicleInfo;
	}
	public void setCarVehicleInfo(CarVehicleInfo carVehicleInfo) {
		this.carVehicleInfo = carVehicleInfo;
	}
	public CarLoanInfo getCarLoanInfo() {
		return carLoanInfo;
	}
	public void setCarLoanInfo(CarLoanInfo carLoanInfo) {
		this.carLoanInfo = carLoanInfo;
	}
	public CarCustomer getCarCustomer() {
		return carCustomer;
	}
	public void setCarCustomer(CarCustomer carCustomer) {
		this.carCustomer = carCustomer;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public Double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public CarAuditResult getCarAuditResult() {
		return carAuditResult;
	}
	public void setCarAuditResult(CarAuditResult carAuditResult) {
		this.carAuditResult = carAuditResult;
	}
	public CarApplicationInterviewInfo getCarApplicationInterviewInfo() {
		return carApplicationInterviewInfo;
	}
	public void setCarApplicationInterviewInfo(
			CarApplicationInterviewInfo carApplicationInterviewInfo) {
		this.carApplicationInterviewInfo = carApplicationInterviewInfo;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	
}
