package com.creditharmony.loan.credit.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.credit.dao.CreditLoanInfoDao;
import com.creditharmony.loan.credit.entity.CreditLoanInfo;


/**
 * 简版贷款信息Service
 * @Class Name CreditLoanInfoService
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Service
@Transactional(value="loanTransactionManager",readOnly=true)
public class CreditLoanInfoService extends  CoreManager<CreditLoanInfoDao,CreditLoanInfo>{

	
    /**
     * 根据个人征信简版id检索贷款信息List
     * 2016年2月2日
     * By zhanghu
     * @param creditReportSimpleId
     * @return 贷款信息List
     */
	public List<CreditLoanInfo> selectByCreditLoanInfo(String creditReportSimpleId) {
		CreditLoanInfo creditLoanInfo = new CreditLoanInfo();
		creditLoanInfo.setRelationId(creditReportSimpleId);
		return this.dao.selectByCreditLoanInfo(creditLoanInfo);
	}

	
	/**
	 * 根据个人征信简版id删除贷款信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param relationId 个人征信简版id
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int deleteByRelationId(String relationId) {
		return this.dao.deleteByRelationId(relationId);
	}
	
	/**
	 * 根据个人征信简版id删除贷款信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param relationId 个人征信简版id
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public CreditLoanInfo insertCreditLoanInfo(CreditLoanInfo creditLoanInfo) {
		CreditLoanInfo result = new CreditLoanInfo();
		if(creditLoanInfo != null){
			if(StringUtils.isNotEmpty(creditLoanInfo.getId())){
				creditLoanInfo.preUpdate();
				this.dao.updateByPrimaryKeySelective(creditLoanInfo);
				result.setId(creditLoanInfo.getId());
				result.setRelationId(creditLoanInfo.getRelationId());
			}else{
				// 初始化默认数据
				creditLoanInfo.preInsert();
				this.dao.insertCreditLoanInfo(creditLoanInfo);
				result.setId(creditLoanInfo.getId());
				result.setRelationId(creditLoanInfo.getRelationId());
			}
		}
		return result;
	}

	/**
	 * 根据id删除贷款信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param id
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int deleteLoanInfoById(String id) {
		return this.dao.deleteByPrimaryKey(id);
	}

	
}
