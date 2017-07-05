package com.creditharmony.loan.borrow.payback.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.service.HistoricalRecordsService;

/**
 * 还款历史记录弹框
 * @Class Name HistoricalRecordsController
 * @author 李强
 * @Create In 2015年12月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/historicalRecords")
public class HistoricalRecordsController extends BaseController  {
	
	@Autowired
	private HistoricalRecordsService historicalRecordsService;
	
	/**
	 * 还款历史记录弹框
	 * 2015年12月23日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackOpe
	 * @return 转至historicalRecords.jsp页面
	 */
	@RequestMapping(value = "historicalRecords")
	public String historicalRecords(HttpServletRequest request,HttpServletResponse response,Model model,PaybackOpe paybackOpe){
		Page<PaybackOpe> waitPage = null;
		if(StringUtils.isEmpty(paybackOpe.getDictLoanStatus())){
			// 共通历史记录弹框
			PaybackOpe paybackOpes = historicalRecordsService.queryLoanCode(paybackOpe.getrPaybackId());
			paybackOpe.setLoanCode(paybackOpes.getLoanCode());
			waitPage = historicalRecordsService.allHistoricalRecordsList(new Page<PaybackOpe>(request, response),paybackOpe);
		}else{
			// 逾期管理历史记录弹框
			waitPage = historicalRecordsService.allHistoricalRecordsLists(new Page<PaybackOpe>(request, response),paybackOpe);
		}
		for(PaybackOpe po:waitPage.getList()){
			String dictLoanStatusLabel=DictCache.getInstance().getDictLabel("jk_repayment_serial",po.getDictLoanStatus());
			po.setDictLoanStatusLabel(dictLoanStatusLabel);
			String operateResultLabel=DictCache.getInstance().getDictLabel("jk_payback_operate",po.getOperateResult());
			po.setOperateResultLabel(operateResultLabel);
		}
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("paybackOpe", paybackOpe);
		return "borrow/payback/repayment/historicalRecords";
	}
	
	/**
	 * 集中划扣已拆分历史记录弹框
	 * 2015年12月24日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackSplit
	 * @return 转至splitHis.jsp页面
	 */
	@RequestMapping(value = "allSplitHis")
	public String allSplitHis(HttpServletRequest request,HttpServletResponse response,Model model,PaybackSplit paybackSplit){
		Page<PaybackSplit> waitPage = historicalRecordsService.allSplitHisList(new Page<PaybackSplit>(request, response), paybackSplit);
		
		for(PaybackSplit ps:waitPage.getList()){
			String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",ps.getDictDealType());
		    ps.setDictDealTypeLabel(dictDealType);
			
			String splitBackResult=DictCache.getInstance().getDictLabel("jk_payback_operate",ps.getSplitBackResult());
			ps.setSplitBackResultLabel(splitBackResult);
		}
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("PaybackSplit", paybackSplit);
		return "borrow/payback/repayment/splitHis";
	}
}
