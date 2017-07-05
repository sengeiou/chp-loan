package com.creditharmony.loan.credit.web;


import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.credit.entity.CreditCardDetailedOne;
import com.creditharmony.loan.credit.entity.CreditCardDetailedTwo;
import com.creditharmony.loan.credit.entity.CreditGuaranteeDetailed;
import com.creditharmony.loan.credit.entity.CreditLoanDetailedOne;
import com.creditharmony.loan.credit.entity.CreditLoanDetailedTwo;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.ex.CreditCardDetailedOneEx;
import com.creditharmony.loan.credit.entity.ex.CreditGuaranteeDetailedEx;
import com.creditharmony.loan.credit.entity.ex.CreditLoanDetailedOneEx;
import com.creditharmony.loan.credit.service.CreditCardDetailedOneService;
import com.creditharmony.loan.credit.service.CreditGuaranteeDetailedService;
import com.creditharmony.loan.credit.service.CreditLoanDetailedOneService;
import com.creditharmony.loan.credit.service.CreditLoanDetailedTwoService;

/**
 * 祥版信用报告
 * @Class Name CreditReportAddController
 * @author 侯志斌
 * @Create In 2016年1月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/credit")
public class CreditReportAddController extends BaseController{

	@Autowired
	private CreditLoanDetailedOneService creditLoanDetailedOneService;  
	
	@Autowired
	private CreditLoanDetailedTwoService creditLoanDetailedTwoService;  
	
	@Autowired
	private CreditCardDetailedOneService creditCardDetailedOneService;  
	
	@Autowired
	private CreditGuaranteeDetailedService creditGuaranteeDetailedService;

	/**
	 * 进入征信个人祥版-明细
	 * 2016年1月29日
	 * By 侯志斌
	 * @param model 页面数据类
	 * @return 
	 */
	@RequestMapping(value="creditReport")
	public ModelAndView creditReport(ModelAndView  model) throws Exception{
	   
		model.setViewName("credit/creditReport");
		return model;	
	}
	
	/**
	 * 保存贷款明细一
	 * 2016年02月02日
	 * By 侯志斌
	 * @param model 贷款实体
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="asyncSaveLoanOne")
	public String  asyncSaveLoanOne(CreditLoanDetailedOne model){
		return creditLoanDetailedOneService.save(model);
	}
	
	/**
	 * 绑定数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  creditReportDetailed 征信实体类
	 * @return CreditLoanDetailedOneEx 贷款一实体扩展类
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="showData")
	public CreditLoanDetailedOneEx showData(CreditReportDetailed creditReportDetailed) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		CreditLoanDetailedOneEx result = creditLoanDetailedOneService.showData(creditReportDetailed);
		return result;
	}
	
	/**
	 * 保存数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  creditLoanDetailedOneEx 贷款实体扩展类
	 * @param  relationType 期数表中的关系类型，贷款：1
	 * @param  oneLoanRelationId 贷款信息一的关联ID，传递过来的，默认为1待修改
	 * @return 保存结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveData")
	public CreditLoanDetailedTwo saveData(CreditLoanDetailedOneEx creditLoanDetailedOneEx,String relationType,String oneLoanRelationId) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		CreditLoanDetailedTwo result = new CreditLoanDetailedTwo();
		if(creditLoanDetailedOneEx != null && StringUtils.isNotEmpty(creditLoanDetailedOneEx.getLoanCode())
				&& StringUtils.isNotEmpty(creditLoanDetailedOneEx.getType())
				&& StringUtils.isNotEmpty(creditLoanDetailedOneEx.getRelId())){
			result = creditLoanDetailedOneService.saveData(creditLoanDetailedOneEx,relationType,oneLoanRelationId);
		}
		return result;
	}
	
	/**
	 * 删除数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  record 贷款实体类
	 * @return 操作结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="deleteData")
	public int deleteData(CreditLoanDetailedOne record){
		int result = creditLoanDetailedOneService.deleteData(record);
		return result;
	}
	
	/**
	 * 绑定信用卡数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  creditReportDetailed 关联ID
	 * @return CreditCardDetailedOneEx 信用卡一实体扩展类
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="showCardData")
	public CreditCardDetailedOneEx showCardData(CreditReportDetailed creditReportDetailed) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		
		CreditCardDetailedOneEx result = creditCardDetailedOneService.showData(creditReportDetailed);
		return result;
	}
	
	/**
	 * 保存信用卡数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  creditCardDetailedOneEx 信用卡一实体扩展类
	 * @return 操作结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveCardData")
	public CreditCardDetailedTwo saveCardData(CreditCardDetailedOneEx creditCardDetailedOneEx) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		CreditCardDetailedTwo result = new CreditCardDetailedTwo();
		if(creditCardDetailedOneEx != null && StringUtils.isNotEmpty(creditCardDetailedOneEx.getLoanCode())
				&& StringUtils.isNotEmpty(creditCardDetailedOneEx.getType())
				&& StringUtils.isNotEmpty(creditCardDetailedOneEx.getRelId())){
			result = creditCardDetailedOneService.saveData(creditCardDetailedOneEx);
		}
		return result;
	}
	
	/**
	 * 删除信用卡数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  record 信用卡实体类
	 * @return 操作结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="deleteCardData")
	public int deleteCardData(CreditCardDetailedOne record){
		int result = creditCardDetailedOneService.deleteData(record);
		return result;
	}
	
	/**
	 * 绑定担保数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param  creditReportDetailed 征信实体类
	 * @return CreditGuaranteeDetailedEx 担保实体扩展
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="showGuaranteeData")
	public CreditGuaranteeDetailedEx showGuaranteeData(CreditReportDetailed creditReportDetailed) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		CreditGuaranteeDetailedEx result = creditGuaranteeDetailedService.showData(creditReportDetailed);
		return result;
	}
	
	/**
	 * 保存担保数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param creditGuaranteeDetailedEx 担保实体扩展类
	 * @return 操作结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveGuaranteeData")
	public String saveGuaranteeData(CreditGuaranteeDetailedEx creditGuaranteeDetailedEx) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		if(creditGuaranteeDetailedEx != null && StringUtils.isNotEmpty(creditGuaranteeDetailedEx.getLoanCode())
				&& StringUtils.isNotEmpty(creditGuaranteeDetailedEx.getType())
				&& StringUtils.isNotEmpty(creditGuaranteeDetailedEx.getRelId())){
			boolean result = creditGuaranteeDetailedService.saveData(creditGuaranteeDetailedEx);
			if(result){
				return "true";
			}else{
				return "false";
			}
		}else{
			return "false";
		}
	}
	
	/**
	 * 删除担保数据
	 * 2016年2月1日
	 * By 侯志斌
	 * @param record 担保实体类
	 * @return 操作结果
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="deleteGuaranteeData")
	public int deleteGuaranteeData(CreditGuaranteeDetailed record){
		int result = creditGuaranteeDetailedService.deleteData(record);
		return result;
	}
	
}
