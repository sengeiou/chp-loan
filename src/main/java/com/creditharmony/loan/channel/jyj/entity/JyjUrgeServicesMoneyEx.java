package com.creditharmony.loan.channel.jyj.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 催收服务费的扩展类，用来对催收服务费进行各种操作
 * 
 * @Class Name UrgeServicesMoneyEx
 * @author 朱静越
 * @Create In 2015年12月11日
 */
@SuppressWarnings("serial")
public class JyjUrgeServicesMoneyEx extends DataEntity<JyjUrgeServicesMoneyEx> {
	
	private String id; // id
	
	private String contractCode;
	
	private BigDecimal money; //
	
	private  BigDecimal  paybackBuleAmount;

	public String getId() {
		return id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}

	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}
	
	
}