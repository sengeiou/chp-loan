
package com.creditharmony.loan.borrow.grant.entity.ex;


import com.creditharmony.core.excel.annotation.ExcelField;
/**
 * 资金托管放款审核导出
 * @Class Name GrantAuditTGEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class GrantAuditTGEx {
	// applyId
	private String applyId;
	// 放款id,
	private String id;
	// 借款编号
	private String loanCode;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1, groups = 1)
	private String contractCode; 
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10, groups = 1)
	private String  customerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 10, groups = 1)
	private String storeName;
	// 产品名称
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 40, groups = 1)
	private String replyProductName;
	// 证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 20, groups = 1)
	private String identityCode;
	// 期数
	@ExcelField(title = "批复期限", type = 0, align = 2, sort = 70, groups = 1)
	private Integer replyMonth;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 50, groups = 1)
	private Double contractMoney;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 60, groups = 1)
	private Double lendingMoney;
	// 标识，从字典表中取值
	@ExcelField(title = "标识", type = 0, align = 2, sort = 110, groups = 1)
	private String channelName;
	// 是否电销，从字典表中取值
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 120, groups = 1,dictType="jk_telemarketing")
	private String telesalesFlag;
	// 是否加急
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 130,groups = 1,dictType="jk_urgent_flag")
	private String urgentFlag;
	// 借款类型，需要从字典表中取值
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 30, groups = 1,dictType="jk_loan_type")
	private String classType;
	// 放款账户，银行卡号
	@ExcelField(title = "放款账号", type = 0, align = 2, sort = 80, groups = 1)
	private String lendingAccount;
	
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getReplyProductName() {
		return replyProductName;
	}
	public void setReplyProductName(String replyProductName) {
		this.replyProductName = replyProductName;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public Integer getReplyMonth() {
		return replyMonth;
	}
	public void setReplyMonth(Integer replyMonth) {
		this.replyMonth = replyMonth;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public Double getLendingMoney() {
		return lendingMoney;
	}
	public void setLendingMoney(Double lendingMoney) {
		this.lendingMoney = lendingMoney;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getTelesalesFlag() {
		return telesalesFlag;
	}
	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getLendingAccount() {
		return lendingAccount;
	}
	public void setLendingAccount(String lendingAccount) {
		this.lendingAccount = lendingAccount;
	}
}