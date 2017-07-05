package com.creditharmony.loan.borrow.grant.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.loan.type.ChkBackReason;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportTLEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLFirstEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLSecondEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.GrantExportTLService;
import com.creditharmony.loan.borrow.grant.service.GrantExportZJService;
import com.creditharmony.loan.borrow.grant.service.GrantService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.service.GrantUrgeBackService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
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
 * 放款处理
 * @Class Name GrantController
 * @author 朱静越
 * @Create In 2015年12月7日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantDeal")
public class GrantController extends BaseController {
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantDeductsService grantDeductsService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private GrantExportZJService grantExportZJService;
	@Autowired
	private GrantExportTLService grantExportTLService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private GrantService grantService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	/**
	 * 查询放款列表页面数据
	 * 2017年1月20日
	 * By 朱静越
	 * @param model
	 * @param loanFlowQueryParam
	 * @param request
	 * @param response
	 * @param listFlag
	 * @return
	 */
	@RequestMapping(value = "grantItem")
	public String grantDone(Model model, LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request, HttpServletResponse response,
			String listFlag) {
		// 查询金账户列表
		if (LoanModel.TG.getName().equals(listFlag)) {
			loanFlowQueryParam.setModel(LoanModel.TG.getCode());
			loanFlowQueryParam.setTgFlag(YESNO.YES.getCode());
			loanFlowQueryParam.setDealUser(null);
		}else {
			User user = UserUtils.getUser(); // 获取登录人的id
			loanFlowQueryParam.setDealUser(user.getId());
		}
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		Page<LoanFlowWorkItemView> grantPage = grantService.getGrantLists(
				new Page<LoanFlowWorkItemView>(request, response), loanFlowQueryParam);
		Double totalGrantMoney = 0.00;
		Double totalGrantFailMoney = 0.00;
		List<LoanFlowWorkItemView> grantLists = grantPage.getList();
		if (ObjectHelper.isNotEmpty(grantLists)) {
			totalGrantMoney = grantLists.get(0).getTotalGrantMoney();
			totalGrantFailMoney = grantLists.get(0).getTotalGrantFailMoney();
		}
		// 获得汇金产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);		
		// 放款批次的查询，初始化查询下拉列表的
		model.addAttribute("LoanFlowQueryParam", loanFlowQueryParam);
		model.addAttribute("workItems", grantPage);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalGrantFailAmount", totalGrantFailMoney);
		model.addAttribute("totalItem", grantPage.getCount());
		model.addAttribute("listFlag", listFlag);
		model.addAttribute("productList", productList);
		return "borrow/grant/loanflow_grantList";
	}

	/**
	 * 查看退回原因，数据库中存放的是退回原因的code 
	 * 2015年12月25日 By 朱静越
	 * @param contractCode 合同编号
	 * @return 退回原因
	 */
	@RequestMapping(value = "seeBackReason")
	@ResponseBody
	public String seeBackReason(String contractCode) {
		String backReason = grantService.selectBackRea(contractCode);
		for (ChkBackReason s : ChkBackReason.values()) {
			if (s.getCode().equals(backReason)) {
				backReason = s.getName();
			}
		}
		return backReason;
	}

