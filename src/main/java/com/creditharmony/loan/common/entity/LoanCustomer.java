package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户详细信息
 * @Class Name LoanCustomer
 * @author zhangping
 * @Create In 2015年11月29日
 */
public class LoanCustomer extends DataEntity<LoanCustomer> {

	private static final long serialVersionUID = -7707667923173843334L;
    // 客户编号
	private String customerCode;
	// 借款编码
    private String loanCode;	  
    // 流水号
    private String applyId;    
    // 客户姓名
    private String customerName; 
    // 证件类型
    private String dictCertType; 
    // 证件类型名
    private String dictCertTypeLabel;
    // 证件号码
    private String customerCertNum;
    // 发证机关
    private String customerCertOrg; 
    // 身份证有效期开始时间
    private Date idStartDay;   
    // 借款编码身份证有效期结束时间
    private Date idEndDay;    
    // 性别
    private String customerSex; 
    // 性别名
    private String customerSexLabel;
    // 户籍省
    private String customerRegisterProvince; 
    // 户籍市
    private String customerRegisterCity;     
    // 户籍区
    private String customerRegisterArea;	
    // 户籍地址
    private String customerRegisterAddress;  
    // 出生日期
    private Date customerBirthday;			
    // 英文名称
    private String customerEname; 			
    // 婚姻状况
    private String dictMarry;
    // 婚姻状况 旧数据
    private String oldDictMarry;
    // 婚姻状况名
    private String dictMarryLabel;
    // 学历
    private String dictEducation;	
    // 学历名
    private String dictEducationLabel;
    // 毕业日期
    private Date customerGraduationDay;	
    // 移动电话
    private String customerPhoneFirst;		
    // 移动电话2
    private String customerPhoneSecond;	
    // 固定电话
    private String customerTel;			
    // 电子邮箱
    private String customerEmail;	
    // 签发机关
    private String issuingAuthority;
    // 客户传真
    private String customerFax;				
    // 有无子女
    private String customerHaveChildren;	
    // 居住省
    private String customerLiveProvince;	
    // 居住市
    private String customerLiveCity;		
    // 居住区
    private String customerLiveArea;		
    // 详细地址
    private String customerAddress;			
    // 1 有效 2 作废
    private BigDecimal customerStatus;		
    // 其他说明
    private String customerOther;			
    // 是否风险客户
    private String dictCustomerDanger;		
    // 客户类型
    private String dictCustomerDiff;
    // 客户状态（字典码502）
    private String dictCustomerStatus;		
    // 家人是否知息此借款
    private String contactIsKnow;			
    // 是否开通金账户（1：是 0：否）
    private String customerIsGold;
    // 金账户帐号
    private String trusteeshipNo;
    // 由何处得知公司（关联字典）
    private String dictCustomerSource;	
    // 由何处得知我公司名
    private String dictCustomerSourceLabel;
    // 电销来源（电销用）
    private String customerTeleSalesSource;			
    // 是否电销
    private String customerTelesalesFlag; 
   // 是否电销名称
    private String customerTelesalesFlagLabel; 
    // 知情人与本人关系
    private String dictRelationType;         
    // 电销组织机构编号
    private String customerTeleSalesOrgCode;          
    // 短信验证码
    private String customerPin;
    // 验证码确认标识
    private String captchaIfConfirm;
    // 确认超时结束时间
    private Date confirmTimeout;
    // app签字标识
    private String appSignFlag;
    // 身份证验证标识
    private String idValidFlag;
    // 合同照片ID
    private String composePhotoId;
    // 身份证验证分数
    private Float idValidScore;
    //子女个数
    private String customerChildrenCount;
    //供养人数
    private int customerFamilySupport;
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
    //社保密码
    private String socialSecurityPassword;
    //户籍性质
    private String registerProperty;
    //qq号
    private String customerQq;
    //微博号
    private String customerWeibo;
    //征信用户名
    private String creditUsername;
    //征信密码
    private String creditPassword;
    //征信授权码
    private String creditAuthCode;
    //由何处了解到我公司：查询出来的数据，以逗号分隔的字符串
    private String dictCustomerSourceNew;
    //由何处了解到我公司:多选，接收页面数据
    private String[] dictCustomerSourceNewStr;
    //由何处了解到我公司选择其他时的备注信息
    private String dictCustomerSourceNewOther;
    //以上可知晓本次借款的联系人:多选，接收页面数据
    private String whoCanKnowBorrow; 
    //以上可知晓本次借款的联系人选择其他时的备注信息
    private String whoCanKnowTheBorrowingRemark;
    //邮箱是否验证
    private String emailIfConfirm;
    //临时验证
    private String tempEmailIfConfirm;
    
