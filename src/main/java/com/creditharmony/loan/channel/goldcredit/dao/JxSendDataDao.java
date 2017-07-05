package com.creditharmony.loan.channel.goldcredit.dao;

import java.util.List;

import com.creditharmony.adapter.service.jxcredit.bean.CreditCreditorsRightsOutBean;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
@LoanBatisDao
public interface JxSendDataDao extends CrudDao<CreditCreditorsRightsOutBean>{
	/**
	 * 金信网的二次债权推送
	 * @param contractCodesList 合同编号集合
	 * @return 返回金信网要的数据信息的集合封装
	 */
	public List<CreditCreditorsRightsOutBean> findJINXINPush(List<String> contractCodesList);
	/**
	 * 金信网退回的数据信息，根据合同编号去查询loanCode
	 * @param contractCodesList 合同编号集合
	 * @return 借款编号的集合
	 */
	public List<String> findJINXINSendBack(List<String> contractCodesList);
}
