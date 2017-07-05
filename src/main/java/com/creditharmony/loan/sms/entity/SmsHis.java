package com.creditharmony.loan.sms.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 短信发送履历表
 * 
 * @Class Name SmsHis
 * @author 朱杰
 * @Create In 2016年3月8日
 */
public class SmsHis extends DataEntity<SmsHis> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -3943124417186071190L;
	/**
	 * 借款编码
	 */
	private String loanCode;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 发送时间
	 */
	private Date smsSendTime;
	/**
	 * 短信内容
	 */
	private String smsMsg;
	/**
	 * 短信模板名称
	 */
	private String smsTempletId;
	/**
	 * 发送状态
	 */
	private String smsSendStatus;
	/**
	 * 唯一标识
	 */
	private String onlyFlag;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSmsMsg() {
		return smsMsg;
	}

	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}

	public String getSmsTempletId() {
		return smsTempletId;
	}

	public void setSmsTempletId(String smsTempletId) {
		this.smsTempletId = smsTempletId;
	}

	public String getOnlyFlag() {
		return onlyFlag;
	}

	public void setOnlyFlag(String onlyFlag) {
		this.onlyFlag = onlyFlag;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public Date getSmsSendTime() {
		return smsSendTime;
	}

	public void setSmsSendTime(Date smsSendTime) {
		this.smsSendTime = smsSendTime;
	}

	public String getSmsSendStatus() {
		return smsSendStatus;
	}

	public void setSmsSendStatus(String smsSendStatus) {
		this.smsSendStatus = smsSendStatus;
	}

}
