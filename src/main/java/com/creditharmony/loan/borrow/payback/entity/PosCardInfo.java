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
 * POS刷卡查账用
 * 
 * @Class Name PaybackTransferInfo
 * @author guanhongchang
 * @Create In 2016年2月24日
 */
@SuppressWarnings("serial")
public class PosCardInfo extends DataEntity<PosCardInfo> {
	// 还款申请id
	private String rPaybackApplyId;
	// 合同编号
	private String contractCode;
	// 参考号
	private String referCode;            
	// 订单号
	private String posOrderNumber;  
	// POS小票凭证
	private String posReceiptsCredentials;
	// 存款方式
	private String dictDepositPosCard;
	// 到账日期
	private Date paybackDate;
	// 到账日期 赋值用
	private String paybackDateStr;
	// 实际到账金额
	private String applyReallyAmountPosCard;
	// 实际到账金额
	private BigDecimal applyReallyAmount;
	// 上传人
	private String uploadNamePosCard;
	// 上传时间
	private Date uploadDatePosCard;
	// 上传文件名
	private String uploadFilename;
	// 上传文件路径
	private String uploadPath;

	
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}

	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}


	public Date getPaybackDate() {
		return paybackDate;
	}

	public void setPaybackDate(Date paybackDate) {
		this.paybackDate = paybackDate;
	}

	public String getDictDepositPosCard() {
		return dictDepositPosCard;
	}

	public void setDictDepositPosCard(String dictDepositPosCard) {
		this.dictDepositPosCard = dictDepositPosCard;
	}

	public String getPaybackDateStr() {
		return paybackDateStr;
	}

	public void setPaybackDateStr(String paybackDateStr) {
		this.paybackDateStr = paybackDateStr;
	}

	public String getApplyReallyAmountPosCard() {
		return applyReallyAmountPosCard;
	}

	public void setApplyReallyAmountPosCard(String applyReallyAmountPosCard) {
		this.applyReallyAmountPosCard = applyReallyAmountPosCard;
	}

	public String getrPaybackApplyId() {
		return rPaybackApplyId;
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

	public String getPosReceiptsCredentials() {
		return posReceiptsCredentials;
	}

	public void setPosReceiptsCredentials(String posReceiptsCredentials) {
		this.posReceiptsCredentials = posReceiptsCredentials;
	}


	public String getUploadNamePosCard() {
		return uploadNamePosCard;
	}

	public void setUploadNamePosCard(String uploadNamePosCard) {
		this.uploadNamePosCard = uploadNamePosCard;
	}

	public Date getUploadDatePosCard() {
		return uploadDatePosCard;
	}

	public void setUploadDatePosCard(Date uploadDatePosCard) {
		this.uploadDatePosCard = uploadDatePosCard;
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

	
}