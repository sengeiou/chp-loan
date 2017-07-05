/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create By 朱静越
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @author 朱静越
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.borrow.grant.dao;

import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;

/**
 * 放款审核列表中的导出表格查询
 * @Class Name SyncFortuneDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface SyncFortuneDao extends CrudDao<SyncClaim>{
	
	/**
	 * 查询要推送到财富的数据对象
	 * 2016年1月14日
	 * By 朱静越
	 * @param applyId 参数
	 * @return 要同步的对象
	 */
	public SyncClaim syncFortune(String applyId);
}