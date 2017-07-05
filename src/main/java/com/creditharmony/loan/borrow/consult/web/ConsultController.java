package com.creditharmony.loan.borrow.consult.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.ocr.dto.RecognizeResult;
import com.creditharmony.core.ocr.util.RecognizeHelper;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.AdditionalUser;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.AdditionalUserManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.consult.constats.ConsultDataSource;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.borrow.consult.groups.ConsultRuleGroup;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.borrow.consult.view.ConsultBaseQuery;
import com.creditharmony.loan.borrow.consult.view.ConsultRuleResultVO;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * 客户咨询controller
 * 
 * @Class Name ConsultController
 * @author 张平
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/consult")
public class ConsultController extends BaseController {

	@Autowired
	private ConsultService consultService;

	@Autowired
	private NumberMasterService numberMasterService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private CustomerManagementService customerManagementService;

	@Autowired
	private AdditionalUserManager additionalUserManager;

	@Autowired
	private UserManager userManager;

	/**
	 * 初始化页面 2015年12月3日 By 张平
	 * 
	 * @param consult
	 * @param model
	 * @return consultForm。jsp
	 */
	@RequestMapping(value = { "openConsultForm" })
	public String openConsultForm(Consult consult, Model model) {
		User user = UserUtils.getUser();
		String consTeamEmpName = user.getName();
		// 查询代管账号
		AdditionalUser additionalUser = additionalUserManager.selectAdditionalUser(user.getId());

		User storeManager = null;
		User customerManager = null;

		// 如果是代管账号, 取原账号 角色为门店经理的账号 的姓名
		if (additionalUser != null) {
			// 查原账号 是不是门店经理
			storeManager = userManager.findUserByUserCodeAndRoleAndOrg(additionalUser.getUserCode(), LoanRole.STORE_MANAGER.id, user.getDepartment().getParentId());
			// 查原账号 是不是客户经理
			customerManager = userManager.findUserByUserCodeAndRoleAndOrg(additionalUser.getUserCode(), LoanRole.FINANCING_MANAGER.id, user.getDepartment().getId());
		}

		if (ObjectHelper.isNotEmpty(storeManager) && ObjectHelper.isNotEmpty(customerManager)) {
			consTeamEmpName = storeManager.getName();
			model.addAttribute("storeManager", storeManager);
		} else {
			Map<String, String> userMap = new HashMap<String, String>();
			String departmentId = UserUtils.getUser().getDepartment().getId();
			userMap.put("departmentId", departmentId);// 部门id
			userMap.put("roleId", LoanRole.FINANCING_MANAGER.id);// 团队经理
			List<UserInfo> customerManagers = userInfoService.getRoleUser(userMap);
			model.addAttribute("customerManagers", customerManagers);
		}

		consult.setConsTeamEmpName(consTeamEmpName);
		consult.setLoanTeamEmpcode(user.getId());
		consult.setDictLoanType("1");
		consult.setLoanTeamOrgId(user.getDepartment().getId());
		model.addAttribute("consult", consult);

		return "apply/consult/consultForm";
	}


	/**
	 * 根据身份证 校验咨询规则 
	 * By 任志远 2017年4月10日
	 *
	 * @param certNum
	 * @return
	 */
	@RequestMapping("asynFindByCertNum")
	@ResponseBody
	public ConsultRuleResultVO checkConsultRule(ConsultBaseQuery consultBaseQuery) {
		try {
			Set<ConstraintViolation<ConsultBaseQuery>> validateResult = validator.validate(consultBaseQuery, ConsultRuleGroup.class);
			if (!validateResult.isEmpty()) {
				return new ConsultRuleResultVO(false, validateResult.iterator().next().getMessage());
			} else {
				ConsultRuleResultVO consultRuleResultVO = consultService.checkConsultRule(consultBaseQuery.getCertNum());
				return consultRuleResultVO;
			}
		} catch (Exception e) {
			logger.error("门店咨询校验====参数：" + consultBaseQuery.toString(), e);
			return new ConsultRuleResultVO(false, "");
		}
	}

