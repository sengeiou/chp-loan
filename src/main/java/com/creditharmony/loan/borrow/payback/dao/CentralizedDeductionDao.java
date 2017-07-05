package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;


import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * @Class Dao实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月4日
 */
@LoanBatisDao
public interface CentralizedDeductionDao extends
		CrudDao<CentralizedDeductionDao> {

	/**
	 * 集中划扣 、划扣 已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param paybackApply
	 * @return 集中划扣、划扣已办List
	 */
	public List<PaybackApply> allCentralizedDeductionList(PageBounds pageBounds,
			PaybackApply paybackApply);
	public List<PaybackApply> allCentralizedDeductionList(PaybackApply paybackApply);
	
	/**
	 * 集中划扣 查看页面
	 * 2015年12月17日
	 * By 李强
	 * @param paybackApply
	 * @return 集中划扣已办单条数据对象 
	 */
	public PaybackApply seeCentralizedDeduction(String bId);
	
	/**
	 * 导出集中划扣数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 集中划扣已办单挑数据对象
	 */
	public PaybackApply exportExcel(String id);
	
	/**
	 * 导出还款划扣数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 集中还款划扣已办单挑数据对象
	 */
	public PaybackApply redlineExportExcel(String id);

	/**
	 * 集中划扣 
	 * 2015年12月17日
	 * By 翁私
	 * @param pageBounds
	 * @param paybackApply
	 * @return 集中划扣List
	 */
	public List<PaybackApply> centerDeductionAgencyList(PageBounds pageBounds, PaybackApply paybackApply);
	
	/**
	 * 集中划扣 count
	 * 
	 * @param  paybackApply
	 * @return count
	 */
	public int centerDeductionAgencyListCnt(PaybackApply paybackApply);
	
	/**
	 * 集中划扣 导出
	 * 2015年12月17日
	 * By 翁私
	 * @param pageBounds
	 * @param paybackApply
	 * @return 集中划扣List
	 */
	public PaybackApply centerExportExcel(String id);
	
	/**
	 * 查询代还款条数
	 * @param paybackApply
	 * @return
	 */
	public long getDhkCnt(PaybackApply paybackApply);
}
