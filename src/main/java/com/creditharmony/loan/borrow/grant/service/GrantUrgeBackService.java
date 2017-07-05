package com.creditharmony.loan.borrow.grant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;

/**
 * 放款失败催收费退回处理service
 * @Class Name GrantUrgeBackService
 * @author 朱静越
 * @Create In 2016年1月27日
 */
@Service("GrantUrgeBackService")
public class GrantUrgeBackService extends CoreManager<GrantUrgeBackDao,GrantUrgeBackEx>{
	
	/**
	 * 查询放款退回催收服务费列表
	 * 2016年1月27日
	 * By 朱静越
	 * @param page 页面
	 * @param grantUrgeBackEx 查询条件
	 * @return 催收退回列表分页对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<GrantUrgeBackEx> selectUrgeBackList(Page<GrantUrgeBackEx> page,GrantUrgeBackEx grantUrgeBackEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("r_charge_id");
		PageList<GrantUrgeBackEx> pageList = (PageList<GrantUrgeBackEx>)dao.selectUrgeBackList(pageBounds, grantUrgeBackEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询催收退回，不带分页
	 * 2016年2月24日
	 * By 朱静越
	 * @param grantUrgeBackEx 查询条件
	 * @return 结果集
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<GrantUrgeBackEx> selectUrgeBackListNo(GrantUrgeBackEx grantUrgeBackEx){
		return dao.selectUrgeBackList(grantUrgeBackEx);
	}
	
	/**
	 * 根据催收主表id进行查询已收记录页面
	 * 2016年2月15日
	 * By 朱静越
	 * @param urgeId 催收主表id
	 * @return 已收记录集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<GrantUrgeBackEx> backDone(String urgeId){
		return dao.backDone(urgeId);
	}
	
	/**
	 * 删除拆分表中的回盘结果为划扣失败的单子
	 * 2016年2月22日
	 * By 朱静越
	 * @param list 要进行删除的集合
	 * @return 删除结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int delGrantFail(List<UrgeServicesMoneyEx> list){
		return dao.delGrantFail(list);
	}
	
	/**
	 * 根据催收主表id查询要进行删除的单子
	 * 2016年2月22日
	 * By 朱静越
	 * @param urgeId 催收主表id
	 * @return 结果集
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeServicesMoneyEx> selSplitDelete(String urgeId){
		return dao.selSplitDelete(urgeId);
	}
	
	/**
	 * 根据合同编号查询要进行放款审核退回时催收服务费的处理状态
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return  处理状态
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String getDealCount(String contractCode){
		return dao.getDealCount(contractCode);
	}
	
	/**
	 * 根据合同编号查询退款状态
	 * 2016年1月29日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 退款状态
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String getUnReturnCount(String contractCode){
		return dao.getUnReturnCount(contractCode);
	}
	
	/**
	 * 根据合同编号查询拆分表中回盘结果为成功的单子，并进行合并
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 放款失败催收服务费退回实体
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public GrantUrgeBackEx getSuccessTest(String urgeId){
		return dao.getUrgeBack(urgeId);
	}
	
	/**
	 * 向催收服务费退回表中插入数据
	 * 2016年1月27日
	 * By 朱静越
	 * @param grantUrgeBackEx 要进行添加的数据
	 * @return 添加的结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insertUrgeBack(GrantUrgeBackEx grantUrgeBackEx){
		grantUrgeBackEx.preInsert();
		return dao.insertUrgeBack(grantUrgeBackEx);
	}
	
	/**
	 * 根据合同编号查询拆分表中的关联催收id
	 * 2016年1月27日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 催收id
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String selUrgeId(String contractCode){
		return dao.selUrgeId(contractCode);
	}
	
	/**
	 * 点击确认退款之后，根据关联催收id，更新退回表的退回状态，退回时间，退回中间人id
	 * 2016年2月1日
	 * By 朱静越
	 * @param grantUrgeBackEx 退回实体
	 * @return 更新结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updUrgeBack(GrantUrgeBackEx grantUrgeBackEx){
		grantUrgeBackEx.preUpdate();
		return dao.updUrgeBack(grantUrgeBackEx);
	}
	
	/**
	 * 根据合同编号查询借款状态变更记录表和还款操作流水表查看历史，综合的历史
	 * 2016年2月2日
	 * By 朱静越
	 * @param page 页面对象
	 * @param contractCode 合同编号
	 * @return 历史集合，分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<GrantUrgeBackEx> selHistory(Page<GrantUrgeBackEx> page,String contractCode){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("operateTime");
		PageList<GrantUrgeBackEx> pageList = (PageList<GrantUrgeBackEx>)dao.selHistory(pageBounds, contractCode);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
