package com.creditharmony.loan.borrow.consult.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ValidHistory;
import com.creditharmony.loan.borrow.payback.entity.Payback;

/**
 * 身份验证DAO
 * @Class Name ValidHisDao
 * @author 宋锋
 * @Create In 2016年10月20日
 */
@LoanBatisDao
public interface ValidHisDao extends CrudDao<ValidHistory> {

	
	/**
	 *修改共借人身份验证信息 
	 *@author songfeng
	 *@Create In 2016年10月20日
	 *@return none
	 */
	public void updateCoborrowerStatus(Map<String,Object> param);
	
	/**
	 *修改主借人身份验证信息 
	 *@author songfeng
	 *@Create In 2016年10月20日
	 *@return none
	 */
	public void updateLoanCustomerStatus(Map<String,Object> param);
	

	/**
	 *添加身份验证历史信息 
	 *@author songfeng
	 *@Create In 2016年10月20日
	 *@return none
	 */
	public void insertValidHis(ValidHistory validHistory);
	
	
}
