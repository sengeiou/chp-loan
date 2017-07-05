package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

@LoanBatisDao
public interface GCLoanGrantDao {

	/**
	 * 查询金信待放款列表--分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询金信待放款列表--不分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(LoanFlowQueryParam loanFlowQueryParam);
}
