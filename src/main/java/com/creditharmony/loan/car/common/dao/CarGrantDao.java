/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.car.common.dao;

import java.util.HashMap;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carGrant.ex.CarGrantEx;

/**
 * 待放款列表中的待放款的单子
 * @Class Name CarGrantDao
 * @Create In 2016年2月5日
 */
@LoanBatisDao
public interface CarGrantDao extends CrudDao<CarGrantEx>{
	
	/**
	 *  查询待放款列表中的线下导出表
	 * 2016年2月5日
	 * @param id 放款id
	 * @return 放款表实体
	 */
	 public CarGrantEx getGrantList(String id);
	 
	 /**
	 *  查询待放款列表中的线下导出表  车借
	 * 2016年2月5日
	 * @param hashMap 
	 * @return 放款表实体
	 */
	 public CarGrantEx getCarGrantList(HashMap<Object, Object> hashMap);
	 
}