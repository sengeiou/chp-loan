package com.creditharmony.loan.channel.goldcredit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.service.jxcredit.bean.CreditCreditorsRightsOutBean;
import com.creditharmony.loan.channel.goldcredit.dao.JxSendDataDao;

@Service
public class JxSendDataService {
	@Autowired
	private JxSendDataDao jxSendDataDao;
	
	/**
	 * 金信网的二次债权推送
	 * @param contractCodesList 合同编号集合
	 * @return 返回金信网要的数据信息的集合封装
	 */
	public List<CreditCreditorsRightsOutBean> findJINXINPush(List<String> contractCodesList){
		List<CreditCreditorsRightsOutBean> outBeanList = jxSendDataDao.findJINXINPush(contractCodesList);
		return outBeanList;
	}
	/**
	 * 金信网退回的数据信息，根据合同编号去查询loanCode
	 * @param contractCodesList 合同编号集合
	 * @return 借款编号的集合
	 */
	public List<String> findJINXINSendBack(List<String> contractCodesList){
		return jxSendDataDao.findJINXINSendBack(contractCodesList);
	}
}
