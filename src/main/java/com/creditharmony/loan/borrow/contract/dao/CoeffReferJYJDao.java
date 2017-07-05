/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoRateInfoDao.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午9:56:01
 */
package com.creditharmony.loan.borrow.contract.dao;


import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.CoeffReferJYJ;

/**
 * 产品费率-简易借dao层
 * @Class Name CoeffReferDao
 * @author 申阿伟
 * @Create In 2017年5月6日
 */
@LoanBatisDao
public interface CoeffReferJYJDao extends CrudDao<CoeffReferJYJ> {
    
   public List<CoeffReferJYJ> selectCoeffRefer(CoeffReferJYJ coeffReferJYJ);
}
