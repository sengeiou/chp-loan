package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;

/**
 * @Class Dao实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年11月23日
 */
@LoanBatisDao
public interface RepaymentReminderDao extends CrudDao<RepaymentReminderDao> {

	/**
	 * 查询发起还款提醒列表
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param repaymentReminder
	 * @return
	 */
	public List<RepaymentReminderEx> allRepaymentReminderList(PageBounds pageBounds,
			RepaymentReminderEx repaymentReminder);
	/**
	 *查询总金额 
	 * @param repaymentReminder
     * @return Map<String,Object>
	 */
	public Map<String,Object> selectSumAmount(RepaymentReminderEx repaymentReminder);
	/**
	 * 查询发起还款提醒列表全部数据
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param repaymentReminder
	 * @return
	 */
	public List<RepaymentReminderEx> allRepaymentReminderList(RepaymentReminderEx repaymentReminder);

	/**
	 * 查询 短信推送所需要的数据
	 * 2015年12月17日
	 * By 李强
	 * @param contractCode
	 * @return
	 */
	public RepaymentReminderEx queryRepaymentReminderList(RepaymentReminderEx repaymentReminders);
	/**
	 * 将短信推送的数据添加到表(汇金短信提醒_还款表)
	 * 2015年12月17日
	 * By 李强
	 * @param repaymentReminder
	 */
	public void insertShortMessagePayback(RepaymentReminderEx repaymentReminder);

	/**
	 * 将短信推送的数据添加到表(借款人服务部提醒信息)
	 * 2015年12月17日
	 * By 李强
	 * @param repaymentReminder
	 */
	public void insertShortMessageServicesRemind(
			RepaymentReminderEx repaymentReminder);

	/**
	 * 推送成功后把待还款的 提醒标识变成已提醒
	 * 2015年12月17日
	 * By 李强
	 * @param contractCode
	 * @return
	 */
	public String updateRepaymentReminder(RepaymentReminderEx repaymentReminder);
	
	/**
	 * 导出发起还款提醒列表的数据
	 * 2016年3月24日
	 * By zhaojinping
	 * @param paramMap
	 * @return
	 */
	public List<RepaymentReminderEx> exportRemindExcel(RepaymentReminderEx repaymentReminder);
	
	/**
	 * 查询所有需要发送提醒的期供ID
	 * 2016年5月25日
	 * By 王彬彬
	 * @param repaymentReminder
	 * @return
	 */
	public List<String> fingAllId(RepaymentReminderEx repaymentReminder);
}
