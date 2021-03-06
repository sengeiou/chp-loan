package com.creditharmony.loan.borrow.applyinfo.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 
 * @Class Name Customer
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
public class Customer extends DataEntity<Customer> {

	private static final long serialVersionUID = -4826593454690408310L;

    private String applyId;                      //applyid
    private String customerCode;                 //客户编码
    private String loanCode;                     //借款编号
    private String customerName;                 //客户姓名
    private String customerSex;                  //性别
    private String customerSexLabel;			 //性别名
    private String customerRegisterProvince;     //户籍省
    private String customerRegisterCity;         //户籍市
    private String customerRegisterArea;         //户籍区
    private String customerRegisterAddress;      //户籍地址
    private Date customerBirthday;               //出生日期
    private String customerCertOrg;              //发证机关
    private String idStartDate;                    //身份证有效期开始时间
    private String idEndDate;            			 //身份证有效期结束时间
    private String customerEname;            		 //英文名称
    private String dictMarry;     				 //婚姻状况
    private String dictMarryLabel;				 //婚姻状况名称
    private String dictEducation;    		 //学历
    private String dictEducationLabel;		 //学历名称	
    private Date customerGraduationDay;       //毕业日期
    private String customerPhoneFirst;     	 //手机号
    private String customerPhoneSecond;    	 //手机号2
    private String customerTel;    			 //固定电话
    private String customerEmail;     			 //电子邮箱
    private String customerFax;     			 //客户传真
    private String customerHaveChildren;     	 //有无子女
    private String liveProvince;     			 //居住省
    private String liveCity;     				 //居住市
    private String liveArea;    				 //居住区
    private String customerAddress;    		     //详细地址
    private String customerHouseProperty;     	 //住房性质
    private String customerPostCode;     	     //邮编
    private String customerStatus;     	         //有效、作废
    private String customerOther;     	         //其他说明
    private String dictCustomerDanger;     	     //是否风险客户
    private String dictCustomerLoanType;     	 //客户类型
    private String dictCustomerLoanTypeLabel;	 //客户类型名称
    private String dictCustomerStatus;     	     //客户状态
    private String ContactIsKnow;     			 //家人是否知悉此借款
    private String customerIsGold;     	         //是否开通金账户
    private String dictCustomerSource;     	     //由何处得知公司
    private String teleSalesSource;     	     //电销来源
    private String loanIsPhone;     	         //是否电销
    private String teleSalesOrgid;     	         //吊销组织机构ID
	private String dictCompIndustry;			 //行业类别
	private String dictCertType;				 //证件类型
	private String dictCertTypeLabel;			 //证件类型名称
	private String customerCertNum;				 //证件号码
	
	public String getCustomerSexLabel() {
		return customerSexLabel;
	}
	public void setCustomerSexLabel(String customerSexLabel) {
		this.customerSexLabel = customerSexLabel;
	}
	public String getDictMarryLabel() {
		return dictMarryLabel;
	}
	public void setDictMarryLabel(String dictMarryLabel) {
		this.dictMarryLabel = dictMarryLabel;
	}
	public String getDictEducationLabel() {
		return dictEducationLabel;
	}
	public void setDictEducationLabel(String dictEducationLabel) {
		this.dictEducationLabel = dictEducationLabel;
	}
	public String getDictCustomerLoanTypeLabel() {
		return dictCustomerLoanTypeLabel;
	}
	public void setDictCustomerLoanTypeLabel(String dictCustomerLoanTypeLabel) {
		this.dictCustomerLoanTypeLabel = dictCustomerLoanTypeLabel;
	}
	public String getDictCertTypeLabel() {
		return dictCertTypeLabel;
	}
	public void setDictCertTypeLabel(String dictCertTypeLabel) {
		this.dictCertTypeLabel = dictCertTypeLabel;
	}
	public String getDictCompIndustry() {
		return dictCompIndustry;
	}
	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	public String getCustomerRegisterProvince() {
		return customerRegisterProvince;
	}
	
