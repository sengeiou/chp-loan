/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.dao.PlatformRuleDao.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.PlatformRule;

/**
 * 平台规则
 * 
 * @Class Name PlatformRuleDao
 * @author 王彬彬
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface PlatformRuleDao extends CrudDao<PlatformRule> {

	List<PlatformRule> findRuleByBank(PlatformRule platformRule);


}