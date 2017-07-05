package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.ManualOperationEx;
import com.creditharmony.loan.borrow.payback.entity.ex.MonthExcelEx;
import com.creditharmony.loan.borrow.payback.service.ManualOperationService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 风控手动冲抵
 * @Class ManualOperationController 
 * @author 李强
 * @Create In 2015年12月17日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/manualOperation")
public class ManualOperationController extends BaseController  {
	@Autowired
	private ManualOperationService manualOperationService;
	
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	
	/**
	 * 风控手动冲抵列表
	 * 2015年12月17日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param manualOperationEx
	 * @return manualOperation.jsp
	 */
	@RequestMapping(value = "allManualOperationList")
	public String allManualOperationList(HttpServletRequest request,HttpServletResponse response,Model model,ManualOperationEx manualOperationEx){
		manualOperationEx.setBankTopFlag(DeductedConstantEx.INIT_ZERO);
		manualOperationEx.setEnumTwo(AgainstStatus.NO_AGAINST.getCode());
		manualOperationEx.setEnumThree(RepayType.ACCOUNT_CHECK.getCode());
		Page<ManualOperationEx> waitPage = manualOperationService.allManualOperationList(new Page<ManualOperationEx>(request, response),manualOperationEx);
		if (ArrayHelper.isNotEmpty(waitPage.getList())) {
			nullJudgment(waitPage.getList(),manualOperationEx);
			for (int i = DeductedConstantEx.INIT_ZERO; i < waitPage.getList().size() ; i++) {
				// 当期已还金额 = 实还本金+实还利息
				BigDecimal hisOverpaybackMonthMoney = waitPage.getList().get(i).getMonthCapitalPayactual().add(waitPage.getList().get(i).getMonthInterestPayactual());
				waitPage.getList().get(i).setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
				
				// 当期未还金额 = (应还本金+应还利息) - (实还本金 + 实还利息)
				BigDecimal twoShould = waitPage.getList().get(i).getMonthPayAmount().add(waitPage.getList().get(i).getMonthInterestBackshould());
				waitPage.getList().get(i).setNotOverpaybackMonthMoney(twoShould.subtract(hisOverpaybackMonthMoney));
			}
		}
		
		for(ManualOperationEx mo:waitPage.getList()){
			String applyBankName=DictCache.getInstance().getDictLabel("jk_open_bank",mo.getApplyBankName());
			mo.setApplyBankNameLabel(applyBankName);
			
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",mo.getDictLoanStatus());
			mo.setDictLoanStatusLabel(dictLoanStatus);
			
			String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",mo.getLoanMark());
			mo.setLoanMarkLabel(loanMark);
			
			String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",mo.getDictDealType());
			mo.setDictDealTypeLabel(dictDealType);
		}
		model.addAttribute("ManualOperationEx",manualOperationEx);
		logger.debug("invoke LoanServiceBureauContrlller method: allLoanServiceBureauList, consult.id is: "+ manualOperationEx);
		model.addAttribute("waitPage", waitPage);
		logger.debug("invoke LoanServiceBureauContrlller method: allLoanServiceBureauList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/manualOperation";
	}
	
	/**
	 * 风控手动冲抵 查看页面数据
	 * 2015年12月18日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param manualOperationEx
	 * @return seeManualOperation.jsp
	 */
	@RequestMapping(value = "queryManualOperation")
	public String queryManualOperation(HttpServletRequest request,HttpServletResponse response,Model model,ManualOperationEx manualOperationEx){
		List<ManualOperationEx> manualOperationList = manualOperationService.queryManualOperation(manualOperationEx);
		/*BigDecimal notPenaltyPunishShouldSum = BigDecimal.ZERO;
		BigDecimal bgSum = new BigDecimal("0.00");
		try {
			// 查看页面列表
			manualOperationList = manualOperationService.queryManualOperation(manualOperationEx);
			if (ArrayHelper.isNotEmpty(manualOperationList)) {
				nullJudgment(manualOperationList,manualOperationEx);
				for(int i = 0;i < manualOperationList.size() ; i++){
				ManualOperationEx manualOperationExInfo = manualOperationList.get(i);
  				if(!ObjectHelper.isEmpty(manualOperationExInfo)){
  					String contractVersion = manualOperationExInfo.getContractVersion();
					wrapManualOperationEx(manualOperationList, i,contractVersion);
  					if(StringUtils.isNotEmpty(getMonths(manualOperationExInfo))){
  	  					int months = Integer.parseInt(getMonths(manualOperationExInfo));
  	  				  if(i > months-1){
						  manualOperationList.get(i).setHisOverpaybackMonthMoney(bgSum);
						  manualOperationList.get(i).setNotOverpaybackMonthMoney(bgSum);
						  manualOperationList.get(i).setContractMonthRepayAmount(bgSum);
					  }else{
						  if(i == months - 1){
							  manualOperationList.get(i).setHisOverpaybackMonthMoney(manualOperationList.get(i).getMonthBeforeFinishAmount());
							  manualOperationList.get(i).setNotOverpaybackMonthMoney(bgSum);
						  }
				      }
  					}
					// 未还违约金及罚息总和 
					if(notPenaltyPunishShouldSum.compareTo(BigDecimal.ZERO) > 0){
						notPenaltyPunishShouldSum = notPenaltyPunishShouldSum.add(manualOperationList.get(i).getNotPenaltyPunishShould());
					}else{
						notPenaltyPunishShouldSum = manualOperationList.get(i).getNotPenaltyPunishShould();
					}
					
					// 得到产品类型name
					LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
					loanPrd.setProductCode(manualOperationList.get(i).getProductType());
					List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
					if (ArrayHelper.isNotEmpty(productList)) {
						manualOperationList.get(i).setProductType(
										productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
					}
					
					if(StringUtils.equals(manualOperationList.get(i).getIsOverdue(), YESNO.YES.getCode())){
						manualOperationList.get(i).setIsOverdue(YESNO.YES.getName());
					}else{
						manualOperationList.get(i).setIsOverdue(YESNO.NO.getName());
					}
				}
			}
		  }
		} catch (Exception e) {
			e.printStackTrace();
			return DeductedConstantEx.DUCTION_TODO;
		}*/

		// 查看按钮 页面的四个字段信息
		ManualOperationEx manualOperation = manualOperationService.queryOperation(manualOperationEx);  
		/*// 未还违约金及罚息总和 
		if(ObjectHelper.isNotEmpty(manualOperation)){
			manualOperation.setNotPenaltyPunishShouldSum(notPenaltyPunishShouldSum);
			List<ManualOperationEx> slist = manualOperationService.sumContractMonthRepay(manualOperationEx);
			if(ArrayHelper.isNotEmpty(slist)){
			    nullJudgment(slist,manualOperation);
			    if (ArrayHelper.isNotEmpty(manualOperationList)) {
			    	BigDecimal amount = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					BigDecimal sunAmount = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					for(int i = DeductedConstantEx.INIT_ZERO;i < manualOperationList.size(); i++){
						amount = manualOperationList.get(i).getHisOverpaybackMonthMoney();
						amount = (amount == null) ? new BigDecimal(DeductedConstantEx.INIT_AMOUNT) :amount;
						sunAmount = sunAmount.add(amount);
					}
					manualOperation.setSunAmount(sunAmount);
			    }
			    	
				String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",manualOperation.getDictPayStatus());
				manualOperation.setDictPayStatusLabel(dictPayStatus);
				if(ArrayHelper.isNotEmpty(manualOperationList)){
				for(ManualOperationEx mo:manualOperationList){
					String dictMonthStatus=DictCache.getInstance().getDictLabel("jk_period_status",mo.getDictMonthStatus());
					mo.setDictMonthStatusLabel(dictMonthStatus);
					String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",mo.getLoanMark());
					mo.setLoanMarkLabel(loanMark);
				}
			  }
			}
		}*/
		model.addAttribute("waitPage", manualOperationList);
		model.addAttribute("manualOperation", manualOperation);
		//model.addAttribute("manualOperationEx",manualOperationEx);
		/*logger.debug("invoke ManualOperationController method: queryManualOperation, consult.id is: "+ manualOperationList);
		logger.debug("invoke ManualOperationController method: queryManualOperation, consult.id is: "+ manualOperation);*/
		return "borrow/payback/repayment/seeManualOperation";
	}

	private void wrapManualOperationEx(
			List<ManualOperationEx> manualOperationList, int i,
			String contractVersion) {
		if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion)>=Integer.valueOf(ContractVer.VER_ONE_FOUR.getCode())){
		// 1.4版本合同金额计算	
		// 当期期供已还金额 = 实还本金+实还利息+实还分期服务费
		BigDecimal hisOverpaybackMonthMoney = manualOperationList.get(i).getMonthCapitalPayactual()
				.add(manualOperationList.get(i).getMonthInterestPayactual())
				.add(manualOperationList.get(i).getActualmonthFeeService());
		manualOperationList.get(i).setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
		
		// 当期期供未还金额 = (应还本金+应还利息+应还分期服务费) - (实还本金 + 实还利息+实还分期服务费)
		BigDecimal twoShould = manualOperationList.get(i).getMonthPayAmount()
				.add(manualOperationList.get(i).getMonthInterestBackshould())
				.add(manualOperationList.get(i).getMonthFeeService());
		BigDecimal notOverpaybackMonthMoney = twoShould.subtract(hisOverpaybackMonthMoney);
		if(notOverpaybackMonthMoney==null || notOverpaybackMonthMoney.compareTo(BigDecimal.ZERO)<=0)
		{
			notOverpaybackMonthMoney = BigDecimal.ZERO;
		}
		if(manualOperationList.get(i).getDictMonthStatus().equals(PeriodStatus.PAID.getCode()))
		{
			notOverpaybackMonthMoney = BigDecimal.ZERO;
		}
		manualOperationList.get(i).setNotOverpaybackMonthMoney(notOverpaybackMonthMoney);
		// 应还滞纳金及罚息 = 应还滞纳金 + 应还罚息
		BigDecimal interestPenaltyPunishShould = manualOperationList.get(i).getMonthLateFee().add(manualOperationList.get(i).getMonthInterestPunishshould());
		manualOperationList.get(i).setInterestPenaltyPunishShould(interestPenaltyPunishShould);
		// 减免滞纳金及罚息总额
		BigDecimal monthLateFeeReduction = manualOperationList.get(i).getMonthLateFeeReduction();
		BigDecimal monthPunishReduction = manualOperationList.get(i).getMonthPunishReduction();
		BigDecimal penaltyPunishReductionSum = monthLateFeeReduction.add(monthPunishReduction);
		manualOperationList.get(i).setPenaltyPunishReductionSum(penaltyPunishReductionSum);
		
		// 未还罚息及滞纳金 = 应还滞纳金及罚息 - (实还滞纳金 + 实还罚息) -(减免滞纳金+减免罚息)
		BigDecimal shiPenaltyPunishShould = manualOperationList.get(i).getActualMonthLateFee().add(manualOperationList.get(i).getMonthInterestPunishactual());
		  // 实还滞纳金罚息
		manualOperationList.get(i).setPenaltyPunishActualSum(shiPenaltyPunishShould);
		manualOperationList.get(i).setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunishShould)
				.subtract(penaltyPunishReductionSum));
		}else{
			// 1.3版本合同金额计算
			// 当期期供已还金额 (实还本金+实还利息)
			BigDecimal hisOverpaybackMonthMoney = manualOperationList.get(i).getMonthCapitalPayactual().add(manualOperationList.get(i).getMonthInterestPayactual());
			manualOperationList.get(i).setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
			// 当前期供未还金额 (应还本金+应还利息 - 实还本金 - 实还利息)
			BigDecimal twoShould = manualOperationList.get(i).getMonthPayAmount().add(manualOperationList.get(i).getMonthInterestBackshould());
			
			BigDecimal notOverpaybackMonthMoney = twoShould.subtract(hisOverpaybackMonthMoney);
			if(notOverpaybackMonthMoney==null || notOverpaybackMonthMoney.compareTo(BigDecimal.ZERO)<=0)
			{
				notOverpaybackMonthMoney = BigDecimal.ZERO;
			}
			if(manualOperationList.get(i).getDictMonthStatus().equals(PeriodStatus.PAID.getCode()))
			{
				notOverpaybackMonthMoney = BigDecimal.ZERO;
			}
			manualOperationList.get(i).setNotOverpaybackMonthMoney(notOverpaybackMonthMoney);
			// 应还违约金及罚息 (应还违约金 +应还罚息)
			BigDecimal interestPenaltyPunishShould = manualOperationList.get(i).getMonthPenaltyShould().add(manualOperationList.get(i).getMonthInterestPunishshould());
			manualOperationList.get(i).setInterestPenaltyPunishShould(interestPenaltyPunishShould);
			// 减免违约金及罚息总额 (减免违约金+减免罚息)
			BigDecimal monthPenaltyReduction = manualOperationList.get(i).getMonthPenaltyReduction();
			BigDecimal monthPunishReduction = manualOperationList.get(i).getMonthPunishReduction();
			BigDecimal penaltyPunishReductionSum = monthPenaltyReduction.add(monthPunishReduction);
			manualOperationList.get(i).setPenaltyPunishReductionSum(penaltyPunishReductionSum);
			// 实还违约金及罚息 (实还违约金+实还罚息)
			BigDecimal shiPenaltyPunsihShould = manualOperationList.get(i).getMonthPenaltyActual().add(manualOperationList.get(i).getMonthInterestPunishactual());
			manualOperationList.get(i).setPenaltyPunishActualSum(shiPenaltyPunsihShould);
			// 未还违约金及罚息(应还违约金+应还罚息-实还违约金-实还罚息-减免违约金-减免罚息)
			manualOperationList.get(i).setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunsihShould).subtract(penaltyPunishReductionSum));
			
		}
	}
	
	/**
	 * 风控批量冲抵
	 * 2015年12月21日
	 * By 李强
	 * @param model
	 * @param ids
	 * @param chargeId
	 * @return 冲抵结果 1：成功  2：失败
	 */
	@ResponseBody
	@RequestMapping(value = "queryChargeAgainst")
	public String queryChargeAgainst(Model model, String ids , String chargeId){
		String[] id = ids.split(";");
		String[] chargeIds = chargeId.split(";");
		List<ManualOperationEx> slist = null;
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();// 蓝补交易明细表
		for(int i = DeductedConstantEx.INIT_ZERO;i < id.length - DeductedConstantEx.ONE; i++){
			ManualOperationEx manualOperation = manualOperationService.queryChargeAgainst(id[i + DeductedConstantEx.ONE ]);
			nullJudgment(slist,manualOperation);
			if(manualOperation.getPaybackBuleAmount().compareTo(manualOperation.getMonthCapitalPayactual().add(manualOperation.getMonthInterestPayactual())) == DeductedConstantEx.CONPARETO){
				return DeductedConstantEx.ALSO_AMOUNT;// 如果蓝补金额小于期供金额,
			}else{
				manualOperation.setMonthCapitalPayactual(manualOperation.getMonthCapitalPayactual());
			    manualOperation.setMonthInterestPayactual(manualOperation.getMonthInterestPayactual());
				try {
					manualOperation.setEnumOne(PeriodStatus.PAID.getCode());
					manualOperationService.updateNotMoney(manualOperation);// 冲抵期供(修改实还本金利息、期供状态)
					BigDecimal paybackBuleAmount = manualOperation.getPaybackBuleAmount().subtract(manualOperation.getMonthCapitalPayactual().add(manualOperation.getMonthInterestPayactual()));
					manualOperation.setPaybackBuleAmount(paybackBuleAmount);
					manualOperationService.updatePaybackBuleAmount(manualOperation);// 冲抵期供成功后 将蓝补余额存回还款主表的蓝补金额里面
					
					ManualOperationEx manualOperations = new ManualOperationEx();
					manualOperations.preUpdate();
					manualOperations.setChargeId(chargeIds[i + DeductedConstantEx.ONE]);
					manualOperations.setEnumThree(DeductedConstantEx.ALSO_THREE);
					manualOperationService.updateChargeStatus(manualOperations);// 冲抵成功后修改冲抵申请表中冲抵状态为：已冲抵
					// 如果冲抵期数未最后一期，修改主表中的还款状态为：5 结清待确认
					if(manualOperation.getContractMonths().equals(manualOperation.getMonths())){
						manualOperation.setEnumThree(RepayStatus.SETTLE_CONFIRM.getCode());
						manualOperation.preUpdate();
						manualOperationService.updatePaybackStatus(manualOperation);
					}else{// 修改主表中的当前第几期
						int paybackCurrentMonth = manualOperation.getPaybackCurrentMonth() + DeductedConstantEx.ONE;
						manualOperation.preUpdate();
						manualOperation.setPaybackCurrentMonth(paybackCurrentMonth);
						manualOperationService.updatePaybackCurrentMonth(manualOperation);
					}
					paybackBuleAmont.setIsNewRecord(false);
					paybackBuleAmont.preInsert();
					paybackBuleAmont.setrMonthId(manualOperation.getMonthId());// 关连期供ID
					paybackBuleAmont.setTradeType(DeductedConstantEx.DUCTION_TODO);// 交易类型
					paybackBuleAmont.setDictDealUse(DeductedConstantEx.DICTDEALUSE);// 交易用途
					paybackBuleAmont.setTradeAmount(manualOperation.getMonthCapitalPayactual().add(manualOperation.getMonthInterestPayactual()));// 交易金额
					paybackBuleAmont.setDictOffsetType(ChargeType.CHARGE_STORE.getCode()); // 冲抵类型
					paybackBuleAmont.setSurplusBuleAmount(paybackBuleAmount);// 蓝补余额
					manualOperationService.insertPaybackBuleAmont(paybackBuleAmont);// 添加蓝补交易明细记录
				} catch (Exception e) {
					e.printStackTrace();
					return DeductedConstantEx.CENTRALIZED_DUCTION;
				}
			}
			
		}
		return DeductedConstantEx.DUCTION_TODO;
	}
	
	/**
	 * 导出Excel数据表
	 * 2015年12月25日
	 * By 李强
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, String idVal){
		ExcelUtils excelutil = new ExcelUtils();
		String[] chargeId = idVal.split(";");
		List<ManualOperationEx> customerList = new ArrayList<ManualOperationEx>();
		ManualOperationEx manualOperationEx = new ManualOperationEx();
			if (StringUtils.isEmpty(idVal)) {
				// 如果没有选中的数据，则导出处全部的数据
				manualOperationEx.setBankTopFlag(DeductedConstantEx.INIT_ZERO);
				manualOperationEx.setEnumTwo(AgainstStatus.NO_AGAINST.getCode());
				manualOperationEx.setEnumThree(RepayType.ACCOUNT_CHECK.getCode());
				Page<ManualOperationEx> waitPage = manualOperationService.allManualOperationList(new Page<ManualOperationEx>(request, response),manualOperationEx);
					nullJudgment(waitPage.getList(),manualOperationEx);
					for (int i = 0; i < waitPage.getList().size() ; i++) {
						// 当期已还金额 = 实还本金+实还利息
						BigDecimal hisOverpaybackMonthMoney = waitPage.getList().get(i).getMonthCapitalPayactual().add(waitPage.getList().get(i).getMonthInterestPayactual());
						waitPage.getList().get(i).setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
						// 当期未还金额 = (应还本金+应还利息) - (实还本金 + 实还利息)
						BigDecimal twoShould = waitPage.getList().get(i).getMonthPayAmount().add(waitPage.getList().get(i).getMonthInterestBackshould());
						waitPage.getList().get(i).setNotOverpaybackMonthMoney(twoShould.subtract(hisOverpaybackMonthMoney));
					    
						// 应还违约金及罚息 = 应还违约金 + 应还罚息
						BigDecimal interestPenaltyPunishShould = waitPage.getList().get(i).getMonthPenaltyShould().add(waitPage.getList().get(i).getMonthInterestPunishshould());
						waitPage.getList().get(i).setInterestPenaltyPunishShould(interestPenaltyPunishShould);
						// 减免违约金及罚息总额
						BigDecimal monthPenaltyReduction = waitPage.getList().get(i).getMonthPenaltyReduction();
						BigDecimal monthPunishReduction = waitPage.getList().get(i).getMonthPunishReduction();
						BigDecimal penaltyPunishReductionSum = monthPenaltyReduction.add(monthPunishReduction);
						waitPage.getList().get(i).setPenaltyPunishReductionSum(penaltyPunishReductionSum);
						
						// 未还罚息及违约金 = 应还违约金及罚息 - (实还违约金 + 实还罚息)
						BigDecimal shiPenaltyPunishShould = waitPage.getList().get(i).getMonthPenaltyActual().add(waitPage.getList().get(i).getMonthInterestPunishactual());
						  // 实还违约金罚息
						waitPage.getList().get(i).setPenaltyPunishActualSum(shiPenaltyPunishShould);
						waitPage.getList().get(i).setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunishShould)
								.subtract(monthPenaltyReduction).subtract(monthPunishReduction));
					}
				excelutil.exportExcel(waitPage.getList(), DeductedConstantEx.STORE_NAME
						+ System.currentTimeMillis(), ManualOperationEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, null);
			}else{
				// 如果有选中的数据 则导出选中的数据
				for(int i = DeductedConstantEx.INIT_ZERO; i < chargeId.length - DeductedConstantEx.ONE; i++ ){
					List<ManualOperationEx> nullList=new ArrayList<ManualOperationEx>();
					manualOperationEx = manualOperationService.exportExcelData(chargeId[i + DeductedConstantEx.ONE]);
					nullJudgment(nullList,manualOperationEx);
						// 当期已还金额 = 实还本金+实还利息
						BigDecimal hisOverpaybackMonthMoney = manualOperationEx.getMonthCapitalPayactual().add(manualOperationEx.getMonthInterestBackshould());
						manualOperationEx.setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
						// 当期未还金额 = (应还本金+应还利息) - (实还本金 + 实还利息)
						BigDecimal twoShould = manualOperationEx.getMonthPayAmount().add(manualOperationEx.getMonthInterestBackshould());
						manualOperationEx.setNotOverpaybackMonthMoney(twoShould.subtract(hisOverpaybackMonthMoney));
					
						// 应还违约金及罚息 = 应还违约金 + 应还罚息
						BigDecimal interestPenaltyPunishShould = manualOperationEx.getMonthPenaltyShould().add(manualOperationEx.getMonthInterestPunishshould());
						manualOperationEx.setInterestPenaltyPunishShould(interestPenaltyPunishShould);
						// 减免违约金及罚息总额
						BigDecimal monthPenaltyReduction = manualOperationEx.getMonthPenaltyReduction();
						BigDecimal monthPunishReduction = manualOperationEx.getMonthPunishReduction();
						BigDecimal penaltyPunishReductionSum = monthPenaltyReduction.add(monthPunishReduction);
						manualOperationEx.setPenaltyPunishReductionSum(penaltyPunishReductionSum);
						
						// 未还罚息及违约金 = 应还违约金及罚息 - (实还违约金 + 实还罚息)
						BigDecimal shiPenaltyPunishShould = manualOperationEx.getMonthPenaltyActual().add(manualOperationEx.getMonthInterestPunishactual());
						  // 实还违约金罚息
						manualOperationEx.setPenaltyPunishActualSum(shiPenaltyPunishShould);
						manualOperationEx.setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunishShould)
								.subtract(monthPenaltyReduction).subtract(monthPunishReduction));
						
						customerList.add(manualOperationEx);
				}
				excelutil.exportExcel(customerList, DeductedConstantEx.STORE_NAME
						+ System.currentTimeMillis(), ManualOperationEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, null);
			}
	}
	
	/**
	 * 还款明细页面Excel数据表
	 * 2015年12月25日
	 * By 李强
	 * @param request
	 * @param response
	 * @param monthExcelEx
	 */
	@RequestMapping(value = "exportMonthExcel")
	public void exportMonthExcel(HttpServletRequest request,HttpServletResponse response,MonthExcelEx monthExcelEx){
		ExcelUtils excelutil = new ExcelUtils();
		DecimalFormat df = new DecimalFormat("0.00");
		List<MonthExcelEx> mlist = manualOperationService.exportMonthExcel(monthExcelEx);
		if (ArrayHelper.isNotEmpty(mlist)) {
			nullJudgmentMonth(mlist);
			for(int i = 0;i < mlist.size() ; i++){
				MonthExcelEx monthExcelExInfo = mlist.get(i);
  				if(!ObjectHelper.isEmpty(monthExcelExInfo)){
					String contractVersion = monthExcelExInfo.getContractVersion();
					if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
						//1.4版本合同金额计算
				// 当期期供已还金额 = 实还本金+实还利息+实还分期服务费
				BigDecimal hisOverpaybackMonthMoney = mlist.get(i).getMonthCapitalPayactual()
						.add(mlist.get(i).getMonthInterestPayactual())
						.add(mlist.get(i).getActualmonthFeeService());
				mlist.get(i).setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
				
				// 当期期供未还金额 = (应还本金+应还利息+应还分期服务费) - (实还本金 + 实还利息+实还分期服务费)
				BigDecimal twoShould = mlist.get(i).getMonthPayAmount()
						.add(mlist.get(i).getMonthInterestBackshould())
						.add(mlist.get(i).getMonthFeeService());
				mlist.get(i).setNotOverpaybackMonthMoney(twoShould.subtract(hisOverpaybackMonthMoney));
				// 应还滞纳金及罚息 = 应还滞纳金 + 应还罚息
				BigDecimal interestPenaltyPunishShould = mlist.get(i).getMonthLateFee().add(mlist.get(i).getMonthInterestPunishshould());
				mlist.get(i).setInterestPenaltyPunishShould(interestPenaltyPunishShould);
				// 减免滞纳金及罚息总额
				BigDecimal monthLateFeeReduction = mlist.get(i).getMonthLateFeeReduction();
				BigDecimal monthPunishReduction = mlist.get(i).getMonthPunishReduction();
				BigDecimal penaltyPunishReductionSum = monthLateFeeReduction.add(monthPunishReduction);
				mlist.get(i).setPenaltyPunishReductionSum(penaltyPunishReductionSum);
				
				// 未还罚息及滞纳金 = 应还滞纳金及罚息 - (实还滞纳金 + 实还罚息) -(减免滞纳金+减免罚息)
				BigDecimal shiPenaltyPunishShould = mlist.get(i).getActualMonthLateFee().add(mlist.get(i).getMonthInterestPunishactual());
				  // 实还滞纳金罚息
				mlist.get(i).setPenaltyPunishActualSum(shiPenaltyPunishShould);
				mlist.get(i).setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunishShould)
						.subtract(penaltyPunishReductionSum));
				    }else{
				    	// 1.3版本合同金额计算
						// 当期期供已还金额 (实还本金+实还利息)
						BigDecimal hisOverpaybackMonthMoney = monthExcelExInfo.getMonthCapitalPayactual().add(monthExcelExInfo.getMonthInterestPayactual());
						monthExcelExInfo.setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
						// 当前期供未还金额 (应还本金+应还利息 - 实还本金 - 实还利息)
						BigDecimal twoShould = monthExcelExInfo.getMonthPayAmount().add(monthExcelExInfo.getMonthInterestBackshould());
						monthExcelExInfo.setNotOverpaybackMonthMoney(twoShould.subtract(hisOverpaybackMonthMoney));
						// 应还违约金及罚息 (应还违约金 +应还罚息)
						BigDecimal interestPenaltyPunishShould = monthExcelExInfo.getMonthPenaltyShould().add(monthExcelExInfo.getMonthInterestPunishshould());
						monthExcelExInfo.setInterestPenaltyPunishShould(interestPenaltyPunishShould);
						// 减免违约金及罚息总额 (减免违约金+减免罚息)
						BigDecimal monthPenaltyReduction = monthExcelExInfo.getMonthPenaltyReduction();
						BigDecimal monthPunishReduction = monthExcelExInfo.getMonthPunishReduction();
						BigDecimal penaltyPunishReductionSum = monthPenaltyReduction.add(monthPunishReduction);
						monthExcelExInfo.setPenaltyPunishReductionSum(penaltyPunishReductionSum);
						// 实还违约金及罚息 (实还违约金+实还罚息)
						BigDecimal shiPenaltyPunsihShould = monthExcelExInfo.getMonthPenaltyActual().add(monthExcelExInfo.getMonthInterestPunishactual());
						monthExcelExInfo.setPenaltyPunishActualSum(shiPenaltyPunsihShould);
						// 未还违约金及罚息(应还违约金+应还罚息-实还违约金-实还罚息-减免违约金-减免罚息)
						monthExcelExInfo.setNotPenaltyPunishShould(interestPenaltyPunishShould.subtract(shiPenaltyPunsihShould).subtract(penaltyPunishReductionSum));
				    }
				// 导出格式化金额 金额保留两位小数
				if(!ObjectHelper.isEmpty(mlist.get(i).getHisOverpaybackMonthMoney())){
					mlist.get(i).setHisOverpaybackMonthMoney(new BigDecimal(df.format(mlist.get(i).getHisOverpaybackMonthMoney())));
				}	
				if(!ObjectHelper.isEmpty(mlist.get(i).getNotOverpaybackMonthMoney())){
					mlist.get(i).setNotOverpaybackMonthMoney(new BigDecimal(df.format(mlist.get(i).getNotOverpaybackMonthMoney())));
				}
				if(!ObjectHelper.isEmpty(mlist.get(i).getInterestPenaltyPunishShould())){
					mlist.get(i).setInterestPenaltyPunishShould(new BigDecimal(df.format(mlist.get(i).getInterestPenaltyPunishShould())));
				}				
				if(!ObjectHelper.isEmpty(mlist.get(i).getNotPenaltyPunishShould())){
					mlist.get(i).setNotPenaltyPunishShould(new BigDecimal(df.format(mlist.get(i).getNotPenaltyPunishShould())));
				}
				if(!ObjectHelper.isEmpty(mlist.get(i).getPenaltyPunishReductionSum())){
					mlist.get(i).setPenaltyPunishReductionSum(new BigDecimal(df.format(mlist.get(i).getPenaltyPunishReductionSum())));
				}
				if(!ObjectHelper.isEmpty(mlist.get(i).getPenaltyPunishActualSum())){
					mlist.get(i).setPenaltyPunishActualSum(new BigDecimal(df.format(mlist.get(i).getPenaltyPunishActualSum())));
				}				
				if(!ObjectHelper.isEmpty(mlist.get(i).getContractMonthRepayAmount())){
					mlist.get(i).setContractMonthRepayAmount(new BigDecimal(df.format(mlist.get(i).getContractMonthRepayAmount())));
				}
				if(!ObjectHelper.isEmpty(mlist.get(i).getMonthBeforeFinishAmount())){
					mlist.get(i).setMonthBeforeFinishAmount(new BigDecimal(df.format(mlist.get(i).getMonthBeforeFinishAmount())));
				}				
				LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
				loanPrd.setProductCode(mlist.get(i).getProductType());
			    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			    if(ArrayHelper.isNotEmpty(productList)){
			    	if(!ObjectHelper.isEmpty(productList.get(DeductedConstantEx.INIT_ZERO))){
			    		mlist.get(i).setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
			    	}
			    }
			    
			}
		}
	 
   }
		excelutil.exportExcel(mlist, DeductedConstantEx.MONTH + System.currentTimeMillis(), MonthExcelEx.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
	}

	private String getMonths(ManualOperationEx manualOperationEx) {
		String months = "";
		// 实还本金
		BigDecimal monthCapitalPayactual = manualOperationEx.getMonthCapitalPayactual();
		// 一次性提前结清金额
		BigDecimal monthBeforeFinishAmount = manualOperationEx.getMonthBeforeFinishAmount();
		if(monthCapitalPayactual.compareTo(monthBeforeFinishAmount) == Integer.parseInt(YESNO.NO.getCode())){
			months = manualOperationEx.getMonths();
		}
		return months;
	}
	
	
	
	/**
	 * 金额运算时非空判断
	 * 2016年1月29日
	 * By zhaojinping
	 * @param slist
	 * @param manualOperationEx
	 * return none
	 */
	public void nullJudgment(List<ManualOperationEx> slist,
			ManualOperationEx manualOperationEx) {
		if (ArrayHelper.isNotEmpty(slist)) {
			for (int i = DeductedConstantEx.INIT_ZERO; i < slist.size() ; i++) {
				// 应还本金
				if (slist.get(i).getMonthPayAmount() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPayAmount(bigDecimals);
				}
				// 应还利息
				if (slist.get(i).getMonthInterestBackshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestBackshould(bigDecimals);
				}
				// 实还本金
				if (slist.get(i).getMonthCapitalPayactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthCapitalPayactual(bigDecimals);
				}
				// 实还利息
				if (slist.get(i).getMonthInterestPayactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPayactual(bigDecimals);
				}
				// 应还分期服务费        
				if (slist.get(i).getMonthFeeService() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthFeeService(bigDecimals);
				}
				// 应还罚息           
				if (slist.get(i).getMonthInterestPunishshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPunishshould(bigDecimals);
				}
				// 实还分期服务费
				if (slist.get(i).getActualmonthFeeService() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setActualmonthFeeService(bigDecimals);
				}
				// 实还罚息
				if (slist.get(i).getMonthInterestPunishactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPunishactual(bigDecimals);
				}
				
				// 应还滞纳金
				if (slist.get(i).getMonthLateFee() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthLateFee(bigDecimals);
				}
				// 实还滞纳金
				if (slist.get(i).getActualMonthLateFee() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setActualMonthLateFee(bigDecimals);
				}
				// 减免滞纳金
				if (slist.get(i).getMonthLateFeeReduction() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthLateFeeReduction(bigDecimals);
				}
				// 减免罚息
				if (slist.get(i).getMonthPunishReduction() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPunishReduction(bigDecimals);
				}
				// 应还违约金
				if(slist.get(i).getMonthPenaltyShould() == null){
					BigDecimal monthPenaltyShould = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyShould(monthPenaltyShould);
				}
				// 实还违约金
				if(slist.get(i).getMonthPenaltyActual() == null){
					BigDecimal monthPenaltyActual = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyActual(monthPenaltyActual);
				}
				// 减免违约金
				if(slist.get(i).getMonthPenaltyReduction() == null){
					BigDecimal monthPenaltyReduction = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyReduction(monthPenaltyReduction);
				}
			}
		}
		// 应还本金
		if (StringUtils.isEmpty(String.valueOf(manualOperationEx.getMonthPayAmount()))) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthPayAmount(bigDecimals);
		}
		// 应还利息
		if (manualOperationEx.getMonthInterestBackshould() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthInterestBackshould(bigDecimals);
		}
		// 实还本金
		if (manualOperationEx.getMonthCapitalPayactual() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthCapitalPayactual(bigDecimals);
		}
		// 实还利息
		if (manualOperationEx.getMonthInterestPayactual() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthInterestPayactual(bigDecimals);
		}
		
		// 减免罚息
		if (manualOperationEx.getMonthPunishReduction() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthPunishReduction(bigDecimals);
		}
		
		// 应还分期服务费        
		if (manualOperationEx.getMonthFeeService() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthFeeService(bigDecimals);
		}
		// 应还罚息           
		if (manualOperationEx.getMonthInterestPunishshould() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthInterestPunishshould(bigDecimals);
		}
		// 实还分期服务费
		if (manualOperationEx.getActualmonthFeeService() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setActualmonthFeeService(bigDecimals);
		}
		// 实还罚息
		if (manualOperationEx.getMonthInterestPunishactual() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthInterestPunishactual(bigDecimals);
		}
		
		// 应还滞纳金
		if (manualOperationEx.getMonthLateFee() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthLateFee(bigDecimals);
		}
		// 实还滞纳金
		if (manualOperationEx.getActualMonthLateFee() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setActualMonthLateFee(bigDecimals);
		}
		// 减免滞纳金
		if (manualOperationEx.getMonthLateFeeReduction() == null) {
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthLateFeeReduction(bigDecimals);
		}
		// 应还违约金
		if(manualOperationEx.getMonthPenaltyShould() == null){
			BigDecimal monthPenaltyShould = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthPenaltyShould(monthPenaltyShould);
		}
		// 实还违约金
		if(manualOperationEx.getMonthPenaltyActual() == null){
			BigDecimal monthPenaltyActual = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthPenaltyActual(monthPenaltyActual);
		}
		// 减免违约金
		if(manualOperationEx.getMonthPenaltyReduction() == null){
			BigDecimal monthPenaltyReduction = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			manualOperationEx.setMonthPenaltyReduction(monthPenaltyReduction);
		}
		
		slist = null;
	}
	
	
	
	
	/**
	 * 金额运算时非空判断(导出冲抵查看页面的期供Excel)
	 * 2015年12月9日
	 * By 李强
	 * @param slist
	 * @return none
	 */
	public void nullJudgmentMonth(List<MonthExcelEx> slist) {
		if (ArrayHelper.isNotEmpty(slist)) {
			for (int i = DeductedConstantEx.INIT_ZERO; i < slist.size() ; i++) {
				// 应还本金
				if (slist.get(i).getMonthPayAmount() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPayAmount(bigDecimals);
				}
				// 应还利息
				if (slist.get(i).getMonthInterestBackshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestBackshould(bigDecimals);
				}
				// 实还本金
				if (slist.get(i).getMonthCapitalPayactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthCapitalPayactual(bigDecimals);
				}
				// 实还利息
				if (slist.get(i).getMonthInterestPayactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPayactual(bigDecimals);
				}
				// 应还分期服务费
				if (slist.get(i).getMonthFeeService() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthFeeService(bigDecimals);
				}
				// 实还分期服务费
				if (slist.get(i).getActualmonthFeeService() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setActualmonthFeeService(bigDecimals);
				}
				
				// 应还罚息	     
				if (slist.get(i).getMonthInterestPunishshould() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPunishshould(bigDecimals);
				}
				
				// 实还罚息
				if (slist.get(i).getMonthInterestPunishactual() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthInterestPunishactual(bigDecimals);
				}
				
				// 减免罚息
				if (slist.get(i).getMonthPunishReduction() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPunishReduction(bigDecimals);
				}
				
				// 应还滞纳金
				if (slist.get(i).getMonthLateFee() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthLateFee(bigDecimals);
				}
				
				// 实还滞纳金
				if (slist.get(i).getActualMonthLateFee() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setActualMonthLateFee(bigDecimals);
				}
				
				// 减免滞纳金
				if (slist.get(i).getMonthLateFeeReduction() == null) {
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthLateFeeReduction(bigDecimals);
				}
				
				// 应还违约金
				if(slist.get(i).getMonthPenaltyShould() == null){
					BigDecimal monthPenaltyShould = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyShould(monthPenaltyShould);
				}
				// 实还违约金
				if(slist.get(i).getMonthPenaltyActual() == null){
					BigDecimal monthPenaltyActual = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyActual(monthPenaltyActual);
				}
				// 减免违约金
				if(slist.get(i).getMonthPenaltyReduction() == null){
					BigDecimal monthPenaltyReduction = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setMonthPenaltyReduction(monthPenaltyReduction);
				}
				
			}
		}
	}
}
