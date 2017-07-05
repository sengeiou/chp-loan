package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

public class GrantExcel3 {

	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = 1)
	private String index;
	@ExcelField(title = "解冻目标登录名", type = 0, align = 2, sort = 1, groups = 1)
	private String receiveLoginName;
	@ExcelField(title = "解冻目标中文名称", type = 0, align = 2, sort = 1, groups = 1)
	private String receiveChinaName;
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 1, groups = 1)
	private String tradeMoney;
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 1, groups = 1)
	private String mark;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

}
