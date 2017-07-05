package com.creditharmony.loan.borrow.consult.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.DeleteFlagType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.OrgManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.consult.constats.AllotResultCode;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.groups.DoAllotGroup;
import com.creditharmony.loan.borrow.consult.groups.UpdateStatusGroup;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.borrow.consult.view.AllotResultVO;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerDetailView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerView;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 客户管理controller
 * 
 * @Class Name CustomerManagementController
 * @author 张平
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/consult/customerManagement")
public class CustomerManagementController extends BaseController {

    @Autowired
    private CustomerManagementService customerManagementService;
    @Autowired
    private ConsultService consultService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private OrgManager orgManager;
    @Autowired
    private MenuManager menuManager;

	/**
	 * 客户咨询管理列表（客户列表）
	 * By 任志远 2017年4月27日
	 *
	 * @param consultSearchView
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "findConsultList", "" })
	public String findConsultList(ConsultSearchView consultSearchView, HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		if (StringHelper.isNotEmpty(consultSearchView.getCustomerName())) {
			consultSearchView.setCustomerName(StringEscapeUtils.unescapeHtml4(consultSearchView.getCustomerName()));
		}
		List<Role> roleList = user.getRoleList();
		boolean isManager = false;
		if (!ObjectHelper.isEmpty(roleList)) {
			for (Role role : roleList) {
				if (LoanRole.TEAM_MANAGER.id.equals(role.getId())) {
					isManager = true;
					break;
				}
			}
		}
		// 数据权限控制
		String queryRight = DataScopeUtil.getLoanConsultDataScope("T2");
		consultSearchView.setQueryRight(queryRight);
		consultSearchView.setConsTelesalesFlag(YESNO.NO.getCode());
		Page<ConsultSearchView> page = customerManagementService.findCustomerConsultionPage(new Page<ConsultSearchView>(request, response), consultSearchView);
		
		String menuId = (String) request.getParameter("menuId"); 
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId); 
		for (String resKey:resKeyList) { 
			request.setAttribute(resKey, 1); 
		}
		
		request.setAttribute("isManager", isManager);
		request.setAttribute("page", page);
		request.setAttribute("today", new Date());
		request.setAttribute("consultSearchView", consultSearchView);
		request.setAttribute("menuId", menuId);
		return "apply/consult/CustomerManagementList";
	}

	/**
	 * 分配门店放弃并且客户经理离职的咨询客户
	 * By 任志远 2017年5月7日
	 *
	 * @param id	咨询ID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="findConsultList/allot", method=RequestMethod.GET)
	public String allot(String id, HttpServletRequest request, HttpServletResponse response){
		Consult consult = consultService.get(id);
		Org storeOrg = orgManager.get(consult.getStoreOrgid());
		List<User> teamManagerList = userManager.findUserByRoleAndParentOrg(LoanRole.TEAM_MANAGER.id, storeOrg.getId());
		User currentTeamManager = userManager.get(consult.getLoanTeamEmpcode());
		if(currentTeamManager != null && currentTeamManager.getId() != null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", LoanRole.FINANCING_MANAGER.id);
			params.put("departmentId", consult.getLoanTeamOrgId());
			params.put("deleteFlag", YESNO.NO.getCode());
			params.put("status", YESNO.YES.getCode());
			List<User> customerManagerList = userManager.findByParams(params);
			request.setAttribute("customerManagerList", customerManagerList);
		}
		request.setAttribute("consult", consult);
		request.setAttribute("storeOrg", storeOrg);
		request.setAttribute("teamManagerList", teamManagerList);
		request.setAttribute("currentTeamManager", currentTeamManager);
		return "apply/consult/consultAllot";
	}
	
	@RequestMapping(value="findConsultList/allot", method=RequestMethod.POST)
	@ResponseBody
	public AllotResultVO doAllot(Consult consult, HttpServletRequest request, HttpServletResponse response){

		ConsultRecord consultRecord = new ConsultRecord();
		consultRecord.setConsLoanRecord("分配团队经理和客户经理");
		consultRecord.setConsCommunicateDate(new Date());
		consult.setConsultRecord(consultRecord);
		try{
			Set<ConstraintViolation<Consult>> validateResult = validator.validate(consult, DoAllotGroup.class);
			if (!validateResult.isEmpty()) {
				return new AllotResultVO(AllotResultCode.PARAM_ERROR.getCode(), validateResult.iterator().next().getMessage());
			}else{
				consultService.saveConsult(consult);
				consultService.saveConsultRecord(consult);
				return new AllotResultVO(AllotResultCode.SUCCESS.getCode(), AllotResultCode.SUCCESS.getDesc());	
			}
		}catch(Exception e){
			logger.error("param={id="+consult.getId()+"loanTeamEmpcode="+consult.getLoanTeamEmpcode()+"managerCode="+consult.getManagerCode()+"}", e);
			return new AllotResultVO(AllotResultCode.EXCEPTION.getCode(), AllotResultCode.EXCEPTION.getDesc());
		}
	}
	
	
    /**
     * 初始化页面 
     * 2015年12月3日 By 张平
     * @param consultSearchView
     * @param model
     * @return 重定向findConsultList
     */
    @RequestMapping(value = { "openConsultSearchForm" })
    public String openConsultSearchForm(ConsultSearchView consultSearchView,
            Model model, HttpServletRequest request) {
        model.addAttribute("consultSearchView", consultSearchView);
        System.out.println(request.getParameter("menuId"));
        return "redirect:" + adminPath
                + "/consult/customerManagement/findConsultList?menuId="+request.getParameter("menuId")+"&repage";
    }

