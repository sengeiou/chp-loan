package com.creditharmony.loan.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.sms.dao.SmsDao;
import com.creditharmony.loan.sms.entity.SmsHis;
import com.creditharmony.loan.sms.entity.SmsInformation;
import com.creditharmony.loan.sms.entity.SmsTemplate;

/**
 * 
 * 信审提醒短信
 * @author 翁私
 *
 */
@Service
public class CheckRemindSmsService {
	
	@Autowired
	private SmsDao smsDao;
    /**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(CheckRemindSmsService.class);
	
	public static final String templateCode ="hjcr"; //汇金信审提醒短信  模块code
	
    /**
     * 发送短信方法
     * @param loanCode 
     */
	@Transactional(value = "loanTransactionManager", readOnly = false)
    public void sendMsm(String loanCode){
    	if(StringUtils.isNotEmpty(loanCode)){
    		SmsHis psms = new SmsHis();
    		psms.setLoanCode(loanCode);
        	List<SmsInformation> smsList = smsDao.queryRemindSms(psms);
        	SmsTemplate tem = smsDao.getSmsTemplate(templateCode);
        	if(ArrayHelper.isNotEmpty(smsList)){
        		SmsInformation sms = smsList.get(0);
	        		if(StringUtils.isNotEmpty(sms.getPhone())){
	        			Map<String,String> lsParam = new HashMap<String,String>(); 
	        			lsParam.put("\\{#Name#\\}",sms.getCustomerName()); 
	        			sms.setSmsTemplateId(tem.getId());
	        			sms.setSmsTemplateContent(getSmsMsg(tem.getTemplateContent(), lsParam));
	        			//插入 信审提醒短信表
	        			sms.preInsert();
	        			smsDao.insertRemindSms(sms);
	        			logger.info("【信审提醒短信】：插入成功！");
	        		 }else{
	        			logger.info("【信审提醒短信】：手机号为空，借款编号："+loanCode);
	        		 }
        	  }else{
        		logger.info("【信审提醒短信】：无可发送的短信，借款编号："+loanCode);
        	}
    	}else{
    		logger.info("【信审提醒短信】：无可发送的短信，借款编号为空");
       }
    }
	
	/** 
	* 拼装短信 
	* 
	* @param tplCd 
	* @param lsParam 
	* @return String 
	*/ 
	public static String getSmsMsg(String tpl, Map<String,String> lsParam) { 
	String msg = ""; 
	if (ObjectHelper.isEmpty(lsParam)) { 
	return msg; 
	} 

	for (String tmpKey:lsParam.keySet()) { 
	tpl = tpl.replaceFirst(tmpKey, lsParam.get(tmpKey)); 
	} 
	msg = tpl; 

	return msg; 
	}
	

}
