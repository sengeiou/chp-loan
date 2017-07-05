package com.creditharmony.loan.channel.jyj.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
import com.creditharmony.loan.borrow.grant.service.GrantService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantHisService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantAuditEx;
import com.creditharmony.loan.channel.jyj.service.JYJGrantAuditService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.google.common.collect.Lists;

/**
 * 放款审核进行处理
 * @Class Name GrantAuditController
 * @author 朱静越
 * @Create In 2015年12月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/jyj/grantAudit")
public class JYJGrantAuditController extends BaseController {
	
	@Autowired
	private JYJGrantAuditService grantAuditService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private GrantService grantService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private LoanGrantHisService loanGrantHisService;
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private GrantUrgeBackDao grantUrgeBackDao;
	
	
	/**
	 * 查询放款审核列表
	 * 2017年1月17日
	 * By 朱静越
	 * @param model
	 * @param loanFlowQueryParam
	 * @param request
	 * @param response
	 * @param returnUrl
	 * @param listFlag
	 * @return
	 */
	@RequestMapping(value = "grantAuditItem")
	public String grantDone(Model model, LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request, HttpServletResponse response) {
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		Page<JYJGrantAuditEx> grantAuditPage = grantAuditService.getGrantAuditList(
				new Page<JYJGrantAuditEx>(request, response), loanFlowQueryParam);
		Double totalGrantMoney = 0.00;
		if (ObjectHelper.isNotEmpty(grantAuditPage.getList())) {
			if(grantAuditPage.getList().get(0).getTotalGrantMoney()!=null){
				totalGrantMoney = grantAuditPage.getList().get(0).getTotalGrantMoney();
			}
		}
		// 放款批次的查询，初始化查询下拉列表的
		List<LoanGrant> grantPchList = loanGrantService.selGrantPch();
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("LoanFlowQueryParam", loanFlowQueryParam);
		model.addAttribute("workItems", grantAuditPage);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalItem", grantAuditPage.getCount());
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("grantPchList", grantPchList);
		return "channel/jyj/jyj_grantAuditList";
	}
	
	/**
	 * 导出放款审核列表
	 * 2017年1月19日
	 * By 朱静越
	 * @param request
	 * @param listFlag 区分标识
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param idVal applyIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "grantAuditExl")
	public String grantAuditExl(HttpServletRequest request,String listFlag,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal,RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		List<JYJGrantAuditEx> auditList = new ArrayList<JYJGrantAuditEx>();
		if (StringUtils.isEmpty(idVal)) {
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
		} else {
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			String[] applyIds = idVal.split(","); // 如果有选中的单子,将选中的单子导出
			grtQryParam.setApplyIds(applyIds);
		}
		try {
			auditList = grantAuditService.getGrantAuditList(grtQryParam);
			excelutil.exportExcel(auditList,
					FileExtension.GRANT_AUDIT + DateUtils.getDate("yyyyMMdd"),
					JYJGrantAuditEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, 1);
		
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "放款审核表导出失败，请重试");
		}
		return "redirect:" + adminPath + "/channel/jyj/grantAudit/grantAuditItem";
	}

	/**
	 * 查找放款历史
	 * 2016年5月10日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGrantHis")
	public String getGrantHis(HttpServletRequest request,
			HttpServletResponse response,String contractCode,Model model){
		Page<LoanGrantHis> historyPage = loanGrantHisService.getGrantHis(
				new Page<LoanGrantHis>(request, response), contractCode);
		model.addAttribute("page", historyPage);
		model.addAttribute("contractCode", contractCode);
		return "/borrow/history/grantspl";
	}

	/**
	 * 结果确认
	 * 2017年6月7日
	 * By wjj
	 * @param request
	 * @param response
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateEmergency")
	public String updateEmergency(HttpServletRequest request,LoanFlowQueryParam loanFlowQueryParam,
			HttpServletResponse response,String contractCode,String grantAuditResult,String emergencyRemark,Model model){
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		String message = "操作成功";
		int successNum = 0;
		int failNum = 0;
		AjaxNotify ajaxNotify = new AjaxNotify();
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		LoanInfo loanInfo = new LoanInfo();
		String returnMes = null;
		String emergencyRemarkName = DictCache.getInstance().getDictLabel("jk_chk_back_reason", emergencyRemark);
		// 如果没有进行勾选
		if("".equals(contractCode)){
			List<JYJGrantAuditEx> list = grantAuditService.getGrantAuditList(loanFlowQueryParam);
			if("0".equals(grantAuditResult)){
				//放款失败，状态改为放款退回,退回原因放到合同表
				for(int i=0;i<list.size();i++){
					failedContractCode = "";
					try {
						message = grantAuditService.updateBackResultAndLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode(),emergencyRemarkName, list.get(i));
						if (StringHelper.isNotEmpty(message)) {
							failedContractCode = list.get(i).getContractCode();
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("尾次放款审核退回，出现异常，合同编号为："+list.get(i).getContractCode());
						failedContractCode = list.get(i).getContractCode();
					}
				}
				if (StringHelper.isNotEmpty(failedContractCode)) {
					failedCodeList.add(failedContractCode);
					failNum ++;
				}else {
					successNum ++;
				}
			} else if("1".equals(grantAuditResult)){
				//如果首次放款比例为100%是，状态改为已放款
				for(int i=0;i<list.size();i++){
					failedContractCode = "";
					returnMes = judgeIsSuccess(list.get(i).getContractCode());
					if (StringHelper.isEmpty(returnMes)) {
						try {
							List<Map<String, String>> lists = grantAuditService.getFirstLoanProportion(list.get(i).getContractCode());
							if(!lists.isEmpty()&&lists.get(0)!=null){
								// 控制，如果划扣状态为划扣失败的，
								if("100.00".equals(String.valueOf(lists.get(0).get("firstloanproportion")))){
									LoanGrant loanGrant=new LoanGrant();
									loanGrant.setContractCode(list.get(i).getContractCode());
									loanGrant.setCheckResult(VerityStatus.PASS.getCode());
									loanGrant.setCheckTime(new Date());
									loanGrant.setCheckEmpId(UserUtils.getUser().getId());
									loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
									loanGrant.setLendingTime(new Date());
									// 更新借款状态
									loanInfo.setApplyId(list.get(i).getApplyId());
									loanInfo.setLoanCode(list.get(i).getLoanCode());
									loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT
											.getCode());
									grantAuditService.updateGrantDone(loanInfo,loanGrant);
								}else{
									grantAuditService.updateGrantFlag(list.get(i).getContractCode());
									loanInfo.setApplyId(list.get(i).getApplyId());
									loanInfo.setLoanCode(list.get(i).getLoanCode());
									loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_TO_SEND.getCode());
									historyService.saveLoanStatusHis(loanInfo,"首次放款审核", VerityStatus.PASS.getName(),"");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("尾次放款审核发生异常，合同编号为："+list.get(i).getContractCode());
							failedContractCode = list.get(i).getContractCode();
						}
					}else {
						failedContractCode = list.get(i).getContractCode();
					}
					if (StringHelper.isNotEmpty(failedContractCode)) {
						failedCodeList.add(failedContractCode);
						failNum ++;
					}else {
						successNum ++;
					}
				}
			}
		}else{
			String[] contracts = contractCode.split(",");
			if("0".equals(grantAuditResult)){
				//放款失败，状态改为放款退回,退回原因放到合同表
				for(int i=0;i<contracts.length;i++){
					failedContractCode = "";
					List<Map<String, String>> info = grantAuditService.getApplyId(contracts[i]);
					if(!info.isEmpty()&&info.get(0)!=null){
						JYJGrantAuditEx jyjGrantAuditEx = new JYJGrantAuditEx();
						jyjGrantAuditEx.setContractCode(contracts[i]);
						jyjGrantAuditEx.setApplyId(info.get(0).get("applyid"));
						jyjGrantAuditEx.setLoanCode(info.get(0).get("loancode"));
						try {
							message = grantAuditService.updateBackResultAndLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode(),emergencyRemarkName, jyjGrantAuditEx);
							if (StringHelper.isNotEmpty(message)) {
								failedContractCode = jyjGrantAuditEx.getContractCode();
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("首次次放款审核退回，出现异常，合同编号为："+jyjGrantAuditEx.getContractCode()
									+"异常信息为："+e);
							failedContractCode = jyjGrantAuditEx.getContractCode();
						}
					}
					if (StringHelper.isNotEmpty(failedContractCode)) {
						failedCodeList.add(failedContractCode);
						failNum ++;
					}else {
						successNum ++;
					}
					}
			} else if("1".equals(grantAuditResult)){
				//如果首次放款比例为100%是，状态改为已放款
				for(int i=0;i<contracts.length;i++){
					failedContractCode = "";
					List<Map<String, String>> lists = grantAuditService.getFirstLoanProportion(contracts[i]);
					returnMes = judgeIsSuccess(contracts[i]);
					if (StringHelper.isEmpty(returnMes)) {
						try {
							if(!lists.isEmpty()&&lists.get(0)!=null){
								if("100.00".equals(String.valueOf(lists.get(0).get("firstloanproportion")))){
									LoanGrant loanGrant=new LoanGrant();
									loanGrant.setContractCode(contracts[i]);
									loanGrant.setCheckResult(VerityStatus.PASS.getCode());
									loanGrant.setCheckTime(new Date());
									loanGrant.setCheckEmpId(UserUtils.getUser().getId());
									loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
									loanGrant.setLendingTime(new Date());
									// 更新借款状态
									loanInfo.setApplyId(lists.get(0).get("applyid"));
									loanInfo.setLoanCode(lists.get(0).get("loancode"));
									loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT
											.getCode());
									grantAuditService.updateGrantDone(loanInfo,loanGrant);
								}else{
									grantAuditService.updateGrantFlag(contracts[i]);
									loanInfo.setApplyId(lists.get(0).get("applyid"));
									loanInfo.setLoanCode(lists.get(0).get("loancode"));
									historyService.saveLoanStatusHis(loanInfo,"首次放款审核", VerityStatus.PASS.getName(),"");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("首次放款审核发生异常，合同编号为："+contracts[i]
									+"异常信息为："+contracts[i]);
							failedContractCode = contracts[i];
						}
					}else {
						failedContractCode = contracts[i];
					}
					if (StringHelper.isNotEmpty(failedContractCode)) {
						failedCodeList.add(failedContractCode);
						failNum ++;
					}else {
						successNum ++;
					}
				}
			}
		}
		if (ArrayHelper.isNotEmpty(failedCodeList)) {
			AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
			message = ajaxNotify.getMessage();
		}else{
			message ="操作成功"+successNum+"条";
		}
		return message;
	}
	
	/**
	 * 判断是否可以进行尾款放款审核
	 * 2017年1月18日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public String judgeIsSuccess(String contractCode){
		String returnMes = null;
		String dealStatus = grantUrgeBackDao.getDealCount(contractCode);
		if (UrgeCounterofferResult.PAYMENT_SUCCEED.getCode().equals(dealStatus)
				||UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode().equals(dealStatus)) {
			returnMes = null;
		}else {
			returnMes = "deal";
		}
		return returnMes;
	}
	
}
