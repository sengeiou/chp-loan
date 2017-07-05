package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.loan.common.dao.RepaymentDateDao;
import com.creditharmony.loan.common.entity.GlBill;

/**
 * @Class Name RepaymentDateService
 * @author 翁私
 * @Create In 2016年03月26日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class RepaymentDateService {
	@Autowired
	public RepaymentDateDao dateDao;
	
	/**
	 * 查询还款日 2016年3月26日 By 翁私
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<GlBill> getRepaymentDate() {
		List<GlBill> list = dateDao.getRepaymentDate();
		return list;
	}

}
