package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.ex.EarlySettlementEx;

/**
 * 提前结清已办
 * 
 * @Class Name EarlySettlementHaveTodoDao
 * @author 李强
 * @Create In 2015年12月01日
 */
@LoanBatisDao
public interface EarlySettlementDao extends
		CrudDao<EarlySettlementDao> {

	/**
	 * 查询提前结清已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param earlySettlement
	 * @return 提前结清已办List
	 */
	public List<EarlySettlementEx> allEarlySettlementList(PageBounds pageBounds,
			EarlySettlementEx earlySettlement);

	/**
	 * 查询提前结清已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param earlySettlement
	 * @return 提前结清已办List
	 */
	public List<EarlySettlementEx> allEarlySettlementList(EarlySettlementEx earlySettlement);

	/**
	 * 查看 提前结清已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param earlySettlement
	 * @return 结清已办单个对象数据
	 */
	public EarlySettlementEx seeEarlySettlement(EarlySettlementEx earlySettlement);
	
	/**
	 * 获取减免的违约金罚息总和
	 * 2016年3月5日
	 * By zhaojinping
	 * @param contractCode
	 * @return
	 */
	public EarlySettlementEx getPunishPenaltySum(String contractCode); 
	
}
