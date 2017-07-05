package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 联系人
 * @Class Name CarCustomerContactPerson
 * @author 安子帅
 * @Create In 2016年1月22日
 */
public class CarCustomerContactPerson extends DataEntity<CarCustomerContactPerson> {

	private static final long serialVersionUID = -2921792403098896034L;
	
	  private String loanCode;    // 借款编码
	  private String rCustomerCoborrowerCode;    //关联CODE(主借人，共借人)
	  private String loanCustomterType;    //关联类型(主借人0/共借人1)
	  private String contactName;    //- 姓名
	  private String dictContactRelation;    //- 和本人关系
	  private String contactUint;    //单位名称
	  private String dictContactNowAddress;    // 家庭或工作单位地址
	  private String contactUnitTel;    //联系电话
	  private String contactUnitTelEnc;    //联系电话
	  private String compTel;		//单位电话
	  private String newLoanCode;
	  private String dictContactRelationName; //和本人关系名称
	  
	
	

	public String getLoanCode() {
		return loanCode;
	}
	public String getDictContactRelationName() {
		return dictContactRelationName;
	}
	public void setDictContactRelationName(String dictContactRelationName) {
		this.dictContactRelationName = dictContactRelationName;
	}
	public String getNewLoanCode() {
		return newLoanCode;
	}
	public void setNewLoanCode(String newLoanCode) {
		this.newLoanCode = newLoanCode;
	}
	public String getCompTel() {
		return compTel;
	}
	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}
	public String getId() {
		return id;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getrCustomerCoborrowerCode() {
		return rCustomerCoborrowerCode;
	}
	public void setrCustomerCoborrowerCode(String rCustomerCoborrowerCode) {
		this.rCustomerCoborrowerCode = rCustomerCoborrowerCode;
	}
	public String getLoanCustomterType() {
		return loanCustomterType;
	}
	public void setLoanCustomterType(String loanCustomterType) {
		this.loanCustomterType = loanCustomterType;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getDictContactRelation() {
		return dictContactRelation;
	}
	public void setDictContactRelation(String dictContactRelation) {
		this.dictContactRelation = dictContactRelation;
	}
	public String getContactUint() {
		return contactUint;
	}
	public void setContactUint(String contactUint) {
		this.contactUint = contactUint;
	}
	public String getDictContactNowAddress() {
		return dictContactNowAddress;
	}
	public void setDictContactNowAddress(String dictContactNowAddress) {
		this.dictContactNowAddress = dictContactNowAddress;
	}
	
	public String getContactUnitTel() {
        if(contactUnitTel != null){
            contactUnitTel = decryptPhones(contactUnitTel,"t_cj_customer_contact_person","contact_unit_tel");
        }
		return contactUnitTel;
	}

	public String getContactUnitTelEnc() {
		return contactUnitTelEnc;
	}

	public void setContactUnitTel(String contactUnitTel) {
		if(contactUnitTel != null && contactUnitTel.length() == 11){
			contactUnitTel = encryptPhones(contactUnitTel,"t_cj_customer_contact_person","contact_unit_tel");
		}
		this.contactUnitTel = contactUnitTel;
		this.contactUnitTelEnc = contactUnitTel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	  
}
