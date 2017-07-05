/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.LoanPrdController.java
 * @Create By 张进
 * @Create In 2016年2月4日 上午11:11:02
 */
package com.creditharmony.loan.common.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.LoanPrdEntity;
import com.creditharmony.loan.common.service.LoanPrdService;

@Controller
@RequestMapping(value="${adminPath}/common/pro")
/**
 * 查询产品功能控制层
 * @author 张进
 *
 */
public class LoanPrdController extends BaseController{
	@Autowired
	private LoanPrdService loanPrdService;
	 
	/**
	 * 根据 productCode 查询产品对应的期数
	 * @param productCode
	 * @return 产品对应的期数
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "getProproductMonthsByProductCode")
	public String getProproductMonthsByProductCode(String productCode,String productType){
		List<String> listMonths = new ArrayList<String>();
		if(productCode != null  && !"".equals(productCode.trim())){
			LoanPrdEntity loanPrdEntity = loanPrdService.getPrdByTypeAndCode(productType, productCode);
			if(loanPrdEntity != null){
				listMonths = Arrays.asList(loanPrdEntity.getProductMonths().split(","));
			}
		}
		return jsonMapper.toJson(listMonths);
	}
}