package com.creditharmony.loan.car.carConsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCustomerBaseDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;

/**
 * 客户咨询_客户基本信息
 * @Class Name CarCustomerService
 * @author 安子帅
 * @Create In 2015年1月22日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerBaseService extends CoreManager<CarCustomerBaseDao, CarCustomerBase> {
	@Autowired
	private CarCustomerBaseDao carCustomerBaseDao;
	@Autowired
	private CarCustomerDao carCustomerDao;
	/**
	 * 保存客户基本信息 2015年1月22日 
	 * By 安子帅
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomerBase(CarCustomerBase carCustomerBase) {
		carCustomerBaseDao.insertCarCustomerBase(carCustomerBase);
	}
	/**
	 * 查询客户基本信息 2015年1月25日 
	 * By 甘泉
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomerBase selectCarCustomerBase(String customerCode) {
		return carCustomerBaseDao.selectByCustomerCode(customerCode);
	}
	/**
	 * 修改客户基本信息 2015年1月25日 
	 * By 甘泉
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarCustomerBase carCustomerBase) {
		carCustomerBaseDao.update(carCustomerBase);
	}
	
	/**
	 * 修改客户基本信息 2015年1月25日 
	 * By 甘泉
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCustomer(CarCustomerBase carCustomerBase, CarCustomer carCustomer) {
		carCustomerBaseDao.update(carCustomerBase);
		carCustomerDao.update(carCustomer);
	}
}
