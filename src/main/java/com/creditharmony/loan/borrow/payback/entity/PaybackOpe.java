package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 还款操作历史
 * @Class Name PaybackOpe
 * @author zhaojinping
 * @Create In 2015年12月7日
 */
@SuppressWarnings("serial")
public class PaybackOpe extends DataEntity<PaybackOpe> {
	// 还款申请表ID
	private String rPaybackApplyId;
	// 关联ID(还款主表)
	private String rPaybackId;
	// 操作步骤(申请还款，划扣，审核，确认)
	private String dictLoanStatus;
	// 操作步骤(申请还款，划扣，审核，确认)名称
	private String dictLoanStatusLabel;
	// 关联类型（还款/催收服务费）
	private String dictRDeductType;
	// 操作结果
	private String operateResult;
	// 操作结果(申请成功，申请失败，划扣成功，划扣失败，匹配成功，匹配失败，审核成功，审核失败，确认成功，确认失败，减免成功，减免失败，退回)名称
	private String operateResultLabel;
	// 操作人
	private String operator;
	// 操作人code
	private String operateCode;
	// 操作人时间
	private Date operateTime;
	//备注
	private String remark;
	// 借款编码
	private String loanCode;
	
	private String operateRoleId;
	
	public String getrPaybackId() {
		return rPaybackId;
	}

	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getDictRDeductType() {
		return dictRDeductType;
	}

	public void setDictRDeductType(String dictRDeductType) {
		this.dictRDeductType = dictRDeductType;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	public String getrPaybackApplyId() {
		return rPaybackApplyId;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public void setrPaybackApplyId(String rPaybackApplyId) {
		this.rPaybackApplyId = rPaybackApplyId;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getOperateResultLabel() {
		return operateResultLabel;
	}

	public void setOperateResultLabel(String operateResultLabel) {
		this.operateResultLabel = operateResultLabel;
	}

	public String getOperateRoleId() {
		return operateRoleId;
	}

	public void setOperateRoleId(String operateRoleId) {
		this.operateRoleId = operateRoleId;
	}
	
	
}
