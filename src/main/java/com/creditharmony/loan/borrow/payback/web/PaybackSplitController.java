package com.creditharmony.loan.borrow.payback.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.common.type.UseFlag;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.entity.PlatformBankEntity;
import com.creditharmony.core.deduct.service.PlatformBankService;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.excel.util.ExportExcel;
import com.creditharmony.core.fortune.type.FuYouAccountBackState;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TrustmentStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackTrustEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.borrow.payback.facade.CenterDeductBackFacade;
import com.creditharmony.loan.borrow.payback.facade.DeductPaybackFacade;
import com.creditharmony.loan.borrow.payback.facade.FuYouDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.HaoYiLianDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.HaoYiLianFacade;
import com.creditharmony.loan.borrow.payback.facade.PaybackSplitOneKeyFacade;
import com.creditharmony.loan.borrow.payback.facade.PaybackSplitOnlineFacade;
import com.creditharmony.loan.borrow.payback.facade.TongLianDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.TongLianFacade;
import com.creditharmony.loan.borrow.payback.facade.ZhongJinDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.ZhongJinFacade;
import com.creditharmony.loan.borrow.payback.service.BankPlantPortService;
import com.creditharmony.loan.borrow.payback.service.CenterDeductService;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
import com.creditharmony.loan.borrow.payback.service.PlatformGotoRuleManager;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.service.SystemSetMaterService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ExportHelper;
import com.creditharmony.loan.common.utils.FilterHelper;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.sync.data.remote.FyMoneyAccountService;

