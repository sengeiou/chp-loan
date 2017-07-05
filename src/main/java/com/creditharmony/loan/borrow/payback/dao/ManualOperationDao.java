package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.ex.ManualOperationEx;
import com.creditharmony.loan.borrow.payback.entity.ex.MonthExcelEx;

/**
 * Dao实现支持类
 * @Class Name ManualOperationDao
 * @author 李强
 * @Create In 2015年12月17日
 */
@LoanBatisDao
public interface ManualOperationDao extends CrudDao<CentralizedDeductionDao> {

	/**
	 * 风控手动冲抵列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param manualOperationEx
	 * @return 手动冲抵List
	 */
	public List<ManualOperationEx> allManualOperationList(PageBounds pageBounds,ManualOperationEx manualOperationEx);
	
	/**
	 * 风控手动冲抵 查看页面数据
	 * 2015年12月18日
	 * By 李强
	 * @param manualOperationEx
	 * @return 手动冲抵单条数据
	 */
	public List<ManualOperationEx> queryManualOperation(ManualOperationEx manualOperationEx);
	
	/**
	 * 风控手动冲抵 查看页面数据(页面上面四个字段)
	 * 2015年12月18日
	 * By 李强
	 * @param manualOperationEx
	 * @return 手动冲抵查看页面中  页面上部分的四个字段信息
	 */
	public ManualOperationEx queryOperation(ManualOperationEx manualOperationEx);
	
	/**
	 * 风控手动冲抵 查看页面数据(查询已还期供总额)
	 * 2015年12月18日
	 * By 李强
	 * @param manualOperationEx
	 * @return 手动冲抵查看页面   还款历史明细信息
	 */
	public List<ManualOperationEx> sumContractMonthRepay(ManualOperationEx manualOperationEx);
	
	/**
	 * 风控批量冲抵时的蓝补金额
	 * 2015年12月21日
	 * By 李强
	 * @param id
	 * @return 冲抵时需要的蓝补金额(还款主表)
	 */
	public ManualOperationEx queryChargeAgainst(String id);
	
	/**
	 * 风控批量冲抵   冲抵期供(修改实还本金利息)
	 * 2015年12月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	public void updateNotMoney(ManualOperationEx manualOperationEx);
	
	/**
	 * 风控批量冲抵成功后 将蓝补余额存回还款主表的蓝补金额里面
	 * 2015年12月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	public void updatePaybackBuleAmount(ManualOperationEx manualOperationEx);
	
	/**
	 * 添加蓝补交易明细记录
	 * 2015年12月21日
	 * By 李强
	 * @param paybackBuleAmont
	 * @return none
	 */
	public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont);
	
	/**
	 * 导出Excel数据
	 * 2015年12月25日
	 * By 李强
	 * @param id
	 * @return 查询出需要导出的数据
	 */
	public ManualOperationEx exportExcelData(String chargeId);
	
	/**
	 * 导出查看页面的期供Excel数据
	 * 2015年12月25日
	 * By 李强
	 * @param monthExcelEx
	 * @return 导出手动冲抵查看页面的还款历史明细记录信息
	 */
	public List<MonthExcelEx> exportMonthExcel(MonthExcelEx monthExcelEx);
	
	/**
	 * 冲抵成功后修改冲抵申请表中冲抵状态为：已冲抵
	 * 2016年1月5日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	public void updateChargeStatus(ManualOperationEx manualOperationEx);
	
	/**
	 * 手动冲抵：如果冲抵期数未最后一期，修改主表中的还款状态为：5 结清待确认
	 * 2016年1月13日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	public void updatePaybackStatus(ManualOperationEx manualOperationEx);
	
	/**
	 * 手动冲抵：冲抵成功后修改主表中的 当前第几期
	 * 2016年1月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	public void updatePaybackCurrentMonth(ManualOperationEx manualOperationEx);
}
