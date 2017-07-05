package com.creditharmony.loan.deduct.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.car.carRefund.service.CarRefundAuditService;


/**
 * 车借服务费退款处理类
 * @Class Name CarServiceChargeReturnHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
@Component
public class CarServiceChargeReturnHandler extends DeductResultHandler {
	
	@Autowired
	private CarRefundAuditService carRefundAuditService;
	
	/**
	 * 车借服务费退款
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void handleBusiness(LoanDeductEntity deduct) {
		carRefundAuditService.refundDealBack(deduct);
	}

}
