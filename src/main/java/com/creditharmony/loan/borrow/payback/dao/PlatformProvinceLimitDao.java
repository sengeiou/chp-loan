package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.FortuneBatisDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PlatformProvinceLimit;

/**
 * 省市平台跳转限制Dao
 * @Class Name PlatformProvinceLimitDao
 * @author 周俊
 * @Create In 2016年3月4日
 */
@LoanBatisDao
public interface PlatformProvinceLimitDao extends CrudDao<PlatformProvinceLimit>{

	/**
	 * 查询
	 * 2016年3月4日
	 * By 周俊
	 * @param map
	 * @return
	 */
	public List<PlatformProvinceLimit> findPlatformProvinceLimit(PlatformProvinceLimit platformProvinceLimit);
}
