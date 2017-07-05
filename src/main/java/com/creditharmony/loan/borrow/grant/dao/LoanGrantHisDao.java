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

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
@LoanBatisDao
public interface LoanGrantHisDao extends CrudDao<LoanGrantHis>{
	/**
	 * 插入放款历史记录表
	 * 2016年3月31日
	 * By 朱静越
	 * @param loanGrantHis
	 * @return
	 */
	public int insertGrantHis(LoanGrantHis loanGrantHis);
	
	/**
	 * 更新放款记录表
	 * 2016年4月1日
	 * By 朱静越
	 * @param loanGrantHis
	 * @return
	 */
	public int updateLoanGrantHis(LoanGrantHis loanGrantHis);
	
	/**
	 * 根据合同编号获得历史列表
	 * 2016年5月10日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public List<LoanGrantHis> getGrantHis(PageBounds pageBounds,String contractCode);
}
