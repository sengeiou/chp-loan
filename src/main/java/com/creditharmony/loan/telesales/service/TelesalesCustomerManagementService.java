package com.creditharmony.loan.telesales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.transate.dao.TransateDao;
import com.creditharmony.loan.telesales.dao.TelesalesCustomerManagementDAO;
import com.creditharmony.loan.telesales.view.TelesaleConsultSearchView;

/**
 * 信借电销客户咨询列表Service层
 * @Class Name TelesalesCustomerManagementService
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class TelesalesCustomerManagementService extends
		CoreManager<TelesalesCustomerManagementDAO, TelesaleConsultSearchView> {

	@Autowired
	private TelesalesCustomerManagementDAO telesalesCustomerDao;
	@Autowired
	private TransateDao transateDao;

	/**
	 * 信借电销客户咨询查询
	 * 2016年3月11日
	 * By 周怀富
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<TelesaleConsultSearchView> findTelesalesCustomerList(
			Page<TelesaleConsultSearchView> page,
			TelesaleConsultSearchView consultview) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<TelesaleConsultSearchView> pageList = (PageList<TelesaleConsultSearchView>) dao
				.findTelesalesCustomerList(pageBounds, consultview);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 门店客服取单查询
	 * 2016年3月11日
	 * By 周怀富
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<TelesaleConsultSearchView> findTelesaleCustomerOrderList(
			Page<TelesaleConsultSearchView> page,
			TelesaleConsultSearchView consultview) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<TelesaleConsultSearchView> pageList = (PageList<TelesaleConsultSearchView>) dao
				.findTelesaleCustomerOrderList(pageBounds, consultview);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询客户历史借款记录
	 * 2016年3月11日
	 * By 周怀富
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<TelesaleConsultSearchView> findTelesaleCustomerLoanList(
			Page<TelesaleConsultSearchView> page,
			TelesaleConsultSearchView consultview) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<TelesaleConsultSearchView> pageList = (PageList<TelesaleConsultSearchView>) dao
				.findTelesaleCustomerLoanList(pageBounds, consultview);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询电销复议列表
	 * 2016年3月11日
	 * By 周怀富
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<TelesaleConsultSearchView> findTelesaleReconsiderInfoList(
			Page<TelesaleConsultSearchView> page,
			TelesaleConsultSearchView consultview) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<TelesaleConsultSearchView> pageList = (PageList<TelesaleConsultSearchView>) dao
				.findTelesaleReconsiderList(pageBounds, consultview);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询电销借款申请管理
	 * 2016年3月11日
	 * By 周怀富
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Page<TelesaleConsultSearchView> findTelesaleApplyLoanInfoList(
			Page<TelesaleConsultSearchView> page,
			TelesaleConsultSearchView consultview) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<TelesaleConsultSearchView> pageList = (PageList<TelesaleConsultSearchView>) dao
				.findTelesaleApplyLoanInfoList(pageBounds, consultview);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查找产品列表
	 * 2016年3月11日
	 * By 周怀富
	 * @return List
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<TelesaleConsultSearchView> finProductList() {
		return telesalesCustomerDao.findProducts();
	}
	
	/**
	 * 导出电销借款申请管理列表
	 * 2016年3月14日
	 * By 周怀富
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<TelesaleConsultSearchView> exportTelesaleApplyLoanList(TelesaleConsultSearchView consultview){
		return telesalesCustomerDao.exportTelesaleApplyLoanInfoList(consultview);
	}
}
