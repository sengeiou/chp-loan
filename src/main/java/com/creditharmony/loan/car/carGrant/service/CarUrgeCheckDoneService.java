package com.creditharmony.loan.car.carGrant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.lend.type.LendConstants;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeCheckDoneEx;
import com.creditharmony.loan.car.common.dao.CarUrgeCheckDoneDao;


@Service("carUrgeCheckDoneService")
public class CarUrgeCheckDoneService extends CoreManager<CarUrgeCheckDoneDao ,CarUrgeCheckDoneEx> {
	
	/**
	 * 催收服务费查账已办列表初始化
	 * 2016年3月2日
	 * By 朱静越
	 * @param page
	 * @param urgeCheckDoneEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarUrgeCheckDoneEx> selCheckDone(Page<CarUrgeCheckDoneEx> page,CarUrgeCheckDoneEx urgeCheckDoneEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		// 设置车借产品
		urgeCheckDoneEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
		PageList<CarUrgeCheckDoneEx> pageList = (PageList<CarUrgeCheckDoneEx>)dao.selCheckDone(pageBounds, urgeCheckDoneEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询
	 * 2016年3月3日
	 * By 朱静越
	 * @param urgeCheckDoneEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarUrgeCheckDoneEx> queryDoneList(CarUrgeCheckDoneEx urgeCheckDoneEx){
		// 设置车借产品
		urgeCheckDoneEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
		return dao.selCheckDone(urgeCheckDoneEx);
	}

}
