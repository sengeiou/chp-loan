package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 共借人基本信息
 * 
 * @Class Name CarLoanCoborrower
 * @author 安子帅
 * @Create In 2016年1月21日
 */
public class CarLoanCoborrower extends DataEntity<CarLoanCoborrower> {

	private static final long serialVersionUID = 5860306617525656851L;

	private String loanCode; // 借款编码
	private String id; // ID
	private String coboName; // 姓名
	private String dictSex; // 性别
	private String certNum; // 证件号码
	private String dictHouseholdProvince; // 户籍省
	private String dictHouseholdView; //户籍省
	private String dictHouseholdCity; // 户籍市
	private String dictHouseholdArea; // 户籍区
	private String householdAddress; // 户籍地址
	//private String CoboHouseHoldProperty; //住房性质
	private String coboHouseHoldProperty; // 住房性质
	private String mobile; // 手机号
	private String mobileEnc; // 手机号
	private String familyTel; // 固定电话
	private String dictMarryStatus; // 婚姻状况
	private String haveChildFlag; // 是否有子女
	private String dictLiveProvince; // 现住址省
	private String dictLiveView; 
	private String dictLiveCity; // 现住址市
	private String dictLiveArea; // 现住址区
	private String nowAddress; // 现住址详细地址
	private String email; // 电子邮箱
	private String contactIsKnow; // 家人是否知息此借款
	private String dictRelationType; // 知情人与本人关系
	private String dictRelationCustomer;// 与主借人关系
	private String houseOther; // 其他说明
	private String dictSocialSecurity; // 是否有社保
	private String createBy; // 创建人
	private Date createTime; // 创建时间
	private String modifyBy; // 修改人
	private Date modifyTime; // 最后修改时间
	private String dictCertType; // 证件类型
	private BigDecimal houseRent; // 房屋出租
	private String haveOtherIncome;//其他收入
	private BigDecimal otherIncome; // 其他所得
	private List<CarCustomerContactPerson> carCustomerContactPerson;
	private String newLoanCode;

	private String captchaIfConfirm;// 验证码是否确认
	private Date confirmTimeout;// 确认超时结束时间
	private String customerPin; // 客户验证码
	private String idValidScore;//身份验证分数
	
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
    
    //2016-05-22 Wangj 共借人增加工作单位、地址、职务  
    private String companyName;     // 单位名称
    private String companyAddress;  // 单位地址
    private String companyPosition; // 单位职务 
    private String isemailmodify ; //是否修改邮箱
    private String istelephonemodify;//是否修改手机
    
    private String oldMobile;// 上一次的电话
    private String oldEmail;//上一次邮箱
    
	public String getHaveOtherIncome() {
		return haveOtherIncome;
	}

	public void setHaveOtherIncome(String haveOtherIncome) {
		this.haveOtherIncome = haveOtherIncome;
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

    
    
    public String getNewLoanCode() {
		return newLoanCode;
	}

	public void setNewLoanCode(String newLoanCode) {
		this.newLoanCode = newLoanCode;
	}

	public List<CarCustomerContactPerson> getCarCustomerContactPerson() {
		return carCustomerContactPerson;
	}



	public void setCarCustomerContactPerson(
			List<CarCustomerContactPerson> carCustomerContactPerson) {
		this.carCustomerContactPerson = carCustomerContactPerson;
	}

	public BigDecimal getHouseRent() {
		return houseRent;
	}

	public void setHouseRent(BigDecimal houseRent) {
		this.houseRent = houseRent;
	}

	public BigDecimal getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	public String getDictCertType() {
		return dictCertType;
	}

	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode == null ? null : loanCode.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName == null ? null : coboName.trim();
	}

	public String getDictSex() {
		return dictSex;
	}

	public void setDictSex(String dictSex) {
		this.dictSex = dictSex == null ? null : dictSex.trim();
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum == null ? null : certNum.trim();
	}

	public String getDictHouseholdProvince() {
		return dictHouseholdProvince;
	}

	public void setDictHouseholdProvince(String dictHouseholdProvince) {
		this.dictHouseholdProvince = dictHouseholdProvince == null ? null
				: dictHouseholdProvince.trim();
	}

	public String getDictHouseholdCity() {
		return dictHouseholdCity;
	}

	public void setDictHouseholdCity(String dictHouseholdCity) {
		this.dictHouseholdCity = dictHouseholdCity == null ? null
				: dictHouseholdCity.trim();
	}

	public String getDictHouseholdArea() {
		return dictHouseholdArea;
	}

