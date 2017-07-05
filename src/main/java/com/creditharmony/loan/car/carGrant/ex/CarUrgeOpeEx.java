/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.exPaybackOpeEx.java
 * @Create By 王彬彬
 * @Create In 2016年2月18日 下午1:45:36
 */
package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;

/**
 * 记录催收服务费操作记录历史
 * @Class Name CarUrgeOpeEx
 * @author 朱静越
 * @Create In 2016年6月22日
 */
public class CarUrgeOpeEx {
	
	/**
	 * 记录催收服务费划扣或查账的操作历史
	 * @param rUrgeId 催收服务费
	 * @param dictLoanStatus
	 * @param targetWay
	 * @param operateResult
	 * @param remark
	 */
	public CarUrgeOpeEx(String rUrgeId,
			RepaymentProcess dictLoanStatus, TargetWay targetWay,
			String operateResult, String remark) {
		this.rUrgeId = rUrgeId;
		if (dictLoanStatus != null) {
			this.dictLoanStatus = dictLoanStatus.getCode();
		}
		if (targetWay != null) {
			this.dictRDeductType = targetWay.getCode();
		}
		this.operateResult = operateResult;
		this.remark = remark;
	}

	// 关联id（催收服务费）
	private String rUrgeId;
	// 操作步骤(划扣，查账)
	private String dictLoanStatus;
	// 关联类型（还款/催收服务费）
	private String dictRDeductType;
	// 操作结果
	private String operateResult;
	// 备注
	private String remark;

	
	public String getrUrgeId() {
		return rUrgeId;
	}

	public void setrUrgeId(String rUrgeId) {
		this.rUrgeId = rUrgeId;
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
