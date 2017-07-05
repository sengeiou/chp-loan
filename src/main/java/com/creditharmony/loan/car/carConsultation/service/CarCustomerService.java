package com.creditharmony.loan.car.carConsultation.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCustomerBaseDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;

/**
 * 客户咨询_客户信息
 * @Class Name CarCustomerService
 * @author 安子帅
 * @Create In 2015年1月22日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerService extends CoreManager<CarCustomerDao, CarCustomer> {
   
	@Autowired
	private CarCustomerDao carCustomerDao;
	@Autowired
	private CarCustomerBaseDao carCustomerBaseDao;
	/**
	 * 保存客户信息 2015年1月22日 
	 * By 安子帅
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomer(CarCustomer carCustomer) {
		carCustomerDao.insertCarCustomer(carCustomer);
	}
	/**
	 * 查询客户信息 2015年1月29日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomer selectByCustomerCode(String customerCode){
		return carCustomerDao.selectByCustomerCode(customerCode);
	}
	/**
	 * 查询客户信息 2015年1月29日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomer selectByLoanCode(String loanCode){
		return carCustomerDao.selectByloanCode(loanCode);
	}
	/**
	 * 查询客户信息  
	 * By 
	 * @param consult
	 */
		@Transactional(readOnly = false,value = "loanTransactionManager")
		public CarCustomer selectByLoanCodeE(String loanCode){
			return carCustomerDao.selectByLoanCodeE(loanCode);
		}
	 
	/**
	 * 修改客户信息 2015年1月29日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarCustomer carCustomer){
		carCustomerDao.update(carCustomer);
	}
	/**
     *查询客户借款被拒时间小于90天的列数(客户姓名)(拒绝)
     *@param String 
     *@return   int
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int validationByName(String customerName) {
		return carCustomerDao.validationByName(customerName);
	}
	/**
     *查询客户借款被拒时间小于90天的列数(客户手机号码)(拒绝)
     *@param String 
     *@return   int
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int validationByphone(String phone) {
		return carCustomerDao.validationByphone(phone);
	}
	/**
     *查询客户借款被拒时间小于90天的列数(客户姓名)(不符合进件条件)
     *@param String 
     *@return   int
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int validationByName2(String customerName) {
		return carCustomerDao.validationByName2(customerName);
	}
	/**
     *查询客户借款被拒时间小于90天的列数(客户手机号码)(不符合进件条件)
     *@param String 
     *@return   int
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int validationByphone2(String phone) {
		return carCustomerDao.validationByphone2(phone);
	}
	/**
	 * 修改客户信息
	 * @param carCustomer
	 * @param carCustomerBase
	 * By 高远
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarCustomerAndBase(CarCustomer carCustomer,
			CarCustomerBase carCustomerBase) {
		carCustomerBaseDao.update(carCustomerBase);
		carCustomerDao.update(carCustomer);
	}
	
    /**
     * 手机号必须验证；同一客户如果在一个月内再次申请不同车辆的借款，手机号无需再次验证；超过一个月的需要重新验证
     * @param customerCode
     * @return
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarCustomer> selectCustomer(String customerCode) {
		return carCustomerDao.selectCustomer(customerCode);
	}
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomer selectByCustomerCodeNew(String customerCode) {
		return carCustomerDao.selectByCustomerCodeNew(customerCode);
	}
	
}
