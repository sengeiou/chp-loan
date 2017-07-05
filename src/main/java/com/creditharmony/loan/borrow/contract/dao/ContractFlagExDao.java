package com.creditharmony.loan.borrow.contract.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractFlagEx;

/**
 * 操作合同标识
 * @Class Name ContractExDao
 * @author 张灏
 * @Create In 2015年11月30日
 */
@LoanBatisDao
public interface ContractFlagExDao extends CrudDao<ContractFlagEx> {
	
	/**
	 * 查询在合同审核阶段是否有退回的情况  
	 * 2015年11月30日
	 * By 张灏
	 * @param param
	 * @return ContractFlagEx
	 */
	public ContractFlagEx findHasBack(Map<String,Object> param);
	
	/**
	 * 查询当前业务列表所处的上一个阶段  
	 * 2015年11月30日
	 * By 张灏
	 * @param applyId
	 * @return ContractFlagEx
	 */
	public ContractFlagEx findLastStatus(String applyId);
	
	/**
	 * 判断是否为第一单 
	 * 2015年11月30日
	 * By 张灏
	 * @param map
	 * @return ContractFlagEx
	 */
	public ContractFlagEx isOld(Map<String,Object> map);
}
