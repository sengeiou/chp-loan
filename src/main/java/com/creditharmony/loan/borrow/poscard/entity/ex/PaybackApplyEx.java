package com.creditharmony.loan.borrow.poscard.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;


@SuppressWarnings("serial")
public class PaybackApplyEx extends DataEntity<PaybackApplyEx>{
	// 序号
	private String index;
	
	//订单编号
	@ExcelField(title = "POS机订单编号", type = 0, align = 2, sort = 3,groups ={1,2})
	private String posBillCode;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3,groups ={1,2})
	private String contractCode;
	//客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 3,groups ={1,2})
	private String customerName;
	//合同到期日
	@ExcelField(title = "合同到期日", type = 0, align = 2, sort = 9,groups ={1,2})
	private Date contractEndDay;
	//门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 3,groups ={1,2})
	private String loanTeamOrgId;
	//借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_loan_status")
	private String dictLoanStatus;
	//还款日
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 9,groups ={1,2})
	private Date paybackDay;
	//申请还款金额
	@ExcelField(title = "申请还款金额", type = 0, align = 2, sort = 9,groups ={1,2})
	private BigDecimal applyAmount;
	//实际还款金额
	@ExcelField(title = "实际还款金额", type = 0, align = 2, sort = 9,groups ={1,2})
	private BigDecimal applyReallyAmount;
	//还款类型
	@ExcelField(title = "还款类型", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_repay_type")
	private String dictPayUse;
	//还款状态
	@ExcelField(title = "还款状态", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_repay_status")
	private String dictPayStatus;
	//回盘结果
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_counteroffer_result")
	private String dictPayResult;
	//失败原因
	@ExcelField(title = "失败原因", type = 0, align = 2, sort = 7,groups ={1,2})
	private String applyBackMes;
	//标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_channel_flag")
	private String loanFlag;
	//是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_telemarketing")
	private String customerTelesalesFlag;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getPosBillCode() {
		return posBillCode;
	}
	public void setPosBillCode(String posBillCode) {
		this.posBillCode = posBillCode;
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
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getLoanTeamOrgId() {
		return loanTeamOrgId;
	}
	public void setLoanTeamOrgId(String loanTeamOrgId) {
		this.loanTeamOrgId = loanTeamOrgId;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public Date getPaybackDay() {
		return paybackDay;
	}
	public void setPaybackDay(Date paybackDay) {
		this.paybackDay = paybackDay;
	}
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	public String getDictPayUse() {
		return dictPayUse;
	}
	public void setDictPayUse(String dictPayUse) {
		this.dictPayUse = dictPayUse;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getDictPayResult() {
		return dictPayResult;
	}
	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}
	public String getApplyBackMes() {
		return applyBackMes;
	}
	public void setApplyBackMes(String applyBackMes) {
		this.applyBackMes = applyBackMes;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}

}
