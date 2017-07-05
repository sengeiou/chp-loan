package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.EarlySettlementExamService;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;

/**
 * 提前结清待审核列表业务处理Controller
 * 
 * @Class Name EarlySettlementExamController
 * @author zhaojinping
 * @Create In 2015年11月30日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/earlySettlement")
public class EarlySettlementExamController extends BaseController {

	@Autowired
	private EarlySettlementExamService earlySettlementExamService;
	@Autowired
	private PaybackSplitService paybackSplitService;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private HistoryService historyService;
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	@Autowired
	private RepaymentDateService dateService;

	/**
	 * 获取提前结清待审核列表 2016年1月6日 By zhaojinping
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackCharge
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String getAllList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			PaybackCharge paybackCharge) {
		BigDecimal bgSum = new BigDecimal("0.00");
		String storeId = paybackCharge.getStoreyc();
		if(storeId!= null && !"".equals(storeId)){
			paybackCharge.setStoreId(appendString(storeId));
		}
		// 设置冲抵申请表中的冲抵方式为 提前结清 3
		paybackCharge.setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
		// 设置冲抵申请表中的冲抵状态为 ‘1 冲抵待审核’
		paybackCharge.setChargeStatus(AgainstStatus.AGAINST_VERIFY.getCode());
        if(!ObjectHelper.isEmpty(paybackCharge.getUrgeServicesMoney())){
        	paybackCharge.getUrgeServicesMoney().setReturnLogo(YESNO.NO.getCode());
        }else{
        	UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
        	urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
        	paybackCharge.setUrgeServicesMoney(urgeServicesMoney);
        }
		Page<PaybackCharge> waitPage = earlySettlementExamService.getAllList(
				new Page<PaybackCharge>(request, response), paybackCharge);
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		List<Dict> dictList = DictCache.getInstance().getList();
		for(PaybackCharge pc:waitPage.getList()){
			if(ObjectHelper.isEmpty(pc.getUrgeServicesMoney())){
				UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
				urgeServicesMoney.setUrgeMoeny(bgSum);
				pc.setUrgeServicesMoney(urgeServicesMoney);
			}else{
				if(ObjectHelper.isEmpty(pc.getUrgeServicesMoney().getUrgeMoeny())){
					pc.getUrgeServicesMoney().setUrgeMoeny(bgSum);
				}
			}
			for(Dict dict:dictList){
				if(dict.getValue()==null){
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
				if("jk_contract_ver".equals(dict.getType()) && pc.getContract()!=null && dict.getValue().equals(pc.getContract().getContractVersion())){
					pc.getContract().setContractVersionLabel(dict.getLabel());
				}
				if("jk_loan_model".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getModel())){
					pc.getLoanInfo().setModelLabel(dict.getLabel());
				}
			}
		}
		model.addAttribute("waitPage", waitPage);
		return "borrow/payback/earlySettlement/earlySettlementExam";
	}

	/**
	 * 更新冲抵申请表中的冲抵状态为还款退回 2016年1月13日 By zhaojinping
	 * 
	 * @param request
	 * @param paybackCharge
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "updatePaybackChargeStatus")
	public String updateChargeStatus(HttpServletRequest request,
			PaybackCharge paybackCharge,@RequestParam(value = "pId", required = false) String pId) {
		String flag = request.getParameter("flag");
		PaybackOpeEx paybackOpes = null;
		boolean isSuccess = false;
			if (!ObjectHelper.isEmpty(paybackCharge)) {
				// 如果为0，代表借款人服务部退回
				if(YESNO.NO.getCode().equals(flag)){
					paybackCharge.setChargeStatus(AgainstStatus.PAYBACK_RETURN
							.getCode());
					// 向还款操作流水中插入记录
					paybackOpes = new PaybackOpeEx(paybackCharge.getId(), pId,
							RepaymentProcess.VERIFY, TargetWay.REPAYMENT, PaybackOperate.CHECK_FAILED.getCode(),
							paybackCharge.getReturnReason());
				}else{
					// 如果为1，代表风控部退回
					paybackCharge.setChargeStatus(AgainstStatus.RIS_PAYBACK_RETURN
							.getCode());
					// 向还款操作流水中插入记录
					paybackOpes = new PaybackOpeEx(paybackCharge.getId(), pId,
							RepaymentProcess.EARLY_SETTLED, TargetWay.REPAYMENT,  PaybackOperate.CONFIRM_FAILED.getCode(),
							paybackCharge.getReturnReason());
				}
				// 设置冲抵申请对象的冲抵状态为 ‘还款退回 4’,还款标示状态为‘还款退回’
				paybackCharge.preUpdate();
				Payback payback = new Payback();
				payback.setId(pId);
				payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
				payback.setModifyBy(UserUtils.getUser().getId());
				payback.setModifyTime(new Date());
				isSuccess = earlySettlementExamService
						.updatePaybackChargeStatus(paybackCharge,paybackOpes,payback);
				logger.debug("invoke EarlySettlementExamController method: updatePaybackResult, paramMap is: "
						+ paybackCharge);
			}
		return JsonMapper.nonDefaultMapper().toJson(isSuccess);
	}

	/**
	 * 获取历史页面信息 2016年1月6日 By zhaojinping
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "getHirstory")
	public String getHirstory(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<PaybackOpe> listOpe = null;
		String applyId = request.getParameter("applyId");
		if (StringUtils.isNotEmpty(applyId)) {
			String mainId = paybackSplitService.getMainId(applyId);
			listOpe = paybackSplitService.getAllHirstory(new Page<PaybackOpe>(
					request, response), mainId);
			model.addAttribute("listOpe", listOpe);
			model.addAttribute("applyId", applyId);
		}
		return "borrow/payback/earlySettlement/paybackHirstory";
	}

	/**
	 * 根据申请id,查询要在详情页面中要显示的信息 2016年1月13日 By zhaojinping
	 * 
	 * @param request
	 * @param paybackCharge
	 * @param model
	 * @return string
	 */
	@RequestMapping(value = "getEarlyBackApply")
	public String getEarlyBackApply(HttpServletRequest request,
			PaybackCharge paybackCharge, Model model) {
		String chargeId = request.getParameter("id");
		BigDecimal bgSum = new BigDecimal("0.00");
		model.addAttribute("id", chargeId);
    	paybackCharge.setId(chargeId);
    	UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
    	urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
    	paybackCharge.setUrgeServicesMoney(urgeServicesMoney);
		paybackCharge = earlySettlementExamService.getEarlyBackApply(paybackCharge);
		if(!ObjectHelper.isEmpty(paybackCharge)){
			if(ObjectHelper.isEmpty(paybackCharge.getUrgeServicesMoney())){
				urgeServicesMoney = new UrgeServicesMoney();
				urgeServicesMoney.setUrgeMoeny(bgSum);
				paybackCharge.setUrgeServicesMoney(urgeServicesMoney);
			}else{
				if(ObjectHelper.isEmpty(paybackCharge.getUrgeServicesMoney().getUrgeMoeny())){
					paybackCharge.getUrgeServicesMoney().setUrgeMoeny(bgSum);
				}
			}
			BigDecimal settleTotalAmount = paybackCharge.getSettleTotalAmount();
			BigDecimal applyBuleAmount = paybackCharge.getApplyBuleAmount();
			PaybackMonth paybackMonth = paybackCharge.getPaybackMonth();
			if(settleTotalAmount.compareTo(applyBuleAmount) > Integer.parseInt(YESNO.NO.getCode())){
				if(!ObjectHelper.isEmpty(paybackMonth)){
					paybackMonth.setCreditAmount(settleTotalAmount.subtract(applyBuleAmount));
				}
			}else{
				if(!ObjectHelper.isEmpty(paybackMonth)){
					paybackMonth.setCreditAmount(new BigDecimal("0.00"));
				}
			}
			// 设置列表中的门店名称的显示
			if (paybackCharge.getLoanInfo() == null) {
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setLoanStoreOrgId(null);
				paybackCharge.setLoanInfo(loanInfo);
			} else {
				paybackCharge.getLoanInfo().setLoanStoreOrgId(
						String.valueOf(OrgCache.getInstance()
							.get(paybackCharge.getLoanInfo()
								.getLoanStoreOrgId())));
				}
			// 设置产品类型
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductType(paybackCharge.getLoanInfo().getProductType());
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			paybackCharge.getLoanInfo().setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
			//PaybackMonth paybackMonth = paybackCharge.getPaybackMonth();
//			if (!ObjectHelper.isEmpty(paybackMonth)) {
//				PaybackMonth paybackMonthSum = earlySettlementExamService.getPenaltyPunishSum(paybackCharge.getContractCode());
//				if(!ObjectHelper.isEmpty(paybackMonthSum)){
//					paybackMonth.setMonthPenaltyReductionSum(paybackMonthSum.getMonthPenaltyReductionSum());
//					paybackMonth.setMonthPunishReductionSum(paybackMonthSum.getMonthPunishReductionSum());
//				}
//			 }
			if(!ObjectHelper.isEmpty(paybackCharge.getContract())){
				String contractVersion=DictCache.getInstance().getDictLabel("jk_contract_ver",paybackCharge.getContract().getContractVersion());
				paybackCharge.getContract().setContractVersionLabel(contractVersion);
			}
			if(!ObjectHelper.isEmpty(paybackCharge.getLoanInfo())){
				String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",paybackCharge.getLoanInfo().getDictLoanStatus());
				paybackCharge.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			}
			 model.addAttribute("paybackCharge", paybackCharge);
			}
		return "borrow/payback/earlySettlement/earlySettlementExamDetails";
	}

	/**
	 * 更改还款主表中的还款状态为 ‘提前结清待确认 6’ 更改冲抵申请表中的冲抵状态为‘冲抵待确认 2’ 2016年1月6日 By
	 * zhaojinping
	 * 
	 * @param id
	 * @param chargeId
	 * @param returnReason
	 * @return redirect page
	 */
	@ResponseBody
	@RequestMapping(value = "updatePayBackStatus")
	public String updatePaybackStatus(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "chargeId", required = false) String chargeId,
			@RequestParam(value = "returnReason", required = false) String returnReason) {
                // 客户没有未还的违约金罚息
				PaybackCharge paybackCharge = new PaybackCharge();
				// 更新还款状态
				Payback payback = new Payback();
				payback.setId(id);
				payback.setModifyBy(UserUtils.getUser().getId());// 获取当前登录用户
				payback.setModifyTime(new Date());
				payback.setDictPayStatus(RepayStatus.PRE_SETTLE_CONFIRM.getCode());
				payback.preUpdate();
				 // 更新还款冲抵期供表的冲抵状态
				paybackCharge.setId(chargeId);
				paybackCharge.setChargeStatus(AgainstStatus.AGAINST_CONFIRM
						.getCode());
				paybackCharge.setReturnReason(returnReason);
				paybackCharge.setModifyBy(UserUtils.getUser().getId());// 获取当前登录用户
				paybackCharge.setModifyTime(new Date());
				paybackCharge.preUpdate();
				// 向还款操作流水中插入历史记录
				PaybackOpeEx paybackOpes = new PaybackOpeEx(chargeId,
						payback.getId(), RepaymentProcess.VERIFY,
						TargetWay.REPAYMENT,
						PaybackOperate.CHECK_SUCCEED.getCode(),
						returnReason);
				earlySettlementExamService
				.updatePaybackChargeStatus(paybackCharge,paybackOpes,payback);
		return "redirect:" + adminPath + "/borrow/payback/earlySettlement/list";
	}
	
	/**
	 * 
	 * 2016年2月24日
	 * By zhaojinping
	 * @param request
	 * @param response
	 * @param docId
	 */
	@RequestMapping(value = "downZip")
	public String downZip(HttpServletRequest request, HttpServletResponse response ,String docId,String fileName,String id, RedirectAttributes redirectAttributes) {
		try {
			fileName = new String(fileName.getBytes(), "iso8859-1");
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName.replace(" ", ""));
			DmService dmService = DmService.getInstance();
			dmService.download(response.getOutputStream(), docId);
			return null;
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "文件不存在或下载异常");
			return "redirect:" + adminPath + "/borrow/payback/earlySettlement/getEarlyBackApply?id=" + id;
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
}
