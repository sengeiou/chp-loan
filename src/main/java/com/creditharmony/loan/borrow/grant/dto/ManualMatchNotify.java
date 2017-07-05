package com.creditharmony.loan.borrow.grant.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手工匹配的notify类
 * 用于：ajax调用时返回json对象的封装
 * @Class Name ManualMatchNotify
 * @author 张永生
 * @Create In 2016年4月25日
 */
public class ManualMatchNotify implements Serializable{

	private static final long serialVersionUID = 3032789047052371810L;

	private String success;
	private String message;      //消息
	private BigDecimal amount;   //金额
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
