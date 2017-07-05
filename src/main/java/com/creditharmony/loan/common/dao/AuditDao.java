package com.creditharmony.loan.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;

/**
 * 操作(还款_转账信息记录表)Dao
 * @Class Name ApplyLaunchDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface AuditDao extends CrudDao<PaybackTransferInfo>{

	/**
	 * 更新还款_转账信息记录表
	 * 2016年1月18日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 */
	public void updateInfoStatus(PaybackTransferInfo paybackTransferInfo);
}
