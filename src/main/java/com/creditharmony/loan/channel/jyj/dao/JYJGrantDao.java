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
package com.creditharmony.loan.channel.jyj.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantBFEx;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 待放款列表中的待放款的单子
 * 
 * @Class Name SendMoneyDao
 * @author 朱静越
 * @Create In 2015年12月21日
 */
@LoanBatisDao
public interface JYJGrantDao extends CrudDao<JYJGrantEx> {
	
	/**
	 * 查询待放款列表
	 * 2017年1月20日
	 * By 朱静越
	 * @param pageBounds
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getJyjGrantLists(PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);
	
	/**
	 * 不分页查询列表
	 * 2017年1月20日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getJyjGrantLists(LoanFlowQueryParam loanFlowQueryParam);

	/**
	 * 查询待放款列表中的线下导出表 2015年12月21日 By 朱静越
	 * 
	 * @param id
	 *            放款id
	 * @return 放款表实体
	 */
	public JYJGrantEx getGrantList(String applyId);
	
	/**
	 * 根据合同编号查询退回原因
	 * 2015年12月23日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 退回原因
	 */
	public String selectBackRea(String contractCode);
	
	/**
	 * 选择退回原因
	 * 2016年5月17日
	 * By 朱静越
	 * @param map
	 * @return
	 */
	public int selGrantBackMes(Map<String, Object> map);

	/**
	 * 获取提交批次、放款批次不重复的集合
	 * @param batch  集合 
	 * 2016年3月12日
	 * 张建雄
	 * @return
	 */
	public List<String> findSubmitBatchList(Map<String,String> batch);
	
	/**
	 * 查询宝付模板
	 * 2017年5月17日
	 * By 朱静越
	 * @param jyjGrantBFEx
	 * @return
	 */
	public List<JYJGrantBFEx> getBFGrantList(JYJGrantBFEx jyjGrantBFEx);
}