    /**
     * 加载单条客户管理记录 
     * 2015年12月3日 By 张平
     * 2016年1月25日
     * By 李强
     * @param model
     * @param consultId
     * @param customerCode
     * @param customerName
     * @param mateCertNum
     * @param customerMobilePhone
     * @return CustomerManagementForm.jsp
     */
    @RequestMapping(value = { "findConsult" })
    public String findConsult(Model model,String consultId,String customerCode,String customerName,String mateCertNum,
            String customerMobilePhone) {
        model.addAttribute("consultId",consultId);
        model.addAttribute("customerName",StringEscapeUtils.unescapeHtml4(customerName));
        model.addAttribute("mateCertNum",mateCertNum);
        model.addAttribute("customerCode", customerCode);
        model.addAttribute("customerMobilePhone",customerMobilePhone);
        return "apply/consult/CustomerManagementForm";
    }

    /**
     * 新增咨询数据 
     * 2015年12月3日 By 张平
     * @param consultSearchView
     * @param model
     * @param redirectAttributes
     * @return 重定向findConsultList方法
     */
    @RequestMapping(value = "saveConsult")
    public String saveConsult(ConsultSearchView consultSearchView, Model model,
            RedirectAttributes redirectAttributes) {
        consultService.saveConsultRecord(consultService
                .dataConversion(consultSearchView));
        return "redirect:" + adminPath
                + "/consult/customerManagement/findConsultList?repage";
    }
    
    @RequestMapping(value="updateConsult")
    @ResponseBody
    public String updateConsult(Consult consult, Model model){
        consultService.updateConsult(consult);
        return BooleanType.TRUE;
    }

    /**
     * 电信历史沟通记录页面
     * @param consultSearchView
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "TMfindHistory" })
    public String TMfindHistory(ConsultSearchView consultSearchView, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        Page<ConsultSearchView> page = customerManagementService.findHisPage(
                new Page<ConsultSearchView>(request, response),
                consultSearchView);
        model.addAttribute("page", page);
        return "telesales/consult/telesaleManagementHisList";
    }

    /**
     * 客户咨询历史列表 
     * 2015年12月3日 By 张平
     * @param consultSearchView
     * @param model
     * @param request
     * @param response
     * @return CustomerManagementHisList.jsp
     */
    @RequestMapping(value = { "findHistory" })
    public String findHistory(ConsultSearchView consultSearchView, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        Page<ConsultSearchView> page = customerManagementService.findHisPage(
                new Page<ConsultSearchView>(request, response),
                consultSearchView);
        model.addAttribute("page", page);
        return "apply/consult/CustomerManagementHisList";
    }

    
    
