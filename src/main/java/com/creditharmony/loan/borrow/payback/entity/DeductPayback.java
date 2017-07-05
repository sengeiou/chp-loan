package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 待还款划扣DeductPayback
 * 
 * @author ws
 * @Create In 2015年12月29日
 */
@SuppressWarnings("serial")
public class DeductPayback extends DataEntity<DeductPayback> {
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 10)
	private String bankName;
	// 扣款人银行账号
	@ExcelField(title = "扣款人银行账号", type = 0, align = 2, sort = 20)
	private String bankAccount;
	// 户名
	@ExcelField(title = "扣款人银行账号", type = 0, align = 2, sort = 30)
	private String bankAccountName;
	// 金额(单位:元)
	@ExcelField(title = "金额(单位:元)", type = 0, align = 2, sort = 40)
	private BigDecimal splitAmount;
	// 企业流水账号 暂时用id代替
	@ExcelField(title = "企业流水号", type = 0, align = 2, sort = 50)
	private String id;
	// 备注
	@ExcelField(title = "备注", type = 0, align = 2, sort = 60)
	private String remarks;
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 70)
	private String customerPhoneFirst;
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 80)
	private String dictertType;
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 90)
	private String customerCertNum;

	@ExcelField(title = "交易状态", type = 2, align = 2, sort = 100)
	private String tradingStatus;
	@ExcelField(title = "企业流水号", type = 2, align = 2, sort = 110)
	private String enterpriseSerialno1;
	@ExcelField(title = "企业流水号", type = 2, align = 2, sort = 120)
	private String enterpriseSerialno;
	
	// 返回附言
	private String returnPost;
	
	private boolean success;

	public String getEnterpriseSerialno1() {
		return enterpriseSerialno1;
	}

	public void setEnterpriseSerialno1(String enterpriseSerialno1) {
		this.enterpriseSerialno1 = enterpriseSerialno1;
	}

	// 划扣平台
	private String dictDealType;
	// 申请状态
	private String dictPaybackStatus;
	// 回盘结果
	private String counteroffer;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}

	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}

	public String getDictertType() {
		return dictertType;
	}

	public void setDictertType(String dictertType) {
		this.dictertType = dictertType;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public String getDictPaybackStatus() {
		return dictPaybackStatus;
	}

	public void setDictPaybackStatus(String dictPaybackStatus) {
		this.dictPaybackStatus = dictPaybackStatus;
	}

	public String getTradingStatus() {
		return tradingStatus;
	}

	public void setTradingStatus(String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}

	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public String getCounteroffer() {
		return counteroffer;
	}

	public void setCounteroffer(String counteroffer) {
		this.counteroffer = counteroffer;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getReturnPost() {
		return returnPost;
	}

	public void setReturnPost(String returnPost) {
		this.returnPost = returnPost;
	}
	
	

}
