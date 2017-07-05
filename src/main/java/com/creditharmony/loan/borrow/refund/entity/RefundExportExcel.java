package com.creditharmony.loan.borrow.refund.entity;


import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 退款导出表
 * 
 * @Class Name Refund
 * @author WJJ
 * @Create In 2016年4月19日
 */
@SuppressWarnings("serial")
public class RefundExportExcel  extends DataEntity<RefundExportExcel>{
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1)
	private String contractCode;
	@ExcelField(title = "收款账户", type = 0, align = 2, sort = 2)
	private String incomeAccount;
	@ExcelField(title = "收款户名", type = 0, align = 2, sort = 3)
	private String incomeName;
	@ExcelField(title = "转账金额", type = 0, align = 2, sort = 4)
	private String refundMoney;
	@ExcelField(title = "备注", type = 0, align = 2, sort = 5)
	private String remark;
	@ExcelField(title = "收款银行", type = 0, align = 2, sort = 6)
	private String incomeBank;
	@ExcelField(title = "收款银行支行", type = 0, align = 2, sort = 7)
	private String incomeBranch;
	@ExcelField(title = "收款省直辖市", type = 0, align = 2, sort = 8)
	private String incomeCity;
	@ExcelField(title = "收款市县", type = 0, align = 2, sort = 9)
	private String incomeCounty;
	@ExcelField(title = "期数", type = 0, align = 2, sort = 10)
	private String contractMonths;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 11)
	private String contractAmount;
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 12)
	private String contractVersion;
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 13)
	private String urgeMoeny;
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 14,dictType = "jk_telemarketing")
	private String customerFlag;
	@ExcelField(title = "唯一标识", type = 0, align = 2, sort = 15)
	private String refundId;
	@ExcelField(title = "退款银行", type = 0, align = 2, sort = 16)
	private String refundBank;
	@ExcelField(title = "退款日期", type = 0, align = 2, sort = 17)
	private String backDate;
	private String loanStatus;
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getIncomeAccount() {
		return incomeAccount;
	}
	public void setIncomeAccount(String incomeAccount) {
		this.incomeAccount = incomeAccount;
	}
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIncomeBank() {
		return incomeBank;
	}
	public void setIncomeBank(String incomeBank) {
		this.incomeBank = incomeBank;
	}
	public String getIncomeBranch() {
		return incomeBranch;
	}
	public void setIncomeBranch(String incomeBranch) {
		this.incomeBranch = incomeBranch;
	}
	public String getIncomeCity() {
		return incomeCity;
	}
	public void setIncomeCity(String incomeCity) {
		this.incomeCity = incomeCity;
	}
	public String getIncomeCounty() {
		return incomeCounty;
	}
	public void setIncomeCounty(String incomeCounty) {
		this.incomeCounty = incomeCounty;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(String urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	public String getCustomerFlag() {
		return customerFlag;
	}
	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getRefundBank() {
		return refundBank;
	}
	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
}

