package com.creditharmony.loan.yunwei.fk.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.yunwei.fk.entity.FkOperateObj;
import com.creditharmony.loan.yunwei.fk.service.FkOperateService;
import com.sun.star.uno.Exception;

@Controller
@RequestMapping(value = "${adminPath}/fk/opt")
public class FkController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(FkController.class);
	
	@Autowired
	private FkOperateService fkOperateService;
	
	/**
	 * 跳转至风控操作页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goFkOperate")
	public String goFkOperate(Model model, HttpServletRequest request) {
		logger.info("跳转至风控操作数据页面");
		return "yunwei/fk/fk";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "operateTwo")
	public String operateTwo(Model model, HttpServletRequest request) {
		logger.info("操作风控核定数据。。。");
		if(!(UserUtils.getUser().getLoginName().equals("609295")
			|| UserUtils.getUser().getLoginName().equals("609471")
			|| UserUtils.getUser().getLoginName().equals("609348")
			|| UserUtils.getUser().getLoginName().equals("60000879")
			|| UserUtils.getUser().getLoginName().equals("606255"))) {
			model.addAttribute("errMsg", "没有权限操作");
			return "yunwei/fk/fk";
		}
		fkOperateService.fkOperateTwo();
		
		model.addAttribute("errMsg","");
		return "yunwei/fk/fk";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "operate")
	public String operate(Model model, HttpServletRequest request) {
		
		logger.info("操作风控核定数据。。。");
		if(!(UserUtils.getUser().getLoginName().equals("609295")
			|| UserUtils.getUser().getLoginName().equals("609471")
			|| UserUtils.getUser().getLoginName().equals("609348")
			|| UserUtils.getUser().getLoginName().equals("60000879")
			|| UserUtils.getUser().getLoginName().equals("606255"))) {
			model.addAttribute("errMsg", "没有权限操作");
			return "yunwei/fk/fk";
		}
		// 还款日
		String repaymentDate = request.getParameter("repaymentDate");
		//　合同编号
		String contractCodes = request.getParameter("contractCodes");
		if(StringUtils.isEmpty(repaymentDate)) {
			model.addAttribute("errMsg","请填写要操作的还款日期！");
			return "yunwei/fk/fk";
		}
		if(StringUtils.isEmpty(contractCodes)){
			model.addAttribute("errMsg","请填写要操作的合同编号！");
			return "yunwei/fk/fk";
		}
		FkOperateObj returnObj = new FkOperateObj();
		try {
			// 
			FkOperateObj parmasOperate = new FkOperateObj();
			// 设置参数
			parmasOperate.setContractCodes(contractCodes.trim());
			parmasOperate.setRepaymentDate(repaymentDate.trim());
			// 调用方法，返回参数
			returnObj = fkOperateService.fkOperate(parmasOperate);
		} catch (IOException e) {
			logger.error("操作时出现IO异常",e);
		} catch (Exception e) {
			logger.error("操作时出现异常",e);
		}
		if(!StringUtils.isEmpty(returnObj.getErrMsg())) {
			model.addAttribute("errMsg",returnObj.getErrMsg());
		}
		return "yunwei/fk/fk";
	}
	
}
