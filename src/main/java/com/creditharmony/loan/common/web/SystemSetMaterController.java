package com.creditharmony.loan.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.SystemSetMaterService;

@Controller
@RequestMapping(value = "${adminPath}/common/systemSetMater")
public class SystemSetMaterController extends BaseController{

	@Autowired
	private SystemSetMaterService systemSetMaterService;
	 
	@ResponseBody
	@RequestMapping(value = "save")
	public String save(SystemSetting sys){
		systemSetMaterService.save(sys);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="insert")
	public SystemSetting insertSysSetting(SystemSetting sys){
	    return systemSetMaterService.insertSysSetting(sys);
	}
    @ResponseBody
	@RequestMapping(value = "delete")
	public String delete(SystemSetting sys){
		systemSetMaterService.delete(sys);
		return "success";
	}
	@ResponseBody
	@RequestMapping(value = "getBean")
	public String getBean(SystemSetting sys){
		 sys =systemSetMaterService.get(sys);
		 return jsonMapper.toJson(sys);
	}
	
	/**
	 * 跳转待还款划扣页面 2016年1月6日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goDeductPaybackList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			 SystemSetting sys) {
		// paybackApply.setSplitFlag(RepayApplyStatus.PRE_PAYMENT.getCode());
		List<SystemSetting> sysPage = systemSetMaterService.findList(sys);
		model.addAttribute("sysPage", sysPage);
		return "comm/SystemSetMater";
	}
	
}
