/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 银行信息
 * @Class Name PaybackTransferInfo
 * @author zhangfeng
 * @Create In 2015年12月18日
 */
@SuppressWarnings("serial")
public class PaybackTransferInfo extends DataEntity<PaybackTransferInfo> {
	// 还款申请id
	private String rPaybackApplyId;
	// 合同编号
	private String contractCode;
	// 存款方式
	private String dictDeposit;
	// 存款支行
	private String accountBranch;
	// 存款时间
	private Date tranDepositTime;
	// 存款时间 clone赋值
	private String tranDepositTimeStr;
	// 存款时间点
	private Date applyTime;
	private String applyTimeStr;
	// 存入账号
	private String storesInAccount;
	// 存入户名
	private String storesInAccountname;
	// 实际到账金额
	private BigDecimal reallyAmount;
	// 实际到账金额
	private String reallyAmountStr;
	// 实际存款人
	private String depositName;
	// 查账状态
	private String auditStatus;
	// 上传人
	private String uploadName;
	// 上传时间
	private Date uploadDate;
	// 上传文件名
	private String uploadFilename;
	// 上传文件路径
	private String uploadPath;
	// 关联类型(催收服务费,还款)
	private String relationType;
	
	
	//--POS刷卡查账用字段开始
	//参考号
	private String referCode;
	//订单号
	private String posOrderNumber;
	//存款方式
	private String dictDepositPosCard;
	//存款方式名称
	private String dictDepositPosCardLabel;
	//到账时间
	private Date paybackDate;
	//实际到账金额
	private String applyReallyAmountPosCard;
	//上传人
	private String uploadNamePosCard;
	//上传时间
	private Date uploadDatePosCard;
	//--POS刷卡查账用字段结束
	
	private String reqTime;
	
	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getrPaybackApplyId() {
		return rPaybackApplyId;
	}

	public Date getPaybackDate() {
		return paybackDate;
	}

	public void setPaybackDate(Date paybackDate) {
		this.paybackDate = paybackDate;
	}

	public Date getUploadDatePosCard() {
		return uploadDatePosCard;
	}

	public void setUploadDatePosCard(Date uploadDatePosCard) {
		this.uploadDatePosCard = uploadDatePosCard;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}

	public String getPosOrderNumber() {
		return posOrderNumber;
	}

	public void setPosOrderNumber(String posOrderNumber) {
		this.posOrderNumber = posOrderNumber;
	}

	public String getDictDepositPosCard() {
		return dictDepositPosCard;
	}

	public void setDictDepositPosCard(String dictDepositPosCard) {
		this.dictDepositPosCard = dictDepositPosCard;
	}

	public String getApplyReallyAmountPosCard() {
		return applyReallyAmountPosCard;
	}

	public void setApplyReallyAmountPosCard(String applyReallyAmountPosCard) {
		this.applyReallyAmountPosCard = applyReallyAmountPosCard;
	}

	public String getUploadNamePosCard() {
		return uploadNamePosCard;
	}

	public void setUploadNamePosCard(String uploadNamePosCard) {
		this.uploadNamePosCard = uploadNamePosCard;
	}

	public void setrPaybackApplyId(String rPaybackApplyId) {
		this.rPaybackApplyId = rPaybackApplyId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getDictDeposit() {
		return dictDeposit;
	}

	public void setDictDeposit(String dictDeposit) {
		this.dictDeposit = dictDeposit;
	}

	public Date getTranDepositTime() {
		return tranDepositTime;
	}

	public void setTranDepositTime(Date tranDepositTime) {
		this.tranDepositTime = tranDepositTime;
	}

	public String getTranDepositTimeStr() {
		return tranDepositTimeStr;
	}

	public void setTranDepositTimeStr(String tranDepositTimeStr) {
		this.tranDepositTimeStr = tranDepositTimeStr;
	}

	public String getStoresInAccount() {
		return storesInAccount;
	}

	public void setStoresInAccount(String storesInAccount) {
		this.storesInAccount = storesInAccount;
	}

	public String getStoresInAccountname() {
		return storesInAccountname;
	}

	public void setStoresInAccountname(String storesInAccountname) {
		this.storesInAccountname = storesInAccountname;
	}

	public BigDecimal getReallyAmount() {
		return reallyAmount;
	}

	public void setReallyAmount(BigDecimal reallyAmount) {
		this.reallyAmount = reallyAmount;
	}

	public String getDepositName() {
		return depositName;
	}

	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadFilename() {
		return uploadFilename;
	}

	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getReallyAmountStr() {
		return reallyAmountStr;
	}

	public void setReallyAmountStr(String reallyAmountStr) {
		this.reallyAmountStr = reallyAmountStr;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getDictDepositPosCardLabel() {
		return dictDepositPosCardLabel;
	}

	public void setDictDepositPosCardLabel(String dictDepositPosCardLabel) {
		this.dictDepositPosCardLabel = dictDepositPosCardLabel;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
}