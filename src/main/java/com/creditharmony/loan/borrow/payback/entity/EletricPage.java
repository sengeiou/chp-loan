/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.PaybackMonth.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 電催后台数据表
 * 
 * @Class Name EletricPage
 * @author liushikang
 * @Create In 2016年2月29日
 */
@SuppressWarnings("serial")
public class EletricPage  extends DataEntity<EletricPage>{
	
		// 合同编号
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 0,groups ={1,2})
		private String contractCode;
		// 客户姓名
		@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 1,groups ={1,2})
		private String customerName;
		// 开户行名称
		@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 2,groups ={1,2})
		private String bankName;
		// 门店名称
		@ExcelField(title = "门店名称", type = 0, align = 2, sort = 3,groups ={1,2})
		private String loanTeamOrgId;
		// 合同到期日
		@ExcelField(title = "合同到期日", type = 0, align = 2, sort = 4,groups ={1,2})
		private Date contractEndDay;
		// 批复期限
		@ExcelField(title = "批复期限", type = 0, align = 2, sort = 5,groups ={1,2})
		private BigDecimal contractMonths;
		// 还款日
		@ExcelField(title = "还款日", type = 0, align = 2, sort = 6,groups ={1,2})
		private Date paybackDay;
		// 月还期供金额
		@ExcelField(title = "月还期供金额", type = 0, align = 2, sort = 7,groups ={1,2})
		private BigDecimal paybackMonthAmount;
		// 借款状态
		@ExcelField(title = "借款状态", type = 0, align = 2, sort = 8,groups ={1,2}, dictType = "jk_loan_status")
		private String dictLoanStatus;
		// 期供状态
		@ExcelField(title = "期供状态", type = 0, align = 2, sort = 9,groups ={1,2}, dictType = "jk_period_status")
		private String dictMonthStatus;
		// 还款状态
		@ExcelField(title = "还款状态", type = 0, align = 2, sort = 10,groups ={1,2}, dictType = "jk_repay_status")
		private String dictPayStatus;
		// 蓝补金额
		@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 11,groups ={1,2})
		private BigDecimal paybackBuleAmount;
		// 已还款金额
		@ExcelField(title = "已还款金额", type = 0, align = 2, sort = 12,groups ={1,2})
		private BigDecimal monthsAomuntPaybacked;
		// 标识
		@ExcelField(title = "标识", type = 0, align = 2, sort = 13,groups ={1,2}, dictType = "jk_channel_flag")
		private String loanFlag;
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
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getLoanTeamOrgId() {
			return loanTeamOrgId;
		}
		public void setLoanTeamOrgId(String loanTeamOrgId) {
			this.loanTeamOrgId = loanTeamOrgId;
		}
		public Date getContractEndDay() {
			return contractEndDay;
		}
		public void setContractEndDay(Date contractEndDay) {
			this.contractEndDay = contractEndDay;
		}
		public BigDecimal getContractMonths() {
			return contractMonths;
		}
		public void setContractMonths(BigDecimal contractMonths) {
			this.contractMonths = contractMonths;
		}
		public Date getPaybackDay() {
			return paybackDay;
		}
		public void setPaybackDay(Date paybackDay) {
			this.paybackDay = paybackDay;
		}
		public BigDecimal getPaybackMonthAmount() {
			return paybackMonthAmount;
		}
		public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
			this.paybackMonthAmount = paybackMonthAmount;
		}
		public String getDictLoanStatus() {
			return dictLoanStatus;
		}
		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}
		public String getDictMonthStatus() {
			return dictMonthStatus;
		}
		public void setDictMonthStatus(String dictMonthStatus) {
			this.dictMonthStatus = dictMonthStatus;
		}
		public String getDictPayStatus() {
			return dictPayStatus;
		}
		public void setDictPayStatus(String dictPayStatus) {
			this.dictPayStatus = dictPayStatus;
		}
		public BigDecimal getPaybackBuleAmount() {
			return paybackBuleAmount;
		}
		public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
			this.paybackBuleAmount = paybackBuleAmount;
		}
		public BigDecimal getMonthsAomuntPaybacked() {
			return monthsAomuntPaybacked;
		}
		public void setMonthsAomuntPaybacked(BigDecimal monthsAomuntPaybacked) {
			this.monthsAomuntPaybacked = monthsAomuntPaybacked;
		}
		public String getLoanFlag() {
			return loanFlag;
		}
		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}
		
		
}