	public void setDictHouseholdArea(String dictHouseholdArea) {
		this.dictHouseholdArea = dictHouseholdArea == null ? null
				: dictHouseholdArea.trim();
	}

	public String getHouseholdAddress() {
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress == null ? null
				: householdAddress.trim();
	}

	public String getMobile() {
        if(mobile != null){
            mobile = decryptPhones(mobile.trim(),"t_cj_loan_coborrower","mobile");
        }
		return mobile;
	}

	public String getMobileEnc(){
		return mobileEnc;
	}

	public void setMobile(String mobile) {
		if(mobile != null && mobile.length() == 11){
			mobile = encryptPhones(mobile.trim(),"t_cj_loan_coborrower","mobile");
		}
		this.mobile = mobile == null ? null : mobile.trim();
		this.mobileEnc = mobile == null ? null : mobile.trim();
	}

	public String getFamilyTel() {
		return familyTel;
	}

	public void setFamilyTel(String familyTel) {
		this.familyTel = familyTel == null ? null : familyTel.trim();
	}

	public String getDictMarryStatus() {
		return dictMarryStatus;
	}

	public void setDictMarryStatus(String dictMarryStatus) {
		this.dictMarryStatus = dictMarryStatus == null ? null : dictMarryStatus
				.trim();
	}

	public String getHaveChildFlag() {
		return haveChildFlag;
	}

	public void setHaveChildFlag(String haveChildFlag) {
		this.haveChildFlag = haveChildFlag == null ? null : haveChildFlag
				.trim();
	}

	public String getDictLiveProvince() {
		return dictLiveProvince;
	}

	public void setDictLiveProvince(String dictLiveProvince) {
		this.dictLiveProvince = dictLiveProvince == null ? null
				: dictLiveProvince.trim();
	}

	public String getDictLiveCity() {
		return dictLiveCity;
	}

	public void setDictLiveCity(String dictLiveCity) {
		this.dictLiveCity = dictLiveCity == null ? null : dictLiveCity.trim();
	}

	public String getDictLiveArea() {
		return dictLiveArea;
	}

	public void setDictLiveArea(String dictLiveArea) {
		this.dictLiveArea = dictLiveArea == null ? null : dictLiveArea.trim();
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress == null ? null : nowAddress.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getContactIsKnow() {
		return contactIsKnow;
	}

	public void setContactIsKnow(String contactIsKnow) {
		this.contactIsKnow = contactIsKnow == null ? null : contactIsKnow
				.trim();
	}

	public String getDictRelationType() {
		return dictRelationType;
	}

	public void setDictRelationType(String dictRelationType) {
		this.dictRelationType = dictRelationType == null ? null
				: dictRelationType.trim();
	}

	public String getDictRelationCustomer() {
		return dictRelationCustomer;
	}

	public void setDictRelationCustomer(String dictRelationCustomer) {
		this.dictRelationCustomer = dictRelationCustomer == null ? null
				: dictRelationCustomer.trim();
	}

	public String getHouseOther() {
		return houseOther;
	}

	public void setHouseOther(String houseOther) {
		this.houseOther = houseOther == null ? null : houseOther.trim();
	}

	public String getDictSocialSecurity() {
		return dictSocialSecurity;
	}

	public void setDictSocialSecurity(String dictSocialSecurity) {
		this.dictSocialSecurity = dictSocialSecurity == null ? null
				: dictSocialSecurity.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	public String getIstelephonemodify() {
		return istelephonemodify;
	}

	public void setIstelephonemodify(String istelephonemodify) {
		this.istelephonemodify = istelephonemodify;
	}

	public String getIsemailmodify() {
		return isemailmodify;
	}

	public void setIsemailmodify(String isemailmodify) {
		this.isemailmodify = isemailmodify;
	}
	public String getDictHouseholdView() {
		return dictHouseholdView;
	}

	public void setDictHouseholdView(String dictHouseholdView) {
		this.dictHouseholdView = dictHouseholdView;
	}

	public String getDictLiveView() {
		return dictLiveView;
	}

	public void setDictLiveView(String dictLiveView) {
		this.dictLiveView = dictLiveView;
	}

	public String getCoboHouseHoldProperty() {
		return coboHouseHoldProperty;
	}

	public void setCoboHouseHoldProperty(String coboHouseHoldProperty) {
		this.coboHouseHoldProperty = coboHouseHoldProperty;
	}

	public String getIdValidScore() {
		return idValidScore;
	}

	public void setIdValidScore(String idValidScore) {
		this.idValidScore = idValidScore;
	}

	public String getOldMobile() {
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	
}