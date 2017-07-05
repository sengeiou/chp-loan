package com.creditharmony.loan.channel.finance.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.channel.finance.entity.CarFinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.service.CarFinancialBusinessService;
import com.creditharmony.loan.channel.finance.view.CarFinancialBusinessView;

/**
 * 大金融业务列表控制器
 * 
 * @Class FinancialBusinessController
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/carBusiness")
public class CarFinancialBusinessController extends BaseController {
	@Autowired
	private CarFinancialBusinessService carFinancialBusinessService;
	@RequestMapping(value = { "init" })
	public String init(HttpServletRequest request,
			HttpServletResponse response, CarFinancialBusinessView params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("params", params);
			// 获得符合大金融业务数据列表
			Page<CarFinancialBusinessEntity> carFinancialBusiness = carFinancialBusinessService.getFinancialBusinessList(new
					Page<CarFinancialBusinessEntity>(request,response), params);
			List<CarFinancialBusinessEntity> financeList = carFinancialBusiness.getList();
			BigDecimal totalDeducts = new BigDecimal(0);
			if (ArrayHelper.isNotEmpty(financeList)) {
				for (int i = 0; i < financeList.size(); i++) {
					BigDecimal deducts = financeList.get(i).getContractAmount();
					if (deducts != null){
						totalDeducts = totalDeducts.add(deducts);
					}
				}
			}
			model.addAttribute("financialBusiness",carFinancialBusiness);
			model.addAttribute("count", financeList.size());
			model.addAttribute("amount", totalDeducts);
		}
		return "channel/finance/carFinancialBusiness";
	}
}
