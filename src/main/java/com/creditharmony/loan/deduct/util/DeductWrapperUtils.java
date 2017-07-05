package com.creditharmony.loan.deduct.util;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;

/**
 * 划扣相关对象包装工具类
 * @Class Name DeductUtils
 * @author 张永生
 * @Create In 2016年5月20日
 */
public class DeductWrapperUtils {
	
	/** 操作人 **/
	private static final String OPERATOR = "FinanceBatch";

	/**
	 * 包装还款历史对象
	 * 2016年5月20日
	 * By 张永生
	 * @param loanDeduct
	 * @return
	 */
	public static PaybackOpe wrapperPaybackOpe(LoanDeductEntity loanDeduct) {
		PaybackOpe paybackOperate = new PaybackOpe();
		paybackOperate.setrPaybackApplyId(loanDeduct.getBatId());
		paybackOperate.setrPaybackId(loanDeduct.getBusinessId());
		paybackOperate.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
		paybackOperate.setDictRDeductType(loanDeduct.getDeductSysIdType());
		if (loanDeduct.getDeductSucceedMoney().equals(loanDeduct.getApplyAmount())) {
			paybackOperate.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			BigDecimal deductedMoney = new BigDecimal(loanDeduct.getDeductSucceedMoney());
			paybackOperate.setRemarks("划扣成功" + ":" + deductedMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			paybackOperate.setRemarks("划扣失败");
			paybackOperate.setOperateResult(PaybackOperate.DEDECT_FAILED.getCode());
		}
		paybackOperate.preInsert();
		paybackOperate.setOperator(OPERATOR);
		paybackOperate.setOperateCode(OPERATOR);
		paybackOperate.setOperateTime(new Date());
		return paybackOperate;
	}
	
}
