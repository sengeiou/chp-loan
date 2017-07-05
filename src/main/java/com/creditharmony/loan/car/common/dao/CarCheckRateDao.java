package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
@LoanBatisDao
public interface CarCheckRateDao extends CrudDao<CarCheckRate>{

	/**
	 * 根据借款编号获取合同费率
	 * 2016年2月19日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
    public CarCheckRate selectByLoanCode(String loanCode);
    
    /**
     * 根据借款编号删除合同费率
     * 2016年2月19日
     * By 申诗阔
     * @param carCheckRate
     */
    public void deleteCarCheckRate(CarCheckRate carCheckRate);

    /**
     * 保存合同费率实体
     * 2016年2月18日
     * By 申诗阔
     * @param carCheckRate
     */
    public void insertCarCheckRate(CarCheckRate carCheckRate);
}