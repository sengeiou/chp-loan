package com.creditharmony.loan.car.common.entity;

import java.util.Date;

import org.apache.ibatis.type.JdbcType;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 借款状态变更
 * @Class Name CarLoanStatusHis
 * @author ganquan
 * @Create In 2016年1月21日
 */
public class CarLoanStatusHis extends DataEntity<CarLoanStatusHis> {
	private static final long serialVersionUID = 2223213927316275576L;
	private String id;              //id
	private String applyId;			// applyId
	private String loanCode;        //借款编码
	private String dictLoanStatus;  //借款状态
	private String operateStep;     //操作步骤(回退,放弃,拒绝 等)
	private String dictSysFlag;     //系统标示(汇金，汇诚，公共)
	private String operateResult;   //操作结果
	private String operator;        //操作人
	private String operatorRoleId;  //操作人角色
	private String orgCode;         //机构编码
	private Date operateTime;       //操作时间
	private String remark;          //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
		this.operateStep = operateStep;
	}
	public String getDictSysFlag() {
		return dictSysFlag;
	}
	public void setDictSysFlag(String dictSysFlag) {
		this.dictSysFlag = dictSysFlag;
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
	public String getOperatorRoleId() {
		return operatorRoleId;
	}
	public void setOperatorRoleId(String operatorRoleId) {
		this.operatorRoleId = operatorRoleId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	

}
