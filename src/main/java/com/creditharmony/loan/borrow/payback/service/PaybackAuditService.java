package com.creditharmony.loan.borrow.payback.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.PaybackAuditDao;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackAuditEx;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 还款查账已办
 * @Class Service实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月8日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PaybackAuditService {

	@Autowired
	private PaybackAuditDao paybackAuditHavaTodoDao;

	/**
	 * 还款查账已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param paybackAudit
	 * @return 还款查账已办数据集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackAuditEx> allPaybackAuditHavaTodoList(Page<PaybackAuditEx> page,PaybackAuditEx paybackAudit) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("payBackId");
		PageList<PaybackAuditEx> pageList = (PageList<PaybackAuditEx>)paybackAuditHavaTodoDao.allPaybackAuditHavaTodoList(pageBounds,paybackAudit);
		PageUtil.convertPage(pageList, page);
		return page;
	};

	/**
	 * 还款查账已办 查看
	 * 2015年12月17日
	 * By 李强
	 * @param paybackAudit
	 * @return 还款查账已办单条数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackAuditEx seePaybackAuditHavaTodo(PaybackAuditEx paybackAudit) {
		return paybackAuditHavaTodoDao.seePaybackAuditHavaTodo(paybackAudit);
	};
	
	/**
	 * 还款查账已办 查看页面数据  汇款账号信息
	 * 2016年1月13日
	 * By 李强
	 * @param payBackId
	 * @return 汇款账号信息List
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackAuditEx> seePaybackAuditHavaList(String payBackId){
		return paybackAuditHavaTodoDao.seePaybackAuditHavaList(payBackId);
	};
	
	/**
	 * 导出还款查账已办数据表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 还款查账已办单条数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackAuditEx exportExcel(PaybackAuditEx paybackAuditEx){
		return paybackAuditHavaTodoDao.exportExcel(paybackAuditEx);
	};
	
	/**
	 * 查询存入账户信息
	 * 2016年5月16日
	 * By 赵金平
	 * @param depositFlag
	 * @return
	 */
	@Transactional(readOnly = true ,value = "loanTransactionManager")
	public List<MiddlePerson> selectAllMiddle (String depositFlag){
		return paybackAuditHavaTodoDao.selectAllMiddle(depositFlag);
	}

	/**
	 * 查询POS详细记录
	 * 2016年5月16日
	 * By 赵金平
	 * @param depositFlag
	 * @return
	 */
	public List<PaybackAuditEx> seePaybackAuditHavaListPos(String payBackId) {
		// TODO Auto-generated method stub
		return paybackAuditHavaTodoDao.seePaybackAuditHavaListPos(payBackId);
	}
}
