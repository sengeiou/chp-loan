package com.creditharmony.loan.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.LoanBank;
/**
 * 账户信息Dao
 * @Class Name LoanBankDao
 * @author zhangping
 * @Create In 2015年11月29日
 */
@LoanBatisDao
public interface LoanBankDao extends CrudDao<LoanBank> {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(LoanBank record);

    int insertSelective(LoanBank record);

    LoanBank selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(LoanBank record);

    int updateByPrimaryKey(LoanBank record);

	LoanBank selectByLoanCode(String loanCode);
	/**
	 *通过主键查询  
	 * 
	 */
	LoanBank selectByPrimaryKey(String id);

	List<LoanBank> findAllList(LoanBank record);

	/**
	 * 更新LoanBank TopFlag
	 * 2015年12月29日
	 * By zhangfeng
	 * @param loanBank
	 */
	void updateTopFlag(LoanBank loanBank);   
	
	/**
	 * 更新卡号维护类型
	 * 2016年4月8日
	 * By 王彬彬
	 * @param loanBank
	 */
	public void updateMaintainType(LoanBank loanBank);
	
	/**
	 * 根据loan_code和还款标识进行更新
	 * 2017年4月10日
	 * By 朱静越
	 * @param loanBank
	 */
	public void updateByLoanCode(LoanBank loanBank);
	
	public List<LoanBank> queryCertification(String loanCode);

	void updateBankByLoanCode(LoanBank bank);

	/**
	 * 查询支行
	 * @param bank
	 * @param pageBounds
	 * @return
	 */
	PageList<LoanBank> findBanklist(LoanBank bank, PageBounds pageBounds);

	List<LoanBank> queryCertificationById(String oldAccountId);

	void updateBankById(LoanBank bankquery);
	/**
	 * 修改银行账户和授权人
	 */
	void updateAccountNameAndAuthorizer(LoanBank loanBank);
}