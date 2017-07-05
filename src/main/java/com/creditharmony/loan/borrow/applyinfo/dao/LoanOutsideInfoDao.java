package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskList;

/**
 * 外访 Dao
 * @Class Name LoanOutsideInfoDao
 * @author 张灏
 * @Create In 2015年12月25日
 * @version 1.0
 */
@LoanBatisDao
public interface LoanOutsideInfoDao  extends CrudDao<LoanOutsideTaskList>{
	
	/**
	 * 根据LoanCode更新LoanOutsideTaskList
	 * @author 张灏
     * @Create In 2015年12月25日
	 * @param record
	 * @return int 执行更新影响的行数，为0标示更新失败 
	 */
	public int updateLoanOutsideTaskListByLoanCode(LoanOutsideTaskList record);
	 
	/**
	 * 根据LoanCode查询LoanOutsideTaskInfo列表
	 * @author 张灏
     * @Create In 2015年12月25日
	 * @param record
	 * @return List<LoanOutsideTaskInfo> 符合条件的LoanOutsideTaskInfo
	 */
	public List<LoanOutsideTaskInfo> getLoanOutsideTaskInfoList(LoanOutsideTaskList record);
	 
}
