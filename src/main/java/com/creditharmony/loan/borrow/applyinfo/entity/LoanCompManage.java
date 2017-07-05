package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户经营信息
 * 
 * @Class Name LoanCompManage
 * @author 卢建学
 * @Create In 2016年09月12日
 */
public class LoanCompManage extends DataEntity<LoanCompManage> {

	private static final long serialVersionUID = 4803015898775983943L;

	private String loanCode; // 借款编码

	private String businessLicenseRegisterNum; // 营业执照注册号

	private Date compCreateDate; // 成立日期

	private String compType; // 企业类型
	
	private String compTypeRemark; //企业类型备注

	private BigDecimal averageMonthTurnover; // 平均月营业额

	private String managerBusiness; // 主营业务

	private BigDecimal compRegisterCapital; // 企业注册资本

	private BigDecimal customerRatioComp; // 申请人占股比例

	private String corporateRepresent; // 法定代表人姓名

	private String certNum; // 法定代表人身份证号码
	
	private Date idStartDay;    // 证件有效期开始时间
	
	private Date idEndDay;      // 证件有效期结束时间

	private String corporateRepresentMobile; // 法定代表人手机号

	private String compEmail; // 企业邮箱

	private BigDecimal businessArea; // 营业面积

	private String managePlace; // 经营场所

	private String manageAddressProvince; // 所在省市

	private String manageAddressCity; // 所在市

	private String manageAddressArea; // 所在区

	private String manageAddress; // 经营地址

	private BigDecimal monthRentMoney; // 月租金

	private BigDecimal monthPayMoney; // 月还款
	
	private String creditCode; //信用代码
	
	private String orgCode;  //组织机构码
	
	public Date getIdStartDay() {
		return idStartDay;
	}

	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}

	public Date getIdEndDay() {
		return idEndDay;
	}

	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getBusinessLicenseRegisterNum() {
		return businessLicenseRegisterNum;
	}

	public void setBusinessLicenseRegisterNum(String businessLicenseRegisterNum) {
		this.businessLicenseRegisterNum = businessLicenseRegisterNum;
	}

	public Date getCompCreateDate() {
		return compCreateDate;
	}

	public void setCompCreateDate(Date compCreateDate) {
		this.compCreateDate = compCreateDate;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}
	
	public String getCompTypeRemark() {
		return compTypeRemark;
	}

	public void setCompTypeRemark(String compTypeRemark) {
		this.compTypeRemark = compTypeRemark;
	}

	public BigDecimal getAverageMonthTurnover() {
		return averageMonthTurnover;
	}

	public void setAverageMonthTurnover(BigDecimal averageMonthTurnover) {
		this.averageMonthTurnover = averageMonthTurnover;
	}

	public String getManagerBusiness() {
		return managerBusiness;
	}

	public void setManagerBusiness(String managerBusiness) {
		this.managerBusiness = managerBusiness;
	}

	public BigDecimal getCompRegisterCapital() {
		return compRegisterCapital;
	}

	public void setCompRegisterCapital(BigDecimal compRegisterCapital) {
		this.compRegisterCapital = compRegisterCapital;
	}

	public BigDecimal getCustomerRatioComp() {
		return customerRatioComp;
	}

	public void setCustomerRatioComp(BigDecimal customerRatioComp) {
		this.customerRatioComp = customerRatioComp;
	}

	public String getCorporateRepresent() {
		return corporateRepresent;
	}

	public void setCorporateRepresent(String corporateRepresent) {
		this.corporateRepresent = corporateRepresent;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCorporateRepresentMobile() {
		return corporateRepresentMobile;
	}

	public void setCorporateRepresentMobile(String corporateRepresentMobile) {
		this.corporateRepresentMobile = corporateRepresentMobile;
	}

	public String getCompEmail() {
		return compEmail;
	}

	public void setCompEmail(String compEmail) {
		this.compEmail = compEmail;
	}

	public BigDecimal getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(BigDecimal businessArea) {
		this.businessArea = businessArea;
	}

	public String getManagePlace() {
		return managePlace;
	}

	public void setManagePlace(String managePlace) {
		this.managePlace = managePlace;
	}

	public String getManageAddressProvince() {
		return manageAddressProvince;
	}

	public void setManageAddressProvince(String manageAddressProvince) {
		this.manageAddressProvince = manageAddressProvince;
	}

	public String getManageAddressCity() {
		return manageAddressCity;
	}

	public void setManageAddressCity(String manageAddressCity) {
		this.manageAddressCity = manageAddressCity;
	}

	public String getManageAddressArea() {
		return manageAddressArea;
	}

	public void setManageAddressArea(String manageAddressArea) {
		this.manageAddressArea = manageAddressArea;
	}

	public String getManageAddress() {
		return manageAddress;
	}

	public void setManageAddress(String manageAddress) {
		this.manageAddress = manageAddress;
	}

	public BigDecimal getMonthRentMoney() {
		return monthRentMoney;
	}

	public void setMonthRentMoney(BigDecimal monthRentMoney) {
		this.monthRentMoney = monthRentMoney;
	}

	public BigDecimal getMonthPayMoney() {
		return monthPayMoney;
	}

	public void setMonthPayMoney(BigDecimal monthPayMoney) {
		this.monthPayMoney = monthPayMoney;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	

}