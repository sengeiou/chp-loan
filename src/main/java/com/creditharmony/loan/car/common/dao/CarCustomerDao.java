package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.car.common.entity.CarCustomer;
@LoanBatisDao
public interface CarCustomerDao  extends CrudDao<CarCustomer> {
	 /**
     *新增客户基本信息 
     *@param CarCustomer 
     *@return  
     */
    public void insertCarCustomer(CarCustomer carCustomer);
    
    /**
     *修改客户基本信息 
     *@param CarCustomer 
     *@return  
     */
    public Integer updateById(CarCustomer carCustomer);
    /**
     *查询客户基本信息 
     *@param CarCustomer customerCode
     *@return  
     */
    public CarCustomer selectByCustomerCode(String customerCode);
    /**
     *查询客户基本信息 
     *@param CarCustomer customerCode
     *@return  
     */
    public CarCustomer selectByloanCode(String loanCode);
    /**
     *查询客户基本信息 
     *@param CarCustomer customerCode
     *@return  
     */
    public CarCustomer selectByLoanCodeE(String loanCode);
    
    /**
     *查询身份证借车数量
     *@param String 
     *@return  
     */
    public Integer vehicleCeiling(String customerCertNum);
    /**
     *查询客户借款被拒时间小于90天的列数
     *@param String 
     *@return   Integer
     */
    public Integer notRejectTime(String customerCertNum);
    /**
     *咨询是对客户姓名验证其是否是属于不符合进件条件的(90天的限制)
     *@param String 
     *@return   int
     */
    public int validationByName2(String customerName);
    /**
     *咨询是对客户手机号码验证其是否是属于不符合进件条件的(90天的限制)
     *@param String 
     *@return   int
     */
    public int validationByphone2(String phone);
    /**
     *咨询是对客户姓名验证其是否是属于门店拒绝的(90天的限制)
     *@param String 
     *@return   int
     */
    public int validationByName(String customerName);
    /**
     *咨询是对客户手机号码验证其是否是属于门店拒绝的(90天的限制)
     *@param String 
     *@return   int
     */
    public int validationByphone(String phone);
    
    /**
     * 手机号必须验证；同一客户如果在一个月内再次申请不同车辆的借款，手机号无需再次验证；超过一个月的需要重新验证
     * @param customerCode
     * @return
     */
    public List<CarCustomer>  selectCustomer(String customerCode);
	public CarCustomer selectByCustomerCodeNew(String customerCode);
    
}