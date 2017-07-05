package com.creditharmony.loan.borrow.poscard.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeBackMoneyEx;
//导出pos后台数据列表
@SuppressWarnings("serial")
public class PosBacktageEx extends DataEntity<UrgeBackMoneyEx>{
	// 序号
	private String index;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 2,groups ={1,2})
	private String contractCode;
	// 参考编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3,groups ={1,2})
	private String referCode;
	//pos订单编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 4,groups ={1,2})
	private String posOrderNumber;
	//查账日期
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 5,groups ={1,2})
	private Date auditDate;
	//到账日期
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 6,groups ={1,2})
	private Date paybackDack;
	//存入账户
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 7,groups ={1,2})
	private  String depositedAccount;
	//匹配状态
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 8,groups ={1,2})
	private String matchingState;
	//金额
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 9,groups ={1,2})
	private BigDecimal applyReallyAmount;
	
	
	
	//------------------------------------------
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getReferCode() {
		return referCode;
	}
	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}
	public String getPosOrderNumber() {
		return posOrderNumber;
	}
	public void setPosOrderNumber(String posOrderNumber) {
		this.posOrderNumber = posOrderNumber;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Date getPaybackDack() {
		return paybackDack;
	}
	public void setPaybackDack(Date paybackDack) {
		this.paybackDack = paybackDack;
	}
	public String getDepositedAccount() {
		return depositedAccount;
	}
	public void setDepositedAccount(String depositedAccount) {
		this.depositedAccount = depositedAccount;
	}
	public String getMatchingState() {
		return matchingState;
	}
	public void setMatchingState(String matchingState) {
		this.matchingState = matchingState;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	
	

	
	
	
}
