package com.creditharmony.loan.borrow.grant.dto;

import java.io.Serializable;

/**
 * 催收服务费通知类
 * @Class Name UrgeBackMoneyNotify
 * @author 张永生
 * @Create In 2016年4月25日
 */
public class UrgeBackMoneyNotify implements Serializable{

	private static final long serialVersionUID = 8606965040345974234L;
	private int flag;
	private String message;
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
