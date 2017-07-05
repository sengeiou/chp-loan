/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create By 朱静越
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @author 朱静越
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;

/**
 * 放款审核列表中的打款表导出
 * @Class Name SendMoneyDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface SendMoneyDao extends CrudDao<SendMoneyEx>{
	
	/**
	 * 查询放款审核列表的打款表
	 * 2015年12月21日
	 * By 朱静越
	 * @param id applyId
	 * @return 打款表实体
	 */
	 public List<SendMoneyEx> getMoneyList(Map<String, Object> ids);
	 /**
		 * 查询打款表中申请了门店冻结的数据信息
		 * 2016年3月7日
		 * By 张建雄
		 * @param contractCodes 合同编号
		 * @return 打款表实体
		 */
	 public List<SendMoneyEx> findStoreFrozenList(List<String> contractCodesList);
	 
	 /**
		 * 查询放款审核列表的打款表
		 * 2015年12月21日
		 * By 张建雄
		 * @param loanCodes
		 * @return 打款表实体
		 */
	 public List<SendMoneyEx> getAllMoneyList(List<String>loanCodes);
}