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
import com.creditharmony.loan.borrow.grant.entity.ex.GrantCustomerEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

/**
 * 放款确认列表中的客户信息表导出
 * @Class Name GrantCustomerDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface GrantCustomerDao extends CrudDao<GrantCustomerEx>{
	
	/**
	 * 获得放款确认列表要进行导出的客户信息表
	 * 2016年1月6日
	 * By 朱静越
	 * @param id
	 * @return 客户信息实体
	 */
	public List<GrantCustomerEx> getCustomerList(Map<String, Object> ids);
	
	/**
	 * 根据借款编码获取客户信息
	 * 2016年3月4日
	 * xiaoniu.hu
	 * @param loanCodes
	 * @return
	 */
	public List<GrantCustomerEx> getCustomerByLoanCodes(LoanFlowQueryParam loanFlowQueryParam);
}