/**
 * 集中拆分还款控制器
 * 
 * @Class Name PaybackSplitController
 * @author zhaojinping
 * @Create In 2015年12月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/payBackSplitapply")
public class PaybackSplitController extends BaseController {

	@Autowired
	private PaybackSplitService paybackSplitService;

	@Autowired
	private PaymentSplitService paymentSplitService;

	@Autowired
	private CenterDeductService centerDeductService;

	@Autowired
	private DeductPaybackService deductPaybackService;

	@Autowired
	private DeductUpdateService deductUpdateService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private FyMoneyAccountService fyMoneyAccountService;
	
	@Autowired
	private ThreePartFileName threePartFileName;
	
	@Autowired
	private RepaymentDateService dateService;
	
	@Autowired
	private PlatformBankService platformBankService;
	
	@Autowired
	private DeductPaybackFacade deductPaybackFacade;
	@Autowired
	private HaoYiLianFacade haoYiLianFacade;
	@Autowired
	private TongLianFacade tongLianFacade;
	@Autowired
	private ZhongJinFacade zhongJinFacade;
	@Autowired
	private FuYouDeductSplitFacade fuYouDeductSplitFacade;
	@Autowired
	private HaoYiLianDeductSplitFacade haoYiLianDeductSplitFacade;
	@Autowired
	private ZhongJinDeductSplitFacade zhongJinDeductSplitFacade;
	@Autowired
	private TongLianDeductSplitFacade tongLianDeductSplitFacade;
	@Autowired
	private SystemSetMaterService systemSetMaterService;
	
	@Autowired
	private PaybackSplitOneKeyFacade paybackSplitFacade;
	@Autowired
	private PaybackSplitOnlineFacade paybackSplitOnlineFacade;
	@Autowired
	private BankPlantPortService bankPlantPortService;
	@Autowired
	private PlatformGotoRuleManager platformGotoRuleManager;
	@Autowired
	private CenterDeductBackFacade  centerDeductBackFacade;

	/**
	 * 获取集中划扣列表数据 2015年12月11日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @return 跳转路径
	 */
	@RequestMapping(value = "paybackSplitListApply")
	public String getSplitList(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		prepareSearchCondition(paramMap);
		String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		
		Page<PaybackSplit> paybackSplitPage = paybackSplitService.getDeductPage(paramMap,request,response);
		NumTotal numTotal = new  NumTotal();
		List<PaybackSplit> paybacklist = paybackSplitPage.getList();
		if(paybacklist.size()>0){
		    PaybackSplit  split  =  paybacklist.get(0);
			numTotal.setNum(String.valueOf(paybackSplitPage.getCount()));
			numTotal.setTotal(split.getSumAmont()== null ? "0" : split.getSumAmont().toString());
			numTotal.setReallyTotal(split.getSumReallyAmont()== null ? "0" : split.getSumReallyAmont().toString());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("page", paybackSplitPage);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("message", "");
		model.addAttribute("numTotal", numTotal);
		logger.debug("invoke ApplyPayLaunchController method: findContractData, consult.id is: "
				+ paybackSplitPage);
		return "borrow/payback/centerdeduct/paybackSplit";
	}

	/**
	 * 封装查询参数 2016年3月13日 By 王浩
	 * 
	 * @param paramMap
	 */
	private void prepareSearchCondition(Map<String, Object> paramMap) {
		paramMap.put("dictOptType", OperateType.COLLECT_DEDUCT.getCode());
		paramMap.put("dictPaybackStatus",
				RepayApplyStatus.PRE_PAYMENT.getCode());
		String idVal = null;
		if (StringUtils.isNotEmpty((String)paramMap.get("id"))){
			idVal = paramMap.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			paramMap.put("ids", Arrays.asList(idVal.split(",")));
		}
		String stores = (String) paramMap.get("stores");
		String bank = (String) paramMap.get("bank");
		String dayName = (String) paramMap.get("dayName");
		String dictDealType = (String) paramMap.get("dictDealTypeId");
		if (!ObjectHelper.isEmpty(stores)) {
			paramMap.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			paramMap.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		if (!ObjectHelper.isEmpty(dayName)) {
			paramMap.put("monthPayDay", Arrays.asList(dayName.split(",")));
		}
		if (!ObjectHelper.isEmpty(dictDealType)) {
			paramMap.put("dictDealType", Arrays.asList(dictDealType.split(",")));
		}
		String queryRule = (String) paramMap.get("queryRule");
		String overdueDays = (String) paramMap.get("overdueDays");
		
		if(!ObjectHelper.isEmpty(queryRule)){
			if(!ObjectHelper.isEmpty(overdueDays)){
				String queryoverdueDaysString;
				if(queryRule.equals("0")){
					 queryoverdueDaysString = "< cast("+overdueDays+" as numeric)";
				}else {
					 queryoverdueDaysString = ">= cast("+overdueDays+" as numeric)";
				}
				paramMap.put("queryoverdueDaysString", queryoverdueDaysString);
			}
		}
		String qfailReason = (String) paramMap.get("qfailReason");
		if(!ObjectHelper.isEmpty(qfailReason)){
			if(qfailReason.equals("0")){
				qfailReason = "((COALESCE(pa.fail_Reason,'') like '%不足%')  "
						+ "    or (COALESCE(pa.fail_Reason,'') like '%没有足够可用资金%')"
						+ "    or (COALESCE(pa.fail_Reason,'') like '%交易透支%')"
						+ "    or (COALESCE(pa.fail_Reason,'') like '%金额不能为零%')"
						+ "    or (COALESCE(pa.fail_Reason,'') like '%不够支付%'))";
			}else{
				qfailReason = "((COALESCE(pa.fail_Reason,'') not like '%不足%')  "
						+ "    and (COALESCE(pa.fail_Reason,'') not like '%没有足够可用资金%')"
						+ "    and (COALESCE(pa.fail_Reason,'') not like '%交易透支%')"
						+ "    and (COALESCE(pa.fail_Reason,'') not like '%金额不能为零%')"
						+ "    and (COALESCE(pa.fail_Reason,'') not like '%不够支付%'))";
			}
			paramMap.put("failReason", qfailReason);
		}
		
	}

	/**
	 * 查询历史数据 2015年12月11日 By wengsi()
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "getHirstory")
	public String getHirstory(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<PaybackOpe> listOpe = null;
		String applyId = request.getParameter("applyId");
		if (applyId != null && !(("").equals(applyId))) {
			String mainId = paybackSplitService.getMainId(applyId);
			listOpe = paybackSplitService.getAllHirstory(new Page<PaybackOpe>(
					request, response), mainId);
			model.addAttribute("listOpe", listOpe);
			model.addAttribute("applyId", applyId);
		}
		return "borrow/payback/centerdeduct/centerDeductHirstory";
	}

	/**
	 * 批量退回 2015年12月11日 By wengsi
	 * 
	 * @param applyId
	 * @param textarea
	 * @return none
	 */
	@RequestMapping(value = "backResult", method = RequestMethod.POST)
	public String backApply(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String message = StringUtils.EMPTY;
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "search_");
		prepareSearchCondition(paramMap);
		try {
			String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
			paramMap.put("counteroffer", counteroffer);
			List<PaybackSplit> applyList = paybackSplitService.getAllList(paramMap);
			/*for (int i = 0; i < applyList.size(); i++) {
				try {
					paybackSplitService.operateBackApply(paramMap, applyList.get(i));
				} catch (Exception e) {
					a++;
					e.printStackTrace();
				}
			}*/
			message = centerDeductBackFacade.submitData(applyList,paramMap);
		} catch (Exception e) {
			message = "退回失败";
			e.printStackTrace();
		}
		paramMap.clear();
		String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		Page<PaybackSplit> paybackSplitPage = paybackSplitService.getDeductPage(
				paramMap,request,response);
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		List<PaybackSplit> paybacklist = paybackSplitPage.getList();
		NumTotal numTotal = new  NumTotal();
		if(paybacklist.size()>0){
		    PaybackSplit  split  =  paybacklist.get(0);
			numTotal.setNum(String.valueOf(paybackSplitPage.getCount()));
			numTotal.setTotal(split.getSumAmont()== null ? "0" : split.getSumAmont().toString());
		}
		model.addAttribute("dayList", dayList);
		model.addAttribute("page", paybackSplitPage);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("message",message);
		model.addAttribute("numTotal", numTotal);
		logger.debug("invoke ApplyPayLaunchController method: findContractData, consult.id is: "
				+ paybackSplitPage);
		return "borrow/payback/centerdeduct/paybackSplit";
	}


	/**
	 * 线上划扣  2015年12月17日 By 翁私
	 * @param id
	 * @param realTimePlat
	 * @param deductMethod
	 * @return 跳转路径
	 */
	@RequestMapping(value = "lineDeductorKeysend", method = RequestMethod.POST)
	public String lineDeductorKeysend(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		String deductType = (String) paramMap.get("deductType");// 区分线上划扣和一键发送
		prepareSearchCondition(paramMap);
		String message = StringUtils.EMPTY;
		// 根据查询条件查询要划扣的数据
		paramMap.put("splitbackResult", StringUtils.EMPTY);
		String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		//paramMap.put("model", LoanModel.TG.getCode());
		deductReqList = paybackSplitService.queryDeductReqList(paramMap);
		// 调用批处理的接口
		if (deductReqList.size() > 0) {
		   message = paybackSplitOnlineFacade.submitData(deductReqList, deductType);
		 } else {
		    message = message + "<br>" + "没有要发送的数据或已经发送！";
		}
		// 插入操作流水
		// deductPaybackService.insertPaybackOpe(deductReqList,deductType);
		//paramMap.clear();
	    counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		model.addAttribute("message", message);
		// paramMap.put("model","");
		Page<PaybackSplit> paybackSplitPage = paybackSplitService.getDeductPage(
				paramMap,request,response);
		List<PaybackSplit> paybacklist = paybackSplitPage.getList();
		NumTotal numTotal = new  NumTotal();
		if(paybacklist.size()>0){
		    PaybackSplit  split  =  paybacklist.get(0);
			numTotal.setNum(String.valueOf(paybackSplitPage.getCount()));
			numTotal.setTotal(split.getSumAmont()== null ? "0" : split.getSumAmont().toString());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("page", paybackSplitPage);
		model.addAttribute("numTotal", numTotal);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("dayList", dayList);
		model.addAttribute("message",message);
		logger.debug("invoke ApplyPayLaunchController method: findContractData, consult.id is: "
				+ paybackSplitPage);
		return "borrow/payback/centerdeduct/paybackSplit";
	}
	
	/**
	 * 一键发送  2015年12月17日 By 翁私
	 * @param id
	 * @param realTimePlat
	 * @param deductMethod
	 * @return 跳转路径
	 */
	@RequestMapping(value = "oneKeysendDeductor", method = RequestMethod.POST)
	public String oneKeysendDeductor(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		String deductType = (String) paramMap.get("deductType");// 区分线上划扣和一键发送
		prepareSearchCondition(paramMap);
		String message = StringUtils.EMPTY;
		// 根据查询条件查询要划扣的数据
		paramMap.put("splitbackResult", StringUtils.EMPTY);
		String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
	
		paramMap.put("counteroffer", counteroffer);
		paramMap.put("model", LoanModel.TG.getCode());
		deductReqList = paybackSplitService.queryDeductOneKeyList(paramMap);
		logger.info("发送划扣开始！");
		if (deductReqList.size() > 0) {
			 Map<String,String>  map = bankPlantData();
			 message = paybackSplitFacade.submitData(deductReqList,deductType,map);
		 } else {
		    message = message + "<br>" + "没有要发送的数据或已经发送！";
		}
		paramMap.clear();
	    counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		model.addAttribute("message", message);
		paramMap.put("model","");
		Page<PaybackSplit> paybackSplitPage = paybackSplitService.getDeductPage(paramMap,request,response);
		List<PaybackSplit> paybacklist = paybackSplitPage.getList();
		NumTotal numTotal = new  NumTotal();
		if(paybacklist.size()>0){
		    PaybackSplit  split  =  paybacklist.get(0);
			numTotal.setNum(String.valueOf(paybackSplitPage.getCount()));
			numTotal.setTotal(split.getSumAmont()== null ? "0" : split.getSumAmont().toString());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("page", paybackSplitPage);
		model.addAttribute("numTotal", numTotal);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("dayList", dayList);
		model.addAttribute("message",message);
		logger.info("发送划扣结束！");
		return "borrow/payback/centerdeduct/paybackSplit";
	}
	
	/**
	 * 集中划扣已拆分列表导出(富有) 
	 * 2016年5月4日
	 * By 张永生
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportExcelFy")
	public String exportFyExcel(Model model,HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		try {
			// 判断划扣平台
			prepareSearchCondition(paramMap);
			// 设置划扣类型
			paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
			String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
					+ "','" // 待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
			paramMap.put("counteroffer", counteroffer);
			paramMap.put("model",LoanModel.TG.getCode());
			List<PaybackApply> applyList = paybackSplitService.queryApplyList(paramMap);
			String[] header = {"序号","开户行","扣款人银行账号","户名","金额(单位:元)","企业流水账号","备注","手机号","证件类型","证件号"};
			fuYouDeductSplitFacade
					.submitSplitData(applyList,
							threePartFileName.getFyDsExportFileName(), header,
							response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "导出富友出错，" + e.getMessage());
		}
		return getPath(paramMap,model,request,response);
	}

	/**
	 * 集中划扣已拆分列表导出（好易联） 2015年12月26日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelHyl")
	public String exportExcelHyl(Model model,HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		    Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		try {
			
			List<PaybackApply> applyList = new ArrayList<PaybackApply>();
			prepareSearchCondition(paramMap);
			// 设置划扣类型
			paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
		    String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
						+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
						+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
		     paramMap.put("counteroffer", counteroffer);
		     paramMap.put("model",LoanModel.TG.getCode());
		     applyList = paybackSplitService.queryApplyList(paramMap);
		     haoYiLianDeductSplitFacade
				.submitSplitData(applyList,
						threePartFileName.getHylDsExportFileName(),
						response);
		     return null;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "导出好易联出错，" + e.getMessage());
		}
		
		  return getPath(paramMap,model,request,response);
	}
	
	/**
	 * 集中划扣已拆分列表导出（好易联txt） 2015年12月26日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportTxtHyl")
	public String exportTxtHyl(Model model,HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		try {
			List<PaybackApply> applyList = new ArrayList<PaybackApply>();
			prepareSearchCondition(paramMap);
			// 设置划扣类型
			paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
			String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
			paramMap.put("counteroffer", counteroffer);
			paramMap.put("model",LoanModel.TG.getCode());
			applyList = paybackSplitService.queryApplyList(paramMap);
			haoYiLianDeductSplitFacade.submitSplitTxt(applyList,threePartFileName.getHylDsExportFileName(),
					response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "导出好易联出错，" + e.getMessage());
		}
		
		return getPath(paramMap,model,request,response);
	}
	
	/**
	 *  将错误信息传回到页面 2016年4月1日 By wengsi
	 * 
	 * @param paramMap
	 * @param model
	 * @param request
	 * @param response
	 * @return 跳转路径
	 */
	public String getPath(Map<String, Object> paramMap,Model model,HttpServletRequest request,
			HttpServletResponse response){
		prepareSearchCondition(paramMap);
		String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
				+ "','" // 待划扣
				+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
		paramMap.put("counteroffer", counteroffer);
		Page<PaybackSplit> paybackSplitPage = paybackSplitService.getDeductPage(
				paramMap,request,response);
		
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("paybackSplitPage", paybackSplitPage);
		model.addAttribute("paramMap", paramMap);
		paramMap.put("model","");
		logger.debug("invoke ApplyPayLaunchController method: findContractData, consult.id is: "
				+ paybackSplitPage);
		return "borrow/payback/centerdeduct/paybackSplit";
		
	}

	/**
	 * 集中划扣已拆分列表导出（中金） 2015年12月26日 By wengsi
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelZj")
	public String exportExcelZj(Model model,HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		List<PaybackApply> applyList = new ArrayList<PaybackApply>();
		try {
			prepareSearchCondition(paramMap);
			// 设置划扣类型
			paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
			String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
					+ "','" // 待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
			paramMap.put("counteroffer", counteroffer);
			paramMap.put("model",LoanModel.TG.getCode());
			applyList = paybackSplitService.queryApplyList(paramMap);
			String[] header = {"明细流水号","金额(元)","银行名称","账户类型","账户名称","账户号码","分支行","省份","城市","结算标识","备注","证件类型","证件号码","手机号","电子邮箱","协议用户编号"};
			zhongJinDeductSplitFacade.submitSplitData(applyList,threePartFileName.getZjDsExportFileName(), header,response);
			paramMap.put("model","");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "导出中金出错," + e.getMessage());
		}
		return getPath(paramMap,model,request,response);
	}

	/**
	 * 集中划扣已拆分列表导出（中金 txt）
	 * 2015年12月26日
	 * By wengsi
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value="exportTxtZj")
	public String exportTxtZj(Model model,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "search_");
		List<PaybackApply> applyList = new ArrayList<PaybackApply>();
	    prepareSearchCondition(paramMap);
		// 设置划扣类型
	 	paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
			String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
			paramMap.put("counteroffer", counteroffer);
			paramMap.put("model",LoanModel.TG.getCode());
			applyList = paybackSplitService.queryApplyList(paramMap);
			try {
			zhongJinDeductSplitFacade.submitSplitText(applyList, threePartFileName.getZjDsExportFileName(), response);
			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "导出中金出错," + e.getMessage());
			}
			return getPath(paramMap,model,request,response);
	}
	
	/**
	 * 集中划扣已拆分列表导出（通联） 2015年12月26日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelTl")
	public String exportExcelTl(Model model,HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "search_");
		try {
			List<PaybackApply> applyList = new ArrayList<PaybackApply>();
			prepareSearchCondition(paramMap);
				// 设置划扣类型
			paramMap.put("deductType",OperateType.COLLECT_DEDUCT.getCode());
			String counteroffer = "'" + CounterofferResult.PREPAYMENT.getCode()
					+ "','" // 待划扣
					+ CounterofferResult.PROCESSED.getCode() + "','" // 处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() + "'";// 失败继续处理
			paramMap.put("counteroffer", counteroffer);
			paramMap.put("model",LoanModel.TG.getCode());
			applyList = paybackSplitService.queryApplyList(paramMap);
			// 导出过的数据
			tongLianDeductSplitFacade.submitSplitData(applyList, threePartFileName.getTlDsExportFileName(),response);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "导出通联出错," + e.getMessage());
		}
		  return getPath(paramMap,model,request,response);

	}

	/**
	 * 多线程 富有导入 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return msg
	 */
	@RequestMapping(value = "importExcelFy", method = RequestMethod.POST)
	@ResponseBody
	public String importFyExcel(HttpServletRequest request,
			HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
		String message = "";
		try {
			ExportHelper ex = new ExportHelper();
			Sheet sheet = ex.getSheet(file,0);
			Map<String, List<ImportEntity>> map = new HashMap<String, List<ImportEntity>>();
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				ex.assignDeductPayback(row,map);
			}
			message = deductPaybackFacade.submitData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return message;
	}

	/**
	 * 多线程 好易联导入 2015年12月26日 By wengsi
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 返回json
	 */
	@RequestMapping(value = "importExcelHyl", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelHyl(HttpServletRequest request,HttpServletResponse response, String plat,@RequestParam MultipartFile file) {
		String message = "";
		try {
			ExportHelper ex = new ExportHelper();
			Sheet sheet = ex.getSheet(file,1);
			Map<String, List<ImportEntity>> map = new HashMap<String, List<ImportEntity>>();
			for (int i = sheet.getFirstRowNum() + 2; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				ex.assembleHylData(row,map);
			}
			message = haoYiLianFacade.submitData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return message;
	}

	/**
	 * 待还款划扣列表导入(通联) 2016年1月6日 By wengsi
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importExcelTl1", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelTl1(HttpServletRequest request,HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
		    String message = "";
		try {
			ExportHelper ex = new ExportHelper();
			Sheet sheet = ex.getSheet(file,0);
			Map<String, List<ImportEntity>> map = new HashMap<String, List<ImportEntity>>();
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				ex.assembleTlData(row,map);
			}
			message = tongLianFacade.submitData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return message;
	}

	/**
	 * 待还款划扣列表导入(中金) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importExcelZj", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelZj(HttpServletRequest request,HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
		String message = "";
		 try {
			     ExportHelper ex = new ExportHelper();
				Sheet sheet = ex.getSheet(file,0);
				Map<String, List<ImportEntity>> map = new HashMap<String, List<ImportEntity>>();
				for (int i = sheet.getFirstRowNum() +2; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					ex.assembleZjData(row,map);
				}
				message = zhongJinFacade.submitData(map);

			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		return message;
	}

	/**
	 * 委托充值导出
	 * 
	 * 2016年3月10日 By 朱杰
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "trustRechargeOutput", method = RequestMethod.POST)
	public void trustRechargeOutput(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");

		String idVal = null;
		if (paramMap.get("id") != null
				&& StringUtils.isNotEmpty(paramMap.get("id").toString())) {
			idVal = paramMap.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			paramMap.put("ids", Arrays.asList(idVal.split(",")));
		}
		prepareSearchCondition(paramMap);
		paramMap.put("model",LoanModel.TG.getCode());
		List<PaybackSplit> paybackSplitList = paybackSplitService
				.getAllList(paramMap);
		int count = 1;
		// 导出结果集
		List<PaybackTrustEx> splitTrustList = new ArrayList<PaybackTrustEx>();
		for (PaybackSplit paybackSplit : paybackSplitList) {
			PaybackTrustEx splitTrust = new PaybackTrustEx();
			splitTrust = paybackSplitService.copyProperties(splitTrust,
					paybackSplit);
			splitTrust.setSeq(String.valueOf(count++));
			splitTrustList.add(splitTrust);
		}

		// 导出excel
		ExportExcel exportExcel = new ExportExcel(null, PaybackTrustEx.class);
		// 设置导出数据源
		exportExcel.setDataList(splitTrustList);
		try {
			// 写出文件到客户端
			exportExcel.write(response, "PW11_"
					+ DateUtils.getDate("yyyyMMdd") + "_0000.xlsx");
			exportExcel.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("列表导出失败", e);
		}

	}

	/**
	 * 线下划拨导出 2016年3月11日 By 王浩
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "trustDeductOutput", method = RequestMethod.POST)
	public void trustDeductOutput(Model model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");

		String idVal = null;
		if (paramMap.get("id") != null
				&& StringUtils.isNotEmpty(paramMap.get("id").toString())) {
			idVal = paramMap.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			paramMap.put("ids", Arrays.asList(idVal.split(",")));
		}
		prepareSearchCondition(paramMap);
		paramMap.put("model",LoanModel.TG.getCode());
		List<PaybackSplit> paybackSplitList = paybackSplitService
				.getAllList(paramMap);
		int count = 1;
		// 导出结果集
		List<TrustDeductEx> trustDeductList = new ArrayList<TrustDeductEx>();
		for (PaybackSplit paybackSplit : paybackSplitList) {
			TrustDeductEx trustDeduct = new TrustDeductEx();
			trustDeduct = paybackSplitService.copyDeductProperties(trustDeduct,
					paybackSplit);
			trustDeduct.setSeq(String.valueOf(count++));
			trustDeductList.add(trustDeduct);
		}

		// 导出excel
		ExportExcel exportExcel = new ExportExcel(null, TrustDeductEx.class);
		// 设置导出数据源
		exportExcel.setDataList(trustDeductList);
		try {
			// 写出文件到客户端
			exportExcel.write(response,"PW03_"
					+ DateUtils.getDate("yyyyMMdd") + "_0000.xlsx");
			exportExcel.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("列表导出失败", e);
		}

	}

	/**
	 * 委托充值导入 2016年3月10日 By 王浩
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "trustRechargeInput", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String importExcelTrust(HttpServletRequest request,
			HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
		ExcelUtils excelUtils = new ExcelUtils();
		File f = null;
		String rtnMsg="";
		try {
			f = LoanFileUtils.multipartFile2File(file);
			List<TrusteeImportEx> dataList = (List<TrusteeImportEx>) excelUtils
					.importExcel(f, 0, 0, TrusteeImportEx.class);
			if (dataList != null) {
				for (TrusteeImportEx trusteeImport : dataList) {
					String[] params = null;
					if (StringUtils.isNotEmpty(trusteeImport.getRemarks())
							&& trusteeImport.getRemarks().split("_").length > 0) {
						params = trusteeImport.getRemarks().split("_");
					}
					if (params != null && params.length == 3) {
						if (params[1].equals(DeductedConstantEx.PAYBACK_TRUST)) {
							// 合同编号
							trusteeImport.setContractCode(params[0]);
							// 还款申请id
							trusteeImport.setPaybackApplyId(params[2]);
	                        if(StringUtils.isNotEmpty(trusteeImport.getReturnCode())){
								if (trusteeImport.getReturnCode().equals(
										FuYouAccountBackState.JYCG.value)) {
									// 返回结果：成功
									trusteeImport
											.setReturnCode(CounterofferResult.PAYMENT_SUCCEED
													.getCode());
									// 失败原因
									trusteeImport.setReturnMsg("");
								} else {
									// 失败原因
									trusteeImport.setReturnMsg(trusteeImport
											.getReturnCode()
											+ ":"
											+ trusteeImport.getReturnMsg());
									// 返回结果：失败
									trusteeImport
											.setReturnCode(CounterofferResult.PAYMENT_FAILED
													.getCode());
	
								}
								paybackSplitService.updateTrustRecharge(trusteeImport);
							}
	                        else{
	                        	rtnMsg="【" + params[0] +"】委托充值导入数据错误" + "<br>";
	                        }
						}

					}
				}
			}
		} catch (Exception e) {
			rtnMsg = "文件导入失败";
		}
		return rtnMsg;
	}

	/**
	 * 线下划拨导入 2016年3月10日 By 王浩
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "trustDeductInput", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String importExcelTrustDeduct(HttpServletRequest request,
			HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
		ExcelUtils excelUtils = new ExcelUtils();
		File f = null;
		String rtnMag="";
		try {
			f = LoanFileUtils.multipartFile2File(file);
			List<TrustDeductImportEx> dataList = (List<TrustDeductImportEx>) excelUtils
					.importExcel(f, 0, 0, TrustDeductImportEx.class);
			if (dataList != null) {
				for(int i=0;i<dataList.size();i++){
					TrustDeductImportEx deductImport = dataList.get(i);
					String[] params = null;
					if (StringUtils.isNotEmpty(deductImport.getRemarks())
							&& deductImport.getRemarks().split("_").length > 0) {
						params = deductImport.getRemarks().split("_");
					}
					if (params != null && params.length == 3) {
						if (params[1].equals(DeductedConstantEx.PAYBACK_TRUST_SPLIT)) {
							try{
								deductImport.setContractCode(params[0]);
								deductImport.setPaybackApplyId(params[2]);
								Boolean  flag = true;
								if(StringUtils.isNotEmpty(deductImport.getReturnCode())){
									if (deductImport.getReturnCode().equals(
											FuYouAccountBackState.JYCG.value)) {
										// 成功
										deductImport
												.setReturnCode(CounterofferResult.PAYMENT_SUCCEED
														.getCode());
										deductImport.setReturnMsg("");
										
									} else {
										// 失败原因
										
										deductImport.setReturnMsg(deductImport
												.getReturnCode()
												+ ":"
												+ deductImport.getReturnMsg());
										deductImport
												.setReturnCode(CounterofferResult.PAYMENT_FAILED
														.getCode());
										// 交易金额为0
										deductImport.setTrustAmount("0");
										flag = false; 
									}
									LoanDeductEntity iteratorSplit = paybackSplitService
											.setLoanDeductEntity(deductImport);
									PaybackOpeEx paybackOpes = null;
									if(flag){
										 paybackOpes = new PaybackOpeEx(iteratorSplit.getBatId(),
												iteratorSplit.getBusinessId(), RepaymentProcess.LINE__TRANSFER,
													TargetWay.PAYMENT,
													PaybackOperate.ONLINE_TRANSFER_SUCCESS.getCode(),"划拨" + ":" + iteratorSplit.getDeductSucceedMoney());
									}else{
										 paybackOpes = new PaybackOpeEx(iteratorSplit.getBatId(),
												iteratorSplit.getBusinessId(), RepaymentProcess.LINE__TRANSFER,
													TargetWay.PAYMENT,
													PaybackOperate.ONLINE_TRANSFER_FAILED.getCode(),deductImport.getReturnMsg());
									}
									paybackSplitService.insertPaybackOpe(paybackOpes);
									// 更新回款信息
									if (iteratorSplit != null) {
										deductUpdateService.updateDeductInfo(iteratorSplit);
									}
								}
							}catch(Exception e){
								rtnMag+="第" + (i+1) + "行数据错误;";
							}
							
						}
						else{
							rtnMag="文件数据错误";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("集中划扣划拨上传失败",e);
			rtnMag = "文件导入失败";
		}
		return rtnMag;
	}

	/**
	 * 线上委托充值 2016年3月13日 By 朱杰
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "trushRechargeOnline", method = RequestMethod.POST)
	@ResponseBody
	public String trushRechargeOnline(Model model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		// 获取数据
		String idVal = null;
		if (paramMap.get("id") != null
				&& StringUtils.isNotEmpty(paramMap.get("id").toString())) {
			idVal = paramMap.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			paramMap.put("ids", idVal.split(","));
		}
		paramMap.put("model",LoanModel.TG.getCode());
		paramMap.put("trustRecharge", TrustmentStatus.YWT.getCode());
		prepareSearchCondition(paramMap);
		List<PaybackSplit> paybackSplitList = paybackSplitService
				.getAllList(paramMap);
		String rtnMsg = "";
		for (PaybackSplit paybackSplit : paybackSplitList) {
			try{
				if (paybackSplit.getSplitAmount()
						.compareTo(new BigDecimal(1000000)) == 1) {
					// 大于100万，不处理
					rtnMsg +=  "【"+paybackSplit.getContract().getContractCode()+"】"
							+ "的委托充值金额大于100万<br>";
				} else {
					// 委托充值，并更新数据库
					rtnMsg += paybackSplitService.trustRecharge(paybackSplit);
				}
			}catch(Exception e){
				logger.error( "【"+paybackSplit.getContract().getContractCode()+"】:操作失败",e);
				rtnMsg += "【"+paybackSplit.getContractCode()+"】委托充值操作失败<br>";
			}			
		}
		return jsonMapper.toJson(rtnMsg);
	}

	/**
	 * 线上划拨 2016年3月14日 By 朱杰
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "trustDeductOnline", method = RequestMethod.POST)
	@ResponseBody
	public String trustDeductOnline(Model model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		String rtnMsg="";
		// 获取数据
		String idVal = null;
		paramMap.put("model",LoanModel.TG.getCode());
		if (paramMap.get("id") != null
				&& StringUtils.isNotEmpty(paramMap.get("id").toString())) {
			idVal = paramMap.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			paramMap.put("ids", idVal.split(","));
		}
		prepareSearchCondition(paramMap);
		List<PaybackSplit> paybackSplitList = paybackSplitService
				.getAllList(paramMap);
		for(int i=0;i<paybackSplitList.size();i++){
			try{
				rtnMsg += paybackSplitService.transferOnline(paybackSplitList.get(i));
			}catch(Exception e){
				e.printStackTrace();
				rtnMsg += "【"+paybackSplitList.get(i).getContract().getContractCode()+"】线上划拨操作失败<br>";
			}
			
		}
		return rtnMsg;
	}

	/**
	 * 讲系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public String changeNum(String cum){
		//0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
		if(cum.equals(CertificateType.SFZ.getCode())){
			return "0";
		}else if(cum.equals(CertificateType.JGZ.getCode())){
			return "3";
		}else if(cum.equals(CertificateType.HZ.getCode())){
			return "2";
		}else if(cum.equals(CertificateType.HKB.getCode())){
			return "1";
		}else if(cum.equals(CertificateType.GAJMLWNDTXZ.getCode())){
			return "5";
		}
		return "";
	}
	
	@RequestMapping(value = "queryList")
	public String queryList(){
		deductPaybackService.bankPlantData("301");
		return "success";
	}
	
	
	/**
	 * 启动或者停止滚动划扣
	 * 2016年4月22日
	 * By 翁私
	 * @param sys
	 * @return success
	 */
	@RequestMapping(value = "startOrstopRolls")
	@ResponseBody
	public String startOrstopRolls(SystemSetting sys){
		paybackSplitService.startOrstopRolls(sys);
		return "success";
	}
	
	/**
	 * 查询实时批量
	 * 2016年4月22日
	 * By 翁私
	 * @param sys
	 * @return success
	 */
	@RequestMapping(value = "selPlatformBank")
	@ResponseBody
	public Object selPlatformBank(String platformId){
		PlatformBankEntity key = new PlatformBankEntity();
		key.setSysId(String.valueOf(SystemFlag.LOAN.value));
		key.setPlatformId(platformId);
		key.setStatus(UseFlag.QY.value);
		key.setDeductFlag(DeductFlagType.COLLECTION.getCode());
		List<PlatformBankEntity> ls = platformBankService.selPlatformBank(key);
		List<PlatformBankEntity> lsPb = new ArrayList<PlatformBankEntity>();
		
		for (int i = 0; i < ls.size(); i++) {
			if (i == 0) {
				lsPb.add(ls.get(i));
			}
			boolean isExist = false;
			for (int j = 0; j < lsPb.size(); j++) {
				if (ls.get(i).getDeductType().equals(lsPb.get(j).getDeductType())) {
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				lsPb.add(ls.get(i));
			}
		}
		return lsPb;
	}
	
	/**
	 * 判断滚动开始或者停止 2015年4月21日 By 翁私
	 * @return sys 
	 */
	@RequestMapping(value = "getSystemSetting")
	@ResponseBody
	public String getSystemSetting(){
		SystemSetting sys = new SystemSetting();
		sys.setSysFlag("SYS_COLLECT_DEDUCT");
		sys=paybackSplitService.getSystemSetting(sys);
		String sysValue  = sys.getSysValue();
		if (YESNO.YES.getCode().equals(sysValue)) {
			return "success";
		}
		return "fail";
	}
	
	/**
	 *  将银行和平台组装在一起  2015年4月21日 By 翁私
	 * @return map
	 */
	public Map<String, String> bankPlantData() {
		Map<String,String>  map = new HashMap<String,String>();
		PlatformGotoRule entity = new PlatformGotoRule();
		BankPlantPort port = new BankPlantPort();
    	entity.setIsConcentrate(YESNO.YES.getCode());
		port.setIsConcentrate(YESNO.YES.getCode());
		entity.setStatus(YESNO.YES.getCode());
	    // 银行接口
	 	List<BankPlantPort>  pantlist   =   bankPlantPortService.findPlantList(port);
        // 跳转顺序
		List<PlatformGotoRule>  skilist   =  platformGotoRuleManager.findList(entity);
			
		for(BankPlantPort bankplant:pantlist){
			for(PlatformGotoRule plantSkip:skilist){
				StringBuffer rule = new StringBuffer();
				String[]  plants = bankplant.getPlantCode().split(",");
				String[]  ports = bankplant.getBatchFlag().split(",");
				String[]  skipplants = plantSkip.getPlatformRule().split(",");
				
				for(int i=0 ; i<skipplants.length ; i++){
					for(int j=0 ; j<plants.length ; j++){
						if(skipplants[i].equals(plants[j])){
								rule.append(plants[j]+":"+ports[j]+",");
						}
					}
				}
				String rulestring =rule.toString();
				if(!"".equals(rulestring) && rulestring != null){
					rulestring=rulestring.substring(0,rulestring.length()-1);
					map.put(bankplant.getBankCode()+""+plantSkip.getPlatformId(), rulestring);
				}
			}
		}
		return map;
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "exportExcel")
	public String   exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map){
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		prepareSearchCondition(paramMap);
		try{
			String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
					+ CounterofferResult.PROCESS.getCode() + "','" //处理中
					+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
					+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
			
			paramMap.put("counteroffer", counteroffer);
			
			ExportCenterDeductSplitHelper.exportData(paramMap,response);
			return null;
		 }catch(Exception e){
			e.printStackTrace();
			return "redirect:" + adminPath + "/borrow/payback/centerdeduct/paybackSplit";
		}
	 }
	
}
