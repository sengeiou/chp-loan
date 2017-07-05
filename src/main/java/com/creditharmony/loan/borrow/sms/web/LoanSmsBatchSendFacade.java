package com.creditharmony.loan.borrow.sms.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.loan.borrow.pushdata.util.ComUtils;
import com.creditharmony.loan.borrow.pushdata.util.Constants;
import com.creditharmony.loan.borrow.sms.dao.SmsLoanSendListMapper;
import com.creditharmony.loan.borrow.sms.entity.SmsSendList;
import com.creditharmony.loan.borrow.sms.service.LoanSmsBatchSendService;

/**
 * 汇金短信批量发送
 * 
 * @Class Name LoanSmsBatchSendService
 * @author zhaojunlei
 * @Create In 2015年12月17日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/smsdata")
public class LoanSmsBatchSendFacade {

	// 日志
	private static final Logger log = LoggerFactory
			.getLogger(LoanSmsBatchSendFacade.class);
	@Autowired
	private SmsLoanSendListMapper smsSendListMapper;
	@Autowired
	private LoanSmsBatchSendService loanSmsBatchSendService;

	/**
	 * 汇金批量发送短信 By zhaojunlei
	 */
	
	@RequestMapping(value = "loanSmsBatchSend")
	public String  loanSmsBatchSend(@RequestParam Map<String,Object> map,Model model) {
		int i = 0;
		log.debug("【汇金】汇金短信批量发送批处理开始运行，当前时间是：" + new Date());
		//String quantity = (String) map.get("quantity");
		map.put("sendStatus", Constants.SMSSENDSTATUS_WAIT);
		List<SmsSendList> list = smsSendListMapper
				.getAllSmsSendList(map);
		if (ComUtils.isEmptyList(list)) {
			log.debug("【汇金】没有要发送短信的数据，当前时间是：" + new Date());
			return "borrow/smsdata/complete1";
		} else {
			// 批量发送
			 i = loanSmsBatchSendService.LoanSmsBatchthridSend(list);
		}
		log.debug("【汇金】汇金短信批量发送批处理结束运行，发送短信" + i + "条。当前时间是：" + ComUtils.nowTime(Constants.DATAFORMAT[3]));
		model.addAttribute("dateCount",i+"");
		return "borrow/smsdata/complete";
	}
	
	@RequestMapping(value = "sendOut")
	public  String  sendOut(){
		return "borrow/smsdata/smsdata";
	}
	
	@RequestMapping(value = "queryCount")
    @ResponseBody
    public String  queryCount(Model model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam  Map<String, Object> map) throws Exception{
		map.put("sendStatus", Constants.SMSSENDSTATUS_WAIT);
   	    String counts = smsSendListMapper.queryCount(map);
   	    return counts;
   	 
    }
	
	@RequestMapping(value = "stopSms")
    @ResponseBody
	public String stopSms(String flag){
	    loanSmsBatchSendService.stopSms(flag);
		return "success";
	}
}