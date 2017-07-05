package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.dao.ConfirmPaybackDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 结清确认业务处理Service
 * @Class Name ConfirmPaybackService
 * @author zhangfeng
 * @Create In 2016年1月6日
 */
@Service("confirmPaybackService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ConfirmPaybackService extends CoreManager<ConfirmPaybackDao, PaybackApply> {

	public final static String MONTH_ZERO = "0.00";
	@Autowired
	public HistoryService historyService;
	@Autowired
	public PaybackService paybackService;
	@Autowired
	public EarlyFinishConfirmService earlyFinishConfirmService;
	
	/**
	 * 查询结清页面列表数据
	 * @param page
	 * @param payback
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Payback> findAllConfirm(Payback payback){
		String contractVersion = "";
		BigDecimal bgSum = new BigDecimal("0.00");
		List<Payback> dataList = dao.findConfirm(payback);
		for (int i = 0; i < dataList.size(); i++) {
			// 设置返款金额
			UrgeServicesMoney urgeServicesMoney = dataList.get(i).getUrgeServicesMoney();
			if (ObjectHelper.isEmpty(urgeServicesMoney)) {
				urgeServicesMoney = new UrgeServicesMoney();
				urgeServicesMoney.setUrgeMoeny(bgSum);
				urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
				dataList.get(i).setUrgeServicesMoney(urgeServicesMoney);
			} else {
				BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
				BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
				if (ObjectHelper.isEmpty(urgeMoney)) {
					dataList.get(i).getUrgeServicesMoney().setUrgeMoeny(bgSum);
				}
				if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
					dataList.get(i).getUrgeServicesMoney().setUrgeDecuteMoeny(bgSum);
				}
			}
			Contract contract = dataList.get(i).getContract();
	    	if(!ObjectHelper.isEmpty(contract)){
	    		contractVersion = contract.getContractVersion();
	    	}
	    	PaybackMonth paybackMonth = dataList.get(i).getPaybackMonth();
	    	if(!ObjectHelper.isEmpty(paybackMonth)){
	    		findDefaultConfirmInfo(paybackMonth,contractVersion);
	    	}
		}
		return dataList;
	}
	
	
	/**
	 * 查询结清页面列表数据
	 * @param page
	 * @param payback
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Payback> findConfirm(Page<Payback> page, Payback payback){
		String contractVersion = "";
		BigDecimal bgSum = new BigDecimal("0.00");
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setAsyncTotalCount(false);
		PageList<Payback> pageList = (PageList<Payback>) dao
					.findConfirm(pageBounds, payback);
		for (int i = 0; i < pageList.size(); i++) {
			// 设置返款金额
			UrgeServicesMoney urgeServicesMoney = pageList.get(i).getUrgeServicesMoney();
			if (ObjectHelper.isEmpty(urgeServicesMoney)) {
				urgeServicesMoney = new UrgeServicesMoney();
				urgeServicesMoney.setUrgeMoeny(bgSum);
				urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
				pageList.get(i).setUrgeServicesMoney(urgeServicesMoney);
			} else {
				BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
				BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
				if (ObjectHelper.isEmpty(urgeMoney)) {
					pageList.get(i).getUrgeServicesMoney().setUrgeMoeny(bgSum);
				}
				if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
					pageList.get(i).getUrgeServicesMoney().setUrgeDecuteMoeny(bgSum);
				}
			}
			
			Contract contract = pageList.get(i).getContract();
	    	if(!ObjectHelper.isEmpty(contract)){
	    		contractVersion = contract.getContractVersion();
	    	}
	    	PaybackMonth paybackMonth = pageList.get(i).getPaybackMonth();
	    	if(!ObjectHelper.isEmpty(paybackMonth)){
	    		findDefaultConfirmInfo(paybackMonth,contractVersion);
	    	}
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 查询结清确认详情页面数据信息
	 * @param payback
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Payback> findConfirmInfo(Payback payback){
		BigDecimal bgSum = new BigDecimal("0.00");
		String contractVersion = "";
		List<Payback> paybackList = new ArrayList<Payback>();
		paybackList = dao.findConfirm(payback);
		if (ArrayHelper.isNotEmpty(paybackList) && paybackList.size() > 0) {
			// 设置返款金额
			UrgeServicesMoney urgeServicesMoney = paybackList.get(0).getUrgeServicesMoney();
			if(ObjectHelper.isEmpty(urgeServicesMoney)){
				urgeServicesMoney = new UrgeServicesMoney();
				urgeServicesMoney.setUrgeMoeny(bgSum);
				urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
				paybackList.get(0).setUrgeServicesMoney(urgeServicesMoney);
			}else{
				BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
				BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
				if(ObjectHelper.isEmpty(urgeMoney)){
					paybackList.get(0).getUrgeServicesMoney().setUrgeMoeny(bgSum);
				}
				if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
					paybackList.get(0).getUrgeServicesMoney().setUrgeDecuteMoeny(bgSum);
				}
			}
			for (int i = 0; i < paybackList.size(); i++) {
				Contract contract = paybackList.get(0).getContract();
				if(!ObjectHelper.isEmpty(contract)){
					contractVersion = contract.getContractVersion();
				}
				PaybackMonth paybackMonth = paybackList.get(0).getPaybackMonth();
				paybackMonth = findDefaultConfirmInfo(paybackMonth,contractVersion);
                if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
					
					/** 新应还款总额 
					 * 应还款总额(应还总额sum+ 应还滞纳金sum + 应还罚息sum - 减免违约金(滞纳金)罚息sum)
					 * (month_pay_total+month_late_fee+month_interest_punishshould-(month_late_fee_reduction+month_punish_reduction))
					 *paybackMonth.getMonthPayTotal();//应还总额
					 *paybackMonth.getMonthLateFeeSum();//应还滞纳金sum
					 *paybackMonth.getMonthInterestPayactualSum();//应还罚息sum
					 *paybackMonth.getMonthLateFeeReductionSum();//减免滞纳金sum 
					 */
					paybackMonth.setMoneyAmount(paybackMonth.getMonthPayTotalSum()
							.add(paybackMonth.getMonthLateFeeSum())
							.add(paybackMonth.getMonthInterestPunishshouldSum())
							.subtract(paybackMonth.getMonthLateFeeReductionSum())
							.subtract(paybackMonth.getMonthPunishReductionSum()));

				}else{
					// 应还期供总额  = 合同表中的月还总金额*批借期数					
					BigDecimal contractMonthRepayAmount = contract
						.getContractMonthRepayAmount();
				contractMonthRepayAmount = contractMonthRepayAmount == null ? bgSum	: contractMonthRepayAmount;

					// 应还款总额
					BigDecimal monthCapitalPayactual = contractMonthRepayAmount
							.multiply(contract.getContractMonths());
					// 应还总额   应还期供总额+应还违约金总额+应还罚息总额
					BigDecimal monthInterestPunishshouldSum = paybackMonth
							.getMonthInterestPunishshouldSum();
					monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum
							: monthInterestPunishshouldSum;
					BigDecimal monthPenaltyShouldSum = paybackMonth
							.getMonthPenaltyShouldSum();
					monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum
							: monthPenaltyShouldSum;
					paybackMonth.setMoneyAmount(monthCapitalPayactual
							.add(monthInterestPunishshouldSum
									.add(monthPenaltyShouldSum)).subtract(paybackMonth.getMonthPenaltyReductionSum()).subtract(paybackMonth.getMonthPunishReductionSum())); 
				}
             // 减免人
    			paybackList.get(0).setRemissionBy(UserUtils.getUser().getName());
			}
		}
		return paybackList;
	}
	
	/**
	 * 计算页面需要显示的金额
	 * @param paybackMonth
	 * @param contractVersion
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackMonth findDefaultConfirmInfo(PaybackMonth paybackMonth,String contractVersion) {
		BigDecimal bgSum = new BigDecimal(MONTH_ZERO);
		if (!ObjectHelper.isEmpty(paybackMonth)) {
			if (!ObjectHelper.isEmpty(paybackMonth)) {
				if(paybackMonth.getMonthCapitalPayactual()==null){
					paybackMonth.setMonthCapitalPayactual(bgSum);
				}
				if(paybackMonth.getMonthInterestPayactual()==null){
					paybackMonth.setMonthInterestPayactual(bgSum);
				}
				if(paybackMonth.getMonthFeeService()==null){
					paybackMonth.setMonthFeeService(bgSum);
				}
				if(paybackMonth.getActualMonthFeeService()==null){
					paybackMonth.setActualMonthFeeService(bgSum);
				}
				if(paybackMonth.getActualMonthLateFee()==null){
					paybackMonth.setActualMonthLateFee(bgSum);
				}
				if(paybackMonth.getMonthInterestPunishactual()==null){
					paybackMonth.setMonthInterestPunishactual(bgSum);
				}
				if(paybackMonth.getMonthPenaltyActual()==null){
					paybackMonth.setMonthPenaltyActual(bgSum);
				}
				if(paybackMonth.getMonthPayTotal()==null){
					paybackMonth.setMonthPayTotal(bgSum);
				}
				if(paybackMonth.getMonthLateFeeSum()==null){
					paybackMonth.setMonthLateFeeSum(bgSum);
				}
				if(paybackMonth.getMonthInterestPayactualSum()==null){
					paybackMonth.setMonthInterestPayactualSum(bgSum);
				}
				if(paybackMonth.getMonthLateFeeReductionSum()==null){
					paybackMonth.setMonthLateFeeReductionSum(bgSum);
				}
				if(paybackMonth.getMonthPunishReductionSum()==null){
					paybackMonth.setMonthPunishReductionSum(bgSum);
				}
				if(paybackMonth.getActualMonthLateFeeSum()==null){
					paybackMonth.setActualMonthLateFeeSum(bgSum);
				}
				if(paybackMonth.getMonthPenaltyShouldSum()==null){
					paybackMonth.setMonthPenaltyShouldSum(bgSum);
				}
				if(paybackMonth.getMonthPenaltyActualSum()==null){
					paybackMonth.setMonthPenaltyActualSum(bgSum);
				}
				if(paybackMonth.getMonthPenaltyReductionSum()==null){
					paybackMonth.setMonthPenaltyReductionSum(bgSum);
				}
				if(paybackMonth.getMonthPayTotalSum()==null){
					paybackMonth.setMonthPayTotalSum(bgSum);
				}
				
				// 已还期供金额 实还本金sum + 实还利息sum + 实还分期服务费sum   
				BigDecimal monthCapitalPayactualSum = paybackMonth.getMonthCapitalPayactualSum();
				monthCapitalPayactualSum = monthCapitalPayactualSum == null ? bgSum :monthCapitalPayactualSum;
				paybackMonth.setMonthCapitalPayactualSum(monthCapitalPayactualSum);
				BigDecimal monthInterestPayactualSum =  paybackMonth.getMonthInterestPayactualSum();
				monthInterestPayactualSum = monthInterestPayactualSum == null ? bgSum :monthInterestPayactualSum;
				paybackMonth.setMonthInterestPayactualSum(monthInterestPayactualSum);
				BigDecimal actualMonthFeeServiceSum = paybackMonth.getActualMonthFeeServiceSum();
				actualMonthFeeServiceSum = actualMonthFeeServiceSum == null ? bgSum : actualMonthFeeServiceSum;
				paybackMonth.setActualMonthFeeServiceSum(actualMonthFeeServiceSum);
				// 未还违约金及罚息金额 应还违约金sum-实还违约金sum + 应还罚息sum-实还罚息sum - 减免违约金罚息sum
				// 应还违约金总额 
				BigDecimal monthPenaltyShouldSum =  paybackMonth.getMonthPenaltyShouldSum();
				monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum :monthPenaltyShouldSum;
				paybackMonth.setMonthPenaltyShouldSum(monthPenaltyShouldSum);
				// 实还违约金总额
				BigDecimal monthPenaltyActualSum = paybackMonth.getMonthPenaltyActualSum();
				monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum :monthPenaltyActualSum;
				paybackMonth.setMonthPenaltyActualSum(monthPenaltyActualSum);
				// 应还罚息总额
				BigDecimal monthInterestPunishshouldSum = paybackMonth.getMonthInterestPunishshouldSum();
				monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum :monthInterestPunishshouldSum;
				paybackMonth.setMonthInterestPunishshouldSum(monthInterestPunishshouldSum);
				// 实还罚息总额
				BigDecimal monthInterestPunishactualSum = paybackMonth.getMonthInterestPunishactualSum();
				monthInterestPunishactualSum = monthInterestPunishactualSum == null ? bgSum :monthInterestPunishactualSum;
				paybackMonth.setMonthInterestPunishactualSum(monthInterestPunishactualSum);
				
				/** 已还违约金及罚息金额 实还违约金sum + 实还罚息sum
				paybackMonth.setOverDueAmontPaybacked(monthPenaltyActualSum.add(monthInterestPunishactualSum));
				 */
				//新已还违约金(滞纳金)及罚息金额(实还滞纳金sum + 实还罚息sum)
				paybackMonth.setOverDueAmontPaybackedSum(paybackMonth.getActualMonthLateFeeSum().add(monthInterestPunishactualSum));
				// 实还总额   已还违约金及罚息金额 + 已还期供金额
				if(StringUtils.isNotEmpty(contractVersion)&&(new BigDecimal(contractVersion)).compareTo(new BigDecimal(ContractVer.VER_ONE_FOUR.getCode()))>=0){
					// 1.4版本合同
					// 已还期供金额 实还本金sum + 实还利息sum + 实还分期服务费sum
					paybackMonth.setMonthsAomuntPaybacked(monthCapitalPayactualSum.add(monthInterestPayactualSum).add(actualMonthFeeServiceSum));
					/** 新减免违约金罚息sum(减免滞纳金sum+减免罚息sum) */
					paybackMonth.setCreditAmount(paybackMonth.getMonthLateFeeReductionSum().add(paybackMonth.getMonthPunishReductionSum()));
					//新未还违约金(滞纳金)及罚息金额(应还滞纳金sum-实还滞纳金sum + 应还罚息sum-实还罚息sum - 减免违约金(滞纳金)罚息sum)
					paybackMonth.setPenaltyInterest(paybackMonth.getMonthLateFeeSum()
							.subtract(paybackMonth.getActualMonthLateFeeSum())
							.add(monthInterestPunishshouldSum)
							.subtract(monthInterestPunishactualSum)
							.subtract(paybackMonth.getMonthLateFeeReductionSum())
							.subtract(paybackMonth.getMonthPunishReductionSum()));
					//新已还违约金(滞纳金)及罚息金额(实还滞纳金sum + 实还罚息sum)
					paybackMonth.setOverDueAmontPaybackedSum(paybackMonth.getActualMonthLateFeeSum().add(monthInterestPunishactualSum));
					paybackMonth.setActualAmount(paybackMonth
							.getMonthCapitalPayactualSum()
							.add(paybackMonth.getMonthInterestPayactualSum())
							.add(paybackMonth.getActualMonthFeeServiceSum())
							.add(paybackMonth.getActualMonthLateFeeSum())
							.add(paybackMonth.getMonthInterestPunishactualSum()));	
				}else{
					//1.3版本合同
					// 已还期供金额 实还本金+实还利息
					paybackMonth.setMonthsAomuntPaybacked(monthCapitalPayactualSum.add(monthInterestPayactualSum));
					// 减免金额(减免违约金+减免罚息)
					BigDecimal creditAmount = paybackMonth.getMonthPenaltyReductionSum().add(paybackMonth.getMonthPunishReductionSum());
					paybackMonth.setCreditAmount(creditAmount);
					// 未还违约金及罚息总额(未还违约金sum+未还罚息sum)
					paybackMonth.setPenaltyInterest(monthPenaltyShouldSum.subtract(monthPenaltyActualSum).add(monthInterestPunishshouldSum.subtract(monthInterestPunishactualSum)).subtract(creditAmount));
					// 已还违约金及罚息金额 实还违约金sum + 实还罚息sum
					paybackMonth.setOverDueAmontPaybackedSum(monthPenaltyActualSum.add(monthInterestPunishactualSum));
					// 实还总额   已还违约金及罚息金额 + 已还期供金额
					BigDecimal overDueAmontPaybacked = paybackMonth.getOverDueAmontPaybackedSum();
					overDueAmontPaybacked = overDueAmontPaybacked == null ? bgSum :overDueAmontPaybacked;
					BigDecimal monthsAomuntPaybacked = paybackMonth.getMonthsAomuntPaybacked();
					monthsAomuntPaybacked = monthsAomuntPaybacked == null ? bgSum :monthsAomuntPaybacked;
					BigDecimal actualAmount = overDueAmontPaybacked.add(monthsAomuntPaybacked);
					actualAmount = actualAmount == null ? bgSum :actualAmount;
					paybackMonth.setActualAmount(actualAmount);
				}
			}
		}else{
			logger.debug("invoke ConfirmPaybackService method: findDefaultConfirmInfo, paybackMonth is null");
		}
		return paybackMonth;
	}
	
	/**
	 * 获取保存的返款信息记录
	 * 2016年3月2日
	 * By zhaojinping
	 * @param ContractCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
   public UrgeServicesBackMoney getUrgeBackAmount(String ContractCode){
	   return dao.getUrgeBackAmount(ContractCode);
   }
	
	/**
	 * 获取结清确认时输入的审核意见
	 * 2016年3月3日
	 * By zhaojinping
	 * @param paybackOpe
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackOpe> getConfirmedRemark(PaybackOpe paybackOpe){
		return dao.getConfirmedRemark(paybackOpe);
	}
	
	/**
	 * 结清 是
	 * @param payback
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConfirmPayback(Payback payback){
		
		// 修改还款主表中的还款状态为结清
		payback.setDictPayStatus(RepayStatus.SETTLE.getCode());
		// 结清时间
		payback.setSettlementDate(new Date());
		payback.preUpdate();
		paybackService.updatePayback(payback);
		
		// 修改借款状态为结清
		LoanInfo li = payback.getLoanInfo();
		if(ObjectHelper.isEmpty(li)){
			li = new LoanInfo();
		}
		li.setDictLoanStatus(LoanStatus.SETTLE.getCode());
		// 设置结清时间
		li.setSettledDate(new Date());
		li.preUpdate();
		payback.setLoanInfo(li);
		paybackService.updateDictLoanStatus(payback);
		
		// 插入返款金额
		BigDecimal urgeBackMoney = payback.getUrgeServicesMoney().getUrgeMoeny();
		if (urgeBackMoney.compareTo(BigDecimal.ZERO)>0) {
			UrgeServicesBackMoney ub = new UrgeServicesBackMoney();
			ub.setrPaybackId(payback.getId());
			ub.setContractCode(payback.getContractCode());
			ub.setDictPayStatus(UrgeRepay.REPAY_TO_APPLY.getCode());
			ub.setSettlementTime(new Date());
			ub.setPaybackBackMoney(urgeBackMoney);
			earlyFinishConfirmService.insertBackAmount(ub);
		}

		// 操作历史
		PaybackOpeEx paybackOpes = new PaybackOpeEx(null,
		payback.getId(), RepaymentProcess.CONFIRM,
		TargetWay.REPAYMENT, PaybackOperate.CONFIRM_SUCCEED.getCode(), payback.getReturnReason());
		historyService.insertPaybackOpe(paybackOpes);
	}
	
	/**
	 * 结清 否
	 * @param payback
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveNotConfirmPayback(Payback payback) {
		// 修改还款主表中的还款状态为结清失败
		payback.setDictPayStatus(RepayStatus.SETTLE_FAILED.getCode());
		payback.preUpdate();
		paybackService.updatePayback(payback);
		
		// 记录历史
		PaybackOpeEx paybackOpes = new PaybackOpeEx(null, payback.getId(),
				RepaymentProcess.CONFIRM, TargetWay.REPAYMENT,
				PaybackOperate.CONFIRM_FAILED.getCode(),
				payback.getReturnReason());
		historyService.insertPaybackOpe(paybackOpes);
	}
}
