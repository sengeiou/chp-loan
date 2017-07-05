/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.daoReconsiderExDao.java
 * @Create By 张灏
 * @Create In 2015年12月25日 下午5:37:24
 */
package com.creditharmony.loan.borrow.reconsider.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderEx;

/**
 * 复议发起 Dao
 * @Class Name ReconsiderExDao
 * @author 张灏
 * @Create In 2015年12月25日
 */
@LoanBatisDao
public interface ReconsiderExDao extends CrudDao<ReconsiderEx> {

    /**
     * 获取复议发起待办列表 
     * @author 张灏
     * @Create In 2015年12月25日
     * @param param 检索条件封装类 
     * @return List<ReconsiderEx>
     * 
     */
    public List<ReconsiderEx> findByParam(ReconsiderEx param);
}
