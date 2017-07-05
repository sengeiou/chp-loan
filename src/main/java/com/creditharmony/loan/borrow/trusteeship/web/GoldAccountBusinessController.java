package com.creditharmony.loan.borrow.trusteeship.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.trusteeship.service.GoldAccountBusinessService;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountBusiness;

/**
 * 金账户业务列表
 * 
 * @Class Name GoldAccountBusinessController
 * @author 张建雄
 * @Create In 2016年3月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/trusteeship/business")
public class GoldAccountBusinessController extends BaseController {
	@Autowired
	private GoldAccountBusinessService service;

	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, GoldAccountBusiness params, Model model) {
		Page<GoldAccountBusiness> creditorBack = service
				.findGoldAccountCeilingList(new Page<GoldAccountBusiness>(
						request, response), params);
		for(GoldAccountBusiness gb:creditorBack.getList()){
			String  contractVersionLabel=DictCache.getInstance().getDictLabel("jk_contract_ver",gb.getContractVersion());
			gb.setContractVersionLabel(contractVersionLabel);
			
			String channelLabel=DictCache.getInstance().getDictLabel("jk_channel_flag",gb.getChannel());
			gb.setChannelLabel(channelLabel);
			
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",gb.getModel());
			gb.setModelLabel(modelLabel);
		}
		model.addAttribute("creditorBack", creditorBack);
		model.addAttribute("params", params);
		return "borrow/trusteeship/goldAccountBusinessList";
	}
	/**
	 * 金账户业务导出 2016年3月26日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid 
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "exportBusiness")
	public void exportBusiness(HttpServletRequest request,
			HttpServletResponse response, String cid,GoldAccountBusiness params) {
		 service.exportBusiness(cid, params, response);
	}
}
