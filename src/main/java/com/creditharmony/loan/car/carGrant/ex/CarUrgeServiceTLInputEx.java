package com.creditharmony.loan.car.carGrant.ex;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款当日待划扣列表通联平台导入
 * @Class Name CarUrgeServiceTLInputEx
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class CarUrgeServiceTLInputEx extends DataEntity<CarUrgeServiceTLInputEx> {

	//文件序号
	@ExcelField(title = "文件序号", type = 0, align = 2, sort = 10)
	private String fileIndex;	
	//文件名
	@ExcelField(title = "文件名", type = 0, align = 2, sort = 20)
	private String fileName;
	//记录序号
	@ExcelField(title = "记录序号", type = 0, align = 2, sort = 30)
	private String recordIndex;
	//交易类型
	@ExcelField(title = "交易类型", type = 0, align = 2, sort = 40)
	private String transactionType;
	// 处理状态
	@ExcelField(title = "处理状态", type = 0, align = 2, sort = 50)
	private String  dictDealStatus;
	// 商户
	@ExcelField(title = "商户", type = 0, align = 2, sort = 60)
	private String businessMerchant;
	// 业务名称
	@ExcelField(title = "业务名称", type = 0, align = 2, sort = 70)
	private String businessName;
	// 开户银行
	@ExcelField(title = "开户银行", type = 0, align = 2, sort = 80)
	private String bankName;
	// 账号
	@ExcelField(title = "账号", type = 0, align = 2, sort = 90)
	private String  bankAccount;
	// 姓名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 100)
	private String bankAccountName;
	// 交易金额
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 140)
	private double  transactionAmount;
	// 手续费
	@ExcelField(title = "手续费", type = 0, align = 2, sort = 150)
	private double  counterFee;
	//开户省
	@ExcelField(title = "开户省", type = 0, align = 2, sort = 160)
	private String bankProvince;
	// 开户市
	@ExcelField(title = "开户市", type = 0, align = 2, sort = 170)
	private String bankCity;
	// 支付行号
	@ExcelField(title = "支付行号", type = 0, align = 2, sort = 180)
	private String bankCode;
	// 终端号
	@ExcelField(title = "终端号", type = 0, align = 2, sort = 190)
	private String terminalCode;
	//提交时间
	@ExcelField(title = "提交时间", type = 0, align = 2, sort = 200)
	private Date submitTime;
	//商户审核时间
	@ExcelField(title = "商户审核时间", type = 0, align = 2, sort = 210)
	private Date merchantCheckTime;
	//完成时间
	@ExcelField(title = "完成时间", type = 0, align = 2, sort = 220)
	private Date completeTime;
	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 230)
	private String reason;
	// 保留字段
	@ExcelField(title = "保留字段", type = 0, align = 2, sort = 240)
	private String redain;
	// 备注，唯一标识，根据该列进行判断
	@ExcelField(title = "备注", type = 0, align = 2, sort = 250)
	private String enterpriseSerialno;
	
	public String getFileIndex() {
		return fileIndex;
	}
	public void setFileIndex(String fileIndex) {
		this.fileIndex = fileIndex;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRecordIndex() {
		return recordIndex;
	}
	public void setRecordIndex(String recordIndex) {
		this.recordIndex = recordIndex;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getDictDealStatus() {
		return dictDealStatus;
	}
	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
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
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public double getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(double counterFee) {
		this.counterFee = counterFee;
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
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getTerminalCode() {
		return terminalCode;
	}
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Date getMerchantCheckTime() {
		return merchantCheckTime;
	}
	public void setMerchantCheckTime(Date merchantCheckTime) {
		this.merchantCheckTime = merchantCheckTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRedain() {
		return redain;
	}
	public void setRedain(String redain) {
		this.redain = redain;
	}
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}
	
	
	
}