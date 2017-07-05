package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.Split;

/**
 * 信息占比dao层
 * @Class Name ContractDao
 * @author 申阿伟
 * @Create In 2017年02月20日
 */
@LoanBatisDao
public interface SplitDao extends CrudDao<Split>{
	/**
	 * 查询当前有效占比
	 * @author 申阿伟
	 * @return Split
	 */
	public Split findBySplit();
	
	/**
	 * 更新历史占比为0
	 * @author 申阿伟
	 * @return int
	 */
	public int updateSplit();
	
	/**
	 * 新增占比
	 * @author 申阿伟
	 * @return int
	 */
	public int saveSpilt(Split split);
}
