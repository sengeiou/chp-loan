package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.CustomerLivings;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 客户基本信息表
 * @Class Name CarCustomerBase
 * @author 张进
 * @Create In 2016年1月22日
 */
@SuppressWarnings("serial")
public class CarCustomerBase extends DataEntity<CarCustomerBase> {

    private CarCustomer carCustomer;                 //客户信息
    private CustomerLivings customerLivings;   //客户居住情况
    private CarCustomerBankInfo  carCustomerBankInfo;				   //账户信息
    private List<CarLoanCoborrower> carLoanCoborrowers = new LinkedList<CarLoanCoborrower>();      //共借人基本信息
    private CarCustomerCompany carCustomerCompany;		   //工作信息
    private CarVehicleInfo carVehicleInfo;     //车辆信息
    private List<CarCustomerContactPerson> carCustomerContactPerson;			   //联系人信息
    private String customerCode;               //客户编码
    private String customerName;                 //客户姓名
    private String customerSex;                  //性别
    private Date customerBirthday;               //出生日期
    private String dictCertType;     			 //证件类型
    private String customerCertNum;     			 //证件号码
    private String customerCertOrg;              //发证机关
    private Date idStartDay;                    //身份证有效期开始时间
    private Date idEndDay;            			 //身份证有效期结束时间
    private String customerMobilePhone;     	 //手机号
    private String custoemrMobilePhoneEnc;
    private String dictCompIndustry;    	     //行业类别
    private String customerNameOcr;    			 //客户姓名ocr地址
    private String customerCretOcr;    			 //客户身份证ocr地址
    private String flag;
    private String areaNo;                      // 区号
    private String telephoneNo;                 // 座机号
    private String consultId;                  // 咨询ID
	private String customerCodeOld;
	private String isTelephoneModify;			//电话是否修改
	private String isEmailModify;			//邮箱是否修改
    
	
	
    public String getIsTelephoneModify() {
		return isTelephoneModify;
	}
	public void setIsTelephoneModify(String isTelephoneModify) {
		this.isTelephoneModify = isTelephoneModify;
	}
	public String getCustomerCodeOld() {
		return customerCodeOld;
	}
	public void setCustomerCodeOld(String customerCodeOld) {
		this.customerCodeOld = customerCodeOld;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public CarCustomer getCarCustomer() {
		return carCustomer;
	}
	public void setCarCustomer(CarCustomer carCustomer) {
		this.carCustomer = carCustomer;
	}
	public CustomerLivings getCustomerLivings() {
		return customerLivings;
	}
	public void setCustomerLivings(CustomerLivings customerLivings) {
		this.customerLivings = customerLivings;
	}
	public CarCustomerBankInfo getCarCustomerBankInfo() {
		return carCustomerBankInfo;
	}
	public void setCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		this.carCustomerBankInfo = carCustomerBankInfo;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public CarCustomerCompany getCarCustomerCompany() {
		return carCustomerCompany;
	}
	public void setCarCustomerCompany(CarCustomerCompany carCustomerCompany) {
		this.carCustomerCompany = carCustomerCompany;
	}
	public CarVehicleInfo getCarVehicleInfo() {
		return carVehicleInfo;
	}
	public void setCarVehicleInfo(CarVehicleInfo carVehicleInfo) {
		this.carVehicleInfo = carVehicleInfo;
	}
	public List<CarCustomerContactPerson> getCarCustomerContactPerson() {
		return carCustomerContactPerson;
	}
	public void setCarCustomerContactPerson(
			List<CarCustomerContactPerson> carCustomerContactPerson) {
		this.carCustomerContactPerson = carCustomerContactPerson;
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
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public Date getCustomerBirthday() {
		return customerBirthday;
	}
	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}
	
	public String getCustomerCertOrg() {
		return customerCertOrg;
	}
	public void setCustomerCertOrg(String customerCertOrg) {
		this.customerCertOrg = customerCertOrg;
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
	public String getCustomerMobilePhone() {
		/*if(customerMobilePhone != null){
			customerMobilePhone = decryptPhones(customerMobilePhone,"T_JK_CUSTOMER_BASE","customer_mobile_phone");
        }*/
		return customerMobilePhone;
	}
	public String getCustomerMobilePhoneEnc() {
		return custoemrMobilePhoneEnc;
	}
	public void setCustomerMobilePhone(String customerMobilePhone) {
		/*if(customerMobilePhone != null && customerMobilePhone.length() == 11){
			customerMobilePhone = encryptPhones(customerMobilePhone,"T_JK_CUSTOMER_BASE","customer_mobile_phone");
		}*/
		this.customerMobilePhone = customerMobilePhone;
		this.custoemrMobilePhoneEnc = customerMobilePhone;
	}
	public String getDictCompIndustry() {
		return dictCompIndustry;
	}
	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry;
	}
	public String getCustomerNameOcr() {
		return customerNameOcr;
	}
	public void setCustomerNameOcr(String customerNameOcr) {
		this.customerNameOcr = customerNameOcr;
	}
	public String getCustomerCretOcr() {
		return customerCretOcr;
	}
	public void setCustomerCretOcr(String customerCretOcr) {
		this.customerCretOcr = customerCretOcr;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getConsultId() {
		return consultId;
	}
	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}
	public String getIsEmailModify() {
		return isEmailModify;
	}
	public void setIsEmailModify(String isEmailModify) {
		this.isEmailModify = isEmailModify;
	}
	

}
