package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

@LoanBatisDao
public interface GCDiscardDao {

	/**
	 * 查询金信待分配卡号列表--分页
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询金信待分配卡号列表--不分页
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(LoanFlowQueryParam loanFlowQueryParam);
}
