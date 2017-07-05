/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webHistoryController.java
 * @Create By 朱静越
 * @Create In 2015年12月1日 下午2:39:02
 */
/**
 * @Class Name HistoryController
 * @author 朱静越
 * @Create In 2015年12月1日
 */
package com.creditharmony.loan.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.common.entity.EmailOpe;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.service.HistoryService;
/**
 * 查询借款历史功能控制层
 * @author 张进
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/common/history")
public class HistoryController extends BaseController {
	@Autowired
	private HistoryService historyService;

	/**
	 * 根据applyid查找借款历史信息 2015年12月8日 By 朱静越
	 * 
	 * @param request
	 *            request对象
	 * @param response
	 *            response对象
	 * @param m
	 *            Model对象，前后台数据传输载体
	 * @param loanStatusHis
	 *            查询条件，获取applyId查询
	 * @return
	 */
	@RequestMapping(value = "showLoanHisByApplyId")
	public String showLoanHisByApplyId(HttpServletRequest request,
			HttpServletResponse response, Model m, LoanStatusHis loanStatusHis) {
		Page<LoanStatusHis> page = new Page<LoanStatusHis>(request, response);
		// 数据库查询列表数据
		page = historyService.findLoanStatusHisList(page, loanStatusHis);

		// 传递数据到前台页面展示
		m.addAttribute("info", loanStatusHis);
		m.addAttribute("page", page);
		return "/borrow/history/history";
	}
	
	/**
	 * 根据applyid查找借款历史信息 2015年12月8日 By 朱静越
	 * 
	 * @param request
	 *            request对象
	 * @param response
	 *            response对象
	 * @param m
	 *            Model对象，前后台数据传输载体
	 * @param loanStatusHis
	 *            查询条件，获取LoanCode查询
	 * @return
	 */
	@RequestMapping(value = "showLoanHisByLoanCode")
	public String showLoanHisByLoanCode(HttpServletRequest request,
			HttpServletResponse response, Model m, LoanStatusHis loanStatusHis) {
		Page<LoanStatusHis> page = new Page<LoanStatusHis>(request, response);
		// 数据库查询列表数据
		page = historyService.findHisPageByLoanCode(page, loanStatusHis);

		// 传递数据到前台页面展示
		m.addAttribute("info", loanStatusHis);
		m.addAttribute("page", page);
		return "/borrow/history/Loanhistory";
	}
	

	/**
	 * 测试用的方法 2015年12月9日 By 朱静越
	 * 
	 * @return
	 */
	@RequestMapping(value = "insert")
	public String insertHistory() {
		LoanInfo loaninfo = new LoanInfo();
		historyService.saveLoanStatusHis(loaninfo, "测试添加", "成功", "这里是备注信息");
		return "redirect:" + adminPath
				+ "/common/history/showLoanHisByApplyId?applyId=5";
	}

	/**
	 * 还款操作流水(集中划扣)
	 * 2016年2月19日
	 * By 王彬彬
	 * @param request 请求
	 * @param response 返回
	 * @param model model
	 * @param rPaybackId 还款主表ID
	 * @param payBackApplyId 还款申请ID
	 * @return 还款操作流水
	 */
	@RequestMapping(value = "showPayBackHis")
	public String showPayBackHis(HttpServletRequest request,
			HttpServletResponse response, Model model, String rPaybackId,
			String payBackApplyId,String lisi) {
		try {
			Page<PaybackOpe> pageOpe = new Page<PaybackOpe>(request, response);
			Page<PaybackSplit> pageSplit = new Page<PaybackSplit>(request, response);
			// 数据库查询列表数据
			if(StringUtils.isNotEmpty(lisi)){
				pageSplit= historyService.getPaybackSplit(rPaybackId,payBackApplyId,pageSplit);
			}else{
				pageOpe = historyService.getPaybackOpe(payBackApplyId, rPaybackId,lisi,
						TargetWay.PAYMENT, pageOpe);
			}
			// 传递数据到前台页面展示
			if(StringUtils.isNotEmpty(lisi)){
			model.addAttribute("page", pageSplit);
			}else{
				model.addAttribute("page", pageOpe);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("还款操作流水获取失败,还款主表ID："+rPaybackId+"还款申请表ID：" + payBackApplyId);
		}
		model.addAttribute("rPaybackId", rPaybackId);
		model.addAttribute("payBackApplyId", payBackApplyId);
		model.addAttribute("lisi", lisi);
		if(StringUtils.isNotEmpty(lisi)){
			
			return "/borrow/history/paybackspl";
		}else{
			
			return "/borrow/history/paybackhis";
		}
	}
	
	/**
	 * 还款操作流水(非集中划扣)
	 * 2016年2月19日
	 * By zhangfeng
	 * @param request 请求
	 * @param response 返回
	 * @param model model
	 * @param rPaybackId 还款主表ID
	 * @param payBackApplyId 还款申请ID
	 * @return 还款操作流水
	 */
	@RequestMapping(value = "showNoDeductPaybackHistory")
	public String showNoDeductPaybackHistory(HttpServletRequest request,
			HttpServletResponse response, Model model, String rPaybackId,
			String payBackApplyId,String dictLoanStatus) {
		Page<PaybackOpe> pageOpe = new Page<PaybackOpe>(request, response);
		try {
			// 数据库查询列表数据
			pageOpe = historyService.getPaybackOpeHistory(payBackApplyId, rPaybackId, dictLoanStatus, null, pageOpe);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("还款操作流水获取失败,还款主表ID："+rPaybackId+"还款申请表ID：" + payBackApplyId);
		}
		model.addAttribute("rPaybackId", rPaybackId);
		model.addAttribute("payBackApplyId", payBackApplyId);
		// 传递数据到前台页面展示
		model.addAttribute("page", pageOpe);
		return "/borrow/history/paybackhis";
	}
	
	/**
	 * 催收服务费催收历史
	 * 2016年2月19日
	  * By 王彬彬
	 * @param request 请求
	 * @param response 返回
	 * @param model model
	 * @param rPaybackId 还款主表ID
	 * @param payBackApplyId 还款申请ID
	 * @return 催收服务费历史
	 */
	@RequestMapping(value = "showFeekHis")
	public String showFeekHis(HttpServletRequest request,
			HttpServletResponse response, Model model, 
			String payBackApplyId ) {
		try {
			Page<PaybackOpe> page = new Page<PaybackOpe>(request, response);
			// 数据库查询列表数据
			page = historyService.getPaybackOpe(null, payBackApplyId,null,
					TargetWay.SERVICE_FEE, page);
			// 传递数据到前台页面展示
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("催收服务费流水获取失败,ID："+payBackApplyId);
		}
		model.addAttribute("payBackApplyId", payBackApplyId);
		return "/borrow/history/paybackhis";
	}

	/**
	 * 退款操作历史
	 * 根据合同号查询 
	 */
	@RequestMapping(value = "showPaybackHistory")
	public String showPaybackHistory(HttpServletRequest request,
			HttpServletResponse response, Model model, String contractCode) {
		try {
			Page<PaybackHistory> pageSplit = new Page<PaybackHistory>(request, response);
			// 数据库查询列表数据	
			pageSplit= historyService.showPaybackHistory(contractCode,pageSplit);
			// 传递数据到前台页面展示
			model.addAttribute("page", pageSplit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("还款操作流水获取失败,合同编号："+contractCode);
		}
		return "/borrow/history/paybackHistory";
	}
	
	/**
	 * 邮件操作历史
	 * @author 于飞
	 * @Create 2017年3月8日
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackMonthId
	 * @return
	 */
	@RequestMapping(value = "showEmailOpe")
	public String showEmailOpe(HttpServletRequest request,
			HttpServletResponse response, Model model, String emailId,String contractCode){
		try{
			Page<EmailOpe> page = new Page<EmailOpe>(request, response);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("emailId", emailId);
			map.put("contractCode", contractCode);
			page = historyService.showEmailOpe(map,page);
			// 传递数据到前台页面展示
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("邮件获取历史数据失败,ID："+emailId);
		}
		model.addAttribute("emailId", emailId);
		return "/borrow/history/emailOpe";
	}
}