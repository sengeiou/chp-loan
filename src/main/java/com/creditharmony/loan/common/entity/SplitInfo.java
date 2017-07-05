package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
/**
 * 拆分实体
 * 
 * @Class Name SplitInfo
 * @author 翁私
 * @Create In 2016年1月21日
 */
public class SplitInfo {
	
	    private String id;
	    // 划扣类型(0：集中划扣 1：否)
	 	private String dictDeductType;
	    // 拆分父id
	 	private String splitFatherId;
	    // 借款编码
	 	private String loanCode;
	    // 合同编号
	 	private String contractCode;	
	    // 申请金额
	 	private BigDecimal applyAmount;
	    // 开户行名称
	 	private String applyBankName;
	 	// 划扣平台
	 	private String dictDealType;
	 	
	 	
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDictDeductType() {
			return dictDeductType;
		}
		public void setDictDeductType(String dictDeductType) {
			this.dictDeductType = dictDeductType;
		}
		public String getSplitFatherId() {
			return splitFatherId;
		}
		public void setSplitFatherId(String splitFatherId) {
			this.splitFatherId = splitFatherId;
		}
		public String getLoanCode() {
			return loanCode;
		}
		public void setLoanCode(String loanCode) {
			this.loanCode = loanCode;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public BigDecimal getApplyAmount() {
			return applyAmount;
		}
		public void setApplyAmount(BigDecimal applyAmount) {
			this.applyAmount = applyAmount;
		}
		public String getApplyBankName() {
			return applyBankName;
		}
		public void setApplyBankName(String applyBankName) {
			this.applyBankName = applyBankName;
		}
		public String getDictDealType() {
			return dictDealType;
		}
		public void setDictDealType(String dictDealType) {
			this.dictDealType = dictDealType;
		}
		
}
