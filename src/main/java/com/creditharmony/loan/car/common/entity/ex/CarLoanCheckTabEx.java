package com.creditharmony.loan.car.common.entity.ex;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @Class Name CarLoanCheckTabEx
 * @author 高远
 * @Create In 2016年4月22日
 */
public class CarLoanCheckTabEx {
	private BigDecimal loanApplyAmount;//申请金额             
	private String dictProductType;//申请产品类型
	private BigDecimal loanMonths;//申请借款期限    
	private Date loanApplyTime;// 申请时间   
	private String dictLoanCommonRepaymentFlag;// 是否有共同还款人    
	private String mortgagee;//抵押权人               
	private String loanAuthorizer;// 授权人         
	private String dictSettleRelend;//结清再借   
	private String dictLoanUse;	//  借款用途                  
    private String customerName;                 //客户姓名
    private String customerSex;                  //性别
    private String dictMarryStatus;     				 //婚姻状况
    private String customerPhoneFirst;     	 //手机号
    private String dictEducation;    		 //学历
    private String customerCertNum;				//证件号码
    private Date idStartDay;                    //身份证有效期开始时间
    private Date idEndDay;            			 //身份证有效期结束时间
    private String customerTempPermit;          //暂住证
    private String customerRegisterProvince;     //户籍省
    private String customerRegisterCity;         //户籍市
    private String customerRegisterArea;         //户籍区
    private String customerRegisterAddress;      //户籍地址
    private String customerLiveProvince;     			 //居住省
    private String customerLiveCity;     				 //居住市
    private String customerLiveArea;    				 //居住区
    private String customerAddress;    		     //详细地址
    private String customerHouseHoldProperty; //住房性质
    private Date customerFirtArriveYear;      //初到本市年份
    private Integer customerFamilySupport;          //供养亲属人数
    private String customerEmail;     			 //电子邮箱
    private Double creditLine;                     //信用额度
    private String companyName;   // 单位名称
    private String dictDepartment;   //所属部门
	private String dictCompanyProvince;   //单位省
	private String dictCompanyCity;   //单位市
	private String dictCompanyArea;   // 单位区
	private String companyAddress;   //单位地址
	private String workTelephone;   // 单位电话
	private BigDecimal monthlyPay;   //月均薪资
	private String dictUnitNature;   //单位性质
	private String dictEnterpriseNature;   // 企业性质
	private String contactName;    //- 姓名
	private String contactUnitTel;    //联系电话
	private String coboName;  //姓名
	private String dictCertType;     			 //证件类型
	private String certNum;   //证件号码
	private String dictSex;   //性别
	private String mobile;                 //手机号
	private String coboDictMarryStatus;     //婚姻状况
	private String ccId;
	private int cpCount;
	private int clcCount;
	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public String getCcId() {
		return ccId;
	}
	public void setCcId(String ccId) {
		this.ccId = ccId;
	}
	public int getCpCount() {
		return cpCount;
	}
	public void setCpCount(int cpCount) {
		this.cpCount = cpCount;
	}
	public int getClcCount() {
		return clcCount;
	}
	public void setClcCount(int clcCount) {
		this.clcCount = clcCount;
	}
	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	public BigDecimal getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(BigDecimal loanMonths) {
		this.loanMonths = loanMonths;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getDictLoanCommonRepaymentFlag() {
		return dictLoanCommonRepaymentFlag;
	}
	public void setDictLoanCommonRepaymentFlag(String dictLoanCommonRepaymentFlag) {
		this.dictLoanCommonRepaymentFlag = dictLoanCommonRepaymentFlag;
	}
	public String getMortgagee() {
		return mortgagee;
	}
	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	public String getLoanAuthorizer() {
		return loanAuthorizer;
	}
	public void setLoanAuthorizer(String loanAuthorizer) {
		this.loanAuthorizer = loanAuthorizer;
	}
	public String getDictSettleRelend() {
		return dictSettleRelend;
	}
	public void setDictSettleRelend(String dictSettleRelend) {
		this.dictSettleRelend = dictSettleRelend;
	}
	public String getDictLoanUse() {
		return dictLoanUse;
	}
	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
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
	public String getDictMarryStatus() {
		return dictMarryStatus;
	}
	public void setDictMarryStatus(String dictMarryStatus) {
		this.dictMarryStatus = dictMarryStatus;
	}
	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}
	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}
	public String getDictEducation() {
		return dictEducation;
	}
	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

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
	public String getCustomerTempPermit() {
		return customerTempPermit;
	}
	public void setCustomerTempPermit(String customerTempPermit) {
		this.customerTempPermit = customerTempPermit;
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

	public String getCustomerHouseHoldProperty() {
		return customerHouseHoldProperty;
	}
	public void setCustomerHouseHoldProperty(String customerHouseHoldProperty) {
		this.customerHouseHoldProperty = customerHouseHoldProperty;
	}
	public Date getCustomerFirtArriveYear() {
		return customerFirtArriveYear;
	}
	public void setCustomerFirtArriveYear(Date customerFirtArriveYear) {
		this.customerFirtArriveYear = customerFirtArriveYear;
	}
	public Integer getCustomerFamilySupport() {
		return customerFamilySupport;
	}
	public void setCustomerFamilySupport(Integer customerFamilySupport) {
		this.customerFamilySupport = customerFamilySupport;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Double getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(Double creditLine) {
		this.creditLine = creditLine;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDictDepartment() {
		return dictDepartment;
	}
	public void setDictDepartment(String dictDepartment) {
		this.dictDepartment = dictDepartment;
	}
	public String getDictCompanyProvince() {
		return dictCompanyProvince;
	}
	public void setDictCompanyProvince(String dictCompanyProvince) {
		this.dictCompanyProvince = dictCompanyProvince;
	}
	public String getDictCompanyCity() {
		return dictCompanyCity;
	}
	public void setDictCompanyCity(String dictCompanyCity) {
		this.dictCompanyCity = dictCompanyCity;
	}
	public String getDictCompanyArea() {
		return dictCompanyArea;
	}
	public void setDictCompanyArea(String dictCompanyArea) {
		this.dictCompanyArea = dictCompanyArea;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getWorkTelephone() {
		return workTelephone;
	}
	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
	}
	public BigDecimal getMonthlyPay() {
		return monthlyPay;
	}
	public void setMonthlyPay(BigDecimal monthlyPay) {
		this.monthlyPay = monthlyPay;
	}
	public String getDictUnitNature() {
		return dictUnitNature;
	}
	public void setDictUnitNature(String dictUnitNature) {
		this.dictUnitNature = dictUnitNature;
	}
	public String getDictEnterpriseNature() {
		return dictEnterpriseNature;
	}
	public void setDictEnterpriseNature(String dictEnterpriseNature) {
		this.dictEnterpriseNature = dictEnterpriseNature;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactUnitTel() {
		return contactUnitTel;
	}
	public void setContactUnitTel(String contactUnitTel) {
		this.contactUnitTel = contactUnitTel;
	}
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getDictSex() {
		return dictSex;
	}
	public void setDictSex(String dictSex) {
		this.dictSex = dictSex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCoboDictMarryStatus() {
		return coboDictMarryStatus;
	}
	public void setCoboDictMarryStatus(String coboDictMarryStatus) {
		this.coboDictMarryStatus = coboDictMarryStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDictHouseholdProvince() {
		return dictHouseholdProvince;
	}
	public void setDictHouseholdProvince(String dictHouseholdProvince) {
		this.dictHouseholdProvince = dictHouseholdProvince;
	}
	public String getDictHouseholdCity() {
		return dictHouseholdCity;
	}
	public void setDictHouseholdCity(String dictHouseholdCity) {
		this.dictHouseholdCity = dictHouseholdCity;
	}
	public String getDictHouseholdArea() {
		return dictHouseholdArea;
	}
	public void setDictHouseholdArea(String dictHouseholdArea) {
		this.dictHouseholdArea = dictHouseholdArea;
	}
	public String getHouseholdAddress() {
		return householdAddress;
	}
	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}
	public String getDictLiveProvince() {
		return dictLiveProvince;
	}
	public void setDictLiveProvince(String dictLiveProvince) {
		this.dictLiveProvince = dictLiveProvince;
	}
	public String getDictLiveCity() {
		return dictLiveCity;
	}
	public void setDictLiveCity(String dictLiveCity) {
		this.dictLiveCity = dictLiveCity;
	}
	public String getDictLiveArea() {
		return dictLiveArea;
	}
	public void setDictLiveArea(String dictLiveArea) {
		this.dictLiveArea = dictLiveArea;
	}
	public String getNowAddress() {
		return nowAddress;
	}
	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}
	private String email;               //电子邮箱
    private String dictHouseholdProvince;  //户籍省
    private String dictHouseholdCity;      //户籍市
    private String dictHouseholdArea;      //户籍区
    private String householdAddress;       //户籍地址
    private String dictLiveProvince;    //现住址省
    private String dictLiveCity;        //现住址市 
    private String dictLiveArea;        //现住址区
    private String nowAddress;          //现住址详细地址
    
    

	
}