    public String getDictCustomerSourceNew() {
		return dictCustomerSourceNew;
	}

	public void setDictCustomerSourceNew(String dictCustomerSourceNew) {
		this.dictCustomerSourceNew = dictCustomerSourceNew;
	}

	public String[] getDictCustomerSourceNewStr() {
		return dictCustomerSourceNewStr;
	}

	public void setDictCustomerSourceNewStr(String[] dictCustomerSourceNewStr) {
		this.dictCustomerSourceNewStr = dictCustomerSourceNewStr;
	}

	public String getDictCustomerSourceNewOther() {
		return dictCustomerSourceNewOther;
	}

	public void setDictCustomerSourceNewOther(String dictCustomerSourceNewOther) {
		this.dictCustomerSourceNewOther = dictCustomerSourceNewOther;
	}

	public String getCustomerQq() {
		return customerQq;
	}

	public void setCustomerQq(String customerQq) {
		this.customerQq = customerQq;
	}

	public String getCustomerWeibo() {
		return customerWeibo;
	}

	public void setCustomerWeibo(String customerWeibo) {
		this.customerWeibo = customerWeibo;
	}

	public String getCreditUsername() {
		return creditUsername;
	}

	public void setCreditUsername(String creditUsername) {
		this.creditUsername = creditUsername;
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

	public String getRegisterProperty() {
		return registerProperty;
	}

	public void setRegisterProperty(String registerProperty) {
		this.registerProperty = registerProperty;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getSocialSecurityPassword() {
		return socialSecurityPassword;
	}

	public void setSocialSecurityPassword(String socialSecurityPassword) {
		this.socialSecurityPassword = socialSecurityPassword;
	}

	public String getCustomerChildrenCount() {
		return customerChildrenCount;
	}

	public void setCustomerChildrenCount(String customerChildrenCount) {
		this.customerChildrenCount = customerChildrenCount;
	}

	public int getCustomerFamilySupport() {
		return customerFamilySupport;
	}

	public void setCustomerFamilySupport(int customerFamilySupport) {
		this.customerFamilySupport = customerFamilySupport;
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

	public String getDictCustomerSourceLabel() {
		return dictCustomerSourceLabel;
	}

	public void setDictCustomerSourceLabel(String dictCustomerSourceLabel) {
		this.dictCustomerSourceLabel = dictCustomerSourceLabel;
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

	public String getDictCertTypeLabel() {
		return dictCertTypeLabel;
	}

	public void setDictCertTypeLabel(String dictCertTypeLabel) {
		this.dictCertTypeLabel = dictCertTypeLabel;
	}

	public String getCustomerSexLabel() {
		return customerSexLabel;
	}

	public void setCustomerSexLabel(String customerSexLabel) {
		this.customerSexLabel = customerSexLabel;
	}

	public String getCustomerTeleSalesSource() {
        return customerTeleSalesSource;
    }

    public void setCustomerTeleSalesSource(String customerTeleSalesSource) {
        this.customerTeleSalesSource = customerTeleSalesSource;
    }

    public String getCustomerTeleSalesOrgCode() {
        return customerTeleSalesOrgCode;
    }

    public void setCustomerTeleSalesOrgCode(String customerTeleSalesOrgCode) {
        this.customerTeleSalesOrgCode = customerTeleSalesOrgCode;
    }

    /**
     * @return the customerPin
     */
    public String getCustomerPin() {
        return customerPin;
    }

    /**
     * @param customerPin the String customerPin to set
     */
    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getCustomerCertOrg() {
        return customerCertOrg;
    }

    public void setCustomerCertOrg(String customerCertOrg) {
        this.customerCertOrg = customerCertOrg;
    }

    public Date getIdStartDay() {
        return idStartDay;
    }

    public void setIdStarDay(Date idStartDay) {
        this.idStartDay = idStartDay;
    }

    public Date getIdEndDay() {
        return idEndDay;
    }

    public void setIdEndDay(Date idEndDay) {
        this.idEndDay = idEndDay;
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

    public String getCustomerEname() {
        return customerEname;
    }

    public void setCustomerEname(String customerEname) {
        this.customerEname = customerEname;
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
    
    public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
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

    public String getCustomerLiveProvince() {
        return customerLiveProvince;
    }

    public void setCustomerLiveProvince(String customerLiveProvince) {
        this.customerLiveProvince = customerLiveProvince;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public BigDecimal getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(BigDecimal customerStatus) {
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
        this.dictCustomerDanger = dictCustomerDanger == null ? null : dictCustomerDanger.trim();
    }

    public String getDictCustomerDiff() {
        return dictCustomerDiff;
    }

    public void setDictCustomerDiff(String dictCustomerDiff) {
        this.dictCustomerDiff = dictCustomerDiff;
    }

	public String getDictCustomerStatus() {
        return dictCustomerStatus;
    }

    public void setDictCustomerStatus(String dictCustomerStatus) {
        this.dictCustomerStatus = dictCustomerStatus == null ? null : dictCustomerStatus.trim();
    }

    public String getContactIsKnow() {
        return contactIsKnow;
    }

	public void setContactIsKnow(String contactIsKnow) {
		this.contactIsKnow = contactIsKnow == null ? null : contactIsKnow.trim();
	}

	public String getCustomerIsGold() {
		return customerIsGold;
	}

	public void setCustomerIsGold(String customerIsGold) {
		this.customerIsGold = customerIsGold == null ? null : customerIsGold.trim();
	}

	public String getTrusteeshipNo() {
		return trusteeshipNo;
	}

	public void setTrusteeshipNo(String trusteeshipNo) {
		this.trusteeshipNo = trusteeshipNo == null ? null : trusteeshipNo.trim();
	}

	public String getDictCustomerSource() {
		return dictCustomerSource;
	}

    public void setDictCustomerSource(String dictCustomerSource) {
        this.dictCustomerSource = dictCustomerSource == null ? null : dictCustomerSource.trim();
    }

    public String getDictRelationType() {
        return dictRelationType;
    }

    public void setDictRelationType(String dictRelationType) {
        this.dictRelationType = dictRelationType;
    }

    public void setIdStartDay(Date idStartDay) {
        this.idStartDay = idStartDay;
    }

	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}

	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}

	public String getCustomerTelesalesFlagLabel() {
		return customerTelesalesFlagLabel;
	}

	public void setCustomerTelesalesFlagLabel(String customerTelesalesFlagLabel) {
		this.customerTelesalesFlagLabel = customerTelesalesFlagLabel;
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
     * @return the oldDictMarry
     */
    public String getOldDictMarry() {
        return oldDictMarry;
    }

    /**
     * @param oldDictMarry the String oldDictMarry to set
     */
    public void setOldDictMarry(String oldDictMarry) {
        this.oldDictMarry = oldDictMarry;
    }

	public String getWhoCanKnowBorrow() {
		return whoCanKnowBorrow;
	}

	public void setWhoCanKnowBorrow(String whoCanKnowBorrow) {
		this.whoCanKnowBorrow = whoCanKnowBorrow;
	}

	public String getWhoCanKnowTheBorrowingRemark() {
		return whoCanKnowTheBorrowingRemark;
	}

	public void setWhoCanKnowTheBorrowingRemark(String whoCanKnowTheBorrowingRemark) {
		this.whoCanKnowTheBorrowingRemark = whoCanKnowTheBorrowingRemark;
	}

	public String getEmailIfConfirm() {
		return emailIfConfirm;
	}

	public void setEmailIfConfirm(String emailIfConfirm) {
		this.emailIfConfirm = emailIfConfirm;
	}

	public String getTempEmailIfConfirm() {
		return tempEmailIfConfirm;
	}

	public void setTempEmailIfConfirm(String tempEmailIfConfirm) {
		this.tempEmailIfConfirm = tempEmailIfConfirm;
	}
}