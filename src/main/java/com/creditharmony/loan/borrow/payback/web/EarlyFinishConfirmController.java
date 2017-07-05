package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.ConfirmPaybackService;
import com.creditharmony.loan.borrow.payback.service.EarlyFinishConfirmService;
import com.creditharmony.loan.borrow.payback.service.EarlySettlementExamService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.excel.SXXExcel;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.sync.data.fortune.ForuneSyncCreditorService;

/**
 * 提前结清确认列表页面的Controller
 * 
 * @Class Name EarlyFinishConfirmController
 * @author zhaojinping
 * @Create In 2015年12月24日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/earlyFinishConfirm")
public class EarlyFinishConfirmController extends BaseController {
    
	public final static String MONTH_ZERO = "0.00";
	@Autowired
	private ConfirmPaybackService confirmPaybackService;
	@Autowired
	private EarlyFinishConfirmService earlyFinishConfirmService;
	@Autowired
	private EarlySettlementExamService earlySettlementExamService;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private ForuneSyncCreditorService foruneSyncCreditorService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepaymentDateService dateService;

	/**
	 * 获取提前结清确认列表页面 2015年12月24日 By zhaojinping
	 * 
	 * @param request
	 * @param response
	 * @param payback
	 * @param model
	 * @return page
	 * @throws ParseException 
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, PaybackCharge paybackCharge,Model model) throws ParseException {
		if(paybackCharge.getStoreId()!=null&&!"".equals(paybackCharge.getStoreId())){
			paybackCharge.setOrgList(paybackCharge.getStoreId().split(","));
		}
/*		String storeId = paybackCharge.getStoreyc();
		if (storeId != null && !"".equals(storeId)) {
			paybackCharge.setStoreId(appendString(storeId));
		}*/
		
		/*if(!ObjectHelper.isEmpty(paybackCharge.getBeginDate())){
			if(ObjectHelper.isEmpty(paybackCharge.getEndDate())){
				paybackCharge.setEndDate(new Date());
			}else{
				String endDateStr = DateUtils.formatDate(paybackCharge.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				paybackCharge.setEndDate(endDate);
			}
		}else{
			if(!ObjectHelper.isEmpty(paybackCharge.getEndDate())){
				String endDateStr = DateUtils.formatDate(paybackCharge.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+ " 23:59:59");
				paybackCharge.setEndDate(endDate);
			}
		}*/
	/*	// 设置冲抵申请表中的冲抵方式为 提前结清 3
		paybackCharge.setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
		// 设置冲抵申请表中的冲抵状态为提前结清待确认 2
		paybackCharge.setChargeStatus(AgainstStatus.AGAINST_CONFIRM.getCode());
		// 设置查询有效的催收服务费金额
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
		paybackCharge.setUrgeServicesMoney(urgeServicesMoney);*/
		Page<PaybackCharge> waitPage = earlyFinishConfirmService.findPayback(new Page<PaybackCharge>(request, response), paybackCharge);
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		/*List<Dict> dictList = DictCache.getInstance().getList();
		for (PaybackCharge pc : waitPage.getList()) {
           for(Dict dict:dictList){
        	   if(dict.getValue() == null){
        		   continue;
        	   }
        	   if("jk_loan_apply_status".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getDictLoanStatus())){
					pc.getLoanInfo().setDictLoanStatusLabel(dict.getLabel());
				}
				if("jk_repay_type".equals(dict.getType()) && dict.getValue().equals(pc.getDictOffsetType())){
					pc.setDictOffsetTypeLabel(dict.getLabel());
				}
				if("jk_repay_status".equals(dict.getType()) && pc.getPayback()!=null && dict.getValue().equals(pc.getPayback().getDictPayStatus())){
					pc.getPayback().setDictPayStatusLabel(dict.getLabel());
				}
				if("jk_channel_flag".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getLoanFlag())){
					pc.getLoanInfo().setLoanFlagLabel(dict.getLabel());
				}
				
				if("jk_telemarketing".equals(dict.getType()) && dict.getValue()!=null && pc.getLoanCustomer()!=null && dict.getValue().equals(pc.getLoanCustomer().getCustomerTelesalesFlag())){
					pc.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getLabel());
				}
				if("jk_contract_ver".equals(dict.getType()) && pc.getContract()!=null && dict.getValue().equals(pc.getContract().getContractVersion())){
					pc.getContract().setContractVersionLabel(dict.getLabel());
				}
				if("jk_loan_model".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getModel())){
					pc.getLoanInfo().setModelLabel(dict.getLabel());
				}
             }
		}*/
		model.addAttribute("paybackCharge",paybackCharge);
		model.addAttribute("waitPage", waitPage);
		return "borrow/payback/earlySettlement/earlyFinishConfirm";
	}

	/**
	 * 通过合同编号显示提前结清的详情页面 2015年12月25日 By zhaojinping
	 * @param contarctCode
	 * @param model
	 * @return paybackCharge
	 */
	@RequestMapping(value = "getPaybackInfo")
	public String getPaybackInfo(PaybackCharge paybackCharge, Model model) {
		/*paybackCharge.setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
		paybackCharge.setChargeStatus(AgainstStatus.AGAINST_CONFIRM.getCode());
		paybackCharge.setId(chargeId);
		// 设置查询有效的催收服务费金额
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
		paybackCharge.setUrgeServicesMoney(urgeServicesMoney);*/
		PaybackCharge paybackChargeInfo = earlyFinishConfirmService.findPayback(paybackCharge);
		/*if(!ObjectHelper.isEmpty(paybackChargeInfo.getPaybackMonth())){
			paybackChargeInfo.getPaybackMonth().setReductionBy(UserUtils.getUser().getId());
		}
		if(!ObjectHelper.isEmpty(paybackChargeInfo.getLoanInfo())){
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",paybackChargeInfo.getLoanInfo().getDictLoanStatus());
			paybackChargeInfo.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
		}*/
		model.addAttribute("paybackCharge", paybackChargeInfo);
		return "borrow/payback/earlySettlement/earlyFinishConfirmDetails";
	}

	/**
	 * 保存提前结清详情页面信息 2015年12月28日 By zhaojinping
	 * 
	 * @param payback
	 * @param request
	 * @param model
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public String saveEarlyFinishInfo(PaybackCharge paybackCharge,
			HttpServletRequest request, Model model,@RequestParam(value = "valBack", required = false) String valBack,
			@RequestParam(value = "val", required = false) String val) {
		String msg = "";
		// 风控部提前结清时输入的返款金额
		// 审核意见
		String applyBackMes = request.getParameter("applyBackMes");
		// '0'表示确认提前结清
		Payback payback = paybackCharge.getPayback();
		payback.setContractCode(paybackCharge.getContractCode());
		payback.setContract(paybackCharge.getContract());
		if (YESNO.NO.getCode().equals(val)) {
			try{
				msg = earlyFinishConfirmService.earlyFininshConfirm( paybackCharge, payback, valBack, applyBackMes);
			}catch(Exception e){
				logger.error("Excetpion:", e);
				if(("与财富通讯接口异常！").equals(e.getMessage())){
					msg = "5";
					return msg;
				}else{
					msg = "6";
					return msg;
				}
			}
		} else {
			// 如果为1，代表风控部退回
			paybackCharge.setChargeStatus(AgainstStatus.RIS_PAYBACK_RETURN.getCode());
			// 向还款操作流水中插入记录
			PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackCharge.getId(), payback.getId(),
					RepaymentProcess.EARLY_SETTLED, TargetWay.REPAYMENT,  PaybackOperate.CONFIRM_FAILED.getCode(),
					paybackCharge.getReturnReason());
			paybackCharge.preUpdate();
			payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
			earlySettlementExamService.updatePaybackChargeStatus(paybackCharge,paybackOpes,payback);
			msg = "0";
		}
		return msg;
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
		idArray = ids.split(",");
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
			if (i == 0){
				parameter.append("'" +id +"'");
			}else {
				parameter.append(",'" +id + "'");
			}
		}
		return parameter.toString();
	}
	
	
	/**
	 * 获取提前结清确认列表页面 2015年12月24日 By zhaojinping
	 * 
	 * @param request
	 * @param response
	 * @param payback
	 * @param model
	 * @return page
	 * @throws ParseException 
	 */
	@RequestMapping(value = "excelList")
	public void excel(HttpServletRequest request,
			HttpServletResponse response, PaybackCharge paybackCharge,Model model) throws ParseException {
		if(paybackCharge.getStoreId()!=null&&!"".equals(paybackCharge.getStoreId())){
			paybackCharge.setOrgList(paybackCharge.getStoreId().split(","));
		}
		List<PaybackCharge> pageList = earlyFinishConfirmService.findAllPayback(paybackCharge);
		SXXExcel excel = new SXXExcel("提前结清确认列表", new String[]{"门店名称","合同编号","客户姓名","合同金额","已催收服务费金额","放款金额","批借期数","首期还款日","最长逾期天数","未还违约金罚息总额","提前结清应还款总额","申请还款金额","还款账单日","申请提前结清日期","借款状态","减免金额","渠道","模式","是否电销"});
		for(PaybackCharge item : pageList)
		{
			excel.addSXSSFRow(new Object[]{
					item.getLoanInfo().getLoanStoreOrgName(),item.getContractCode(),item.getLoanCustomer().getCustomerName(),item.getContract().getContractAmount(),
					item.getUrgeServicesMoney()==null?"0.00":item.getUrgeServicesMoney().getUrgeDecuteMoeny(),
					item.getLoanGrant().getGrantAmount(),item.getContract().getContractMonths(),item.getContract().getContractReplayDay(),
					item.getPayback().getPaybackMaxOverduedays(),item.getPenaltyTotalAmount(), item.getSettleTotalAmount().add(item.getPenaltyTotalAmount()),item.getApplyBuleAmount(),
					item.getPayback().getPaybackDay(),item.getCreateTime(),item.getLoanInfo().getDictLoanStatusLabel(),
					item.getSettleTotalAmount().add(item.getPenaltyTotalAmount()).subtract(item.getPayback().getPaybackBackAmount()).compareTo(BigDecimal.ZERO)==-1? "0.00"  : item.getSettleTotalAmount().add(item.getPenaltyTotalAmount()).subtract(item.getPayback().getPaybackBackAmount()),
					item.getLoanInfo().getLoanFlagLabel(),
					item.getLoanInfo().getModelLabel(),
					item.getLoanCustomer().getCustomerTelesalesFlagLabel()
			});
		}
		excel.outputExcel(response, "提前结清确认列表_"+DateUtil.DateToString(new Date(), "yyyyMMdd")+".xls");
	}
}
