package com.creditharmony.loan.borrow.contract.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class ContractOperateInfo extends DataEntity<ContractOperateInfo>{
 
    private static final long serialVersionUID = -8007906125810022487L;
    // 合同编号
    private String contractCode;    
    // 借款编码
    private String loanCode;        
    // 操作类型（利率审核，合同制作，合同审核）
    private String dictOperateType;  
    // 操作人
    private String operator;       
    // 操作时间
    private Date operateTime;       
    // 操作机构编码 
    private String operateOrgCode; 
    // 下一个节点
    private String dictContractNextNode;
    // 操作结果
    private String dictOperateResult;    
    // 备注
    private String remarks;    
    //手动验证结果
    private String verification;
    //手动验证不通过的原因
    private String returnReason;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getDictOperateType() {
        return dictOperateType;
    }

    public void setDictOperateType(String dictOperateType) {
        this.dictOperateType = dictOperateType == null ? null : dictOperateType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateOrgCode() {
        return operateOrgCode;
    }

    public void setOperateOrgCode(String operateOrgCode) {
        this.operateOrgCode = operateOrgCode == null ? null : operateOrgCode.trim();
    }

    public String getDictContractNextNode() {
        return dictContractNextNode;
    }

    public void setDictContractNextNode(String dictContractNextNode) {
        this.dictContractNextNode = dictContractNextNode == null ? null : dictContractNextNode.trim();
    }

    public String getDictOperateResult() {
        return dictOperateResult;
    }

    public void setDictOperateResult(String dictOperateResult) {
        this.dictOperateResult = dictOperateResult == null ? null : dictOperateResult.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

    
}