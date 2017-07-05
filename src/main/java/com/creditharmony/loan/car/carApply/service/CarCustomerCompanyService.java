package com.creditharmony.loan.car.carApply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCustomerCompanyDao;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;

/**
 * 工作信息
 * @Class Name CarCustomerCompanyService
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerCompanyService extends CoreManager<CarCustomerCompanyDao, CarCustomerCompany> {
	@Autowired
	private CarCustomerCompanyDao carCustomerCompanyDao;

	/**
	 * 保存工作信息 2016年2月16日 
	 * By 安子帅
	 * @param CarCustomerCompany
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomerCompany(CarCustomerCompany carCustomerCompany) {
		carCustomerCompanyDao.insert(carCustomerCompany);
	}
	/**
	 * 查询工作信息 2016年2月16日 
	 * By 安子帅
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomerCompany selectCarCompany(String loanCode) {
		return carCustomerCompanyDao.selectCarCustomerCompany(loanCode);
	}
	/**
	 * 修改工作信息 2016年2月16日 
	 * By 安子帅
	 * @param CarCustomerCompany
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarCustomerCompany carCustomerCompany) {
		carCustomerCompanyDao.update(carCustomerCompany);
	}
}
