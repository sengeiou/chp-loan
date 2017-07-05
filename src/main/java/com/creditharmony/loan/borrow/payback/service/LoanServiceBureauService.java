package com.creditharmony.loan.borrow.payback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.LoanServiceBureauDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.ex.LoanServiceBureauEx;

/**
 * @Class Service实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月9日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class LoanServiceBureauService {

	@Autowired
	private LoanServiceBureauDao loanServiceBureauHavaToDao;

	/**
	 * 集中划扣申请已办列表页面
	 * 2016年3月29日
	 * By zhaojinping
	 * @param page
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<LoanServiceBureauEx> centerApplyHaveToList(Page<LoanServiceBureauEx> page,LoanServiceBureauEx loanServiceBureau){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		page.setCountBy("ids");
		PageList<LoanServiceBureauEx> pageList = (PageList<LoanServiceBureauEx>)loanServiceBureauHavaToDao.centerApplyHaveToList(pageBounds,loanServiceBureau);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 待提前结清确认已办列表页面 
	 * 2016年3月29日
	 * By zhaojinping
	 * @param page
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<LoanServiceBureauEx> earlyExamHavetoList(Page<LoanServiceBureauEx> page,LoanServiceBureauEx loanServiceBureau){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("ids");
		PageList<LoanServiceBureauEx> pageList = (PageList<LoanServiceBureauEx>)loanServiceBureauHavaToDao.earlyExamHavetoList(pageBounds,loanServiceBureau);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	

	/**
	 * 门店已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<LoanServiceBureauEx> allStoresAlreadyDoList(Page<LoanServiceBureauEx> page,
			LoanServiceBureauEx loanServiceBureau) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("ids");
		PageList<LoanServiceBureauEx> pageList = (PageList<LoanServiceBureauEx>)loanServiceBureauHavaToDao.allStoresAlreadyDoList(pageBounds,loanServiceBureau);
		PageUtil.convertPage(pageList, page);
		return page;
	};

	/**
	 * 门店已办 单个查看(还款用途为：提前结清)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public LoanServiceBureauEx seeStoresAlreadyDo(
			LoanServiceBureauEx loanServiceBureau) {
		return loanServiceBureauHavaToDao.seeStoresAlreadyDo(loanServiceBureau);
	};

	/**
	 * 门店已办 单个查看(还款用途为：非提前结清)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public LoanServiceBureauEx seeStoresAlreadyDos(
			LoanServiceBureauEx loanServiceBureau) {
		return loanServiceBureauHavaToDao
				.seeStoresAlreadyDos(loanServiceBureau);
	};

	/**
	 * 门店已办 单个查看(还款用途为：非提前结清 汇款/转账还款信息)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackTransferInfo> seePayBackTrans(String ids) {
		return loanServiceBureauHavaToDao.seePayBackTrans(ids);
	}
	

	
	/**
	 * 电催已办列表
	 * 2016年2月25日
	 * By liushikang
	 * @param page
	 * @param loanServiceBureau
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<LoanServiceBureauEx> allStoresAlreadyDoListEl(Page<LoanServiceBureauEx> page,
			LoanServiceBureauEx loanServiceBureau) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("ids");
		PageList<LoanServiceBureauEx> pageList = (PageList<LoanServiceBureauEx>)loanServiceBureauHavaToDao.allStoresAlreadyDoListEl(pageBounds,loanServiceBureau);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * POS刷卡查账列表
	 * 2016年2月25日
	 * By guanhongchang
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackTransferInfo> seePayBackTransPos(String ids) {
		return loanServiceBureauHavaToDao.seePayBackTransPos(ids);
	};
}
