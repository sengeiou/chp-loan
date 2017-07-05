package com.creditharmony.loan.borrow.payback.web;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.account.service.RepayAccountService;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;
import com.creditharmony.loan.borrow.payback.service.RemindRepaymentAgencyService;

/**
 * 控制器支持类  提醒还款待办
 * @Class Name RemindRepaymentAgencyController
 * @author 李强
 * @Create In 2015年11月30日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/payback/remindAgency")
public class RemindRepaymentAgencyController extends BaseController {

	@Autowired
	private RemindRepaymentAgencyService repaymentAgencyService;
	@Autowired
	RepayAccountService repayAccountService;
	@Autowired
    private UserManager userManager;
	/**
	 * 提醒还款代办列表
	 * In 2015年11月30日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param repaymentReminder
	 * @return 提醒还款待办列表页面
	 */
	@RequestMapping(value = "remindRepaymentAgencyList")
	public String remindRepaymentAgencyList(HttpServletRequest request,HttpServletResponse response,Model model,RepaymentReminderEx repaymentReminder) {
	
		//	//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
	    repaymentReminder.setQueryRight(queryRight);
		
		Page<RepaymentReminderEx> waitPage = repaymentAgencyService.allRemindRepaymentAgencyList(new Page<RepaymentReminderEx>(request, response),repaymentReminder);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		
		for(RepaymentReminderEx re:waitPage.getList()){
			String applyBankName=DictCache.getInstance().getDictLabel("jk_open_bank",re.getApplyBankName());
			re.setApplyBankNameLabel(applyBankName);
			
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",re.getDictLoanStatus());
			re.setDictLoanStatusLabel(dictLoanStatus);
			
			String dictMonthStatus=DictCache.getInstance().getDictLabel("jk_period_status",re.getDictMonthStatus());
			re.setDictMonthStatusLabel(dictMonthStatus);
			
			String logo=DictCache.getInstance().getDictLabel("jk_channel_flag",re.getLogo());
			re.setLogoLabel(logo);
			
			String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",re.getDictDealType());
			re.setDictDealTypeLabel(dictDealType);
			User loanManagerName = userManager.get(re.getLoanManagerName());
			User loanTeamManagerName = userManager.get(re.getLoanTeamManagerName());
			User loanSurveyEmpName = userManager.get(re.getLoanSurveyEmpName());
			User loanCustomerService = userManager.get(re.getLoanCustomerService());
			if(loanManagerName != null){
				re.setLoanManagerName(loanManagerName.getName());
			}
			if(loanTeamManagerName != null){
				re.setLoanTeamManagerName(loanTeamManagerName.getName());
			}
			if(loanSurveyEmpName != null){
				re.setLoanSurveyEmpName(loanTeamManagerName.getName());
			}
			if(loanCustomerService != null){
				re.setLoanCustomerService(loanCustomerService.getName());
			}
		}
		model.addAttribute("billDayList", billDay);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("RepaymentReminderEx", repaymentReminder);
		logger.debug("invoke RemindRepaymentAgencyController method: remindRepaymentAgencyList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/remindRepaymentAgency";
	}
	
	/**
	 * 还款提醒待办列表(门店用)
	 * 2015年12月30日
	 * By lirui
	 * @param request request对象
	 * @param response response对象
	 * @param model model模型
	 * @param repaymentReminder 搜索参数
	 * @return 还款提醒待办列表(门店)页面 
	 */
	@RequestMapping(value = "reremindRepayByStore")
	public String remindRepayByStore(HttpServletRequest request,HttpServletResponse response,Model model,RepaymentReminderEx repaymentReminder) {	
		model.addAttribute("repay", repaymentReminder);			
		Page<RepaymentReminderEx> waitPage = repaymentAgencyService.allRemindRepaymentAgencyList(new Page<RepaymentReminderEx>(request, response),repaymentReminder);
		List<RepaymentReminderEx> list = waitPage.getList();
		for (RepaymentReminderEx repaymentReminderEx : list) {
			repaymentReminderEx.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_status", repaymentReminderEx.getDictLoanStatus()));
			repaymentReminderEx.setDictMonthStatusLabel(DictCache.getInstance().getDictLabel("jk_period_status", repaymentReminderEx.getDictMonthStatus()));
			repaymentReminderEx.setLogoLabel(DictCache.getInstance().getDictLabel("jk_channel_flag", repaymentReminderEx.getLogo()));
			repaymentReminderEx.setDictDealTypeLabel(DictCache.getInstance().getDictLabel("jk_deduct_plat", repaymentReminderEx.getDictDealType()));
		}
		model.addAttribute("waitPage", waitPage);
		return "borrow/payback/repayment/remindRepayByStore";
	}
	
	/**
	 * 添加门店备注
	 * 2015年12月30日
	 * By lirui
	 * @param rre 备注信息存放容器
	 * @param remarkId 要添加备注的记录ID
	 * @return 还款提醒待办列表
	 */
	@RequestMapping(value = "addRemark",method = RequestMethod.POST)
	public String addReamrk(RepaymentReminderEx rre,String remarkId) {
		rre.setId(remarkId);
		repaymentAgencyService.addRemark(rre);
		return "redirect:" + adminPath + "/borrow/payback/remindAgency/reremindRepayByStore";
	}
}
