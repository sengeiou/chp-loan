
package com.creditharmony.loan.channel.jyj.entity;


import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 待放款列表中的待放款列表导出，关于用户,type=2，仅导入
 * @Class Name GrantEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class JYJGrantBFEx extends DataEntity<JYJGrantBFEx> {
	private static final long serialVersionUID = 7783081129793432292L;
	// 流程id
	private String applyId;
	// applyIds
	private String[] applyIds;
	// 放款id,
	private String id;
	// 借款编号
	private String loanCode;
	// 合同编号
	private String contractCode; 
	// 客户姓名
	@ExcelField(title = "收款方银行账号 ", type = 0, align = 2, sort = 30)
	private String bankAccount;	
	// 银行账号
	@ExcelField(title = "收款方姓名", type = 0, align = 2, sort = 20)
	private String  customerName;
	// 金额
	@ExcelField(title = "金额", type = 0, align = 2, sort = 80)
	private String grantAmount;
	// 尾次放款金额
	private String lastGrantAmount;
	// 首次放款金额
	private String firstGrantAmount;
	// 开户行6
	private String  bankName;
	// 备注列5
	@ExcelField(title = "商户备注", type = 0, align = 2, sort = 90)
	private String remark;
	// 具体支行7
	private String  bankBranch;	
	// 收款直省8
	@ExcelField(title = "开户行所在省", type = 0, align = 2, sort = 40)
	private String  bankProvince;
	// 收款市9
	@ExcelField(title = "开户行所在市", type = 0, align = 2, sort = 50)
	private String  bankCity;	
	// 开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 60)
	private String openBankName;
	// 收款银行名称
	@ExcelField(title = "收款方银行名称", type = 0, align = 2, sort = 70)
	private String userBankName;
	// 商户订单号
	@ExcelField(title = "商户订单号", type = 0, align = 2, sort = 80)
	private String orderId;
    // 回执结果
    private String grantRecepitResult;
	
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
	
	public String getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
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
	
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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
	public String getLastGrantAmount() {
		return lastGrantAmount;
	}
	public void setLastGrantAmount(String lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}
	public String getGrantRecepitResult() {
		return grantRecepitResult;
	}
	public void setGrantRecepitResult(String grantRecepitResult) {
		this.grantRecepitResult = grantRecepitResult;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public String getUserBankName() {
		return userBankName;
	}
	public void setUserBankName(String userBankName) {
		this.userBankName = userBankName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFirstGrantAmount() {
		return firstGrantAmount;
	}
	public void setFirstGrantAmount(String firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}
	public String[] getApplyIds() {
		return applyIds;
	}
	public void setApplyIds(String[] applyIds) {
		this.applyIds = applyIds;
	}
}