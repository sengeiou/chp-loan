package com.creditharmony.loan.borrow.payback.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.excel.util.ExportExcel;
import com.creditharmony.core.fortune.type.FuYouAccountBackState;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.ChannelFlag;
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
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.service.LoanBankService;
import com.creditharmony.loan.borrow.payback.entity.BankRule;
import com.creditharmony.loan.borrow.payback.entity.DeductCondition;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackTrustEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.borrow.payback.facade.PendFuYouDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.PendFuyouFacade;
import com.creditharmony.loan.borrow.payback.facade.PendHaoYiLianDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.PendHaoYiLianFacade;
import com.creditharmony.loan.borrow.payback.facade.PendTongLianDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.PendTongLianFacade;
import com.creditharmony.loan.borrow.payback.facade.PendZhongJinDeductSplitFacade;
import com.creditharmony.loan.borrow.payback.facade.PendZhongJinFacade;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
import com.creditharmony.loan.borrow.payback.util.PaybackTrustUtil;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ExportHelper;
import com.creditharmony.loan.common.utils.FilterHelper;
import com.creditharmony.loan.common.utils.LoanFileUtils;

/**
 * 待还款划扣处理Controller
 * 
 * @Class Name DeductPaybackController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deduct")
public class DeductPaybackController extends BaseController {

	@Autowired
	private PaybackService paybackService;
	@Autowired
	private DeductPaybackService deductPaybackService;
	@Autowired
	private PaymentSplitService paymentSplitService;
	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
	private PaybackSplitService paybackSplitService;
	
	@Autowired
	private PendFuyouFacade deductPaybackFacade;
	@Autowired
	private PendHaoYiLianFacade haoYiLianFacade;
	@Autowired
	private PendTongLianFacade tongLianFacade;
	@Autowired
	private PendZhongJinFacade zhongJinFacade;
	
	@Autowired
	private PendFuYouDeductSplitFacade pendFuYouDeductSplitFacade;
	@Autowired
	private PendHaoYiLianDeductSplitFacade pendHaoYiLianDeductSplitFacade;
	@Autowired
	private PendTongLianDeductSplitFacade pendTongLianDeductSplitFacade;
	@Autowired
	private PendZhongJinDeductSplitFacade pendZhongJinDeductSplitFacade;
	@Autowired
	private RepaymentDateService dateService;
	@Autowired
	private LoanBankService loanBankService;

	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 跳转待还款划扣页面 2016年1月6日 
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goDeductPaybackList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam Map<String, Object> map) {
		
		prepareSearchCondition(map);
		map.put("channelFlag", ChannelFlag.ZCJ.getCode());
		Page<PaybackApply> paybackApplyPage = deductPaybackService
				.findApplyPayback(new Page<PaybackApply>(request, response),
						map);

		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(paybackApplyPage.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for(PaybackApply pa:paybackApplyPage.getList()){
				if(pa.getPayback() != null){
					pa.getPayback().setDictPayStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.PAY_STATUS, pa.getPayback().getDictPayStatus()));
				}
				if(pa.getLoanBank() != null){
					pa.getLoanBank().setBankNameLabel(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, pa.getLoanBank().getBankName()));
				}
				pa.setSplitBackResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, pa.getSplitBackResult()));
				pa.setTrustRechargeResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, pa.getTrustRechargeResult()));
				if(pa.getLoanInfo() != null){
					pa.getLoanInfo().setDictLoanStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, pa.getLoanInfo().getDictLoanStatus()));
					pa.getLoanInfo().setLoanFlagLabel(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, pa.getLoanInfo().getLoanFlag()));
				}
				pa.setDictDealTypeLabel(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, pa.getDictDealType()));
				pa.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", pa.getModel()));
				pa.setTlSignLabel(DictUtils.getLabel(dictMap,"yes_no", pa.getTlSign()));
			}
		}
		NumTotal numTotal = new  NumTotal();
		List<PaybackApply> paybacklist = paybackApplyPage.getList();
		if(paybacklist.size()>0){
			    numTotal.setNum(String.valueOf(paybackApplyPage.getCount()));
				numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("page", paybackApplyPage);
		model.addAttribute("paramBean", map);
		model.addAttribute("message", "");
		model.addAttribute("numTotal",numTotal);
		logger.debug("invoke DeductPaybackController method: goDeductPaybackList, contarctCode is: ");
		return "borrow/payback/paybackflow/deductPayback";
	}
	
	
	private void prepareSearchCondition(Map<String, Object> map){
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("splitFlag", RepayApplyStatus.PRE_PAYMENT.getCode());
		
		if (!ObjectHelper.isEmpty(map.get("stores"))) {
			String stores = (String)map.get("stores");
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(map.get("bank"))) {
			String bank = (String)map.get("bank");
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		
		if (!ObjectHelper.isEmpty(map.get("paybackDay"))) {
			String dayName = (String)map.get("paybackDay");
			map.get("paybackDay");
			map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
		}
		
		String dictDealTypeId = (String)map.get("dictDealTypeId");
		if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
			map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
		}
		map.put("channelFlag", ChannelFlag.ZCJ.getCode());
	}

	/**
	 * 匹配批量退回 2015年12月28日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 * @param contractCodes
	 * @param applyBackMes
	 * @return none
	 */
	@ResponseBody
	@RequestMapping(value = "deductBatchBack")
	public void deductBatchBack(HttpServletRequest request,
			HttpServletResponse response, String ids, String contractCodes,
			String applyBackMes) {
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		PaybackApply paybackApply = new PaybackApply();
		paybackApply.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_RETURN
				.getCode());
		paybackApply.setApplyBackMes(applyBackMes);
		if (StringUtils.isNotEmpty(ids)) {
			String[] id = ids.split(",");
			String[] contractCode = contractCodes.split(",");
			for (int i = 0; i < id.length; i++) {
				paybackTransferInfo.setrPaybackApplyId(id[i]);
				paybackTransferInfo
						.setAuditStatus(BankSerialCheck.CHECKE_FAILED.getCode());
				paybackApply.setId(id[i]);
				paybackApply.preUpdate();
				paybackApply.setContractCode(contractCode[i]);
				paybackService.updatePaybackApply(paybackApply);
				this.savePaybackOpe(paybackApply);
			}
		} else {
			logger.debug("invoke DealPaybackController method: deductBatchBack deductApplyId is empty");
		}
	}

	/**
	 * 批量退回 2015年12月28日 By 翁私
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 * @param contractCodes
	 * @param applyBackMes
	 * @return none
	 */
	@RequestMapping(value = "stayDeductBatchBack")
	public String stayDeductBatchBack(HttpServletRequest request,
			HttpServletResponse response,@RequestParam Map<String, Object> map,Model model) {
		String idVal ="";
		 if (StringUtils.isNotEmpty((String)map.get("id"))) {
			idVal = map.get("id").toString();
		 }
		 if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		 }
		 prepareSearchCondition(map);
		 String message = StringUtils.EMPTY;
		 int a = 0;
			List<PaybackApply> applylist = deductPaybackService.findApplyPayback(map);
		    try {
		    	for (PaybackApply apply : applylist) {
				  deductPaybackService.backApply(map,apply);
		    	}
			} catch (Exception e) {
				a++;
				e.printStackTrace();
			}
		    message = "成功退回" + (applylist.size() - a) + "条, " + "失败 " + a + " 条";
		    map.clear();
		    prepareSearchCondition(map);
			Page<PaybackApply> paybackApplyPage = deductPaybackService
					.findApplyPayback(new Page<PaybackApply>(request, response),map);
			
			for(PaybackApply pa:paybackApplyPage.getList()){
				if(pa.getPayback()!=null && pa.getPayback().getDictPayStatus()!=null){
					String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",pa.getPayback().getDictPayStatus());
					pa.getPayback().setDictPayStatusLabel(dictPayStatus);
				}
				
				if(pa.getLoanBank()!=null && pa.getLoanBank().getBankName()!=null){
					String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",pa.getLoanBank().getBankName());
					pa.getLoanBank().setBankNameLabel(bankName);
				}
				
				if(pa.getSplitBackResult()!=null){
					String splitBackResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getSplitBackResult());
					pa.setSplitBackResultLabel(splitBackResult);
				}
				
				if(pa.getTrustRechargeResult()!=null){
					String trustRechargeResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getTrustRechargeResult());
					pa.setTrustRechargeResultLabel(trustRechargeResult);
				}
				
				if(pa.getLoanInfo()!=null && pa.getLoanInfo().getDictLoanStatus()!=null){
					String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",pa.getLoanInfo().getDictLoanStatus());
					pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
				}
				
				if(pa.getLoanInfo()!=null && pa.getLoanInfo().getLoanFlag()!=null){
					String loanFlag=DictCache.getInstance().getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag());
					pa.getLoanInfo().setLoanFlagLabel(loanFlag);
				}
				
				if(pa.getModel()!=null){
					String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",pa.getModel());
					pa.setModelLabel(modelLabel);
				}
			}
			NumTotal numTotal = new  NumTotal();
			List<PaybackApply> paybacklist = paybackApplyPage.getList();
			if(paybacklist.size()>0){
					numTotal.setNum(String.valueOf(paybackApplyPage.getCount()));
					numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont());
			}
			model.addAttribute("page", paybackApplyPage);
			model.addAttribute("paramBean", map);
			model.addAttribute("message", message);
			logger.debug("invoke DeductPaybackController method: goDeductPaybackList, contarctCode is: ");
			return "borrow/payback/paybackflow/deductPayback";
	}
	
	/**
	 * 保存批量匹配退回操作历史 2015年12月28日 By zhangfeng
	 * 
	 * @param paybackApply
	 * @return none
	 */
	public void savePaybackOpe(PaybackApply paybackApply) {

		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(), null,
				RepaymentProcess.RETURN, TargetWay.REPAYMENT, PaybackOperate.RETURN_SUCCESS.getCode(), "合同编号:"
						+ paybackApply.getContractCode());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 保存划扣退回操作历史 2015年12月28日 By zhangfeng
	 * 
	 * @param paybackApply
	 * @return none
	 */
	public void saveStayPaybackOpe(PaybackApply paybackApply, String backmsg) {

		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(), null,
				RepaymentProcess.DEDECT, TargetWay.REPAYMENT,
				PaybackOperate.REBACK.getCode(), "合同编号:"
						+ paybackApply.getContractCode() + backmsg);
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 待还款划扣列表导出(富有) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelFy")
	public void exportExcelFy(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		String idVal = (String) map.get("id");
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
	    String dayName = (String) map.get("paybackDay");
        if (!ObjectHelper.isEmpty(dayName)) {
			map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
		}
        String dictDealTypeId = (String)map.get("dictDealTypeId");
     		if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
     			map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
     		} 
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				map.put("id", idstring);
			}
			map.put("splitbackResult", "");
			map.put("model",LoanModel.TG.getCode());
			paybackList = deductPaybackService.queryApplyList(map);
			String[] header = {"序号","开户行","扣款人银行账号","户名","金额(单位:元)","企业流水账号","备注","手机号","证件类型","证件号"};
			pendFuYouDeductSplitFacade
			.submitSplitData(paybackList,
					threePartFileName.getFyDsExportFileName(), header,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 待还款划扣列表导出（好易联） 2015年12月26日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelHyl")
	public void exportExcelHyl(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		 String dayName = (String) map.get("paybackDay");
	     if (!ObjectHelper.isEmpty(dayName)) {
				map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
	     String dictDealTypeId = (String)map.get("dictDealTypeId");
			if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
				map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
			} 
		String idVal = (String) map.get("id");
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				map.put("id", idstring);
			}
			map.put("splitbackResult", "");
			map.put("model",LoanModel.TG.getCode());
			paybackList = deductPaybackService.queryApplyList(map);
			pendHaoYiLianDeductSplitFacade.submitSplitData(paybackList,
					threePartFileName.getHylDsExportFileName(),
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 待还款划扣列表导出（好易联txt） 2015年12月26日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportTxtHyl")
	public void exportTxtHyl(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		String dayName = (String) map.get("paybackDay");
	     if (!ObjectHelper.isEmpty(dayName)) {
				map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
	     String dictDealTypeId = (String)map.get("dictDealTypeId");
			if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
				map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
			} 
		String idVal = (String) map.get("id");
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				map.put("id", idstring);
			}
			map.put("splitbackResult", "");
			map.put("model",LoanModel.TG.getCode());
			paybackList = deductPaybackService.queryApplyList(map);
			pendHaoYiLianDeductSplitFacade.submitSplitTxt(paybackList,
					threePartFileName.getHylDsExportFileName(),
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 待还款划扣列表导出(中金) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelZj")
	public void exportExcelZj(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		String idVal = (String) map.get("id");
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		 String dayName = (String) map.get("paybackDay");
	     
	     if (!ObjectHelper.isEmpty(dayName)) {
				map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
	     String dictDealTypeId = (String)map.get("dictDealTypeId");
			if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
				map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
			} 
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				map.put("id", idstring);
			}
			map.put("splitbackResult", "");
			map.put("model",LoanModel.TG.getCode());
			paybackList = deductPaybackService.queryApplyList(map);
			String[] header = {"明细流水号","金额(元)","银行名称","账户类型","账户名称","账户号码","分支行","省份","城市","结算标识","备注","证件类型","证件号码","手机号","电子邮箱","协议用户编号"};
			pendZhongJinDeductSplitFacade.submitSplitData(paybackList,threePartFileName.getZjDsExportFileName(), header,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 待还款划扣列表导出(中金) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportTxtZj")
	public void exportTxtZj(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		String idVal = (String) map.get("id");
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		String dayName = (String) map.get("paybackDay");
	     
	     if (!ObjectHelper.isEmpty(dayName)) {
				map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
		if (!StringUtils.isEmpty(idVal)) {
			String idstring = FilterHelper.appendIdFilter(idVal);
			map.put("id", idstring);
		}
		 String dictDealTypeId = (String)map.get("dictDealTypeId");
			if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
				map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
			} 
		map.put("splitbackResult", "");
		map.put("model",LoanModel.TG.getCode());
		paybackList = deductPaybackService.queryApplyList(map);
		pendZhongJinDeductSplitFacade.submitSplitText(paybackList,threePartFileName.getZjDsExportFileName(),response);
	}

	/**
	 * 待还款划扣列表导出(通联) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @param platId
	 * @return none
	 */
	@RequestMapping(value = "exportExcelTl")
	public void exportExcelTl(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> map) {
		String idVal = (String) map.get("id");
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String stores = (String) map.get("stores");
		String bank = (String) map.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			map.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			map.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		 String dayName = (String) map.get("paybackDay");
	     
	     if (!ObjectHelper.isEmpty(dayName)) {
				map.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
	     String dictDealTypeId = (String)map.get("dictDealTypeId");
			if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
				map.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
			} 
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				map.put("id", idstring);
			}
			map.put("splitbackResult", "");
			map.put("model",LoanModel.TG.getCode());
			paybackList = deductPaybackService.queryApplyList(map);
			// 导出过的数据
			pendTongLianDeductSplitFacade.submitSplitData(paybackList, threePartFileName.getTlDsExportFileName(),response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 待还款划扣列表导入(富有) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importExcelFy", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelFy(HttpServletRequest request,
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
	 * 待还款划扣列表导入(好易联) 2016年1月6日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importExcelHyl", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelHyl(HttpServletRequest request,
			HttpServletResponse response, String plat,
			@RequestParam MultipartFile file) {
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
	 * 
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importExcelTl1", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelTl1(HttpServletRequest request,
			HttpServletResponse response, String plat,
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
	public String importExcelZj(HttpServletRequest request,
			HttpServletResponse response, String plat,
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
	 * 待还款划扣 ：线上划扣 2016年1月6日 By 翁私
	 * 
	 * @param idVal
	 * @param applyAmounts
	 * @param dictDealTypes
	 * @param deductMethod
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "buckleOnLine")
	public String buckleOnLine(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam Map<String, Object> paramMap) {
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		String idVal = (String) paramMap.get("id");
		String deductType = (String) paramMap.get("deductType");//区分线上划扣和一键发送
		paramMap.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		paramMap.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		paramMap.put("channelFlag", ChannelFlag.ZCJ.getCode());
		String stores = (String) paramMap.get("stores");
		String bank = (String) paramMap.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			paramMap.put("storesId", FilterHelper.appendIdFilter(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			paramMap.put("bankId", FilterHelper.appendIdFilter(bank));
		}
		 String dayName = (String) paramMap.get("paybackDay");
	    if (!ObjectHelper.isEmpty(dayName)) {
	    	 paramMap.put("monthPayDay", FilterHelper.appendIdFilter(dayName));
			}
	    String dictDealTypeId = (String)paramMap.get("dictDealTypeId");
		if (!"".equals(dictDealTypeId) && dictDealTypeId != null) {
			paramMap.put("dictDealType", Arrays.asList(dictDealTypeId.split(",")));
		} 
	    paramMap.put("model",LoanModel.TG.getCode());
		String message = StringUtils.EMPTY;
		try {
			if (!StringUtils.isEmpty(idVal)) {
				String idstring = FilterHelper.appendIdFilter(idVal);
				paramMap.put("id", idstring);
			}
			// 根据查询条件查询要划扣的数据
			paramMap.put("splitFlag", RepayApplyStatus.PRE_PAYMENT.getCode());
			deductReqList = deductPaybackService.queryDeductReqList(paramMap);
			int a = 0;
			if (deductReqList.size() > 0) {
				for(int i = 0; i < deductReqList.size(); i++){
					try {
						boolean isSuccess = deductPaybackService.sendBatch(deductReqList.get(i),deductType,OperateType.PAYMENT_DEDUCT.getCode());
						if(!isSuccess){
					    	a++;
					    }
					} catch (Exception e) {
							a++;
							e.printStackTrace();
							logger.error("线上划扣 ！"+e.getMessage());
					}
				}
	    	       message = message + "<br>" + "成功发送" + (deductReqList.size()-a) + "条，" + "失败"+a+"条";
			 } else {
				   message = message + "<br>" + "没有要发送的数据或已经发送！";
			}
			model.addAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		paramMap.put("model","");
		paramMap.put("id", "");
		prepareSearchCondition(paramMap);
		Page<PaybackApply> paybackApplyPage = deductPaybackService
				.findApplyPayback(new Page<PaybackApply>(request, response),
						paramMap);
		
		for(PaybackApply pa:paybackApplyPage.getList()){
			if(pa.getPayback() != null){
				String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",pa.getPayback().getDictPayStatus());
				pa.getPayback().setDictPayStatusLabel(dictPayStatus);
			}
		    
			if(pa.getLoanBank() != null){
				String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",pa.getLoanBank().getBankName());
				pa.getLoanBank().setBankNameLabel(bankName);
			}
			
			String splitBackResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getSplitBackResult());
			pa.setSplitBackResultLabel(splitBackResult);
			
			String trustRechargeResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getTrustRechargeResult());
			pa.setTrustRechargeResultLabel(trustRechargeResult);
			if(pa.getLoanInfo() != null){
				String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",pa.getLoanInfo().getDictLoanStatus());
				pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			}
		
			if(pa.getLoanInfo() != null){
				String loanFlag=DictCache.getInstance().getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag());
				pa.getLoanInfo().setLoanFlagLabel(loanFlag);
			}
			
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",pa.getModel());
			pa.setModelLabel(modelLabel);
		}
		NumTotal numTotal = new  NumTotal();
		List<PaybackApply> paybacklist = paybackApplyPage.getList();
		if(paybacklist.size()>0){
				numTotal.setNum(String.valueOf(paybackApplyPage.getCount()));
				numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont());
		}
		model.addAttribute("page", paybackApplyPage);
		model.addAttribute("paramBean", paramMap);
		logger.info("发送划扣结束！");

		return "borrow/payback/paybackflow/deductPayback";
	}
	
	
	/**
	 * 待还款划扣 ：一键发送 2016年1月6日 By 翁私
	 * 
	 * @param idVal
	 * @param applyAmounts
	 * @param dictDealTypes
	 * @param deductMethod
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "oneKeysendDeductor")
	public String oneKeysendDeductor(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam Map<String, Object> paramMap) {
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		String deductType = (String) paramMap.get("deductType");//区分线上划扣和一键发送
		paramMap.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		paramMap.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		paramMap.put("channelFlag", ChannelFlag.ZCJ.getCode()); //过滤大金融数据
		String message = StringUtils.EMPTY;
		try {
			// 根据查询条件查询要划扣的数据
			//paramMap.put("splitbackResult", StringUtils.EMPTY);
			paramMap.put("splitFlag", RepayApplyStatus.PRE_PAYMENT.getCode());
		    paramMap.put("model",LoanModel.TG.getCode());
			deductReqList = deductPaybackService.queryDeductOneKeyList(paramMap);
			
			logger.info("发送划扣开始！");
			if (deductReqList.size() > 0) {
				
				// 停止自动发送
				deductPaybackService.stopSystemSetting();
				// 组装规则
				List<BankRule> ruleList = deductPaybackService.queryBankRule();
				Map<String,BankRule> ruleMap = new HashMap<String,BankRule>();
				for(BankRule rule : ruleList){
					ruleMap.put(rule.getBankCode(), rule);
				}
				// 查询划扣条件
				List<DeductCondition> conditionList = deductPaybackService.queryDeductCondition();
				Map<String,DeductCondition> conditionMap = new HashMap<String,DeductCondition>();
				for(DeductCondition condition : conditionList){
					conditionMap.put(condition.getPlantCode(),condition);
				}
				// 查询划扣统计信息条件
				Date date = new Date();
				String dateString = format.format(date);
				Map<String,String> deductMap = new HashMap<String,String>();
				deductMap.put("queryDate",dateString);
				List<DeductStatistics> statisticsList = deductPaybackService.queryDeductStatistics(deductMap);
				Map<String,DeductStatistics> statisticsMap = new HashMap<String,DeductStatistics>();
				for(DeductStatistics statistics : statisticsList){
					statisticsMap.put(statistics.getPlantCode(),statistics);
				}
				int a = 0;
				for(int i = 0; i < deductReqList.size(); i++){
					try {
						boolean isSuccess = deductPaybackService.oneKeysendDeductor(deductReqList.get(i),
								deductType,OperateType.PAYMENT_DEDUCT.getCode(),ruleMap,conditionMap,statisticsMap);
						if(!isSuccess){
					    	a++;
					    }	   
					} catch (Exception e) {
						    	a++;
								e.printStackTrace();
								logger.error("一键发送报错！"+e.getMessage());
								
						}
				}
	    	       message = message + "<br>" + "成功发送" + (deductReqList.size()-a) + "条，" + "失败"+a+"条";
			 } else {
				   message = message + "<br>" + "没有要发送的数据或已经发送！";
			}
			model.addAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		paramMap.put("dictPaybackStatus",RepayApplyStatus.PRE_PAYMENT.getCode());
		paramMap.put("splitFlag", RepayApplyStatus.PRE_PAYMENT.getCode());
		paramMap.put("id", "");
		paramMap.put("model","");
		paramMap.put("channelFlag", ChannelFlag.ZCJ.getCode());
		Page<PaybackApply> paybackApplyPage = deductPaybackService.findApplyPayback(new Page<PaybackApply>(request, response),
						paramMap);
		for(PaybackApply pa:paybackApplyPage.getList()){
			if(pa.getPayback() != null){
				String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",pa.getPayback().getDictPayStatus());
				pa.getPayback().setDictPayStatusLabel(dictPayStatus);
			}
			
			if(pa.getLoanBank() != null){
				String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",pa.getLoanBank().getBankName());
				pa.getLoanBank().setBankNameLabel(bankName);
			}
			
			String splitBackResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getSplitBackResult());
			pa.setSplitBackResultLabel(splitBackResult);
			
			String trustRechargeResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getTrustRechargeResult());
			pa.setTrustRechargeResultLabel(trustRechargeResult);
			 if(pa.getLoanInfo() != null){
				 String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",pa.getLoanInfo().getDictLoanStatus());
				 pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
					 
			 }
			 if(pa.getLoanInfo() != null){
				 String loanFlag=DictCache.getInstance().getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag());
				 pa.getLoanInfo().setLoanFlagLabel(loanFlag); 
			 }
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",pa.getModel());
			pa.setModelLabel(modelLabel);
		}
		NumTotal numTotal = new  NumTotal();
		List<PaybackApply> paybacklist = paybackApplyPage.getList();
		if(paybacklist.size()>0){
				numTotal.setNum(String.valueOf(paybackApplyPage.getCount()));
				numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont());
		}
		model.addAttribute("page", paybackApplyPage);
		model.addAttribute("paramBean", paramMap);
		logger.info("发送划扣结束！");

		return "borrow/payback/paybackflow/deductPayback";
	}
	
	/**
	 * 委托充值导出
	 * 2016年3月10日
	 * By 王浩
	 * @param model
	 * @param request
	 * @param response
	 * @param map 
	 */
	@RequestMapping(value = "trustRechargeOutput", method = RequestMethod.POST)
	public void trustRechargeOutput(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> map){
		
		String idVal = null;
		if (map.get("id") != null && StringUtils.isNotEmpty(map.get("id").toString())) {
			idVal = map.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		}
		map.put("model",LoanModel.TG.getCode());
		prepareSearchCondition(map);
		List<PaybackApply> paybackDeductList = deductPaybackService.findApplyPayback(map);
		int count = 1;
		//导出结果集
		List<PaybackTrustEx> splitTrustList = new ArrayList<PaybackTrustEx>();
		for (PaybackApply paybackDeduct : paybackDeductList) {
			PaybackTrustEx deductTrust = new PaybackTrustEx();
			deductTrust = PaybackTrustUtil.copyRechargeProperties(deductTrust, paybackDeduct);
			deductTrust.setSeq(String.valueOf(count++));
			BigDecimal trustAmount = deductTrust.getTrustAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
			deductTrust.setTrustAmount(trustAmount);
			splitTrustList.add(deductTrust);
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
	 * 线下划拨导出
	 * 2016年3月11日
	 * By 王浩
	 * @param model
	 * @param request
	 * @param response
	 * @param map 
	 */
	@RequestMapping(value = "trustDeductOutput", method = RequestMethod.POST)
	public void trustDeductOutput(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> map){
		
		String idVal = null;
		if (map.get("id") != null && StringUtils.isNotEmpty(map.get("id").toString())) {
			idVal = map.get("id").toString();
			map.remove("id");
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		}
		map.put("model",LoanModel.TG.getCode());
		prepareSearchCondition(map);
		List<PaybackApply> paybackApplyList = deductPaybackService.findApplyPayback(map);
		int count=1;
		//导出结果集
		List<TrustDeductEx> trustDeductList = new ArrayList<TrustDeductEx>();
		for (PaybackApply paybackApply : paybackApplyList) {
			TrustDeductEx trustDeduct = new TrustDeductEx();
			trustDeduct = deductPaybackService.copyDeductProperties(trustDeduct, paybackApply);
			trustDeduct.setSeq(String.valueOf(count++));
			trustDeduct.setTrustAmount(trustDeduct.getTrustAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			trustDeductList.add(trustDeduct);
		}
		
		// 导出excel
		ExportExcel exportExcel = new ExportExcel(null,
				TrustDeductEx.class);
		// 设置导出数据源
		exportExcel.setDataList(trustDeductList);
		try {
			// 写出文件到客户端
			exportExcel.write(response,  "PW03_"
					+ DateUtils.getDate("yyyyMMdd") + "_0000.xlsx");
			exportExcel.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("列表导出失败", e);
		}	
	}
	
	/**
	 * 委托充值导入
	 * 2016年3月10日
	 * By 王浩
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 
	 */
	@RequestMapping(value="trustRechargeInput", method=RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String importExcelTrust(HttpServletRequest request, HttpServletResponse response, 
			String plat, @RequestParam MultipartFile file) {
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
							try{
								//合同编号
								trusteeImport.setContractCode(params[0]);
								//还款申请id
								trusteeImport.setPaybackApplyId(params[2]);
								if (StringUtils.isNotEmpty(trusteeImport.getReturnCode())) {
									if (trusteeImport.getReturnCode().equals(
											FuYouAccountBackState.JYCG.value)) {
										// 返回结果：成功
										trusteeImport.setReturnCode(CounterofferResult.PAYMENT_SUCCEED
														.getCode());
										trusteeImport.setReturnMsg("");
									} else {
									
										//失败原因
										trusteeImport.setReturnMsg(
												trusteeImport.getReturnCode()+":"+trusteeImport.getReturnMsg());
										//返回结果：失败
										trusteeImport.setReturnCode(
												CounterofferResult.PAYMENT_FAILED.getCode());
									}
									deductPaybackService.updateTrustRecharge(trusteeImport);
									
								}else{
									rtnMsg +="【" + params[0] +"】委托充值导入数据错误;";
								}
							}catch(Exception e){
								rtnMsg +="【" + params[0] +"】委托充值操作失败;";
							}
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("待还款划扣列表导入文件失败",e);
			rtnMsg="文件导入失败";
		}
		return rtnMsg;
	}
	
	/**
	 * 线下划拨导入
	 * 2016年3月10日
	 * By 王浩
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 
	 */
	@RequestMapping(value="trustDeductInput", method=RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String importExcelTrustDeduct(HttpServletRequest request, HttpServletResponse response, 
			String plat, @RequestParam MultipartFile file) {
		ExcelUtils excelUtils = new ExcelUtils();
		File f = null;
		String rtnMsg = "";
		try {
			f = LoanFileUtils.multipartFile2File(file);
			List<TrustDeductImportEx> dataList = (List<TrustDeductImportEx>) excelUtils
					.importExcel(f, 0, 0, TrustDeductImportEx.class);
			if (dataList != null) {
				for (TrustDeductImportEx deductImport : dataList) {
					String[] params = null;
					Boolean  flag = true;
					if (StringUtils.isNotEmpty(deductImport.getRemarks())
							&& deductImport.getRemarks().split("_").length > 0) {
						params = deductImport.getRemarks().split("_");
					}
					if (params != null && params.length == 3) {
						if (params[1].equals(DeductedConstantEx.PAYBACK_TRUST_DEDUCT)) {
							deductImport.setContractCode(params[0]);
							deductImport.setPaybackApplyId(params[2]);
						  if(StringUtils.isNotEmpty(deductImport.getReturnCode())){
								if (deductImport.getReturnCode().equals(
										FuYouAccountBackState.JYCG.value)) {
									//成功
									deductImport.setReturnCode(CounterofferResult.PAYMENT_SUCCEED.getCode());
									deductImport.setReturnMsg("");
								} else {
									//失败原因
									deductImport.setReturnMsg(deductImport.getReturnCode()+":"+deductImport.getReturnMsg());
									deductImport.setReturnCode(CounterofferResult.PAYMENT_FAILED.getCode());
									//交易金额为0
									deductImport.setTrustAmount("0");
								}
								LoanDeductEntity iteratorSplit = deductPaybackService.setLoanDeductEntity(deductImport);
								PaybackOpeEx paybackOpes = null;
								if(flag){
									 paybackOpes = new PaybackOpeEx(iteratorSplit.getBatId(),
											iteratorSplit.getBusinessId(), RepaymentProcess.LINE__TRANSFER,
												TargetWay.REPAYMENT,
												PaybackOperate.ONLINE_TRANSFER_SUCCESS.getCode(),"划拨" + ":" + iteratorSplit.getDeductSucceedMoney());
								}else{
									 paybackOpes = new PaybackOpeEx(iteratorSplit.getBatId(),
											iteratorSplit.getBusinessId(), RepaymentProcess.LINE__TRANSFER,
												TargetWay.REPAYMENT,
												PaybackOperate.ONLINE_TRANSFER_FAILED.getCode(),deductImport.getReturnMsg());
								}
								paybackSplitService.insertPaybackOpe(paybackOpes);
								// 更新回款信息
								if (iteratorSplit != null) {
									deductUpdateService.updateDeductInfo(iteratorSplit);
								}
						  }
						  else{
							  rtnMsg += "【"+params[0]+"】导入数据不正确;";
						  }
						}else{
							rtnMsg += "【"+params[0]+"】导入数据不正确;";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("待还款划扣列表划拨导入失败",e);
			rtnMsg = "文件导入失败";
		}
		return rtnMsg;
	}
	
	/**
	 * 线上委托充值
	 * 2016年3月13日
	 * By 朱杰
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "trushRechargeOnline", method = RequestMethod.POST)
	@ResponseBody
	public String trushRechargeOnline(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> map){
		String idVal = null;
		if (map.get("id") != null && StringUtils.isNotEmpty(map.get("id").toString())) {
			idVal = map.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		}
		map.put("model",LoanModel.TG.getCode());
		map.put("trustRecharge", TrustmentStatus.YWT.getCode());
		prepareSearchCondition(map);
		List<PaybackApply> paybackDeductList = deductPaybackService.findApplyPayback(map);
		String rtnMsg="";
		for(int i=0;i<paybackDeductList.size();i++){
			PaybackApply paybackApply = paybackDeductList.get(i);
			if(paybackApply.getApplyAmount().compareTo(new BigDecimal(1000000))==1){
				//大于100万，不处理
				rtnMsg+="【"+paybackApply.getContract().getContractCode()+"】的委托充值金额大于100万<br>";
			}else{
				try{
					//委托充值，并更新数据库
					rtnMsg+=deductPaybackService.trustRecharge(paybackApply);		
				}catch(Exception e){
					logger.error("【"+paybackApply.getContract().getContractCode()+"】委托充值操作失败",e);
					rtnMsg += "【"+paybackApply.getContract().getContractCode()+"】委托充值操作失败";
				}
						
			}
		}		
		return jsonMapper.toJson(rtnMsg);
	}
	
	/**
	 * 线上划拨
	 * 2016年3月14日
	 * By 朱杰
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "trustDeductOnline", method = RequestMethod.POST)
	@ResponseBody
	public String trustDeductOnline(Model model,HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> map){
		String rtnMsg="";
		String idVal = null;
		if (map.get("id") != null && StringUtils.isNotEmpty(map.get("id").toString())) {
			idVal = map.get("id").toString();
			map.remove("id");
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		}
		// 只导出Tg标示的
		map.put("model",LoanModel.TG.getCode());
		prepareSearchCondition(map);
		List<PaybackApply> paybackDeductList = deductPaybackService.findApplyPayback(map);
		for(int i=0;i<paybackDeductList.size();i++){
			try{
				rtnMsg += deductPaybackService.transferOnline(paybackDeductList.get(i));
			}catch(Exception e){
				e.printStackTrace();
				rtnMsg += "【"+paybackDeductList.get(i).getContractCode()+"】线上划拨操作失败;";
			}
			
		}	
		return jsonMapper.toJson(rtnMsg);
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
	
	
	@RequestMapping(value = "exportList")
	public void exportList(HttpServletRequest request,
			HttpServletResponse response, Model model,@RequestParam Map<String, Object> map) {
		prepareSearchCondition(map);
		String idVal = null;
		if (map.get("id") != null && StringUtils.isNotEmpty(map.get("id").toString())) {
			idVal = map.get("id").toString();
		}
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加
			map.put("ids", Arrays.asList(idVal.split(",")));
		}
		map.put("channelFlag", ChannelFlag.ZCJ.getCode());
		List<PaybackApply> paybackList = deductPaybackService
				.findApplyPayback(map);

		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(paybackList)){
			dictMap = DictCache.getInstance().getMap();
			for(PaybackApply pa:paybackList){
				if(pa.getPayback() != null){
					pa.getPayback().setDictPayStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.PAY_STATUS, pa.getPayback().getDictPayStatus()));
				}
				if(pa.getLoanBank() != null){
					pa.getLoanBank().setBankNameLabel(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, pa.getLoanBank().getBankName()));
				}
				pa.setSplitBackResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, pa.getSplitBackResult()));
				pa.setTrustRechargeResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, pa.getTrustRechargeResult()));
				if(pa.getLoanInfo() != null){
					pa.getLoanInfo().setDictLoanStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, pa.getLoanInfo().getDictLoanStatus()));
					pa.getLoanInfo().setLoanFlagLabel(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, pa.getLoanInfo().getLoanFlag()));
				}
				pa.setDictDealTypeLabel(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, pa.getDictDealType()));
				pa.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", pa.getModel()));
				pa.setTlSignLabel(DictUtils.getLabel(dictMap,"yes_no", pa.getTlSign()));
		  }
		}
		ExportDeductPaybackHelper.exportData(paybackList, response);
	}
	
	
	
	/**
	 * 判断滚动开始或者停止 2017年4月26日 By 翁私
	 * @return sys 
	 */
	@RequestMapping(value = "getSystemSetting")
	@ResponseBody
	public String getSystemSetting(){
		SystemSetting sys = new SystemSetting();
		sys.setSysFlag("SYS_REPAYMENT_DEDUCT");
		sys=paybackSplitService.getSystemSetting(sys);
		String sysValue  = sys.getSysValue();
		if (YESNO.YES.getCode().equals(sysValue)) {
			return "success";
		}
		return "fail";
	}
	
	/**
	 * 启动或者停止滚动划扣s
	 * 2017年4月26日
	 * By 翁私
	 * @param sys
	 * @return success
	 */
	@RequestMapping(value = "startOrstopRolls")
	@ResponseBody
	public String startOrstopRolls(SystemSetting sys){
		try{
			if(YESNO.YES.getCode().equals(sys.getSysValue())){
				deductPaybackService.startSystemSetting();
			}else if(YESNO.NO.getCode().equals(sys.getSysValue())){
				deductPaybackService.stopSystemSetting();
			}
		}catch(Exception e){
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	
	/**
	 * 查询通过配置规则过滤，没有发送的数据，然后提示业务
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDeductLimit")
	public String queryDeductLimit(){
		String userId = UserUtils.getUser().getId();
		int rolecount = paybackSplitService.selectRoleCount(userId);
		if(rolecount == 0 ){
			return rolecount+"";
		}
		String count = deductPaybackService.queryDeductLimit();
		if(count == null || "".equals(count)){
			count ="0";
		}
		return count;
	}
}
