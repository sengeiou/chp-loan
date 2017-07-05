package com.creditharmony.loan.borrow.applyinfo.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ModifyInfoEx;

/**
 * 共借人基本信息
 * 
 * @Class Name LoanCoborrower
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanCoborrower extends DataEntity<LoanCoborrower> {

	private static final long serialVersionUID = 3788026010096235121L;
	private List<Contact> coborrowerContactList = new ArrayList<Contact>();
	/**
	 * 亲属联系人,把联系人列表按类型分开,前端用
	 */
	private List<Contact> relativesContactList = new ArrayList<Contact>();
	/**
	 * 工作联系人,把联系人列表按类型分开,前端用
	 */
	private List<Contact> workTogetherContactList = new ArrayList<Contact>();
	/**
	 * 其他联系人,把联系人列表按类型分开,前端用
	 */
	private List<Contact> otherContactList = new ArrayList<Contact>();
	
	private CustomerLivings coboLivings;   //客户居住情况
	private LoanCompany coboCompany;          //职业信息
	//自然人保证人借款意愿
	private LoanInfoCoborrower loanInfoCoborrower;
	private String loanCode; // 借款编码
	
	private String coboName; // 共借人姓名

	private Integer coboAge; // 共借人年龄

	private String coboSex; // 共借人性别
	
	private String coboSexName; // 共借人性别 文字

	private String dictCertType; // 证件类型
	
	private String dictCertTypeName; // 证件类型名称 
	
	private String dictEducation; // 学历
	// 住房性质
	private String customerHouseHoldProperty;
	
	private Date idStartDay;    // 证件有效期开始时间
	
	private Date idEndDay;      // 证件有效期结束时间
	
	private Date coboGraduationDay;  // 共借人毕业时间
	
	private String coboHouseHoldHold;  // 户口

	private String coboCertNum; // 证件号码

	private String coboHouseholdProvince; // 省
	
	private String coboHouseholdProvinceName; // 省名

	private String coboHouseholdCity; // 市
	
	private String coboHouseholdCityName; // 市名

	private String coboHouseholdArea; // 区
	
	private String coboHouseholdAreaName; // 区名

	private String coboHouseholdAddress; // 详细地址

	private String coboFamilyTel; // 家庭电话

	private String coboMobile; // MOBILE
	
	private String coboMobile2;

	private String coboLiveingProvince; // 现住址省
	
	private String coboLiveingProvinceName; // 现住址省名

	private String coboLiveingCity; // 现住址市
	
	private String coboLiveingCityName; // 现住址市名

	private String coboLiveingArea; // 现住址区
	
	private String coboLiveingAreaName; // 现住址区名

	private String coboNowAddress; // 现住址

	private String coboNowTel; // 现住址电话

	private String coboEmail; // EMAIL
	
	private String issuingAuthority;

	private String dictMarry; // 婚姻状况

	private String coboHaveChild; // 是否有子女

	private String coboContractIsKnow; // 家人是否知悉此贷款

	private String coboHouseOther; // 其他说明

	private String coboSocialSecurity; // 社保
	// 初到本市时间
	private String customerFirtArriveYear;
	// 起始居住时间
	private Date customerFirstLivingDay;
    // 客户类型
	private String dictCustomerDiff;
	// 短信验证码
	private String customerPin;
	// 验证码确认标识
	private String captchaIfConfirm;
	// 验证超时时间
	private Date confirmTimeout;
	// 修改详细信息(修改后)
	private ModifyInfo modifyInfo;
	// 修改详细信息(修改前)
	private ModifyInfoEx modifyInfoEx;
	// app签字标识
    private String appSignFlag;
    // 身份证验证标识
    private String idValidFlag;
    // 合同照片ID
    private String composePhotoId;	
    // 现场照片
    private String curPlotId;
    // 身份证照片
    private String idCardId;
    // 身份证验证分数
    private Float idValidScore;
    // 身份证评分提示信息
    private String idValidMessage;

    //自然人保证人新添字段
    //QQ
    private String  coboQq;
    //微博
    private String  coboWeibo;
    //子女人数
    private Integer childrenNum; 
    //供养人数
    private Integer supportNum;
    //个人年收入
    private BigDecimal personalYearIncome;
    //家庭月收入
    private BigDecimal homeMonthIncome;
    //家庭月支出
    private BigDecimal homeMonthPay;
    //家庭总负债
    private BigDecimal homeTotalDebt;
    //社保卡号
    private String socialSecurityNumber;
    //户籍性质
    private String registerProperty;
    //征信用户名
    private String creditUserName;
    //征信密码
    private String creditPassword;
    //授权码
    private String creditAuthCode;
    //住宅类别备注
    private String residentialCategoryRemark;
    //以上可知晓本次借款的联系人
    private String whoCanKnowTheBorrowing;
    //以上可知晓本次借款的联系人备注
    private String whoCanKnowTheBorrowingRemark;
    
    public String getWhoCanKnowTheBorrowingRemark() {
		return whoCanKnowTheBorrowingRemark;
	}

	public void setWhoCanKnowTheBorrowingRemark(String whoCanKnowTheBorrowingRemark) {
		this.whoCanKnowTheBorrowingRemark = whoCanKnowTheBorrowingRemark;
	}

	public String getWhoCanKnowTheBorrowing() {
		return whoCanKnowTheBorrowing;
	}

	public void setWhoCanKnowTheBorrowing(String whoCanKnowTheBorrowing) {
		this.whoCanKnowTheBorrowing = whoCanKnowTheBorrowing;
	}

	public LoanInfoCoborrower getLoanInfoCoborrower() {
		return loanInfoCoborrower;
	}

	public void setLoanInfoCoborrower(LoanInfoCoborrower loanInfoCoborrower) {
		this.loanInfoCoborrower = loanInfoCoborrower;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public Integer getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Integer supportNum) {
		this.supportNum = supportNum;
	}

	public BigDecimal getPersonalYearIncome() {
		return personalYearIncome;
	}

	public void setPersonalYearIncome(BigDecimal personalYearIncome) {
		this.personalYearIncome = personalYearIncome;
	}

	public BigDecimal getHomeMonthIncome() {
		return homeMonthIncome;
	}

	public void setHomeMonthIncome(BigDecimal homeMonthIncome) {
		this.homeMonthIncome = homeMonthIncome;
	}

	public BigDecimal getHomeMonthPay() {
		return homeMonthPay;
	}

	public void setHomeMonthPay(BigDecimal homeMonthPay) {
		this.homeMonthPay = homeMonthPay;
	}

	public BigDecimal getHomeTotalDebt() {
		return homeTotalDebt;
	}

	public void setHomeTotalDebt(BigDecimal homeTotalDebt) {
		this.homeTotalDebt = homeTotalDebt;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getRegisterProperty() {
		return registerProperty;
	}

	public void setRegisterProperty(String registerProperty) {
		this.registerProperty = registerProperty;
	}

	public String getCreditUserName() {
		return creditUserName;
	}

	public void setCreditUserName(String creditUserName) {
		this.creditUserName = creditUserName;
	}

	public String getCreditPassword() {
		return creditPassword;
	}

	public void setCreditPassword(String creditPassword) {
		this.creditPassword = creditPassword;
	}

	public String getCreditAuthCode() {
		return creditAuthCode;
	}

	public void setCreditAuthCode(String creditAuthCode) {
		this.creditAuthCode = creditAuthCode;
	}

	public String getCoboQq() {
		return coboQq;
	}

	public void setCoboQq(String coboQq) {
		this.coboQq = coboQq;
	}

	public String getCoboWeibo() {
		return coboWeibo;
	}

	public void setCoboWeibo(String coboWeibo) {
		this.coboWeibo = coboWeibo;
	}

	public String getCoboHouseholdProvinceName() {
		return coboHouseholdProvinceName;
	}

	public void setCoboHouseholdProvinceName(String coboHouseholdProvinceName) {
		this.coboHouseholdProvinceName = coboHouseholdProvinceName;
	}

	public String getCoboHouseholdCityName() {
		return coboHouseholdCityName;
	}

	public void setCoboHouseholdCityName(String coboHouseholdCityName) {
		this.coboHouseholdCityName = coboHouseholdCityName;
	}

	public String getCoboHouseholdAreaName() {
		return coboHouseholdAreaName;
	}

	public void setCoboHouseholdAreaName(String coboHouseholdAreaName) {
		this.coboHouseholdAreaName = coboHouseholdAreaName;
	}

	public String getCoboLiveingProvinceName() {
		return coboLiveingProvinceName;
	}

	public void setCoboLiveingProvinceName(String coboLiveingProvinceName) {
		this.coboLiveingProvinceName = coboLiveingProvinceName;
	}

	public String getCoboLiveingCityName() {
		return coboLiveingCityName;
	}

	public void setCoboLiveingCityName(String coboLiveingCityName) {
		this.coboLiveingCityName = coboLiveingCityName;
	}

	public String getCoboLiveingAreaName() {
		return coboLiveingAreaName;
	}

	public void setCoboLiveingAreaName(String coboLiveingAreaName) {
		this.coboLiveingAreaName = coboLiveingAreaName;
	}

	public ModifyInfoEx getModifyInfoEx() {
		return modifyInfoEx;
	}

	public void setModifyInfoEx(ModifyInfoEx modifyInfoEx) {
		this.modifyInfoEx = modifyInfoEx;
	}

	public ModifyInfo getModifyInfo() {
		return modifyInfo;
	}

	public void setModifyInfo(ModifyInfo modifyInfo) {
		this.modifyInfo = modifyInfo;
	}

	public List<Contact> getCoborrowerContactList() {
        return coborrowerContactList;
    }

    public void setCoborrowerContactList(List<Contact> coborrowerContactList) {
        this.coborrowerContactList = coborrowerContactList;
    }

    public List<Contact> getRelativesContactList() {
		return relativesContactList;
	}

	public void setRelativesContactList(List<Contact> relativesContactList) {
		this.relativesContactList = relativesContactList;
	}

	public List<Contact> getWorkTogetherContactList() {
		return workTogetherContactList;
	}

	public void setWorkTogetherContactList(List<Contact> workTogetherContactList) {
		this.workTogetherContactList = workTogetherContactList;
	}

	public List<Contact> getOtherContactList() {
		return otherContactList;
	}

	public void setOtherContactList(List<Contact> otherContactList) {
		this.otherContactList = otherContactList;
	}

	public CustomerLivings getCoboLivings() {
        return coboLivings;
    }

    public void setCoboLivings(CustomerLivings coboLivings) {
        this.coboLivings = coboLivings;
    }

    public LoanCompany getCoboCompany() {
        return coboCompany;
    }

    public void setCoboCompany(LoanCompany coboCompany) {
        this.coboCompany = coboCompany;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getCoboName() {
        return coboName;
    }

    public void setCoboName(String coboName) {
        this.coboName = coboName;
    }

    public Integer getCoboAge() {
        return coboAge;
    }

    public void setCoboAge(Integer coboAge) {
        this.coboAge = coboAge;
    }

    public String getCoboSex() {
        return coboSex;
    }

    public void setCoboSex(String coboSex) {
        this.coboSex = coboSex;
    }

    public String getCoboSexName() {
		return coboSexName;
	}

	public void setCoboSexName(String coboSexName) {
		this.coboSexName = coboSexName;
	}

	public String getDictCertType() {
        return dictCertType;
    }

    public void setDictCertType(String dictCertType) {
        this.dictCertType = dictCertType;
    }

    public String getDictCertTypeName() {
		return dictCertTypeName;
	}

	public void setDictCertTypeName(String dictCertTypeName) {
		this.dictCertTypeName = dictCertTypeName;
	}

	public String getDictEducation() {
        return dictEducation;
    }

    public void setDictEducation(String dictEducation) {
        this.dictEducation = dictEducation;
    }

    public String getCustomerHouseHoldProperty() {
        return customerHouseHoldProperty;
    }

    public void setCustomerHouseHoldProperty(String customerHouseHoldProperty) {
        this.customerHouseHoldProperty = customerHouseHoldProperty;
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

    public Date getCoboGraduationDay() {
        return coboGraduationDay;
    }

    public void setCoboGraduationDay(Date coboGraduationDay) {
        this.coboGraduationDay = coboGraduationDay;
    }

    public String getCoboHouseHoldHold() {
        return coboHouseHoldHold;
    }

    public void setCoboHouseHoldHold(String coboHouseHoldHold) {
        this.coboHouseHoldHold = coboHouseHoldHold;
    }

    public String getCoboCertNum() {
        return coboCertNum;
    }

    public void setCoboCertNum(String coboCertNum) {
        this.coboCertNum = coboCertNum;
    }

    public String getCoboHouseholdProvince() {
        return coboHouseholdProvince;
    }

    public void setCoboHouseholdProvince(String coboHouseholdProvince) {
        this.coboHouseholdProvince = coboHouseholdProvince;
    }

    public String getCoboHouseholdCity() {
        return coboHouseholdCity;
    }

    public void setCoboHouseholdCity(String coboHouseholdCity) {
        this.coboHouseholdCity = coboHouseholdCity;
    }

    public String getCoboHouseholdArea() {
        return coboHouseholdArea;
    }

    public void setCoboHouseholdArea(String coboHouseholdArea) {
        this.coboHouseholdArea = coboHouseholdArea;
    }

    public String getCoboHouseholdAddress() {
        return coboHouseholdAddress;
    }

    public void setCoboHouseholdAddress(String coboHouseholdAddress) {
        this.coboHouseholdAddress = coboHouseholdAddress;
    }

    public String getCoboFamilyTel() {
        return coboFamilyTel;
    }

    public void setCoboFamilyTel(String coboFamilyTel) {
        this.coboFamilyTel = coboFamilyTel;
    }

    public String getCoboMobile() {
        return coboMobile;
    }

    public void setCoboMobile(String coboMobile) {
        this.coboMobile = coboMobile;
    }

    public String getCoboMobile2() {
        return coboMobile2;
    }

    public void setCoboMobile2(String coboMobile2) {
        this.coboMobile2 = coboMobile2;
    }

    public String getCoboLiveingProvince() {
        return coboLiveingProvince;
    }

    public void setCoboLiveingProvince(String coboLiveingProvince) {
        this.coboLiveingProvince = coboLiveingProvince;
    }

    public String getCoboLiveingCity() {
        return coboLiveingCity;
    }

    public void setCoboLiveingCity(String coboLiveingCity) {
        this.coboLiveingCity = coboLiveingCity;
    }

    public String getCoboLiveingArea() {
        return coboLiveingArea;
    }

    public void setCoboLiveingArea(String coboLiveingArea) {
        this.coboLiveingArea = coboLiveingArea;
    }

    public String getCoboNowAddress() {
        return coboNowAddress;
    }

    public void setCoboNowAddress(String coboNowAddress) {
        this.coboNowAddress = coboNowAddress;
    }

    public String getCoboNowTel() {
        return coboNowTel;
    }

    public void setCoboNowTel(String coboNowTel) {
        this.coboNowTel = coboNowTel;
    }

    public String getCoboEmail() {
        return coboEmail;
    }

    public void setCoboEmail(String coboEmail) {
        this.coboEmail = coboEmail;
    }
    
    public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}

    public String getDictMarry() {
        return dictMarry;
    }

    public void setDictMarry(String dictMarry) {
        this.dictMarry = dictMarry;
    }

    public String getCoboHaveChild() {
        return coboHaveChild;
    }

    public void setCoboHaveChild(String coboHaveChild) {
        this.coboHaveChild = coboHaveChild;
    }

    public String getCoboContractIsKnow() {
        return coboContractIsKnow;
    }

    public void setCoboContractIsKnow(String coboContractIsKnow) {
        this.coboContractIsKnow = coboContractIsKnow;
    }

    public String getCoboHouseOther() {
        return coboHouseOther;
    }

    public void setCoboHouseOther(String coboHouseOther) {
        this.coboHouseOther = coboHouseOther;
    }

    public String getCoboSocialSecurity() {
        return coboSocialSecurity;
    }

    public void setCoboSocialSecurity(String coboSocialSecurity) {
        this.coboSocialSecurity = coboSocialSecurity;
    }

    public String getCustomerFirtArriveYear() {
        return customerFirtArriveYear;
    }

    public void setCustomerFirtArriveYear(String customerFirtArriveYear) {
        this.customerFirtArriveYear = customerFirtArriveYear;
    }

    public Date getCustomerFirstLivingDay() {
        return customerFirstLivingDay;
    }

    public void setCustomerFirstLivingDay(Date customerFirstLivingDay) {
        this.customerFirstLivingDay = customerFirstLivingDay;
    }

    public String getDictCustomerDiff() {
        return dictCustomerDiff;
    }

    public void setDictCustomerDiff(String dictCustomerDiff) {
        this.dictCustomerDiff = dictCustomerDiff;
    }
    
	public String getResidentialCategoryRemark() {
		return residentialCategoryRemark;
	}

	public void setResidentialCategoryRemark(String residentialCategoryRemark) {
		this.residentialCategoryRemark = residentialCategoryRemark;
	}

	/**
     * @return the captchaIfConfirm
     */
    public String getCaptchaIfConfirm() {
        return captchaIfConfirm;
    }

    /**
     * @param captchaIfConfirm the String captchaIfConfirm to set
     */
    public void setCaptchaIfConfirm(String captchaIfConfirm) {
        this.captchaIfConfirm = captchaIfConfirm;
    }

    /**
     * @return the confirmTimeout
     */
    public Date getConfirmTimeout() {
        return confirmTimeout;
    }

    /**
     * @param confirmTimeout the Date confirmTimeout to set
     */
    public void setConfirmTimeout(Date confirmTimeout) {
        this.confirmTimeout = confirmTimeout;
    }

	public String getCustomerPin() {
		return customerPin;
	}

	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}

    /**
     * @return the appSignFlag
     */
    public String getAppSignFlag() {
        return appSignFlag;
    }

    /**
     * @param appSignFlag the String appSignFlag to set
     */
    public void setAppSignFlag(String appSignFlag) {
        this.appSignFlag = appSignFlag;
    }

    /**
     * @return the idValidFlag
     */
    public String getIdValidFlag() {
        return idValidFlag;
    }

    /**
     * @param idValidFlag the String idValidFlag to set
     */
    public void setIdValidFlag(String idValidFlag) {
        this.idValidFlag = idValidFlag;
    }

    /**
     * @return the composePhotoId
     */
    public String getComposePhotoId() {
        return composePhotoId;
    }

    /**
     * @param composePhotoId the String composePhotoId to set
     */
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

    /**
     * @return the idValidScore
     */
    public Float getIdValidScore() {
        return idValidScore;
    }

    /**
     * @param idValidScore the float idValidScore to set
     */
    public void setIdValidScore(Float idValidScore) {
        this.idValidScore = idValidScore;
    }

    /**
     * @return the idValidMessage
     */
    public String getIdValidMessage() {
        return idValidMessage;
    }

    /**
     * @param idValidMessage the String idValidMessage to set
     */
    public void setIdValidMessage(String idValidMessage) {
        this.idValidMessage = idValidMessage;
    }

}