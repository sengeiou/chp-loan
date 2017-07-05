package com.creditharmony.loan.borrow.outvisit.enity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 外访_外访任务清单
 * @Class Name LoanOutsideTaskList
 * @author 张进
 * @Create In 2015年12月26日
 */
public class LoanOutsideTaskList extends DataEntity<LoanOutsideTaskList> {
	private static final long serialVersionUID = 1L;

	private String id;

    private String loanCode;

    private String surveyEmpId;

    private BigDecimal itemDistance;

    private Date surveyStartTime;

    private Date surveyEndTime;

    private String dictSurveyStatus;

    private String dictCheckType;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

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

    public Date getSurveyStartTime() {
        return surveyStartTime;
    }

    public void setSurveyStartTime(Date surveyStartTime) {
        this.surveyStartTime = surveyStartTime;
    }

    public Date getSurveyEndTime() {
        return surveyEndTime;
    }

    public void setSurveyEndTime(Date surveyEndTime) {
        this.surveyEndTime = surveyEndTime;
    }

    public String getDictSurveyStatus() {
        return dictSurveyStatus;
    }

    public void setDictSurveyStatus(String dictSurveyStatus) {
        this.dictSurveyStatus = dictSurveyStatus == null ? null : dictSurveyStatus.trim();
    }

    public String getDictCheckType() {
        return dictCheckType;
    }

    public void setDictCheckType(String dictCheckType) {
        this.dictCheckType = dictCheckType == null ? null : dictCheckType.trim();
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
}