package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfoCoborrower;

@LoanBatisDao
public interface LoanInfoCoborrowerDao extends CrudDao<LoanInfoCoborrower> {

	/**
	 * 根据自然人保证人ID查询自然人保证人借款意愿
	 * By	任志远	2016年9月7日
	 * @param param	
	 * @return
	 */
	public LoanInfoCoborrower queryLoanInfoCoborrowerByRid(Map<String, Object> param);

	/**
	 * 根据自然人保证人ID删除借款意愿
	 * @param param
	 */
	public void deleteByRid(Map<String, Object> param);
}
