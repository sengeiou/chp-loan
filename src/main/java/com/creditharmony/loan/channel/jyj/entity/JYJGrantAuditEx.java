
package com.creditharmony.loan.channel.jyj.entity;


import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 待放款审核节点中的列表显示的字段
 * @Class Name GrantAuditEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class JYJGrantAuditEx extends DataEntity<JYJGrantAuditEx> {
	private static final long serialVersionUID = 3962172465594535962L;
	// applyId
	private String applyId;
	// 放款id,
	private String id;
	// 借款编号
	private String loanCode;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1, groups = 1)
	private String contractCode; 
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10, groups = 1)
	private String  customerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 20, groups = 1)
	private String storeName;
	// 产品名称
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 40, groups = 1)
	private String replyProductName;
	// 期数
	@ExcelField(title = "批复期限", type = 0, align = 2, sort = 70, groups = 1)
	private Integer replyMonth;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 50, groups = 1)
	private Double contractMoney;
	// 放款金额
	@ExcelField(title = "首次放款金额", type = 0, align = 2, sort = 60, groups = 1)
	private Double lendingMoney;
	// 标识，从字典表中取值
	@ExcelField(title = "标识", type = 0, align = 2, sort = 110, groups = 1)
	private String channelName;
	// 是否电销，从字典表中取值
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 120, groups = 1,dictType="jk_telemarketing")
	private String telesalesFlag;
	// 是否加急
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 130,groups = 1,dictType="jk_urgent_flag")
	private String urgentFlag;
	// 借款类型，需要从字典表中取值
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 30, groups = 1,dictType="jk_loan_type")
	private String classType;
	// 账户姓名，中间人的账户姓名，需要修改，暂时
	private String bankAccountName;
	// 放款账户，银行卡号
	@ExcelField(title = "放款账号", type = 0, align = 2, sort = 80, groups = 1)
	private String lendingAccount;
	// 中间人开户行，
	private String cautionerDepositBank;
	// 账户姓名
	@ExcelField(title = "账户姓名", type = 0, align = 2, sort = 100, groups = 1)
	private String custBankAccountName;
	// 共借人
    private String coborrowerName;
    // 催收服务费
    @ExcelField(title = "划扣金额", type = 0, align = 2, sort = 60, groups = 1)
    private Double urgeServiceFee;
    // 放款途径
    private String lendingWayName;
    // 线上放款回盘时间
 	private Date grantBackDate;
 	// 回执结果
 	@ExcelField(title = "费用收取状态", type = 0, align = 2, sort = 60, groups = 1)
    private String receiptResult;
    // 失败原因
    private String failReason;
    // 放款批次
    private String grantBatchCode;
    // 提交批次
    private String submissionBatch;
    // 提交时间
    private String submissionDate;
    // 提交划扣时间
    private Date submitDeductTime;
    // 放款时间
 	private Date lendingTime;
 	//委托提现状态
    private String trustCash;
    //委托提现回盘状态
    private String trustCashRtn;
    //委托提现失败原因
    private String trustCashFailure;
    // 门店id
    private String storeOrgId;
    // 新老标识
    private String loanInfoOldOrNewFlag;
    // 放款总金额
    private Double totalGrantMoney;
    // 门店省
    private String provinceCode;
    // 门店市
    private String cityCode;
    //未划扣金额
    private String unDeductMoney;
    //放款账号
    private String bankAccountNumber;
    //开户行
    @ExcelField(title = "开户行", type = 0, align = 2, sort = 90, groups = 1)
    private String depositBank;
    //拆分标记
    private String issplit;
    
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getReplyProductName() {
		return replyProductName;
	}
	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
	}
	public Integer getReplyMonth() {
		return replyMonth;
	}
	public void setReplyMonth(Integer replyMonth) {
		this.replyMonth = replyMonth;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public Double getLendingMoney() {
		return lendingMoney;
	}
	public void setLendingMoney(Double lendingMoney) {
		this.lendingMoney = lendingMoney;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getTelesalesFlag() {
		return telesalesFlag;
	}
	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getLendingAccount() {
		return lendingAccount;
	}
	public void setLendingAccount(String lendingAccount) {
		this.lendingAccount = lendingAccount;
	}
	public String getCautionerDepositBank() {
		return cautionerDepositBank;
	}
	public void setCautionerDepositBank(String cautionerDepositBank) {
		this.cautionerDepositBank = cautionerDepositBank;
	}
	public String getCoborrowerName() {
		return coborrowerName;
	}
	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}
	public Double getUrgeServiceFee() {
		return urgeServiceFee;
	}
	public void setUrgeServiceFee(Double urgeServiceFee) {
		this.urgeServiceFee = urgeServiceFee;
	}
	public String getLendingWayName() {
		return lendingWayName;
	}
	public void setLendingWayName(String lendingWayName) {
		this.lendingWayName = lendingWayName;
	}
	public Date getGrantBackDate() {
		return grantBackDate;
	}
	public void setGrantBackDate(Date grantBackDate) {
		this.grantBackDate = grantBackDate;
	}
	public String getReceiptResult() {
		return receiptResult;
	}
	public void setReceiptResult(String receiptResult) {
		this.receiptResult = receiptResult;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getGrantBatchCode() {
		return grantBatchCode;
	}
	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}
	public String getSubmissionBatch() {
		return submissionBatch;
	}
	public void setSubmissionBatch(String submissionBatch) {
		this.submissionBatch = submissionBatch;
	}
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getTrustCash() {
		return trustCash;
	}
	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}
	public String getStoreOrgId() {
		return storeOrgId;
	}
	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}
	public String getCustBankAccountName() {
		return custBankAccountName;
	}
	public void setCustBankAccountName(String custBankAccountName) {
		this.custBankAccountName = custBankAccountName;
	}
	public Date getSubmitDeductTime() {
		return submitDeductTime;
	}
	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public Double getTotalGrantMoney() {
		return totalGrantMoney;
	}
	public void setTotalGrantMoney(Double totalGrantMoney) {
		this.totalGrantMoney = totalGrantMoney;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getTrustCashRtn() {
		return trustCashRtn;
	}
	public void setTrustCashRtn(String trustCashRtn) {
		this.trustCashRtn = trustCashRtn;
	}
	public String getTrustCashFailure() {
		return trustCashFailure;
	}
	public void setTrustCashFailure(String trustCashFailure) {
		this.trustCashFailure = trustCashFailure;
	}
	public String getUnDeductMoney() {
		return unDeductMoney;
	}
	public void setUnDeductMoney(String unDeductMoney) {
		this.unDeductMoney = unDeductMoney;
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
	public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
	
}