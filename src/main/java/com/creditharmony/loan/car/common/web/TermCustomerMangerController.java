package com.creditharmony.loan.car.common.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

@Controller
@RequestMapping(value = "${adminPath}/common/termCustomer")
public class TermCustomerMangerController extends BaseController {
	
	@Autowired
	private UserInfoService userInfoService; 

	@ResponseBody
	@RequestMapping(value = "asynLoadCustManagers")
	public String asynLoadCustManagers(String termId) {
		List<UserInfo> termManagers = userInfoService.getTermAllCustomerManager(termId);
		return jsonMapper.toJson(termManagers);
	}

}
