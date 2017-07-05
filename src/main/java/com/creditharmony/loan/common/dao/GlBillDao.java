/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.daoGlBillDao.java
 * @Create By 张灏
 * @Create In 2015年12月30日 下午8:57:34
 */
package com.creditharmony.loan.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.GlBill;

/**
 * 账单日Dao
 * @Class Name GlBillDao
 * @author 张灏
 * @Create In 2015年12月30日
 */
@LoanBatisDao
public interface GlBillDao extends CrudDao<GlBill> {
	
	/**
	 * 根据签订日期查询账单日
	 * 2015年12月30日
	 * By 张灏
	 * @param glBill
	 * @return GlBill
	 */
	public GlBill findBySignDay(GlBill glBill);

	/**
	 * 查询所有的还款日
	 * 2015年12月30日
	 * By 张灏
	 * @param none
	 * @return List<GlBill>
	 */
	public List<GlBill> findAllBillDay();
}
