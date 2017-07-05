package com.creditharmony.loan.channel.finance.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.channel.finance.entity.SettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.excel.Excelutils;
import com.creditharmony.loan.channel.finance.service.SettlementOfClaimsService;
import com.creditharmony.loan.channel.finance.view.SettlementOfClaimsParams;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 债权结清列表控制器
 * 
 * @Class settlementOfClaimsController
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/settlement")
public class SettlementOfClaimsController extends BaseController {
	@Autowired
	private SettlementOfClaimsService settlementOfClaimsService;

	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, SettlementOfClaimsParams params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("params", params);
			Page<SettlementOfClaimsEntity> pageList = settlementOfClaimsService
					.findSettlementOfClaimsList(new Page<SettlementOfClaimsEntity>(
							request, response), params);
			Map<String, Object> mapSum = settlementOfClaimsService.findSumSettlementOfClaimsList(params);
			
			model.addAttribute("settlementList", pageList);
			model.addAttribute("totalamount", mapSum==null?"0":mapSum.get("totalamount"));
		}
		return "channel/finance/settlementOfClaims";
	}

	/**
	 * 债权结清导出 2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "impSettlementOfClaimsList")
	public void impSettlementOfClaimsList(HttpServletRequest request,
			HttpServletResponse response, String cid,SettlementOfClaimsParams params) {
		String sqlPath = "com.creditharmony.loan.channel.finance.dao.SettlementOfClaimsDao.exportSettlementOfClaimsList";
		String fileName = FileExtension.CREDITOR_SETTLE + System.currentTimeMillis();
		String[] header = {"借款ID","债务人","身份证号","借款用途","借款类型","原始期限(月)","原始借款开始日期","原始借款到期日期","还款方式","还款日","还款金额","债权金额(元)","债权月利率（%）","债权转入金额（元）","债权转入期限(月)","债权转出日期","债权人"};
		String[] body = {"contract_code","loan_customer_name","loan_customer_num","dict_loan_use",
						 "product_type","contract_months",
						 "contract_fact_day","contract_end_day","dict_repay_method","contract_replay_day",
						 "contract_month_repay_amount",
					"contract_amount","fee_month_rate","contract_amount","loan_in_months","contract_end_day","middle_name"};
		// 如果有进行选择，获得选中单子的list
		Map<String,Object> queryMap = settlementOfClaimsService.exportSettlementOfClaims(cid,params,response);
		Excelutils.exportExcel(queryMap, response, sqlPath, fileName, header, body);
	}

	@RequestMapping(value = "confirmSettlementOfClaimsList")
	public String confirmSettlementOfClaimsList(HttpServletRequest request,
			HttpServletResponse response, SettlementOfClaimsParams params,
			Model model, String cid) {
		if (StringUtils.isNotEmpty(cid)) {
			settlementOfClaimsService.updateSettlementOfClaims(cid);
		}
		return init(request, response, params, model);
	}
}
