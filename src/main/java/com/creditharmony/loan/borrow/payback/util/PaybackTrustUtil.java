package com.creditharmony.loan.borrow.payback.util;

import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackTrustEx;


/**
 * 集中划扣委托充值导出工具类
 * @Class Name PaybackTrustUtil
 * @author 张永生
 * @Create In 2016年5月20日
 */
public class PaybackTrustUtil {

	/**
	 * 导出委托充值数据时，从一个对象中copy属性 
	 * 2016年3月10日 By 王浩
	 * @param splitTrust
	 * @param paybackDeduct
	 * @return
	 */
	public static PaybackTrustEx copyRechargeProperties(PaybackTrustEx splitTrust,
			PaybackApply paybackDeduct) {
		// 当前应还金额
		splitTrust.setTrustAmount(paybackDeduct.getApplyAmount());
		splitTrust.setCustomerName(paybackDeduct.getLoanCustomer()
				.getCustomerName());
		splitTrust.setTrusteeshipNo(paybackDeduct.getLoanCustomer()
				.getTrusteeshipNo());
		// 合同编号 + "委托充值" + 申请id
		splitTrust.setRemarks(paybackDeduct.getContractCode()
				+ "_" + DeductedConstantEx.PAYBACK_TRUST + "_"
				+ paybackDeduct.getId());
		return splitTrust;
	}
}
