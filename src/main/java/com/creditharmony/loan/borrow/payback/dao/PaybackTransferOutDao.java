package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;

/**
 * 查账账款列表业务处理Dao
 * @Class Name PaybackTransferOutDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface PaybackTransferOutDao extends CrudDao<PaybackTransferOut>{

	/**
	 * 跳转查账账款页面查询列表
	 * 2015年12月2日
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> findList(PaybackTransferOut payBackTransferOut,PageBounds pageBounds);

	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut);
	
	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut);
	
	/**
	 * 获取导出数据
	 * 2015年12月24日
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> findList(PaybackTransferOut payBackTransferOut);

	/**
	 * 导入数据
	 * 2015年12月24日
	 * By zhangfeng
	 * @param lst
	 * @return none
	 */
	public void insert(List<PaybackTransferOut> list);

	/**
	 * 查询账款信息 (未匹配)
	 * 2016年3月23日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getNoAuditedList(PaybackTransferOut paybackTransferOut);

	/**
	 * 查询导入流水表是否存在查账成功的流水 zhangfeng
	 * @param out
	 * @return list 
	 */
	public List<PaybackTransferOut> findAuditedList(PaybackTransferOut out);
	
	/**
	 * 导出查账excel
	 * @param out
	 * @return
	 */
	public List<PaybackTransferOut> exportAuditedList(PaybackTransferOut out);

	/**
	 * /**
	 * 查询账款信息 (自动匹配)
	 * 2016年3月23日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getAutoNoAuditedList(PaybackTransferOut out);
	
}
