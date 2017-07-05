package com.creditharmony.loan.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/** 
 * 邮件历史
* @author 作者 E-mail: 
* @version 创建时间：2017年3月8日 上午10:38:05 
* 类说明 
*/
@SuppressWarnings("serial")
public class EmailOpe extends DataEntity<EmailOpe>{
		// 关联ID(还款主表)
		private String emailId;
		//操作步骤
		private String operateStep;
		// 操作结果
		private String operateResult;
		// 操作人
		private String operator;
		// 操作人code
		private String operateCode;
		// 操作人时间
		private Date operateTime;
		//备注
		private String remark;
		
		public String getEmailId() {
			return emailId;
		}
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		public String getOperateResult() {
			return operateResult;
		}
		public void setOperateResult(String operateResult) {
			this.operateResult = operateResult;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public String getOperateCode() {
			return operateCode;
		}
		public void setOperateCode(String operateCode) {
			this.operateCode = operateCode;
		}
		public Date getOperateTime() {
			return operateTime;
		}
		public void setOperateTime(Date operateTime) {
			this.operateTime = operateTime;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getOperateStep() {
			return operateStep;
		}
		public void setOperateStep(String operateStep) {
			this.operateStep = operateStep;
		}
		
		

}
