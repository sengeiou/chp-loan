/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.outvisit.daoOutVisitDao.java
 * @Create By 王彬彬
 * @Create In 2016年1月24日 下午7:45:11
 */
package com.creditharmony.loan.borrow.outvisit.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.OutsideTaskList;
import com.creditharmony.loan.common.workFlow.view.LoanFlowDictEx;
import com.creditharmony.loan.common.workFlow.view.OutsideCheckWFInfo;

/**
 * 外访基本信息Dao
 * @Class Name OutVisitDao
 * @author 王彬彬
 * @Create In 2016年1月24日
 */
@LoanBatisDao
public interface OutVisitDao extends CrudDao<LoanOutsideTaskInfo>{
	
	/**
	 * 获取借款信息
	 * 2016年1月24日
	 * By 王彬彬
	 * @param map 工作流申请Id
	 * @return 借款基本信息
	 */
	public List<LoanInfo> findLoan(Map<String,String> map);
	
	/**
	 * 获取客户信息（外访客户）
	 * 2016年1月24日
	 * By 王彬彬
	 * @param map 借款编号
	 * @return 外访客户信息
	 */
	public List<Customer> findCustomer(Map<String,String> map);
	
	/**
	 * 获取共借人信息
	 * 2016年1月24日
	 * By 王彬彬
	 * @param map 借款编号
	 * @return 共借人列表信息
	 */
	public List<LoanCoborrower> findCoBorrower(Map<String,String> map);
	
	/**
	 * 更新外放任务表
	 * 2016年2月24日
	 * By 王彬彬
	 * @param map 更新内容和条件
	 */
	public void updateOutVisit(OutsideTaskList outsideTaskList);
	/**
	 * 获取外访、回退清单
	 * 2015年12月25日
	 * By 赖敏
	 * @param type 一级类型
	 * @return 对应的字典列表
	 */
    public List<LoanFlowDictEx> getDictsByType(String type);
    /**
     * 根据历史ID获取外访清单
     * 2015年12月28日
     * By 赖敏
     * @param relationId 关联ID(变更历史表)
     * @return 外访清单
     */
    public List<OutsideCheckWFInfo> getInfosByRid(String relationId);
    
    /**
     * 获取外访任务种类
     * 2016年6月19日
     * By 王彬彬
     * @param loanCode
     * @return 外访任务种类
     */
    public String selectVisitType(String loanCode);

	/** 
	 * 根据LoanCode查外放任务列表
	 * By 任志远 2016年11月30日
	 *
	 * @param param
	 * @return
	 */
	public List<OutsideTaskList> findFinishOutsideTaskListByLoanCode(Map<String, Object> param);
	/**
	 * 根据loanCode查询最新的外访任务
	 */
	public OutsideTaskList selectOutsideTaskListByLoanCode(String loanCode);
	/**
	 * 根据taskId查询外访任务详情
	 */
	public LoanOutsideTaskInfo selectOutsideTaskInfoByTaskId(String taskId);
}
