package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款当日待划扣列表好易联平台导出
 * 
 * @Class Name UrgeServiceHylEx
 * @author 朱静越
 * @Create In 2016年1月6日
 */
@SuppressWarnings("serial")
public class UrgeServiceHylInputEx extends DataEntity<UrgeServiceHylInputEx> {
	
    @ExcelField(title = "商户批次号", type = 0, align = 2, sort = 10)
	private String storeBatchNo;
	@ExcelField(title = "文件名", type = 0, align = 2, sort = 20)
	private String fileName;
	@ExcelField(title = "提交时间", type = 0, align = 2, sort = 30)
	private String submitTime;
	// 结清日期
	@ExcelField(title = "清算日期", type = 2, align = 2, sort = 40)
	private String deductTime;
	// 户名
	@ExcelField(title = "记录序号", type = 0, align = 2, sort = 50)
	private String logSeq;
	@ExcelField(title = "开户省", type = 0, align = 2, sort = 60)
	private String bankProvince;
	@ExcelField(title = "开户市", type = 0, align = 2, sort = 70)
	private String bankCity;
	// 行别
	@ExcelField(title = "行别", type = 0, align = 2, sort = 80, dictType = "jk_open_bank")
	private String bankName;
	// 银行账号
    @ExcelField(title = "银行账号", type = 0, align = 2, sort = 90, dictType = "jk_open_bank")
    private String bankAccount;
    // 银行户名
    @ExcelField(title = "银行户名", type = 0, align = 2, sort = 100, dictType = "jk_open_bank")
    private String bankAccountName;
    // 业务商户
    @ExcelField(title = "业务商户", type = 0, align = 2, sort = 110, dictType = "jk_open_bank")
    private String busiStore;
    // 业务名称
    @ExcelField(title = "业务名称", type = 0, align = 2, sort = 120, dictType = "jk_open_bank")
    private String busiName;
    // 业务名称
    @ExcelField(title = "清分账户", type = 0, align = 2, sort = 130, dictType = "jk_open_bank")
    private String deductsAccount;
	// 交易金额
	@ExcelField(title = "交易金额（元）", type = 0, align = 2, sort = 140)
	private String tradeAmount;
	// 处理状态
	@ExcelField(title = "处理状态", type = 0, align = 2, sort = 150)
	private String dealStatus;
	// 交易结果
	@ExcelField(title = "交易结果", type = 0, align = 2, sort = 160)
	private String tradeResult;
 	// 原因
	@ExcelField(title = "原因", type = 2, align = 2, sort = 170)
	private String reason;
	// 备注，唯一标识，根据该列进行判断
    @ExcelField(title = "备注", type = 2, align = 2, sort = 180)
    private String enterpriseSerialno;
	@ExcelField(title = "备注1", type = 0, align = 2, sort = 185)
	private String remarkOne;
	@ExcelField(title = "备注2", type = 0, align = 2, sort = 190)
	private String remarkTwo;
    /**
     * @return the storeBatchNo
     */
    public String getStoreBatchNo() {
        return storeBatchNo;
    }
    /**
     * @param storeBatchNo the String storeBatchNo to set
     */
    public void setStoreBatchNo(String storeBatchNo) {
        this.storeBatchNo = storeBatchNo;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the String fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the submitTime
     */
    public String getSubmitTime() {
        return submitTime;
    }
    /**
     * @param submitTime the String submitTime to set
     */
    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
    /**
     * @return the deductTime
     */
    public String getDeductTime() {
        return deductTime;
    }
    /**
     * @param deductTime the String deductTime to set
     */
    public void setDeductTime(String deductTime) {
        this.deductTime = deductTime;
    }
    /**
     * @return the logSeq
     */
    public String getLogSeq() {
        return logSeq;
    }
    /**
     * @param logSeq the String logSeq to set
     */
    public void setLogSeq(String logSeq) {
        this.logSeq = logSeq;
    }
    /**
     * @return the bankProvince
     */
    public String getBankProvince() {
        return bankProvince;
    }
    /**
     * @param bankProvince the String bankProvince to set
     */
    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }
    /**
     * @return the bankCity
     */
    public String getBankCity() {
        return bankCity;
    }
    /**
     * @param bankCity the String bankCity to set
     */
    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }
    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }
    /**
     * @param bankName the String bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    /**
     * @return the bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }
    /**
     * @param bankAccount the String bankAccount to set
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    /**
     * @return the bankAccountName
     */
    public String getBankAccountName() {
        return bankAccountName;
    }
    /**
     * @param bankAccountName the String bankAccountName to set
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }
    /**
     * @return the busiStore
     */
    public String getBusiStore() {
        return busiStore;
    }
    /**
     * @param busiStore the String busiStore to set
     */
    public void setBusiStore(String busiStore) {
        this.busiStore = busiStore;
    }
    /**
     * @return the busiName
     */
    public String getBusiName() {
        return busiName;
    }
    /**
     * @param busiName the String busiName to set
     */
    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }
    /**
     * @return the deductsAccount
     */
    public String getDeductsAccount() {
        return deductsAccount;
    }
    /**
     * @param deductsAccount the String deductsAccount to set
     */
    public void setDeductsAccount(String deductsAccount) {
        this.deductsAccount = deductsAccount;
    }
    /**
     * @return the tradeAmount
     */
    public String getTradeAmount() {
        return tradeAmount;
    }
    /**
     * @param tradeAmount the String tradeAmount to set
     */
    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
    /**
     * @return the dealStatus
     */
    public String getDealStatus() {
        return dealStatus;
    }
    /**
     * @param dealStatus the String dealStatus to set
     */
    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }
    /**
     * @return the tradeResult
     */
    public String getTradeResult() {
        return tradeResult;
    }
    /**
     * @param tradeResult the String tradeResult to set
     */
    public void setTradeResult(String tradeResult) {
        this.tradeResult = tradeResult;
    }
    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }
    /**
     * @param reason the String reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    /**
     * @return the enterpriseSerialno
     */
    public String getEnterpriseSerialno() {
        return enterpriseSerialno;
    }
    /**
     * @param enterpriseSerialno the String enterpriseSerialno to set
     */
    public void setEnterpriseSerialno(String enterpriseSerialno) {
        this.enterpriseSerialno = enterpriseSerialno;
    }
    /**
     * @return the remarkOne
     */
    public String getRemarkOne() {
        return remarkOne;
    }
    /**
     * @param remarkOne the String remarkOne to set
     */
    public void setRemarkOne(String remarkOne) {
        this.remarkOne = remarkOne;
    }
    /**
     * @return the remarkTwo
     */
    public String getRemarkTwo() {
        return remarkTwo;
    }
    /**
     * @param remarkTwo the String remarkTwo to set
     */
    public void setRemarkTwo(String remarkTwo) {
        this.remarkTwo = remarkTwo;
    }
	
	
	
}