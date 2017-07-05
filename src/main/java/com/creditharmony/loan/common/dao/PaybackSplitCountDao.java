/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.dao.PlatformRuleDao.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.PaybackSplitCount;

/**
 * 拆分事件处理
 * @Class Name PaybackSplitCountDao
 * @author 王彬彬
 * @Create In 2016年2月22日
 */
@LoanBatisDao
public interface PaybackSplitCountDao extends CrudDao<PaybackSplitCount> {
}