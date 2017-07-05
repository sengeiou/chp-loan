/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.viewPaperLessView.java
 * @Create By 王彬彬
 * @Create In 2016年4月19日 下午9:38:37
 */
package com.creditharmony.loan.borrow.contract.view;

import java.util.Date;

/**
 * 无纸化，短信等使用view
 * @Class Name PaperLessView
 * @author 王彬彬
 * @Create In 2016年4月19日
 */
public class PaperLessView {
	private String loanCode;//借款编码

	private String customerCode;//借款编码or共借人Id
	
	private String cobId;//共借人表Id
	
	private String customerName;//姓名
	
	private String pin;//验证码
	
	private String captchaIfConfirm;//是否已经验证
	
	private Date confirmTimeout; //验证码失效时间点

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCobId() {
		return cobId;
	}

	public void setCobId(String cobId) {
		this.cobId = cobId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCaptchaIfConfirm() {
		return captchaIfConfirm;
	}

	public void setCaptchaIfConfirm(String captchaIfConfirm) {
		this.captchaIfConfirm = captchaIfConfirm;
	}

	public Date getConfirmTimeout() {
		return confirmTimeout;
	}

	public void setConfirmTimeout(Date confirmTimeout) {
		this.confirmTimeout = confirmTimeout;
	}
}
