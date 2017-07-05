package com.creditharmony.loan.borrow.creditor.view;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LoanDebtModel {
	
	private BigDecimal debtTimes;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDateFri;
	
	public BigDecimal getDebtTimes() {
		return debtTimes;
	}
	public void setDebtTimes(BigDecimal debtTimes) {
		this.debtTimes = debtTimes;
	}
	public Date getPayDateFri() {
		return payDateFri;
	}
	public void setPayDateFri(Date payDateFri) {
		this.payDateFri = payDateFri;
	}
	
}
