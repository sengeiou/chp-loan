package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
@LoanBatisDao
public interface DeductPlantStatisticsReportDao extends CrudDao<DeductStatistics>{

	PageList<DeductStatistics> queryList(PageBounds pageBounds,
			DeductStatistics record);

	List<DeductStatistics> queryDeductStatistics();
    
	List<DeductStatistics> queryList(DeductStatistics record);

	PageList<DeductStatistics> periodStatistics(PageBounds pageBounds,
			DeductStatistics record);

	List<DeductStatistics> periodStatistics(DeductStatistics record);
   
}