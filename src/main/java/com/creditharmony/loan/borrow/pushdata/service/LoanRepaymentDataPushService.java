/**
 * 
 */
package com.creditharmony.loan.borrow.pushdata.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.pushdata.dao.PaybackListMapper;
import com.creditharmony.loan.borrow.pushdata.entity.PaybackList;
import com.creditharmony.loan.borrow.pushdata.util.Constants;

/**
 * 汇金待还款数据推送
 * 
 * @author 施大勇 2016年1月15日
 *
 */
@Service
public class LoanRepaymentDataPushService {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory
			.getLogger(LoanRepaymentDataPushService.class);
	/**
	 * 还款_待还款列表
	 */
	@Autowired
	private PaybackListMapper paybackListMapper;

	/**
	 * 更新待还款列表 2016年5月4日 By zhaojunlei
	 * 
	 * @param i
	 * @param modata
	 * @return int
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int updatePaybacklist(int i, PaybackList modata) {
		modata.setId(IdGen.uuid());
		modata.setStatus(YESNO.NO.getCode());
		modata.setModifyBy(Constants.BATCH_NAME);
		modata.setModifyTime(new Date());
		modata.setCreateBy(Constants.BATCH_NAME);
		modata.setCreateTime(new Date());
		int ret = this.updatePaybackList(modata);
		if (Boolean.FALSE == (ret > 0)) {
			logger.debug("待还款列表数据插入失败。失败数据合同编号为  ===> "
					+ modata.getCustomerCode());
			return i;
		}
		return i;
	}

	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int updatePaybackList(PaybackList modata) {
		return paybackListMapper.insertSelective(modata);
	}

}
