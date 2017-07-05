package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.CustomerLivings;
/**
 * 客户居住情况Dao
 * @Class Name CustomerLivingsDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface CustomerLivingsDao extends CrudDao<CustomerLivings>{
   
    int insert(CustomerLivings record);

    int insertSelective(CustomerLivings record);

	CustomerLivings selectByLoanCode(String loanCode, String loanCustomerType1);
	/**
	 *通过参数查询住房信息 
	 *@param key：loanCode 借款Code ,customerType 用户类型 , relateId 关联ID 
	 * 
	 */
	public CustomerLivings findByParam(Map<String,Object> param);
}