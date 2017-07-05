package com.creditharmony.loan.borrow.trusteeship.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * @Class Name GrantExcel4
 * @Create In 2016年3月11日
 */
public class GrantExcel4 {
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = 1)
	private String index;
	@ExcelField(title = "委托提现目标登录名", type = 0, align = 2, sort = 1, groups = 1)
	private String loginName;
	@ExcelField(title = "委托提现目标中文名称", type = 0, align = 2, sort = 1, groups = 1)
	private String chianName;
	@ExcelField(title = "委托提现金额", type = 0, align = 2, sort = 1, groups = 1)
	private String money;
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 1, groups = 1)
	private String mark;

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

	public String getChianName() {
		return chianName;
	}

	public void setChianName(String chianName) {
		this.chianName = chianName;
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
}
