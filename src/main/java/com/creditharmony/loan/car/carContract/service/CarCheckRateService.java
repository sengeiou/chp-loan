package com.creditharmony.loan.car.carContract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCheckRateDao;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;

/**
 * 车借--合同费率
 * @Class Name CarCheckRateService
 * @author 申诗阔
 * @Create In 2016年2月18日
 */
@Service
public class CarCheckRateService extends CoreManager<CarCheckRateDao, CarCheckRate> {
   
	@Autowired
	private CarCheckRateDao carCheckRateDao;
	@Autowired
	private CarContractDao carContractDao;
	
	/**
	 * 保存合同费率信息
	 * 2016年2月18日
	 * By 申诗阔
	 * @param carContract
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveCarCheckRate(CarCheckRate carCheckRate, CarContract carContract) {
		carCheckRateDao.insertCarCheckRate(carCheckRate);
		carContractDao.update(carContract);
	}
	
	/**
	 * 根据合同编号获取合同费率信息
	 * 2016年2月19日
	 * By 申诗阔
	 * @param carContract
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarCheckRate selectCarCheckRateByLoanCode(String loanCode) {
		return carCheckRateDao.selectByLoanCode(loanCode);
	}
	
}
