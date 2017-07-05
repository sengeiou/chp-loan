package com.creditharmony.loan.credit.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CreditPaidFactoring {
    private String id;

    private String loanCode;

    private String loanOrg;

    private String dictCurrency;

    private BigDecimal factoringAmount;

    private Date factoringDay;

    private Date actualDay;

    private Date paidDay;

    private String dictRepayMethod;

    private String dictLevelClass;

    private String dictGuarantee;

    private String dictExhibition;

    private String operation;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

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

    public String getLoanOrg() {
        return loanOrg;
    }

    public void setLoanOrg(String loanOrg) {
        this.loanOrg = loanOrg == null ? null : loanOrg.trim();
    }

    public String getDictCurrency() {
        return dictCurrency;
    }

    public void setDictCurrency(String dictCurrency) {
        this.dictCurrency = dictCurrency == null ? null : dictCurrency.trim();
    }

    public BigDecimal getFactoringAmount() {
        return factoringAmount;
    }

    public void setFactoringAmount(BigDecimal factoringAmount) {
        this.factoringAmount = factoringAmount;
    }

    public Date getFactoringDay() {
        return factoringDay;
    }

    public void setFactoringDay(Date factoringDay) {
        this.factoringDay = factoringDay;
    }

    public Date getActualDay() {
        return actualDay;
    }

    public void setActualDay(Date actualDay) {
        this.actualDay = actualDay;
    }

    public Date getPaidDay() {
        return paidDay;
    }

    public void setPaidDay(Date paidDay) {
        this.paidDay = paidDay;
    }

    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod == null ? null : dictRepayMethod.trim();
    }

    public String getDictLevelClass() {
        return dictLevelClass;
    }

    public void setDictLevelClass(String dictLevelClass) {
        this.dictLevelClass = dictLevelClass == null ? null : dictLevelClass.trim();
    }

    public String getDictGuarantee() {
        return dictGuarantee;
    }

    public void setDictGuarantee(String dictGuarantee) {
        this.dictGuarantee = dictGuarantee == null ? null : dictGuarantee.trim();
    }

    public String getDictExhibition() {
        return dictExhibition;
    }

    public void setDictExhibition(String dictExhibition) {
        this.dictExhibition = dictExhibition == null ? null : dictExhibition.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
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
}