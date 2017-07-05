package com.creditharmony.loan.borrow.serve.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.serve.entity.PaybackMonthSendEmail;

/**
 * 汇金邮件管理
 * 
 * @Class Name EmailServeDao
 * @author 于飞
 * @Create In 2017年3月6日
 */
@LoanBatisDao
public interface EmailServeDao {
	
	/**
	 * 分页获取列表
	 * @author 于飞
	 * @Create 2017年3月6日
	 * @param email
	 * @param pageBounds
	 * @return
	 */
	public PageList<PaybackMonthSendEmail> findEmailList(PaybackMonthSendEmail email,PageBounds pageBounds);
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param email
	 * @return
	 */
	public List<PaybackMonthSendEmail> findEmailList(PaybackMonthSendEmail email);
	
	/**
	 * 发送过的邮件列表
	 * @author 于飞
	 * @Create 2017年3月10日
	 * @param email
	 * @param pageBounds
	 * @return
	 */
	public PageList<PaybackMonthSendEmail> findSendEmailList(PaybackMonthSendEmail email,PageBounds pageBounds);
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param email
	 * @return
	 */
	public List<PaybackMonthSendEmail> findSendEmailList(PaybackMonthSendEmail email);
	
	/**
	 * 发送过的邮件列表
	 * @author 于飞
	 * @Create 2017年3月10日
	 * @param email
	 * @param pageBounds
	 * @return
	 */
	public PageList<PaybackMonthSendEmail> findHolidayList(PaybackMonthSendEmail email,PageBounds pageBounds);
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param email
	 * @return
	 */
	public List<PaybackMonthSendEmail> findHolidayList(PaybackMonthSendEmail email);
	/**
	 * 更新邮件发送状态
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param paybackMonthId
	 * @param status
	 */
	public void updateSendEmailStatus(PaybackMonthSendEmail email);
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param email
	 */
	public void insertSendEmail(PaybackMonthSendEmail email);
	
	/**
	 * 根据状态删除邮件
	 * @author 于飞
	 * @Create 2017年4月17日
	 * @param email
	 */
	public void deleteEmailByStatus(PaybackMonthSendEmail email);
	
	/**
	 * 获取当前日期的后三个工作日
	 * @param nowDayStr 当前日期字符串 yyyyMMdd
	 * @return
	 */
	public List<String> selectNowDayAfterDate(String nowDayStr);
}
