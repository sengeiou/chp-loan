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

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认列表中的导出表格查询
 * @Class Name GrantSureDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface GrantSureDao{
	
	
	/**
	 * 查询放款确认列表
	 * 2017年1月17日
	 * By 朱静越
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGrantSureList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询放款确认列表，不分页
	 * 2017年1月18日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGrantSureList(LoanFlowQueryParam loanFlowQueryParam);
}