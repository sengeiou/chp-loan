package com.creditharmony.loan.borrow.restrictedInlet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.poscard.dao.PosBacktageDao;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.borrow.restrictedInlet.dao.RestrictedInletDao;
import com.creditharmony.loan.borrow.restrictedInlet.entity.RestrictedInlet;
/**
 * 限制高危列表后台数据操作
 * @Class Name RestrictedInletService
 * @author 管洪昌
 * @Create In 2016年1月20日
 */
@Service("RestrictedInletService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class RestrictedInletService extends CoreManager<RestrictedInletDao, RestrictedInlet>{
	@Autowired
	private RestrictedInletDao restrictedInletDao;
	
	/**
	 * 查询列表
	 * @Class Name selectPosBacktageList
	 * @author 管洪昌
	 * @Create In 2016年4月20日
	 */
	public Page<RestrictedInlet> selectRestrictedInletList(
			Page<RestrictedInlet> page, RestrictedInlet restrictedInlet) {
		PageBounds pageBounds = new PageBounds(1,800);
		PageList<RestrictedInlet> pageList = (PageList<RestrictedInlet>)restrictedInletDao.selectRestrictedInletList(pageBounds, restrictedInlet);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
     * 修改逾期高危险设置
     * By 管洪昌
     * @param restrictedInlet
     * @return
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateConsultRestrict(RestrictedInlet restrictedInlet) {
		restrictedInlet.preUpdate();
		dao.update(restrictedInlet);
	}
	
	/**
	 * 查询门店逾期高危线
	 * @Class Name selectPosBacktageList
	 * @author 管洪昌
	 * @Create In 2016年4月20日
	 */
	public Page<RestrictedInlet> selectStoreRestricList(
			Page<RestrictedInlet> page, RestrictedInlet restrictedInlet) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<RestrictedInlet> pageList = (PageList<RestrictedInlet>)restrictedInletDao.selectStoreRestricList(pageBounds, restrictedInlet);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	
	/**
	 * 修改自定义高危线设置
	 * @Class Name selectPosBacktageList
	 * @author 管洪昌
	 * @Create In 2016年4月20日
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateStoreRestrict(RestrictedInlet restrictedInlet) {
		dao.updateStoreRestrict(restrictedInlet);
	}

	/**
	 * 门店经理查询
	 * @Class Name selectPosBacktageList
	 * @author 管洪昌
	 * @Create In 2016年4月20日
	 */
	public Page<RestrictedInlet> selectStroreList(Page<RestrictedInlet> page,
			RestrictedInlet restrictedInlet) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<RestrictedInlet> pageList = (PageList<RestrictedInlet>)restrictedInletDao.selectStroreList(pageBounds, restrictedInlet);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateStore(RestrictedInlet restrictedInlet) {
		dao.updateStore(restrictedInlet);
	}
	
	
	public RestrictedInlet selectStr(RestrictedInlet restrictedInlet) {
		// TODO Auto-generated method stub
		return dao.selectStr(restrictedInlet);
	}
	



}
