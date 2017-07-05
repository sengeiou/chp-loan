/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.exPaybackOpeEx.java
 * @Create By 王彬彬
 * @Create In 2016年2月18日 下午1:45:36
 */
package com.creditharmony.loan.borrow.payback.entity.ex;

import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;

/**
 * 还款操作流水记录用
 * 
 * @Class Name PaybackOpeEx
 * @author 王彬彬
 * @Create In 2016年2月18日
 */
public class PaybackOpeEx {

	/**
	 * 记录操作流水必要字段
	 * 
	 * @param rPaybackApplyId
	 *            借款申请表（ 催收服务费的情况下可不填）
	 * @param rPaybackId
	 *            还款主表（催收服务费表）
	 * @param dictLoanStatus
	 *            操作步骤(申请还款，划扣，审核，确认)
	 * @param dictRDeductType
	 *            关联类型（还款/催收服务费）
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 */
	public PaybackOpeEx(String rPaybackApplyId, String rPaybackId,
			RepaymentProcess dictLoanStatus, TargetWay targetWay,
			String operateResult, String remark) {
		this.rPaybackApplyId = rPaybackApplyId;
		this.rPaybackId = rPaybackId;
		if (dictLoanStatus != null) {
			this.dictLoanStatus = dictLoanStatus.getCode();
		}
		if (targetWay != null) {
			this.dictRDeductType = targetWay.getCode();
		}
		this.operateResult = operateResult;
		this.remark = remark;
	}

	// 还款申请表ID
	private String rPaybackApplyId;
	// 关联ID(还款主表)
	private String rPaybackId;
	// 操作步骤(申请还款，划扣，审核，确认)
	private String dictLoanStatus;
	// 关联类型（还款/催收服务费）
	private String dictRDeductType;
	// 操作结果
	private String operateResult;
	// 备注
	private String remark;

	public String getrPaybackApplyId() {
		return rPaybackApplyId;
	}

	public String getrPaybackId() {
		return rPaybackId;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public String getDictRDeductType() {
		return dictRDeductType;
	}

	public String getRemark() {
		return remark;
	}

	public void setrPaybackApplyId(String rPaybackApplyId) {
		this.rPaybackApplyId = rPaybackApplyId;
	}

	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public void setDictRDeductType(String dictRDeductType) {
		this.dictRDeductType = dictRDeductType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
}
