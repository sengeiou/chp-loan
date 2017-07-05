/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.account.entitykingAccountChangeExport.java
 * @Create By 张灏
 * @Create In 2016年6月11日 下午3:54:37
 */
package com.creditharmony.loan.borrow.account.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name kingAccountChangeExport
 * @author 张灏
 * @Create In 2016年6月11日
 */
public class KingAccountChangeExport extends DataEntity<KingAccountChangeExport> {
    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    // 金账户省市  
    private String kindProvinceCode;
    // 金账户省市 名称
    private String kindProvinceName;
    // 金账户区县
    private String kindCityCode;
    // 金账户区县 名称
    private String kindCityName;
    // 金账户账号
    private String trusteeshipNo;
    // 新手机号
    private String newCustomerPhone;
    // 原有手机号
    private String oldCustomerPhone;
    // 银行名称
    private String bankName;
    // 具体支行
    private String bankBranch;
    // 开户姓名
    private String bankAccountName;
    // 卡号
    private String bankAccount;
    // 证件类型
    private String dictCertType;
    // 证件类型
    private String certTypeName;
    // 证件号码
    private String customerCertNum;
    // 变更类型 
    private String updateType;
    // 维护类型  0:新增、1:修改
    private String dictMaintainType;
    // 借款编号
    private String loanCode;
    // 文件ID
    private String fileId;
    // 文件名称
    private String fileName;
    // 老账户ID
    private String oldBankAccountId;
    /**
     * @return the kindProvinceCode
     */
    public String getKindProvinceCode() {
        return kindProvinceCode;
    }
    /**
     * @param kindProvinceCode the String kindProvinceCode to set
     */
    public void setKindProvinceCode(String kindProvinceCode) {
        this.kindProvinceCode = kindProvinceCode;
    }
    /**
     * @return the kindProvinceName
     */
    public String getKindProvinceName() {
        return kindProvinceName;
    }
    /**
     * @param kindProvinceName the String kindProvinceName to set
     */
    public void setKindProvinceName(String kindProvinceName) {
        this.kindProvinceName = kindProvinceName;
    }
    /**
     * @return the kindCityCode
     */
    public String getKindCityCode() {
        return kindCityCode;
    }
    /**
     * @param kindCityCode the String kindCityCode to set
     */
    public void setKindCityCode(String kindCityCode) {
        this.kindCityCode = kindCityCode;
    }
    /**
     * @return the kindCityName
     */
    public String getKindCityName() {
        return kindCityName;
    }
    /**
     * @param kindCityName the String kindCityName to set
     */
    public void setKindCityName(String kindCityName) {
        this.kindCityName = kindCityName;
    }
    /**
     * @return the trusteeshipNo
     */
    public String getTrusteeshipNo() {
        return trusteeshipNo;
    }
    /**
     * @param trusteeshipNo the String trusteeshipNo to set
     */
    public void setTrusteeshipNo(String trusteeshipNo) {
        this.trusteeshipNo = trusteeshipNo;
    }
    /**
     * @return the newCustomerPhone
     */
    public String getNewCustomerPhone() {
        return newCustomerPhone;
    }
    /**
     * @param newCustomerPhone the String newCustomerPhone to set
     */
    public void setNewCustomerPhone(String newCustomerPhone) {
        this.newCustomerPhone = newCustomerPhone;
    }
    /**
     * @return the oldCustomerPhone
     */
    public String getOldCustomerPhone() {
        return oldCustomerPhone;
    }
    /**
     * @param oldCustomerPhone the String oldCustomerPhone to set
     */
    public void setOldCustomerPhone(String oldCustomerPhone) {
        this.oldCustomerPhone = oldCustomerPhone;
    }
    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }
    /**
     * @param bankName the String bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    /**
     * @return the bankBranch
     */
    public String getBankBranch() {
        return bankBranch;
    }
    /**
     * @param bankBranch the String bankBranch to set
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    /**
     * @return the bankAccountName
     */
    public String getBankAccountName() {
        return bankAccountName;
    }
    /**
     * @param bankAccountName the String bankAccountName to set
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }
    /**
     * @return the bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }
    /**
     * @param bankAccount the String bankAccount to set
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    /**
     * @return the dictCertType
     */
    public String getDictCertType() {
        return dictCertType;
    }
    /**
     * @param dictCertType the String dictCertType to set
     */
    public void setDictCertType(String dictCertType) {
        this.dictCertType = dictCertType;
    }
    /**
     * @return the certTypeName
     */
    public String getCertTypeName() {
        return certTypeName;
    }
    /**
     * @param certTypeName the String certTypeName to set
     */
    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
    /**
     * @return the customerCertNum
     */
    public String getCustomerCertNum() {
        return customerCertNum;
    }
    /**
     * @param customerCertNum the String customerCertNum to set
     */
    public void setCustomerCertNum(String customerCertNum) {
        this.customerCertNum = customerCertNum;
    }
    /**
     * @return the updateType
     */
    public String getUpdateType() {
        return updateType;
    }
    /**
     * @param updateType the String updateType to set
     */
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }
    /**
     * @param fileId the String fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the String fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the oldBankAccountId
     */
    public String getOldBankAccountId() {
        return oldBankAccountId;
    }
    /**
     * @param oldBankAccountId the String oldBankAccountId to set
     */
    public void setOldBankAccountId(String oldBankAccountId) {
        this.oldBankAccountId = oldBankAccountId;
    }
    /**
     * @return the dictMaintainType
     */
    public String getDictMaintainType() {
        return dictMaintainType;
    }
    /**
     * @param dictMaintainType the String dictMaintainType to set
     */
    public void setDictMaintainType(String dictMaintainType) {
        this.dictMaintainType = dictMaintainType;
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
}
