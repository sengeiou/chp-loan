package com.creditharmony.loan.borrow.contract.entity;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class PostponeEntity extends DataEntity<PostponeEntity> {
    private String id;

    private String applyId;

    private String contractCode;

    private String stepName;

    private Date timeoutpointTime;

    private Date postponeTime;

    private String createBy;

    private Date createTime;

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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public Date getTimeoutpointTime() {
        return timeoutpointTime;
    }

    public void setTimeoutpointTime(Date timeoutpointTime) {
        this.timeoutpointTime = timeoutpointTime;
    }

    public Date getPostponeTime() {
        return postponeTime;
    }

    public void setPostponeTime(Date postponeTime) {
        this.postponeTime = postponeTime;
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
}