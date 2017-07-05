package com.creditharmony.loan.channel.bigfinance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.adapter.bean.in.djrcreditor.*;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;

@LoanBatisDao
public interface BfSendDataDao {
	/**
	 * 债权基本信息
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorBaseInfoInBean> getDebtBaseInfo(String loanCode);
	/**
	 * 还款计划
	 * @param contractCode
	 * @return
	 */
	List<DjrSendCreditorRepayPlanInBean> getRepaymentPlanTables(String contractCode);
	/**
	 * 个人资料信息
	 * @param contractCode
	 * @return
	 */
	List<DjrSendCreditorPersonalInBean> getpersonalInfo(String contractCode);
	/**
	 * 配偶资料
	 * @param contractCode
	 * @return
	 */
	List<DjrSendCreditorPersonalPartnerInBean> getSpouseInfo(String contractCode);
	/**
	 * 申请信息
	 * @param contractCode
	 * @return
	 */
	List<DjrSendCreditorAppInBean> getApplicationInfo(String loanCode);
	/**
	 * 共同借款人信息
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorCoborrowerInBean> getCoborrower(String loanCode);
	/**
	 * 信用资料
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorPersonalCreditInBean> getcreditInfo(String loanCode);
	/**
	 * 职业信息
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorPersonalJobInBean> getOccupationalInfo (String loanCode);
	/**
	 * 居住信息
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorPersonalAddrInBean> getResidenceInfo(String loanCode);
	/**
	 * 联系人信息
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorContactInBean> getContactInfo(String loanCode);
	/**
	 * 银行卡资料 
	 * @param loanCode
	 * @return
	 */
	List<DjrSendCreditorBankCardInBean> getBankCardInfo(String loanCode);
	/**
	 * 合同信息
	 * @param contractCode
	 * @return
	 */
	List<DjrSendCreditorContractSubInBean> getContractInfo(Map<String,Object> conditions);
	/**
	 * 是还是新咨询数据
	 * @param loanCode
	 * @return
	 */
	List<Map<String,String>> getInfoIsNew(String loanCode);
}
