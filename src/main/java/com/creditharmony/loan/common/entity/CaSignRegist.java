/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityCaSignRegist.java
 * @Create By 王彬彬
 * @Create In 2016年4月29日 下午6:55:55
 */
package com.creditharmony.loan.common.entity;

/**
 * 保证人注册信息
 * 
 * @Class Name CaSignRegist
 * @author 王彬彬
 * @Create In 2016年4月29日
 */
public class CaSignRegist {

	private String guaranteeName;// 保证人姓名
	private String guaranteeIdNum; // 身份证件号
	private String guaranteeMail; // 电子邮件
	private String guaranteeMobile; // 移动电话
	private String guaranteeTel; // 电话

	private String companyName; // 公司名称
	private String companyProvince; // 省份
	private String companyPaperId; // 文件id
	private String companyRegisteredNo;// 企业工商注册号

	private String certContainer; //  应用名,注册成功后使用
	private String transID;// 缓存号 ,注册成功后使用
	private String registerDate; //注册时间
	
	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuaranteeIdNum() {
		return guaranteeIdNum;
	}

	public void setGuaranteeIdNum(String guaranteeIdNum) {
		this.guaranteeIdNum = guaranteeIdNum;
	}

	public String getGuaranteeMail() {
		return guaranteeMail;
	}

	public void setGuaranteeMail(String guaranteeMail) {
		this.guaranteeMail = guaranteeMail;
	}

	public String getGuaranteeMobile() {
		return guaranteeMobile;
	}

	public void setGuaranteeMobile(String guaranteeMobile) {
		this.guaranteeMobile = guaranteeMobile;
	}

	public String getGuaranteeTel() {
		return guaranteeTel;
	}

	public void setGuaranteeTel(String guaranteeTel) {
		this.guaranteeTel = guaranteeTel;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyPaperId() {
		return companyPaperId;
	}

	public void setCompanyPaperId(String companyPaperId) {
		this.companyPaperId = companyPaperId;
	}

	public String getCompanyRegisteredNo() {
		return companyRegisteredNo;
	}

	public void setCompanyRegisteredNo(String companyRegisteredNo) {
		this.companyRegisteredNo = companyRegisteredNo;
	}

	public String getCertContainer() {
		return certContainer;
	}

	public void setCertContainer(String certContainer) {
		this.certContainer = certContainer;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

}
