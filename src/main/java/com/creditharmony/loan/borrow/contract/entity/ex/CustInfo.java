/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.entityExCustInfo.java
 * @Create By 张灏
 * @Create In 2015年11月28日 下午5:45:37
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name CustInfo
 * @author 张灏
 * @Create In 2015年11月28日
 */
@SuppressWarnings("serial")
public class CustInfo extends DataEntity<CustInfo> {
    // 主借款人
    private String mainLoaner;
    // 证件类型（主借人）
    private String mainCertType;
    // 证件类型名称（主借人）
    private String mainCertTypeName;
    // 证件号码（主借人）
    private String mainCertNum;
    // 主借人性别
    private String mainCertSex;
    // 共借人姓名
    private String compCustName;
    // 共借人证件类型
    private String compCertType;
    // 共借人证件类型名称
    private String compCertTypeName;
    // 共借人证件号码
    private String compCertNum;
    // 保证人姓名
    private String auditEnsureName;
    // 法定代表人
    private String auditLegalMan;
    // 保证人实际经营场所
    private String ensuremanBusinessPlace;
    // 资金托管标识，1托管，空非托管
    private String loanFlag;
    // 门店Code
    private String applyOrgCode;
    // 移动电话
    private String customerPhoneFirst;
    // 移动电话
    private String customerPhoneSecond;
    // 固定电话
    private String customerTel;
    // 借款编号
    private String loanCode;
    // 批复产品
    private String productType;
    // 批复期限
    private BigDecimal contractMonths;
    // 批复金额
    private BigDecimal auditAmount;
    // 是否加急
    private String loanUrgentFlag;
    // 金账户标识
    private String trusteeshipFlag;
    // 门店ID
    private String loanStoreOrgId;
    // 冻结原因
    private String frozenCode;
    // 原因描述
    private String frozenReason;
    // 汇诚审批时间
    private Date auditTime;
    // 客户编号
    private String customerCode;
    // 客户编号
    private String customerId;
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
    // 合成图片ID
    private String composePhotoId;
    // 产品总费率
    private BigDecimal grossRate;
    // 模式
    private String model;
    // 保证人证明ID 
    private String businessProveId;
    // 身份证验证分数
    private Float idValidScore;
    //经营地址带 省市区
    private String maddressName;
    //邮件地址
    private String customerEmail;
    //批复额度
    private double auditRate;
    
    public String getMainLoaner() {
        return mainLoaner;
    }

    public void setMainLoaner(String mainLoaner) {
        this.mainLoaner = mainLoaner;
    }

    public String getMainCertType() {
        return mainCertType;
    }

    public void setMainCertType(String mainCertType) {
        this.mainCertType = mainCertType;
    }

    public String getMainCertTypeName() {
        return mainCertTypeName;
    }

    public void setMainCertTypeName(String mainCertTypeName) {
        this.mainCertTypeName = mainCertTypeName;
    }

    public String getMainCertNum() {
        return mainCertNum;
    }

    public void setMainCertNum(String mainCertNum) {
        this.mainCertNum = mainCertNum;
    }

    public String getCompCustName() {
        return compCustName;
    }

    public void setCompCustName(String compCustName) {
        this.compCustName = compCustName;
    }

    public String getCompCertType() {
        return compCertType;
    }

    public void setCompCertType(String compCertType) {
        this.compCertType = compCertType;
    }

    public String getCompCertTypeName() {
        return compCertTypeName;
    }

    public void setCompCertTypeName(String compCertTypeName) {
        this.compCertTypeName = compCertTypeName;
    }

    public String getCompCertNum() {
        return compCertNum;
    }

    public void setCompCertNum(String compCertNum) {
        this.compCertNum = compCertNum;
    }

    public String getMainCertSex() {
        return mainCertSex;
    }

    public void setMainCertSex(String mainCertSex) {
        this.mainCertSex = mainCertSex;
    }

    public String getAuditEnsureName() {
        return auditEnsureName;
    }

    public void setAuditEnsureName(String auditEnsureName) {
        this.auditEnsureName = auditEnsureName;
    }

