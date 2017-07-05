package com.creditharmony.loan.credit.web;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.credit.entity.CreditCpfDetailed;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.ex.CreditCpfDetailedEx;
import com.creditharmony.loan.credit.service.CreditCpfDetailedService;

/**
 * 公积金操作
 * @Class Name CreditCpfDetailedController
 * @author 李文勇
 * @Create In 2016年2月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/creditdetailed/accumulation")
public class CreditCpfDetailedController extends BaseController {

	@Autowired
	private CreditCpfDetailedService creditCpfDetailedService;
	
	/**
	 * 显示公积金画面
	 * 2016年2月1日
	 * By 李文勇
	 * @return jsp路径
	 */
	@RequestMapping(value="initPage")
	public String initPage(){
		return "credit/providentFundQuery";
	}
	
	/**
	 * 显示数据
	 * 2016年2月1日
	 * By 李文勇
	 * @return CreditCpfDetailedEx
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="showData")
	public CreditCpfDetailedEx showData(CreditReportDetailed creditReportDetailed) throws IllegalAccessException, 
		InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		CreditCpfDetailedEx result = creditCpfDetailedService.showData(creditReportDetailed);
		return result;
	}
	
	/**
	 * 删除数据
	 * 2016年2月1日
	 * By 李文勇
	 * @return int
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="deleteData")
	public int deleteData(CreditCpfDetailed record){
		int result = creditCpfDetailedService.deleteData(record);
		return result;
	}
	
	/**
	 * 保存数据
	 * 2016年2月1日
	 * By 李文勇
	 * @return 操作结了果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveData")
	public String saveData(CreditCpfDetailedEx creditCpfDetailedEx) throws IllegalAccessException, 
		InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		
		if(creditCpfDetailedEx != null && StringUtils.isNotEmpty(creditCpfDetailedEx.getLoanCode())
				&& StringUtils.isNotEmpty(creditCpfDetailedEx.getType())
				&& StringUtils.isNotEmpty(creditCpfDetailedEx.getRelId())){
			boolean result = creditCpfDetailedService.saveData(creditCpfDetailedEx);
			if(result){
				return "true";
			}else{
				return "false";
			}
		}else{
			return "false";
		}
	}
}
