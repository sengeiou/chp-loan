/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.servicePlatformRuleService.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.PlatformRuleDao;
import com.creditharmony.loan.common.entity.PlatformRule;

/**
 * @Class Name PlatformRuleService
 * @author 王彬彬
 * @Create In 2015年12月21日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PlatformRuleService extends
		CoreManager<PlatformRuleDao, PlatformRule> {

	/**
	 * 平台规则新增 2015年12月21日 By 王彬彬
	 * 
	 * @param platformRule
	 *            插入平台数据对象
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insertPlatformRule(PlatformRule platformRule) {
		int count = dao.insert(platformRule);
		return count;
	}

	/**
	 * 获取平台规则 
	 * 2015年12月21日 
	 * By 王彬彬
	 * 
	 * @param plateformrule
	 *            查询条件
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PlatformRule findByParams(PlatformRule plateformrule) {
		return dao.get(plateformrule);
	}

	/**
	 * 获取平台规则 
	 * 2015年12月21日 
	 * By 王彬彬
	 * 
	 * @param plateformrule
	 *            查询条件
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PlatformRule> findAllList(PlatformRule plateformrule) {
		return dao.findAllList(plateformrule);
	}
	/**
	 * 获取平台规则 
	 * @param platformRule
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PlatformRule> findRuleByBank(PlatformRule platformRule) {
		return dao.findRuleByBank(platformRule);
	}


}
