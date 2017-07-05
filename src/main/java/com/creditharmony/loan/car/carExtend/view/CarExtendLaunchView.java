package com.creditharmony.loan.car.carExtend.view;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 客户信息
 * @Class Name Consult
 * @author 安子帅
 * @Create In 2016年3月7日
 */
public class CarExtendLaunchView extends CarExtendBaseBusinessView {
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//客户编码
	private String customerCode;
	//客户姓名
	private String customerName;
	//性别
	private String customerSex;
	//证件类型
	private String dictCertType;
	//证件号码
	private String customerCertNum;
	//手机号码
	private String customerMobilePhone;
	//电子邮箱
	private String customerEmail;
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
	private Float loanApplyAmount;
	//户籍省
	private String customerRegisterProvince; 
	//户籍市
    private String customerRegisterCity;
    //户籍区
    private String customerRegisterArea;  
    //户籍地址
    private String customerRegisterAddress; 
    //居住省
    private String customerLiveProvince;
    //居住市
    private String customerLiveCity; 
    //居住区
    private String customerLiveArea; 
    //详细地址
    private String customerAddress;    		     
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
	//进件时间
	private Date customerIntoTime;
	
	private String customerRegisterAddressView; //户籍地址查看用
	private String customerAddressView;//详细地址查看用
	
	public String getCustomerRegisterAddressView() {
		return customerRegisterAddressView;
	}
	public void setCustomerRegisterAddressView(String customerRegisterAddressView) {
		this.customerRegisterAddressView = customerRegisterAddressView;
	}
	public String getCustomerAddressView() {
		return customerAddressView;
	}
	public void setCustomerAddressView(String customerAddressView) {
		this.customerAddressView = customerAddressView;
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
	public Float getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(Float loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
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
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerRegisterProvince() {
		return customerRegisterProvince;
	}
	public void setCustomerRegisterProvince(String customerRegisterProvince) {
		this.customerRegisterProvince = customerRegisterProvince;
	}
	public String getCustomerRegisterCity() {
		return customerRegisterCity;
	}
	public void setCustomerRegisterCity(String customerRegisterCity) {
		this.customerRegisterCity = customerRegisterCity;
	}
	public String getCustomerRegisterArea() {
		return customerRegisterArea;
	}
	public void setCustomerRegisterArea(String customerRegisterArea) {
		this.customerRegisterArea = customerRegisterArea;
	}
	public String getCustomerRegisterAddress() {
		return customerRegisterAddress;
	}
	public void setCustomerRegisterAddress(String customerRegisterAddress) {
		this.customerRegisterAddress = customerRegisterAddress;
	}
	public String getCustomerLiveProvince() {
		return customerLiveProvince;
	}
	public void setCustomerLiveProvince(String customerLiveProvince) {
		this.customerLiveProvince = customerLiveProvince;
	}
	public String getCustomerLiveCity() {
		return customerLiveCity;
	}
	public void setCustomerLiveCity(String customerLiveCity) {
		this.customerLiveCity = customerLiveCity;
	}
	public String getCustomerLiveArea() {
		return customerLiveArea;
	}
	public void setCustomerLiveArea(String customerLiveArea) {
		this.customerLiveArea = customerLiveArea;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
}

