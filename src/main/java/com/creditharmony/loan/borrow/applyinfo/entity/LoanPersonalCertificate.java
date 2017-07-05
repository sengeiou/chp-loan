package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户证件信息
 * @Class Name LoanPersonalCertificate
 * @author 卢建学
 * @Create In 2016年09月12日
 */
public class LoanPersonalCertificate extends DataEntity<LoanPersonalCertificate> {

	private static final long serialVersionUID = 4803015898775983943L;

	private String loanCode; // 借款编码
	
	private String customerRelMaster; //申请人与户主关系

	private String customerRelMasterRemark; // 申请人与户主关系备注
	
	private String masterName; // 户主姓名

	private String masterCertNum; // 户主身份证号码
	
	private String childrenName; // 子女姓名
	
	private String childrenCertNum; // 子女身份证号码
	
	private String masterAddressProvince; // 户主页所在省市

	private String masterAddressCity; // 房产所在市
	
	private String masterAddressArea; // 户主页区名

	private String masterAddress; // 户主页地址
	
	private String educationalCertificateType; //学历证书类型

	private String graduationSchool; // 毕业院校
	
	private String educationalCertificateNum; // 学历证书编号

	private Date educationalCertificateTime; // 学历证书取得时间
	
	private Date weddingTime;  //结婚日期
	
	private String licenseIssuingAgency;  //发证机构
	
    private String customerName; // 客户姓名(为反显地址增加的字段)
    
    private String customerCertNum; // 证件号码(为反显地址增加的字段)
    
	private String customerAddressProvince; // 主借人的住址所在省市(为反显地址增加的字段)

	private String customerAddressCity; // 主借人的住址所在市(为反显地址增加的字段)
	
	private String customerAddressArea; // 主借人的住址区名(为反显地址增加的字段)

	private String customerAddress; // 主借人的住址地址(为反显地址增加的字段)
	
    private String dictMarry; // 婚姻状况(为校验结婚日期和发证机关增加的字段)
	
	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCustomerRelMaster() {
		return customerRelMaster;
	}

	public void setCustomerRelMaster(String customerRelMaster) {
		this.customerRelMaster = customerRelMaster;
	}

	public String getCustomerRelMasterRemark() {
		return customerRelMasterRemark;
	}

	public void setCustomerRelMasterRemark(String customerRelMasterRemark) {
		this.customerRelMasterRemark = customerRelMasterRemark;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getMasterCertNum() {
		return masterCertNum;
	}

	public void setMasterCertNum(String masterCertNum) {
		this.masterCertNum = masterCertNum;
	}

	public String getChildrenName() {
		return childrenName;
	}

	public void setChildrenName(String childrenName) {
		this.childrenName = childrenName;
	}

	public String getChildrenCertNum() {
		return childrenCertNum;
	}

	public void setChildrenCertNum(String childrenCertNum) {
		this.childrenCertNum = childrenCertNum;
	}

	public String getMasterAddressProvince() {
		return masterAddressProvince;
	}

	public void setMasterAddressProvince(String masterAddressProvince) {
		this.masterAddressProvince = masterAddressProvince;
	}

	public String getMasterAddressCity() {
		return masterAddressCity;
	}

	public void setMasterAddressCity(String masterAddressCity) {
		this.masterAddressCity = masterAddressCity;
	}

	public String getMasterAddressArea() {
		return masterAddressArea;
	}

	public void setMasterAddressArea(String masterAddressArea) {
		this.masterAddressArea = masterAddressArea;
	}

	public String getMasterAddress() {
		return masterAddress;
	}

	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}

	public String getEducationalCertificateType() {
		return educationalCertificateType;
	}

	public void setEducationalCertificateType(String educationalCertificateType) {
		this.educationalCertificateType = educationalCertificateType;
	}

	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	public String getEducationalCertificateNum() {
		return educationalCertificateNum;
	}

	public void setEducationalCertificateNum(String educationalCertificateNum) {
		this.educationalCertificateNum = educationalCertificateNum;
	}

	public Date getEducationalCertificateTime() {
		return educationalCertificateTime;
	}

	public void setEducationalCertificateTime(Date educationalCertificateTime) {
		this.educationalCertificateTime = educationalCertificateTime;
	}

	public Date getWeddingTime() {
		return weddingTime;
	}

	public void setWeddingTime(Date weddingTime) {
		this.weddingTime = weddingTime;
	}

	public String getLicenseIssuingAgency() {
		return licenseIssuingAgency;
	}

	public void setLicenseIssuingAgency(String licenseIssuingAgency) {
		this.licenseIssuingAgency = licenseIssuingAgency;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	
	public String getCustomerAddressProvince() {
		return customerAddressProvince;
	}

	public void setCustomerAddressProvince(String customerAddressProvince) {
		this.customerAddressProvince = customerAddressProvince;
	}

	public String getCustomerAddressCity() {
		return customerAddressCity;
	}

	public void setCustomerAddressCity(String customerAddressCity) {
		this.customerAddressCity = customerAddressCity;
	}

	public String getCustomerAddressArea() {
		return customerAddressArea;
	}

	public void setCustomerAddressArea(String customerAddressArea) {
		this.customerAddressArea = customerAddressArea;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public String getDictMarry() {
		return dictMarry;
	}

	public void setDictMarry(String dictMarry) {
		this.dictMarry = dictMarry;
	}
}