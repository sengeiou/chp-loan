package com.creditharmony.loan.borrow.transate.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;

@LoanBatisDao
/**
 * 信借审批信息详情
 * @Class Name LoanMinuteDao
 * @author lirui
 * @Create In 2015年12月3日
 */
public interface LoanMinuteDao extends CrudDao<LoanMinuteEx>{
	/**
	 * 通过借款编码查找信借审批信息详情
	 * 2015年12月2日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 信借审批信息详情对象
	 */
	public LoanMinuteEx loanMinute(String loanCode);
	
	/**
	 * 更新委托提现标识
	 * 2016年3月15日
	 * By 朱杰
	 * @param loanCode
	 * @return
	 */
	public void updateTrustCash(String loanCode);
	
	/**
	 * 更新委托充值标识
	 * 2016年3月15日
	 * By 朱杰
	 * @param applyId
	 * @return
	 */
	public void updateTrustRecharge(String loanCode);
	
	public List<Map<String,Object>> getCoborrower(String loanCode);
	public List<Map<String,Object>> getCoborrowerOne(String loanCode);
	
	public List<Map<String,Object>> getSendEMail(Map map);
	public List<Map<String,Object>> getSend(Map map);
}	
