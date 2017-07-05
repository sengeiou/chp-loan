package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;

/**
 * 还款操作流水
 * @Class Name HistoricalRecordsDao
 * @author 李强
 * @Create In 2015年12月23日
 */
@LoanBatisDao
public interface HistoricalRecordsDao extends CrudDao<HistoricalRecordsDao> {
	
	/**
	 * 还款历史记录弹框(通用)
	 * 2015年12月23日
	 * By 李强
	 * @param pageBounds
	 * @param paybackOpe
	 * @return List<PaybackOpe>历史记录集合
	 */
	public List<PaybackOpe> allHistoricalRecordsList(PageBounds pageBounds,PaybackOpe paybackOpe);
	
	/**
	 * 还款历史记录弹框(逾期管理)
	 * 2015年12月23日
	 * By 李强
	 * @param pageBounds
	 * @param paybackOpe
	 * @return List<PaybackOpe>历史记录集合
	 */
	public List<PaybackOpe> allHistoricalRecordsLists(PageBounds pageBounds,PaybackOpe paybackOpe);
	
	/**
	 * 集中划扣已拆分历史记录弹框
	 * 2015年12月23日
	 * By 李强
	 * @param pageBounds
	 * @param paybackSplit
	 * @return List<PaybackOpe>历史记录集合
	 */
	public List<PaybackSplit> allSplitHisList(PageBounds pageBounds,PaybackSplit paybackSplit);
	
	/**
	 * 根据还款主表ID查询出借款编码
	 * 2016年1月14日
	 * By 李强
	 * @param rPaybackId
	 * @return loanCode
	 */
	public PaybackOpe queryLoanCode(String rPaybackId);
}
