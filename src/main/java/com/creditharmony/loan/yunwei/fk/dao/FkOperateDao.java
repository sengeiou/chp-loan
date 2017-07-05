package com.creditharmony.loan.yunwei.fk.dao;

import java.util.HashMap;
import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.yunwei.fk.entity.FkOperateObj;
import com.creditharmony.loan.yunwei.fk.entity.PaybackBlueAmount;

@LoanBatisDao
public interface FkOperateDao  extends CrudDao<FkOperateObj>{
	
	// 查询当期的各种金额（返回实体），根据合同编号、还款日期
	public FkOperateObj queryCurrentRepayment(FkOperateObj fkOperateObjParms);
	// 查询蓝补对账单列表（根据合同编号）
	
	// 
	public List<PaybackBlueAmount> queryPaybackBlueAmount(String contractCode);
	
	// 查询还款日后的两个工作日内的进账
	public List<PaybackBlueAmount> queryActualRepayAmount(PaybackBlueAmount paybackBlueAmount);
	
	public List<String> selectNowDayAfterDate(String nowStr);
	
	public FkOperateObj queryBlueMoneyAndQg(String contractCode);
	
	public List<String> selectNowDayBeforeDate(HashMap<String, String> mapParmas);
	
	// 
	public List<FkOperateObj> selectDataForFs();
	
	public List<FkOperateObj> selectDataForXiufu();
	
	public List<String> selectRepaymentDayList(HashMap<String, String> mapParmas);
}
