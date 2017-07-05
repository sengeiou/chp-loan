package com.creditharmony.loan.borrow.payback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.ManualOperationDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.ex.ManualOperationEx;
import com.creditharmony.loan.borrow.payback.entity.ex.MonthExcelEx;

/**
 * @Class Service实现支持类
 * @author 李强
 * @Create In 2015年12月17日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ManualOperationService {
	@Autowired
	private ManualOperationDao manualOperationDao;
	
	/**
	 * 风控手动冲抵列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param manualOperationEx
	 * @return 风控手动冲抵数据集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<ManualOperationEx> allManualOperationList(Page<ManualOperationEx> page,ManualOperationEx manualOperationEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<ManualOperationEx> pageList = (PageList<ManualOperationEx>)manualOperationDao.allManualOperationList(pageBounds,manualOperationEx);
		PageUtil.convertPage(pageList, page);		
		return page;
	};
	
	/**
	 * 风控手动冲抵 查看页面数据
	 * 2015年12月18日
	 * By 李强
	 * @param page
	 * @param manualOperationEx
	 * @return 风控手动冲抵单条数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<ManualOperationEx> queryManualOperation(ManualOperationEx manualOperationEx){
		return manualOperationDao.queryManualOperation(manualOperationEx);
	};
	
	/**
	 * 风控手动冲抵 查看页面数据(页面上面四个字段)
	 * 2015年12月18日
	 * By 李强
	 * @param manualOperationEx
	 * @return 手动冲抵四个字段数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public ManualOperationEx queryOperation(ManualOperationEx manualOperationEx){
		return manualOperationDao.queryOperation(manualOperationEx);
	};
	
	/**
	 * 风控手动冲抵 查看页面数据(查询已还期供总额)
	 * 2015年12月18日
	 * By 李强
	 * @param manualOperationEx
	 * @return 期供数据集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<ManualOperationEx> sumContractMonthRepay(ManualOperationEx manualOperationEx){
		return manualOperationDao.sumContractMonthRepay(manualOperationEx);
	};
	
	/**
	 * 风控批量冲抵时的蓝补金额
	 * 2015年12月21日
	 * By 李强
	 * @param id
	 * @return 风控冲抵单条数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public ManualOperationEx queryChargeAgainst(String id){
		return manualOperationDao.queryChargeAgainst(id);
	};
	
	/**
	 * 风控批量冲抵   冲抵期供(修改实还本金利息)
	 * 2015年12月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateNotMoney(ManualOperationEx manualOperationEx){
		manualOperationDao.updateNotMoney(manualOperationEx);
	};
	
	/**
	 * 风控批量冲抵成功后 将蓝补余额存回还款主表的蓝补金额里面
	 * 2015年12月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackBuleAmount(ManualOperationEx manualOperationEx){
		manualOperationDao.updatePaybackBuleAmount(manualOperationEx);
	};
	
	/**
	 * 添加蓝补交易明细记录
	 * 2015年12月21日
	 * By 李强
	 * @param paybackBuleAmont
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont){
		manualOperationDao.insertPaybackBuleAmont(paybackBuleAmont);
	};
	
	/**
	 * 导出Excel数据
	 * 2015年12月25日
	 * By 李强
	 * @param chargeId
	 * @return 风控单条数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public ManualOperationEx exportExcelData(String chargeId){
		return manualOperationDao.exportExcelData(chargeId);
	}
	
	/**
	 * 导出风控手动冲抵查看页面的期供数据
	 * 2015年12月18日
	 * By 李强
	 * @param monthExcelEx
	 * @return 期供数据集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<MonthExcelEx> exportMonthExcel(MonthExcelEx monthExcelEx){
		return manualOperationDao.exportMonthExcel(monthExcelEx);
	};
	
	/**
	 * 冲抵成功后修改冲抵申请表中冲抵状态为：已冲抵
	 * 2016年1月5日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateChargeStatus(ManualOperationEx manualOperationEx){
		manualOperationDao.updateChargeStatus(manualOperationEx);
	};
	
	/**
	 * 手动冲抵：如果冲抵期数未最后一期，修改主表中的还款状态为：5 结清待确认
	 * 2016年1月13日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackStatus(ManualOperationEx manualOperationEx){
		manualOperationDao.updatePaybackStatus(manualOperationEx);
		};
		
	/**
	 * 手动冲抵：冲抵成功后修改主表中的 当前第几期
	 * 2016年1月21日
	 * By 李强
	 * @param manualOperationEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackCurrentMonth(ManualOperationEx manualOperationEx){
		manualOperationDao.updatePaybackCurrentMonth(manualOperationEx);
	};	
	
}
