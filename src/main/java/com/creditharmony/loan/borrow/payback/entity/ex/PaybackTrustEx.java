package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 集中划扣委托充值导出列表
 * @Class Name PaybackSplitTrustEx
 * @author 王浩
 * @Create In 2016年3月10日
 */
@SuppressWarnings("serial")
public class PaybackTrustEx extends DataEntity<PaybackTrustEx>{
		// 开户行
		@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
		private String seq;
		// 登录名
		@ExcelField(title = "委托充值目标登录名", type = 0, align = 2, sort = 20)
		private String trusteeshipNo;
		// 户名
		@ExcelField(title = "委托充值目标中文名称", type = 0, align = 2, sort = 30)
		private String  customerName;
		// 金额(单位:元)
		@ExcelField(title = "委托充值金额", type = 0, align = 2, sort = 40)
		private BigDecimal trustAmount;
		// 备注
		@ExcelField(title = "备注信息", type = 0, align = 2, sort = 60)
		private String remarks;		

		public String getSeq() {
			return seq;
		}
		public void setSeq(String seq) {
			this.seq = seq;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
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
		public String getTrusteeshipNo() {
			return trusteeshipNo;
		}
		public void setTrusteeshipNo(String trusteeshipNo) {
			this.trusteeshipNo = trusteeshipNo;
		}	
		
}
