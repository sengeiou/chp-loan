package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表富友平台导入
 * @Class Name UrgeServiceFyEx
 * @author 朱静越
 * @Create In 2016年1月6日
 */
@SuppressWarnings("serial")
public class UrgeServiceFyInputEx extends DataEntity<UrgeServiceFyInputEx> {
    // 开户行
    @ExcelField(title = "交易提交时间", type = 0, align = 2, sort = 10)
    private String tradeTime;
     // 交易流水
    @ExcelField(title = "交易流水", type = 0, align = 2, sort = 15)
    private String tradeSeq;
    // 文件明细号
    @ExcelField(title = "文件明细号", type = 0, align = 2, sort = 20)
    private String fileDetailNo;
    // 业务类型
    @ExcelField(title = "业务类型", type = 0, align = 2, sort = 25)
    private String busiType;
    // 文件名
    @ExcelField(title = "文件名", type = 0, align = 2, sort = 30)
    private String fileName;
    // 金额
    @ExcelField(title = "金额", type = 0, align = 2, sort = 35)
    private String splitAmount;
	 // 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 40)
	private String bankName;
	// 扣款人银行账号
	@ExcelField(title = "账户名称", type = 0, align = 2, sort = 45)
	private String  bankAccountName;
	// 账户
	@ExcelField(title = "账户", type = 0, align = 2, sort = 50)
	private String  bankAccount;
	// 交易状态
    @ExcelField(title = "交易状态", type = 2, align = 2, sort = 55)
    private String tradingStatus;
    // 返回附言
    @ExcelField(title = "返回附言", type = 2, align = 2, sort = 60)
    private String reason;
    // 企业流水号
    @ExcelField(title = "企业流水号", type = 2, align = 2, sort = 65)
    private String enterpriseSerialno;
    // 备注
    @ExcelField(title = "备注", type = 0, align = 2, sort = 70)
    private String remarks;
    // 手机号
    @ExcelField(title = "手机号", type = 0, align = 2, sort = 75)
    private String customerPhoneFirst;
    // 请求流水
    @ExcelField(title = "请求流水", type = 0, align = 2, sort = 80)
    private String requestSeqNo;
    /**
     * @return the tradeTime
     */
    public String getTradeTime() {
        return tradeTime;
    }
    /**
     * @param tradeTime the String tradeTime to set
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
    /**
     * @return the tradeSeq
     */
    public String getTradeSeq() {
        return tradeSeq;
    }
    /**
     * @param tradeSeq the String tradeSeq to set
     */
    public void setTradeSeq(String tradeSeq) {
        this.tradeSeq = tradeSeq;
    }
    /**
     * @return the fileDetailNo
     */
    public String getFileDetailNo() {
        return fileDetailNo;
    }
    /**
     * @param fileDetailNo the String fileDetailNo to set
     */
    public void setFileDetailNo(String fileDetailNo) {
        this.fileDetailNo = fileDetailNo;
    }
    /**
     * @return the busiType
     */
    public String getBusiType() {
        return busiType;
    }
    /**
     * @param busiType the String busiType to set
     */
    public void setBusiType(String busiType) {
        this.busiType = busiType;
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
     * @return the splitAmount
     */
    public String getSplitAmount() {
        return splitAmount;
    }
    /**
     * @param splitAmount the String splitAmount to set
     */
    public void setSplitAmount(String splitAmount) {
        this.splitAmount = splitAmount;
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
     * @return the tradingStatus
     */
    public String getTradingStatus() {
        return tradingStatus;
    }
    /**
     * @param tradingStatus the String tradingStatus to set
     */
    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
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
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks the String remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return the customerPhoneFirst
     */
    public String getCustomerPhoneFirst() {
        return customerPhoneFirst;
    }
    /**
     * @param customerPhoneFirst the String customerPhoneFirst to set
     */
    public void setCustomerPhoneFirst(String customerPhoneFirst) {
        this.customerPhoneFirst = customerPhoneFirst;
    }
    /**
     * @return the requestSeqNo
     */
    public String getRequestSeqNo() {
        return requestSeqNo;
    }
    /**
     * @param requestSeqNo the String requestSeqNo to set
     */
    public void setRequestSeqNo(String requestSeqNo) {
        this.requestSeqNo = requestSeqNo;
    }
    
   	
	
}