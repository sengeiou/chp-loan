package com.creditharmony.loan.telesales.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.telesales.entity.TelesalesConsultInfo;
import com.creditharmony.loan.telesales.service.TelesalesConsultService;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * 电销客户咨询Controller层
 * 
 * @Class Name TelesalesConsultController
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/telesales/consult")
public class TelesalesConsultController extends BaseController {

	@Autowired
	private ConsultService consultService;

	@Autowired
	private TelesalesConsultService telesalesConsultService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private NumberMasterService numberMasterService;

	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;

	@Autowired
	private CustomerManagementService customerManagementService;

	/**
	 * 初始化新增电销客户咨询界面 2016年3月11日 By 周怀富
	 * 
	 * @param consult
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = { "openTelesalesCustomerForm" })
	public String openTelesalesCustomerForm(TelesalesConsultInfo consult, HttpServletRequest request, Model model) {
		User user = UserUtils.getUser();

		consult.setManagerCode(user.getId()); // 电销专员编号
		consult.setManagerName(user.getName()); // 电销专员名称
		String departmentId = user.getDepartment().getId();// 电销组织机构
		String teamLeaderRoleId = LoanRole.MOBILE_SALE_TEAM_MANAGER.id.toString(); // 电销团队主管角色ID
		TelesalesConsultInfo teamLeaderInfo = telesalesConsultService.findUserIdByOrgAndRole(departmentId, teamLeaderRoleId);
		String loanTeamEmpcode = ""; // 电销团队主管ID
		String sceneUserIdString = ""; // 现场经理ID
		if (!ObjectHelper.isEmpty(teamLeaderInfo)) {
			// 根据电销专员查询电销团队主管
			loanTeamEmpcode = teamLeaderInfo.getId().toString();
			// 查询电销团队主管的用户信息
			User teamLeaderUser = UserUtils.get(loanTeamEmpcode);
			// 电销团队主管的上级ID
			String teamLeaderDepartmentId = teamLeaderUser.getDepartment() == null ? "" : teamLeaderUser.getDepartment().getParentId();
			String sceneRoleId = LoanRole.MOBILE_SALE_MANAGER.id.toString();
			// 根据电销团队主管查询电销现场经理
			TelesalesConsultInfo sceneLeaderInfo = telesalesConsultService.findUserIdByOrgAndRole(teamLeaderDepartmentId, sceneRoleId);
			if (!ObjectHelper.isEmpty(sceneLeaderInfo)) {
				sceneUserIdString = sceneLeaderInfo.getId().toString();
			}
		}
		consult.setLoanTeamEmpcode(loanTeamEmpcode);
		consult.setSceneManagerCode(sceneUserIdString);
		consult.setDictLoanType("1");

		String curOrgId = user.getDepartment().getId();
		// 本人所在团队ID
		consult.setLoanTeamOrgId(curOrgId);
		consult.setTeleSalesOrgid(curOrgId);
		model.addAttribute("consult", consult);
		return "telesales/consult/telesalesConsultForm";
	}

	/**
	 * 保存电销客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param consult
	 * @param model
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "saveTelesalesInfo")
	public String saveTelesalesInfo(TelesalesConsultInfo consult, Model model, RedirectAttributes redirectAttributes) {
		// 设置沟通时间
		if (ObjectHelper.isEmpty(consult.getConsultRecord().getConsCommunicateDate())) {
			consult.getConsultRecord().setConsCommunicateDate(new Date());

		}
		boolean insert = false;
		// 客户基本信息
		CustomerBaseInfo customerBaseInfo = consult.getCustomerBaseInfo();
		if (!ObjectHelper.isEmpty(customerBaseInfo) && StringUtils.isEmpty(customerBaseInfo.getCustomerCode())) {
			insert = true;
			String customerCode = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER);
			consult.getCustomerBaseInfo().setCustomerCode(customerCode);
			consult.getCustomerBaseInfo().setCustomerName(StringEscapeUtils.unescapeHtml4(consult.getCustomerBaseInfo().getCustomerName()));
		}

		// 设置电销状态
		consult.setConsTelesalesFlag("1");
		// 如果下一步为发送至门店则将状态修改为未取单
		if (consult.getConsultRecord().getConsOperStatus().equals(RsStatus.SEND_TO_STORE.getCode().toString())) {
			consult.getConsultRecord().setConsOperStatus(RsStatus.NO_GET_ORDER.getCode());
		}
		TelesalesConsultInfo telesalesConsult = new TelesalesConsultInfo();
		telesalesConsult.setCustomerCode(consult.getCustomerBaseInfo().getCustomerCode());
		// 保存客户基本信息、借款信息、沟通记录信息
		telesalesConsultService.saveTelesalesConsultInfo(consult, insert);
		addMessage(redirectAttributes, "电销客户咨询录入成功：" + consult.getCustomerBaseInfo().getCustomerName());
		return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleCustomerList?repage";
	}

	/**
	 * 修改电销客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param consult
	 * @param model
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "updateTelesalesInfo")
	public String updateTelesalesInfo(TelesalesConsultInfo consult, Model model, RedirectAttributes redirectAttributes) {
		// 设置沟通时间
		if (ObjectHelper.isEmpty(consult.getConsultRecord().getConsCommunicateDate())) {
			consult.getConsultRecord().setConsCommunicateDate(new Date());
		}
		// 如果下一步为发送至门店则将状态修改为未取单
		if (consult.getConsultRecord().getConsOperStatus().equals(RsStatus.SEND_TO_STORE.getCode().toString())) {
			consult.getConsultRecord().setConsOperStatus(RsStatus.NO_GET_ORDER.getCode());
		}
		// 保存客户基本信息、借款信息、沟通记录信息
		telesalesConsultService.updateTelesalesConsultInfo(consult);
		addMessage(redirectAttributes, "电销客户咨询修改成功：" + consult.getCustomerBaseInfo().getCustomerName());
		return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleCustomerList?repage";
	}

	/**
	 * 取消订单发送 2016年3月11日 By 周怀富
	 * 
	 * @param consult
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = { "cancaleTelesaleConsultSend", "" })
	public String cancaleTelesaleConsultSend(TelesalesConsultInfo consult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		User user = UserUtils.getUser();
		ConsultRecord record = new ConsultRecord();
		// 设置沟通时间
		record.setConsCommunicateDate(new Date());
		record.setConsOperStatus(RsStatus.CONTINUE_CONFIRM.getCode());
		record.setConsLoanRecord("取消发送：" + user.getName());
		String consultId = request.getParameter("id");
		String userNameString = request.getParameter("name");
		consult.setId(consultId);
		consult.setConsOperStatus(RsStatus.CONTINUE_CONFIRM.getCode());
		consult.setConsultRecord(record);
		// 取消发送后,将状态设置为继续跟踪
		telesalesConsultService.cancleTelesalesConsult(consult);
		addMessage(redirectAttributes, "取消发送成功：" + userNameString);
		return "redirect:" + adminPath + "/telesales/customerManagement/findTelesaleCustomerList?repage";
	}

}
