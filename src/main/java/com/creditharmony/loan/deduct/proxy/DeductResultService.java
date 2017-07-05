package com.creditharmony.loan.deduct.proxy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.deduct.handler.DeductResultHandler;
import com.creditharmony.loan.deduct.handler.DeductResultHandlerProvider;

/**
 * 划扣结果服务类
 * @Class Name DeductResultService
 * @author 张永生
 * @Create In 2016年5月20日
 */
@Component
public class DeductResultService {

	private static final Logger logger = LoggerFactory.getLogger(DeductResultService.class);
	
	@Autowired
	private DeductUpdateDao deductUpdateDao;
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveDeductResult(LoanDeductEntity deduct){
		// 集中/非集中划扣回盘（更新主表和申请表，此处不冲抵）
		String deductWay = deduct.getDeductSysIdType();
		try {
			deduct.setId(IdGen.uuid());
			deductUpdateDao.insert(deduct);
			// 拆分记录
			for(PaybackSplitEntityEx paybackSplit : deduct.getSplitData()){
				if (ResultType.POXY_SUCCESS.getCode().equals(paybackSplit.getSplitBackResult())) {
					paybackSplit.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
				} else {
					paybackSplit.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					//TODO 需要重构
					deduct.setFailReason(paybackSplit.getSplitFailResult());
				}
			}
			DeductResultHandler deductResultHandler = DeductResultHandlerProvider.getHandler(deductWay);
			deductResultHandler.handleBusiness(deduct);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("回盘结果更新异常,请求ID:"
					+ deduct.getRequestId()
					+ "\r\n 业务类型:"
					+ DeductWays.parseByCode(deductWay).getName());
			throw new ServiceException(getExceptionMessage(deduct.getRequestId(), deductWay));
		}
	}

	public String getExceptionMessage(String requestId, String deductWay) {
		return "回盘结果更新异常,请求ID:" + requestId
				+ ",业务类型:" + DeductWays.parseByCode(deductWay).getName();
	}
	
}
