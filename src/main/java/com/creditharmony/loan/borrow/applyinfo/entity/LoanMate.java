package com.creditharmony.loan.borrow.applyinfo.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 配偶信息
 * 
 * @Class Name LoanMate
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanMate extends DataEntity<LoanMate> {

	private static final long serialVersionUID = 1884340644438775212L;
	// 配偶公司
	private LoanCompany mateLoanCompany;
	// 借款编码
	private String loanCode;
	// 姓名
	private String mateName;
	// 年龄
	private Integer mateAge;
	// 关联类型(主借人/共借人)
	private String loanCustomterType;
	// 出生日期
	private Date mateBirthday;
	// 证件类型
	private String dictCertType;
	// 证件号码
	private String mateCertNum;
	// 手机
	private String mateTel;
	// 关联ID
	private String rcustomerCoborrowerId;

	// 邮箱
	private String mateEmail;
	// 配偶地址省
	private String mateAddressProvince;
	// 配偶地址市
	private String mateAddressCity;
	// 配偶地址县
	private String mateAddressArea;
	// 配偶详细地址
	private String mateAddress;
	
	public LoanCompany getMateLoanCompany() {
		return mateLoanCompany;
	}

	public void setMateLoanCompany(LoanCompany mateLoanCompany) {
		this.mateLoanCompany = mateLoanCompany;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName == null ? null : mateName.trim();
	}

	public Integer getMateAge() {
		return mateAge;
	}

	public void setMateAge(Integer mateAge) {
		this.mateAge = mateAge;
	}

	public String getLoanCustomterType() {
		return loanCustomterType;
	}

	public void setLoanCustomterType(String loanCustomterType) {
		this.loanCustomterType = loanCustomterType;
	}

	public Date getMateBirthday() {
		return mateBirthday;
	}

	public void setMateBirthday(Date mateBirthday) {
		this.mateBirthday = mateBirthday;
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

	public String getMateTel() {
		return mateTel;
	}

	public void setMateTel(String mateTel) {
		this.mateTel = mateTel;
	}

	public String getRcustomerCoborrowerId() {
		return rcustomerCoborrowerId;
	}

	public void setRcustomerCoborrowerId(String rcustomerCoborrowerId) {
		this.rcustomerCoborrowerId = rcustomerCoborrowerId;
	}

	public String getMateEmail() {
		return mateEmail;
	}

	public void setMateEmail(String mateEmail) {
		this.mateEmail = mateEmail;
	}

	public String getMateAddressProvince() {
		return mateAddressProvince;
	}

	public void setMateAddressProvince(String mateAddressProvince) {
		this.mateAddressProvince = mateAddressProvince;
	}

	public String getMateAddressCity() {
		return mateAddressCity;
	}

	public void setMateAddressCity(String mateAddressCity) {
		this.mateAddressCity = mateAddressCity;
	}

	public String getMateAddress() {
		return mateAddress;
	}

	public void setMateAddress(String mateAddress) {
		this.mateAddress = mateAddress;
	}

	public String getMateAddressArea() {
		return mateAddressArea;
	}

	public void setMateAddressArea(String mateAddressArea) {
		this.mateAddressArea = mateAddressArea;
	}
}