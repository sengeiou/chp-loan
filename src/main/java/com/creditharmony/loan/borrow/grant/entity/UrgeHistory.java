package com.creditharmony.loan.borrow.grant.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 返款申请历史
 * @Class Name UrgeHistory
 * @author 张永生
 * @Create In 2016年4月21日
 */
public class UrgeHistory extends DataEntity<UrgeHistory> {
	
	private static final long serialVersionUID = 7690045729240201345L;
	//催收服务费返款历史id
	private String id;
	//催收服务费返款信息id（关联id）
	private String rUrgeId;
	//合同编号	
	private String contractCode;
	//返款结果
	private String dictayPayResult;
	//操作步骤
	private String operateStep;
	
	//操作人
	private String operator;
	//操作时间
	private Date operateTime;
	

	
	
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

	public String getOperateStep() {
		return operateStep;
	}
	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getrUrgeId() {
		return rUrgeId;
	}
	public void setrUrgeId(String rUrgeId) {
		this.rUrgeId = rUrgeId;
	}
	public String getDictayPayResult() {
		return dictayPayResult;
	}
	public void setDictayPayResult(String dictayPayResult) {
		this.dictayPayResult = dictayPayResult;
	}
	 
	
}
