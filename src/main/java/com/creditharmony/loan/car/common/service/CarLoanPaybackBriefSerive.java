package com.creditharmony.loan.car.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carContract.ex.CarLoanPaybackBrief;
import com.creditharmony.loan.car.common.dao.CarLoanPaybackBriefDao;


/**
 * 还款明细Service 
 * @Class Name CarLoanPayBackDetailSerive
 * @create In 2016年6月1日
 * @author 陈伟丽
 */

@Service
public class CarLoanPaybackBriefSerive extends CoreManager<CarLoanPaybackBriefDao,CarLoanPaybackBrief>{
	
	@Autowired
	private CarLoanPaybackBriefDao carLoanPaybackBriefDao;
	
	 /**
     *插入还款明细数据 
     *@author  陈伟丽
     *@create in 2016年6月1日
     *@param  
     *@return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertPaybackBrief(CarLoanPaybackBrief carLoanPaybackBrief){
		carLoanPaybackBriefDao.insertPaybackBrief(carLoanPaybackBrief);
	}

	public void deletePaybackBriefByLoanCode(String loanCode) {
		carLoanPaybackBriefDao.deletePaybackBrief(loanCode);
		
	}
}
