package com.creditharmony.loan.borrow.refund.entity;

import com.creditharmony.core.persistence.DataEntity;



/**
 * 退款历史表
 * 
 * @Class Name Refund
 * @author WJJ
 * @Create In 2016年4月19日
 */
@SuppressWarnings("serial")
public class PaybackHistory extends DataEntity<PaybackHistory> {
	
	private String id;//
	private String contractCode;
	private String operName;
	private String operResult;
	private String operNotes;
	private String createBy;//创建人
	private String createTimes;//创建时间
	private String modifyBy;//修改人
	private String modifyTimes;//修改时间
	
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
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getOperResult() {
		return operResult;
	}
	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}
	public String getOperNotes() {
		return operNotes;
	}
	public void setOperNotes(String operNotes) {
		this.operNotes = operNotes;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTimes() {
		return createTimes;
	}
	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyTimes() {
		return modifyTimes;
	}
	public void setModifyTimes(String modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	
}

