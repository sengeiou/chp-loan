package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.DeductStatisticsReport;
/**
 * 
* Description:划扣统计dao
* @class DeductStatisticsReportDao
* @author wengsi 
* @date 2017年5月25日上午9:44:35
 */
@LoanBatisDao
public interface DeductStatisticsReportDao extends CrudDao<DeductStatisticsReport>{

	PageList<DeductStatisticsReport> queryList(PageBounds pageBounds,
			DeductStatisticsReport record);

	List<DeductStatisticsReport> queryDeductStatistics();
    
	List<DeductStatisticsReport> queryList(DeductStatisticsReport record);

	PageList<DeductStatisticsReport> periodStatistics(PageBounds pageBounds,
			DeductStatisticsReport record);

	List<DeductStatisticsReport> periodStatistics(DeductStatisticsReport record);
   
}