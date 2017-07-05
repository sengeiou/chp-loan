package com.creditharmony.loan.deduct.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;


/**
 * 车借催收服务划扣处理类
 * @Class Name CarServiceChargeDeductHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
@Component
public class CarServiceChargeDeductHandler extends DeductResultHandler {
	
	@Autowired
	private CarGrantRecepicService carGrantRecepicService;
	
	/**
	 * 车借催收服务费划扣
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void handleBusiness(LoanDeductEntity deduct) {
		carGrantRecepicService.deductReceic(deduct);
	}

}
