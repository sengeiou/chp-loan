package com.creditharmony.loan.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;


public class LoanStatusHis extends DataEntity<LoanStatusHis> {
	
	private static final long serialVersionUID = 6271407556039993007L;

	private String applyId;
    
    private String loanCode;

    private String dictLoanStatus;

    private String operateStep;

    private String dictSysFlag;

    private String operateResult;

    private String operator;
    // 操作人角色Id
    private String operateRoleId;
    // 机构编码
    private String orgCode;
    // 操作时间
    private Date operateTime;

    private String remark;
    
    /**
     * 是否显示全部历史
     */
    private boolean showAll = false;
    
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
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }
  

    public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getOperateStep() {
        return operateStep;
    }

    public void setOperateStep(String operateStep) {
        this.operateStep = operateStep == null ? null : operateStep.trim();
    }

    public String getDictSysFlag() {
        return dictSysFlag;
    }

    public void setDictSysFlag(String dictSysFlag) {
        this.dictSysFlag = dictSysFlag == null ? null : dictSysFlag.trim();
    }

    public String getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(String operateResult) {
        this.operateResult = operateResult == null ? null : operateResult.trim();
    }
    
    public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateRoleId() {
		return operateRoleId;
	}

	public void setOperateRoleId(String operateRoleId) {
		this.operateRoleId = operateRoleId;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
}