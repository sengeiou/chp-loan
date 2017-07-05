package com.creditharmony.loan.car.carApply.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;

/**
 * 面审初审业务view
 * @Class Name ReviewMeetApplyBusinessView
 * @author 陈伟东
 * @Create In 2016年2月22日
 */
public class ReviewMeetFirstAuditBusinessView extends BaseBusinessView {
	
	private CarCustomer carCustomer;
	private CarLoanInfo carLoanInfo;
	// 共借人信息
	private List<CarLoanCoborrower> carLoanCoborrowers;
	//车辆信息
	private CarVehicleInfo carVehicleInfo;
	//面审申请信息
	private CarApplicationInterviewInfo carApplicationInterviewInfo;
	//初审审批结果
	private CarAuditResult carAuditResult;
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//借款状态
	private String dictLoanStatus;
	//管辖城市
	private String jurisdictionCity;
	//省份证真伪
	private String dictIdIstrue;
	//客户人法查询结果
	private String queryResult;
	//114电话查询情况
	private String queryResultPhone;
	//客户工作审核情况
	private String customerJobReview;
	//征信报告显示情况
	private String creditReport;
	//产品类型(审批)
	private String dictProductType;
	//产品类型名称(审批流)
	private String auditBorrowProductName;
	//产品类型code(审批流)
	private String auditBorrowProductCode;
	//批借时间
	private Date auditTime;
	//借款期限(批复流)
	private Integer auditLoanMonths;
	//借款期限(批复)
	private String dictAuditMonths;
	//审批金额（元）
	private Double auditAmount;
	//总费率
	private Double grossRate;
	//首期服务费率
	private Double firstServiceTariffing;
	//审批结果
	private String auditResult;
	//审批意见
	private String auditCheckExamine;
	//客户经理
	private String managerName;
	//团队经理
	private String consTeamManagerName;
	
	private String dictOperStatus;//咨询状态
	
	private String remark;
	
	private String fourLabel;
	private String oneLabel;
	private String threeLabel;
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	private String imageUrl;
	private String contractVersion;
	
	private BigDecimal deviceUsedFee; //GPS设备使用费
	
	
	public BigDecimal getDeviceUsedFee() {
		return deviceUsedFee;
	}
	public void setDeviceUsedFee(BigDecimal deviceUsedFee) {
		this.deviceUsedFee = deviceUsedFee;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDictOperStatus() {
		return dictOperStatus;
	}
	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}
	public CarCustomer getCarCustomer() {
		return carCustomer;
	}
	public void setCarCustomer(CarCustomer carCustomer) {
		this.carCustomer = carCustomer;
	}
	public CarLoanInfo getCarLoanInfo() {
		return carLoanInfo;
	}
	public void setCarLoanInfo(CarLoanInfo carLoanInfo) {
		this.carLoanInfo = carLoanInfo;
	}
	public CarVehicleInfo getCarVehicleInfo() {
		return carVehicleInfo;
	}
	public void setCarVehicleInfo(CarVehicleInfo carVehicleInfo) {
		this.carVehicleInfo = carVehicleInfo;
	}
	public CarApplicationInterviewInfo getCarApplicationInterviewInfo() {
		return carApplicationInterviewInfo;
	}
	public void setCarApplicationInterviewInfo(
			CarApplicationInterviewInfo carApplicationInterviewInfo) {
		this.carApplicationInterviewInfo = carApplicationInterviewInfo;
	}
	public CarAuditResult getCarAuditResult() {
		return carAuditResult;
	}
	public void setCarAuditResult(CarAuditResult carAuditResult) {
		this.carAuditResult = carAuditResult;
	}
	public String getJurisdictionCity() {
		return jurisdictionCity;
	}
	public void setJurisdictionCity(String jurisdictionCity) {
		this.jurisdictionCity = jurisdictionCity;
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
	public String getDictIdIstrue() {
		return dictIdIstrue;
	}
	public void setDictIdIstrue(String dictIdIstrue) {
		this.dictIdIstrue = dictIdIstrue;
	}
	public String getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	public String getQueryResultPhone() {
		return queryResultPhone;
	}
	public void setQueryResultPhone(String queryResultPhone) {
		this.queryResultPhone = queryResultPhone;
	}
	public String getCustomerJobReview() {
		return customerJobReview;
	}
	public void setCustomerJobReview(String customerJobReview) {
		this.customerJobReview = customerJobReview;
	}
	public String getCreditReport() {
		return creditReport;
	}
	public void setCreditReport(String creditReport) {
		this.creditReport = creditReport;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	public String getDictAuditMonths() {
		return dictAuditMonths;
	}
	public void setDictAuditMonths(String dictAuditMonths) {
		this.dictAuditMonths = dictAuditMonths;
	}
	public Double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}
	public Double getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(Double grossRate) {
		this.grossRate = grossRate;
	}
	public Double getFirstServiceTariffing() {
		return firstServiceTariffing;
	}
	public void setFirstServiceTariffing(Double firstServiceTariffing) {
		this.firstServiceTariffing = firstServiceTariffing;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getAuditCheckExamine() {
		return auditCheckExamine;
	}
	public void setAuditCheckExamine(String auditCheckExamine) {
		this.auditCheckExamine = auditCheckExamine;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getFirstBackSourceStep() {
		return firstBackSourceStep;
	}
	public void setFirstBackSourceStep(String firstBackSourceStep) {
		this.firstBackSourceStep = firstBackSourceStep;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	
}
