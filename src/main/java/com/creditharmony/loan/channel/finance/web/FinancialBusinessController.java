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
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.channel.finance.entity.FinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.service.FinancialBusinessService;
import com.creditharmony.loan.channel.finance.view.FinancialBusinessView;
import com.creditharmony.loan.common.utils.CommonDateUtils;

/**
 * 大金融业务列表控制器
 * 
 * @Class FinancialBusinessController
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/financial/business")
public class FinancialBusinessController extends BaseController {
	@Autowired
	private FinancialBusinessService financialBusinessService;
	@RequestMapping(value = { "init" })
	public String init(HttpServletRequest request,
			HttpServletResponse response, FinancialBusinessView params,
			Model model) {
		// 检索条件回显
		if (params != null) {
			model.addAttribute("params", params);
			// 获得符合大金融业务数据列表
			Page<FinancialBusinessEntity> financialBusiness = financialBusinessService.getFinancialBusinessList(new
					Page<FinancialBusinessEntity>(request,response), params);
			Map<String,Object> mapSum = financialBusinessService.getSumFinancialBusinessList(params);
			
			List<FinancialBusinessEntity> financeList = financialBusiness.getList();
			
			List<FinancialBusinessEntity> financeListShow = new ArrayList<FinancialBusinessEntity>();
			
			BigDecimal totalDeducts = BigDecimal.ZERO;
			if (ArrayHelper.isNotEmpty(financeList)) {
				for (int i = 0; i < financeList.size(); i++) {
					FinancialBusinessEntity financialBusinessEntity =financeList.get(i);
					BigDecimal deducts = financeList.get(i).getContractAmount();
					if (deducts != null){
						totalDeducts = totalDeducts.add(deducts);
						financialBusinessEntity.setLoanEndDate(CommonDateUtils
								.monthsAndDayLater(financialBusinessEntity
										.getLoanStartDate(), financialBusinessEntity
										.getLoanMonths(), -1));
						financeListShow.add(financialBusinessEntity);
					}
				}
			}
			
			model.addAttribute("financialBusiness",financialBusiness);
			model.addAttribute("financeListShow",financeListShow);
			model.addAttribute("count", financeList.size());
			model.addAttribute("amount",mapSum==null?"0":mapSum.get("totalamount"));
		}
		return "channel/finance/financialBusiness";
	}
}
