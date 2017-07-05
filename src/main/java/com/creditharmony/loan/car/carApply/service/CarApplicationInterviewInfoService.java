package com.creditharmony.loan.car.carApply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarApplicationInterviewInfoDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;

/**
 * 面审信息
 * @Class Name CarApplicationInterviewInfoService
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarApplicationInterviewInfoService extends CoreManager<CarApplicationInterviewInfoDao, CarApplicationInterviewInfo> {
	@Autowired
	private CarApplicationInterviewInfoDao carApplicationInterviewInfoDao;

	/**
	 * 保存面审信息 2016年2月16日 
	 * By 安子帅
	 * @param CarApplicationInterviewInfo
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarApplicationInterviewInfo(CarApplicationInterviewInfo carApplicationInterviewInfo) {
		carApplicationInterviewInfoDao.insert(carApplicationInterviewInfo);
	}
	/**
	 * 查询面审信息 2016年2月16日 
	 * By 安子帅
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarApplicationInterviewInfo selectCarApplicationInterviewInfo(String loanCode) {
		return carApplicationInterviewInfoDao.selectByLoanCode(loanCode);
	}
	/**
	 * 修改面审信息
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarApplicationInterviewInfo carApplicationInterviewInfo) {
		carApplicationInterviewInfoDao.update(carApplicationInterviewInfo);
	}
}
