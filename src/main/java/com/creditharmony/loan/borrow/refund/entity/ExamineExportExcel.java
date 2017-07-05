package com.creditharmony.loan.borrow.refund.entity;


import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 退款初审导出表
 * 
 * @Class Name Refund
 * @author WJJ
 * @Create In 2016年4月19日
 */
@SuppressWarnings("serial")
public class ExamineExportExcel  extends DataEntity<ExamineExportExcel>{
	@ExcelField(title = "序号", type = 2, align = 2, sort = 1)
	private String num;
	@ExcelField(title = "门店", type = 0, align = 2, sort = 2)
	private String mendianName;
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3)
	private String contractCode;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 4)
	private String customerName;
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 5,dictType = "jk_loan_apply_status")
	private String loanStatus;
	@ExcelField(title = "申请退款金额", type = 0, align = 2, sort = 6)
	private String refundMoney;
	@ExcelField(title = "申请日期", type = 0, align = 2, sort = 7)
	private String createTimes;
	@ExcelField(title = "退款类别", type = 0, align = 2, sort = 8,dictType = "jk_app_type")
	private String appType;
	@ExcelField(title = "退款原因", type = 0, align = 2, sort = 9)
	private String fkReason;
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 10,dictType = "jk_open_bank")
	private String bankName;
	@ExcelField(title = "开户行支行", type = 0, align = 2, sort = 11)
	private String incomeBranch;
	@ExcelField(title = "收款省直辖市", type = 0, align = 2, sort = 12)
	private String incomeCity;
	@ExcelField(title = "收款市县", type = 0, align = 2, sort = 13)
	private String incomeCounty;
	@ExcelField(title = "申请状态", type = 0, align = 2, sort = 14,dictType = "jk_app_status")
	private String appStatus;
	
	public String getMendianName() {
		return mendianName;
	}
	public void setMendianName(String mendianName) {
		this.mendianName = mendianName;
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
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getCreateTimes() {
		return createTimes;
	}
	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getFkReason() {
		return fkReason;
	}
	public void setFkReason(String fkReason) {
		this.fkReason = fkReason;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIncomeBranch() {
		return incomeBranch;
	}
	public void setIncomeBranch(String incomeBranch) {
		this.incomeBranch = incomeBranch;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	
}

