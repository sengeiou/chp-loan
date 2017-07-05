package com.creditharmony.loan.channel.finance.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
import com.creditharmony.loan.channel.finance.excel.Excelutils;
import com.creditharmony.loan.channel.finance.service.CreditorConfirmService;
import com.creditharmony.loan.channel.finance.view.CreditorConfirmParam;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.CommonDateUtils;

/**
 * 债务确认列表控制器
 * 
 * @Class CreditorConfirmController
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/confirm")
public class CreditorConfirmController extends BaseController {
	@Autowired
	private CreditorConfirmService creditorConfirmService;
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, CreditorConfirmParam params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("creditorComfirmParam", params);
		}

		Page<CreditorConfirmEntity> creditorConfirmList = creditorConfirmService
				.findSettlementOfClaimsList(new Page<CreditorConfirmEntity>(
						request, response), params);
		Map<String,Object> mapSum = creditorConfirmService.findSumCreditorConfirmList(params);
		List<CreditorConfirmEntity> creditorList = creditorConfirmList
				.getList();
		List<CreditorConfirmEntity> creditorListShow = new ArrayList<CreditorConfirmEntity>();
		
		BigDecimal totalDeducts = BigDecimal.ZERO;
		if (ArrayHelper.isNotEmpty(creditorList)) {
			for (int i = 0; i < creditorList.size(); i++) {
				CreditorConfirmEntity creditorConfirmEntity = creditorList.get(i);
				BigDecimal deducts = creditorList.get(i).getContractAmount();
				if (deducts != null) {
					totalDeducts = totalDeducts.add(deducts);
					creditorConfirmEntity.setLoanEndDate(CommonDateUtils
							.monthsAndDayLater(
									creditorConfirmEntity.getLoanStartDate(),
									creditorConfirmEntity.getLoanMonths(), -1));
	
					creditorListShow.add(creditorConfirmEntity);
				}
			}
		}
		model.addAttribute("creditorConfirm", creditorConfirmList);
		model.addAttribute("count", creditorList.size());
		model.addAttribute("amount", mapSum==null?"0":mapSum.get("totalamount"));
		model.addAttribute("creditorListShow", creditorListShow);
		return "channel/finance/creditorConfirm";
	}
	/**
	 * 债权结清导出 2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "impCreditorConfirmList")
	public void impCreditorConfirmList(HttpServletRequest request,
			HttpServletResponse response, String cid,CreditorConfirmParam params,Model model) {
		String sqlPath = "com.creditharmony.loan.channel.finance.dao.CreditorConfirmDao.exportCreditorConfirmList";
		String fileName = FileExtension.CREDITOR_CONFIRM + System.currentTimeMillis();
		String[] header = {"借款ID","债务人","身份证号","借款用途","借款类型","原始期限（月）","原始借款开始日期","原始借款到期日期","还款方式 ","还款日","还款金额","债权金额（元） ","债权月利率（%）",
				"债权转入金额（元）","债权转入期限（月）","债权转出日期","债权人",};
		String[] body = {"contract_code","loan_customer_name","loan_customer_num","label","CLASS_TYPE","contract_months","contract_fact_day","contract_end_day",
				"dict_repay_method","contract_replay_day","contract_month_repay_amount",
				"contract_amount","fee_month_rate","contract_amount","contract_months","contract_end_day","middle_name"};
		// 如果有进行选择，获得选中单子的list
		Map<String,Object> queryMap = creditorConfirmService.exportCreditorConfirmList(cid,params,response);
		Excelutils.exportExcel(queryMap, response, sqlPath, fileName, header, body);	
	}
	/**
	 * @param request
	 * @param response
	 * @param params 查询参数信息
	 * @param model
	 * @param cid 
	 * @return
	 */
	@RequestMapping(value = "confirmCreditorConfirmList")
	public String confirmCreditorConfirmList(HttpServletRequest request,
			HttpServletResponse response, CreditorConfirmParam params,
			Model model, String cid) {
		if (StringUtils.isNotEmpty(cid)) {
			creditorConfirmService.updateCreditorConfirm(cid);
		}
		return init(request, response, params, model);
	}
}
