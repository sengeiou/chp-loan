package com.creditharmony.loan.borrow.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class HCAuditResultEntity extends DataEntity<HCAuditResultEntity> implements Serializable {
    private String id;

    private String applyId;

    private String loanCode;

    private String rStatusHisId;

    private String singleTastId;

    private String auditResult;

    private String productType;

    private Integer auditMonths;

    private BigDecimal auditAmount;

    private BigDecimal auditContractAmount;

    private BigDecimal auditMonthRepayAmount;

    private String auditRulesCode;

    private String auditCheckExamine;

    private String auditEnsureName;

    private String auditLegalMan;

    private String ensuremanBusinessPlace;

    private String dictCheckType;

    private String attachmentPath;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    private String stepname;

    private BigDecimal grossRate;

    private String dictSourceType;

    private String businessProveId;

    private String ensuremanBusinessProvince;

    private String ensuremanBusinessCity;

    private String ensuremanBusinessArea;

    private String dictSourceTypePcl;

    private String bestCoborrowerId;

    private String businessCertNum;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getrStatusHisId() {
        return rStatusHisId;
    }

    public void setrStatusHisId(String rStatusHisId) {
        this.rStatusHisId = rStatusHisId == null ? null : rStatusHisId.trim();
    }

    public String getSingleTastId() {
        return singleTastId;
    }

    public void setSingleTastId(String singleTastId) {
        this.singleTastId = singleTastId == null ? null : singleTastId.trim();
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult == null ? null : auditResult.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Integer getAuditMonths() {
        return auditMonths;
    }

    public void setAuditMonths(Integer auditMonths) {
        this.auditMonths = auditMonths;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public BigDecimal getAuditContractAmount() {
        return auditContractAmount;
    }

    public void setAuditContractAmount(BigDecimal auditContractAmount) {
        this.auditContractAmount = auditContractAmount;
    }

    public BigDecimal getAuditMonthRepayAmount() {
        return auditMonthRepayAmount;
    }

    public void setAuditMonthRepayAmount(BigDecimal auditMonthRepayAmount) {
        this.auditMonthRepayAmount = auditMonthRepayAmount;
    }

    public String getAuditRulesCode() {
        return auditRulesCode;
    }

    public void setAuditRulesCode(String auditRulesCode) {
        this.auditRulesCode = auditRulesCode == null ? null : auditRulesCode.trim();
    }

    public String getAuditCheckExamine() {
        return auditCheckExamine;
    }

    public void setAuditCheckExamine(String auditCheckExamine) {
        this.auditCheckExamine = auditCheckExamine == null ? null : auditCheckExamine.trim();
    }

    public String getAuditEnsureName() {
        return auditEnsureName;
    }

    public void setAuditEnsureName(String auditEnsureName) {
        this.auditEnsureName = auditEnsureName == null ? null : auditEnsureName.trim();
    }

    public String getAuditLegalMan() {
        return auditLegalMan;
    }

    public void setAuditLegalMan(String auditLegalMan) {
        this.auditLegalMan = auditLegalMan == null ? null : auditLegalMan.trim();
    }

    public String getEnsuremanBusinessPlace() {
        return ensuremanBusinessPlace;
    }

    public void setEnsuremanBusinessPlace(String ensuremanBusinessPlace) {
        this.ensuremanBusinessPlace = ensuremanBusinessPlace == null ? null : ensuremanBusinessPlace.trim();
    }

    public String getDictCheckType() {
        return dictCheckType;
    }

    public void setDictCheckType(String dictCheckType) {
        this.dictCheckType = dictCheckType == null ? null : dictCheckType.trim();
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath == null ? null : attachmentPath.trim();
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public String getStepname() {
        return stepname;
    }

    public void setStepname(String stepname) {
        this.stepname = stepname == null ? null : stepname.trim();
    }

    public BigDecimal getGrossRate() {
        return grossRate;
    }

    public void setGrossRate(BigDecimal grossRate) {
        this.grossRate = grossRate;
    }

    public String getDictSourceType() {
        return dictSourceType;
    }

    public void setDictSourceType(String dictSourceType) {
        this.dictSourceType = dictSourceType == null ? null : dictSourceType.trim();
    }

    public String getBusinessProveId() {
        return businessProveId;
    }

    public void setBusinessProveId(String businessProveId) {
        this.businessProveId = businessProveId == null ? null : businessProveId.trim();
    }

    public String getEnsuremanBusinessProvince() {
        return ensuremanBusinessProvince;
    }

    public void setEnsuremanBusinessProvince(String ensuremanBusinessProvince) {
        this.ensuremanBusinessProvince = ensuremanBusinessProvince == null ? null : ensuremanBusinessProvince.trim();
    }

    public String getEnsuremanBusinessCity() {
        return ensuremanBusinessCity;
    }

    public void setEnsuremanBusinessCity(String ensuremanBusinessCity) {
        this.ensuremanBusinessCity = ensuremanBusinessCity == null ? null : ensuremanBusinessCity.trim();
    }

    public String getEnsuremanBusinessArea() {
        return ensuremanBusinessArea;
    }

    public void setEnsuremanBusinessArea(String ensuremanBusinessArea) {
        this.ensuremanBusinessArea = ensuremanBusinessArea == null ? null : ensuremanBusinessArea.trim();
    }

    public String getDictSourceTypePcl() {
        return dictSourceTypePcl;
    }

    public void setDictSourceTypePcl(String dictSourceTypePcl) {
        this.dictSourceTypePcl = dictSourceTypePcl == null ? null : dictSourceTypePcl.trim();
    }

    public String getBestCoborrowerId() {
        return bestCoborrowerId;
    }

    public void setBestCoborrowerId(String bestCoborrowerId) {
        this.bestCoborrowerId = bestCoborrowerId == null ? null : bestCoborrowerId.trim();
    }

    public String getBusinessCertNum() {
        return businessCertNum;
    }

    public void setBusinessCertNum(String businessCertNum) {
        this.businessCertNum = businessCertNum == null ? null : businessCertNum.trim();
    }
}