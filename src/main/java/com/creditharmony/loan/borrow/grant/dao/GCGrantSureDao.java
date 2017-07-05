package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

@LoanBatisDao
public interface GCGrantSureDao {

	/**
	 * 查询金信待款项列表--分页
	 * @author songfeng
	 * @Create 2017年2月14日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantSureList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询金信待款项列表--不分页
	 * @author songfeng
	 * @Create 2017年2月16日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantSureList(LoanFlowQueryParam loanFlowQueryParam);
}
