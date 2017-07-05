package com.creditharmony.loan.borrow.payback.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.service.PlatformGotoRuleManager;

/**
 * 划扣规则配置
 * @Class Name PlatformGotoRuleController
 * @author 韩龙
 * @Create In 2016年3月5日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/plantskiporder")
public class PlatformGotoRuleController extends BaseController{
	
	@Autowired
	private PlatformGotoRuleManager platformGotoRuleManager;
	
	/**
	 * 列表
	 * 2016年3月5日
	 * By 韩龙
	 * @param platformGotoRule
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryPage")
	public String platformMngList(PlatformGotoRule platformGotoRule,Model model,
			HttpServletRequest request,	HttpServletResponse response){
		Page<PlatformGotoRule> page = new Page<PlatformGotoRule>(request, response);
		//platformGotoRule.setStatus("3");
		page = platformGotoRuleManager.findPage(page, platformGotoRule);
		model.addAttribute("page", page);
		model.addAttribute("platformGotoRule", platformGotoRule);
		return "borrow/payback/centerdeduct/platformList";
	}
	
	/**
	 * 调转
	 * 2016年3月5日
	 * By 韩龙
	 * @return
	 */
	@RequestMapping(value = "addOrEdit" )
	public String addPlatformEntity(){
		return "borrow/payback/centerdeduct/platformDetailed";
	}
	
	/**
	 * 获取单个对象
	 * 2016年3月5日
	 * By 韩龙
	 * @param platformGotoRule
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get")
	public String getPlatformEntity(PlatformGotoRule platformGotoRule,Model model){
		platformGotoRule = platformGotoRuleManager.get(platformGotoRule);
		model.addAttribute("platformGotoRule", platformGotoRule);
		model.addAttribute("get", "get");
		return "borrow/payback/centerdeduct/platformDetailed";
	}
	
	/**
	 * 保存修改
	 * 2016年3月5日
	 * By 韩龙
	 * @param platformEntity
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String savePlatformEntity(PlatformGotoRule platformEntity,Model model,
			HttpServletRequest request,	HttpServletResponse response){
		PlatformGotoRule rule = new PlatformGotoRule();
		BeanUtils.copyProperties(platformEntity, rule);
		rule.setStatus("3");
		rule = platformGotoRuleManager.get(rule);
		if (!ObjectHelper.isEmpty(rule) && !"".equals(platformEntity.getId())) {
			// 存在同一platformId的数据，则进行修改操作
			platformEntity.setIsNewRecord(false);
			platformEntity.setId(rule.getId());
			addMessage(model, "修改规则成功");
		} else {
			addMessage(model, "新增规则成功");
		}
		platformGotoRuleManager.save(platformEntity);
		return platformMngList(new PlatformGotoRule(),model,request,response);
	}
	
	/**
	 * 添加平台时校验平台的重复性
	 * 2016年4月1日
	 * By 周俊
	 * @param platformId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkPlatform")
	@ResponseBody
	public String checkPlatform(String platformId,String isConcentrate){
		PlatformGotoRule platformGotoRule = new PlatformGotoRule();
		platformGotoRule.setStatus("3");
		platformGotoRule.setPlatformId(platformId);
		platformGotoRule.setIsConcentrate(isConcentrate);
		try {
			platformGotoRule = platformGotoRuleManager.get(platformGotoRule);
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		if (ObjectHelper.isEmpty(platformGotoRule)) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	

	/**
	 * 保存修改
	 * 2016年5月11日
	 * By 翁私
	 * @param platformEntity
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(PlatformGotoRule platformEntity,Model model,
			HttpServletRequest request,	HttpServletResponse response){
		try {
			 platformGotoRuleManager.delete(platformEntity);
			 addMessage(model,"删除成功！");
		} catch (Exception e) {
			 addMessage(model,"删除失败！");
		}
		return platformMngList(new PlatformGotoRule(),model,request,response);
	}
}
