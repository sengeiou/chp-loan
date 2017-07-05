/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.outvisit.serviceOutVisitService.java
 * @Create By 王彬彬
 * @Create In 2016年1月24日 下午7:45:38
 */
package com.creditharmony.loan.borrow.outvisit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.lend.type.LendConstants;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.outvisit.dao.OutVisitDao;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.OutsideTaskList;
import com.creditharmony.loan.common.workFlow.view.LoanFlowDictEx;
import com.creditharmony.loan.common.workFlow.view.OutsideCheckWFInfo;
import com.google.common.collect.Maps;

/**
 * 外访相关信息
 * 
 * @Class Name OutVisitService
 * @author 王彬彬
 * @Create In 2016年1月24日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class OutVisitService extends
		CoreManager<OutVisitDao, LoanOutsideTaskInfo> {
	
	/**
	 * 获取借款信息 2016年1月24日 By 王彬彬
	 * 
	 * @param applyId
	 *            工作流申请Id
	 * @return 借款基本信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanInfo> findLoanByApplyId(String applyId) {
		Map<String, String> mapLoan = new HashMap<String, String>();
		mapLoan.put("applyId", applyId);
		return dao.findLoan(mapLoan);
	}

	/**
	 * 获取借款信息 2016年1月24日 By 王彬彬
	 * 
	 * @param applyId
	 *            工作流申请Id
	 * @return 借款基本信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanInfo> findLoanByLoanCode(String loanCode) {
		Map<String, String> mapLoan = new HashMap<String, String>();
		mapLoan.put("loanCode", loanCode);
		return dao.findLoan(mapLoan);
	}

	/**
	 * 获取客户信息（外访客户） 2016年1月24日 By 王彬彬
	 * 
	 * @param loanCode
	 *            借款编号
	 * @return 客户基本信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Customer> findCustomerByLoanCode(Map<String, String> map) {
		return dao.findCustomer(map);
	}

	/**
	 * 获取共借人信息 2016年1月24日 By 王彬彬
	 * 
	 * @param loanCode
	 *            借款编号
	 * @return 共借人列表信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanCoborrower> findCoBorrowerByApplyId(Map<String, String> map) {
		return dao.findCoBorrower(map);
	}

	/**
	 * 更新外访距离，外访员
	 * 2016年2月24日
	 * By 王彬彬
	 * @param outsideTaskList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutVisit(OutsideTaskList outsideTaskList) {
		outsideTaskList.preUpdate();
		dao.updateOutVisit(outsideTaskList);
	}
	
	/**
	 * 获取外访清单
	 * 2016年5月24日
	 * @return 回退清单
	 */
	public List<LoanFlowDictEx> getVisitDicts(){
    	return dao.getDictsByType(LendConstants.OUT_CHK);
    }
    /**
	 * 获取外访清单
	 * 2016年5月24日
	 * @param relationId 关联ID(变更历史表)
	 * @return 外访清单
	 */
	public List<OutsideCheckWFInfo> getInfosByRid(String relationId){
		return dao.getInfosByRid(relationId);
	}
	
    /**
     * 返回map，页面循环使用
     * 2016年5月24日
     * @return 
     */
    public Map<String, Object> getLoanManFlag(){    	
    	Map<String, Object> map = Maps.newHashMap();
    	map.put(LoanManFlag.MAIN_LOAN.getCode(), LoanManFlag.MAIN_LOAN.getName());
    	map.put(LoanManFlag.COBORROWE_LOAN.getCode(), LoanManFlag.COBORROWE_LOAN.getName());
    	map.put(LoanManFlag.MATE.getCode(), LoanManFlag.MATE.getName());
    	return map;
    }
    
    /**
     * 获取外访任务种类
     * 2016年6月19日
     * By 王彬彬
     * @param loanCode
     * @return 外访任务种类
     */
    public String selectVisitType(String loanCode)
    {
    	return dao.selectVisitType(loanCode);
    }

	/** 
	 * 根据LoanCode查外放任务列表
	 * By 任志远 2016年11月30日
	 *
	 * @param param
	 * @return
	 */
	public List<OutsideTaskList> findFinishOutsideTaskListByLoanCode(Map<String, Object> param) {
		
		return dao.findFinishOutsideTaskListByLoanCode(param);
	}
	/**
	 * 根据loanCode查询最新的外访任务
	 */
	public OutsideTaskList selectOutsideTaskListByLoanCode(String loanCode){
		return dao.selectOutsideTaskListByLoanCode(loanCode);
	}
	/**
	 * 根据taskId查询外访任务详情
	 */
	public LoanOutsideTaskInfo selectOutsideTaskInfoByTaskId(String taskId){
		return dao.selectOutsideTaskInfoByTaskId(taskId);
	}
}
