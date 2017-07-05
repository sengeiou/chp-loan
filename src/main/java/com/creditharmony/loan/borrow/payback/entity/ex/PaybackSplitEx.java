package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class PaybackSplitEx extends DataEntity<PaybackSplitEx>{
	// id
		private String id;
		// 关联还款申请表id
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
		private String contractCode;
		@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
		private String customerName;
		@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
		private String orgName;
		@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 40)
		private String applyBankName;
		@ExcelField(title = " 批借期数 ", type = 0, align = 2, sort = 50)
		private String contractMonths;
		@ExcelField(title = "首期还款日 ", type = 0, align = 2, sort = 60)
		private Date contractReplayDay;
		@ExcelField(title = "实还金额 ", type = 0, align = 2, sort = 70)
		private BigDecimal splitAmounts;
		@ExcelField(title = "期供 ", type = 0, align = 2, sort = 80)
		private BigDecimal paybackMonthAmount;
		@ExcelField(title = " 当期未还期供 ", type = 0, align = 2, sort = 90)
		private BigDecimal notPaybackMonthAmount;
		@ExcelField(title = "当期已还期供 ", type = 0, align = 2, sort = 100)
		private BigDecimal yesPaybackMonthAmount;
		@ExcelField(title = "划扣金额 ", type = 0, align = 2, sort = 110)
		private BigDecimal splitAmount;
		@ExcelField(title = "还款类型 ", type = 0, align = 2, sort = 120)
		private String  type;
		@ExcelField(title = "还款状态", type = 0, align = 2, sort = 130)
		private String dictPayStatus;
		@ExcelField(title = "划扣日期", type = 0, align = 2, sort = 140)
		private Date splitBackDate;
		@ExcelField(title = "还款日", type = 0, align = 2, sort = 150)
		private Date monthPayDay;
		@ExcelField(title = "借款状态", type = 0, align = 2, sort = 160)
		private String dictLoanStatus;
		// 回盘结果(0:待划扣，1:划扣失败、2:划扣成功、3处理中)
		@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 170)
		private String splitBackResult;
		// 是否批量：0是 、 1否
		@ExcelField(title = "划扣方式", type = 0, align = 2, sort = 180)
		private String batchFlag;
		// 失败原因
		@ExcelField(title = "失败原因", type = 0, align = 2, sort = 190)
		private String splitFailResult;
		// 标识
		@ExcelField(title = "标识", type = 0, align = 2, sort = 200)
		private String loanMark;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public String getApplyBankName() {
			return applyBankName;
		}
		public void setApplyBankName(String applyBankName) {
			this.applyBankName = applyBankName;
		}
		public String getContractMonths() {
			return contractMonths;
		}
		public void setContractMonths(String contractMonths) {
			this.contractMonths = contractMonths;
		}
		public Date getContractReplayDay() {
			return contractReplayDay;
		}
		public void setContractReplayDay(Date contractReplayDay) {
			this.contractReplayDay = contractReplayDay;
		}
		public BigDecimal getSplitAmounts() {
			return splitAmounts;
		}
		public void setSplitAmounts(BigDecimal splitAmounts) {
			this.splitAmounts = splitAmounts;
		}
		public BigDecimal getPaybackMonthAmount() {
			return paybackMonthAmount;
		}
		public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
			this.paybackMonthAmount = paybackMonthAmount;
		}
		public BigDecimal getNotPaybackMonthAmount() {
			return notPaybackMonthAmount;
		}
		public void setNotPaybackMonthAmount(BigDecimal notPaybackMonthAmount) {
			this.notPaybackMonthAmount = notPaybackMonthAmount;
		}
		public BigDecimal getYesPaybackMonthAmount() {
			return yesPaybackMonthAmount;
		}
		public void setYesPaybackMonthAmount(BigDecimal yesPaybackMonthAmount) {
			this.yesPaybackMonthAmount = yesPaybackMonthAmount;
		}
		public BigDecimal getSplitAmount() {
			return splitAmount;
		}
		public void setSplitAmount(BigDecimal splitAmount) {
			this.splitAmount = splitAmount;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDictPayStatus() {
			return dictPayStatus;
		}
		public void setDictPayStatus(String dictPayStatus) {
			this.dictPayStatus = dictPayStatus;
		}
		public Date getSplitBackDate() {
			return splitBackDate;
		}
		public void setSplitBackDate(Date splitBackDate) {
			this.splitBackDate = splitBackDate;
		}
		public Date getMonthPayDay() {
			return monthPayDay;
		}
		public void setMonthPayDay(Date monthPayDay) {
			this.monthPayDay = monthPayDay;
		}
		public String getDictLoanStatus() {
			return dictLoanStatus;
		}
		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}
		public String getSplitBackResult() {
			return splitBackResult;
		}
		public void setSplitBackResult(String splitBackResult) {
			this.splitBackResult = splitBackResult;
		}
		public String getBatchFlag() {
			return batchFlag;
		}
		public void setBatchFlag(String batchFlag) {
			this.batchFlag = batchFlag;
		}
		public String getSplitFailResult() {
			return splitFailResult;
		}
		public void setSplitFailResult(String splitFailResult) {
			this.splitFailResult = splitFailResult;
		}
		public String getLoanMark() {
			return loanMark;
		}
		public void setLoanMark(String loanMark) {
			this.loanMark = loanMark;
		}
}
