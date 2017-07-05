package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackAuditEx;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * @Class Dao实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月8日
 */
@LoanBatisDao
public interface PaybackAuditDao extends CrudDao<PaybackAuditDao> {

	/**
	 * 还款查账已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param paybackAudit
	 * @return 查账已办数据List
	 */
	public List<PaybackAuditEx> allPaybackAuditHavaTodoList(PageBounds pageBounds,
			PaybackAuditEx paybackAudit);

	/**
	 * 还款查账已办 查看页面数据
	 * 2015年12月17日
	 * By 李强
	 * @param paybackAudit
	 * @return 还款查账已办单条数据
	 */
	public PaybackAuditEx seePaybackAuditHavaTodo(PaybackAuditEx paybackAudit);
	
	/**
	 * 还款查账已办 查看页面数据  汇款账号信息
	 * 2016年1月13日
	 * By 李强
	 * @param payBackId
	 * @return
	 */
	public List<PaybackAuditEx> seePaybackAuditHavaList(String payBackId);
	
	/**
	 * 导出还款查账已办数据表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 还款查账已办单挑数据
	 */
	public PaybackAuditEx exportExcel(PaybackAuditEx paybackAuditEx);
	
	/**
	 * 查询存入账户信息
	 * 2016年5月16日
	 * By 赵金平
	 * @param depositFlag
	 * @return
	 */
	public List<MiddlePerson> selectAllMiddle (String depositFlag);

	/**
	 * 查询POS单条信息
	 * 2016年5月16日
	 * By 赵金平
	 * @param depositFlag
	 * @return
	 */
	public List<PaybackAuditEx> seePaybackAuditHavaListPos(String payBackId);
	
}
