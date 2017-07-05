package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 车借客户信息
 * @Class Name CarCustomer
 * @author 张进
 * @Create In  2016年1月21日
 */
public class CarCustomer extends DataEntity<CarCustomer> {

	private static final long serialVersionUID = -4826593454690408310L;
	
	private String id;
    private String applyId;                      //applyid
    private String customerCode;                 //客户编码
    private String loanCode;                     //借款编号
    private String customerName;                 //客户姓名
    private String customerSex;                  //性别
    private String customerRegisterProvince;     //户籍省
    private String customerRegisterCity;         //户籍市
    private String customerRegisterArea;         //户籍区
    private String customerRegisterAddress;      //户籍地址
    private Date customerBirthday;               //出生日期
    private String customerCertOrg;              //发证机关
    private Date idStartDate;                    //身份证有效期开始时间
    private Date idEndDate;            			 //身份证有效期结束时间
    private String customerEname;            		 //英文名称
    private String dictMarryStatus;     				 //婚姻状况
    private String dictEducation;    		 //学历
    private Date customerGraduationDay;       //毕业日期
    private String customerPhoneFirst;     	 //手机号
    private String customerPhoneFirstEnc; 
    private String customerPhoneSecond;    	 //手机号2
    private String customerPhoneSecondEnc;
    private String customerTel;    			 //固定电话
    private String customerEmail;     			 //电子邮箱
    private String customerFax;     			 //客户传真
    private String customerHaveChildren;     	 //有无子女
    private String customerLiveProvince;     			 //居住省
    private String customerLiveCity;     				 //居住市
    private String customerLiveArea;    				 //居住区
    private String customerAddress;    		     //详细地址
    private String customerPostCode;     	     //邮编
    private String customerStatus;     	         //有效、作废
    private String customerOther;     	         //其他说明
    private String dictCustomerDanger;     	     //是否风险客户
    private String dictCustomerLoanType;     	 //客户类型
    private String dictCustomerStatus;     	     //客户状态
    private String ContactIsKnow;     			 //家人是否知悉此借款
    private String customerIsGold;     	         //是否开通金账户
    private String dictCustomerSource;     	     //由何处得知公司
    private String teleSalesSource;     	     //电销来源
    private String loanIsPhone;     	         //是否电销
    private String teleSalesOrgid;     	         //吊销组织机构ID
    private String dictCompIndustry;			//行业类别
    
	private String isLongTerm;                  //是否长期
	private String dictCustomerSource2;         //客户来源
	private String customerTempPermit;          //暂住证
	private Date customerFirstLivingDay;        //起始居住时间
	private String cityPhone;                   //本市电话
	private Date customerFirtArriveYear;      //初到本市年份
	private Double creditLine;                     //信用额度
	private Integer customerFamilySupport;          //供养亲属人数
	private Double jydzzmRentMonth;				//月租金
	private String customerHouseHoldProperty;   //住房性质
	private String dictCertType;
	private String customerCertNum;				//证件号码
	private Date idStartDay;
	private Date idEndDay;
	
	
	private String dictCustomerIsDanger;
	private String dictCustomerDiff;
	private String dictSourceType;
	private String customerContactIsKnow;
	private String dictRelationType;
	private String customerGoldFlag;
	private String customerTelesalesSource;
	private String customerTelesalesFlag;
	private String customerTelesalesOrgcode;
	private String coboHouseHoldHold;
	
	private String customerRegisterAddressView; //户籍地址查看用
	private String customerAddressView;//详细地址查看用
	
	private String customerSexCode;	//性别码值
	private String dictMarryStatusCode;	//婚姻状况码值
	private String dictEducationCode;    		 //学历码值
	private String CustomerHaveChildrenCode; //有无子女
	private String customerTempPermitCode; //暂住证
	private String customerHouseHoldPropertyCode; //住房性质
	private String dictCertTypeCode; //证件类型code值
	
	private String captchaIfConfirm;//验证码是否确认
    private Date confirmTimeout;// 确认超时结束时间
    private String customerPin; //客户验证码
    // app签字标识
    private String appSignFlag;
    // 身份证验证标识
    private String idValidFlag;
    // 合成照片ID
    private String composePhotoId;
    
