package com.creditharmony.loan.borrow.payback.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.CentralizedDeductionDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 集中划扣已办、划扣已办
 * Service实现支持类
 * @Class Name CentralizedDeductionService 
 * @author 李强
 * @Create In 2015年12月4日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CentralizedDeductionService {
	@Autowired
	private CentralizedDeductionDao centralizedDeductionToDao;

	/**
	 * 查询集中划扣、划扣 已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param paybackApply
	 * @return 集中划扣、划扣已办集合对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> allCentralizedDeductionList(Page<PaybackApply> page,PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackApply> pageList = (PageList<PaybackApply>)centralizedDeductionToDao.allCentralizedDeductionList(pageBounds,paybackApply);
		PageUtil.convertPage(pageList, page);
		return page;
	};
	public List<PaybackApply> allCentralizedDeductionList(PaybackApply paybackApply){
		return centralizedDeductionToDao.allCentralizedDeductionList(paybackApply);
	}
	/**
	 * 集中划扣 查看页面
	 * 2015年12月17日
	 * By 李强
	 * @param paybackApply
	 * @return 集中划扣已办单条数据对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackApply seeCentralizedDeduction(String bId) {
		return centralizedDeductionToDao.seeCentralizedDeduction(bId);
	};
	
	/**
	 * 导出集中划扣已办数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 集中划扣已办单条数据对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackApply exportExcel(String id){
		return centralizedDeductionToDao.exportExcel(id);
	};
	
	/**
	 * 导出还款划扣已办数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 集中还款划扣已办单条数据对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackApply redlineExportExcel(String id){
		return centralizedDeductionToDao.redlineExportExcel(id);
	}

	/**
	 * 集中划扣已办
	 * 2016年4月4日
	 * By 翁私
	 * @param page
	 * @param paybackApply
	 * @return
	 */
	public Page<PaybackApply> centerDeductionAgencyList(
			Page<PaybackApply> page, PaybackApply paybackApply) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackApply> pageList = (PageList<PaybackApply>)centralizedDeductionToDao.centerDeductionAgencyList(pageBounds,paybackApply);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 分页查询划扣已办
	 * @param paybackApply
	 * @param request
	 * @param response
	 * @return
	 */
	public Page<PaybackApply> getDeductionListPage(PaybackApply paybackApply,
			HttpServletRequest request, HttpServletResponse response) {
		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		paybackApply.setLimit(page.getPageSize());
		if (page.getPageNo() <= 1) {
			paybackApply.setOffset(0);
		} else {
			paybackApply.setOffset((page.getPageNo() - 1) * page.getPageSize());
		}
		List<PaybackApply> pageList = null;
		long cnt = centralizedDeductionToDao.getDhkCnt(paybackApply);
		if(cnt>0){
			page.setCount(cnt);
			pageList = centralizedDeductionToDao.allCentralizedDeductionList(paybackApply);
			page.setList(pageList);
		}else{
			page.setCount(0);
			pageList = new ArrayList<PaybackApply>();
			page.setList(pageList);
		}
		return page;
	};
}
