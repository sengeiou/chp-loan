package com.creditharmony.loan.credit.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.credit.entity.CreditCardInfo;

/**
 * 简版信用卡信息
 * @Class Name CreditCardInfoDao
 * @author 李文勇
 * @Create In 2015年12月31日
 */
@LoanBatisDao
public interface CreditCardInfoDao extends CrudDao<CreditCardInfo> {

	/**
	 * 根据信用卡信息id删除信用卡信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param id 信用卡信息id
	 * @return 执行条数
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 新增信用卡信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param record 信用卡信息
	 * @return 执行条数
	 */
    int insertCreditCardInfo(CreditCardInfo record);
    
    /**
     * 根据信用卡信息检索信用卡信息List
     * 2016年2月2日
     * By zhanghu
     * @param record
     * @return 信用卡信息List
     */
    List<CreditCardInfo> selectByCreditCardInfo(CreditCardInfo record);

	/**
	 * 根据个人征信简版id删除信用卡信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param relationId 个人征信简版id
	 * @return 执行条数
	 */
	int deleteByRelationId(String relationId);
	
	/**
	 * 
	 * 2016年8月17日
	 * By 李文勇
	 * @return
	 */
	public int updateByPrimaryKeySelective(CreditCardInfo record);
}
