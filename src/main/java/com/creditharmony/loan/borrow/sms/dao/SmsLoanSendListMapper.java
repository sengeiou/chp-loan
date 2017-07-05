package com.creditharmony.loan.borrow.sms.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.sms.entity.SmsSendList;

/**
 * 邮件发送列表
 * @Class Name SmsLoanSendListMapper
 * @author 武文涛
 * @Create In 2015年12月10日
 */
@LoanBatisDao
public interface SmsLoanSendListMapper {
	
    /**
     * 根据主键删除数据
     * 2015年12月10日
     * By 武文涛
     * @param key
     * @return int
     */
	public int deleteByPrimaryKey(String key);
	
	/**
	 * 插入数据（自动判断是否为null）
	 * 2015年12月10日
	 * By 武文涛
	 * @param record
	 * @return int
	 */
	public int insertSelective(SmsSendList record);

    /**
     * 根据主键查询数据
     * 2015年12月10日
     * By 武文涛
     * @param key
     * @return SmsSendList
     */
	public SmsSendList selectByPrimaryKey(String key);

    /**
     * 批量更新数据（自动判断是否为null）
     * 2015年12月10日
     * By 武文涛
     * @param record
     * @return int
     */
	public int updateByPrimaryKeySelective(SmsSendList record);

    /**
     * 批量更新数据
     * 2015年12月10日
     * By 武文涛
     * @param record
     * @return int
     */
	public int updateByPrimaryKey(SmsSendList record);

	/**
	 * 从其他表中查寻 汇金短信待发列表 中的数据
	 * 2016年1月15日
	 * By 武文涛
	 * @param record
	 * @return List<SmsSendList>
	 */
	public List<SmsSendList> selectSmsJkSendMsg(SmsSendList record);
	
	/**
	 * 根据发送状态查询
	 * 2016年1月15日
	 * By 武文涛
	 * @param sendStutus
	 * @return List<SmsSendList>
	 */
	public List<SmsSendList> getAllSmsSendList(Map<String,Object> map);

	/**
	 * 查询要发短信的条数
	 * @param map
	 * @return
	 */
	public String queryCount(Map<String, Object> map);

	/**
	 * 查询短信信息
	 * @return
	 */
	public SmsSendList queryContent();

}