package com.creditharmony.loan.yunwei.operate.entity;

import com.creditharmony.core.persistence.DataEntity;

public class OperateObj extends DataEntity<OperateObj> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 26163428054598433L;
	
	// 唯一标识
	private String sessionId;
	
	// 修改数据SQL
	private String updateSql;
	
	// 修改结果返回
	private String updateDataResult;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getUpdateDataResult() {
		return updateDataResult;
	}

	public void setUpdateDataResult(String updateDataResult) {
		this.updateDataResult = updateDataResult;
	}
	
}
