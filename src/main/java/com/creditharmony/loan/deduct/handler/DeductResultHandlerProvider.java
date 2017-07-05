package com.creditharmony.loan.deduct.handler;

import com.creditharmony.common.util.SpringContextHolder;

/**
 * 划扣结果处理者提供类
 * @Class Name DeductResultHandlerProvider
 * @author 张永生
 * @Create In 2016年5月21日
 */
public class DeductResultHandlerProvider {

	/**
	 * 获取划扣结果处理者
	 * 2016年5月21日
	 * By 张永生
	 * @param deductWay
	 * @return
	 */
	public static DeductResultHandler getHandler(String deductWay){
		DeductResultHandler handler =  null;
		Integer caseIndex = DeductWayMapping.getCaseIndex(deductWay);
		switch(caseIndex){
		   case 301:
			   handler = getHandler(CentralizedPaymentHandler.class);
			   break;
		   case 302:
			   handler = getHandler(UnCentralizedPaymentHandler.class);
			   break;
		   case 303:
			   handler = getHandler(ReleaseMoneyHandler.class);
			   break;
		   case 304:
			   handler = getHandler(ServiceChargeCollectHandler.class);
			   break;
		   case 305:
			   handler = getHandler(CarReleaseMoneyHandler.class);
			   break;
		   case 306:
			   handler = getHandler(CarServiceChargeDeductHandler.class);
			   break;
		   case 307:
			   handler = getHandler(CarServiceChargeReturnHandler.class);
			   break;
		}
		return handler;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DeductResultHandler getHandler(Class clazz) {
		return null;
		//return SpringContextHolder.getBean(clazz);
	}
}
