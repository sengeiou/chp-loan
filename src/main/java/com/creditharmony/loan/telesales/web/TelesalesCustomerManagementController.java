package com.creditharmony.loan.telesales.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.druid.util.StringUtils;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.payback.entity.ex.LoanServiceBureauEx;
import com.creditharmony.loan.borrow.payback.service.LoanServiceBureauService;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanDepand;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.telesales.dao.TelesalesCustomerManagementDAO;
import com.creditharmony.loan.telesales.entity.TelesalesConsultInfo;
import com.creditharmony.loan.telesales.service.TelesalesConsultService;
import com.creditharmony.loan.telesales.service.TelesalesCustomerManagementService;
import com.creditharmony.loan.telesales.view.TelesaleConsultSearchView;

/**
 * 信借电销客户咨询列表Controller层
 * 
 * @Class Name TelesalesCustomerManagementController
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/telesales/customerManagement")
public class TelesalesCustomerManagementController extends BaseController {

	@Autowired
	private TelesalesCustomerManagementService telesalesCustomerManagementService;

	@Autowired
	private TelesalesConsultService telesalesConsultService;

	@Autowired
	private TelesalesCustomerManagementDAO telesalesCustomerDao;
	@Autowired
	private RepaymentDateService dateService;

	@Autowired
	private LoanServiceBureauService loanServiceBureauHavaToService;

	/**
	 * 电销已办任务列表 2016年3月11日 By 周怀富
	 * 
	 * @param page
	 * @param consultview
	 * @return Page
	 */
	@RequestMapping(value = "allStoresAlreadyDoList")
	public String allStoresAlreadyDoList(HttpServletRequest request, HttpServletResponse response, Model model, LoanServiceBureauEx loanServiceBureau) {

		// 是否电销 //是电销
		loanServiceBureau.setCustomerTelesalesFlag(YESNO.YES.getCode());

		loanServiceBureau.setEnumTwo(RepayType.EARLY_SETTLE.getCode());
		loanServiceBureau.setDictPaybackStatus("'" + RepayApplyStatus.SPLIT.getCode() + "'," + "'" + RepayApplyStatus.SPLITED.getCode() + "'," + "'" + RepayApplyStatus.TO_PAYMENT.getCode() + "'");
		// 数据权限控制
		String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		loanServiceBureau.setQueryRight(queryRight);
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.allStoresAlreadyDoList(new Page<LoanServiceBureauEx>(request, response), loanServiceBureau);
		// 页面转码
		Map<String, Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(waitPage.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (LoanServiceBureauEx ex : waitPage.getList()) {
				ex.setCustomerTelesalesFlagLabel(DictUtils.getLabel(dictMap, LoanDictType.TELEMARKETING, ex.getCustomerTelesalesFlag()));
				ex.setLoanMarkLabel(DictUtils.getLabel(dictMap, LoanDictType.CHANNEL_FLAG, ex.getLoanMark()));
				ex.setSplitBackResultLabel(DictUtils.getLabel(dictMap, LoanDictType.COUNTEROFFER_RESULT, ex.getSplitBackResult()));
				ex.setDictLoanStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.LOAN_APPLY_STATUS, ex.getDictLoanStatus()));
				ex.setDictPayUseLabel(DictUtils.getLabel(dictMap, LoanDictType.REPAY_TYPE, ex.getDictPayUse()));
				ex.setDictRepayMethodLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Channel, ex.getDictRepayMethod()));
				if (RepayType.EARLY_SETTLE.getCode().equals(ex.getDictPayUse())) {
					ex.setDictPayStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.PAY_STATUS, ex.getDictPayStatus()));
				} else {
					ex.setDictPayStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Apply_Status, ex.getDictPayStatus()));
				}
			}
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: allStoresAlreadyDoList, consult.id is: " + waitPage);
		// 电销售页面
		return "telesales/consult/telesaleAlready";
	}

	/**
	 * 信借电销客户咨询列表查询 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = { "findTelesaleCustomerList", "" })
	public String findTelesaleCustomerList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		List<Role> roleList = user.getRoleList();
		boolean isManager = false;
		// 如果登录人是电销总监//或者电销现场经理或电销数据专员的角色 则能看见所有的数据
		if (!isManager) {
			isManager = LoanDepand.electricData(isManager, roleList);
		}

		if (!isManager)
		// 如果是电销售团队主管的角色 则只能看见本团队的数据
		{
			isManager = LoanDepand.electricDataMobile(isManager, roleList);
			if (isManager) {
				// 查询登陆人团队所有的单子
				consultView.setConsTelesalesOrgcode(user.getDepartment().getId());
			}
		}

		if (!isManager)
		// 如果是电销录单专员或电销专员则只能看自己的数据
		{
			isManager = LoanDepand.electDataMobile(isManager, roleList);
			if (user != null) {
				consultView.setTelesaleManCode(user.getId());
			}
		}
		consultView.setTelesaleManRole(LoanRole.MOBILE_SALE_COMMISSIONER.id); // 电销专员角色id
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色

		Page<TelesaleConsultSearchView> page = (Page<TelesaleConsultSearchView>) telesalesCustomerManagementService.findTelesalesCustomerList(new Page<TelesaleConsultSearchView>(request, response), consultView);
		if (page == null) {
			page = new Page<TelesaleConsultSearchView>();
			page.setPageSize(30);
		}
		model.addAttribute("page", page);
		model.addAttribute("consultView", consultView);
		//导出Excel按钮只开放给电销数据专员，由于权限控制有问题，此处这么判断是临时的，以后等架构改了再用shiro的方式控制吧
		Boolean isSaleDateCommissioner=false;
		if(!ObjectHelper.isEmpty(roleList)){
			for(Role role:roleList){
				if(LoanRole.MOBILE_SALE_DATA_COMMISSIONER.id.equals(role.getId())){
					isSaleDateCommissioner = true;
                    break;
                }
			}
		}
		model.addAttribute("isSaleDateCommissioner", isSaleDateCommissioner);
		return "telesales/consult/telesaleConsultList";
	}

	/**
	 * 查询信借电销取单 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = "findTelesaleOrderList")
	public String findTelesaleOrderList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		Page<TelesaleConsultSearchView> page = new Page<TelesaleConsultSearchView>();
		page.setPageSize(30);
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		// 如果是电销部取单
		if (!LoanOrgType.MOBILE_SALE.key.equals(orgType) && !LoanOrgType.MOBILE_SALE_TEAM.key.equals(orgType)) { // 如果是电销录单专员的话不修改门店编码,否则的话不处理
			consultView.setStoreCode(orgId);
		}
		if (!StringUtils.isEmpty(consultView.getMateCertNum())) {
			consultView.setDictOperStatus(RsStatus.NO_GET_ORDER.getCode()); // 订单为未取单状态
			page = (Page<TelesaleConsultSearchView>) telesalesCustomerManagementService.findTelesaleCustomerOrderList(new Page<TelesaleConsultSearchView>(request, response), consultView);
		}
		model.addAttribute("page", page);
		model.addAttribute("consultView", consultView);
		return "telesales/order/telesaleOrderList";
	}

	/**
	 * 门店客服取单操作 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = { "dealTelesaleOrder", "" })
	public String dealTelesaleOrder(TelesaleConsultSearchView consultView, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		String userCode = user.getId();// 用户ID
		String userName = user.getName();// 用户姓名

		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";

		TelesalesConsultInfo consult = new TelesalesConsultInfo();
		ConsultRecord record = new ConsultRecord();
		if (!ObjectHelper.isEmpty(consultView)) {
			consult.setId(consultView.getId());
			// 不修改门店编码
			/*
			 * //如果是电销部取单 if (!LoanOrgType.MOBILE_SALE.key.equals(orgType) &&
			 * !LoanOrgType.MOBILE_SALE_TEAM.key.equals(orgType) ) { //
			 * 如果是电销录单专员的话不修改门店编码,否则的话不处理 consult.setStoreCode(orgId); }
			 */
			consult.setConsCustomerService(userCode);// 客户人员
			record.setConsOperStatus(RsStatus.GET_ORDER.getCode());// 设置为已取单
			record.setConsLoanRecord(userName + "已取单");// 沟通记录
			record.setConsCommunicateDate(new Date());
			consult.setConsultRecord(record);
		}
		// 更新取单信息和保存操作记录
		telesalesConsultService.getTelesaleOrder(consult);
		addMessage(redirectAttributes, "门店客服取单成功：" + userName);
		return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleOrderList";
	}

	/**
	 * 修改电销客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = { "updateTelesalesConsultInfo", "" })
	public String updateTelesalesConsultInfo(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String consultId = consultView.getId();
		String customerCode = consultView.getCustomerCode();
		String dictOperStatus = consultView.getDictOperStatus();
		// 查询单个客户咨询信息
		TelesalesConsultInfo consult = telesalesConsultService.getSingleTelesalesConsultInfo(consultId, customerCode);
		model.addAttribute("consult", consult);
		request.setAttribute("status", dictOperStatus);
		return "telesales/consult/telesalesConsultUpdate";
	}

	/**
	 * 查看客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = { "viewTelesalesConsultInfo", "" })
	public String viewTelesalesConsultInfo(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String consultId = request.getParameter("id");
		String customerCode = request.getParameter("customerCode");
		// 查询单个客户咨询信息
		TelesalesConsultInfo consult = telesalesConsultService.getSingleTelesalesConsultInfo(consultId, customerCode);
		model.addAttribute("consult", consult);
		return "telesales/consult/telesalesConsultView";
	}

	/**
	 * 电销客户信息查询 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = { "findTelesaleCustomerHisList", "" })
	public String findTelesaleCustomerHisList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		Page<TelesaleConsultSearchView> page = new Page<TelesaleConsultSearchView>();
		page.setPageSize(30);
		if (!StringUtils.isEmpty(consultView.getMateCertNum()) || !StringUtils.isEmpty(consultView.getCustomerMobilePhone())) {
			page = (Page<TelesaleConsultSearchView>) telesalesCustomerManagementService.findTelesaleCustomerLoanList(new Page<TelesaleConsultSearchView>(request, response), consultView);
		}

		model.addAttribute("page", page);
		model.addAttribute("consultView", consultView);
		return "telesales/manage/telesaleCustomerHisList";
	}

	/**
	 * 电销待复议列表筛选 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = { "findTelesaleReconsiderList", "" })
	public String findTelesaleReconsiderList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色id
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色id
		Page<TelesaleConsultSearchView> page = (Page<TelesaleConsultSearchView>) telesalesCustomerManagementService.findTelesaleReconsiderInfoList(new Page<TelesaleConsultSearchView>(request, response), consultView);
		if (page == null) {
			page = new Page<TelesaleConsultSearchView>();
			page.setPageSize(30);
		}
		model.addAttribute("page", page);
		model.addAttribute("consultView", consultView);
		return "telesales/manage/telesaleReconsiderList";
	}

	/**
	 * 电销信借申请管理列表 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = { "findTelesaleApplyLoanList", "" })
	public String findTelesaleApplyLoanList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {

		// 数据权限开始---------------
		User user = UserUtils.getUser();
		List<Role> roleList = user.getRoleList();
		boolean isManager = false;
		// 如果登录人是电销总监//或者电销现场经理或电销数据专员的角色 则能看见所有电销的数据
		if (!isManager) {
			isManager = LoanDepand.electricData(isManager, roleList);
		}

		if (!isManager)
		// 如果是电销售团队主管的角色 则只能看见本团队的数据
		{
			isManager = LoanDepand.electricDataMobile(isManager, roleList);
			if (isManager) {
				// 查询登陆人团队所有的单子
				consultView.setConsTelesalesOrgcode(user.getDepartment().getId());
			}
		}

		if (!isManager)
		// 如果是电销录单专员或电销专员则只能看自己的数据
		{
			isManager = LoanDepand.electDataMobile(isManager, roleList);
			if (user != null) {
				consultView.setTelesaleManCode(user.getId());
			}
		}
		// 结束 - ----------------
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色id
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色id

		Page<TelesaleConsultSearchView> page = (Page<TelesaleConsultSearchView>) telesalesCustomerManagementService.findTelesaleApplyLoanInfoList(new Page<TelesaleConsultSearchView>(request, response), consultView);
		if (page == null) {
			page = new Page<TelesaleConsultSearchView>();
			page.setPageSize(30);
		}
		// 获得所有产品列表(检索条件)
		List<TelesaleConsultSearchView> products = telesalesCustomerManagementService.finProductList();
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		String dayListStr = "";
		for (GlBill gb : dayList) {
			dayListStr = dayListStr + gb.getBillDay() + "|";
		}
		model.addAttribute("dayList", dayListStr);
		model.addAttribute("page", page);
		model.addAttribute("products", products);
		model.addAttribute("consultView", consultView);
		//导出Excel,结清资源,不签约资源,结算再贷资源,审批拒绝资源 5个按钮只开放给电销数据专员，由于权限控制有问题，此处这么判断是临时的，以后等架构改了再用shiro的方式控制吧
		Boolean isSaleDateCommissioner=false;
		if(!ObjectHelper.isEmpty(roleList)){
			for(Role role:roleList){
				if(LoanRole.MOBILE_SALE_DATA_COMMISSIONER.id.equals(role.getId())){
					isSaleDateCommissioner = true;
                    break;
                }
			}
		}
		model.addAttribute("isSaleDateCommissioner", isSaleDateCommissioner);
		return "telesales/manage/telesaleApplyLoanList";
	}

	/**
	 * 对于7天未取单的单子设置为门店放弃 2016年3月11日 By 周怀富
	 */
	public void updateStoreGiveUp() {
		telesalesConsultService.updateStoreGiveUpList();
	}
}
