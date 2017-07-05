/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entityGuaranteeRegister.java
 * @Create By 张灏
 * @Create In 2016年5月21日 下午2:48:10
 */
package com.creditharmony.loan.borrow.contract.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name GuaranteeRegister
 * @author 张灏
 * @Create In 2016年5月21日
 */
public class GuaranteeRegister extends DataEntity<GuaranteeRegister> {

    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    // 保证人姓名
    private String guaranteeName;
    // 保证人身份证号码
    private String guaranteeIdNum;
    // 保证人邮件
    private String guaranteeMail;
    // 保证人移动电话
    private String guaranteeMobile;
    // 保证人电话
    private String guaranteeTel;
    // 公司名称
    private String companyName;
    // 省份
    private String companyProvince;
    // 文件id
    private String companyPaperId;
    // 企业工商注册号
    private String companyRegisteredNo;
    // 应用名,注册成功后使用
    private String certContainer;
    // 缓存号,注册成功后使用
    private String transId;
    // 注册时间
    private Date registerDate;
    // 合同编号
    private String contractCode;
    // 借款编号
    private String loanCode;
    // 关联ID 
    private String relateId;
    /**
     * @return the guaranteeName
     */
    public String getGuaranteeName() {
        return guaranteeName;
    }
    /**
     * @param guaranteeName the String guaranteeName to set
     */
    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }
    /**
     * @return the guaranteeIdNum
     */
    public String getGuaranteeIdNum() {
        return guaranteeIdNum;
    }
    /**
     * @param guaranteeIdNum the String guaranteeIdNum to set
     */
    public void setGuaranteeIdNum(String guaranteeIdNum) {
        this.guaranteeIdNum = guaranteeIdNum;
    }
    /**
     * @return the guaranteeMail
     */
    public String getGuaranteeMail() {
        return guaranteeMail;
    }
    /**
     * @param guaranteeMail the String guaranteeMail to set
     */
    public void setGuaranteeMail(String guaranteeMail) {
        this.guaranteeMail = guaranteeMail;
    }
    /**
     * @return the guaranteeMobile
     */
    public String getGuaranteeMobile() {
        return guaranteeMobile;
    }
    /**
     * @param guaranteeMobile the String guaranteeMobile to set
     */
    public void setGuaranteeMobile(String guaranteeMobile) {
        this.guaranteeMobile = guaranteeMobile;
    }
    /**
     * @return the guaranteeTel
     */
    public String getGuaranteeTel() {
        return guaranteeTel;
    }
    /**
     * @param guaranteeTel the String guaranteeTel to set
     */
    public void setGuaranteeTel(String guaranteeTel) {
        this.guaranteeTel = guaranteeTel;
    }
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyName the String companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * @return the companyProvince
     */
    public String getCompanyProvince() {
        return companyProvince;
    }
    /**
     * @param companyProvince the String companyProvince to set
     */
    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }
    /**
     * @return the companyPaperId
     */
    public String getCompanyPaperId() {
        return companyPaperId;
    }
    /**
     * @param companyPaperId the String companyPaperId to set
     */
    public void setCompanyPaperId(String companyPaperId) {
        this.companyPaperId = companyPaperId;
    }
    /**
     * @return the companyRegisteredNo
     */
    public String getCompanyRegisteredNo() {
        return companyRegisteredNo;
    }
    /**
     * @param companyRegisteredNo the String companyRegisteredNo to set
     */
    public void setCompanyRegisteredNo(String companyRegisteredNo) {
        this.companyRegisteredNo = companyRegisteredNo;
    }
    /**
     * @return the certContainer
     */
    public String getCertContainer() {
        return certContainer;
    }
    /**
     * @param certContainer the String certContainer to set
     */
    public void setCertContainer(String certContainer) {
        this.certContainer = certContainer;
    }
    /**
     * @return the transId
     */
    public String getTransId() {
        return transId;
    }
    /**
     * @param transId the String transId to set
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }
    /**
     * @return the registerDate
     */
    public Date getRegisterDate() {
        return registerDate;
    }
    /**
     * @param registerDate the Date registerDate to set
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    /**
     * @return the contractCode
     */
    public String getContractCode() {
        return contractCode;
    }
    /**
     * @param contractCode the String contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    /**
     * @return the loanCode
     */
    public String getLoanCode() {
        return loanCode;
    }
    /**
     * @param loanCode the String loanCode to set
     */
    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }
    /**
     * @return the relateId
     */
    public String getRelateId() {
        return relateId;
    }
    /**
     * @param relateId the String relateId to set
     */
    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }
}
