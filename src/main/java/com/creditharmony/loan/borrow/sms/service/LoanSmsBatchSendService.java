package com.creditharmony.loan.borrow.sms.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.sms.SmsInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.loan.borrow.pushdata.util.ComUtils;
import com.creditharmony.loan.borrow.pushdata.util.Constants;
import com.creditharmony.loan.borrow.sms.dao.SmsHisMapper;
import com.creditharmony.loan.borrow.sms.dao.SmsLoanSendListMapper;
import com.creditharmony.loan.borrow.sms.entity.OnlyFlag;
import com.creditharmony.loan.borrow.sms.entity.SmsHis;
import com.creditharmony.loan.borrow.sms.entity.SmsSendList;

/**
 * 汇金短信批量发送
 * 
 * @Class Name LoanSmsBatchSendService
 * @author zhaojunlei
 * @Create In 2015年12月17日
 */
@Service
public class LoanSmsBatchSendService {

	@Autowired
	private SmsHisMapper smsHisMapper;
	@Autowired
	private SmsLoanSendListMapper smsSendListMapper;
	// 日志
	private static final Logger log = LoggerFactory
			.getLogger(LoanSmsBatchSendService.class);
	
	 private volatile  static AtomicBoolean exists = new AtomicBoolean(false);  

	/**
	 * 更新相关表的信息 2016年5月4日 By zhaojunlei
	 * 
	 * @param smsSendList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateInfo(SmsSendList smsSendList) {
		// 更新短信代发列表
		smsSendList.setModifyBy(Constants.BATCH_NAME);
		smsSendList.setModifyTime(new Date());
		int i = this.updateSmsSendList(smsSendList);
		if (i == 0) {
			log.debug("【汇金】" + ComUtils.getMessage("MSGBT00003", "短信代发列表")
					+ "唯一标识为" + smsSendList.getOnlyFlag() + "的数据更新失败！");
		} else {
			log.debug("【汇金】短信代发列表唯一标识为" + smsSendList.getOnlyFlag()
					+ "的数据更新成功！");
			// 将数据插入短信发送历史表
			SmsHis smsHis = new SmsHis();
			smsHis.setId(IdGen.uuid());
			smsHis.setLoanCode(smsSendList.getLoanCode());
			smsHis.setCreateBy(Constants.BATCH_NAME);
			smsHis.setCustomerCode(smsSendList.getCustomerCode());
			smsHis.setCustomerName(smsSendList.getCustomerName());
			smsHis.setSmsTemplateId(smsSendList.getSmsModelName());
			smsHis.setModifyBy(Constants.BATCH_NAME);
			smsHis.setModifyTime(new Date());
			smsHis.setSmsSendStatus(smsSendList.getSendStatus());
			smsHis.setSmsSendTime(new Date());
			smsHis.setSmsMsg(smsSendList.getSmsMsg());
			smsHis.setCreateTime(new Date());
			int i2 = this.insertSmsHis(smsHis);
			if (i2 == 0) {
				log.debug("【汇金】"
						+ ComUtils.getMessage("MSGBT00003", "短信发送历史表")
						+ "customerCode为" + smsHis.getCustomerCode()
						+ "的数据插入失败！");
			} else {
				log.debug("【汇金】短信发送历史表customerCode为"
						+ smsHis.getCustomerCode() + "的数据插入成功！");
			}
		}
	}

	/**
	 * 调用第三方短信发送平台发送短信 By zhaojunlei
	 * 
	 * @param list
	 *            <SmsJkSendList> 短信待发送列表
	 * @return 发送后的待发送状态变更后的短信待发送列表
	 */
	public int LoanSmsBatchthridSend(List<SmsSendList> list) {
		// 查询发送内容
		SmsSendList msgBean = smsSendListMapper.queryContent();
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_SMS_FAST);
		SmsInfo param = new SmsInfo();
		int i = 0;
		for (SmsSendList smsSendList : list) {
			// 短信唯一辨识设定,
		   if(exists.compareAndSet(false, true)){
			String onlyFlag = smsSendList.getOnlyFlag();
			if (StringUtils.isEmpty(onlyFlag)) {
				onlyFlag = OnlyFlag.getOnlyFlag();
				smsSendList.setOnlyFlag(onlyFlag);	
			}
			param.setSeqId(onlyFlag);
			// 手机号码设定
			if (StringUtils.isEmpty(smsSendList.getPhone())) {
				log.debug("【汇金】电话号码为空，唯一标识是：" + smsSendList.getOnlyFlag()
						+ "当前时间是：" + new Date());
				smsSendList.setSendStatus(Constants.SMSSENDSTATUS_FAIL);
				continue;
			} else {
				param.setPhoneNo(smsSendList.getPhone());
			}
			// 短信内容设定
		/*	if (StringUtils.isEmpty(smsSendList.getSmsMsg())) {
				log.debug("【批处理】短信内容为空，唯一标识是：" + smsSendList.getOnlyFlag()
						+ "当前时间是：" + new Date());
				smsSendList.setSendStatus(Constants.SMSSENDSTATUS_FAIL);
				continue;
			} else {*/
			   // 设置内容
			   param.setContent(msgBean.getSmsMsg());
		/*	}
*/
		/*	if (null != smsSendList.getSendTime()) {
				Date date1 = smsSendList.getSendTime();
				if (!ComUtils.dateToString(date1, Constants.DATAFORMAT[0]).equals(ComUtils
						.dateToString(date, Constants.DATAFORMAT[0])) ) {
					continue;
				}
			}*/
			// 短信息发送(普通)
			  service.callService(param);
			 // 将发送状态改为已发送
			  smsSendList.setSmsMsg(param.getContent());
			  smsSendList.setSendStatus(Constants.SMSSENDSTATUS_SEND);
			  updateInfo(smsSendList);
			  i++;
			  log.info("【汇金】汇金短信批量发送运行，发送短信" +i+ "条。当前时间是：" + ComUtils.nowTime(Constants.DATAFORMAT[3]));
			  exists.set(false);
		  }
		  
		}
		return i;
	}

	/**
	 * 
	 * 2016年4月20日 By zhaojunlei
	 * 
	 * @param smsSendList
	 * @return int
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int updateSmsSendList(SmsSendList smsSendList) {
		return smsSendListMapper.updateByPrimaryKeySelective(smsSendList);
	}

	/**
	 * 
	 * 2016年4月20日 By zhaojunlei
	 * 
	 * @param smsHis
	 * @return int
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int insertSmsHis(SmsHis smsHis) {
		return smsHisMapper.insertSelective(smsHis);
	}

	public Boolean stopSms(String flag) {
		if("1".equals(flag)){
			exists.set(true);
		}else{
			exists.set(false);
		}
		return true;
	}
}