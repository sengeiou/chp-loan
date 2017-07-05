/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.black.daoBlackDao.java
 * @Create By 张灏
 * @Create In 2015年12月15日 上午9:47:26
 */
package com.creditharmony.loan.borrow.black.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.black.entity.Black;

/**
 * 黑名单 Dao
 * @Class Name BlackDao
 * @author 张灏
 * @Create In 2015年12月15日
 */
@LoanBatisDao
public interface BlackDao extends CrudDao<Black> {
	
	/**
	 * 2015年12月15日
	 * By 张灏
	 * @param param
	 * @return List对象集合数据
	 */
    public List<Black> findBlackByIdentification(Black param); 

}
