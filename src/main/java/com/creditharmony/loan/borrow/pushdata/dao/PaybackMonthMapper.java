package com.creditharmony.loan.borrow.pushdata.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.pushdata.entity.PaybackList;

/**
 * 期供表
 * @Class Name PaybackMonthMapper
 * @author 武文涛
 * @Create In 2015年12月17日
 */
@LoanBatisDao
public interface PaybackMonthMapper {
	
	/**
	 * 插入数据
	 * 2016年2月19日
	 * By 武文涛
	 * @param paybackMonth
	 * @return int
	 */
	public int insertSelective(PaybackMonth paybackMonth);
	
	/**
	 * 更新数据
	 * 2016年1月14日
	 * By 武文涛
	 * @param example
	 * @return int
	 */
	public int updateByPrimaryKeySelective(PaybackMonth example);

	/**
	 * 已还期数 
	 * 2015年12月8日 
	 * By 武文涛
	 * @param paybackMap
	 * @return int
	 */
	public int selectCountBack(Map<String, String> paybackMap);

	/**
	 * 查询还款日前3天，期供状态为待还款的数据
	 * 2015年12月19日
	 * By 施大勇
	 * @param map  
	 * @return 期供数据List
	 */
	public List<PaybackList> selectMonthPayDayBeforeThreeDays(Map<String, Object> map);

	/**
	 * 查询逾期数据
	 * 2016年1月14日
	 * By 武文涛
	 * @param none
	 * @return none
	 */
	public void selectIsOverdueMsg();
	
	/**
	 * 根据参数检索期供表
	 * 2016年1月22日
	 * By 施大勇
	 * @param param
	 * @return PaybackMonth
	 */
	public PaybackMonth selectByParam(PaybackMonth param);

	public String queryCount(Map<String, Object> map);
}