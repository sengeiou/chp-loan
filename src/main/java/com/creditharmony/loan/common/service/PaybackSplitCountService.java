/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.servicePlatformRuleService.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.PaybackSplitCountDao;
import com.creditharmony.loan.common.entity.PaybackSplitCount;

/**
 * 拆分规则
 * @Class Name PaybackSplitCountService
 * @author 王彬彬
 * @Create In 2015年12月21日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PaybackSplitCountService extends
		CoreManager<PaybackSplitCountDao, PaybackSplitCount> {

	/**
	 * 插入拆分统计
	 * 2016年2月22日
	 * By 王彬彬
	 * @param splitcount 拆分统计次数
	 * @return 插入件数
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insertSplitCount(PaybackSplitCount splitcount) {
		int count = dao.insert(splitcount);
		return count;
	}

	/**
	 * 查找拆分匹数
	 * 2016年2月22日
	 * By 王彬彬
	 * @param splitcount 拆分件数
	 * @return 拆分统计信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackSplitCount findByParam(PaybackSplitCount splitcount) {
		PaybackSplitCount paybackSplitCount = dao.get(splitcount);
		return paybackSplitCount;
	}
}
