package com.creditharmony.loan.car.carGrant.ex;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款当日待划扣列表好易联平台导入
 * @Class Name CarUrgeServiceHylInputEx
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class CarUrgeServiceHylInputEx extends DataEntity<CarUrgeServiceHylInputEx> {
	
	//商品批次号
	@ExcelField(title = "商品批次号", type = 0, align = 2, sort = 10)
	private String batchNum;
	//文件名
	@ExcelField(title = "文件名", type = 0, align = 2, sort = 20)
	private String fileName;
	//提交时间
	@ExcelField(title = "提交时间", type = 0, align = 2, sort = 30)
	private String submitTime;
	//清算日期
	@ExcelField(title = "清算日期", type = 0, align = 2, sort = 40)
	private Date deductDate;
	//记录序号
	@ExcelField(title = "记录序号", type = 0, align = 2, sort = 50)
	private String recordIndex;
	//开户省
	@ExcelField(title = "开户省", type = 0, align = 2, sort = 60)
	private String bankProvince;
	// 开户市
	@ExcelField(title = "开户市", type = 0, align = 2, sort = 70)
	private String bankCity;
	//行别
	@ExcelField(title = "行别", type = 0, align = 2, sort = 80)
	private String lineIndex;
	// 银行账号
	@ExcelField(title = "银行账号", type = 0, align = 2, sort = 90)
	private String  bankAccount;
	// 银行户名
	@ExcelField(title = "银行户名", type = 0, align = 2, sort = 100)
	private String bankAccountName;
	// 业务商户
	@ExcelField(title = "业务商户", type = 0, align = 2, sort = 110)
	private String businessMerchant;
	// 业务名称
	@ExcelField(title = "业务名称", type = 0, align = 2, sort = 120)
	private String businessName;
	// 清分账户
	@ExcelField(title = "清分账户", type = 0, align = 2, sort = 130)
	private String  claerAccount;
	// 交易金额
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 140)
	private double  transactionAmount;
	// 处理状态
	@ExcelField(title = "处理状态", type = 0, align = 2, sort = 150)
	private String  dictDealStatus;
	// 交易结果
	@ExcelField(title = "交易结果", type = 0, align = 2, sort = 160)
	private String result;
	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 170)
	private String reason;
	// 备注，唯一标识，根据该列进行判断
	@ExcelField(title = "备注", type = 0, align = 2, sort = 180)
	private String enterpriseSerialno;
	// 备注1
	@ExcelField(title = "备注1", type = 0, align = 2, sort = 190)
	private String remarkOne;
	// 备注2
	@ExcelField(title = "备注2", type = 0, align = 2, sort = 200)
	private String remarkTwo;
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public Date getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}
	public String getRecordIndex() {
		return recordIndex;
	}
	public void setRecordIndex(String recordIndex) {
		this.recordIndex = recordIndex;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getLineIndex() {
		return lineIndex;
	}
	public void setLineIndex(String lineIndex) {
		this.lineIndex = lineIndex;
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
	public String getBusinessMerchant() {
		return businessMerchant;
	}
	public void setBusinessMerchant(String businessMerchant) {
		this.businessMerchant = businessMerchant;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getClaerAccount() {
		return claerAccount;
	}
	public void setClaerAccount(String claerAccount) {
		this.claerAccount = claerAccount;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getDictDealStatus() {
		return dictDealStatus;
	}
	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}
	public String getRemarkOne() {
		return remarkOne;
	}
	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}
	public String getRemarkTwo() {
		return remarkTwo;
	}
	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}
	
	
	

}