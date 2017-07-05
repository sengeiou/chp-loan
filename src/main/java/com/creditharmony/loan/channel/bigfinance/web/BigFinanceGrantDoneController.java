package com.creditharmony.loan.channel.bigfinance.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.channel.goldcredit.excel.ExportBigFinanceDoneHelper;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantDoneView;
import com.google.common.collect.Maps;

/**
 * 大金融已放款列表
 * @Class Name BigFinanceGrantDoneController
 * @author 朱静越
 * @Create In 2016年9月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/bigfinance/grantDone")
public class BigFinanceGrantDoneController extends BaseController {
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private UserManager userManager;
	
	/**
	 * 查询大金融已放款列表
	 * 2016年9月9日
	 * By 朱静越
	 * @param model
	 * @param grantDoneView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "grantDone")
	public String grantDone(Model model,GCGrantDoneView grantDoneView,
			HttpServletRequest request,HttpServletResponse response) {
		// 标示为大金融
		grantDoneView.setLoanFlag(ChannelFlag.ZCJ.getCode());
		
		Page<GCGrantDoneView> grantDonePage = grantDoneService.findGrantDoneList(
				new Page<GCGrantDoneView>(request, response), grantDoneView);
		//提交批次列表
		try {
			Map<String,String> batch = Maps.newHashMap();
			batch.put("loanFlag", ChannelFlag.ZCJ.getCode());
			batch.put("batch", "grantPch");
			List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
			//放款批次
			batch.put("batch", "grantBatch");
			List<String> grantBatchList = loanGrantService.findSubmitBatchList(batch);
			model.addAttribute("submitBatchList", submitBatchList);
			model.addAttribute("grantBatchList", grantBatchList);
		} catch (Exception e) {
			logger.error("在大金融放款审核中，查询提交批次集合失败，原因是："+e.getMessage());
		}
		
		model.addAttribute("loanGrantEx", grantDoneView);
		model.addAttribute("grantDoneList", grantDonePage);
		return "channel/bigfinance/bigFinanceGrantDoneList" ;
	}
	
	/**
	 * 导出已放款列表
	 * 2016年9月9日
	 * By 朱静越
	 * @param request
	 * @param grantDon
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "grantDoneExl")
	public void exportExcel(HttpServletRequest request,GCGrantDoneView grantDon,
			HttpServletResponse response, String idVal) {
		Map<String,Object> queryMap = Maps.newHashMap();
		String[] loanCodes={};
		grantDon.setLoanFlag(ChannelFlag.ZCJ.getCode());
		grantDon.setLoanCode(idVal);
		if (StringUtils.isNotEmpty(idVal)) {
			loanCodes = idVal.split(",");
		}
		try {
			if(loanCodes.length > 0)
				queryMap.put("loanCodes", loanCodes);
			queryMap.put("param", grantDon);
			ExportBigFinanceDoneHelper.exportData(queryMap, response,userManager);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	} 
}