    // 现场照片
    private String curPlotId;
    // 身份证照片
    private String idCardId;
    //身份验证分数
    private BigDecimal idValidScore;
     
    private String idArtificiaFlag;// 手动验证标识
	
	public String getDictCertTypeCode() {
		return dictCertTypeCode;
	}
	public void setDictCertTypeCode(String dictCertTypeCode) {
		this.dictCertTypeCode = dictCertTypeCode;
	}
	public String getCustomerSexCode() {
		return customerSexCode;
	}
	public void setCustomerSexCode(String customerSexCode) {
		this.customerSexCode = customerSexCode;
	}
	public String getDictMarryStatusCode() {
		return dictMarryStatusCode;
	}
	public void setDictMarryStatusCode(String dictMarryStatusCode) {
		this.dictMarryStatusCode = dictMarryStatusCode;
	}
	public String getDictEducationCode() {
		return dictEducationCode;
	}
	public void setDictEducationCode(String dictEducationCode) {
		this.dictEducationCode = dictEducationCode;
	}
	public String getCustomerHaveChildrenCode() {
		return CustomerHaveChildrenCode;
	}
	public void setCustomerHaveChildrenCode(String customerHaveChildrenCode) {
		CustomerHaveChildrenCode = customerHaveChildrenCode;
	}
	public String getCustomerTempPermitCode() {
		return customerTempPermitCode;
	}
	public void setCustomerTempPermitCode(String customerTempPermitCode) {
		this.customerTempPermitCode = customerTempPermitCode;
	}
	public String getCustomerHouseHoldPropertyCode() {
		return customerHouseHoldPropertyCode;
	}
	public void setCustomerHouseHoldPropertyCode(
			String customerHouseHoldPropertyCode) {
		this.customerHouseHoldPropertyCode = customerHouseHoldPropertyCode;
	}
	public Double getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(Double creditLine) {
		this.creditLine = creditLine;
	}
	public Integer getCustomerFamilySupport() {
		return customerFamilySupport;
	}
	public void setCustomerFamilySupport(Integer customerFamilySupport) {
		this.customerFamilySupport = customerFamilySupport;
	}
	public Double getJydzzmRentMonth() {
		return jydzzmRentMonth;
	}
	public void setJydzzmRentMonth(Double jydzzmRentMonth) {
		this.jydzzmRentMonth = jydzzmRentMonth;
	}
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
	public String getCustomerLiveProvince() {
		return customerLiveProvince;
	}
	public void setCustomerLiveProvince(String customerLiveProvince) {
		this.customerLiveProvince = customerLiveProvince;
	}
	public String getCustomerHouseHoldProperty() {
		return customerHouseHoldProperty;
	}
	public void setCustomerHouseHoldProperty(String customerHouseHoldProperty) {
		this.customerHouseHoldProperty = customerHouseHoldProperty;
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
	public Date getIdStartDay() {
		return idStartDay;
	}
	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
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
	
	public Date getIdStartDate() {
		return idStartDate;
	}
	public void setIdStartDate(Date idStartDate) {
		this.idStartDate = idStartDate;
	}
	public Date getIdEndDate() {
		return idEndDate;
	}
	public void setIdEndDate(Date idEndDate) {
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
	public String getCustomerPhoneFirstEnc() {
		return customerPhoneFirstEnc;
	}
	public String getCustomerPhoneSecondEnc() {
		return customerPhoneSecondEnc;
	}
	
	public String getCustomerPhoneFirst() {
		if(customerPhoneFirst != null && customerPhoneFirst.length()!=11){
			customerPhoneFirst = decryptPhones(customerPhoneFirst,"T_JK_LOAN_CUSTOMER","customer_phone_first");
		}
		return customerPhoneFirst;
	}
	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		if(customerPhoneFirst != null && customerPhoneFirst.length() == 11){
			customerPhoneFirst = encryptPhones(customerPhoneFirst,"T_JK_LOAN_CUSTOMER","customer_phone_first");
		}
		this.customerPhoneFirst = customerPhoneFirst;
		this.customerPhoneFirstEnc = customerPhoneFirst;
	}
	public String getCustomerPhoneSecond() {
		if(customerPhoneSecond != null){
			customerPhoneSecond = decryptPhones(customerPhoneSecond,"T_JK_LOAN_CUSTOMER","customer_phone_second");
		}
		return customerPhoneSecond;
	}
	public void setCustomerPhoneSecond(String customerPhoneSecond) {
		if(customerPhoneSecond != null && customerPhoneSecond.length() == 11){
			customerPhoneSecond = encryptPhones(customerPhoneSecond,"T_JK_LOAN_CUSTOMER","customer_phone_second");
		}
		this.customerPhoneSecond = customerPhoneSecond;
		this.customerPhoneSecondEnc = customerPhoneSecond;
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

	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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
	
	public String getIsLongTerm() {
		return isLongTerm;
	}
	public void setIsLongTerm(String isLongTerm) {
		this.isLongTerm = isLongTerm;
	}
	public String getDictCustomerSource2() {
		return dictCustomerSource2;
	}
	public void setDictCustomerSource2(String dictCustomerSource2) {
		this.dictCustomerSource2 = dictCustomerSource2;
	}
	public String getCustomerTempPermit() {
		return customerTempPermit;
	}
	public void setCustomerTempPermit(String customerTempPermit) {
		this.customerTempPermit = customerTempPermit;
	}
	public Date getCustomerFirstLivingDay() {
		return customerFirstLivingDay;
	}
	public void setCustomerFirstLivingDay(Date customerFirstLivingDay) {
		this.customerFirstLivingDay = customerFirstLivingDay;
	}
	public String getCityPhone() {
		return cityPhone;
	}
	public void setCityPhone(String cityPhone) {
		this.cityPhone = cityPhone;
	}
	public Date getCustomerFirtArriveYear() {
		return customerFirtArriveYear;
	}
	public void setCustomerFirtArriveYear(Date customerFirtArriveYear) {
		this.customerFirtArriveYear = customerFirtArriveYear;
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
	public String getDictCustomerIsDanger() {
		return dictCustomerIsDanger;
	}
	public void setDictCustomerIsDanger(String dictCustomerIsDanger) {
		this.dictCustomerIsDanger = dictCustomerIsDanger;
	}
	public String getDictCustomerDiff() {
		return dictCustomerDiff;
	}
	public void setDictCustomerDiff(String dictCustomerDiff) {
		this.dictCustomerDiff = dictCustomerDiff;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public String getCustomerContactIsKnow() {
		return customerContactIsKnow;
	}
	public void setCustomerContactIsKnow(String customerContactIsKnow) {
		this.customerContactIsKnow = customerContactIsKnow;
	}
	public String getDictRelationType() {
		return dictRelationType;
	}
	public void setDictRelationType(String dictRelationType) {
		this.dictRelationType = dictRelationType;
	}
	public String getCustomerGoldFlag() {
		return customerGoldFlag;
	}
	public void setCustomerGoldFlag(String customerGoldFlag) {
		this.customerGoldFlag = customerGoldFlag;
	}
	public String getCustomerTelesalesSource() {
		return customerTelesalesSource;
	}
	public void setCustomerTelesalesSource(String customerTelesalesSource) {
		this.customerTelesalesSource = customerTelesalesSource;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getCustomerTelesalesOrgcode() {
		return customerTelesalesOrgcode;
	}
	public void setCustomerTelesalesOrgcode(String customerTelesalesOrgcode) {
		this.customerTelesalesOrgcode = customerTelesalesOrgcode;
	}
	public String getCoboHouseHoldHold() {
		return coboHouseHoldHold;
	}
	public void setCoboHouseHoldHold(String coboHouseHoldHold) {
		this.coboHouseHoldHold = coboHouseHoldHold;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
				+ ((customerHouseHoldProperty == null) ? 0 : customerHouseHoldProperty
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
				+ ((dictMarryStatus == null) ? 0 : dictMarryStatus.hashCode());
		result = prime * result
				+ ((idEndDate == null) ? 0 : idEndDate.hashCode());
		result = prime * result
				+ ((idStartDate == null) ? 0 : idStartDate.hashCode());
		result = prime * result
				+ ((customerLiveArea == null) ? 0 : customerLiveArea.hashCode());
		result = prime * result
				+ ((customerLiveCity == null) ? 0 : customerLiveCity.hashCode());
		result = prime * result
				+ ((customerLiveProvince == null) ? 0 : customerLiveProvince.hashCode());
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
		CarCustomer other = (CarCustomer) obj;
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
		if (customerHouseHoldProperty == null) {
			if (other.customerHouseHoldProperty != null)
				return false;
		} else if (!customerHouseHoldProperty.equals(other.customerHouseHoldProperty))
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
		if (dictMarryStatus == null) {
			if (other.dictMarryStatus != null)
				return false;
		} else if (!dictMarryStatus.equals(other.dictMarryStatus))
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
		if (customerLiveArea == null) {
			if (other.customerLiveArea != null)
				return false;
		} else if (!customerLiveArea.equals(other.customerLiveArea))
			return false;
		if (customerLiveCity == null) {
			if (other.customerLiveCity != null)
				return false;
		} else if (!customerLiveCity.equals(other.customerLiveCity))
			return false;
		if (customerLiveProvince == null) {
			if (other.customerLiveProvince != null)
				return false;
		} else if (!customerLiveProvince.equals(other.customerLiveProvince))
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
				+ customerEname + ", dictMarryStatus=" + dictMarryStatus
				+ ", dictEducation=" + dictEducation
				+ ", customerGraduationDay=" + customerGraduationDay
				+ ", customerPhoneFirst=" + customerPhoneFirst
				+ ", customerPhoneSecond=" + customerPhoneSecond
				+ ", customerTel=" + customerTel + ", customerEmail="
				+ customerEmail + ", customerFax=" + customerFax
				+ ", customerHaveChildren=" + customerHaveChildren
				+ ", customerLiveProvince=" + customerLiveProvince + ", customerLiveCity=" + customerLiveCity
				+ ", customerLiveArea=" + customerLiveArea + ", customerAddress="
				+ customerAddress + ", customerHouseProperty="
				+ customerHouseHoldProperty + ", customerPostCode="
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaptchaIfConfirm() {
		return captchaIfConfirm;
	}
	public void setCaptchaIfConfirm(String captchaIfConfirm) {
		this.captchaIfConfirm = captchaIfConfirm;
	}
	public Date getConfirmTimeout() {
		return confirmTimeout;
	}
	public void setConfirmTimeout(Date confirmTimeout) {
		this.confirmTimeout = confirmTimeout;
	}
	public String getCustomerPin() {
		return customerPin;
	}
	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}
	public String getAppSignFlag() {
		return appSignFlag;
	}
	public void setAppSignFlag(String appSignFlag) {
		this.appSignFlag = appSignFlag;
	}
	public String getIdValidFlag() {
		return idValidFlag;
	}
	public void setIdValidFlag(String idValidFlag) {
		this.idValidFlag = idValidFlag;
	}
	public String getComposePhotoId() {
		return composePhotoId;
	}
	public void setComposePhotoId(String composePhotoId) {
		this.composePhotoId = composePhotoId;
	}
	public String getCurPlotId() {
		return curPlotId;
	}
	public void setCurPlotId(String curPlotId) {
		this.curPlotId = curPlotId;
	}
	public String getIdCardId() {
		return idCardId;
	}
	public void setIdCardId(String idCardId) {
		this.idCardId = idCardId;
	}
	public BigDecimal getIdValidScore() {
		return idValidScore;
	}
	public void setIdValidScore(BigDecimal idValidScore) {
		this.idValidScore = idValidScore;
	}
	public String getIdArtificiaFlag() {
		return idArtificiaFlag;
	}
	public void setIdArtificiaFlag(String idArtificiaFlag) {
		this.idArtificiaFlag = idArtificiaFlag;
	}
	
	
    public static void main(String[] args) {
    	String enc = encryptPhones("13564534343","T_JK_LOAN_CUSTOMER","customer_phone_first");
    	System.out.println("enc:"+enc);
    	String dec = decryptPhones(enc,"T_JK_LOAN_CUSTOMER","customer_phone_first");
    	System.out.println("dec:"+dec);
	}
	
    
    
}
