package com.creditharmony.loan.telesales.dao;

import java.util.List;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.telesales.view.TelesaleConsultSearchView;

/**
 * 信借电销客户咨询列表Dao层
 * @Class Name TelesalesCustomerManagementDAO
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@LoanBatisDao
public interface TelesalesCustomerManagementDAO extends
		CrudDao<TelesaleConsultSearchView> {

	// 查询电销客户咨询列表
	public List<TelesaleConsultSearchView> findTelesalesCustomerList(
			PageBounds pageBounds, TelesaleConsultSearchView consultView);

	// 客服取单列表
	public List<TelesaleConsultSearchView> findTelesaleCustomerOrderList(
			PageBounds pageBounds, TelesaleConsultSearchView consultView);

	// 客户借款信息列表
	public List<TelesaleConsultSearchView> findTelesaleCustomerLoanList(
			PageBounds pageBounds, TelesaleConsultSearchView consultView);

	// 电销待复议列表
	public List<TelesaleConsultSearchView> findTelesaleReconsiderList(
			PageBounds pageBounds, TelesaleConsultSearchView consultView);

	// 电销借款申请管理列表
	public List<TelesaleConsultSearchView> findTelesaleApplyLoanInfoList(
			PageBounds pageBounds, TelesaleConsultSearchView consultView);

	// 查找产品列表
	public List<TelesaleConsultSearchView> findProducts();
    
	// 导出电销借款申请管理列表
	public List<TelesaleConsultSearchView> exportTelesaleApplyLoanInfoList(
			TelesaleConsultSearchView consultView);
}