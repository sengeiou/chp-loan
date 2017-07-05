package com.creditharmony.loan.sync.data.remote;

import com.creditharmony.adapter.bean.BaseOutInfo;
import com.creditharmony.core.moneyaccount.entity.MoneyAccountInfo;

/**
 * 富友-金账户接口入口
 * @Class Name FYMoneyAccountService
 * @author 武文涛
 * @Create In 2016年2月28日
 */
public interface FyMoneyAccountService {

	/**
	 * 富友-金账户接口入口
	 * 2016年2月28日
	 * By 武文涛
	 * @param moneyAccountInfo
	 * @return BaseOutInfo
	 */
	public BaseOutInfo chooseInterface(MoneyAccountInfo moneyAccountInfo);
}