	/**
	 * 放款表导出
	 * 默认导出查询条件下的全部的单子
	 * 有选择的按照选择进行导出 只对网银的单子进行导出 
	 * 2015年12月22日 
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "grantExl")
	public String grantExl(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal,String isBreakCode, RedirectAttributes redirectAttributes) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.debug("放款表导出开始：" + DateUtils.getDate("yyyyMMddHHmmss"));
			ExcelUtils excelutil = new ExcelUtils();
			List<GrantEx> grantList = new ArrayList<GrantEx>();
			BigDecimal totalAmount = new BigDecimal("0.00");
			String[] ids = null;
			User user = UserUtils.getUser(); // 获取登录人的id
			grtQryParam.setDealUser(user.getId());
			if (StringUtils.isEmpty(idVal)) {
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> grantLists = grantService.getGrantLists(grtQryParam);
				if (ArrayHelper.isNotEmpty(grantLists)) {
				ids = new String[grantLists.size()];
					for (int i = 0; i < grantLists.size(); i++) {
						ids[i] = grantLists.get(i).getApplyId();
					}
				}
			} else {
				ids = idVal.split(",");
			}
			if (!ObjectHelper.isEmpty(ids)) {
				map.put("applyIds", ids);
				map.put("dictLoanWay", PaymentWay.NET_BANK.getCode());
				// 进行退回原因的判断
				String grantBackResult =  grantService.isGrantBackMes(map);
				if (StringUtils.isNotEmpty(grantBackResult)) {
					addMessage(redirectAttributes, grantBackResult);
					return "redirect:"
					+ adminPath
					+ "/borrow/grant/grantDeal/grantItem";
				}
				for (int i = 0; i < ids.length; i++) {
					totalAmount = grantService.grantItemDao(grantList, totalAmount, ids, i);
				}
				if(!ObjectHelper.isEmpty(grantList) && grantList.size()>0){
				    GrantEx lastTotal = new GrantEx();
				    lastTotal.setContractVersion("本次打款总额");
				    lastTotal.setFeeUrgedServiceStr("￥"+totalAmount.toString());
		            grantList.add(lastTotal);
				}
			}
			//isBreakCode=0 时表示需要进行拆分，拆分金额为5万元
			if("0".equals(isBreakCode)){
				grantItemSplit(response, excelutil, grantList);
			}else{
				for(GrantEx gr : grantList){
					gr.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank",gr.getBankName()));
					gr.setBankName(changeBankName(gr.getBankName()));
				}
				excelutil.exportExcel(grantList, FileExtension.GRANT_NAME
						+ DateUtils.getDate("yyyyMMddHHmmss"), null, GrantEx.class,
						FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
						response, null);
			}
			logger.info("放款表导出结束：" + DateUtils.getDate("yyyyMMddHHmmss"));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出放款表出错");
			logger.error("导出放款表出错" + DateUtils.getDate("yyyyMMddHHmmss"));
		}
		addMessage(redirectAttributes, "导出放款表出错");
		return "redirect:" + adminPath + "/borrow/grant/grantDeal/grantItem/?repage";
	}

	/**
	 * 放款表导出按5万元金额拆分
	 * 2017年1月20日
	 * By 朱静越
	 * @param response
	 * @param excelutil
	 * @param grantList
	 */
	private void grantItemSplit(HttpServletResponse response,
			ExcelUtils excelutil, List<GrantEx> grantList) {
		List<GrantEx> grantListNew = new ArrayList<GrantEx>();
		for(GrantEx grantEx:grantList){
			if(grantEx.getGrantAmount()!=null&&grantEx.getGrantAmount()!=""){
				String grantAmount=grantEx.getGrantAmount();
				Double grantAmountDou=Double.parseDouble(grantAmount);
				//获取拆分后的条数
				Double grantSize=grantAmountDou/50000;
				int isBreakSize=grantSize.intValue()+1;
				//将一条拆分成多条
				for(int i=0;i<isBreakSize;i++){
					GrantEx grantExNew=new GrantEx();
					BeanUtils.copyProperties(grantEx, grantExNew);
					grantExNew.setGrantAmount("50000.00");
					if(i==(isBreakSize-1)){
						//算出最后一条数据的值
						Double finalGrantAmount=(grantAmountDou-(50000*(isBreakSize-1)));
						BigDecimal bigDecimal = new BigDecimal(finalGrantAmount);
						//保留2位小数
						bigDecimal = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP); 
						grantExNew.setGrantAmount(bigDecimal.toString());
					}
					grantListNew.add(grantExNew);
				}
			}else{
				grantListNew.add(grantEx);
			}
		}
		for(GrantEx gr : grantListNew){
			gr.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank",gr.getBankName()));
			gr.setBankName(changeBankName(gr.getBankName()));
		}
		excelutil.exportExcel(grantListNew, FileExtension.GRANT_NAME
				+ DateUtils.getDate("yyyyMMddHHmmss"), null, GrantEx.class,
				FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
				response, null);
	}

	/**
	 * 银行名称修改
	 * @param bankName
	 * @return
	 */
	public String changeBankName(String bankName){
		if(bankName != null && !"".equals(bankName)){
			if("中国光大银行".equals(bankName)){
				bankName = "光大银行";
			}else if("平安银行股份有限公司".equals(bankName)){
				bankName = "平安银行";
			}else if("中国邮政储蓄银行股份有限公司".equals(bankName)){
				bankName = "中国邮政储蓄银行";
			}
		}
		return bankName;
	}
	
	/**
     * 放款退回,退回到合同审核
     * 2016年02月24日 By 张灏
     * @param batchParam 合同编号
     * @param backBatchReasonCode 退回原因code
     * @param backBatchReason 退回原因
     * @return 操作结果
     */
    @RequestMapping(value = "batchBack")
    @ResponseBody
    public String batchBack(String batchParam, String backBatchReason,String backBatchReasonCode) {
        String[] batchParams = null;
        String failContractCode = null;
        String message = "";
        List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
        if(batchParam!=null){
            batchParams = batchParam.split(";");
            for(String str:batchParams){
            	try {
            		grantService.grantBackDeal(str, backBatchReason, backBatchReasonCode);
            	} catch (Exception e) {
            		e.printStackTrace();
            		logger.error("放款退回失败，发生异常",e);
            		failContractCode = str;
            	}
            	if(StringUtils.isNotEmpty(failContractCode)){
            		failedCodeList.add(failContractCode);
            		failNum++;
            	}else{
            		successNum++;
            	}
            }
        }
        if (ArrayHelper.isNotEmpty(failedCodeList)) {
			AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
			message = notify.getMessage();
		}else{
			message = "放款退回成功";
		}
        return message;
    }

	/**
	 * 线下放款处理，上传回执结果,同时更新催收服务费表
	 * 2016年1月6日 By 朱静越
	 * @param request
	 * @param response
	 * @param file
	 * @param returnCheckFlag 是否校验退款标识   0为不校验   1为校验
	 * @return 要进行跳转的页面
	 */
    @ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			String returnCheckFlag, HttpServletResponse response,
			@RequestParam MultipartFile file,
			RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		String failContractCode = null;
        String message = "";
        List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		List<GrantEx> grantList = new ArrayList<GrantEx>();
		List<?> datalist = excelutil
				.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
						GrantEx.class);
		if (ArrayHelper.isNotEmpty(datalist)) {
			grantList = (List<GrantEx>) datalist;
			// 进行退款标识的校验
			if (YESNO.YES.getCode().equals(returnCheckFlag)) {
				String checkReturn = grantService.returnStatusCheck(datalist, PaymentWay.NET_BANK.getCode());
				if (StringUtils.isNotEmpty(checkReturn)) {
					return "returning";
				}
			}
			// 放款批次的校验
			String pchCheckReturn = grantService.grantPchCheck(datalist, PaymentWay.NET_BANK.getCode());
			if (StringUtils.isNotEmpty(pchCheckReturn)) {
				return "grantPch";
			}
			// 合同编号为空校验
			for (GrantEx grantExItem : grantList) {
				if (StringUtils.isEmpty(grantExItem.getContractCode()) && StringUtils.isNotEmpty(grantExItem.getBankAccount())) {
					return "导入表格的合同编号不能为空";
				}
			}
			// 放款处理
			for (GrantEx grantExItem : grantList) {
				if (StringUtils.isNotEmpty(grantExItem.getContractCode()) && isGrantAudit(grantExItem.getContractCode())) {
					try {
						grantService.offLineGrantDeal(grantExItem);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("线下放款导入有误，出现异常，合同编号为："+grantExItem.getContractCode());
						failContractCode = grantExItem.getContractCode();
					}
					if(StringUtils.isNotEmpty(failContractCode)){
						failedCodeList.add(failContractCode);
						failNum++;
					}else{
						successNum++;
					}
				}
			}
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
				message = notify.getMessage();
			}else{
				message = "导入成功";
			}		
		}else{
			message = "导入的表格不能为空";
		}
		return message;
	}
    
    /**
	 * 线上放款处理,对选中的单子或者如果没有选中的单子进行对接中金/通联平台处理，
	 * 如果没有选中，默认为全部的中金平台的单子，如果有选中的，将选中的单子发送中金平台 要更新单子的企业流水号为合同编号
	 * 2016年1月20日 By 朱静越
	 * @param checkVal 要进行中金处理的单子的合同编号
	 * @param grantWay 中金的线上放款方式
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "onlineGrantDeal")
	public String onlineGrantDeal(String checkVal, String grantBatch,
			String grantWay, LoanFlowQueryParam grtQryParam) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		checkVal = checkVal.replaceFirst(";", "");
		String[] contract = null;
		String rule = "";
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		String message = "";
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		if (PaymentWay.ZHONGJIN.getCode().equals(grantWay)) {
			rule = DeductPlatType.ZJ.getCode() + ":"
					+ DeductType.BATCH.getCode();
			map.put("dictLoanWay", PaymentWay.ZHONGJIN.getCode());
		} else {
			rule = DeductPlatType.TL.getCode() + ":"
					+ DeductType.BATCH.getCode();
			map.put("dictLoanWay", PaymentWay.TONG_LIAN.getCode());
		}
		// 发送全部时筛选符合发送平台的数据。
		if (StringUtils.isEmpty(checkVal)) {
			grtQryParam.setLendingWayCode(grantWay);
			User user = UserUtils.getUser(); // 获取登录人的id
			grtQryParam.setDealUser(user.getId());
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			List<LoanFlowWorkItemView> workItems = grantService.getGrantLists(grtQryParam);
			// 需要进行判断，如果选择中金，则为默认列表中所有为中金单子
			contract = new String[workItems.size()];
			for (int i = 0; i < workItems.size(); i++) {
				contract[i] = workItems.get(i).getContractCode();
			}
		} else {
			// 根据合同编号查询要进行发送的单子的划扣平台
			contract = checkVal.split(";");
		}
		map.put("contractCodes", contract);
		String grantBackResult = grantService.isGrantBackMes(map);
		if (StringUtils.isNotEmpty(grantBackResult)) {
			message = grantBackResult;
			return message;
		}
		
		if (ArrayHelper.isNotNull(contract)) {
			StringBuilder parameter = new StringBuilder();
			for (int i = 0; i < contract.length; i++) {
				parameter.append("'" + contract[i] + "',");
			}
			// 获得要发送到批处理的单子的list
			String sendContract = parameter.toString().substring(0,
					parameter.lastIndexOf(","));
			LoanGrantEx sendLoanGrant = new LoanGrantEx();
			sendLoanGrant.setContractCode(sendContract);
			sendLoanGrant.setRule(rule);
			sendLoanGrant.setDictLoanWay(grantWay);
			sendLoanGrant.setDictLoanStatus(LoanApplyStatus.LOAN_DEALED.getCode());
			List<DeductReq> dealList = loanGrantService.selSendsList(sendLoanGrant);
			if (ArrayHelper.isNotEmpty(dealList)) {
				// 发送批处理(发送线上划扣申请)
				for (DeductReq deductReqItem : dealList) {
					try {
						logger.debug("方法onlineGrantDeal，线上放款发送划扣处理开始，合同编号为："+deductReqItem.getRequestId());
						failContractCode = grantService.onLineGrant(deductReqItem, grantBatch);
						logger.debug("方法onlineGrantDeal，线上放款发送划扣处理结束，合同编号为："+deductReqItem.getRequestId());
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("线上放款发送划扣失败，发生异常",e);
					}
					if (StringUtils.isNotEmpty(failContractCode)) {
						failedCodeList.add(failContractCode);
						failNum++;
					}else{
						successNum++;
					}
				}
				// 提示框处理
				if (ArrayHelper.isNotEmpty(failedCodeList)) {
					AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
					message = ajaxNotify.getMessage();
				}else{
					message = "发送成功"+successNum+"条";
				}
			}else{
				message = "没有符合条件的要发送划扣的数据";
			}
		}
		return message;
	}
    
	
	/**
	 * 放款中金通联导出,默认导出查询条件下的全部的单子，有选择的按照选择进行导出 导出中金、通联的数据
	 * 2015年12月22日 By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "grantExportExl")
	public String grantExport(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal, String flag, RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		Integer size = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		String[] applyIds = null;
		String lendWayCode = PaymentWay.ZHONGJIN.getCode();
		if (!GrantCommon.EXPORT_ZJ.equals(flag)) {
			lendWayCode = PaymentWay.TONG_LIAN.getCode();
		} 
		try {
			if (StringUtils.isEmpty(idVal)) {
				grtQryParam.setLendingWayCode(lendWayCode);
				User user = UserUtils.getUser(); // 获取登录人的id
				grtQryParam.setDealUser(user.getId());
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> lst = grantService.getGrantLists(grtQryParam);
				if (ArrayHelper.isNotEmpty(lst)) {
					applyIds = new String[lst.size()];
					for (int i = 0; i < lst.size(); i++) {
						applyIds[i] = lst.get(i).getApplyId();
					}
				}
				param.put("applyIds", applyIds);
			} else {
				applyIds = idVal.split(";");
				param.put("applyIds", applyIds);
			}
			param.put("certType", LoanDictType.CERTIFICATE_TYPE);
			param.put("openBank", LoanDictType.OPEN_BANK);
			// 中金导出 更新放款表
			if (GrantCommon.EXPORT_ZJ.equals(flag)) {
				List<GrantExportZJEx> grantExportList = new ArrayList<GrantExportZJEx>();
				if (!ObjectHelper.isEmpty(applyIds)) {
					param.put("lendingWayCode",PaymentWay.ZHONGJIN.getCode());
					param.put("dictLoanWay", PaymentWay.ZHONGJIN.getCode());
					String grantBackResult = grantService.isGrantBackMes(param);
					if (StringUtils.isNotEmpty(grantBackResult)) {
						addMessage(redirectAttributes, grantBackResult);
						return "redirect:"
						+ adminPath
						+ "/borrow/grant/grantDeal/grantItem";
					}
					grantExportList = grantExportZJService.findGrantInfo(param);
					LoanGrant loanGrant = new LoanGrant();
					if (!ObjectHelper.isEmpty(grantExportList)) {
						for (GrantExportZJEx cur : grantExportList) {
							cur.setIdentityCode(""); // 证件号码设置为空
							cur.setAccountType(GrantCommon.ACCOUNT_TYPE_PERSON);
							cur.setRemark("放款_"+cur.getContractCode() + "_"
									+ System.currentTimeMillis());
							loanGrant.setContractCode(cur.getContractCode());
							loanGrant.setEnterpriseSerialno(cur.getRemark());
							loanGrantService.updateLoanGrant(loanGrant);
						}
					}
				}
				excelutil.exportExcel(grantExportList,
						threePartFileName.getZjDfExportFileName(), null,
						GrantExportZJEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, null);
				return null;
			} else if (GrantCommon.EXPORT_TL.equals(flag)) {
				Map<String, Object> getMap = new HashMap<String, Object>();
				List<GrantExportTLEx> grantExportList = new ArrayList<GrantExportTLEx>();
				BigDecimal amountTotal = BigDecimal.ZERO;
				if (!ObjectHelper.isEmpty(applyIds)) {
					param.put("lendingWayCode",PaymentWay.TONG_LIAN.getCode());
					param.put("dictLoanWay", PaymentWay.TONG_LIAN.getCode());
					String grantBackResult = grantService.isGrantBackMes(param);
					if (StringUtils.isNotEmpty(grantBackResult)) {
						addMessage(redirectAttributes, grantBackResult);
						return "redirect:"
						+ adminPath
						+ "/borrow/grant/grantDeal/grantItem";
					}
					getMap = grantExportTLService.getSplitTl(param);
					size = (Integer)getMap.get("size");
					amountTotal = (BigDecimal)getMap.get("amountTotal");
					grantExportList = (List<GrantExportTLEx>)getMap.get("grantExportTLExs");
				}
				String[] header = { "F", ThreePartFileName.tlBusinessCode,
						DateUtils.getDate("yyyyMMdd"),
						String.valueOf(size),
						amountTotal.setScale(0).toString(), "09900" };
				excelutil.exportExcel(grantExportList, header,
						GrantExportTLEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE,
						threePartFileName.getTlDfExportFileName(), response,
						null);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法grantExport,导出放款表出错");
			addMessage(redirectAttributes, "导出放款表出错"+e.getMessage());
		} 
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantDeal/grantItem";
	}

	/**
	 *通联、中金模板导入
	 *@author zhanghao
	 *@Create In 2016年2月29日
	 *@param lendingWay 放款途径
	 *@param templateType 模板类型
	 *@param file 上传的文件
	 *@return String 
	 * 
	 */
    @RequestMapping("importGrantAudit")
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
                + "/borrow/grant/grantDeal/grantItem";
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
            + "/borrow/grant/grantDeal/grantItem";
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
	        LoanGrantEx objLoanGrantEx = loanGrantService.findGrant(loanGrant);
	        BigDecimal grantFailAmount = objLoanGrantEx.getGrantFailAmount();
	        loanGrant.setGrantFailAmount(grantFailAmount);
	        String sumStr = grantZJ.getSum();
	        if(StringUtils.isEmpty(sumStr)){
	            addMessage(redirectAttributes, "中金导入，数据行第"+(i+1)+"行交易金额为空");	
	            return "redirect:"
	            + adminPath
	            + "/borrow/grant/grantDeal/grantItem";
	        }
	        sumStr = sumStr.replace(",", "");
	        Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
	        Matcher m = p.matcher(sumStr);
	        if(!m.matches()){
	            addMessage(redirectAttributes, "中金导入，数据行第"+(i+1)+"行交易金额有误");
	            return "redirect:"
	            + adminPath
	            + "/borrow/grant/grantDeal/grantItem";
	        }
	        BigDecimal sum = new BigDecimal(sumStr);
	        String strDate = grantZJ.getBankPayTime();
	        String tradeStatus = grantZJ.getTradeStatus();
	        if(StringUtils.isNotEmpty(strDate)){
	            Date finishDate = DateUtils.parseDate(strDate);
	            loanGrant.setLendingTime(finishDate);
	        }
	        String strLoanCode = null;
	        if (tradeStatus.indexOf("成功") > -1 ||tradeStatus.equals("30")) {
	        	 loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
	        	 // 如果失败金额不为0时，更新失败金额为：失败金额-交易成功的金额
	        	 if (grantFailAmount.compareTo(BigDecimal.ZERO)!=0) {
					loanGrant.setGrantFailAmount(grantFailAmount.subtract(sum));
				}
	        	 strLoanCode = objLoanGrantEx.getLoanCode();
			}else if(tradeStatus.indexOf("正在处理") > -1 ||tradeStatus.equals("20")){
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
			}else{
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				loanGrant.setGrantFailResult(grantZJ.getBankResponseMsg());
				loanGrant.setGrantFailAmount(sum);
			}
	        loanGrant.setGrantBatch(grantZJ.getGrantBatchCode());
	        try {
	        	grantService.updZJExcel(loanGrant, strLoanCode);
	        	addMessage(redirectAttributes, "中金导入成功");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("中金导入出现异常",e);
				addMessage(redirectAttributes, "导入异常"+e.getMessage());
			}
		}
		return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
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
            + "/borrow/grant/grantDeal/grantItem";
		}
		for(int i = 0 ;i< datalist.size();i++){
            grantTLFirst = (GrantImportTLFirstEx) datalist.get(i);
            remark = grantTLFirst.getRemark();
            if(StringUtils.isEmpty(remark)){
            	addMessage(redirectAttributes, "通联导入，数据行第"+(i+1)+"行备注为空");
            	return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
            }
            String tradeAmountStr = grantTLFirst.getTradeAmount();
            if(StringUtils.isEmpty(tradeAmountStr)){
                addMessage(redirectAttributes, "通联导入，数据行第"+(i+1)+"行交易金额为空");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
            }
            tradeAmountStr = tradeAmountStr.replace(",", "");
            Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
            Matcher m = p.matcher(tradeAmountStr);
            if(!m.matches()){
                addMessage(redirectAttributes,"通联导入，数据行第"+(i+1)+"行交易金额有误");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
            }
		}
		// 遍历所有，进行分类
		try {
			grantService.getInfo(datalist);
			addMessage(redirectAttributes,"通联导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通联导入失败",e);
			addMessage(redirectAttributes,"通联导入失败"+e.getMessage());
			
		}
		return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
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
            + "/borrow/grant/grantDeal/grantItem";
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
                + "/borrow/grant/grantDeal/grantItem";
            }
            grantAmount = grantAmount.replace(",", "");
            Pattern p = Pattern.compile(GrantCommon.PATTERN_STRING);
            Matcher m = p.matcher(grantAmount);
            if(!m.matches()){
                addMessage(redirectAttributes,"通联导入，数据行第"+(i+1)+"行金额有误");
                return "redirect:"
                + adminPath
                + "/borrow/grant/grantDeal/grantItem";
            }
		}
		try {
			grantService.getInfoTL2(datalist);
			addMessage(redirectAttributes,"通联导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通联导入失败",e);
			addMessage(redirectAttributes,"通联导入异常"+e.getMessage());
		}
		return "redirect:"
        + adminPath
        + "/borrow/grant/grantDeal/grantItem";
	}
	
	/**
	 * 根据合同编号判断借款状态
	 * 2016年12月20日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public boolean isGrantAudit(String contractCode){
		Map<String, String> param = new HashMap<String, String>();
		param.put("contractCode", contractCode);
		String dictLoanStatus = loanGrantService.findLoanLinkedContract(param).getDictLoanStatus();
		if (LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(dictLoanStatus)) {
			return false;
		}else {
			return true;
		}
	}
}
