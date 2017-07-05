package com.creditharmony.loan.car.carApply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.dao.CarVehicleInfoDao;
import com.creditharmony.loan.car.common.entity.CarCustomer;

/**
 * 客户信息
 * @Class Name CustomerValidationService
 * @author ganquan
 * @Create In 2016年2月2日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CustomerValidation extends CoreManager<CarCustomerDao, CarCustomer> {
	//客户借款信息dao
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	//客户信息
	@Autowired
	private CarCustomerDao carCustomerDao;
	//车辆信息service
	@Autowired
	private CarVehicleInfoDao carVehicleInfoDao;
	/**
	 * 查询身份证借车上限 2015年1月22日 
	 * By ganquan
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Integer vehicleCeiling(String customerCertNum){
		Integer num = carLoanInfoDao.vehicleCeiling(customerCertNum);
		return num;
	}
	/**
	 * 查询车辆在车借流程且不在继续跟踪和初审退回的借款条数(验证) 2015年1月22日 
	 * By ganquan
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Integer notRepeatSubmit(String plateNumbers){
		Integer num = 0;
		num = carVehicleInfoDao.notRepeatSubmit(plateNumbers);
		return num;
	}
	/**
	 * 查询借客户被拒天数未超过90的次数
	 * By 甘泉 
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Integer notRejectTime(String customerCertNum){
		return carCustomerDao.notRejectTime(customerCertNum);
	}
	/**
	 * 查询车辆在继续跟踪和初审退回的借款条数(验证)
	 * By 甘泉 uploadAndTrialBack
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Integer uploadAndTrialBack(String plateNumbers){
		Integer num = 0;
		num = carVehicleInfoDao.uploadAndTrialBack(plateNumbers);
		return num; 
	}
}