	/**
	 * 保存业务数据 2015年12月3日 By 张平
	 * 
	 * @param consult
	 * @param model
	 * @param redirectAttributes
	 * @return 重定向findConsultList方法
	 */
	@RequestMapping(value = "saveConsult")
	public String saveConsult(Consult consult, Model model, RedirectAttributes redirectAttributes) {
		CustomerBaseInfo customer = consult.getCustomerBaseInfo();
		if (StringHelper.isNotEmpty(customer.getCustomerName())) {
			customer.setCustomerName(StringEscapeUtils.unescapeHtml4(customer.getCustomerName()));
			consult.setCustomerBaseInfo(customer);

		}
		List<String> telStatusList = new ArrayList<String>();
		telStatusList.add(RsStatus.CONTINUE_CONFIRM.getCode());
		telStatusList.add(RsStatus.NO_GET_ORDER.getCode());
		telStatusList.add(RsStatus.GET_ORDER.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("notTelsaleFlag", YESNO.NO.getCode());
		param.put("notTelOperStatus", NextStep.CONTINUE_CONFIRM.getCode());
		param.put("telsaleFlag", YESNO.YES.getCode());
		param.put("telOperStatusList", telStatusList);
		param.put("customerCertNum", consult.getCustomerBaseInfo().getMateCertNum());
		Consult storedConsult = customerManagementService.findConsultMess(param);
		if (!ObjectHelper.isEmpty(storedConsult)) {
			addMessage(redirectAttributes, "客户已咨询，不允许再次咨询");
		} else {
			// 设置沟通时间
			if (ObjectHelper.isEmpty(consult.getConsultRecord().getConsCommunicateDate())) {
				consult.getConsultRecord().setConsCommunicateDate(new Date());
			}
			boolean insert = false;
			CustomerBaseInfo customerBaseInfo = consult.getCustomerBaseInfo();
			if (!ObjectHelper.isEmpty(customerBaseInfo) && StringUtils.isEmpty(customerBaseInfo.getCustomerCode())) {
				insert = true;
				String customerCode = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER);
				consult.getCustomerBaseInfo().setCustomerCode(customerCode);
			}
			// 设置门店ID
			User user = UserUtils.getUser();
			Org curOrg = user.getDepartment();
			Org parentOrg = OrgCache.getInstance().get(curOrg.getParentId());
			if (!ObjectHelper.isEmpty(parentOrg)) {
				consult.setStoreOrgid(parentOrg.getId());
			}
			consult.setLoanTeamOrgId(curOrg.getId());
			consult.setConsultDataSource(ConsultDataSource.PC.getCode());
			consultService.saveCustomerBaseInfo(consult, insert);
			addMessage(redirectAttributes, "客户咨询录入成功：" + StringEscapeUtils.unescapeHtml4(consult.getCustomerBaseInfo().getCustomerName()));
		}
		return "redirect:" + adminPath + "/consult/customerManagement/findConsultList?repage";
	}
	
	 /**
	 * OCR识别 2016年1月20日 By zhangfeng
	 * 
	 * @return bean
	 */
	@RequestMapping(value = "updateFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public RecognizeResult getOcrResult(@RequestParam(value = "file") MultipartFile file) {
		RecognizeResult result = new RecognizeResult();
		try {
			result = RecognizeHelper.recognize("admin", file);
			logger.debug("ocr recoginze name:" + result.getName());
			logger.debug("ocr recoginze address:" + result.getAddress());
			logger.debug("ocr recoginze sex:" + result.getSex());
			logger.debug("ocr recoginze validDate:" + result.getValiddate());
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorCode(e.getMessage());
			logger.error("ocr recoginze exception!");
		}
		return result;
	}

}
