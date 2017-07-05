package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
//导出催收服务费退还
@SuppressWarnings("serial")
public class UrgeBackMoneyEx extends DataEntity<UrgeBackMoneyEx> {
	// 序号
	private String index;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 2,groups ={1,2})
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "收款人名称", type = 0, align = 2, sort = 4,groups ={1,2})
	private String customerName;
	//户籍省
	@ExcelField(title = "收款人所在省", type = 0, align = 2, sort = 6,groups ={1,2})
	private String bankProvince;
	//户籍市
	@ExcelField(title = "收款人所在市", type = 0, align = 2, sort = 7,groups ={1,2})
	private String bankCity;
	// 用户银行账号
	@ExcelField(title = "收款人账号", type = 0, align = 2, sort = 3,groups ={1,2})
	private String bankAccount;
	// 开户行
	@ExcelField(title = "收方开户银行", type = 0, align = 2, sort = 9,groups ={1,2})
	private String bankName;
	//开户支行
	@ExcelField(title = "收方开户支行", type = 0, align = 2, sort = 5,groups ={1,2})
	private String bankBranch;
	// 备注
	@ExcelField(title = "备注", type = 0, align = 2, sort = 20,groups ={1,2})
	private String note;
	// 申请返款金额
	@ExcelField(title = "金额", type = 0, align = 2, sort = 8,groups ={1,2})
	private String paybackBackAmount;
	// 返款结果
	@ExcelField(title = "返款结果", type = 0, align = 2, sort = 10,groups ={2})
	private String dictPayResult;
	// 返款结果
	private String dictPayResultLabel;
	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 11,groups ={2})
	private String remark;
	
	//-------------------------------------------------------------------------------
	// 催收服务费返款ID
		private String id;
		// 证件号码
		private String customerCertNum;
		// 借款状态：结清或者提前结清
		private String dictLoanStatus;
		// 借款产品
		private String productName;
		// 合同金额
		private BigDecimal contractAmount;
		// 放款金额
		private BigDecimal grantAmount;
		// 催收服务费
		private BigDecimal feeUrgedService;
		// 结清日期
		private Date settlementTime;
		// 最长逾期天数
		private int paybackMaxOverduedays;
		// 返款状态
		private String dictPayStatus;
		
		private String dictPayStatusLabel;
		// 返款申请时间
		private Date backApplyPayTime;
		// 返款时间
		private Date backTime;
		// 门店id
		private String[] storesCode;
		// 开户行编码
		private String[] bankNameCode;
		// 门店名称
		private String storesName;
		// 返款申请部门
		private String backApplyDepartmen;
		// 返款办理人
		private String  backTransactor;
		// 返款办理部门
		private String backTransactorTeam;
		// 返款申请人
		private String backApplyBy;
		//合同渠道
		private String channelFlag;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}


		public String getCustomerCertNum() {
			return customerCertNum;
		}

		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}

	

		public String getDictLoanStatus() {
			return dictLoanStatus;
		}

		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}

		public Date getSettlementTime() {
			return settlementTime;
		}

		public void setSettlementTime(Date settlementTime) {
			this.settlementTime = settlementTime;
		}

		public int getPaybackMaxOverduedays() {
			return paybackMaxOverduedays;
		}

		public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
			this.paybackMaxOverduedays = paybackMaxOverduedays;
		}
		public String[] getStoresCode() {
			return storesCode;
		}

		public void setStoresCode(String[] storesCode) {
			this.storesCode = storesCode;
		}

		public String getDictPayStatus() {
			return dictPayStatus;
		}

		public void setDictPayStatus(String dictPayStatus) {
			this.dictPayStatus = dictPayStatus;
		}

	
		public Date getBackApplyPayTime() {
			return backApplyPayTime;
		}

		public void setBackApplyPayTime(Date backApplyPayTime) {
			this.backApplyPayTime = backApplyPayTime;
		}

		public Date getBackTime() {
			return backTime;
		}

		public void setBackTime(Date backTime) {
			this.backTime = backTime;
		}

		public String getBackApplyDepartmen() {
			return backApplyDepartmen;
		}

		public void setBackApplyDepartmen(String backApplyDepartmen) {
			this.backApplyDepartmen = backApplyDepartmen;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}


		public BigDecimal getContractAmount() {
			return contractAmount;
		}

		public void setContractAmount(BigDecimal contractAmount) {
			this.contractAmount = contractAmount;
		}

		public BigDecimal getGrantAmount() {
			return grantAmount;
		}

		public void setGrantAmount(BigDecimal grantAmount) {
			this.grantAmount = grantAmount;
		}

	

		public BigDecimal getFeeUrgedService() {
			return feeUrgedService;
		}

		public void setFeeUrgedService(BigDecimal feeUrgedService) {
			this.feeUrgedService = feeUrgedService;
		}


		public String getBackTransactor() {
			return backTransactor;
		}

		public void setBackTransactor(String backTransactor) {
			this.backTransactor = backTransactor;
		}

		public String getBackTransactorTeam() {
			return backTransactorTeam;
		}

		public void setBackTransactorTeam(String backTransactorTeam) {
			this.backTransactorTeam = backTransactorTeam;
		}

		public String getBackApplyBy() {
			return backApplyBy;
		}

		public void setBackApplyBy(String backApplyBy) {
			this.backApplyBy = backApplyBy;
		}

		


		
	
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getDictPayResult() {
		return dictPayResult;
	}
	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaybackBackAmount() {
		return paybackBackAmount;
	}

	public void setPaybackBackAmount(String paybackBackAmount) {
		this.paybackBackAmount = paybackBackAmount;
	}

	public String getDictPayResultLabel() {
		return dictPayResultLabel;
	}

	public void setDictPayResultLabel(String dictPayResultLabel) {
		this.dictPayResultLabel = dictPayResultLabel;
	}

	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}

	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public String[] getBankNameCode() {
		return bankNameCode;
	}

	public void setBankNameCode(String[] bankNameCode) {
		this.bankNameCode = bankNameCode;
	}

	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	
}
