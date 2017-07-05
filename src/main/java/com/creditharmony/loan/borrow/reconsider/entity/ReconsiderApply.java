package com.creditharmony.loan.borrow.reconsider.entity;


import com.creditharmony.core.persistence.DataEntity;

public class ReconsiderApply extends DataEntity<ReconsiderApply>{

    private static final long serialVersionUID = -912877958460976354L;
    // 流程ID
    private String applyId;
    // 借款编号
    private String loanCode;
    // 复议类型
    private String dictReconsiderType;
    // 复议备注
    private String secondReconsiderMsg;
    // 发起人
    private String applyBy;
    // 机构编号
    private String orgCode;
    // 审核状态
    private String dictCheckStatus;
    
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getDictReconsiderType() {
        return dictReconsiderType;
    }

    public void setDictReconsiderType(String dictReconsiderType) {
        this.dictReconsiderType = dictReconsiderType;
    }

    public String getSecondReconsiderMsg() {
        return secondReconsiderMsg;
    }

    public void setSecondReconsiderMsg(String secondReconsiderMsg) {
        this.secondReconsiderMsg = secondReconsiderMsg;
    }

    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDictCheckStatus() {
        return dictCheckStatus;
    }

    public void setDictCheckStatus(String dictCheckStatus) {
        this.dictCheckStatus = dictCheckStatus;
    }
  
}