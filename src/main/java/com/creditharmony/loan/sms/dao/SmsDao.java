package com.creditharmony.loan.sms.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.sms.entity.SmsHis;
import com.creditharmony.loan.sms.entity.SmsInformation;
import com.creditharmony.loan.sms.entity.SmsTemplate;

/**
 * 短信操作dao
 * @Class Name SmsDao
 * @author 朱杰
 * @Create In 2016年3月4日
 */
@LoanBatisDao
public interface SmsDao extends CrudDao<SmsTemplate> {
	
	/**
	 * 根据模板code获取短信模板
	 * 2016年3月8日
	 * By 朱杰
	 * @param templateCode
	 * @return
	 */
	public SmsTemplate getSmsTemplate(String templateCode);

	/**
	 * 插入短信发送记录
	 * 2016年3月8日
	 * By 朱杰
	 * @param his
	 */
	public void insertSmsHis(SmsHis his);

	/**
	 * 查询信审提短信
	 * 2016年1月10日
	 * @param psms
	 * @return 
	 */
	public List<SmsInformation> queryRemindSms(SmsHis psms);

	/**
	 * 插入信审短信
	 * @param sms
	 */
	public void insertRemindSms(SmsInformation sms);

	/**
	 * 插入预防欺诈短信
	 * @param sms
	 */
	public void insertCheatSms(SmsInformation sms);
	
}