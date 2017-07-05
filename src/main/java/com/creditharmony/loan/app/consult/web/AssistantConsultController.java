package com.creditharmony.loan.app.consult.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.app.consult.entity.AppConsult;
import com.creditharmony.loan.app.consult.service.AssistantConsultService;
import com.creditharmony.loan.app.consult.type.ConsultStatus;
import com.creditharmony.loan.app.consult.type.ConsultType;
import com.creditharmony.loan.app.consult.view.AssistantConsultView;

/**
 * 汇金助手客户咨询控制器
 * @Class Name AssistantConsultController
 * @author 张永生
 * @Create In 2016年6月13日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/consultOcr")
public class AssistantConsultController extends BaseController{

	@Autowired
	private AssistantConsultService consultService;

	/**
	 * 获取门店ocr客户咨询列表页面
	 * @param request
	 * @param response
	 * @param customerInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response,
			AssistantConsultView assistantConsultView, Model model) {
		// 设置区分app和ocr标示字段
		assistantConsultView.setTeamEmpcode(UserUtils.getUser().getId());
		assistantConsultView.setApplyType(ConsultType.OCR);
		assistantConsultView.setLoanStatus(ConsultStatus.INIT);
		Page<AppConsult> dataList = consultService.findPage(new Page<AppConsult>(request, response), assistantConsultView);
	    if((!dataList.getList().isEmpty()) && dataList.getList().size()>0){
		 for (int i = 0; i < dataList.getList().size(); i++) {
			 if(!ObjectHelper.isEmpty(dataList.getList().get(i).getFinancingId())){
				 dataList.getList().get(i).setFinancingId(UserUtils.get(dataList.getList().get(i).getFinancingId()).getName());
			 }
			 if(!ObjectHelper.isEmpty(dataList.getList().get(i).getTeamEmpcode())){
				 dataList.getList().get(i).setTeamEmpcode(UserUtils.get(dataList.getList().get(i).getTeamEmpcode()).getName());
			 }
		  }
	    }else{
	    	logger.info("汇金OCR客户咨询列表为:"+null);
	    }
        model.addAttribute("customerInfo", assistantConsultView);
		model.addAttribute("waitPage", dataList);
		return "apply/consult/ocr/consultOcrList";
	}
	
	/**
	 * 查询ocr客户咨询详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getCustomerInfoById")
	public String getCustomerInfoById(@RequestParam(value="id",required=false) String id,Model model){
		AppConsult appConsult = consultService.getConsult(id);
		AssistantConsultView consultView = new AssistantConsultView();
		BeanUtils.copyProperties(appConsult, consultView);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		// 借款用途
		if(StringUtils.isNotEmpty(appConsult.getLoanUse())){
			consultView.setLoanUseName(DictUtils.getLabel(dictMap,"jk_loan_use", appConsult.getLoanUse()));
		}
		// 行业类型
		if(StringUtils.isNotEmpty(appConsult.getIndustry())){
			consultView.setIndustryName(DictUtils.getLabel(dictMap, "jk_industry_type", appConsult.getIndustry()));
		}
		// 客户经理姓名
		if (StringUtils.isNotEmpty(appConsult.getFinancingId())) {
			consultView.setFinancingName(UserUtils.get(
					appConsult.getFinancingId()).getName());
		}
		// 团队经理姓名
		if (StringUtils.isNotEmpty(appConsult.getTeamEmpcode())) {
			consultView.setTeamName(UserUtils.get(appConsult.getTeamEmpcode()).getName());
		}
		if (StringUtils.isNotEmpty(appConsult.getIdStartDate())){
			String idStartDate = appConsult.getIdStartDate();
			if(!idStartDate.contains(".")){
				idStartDate = DateUtils.formatDate(DateUtils.convertDate(idStartDate), "yyyy-MM-dd");
			} else {
				idStartDate = idStartDate.replace(".", "-");
			}
			consultView.setIdStartDate(idStartDate);
		}
		if (StringUtils.isNotEmpty(appConsult.getIdEndDate())){
			String idEndDate = appConsult.getIdEndDate();
			if(!idEndDate.contains(".")){
				idEndDate = DateUtils.formatDate(DateUtils.convertDate(idEndDate), "yyyy-MM-dd");
			} else {
				idEndDate = idEndDate.replace(".", "-");
			}
			consultView.setIdEndDate(idEndDate);
		}
		consultView.setLoanStatus(NextStep.CONTINUE_CONFIRM.getCode());
		model.addAttribute("appConsult", consultView);
		return "apply/consult/ocr/consultOcrListDetails";
	}

	/**
	 * 保存ocr客户咨询数据
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value="/saveConsult")
	public String saveConsult(AssistantConsultView	consultView,RedirectAttributes redirectAttributes){
		// 1.更新t_jk_app_consultation表中下一步状态字段,是否是历史数据
		// 2.更新t_jk_customer_base客户基本信息表,如果表中没有该客户要插入
		// 3.插入t_jk_consultation_log
		// 4.插入数据到客户咨询表中
		try {
			consultService.saveConsult(consultView);
			addMessage(redirectAttributes, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存处理发生异常",e);
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath + "/apply/consultOcr/list";
	}
	
}
