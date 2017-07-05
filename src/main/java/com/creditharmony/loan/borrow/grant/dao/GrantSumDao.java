/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create By 朱静越
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @author 朱静越
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantSumEx;

/**
 * 放款确认列表中的汇总表导出
 * @Class Name GrantSumDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface GrantSumDao extends CrudDao<GrantSumEx>{
	
	/**
	 * 查询放款审核列表的汇总表
	 * 2015年12月21日
	 * By 朱静越
	 * @param id 放款id
	 * @return 汇总表实体
	 */
	 public List<GrantSumEx> getSumList(Map<String, Object> ids);
	 
	 /**
		 * 查询放款审核列表的汇总表
		 * 2015年12月21日
		 * By 朱静越
		 * @param id 放款id
		 * @return 汇总表实体
		 */
	 public List<GrantSumEx> getSumListByloanCodes(List<String> loanCodes);
}