package com.creditharmony.loan.channel.goldcredit.view;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款导出
 * @Class Name GCGrantView
 * @author xiaoniu.hu
 * @Create In 2016年3月7日
 */
@SuppressWarnings("serial")
public class GCGrantView extends DataEntity<GCGrantView>{
    @ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = {1,2})
	private Integer sequenceNumber;
    
    @ExcelField(title = "门店名称", type = 0, align = 2, sort = 2, groups = {1,2})
    private String storeName;
    
    @ExcelField(title = "合同编号", type = 0, align = 2, sort = 3, groups = {1,2})
    private String contractCode;
    
    @ExcelField(title = "客户姓名", type = 0, align = 2, sort = 4, groups = {1,2})
    private String customerName;
    
    @ExcelField(title = "证件号码", type = 0, align = 2, sort = 5, groups = {1,2})
    private String identityCode;
    
    @ExcelField(title = "期数", type = 0, align = 2, sort = 6, groups = 1)
    private Integer months;
    
    @ExcelField(title = "合同金额", type = 0, align = 2, sort = 7, groups = {1,2})
    private BigDecimal contractMoney;
    
    @ExcelField(title = "放款金额", type = 0, align = 2, sort = 8, groups = {1,2})
    private BigDecimal lendingMoney;
    
    @ExcelField(title = "客户账号", type = 0, align = 2, sort = 9, groups = {1,2})
    private String bankAccountNumber;
    
    @ExcelField(title = "开户行", type = 0, align = 2, sort = 10, groups = {1,2})
    private String depositBank;
    
    @ExcelField(title = "支行名称", type = 0, align = 2, sort = 11, groups = 1)
    private String bankBranchName;
    
    @ExcelField(title = "标识", type = 0, align = 2, sort = 12, groups = {1,2})
    private String channelName;
    
    @ExcelField(title = "客户开卡省", type = 0, align = 2, sort = 13, groups = 1)
 	private String bankProvinceName;
    
    @ExcelField(title = "客户开卡市", type = 0, align = 2, sort = 14, groups = 1)
 	private String bankCityName;
    
    @ExcelField(title = "合同版本号", type = 0, align = 2, sort = 15, groups = 1)
    private String contractVersion;
    
    @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 16, groups = {1,2})
    private String feeUrgedService;
    
    @ExcelField(title = "加急标识", type = 0, align = 2, sort = 17, groups = {1,2},dictType="jk_urgent_flag")
    private String urgentFlag; 
    
    @ExcelField(title = "放款批次", type = 0, align = 2, sort = 18, groups = {1,2})
    private String grantBatchCode;
    
    @ExcelField(title = "借款类型", type = 0, align = 2, sort = 19, groups = 2)
    private String loanType; 
    
    @ExcelField(title = "借款产品", type = 0, align = 2, sort = 20, groups = 2)
    private String productName; 
    
    @ExcelField(title = "未划金额", type = 0, align = 2, sort = 21, groups = 2)
    private String unDeductMoney;
    
    @ExcelField(title = "批复期限", type = 0, align = 2, sort = 22, groups = 2)
    private String replyMonth; 
    
    @ExcelField(title = "账号姓名", type = 0, align = 2, sort = 23, groups = 2)
    private String bankAccountName;
    
    @ExcelField(title = "放款时间", type = 0, align = 2, sort = 24, groups = 2)
    private String lendingTime;

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public BigDecimal getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}

	public BigDecimal getLendingMoney() {
		return lendingMoney;
	}

	public void setLendingMoney(BigDecimal lendingMoney) {
		this.lendingMoney = lendingMoney;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBankProvinceName() {
		return bankProvinceName;
	}

	public void setBankProvinceName(String bankProvinceName) {
		this.bankProvinceName = bankProvinceName;
	}

	public String getBankCityName() {
		return bankCityName;
	}

	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getFeeUrgedService() {
		return feeUrgedService;
	}

	public void setFeeUrgedService(String feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}

	public String getUrgentFlag() {
		return urgentFlag;
	}

	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}

	public String getGrantBatchCode() {
		return grantBatchCode;
	}

	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnDeductMoney() {
		return unDeductMoney;
	}

	public void setUnDeductMoney(String unDeductMoney) {
		this.unDeductMoney = unDeductMoney;
	}

	public String getReplyMonth() {
		return replyMonth;
	}

	public void setReplyMonth(String replyMonth) {
		this.replyMonth = replyMonth;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}
	
}
