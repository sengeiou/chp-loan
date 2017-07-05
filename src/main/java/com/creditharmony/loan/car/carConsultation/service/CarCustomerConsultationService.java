package com.creditharmony.loan.car.carConsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;

/**
 * 客户咨询_咨询信息
 * @Class Name CarCustomerConsultationService
 * @author 安子帅
 * @Create In 2015年1月22日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerConsultationService extends CoreManager<CarCustomerConsultationDao, CarCustomerConsultation> {
   
	@Autowired
	private CarCustomerConsultationDao carCustomerConsultationDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;

	/**
	 * 保存客户咨询信息 2015年1月22日 
	 * By 安子帅
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomerConsultation(CarCustomerConsultation carCustomerConsultation) {
		carCustomerConsultationDao.insertCarCustomerConsultation(carCustomerConsultation);
	}
	
	/**
	 * 修改客户咨询信息 2016年1月29日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void updateCarCustomerConsultation(CarCustomerConsultation carCustomerConsultation){
		carCustomerConsultationDao.update(carCustomerConsultation);
	}
	/**
	 * 查询客户咨询信息 2016年1月29日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomerConsultation selectByCustomerCode(String customerCode){
		return carCustomerConsultationDao.selectByCustomerCode(customerCode);
	}
	/**
	 * 查询客户咨询信息 2016年3月11日 
	 * By 甘泉
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomerConsultation selectByLoanCode(String loanCode){
		return carCustomerConsultationDao.selectByLoanCode(loanCode);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public int selectConsul(String plateNumbers) {
		return carCustomerConsultationDao.selectConsul(plateNumbers);
	}
	/**
	 * 更新客户信息
	 * @param carCustomerConsultation
	 * @param carLoanInfo
	 * By 高远
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarCustomerConsultationAndCarLoanInfo(
			CarCustomerConsultation carCustomerConsultation,
			CarLoanInfo carLoanInfo) {
		carCustomerConsultationDao.update(carCustomerConsultation);
		carLoanInfoDao.update(carLoanInfo);
	};
}
