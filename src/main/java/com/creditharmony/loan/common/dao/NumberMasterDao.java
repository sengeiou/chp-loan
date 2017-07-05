/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.daoNumberMasterDap.java
 * @Create By 王彬彬
 * @Create In 2015年12月29日 上午10:59:04
 */
package com.creditharmony.loan.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.NumberMaster;

/**
 * 编号管理
 * @Class Name NumberMasterDap
 * @author 王彬彬
 * @Create In 2015年12月29日
 */
@LoanBatisDao
public interface NumberMasterDao extends CrudDao<NumberMaster> {

}
