package com.creditharmony.loan.credit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.credit.entity.CreditPaybackInfo;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.ex.CreditQueryRecordEx;
import com.creditharmony.loan.credit.service.CreditPaybackInfoService;
@Controller
@RequestMapping(value = "${adminPath}/credit/creditpaybackdetail")
public class CreditPayBackDetailController extends BaseController{

	@Autowired
	private CreditPaybackInfoService creditPaybackInfoService;
	
	
	
	/**
	 * 跳转页面
	 * 2016年3月31日
	 * By 李文勇
	 * @return
	 */
	@RequestMapping(value = "initPage")
	public String initPage(){
		
		return "credit/creditPayBackDetail";
		
	}
	
	/**
	 * 查询保证人代偿信息列表
	 * 2016年3月31日
	 * By 李文勇
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "showData")
	public List<CreditPaybackInfo> showData(CreditReportDetailed creditReportDetailed){
		List<CreditPaybackInfo> result = creditPaybackInfoService.showData(creditReportDetailed);
		return result;
	}
	
	/**
	 * 查询保证人代偿信息列表
	 * 2016年3月31日
	 * By 李文勇
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveData")
	public String saveData(CreditQueryRecordEx param){
		
		if(param != null && StringUtils.isNotEmpty(param.getLoanCode()) 
				&& StringUtils.isNotEmpty(param.getType())
				&& StringUtils.isNotEmpty(param.getRelId())){
			int result = creditPaybackInfoService.saveData(param);
			if(result > 0){
				return "true";
			}else{
				return "false";
			}
		}else{
			return "false";
		}
	}
	
	/**
	 * 查询保证人代偿信息列表
	 * 2016年3月31日
	 * By 李文勇
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteData")
	public String deleteData(CreditPaybackInfo param){
		int result = creditPaybackInfoService.deleteData(param);
		if(result > 0){
			return "true";
		}
		return "false";
	}
	
}
