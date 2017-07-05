package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;

/**
 * 待还款匹配列表业务处理Dao
 * @Class Name DealPaybackDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface DealPaybackDao extends CrudDao<PaybackApply>{

	/**
	 * 查询查账账款列表
	 * 2015年12月2日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 * @return list
	 */
	public List<PaybackTransferInfo> findTransfer(PaybackTransferInfo paybackTransferInfo);

	/**
	 * 更新汇款上传列表数据状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 */
	public void updateInfoStatus(PaybackTransferInfo paybackTransferInfo);
	
	/**
	 * 还款匹配保存
	 * 2015年12月7日
	 * By zhangfeng
	 * @param paybackApply
	 */
	public void save(PaybackApply paybackApply);

	public PaybackTransferInfo getstransferInfoReq(Map<String, String> map);

	public int updateInfoStatusReq(PaybackTransferInfo info);
}
