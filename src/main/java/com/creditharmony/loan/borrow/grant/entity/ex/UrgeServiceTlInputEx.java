package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表通联平台导出
 * @Class Name UrgeServiceTlEx
 * @author
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class UrgeServiceTlInputEx extends DataEntity<UrgeServiceTlInputEx> {

    //文件序号
    @ExcelField(title = "文件序号", type = 2, align = 2, sort = 1)
    private String fileNo;
    //文件名称
    @ExcelField(title = "文件名称", type = 2, align = 2, sort = 2)
    private String fileName;
    //记录序号
    @ExcelField(title = "记录序号", type = 2, align = 2, sort = 3)
    private String recordNo;
    //交易类型
    @ExcelField(title = "交易类型", type = 2, align = 2, sort = 4)
    private String transactionType;
    //处理状态
    @ExcelField(title = "处理状态", type = 2, align = 2, sort = 5)
    private String Result;
    //商户
    @ExcelField(title = "商户", type = 2, align = 2, sort = 6)
    private String shangHu;
    //业务名称
    @ExcelField(title = "业务名称", type = 2, align = 2, sort = 7)
    private String businessName;
    //开户银行
	@ExcelField(title = "开户银行", type = 2, align = 2, sort = 8, dictType = "jk_open_bank")
	private String bankName;
	//账号
	@ExcelField(title = "账号", type = 2, align = 2, sort = 9)
	private String bankAccount;
	//姓名
	@ExcelField(title = "姓名", type = 2, align = 2, sort = 10)
	private String name;
	// 交易金额
	@ExcelField(title = "交易金额", type = 2, align = 2, sort = 11)
	private String transactionAmount;
	//手续费
	@ExcelField(title = "手续费", type = 2, align = 2, sort = 12)
	private String counterFee;
	@ExcelField(title = "开户行所在省", type = 2, align = 2, sort = 13)
	private String bankProvince;
	@ExcelField(title = "开户行所在市", type = 2, align = 2, sort = 14)
	private String bankCity;
	//支付行号
	@ExcelField(title = "支付行号", type = 2, align = 2, sort = 15)
	private String paymentRow;
	//终端号
	@ExcelField(title = "终端号", type = 2, align = 2, sort = 16)
	private String terminalNo;
	//提交时间
	@ExcelField(title = "提交时间", type = 2, align = 2, sort = 17)
	private String submitTime;
	//商户审核时间
	@ExcelField(title = "商户审核时间", type = 2, align = 2, sort = 18)
	private String examineTime;
	//完成时间
	@ExcelField(title = "完成时间", type = 2, align = 2, sort = 19)
	private String completeTime;
	// 原因
	@ExcelField(title = "原因", type = 2, align = 2, sort = 20)
	private String reason;
	//保留字段 拆分表id
	@ExcelField(title = "保留字段", type = 2, align = 2, sort = 21)
	private String customUserName;
	// 备注，唯一标识，根据该列进行判断
	@ExcelField(title = "备注", type = 2, align = 2, sort = 22)
	private String enterpriseSerialno;
	
	public String getCustomUserName() {
		return customUserName;
	}
	public void setCustomUserName(String customUserName) {
		this.customUserName = customUserName;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(String counterFee) {
		this.counterFee = counterFee;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
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
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getShangHu() {
		return shangHu;
	}
	public void setShangHu(String shangHu) {
		this.shangHu = shangHu;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPaymentRow() {
		return paymentRow;
	}
	public void setPaymentRow(String paymentRow) {
		this.paymentRow = paymentRow;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getExamineTime() {
		return examineTime;
	}
	public void setExamineTime(String examineTime) {
		this.examineTime = examineTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
}