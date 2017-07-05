package com.creditharmony.loan.channel.finance.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
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
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.car.creditorRights.service.CreditorRightsService;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
import com.creditharmony.loan.channel.finance.service.CarCreditorConfirmService;
import com.creditharmony.loan.channel.finance.view.CreditorConfirmParam;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.service.LoanPrdService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 债务确认列表控制器
 * 
 * @Class CarCreditorConfirmController
 * @author 陈伟丽
 * @Create In 2016年5月17日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/carConfirm")
public class CarCreditorConfirmController extends BaseController {
	@Autowired
	private CarCreditorConfirmService carCreditorConfirmService;
	@Autowired
	private LoanPrdService loanPrdService;
	@Autowired
	private CreditorRightsService creditorRightsService;
	
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, CreditorConfirmParam params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("creditorComfirmParam", params);
		}
		 Page<CreditorConfirmEntity> creditorConfirmList = carCreditorConfirmService.findCarSettlementOfClaimsList(new
		 Page<CreditorConfirmEntity>(request,response), params);
		 List<CreditorConfirmEntity> creditorList = creditorConfirmList.getList();
		 BigDecimal totalDeducts = new BigDecimal(0);
		 if (ArrayHelper.isNotEmpty(creditorList)) {
			  for (int i = 0; i < creditorList.size(); i++) {
				  BigDecimal deducts = creditorList.get(i).getContractAmount();
				  if (deducts != null){
					  totalDeducts = totalDeducts.add(deducts);
				  }
			}
		  }
		  model.addAttribute("creditorConfirm",creditorConfirmList);
		  model.addAttribute("count", creditorList.size());
		  model.addAttribute("amount", totalDeducts);
		return "channel/finance/carCreditorConfirm";
	}
	
	/**
	 * 债权结清导出 2016年5月17日 By 陈伟丽
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "impCarCreditorConfirmList")
	public void impCreditorConfirmList(HttpServletRequest request,
			HttpServletResponse response, CreditorConfirmParam params,Model model) {
		DecimalFormat myformat=new DecimalFormat("0.00");
		List<CreditorConfirmEntity> s = carCreditorConfirmService.exportCarCreditorConfirmList(params);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (CreditorConfirmEntity ex : s) {
			ex.setClassType(loanPrdService.getPrdLabelByTypeAndCode("products_type_car_credit", ex.getClassType()));
			ex.setDictLoanUse(DictUtils.getLabel(dictMap,LoanDictType.JK_LOAN_USE, ex.getDictLoanUse()));
			ex.setContractMonthRepayAmount(new BigDecimal(myformat.format(ex.getContractMonthRepayAmount())));
			ex.setContractAmount(new BigDecimal(myformat.format(ex.getContractAmount())));
			ex.setCreditorImportMoney(ex.getContractAmount());
			ex.setCreditorImportMonths(ex.getLoanMonths());
			ex.setCreditorOutDate(ex.getLoanEndDate());
		}
		ExcelUtils excelutil = new ExcelUtils();
		excelutil.exportExcel(s, FileExtension.CREDITOR_CONFIRM, null,
				CreditorConfirmEntity.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_DATA, response, null);
	}
	/**
	 * @param request
	 * @param response
	 * @param params 查询参数信息
	 * @param model
	 * @param cid 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirmCarCreditorConfirmList")
	public String confirmCarCreditorConfirmList(HttpServletRequest request,
			HttpServletResponse response, CreditorConfirmParam params,Model model) {
		Map<String,Object> param = Maps.newHashMap();
		String cid = params.getCid();
		if (StringUtils.isEmpty(params.getCid())) {
			List<CreditorConfirmEntity> s = carCreditorConfirmService.exportCarCreditorConfirmList(params);
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i<s.size();i++){
				if(i!=0){
					sb.append(",");
				}
				CreditorConfirmEntity creditorConfirmEntity = s.get(i);
				sb.append("'"+creditorConfirmEntity.getId()+"'");
			}
			cid = sb.toString();
		}
		param.put("cid", cid);
		param.put("creditType",CreditorRights.CREDIT_TYPE_HASCONFIRM);
		param.put("creditConfirmDate", new Date());
		creditorRightsService.updateCarCreditorForConfirm(param);
		return "true";
	}
}
