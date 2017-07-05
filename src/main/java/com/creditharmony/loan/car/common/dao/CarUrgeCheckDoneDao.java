package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeCheckDoneEx;

/**
 * 催收服务费查账已办列表处理
 * @Class Name UrgeCheckDoneDao
 * @author 朱静越
 * @Create In 2016年3月2日
 */
@LoanBatisDao
public interface CarUrgeCheckDoneDao extends CrudDao<CarUrgeCheckDoneEx> {
	
	/**
	 * 催收服务费查账已办列表页面初始化
	 * 2016年3月2日
	 * By 朱静越
	 * @param urgeCheckDoneEx
	 * @return
	 */
	public List<CarUrgeCheckDoneEx> selCheckDone(PageBounds pageBounds,CarUrgeCheckDoneEx urgeCheckDoneEx);
	
	/**
	 * 催收服务费根据查询条件查询列表，不带分页
	 * 2016年3月3日
	 * By 朱静越
	 * @param urgeCheckDoneEx 查询条件
	 * @return 集合
	 */
	public List<CarUrgeCheckDoneEx> selCheckDone(CarUrgeCheckDoneEx urgeCheckDoneEx);
	
}
