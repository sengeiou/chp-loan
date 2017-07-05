package com.creditharmony.loan.borrow.statistics.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.statistics.entity.ReturnRateStats;
import com.creditharmony.loan.borrow.statistics.view.ReturnRateStatsParams;

/**
 * 退回率统计Dao
 * 
 * @author 任志远
 * @date 2016年11月16日
 */
@LoanBatisDao
public interface ReturnRateStatsDao {

	/** 
	 * 查询退回率统计报表
	 * By 任志远 2016年11月16日
	 *
	 * @param returnRateStatsParams
	 * @param pageBounds
	 * @return
	 */
	PageList<ReturnRateStats> findReturnRateStatsList(ReturnRateStatsParams returnRateStatsParams, PageBounds pageBounds);

	/**
	 * 查询退回率统计导出列表
	 * By 任志远 2016年11月23日
	 *
	 * @param returnRateStatsParams
	 * @return
	 */
	List<ReturnRateStats> findExportReturnRateStatsList(ReturnRateStatsParams returnRateStatsParams);

	
}
