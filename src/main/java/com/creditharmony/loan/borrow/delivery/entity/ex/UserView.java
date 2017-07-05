package com.creditharmony.loan.borrow.delivery.entity.ex;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 员工信息
 * @Class Name UserView
 * @author lirui
 * @Create In 2015年12月14日
 */
@SuppressWarnings("serial")
public class UserView extends DataEntity<UserView> {
	private String userCode;// 员工编码
	private String userName;// 员工姓名
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
