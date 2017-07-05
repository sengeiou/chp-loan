package com.creditharmony.loan.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;

/**
 * 还款业务查询列表公共service
 * @Class Name PaybackService
 * @author zhangfeng
 * @Create In 2015年12月22日
 */
@Service("paybackService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PaybackService extends CoreManager<PaybackDao, PaybackApply> {
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private PaybackDao paybackDao;

	/**
	 * 查询还款申请信息(页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param page
	 * @param paybackApply
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> findApplyPayback(Page<PaybackApply> page,PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("applyPaybackId");
		PageList<PaybackApply> pageList = (PageList<PaybackApply>) dao
				.findApplyPayback(paybackApply, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询门店待办信息(页面)
	 * 2015年12月22日
	 * By 于飞
	 * @param page
	 * @param paybackApply
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> findReturnApplyPayback(Page<PaybackApply> page,PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("applyPaybackId");
		PageList<PaybackApply> pageList = (PageList<PaybackApply>) dao
				.findReturnApplyPayback(paybackApply, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询还款信息(页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param page
	 * @param payback
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Payback> findPayback(Page<Payback> page, Payback payback) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("paybackId");
		PageList<Payback> pageList = (PageList<Payback>) dao.findPayback(payback, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询还款申请信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackApply> findApplyPayback(PaybackApply paybackApply) {
		return dao.findApplyPayback(paybackApply);
	}
	
	/**
	 * 查询还款申请信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackApply> findReturnApplyPayback(PaybackApply paybackApply) {
		return dao.findReturnApplyPayback(paybackApply);
	}

	/**
	 * 查询还款信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param payback
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Payback> findPayback(Payback payback) {
		return dao.findPayback(payback);
	}

	/**
	 * 更新还款状态
	 * 2015年12月22日
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePayback(Payback payback) {
		dao.updatePayback(payback);
	}
	
	/**
	 * 更新还款申请状态
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(PaybackApply paybackApply) {
		dao.updatePaybackApply(paybackApply);
	}
	
	/**
	 * 结清和提前结清更新借款状态
	 * 2016年1月6日
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDictLoanStatus(Payback payback){
		dao.updateDictLoanStatus(payback);
	}
	
//	/**
//	 * 插入还款操作流水历史
//	 * 2016年1月6日
//	 * By zhangfeng
//	 * @param paybackOpe
//	 * @return none
//	 */
//	@Transactional(readOnly = false, value = "loanTransactionManager")
//	public void insertPaybackOpe(PaybackOpe paybackOpe){
//		paybackOpe.preInsert();
//		
//		paybackOpe.setOperator(UserUtils.getUser().getUserCode());
//		paybackOpe.setOperateCode(UserUtils.getUser().getId());
//		paybackOpe.setOperateTime(new Date());
// 		
//		loanStatusHisDao.insertPaybackOpe(paybackOpe);
//	}

	
	/**
	 * 查询当前合同编号 是否是逾期
	 * 2016年2月16日
	 * By guanhongchang
	 * @param payback
	 * @return list
	 */
	public List<Payback> findApplyByOverdue(Payback payback) {
		return dao.findApplyByOverdue(payback);	
	}

	/**
	 * 查询当前合同编号在门店待办是否有待还款的信息
	 * 2016年2月16日
	 * By guanhongchang
	 * @param payback
	 * @return list
	 */
	public List<PaybackApply> findApplyByDealt(PaybackApply paybackApply) {
		return dao.findApplyByDealt(paybackApply);	
	}

	
	/**
	 * pos已匹配列表
	 * 2016年2月16日
	 * By guanhongchang
	 * @param payback
	 * @return list
	 */
	public Page<PaybackApply> findApplyPaybackInfo(Page<PaybackApply> page,
			PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("applyPaybackId");
		PageList<PaybackApply> pageList = (PageList<PaybackApply>) dao.findApplyPaybackInfo(paybackApply, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * pos已匹配列表
	 * 2016年2月16日
	 * By guanhongchang
	 * @param payback
	 * @return list
	 */
	public List<PaybackApply> findApplyPaybackInfo(PaybackApply paybackApply) {
		// TODO Auto-generated method stub
		return dao.findApplyPaybackInfo(paybackApply);
	}
	
	/**
	 * 电催查询还款信息(页面)
	 * 2016年2月25日
	 * By liushikang
	 * @param page
	 * @param payback
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Payback> findPaybackEletr(Payback payback) {
		return dao.findPaybackEletr(payback);
	}

	/**
	 * 电催查询还款申请信息(页面)
	 * 2015年12月22日
	 * By liushikang
	 * @param page
	 * @param paybackApply
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> findApplyElert(Page<PaybackApply> page,PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("applyPaybackId");
		PageList<PaybackApply> pageList = (PageList<PaybackApply>) dao
				.findApplyElert(paybackApply, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询电催还款申请信息(页面)
	 * 2015年12月22日
	 * By zhaojunlei
	 * @param page
	 * @param paybackApply
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Payback> findElectricPayback(Page<Payback> page, Payback payback) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("paybackId");
		PageList<Payback> pageList = (PageList<Payback>) dao.findElectricPayback(payback, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 通过合同编号查询还款信息
	 * @param map
	 * @return  payback
	 */
	public Payback findPaybackByContract(Map<String, Object> map) {
		return paybackDao.selectpayBack(map);
	}

	/**
	 * 匹配查账加锁更新申请表
	 * @param pa
	 * @return int
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updatePaybackApplyReq(PaybackApply pa) {
		return paybackDao.updatePaybackApplyReq(pa);
	}

	@Transactional(readOnly = false, value = "loanTransactionManager")
	public PaybackApply getApplyPaybackReq(Map<String, String> map) {
		return paybackDao.getApplyPaybackReq(map);
	}
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<PaybackApply> findApplyPaybackList(Page<PaybackApply> page,PaybackApply paybackApply){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("applyPaybackId");
		PageList<PaybackApply> pageList = (PageList<PaybackApply>)paybackDao.findApplyPaybackList(pageBounds,paybackApply);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
