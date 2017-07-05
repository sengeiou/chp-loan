/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.daoChangerInfoDao.java
 * @Create By 张灏
 * @Create In 2016年6月21日 上午11:41:09
 */
package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarChangerInfo;

/**
 * 退回门店，修改信息
 * @Class Name ChangerInfoDao
 * @author 张灏
 * @Create In 2016年6月21日
 */
@LoanBatisDao
public interface CarChangerInfoDao extends CrudDao<CarChangerInfo> {
  
    /**
     * 新建变更记录
     * 2016年5月18日
     */
    public void insertChangerInfo(CarChangerInfo changerInfo);
    
    
    public List<CarChangerInfo> selectByUpdateId (String upateId);
}
