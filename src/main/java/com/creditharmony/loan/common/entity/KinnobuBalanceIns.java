package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 中金余额不足交易相应码
 * @author xh
 *
 */
@SuppressWarnings("serial")
public class KinnobuBalanceIns extends DataEntity<KinnobuBalanceIns>{
	
	private String kinnobuBankCode;
	
	private String kinnobuBankMsg;
	
	private String id;

	public String getKinnobuBankCode() {
		return kinnobuBankCode;
	}

	public void setKinnobuBankCode(String kinnobuBankCode) {
		this.kinnobuBankCode = kinnobuBankCode;
	}

	public String getKinnobuBankMsg() {
		return kinnobuBankMsg;
	}

	public void setKinnobuBankMsg(String kinnobuBankMsg) {
		this.kinnobuBankMsg = kinnobuBankMsg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
