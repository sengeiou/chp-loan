package com.creditharmony.loan.borrow.refund.entity;


import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 已退款导出
 * 
 * @Class 
 * @author huownelong
 * @Create In 2016年10月25日
 */
@SuppressWarnings("serial")
public class AlreadyRefundExportExcel  extends DataEntity<AlreadyRefundExportExcel>{
	
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1)
	private String contractCode;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 2)
	private String contractAmount;
	@ExcelField(title = "退款金额", type = 0, align = 2, sort = 3)
	private String refundMoney;
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 4)
	private String menDianName;
	@ExcelField(title = "退款银行", type = 0, align = 2, sort = 5)
	private String incomeBank;
	@ExcelField(title = "退款日期", type = 0, align = 2, sort = 6)
	private String backDate;
	//借款状态
	private String loanStatus;
	
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getMenDianName() {
		return menDianName;
	}
	public void setMenDianName(String menDianName) {
		this.menDianName = menDianName;
	}
	public String getIncomeBank() {
		return incomeBank;
	}
	public void setIncomeBank(String incomeBank) {
		this.incomeBank = incomeBank;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
}

