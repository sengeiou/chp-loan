package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
@LoanBatisDao
public interface CarCustomerCompanyDao extends CrudDao<CarCustomerCompany>{
	/**
	 * 新增工作信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerCompany
	 */
    public void insertCarCustomerCompany(CarCustomerCompany carCustomerCompany);
    /**
   	 * 查询工作信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerCompany
	 */
   	public CarCustomerCompany selectCarCustomerCompany(String loanCode);
   	/**
	 * 修改工作信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerCompany
	 */
	public int update(CarCustomerCompany carCustomerCompany);
	
}