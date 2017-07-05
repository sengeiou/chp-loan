package com.creditharmony.loan.borrow.restrictedInlet.dao;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.restrictedInlet.entity.RestrictedInlet;

/**
 * 高危线设置
 * @Class Name RestrictedInletDao
 * @author 管洪昌
 * @Create In 2016年4月20日
 */
@LoanBatisDao
public interface RestrictedInletDao extends CrudDao<RestrictedInlet>{
	
	
	PageList<RestrictedInlet> selectRestrictedInletList(PageBounds pageBounds,
			RestrictedInlet restrictedInlet);

	PageList<RestrictedInlet> selectStoreRestricList(PageBounds pageBounds,
			RestrictedInlet restrictedInlet);

	void updateStoreRestrict(RestrictedInlet restrictedInlet);

	PageList<RestrictedInlet> selectStroreList(PageBounds pageBounds,
			RestrictedInlet restrictedInlet);

	void updateStore(RestrictedInlet restrictedInlet);

	RestrictedInlet selectStr(RestrictedInlet restrictedInlet);

}
