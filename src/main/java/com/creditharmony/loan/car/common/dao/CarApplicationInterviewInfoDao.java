package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
@LoanBatisDao
public interface CarApplicationInterviewInfoDao extends CrudDao<CarApplicationInterviewInfo>{
	/**
	 * 新增面审信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param CarApplicationInterviewInfo
	 */
    public void insertCarApplicationInterviewInfo(CarApplicationInterviewInfo carApplicationInterviewInfo);
    /**
   	 * 查询面审信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param CarApplicationInterviewInfo
	 */
   	public CarApplicationInterviewInfo selectCarInterviewInfo(String loanCode);
   	
   	/**
   	 * 根据借款编码查询面审信息
   	 * 2016年2月23日
   	 * By 陈伟东
   	 * @param loanCode
   	 * @return
   	 */
   	public CarApplicationInterviewInfo selectByLoanCode(String loanCode);
   
}