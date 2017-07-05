package com.creditharmony.loan.app.consult.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.app.consult.entity.AppConsult;
import com.creditharmony.loan.app.consult.service.AppConsultService;
import com.creditharmony.loan.app.consult.view.AppConsultView;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * App客户咨询控制器
 * @Class Name AppConsultController
 * @author 朱静越
 * @Create In 2016年6月10日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/consultapp")
public class AppConsultController extends BaseController {

	@Autowired
	private AppConsultService appConsultService;
	
	@Autowired
    private NumberMasterService numberMasterService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;
	
	@Autowired
	private UserManager userManager;
	
	/**
	 * 获得app页面列表,分页
	 * 2016年6月10日
	 * By 朱静越
	 * @param consultView 查询条件
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getConsultList" )
	public String  getConsultList(AppConsultView consultView, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// 获得当前用户登录名称，即团队经理的id,进行权限控制
		String curTeamEmpCode = UserUtils.getUser().getId();
		consultView.setTeamEmpcode(curTeamEmpCode);
		Page<AppConsult> appCustomerInfoPage = appConsultService
				.findList(new Page<AppConsult>(request, response),
						consultView);
		for (AppConsult appConsult : appCustomerInfoPage.getList()) {
			User userFinancing =  userManager.get(appConsult.getFinancingId());
			if (!ObjectHelper.isEmpty(userFinancing)) {
				appConsult.setFinancingId(userFinancing.getName());
			}
			User teamUser = userManager.get(appConsult.getTeamEmpcode());
			if (!ObjectHelper.isEmpty(teamUser)) {
				appConsult.setTeamEmpcode(teamUser.getName());
			}
		}
		model.addAttribute("appCustomerInfoPage", appCustomerInfoPage);
		model.addAttribute("appCustomerInfo", consultView);
		return "apply/consultapp/appCustomerConsultList";
	}
	
	/**
	 * 根据id获得咨询的详细页面
	 * 2016年6月10日
	 * By 朱静越
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getConsultForm")
	public String  getConsultForm(String id, Model model) {
		AppConsult appConsult = appConsultService.getConsultForm(id);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		// 借款用途
		appConsult.setLoanUseName(DictUtils.getLabel(dictMap,"jk_loan_use", appConsult.getLoanUse()));
		// 行业类型
		appConsult.setIndustryName(DictUtils.getLabel(dictMap, "jk_industry_type", appConsult.getIndustry()));
		
		AppConsultView consultView = new AppConsultView();
		BeanUtils.copyProperties(appConsult, consultView);
		// 人员查询
		User userFinancing =  userManager.get(consultView.getFinancingId());
		if (!ObjectHelper.isEmpty(userFinancing)) {
			consultView.setFinancingName(userFinancing.getName());
		}
		User teamUser = userManager.get(consultView.getTeamEmpcode());
		if (!ObjectHelper.isEmpty(teamUser)) {
			consultView.setTeamEmpName(teamUser.getName());
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
		model.addAttribute("appCustomerInfo", consultView);
		return "apply/consultapp/appConsultForm";
	}
	
	/**
	 * app保存；1.根据身份证号进行判断，base表中是否存在，如果存在，进行update，不存在，进行insert；
	 * 2.对咨询表进行insert;
	 * 3.对咨询log表进行insert;
	 * 4.更新app咨询表
	 * 2016年6月11日
	 * By 朱静越
	 * @param consultView
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "saveConsult")
	public String saveConsult(AppConsultView consultView, RedirectAttributes redirectAttributes){
		Boolean insertFlag = false;
		// 获得身份证号，进行判断
		String mateCertNum = consultView.getCertNum();
		String customerCode = appConsultService.findCustomerByMcNum(mateCertNum);
		if (StringUtils.isNotEmpty(customerCode)) {
			// 设置客户编码，进行更新
			consultView.setCustomerId(customerCode);
		} else {
			// 生成客户编码，进行插入
			String customerId = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER); 
			consultView.setCustomerId(customerId);
			insertFlag = true;
		}
		try {
			appConsultService.saveConsult(insertFlag, consultView);
			addMessage(redirectAttributes, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存APP客户咨询处理发生异常",e);
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath
				+ "/apply/consultapp/getConsultList";
	}

}
