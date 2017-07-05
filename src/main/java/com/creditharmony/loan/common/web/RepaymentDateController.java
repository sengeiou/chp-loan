package com.creditharmony.loan.common.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.service.RepaymentDateService;

/**
 * 查询还款日
 * @Class Name CityInfoController
 * @author 翁私
 * @Create In 2016年3月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/repaymentDate")
public class RepaymentDateController {
	
	@Autowired
	private RepaymentDateService dateService;
	
	@RequestMapping(value = "getRepaymentDate")
	public List<GlBill> getRepaymentDate(){
		List<GlBill> list= dateService.getRepaymentDate();
		return list;
	}
	
	

}
