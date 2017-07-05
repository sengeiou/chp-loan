package com.creditharmony.loan.borrow.certification.entity;

import java.util.Date;


/**
 * 卡联接口查询使用的银行实体
 * @Class Name LoanBank
 * @author 朱静越
 * @Create In 2016年8月2日
 */
public class LoanBank {
	
	private String id;
	// 商户交易流水
	private String trSerialNo;
	// 新的商户交易流水
	private String newTrSerialNo;
	// 交易日期
	private String transDate;
	// 认证标识
	private String realAuthen;
	// 签约标识
	private String klSign;
	// 账户号
	private String accNo;
	// 账户名
	private String accName;
	// 证件号码
	private String certNo;
	// 手机号码
	private String phone;
	// 开户行所在省份编码
	private String provNo;
	// 开户行号
	private String bankCode;
	// 卡折标识
	private String cardType;
	// 证件类型
	private String certType;
	// 支行名称
	private String bankName;
	// 协议开始时间
	private String beginDate;
	// 协议结束时间
	private String endDate;
	
	private String loanCode;
	// 银行名称
	private String bankFullName;
	
	private Date commitDate;
	
	private long pageNo;
	
	private String singleSerialNo;
	
	// 畅捷是否实名认证
	private String cjAuthen;
	// 畅捷是否签约
	private String cjSign;
	// 畅捷是否签约流水号
	private String cjQyNo;
	// 签约协议号
	private String  protocolNo;
	// 签约失败原因
	private String cjSignFailure;
	// 实名认证原因
	private String cjAuthenFailure;
	
	
	public String getTrSerialNo() {
		return trSerialNo;
	}
	public void setTrSerialNo(String trSerialNo) {
		this.trSerialNo = trSerialNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getRealAuthen() {
		return realAuthen;
	}
	public void setRealAuthen(String realAuthen) {
		this.realAuthen = realAuthen;
	}
	public String getNewTrSerialNo() {
		return newTrSerialNo;
	}
	public void setNewTrSerialNo(String newTrSerialNo) {
		this.newTrSerialNo = newTrSerialNo;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getKlSign() {
		return klSign;
	}
	public void setKlSign(String klSign) {
		this.klSign = klSign;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public String getProvNo() {
		return provNo;
	}
	public void setProvNo(String provNo) {
		this.provNo = provNo;
	}
	public long getPageNo() {
		return pageNo;
	}
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	public String getSingleSerialNo() {
		return singleSerialNo;
	}
	public void setSingleSerialNo(String singleSerialNo) {
		this.singleSerialNo = singleSerialNo;
	}
	public String getCjAuthen() {
		return cjAuthen;
	}
	public void setCjAuthen(String cjAuthen) {
		this.cjAuthen = cjAuthen;
	}
	public String getCjSign() {
		return cjSign;
	}
	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}
	public String getCjQyNo() {
		return cjQyNo;
	}
	public void setCjQyNo(String cjQyNo) {
		this.cjQyNo = cjQyNo;
	}
	public String getProtocolNo() {
		return protocolNo;
	}
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}
	public String getCjSignFailure() {
		return cjSignFailure;
	}
	public void setCjSignFailure(String cjSignFailure) {
		this.cjSignFailure = cjSignFailure;
	}
	public String getCjAuthenFailure() {
		return cjAuthenFailure;
	}
	public void setCjAuthenFailure(String cjAuthenFailure) {
		this.cjAuthenFailure = cjAuthenFailure;
	}
	
	
}