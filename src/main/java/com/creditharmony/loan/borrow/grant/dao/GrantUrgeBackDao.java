package com.creditharmony.loan.borrow.grant.dao;
import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;

/**
 * 放款失败催收服务费退回
 * @Class Name GrantUrgeBackDao
 * @author 朱静越
 * @Create In 2016年1月21日
 */
@LoanBatisDao
public interface GrantUrgeBackDao extends CrudDao<GrantUrgeBackEx>{
	
	/**
	 * 查找放款失败，催收服务费退回列表
	 * 2016年1月27日
	 * By 朱静越
	 * @param pageBounds
	 * @param grantUrgeBackEx  页面查询条件
	 * @return 查询出的集合
	 */
	public List<GrantUrgeBackEx> selectUrgeBackList(PageBounds pageBounds,GrantUrgeBackEx grantUrgeBackEx);
	
	/**
	 * 根据催收主表进行查询该笔单子在拆分表中的成功的个数
	 * 2016年2月26日
	 * By 朱静越
	 * @param id 催收主表id
	 * @return 成功的个数
	 */
	public int selUrgeFail(String id);
	
	/**
	 * 根据条件查询催收退回列表
	 * 2016年2月24日
	 * By 朱静越
	 * @param grantUrgeBackEx 查询条件
	 * @return 查询集合，不带分页
	 */
	public List<GrantUrgeBackEx> selectUrgeBackList(GrantUrgeBackEx grantUrgeBackEx);
	
	/**
	 * 根据催收主表id进行已收记录页面的查询
	 * 2016年2月15日
	 * By 朱静越
	 * @param urgeId 催收主表id
	 * @return 已收记录集合，主要从拆分表中取值
	 */
	public List<GrantUrgeBackEx> backDone(String urgeId);
	
	/**
	 * 删除拆分表中回盘结果为划扣失败的单子的list
	 * 2016年2月22日
	 * By 朱静越
	 * @param list 要删除的list
	 * @return 删除结果
	 */
	public int delGrantFail(List<UrgeServicesMoneyEx> list);
	
	/**
	 * 根据催收主表id进行删除，删除主表
	 * 2016年2月26日
	 * By 朱静越
	 * @param id 催收主表id
	 * @return 删除结果
	 */
	public int delUrge(String id);
	
	/**
	 * 根据催收主表id查询拆分表中回盘结果为失败的单子
	 * 2016年2月22日
	 * By 朱静越
	 * @param urgeId 催收主表id
	 * @return 结果集
	 */
	public List<UrgeServicesMoneyEx> selSplitDelete(String urgeId);
	
	/**
	 * 根据合同编号查询要进行放款审核退回的单子的拆分回盘结果为处理中的单子的个数
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return  该单子的处理状态
	 */
	public String getDealCount(String contractCode);
	
	/**
	 * 根据合同编号查询退款状态
	 * 2016年1月29日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 退款状态
	 */
	public String getUnReturnCount(String contractCode);
	
	/**
	 * 根据合同编号查询拆分表中的回盘结果为成功的单子，并进行合并
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 放款失败催收服务费退回
	 */
	public GrantUrgeBackEx getUrgeBack(String urgeId);
	
	/**
	 * 向催收服务费退回表中插入数据
	 * 2016年1月27日
	 * By 朱静越
	 * @param grantUrgeBackEx 放款催收实体
	 * @return  插入成功与否
	 */
	public int insertUrgeBack(GrantUrgeBackEx grantUrgeBackEx);
	
	/**
	 * 根据合同编号查询拆分表的关联id
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 催收id
	 */
	public String selUrgeId(String contractCode);
	
	/**
	 * 根据关联催收id，更新单子的退款状态，退款时间，退款中间人id
	 * 2016年2月1日
	 * By 朱静越
	 * @param grantUrgeBackEx 催收退回实体
	 * @return 更新结果
	 */
	public int updUrgeBack(GrantUrgeBackEx grantUrgeBackEx);
	
	/**
	 * 根据合同编号查找借款状态变更表和还款操作流水表，查看单子的全部历史记录，结合
	 * 2016年2月2日
	 * By 朱静越
	 * @param pageBounds 分页对象
	 * @param contractCode 合同编号
	 * @return 历史集合
	 */
	public List<GrantUrgeBackEx> selHistory(PageBounds pageBounds,String contractCode);
}