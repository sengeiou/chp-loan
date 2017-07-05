package com.creditharmony.loan.borrow.transate.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.transate.entity.ex.TraParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;
/**
 * 信借已办列列表Dao
 * @Class Name TransateDao
 * @author lirui
 * @Create In 2015年12月2日
 */
@LoanBatisDao
public interface TransateDao extends CrudDao<TransateEx> {
	
	/**
	 * 展示所有已办列表
	 * 2015年12月2日
	 * By lirui
	 * @param traParams 查询参数
	 * @param pageBounds 分页参数
	 * @return 已办列表
	 */
	public List<TransateEx> getTransact(PageBounds pageBounds,TraParamsEx traParams);
	
	/**
	 * 根据借款编码获得共借人集合
	 * 2016年2月29日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 共借人姓名集合
	 */
	public List<String> getCobos(String loanCode);

	/**
	 * 查询数据是否是利率审核之后的数据
	 * 2016年4月13日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return loanCode
	 */
	public String checkUrl(String loanCode);
}
