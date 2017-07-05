package com.creditharmony.loan.borrow.transate.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

public class LoanInfoExport {

	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 100)
	private String contractCode;
	
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 100)
	private String loanCustomerName;
	
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 100)
	private String loanStoreOrgName;
	
	@ExcelField(title = "合同到期日", type = 0, align = 2, sort = 100)
	private String contractEndDay;
	
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 100)
	private String dictLoanStatusLabel;
	
	@ExcelField(title = "有无邮箱", type = 0, align = 2, sort = 100)
	private String emailFalg;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public String getLoanStoreOrgName() {
		return loanStoreOrgName;
	}

	public void setLoanStoreOrgName(String loanStoreOrgName) {
		this.loanStoreOrgName = loanStoreOrgName;
	}

	public String getContractEndDay() {
		return contractEndDay;
	}

	public void setContractEndDay(String contractEndDay) {
		this.contractEndDay = contractEndDay;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getEmailFalg() {
		return emailFalg;
	}

	public void setEmailFalg(String emailFalg) {
		this.emailFalg = emailFalg;
	}
}
