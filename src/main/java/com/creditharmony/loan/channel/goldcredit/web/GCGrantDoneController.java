package com.creditharmony.loan.channel.goldcredit.web;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.channel.common.web.WorkFlowController;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCDoneHelper;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantDoneView;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.google.common.collect.Maps;

/**
 * 金信已放款列表
 * @Class Name GrantDoneController
 * @author 董超
 * @Create In 2015年12月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/grantDone")
public class GCGrantDoneController extends WorkFlowController {
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private UserManager userManager;
	
	/**
	 * 查询金信已放款页面
	 * 2015年12月15日
	 * By 董超
	 * @param model
	 * @param loanGrantEx 放款实体
	 * @return
	 */
	@RequestMapping(value = "grantDone")
	public String grantDone(Model model,GCGrantDoneView grantDoneView,
			HttpServletRequest request,HttpServletResponse response) {
		// 查询放款状态为已放款的单子
		grantDoneView.setDictLoanStatus(LoanApplyStatus.LOAN_SENDED.getCode());
		// 标示为金信
		grantDoneView.setLoanFlag(ChannelFlag.JINXIN.getCode());
		
		Page<GCGrantDoneView> grantDonePage = grantDoneService.findGrantDoneList(
				new Page<GCGrantDoneView>(request, response), grantDoneView);
		List<GCGrantDoneView> list = grantDonePage.getList();
		BigDecimal totalGrantMoney = new BigDecimal("0.00"); 
		long totalNum = 0;
		if (ObjectHelper.isNotEmpty(list)) {
			totalGrantMoney = list.get(0).getTotalGrantMoney();
			totalNum = grantDonePage.getCount();
		}
		//提交批次列表
		try {
			Map<String,String> batch = Maps.newHashMap();
			batch.put("loanFlag", ChannelFlag.JINXIN.getCode());
			batch.put("batch", "grantPch");
			batch.put("dictLoanStatus", LoanApplyStatus.LOAN_SENDED.getCode());
			List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
			//放款批次
			batch.put("batch", "grantBatch");
			List<String> grantBatchList = loanGrantService.findSubmitBatchList(batch);
			model.addAttribute("submitBatchList", submitBatchList);
			model.addAttribute("grantBatchList", grantBatchList);
		} catch (Exception e) {
			logger.error("在金信放款审核中，查询提交批次集合失败，原因是："+e.getMessage());
		}
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalNum", totalNum);
		model.addAttribute("loanGrantEx", grantDoneView);
		model.addAttribute("grantDoneList", grantDonePage);
		return "channel/goldcredit/goldCredit_grantDoneList" ;
	}
	
	/**
	 * 导出已放款列表
	 * 2016年2月23日
	 * By 董超
	 * @param request
	 * @param response
	 * @param idVal 根据合同编号
	 */
	@RequestMapping(value = "grantDoneExl")
	public void exportExcel(HttpServletRequest request,GCGrantDoneView grantDon,
			HttpServletResponse response, String idVal) {
		Map<String,Object> queryMap = Maps.newHashMap();
		String[] loanCodes={};
		grantDon.setDictLoanStatus(LoanApplyStatus.LOAN_SENDED.getCode());
		grantDon.setLoanFlag(ChannelFlag.JINXIN.getCode());
		grantDon.setLoanCode(idVal);
		if (StringUtils.isNotEmpty(idVal)) {
			loanCodes = idVal.split(",");
		}
		try {
			if(loanCodes.length > 0)
				queryMap.put("loanCodes", loanCodes);
			queryMap.put("param", grantDon);
			ExportGCDoneHelper.exportData(queryMap, response,userManager);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	} 
	
	@Override
	protected String fetchTaskItems(Model model, LoanFlowQueryParam queryParam,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
