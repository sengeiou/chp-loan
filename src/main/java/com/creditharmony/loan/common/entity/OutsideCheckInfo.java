package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class OutsideCheckInfo extends DataEntity<OutsideCheckInfo>{
 
    private String loanCode;

    private String checkDepartment;

    private String surveyEmpId;

    private BigDecimal itemDistance;

    private String surveyStartTime;

    private String surveyEndTime;

    private String dictSurveyStatus;

    private String json;

    private String dictCheckType;

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getCheckDepartment() {
        return checkDepartment;
    }

    public void setCheckDepartment(String checkDepartment) {
        this.checkDepartment = checkDepartment == null ? null : checkDepartment.trim();
    }

    public String getSurveyEmpId() {
        return surveyEmpId;
    }

    public void setSurveyEmpId(String surveyEmpId) {
        this.surveyEmpId = surveyEmpId == null ? null : surveyEmpId.trim();
    }

    public BigDecimal getItemDistance() {
        return itemDistance;
    }

    public void setItemDistance(BigDecimal itemDistance) {
        this.itemDistance = itemDistance;
    }

    public String getSurveyStartTime() {
        return surveyStartTime;
    }

    public void setSurveyStartTime(String surveyStartTime) {
        this.surveyStartTime = surveyStartTime == null ? null : surveyStartTime.trim();
    }

    public String getSurveyEndTime() {
        return surveyEndTime;
    }

    public void setSurveyEndTime(String surveyEndTime) {
        this.surveyEndTime = surveyEndTime == null ? null : surveyEndTime.trim();
    }

    public String getDictSurveyStatus() {
        return dictSurveyStatus;
    }

    public void setDictSurveyStatus(String dictSurveyStatus) {
        this.dictSurveyStatus = dictSurveyStatus == null ? null : dictSurveyStatus.trim();
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json == null ? null : json.trim();
    }

    public String getDictCheckType() {
        return dictCheckType;
    }

    public void setDictCheckType(String dictCheckType) {
        this.dictCheckType = dictCheckType == null ? null : dictCheckType.trim();
    }
}