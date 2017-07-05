package com.creditharmony.loan.credit.dao;


import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.CreditReportRisk;

/**
 * @Class Name CreditReportDao
 * @author 黄维
 * @Create In 2015年12月2日
 * 征信报告
 */
@LoanBatisDao
public interface CreditReportDao extends CrudDao<CreditReportRisk> {

	/**
	 * 根据借款编号获取征信报告信息
	 * 2015年12月2日
	 * By 黄维
	 * @param loanCode
	 */
	public List<CreditReportRisk> getPersonCreditReportDetailedByCode(CreditReportRisk creditReportRisk);

	/**
	 * 2015年12月4日
	 * By 黄维
	 * @param creditReportRisk 
	 */
	public int asyncSaveCreditReportRiskInfo(CreditReportRisk creditReportRisk);
	
	/**
	 * 根据ID更新数据
	 * 2015年12月12日
	 * By 李文勇
	 * @param creditReportRiskEx
	 * @return
	 */
	public int updataById(CreditReportRisk creditReportRisk);
	
	/**
	 * 根据借款编号，和征信报告版本查询ID
	 * 2015年12月14日
	 * By 李文勇
	 * @param creditReportRisk
	 * @return
	 */
	public CreditReportRisk getIdByVersion(CreditReportRisk creditReportRisk);
	
	/**
	 * 根据借款编号获取版本号
	 * 2015年12月15日
	 * By 李文勇
	 * @param creditReportRisk
	 * @return
	 */
	public List<CreditReportRisk> getCreditJsonByloanCode(CreditReportRisk creditReportRisk);
	
	public List<CreditReportRisk> getTeleCreditRisk(Map<String,Object> filter);
    
}
