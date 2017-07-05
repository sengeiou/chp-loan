package com.creditharmony.loan.car.common.service.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;
import com.creditharmony.loan.car.carRefund.service.CarRefundAuditService;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.service.CarLoanFinanceUpdateService;

/**
 * 车借划扣使用服务
 * @Class Name DeductUpdateService
 * @author 李虎城
 * @Create In 2016年12月12日
 */
@Service
public class CarLoanFinanceUpdateServiceImpl implements CarLoanFinanceUpdateService {

	protected Logger logger = LoggerFactory
			.getLogger(CarLoanFinanceUpdateServiceImpl.class);

	@Autowired
	private CarGrantRecepicService carGrantRecepicService;
	@Autowired
	private CarRefundAuditService carRefundAuditService;
//	@Autowired
//	private CarDeductReqUpdateService carDeductReqUpdateService;

	/**
	 * 集中划扣更新 2016年2月2日 By 王彬彬
	 * 
	 * @param deductList
	 *            划扣返回结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplit(List<LoanDeductEntity> deductList) {
		// 划扣回盘
		Iterator<LoanDeductEntity> iterator = deductList.iterator();
		// 集中/非集中划扣回盘（更新主表和申请表，此处不冲抵）
		while (iterator.hasNext()) {
			LoanDeductEntity iteratorSplit = iterator.next();
			try {
				
				/**
				 * 车借还款划扣结果处理
				 */
				/*if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJHK_09.getCode())
						|| iteratorSplit.getDeductSysIdType().equals(
								CarDeductWays.CJHK_10.getCode())) {
					if (ArrayHelper.isNotEmpty(iteratorSplit.getSplitData())) {
						carDeductReqUpdateService.paybackReceic(iteratorSplit);
					}
				}*/
				
				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_01.getCode())) {
					// 车借放款
					carGrantRecepicService.grantReceic(
							iteratorSplit.getBusinessId(),
							iteratorSplit.getDeductFailMoney());
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_02.getCode())) {
					// 车借催收服务费划扣
					carGrantRecepicService.deductReceic(iteratorSplit);
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_03.getCode())) {
					// 车借服务费退款
					carRefundAuditService.refundDealBack(iteratorSplit);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("回盘结果更新一场：请求ID"
						+ iteratorSplit.getRequestId()
						+ "\r\n 业务类型"
						+ CarDeductWays.parseByCode(
								iteratorSplit.getDeductSysIdType()).getName());
			}
		}
	}

}
