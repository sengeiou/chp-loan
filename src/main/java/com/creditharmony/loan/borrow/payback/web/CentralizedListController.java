package com.creditharmony.loan.borrow.payback.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.service.CentralizedListService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * @Class 集中划扣数据列表
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/centralized/deduct")
public class CentralizedListController extends BaseController  {
	@Autowired
	private CentralizedListService centralizedListService;
	@Autowired
	private RepaymentDateService dateService;
	
    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);

	/**
	 * 集中划扣已办列表
	 * 2016年7月14日
	 * By 王彬彬
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return
	 */
	@RequestMapping(value = "allCentralizedDeductionList")
	public String allCentralizedDeductionList(HttpServletRequest request,
			HttpServletResponse response, Model model, PaybackApply paybackApply) {
		
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("menuId",menuId);
		if (ObjectHelper.isEmpty(paybackApply)
				|| (StringUtils.isEmpty(paybackApply.getCustomerName())
						&& StringUtils.isEmpty(paybackApply.getContractCode())
						&& StringUtils.isEmpty(paybackApply.getApplyBankName())
						&& StringUtils.isEmpty(paybackApply.getBank())
						&& paybackApply.getMonthPayDay() == null
						&& StringUtils.isEmpty(paybackApply
								.getSplitBackResult())
						&& paybackApply.getBeginDate() == null
						&& paybackApply.getEndDate() == null
						&& StringUtils.isEmpty(paybackApply.getPaybackDay())
						&& StringUtils.isEmpty(paybackApply.getLoanMark()) && StringUtils
							.isEmpty(paybackApply.getModel()))) {
			return "borrow/payback/repayment/centralizedList";
		}
		
		User currentUser = (User) UserUtils.getSession().getAttribute(
				SystemConfigConstant.SESSION_USER_INFO);
		
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		String orgName = org != null ? org.getName() : "";
		
		boolean isManager = true;
		// 如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)) {
			isManager = false;
			// 如果是门店 则 默认门店框体选项
			paybackApply.setStores(orgId);
			paybackApply.setStoresName(orgName);
		} else if (LoanOrgType.TEAM.key.equals(orgType)) {
			isManager = false;
			// 如果是门店 则 默认门店框体选项
			String storeId = currentUser.getDepartment().getParentId();
			paybackApply.setStores(storeId);
			paybackApply.setLoanTeamManagerName(currentUser.getName());
		}
		
		model.addAttribute("isManager", isManager);

		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		page = centralizedListService.getCentralizedInfo(page, paybackApply);
		if(StringUtils.isNotEmpty(paybackApply.getBillDay()))
		{
			paybackApply.setBillDay(paybackApply.getBillDay().replaceAll("'", ""));
		}
		if(StringUtils.isNotEmpty(paybackApply.getPaybackDay()))
		{
			paybackApply.setPaybackDay(paybackApply.getPaybackDay().replaceAll("'", ""));
		}
		try {
			
			model.addAttribute("waitPage", page);
			model.addAttribute("PaybackApply", paybackApply);
			NumTotal numTotal = new NumTotal();
			List<PaybackApply> paybacklist = page.getList();
			if (paybacklist.size() > 0) {
				numTotal.setNum(String.valueOf(page.getCount()));
				numTotal.setTotal(paybacklist.get(0).getSumAmont() == null ? "0"
						: paybacklist.get(0).getSumAmont());
			} else {
				numTotal.setNum("0");
				numTotal.setTotal("0");
			}
			model.addAttribute("numTotal", numTotal);
			// 查询还款日
			List<GlBill> dayList = dateService.getRepaymentDate();
			model.addAttribute("dayList", dayList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("集中划扣已办列表获取失败\r\n" + e.getMessage());
			model.addAttribute("message", "集中划扣已办列表获取失败");
		}
		return "borrow/payback/repayment/centralizedList";
	}

	/**
	 * 导出集中划扣数据列表
	 * 2015年12月25日
	 * By 导出excel
	 * @param request
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,PaybackApply paybackApply){
		String idVal = paybackApply.getId();
		//数据权限导出
		User currentUser = (User) UserUtils.getSession().getAttribute(
				SystemConfigConstant.SESSION_USER_INFO);
		
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		String orgName = org != null ? org.getName() : "";
		
		// 如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)) {
			// 如果是门店 则 默认门店框体选项
			paybackApply.setStores(orgId);
			paybackApply.setStoresName(orgName);
		} else if (LoanOrgType.TEAM.key.equals(orgType)) {
			// 如果是门店 则 默认门店框体选项
			String storeId = currentUser.getDepartment().getParentId();
			paybackApply.setStores(storeId);
			paybackApply.setLoanTeamManagerName(currentUser.getName());
		}
		
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			paybackApply.setId(FilterHelper.appendIdFilter(idVal));
		}
		String bank = paybackApply.getBank();
		if (!ObjectHelper.isEmpty(bank)) {
			paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
		}
		String stores = paybackApply.getStores();
		if (!ObjectHelper.isEmpty(stores)) {
			paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
		}
		String dayName = paybackApply.getPaybackDay();
		if (!ObjectHelper.isEmpty(dayName)) {
			paybackApply.setPaybackDay(FilterHelper.appendIdFilter(dayName));
		}
		String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		paybackApply.setQueryRight(queryRight);
		ExportCenterListDeductHelper.exportCenterTodo(paybackApply, response);
		//导出成功
		
		if(StringUtils.isNotEmpty(paybackApply.getBillDay()))
		{
			paybackApply.setBillDay(paybackApply.getBillDay().replaceAll("'", ""));
		}
		if(StringUtils.isNotEmpty(paybackApply.getPaybackDay()))
		{
			paybackApply.setPaybackDay(paybackApply.getPaybackDay().replaceAll("'", ""));
		}
	}
	
	/**
	 * 电销集中划扣已办列表
	 * 2016年7月14日
	 * By 王彬彬
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return
	 */
	@RequestMapping(value = "collectDeductionPhoneSaleList")
	public String collectDeductionPhoneSaleList(HttpServletRequest request,
			HttpServletResponse response, Model model, PaybackApply paybackApply) {
		
		// 1代表是电销的数据
		paybackApply.setPhoneSaleSign("1");
		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		page = centralizedListService.getCentralizedInfo(page, paybackApply);
		if(StringUtils.isNotEmpty(paybackApply.getBillDay()))
		{
			paybackApply.setBillDay(paybackApply.getBillDay().replaceAll("'", ""));
		}
		if(StringUtils.isNotEmpty(paybackApply.getPaybackDay()))
		{
			paybackApply.setPaybackDay(paybackApply.getPaybackDay().replaceAll("'", ""));
		}
		try {
			
			model.addAttribute("waitPage", page);
			model.addAttribute("PaybackApply", paybackApply);
			NumTotal numTotal = new NumTotal();
			List<PaybackApply> paybacklist = page.getList();
			if (paybacklist.size() > 0) {
				numTotal.setNum(String.valueOf(page.getCount()));
				numTotal.setTotal(paybacklist.get(0).getSumAmont() == null ? "0"
						: paybacklist.get(0).getSumAmont());
			} else {
				numTotal.setNum("0");
				numTotal.setTotal("0");
			}
			model.addAttribute("numTotal", numTotal);
			// 查询还款日
			List<GlBill> dayList = dateService.getRepaymentDate();
			model.addAttribute("dayList", dayList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("集中划扣已办列表获取失败\r\n" + e.getMessage());
			model.addAttribute("message", "集中划扣已办列表获取失败");
		}
		return "borrow/payback/repayment/centralizedPhoneSaleList";
	}
	
	/**
	 * 导出集中划扣数据列表
	 * 2015年12月25日
	 * By 导出excel
	 * @param request
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportPhoneSaleExcel")
	public void exportPhoneSaleExcel(HttpServletRequest request,HttpServletResponse response,PaybackApply paybackApply){
		
		// 1代表是电销的数据
		paybackApply.setPhoneSaleSign("1");

		String idVal = paybackApply.getId();
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			paybackApply.setId(FilterHelper.appendIdFilter(idVal));
		}
		String bank = paybackApply.getBank();
		if (!ObjectHelper.isEmpty(bank)) {
			paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
		}
		String stores = paybackApply.getStores();
		if (!ObjectHelper.isEmpty(stores)) {
			paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
		}
		String dayName = paybackApply.getPaybackDay();
		if (!ObjectHelper.isEmpty(dayName)) {
			paybackApply.setPaybackDay(FilterHelper.appendIdFilter(dayName));
		}
		
		ExportCenterListDeductHelper.exportCenterTodo(paybackApply, response);
		//导出成功
		
		if(StringUtils.isNotEmpty(paybackApply.getBillDay()))
		{
			paybackApply.setBillDay(paybackApply.getBillDay().replaceAll("'", ""));
		}
		if(StringUtils.isNotEmpty(paybackApply.getPaybackDay()))
		{
			paybackApply.setPaybackDay(paybackApply.getPaybackDay().replaceAll("'", ""));
		}
	}
}
