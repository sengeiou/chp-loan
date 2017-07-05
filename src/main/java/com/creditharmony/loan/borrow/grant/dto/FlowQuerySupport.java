package com.creditharmony.loan.borrow.grant.dto;

import java.io.Serializable;

/**
 * 流程查询支持类
 * @Class Name FlowQuerySupport
 * @author 张永生
 * @Create In 2016年4月22日
 */
public class FlowQuerySupport implements Serializable{

	private static final long serialVersionUID = 8219870982242848303L;

	private String queueName;        //队列名称
	private String returnUrl;        //返回页面的URL
	private String model = null;     //待办模式
	
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
