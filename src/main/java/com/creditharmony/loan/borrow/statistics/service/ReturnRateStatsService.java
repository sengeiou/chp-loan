package com.creditharmony.loan.borrow.statistics.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.statistics.dao.ReturnRateStatsDao;
import com.creditharmony.loan.borrow.statistics.entity.ReturnRateStats;
import com.creditharmony.loan.borrow.statistics.view.ReturnRateStatsParams;

/**
 * 退回率统计服务层
 *
 * @author 任志远
 * @date 2016年11月16日
 */
@Service
@Transactional(readOnly=true,value = "loanTransactionManager")
public class ReturnRateStatsService {
	
	@Resource
	private ReturnRateStatsDao returnRateStatsDao;

	/** 
	 * 查询退回率统计报表
	 * By 任志远 2016年11月16日
	 *
	 * @param page
	 * @return
	 */
	public Page<ReturnRateStats> findReturnRateStatsPage(Page<ReturnRateStats> page, ReturnRateStatsParams returnRateStatsParams) {

		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		PageList<ReturnRateStats> pageList = (PageList<ReturnRateStats>)returnRateStatsDao.findReturnRateStatsList(returnRateStatsParams, pageBounds);
		PageUtil.convertPage(pageList, page);
		
		return page;
	}
	
	/** 
	 * 查询退回率统计报表
	 * By 任志远 2016年11月16日
	 *
	 * @param page
	 * @return
	 */
	public List<ReturnRateStats> findExportReturnRateStatsList(ReturnRateStatsParams returnRateStatsParams) {
		
		return returnRateStatsDao.findExportReturnRateStatsList(returnRateStatsParams);
	}

}
