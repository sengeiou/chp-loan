package com.creditharmony.loan.borrow.payback.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PlantSkipOrder;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.service.PlantSkipOrderService;

/**
 * 平台跳转顺序表
 * 
 * @Class Name BankPlantPortController
 * @author 翁私
 * @Create In 2016年4月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/plantskipordernew")
public class PlantSkipOrderController extends BaseController{
	
	@Autowired
	private PlantSkipOrderService plantSkipOrderService;
	
	/**
	 * 增加平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "save")
	public String insert(PlantSkipOrder record,HttpServletRequest request, HttpServletResponse response, Model model){
		if(ObjectHelper.isEmpty(record.getId())){
			plantSkipOrderService.insert(record);
			addMessage(model, "新增规则成功");
		}else{
			plantSkipOrderService.update(record);
			addMessage(model, "修改规则成功");
		}
		return queryPage(new PlantSkipOrder(),request,response,model);
	}
	

	/**
	 * 删除平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "delete")
	public String delete(PlantSkipOrder record,HttpServletRequest request, HttpServletResponse response, Model model){
		String id = appendString(record.getId());
		record.setId(id);
		try{
			plantSkipOrderService.delete(record);
			addMessage(model,"删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			addMessage(model,"删除失败！");
		}
		return queryPage(new PlantSkipOrder(),request,response,model);
	}
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryPage")
	public String queryPage(PlantSkipOrder record,HttpServletRequest request, HttpServletResponse response, Model model){
		Page<PlantSkipOrder> bankPage  = plantSkipOrderService.queryPage(new Page<PlantSkipOrder>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		return "borrow/payback/centerdeduct/plantSkipOrder";
	}
	
	@ResponseBody
	@RequestMapping(value = "getBean")
	public String getBean(PlantSkipOrder record){
		record =plantSkipOrderService.selectByPrimaryKey(record);
		 return jsonMapper.toJson(record);
	}
	
	
	/**
	 * 获取单个对象
	 * 2017年4月14日
	 * By wengsi
	 * @param platformGotoRule
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get")
	public String getPlatformEntity(PlantSkipOrder record,Model model){
		record = plantSkipOrderService.getById(record);
		model.addAttribute("record", record);
		model.addAttribute("get", "get");
		return "borrow/payback/centerdeduct/plantSkipDetail";
	}
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (String id : idArray) {
			if (id != null && !"".equals(id)) {
				parameter.append("'" + id + "',");
			}
		}
		String idstring = null;
		if (parameter != null) {
			idstring = parameter.toString();
			idstring = idstring.substring(0, parameter.lastIndexOf(","));
		}
		return idstring;
	}
	
	/**
	 * 调转
	 * 2017年4月13日
	 * By 翁私
	 * @return
	 */
	@RequestMapping(value = "goAdd" )
	public String goAdd(){
		return "borrow/payback/centerdeduct/plantSkipDetail";
	}
	
	
	/**
	 * 添加平台时校验平台的重复性
	 * 2017年4月14日
	 * By 翁私
	 * @param platformId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkPlatform")
	@ResponseBody
	public String checkPlatform(String bankCode,String isConcentrate){
		PlantSkipOrder record = new PlantSkipOrder();
		record.setBankCode(bankCode);
		record.setIsConcentrate(isConcentrate);
		try {
			record = plantSkipOrderService.findSkip(record);
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		if (ObjectHelper.isEmpty(record)) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
	

}
