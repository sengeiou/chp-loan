package com.creditharmony.loan.channel.goldcredit.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCBusinessHelper;
import com.creditharmony.loan.channel.goldcredit.service.GCBusinessService;
import com.creditharmony.loan.channel.goldcredit.view.GCBusiness;
import com.google.common.collect.Maps;

/**
 * 金信业务列表
 * 
 * @Class Name GCBusinessController
 * @author 张建雄
 * @Create In 2016年2月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/business")
public class GCBusinessController extends BaseController {
	@Autowired
	private GCBusinessService service;
	@Autowired
	private ContractService contractService;
	
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, GCBusiness params, Model model) {
		if (params != null){
			model.addAttribute("params", params);
			Page<GCBusiness> creditorBack = service
					.findGCCeilingList(new Page<GCBusiness>(
							request, response), params);
			model.addAttribute("creditorBack", creditorBack);
		}
		return "channel/goldcredit/goldCreditBusinessList";
	}
	/**
	 * 金信业务导出 2016年2月25日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "exportBusiness")
	public void exportBusiness(HttpServletRequest request,
			HttpServletResponse response, String cid,GCBusiness params) {
		try {
			params.setChannel(ChannelFlag.JINXIN.getCode());
			params.setLoanCodes(cid);
			Map<String ,Object> conditions = Maps.newHashMap();
			if (StringUtils.isNotEmpty(params.getStoreOrgId())){
				String []storeOrgIds = params.getStoreOrgId().split(",");
				conditions.put("storeOrgIds", storeOrgIds);
			}
			if (StringUtils.isNotEmpty(cid)) {
				String [] loanCodes = cid.split(",");
				conditions.put("loanCodes", loanCodes);
			}
			conditions.put("params", params);
			//导出Excel
			ExportGCBusinessHelper.exportData(conditions, response);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
}
