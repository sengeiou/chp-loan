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
import com.creditharmony.loan.common.entity.GlBillHz;

/**
 * 账单日Dao
 * @Class Name GlBillDao
 * @author 申阿伟
 * @Create In 2017年03月31日
 */
@LoanBatisDao
public interface GlBillHzDao extends CrudDao<GlBillHz> {
	
	/**
	 * 根据签订日期查询账单日
	 * 2017年03月31日
	 * By 申阿伟
	 * @param glBillhz
	 * @return GlBillHz
	 */
	public GlBillHz findBySignDay(GlBillHz glBillhz);

	/**
	 * 查询所有的还款日
	 * 2017年03月21日
	 * By 申阿伟
	 * @param none
	 * @return List<GlBillHz>
	 */
	public List<GlBillHz> findAllBillDay();
}
