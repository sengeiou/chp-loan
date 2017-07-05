package com.creditharmony.loan.borrow.applyinfo.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 门店复核dao
 * @Class Name StoreReviewDao
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
@LoanBatisDao
public interface StoreReviewDao extends CrudDao<LoanCustomer> {
	
}
