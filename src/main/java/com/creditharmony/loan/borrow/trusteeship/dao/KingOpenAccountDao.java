package com.creditharmony.loan.borrow.trusteeship.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

@LoanBatisDao
public interface KingOpenAccountDao {
	
	/**
	 * 分页查询金账户开户列表
	 * 2017年2月13日
	 * By 朱静越
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getKingOpenList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 不分页查询金账户开户列表信息
	 * 2017年2月13日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getKingOpenList(LoanFlowQueryParam loanFlowQueryParam);
}
