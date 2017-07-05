package com.creditharmony.loan.borrow.payback.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.HistoricalRecordsDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;

/**
 * 还款操作流水
 * @Class Name HistoricalRecordsService
 * @author 李强
 * @Create In 2015年12月23日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class HistoricalRecordsService {

	@Autowired
	private HistoricalRecordsDao historicalRecordsDao;
	
	/**
	 * 还款历史记录弹框(通用)
	 * 2015年12月23日
	 * By 李强
	 * @param page
	 * @param paybackOpe
	 * @return Page<PaybackOpe>历史记录分页信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackOpe> allHistoricalRecordsList(Page<PaybackOpe> page,PaybackOpe paybackOpe){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>)historicalRecordsDao.allHistoricalRecordsList(pageBounds,paybackOpe);
		PageUtil.convertPage(pageList, page);
		return page;
	};
	
	/**
	 * 还款历史记录弹框(逾期管理)
	 * 2015年12月23日
	 * By 李强
	 * @param page
	 * @param paybackOpe
	 * @return Page<PaybackOpe>历史记录分页信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackOpe> allHistoricalRecordsLists(Page<PaybackOpe> page,PaybackOpe paybackOpe){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>)historicalRecordsDao.allHistoricalRecordsLists(pageBounds,paybackOpe);
		PageUtil.convertPage(pageList, page);
		return page;
	};
	
	/**
	 * 集中划扣已拆分历史记录弹框
	 * 2015年12月23日
	 * By 李强
	 * @param page
	 * @param paybackSplit
	 * @return Page<PaybackOpe>历史记录分页信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackSplit> allSplitHisList(Page<PaybackSplit> page,PaybackSplit paybackSplit){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackSplit> pageList = (PageList<PaybackSplit>)historicalRecordsDao.allSplitHisList(pageBounds,paybackSplit);
		PageUtil.convertPage(pageList, page);
		return page;
	};
	
	/**
	 * 根据还款主表ID查询出借款编码
	 * 2016年1月14日
	 * By 李强
	 * @param rPaybackId
	 * @return loanCode
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackOpe queryLoanCode(String rPaybackId){
		return historicalRecordsDao.queryLoanCode(rPaybackId);
	};
}
