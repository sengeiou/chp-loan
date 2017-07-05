/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exTlImportExport.java
 * @Create By 王彬彬
 * @Create In 2016年3月1日 上午11:52:30
 */
package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 通联导入导出模版（group1:模版1，2:模版2，3：导出模版）
 * 
 * @Class Name TlImportExport
 * @author 王彬彬
 * @Create In 2016年3月1日
 */
public class TlImport {
	// 文件序号
	@ExcelField(title = "文件序号", type = 0, align = 2, sort = 10, groups = { 1 })
	private String fileNum;

	// 文件名
	@ExcelField(title = "文件名", type = 0, align = 2, sort = 20, groups = { 1 })
	private String fileName;

	// 记录序号
	@ExcelField(title = "记录序号", type = 0, align = 2, sort = 30, groups = { 1 })
	private String recordNum;

	// 交易类型
	@ExcelField(title = "交易类型", type = 0, align = 2, sort = 40, groups = { 1 })
	private String tradeType;

	// 处理状态
	@ExcelField(title = "处理状态", type = 0, align = 2, sort = 50, groups = { 1 })
	private String dealStatus;

	// 商户
	@ExcelField(title = "商户", type = 0, align = 2, sort = 60, groups = { 1 })
	private String business;

	// 业务名称
	@ExcelField(title = "业务名称", type = 0, align = 2, sort = 70, groups = { 1 })
	private String businessType;

	// 开户银行
	@ExcelField(title = "开户银行", type = 0, align = 2, sort = 80, groups = { 1 })
	private String bankName;

	// 账号
	@ExcelField(title = "账号", type = 0, align = 2, sort = 90, groups = { 1 })
	private String bankAccount;

	// 姓名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 100, groups = { 1 })
	private String name;

	// 交易金额
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 110, groups = { 1 })
	private String tradeAmount;

	// 手续费
	@ExcelField(title = "手续费", type = 0, align = 2, sort = 120, groups = { 1 })
	private String tradeFee;

	// 开户省
	@ExcelField(title = "开户省", type = 0, align = 2, sort = 130, groups = { 1 })
	private String bankProvince;

	// 开户市
	@ExcelField(title = "开户市", type = 0, align = 2, sort = 140, groups = { 1 })
	private String bankCity;

	// 支付行号
	@ExcelField(title = "支付行号", type = 0, align = 2, sort = 150, groups = { 1 })
	private String bankCode;

	// 终端号
	@ExcelField(title = "终端号", type = 0, align = 2, sort = 160, groups = { 1 })
	private String terminalCode;

	// 提交时间(2016-01-21 17:40:00)
	@ExcelField(title = "提交时间", type = 0, align = 2, sort = 170, groups = { 1 })
	private String submitTime;

	// 商户审核时间(2016-01-21 17:40:01)
	@ExcelField(title = "商户审核时间", type = 0, align = 2, sort = 180, groups = { 1 })
	private String businessCheckTime;

	// 完成时间
	@ExcelField(title = "完成时间", type = 0, align = 2, sort = 190, groups = { 1 })
	private String completeTime;

	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 200, groups = { 1 })
	private String reason;

	// 保留字段
	@ExcelField(title = "保留字段", type = 0, align = 2, sort = 210, groups = { 1 })
	private String holeInfo;

	// 备注
	@ExcelField(title = "备注", type = 0, align = 2, sort = 220, groups = { 1 })
	private String remark;
	
	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
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

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getBusinessCheckTime() {
		return businessCheckTime;
	}

	public void setBusinessCheckTime(String businessCheckTime) {
		this.businessCheckTime = businessCheckTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHoleInfo() {
		return holeInfo;
	}

	public void setHoleInfo(String holeInfo) {
		this.holeInfo = holeInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
