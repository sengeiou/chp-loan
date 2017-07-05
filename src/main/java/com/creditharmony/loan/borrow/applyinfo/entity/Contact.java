package com.creditharmony.loan.borrow.applyinfo.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 联系人信息
 * 
 * @Class Name Contact
 * @author 张平
 * @Create In 2015年12月3日
 */
public class Contact extends DataEntity<Contact> {

	private static final long serialVersionUID = 3109956857233484043L;
	// 借款编码
	private String loanCode;
	// 关联ID(主借人，共借人)
	private String rcustomerCoborrowerId;
    // 姓名
	private String contactName;
	// 和本人关系
	private String contactRelation;
	// 和本人关系名称
	private String contactRelationLabel;
    // 人员类型
	private String contactType;
	// 关联类型(主借人/共借人)
	private String loanCustomterType;
	// 工作单位
	private String contactSex;
	// 单位电话
	private String contactUnitTel;
	// MOBILE
	private String contactMobile;
	// 现住址
	private String contactNowAddress;
	// EMAIL
	private String contactEmail;
    // 关系类型
	private String relationType;
	// 关系类型名称
	private String relationTypeLabel;

	//新申请表 增加字段
	//身份证号码
	private String certNum;
	//宅电
	private String homeTel;
	//部门
	private String department;
	//职务
	private String post;
	//备注
	private String remarks;
	
	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getContactRelationLabel() {
		return contactRelationLabel;
	}

	public void setContactRelationLabel(String contactRelationLabel) {
		this.contactRelationLabel = contactRelationLabel;
	}

	public String getRelationTypeLabel() {
		return relationTypeLabel;
	}

	public void setRelationTypeLabel(String relationTypeLabel) {
		this.relationTypeLabel = relationTypeLabel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation == null ? null : contactRelation
				.trim();
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType == null ? null : contactType.trim();
	}

	public String getLoanCustomterType() {
		return loanCustomterType;
	}

	public void setLoanCustomterType(String loanCustomterType) {
		this.loanCustomterType = loanCustomterType == null ? null
				: loanCustomterType.trim();
	}

	public String getContactSex() {
		return contactSex;
	}

	public void setContactSex(String contactSex) {
		this.contactSex = contactSex;
	}

	public String getContactUnitTel() {
		return contactUnitTel;
	}

	public void setContactUnitTel(String contactUnitTel) {
		this.contactUnitTel = contactUnitTel == null ? null : contactUnitTel
				.trim();
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile == null ? null : contactMobile
				.trim();
	}

	public String getContactNowAddress() {
		return contactNowAddress;
	}

	public void setContactNowAddress(String contactNowAddress) {
		this.contactNowAddress = contactNowAddress == null ? null
				: contactNowAddress.trim();
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail == null ? null : contactEmail.trim();
	}

	public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

    public String getRcustomerCoborrowerId() {
        return rcustomerCoborrowerId;
    }

    public void setRcustomerCoborrowerId(String rcustomerCoborrowerId) {
        this.rcustomerCoborrowerId = rcustomerCoborrowerId;
    }

}