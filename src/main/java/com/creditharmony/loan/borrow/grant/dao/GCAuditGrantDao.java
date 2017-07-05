package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

@LoanBatisDao
public interface GCAuditGrantDao {

	/**
	 * 查询金信待放款审核列表--分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<GrantAuditEx> getGCAuditGrantList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询金信待放款审核列表--不分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<GrantAuditEx> getGCAuditGrantList(LoanFlowQueryParam loanFlowQueryParam);
}
