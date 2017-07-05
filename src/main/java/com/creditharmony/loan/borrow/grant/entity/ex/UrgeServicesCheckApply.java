package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 催收服务费查账申请
 * @Class Name UrgeServicesCheckApply
 * @author zhangfeng
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class UrgeServicesCheckApply extends DataEntity<UrgeServicesCheckApply> {

	// id
	private String id;
	// 关联催收服务费id
	private String rServiceChargeId;
	// 合同编号
	private String contractCode;
	// 催收方式
	private String urgeMethod;
	// 申请金额
	private BigDecimal urgeApplyAmount;
	// 申请日期
	private Date urgeApplyDate;
	// 申请状态
	private String urgeApplyStatus;
	// 实际到账金额
	private BigDecimal urgeReallyAmount;
	// 退回原因
	private String urgeBackReason;
	// 修改时间
	private String reqTime;
	
	// 存入银行
	private String dictDepositAccount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public String getUrgeMethod() {
		return urgeMethod;
	}

	public void setUrgeMethod(String urgeMethod) {
		this.urgeMethod = urgeMethod;
	}

	public BigDecimal getUrgeApplyAmount() {
		return urgeApplyAmount;
	}

	public void setUrgeApplyAmount(BigDecimal urgeApplyAmount) {
		this.urgeApplyAmount = urgeApplyAmount;
	}

	public Date getUrgeApplyDate() {
		return urgeApplyDate;
	}

	public void setUrgeApplyDate(Date urgeApplyDate) {
		this.urgeApplyDate = urgeApplyDate;
	}

	public String getUrgeApplyStatus() {
		return urgeApplyStatus;
	}

	public void setUrgeApplyStatus(String urgeApplyStatus) {
		this.urgeApplyStatus = urgeApplyStatus;
	}

	public BigDecimal getUrgeReallyAmount() {
		return urgeReallyAmount;
	}

	public void setUrgeReallyAmount(BigDecimal urgeReallyAmount) {
		this.urgeReallyAmount = urgeReallyAmount;
	}

	public String getUrgeBackReason() {
		return urgeBackReason;
	}

	public void setUrgeBackReason(String urgeBackReason) {
		this.urgeBackReason = urgeBackReason;
	}

	public String getrServiceChargeId() {
		return rServiceChargeId;
	}

	public void setrServiceChargeId(String rServiceChargeId) {
		this.rServiceChargeId = rServiceChargeId;
	}

	public String getDictDepositAccount() {
		return dictDepositAccount;
	}

	public void setDictDepositAccount(String dictDepositAccount) {
		this.dictDepositAccount = dictDepositAccount;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
}