    /**
     * 修改客户咨询状态 
     * 2015年12月3日 By 张平
     * @param consultSearchView
     * @param model
     * @return 重定向findConsultList
     */
    @RequestMapping(value = { "updateStatus", "" })
    public String updateStatus(ConsultSearchView consultSearchView, Model model) {
        customerManagementService.updateStatus(consultSearchView.getId());
        return "redirect:" + adminPath
                + "/consult/customerManagement/findConsultList?repage";
    }
    
    /**
     * 邀请客户列表
     * By 任志远 2017年5月7日
     *
     * @param inviteCustomerView 查询条件
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = { "inviteCustomerList" })
	public String inviteCustomerList(InviteCustomerView inviteCustomerView, HttpServletRequest request, HttpServletResponse response) {
		if (StringHelper.isNotEmpty(inviteCustomerView.getCustomerName())) {
			inviteCustomerView.setCustomerName(StringEscapeUtils.unescapeHtml4(inviteCustomerView.getCustomerName()));
		}
		// 数据权限控制
		String queryRight = DataScopeUtil.getLoanConsultDataScope("T2");
		inviteCustomerView.setQueryRight(queryRight);
		Page<InviteCustomerView> page = customerManagementService.findInviteCustomerPage(new Page<InviteCustomerView>(request, response), inviteCustomerView);
		List<Org> businessOrgList = orgManager.findAllGroup(LoanOrgType.BUISNESS_DEPT.key, DeleteFlagType.NORMAL);
		request.setAttribute("inviteCustomerView", inviteCustomerView);
		request.setAttribute("page", page);
		request.setAttribute("businessOrgList", businessOrgList);
		return "apply/consult/inviteCustomerList";
	}
	
	@RequestMapping("inviteCustomerList/export")
	public void exportInviteCustomerList(InviteCustomerView inviteCustomerView, HttpServletRequest request, HttpServletResponse response){
		
		if (StringHelper.isNotEmpty(inviteCustomerView.getCustomerName())) {
			inviteCustomerView.setCustomerName(StringEscapeUtils.unescapeHtml4(inviteCustomerView.getCustomerName()));
		}
		// 数据权限控制
		String queryRight = DataScopeUtil.getLoanConsultDataScope("T2");
		inviteCustomerView.setQueryRight(queryRight);
		List<InviteCustomerView> inviteCustomerList = customerManagementService.findInviteCustomerView(inviteCustomerView);
		
		ExcelUtils excelutil = new ExcelUtils();
		excelutil.exportExcel(inviteCustomerList, FileExtension.INVITE_CUSTOMER_LIST + System.currentTimeMillis(), InviteCustomerView.class, FileExtension.XLSX, FileExtension.OUT_TYPE_DATA, response, 1);
	}
	
	/**
	 * 邀请客户列表详细（备注）
	 * By 任志远 2017年5月8日
	 *
	 * @param customerId	客户ID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "inviteCustomerList/remark" }, method = RequestMethod.GET)
	public String remark(String customerId, HttpServletRequest request, HttpServletResponse response) {
		List<InviteCustomerDetailView> inviteCustomerDetailList = customerManagementService.findInviteCustomerDetailList(customerId);
		request.setAttribute("inviteCustomerDetailList", inviteCustomerDetailList);
		return "apply/consult/inviteCustomerDetailList";
	}
	
	/**
	 * 邀请客户列表人工分配
	 * By 任志远 2017年5月16日
	 *
	 * @param provinceId	省分ID
	 * @param cityId		城市ID
	 * @param id			邀请客户表ID
	 * @param request	
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "inviteCustomerList/allot", method = RequestMethod.GET)
	public String allotInviteCustomer(String provinceId, String cityId, String id, HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(provinceId) && StringUtils.isNotEmpty(cityId)){
			List<Org> defaultAreaOrg = orgManager.getOrgByTypeAndProvinceAndCityAndDistrict(provinceId, cityId, null, LoanOrgType.DISTRICT);
			if (defaultAreaOrg != null && !defaultAreaOrg.isEmpty()) {
				List<Org> storeOrgList = orgManager.getOrgByParentIds(defaultAreaOrg.get(0).getId());
				request.setAttribute("storeOrgList", storeOrgList);
				request.setAttribute("defaultAreaOrg", defaultAreaOrg.get(0));
			}
		}
		List<Org> areaOrgList = orgManager.findAllGroup(LoanOrgType.DISTRICT.key, DeleteFlagType.NORMAL);
		request.setAttribute("areaOrgList", areaOrgList);
		request.setAttribute("id", id);
		return "apply/consult/inviteCustomerAllot";
	}
	
	/**
	 * 邀请客户列表人工分配保存
	 * By 任志远 2017年5月16日
	 *
	 * @param inviteCustomerView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "inviteCustomerList/allot", method = RequestMethod.POST)
	@ResponseBody
	public AllotResultVO doAllotInviteCustomer(InviteCustomerView inviteCustomerView, HttpServletRequest request, HttpServletResponse response) {
		
		try{
			Set<ConstraintViolation<InviteCustomerView>> validateResult = validator.validate(inviteCustomerView, DoAllotGroup.class);
			if (!validateResult.isEmpty()) {
				return new AllotResultVO(AllotResultCode.PARAM_ERROR.getCode(), validateResult.iterator().next().getMessage());
			}else{
				customerManagementService.doAllotInviteCustomer(inviteCustomerView);
				return new AllotResultVO(AllotResultCode.SUCCESS.getCode(), AllotResultCode.SUCCESS.getDesc());	
			}
		}catch(Exception e){
			logger.error("param={areaOrgId="+inviteCustomerView.getAreaOrgId()+"storeOrgId="+inviteCustomerView.getStoreOrgId()+"customerManagerId="+inviteCustomerView.getFinancingId()+"}", e);
			return new AllotResultVO(AllotResultCode.EXCEPTION.getCode(), AllotResultCode.EXCEPTION.getDesc());
		}
	}
	
	/**
	 * 邀请客户列表修改状态
	 * By 任志远 2017年5月16日
	 *
	 * @param inviteCustomerView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "inviteCustomerList/updateStatus", method = RequestMethod.GET)
	public String updateInviteCustomerStatus(InviteCustomerView inviteCustomerView, HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("inviteCustomerView", inviteCustomerView);
		return "apply/consult/updateInviteCustomerStatus";
	}
	
	/**
	 * 邀请客户列表保存修改状态
	 * By 任志远 2017年5月16日
	 *
	 * @param inviteCustomerView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "inviteCustomerList/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public AllotResultVO doUpdateInviteCustomerStatus(InviteCustomerView inviteCustomerView, HttpServletRequest request, HttpServletResponse response){
		try{
			Set<ConstraintViolation<InviteCustomerView>> validateResult = validator.validate(inviteCustomerView, UpdateStatusGroup.class);
			if (!validateResult.isEmpty()) {
				return new AllotResultVO(AllotResultCode.PARAM_ERROR.getCode(), validateResult.iterator().next().getMessage());
			}else{
				customerManagementService.doAllotInviteCustomer(inviteCustomerView);
				return new AllotResultVO(AllotResultCode.SUCCESS.getCode(), AllotResultCode.SUCCESS.getDesc());	
			}
		}catch(Exception e){
			logger.error("param={areaOrgId="+inviteCustomerView.getAreaOrgId()+"storeOrgId="+inviteCustomerView.getStoreOrgId()+"customerManagerId="+inviteCustomerView.getFinancingId()+"}", e);
			return new AllotResultVO(AllotResultCode.EXCEPTION.getCode(), AllotResultCode.EXCEPTION.getDesc());
		}
	}
	
}
