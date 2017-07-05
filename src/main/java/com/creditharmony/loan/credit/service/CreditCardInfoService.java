package com.creditharmony.loan.credit.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.credit.dao.CreditCardInfoDao;
import com.creditharmony.loan.credit.entity.CreditCardInfo;

/**
 * 简版信用卡明细信息Service
 * @Class Name CreditCardInfoService
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Service
@Transactional(value="loanTransactionManager",readOnly=true)
public class CreditCardInfoService extends  CoreManager<CreditCardInfoDao,CreditCardInfo> {
	
    /**
     * 根据个人征信简版id检索信用卡信息List
     * 2016年2月2日
     * By zhanghu
     * @param creditReportSimpleId
     * @return 信用卡信息List
     */
	public List<CreditCardInfo> selectByCreditCardInfo(String creditReportSimpleId) {
		CreditCardInfo creditCardInfo = new CreditCardInfo();
		creditCardInfo.setRelationId(creditReportSimpleId);
		return this.dao.selectByCreditCardInfo(creditCardInfo);
	}

	
	/**
	 * 根据个人征信简版id删除信用卡信息
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
	 * 根据个人征信简版id删除信用卡信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param relationId 个人征信简版id
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public CreditCardInfo insertCreditCardInfo(CreditCardInfo creditCardInfo) {
		CreditCardInfo result = new CreditCardInfo();
		if(creditCardInfo != null){
			if(StringUtils.isNotEmpty(creditCardInfo.getId())){
				// 更新数据
				creditCardInfo.preUpdate();
				creditCardInfo.setModifyTime(new Date());
				this.dao.updateByPrimaryKeySelective(creditCardInfo);
				result.setId(creditCardInfo.getId());
				result.setRelationId(creditCardInfo.getRelationId());
				
			}else{
				// 插入数据
				creditCardInfo.preInsert();
				this.dao.insertCreditCardInfo(creditCardInfo);
				result.setId(creditCardInfo.getId());
				result.setRelationId(creditCardInfo.getRelationId());
			}
		}
		return result;
	}

	/**
	 * 根据id删除信用卡信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param id
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int deleteCardInfoById(String id) {
		return this.dao.deleteByPrimaryKey(id);
	}

}
