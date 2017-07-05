package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 余额不足实体
 * @author 翁私
 *
 */
@SuppressWarnings("serial")
public class BalanceInfo extends DataEntity<AuditBack>{
	
	/**
	 * 划扣账号
	 */
	private String accountNo;
	
	private int  total;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
