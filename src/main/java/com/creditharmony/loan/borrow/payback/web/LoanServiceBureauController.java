package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.Eletric;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.LoanServiceBureauEx;
import com.creditharmony.loan.borrow.payback.service.LoanServiceBureauService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * @Class 控制器支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/loanServices")
public class LoanServiceBureauController extends BaseController {

	@Autowired
	private LoanServiceBureauService loanServiceBureauHavaToService;
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	@Autowired
	private RepaymentDateService dateService;
	
    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);


	/**
	 * 集中划扣申请已办列表页面
	 * 2016年3月29日
	 * By zhaojinping
	 * @param request
	 * @param response
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "centerApplyHaveToList")
	public String centerApplyHaveToList(HttpServletRequest request,HttpServletResponse response,Model model,LoanServiceBureauEx loanServiceBureau){
		String stores = loanServiceBureau.getStore();
		if (!ObjectHelper.isEmpty(stores)) {
			loanServiceBureau.setStoreId(FilterHelper.appendIdFilter(stores));
		}
		//loanServiceBureau.setEnumOne(OperateType.COLLECT_DEDUCT.getCode());
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.centerApplyHaveToList(new Page<LoanServiceBureauEx>(request, response),loanServiceBureau);
		// 设置‘还款类型’字段 为 ‘集中划扣’
		for (int i = 0; i < waitPage.getList().size(); i++) {
			waitPage.getList().get(i).setDictPayUse(RepayType.COLLECTION_PAYMET.getName());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		List<Dict> dictList = DictCache.getInstance().getList();
		for(LoanServiceBureauEx ls:waitPage.getList()){
			for(Dict dict:dictList){
				// 还款状态
					if("jk_repay_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictPayStatus())){
						ls.setDictPayStatusLabel(dict.getLabel());
				}
				// 借款状态
				if("jk_loan_apply_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictLoanStatus())){
					ls.setDictLoanStatusLabel(dict.getLabel());
				}
				// 标识
				if("jk_channel_flag".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getLoanMark())){
					ls.setLoanMarkLabel(dict.getLabel());
				}
				// 划扣平台
				if("jk_deduct_plat".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictDealType())){
					ls.setDictDealTypeLabel(dict.getLabel());
				}
				// 回盘结果
				if("jk_counteroffer_result".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getSplitBackResult())){
					ls.setSplitBackResult(dict.getLabel());
				}
				
				if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getModel())){
					ls.setModelLabel(dict.getLabel());
				}
			}
		}
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage); 
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: centerApplyHaveToList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/loanServiceBureau";
	}
	
	/**
	 * 借款人服务部待提前结清确认已办列表页面
	 * 2016年3月29日
	 * By zhaojinping
	 * @param request;
	 * @param response
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "earlyExamHavetoList")
	public String earlyExamHavetoList(HttpServletRequest request,HttpServletResponse response,Model model,LoanServiceBureauEx loanServiceBureau){
		String stores = loanServiceBureau.getStore();
		if (!ObjectHelper.isEmpty(stores)) {
			loanServiceBureau.setStoreId(FilterHelper.appendIdFilter(stores));
		}
		loanServiceBureau.setEnumTwo(RepayType.EARLY_SETTLE.getCode());
		loanServiceBureau.setChargeStatus("'" + AgainstStatus.AGAINST_CONFIRM.getCode() + "','"
				+ AgainstStatus.AIAINST.getCode() + "','"
				+ AgainstStatus.PAYBACK_RETURN.getCode() + "','"
				+ AgainstStatus.RIS_PAYBACK_RETURN.getCode()+"'");
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.earlyExamHavetoList(new Page<LoanServiceBureauEx>(request, response),loanServiceBureau);
		// 设置‘还款类型’字段 为 ‘提前结清’
		for (int i = 0; i < waitPage.getList().size(); i++) {
			waitPage.getList().get(i).setDictPayUse(RepayType.EARLY_SETTLE.getName());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		List<Dict> dictList = DictCache.getInstance().getList();
		for(LoanServiceBureauEx ls:waitPage.getList()){
			for(Dict dict : dictList){
				// 借款状态
				if("jk_loan_apply_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictLoanStatus())){
					ls.setDictLoanStatusLabel(dict.getLabel());
				}
				// 还款状态
				if("jk_repay_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictPayStatus())){
					ls.setDictPayStatusLabel(dict.getLabel());
				}
				// 标识
				if("jk_channel_flag".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getLoanMark())){
					ls.setLoanMarkLabel(dict.getLabel());
				}
				// 划扣平台
				if("jk_deduct_plat".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getDictDealType())){
					ls.setDictDealTypeLabel(dict.getLabel());
				}
				if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(ls.getModel())){
					ls.setModelLabel(dict.getLabel());
				}
			}
		}
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: earlyExamHavetoList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/earlyFinishHaveTo";
	}

	/**
	 * 门店已办页面
	 * 2015年12月9日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	//TODO 添加失败原因列
	@RequestMapping(value = "allStoresAlreadyDoList")
	public String allStoresAlreadyDoList(HttpServletRequest request,HttpServletResponse response,Model model,
			LoanServiceBureauEx loanServiceBureau) {
		if(ObjectHelper.isEmpty(loanServiceBureau.getCreateTime()) && StringUtils.isEmpty(loanServiceBureau.getCustomerName())  
				&& StringUtils.isEmpty(loanServiceBureau.getCustomerName())
				&& StringUtils.isEmpty(loanServiceBureau.getContractCode())
				&& StringUtils.isEmpty(loanServiceBureau.getSplitBackResult())
				&& StringUtils.isEmpty(loanServiceBureau.getDictPayUse())
				&& StringUtils.isEmpty(loanServiceBureau.getRepaymentDate())
				&& StringUtils.isEmpty(loanServiceBureau.getDictSourceType())
				&& StringUtils.isEmpty(loanServiceBureau.getLoanMark())
				&& StringUtils.isEmpty(loanServiceBureau.getCustomerTelesalesFlag())
				&& StringUtils.isEmpty(loanServiceBureau.getDictRepayMethod())
				&& StringUtils.isEmpty(loanServiceBureau.getModel())
				&& StringUtils.isEmpty(loanServiceBureau.getDictPayStatus())
				&& (!ObjectHelper.isNotEmpty(loanServiceBureau.getBeginDate()))
				/*&& ObjectHelper.isNotEmpty(loanServiceBureau.getEndDate())*/
				)
		{
			loanServiceBureau.setCreateTime(new Date());
		}
		
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("menuId",menuId);
		
		String dictPayStatusCode = "";
		loanServiceBureau.setEnumTwo(RepayType.EARLY_SETTLE.getCode());
		loanServiceBureau.setDictPaybackStatus("'"+ RepayApplyStatus.SPLIT.getCode()+"',"+"'"+ RepayApplyStatus.SPLITED.getCode()+"',"+ "'"+ RepayApplyStatus.TO_PAYMENT.getCode()+"'");
		if(!ObjectHelper.isEmpty(loanServiceBureau) && !ObjectHelper.isEmpty(loanServiceBureau.getDictPayStatus())){
		    dictPayStatusCode = loanServiceBureau.getDictPayStatus();
			if(RepayApplyStatus.REPAYMENT_RETURN.getCode().equals(dictPayStatusCode)){
				loanServiceBureau.setDictPayStatus("'"+ RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayStatus.PAYBACK_RETURN.getCode()+"'");
			}else{
				loanServiceBureau.setDictPayStatus("'"+ dictPayStatusCode + "'");
			}
		}
		//  数据权限控制
		String  queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		loanServiceBureau.setQueryRight(queryRight);
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.allStoresAlreadyDoList(new Page<LoanServiceBureauEx>(request, response),loanServiceBureau);
		//页面转码
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(waitPage.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (LoanServiceBureauEx ex : waitPage.getList()) {
				ex.setCustomerTelesalesFlagLabel(DictUtils.getLabel(dictMap,LoanDictType.TELEMARKETING, ex.getCustomerTelesalesFlag()));
				ex.setLoanMarkLabel(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, ex.getLoanMark()));
				ex.setSplitBackResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, ex.getSplitBackResult()));
				ex.setDictLoanStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.LOAN_APPLY_STATUS, ex.getDictLoanStatus()));
				ex.setDictPayUseLabel(DictUtils.getLabel(dictMap,LoanDictType.REPAY_TYPE, ex.getDictPayUse()));
			    ex.setDictRepayMethodLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Channel, ex.getDictRepayMethod()));
			    ex.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", ex.getModelLabel()));
			    
			    // 如果还款类型为还款申请，还款状态取还款申请表中的还款申请状态
                if(RepayChannel.DEDUCT.getCode().equals(ex.getDictRepayMethod()) || RepayChannel.NETBANK_CHARGE.getCode().equals(ex.getDictRepayMethod())){
                	ex.setDictPaybackStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Apply_Status, ex.getDictPaybackStatus()));
                }else{
                // 如果是提前结清或结清，还款状态取还款主表中的还款状态
                	ex.setDictPaybackStatusLabel(DictUtils.getLabel(dictMap, "jk_charge_against_status", ex.getDictPaybackStatus()));
                }
			    // 模式
				String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
				ex.setModelLabel(dictLoanModel);
			}
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		loanServiceBureau.setDictPayStatus(dictPayStatusCode);
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: allStoresAlreadyDoList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/stresAlready";
	}
	
	
	/**
	 * 电销门店已办页面
	 * 2017年03月02日
	 * By 翁私
	 * @param request
	 * @param response
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "phoneSalestoresAlreadyDoList")
	public String phoneSalestoresAlreadyDoList(HttpServletRequest request,HttpServletResponse response,Model model,
			LoanServiceBureauEx loanServiceBureau) {
		
		// 是否电销 1 是
		loanServiceBureau.setPhoneSaleSign("1");
		String dictPayStatusCode = "";
		loanServiceBureau.setEnumTwo(RepayType.EARLY_SETTLE.getCode());
		loanServiceBureau.setDictPaybackStatus("'"+ RepayApplyStatus.SPLIT.getCode()+"',"+"'"+ RepayApplyStatus.SPLITED.getCode()+"',"+ "'"+ RepayApplyStatus.TO_PAYMENT.getCode()+"'");
		if(!ObjectHelper.isEmpty(loanServiceBureau) && !ObjectHelper.isEmpty(loanServiceBureau.getDictPayStatus())){
		    dictPayStatusCode = loanServiceBureau.getDictPayStatus();
			if(RepayApplyStatus.REPAYMENT_RETURN.getCode().equals(dictPayStatusCode)){
				loanServiceBureau.setDictPayStatus("'"+ RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayStatus.PAYBACK_RETURN.getCode()+"'");
			}else{
				loanServiceBureau.setDictPayStatus("'"+ dictPayStatusCode + "'");
			}
		}
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.allStoresAlreadyDoList(new Page<LoanServiceBureauEx>(request, response),loanServiceBureau);
		//页面转码
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(waitPage.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (LoanServiceBureauEx ex : waitPage.getList()) {
				ex.setCustomerTelesalesFlagLabel(DictUtils.getLabel(dictMap,LoanDictType.TELEMARKETING, ex.getCustomerTelesalesFlag()));
				ex.setLoanMarkLabel(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, ex.getLoanMark()));
				ex.setSplitBackResultLabel(DictUtils.getLabel(dictMap,LoanDictType.COUNTEROFFER_RESULT, ex.getSplitBackResult()));
				ex.setDictLoanStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.LOAN_APPLY_STATUS, ex.getDictLoanStatus()));
				ex.setDictPayUseLabel(DictUtils.getLabel(dictMap,LoanDictType.REPAY_TYPE, ex.getDictPayUse()));
			    ex.setDictRepayMethodLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Channel, ex.getDictRepayMethod()));
			    ex.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", ex.getModelLabel()));
			    
			    // 如果还款类型为还款申请，还款状态取还款申请表中的还款申请状态
                if(RepayChannel.DEDUCT.getCode().equals(ex.getDictRepayMethod()) || RepayChannel.NETBANK_CHARGE.getCode().equals(ex.getDictRepayMethod())){
                	ex.setDictPaybackStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.Repay_Apply_Status, ex.getDictPaybackStatus()));
                }else{
                // 如果是提前结清或结清，还款状态取还款主表中的还款状态
                	ex.setDictPaybackStatusLabel(DictUtils.getLabel(dictMap, "jk_charge_against_status", ex.getDictPaybackStatus()));
                }
			    // 模式
				String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
				ex.setModelLabel(dictLoanModel);
			}
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		loanServiceBureau.setDictPayStatus(dictPayStatusCode);
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: allStoresAlreadyDoList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/stresAlreadyPhoneSale";
	}
	
	

	/**
	 * 门店已办 查看页面
	 * 2015年12月9日
	 * By 李强
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "seeStoresAlreadyDo")
	public String seeStoresAlreadyDo(Model model,LoanServiceBureauEx loanServiceBureau) {
		List<LoanServiceBureauEx> slist = null;
		// 如果还款类型为3：提前结清，则返回门店已办提前结清查看页面，否则返回门店非提前结清查看页面
		if (loanServiceBureau.getDictPayUse().equals(DeductedConstantEx.ALSO_THREE)) {
			LoanServiceBureauEx loanServiceBureaus = loanServiceBureauHavaToService
					.seeStoresAlreadyDo(loanServiceBureau);  
			nullJudgment(slist, loanServiceBureaus);
			// 设置减免的提前结清金额
		    BigDecimal settleTotalAmount = loanServiceBureaus.getSettleTotalAmount();
			BigDecimal applyBuleAmount = loanServiceBureaus.getApplyBuleAmount();
			if(settleTotalAmount.compareTo(applyBuleAmount) > Integer.parseInt(YESNO.NO.getCode())){
				loanServiceBureaus.setReductionAmount(settleTotalAmount.subtract(applyBuleAmount));
			}else{
				loanServiceBureaus.setReductionAmount(new BigDecimal("0.00"));
			}		
			// 减免金额=减免滞纳金 + 减免罚息
			BigDecimal reductionAmount = loanServiceBureaus.getMonthLateFeeReduction().add(loanServiceBureaus.getMonthPunishReduction());
			//loanServiceBureaus.setReductionAmount(reductionAmount);
			// 未还违约金(滞纳金)及罚息总金额=(应还滞纳金+应还罚息) - (实还滞纳金 + 实还罚息) -(减免滞纳金+减免罚息)
			BigDecimal bigDecimals = loanServiceBureaus.getMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishshould());
			BigDecimal bigDecimal = loanServiceBureaus.getActualMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishactual());
			loanServiceBureaus.setNotPunishViolate(bigDecimals.subtract(bigDecimal).subtract(reductionAmount));
			// 提前结清应还总金=提前结清金额 + (未还违约金(滞纳金)及罚息总额)
			loanServiceBureaus.setInterestforeFinishAmount(loanServiceBureaus.getMonthBeforeFinishAmount().add(loanServiceBureaus.getNotPunishViolate()));
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(loanServiceBureaus.getProductType());
		    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		    loanServiceBureaus.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
		    String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",loanServiceBureaus.getDictLoanStatus());
		    loanServiceBureaus.setDictLoanStatusLabel(dictLoanStatus);
		    model.addAttribute("stores", loanServiceBureaus);
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ loanServiceBureaus);
			return "borrow/payback/repayment/seeStoresAlready";
		} else {
			LoanServiceBureauEx loanServiceBureaus = loanServiceBureauHavaToService.seeStoresAlreadyDos(loanServiceBureau);
			//判断是不是POS机刷卡查账
			//如果是 去查POS信息从表
			List<PaybackTransferInfo> payBackTransferInfoPos = loanServiceBureauHavaToService.seePayBackTransPos(loanServiceBureau.getIds());
			List<PaybackTransferInfo> payBackTransferInfo = loanServiceBureauHavaToService.seePayBackTrans(loanServiceBureau.getIds());
			nullJudgment(slist, loanServiceBureaus);
			// 本次应还款金额=应还本金 + 应还利息 + 应还滞纳金 + 应还罚息
			BigDecimal bigDecimals1 = loanServiceBureaus.getMonthPayMoney().add(loanServiceBureaus.getMonthInterestBackshould());
			BigDecimal bigDecimals2 = loanServiceBureaus.getMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishshould());
			loanServiceBureaus.setShouldLoanAmount(bigDecimals1.add(bigDecimals2));
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(loanServiceBureaus.getProductType());
		 //   List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		    //loanServiceBureaus.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
			 String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",loanServiceBureaus.getDictLoanStatus());
			 loanServiceBureaus.setDictLoanStatusLabel(dictLoanStatus);
			 String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",loanServiceBureaus.getLoanMark());
			 loanServiceBureaus.setLoanMarkLabel(loanMark);
			 String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",loanServiceBureaus.getDictDealType());
			 loanServiceBureaus.setDictDealTypeLabel(dictDealType);
			 String dictPosType=DictCache.getInstance().getDictLabel("jk_pos",loanServiceBureaus.getDictPosType());
			 loanServiceBureaus.setDictPosTypeLabel(dictPosType);

			// 开户行
			if(ObjectHelper.isNotEmpty(loanServiceBureaus) && StringUtils.isNotEmpty(loanServiceBureaus.getApplyBankName())){
				String bankNameLabel = DictCache.getInstance().getDictLabel("jk_open_bank", loanServiceBureaus.getApplyBankName());
				if(StringUtils.isNotEmpty(bankNameLabel)){
					loanServiceBureaus.setApplyBankName(bankNameLabel);
				}
			}
			 model.addAttribute("stores", loanServiceBureaus);
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ loanServiceBureaus);
			if(StringUtils.equals(loanServiceBureaus.getDictRepayMethod(), RepayChannel.POS_CHECK.getCode())){
				for(PaybackTransferInfo pt:payBackTransferInfoPos){
					String dictDepositPosCard=DictCache.getInstance().getDictLabel("jk_pos",pt.getDictDepositPosCard());
					pt.setDictDepositPosCardLabel(dictDepositPosCard);
				}
				model.addAttribute("payBackTransFif", payBackTransferInfoPos);
			}else{
				for(PaybackTransferInfo pt:payBackTransferInfo){
					String dictDepositPosCard=DictCache.getInstance().getDictLabel("jk_pos",pt.getDictDepositPosCard());
					pt.setDictDepositPosCardLabel(dictDepositPosCard);
					
					// 上传人
					String uploadId = pt.getUploadName();
					if(StringUtils.isNotEmpty(uploadId)){
						User uploadUser = UserUtils.get(uploadId);
						if(!ObjectHelper.isEmpty(uploadUser)){
							String uploadName = uploadUser.getName();
							pt.setUploadName(uploadName);
						}
					}
				}
				model.addAttribute("payBackTransFif", payBackTransferInfo);
			}
			
			if (StringUtils.isNotEmpty(loanServiceBureaus.getDictLoanStatus())) {
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel("jk_loan_apply_status",
						loanServiceBureaus.getDictLoanStatus());
				loanServiceBureaus.setDictLoanStatusLabel(dictLoanStatusLabel);
	    	}
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ payBackTransferInfo);
			return "borrow/payback/repayment/seeStoresAlreadys";
		}
	}

	/**
	 * 金额运算时非空判断
	 * 2015年12月9日
	 * By 李强
	 * @param slist
	 * @param loanServiceBureau
	 */
	public void nullJudgment(List<LoanServiceBureauEx> slist,
			LoanServiceBureauEx loanServiceBureau) {
		if (ArrayHelper.isNotEmpty(slist)) {
			for (int i = 0; i < slist.size(); i++) {
				// 申请还期总额
				if (slist.get(i).getApplyAmountPayback() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setApplyAmountPayback(bigDecimals);
				}
				// 申请违约金总额
				if (slist.get(i).getApplyAmountViolate() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setApplyAmountViolate(bigDecimals);
				}
				// 申请还罚息总额
				if (slist.get(i).getApplyAmountPunish() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setApplyAmountPunish(bigDecimals);
				}
				// 应还本金
				if (slist.get(i).getMonthPayMoney() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPayMoney(bigDecimals);
				}
				// 应还利息
				if (slist.get(i).getMonthInterestBackshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestBackshould(bigDecimals);
				}
				// 应还罚息
				if (slist.get(i).getMonthInterestPunishshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPunishshould(bigDecimals);
				}
				// 应还滞纳金
				if (slist.get(i).getMonthLateFee() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthLateFee(bigDecimals);
				}
			}
		}
		// 实还罚息
		if (loanServiceBureau.getMonthInterestPunishactual() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthInterestPunishactual(bigDecimal);
		}
		// 实还滞纳金
		if (loanServiceBureau.getActualMonthLateFee() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setActualMonthLateFee(bigDecimal);
		}

		// 提前结清金额
		if (loanServiceBureau.getMonthBeforeFinishAmount() == null) {
			BigDecimal bigDecimal2 = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthBeforeFinishAmount(bigDecimal2);
		}
		// 应还本金
		if (loanServiceBureau.getMonthPayMoney() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthPayMoney(bigDecimal);
		}
		// 应还利息
		if (loanServiceBureau.getMonthInterestBackshould() == null) {
			BigDecimal bigDecimal2 = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthInterestBackshould(bigDecimal2);
		}
		// 应还罚息
		if (loanServiceBureau.getMonthInterestPunishshould() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthInterestPunishshould(bigDecimal);
		}
		// 应还滞纳金
		if (loanServiceBureau.getMonthLateFee() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthLateFee(bigDecimals);
		}
		// 减免滞纳金
		if (loanServiceBureau.getMonthLateFeeReduction() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthLateFeeReduction(bigDecimal);
		}
		// 减免罚息
		if (loanServiceBureau.getMonthPunishReduction() == null) {
			BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			loanServiceBureau.setMonthPunishReduction(bigDecimal);
		}
	}
	//-----------------
	
	/**
	 * 电催已办页面
	 * 2016年2月25日
	 * By liushikang
	 * @param request
	 * @param response
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "allStoresElertDoList")
	public String allStoresElertDoList(HttpServletRequest request,HttpServletResponse response,Model model,
			LoanServiceBureauEx loanServiceBureau) {
		
		loanServiceBureau.setEnumTwo(RepayType.EARLY_SETTLE.getCode());
		loanServiceBureau.setUrgeManage(Eletric.ELETRIC.getCode());
		loanServiceBureau.setDictPaybackStatus("'"+ RepayApplyStatus.SPLIT.getCode()+"',"+"'"+ RepayApplyStatus.SPLITED.getCode()+"',"+ "'"+ RepayApplyStatus.TO_PAYMENT.getCode()+"',"+"'"+RepayApplyStatus.HAS_PAYMENT.getCode()+"'");

		//  数据权限控制
		String  queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		loanServiceBureau.setQueryRight(queryRight);
		Page<LoanServiceBureauEx> waitPage = loanServiceBureauHavaToService.allStoresAlreadyDoListEl(new Page<LoanServiceBureauEx>(request, response),loanServiceBureau);
	/*	for(int i = 0; i< waitPage.getList().size(); i++){
			// 如果还款类型=提前结清 && 回盘结果(冲抵状态)=还款退回 实际还款金额就 = 0
			if(waitPage.getList().get(i).getDictOffsetType().equals(RepayType.EARLY_SETTLE.getCode()) && !waitPage.getList().get(i).getSplitBackResult().equals(AgainstStatus.AIAINST.getName())){
				BigDecimal bigDecimal = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
				waitPage.getList().get(i).setApplyReallyAmount(bigDecimal);
			}
		}*/
		
		for(LoanServiceBureauEx ls:waitPage.getList()){
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",ls.getDictLoanStatus() );
			ls.setDictLoanStatusLabel(dictLoanStatus);
			
			String dictPayUse=DictCache.getInstance().getDictLabel("jk_repay_type", ls.getDictPayUse());
			ls.setDictPayUseLabel(dictPayUse);
			
			String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",ls.getDictPayStatus() );
			ls.setDictPayStatusLabel(dictPayStatus);
			
			String splitBackResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",ls.getSplitBackResult() );
			ls.setSplitBackResultLabel(splitBackResult);
			String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",ls.getLoanMark() );
			ls.setLoanMarkLabel(loanMark);
			
			String customerTelesalesFlag=DictCache.getInstance().getDictLabel("jk_telemarketing",ls.getCustomerTelesalesFlag() );
			ls.setCustomerTelesalesFlagLabel(customerTelesalesFlag);
		}
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("LoanServiceBureauEx", loanServiceBureau);
		logger.debug("invoke LoanServiceBureauContrlller method: allStoresAlreadyDoList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/stresAlreadyElert";
	}
	
	
	/**
	 * 电催已办查看页面
	 * 2015年2月25日
	 * By 李强
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "seeStoresElectDo")
	public String seeStoresElectDo(Model model,LoanServiceBureauEx loanServiceBureau) {
		List<LoanServiceBureauEx> slist = null;
		// 如果还款类型为3：提前结清，则返回门店已办提前结清查看页面，否则返回门店非提前结清查看页面
		if (loanServiceBureau.getDictPayUse().equals(DeductedConstantEx.ALSO_THREE)) {
			LoanServiceBureauEx loanServiceBureaus = loanServiceBureauHavaToService
					.seeStoresAlreadyDo(loanServiceBureau);
			nullJudgment(slist, loanServiceBureaus);
			
			// 减免金额=减免滞纳金 + 减免罚息
			BigDecimal reductionAmount = loanServiceBureaus.getMonthLateFeeReduction().add(loanServiceBureaus.getMonthPunishReduction());
			loanServiceBureaus.setReductionAmount(reductionAmount);
						
			// 未还违约金(滞纳金)及罚息总金额=(应还滞纳金+应还罚息) - (实还滞纳金 + 实还罚息) -(减免滞纳金+减免罚息)
			BigDecimal bigDecimals = loanServiceBureaus.getMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishshould());
			BigDecimal bigDecimal = loanServiceBureaus.getActualMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishactual());
			loanServiceBureaus.setNotPunishViolate(bigDecimals.subtract(bigDecimal).subtract(reductionAmount));

			// 提前结清应还总金=提前结清金额 + (未还违约金(滞纳金)及罚息总额)
			loanServiceBureaus.setInterestforeFinishAmount(loanServiceBureaus.getMonthBeforeFinishAmount().add(loanServiceBureaus.getNotPunishViolate()));
			
			
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(loanServiceBureaus.getProductType());
		    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		    loanServiceBureaus.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
       
		    String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",loanServiceBureaus.getDictLoanStatus());
		    loanServiceBureaus.setDictLoanStatusLabel(dictLoanStatus);
		    model.addAttribute("stores", loanServiceBureaus);
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ loanServiceBureaus);
			return "borrow/payback/repayment/seeStoresAlready";
		} else {
			LoanServiceBureauEx loanServiceBureaus = loanServiceBureauHavaToService.seeStoresAlreadyDos(loanServiceBureau);
			//判断是不是POS机刷卡查账
			//如果是 去查POS信息从表
			List<PaybackTransferInfo> payBackTransferInfoPos = loanServiceBureauHavaToService.seePayBackTransPos(loanServiceBureau.getIds());
			
			List<PaybackTransferInfo> payBackTransferInfo = loanServiceBureauHavaToService.seePayBackTrans(loanServiceBureau.getIds());
			nullJudgment(slist, loanServiceBureaus);
			// 本次应还款金额=应还本金 + 应还利息 + 应还滞纳金 + 应还罚息
			BigDecimal bigDecimals1 = loanServiceBureaus.getMonthPayMoney().add(loanServiceBureaus.getMonthInterestBackshould());
			BigDecimal bigDecimals2 = loanServiceBureaus.getMonthLateFee().add(loanServiceBureaus.getMonthInterestPunishshould());
			loanServiceBureaus.setShouldLoanAmount(bigDecimals1.add(bigDecimals2));
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(loanServiceBureaus.getProductType());
		 //   List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		    //loanServiceBureaus.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
			
			 String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",loanServiceBureaus.getDictLoanStatus());
			    loanServiceBureaus.setDictLoanStatusLabel(dictLoanStatus);
			    
			    String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",loanServiceBureaus.getLoanMark());
			    loanServiceBureaus.setLoanMarkLabel(loanMark);
			    
			    String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",loanServiceBureaus.getDictDealType());
			    loanServiceBureaus.setDictDealTypeLabel(dictDealType);
			    
			    String dictPosType=DictCache.getInstance().getDictLabel("jk_pos",loanServiceBureaus.getDictPosType());
			    loanServiceBureaus.setDictPosTypeLabel(dictPosType);
			    
			    
			   
			model.addAttribute("stores", loanServiceBureaus);
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ loanServiceBureaus);
			if(StringUtils.equals(loanServiceBureaus.getDictRepayMethod(), RepayChannel.POS_CHECK.getCode())){
				for(PaybackTransferInfo pt:payBackTransferInfoPos){
					String dictDepositPosCard=DictCache.getInstance().getDictLabel("jk_pos",pt.getDictDepositPosCard());
					pt.setDictDepositPosCardLabel(dictDepositPosCard);
				}
				model.addAttribute("payBackTransFif", payBackTransferInfoPos);
			}else{
				for(PaybackTransferInfo pt:payBackTransferInfo){
					String dictDepositPosCard=DictCache.getInstance().getDictLabel("jk_pos",pt.getDictDepositPosCard());
					pt.setDictDepositPosCardLabel(dictDepositPosCard);
				}
				model.addAttribute("payBackTransFif", payBackTransferInfo);
			}
			
			
			logger.debug("invoke LoanServiceBureauContrlller method: seeStoresAlreadyDo, consult.id is: "+ payBackTransferInfo);
			return "borrow/payback/repayment/seeStoresAlreadys";
		}
	}
}
