/**
 * 
 */
package com.creditharmony.loan.borrow.pushdata.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.loan.borrow.pushdata.dao.PaybackMonthMapper;
import com.creditharmony.loan.borrow.pushdata.entity.PaybackList;
import com.creditharmony.loan.borrow.pushdata.service.LoanRepaymentDataPushService;
import com.creditharmony.loan.borrow.pushdata.util.ComUtils;

/**
 * 汇金待还款数据推送
 * @author 施大勇
 * 2016年1月15日
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/pushdata")
public class LoanRepaymentDataPushFacade {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(LoanRepaymentDataPushFacade.class);

	/**
	 * 还款_期供表
	 */
	@Autowired
	private PaybackMonthMapper paybackMonthMapper;
	
	@Autowired
	private LoanRepaymentDataPushService loanRepaymentDataPushService;
	
	/**
	 * 汇金待还款数据推送
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "pushRun")
	public String pushRun(@RequestParam Map<String,Object> map) throws Exception {
		logger.debug("汇金待还款数据推送处理开始。"+new Date());
		List<PaybackList> pmonthList = getData(map);
		if (ComUtils.isEmptyList(pmonthList)) {
			logger.debug("期供表无需要处理的数据，处理结束。");
			return null;
		}
		int i = 0;
		for (PaybackList modata : pmonthList) {
			i = loanRepaymentDataPushService.updatePaybacklist(i, modata);
		}
		logger.debug("处理正常结束。");
		return "borrow/pushdata/complete";
	}

	/**
	 * 获取期供表数据（T+3）
	 * @return 结果
	 */
	private List<PaybackList> getData(Map<String, Object> map) {
		// 声明数据检索用输入参数
		// 期供状态（待还款）
		map.put("dictMonthStatus", PeriodStatus.REPAYMENT.getCode());
		// 还款日（T+3）
		//Date threeDay = ComUtils.daysLater(new Date(),0);
		List<PaybackList> retList = paybackMonthMapper.selectMonthPayDayBeforeThreeDays(map);
		return retList;
	}
	
	public Date parseDate(String s) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(s);
    }
	
	@RequestMapping(value = "goPush")
	public  String goPush(){
		return "borrow/pushdata/pushdata";
	}
	
	 
    @RequestMapping(value = "queryCount")
    @ResponseBody
    public String  queryCount(Model model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam  Map<String, Object> map) throws Exception{
    	map.put("dictMonthStatus", PeriodStatus.REPAYMENT.getCode());
   	    String counts = paybackMonthMapper.queryCount(map);
   	    return counts;
   	 
    }
}
