package com.creditharmony.loan.borrow.applyinfo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanOutsideInfoDao;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskList;


/**
 * 外访任务Service
 * @Class Name LoanOutsideTaskInfoService
 * @author 张进
 * @Create In 2015年12月26日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class LoanOutsideTaskInfoService extends CoreManager<LoanOutsideInfoDao, LoanOutsideTaskList> {
	@Autowired
	private LoanOutsideInfoDao loanOutsideInfoDao;
	
	
	/**
	 * 根据LoanCode更新LoanOutsideTaskList
	 * @param record
	 * @return 执行更新影响的行数，为0标示更新失败
	 */
	public int updateLoanOutsideTaskListByLoanCode(LoanOutsideTaskList record){
		return loanOutsideInfoDao.updateLoanOutsideTaskListByLoanCode(record);
	}
	 
	/**
	 * 根据LoanCode查询LoanOutsideTaskInfo列表
	 * @param loanCode 借款编码
	 * @return 执行更新影响的行数，为0标示更新失败
	 */
	public List<LoanOutsideTaskInfo> getLoanOutsideTaskInfoListByLoanCode(String loanCode){
		LoanOutsideTaskList record = new LoanOutsideTaskList();
		record.setLoanCode(loanCode);
		return loanOutsideInfoDao.getLoanOutsideTaskInfoList(record);
	}

	
	
}
