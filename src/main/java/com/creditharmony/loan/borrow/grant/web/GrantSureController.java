package com.creditharmony.loan.borrow.grant.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.RateInfoService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantService;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.service.UrgeStatisticsViewService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.ExportGrantSureHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;

/**
 * 放款确认列表处理事件
 * @Class Name GrantSureController
 * @author 朱静越
 * @Create In 2015年11月27日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/grant/grantSure")
public class GrantSureController extends BaseController {
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private UserManager userManager;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
	private RateInfoService rateInfoService;
	@Autowired
	private UrgeStatisticsViewService urgeStatisticsViewService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private GrantService grantService;
	@Autowired
	private GrantSureService grantSureService;

	private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);
	private final String strClassName = this.getClass().getName();
	
	/**
	 * 待款项确认数据列表查询
	 * 2017年2月8日
	 * By 朱静越
	 * @param model
	 * @param request
	 * @param response
	 * @param grtQryParam 查询条件
	 * @param listFlag 区分标识：信借和资金托管
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGrantSureInfo")
	private String getGrantSureInfo(Model model,
			HttpServletRequest request, HttpServletResponse response,
		    LoanFlowQueryParam grtQryParam, String listFlag) throws Exception {
		Double totalGrantMoney = 0.0;
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		if (LoanModel.TG.getName().equals(listFlag)) {
			grtQryParam.setModel(LoanModel.TG.getCode());
			grtQryParam.setTgFlag(YESNO.YES.getCode());
		}
		Page<LoanFlowWorkItemView> grantSurePage = grantSureService.getGrantSureList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();;
		for (LoanFlowWorkItemView tagItem : grantSurePage.getList()) {
		    tagItem.setContractVersion(DictUtils.getLabel(dictMap,LoanDictType.CONTRACT_VER, tagItem.getContractVersion()));
		    tagItem.setModelLabel(DictCache.getInstance().getDictLabel("jk_loan_model",tagItem.getModel()));
		    tagItem.setDepositBank(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, tagItem.getDepositBank()));
		    tagItem.setLoanStatusName(DictUtils.getLabel(dictMap, LoanDictType.LOAN_APPLY_STATUS, tagItem.getLoanStatusName()));
		}
		if (ObjectHelper.isNotEmpty(grantSurePage.getList())) {
			totalGrantMoney = grantSurePage.getList().get(0).getTotalGrantMoney();
		}
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		// 不是合同审核操作人员 默认为1
		String notContractAudit = YESNO.YES.getCode();
		// 款项确认权限控制
	    List<Role> roleList = user.getRoleList();
        for(Role r:roleList){
            if(r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
                r.getId().equals(BaseRole.LOANER_CONTRACT_APPROVER.id)||
                BaseRole.CONTRACT_MAKE_LEADER.id.equals(r.getId())||
                BaseRole.LOANER_CONTRACT_MAKER.id.equals(r.getId())||
                BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id.equals(r.getId())){
                notContractAudit=YESNO.NO.getCode();
                break;   
            }
        } 
		// 汇金产品获取
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalItem", grantSurePage.getCount());
		model.addAttribute("listFlag", listFlag);
		model.addAttribute("notContractAudit", notContractAudit);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", grantSurePage);
		
		String menuId = (String) request.getParameter("menuId");
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		return "borrow/grant/loanflow_grantSureList";
	}

	/**
	 * 更改单子的借款标识：通过单子的借款编号等，将单子的标识更新为传过来的参数
	 * 2015年12月3日 By 朱静越
	 * @param model
	 * @param checkVal 从前台传送过来的参数，流程参数
	 * @param borrowFlag 要进行修改的标识
	 * @param request
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "updateLoanFlag")
	public String updateLoanFlag(Model model,HttpServletRequest request, String checkVal,
			String borrowFlag) {
		// 遍历checkVal,以';'把，每一个放到apply中
		String[] applyIds = null;
		String[] applyParam = null;
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		String message = "";
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		if (StringUtils.isNotEmpty(checkVal)) {
			applyIds = checkVal.split(";");
			for (int i = 0; i < applyIds.length; i++) {
				applyParam = applyIds[i].split(",");
				try {
					logger.debug("类"+strClassName+"的更新标识的方法：updateLoanFlag开始处理");
					grantSureService.updateFlagParam(applyParam, borrowFlag);
					logger.debug("类"+strClassName+"的更新标识的方法：updateLoanFlag处理结束");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(
							"updateFlagParam:更改流程标识发生异常,apply={},borrowFlag={}",
							new Object[] { applyParam,borrowFlag});
					failContractCode = applyParam[1];
				}
				if (StringUtils.isNotEmpty(failContractCode)) {
					failedCodeList.add(failContractCode);
					failNum++;
				}else{
					successNum++;
				}
			}
		}
		// 单笔修改
		if (null!= applyIds && applyIds.length == 1) {
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				message = "修改失败";
			}else{
				message = "修改成功";
			}
		// 多笔修改
		}else {
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
				message = ajaxNotify.getMessage();
			}else{
				message = "修改成功";
			}
		}		
		return message;
	}

	/**
	 * 导出客户信息表 2015年12月22日 By 朱静越
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @return null
	 */
	@RequestMapping(value = "grantCustomerExl")
	public String grantCustomerExl(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal, RedirectAttributes redirectAttributes, String listFlag) {
		String[] ids = null;
		Map<String, Object> idMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(idVal)) {
				if (LoanModel.TG.getName().equals(listFlag)) {
					grtQryParam.setTgFlag(YESNO.YES.getCode());
					grtQryParam.setModel(LoanModel.TG.getCode());
				}
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> workItems = grantSureService.getGrantSureList(grtQryParam);
				if (ArrayHelper.isNotEmpty(workItems)) {
					ids = new String[workItems.size()];
					for (int i = 0; i < workItems.size(); i++) {
						ids[i] = workItems.get(i).getApplyId();
					}
				}
			} else {
				ids = idVal.split(",");
			}
			if(ObjectHelper.isEmpty(ids))
			{
				addMessage(redirectAttributes, "无可导出的客户信息！");
				return "redirect:"
						+ adminPath
						+ "/borrow/grant/grantSure/getGrantSureInfo?listFlag="
						+ listFlag+"&menuId="+request.getParameter("menuId");
			}
			idMap.put("ids", ids);
			String fileName = FileExtension.GRANT_CUSTOMER + DateUtils.getDate("yyyyMMdd");
			String[] header = {"客户姓名","身份证号码","性别","产品种类","信用合同编号","所在地","还款银行账号","开户行","实放金额","合同金额","月还款金额","期数","审核日期","首期还款日"
	        		,"合同到期日","账单日","模式","渠道","合同签订日期","是否电销","是否加急","划扣平台","合同版本号",
	        		"催收服务费","外访费","审核次数","最后一次退回原因","审核人","风险等级","回访状态"};
			ExportGrantSureHelper.customerExport(idMap, header, fileName, response, userManager);
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "客户信息表导出失败，请重试！");
			logger.error("方法:grantCustomerExl,客户信息表导出失败.");
			e.printStackTrace();
		} 
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantSure/getGrantSureInfo?listFlag="
		+ listFlag+"&menuId="+request.getParameter("menuId");
	}

	/**
	 * 打款表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出 
	 * 2015年12月22日 By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "sendMoneyExl")
	public String sendMoneyExl(HttpServletRequest request,String listFlag,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal, RedirectAttributes redirectAttributes) {
		String[] ids = null;
		Map<String, Object> idsMap = new HashMap<String, Object>();
		
		try {
			if (StringUtils.isEmpty(idVal)) {
				if (LoanModel.TG.getName().equals(listFlag)) {
					grtQryParam.setTgFlag(YESNO.YES.getCode());
					grtQryParam.setModel(LoanModel.TG.getCode());
				}
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> workItems = grantSureService.getGrantSureList(grtQryParam);
				if (ArrayHelper.isNotEmpty(workItems)) {
					ids = new String[workItems.size()];
					for (int i = 0; i < workItems.size(); i++) {
						ids[i] = workItems.get(i).getApplyId();
						}
				}
			} else {
				ids = idVal.split(",");
			}
			idsMap.put("ids", ids);
			String fileName = threePartFileName.getHjGrantExportFileName();

			String[] header={"放款时间","序号","地区","合同编号","账户名","证件号码","期数","借款利率（%）","合同金额","实放金额","催收服务费","账户","开户行","支行名称","渠道","合同版本号","是否电销","是否有保证人","回访状态"};
			//String[] header={"放款时间","序号","地区","合同编号","账户名","证件号码","期数","借款利率（%）","合同金额","实放金额","催收服务费","账户","开户行","支行名称","渠道","合同版本号","是否加急","是否无纸化","是否有保证人","回访状态"};
			ExportGrantSureHelper.exportData(idsMap, header,fileName, response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "打款表导出失败，请重试！");
		}
		return "redirect:"
				+ adminPath
				+ "/borrow/grant/grantSure/getGrantSureInfo?listFlag="+listFlag+"&menuId="+request.getParameter("menuId");
	}

	/**
	 * 汇总表导出，默认导出查询条件下的全部的单子 
	 * 2015年12月22日 By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "grantSumExl")
	public String grantSumExl(HttpServletRequest request,String listFlag,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal, RedirectAttributes redirectAttributes) {
		String[] ids = null;
		Map<String, Object> idsMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(idVal)) {
				if (LoanModel.TG.getName().equals(listFlag)) {
					grtQryParam.setModel(LoanModel.TG.getCode());
					grtQryParam.setTgFlag(YESNO.YES.getCode());
				}
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> workItems = grantSureService.getGrantSureList(grtQryParam);
				if (ArrayHelper.isNotEmpty(workItems)) {
					ids = new String[workItems.size()];
					for (int i = 0; i < workItems.size(); i++) {
						ids[i] = workItems.get(i).getApplyId();
					}
				}
			} else {
				ids = idVal.split(",");
			}
			idsMap.put("ids", ids);
			String fileName = threePartFileName.getHjGrantSumExportFileName();
		 

			String[] header = {"序号","合同编号","借款人姓名(共借人)","批借金额","合同金额","实放金额","外访费",
	        		"催收服务费","分期服务费","月利息","月还金额","产品种类","期数","总费率（%）","月利率（%）",
	        		"合同日期","首期还款日","加急费","渠道","合同版本号","划扣平台","是否电销","是否无纸化","推送日期"};
//			String[] header = {"序号","合同编号","借款人姓名(共借人)","批借金额","合同金额","实放金额","外访费","前期咨询费","前期审核费","前期居间服务费","前期信息服务费","前期综合服务费",
//	        		"催收服务费","产品种类","期数","风险等级","总费率（%）","月利率（%）","合同日期","首期还款日","加急费","渠道","合同版本号","是否加急","是否无纸化","是否有保证人","月服务费"};
			ExportGrantSureHelper.sumExport(idsMap, response, header, fileName);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "汇总表导出失败，请重试！");
		}
		return "redirect:"
				+ adminPath
				+ "/borrow/grant/grantSure/getGrantSureInfo?listFlag="+listFlag+"&menuId="+request.getParameter("menuId");
	}
	
	/**
	 * 放款确认导入回执结果，修改单子的状态，门店冻结的和待回访的数据不允许操作确认
	 * 在上传回执结果的时候，如果当前上传的单子中有门店申请冻结的单子，给出提示；
	 * 2016年3月24日 By 朱静越
	 * @param request
	 * @param model
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "file", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes, String listFlag) {
		String message = null;
		StringBuffer messageBuffer = new StringBuffer();
		String frozenContractCode = null;
		List<String> frozenContractCodeList = Lists.newArrayList();
		//保存待回访的数据
		List<SendMoneyEx> revisitStatusWaitContractCodeList = Lists.newArrayList();
		try {
			ExcelUtils excelUtil = new ExcelUtils();
			List<SendMoneyEx> dataList = (List<SendMoneyEx>) excelUtil.importExcel(
					LoanFileUtils.multipartFile2File(file), 1, 0,
					SendMoneyEx.class);
			for (SendMoneyEx sendItem : dataList) {
				if(StringUtils.isNotEmpty(sendItem.getContractCode())){
					frozenContractCode = loanGrantService.selFrozenContract(sendItem.getContractCode());
					if (StringUtils.isNotEmpty(frozenContractCode)) {
						frozenContractCodeList.add(frozenContractCode);
					}
					Contract contract = contractService.findByContractCode(sendItem.getContractCode());
					if(contract != null && (GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(contract.getRevisitStatus())||
							GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(contract.getRevisitStatus()))){
						revisitStatusWaitContractCodeList.add(sendItem);
					}
				}
			}
			if(revisitStatusWaitContractCodeList.size() > 0){
				messageBuffer.append(GrantCommon.REVISIT_STATUS_INFO_TIP);
				for(SendMoneyEx sme : revisitStatusWaitContractCodeList){
					messageBuffer.append(sme.getCustomerName()+ " " + sme.getContractCode() + ",");
				}
				message = messageBuffer.toString();
				message = message.substring(0, message.length()-1);
				return null;
			}
			if(frozenContractCodeList.size() > 0){
				messageBuffer.append("合同编号如下：");
				for(String failedCodeItem : frozenContractCodeList){
					messageBuffer.append(failedCodeItem);
					messageBuffer.append("，");
				}
				messageBuffer.append("的客户门店已申请冻结，不允许导入");
				message = messageBuffer.toString();
				return null;
			}
			message = exportSendExl(dataList, listFlag);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法importResult：上传待款项确认回执结果,导入错误");
			message = "导入错误";
		} finally{
			addMessage(redirectAttributes, message);
			return "redirect:"
			+ adminPath
			+ "/borrow/grant/grantSure/getGrantSureInfo?listFlag="
			+ listFlag+"&menuId="+request.getParameter("menuId");
		}
	}

	/**
	 * 放款确认导入回执结果，单笔操作
	 * 2016年5月5日
	 * By 朱静越
	 * @param applyIdList
	 * @param dataList
	 * @param listFlag
	 * @return
	 */
	public String exportSendExl(List<SendMoneyEx> dataList, String listFlag) {
		String message = "";
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		String curLetter = threePartFileName.getLoanCur();
        String curUrgentFlag = threePartFileName.getUrgeCur();
        Date submitTime = new Date();
		for (int i=0; i<dataList.size();i++) {
			Contract contract = contractService.findByContractCode(dataList.get(i).getContractCode());
			if (ObjectHelper.isNotEmpty(contract)) {
				try {
					message = grantSureService.saveImportSend(contract, listFlag, curLetter, 
							curUrgentFlag,submitTime,dataList.get(i).getLoanUrgentFlag());
				} catch (Exception eItem) {
					eItem.printStackTrace();
					failContractCode = contract.getContractCode();
					logger.error("方法exportSendExl：单笔处理待款项确认上传回执结果发生异常，合同编号为："+failContractCode,eItem);
				}
				if (StringUtils.isNotEmpty(failContractCode)) {
					failedCodeList.add(failContractCode);
					failNum++;
				}else{
					successNum++;
				}
			}
		}
		if (ArrayHelper.isNotEmpty(failedCodeList)) {
			AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
			message = ajaxNotify.getMessage();
		}else {
			message = "处理成功"+successNum+"条";
		}
		return message;
	}
	
	/**
	 * 标识上传功能，获得标识列的值，进行校验，为空的表示为【财富】
	 * 如果标识不为【P2P】，【财富】的进行统计，完成之后，直接进行弹出。
	 * 如果所有标识符合条件，一笔一笔进行更新标识
	 * 2016年5月17日
	 * By 朱静越
	 * @param request
	 * @param model
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping(value = "uploanFlag")
	public String uploanFlag(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "file", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		String message = null;
		int flagInt = 0;
		try {
			ExcelUtils excelUtil = new ExcelUtils();
			List<SendMoneyEx> dataList = (List<SendMoneyEx>) excelUtil.importExcel(
					LoanFileUtils.multipartFile2File(file), 1, 0,
					SendMoneyEx.class);
			for (SendMoneyEx sendItem : dataList) {
				String loanFlag = sendItem.getLoanFlag();
				if (StringUtils.isEmpty(loanFlag)) {
					loanFlag = ChannelFlag.CHP.getName();
				}
				if (StringUtils.isNotEmpty(loanFlag)&&("P2p".equals(loanFlag)||"p2P".equals(loanFlag)||"p2p".equals(loanFlag)||"P2P".equals(loanFlag))) {
					loanFlag = ChannelFlag.P2P.getName();
				}
				if (!(ChannelFlag.CHP.getName().equals(loanFlag)||ChannelFlag.P2P.getName().equals(loanFlag))) {
					flagInt++;
					break;
				}
				sendItem.setLoanFlag(loanFlag);
			}
			if (flagInt > 0) {
				message = "存在错误标识，请重新上传";
			} else {
				message = exportUpFlag(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法importResult,导入错误");
			message = "导入错误";
		} finally{
			addMessage(redirectAttributes, message);
			return "redirect:"
			+ adminPath
			+ "/borrow/grant/grantSure/getGrantSureInfo?menuId="+request.getParameter("menuId");
		}
	}
	
	/**
	 * 上传标识单笔处理:将数据库中该数据的借款标识更新为上传的信息，
	 * 同时根据数据库中的单子标识和上传的单子标识进行比较，记录历史
	 * 2016年5月17日
	 * By 朱静越
	 * @param dataList 上传的表格
	 * @return
	 */
	public String exportUpFlag(List<SendMoneyEx> dataList) {
		String message = "导入成功";
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		// 根据contractCode查询合同，获得标识
		for (int i = 0; i < dataList.size(); i++) {
			Contract contract = contractService.findByContractCode(dataList.get(i).getContractCode());
			if (ObjectHelper.isNotEmpty(contract)) {
				try {
					String loanFlag = dataList.get(i).getLoanFlag();
					grantSureService.saveUpFlag(contract,loanFlag);
				} catch (Exception eItem) {
					eItem.printStackTrace();
					failContractCode = contract.getContractCode();
					logger.error("类"+strClassName+"的方法exportUpFlag：处理上传标识，出现异常",eItem);
				}
				if (StringUtils.isNotEmpty(failContractCode)) {
					failedCodeList.add(failContractCode);
					failNum++;
				}else{
					successNum++;
				}
			}
		}
		if (ArrayHelper.isNotEmpty(failedCodeList)) {
			AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
			message = ajaxNotify.getMessage();
		}
		return message+successNum+"条";
	}	
	
	
	@RequestMapping(value = "pathSkip")
	public String pathSkip(Model model,
			HttpServletRequest request, HttpServletResponse response,String returnURL,String isFailBtn){
		model.addAttribute("isFailBtn", isFailBtn);
		return "borrow/grant/" + returnURL;
	}
	
	
	
}