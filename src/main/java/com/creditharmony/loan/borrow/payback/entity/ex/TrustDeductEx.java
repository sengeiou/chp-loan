package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 线上划拨导出
 * @Class Name TrustDeductEx
 * @author 王浩
 * @Create In 2016年3月12日
 */
@SuppressWarnings("serial")
public class TrustDeductEx extends DataEntity<TrustDeductEx>{
		// 开户行
		@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
		private String seq;
		// 登录名
		@ExcelField(title = "付款方登录名", type = 0, align = 2, sort = 20)
		private String trusteeshipNo;
		// 户名
		@ExcelField(title = "付款方中文名称", type = 0, align = 2, sort = 30)
		private String  customerName;
		@ExcelField(title = "付款资金来自冻结", type = 0, align = 2, sort = 40)
		private String  fundFromFrozen;
		@ExcelField(title = "收款方登录名", type = 0, align = 2, sort = 50)
		private String  payeeLoginName;
		@ExcelField(title = "收款方中文名称", type = 0, align = 2, sort = 60)
		private String  payeeName;
		@ExcelField(title = "收款后立即冻结", type = 0, align = 2, sort = 70)
		private String  fundFrozen;		
		// 金额(单位:元)
		@ExcelField(title = "交易金额", type = 0, align = 2, sort = 80)
		private BigDecimal trustAmount;
		@ExcelField(title = "备注信息", type = 0, align = 2, sort = 90)
		private String remarks;		
		@ExcelField(title = "预授权合同号", type = 0, align = 2, sort = 100)
		private String  preAuthContractCode;
		
		public String getSeq() {
			return seq;
		}
		public void setSeq(String seq) {
			this.seq = seq;
		}
		public String getTrusteeshipNo() {
			return trusteeshipNo;
		}
		public void setTrusteeshipNo(String trusteeshipNo) {
			this.trusteeshipNo = trusteeshipNo;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getFundFromFrozen() {
			return fundFromFrozen;
		}
		public void setFundFromFrozen(String fundFromFrozen) {
			this.fundFromFrozen = fundFromFrozen;
		}
		public String getPayeeLoginName() {
			return payeeLoginName;
		}
		public void setPayeeLoginName(String payeeLoginName) {
			this.payeeLoginName = payeeLoginName;
		}
		public String getPayeeName() {
			return payeeName;
		}
		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}
		public String getFundFrozen() {
			return fundFrozen;
		}
		public void setFundFrozen(String fundFrozen) {
			this.fundFrozen = fundFrozen;
		}
		public BigDecimal getTrustAmount() {
			return trustAmount;
		}
		public void setTrustAmount(BigDecimal trustAmount) {
			this.trustAmount = trustAmount;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getPreAuthContractCode() {
			return preAuthContractCode;
		}
		public void setPreAuthContractCode(String preAuthContractCode) {
			this.preAuthContractCode = preAuthContractCode;
		}
		
}
