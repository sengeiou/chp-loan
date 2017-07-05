package com.creditharmony.loan.car.carRefund.ex;


import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 退款待办节点中的导出列表显示的字段
 * @Class Name RefundAuditEx
 * @author 蒋力
 * @Create In 2016年3月1日
 */
public class RefundAuditEx extends DataEntity<RefundAuditEx> {
		/**
	 * 
	 */
	private static final long serialVersionUID = -2327691276081972516L;
		@ExcelField(title = "序号", type = 0, align = 2, sort = 0, groups = 1)
		private String id;
		//关联催收服务费信息表ID
		private String rChargeId;
		//退回金额
		private Double returnAmount;
		//退回状态
		private String returnStatus;
		//退回时间
		private Date returnTime;
		//退回中间人ID
		private String returnIntermediaryId;
		// 合同编号
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1, groups = 1)
		private String contractCode;	
		@ExcelField(title = "门店名称", type = 0, align = 2, sort = 5, groups = 1)
		private String storeName;
		// 客户姓名
		@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10, groups = 1)
		private String loanCustomerName;
		@ExcelField(title = "借款产品", type = 0, align = 2, sort = 20, groups = 1)
		private String productType;
		@ExcelField(title = "合同金额", type = 0, align = 2, sort = 30, groups = 1)
		private String contractAmount;
		@ExcelField(title = "应划扣金额", type = 0, align = 2, sort = 40, groups = 1)
		private String urgeMoeny;
		@ExcelField(title = "实划扣金额", type = 0, align = 2, sort = 50, groups = 1)
		private String urgeDecuteMoeny;
		@ExcelField(title = "批借期限(天)", type = 0, align = 2, sort = 60, groups = 1)
		private String contractMonths;
		@ExcelField(title = "开户行", type = 0, align = 2, sort = 70, groups = 1)
		private String cardBank;
		@ExcelField(title = "借款状态", type = 0, align = 2, sort = 80, groups = 1)
		private String dictLoanStatus;
		//审批状态
		@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 90, groups = 1)
		private String auditStatus;
		//审核拒绝原因
		@ExcelField(title = "回盘原因", type = 0, align = 2, sort = 100, groups = 1)
		private String auditRefuseReason;
		@ExcelField(title = "是否电销", type = 0, align = 2, sort = 110, groups = 1)
		private String customerTelesalesFlag;
		private String applyId;
		private String loanCode;//借款编码
		private String bankCardNo;//客户卡号
		
		private String requireStatus;//条件要求状态
		
		public String getRequireStatus() {
			return requireStatus;
		}
		public void setRequireStatus(String requireStatus) {
			this.requireStatus = requireStatus;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getrChargeId() {
			return rChargeId;
		}
		public void setrChargeId(String rChargeId) {
			this.rChargeId = rChargeId;
		}
		public Double getReturnAmount() {
			return returnAmount;
		}
		public void setReturnAmount(Double returnAmount) {
			this.returnAmount = returnAmount;
		}
		public String getReturnStatus() {
			return returnStatus;
		}
		public void setReturnStatus(String returnStatus) {
			this.returnStatus = returnStatus;
		}
		public Date getReturnTime() {
			return returnTime;
		}
		public void setReturnTime(Date returnTime) {
			this.returnTime = returnTime;
		}
		public String getReturnIntermediaryId() {
			return returnIntermediaryId;
		}
		public void setReturnIntermediaryId(String returnIntermediaryId) {
			this.returnIntermediaryId = returnIntermediaryId;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getAuditStatus() {
			return auditStatus;
		}
		public void setAuditStatus(String auditStatus) {
			this.auditStatus = auditStatus;
		}
		public String getAuditRefuseReason() {
			return auditRefuseReason;
		}
		public void setAuditRefuseReason(String auditRefuseReason) {
			this.auditRefuseReason = auditRefuseReason;
		}
		public String getStoreName() {
			return storeName;
		}
		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
		public String getLoanCustomerName() {
			return loanCustomerName;
		}
		public void setLoanCustomerName(String loanCustomerName) {
			this.loanCustomerName = loanCustomerName;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
		public String getContractAmount() {
			return contractAmount;
		}
		public void setContractAmount(String contractAmount) {
			this.contractAmount = contractAmount;
		}
		public String getUrgeMoeny() {
			return urgeMoeny;
		}
		public void setUrgeMoeny(String urgeMoeny) {
			this.urgeMoeny = urgeMoeny;
		}
		public String getUrgeDecuteMoeny() {
			return urgeDecuteMoeny;
		}
		public void setUrgeDecuteMoeny(String urgeDecuteMoeny) {
			this.urgeDecuteMoeny = urgeDecuteMoeny;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getContractMonths() {
			return contractMonths;
		}
		public void setContractMonths(String contractMonths) {
			this.contractMonths = contractMonths;
		}
		public String getCardBank() {
			return cardBank;
		}
		public void setCardBank(String cardBank) {
			this.cardBank = cardBank;
		}
		public String getDictLoanStatus() {
			return dictLoanStatus;
		}
		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}
		public String getCustomerTelesalesFlag() {
			return customerTelesalesFlag;
		}
		public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
			this.customerTelesalesFlag = customerTelesalesFlag;
		}
		public String getApplyId() {
			return applyId;
		}
		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}
		public String getLoanCode() {
			return loanCode;
		}
		public void setLoanCode(String loanCode) {
			this.loanCode = loanCode;
		}
		public String getBankCardNo() {
			return bankCardNo;
		}
		public void setBankCardNo(String bankCardNo) {
			this.bankCardNo = bankCardNo;
		}
		
		
}