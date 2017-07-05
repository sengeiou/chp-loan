package com.creditharmony.loan.car.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;

/**
 * 车借流程已办列表Controller
 * @Class Name CarLoanWorkItemsController
 * @author 陈伟东
 * @Create In 2016年25日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carLoanDoneList")
public class CarLoanDoneListController extends BaseController {
	

	@RequestMapping(value = "list")
	public String fetchTaskItems(Model model,
			@ModelAttribute(value = "carLoanFlowQueryParam") CarLoanFlowQueryView queryParms) throws Exception{
		
		
		return null;
	}
}
