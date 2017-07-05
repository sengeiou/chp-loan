package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * @Class Name GrantExcel5
 * @Create In 2016年3月11日
 */
public class GrantExcel5 {
	@ExcelField(title = "序号", type = 2, groups = 1)
	private String index;
	@ExcelField(title = "委托提现目标登录名", type = 2, groups = 1)
	private String loginName;
	@ExcelField(title = "委托提现目标中文名称", type = 2, groups = 1)
	private String chinaName;
	@ExcelField(title = "委托提现金额", type = 2, groups = 1)
	private String money;
	@ExcelField(title = "备注信息", type = 2, groups = 1)
	private String mark;
	@ExcelField(title = "返回码", type = 2, groups = 1)
	private String returnCode;
	@ExcelField(title = "返回描述", type = 2, groups = 1)
	private String returnMsg;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
