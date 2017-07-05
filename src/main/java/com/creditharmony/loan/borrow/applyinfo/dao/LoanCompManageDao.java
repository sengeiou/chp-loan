package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;

/**
 * 新版的经营信息dao
 * @author Yong-Wang
 *
 */
@LoanBatisDao
public interface LoanCompManageDao extends CrudDao<LoanCompManage>{
	
	/*
	 * 根据loanCode查询经营信息 
	 */
	public LoanCompManage findCompManageByLoanCode(String loanCode);
	
	/**
	 * 根据营业执照注册号 查借款笔数
	 * By 任志远 2017年1月5日
	 *
	 * @param businessLicenseRegisterNum
	 * @return
	 */
	public Long queryLoanCompManageCountByBusinessLicenseRegisterNum(Map<String, Object> param);
}
