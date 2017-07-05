package com.creditharmony.loan.car.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.LoanPrdEntity;
import com.creditharmony.loan.common.service.LoanPrdService;

/**
 * 获取产品相关的期限  费率等信息
 * @Class Name ProdcutInfoController
 * @author 李静辉
 * @Create In 2016年2月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/productInfo")
public class ProdcutInfoController extends BaseController {
	
	@Autowired
	private LoanPrdService loanPrdService; 

	/**
	 * 根据产品编码和产品类型  信借   车借   等获取  产品期限（期数）
	 * 2016年2月27日
	 * By 李静辉
	 * @param productCode   产品code  
	 * @param productType  产品类型 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "asynLoadProductMonths", method = RequestMethod.POST)
	public String asynLoadProductMonths(String productCode,String productType) {
		LoanPrdEntity l = LoanPrdService.getPrdByTypeAndCode(productType,productCode);
		return l.getProductMonths();
	}


}
