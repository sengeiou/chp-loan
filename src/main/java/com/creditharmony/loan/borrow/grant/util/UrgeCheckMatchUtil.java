package com.creditharmony.loan.borrow.grant.util;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;

/**
 * 催收查账匹配工具类
 * @Class Name UrgeCheckMatchUtil
 * @author 张永生
 * @Create In 2016年4月26日
 */
public class UrgeCheckMatchUtil {


	/**
	 * 是否可以匹配
	 * 2016年4月26日
	 * By 张永生
	 * @param info
	 * @param out
	 * @return
	 */
	public static boolean isCanMatch(PaybackTransferInfo info,
			PaybackTransferOut out) {
		return info.getReallyAmount() != null
				&& info.getTranDepositTime() != null
				&& !StringUtils.isEmpty(info.getStoresInAccount())
				&& out.getOutReallyAmount() != null
				&& out.getOutDepositTime() != null
				&& !StringUtils.isEmpty(out.getOutEnterBankAccount());
	}

	/**
	 * 是否匹配成功
	 * 2016年4月26日
	 * By 张永生
	 * @param info
	 * @param out
	 * @return
	 */
	public static boolean isMatchSuccess(PaybackTransferInfo info,
			PaybackTransferOut out) {
		return info.getReallyAmount().compareTo(out.getOutReallyAmount()) == 0
				&& info.getTranDepositTime().getTime() == out.getOutDepositTime().getTime()
				&& StringUtils.equals(info.getStoresInAccount(),out.getOutEnterBankAccount())
				&& StringUtils.equals(info.getDepositName(),out.getOutDepositName());
	}
	
	/**
	 * 是否匹配失败
	 * 2016年4月26日
	 * By 张永生
	 * @param info
	 * @param out
	 * @return
	 */
	public static boolean isMatchFail(PaybackTransferInfo info,
			PaybackTransferOut out) {
		return info.getReallyAmount().compareTo(
				out.getOutReallyAmount()) == 0
				&& info.getTranDepositTime().getTime() == out.getOutDepositTime().getTime()
				&& StringUtils.equals(info.getStoresInAccount(),out.getOutEnterBankAccount())
				&& !StringUtils.equals(info.getDepositName(),out.getOutDepositName());
	}

	
}
