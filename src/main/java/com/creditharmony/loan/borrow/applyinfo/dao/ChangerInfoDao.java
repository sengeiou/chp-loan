/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.daoChangerInfoDao.java
 * @Create By 张灏
 * @Create In 2016年6月21日 上午11:41:09
 */
package com.creditharmony.loan.borrow.applyinfo.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.view.ChangerInfo;

/**
 * 退回门店，修改信息
 * @Class Name ChangerInfoDao
 * @author 张灏
 * @Create In 2016年6月21日
 */
@LoanBatisDao
public interface ChangerInfoDao extends CrudDao<ChangerInfo> {
  
    /**
     * 新建变更记录
     * 2016年5月18日
     */
    public void insertChangerInfo(ChangerInfo changerInfo);
}
