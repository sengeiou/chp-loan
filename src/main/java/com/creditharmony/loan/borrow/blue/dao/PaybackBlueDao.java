package com.creditharmony.loan.borrow.blue.dao;

import java.util.List;
import java.util.Map;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlue;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlueAmountEx;

/**
 * 蓝补账号管理列表信息Dao
 * @Class Name PaybackBlueDao
 * @author 侯志斌
 * @Create In 2016年3月1日
 */
@LoanBatisDao
public interface PaybackBlueDao extends CrudDao<PaybackBlue>{

	/**
	 * 查询蓝补账号管理列表信息
	 * 2016年3月1日
	 * By 侯志斌
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public List<PaybackBlue> findPayback(PaybackBlue paybackBlue, PageBounds pageBounds);
	
	/**
	 * 查询蓝补账号管理列表信息(count)
	 * 
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public int cnt(PaybackBlue paybackBlue);
	
	/**
	 * 查询另一个蓝补账号
	 * 2016年3月3日
	 * By 侯志斌
	 * @param params
	 * @return String
	 */
	public String findPaybackByCustomer(Map<String, Object> params);
	
	/**
	 * 根据查询条件查询进行导出的单子
	 * 2016年3月1日
	 * By 侯志斌
	 * @param paybackBlueAmountEx
	 * @return list
	 */
	public List<PaybackBlueAmountEx> selectPaybackBlueAmoun(PaybackBlueAmountEx paybackBlueAmountEx);

	/**
	 * 查询蓝补对账单
	 * 2016年3月4日
	 * By 侯志斌
	 * @param paybackBlueAmountEx
	 * @param pageBounds
	 * @return list
	 */
	public PageList<PaybackBlueAmountEx> selectPaybackBlueAmoun(PaybackBlueAmountEx paybackBlueAmountEx, PageBounds pageBounds);
	
	/**
	 * 获取客户基本信息
	 * 2016年3月4日
	 * By 侯志斌
	 * @param paybackBlue
	 * @return list
	 */
	public List<Map<String,Object>> getCustomer(PaybackBlue paybackBlue);

	/**
	 * get payback
	 * @param pb
	 * @return PaybackBlue
	 */
	public PaybackBlue findPayback(PaybackBlue pb);
}
