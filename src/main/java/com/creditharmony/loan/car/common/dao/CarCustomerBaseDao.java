package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
@LoanBatisDao
public interface CarCustomerBaseDao extends CrudDao<CarCustomerBase>{
	/**
     *新增客户基本信息 
     *@param CarCustomerBase  
     *@return   
     */
    public void insertCarCustomerBase(CarCustomerBase carCustomerBase);
    /**
     *修改客户基本信息 
     *@param CarCustomerBase  
     *@return   
     */
    public Integer updateById(CarCustomerBase carCustomerBase);
    
    
    /**
     *查询客户基本信息 
     *@param CarCustomerBase 
     *@return  
     */
	public CarCustomerBase selectByCustomerCode(String customerCode);
	/**
     *修改客户基本信息 
     *@param CarCustomerBase 
     *@return  
     */
	public int update(CarCustomerBase carCustomerBase);
}