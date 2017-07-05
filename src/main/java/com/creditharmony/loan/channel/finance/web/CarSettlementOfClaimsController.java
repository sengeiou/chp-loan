package com.creditharmony.loan.channel.finance.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.channel.finance.entity.CarSettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
import com.creditharmony.loan.channel.finance.excel.Excelutils;
import com.creditharmony.loan.channel.finance.service.CarSettlementOfClaimsService;
import com.creditharmony.loan.channel.finance.view.SettlementOfClaimsParams;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 债权结清列表控制器
 * 
 * @Class settlementOfClaimsController
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/carSettlement")
public class CarSettlementOfClaimsController extends BaseController {
	@Autowired
	private CarSettlementOfClaimsService carSettlementOfClaimsService;

	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, SettlementOfClaimsParams params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("params", params);
			Page<CarSettlementOfClaimsEntity> pageList = carSettlementOfClaimsService
					.findSettlementOfClaimsList(new Page<CarSettlementOfClaimsEntity>(
							request, response), params);
			List<CarSettlementOfClaimsEntity> settlementList = pageList.getList();
			BigDecimal decimal = new BigDecimal(0);
			if (ArrayHelper.isNotEmpty(settlementList)) {
				for (int i = 0; i < settlementList.size(); i++) {
					BigDecimal contractAmount = settlementList.get(i)
							.getContractAmount();
					if (contractAmount != null) {
						decimal = decimal.add(contractAmount);
					}
				}
			}
			model.addAttribute("settlementList", pageList);
			model.addAttribute("contractAmount", decimal);
			model.addAttribute("settlementCount", settlementList.size());
		}
		return "channel/finance/carSettlementOfClaims";
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
			HttpServletResponse response,SettlementOfClaimsParams params) {
		DecimalFormat myformat=new DecimalFormat("0.00");
		List<CarSettlementOfClaimsEntity> s = carSettlementOfClaimsService.findSettlementOfClaimsList(params);
		for (CarSettlementOfClaimsEntity ex : s) {
			ex.setContractAmount(new BigDecimal(myformat.format(ex.getContractAmount())));
		}
		ExcelUtils excelutil = new ExcelUtils();
		excelutil.exportExcel(s, FileExtension.CREDITOR_SETTLE, null,
				CarSettlementOfClaimsEntity.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_DATA, response, null);
	}

	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "confirmSettlementOfClaimsList")
	public String confirmSettlementOfClaimsList(HttpServletRequest request,
			HttpServletResponse response, SettlementOfClaimsParams params,
			Model model) {
		if (StringUtils.isNotEmpty(params.getCid())) {
			carSettlementOfClaimsService.updateCarSettlementConfirm(params);
		}
		return  "已提交确认成功";
	}
}