	public String getIdStartDate() {
		return idStartDate;
	}
	public void setIdStartDate(String idStartDate) {
		this.idStartDate = idStartDate;
	}
	public String getIdEndDate() {
		return idEndDate;
	}
	public void setIdEndDate(String idEndDate) {
		this.idEndDate = idEndDate;
	}
	public String getCustomerEname() {
		return customerEname;
	}
	public void setCustomerEname(String customerEname) {
		this.customerEname = customerEname;
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
	public Date getCustomerBirthday() {
		return customerBirthday;
	}
	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}
	public String getCustomerCertOrg() {
		return customerCertOrg;
	}
	public void setCustomerCertOrg(String customerCertOrg) {
		this.customerCertOrg = customerCertOrg;
	}
	
	
	public String getDictMarry() {
		return dictMarry;
	}
	public void setDictMarry(String dictMarry) {
		this.dictMarry = dictMarry;
	}
	public String getDictEducation() {
		return dictEducation;
	}
	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}
	public Date getCustomerGraduationDay() {
		return customerGraduationDay;
	}
	public void setCustomerGraduationDay(Date customerGraduationDay) {
		this.customerGraduationDay = customerGraduationDay;
	}
	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}
	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}
	public String getCustomerPhoneSecond() {
		return customerPhoneSecond;
	}
	public void setCustomerPhoneSecond(String customerPhoneSecond) {
		this.customerPhoneSecond = customerPhoneSecond;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerFax() {
		return customerFax;
	}
	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}
	public String getCustomerHaveChildren() {
		return customerHaveChildren;
	}
	public void setCustomerHaveChildren(String customerHaveChildren) {
		this.customerHaveChildren = customerHaveChildren;
	}
	public String getLiveProvince() {
		return liveProvince;
	}
	public void setLiveProvince(String liveProvince) {
		this.liveProvince = liveProvince;
	}
	public String getLiveCity() {
		return liveCity;
	}
	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}
	public String getLiveArea() {
		return liveArea;
	}
	public void setLiveArea(String liveArea) {
		this.liveArea = liveArea;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerHouseProperty() {
		return customerHouseProperty;
	}
	public void setCustomerHouseProperty(String customerHouseProperty) {
		this.customerHouseProperty = customerHouseProperty;
	}
	public String getCustomerPostCode() {
		return customerPostCode;
	}
	public void setCustomerPostCode(String customerPostCode) {
		this.customerPostCode = customerPostCode;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getCustomerOther() {
		return customerOther;
	}
	public void setCustomerOther(String customerOther) {
		this.customerOther = customerOther;
	}
	public String getDictCustomerDanger() {
		return dictCustomerDanger;
	}
	public void setDictCustomerDanger(String dictCustomerDanger) {
		this.dictCustomerDanger = dictCustomerDanger;
	}
	public String getDictCustomerLoanType() {
		return dictCustomerLoanType;
	}
	public void setDictCustomerLoanType(String dictCustomerLoanType) {
		this.dictCustomerLoanType = dictCustomerLoanType;
	}
	public String getDictCustomerStatus() {
		return dictCustomerStatus;
	}
	public void setDictCustomerStatus(String dictCustomerStatus) {
		this.dictCustomerStatus = dictCustomerStatus;
	}
	public String getContactIsKnow() {
		return ContactIsKnow;
	}
	public void setContactIsKnow(String contactIsKnow) {
		ContactIsKnow = contactIsKnow;
	}
	public String getCustomerIsGold() {
		return customerIsGold;
	}
	public void setCustomerIsGold(String customerIsGold) {
		this.customerIsGold = customerIsGold;
	}
	public String getDictCustomerSource() {
		return dictCustomerSource;
	}
	public void setDictCustomerSource(String dictCustomerSource) {
		this.dictCustomerSource = dictCustomerSource;
	}
	public String getTeleSalesSource() {
		return teleSalesSource;
	}
	public void setTeleSalesSource(String teleSalesSource) {
		this.teleSalesSource = teleSalesSource;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getTeleSalesOrgid() {
		return teleSalesOrgid;
	}
	public void setTeleSalesOrgid(String teleSalesOrgid) {
		this.teleSalesOrgid = teleSalesOrgid;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ContactIsKnow == null) ? 0 : ContactIsKnow.hashCode());
		result = prime * result + ((applyId == null) ? 0 : applyId.hashCode());
		result = prime * result
				+ ((customerAddress == null) ? 0 : customerAddress.hashCode());
		result = prime
				* result
				+ ((customerBirthday == null) ? 0 : customerBirthday.hashCode());
		result = prime * result
				+ ((customerCertOrg == null) ? 0 : customerCertOrg.hashCode());
		result = prime * result
				+ ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime
				* result
				+ ((dictEducation == null) ? 0 : dictEducation
						.hashCode());
		result = prime * result
				+ ((customerEmail == null) ? 0 : customerEmail.hashCode());
		result = prime * result
				+ ((customerEname == null) ? 0 : customerEname.hashCode());
		result = prime * result
				+ ((customerFax == null) ? 0 : customerFax.hashCode());
		result = prime
				* result
				+ ((customerGraduationDay == null) ? 0
						: customerGraduationDay.hashCode());
		result = prime
				* result
				+ ((customerHaveChildren == null) ? 0 : customerHaveChildren
						.hashCode());
		result = prime
				* result
				+ ((customerHouseProperty == null) ? 0 : customerHouseProperty
						.hashCode());
		result = prime * result
				+ ((customerIsGold == null) ? 0 : customerIsGold.hashCode());
		result = prime
				* result
				+ ((customerPhoneFirst == null) ? 0 : customerPhoneFirst
						.hashCode());
		result = prime
				* result
				+ ((customerPhoneSecond == null) ? 0 : customerPhoneSecond
						.hashCode());
		result = prime * result
				+ ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result
				+ ((customerOther == null) ? 0 : customerOther.hashCode());
		result = prime * result
				+ ((customerTel == null) ? 0 : customerTel.hashCode());
		result = prime
				* result
				+ ((customerPostCode == null) ? 0 : customerPostCode.hashCode());
		result = prime
				* result
				+ ((customerRegisterAddress == null) ? 0
						: customerRegisterAddress.hashCode());
		result = prime
				* result
				+ ((customerRegisterArea == null) ? 0 : customerRegisterArea
						.hashCode());
		result = prime
				* result
				+ ((customerRegisterCity == null) ? 0 : customerRegisterCity
						.hashCode());
		result = prime
				* result
				+ ((customerRegisterProvince == null) ? 0
						: customerRegisterProvince.hashCode());
		result = prime * result
				+ ((customerSex == null) ? 0 : customerSex.hashCode());
		result = prime * result
				+ ((customerStatus == null) ? 0 : customerStatus.hashCode());
					
		result = prime
				* result
				+ ((dictCustomerDanger == null) ? 0 : dictCustomerDanger
						.hashCode());
		result = prime
				* result
				+ ((dictCustomerLoanType == null) ? 0 : dictCustomerLoanType
						.hashCode());
		result = prime
				* result
				+ ((dictCustomerSource == null) ? 0 : dictCustomerSource
						.hashCode());
		result = prime
				* result
				+ ((dictCustomerStatus == null) ? 0 : dictCustomerStatus
						.hashCode());
		result = prime * result
				+ ((dictMarry == null) ? 0 : dictMarry.hashCode());
		result = prime * result
				+ ((idEndDate == null) ? 0 : idEndDate.hashCode());
		result = prime * result
				+ ((idStartDate == null) ? 0 : idStartDate.hashCode());
		result = prime * result
				+ ((liveArea == null) ? 0 : liveArea.hashCode());
		result = prime * result
				+ ((liveCity == null) ? 0 : liveCity.hashCode());
		result = prime * result
				+ ((liveProvince == null) ? 0 : liveProvince.hashCode());
		result = prime * result
				+ ((loanCode == null) ? 0 : loanCode.hashCode());
		result = prime * result
				+ ((loanIsPhone == null) ? 0 : loanIsPhone.hashCode());
				
		result = prime * result
				+ ((teleSalesOrgid == null) ? 0 : teleSalesOrgid.hashCode());
		result = prime * result
				+ ((teleSalesSource == null) ? 0 : teleSalesSource.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (ContactIsKnow == null) {
			if (other.ContactIsKnow != null)
				return false;
		} else if (!ContactIsKnow.equals(other.ContactIsKnow))
			return false;
		if (applyId == null) {
			if (other.applyId != null)
				return false;
		} else if (!applyId.equals(other.applyId))
			return false;
		if (customerAddress == null) {
			if (other.customerAddress != null)
				return false;
		} else if (!customerAddress.equals(other.customerAddress))
			return false;
		if (customerBirthday == null) {
			if (other.customerBirthday != null)
				return false;
		} else if (!customerBirthday.equals(other.customerBirthday))
			return false;
		if (customerCertOrg == null) {
			if (other.customerCertOrg != null)
				return false;
		} else if (!customerCertOrg.equals(other.customerCertOrg))
			return false;
		if (customerCode == null) {
			if (other.customerCode != null)
				return false;
		} else if (!customerCode.equals(other.customerCode))
			return false;
		if (dictEducation == null) {
			if (other.dictEducation != null)
				return false;
		} else if (!dictEducation.equals(other.dictEducation))
			return false;
		if (customerEmail == null) {
			if (other.customerEmail != null)
				return false;
		} else if (!customerEmail.equals(other.customerEmail))
			return false;
		if (customerEname == null) {
			if (other.customerEname != null)
				return false;
		} else if (!customerEname.equals(other.customerEname))
			return false;
		if (customerFax == null) {
			if (other.customerFax != null)
				return false;
		} else if (!customerFax.equals(other.customerFax))
			return false;
		if (customerGraduationDay == null) {
			if (other.customerGraduationDay != null)
				return false;
		} else if (!customerGraduationDay.equals(other.customerGraduationDay))
			return false;
		if (customerHaveChildren == null) {
			if (other.customerHaveChildren != null)
				return false;
		} else if (!customerHaveChildren.equals(other.customerHaveChildren))
			return false;
		if (customerHouseProperty == null) {
			if (other.customerHouseProperty != null)
				return false;
		} else if (!customerHouseProperty.equals(other.customerHouseProperty))
			return false;
		if (customerIsGold == null) {
			if (other.customerIsGold != null)
				return false;
		} else if (!customerIsGold.equals(other.customerIsGold))
			return false;
		if (customerPhoneFirst == null) {
			if (other.customerPhoneFirst != null)
				return false;
		} else if (!customerPhoneFirst.equals(other.customerPhoneFirst))
			return false;
		if (customerPhoneSecond == null) {
			if (other.customerPhoneSecond != null)
				return false;
		} else if (!customerPhoneSecond.equals(other.customerPhoneSecond))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerOther == null) {
			if (other.customerOther != null)
				return false;
		} else if (!customerOther.equals(other.customerOther))
			return false;
		if (customerTel == null) {
			if (other.customerTel != null)
				return false;
		} else if (!customerTel.equals(other.customerTel))
			return false;
		if (customerPostCode == null) {
			if (other.customerPostCode != null)
				return false;
		} else if (!customerPostCode.equals(other.customerPostCode))
			return false;
		if (customerRegisterAddress == null) {
			if (other.customerRegisterAddress != null)
				return false;
		} else if (!customerRegisterAddress
				.equals(other.customerRegisterAddress))
			return false;
		if (customerRegisterArea == null) {
			if (other.customerRegisterArea != null)
				return false;
		} else if (!customerRegisterArea.equals(other.customerRegisterArea))
			return false;
		if (customerRegisterCity == null) {
			if (other.customerRegisterCity != null)
				return false;
		} else if (!customerRegisterCity.equals(other.customerRegisterCity))
			return false;
		if (customerRegisterProvince == null) {
			if (other.customerRegisterProvince != null)
				return false;
		} else if (!customerRegisterProvince
				.equals(other.customerRegisterProvince))
			return false;
		if (customerSex == null) {
			if (other.customerSex != null)
				return false;
		} else if (!customerSex.equals(other.customerSex))
			return false;
	   if (customerStatus == null) {
			if (other.customerStatus != null)
				return false;
		} else if (!customerStatus.equals(other.customerStatus))
			return false;
		
		if (dictCustomerDanger == null) {
			if (other.dictCustomerDanger != null)
				return false;
		} else if (!dictCustomerDanger.equals(other.dictCustomerDanger))
			return false;
		if (dictCustomerLoanType == null) {
			if (other.dictCustomerLoanType != null)
				return false;
		} else if (!dictCustomerLoanType.equals(other.dictCustomerLoanType))
			return false;
		if (dictCustomerSource == null) {
			if (other.dictCustomerSource != null)
				return false;
		} else if (!dictCustomerSource.equals(other.dictCustomerSource))
			return false;
		if (dictCustomerStatus == null) {
			if (other.dictCustomerStatus != null)
				return false;
		} else if (!dictCustomerStatus.equals(other.dictCustomerStatus))
			return false;
		if (dictMarry == null) {
			if (other.dictMarry != null)
				return false;
		} else if (!dictMarry.equals(other.dictMarry))
			return false;
		if (idEndDate == null) {
			if (other.idEndDate != null)
				return false;
		} else if (!idEndDate.equals(other.idEndDate))
			return false;
		if (idStartDate == null) {
			if (other.idStartDate != null)
				return false;
		} else if (!idStartDate.equals(other.idStartDate))
			return false;
		if (liveArea == null) {
			if (other.liveArea != null)
				return false;
		} else if (!liveArea.equals(other.liveArea))
			return false;
		if (liveCity == null) {
			if (other.liveCity != null)
				return false;
		} else if (!liveCity.equals(other.liveCity))
			return false;
		if (liveProvince == null) {
			if (other.liveProvince != null)
				return false;
		} else if (!liveProvince.equals(other.liveProvince))
			return false;
		if (loanCode == null) {
			if (other.loanCode != null)
				return false;
		} else if (!loanCode.equals(other.loanCode))
			return false;
		if (loanIsPhone == null) {
			if (other.loanIsPhone != null)
				return false;
		} else if (!loanIsPhone.equals(other.loanIsPhone))
			return false;
	
		if (teleSalesOrgid == null) {
			if (other.teleSalesOrgid != null)
				return false;
		} else if (!teleSalesOrgid.equals(other.teleSalesOrgid))
			return false;
		if (teleSalesSource == null) {
			if (other.teleSalesSource != null)
				return false;
		} else if (!teleSalesSource.equals(other.teleSalesSource))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Customer [applyId=" + applyId + ", customerCode="
				+ customerCode + ", loanCode=" + loanCode + ", customerName="
				+ customerName + ", customerSex=" + customerSex
				+ ", customerRegisterProvince=" + customerRegisterProvince
				+ ", customerRegisterCity=" + customerRegisterCity
				+ ", customerRegisterArea=" + customerRegisterArea
				+ ", customerRegisterAddress=" + customerRegisterAddress
				+ ", customerBirthday=" + customerBirthday
				+ ", customerCertOrg=" + customerCertOrg + ", idStartDate="
				+ idStartDate + ", idEndDate=" + idEndDate + ", customerEname="
				+ customerEname + ", dictMarry=" + dictMarry
				+ ", dictEducation=" + dictEducation
				+ ", customerGraduationDay=" + customerGraduationDay
				+ ", customerPhoneFirst=" + customerPhoneFirst
				+ ", customerPhoneSecond=" + customerPhoneSecond
				+ ", customerTel=" + customerTel + ", customerEmail="
				+ customerEmail + ", customerFax=" + customerFax
				+ ", customerHaveChildren=" + customerHaveChildren
				+ ", liveProvince=" + liveProvince + ", liveCity=" + liveCity
				+ ", liveArea=" + liveArea + ", customerAddress="
				+ customerAddress + ", customerHouseProperty="
				+ customerHouseProperty + ", customerPostCode="
				+ customerPostCode + ", customerStatus=" + customerStatus
				+ ", customerOther=" + customerOther + ", dictCustomerDanger="
				+ dictCustomerDanger + ", dictCustomerLoanType="
				+ dictCustomerLoanType + ", dictCustomerStatus="
				+ dictCustomerStatus + ", ContactIsKnow=" + ContactIsKnow
				+ ", customerIsGold=" + customerIsGold
				+ ", dictCustomerSource=" + dictCustomerSource
				+ ", teleSalesSource=" + teleSalesSource + ", loanIsPhone="
				+ loanIsPhone + ", teleSalesOrgid=" + teleSalesOrgid
				+ ", dictCompIndustry=" + dictCompIndustry + "]";
	}
	
	
    
	
    
    
}
