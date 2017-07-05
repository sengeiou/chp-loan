package com.creditharmony.loan.borrow.payback.dao;

import java.math.BigDecimal;
import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.OverdueManageEx;

/**
 * 逾期管理
 * @Class Name OverdueManageDao
 * @author 李强
 * @Create In 2015年12月14日
 */
@LoanBatisDao
public interface OverdueManageDao extends CrudDao<OverdueManageDao>{
	
	/**
	 * 查询逾期管理列表
	 * 2015年12月14日
	 * By 李强
	 * @param pageBounds
	 * @param overdueManageEx
	 * @return 期供逾期数据集合
	 */
	public List<OverdueManageEx> allOverdueManageList(PageBounds pageBounds,OverdueManageEx overdueManageEx);
	
	/**
	 * 查询逾期管理列表
	 * 2015年12月14日
	 * By 李强
	 * @param pageBounds
	 * @param overdueManageEx
	 * @return 期供逾期数据集合
	 */
	public int cnt(OverdueManageEx overdueManageEx);
	
	/**
	 * 查询逾期管理 调整页面信息
	 * 2015年12月14日
	 * By 李强
	 * @param id
	 * @return 所指定的单个期供逾期数据
	 */
	public OverdueManageEx queryOverdueManage(String id);
	
	/**
	 * 修改逾期管理天数 
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 */
	public void updateOverdueManage(OverdueManageEx overdueManageEx);
	
	/**
	 * 修改减免金额(输入的减免金额小于或等于应还违约金)
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 */
	public void updateMonthPenaltyReduction(OverdueManageEx overdueManageEx);
	
	/**
	 * 修改减免金额(输入的减免金额大于应还违约金且小于应还违约金罚息)
	 * 2015年12月15日
	 * By 李强
	 * @param overdueManageEx
	 */
	public void updateMonthPunishReduction(OverdueManageEx overdueManageEx);
	
	/**
	 * 查询蓝补金额
	 * 2015年12月16日
	 * By 李强
	 * @param overdueManageEx
	 * @return 还款主表蓝补金额数
	 */
	public OverdueManageEx queryPaybackBuleAmount(OverdueManageEx overdueManageEx);
	
	/**
	 * 剩余的减免金额添加蓝补金额
	 * 2015年12月16日
	 * By 李强
	 * @param overdueManageEx
	 */
	public void updatePaybackBuleAmount(OverdueManageEx overdueManageEx);
	
	/**
	 * 修改逾期天数、减免金额后插入还款操作流水记录表
	 * 2015年12月15日
	 * By 李强
	 * @param paybackOpe
	 */
	public void insertPaybackOpe(PaybackOpe paybackOpe);
	
	/**
	 * 查询客户姓名公共方法
	 * 2016年1月22日
	 * By 李强
	 * @param loanTeamOrgid
	 * @return
	 */
	public OverdueManageEx queryCustomerName(OverdueManageEx overdueManageEx);

	/**
	 * 导出数据 2016年3月4日 by wengsi
	 * @param overdueManageEx
	 * @return list
	 */
	public List<OverdueManageEx> allOverdueManageList(
			OverdueManageEx overdueManageEx);
	
	/**
	 * 批量减免
	 * 2016年3月5日
	 * By zhaojinping
	 * @param id
	 */
	public void batchReduce(OverdueManageEx overdueManage);

	public int getCount(OverdueManageEx overdueManageEx);
	
	/** 查询违约金罚息滞纳金是否全部还完  */
	public BigDecimal selectIfAllReduction(String monthId);
}


