package com.creditharmony.loan.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.credit.dao.CreditUnclearedNotesDiscountedDao;
import com.creditharmony.loan.credit.entity.CreditUnclearedNotesDiscounted;

/**
 * 企业征信_未结清票据贴现Service
 * @Class Name CreditUnclearedNotesDiscountedService
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Service
@Transactional(value="loanTransactionManager",readOnly=true)
public class CreditUnclearedNotesDiscountedService extends  CoreManager<CreditUnclearedNotesDiscountedDao,CreditUnclearedNotesDiscounted> {

	/**
	 * 根据id删除信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param id 
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int deleteByPrimaryKey(String id) {
		return this.dao.deleteByPrimaryKey(id);
	}
	
	/**
	 * 新增信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param record 
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int insertCreditUnclearedNotesDiscounted(CreditUnclearedNotesDiscounted record) {
		// 初始化默认数据
		record.preInsert();
		return this.dao.insertCreditUnclearedNotesDiscounted(record);
	}
    
    /**
     * 根据借款编码检索信息List
     * 2016年2月2日
     * By zhanghu
     * @param loanCode 借款编码
     * @return 信息List
     */
	public List<CreditUnclearedNotesDiscounted> selectByLoanCode(String loanCode) {
		return this.dao.selectByLoanCode(loanCode);
	}

	/**
	 * 根据借款编码删除信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param loanCode 借款编码
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int deleteByLoanCode(String loanCode) {
		return this.dao.deleteByLoanCode(loanCode);
	}
	
}
