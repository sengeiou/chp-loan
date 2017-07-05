package com.creditharmony.loan.telesales.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanDepand;
import com.creditharmony.loan.common.entity.ExportHistory;
import com.creditharmony.loan.common.service.ExportHistoryService;
import com.creditharmony.loan.telesales.entity.TelesalesHelper;
import com.creditharmony.loan.telesales.service.TelesalesConsultService;
import com.creditharmony.loan.telesales.service.TelesalesCustomerManagementService;
import com.creditharmony.loan.telesales.view.TelesaleConsultSearchView;

/**
 * 电销数据导出Controller层
 * 
 * @Class Name TelesalesExportFileController
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/telesales/export")
public class TelesalesExportFileController extends BaseController {

	@Autowired
	private TelesalesConsultService telesalesConsultService;

	@Autowired
	private TelesalesCustomerManagementService telesalesCustomerManagementService;
	
	@Autowired
	private ExportHistoryService exporrHis;

	/**
	 * 电销借款申请管理导出Excel 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportApplyInfoExcel", "" })
	public String exportApplyInfoExcel(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		ExportHistory his = new ExportHistory();
		his.preInsert();
		his.setExportDesc("电销借款申请管理列表");
		exporrHis.insertExportHistory(his);
		String loanCode = request.getParameter("loancode") == null ? "" : request.getParameter("loancode");
		String[] id = loanCode.split(";");
		if (StringUtils.isNotEmpty(loanCode)) {
			StringBuffer ids = new StringBuffer();
			for (String i : id) {
				if (StringUtils.isNotEmpty(i)) {
					ids.append("'").append(i).append("',");
				}
			}
			ids.deleteCharAt(ids.length() - 1);
			consultView.setLoanCode(ids.toString());
		} else {
			consultView.setLoanCode(null);
		}
		// 数据权限开始---------------
		User user = UserUtils.getUser();
		List<Role> roleList = user.getRoleList();
		boolean isManager = false;
		// 如果登录人是电销总监//或者电销现场经理或者电销数据专员的角色 则能看见所有的数据
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

		consultView.setTableName("电销借款申请管理导出");
		// 导出
		try {
			TelesalesHelper.exportData(consultView, response, null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleApplyLoanList";
		}

	}

	/**
	 * 信借电销咨询客户列表导出Excel 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = { "exportTelesaleCustomerListExcel", "" })
	public String findTelesaleCustomerList(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		ExportHistory his = new ExportHistory();
		his.preInsert();
		his.setExportDesc("电销咨询客户列表");
		exporrHis.insertExportHistory(his);
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
		consultView.setTableName("电销客户咨询列表");
		try {
			// ExportPosHelper.exportData(posBacktage, response);
			TelesalesHelper.exportData(consultView, response, "exportTelesaleCustomerListExcel");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/consult/telesaleConsultList";
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
		idArray = ids.split(";");
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			if (i == 0) {
				parameter.append("'" + id + "'");
			} else {
				parameter.append(",'" + id + "'");
			}
		}
		return parameter.toString();
	}

	/**
	 * 电销借款申请管理结清资源 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportLoanCleanExcel", "" })
	public String exportLoanCleanExcel(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * consultView
		 * .setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); //
		 * 电销团队主管角色id consultView
		 * .setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); //
		 * 电销现场经理角色id // 设置借款状态为结清和提前结清 String dictLoanStatusCode = "'" +
		 * LoanApplyStatus.SETTLE.getCode() + "','" +
		 * LoanApplyStatus.EARLY_SETTLE.getCode() + "'";
		 * consultView.setDictLoanStatusCode(dictLoanStatusCode);
		 * //------------------------------------- String idVal =
		 * consultView.getId(); if(idVal!= null && !"".equals(idVal)){
		 * consultView.setId(appendString(idVal)); }
		 */
		/*
		 * String idVal = posBacktage.getId(); if(idVal!= null &&
		 * !"".equals(idVal)){ posBacktage.setId(appendString(idVal)); }
		 */
		try {
			// ExportPosHelper.exportData(posBacktage, response);
			ExportHistory his = new ExportHistory();
			his.preInsert();
			his.setExportDesc("电销结清资源");
			exporrHis.insertExportHistory(his);
			consultView.setTableName("结清资源");
			TelesalesHelper.exportData(consultView, response, "exportLoanCleanExcel");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleApplyLoanList";
		}
	}

	/**
	 * 电销借款申请管理不签约资源 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportLoanNoSignExcel", "" })
	public String exportLoanNoSignExcel(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色id
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色id
		ExportHistory his = new ExportHistory();
		his.preInsert();
		his.setExportDesc("电销不签约资源");
		exporrHis.insertExportHistory(his);
		// 设置借款状态为签约超时
		String dictLoanStatusCode = "'" + LoanApplyStatus.SIGN_TIMEOUT.getCode() + "'";
		consultView.setDictLoanStatusCode(dictLoanStatusCode);
		consultView.setTableName("不签约资源");
		String idVal = consultView.getId();
		if (idVal != null && !"".equals(idVal)) {
			consultView.setId(appendString(idVal));
		}
		/*
		 * String idVal = posBacktage.getId(); if(idVal!= null &&
		 * !"".equals(idVal)){ posBacktage.setId(appendString(idVal)); }
		 */
		try {
			// ExportPosHelper.exportData(posBacktage, response);
			TelesalesHelper.exportData(consultView, response, "exportLoanNoSignExcel");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleApplyLoanList";
		}

	}

	/**
	 * 电销借款申请管理结算再贷资源 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportLoanAgainExcel", "" })
	public String exportLoanAgainExcel(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		/* 电销借款申请管理结算再贷资源 */
		// 条件状态
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色id
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色id
		// 设置借款状态为结清待确认
		String dictLoanStatusCode = "'" + LoanApplyStatus.SETTLE_CONFIRM.getCode() + "','" + LoanApplyStatus.EARLY_SETTLE.getCode() + "'";
		consultView.setDictLoanStatusCode(dictLoanStatusCode);
		// -------------------------------
		consultView.setTableName("结清再贷资源");
		String idVal = consultView.getId();
		if (idVal != null && !"".equals(idVal)) {
			consultView.setId(appendString(idVal));
		}
		/*
		 * String idVal = posBacktage.getId(); if(idVal!= null &&
		 * !"".equals(idVal)){ posBacktage.setId(appendString(idVal)); }
		 */
		ExportHistory his = new ExportHistory();
		his.preInsert();
		his.setExportDesc("电销结清再贷资源");
		exporrHis.insertExportHistory(his);
		try {
			// ExportPosHelper.exportData(posBacktage, response);
			TelesalesHelper.exportData(consultView, response, "exportLoanAgainExcel");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleApplyLoanList";
		}
	}

	/**
	 * 电销借款申请管理审批拒绝资源 2016年3月11日 By 周怀富
	 * 
	 * @param consultView
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportLoanNoAuditExcel", "" })
	public String exportLoanNoAuditExcel(TelesaleConsultSearchView consultView, Model model, HttpServletRequest request, HttpServletResponse response) {
		// 导出条件
		consultView.setTelesaleTeamLeaderRole(LoanRole.MOBILE_SALE_TEAM_MANAGER.id); // 电销团队主管角色id
		consultView.setTelesaleSiteManagerRole(LoanRole.MOBILE_SALE_MANAGER.id); // 电销现场经理角色id
		// 设置借款状态为复审拒借
		String dictLoanStatusCode = "'" + LoanApplyStatus.RECHECK_REJECT.getCode() + "'";
		consultView.setDictLoanStatusCode(dictLoanStatusCode);

		consultView.setTableName("审批拒绝资源");
		// 数据权限------------------------------------------------

		// 数据权限------------------------------------------------

		String idVal = consultView.getId();
		if (idVal != null && !"".equals(idVal)) {
			consultView.setId(appendString(idVal));
		}
		/*
		 * String idVal = posBacktage.getId(); if(idVal!= null &&
		 * !"".equals(idVal)){ posBacktage.setId(appendString(idVal)); }
		 */
		ExportHistory his = new ExportHistory();
		his.preInsert();
		his.setExportDesc("电销审批拒绝资源");
		exporrHis.insertExportHistory(his);
		try {
			// ExportPosHelper.exportData(posBacktage, response);
			TelesalesHelper.exportData(consultView, response, "exportLoanNoAuditExcel");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleApplyLoanList";
		}

	}
}
