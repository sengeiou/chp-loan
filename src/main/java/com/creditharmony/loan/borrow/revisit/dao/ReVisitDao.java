package com.creditharmony.loan.borrow.revisit.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.revisit.view.RevisitAndPersonInfo;
/**
 * 查询回访失败列表
 * @author songfeng
 *
 */
@LoanBatisDao
public interface ReVisitDao extends CrudDao<RevisitAndPersonInfo>{
	
	public List<RevisitAndPersonInfo> findExportList(RevisitAndPersonInfo revPersonInfo);

}
