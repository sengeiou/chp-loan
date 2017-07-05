package com.creditharmony.loan.yunwei.operate.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.yunwei.operate.entity.OperateObj;
import com.creditharmony.loan.yunwei.operate.service.OperateDataService;

/**
 * 修改SQL
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/yunwei/operate")
public class AdminOperateController extends BaseController  {
	
	private static Logger logger = LoggerFactory.getLogger(AdminOperateController.class);
	
	@Autowired
	private OperateDataService operateDataService;
	
	/**
	 * 跳转至查询页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goPageOperateData")
	public String goPageOperateData(Model model, HttpServletRequest request) {
		logger.info("跳转至修改数据页面");
		OperateObj operateObj = new OperateObj();
		model.addAttribute("operateObj", operateObj);
		return "yunwei/operate/operateData";
	}
	
	/**
	 * 修改数据
	 * @param queryOj
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "operateDataBySql")
	public String operateDataBySql(OperateObj operateObj,Model model, HttpServletRequest request) {
		if(!(UserUtils.getUser().getLoginName().equals("609295")
			|| UserUtils.getUser().getLoginName().equals("609471")
			|| UserUtils.getUser().getLoginName().equals("609348")
			|| UserUtils.getUser().getLoginName().equals("60000879"))) {
			model.addAttribute("error", "无权限操作该功能！");
			return "yunwei/operate/operateData";
		}
		// 
		if(StringUtils.isEmpty(operateObj.getSessionId()) ||
			StringUtils.isEmpty(operateObj.getUpdateSql())) {
			model.addAttribute("error", "修改标识和SQL语句不能为空，请检查！");
			return "yunwei/operate/operateData";
		}
		String updateSql = operateObj.getUpdateSql().trim();
		// 判断SQL是否以update或delete开头
		if(!(updateSql.startsWith("UPDATE ")
			|| updateSql.startsWith("DELETE ")
			|| updateSql.startsWith("INSERT "))) {
			model.addAttribute("error", "请输入正确的执行SQL！");
			return "yunwei/operate/operateData";
		}
		// 如果不包含where条件，则返回错误信息。
		if(updateSql.startsWith("UPDATE ") || updateSql.startsWith("DELETE ")) {
			if(!operateObj.getUpdateSql().trim().contains("WHERE ")){
				model.addAttribute("error", "SQL语句必须包含WHERE条件！");
				return "yunwei/operate/operateData";
			}
		}
		operateObj.setUpdateSql(updateSql);
		try {
			operateObj = operateDataService.updateDate(operateObj);
		} catch (Exception e) {
			logger.error("执行SQL语句时报错。",e);
			operateObj.setUpdateDataResult("执行SQL语句时报错，请检查SQL！" + e.getMessage());
			model.addAttribute("error", "执行SQL语句时报错，请检查SQL！");
		}
		model.addAttribute("operateObj", operateObj);
		// 
		return "yunwei/operate/operateData";
	}
	
}
