package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;

/**
 * 查账账款列表业务处理Dao
 * @Class Name AuditedDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface UrgeCheckAuditedDao extends CrudDao<PaybackTransferOut>{

	/**
	 * 催收服务费跳转列表 
	 * 2015年12月2日
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getUrgeAuditedList(PaybackTransferOut payBackTransferOut,PageBounds pageBounds);

	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 */
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut);
	
	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 */
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut);
	
	/**
	 * 获取导出数据
	 * 2015年12月24日
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getAuditedList(PaybackTransferOut payBackTransferOut);

	/**
	 * 导入数据
	 * 2015年12月24日
	 * By zhangfeng
	 * @param lst
	 */
	public void insert(List<PaybackTransferOut> lst);

	/**
	 * 查询未匹配银行流水
	 * 2016年2月29日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return
	 */
	public List<PaybackTransferOut> getNoAuditedList(PaybackTransferOut paybackTransferOut);
}
