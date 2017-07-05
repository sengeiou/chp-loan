package com.creditharmony.loan.channel.jyj.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
@LoanBatisDao
public interface JyjBorrowBankConfigureDao extends CrudDao<JyjBorrowBankConfigure>{

	/**
	 * 查询列表
	 * @param record
	 * @return
	 */
	List<JyjBorrowBankConfigure> queryList(JyjBorrowBankConfigure record);
	
	public JyjBorrowBankConfigure selectBank(JyjBorrowBankConfigure record);
	// 更新所有的数据
	void updateAllinvalid(JyjBorrowBankConfigure record);
	//根据银行code更新数据
	void updateByBankCode(JyjBorrowBankConfigure record);
	/**
	 * 查询金信简易借待分配卡号列表--分页
	 * @author wjj
	 * @Create 2017年5月6日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询简易借待分配卡号--不分页
	 * 2017年5月7日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 查询金信简易借待放款列表--分页
	 * @author wjj
	 * @Create 2017年5月6日
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);

	/**
	 * 查询金信简易借待放款列表--不分页
	 * @author  wjj
	 * @Create 2017年5月6日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(LoanFlowQueryParam loanFlowQueryParam);

	/** 
	 * 修改费率
	 * By 任志远 2017年5月22日
	 *
	 * @param params
	 */
	public void updateProportion(Map<String, Object> params);
	
}
