package com.creditharmony.loan.borrow.transate.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanEmailEdit;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanInfoExport;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;
@LoanBatisDao
/**
 * 信借数据信息查询
 * @Class Name LoanInfoDao
 * @author lirui
 * @Create In 2015年12月3日
 */
public interface LoanInfoDao extends CrudDao<TransateEx> {
	/**
	 * 查询信借数据列表
	 * 2015年12月2日
	 * By lirui
	 * @param params 检索参数
	 * @param pageBounds 分页信息
	 * @return 数据信息列表集合
	 */
	public List<TransateEx> loanInfo(PageBounds pageBounds,LoanParamsEx params);
	
	/**
	 * 查询信借数据列表count
	 * 2015年12月2日
	 * By lirui
	 * @param params 检索参数
	 * @param pageBounds 分页信息
	 * @return 数据信息列表集合
	 */
	public int cnt(LoanParamsEx params);
		
	/**
	 * 获得借款产品列表
	 * 2015年12月2日
	 * By lirui
	 * @return 产品列表
	 */
	public List<String> products();
	
	/**
	 * 根据借款编码获得共借人集合
	 * 2016年2月29日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 共借人姓名集合
	 */
	public List<String> getCobos(String loanCode);
	
	public LoanEmailEdit selectEmailByLoanCode(String loanCode);
	
	public int updateCustomer(LoanEmailEdit editInfo);
	
	public List<LoanInfoExport> loanInfoEmailExport(LoanParamsEx params);
	
	
}
