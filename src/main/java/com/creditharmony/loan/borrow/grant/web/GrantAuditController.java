package com.creditharmony.loan.borrow.grant.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditTGEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLFirstEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLSecondEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.ParamEx;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.borrow.grant.service.GrantService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantHisService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.google.common.collect.Lists;

/**
 * 放款审核进行处理
 * @Class Name GrantAuditController
 * @author 朱静越
 * @Create In 2015年12月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantAudit")
public class GrantAuditController extends BaseController {
	
	@Autowired
	private GrantAuditService grantAuditService;
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
	private LoanPrdMngService loanPrdMngService;
	
	
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
			HttpServletRequest request, HttpServletResponse response,
			String listFlag) {
		// 查询金账户列表
		if (LoanModel.TG.getName().equals(listFlag)) {
			loanFlowQueryParam.setModel(LoanModel.TG.getCode());
			loanFlowQueryParam.setTgFlag(YESNO.YES.getCode());
		}
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		Page<GrantAuditEx> grantAuditPage = grantAuditService.getGrantAuditList(
				new Page<GrantAuditEx>(request, response), loanFlowQueryParam);
		Double totalGrantMoney = 0.00;
		if (ObjectHelper.isNotEmpty(grantAuditPage.getList())) {
			totalGrantMoney = grantAuditPage.getList().get(0).getTotalGrantMoney();
		}
		// 放款批次的查询，初始化查询下拉列表的
		List<LoanGrant> grantPchList = loanGrantService.selGrantPch();
		List<CityInfo> provinceList = cityInfoService.findProvince();
		// 获得汇金产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("LoanFlowQueryParam", loanFlowQueryParam);
		model.addAttribute("workItems", grantAuditPage);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalItem", grantAuditPage.getCount());
		model.addAttribute("listFlag", listFlag);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("grantPchList", grantPchList);
		return "borrow/grant/loanflow_grantAuditList";
	}

	/**
	 * 放款审核跳转,单个或者批量操作都可以进行 
	 * 批量操作，checkVal是否为空，在前台有进行控制，所以在页面中不需要进行判断
	 * 2015年12月29日 By 朱静越
	 * @param model 中存放要在页面中显示的list
	 * @param listFlag 区分TG和非TG的标识
	 * @param checkVal applyId，根据applyId进行处理
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "grantAuditJump")
	public String granAuditJump(Model model, String checkVal,String listFlag){
		LoanFlowQueryParam loanFlowQueryParam = new LoanFlowQueryParam();
		// 根据applyId查询审核页面中要显示的字段 
		String[] applyIds =  checkVal.split(",");
		loanFlowQueryParam.setApplyIds(applyIds);
		if (LoanModel.TG.getName().equals(listFlag)) {
			loanFlowQueryParam.setModel(LoanModel.TG.getCode());
			loanFlowQueryParam.setTgFlag(YESNO.YES.getCode());
		}
		List<GrantAuditEx> grantAuditList = grantAuditService.getGrantAuditList(loanFlowQueryParam);
		//控制拆分数据不能退回
		StringBuffer messageBuffer = new StringBuffer();
		String isExistSplit="";
		messageBuffer.append(GrantCommon.ISSPLIT_DATA_MESSAGE);
		for(GrantAuditEx ex:grantAuditList){
			if(!"0".equals(ex.getIssplit())){
				messageBuffer.append(ex.getCustomerName()+ " " + ex.getContractCode() + ",");
				isExistSplit="1";
			}
			
		}
		String message = messageBuffer.toString();
		message = message.substring(0, message.length()-1);
		model.addAttribute("messageBuffer", messageBuffer);
		model.addAttribute("isExistSplit", isExistSplit);
		model.addAttribute("listFlag", listFlag);
		model.addAttribute("list", grantAuditList);
		return "borrow/grant/loanflow_grantAudit_approve_0";
	}

	/**
	 * 审核退回处理,退回节点，更新表数据，审核结果，审核专员，退回原因,将放款的失败原因置为空
	 * 2015年12月11日 By 朱静越 修改单子的状态，修改放款记录表
	 * @param apply 流程属性
	 * @param result 审核退回原因
	 * @param responseURL 退回的节点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditBack")
	public String granAuditBack(ParamEx param, String result,String listFlag) {
		List<DistachParamEx> list = param.getList();
		int deductNum = 0;
		int checkNum = 0;
		int successNum = 0;
		int failNum = 0;
		String message = "";
		AjaxNotify ajaxNotify = new AjaxNotify();
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		String getMessage = null;
		for (DistachParamEx distachParamItem : list) {
			try {
				getMessage = grantAuditService.backDeal(distachParamItem, result,listFlag);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("放款审核退回发生异常",e);
				failedContractCode = distachParamItem.getContractCode();
			}
			if (StringUtils.isNotEmpty(failedContractCode)) {
				failedCodeList.add(failedContractCode);
				failNum ++;
			} else {
				if(StringUtils.isEmpty(getMessage)){
					successNum++;
				}else if("deduct".equals(getMessage)){
					deductNum++;
				}else{
					checkNum++;
				}
			}
		}
		if (list.size() == 1) { //单笔审核
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				message = "退回失败";
			}else if(deductNum > 0){
				message = "该数据正在划扣中，不能进行放款审核退回";
			}else if(checkNum > 0){
				message = "该数据正在查账中，不能进行放款审核退回";
			}else{
				message = "退回成功";
			}
		
		}else {    //多笔审核
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
				message = ajaxNotify.getMessage();
			}else if(deductNum >0){
				message += "存在" + deductNum + "条正在划扣";
			}else if(checkNum >0){
				message += "存在" + checkNum + "条正在查账";
			}else{
				message += "退回成功";
			}
		}
		return message;
	}
	
	/**
	 * 放款审核通过，更新放款时间，放款状态，审核结果， 
	 * 2015年12月11日 By 朱静越 修改单子状态，修改放款记录表
	 * @param apply applyId
	 * @param result 放款时间
	 * @return 处理结果
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditOver")
	public String granAuditOver(ParamEx param, Date result,String listFlag) {
		String message = "";
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<DistachParamEx> list = param.getList();
		List<String> failedCodeList = Lists.newArrayList();
		for (DistachParamEx distach : list) {
			try {
				grantAuditService.grantAuditDeal(distach, result,listFlag);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("放款审核失败，发生异常",e);
				failContractCode = distach.getContractCode();
			}
			if (StringUtils.isNotEmpty(failContractCode)) {
				failedCodeList.add(failContractCode);
				failNum++;
			}else{
				successNum++;
			}
		}
		
		if (list.size() == 1) { //单笔审核
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				message = "放款审核失败";
			}else{
				message = "放款审核成功";
			}
		}else {  //多笔审核
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
				message = ajaxNotify.getMessage();
			}else{
				message ="放款审核成功";
			}
		}
		return message;
	}
	
	/**
	 * 导出放款审核列表，包括资金托管和信借
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
		List<GrantAuditEx> auditList = new ArrayList<GrantAuditEx>();
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		if (StringUtils.isNotEmpty(idVal)) {
			String[] applyIds = idVal.split(","); // 如果有选中的单子,将选中的单子导出
			grtQryParam.setApplyIds(applyIds);
		} 
		try {
			auditList = grantAuditService.getGrantAuditList(grtQryParam);
			if (LoanModel.TG.getName().equals(listFlag)) {
				grantAuditTGDao(response, excelutil, auditList);
			}else {
				excelutil.exportExcel(auditList,
						FileExtension.GRANT_AUDIT + DateUtils.getDate("yyyyMMdd"),
						GrantAuditEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, 1);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "放款审核表导出失败，请重试");
		}
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantAudit/grantAuditItem?listFlag="+listFlag;
	}

	/**
	 * 资金托管放款审核导出
	 * 2017年1月19日
	 * By 朱静越
	 * @param response
	 * @param excelutil
	 * @param auditList 正常的查询
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void grantAuditTGDao(HttpServletResponse response,
			ExcelUtils excelutil, List<GrantAuditEx> auditList)
			throws IllegalAccessException, InvocationTargetException {
		List<GrantAuditTGEx> grantAuditTGList;
		grantAuditTGList = new ArrayList<GrantAuditTGEx>();
		for (int i = 0; i < auditList.size(); i++) {
			BeanUtils.copyProperties(auditList.get(i), grantAuditTGList.get(i));
			grantAuditTGList.get(i).setLendingAccount("资金托管放款");
		}
		excelutil.exportExcel(auditList,
				FileExtension.GRANT_AUDIT + DateUtils.getDate("yyyyMMdd"),
				GrantAuditEx.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, 1);
	}
	
	/**
	 * 检索需要修改银行卡号的信息，并显示在待修改放款行界面 
	 * 2016年3月4日 By 朱静越
	 * @param idVal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updBankJump")
	public String updBankJump(String idVal, Model model){
		LoanFlowQueryParam loanFlowQueryParam = new LoanFlowQueryParam();
		String[] applyIds =  idVal.split(","); // 根据applyId查询审核页面中要显示的字段 
		loanFlowQueryParam.setApplyIds(applyIds);
		List<GrantAuditEx> grantAuditList = grantAuditService.getGrantAuditList(loanFlowQueryParam);	
		model.addAttribute("list", grantAuditList);
		model.addAttribute("flag", "grantAudit");
		return "borrow/grant/loanflow_disCard_approve_0";
	}

	/**
	 * 点击选择账户的时候，弹出中间人，进行中间人的选择 
	 * 2016年2月28日 By 朱静越
	 * @param model
	 * @param request
	 * @param response
	 * @param midPerson
	 * @return
	 */
	@RequestMapping(value = "selectMiddle")
	public String selectMiddle(Model model, HttpServletRequest request,
			HttpServletResponse response, MiddlePerson midPerson) {
		Page<MiddlePerson> middlePage = middlePersonService.selectAllMiddle(
				new Page<MiddlePerson>(request, response), midPerson);
		if (midPerson != null) {
			model.addAttribute("midPerson", midPerson);
		}
		model.addAttribute("middlePage", middlePage);
		model.addAttribute("sureFlag", "grantAudit");
		return "borrow/grant/disCardMiddle";
	}

	/**
	 * 放款审核开户行提交 
	 * 2016年3月4日 By 朱静越
	 * @param model
	 * @param middleId
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updBankCommit")
	public String updBankCommit(Model model, String middleId, ParamEx param,String deftokenId,String deftoken) {
		List<DistachParamEx> list = param.getList();
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		String message = "";
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		for (DistachParamEx distachParamItem : list) {
			try {
				grantAuditService.updBankDeal(distachParamItem, middleId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("放款审核修改放款银行失败，发生异常",e);
				failContractCode = distachParamItem.getContractCode();
			}
			if (StringUtils.isNotEmpty(failContractCode)) {
				failedCodeList.add(failContractCode);
				failNum++;
			}else{
				successNum++;
			}
		}
		if (list.size() == 1) { // 单笔修改
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				message = "修改失败";
			}else{
				message = "修改成功";
			}
		}else {  // 多笔修改
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
				message = ajaxNotify.getMessage();
			}else{
				message ="修改成功";
			}
		}	
		return message;
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
	 * 放款审核导入处理
	 * 2016年5月16日
	 * By 朱静越
	 * @param lendingWay
	 * @param templateType
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "importGrantAudit")
	public String importGrantAudit(String lendingWay,String templateType,MultipartFile file,RedirectAttributes redirectAttributes){
	    ExcelUtils excelutil = new ExcelUtils();
        List<?> datalist = null;
	    if(PaymentWay.ZHONGJIN.getCode().equals(lendingWay)){
	        datalist = excelutil
                    .importExcel(LoanFileUtils.multipartFile2File(file), 1, 0,
                            GrantImportZJEx.class);
	        importZJ(datalist, redirectAttributes);
	    }else if(PaymentWay.TONG_LIAN.getCode().equals(lendingWay)){
	       if(GrantCommon.TL_FIRST.equals(templateType)){
	           datalist = excelutil
	                    .importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
	                            GrantImportTLFirstEx.class);  
	        importTLFirst(datalist,redirectAttributes); 
	       }else if(GrantCommon.TL_SECOND.equals(templateType)){
	           datalist = excelutil
                       .importExcel(LoanFileUtils.multipartFile2File(file), 1, 0,
                               GrantImportTLSecondEx.class); 
	         importTLSecond(datalist, redirectAttributes); 
	       }
	    }
	    return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
	
	}
	
	/**
	 * 导入中金处理
	 * 2016年5月11日
	 * By 朱静越
	 * @param datalist
	 * @param redirectAttributes
	 * @return
	 */
	public String importZJ(List<?> datalist,RedirectAttributes redirectAttributes){
		GrantImportZJEx grantZJ = new GrantImportZJEx();
		LoanGrant loanGrant = new LoanGrant();
		String contractCode = null;
		String remark = null;
		// 放款批次的校验
		String pchCheckReturn = grantService.grantPchCheck(datalist, PaymentWay.ZHONGJIN.getCode());
		if (StringUtils.isNotEmpty(pchCheckReturn)) {
			addMessage(redirectAttributes, pchCheckReturn);
			return "redirect:"
            + adminPath
            + "/borrow/grant/grantAudit/grantAuditItem";
		}		
		for(int i = 0 ;i< datalist.size();i++){
			grantZJ = (GrantImportZJEx) datalist.get(i);
	        remark = grantZJ.getRemark();
	        if(StringUtils.isEmpty(remark)){
	            break;
	        }
	        loanGrant.setEnterpriseSerialno(remark);
	        contractCode = remark.split("_")[1];
	        loanGrant.setContractCode(contractCode);
	        BigDecimal grantFailAmount = loanGrantService.findGrant(loanGrant).getGrantFailAmount();
	        loanGrant.setGrantFailAmount(grantFailAmount);
	        String sumStr = grantZJ.getSum();
	        if(StringUtils.isEmpty(sumStr)){
	            addMessage(redirectAttributes, "中金导入，数据行第"+(i+1)+"行交易金额为空");	
	            return "redirect:"
	            + adminPath
	            + "/borrow/grant/grantAudit/grantAuditItem";
	        }
	        sumStr = sumStr.replace(",", "");
	        Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
	        Matcher m = p.matcher(sumStr);
	        if(!m.matches()){
	            addMessage(redirectAttributes, "中金导入，数据行第"+(i+1)+"行交易金额有误");
	            return "redirect:"
	            + adminPath
	            + "/borrow/grant/grantAudit/grantAuditItem";
	        }
	        BigDecimal sum = new BigDecimal(sumStr);
	        String strDate = grantZJ.getBankPayTime();
	        String tradeStatus = grantZJ.getTradeStatus();
	        if(StringUtils.isNotEmpty(strDate)){
	            Date finishDate = DateUtils.parseDate(strDate);
	            loanGrant.setLendingTime(finishDate);
	        }
	        if (tradeStatus.indexOf("成功") > -1 ||tradeStatus.equals("30")) {
	        	 loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
	        	 // 如果失败金额不为0时，更新失败金额为：失败金额-交易成功的金额
	        	 if (grantFailAmount.compareTo(BigDecimal.ZERO)!=0) {
					loanGrant.setGrantFailAmount(grantFailAmount.subtract(sum));
				}
			}else if(tradeStatus.indexOf("正在处理") > -1 ||tradeStatus.equals("20")){
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
			}else{
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				loanGrant.setGrantFailResult(grantZJ.getBankResponseMsg());
				loanGrant.setGrantFailAmount(sum);
			}
	        loanGrant.setGrantBatch(grantZJ.getGrantBatchCode());
	        try {
	        	loanGrantService.updateLoanGrant(loanGrant);
	        	addMessage(redirectAttributes, "中金导入成功");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("中金导入出现异常",e);
				addMessage(redirectAttributes, "导入异常"+e.getMessage());
			}
		}
		return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
	}
	
	/**
	 * 导入通联1处理，通联的数据进行拆分，导出时候直接进行拆分
	 * 2016年5月11日
	 * By 朱静越
	 * @param datalist
	 * @param redirectAttributes
	 * @return
	 */
	public String importTLFirst(List<?> datalist,RedirectAttributes redirectAttributes){
		GrantImportTLFirstEx grantTLFirst = new GrantImportTLFirstEx();
		String remark = null;
		// 放款批次的校验
		String pchCheckReturn = loanGrantService.grantPchCheckTL(datalist, GrantCommon.TL_FIRST);
		if (StringUtils.isNotEmpty(pchCheckReturn)) {
			addMessage(redirectAttributes, pchCheckReturn);
			return "redirect:"
            + adminPath
            + "/borrow/grant/grantAudit/grantAuditItem";
		}
		for(int i = 0 ;i< datalist.size();i++){
            grantTLFirst = (GrantImportTLFirstEx) datalist.get(i);
            remark = grantTLFirst.getRemark();
            if(StringUtils.isEmpty(remark)){
            	addMessage(redirectAttributes, "通联导入，数据行第"+(i+1)+"行备注为空");
            	return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
            }
            String tradeAmountStr = grantTLFirst.getTradeAmount();
            if(StringUtils.isEmpty(tradeAmountStr)){
                addMessage(redirectAttributes, "通联导入，数据行第"+(i+1)+"行交易金额为空");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
            }
            tradeAmountStr = tradeAmountStr.replace(",", "");
            Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
            Matcher m = p.matcher(tradeAmountStr);
            if(!m.matches()){
                addMessage(redirectAttributes,"通联导入，数据行第"+(i+1)+"行交易金额有误");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
            }
		}
		// 遍历所有，进行分类
		try {
			grantAuditService.getInfo(datalist);
			addMessage(redirectAttributes,"通联导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通联导入失败",e);
			addMessage(redirectAttributes,"通联导入失败"+e.getMessage());
			
		}
		return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
	}
	
	/**
	 * 通联模板2的导入处理
	 * 2016年5月12日
	 * By 朱静越
	 * @param datalist
	 * @param redirectAttributes
	 * @return
	 */
	public String importTLSecond(List<?> datalist,RedirectAttributes redirectAttributes){
		String remark = null;
		// 放款批次的校验
		String pchCheckReturn = loanGrantService.grantPchCheckTL(datalist, GrantCommon.TL_SECOND);
		if (StringUtils.isNotEmpty(pchCheckReturn)) {
			addMessage(redirectAttributes, pchCheckReturn);
			return "redirect:"
            + adminPath
            + "/borrow/grant/grantAudit/grantAuditItem";
		}		
		for(int i = 0 ;i< datalist.size();i++){
			GrantImportTLSecondEx grantTLSecond = (GrantImportTLSecondEx) datalist.get(i);
            remark = grantTLSecond.getRemark();
            if(StringUtils.isEmpty(remark)){
                break;
            }
            String grantAmount = grantTLSecond.getGrantAmount();
            if(StringUtils.isEmpty(grantAmount)){
                addMessage(redirectAttributes,"通联导入，数据行第"+(i+1)+"行金额为空");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
            }
            grantAmount = grantAmount.replace(",", "");
            Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
            Matcher m = p.matcher(grantAmount);
            if(!m.matches()){
                addMessage(redirectAttributes,"通联导入，数据行第"+(i+1)+"行金额有误");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantAudit/grantAuditItem";
            }
		}
		try {
			grantAuditService.getInfoTL2(datalist);
			addMessage(redirectAttributes,"通联导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通联导入失败",e);
			addMessage(redirectAttributes,"通联导入异常"+e.getMessage());
		}
				
		return "redirect:"
        + adminPath
        + "borrow/grant/grantAudit/grantAuditItem";
	}
	
}
