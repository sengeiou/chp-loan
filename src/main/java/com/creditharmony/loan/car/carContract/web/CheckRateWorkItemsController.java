package com.creditharmony.loan.car.carContract.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 审核费率待办列表Controller
 * @Class Name CheckRateWorkItemsController
 * @author 陈伟东
 * @Create In 2016年2月13日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carContract/checkRateWorkItems")
public class CheckRateWorkItemsController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "fetchTaskItems")
	public String fetchTaskItems(Model model,
			@ModelAttribute(value = "carLoanFlowQueryParam") CarLoanFlowQueryView queryParms,
			HttpServletRequest request, FlowPage page,
			HttpServletResponse response) throws Exception{
		
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		ReflectHandle.copy(queryParms, param);
		
		String queue = "HJ_CAR_RATE_CHECK";
		TaskBean taskBean = flowService.fetchTaskItems(queue, param,
				CarLoanFlowWorkItemView.class);
		
//		
//		String args = new String();
//		page.setPageSize(30);
//		flowService.fetchTaskItems(queue, param, page, args,
//				ContractWorkItemView.class);
				
		List<CarLoanFlowWorkItemView> itemList = new ArrayList<CarLoanFlowWorkItemView>();

		if (taskBean.getItemList() != null) {
			itemList = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
		}
		model.addAttribute("itemList", itemList);
		return "car/contract/carLoanflow_check_rate_workItems";
	}
}