    public String getAuditLegalMan() {
        return auditLegalMan;
    }

    public void setAuditLegalMan(String auditLegalMan) {
        this.auditLegalMan = auditLegalMan;
    }

    public String getEnsuremanBusinessPlace() {
        return ensuremanBusinessPlace;
    }

    public void setEnsuremanBusinessPlace(String ensuremanBusinessPlace) {
        this.ensuremanBusinessPlace = ensuremanBusinessPlace;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }

    public String getApplyOrgCode() {
        return applyOrgCode;
    }

    public void setApplyOrgCode(String applyOrgCode) {
        this.applyOrgCode = applyOrgCode;
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

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getContractMonths() {
        return contractMonths;
    }

    public void setContractMonths(BigDecimal contractMonths) {
        this.contractMonths = contractMonths;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public String getLoanUrgentFlag() {
        return loanUrgentFlag;
    }

    public String getLoanStoreOrgId() {
        return loanStoreOrgId;
    }

    public void setLoanStoreOrgId(String loanStoreOrgId) {
        this.loanStoreOrgId = loanStoreOrgId;
    }

    public String getFrozenCode() {
        return frozenCode;
    }

    public void setFrozenCode(String frozenCode) {
        this.frozenCode = frozenCode;
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
    }

    public void setLoanUrgentFlag(String loanUrgentFlag) {
        this.loanUrgentFlag = loanUrgentFlag;
    }

    /**
     * @return the trusteeshipFlag
     */
    public String getTrusteeshipFlag() {
        return trusteeshipFlag;
    }

    /**
     * @param trusteeshipFlag
     *            the String trusteeshipFlag to set
     */
    public void setTrusteeshipFlag(String trusteeshipFlag) {
        this.trusteeshipFlag = trusteeshipFlag;
    }

    public void setLoanIsUrgent(String loanUrgentFlag) {
        this.loanUrgentFlag = loanUrgentFlag;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCode
     *            the String customerCode to set
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * @return the customerPin
     */
    public String getCustomerPin() {
        return customerPin;
    }

    /**
     * @param customerPin
     *            the String customerPin to set
     */
    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
    }

    /**
     * @return the captchaIfConfirm
     */
    public String getCaptchaIfConfirm() {
        return captchaIfConfirm;
    }

    /**
     * @param captchaIfConfirm
     *            the String captchaIfConfirm to set
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
     * @param confirmTimeout
     *            the Date confirmTimeout to set
     */
    public void setConfirmTimeout(Date confirmTimeout) {
        this.confirmTimeout = confirmTimeout;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the String customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the appSignFlag
     */
    public String getAppSignFlag() {
        return appSignFlag;
    }

    /**
     * @param appSignFlag
     *            the String appSignFlag to set
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
     * @param idValidFlag
     *            the String idValidFlag to set
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
     * @param composePhotoId
     *            the String composePhotoId to set
     */
    public void setComposePhotoId(String composePhotoId) {
        this.composePhotoId = composePhotoId;
    }

    /**
     * @return the grossRate
     */
    public BigDecimal getGrossRate() {
        return grossRate;
    }

    /**
     * @param grossRate
     *            the BigDecimal grossRate to set
     */
    public void setGrossRate(BigDecimal grossRate) {
        this.grossRate = grossRate;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model
     *            the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the businessProveId
     */
    public String getBusinessProveId() {
        return businessProveId;
    }

    /**
     * @param businessProveId the String businessProveId to set
     */
    public void setBusinessProveId(String businessProveId) {
        this.businessProveId = businessProveId;
    }

    /**
     * @return the idValidScore
     */
    public Float getIdValidScore() {
        return idValidScore;
    }

    /**
     * @param idValidScore the Float idValidScore to set
     */
    public void setIdValidScore(Float idValidScore) {
        this.idValidScore = idValidScore;
    }

	public String getMaddressName() {
		return maddressName;
	}

	public void setMaddressName(String maddressName) {
		this.maddressName = maddressName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public double getAuditRate() {
		return auditRate;
	}

	public void setAuditRate(double auditRate) {
		this.auditRate = auditRate;
	}
	
	
    
}
