package com.creditharmony.loan.borrow.payback.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 还款申请列表处理Controller
 * 
 * @Class Name ApplyController
 * @author yufei
 * @Create In 2017年3月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/applyList")
public class ApplyListController extends BaseController {

	@Autowired
	private PaybackService paybackService;

	
	/**
	 * 还款申请明细列表
	 * @author 于飞
	 * @Create 2017年3月1日
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "applyList")
	public String applyList(Model model, HttpServletRequest request, 
			HttpServletResponse response, PaybackApply paybackApply) {
		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		//查询条件有值时进行查询
		if((paybackApply.getOrgCode()!=null && !paybackApply.getOrgCode().equals(""))
				|| (paybackApply.getContractCode()!=null && !paybackApply.getContractCode().equals(""))
				|| (paybackApply.getCustomerName()!=null && !paybackApply.getCustomerName().equals(""))
				|| (paybackApply.getCreateBy()!=null && !paybackApply.getCreateBy().equals(""))
				|| (paybackApply.getBank()!=null && !paybackApply.getBank().equals(""))
				|| (paybackApply.getEnumOne()!=null && !paybackApply.getEnumOne().equals(""))
				|| (paybackApply.getEnumTwo()!=null && !paybackApply.getEnumTwo().equals(""))
				){
			if(paybackApply.getOrgCode()!=null && !paybackApply.getOrgCode().equals("")){
				paybackApply.setOrgCode(appendString(paybackApply.getOrgCode()));
			}
			
			page = paybackService.findApplyPaybackList(page,paybackApply);
			
			if(paybackApply.getOrgCode()!=null && !paybackApply.getOrgCode().equals("")){
				paybackApply.setOrgCode(paybackApply.getOrgCode().replace("'", ""));
			}
			
		}
		model.addAttribute("paybackApply", paybackApply);
		model.addAttribute("page", page);
		return "borrow/payback/applypay/applyList";
	}
	
	/**
	 * 导出数据
	 * @author 于飞
	 * @Create 2017年3月2日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportExl")
	public void exportExl(HttpServletRequest request, HttpServletResponse response
			, String ids,PaybackApply paybackApply){
		try{
		if (StringUtils.isNotEmpty(ids)) {
			paybackApply.setId(appendString(ids));
		}
		if(paybackApply.getOrgCode()!=null && !"".equals(paybackApply.getOrgCode())){
			paybackApply.setOrgCode(appendString(paybackApply.getOrgCode()));
		}
		ExportPaybackApplyHelper.exportData(paybackApply, response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("--文件导出错误"+e.getMessage());
		}
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
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
			if (i == 0){
				parameter.append("'" +id +"'");
			}else {
				parameter.append(",'" +id + "'");
			}
		}
		return parameter.toString();
	}
	
}
