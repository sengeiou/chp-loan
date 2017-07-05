package com.creditharmony.loan.borrow.consult.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.CustomerLivings;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanPersonalCertificate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 客户基本信息表
 * @Class Name CustomerBaseInfo
 * @author 张平
 * @Create In 2015年12月3日
 */
@SuppressWarnings("serial")
public class CustomerBaseInfo extends DataEntity<CustomerBaseInfo> {

    private LoanCustomer loanCustomer;                 //客户信息
    private CustomerLivings customerLivings;   //客户居住情况
    private LoanBank  loanBank;				   //账户信息
    private List<LoanCoborrower> loanCoborrower = new LinkedList<LoanCoborrower>();      //共借人基本信息
    private LoanCompany customerLoanCompany;		   //职业信息
    private List<LoanCreditInfo> loanCreditInfoList;	   //信用资料信息
    private List<LoanHouse> customerLoanHouseList;			   //客户房产信息
    private LoanInfo loanInfo;				   //借款信息主信息
    private LoanMate loanMate;				   //配偶信息
    private LoanRemark loanRemark;			   //借款备注信息
    private List<Contact> customerContactList;			   //联系人信息
    private String customerCode;               //客户编码
    private String customerName;                 //客户姓名
    private String customerSex;                  //性别
    private Date customerBirthday;               //出生日期
    private String dictCertType;     			 //证件类型
    private String mateCertNum;     			 //证件号码
    private String customerCertOrg;              //发证机关
    private Date idStartDay;                    //身份证有效期开始时间
    private Date idEndDay;            			 //身份证有效期结束时间
    private String customerMobilePhone;     	 //手机号
    private String dictCompIndustry;    	     //行业类别
    private String customerNameOcr;    			 //客户姓名ocr地址
    private String customerCretOcr;    			 //客户身份证ocr地址
    private String flag;
    private String areaNo;                      // 区号
    private String telephoneNo;                 // 座机号
    private String consultId;                  // 咨询ID
    
    private String consTelesalesFlag;                  // 电销
    
    private LoanCompManage loanCompManage; //经营信息
    private LoanPersonalCertificate loanPersonalCertificate; // 证件信息
    private List<Contact> relationContactList =  new ArrayList<Contact>(); //亲属联系人list
    private List<Contact> workProveContactList =  new ArrayList<Contact>();; //工作证明人list
    private List<Contact> otherContactList =  new ArrayList<Contact>();; //其他联系人list

    
    
    
	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}
	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public LoanCustomer getLoanCustomer() {
		return loanCustomer;
	}
	public void setLoanCustomer(LoanCustomer loanCustomer) {
		this.loanCustomer = loanCustomer;
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
	public String getMateCertNum() {
		return mateCertNum;
	}
	public void setMateCertNum(String mateCertNum) {
		this.mateCertNum = mateCertNum;
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
		return customerMobilePhone;
	}
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
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
	public CustomerLivings getCustomerLivings() {
		return customerLivings;
	}
	public void setCustomerLivings(CustomerLivings customerLivings) {
		this.customerLivings = customerLivings;
	}
	public LoanBank getLoanBank() {
		return loanBank;
	}
	public void setLoanBank(LoanBank loanBank) {
		this.loanBank = loanBank;
	}
	public List<LoanCoborrower> getLoanCoborrower() {
		return loanCoborrower;
	}
	public void setLoanCoborrower(List<LoanCoborrower> loanCoborrower) {
		this.loanCoborrower = loanCoborrower;
	}
	public LoanCompany getLoanCompany() {
		return customerLoanCompany;
	}
	public void setLoanCompany(LoanCompany customerLoanCompany) {
		this.customerLoanCompany = customerLoanCompany;
	}
	
	public List<LoanCreditInfo> getLoanCreditInfoList() {
		return loanCreditInfoList;
	}
	public void setLoanCreditInfoList(List<LoanCreditInfo> loanCreditInfoList) {
		this.loanCreditInfoList = loanCreditInfoList;
	}
	
	public LoanCompany getCustomerLoanCompany() {
		return customerLoanCompany;
	}
	public void setCustomerLoanCompany(LoanCompany customerLoanCompany) {
		this.customerLoanCompany = customerLoanCompany;
	}
	public List<LoanHouse> getCustomerLoanHouseList() {
		return customerLoanHouseList;
	}
	public void setCustomerLoanHouseList(List<LoanHouse> customerLoanHouseList) {
		this.customerLoanHouseList = customerLoanHouseList;
	}
	public LoanInfo getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	public LoanMate getLoanMate() {
		return loanMate;
	}
	public void setLoanMate(LoanMate loanMate) {
		this.loanMate = loanMate;
	}
	public LoanRemark getLoanRemark() {
		return loanRemark;
	}
	public void setLoanRemark(LoanRemark loanRemark) {
		this.loanRemark = loanRemark;
	}
	public List<Contact> getCustomerContactList() {
		return customerContactList;
	}
	public void setCustomerContactList(List<Contact> customerContactList) {
		this.customerContactList = customerContactList;
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
	public LoanCompManage getLoanCompManage() {
		return loanCompManage;
	}
	public void setLoanCompManage(LoanCompManage loanCompManage) {
		this.loanCompManage = loanCompManage;
	}
	public LoanPersonalCertificate getLoanPersonalCertificate() {
		return loanPersonalCertificate;
	}
	public void setLoanPersonalCertificate(
			LoanPersonalCertificate loanPersonalCertificate) {
		this.loanPersonalCertificate = loanPersonalCertificate;
	}
	public List<Contact> getRelationContactList() {
		return relationContactList;
	}
	public void setRelationContactList(List<Contact> relationContactList) {
		this.relationContactList = relationContactList;
	}
	public List<Contact> getWorkProveContactList() {
		return workProveContactList;
	}
	public void setWorkProveContactList(List<Contact> workProveContactList) {
		this.workProveContactList = workProveContactList;
	}
	public List<Contact> getOtherContactList() {
		return otherContactList;
	}
	public void setOtherContactList(List<Contact> otherContactList) {
		this.otherContactList = otherContactList;
	}
}
