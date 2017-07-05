package com.creditharmony.loan.car.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 催收服务费操作历史
 * @Class Name CarUrgeOpe
 * @author 朱静越
 * @Create In 2016年6月22日
 */
public class CarUrgeOpe extends DataEntity<CarUrgeOpe> {
	
	private static final long serialVersionUID = 9086615814167880974L;
	
	private String id;
	// 关联id
	private String rUrgeId;
	// 操作步骤(划扣，查账)
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
	// 备注
	private String remark;
	// 操作人角色
	private String operateRoleId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getrUrgeId() {
		return rUrgeId;
	}

	public void setrUrgeId(String rUrgeId) {
		this.rUrgeId = rUrgeId;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
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

	public String getOperateCode() {
		return operateCode;
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
