package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractAndContractFee;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractAmountSummaryEx;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.payback.entity.Zhrefund;

/**
 * 合同dao层
 * @Class Name ContractDao
 * @author 张灏 
 * @Create In 2015年12月1日
 */
@LoanBatisDao
public interface ZhrefundDao extends CrudDao<Zhrefund>{
   
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年2月7日
	 * @param zhrefund
	 * @return
	 */
	public PageList<Zhrefund> getZhrefundList(Zhrefund zhrefund,PageBounds pageBounds);
	
	/**
	 * 修改中和东方退款状态
	 * @author 于飞
	 * @Create 2017年2月7日
	 * @param zhrefund
	 */
	public void updateZhrefundStatus(Zhrefund zhrefund);
	
	/**
	 * 插入中和东方不可退款数据
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param zhrefund
	 */
	public void insertZhrefund(List<Zhrefund> zhrefund);
	
	/**
	 * 根据合同编号查找对应的数据
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param zhrefund
	 * @return
	 */
	public Zhrefund findByContractCode(Zhrefund zhrefund);
}
