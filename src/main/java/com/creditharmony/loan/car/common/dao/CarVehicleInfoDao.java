package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;

@LoanBatisDao
public interface CarVehicleInfoDao extends CrudDao<CarVehicleInfo>{
	 /**
     *新增车辆基本信息 
     *@param CarCustomer   
     *@return  
     */
    public void insertCarVehicleInfo(CarVehicleInfo carVehicleInfo);
    /**
     *查询车辆基本信息 
     *@param CarCustomer 
     *@return  
     */
	public CarVehicleInfo selectByLoanCode(String loanCode);
	/**
     *修改车辆基本信息 
     *@param CarCustomer 
     *@return  
     */
	public int update(CarVehicleInfo carVehicleInfo);
	/**
     *查询车辆在车借流程且不在继续跟踪和初审退回的借款条数(验证)
     *@param String 
     *@return  Integer
     */
	public Integer notRepeatSubmit(String plateNumbers);
	/**
     *查询车辆在继续跟踪和初审退回的借款条数(验证)
     *@param String 
     *@return  Integer
     */
	public Integer uploadAndTrialBack(String plateNumbers);
	
    public int selectPlnum(String plateNumbers);
    
	public int updateById(CarVehicleInfo carVehicleInfo);
	
	/**
     *根据车牌号查询车辆基本信息 
     *@param CarCustomer 
     *@return  
     */
	public CarVehicleInfo selectByPlateNumbers(String plateNumbers);
}