package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

public class GrantExcel1 {

	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = 1)
	private String index;
	@ExcelField(title = "付款方登录名", type = 0, align = 2, sort = 1, groups = 1)
	private String payLoginName;
	@ExcelField(title = "付款方中文名称", type = 0, align = 2, sort = 1, groups = 1)
	private String payChinaName;
	@ExcelField(title = "付款资金来自冻结", type = 0, align = 2, sort = 1, groups = 1)
	private String payFundFreeze;
	@ExcelField(title = "收款方登录名", type = 0, align = 2, sort = 1, groups = 1)
	private String receiveLoginName;
	@ExcelField(title = "收款方中文名称", type = 0, align = 2, sort = 1, groups = 1)
	private String receiveChinaName;
	@ExcelField(title = "收款后立即冻结", type = 0, align = 2, sort = 1, groups = 1)
	private String receiveFundFreeze;
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 1, groups = 1)
	private String tradeMoney;
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 1, groups = 1)
	private String mark;
	@ExcelField(title = "预授权合同号", type = 0, align = 2, sort = 1, groups = 1)
	private String preAuthorizationContractNum;
	//@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 1, groups = 1)
	private String contractVersion;
	//@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 1, groups = 1)
	private String urgedServiceFee;	
	private String grantMoney;
	private String applyId;
	private String contractCode;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getPayLoginName() {
		return payLoginName;
	}

	public void setPayLoginName(String payLoginName) {
		this.payLoginName = payLoginName;
	}

	public String getPayChinaName() {
		return payChinaName;
	}

	public void setPayChinaName(String payChinaName) {
		this.payChinaName = payChinaName;
	}

	public String getPayFundFreeze() {
		return payFundFreeze;
	}

	public void setPayFundFreeze(String payFundFreeze) {
		this.payFundFreeze = payFundFreeze;
	}

	public String getReceiveLoginName() {
		return receiveLoginName;
	}

	public void setReceiveLoginName(String receiveLoginName) {
		this.receiveLoginName = receiveLoginName;
	}

	public String getReceiveChinaName() {
		return receiveChinaName;
	}

	public void setReceiveChinaName(String receiveChinaName) {
		this.receiveChinaName = receiveChinaName;
	}

	public String getReceiveFundFreeze() {
		return receiveFundFreeze;
	}

	public void setReceiveFundFreeze(String receiveFundFreeze) {
		this.receiveFundFreeze = receiveFundFreeze;
	}

	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getPreAuthorizationContractNum() {
		return preAuthorizationContractNum;
	}

	public void setPreAuthorizationContractNum(
			String preAuthorizationContractNum) {
		this.preAuthorizationContractNum = preAuthorizationContractNum;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getUrgedServiceFee() {
		return urgedServiceFee;
	}

	public void setUrgedServiceFee(String urgedServiceFee) {
		this.urgedServiceFee = urgedServiceFee;
	}

	public String getGrantMoney() {
		return grantMoney;
	}

	public void setGrantMoney(String grantMoney) {
		this.grantMoney = grantMoney;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
}
