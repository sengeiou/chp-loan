package com.creditharmony.loan.aops.web;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.loan.aops.aspects.PropetiesManage;

@Controller
@Component
@RequestMapping(value = "${adminPath}/aop/cotrl")
public class AopController {

	//http://127.0.0.1:8080/chp-sms/a/aop/cotrl/setGlobal
	@ResponseBody
	@RequestMapping(value = {"setGlobal"})
	public String setGlobal(Model model) {
		if(PropetiesManage.booAopFlgGlobal){
			PropetiesManage.booAopFlgGlobal = false;
			return "false";
		} else {
			PropetiesManage.booAopFlgGlobal = true;
			return "true";
		}
	}
}
