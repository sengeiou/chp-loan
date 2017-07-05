package com.creditharmony.loan.borrow.zcj.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 大金融待款项确认数据列表
 * @Class Name ZcjGrantSureDao
 * @author 朱静越
 * @Create In 2017年2月17日
 */
@LoanBatisDao
public interface ZcjGrantSureDao{
	
	
	/**
	 * 查询大金融放款确认列表
	 * 2017年1月17日
	 * By 朱静越
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getZcjGrantSureList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询大金融放款确认列表，不分页
	 * 2017年1月18日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getZcjGrantSureList(LoanFlowQueryParam loanFlowQueryParam);
}