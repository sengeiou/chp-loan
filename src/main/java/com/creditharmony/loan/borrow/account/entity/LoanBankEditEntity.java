package com.creditharmony.loan.borrow.account.entity;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class LoanBankEditEntity extends DataEntity<RepayAccountApply>{
	
    private String id;

    private String loanCode;

    private String bankName;

    private String bankProvince;

    private String bankCity;

    private String bankOrc;

    private String dictCreaType;

    private String bankBranch;

    private String bankAccountName;

    private String bankAccount;

    private String bankSigningPlatform;

    private String dictMaintainStatus;

    private Integer bankTopFlag;

    private String dictMaintainType;

    private String bankCheckResult;

    private String bankCheckDesc;

    private String bankOldAccount;

    private String bankIsRareword;

    private String bankJzhKhhss;

    private String bankJzhKhhqx;

    private String bankAuthorizer;

    private Date createTime;

    private String createBy;

    private String modifyBy;

    private Date modifyTime;

    private Date applyTime;

    private Date preserveTime;

    private String fileId;

    private String fileName;

    private String oldBankAccountId;

    private String updatetype;

    private String dictSourceType;

    private String repaymentFlag;

    private String dictSourceTypePcl;

    private String tlSign;

    private String trSerialNo;

    private Date transDate;

    private String realAuthen;

    private String klSign;

    private String bankNo;

    private String singleSerialNo;

    private String cjSmNo;

    private String cjQyNo;

    private String protocolNumber;

    private String cjAuthen;

    private String cjSign;

    private String cjSignFailure;

    private String cjAuthenFailure;

    private String updatecontent;
    
    private String emailFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince == null ? null : bankProvince.trim();
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity == null ? null : bankCity.trim();
    }

    public String getBankOrc() {
        return bankOrc;
    }

    public void setBankOrc(String bankOrc) {
        this.bankOrc = bankOrc == null ? null : bankOrc.trim();
    }

    public String getDictCreaType() {
        return dictCreaType;
    }

    public void setDictCreaType(String dictCreaType) {
        this.dictCreaType = dictCreaType == null ? null : dictCreaType.trim();
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch == null ? null : bankBranch.trim();
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankSigningPlatform() {
        return bankSigningPlatform;
    }

    public void setBankSigningPlatform(String bankSigningPlatform) {
        this.bankSigningPlatform = bankSigningPlatform == null ? null : bankSigningPlatform.trim();
    }

    public String getDictMaintainStatus() {
        return dictMaintainStatus;
    }

    public void setDictMaintainStatus(String dictMaintainStatus) {
        this.dictMaintainStatus = dictMaintainStatus == null ? null : dictMaintainStatus.trim();
    }

    public Integer getBankTopFlag() {
        return bankTopFlag;
    }

    public void setBankTopFlag(Integer bankTopFlag) {
        this.bankTopFlag = bankTopFlag;
    }

    public String getDictMaintainType() {
        return dictMaintainType;
    }

    public void setDictMaintainType(String dictMaintainType) {
        this.dictMaintainType = dictMaintainType == null ? null : dictMaintainType.trim();
    }

    public String getBankCheckResult() {
        return bankCheckResult;
    }

    public void setBankCheckResult(String bankCheckResult) {
        this.bankCheckResult = bankCheckResult == null ? null : bankCheckResult.trim();
    }

    public String getBankCheckDesc() {
        return bankCheckDesc;
    }

    public void setBankCheckDesc(String bankCheckDesc) {
        this.bankCheckDesc = bankCheckDesc == null ? null : bankCheckDesc.trim();
    }

    public String getBankOldAccount() {
        return bankOldAccount;
    }

    public void setBankOldAccount(String bankOldAccount) {
        this.bankOldAccount = bankOldAccount == null ? null : bankOldAccount.trim();
    }

    public String getBankIsRareword() {
        return bankIsRareword;
    }

    public void setBankIsRareword(String bankIsRareword) {
        this.bankIsRareword = bankIsRareword == null ? null : bankIsRareword.trim();
    }

    public String getBankJzhKhhss() {
        return bankJzhKhhss;
    }

    public void setBankJzhKhhss(String bankJzhKhhss) {
        this.bankJzhKhhss = bankJzhKhhss == null ? null : bankJzhKhhss.trim();
    }

    public String getBankJzhKhhqx() {
        return bankJzhKhhqx;
    }

    public void setBankJzhKhhqx(String bankJzhKhhqx) {
        this.bankJzhKhhqx = bankJzhKhhqx == null ? null : bankJzhKhhqx.trim();
    }

    public String getBankAuthorizer() {
        return bankAuthorizer;
    }

    public void setBankAuthorizer(String bankAuthorizer) {
        this.bankAuthorizer = bankAuthorizer == null ? null : bankAuthorizer.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPreserveTime() {
        return preserveTime;
    }

    public void setPreserveTime(Date preserveTime) {
        this.preserveTime = preserveTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getOldBankAccountId() {
        return oldBankAccountId;
    }

    public void setOldBankAccountId(String oldBankAccountId) {
        this.oldBankAccountId = oldBankAccountId == null ? null : oldBankAccountId.trim();
    }

    public String getUpdatetype() {
        return updatetype;
    }

    public void setUpdatetype(String updatetype) {
        this.updatetype = updatetype == null ? null : updatetype.trim();
    }

    public String getDictSourceType() {
        return dictSourceType;
    }

    public void setDictSourceType(String dictSourceType) {
        this.dictSourceType = dictSourceType == null ? null : dictSourceType.trim();
    }

    public String getRepaymentFlag() {
        return repaymentFlag;
    }

    public void setRepaymentFlag(String repaymentFlag) {
        this.repaymentFlag = repaymentFlag == null ? null : repaymentFlag.trim();
    }

    public String getDictSourceTypePcl() {
        return dictSourceTypePcl;
    }

    public void setDictSourceTypePcl(String dictSourceTypePcl) {
        this.dictSourceTypePcl = dictSourceTypePcl == null ? null : dictSourceTypePcl.trim();
    }

    public String getTlSign() {
        return tlSign;
    }

    public void setTlSign(String tlSign) {
        this.tlSign = tlSign == null ? null : tlSign.trim();
    }

    public String getTrSerialNo() {
        return trSerialNo;
    }

    public void setTrSerialNo(String trSerialNo) {
        this.trSerialNo = trSerialNo == null ? null : trSerialNo.trim();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getRealAuthen() {
        return realAuthen;
    }

    public void setRealAuthen(String realAuthen) {
        this.realAuthen = realAuthen == null ? null : realAuthen.trim();
    }

    public String getKlSign() {
        return klSign;
    }

    public void setKlSign(String klSign) {
        this.klSign = klSign == null ? null : klSign.trim();
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getSingleSerialNo() {
        return singleSerialNo;
    }

    public void setSingleSerialNo(String singleSerialNo) {
        this.singleSerialNo = singleSerialNo == null ? null : singleSerialNo.trim();
    }

    public String getCjSmNo() {
        return cjSmNo;
    }

    public void setCjSmNo(String cjSmNo) {
        this.cjSmNo = cjSmNo == null ? null : cjSmNo.trim();
    }

    public String getCjQyNo() {
        return cjQyNo;
    }

    public void setCjQyNo(String cjQyNo) {
        this.cjQyNo = cjQyNo == null ? null : cjQyNo.trim();
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber == null ? null : protocolNumber.trim();
    }

    public String getCjAuthen() {
        return cjAuthen;
    }

    public void setCjAuthen(String cjAuthen) {
        this.cjAuthen = cjAuthen == null ? null : cjAuthen.trim();
    }

    public String getCjSign() {
        return cjSign;
    }

    public void setCjSign(String cjSign) {
        this.cjSign = cjSign == null ? null : cjSign.trim();
    }

    public String getCjSignFailure() {
        return cjSignFailure;
    }

    public void setCjSignFailure(String cjSignFailure) {
        this.cjSignFailure = cjSignFailure == null ? null : cjSignFailure.trim();
    }

    public String getCjAuthenFailure() {
        return cjAuthenFailure;
    }

    public void setCjAuthenFailure(String cjAuthenFailure) {
        this.cjAuthenFailure = cjAuthenFailure == null ? null : cjAuthenFailure.trim();
    }

    public String getUpdatecontent() {
        return updatecontent;
    }

    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent == null ? null : updatecontent.trim();
    }

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}
}