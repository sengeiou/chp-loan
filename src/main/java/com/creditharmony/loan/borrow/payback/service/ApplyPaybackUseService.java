package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.payback.dao.ApplyPaybackUseDao;
import com.creditharmony.loan.borrow.payback.dao.EarlyFinishConfirmDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.dao.PaybackBlueAmountDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.CeUtils;

/**
 * 还款用途申请service
 * @Class Name ApplyPaybackUseService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("applyPaybackUseService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ApplyPaybackUseService extends
		CoreManager<ApplyPaybackUseDao, Payback> {

	@Autowired
	private ApplyPaybackUseDao applyPaybackUseDao;
	
	@Autowired
	private PaybackDao paybackDao;
	
	@Autowired
	private PaybackBlueAmountDao paybackBlueAmountDao;
	
	@Autowired
	private EarlyFinishConfirmDao earlyFinishConfirmDao;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private CenterDeductService centerDeductService;
	
	@Autowired
	private PaybackMonthDao paybackMonthDao;
	
	@Autowired
	private EarlyFinishConfirmService earlyFinishService;
	
	public final static BigDecimal bgSum = BigDecimal.ZERO;

	/**
	 * 还款用途申请列表计算当前期供信息 
	 * 2015年12月20日 By zhangfeng
	 * @param payback
	 * @return PaybackMonth
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackMonth findCurrentmonthAmountList(Payback payback) {
		return applyPaybackUseDao.findCurrentmonthAmount(payback);
	}

	/**
	 * 还款用途申请详细信息计算逾期期供信息 
	 * 2015年12月21日 By zhangfeng
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public void findOverdueMonthAmount(Payback payback) {
		PaybackMonth paybackMonth = new PaybackMonth();
		paybackMonth.setDictMonthStatus("'"+PeriodStatus.OVERDUE.getCode()+"',"+"'"+PeriodStatus.REPLEVY.getCode()+"'");
		
		payback.setPaybackMonth(paybackMonth);
		PaybackMonth paybackMonthReturn = applyPaybackUseDao
				.findOverdueMonthAmount(payback);
		if (ObjectHelper.isEmpty(paybackMonthReturn)) {
			paybackMonth.setMonthAmountSum(bgSum);
			paybackMonth.setMonthPenaltySum(bgSum);
			paybackMonth.setMonthInterestPunishSum(bgSum);
			payback.setPaybackMonth(paybackMonth);
		} else {
			// 应还本金总额
			BigDecimal monthPayAmountSum = paybackMonthReturn
					.getMonthPayAmountSum();
			// 应还利息总额
			BigDecimal monthInterestBackShouldSum = paybackMonthReturn
					.getMonthInterestBackShouldSum();
			// 实还本金总额
			BigDecimal monthCapitalPayactualSum = paybackMonthReturn
					.getMonthCapitalPayactualSum();
			// 实还利息总额
			BigDecimal monthInterestPayactualSum = paybackMonthReturn
					.getMonthInterestPayactualSum();
			// 应还分期服务费总额
			BigDecimal monthFeeServiceSum = paybackMonthReturn
					.getMonthFeeServiceSum();
			// 实还分期服务费总额
			BigDecimal actualMonthFeeServiceSum = paybackMonthReturn
					.getActualMonthFeeServiceSum();

			monthPayAmountSum = monthPayAmountSum == null ? bgSum
					: monthPayAmountSum;
			monthInterestBackShouldSum = monthInterestBackShouldSum == null ? bgSum
					: monthInterestBackShouldSum;
			monthCapitalPayactualSum = monthCapitalPayactualSum == null ? bgSum
					: monthCapitalPayactualSum;
			monthInterestPayactualSum = monthInterestPayactualSum == null ? bgSum
					: monthInterestPayactualSum;
			monthFeeServiceSum = monthFeeServiceSum == null ? bgSum
					: monthFeeServiceSum;
			actualMonthFeeServiceSum = actualMonthFeeServiceSum == null ? bgSum
					: actualMonthFeeServiceSum;

			Contract contract = payback.getContract();
			if (!ObjectHelper.isEmpty(contract)) {
				String contractVersion = contract.getContractVersion();
				//if (StringUtils.isNotEmpty(contractVersion)) {
					if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4) {
						// 1.4新合同
						// 逾期未还期供总金额(应还本金Sum+应还利息Sum+应还分期服务费Sum-实还本金Sum-实还利息Sum-实还分期服务费Sum)
						paybackMonthReturn.setMonthAmountSum(monthPayAmountSum
								.add(monthInterestBackShouldSum)
								.add(monthFeeServiceSum)
								.subtract(monthCapitalPayactualSum)
								.subtract(monthInterestPayactualSum)
								.subtract(actualMonthFeeServiceSum));

						// 应还滞纳金总额(sum)
						BigDecimal monthLateFeeSum = paybackMonthReturn
								.getMonthLateFeeSum();
						// 实还滞纳金总额(sum)
						BigDecimal actualMonthLateFeeSum = paybackMonthReturn
								.getActualMonthLateFeeSum();
						// 减免滞纳金(sum)
						BigDecimal monthLateFeeReductionSum = paybackMonthReturn
								.getMonthLateFeeReductionSum();
						// 应还滞纳金总额(sum)
						monthLateFeeSum = monthLateFeeSum == null ? bgSum
								: monthLateFeeSum;
						// 实还滞纳金总额(sum)
						actualMonthLateFeeSum = actualMonthLateFeeSum == null ? bgSum
								: actualMonthLateFeeSum;
						// 减免滞纳金
						monthLateFeeReductionSum = monthLateFeeReductionSum == null ? bgSum
								: monthLateFeeReductionSum;

						// 未还违约金(滞纳金)总金额(应还违约金总额(sum)-实还违约金总额(sum)-减免违约金(sum))
						paybackMonthReturn.setMonthPenaltySum(monthLateFeeSum
								.subtract(actualMonthLateFeeSum).subtract(
										monthLateFeeReductionSum));
					} else {
						// 旧合同
						// 逾期未还期供总金额(应还本金+应还利息-实还本金-实还利息)
						paybackMonthReturn
								.setMonthAmountSum(monthPayAmountSum
										.add(monthInterestBackShouldSum)
										.subtract(
												monthCapitalPayactualSum
														.add(monthInterestPayactualSum)));
						// 应还违约金总额Sum
						BigDecimal monthPenaltyShouldSum = paybackMonthReturn
								.getMonthPenaltyShouldSum();
						// 实还违约金总额Sum
						BigDecimal monthPenaltyActualSum = paybackMonthReturn
								.getMonthPenaltyActualSum();
						// 减免违约金总额Sum
						BigDecimal monthPenaltyReductionSum = paybackMonthReturn
								.getMonthPenaltyReductionSum();

						monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum
								: monthPenaltyShouldSum;
						monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum
								: monthPenaltyActualSum;
						monthPenaltyReductionSum = monthPenaltyReductionSum == null ? bgSum
								: monthPenaltyReductionSum;

						// 未还违约金(滞纳金)总金额(应还滞纳金总额(sum)-实还滞纳金总额(sum)-减免滞纳金(sum))
						paybackMonthReturn
								.setMonthPenaltySum(monthPenaltyShouldSum
										.subtract(monthPenaltyActualSum)
										.subtract(monthPenaltyReductionSum));
					}
				//}
			}

			BigDecimal monthInterestPunishshouldSum = paybackMonthReturn
					.getMonthInterestPunishshouldSum();
			BigDecimal monthInterestPunishactualSum = paybackMonthReturn
					.getMonthInterestPunishactualSum();
			BigDecimal monthPunishReductionSum = paybackMonthReturn
					.getMonthPunishReductionSum();

			monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum
					: monthInterestPunishshouldSum;
			monthInterestPunishactualSum = monthInterestPunishactualSum == null ? bgSum
					: monthInterestPunishactualSum;
			monthPunishReductionSum = monthPunishReductionSum == null ? bgSum
					: monthPunishReductionSum;

			// 未还罚息总金额(应还罚息总额(sum)-实还罚息总额(sum)-减免罚息(sum))
			paybackMonthReturn
					.setMonthInterestPunishSum(monthInterestPunishshouldSum
							.subtract(monthInterestPunishactualSum).subtract(
									monthPunishReductionSum));
			payback.setPaybackMonth(paybackMonthReturn);
			// 设置还款冲抵日期
			payback.setModifyTime(new Date());
		}
	}

	/**
	 * 还款用途申请详细信息计算当期期供信息 2015年12月21日 By zhangfeng
	 * 
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public void findCurrentmonthAmount(Payback payback) {
		PaybackMonth paybackMonth = new PaybackMonth();

		PaybackMonth paybackMonthReturn = applyPaybackUseDao
				.findCurrentmonthAmount(payback);
		if (paybackMonthReturn == null) {
			paybackMonth.setMonthPenaltySum(bgSum);
			paybackMonth.setMonthInterestPunishSum(bgSum);
			payback.setPaybackMonth(paybackMonth);
		} else {
			// 应还违约金总额(sum)
			BigDecimal monthPenaltyShouldSum = paybackMonthReturn
					.getMonthPenaltyShouldSum();
			// 实还违约金总额(sum)
			BigDecimal monthPenaltyActualSum = paybackMonthReturn
					.getMonthPenaltyActualSum();
			// 减免违约金总额(sum)
			BigDecimal monthPenaltyReductionSum = paybackMonthReturn
					.getMonthPenaltyReductionSum();
			// 应还滞纳金总额(sum)
			BigDecimal monthLateFeeSum = paybackMonthReturn
					.getMonthLateFeeSum();
			// 实还滞纳金总额(sum)
			BigDecimal actualMonthLateFeeSum = paybackMonthReturn
					.getActualMonthLateFeeSum();
			// 应还罚息总额(sum)
			BigDecimal monthInterestPunishshouldSum = paybackMonthReturn
					.getMonthInterestPunishshouldSum();
			// 实还罚息总额(sum)
			BigDecimal monthInterestPunishactualSum = paybackMonthReturn
					.getMonthInterestPunishactualSum();
			// 减免滞纳金(sum)
			BigDecimal monthLateFeeReductionSum = paybackMonthReturn
					.getMonthLateFeeReductionSum();
			// 减免罚息(sum)
			BigDecimal monthPunishReductionSum = paybackMonthReturn
					.getMonthPunishReductionSum();
			// 提前结清金额
			BigDecimal monthBeforeFinishAmount = paybackMonthReturn
					.getMonthBeforeFinishAmount();

			monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum
					: monthPenaltyShouldSum;
			monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum
					: monthPenaltyActualSum;
			monthPenaltyReductionSum = monthPenaltyReductionSum == null ? bgSum
					: monthPenaltyReductionSum;
			monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum
					: monthInterestPunishshouldSum;
			monthInterestPunishactualSum = monthInterestPunishactualSum == null ? bgSum
					: monthInterestPunishactualSum;
			monthLateFeeSum = monthLateFeeSum == null ? bgSum : monthLateFeeSum;
			actualMonthLateFeeSum = actualMonthLateFeeSum == null ? bgSum
					: actualMonthLateFeeSum;
			monthLateFeeReductionSum = monthLateFeeReductionSum == null ? bgSum
					: monthLateFeeReductionSum;
			monthPunishReductionSum = monthPunishReductionSum == null ? bgSum
					: monthPunishReductionSum;
			monthBeforeFinishAmount = monthBeforeFinishAmount == null ? bgSum
					: monthBeforeFinishAmount;

			// 未还罚息总金额(sum)
			BigDecimal monthInterestPunishSum = monthInterestPunishshouldSum
					.subtract(monthInterestPunishactualSum).subtract(
							monthPunishReductionSum);

			BigDecimal monthPenaltySum = bgSum;
			BigDecimal penaltyInterest = bgSum;
			Contract contract = payback.getContract();
			if (!ObjectHelper.isEmpty(contract)) {
				String contractVersion = contract.getContractVersion();
				//if (StringUtils.isNotEmpty(contractVersion)) {
					if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4) {
						// 未还滞纳金总金额(sum)
						monthPenaltySum = monthLateFeeSum.subtract(
								actualMonthLateFeeSum).subtract(
								monthLateFeeReductionSum);
						// 未还滞纳金及罚息总额
						penaltyInterest = monthPenaltySum
								.add(monthInterestPunishSum);
					} else {
						// 未还违约金总金额(sum)
						monthPenaltySum = monthPenaltyShouldSum.subtract(
								monthPenaltyActualSum).subtract(
								monthPenaltyReductionSum);
						// 未还违约金及罚息总额
						penaltyInterest = monthPenaltySum
								.add(monthInterestPunishSum);
					}
				//}
			}
			paybackMonthReturn.setPenaltyInterest(penaltyInterest);
			// 一次性提前结清应还款额
			paybackMonthReturn
					.setMonthBeforeFinishAmount(monthBeforeFinishAmount);
			// 提前结清应还款总金额
			paybackMonthReturn.setShoudBackAmount(penaltyInterest
					.add(monthBeforeFinishAmount));
			// 减免金额
			if (paybackMonthReturn.getShoudBackAmount().compareTo(
					payback.getPaybackBuleAmount()) > Integer.parseInt(YESNO.NO
					.getCode())) {
				// 提前结清应还款总额 > 蓝补金额
				BigDecimal paybackBuleAmount = payback.getPaybackBuleAmount();
				// 提前结清应还款总额
				BigDecimal shoudBackAmount = paybackMonthReturn
						.getShoudBackAmount();

				paybackBuleAmount = paybackBuleAmount == null ? bgSum
						: paybackBuleAmount;
				shoudBackAmount = shoudBackAmount == null ? bgSum
						: shoudBackAmount;
				paybackMonthReturn.setCreditAmount(shoudBackAmount
						.subtract(paybackBuleAmount));
			} else {
				// 如果提前结清应还款总额 < = 蓝补金额 将减免金额置为0
				paybackMonthReturn.setCreditAmount(bgSum);
			}
			payback.setPaybackMonth(paybackMonthReturn);
		}
	}

	/**
	 * 提前还款或者逾期还款 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String overdueOrNormalPayback(Payback payback) {
		String msg = null;
		msg = this.overduePayback(payback, msg);
//		// 未还违约金(滞纳金)总额
//		BigDecimal monthPenaltySum = payback.getPaybackMonth()
//				.getMonthPenaltySum();
//		monthPenaltySum = monthPenaltySum == null ? bgSum : monthPenaltySum;
//		// 未还罚息总额
//		BigDecimal monthInterestPunishSum = payback.getPaybackMonth()
//				.getMonthInterestPunishSum();
//		monthInterestPunishSum = monthInterestPunishSum == null ? bgSum
//				: monthInterestPunishSum;
//		// 判断是否逾期
//		if (monthPenaltySum.compareTo(BigDecimal.ZERO) != 0
//				|| monthInterestPunishSum.compareTo(BigDecimal.ZERO) != 0) {
//			// 逾期
//			msg = this.overduePayback(payback, msg);
//		} else {
//			// 提前还款
//			msg = this.beforePayback(payback, msg);
//		}
		return msg;
	}

	/**
	 * 计算逾期金额 2016年1月7日 By zhangfeng
	 * 
	 * @param paybackMonth
	 * @return BigDecimal
	 * @return none
	 */
	public PaybackMonth calculateOneMonthAmount(PaybackMonth paybackMonth,
			String contractVersion) {
		// 计算当前期供逾期金额(应还本金-实还本金)+(应还利息-实还利息)
		// 未还分期服务费
		BigDecimal monthNoFeeService = paybackMonth.getMonthFeeService()
				.subtract(paybackMonth.getActualMonthFeeService());
		// 未还本金
		BigDecimal monthNoPaybacked = paybackMonth.getMonthPayAmount()
				.subtract(paybackMonth.getMonthCapitalPayactual());
		// 未还利息
		BigDecimal monthInterestNoPaybacked = paybackMonth
				.getMonthInterestBackshould().subtract(
						paybackMonth.getMonthInterestPayactual());
		// 未还违约金(应还违约金-实还违约金-减免违约金)
		BigDecimal monthPenaltyNoPaybacked = paybackMonth
				.getMonthPenaltyShould()
				.subtract(paybackMonth.getMonthPenaltyActual())
				.subtract(paybackMonth.getMonthPenaltyReduction());
		// 未还滞纳金(应还滞纳金-实还滞纳金-减免滞纳金)
		BigDecimal monthLateNoPaybacked = paybackMonth.getMonthLateFee()
				.subtract(
						paybackMonth.getActualMonthLateFee().subtract(
								paybackMonth.getMonthLateFeeReduction()));
		// 未还罚息
		BigDecimal monthInterestPunishNoPaybacked = paybackMonth
				.getMonthInterestPunishshould().subtract(
						paybackMonth.getMonthInterestPunishactual().subtract(
								paybackMonth.getMonthPunishReduction()));

		// 1.4版本合同  
		if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4) {
			// 逾期未还期供总金额
			paybackMonth.setMonthAmountSum(monthNoFeeService.add(
					monthNoPaybacked).add(monthInterestNoPaybacked));
			// 未还滞纳金金额
			paybackMonth.setPenaltyLateNoPaybacked(monthLateNoPaybacked);
		}else{
			// 逾期未还期供总金额
			paybackMonth.setMonthAmountSum(monthNoPaybacked.add(monthInterestNoPaybacked));
			// 未还违约金金额
			paybackMonth.setPenaltyLateNoPaybacked(monthPenaltyNoPaybacked);
		}
		// 未还罚息
		paybackMonth.setInterestNoPaybacked(monthInterestPunishNoPaybacked);
		return paybackMonth;
	}

	/**
	 * 查询期供（没有逾期） 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackMonth> getPaybackMonth(Payback payback) {
		return applyPaybackUseDao.getPaybackMonth(payback);
	}

	/**
	 * 查询期供（逾期） 2016年1月7日 By zhangfeng
	 * 
	 * @param paybackMonth
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackMonth> getOverduePaybackMonth(PaybackMonth paybackMonth) {
		return applyPaybackUseDao.getOverduePaybackMonth(paybackMonth);
	}

	/**
	 * 冲抵期供 2016年1月6日 By zhangfeng
	 * 
	 * @param paybackMonth
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateMonthsAmount(PaybackMonth paybackMonth) {
		int result = applyPaybackUseDao.updateMonthsAmount(paybackMonth);
		//给大金融推送最新期供信息
		earlyFinishService.sendDjrPaybackMonthData(paybackMonth);
		return result;
	}

	/**
	 * 保存冲抵信息 2016年1月6日 By zhangfeng
	 * 
	 * @param files
	 * @param paybackCharge
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertOffset(MultipartFile[] files, PaybackCharge paybackCharge) {
		if (files[0].getSize() > 0) {
			DocumentBean db = CeUtils.uploadFile(files[0],
					paybackCharge.getContractCode(),CeFolderType.LOAN_CHANGE);
			if (!ObjectHelper.isEmpty(db)) {
				paybackCharge.setUploadPath(db.getDocId());
				paybackCharge.setUploadFilename(db.getDocTitle());
				paybackCharge.setUploadName(UserUtils.getUser().getId());
				paybackCharge.setUploadDate(new Date());
				applyPaybackUseDao.insertOffset(paybackCharge);
			}
		}
	}

	/**
	 * 提前结清历史 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void beforeConfirmPayback(Payback payback) {
		String paybackApplyId = null;
		if (payback.getPaybackCharge() != null) {
			paybackApplyId = payback.getPaybackCharge().getId();
		}
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.REPAYMENT_APPLY, TargetWay.REPAYMENT,
				PaybackOperate.APPLY_SUCEED.getCode(), "合同编号:" + payback.getContractCode() + "申请提前结清总额:"
						+ payback.getPaybackCharge().getApplyBuleAmount());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 正常还款历史 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void normalPaybackHistory(Payback payback) {
		String paybackApplyId = null;
		if (payback.getPaybackApply() != null) {
			paybackApplyId = payback.getPaybackApply().getId();
		}
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.REPAYMENT_APPLY,
				TargetWay.PAYMENT, "成功", payback.getContractCode());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 结清还款历史 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void confirmPaybackHistory(Payback payback) {
		String paybackApplyId = null;
		if (payback.getPaybackApply() != null) {
			paybackApplyId = payback.getPaybackApply().getId();
		}
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.CONFIRM, TargetWay.PAYMENT,
				RepayStatus.SETTLE_CONFIRM.getCode(), payback.getContractCode());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 逾期还款历史 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void overduePaybackHistory(Payback payback) {
		String paybackApplyId = null;
		if (payback.getPaybackApply() != null) {
			paybackApplyId = payback.getPaybackApply().getId();
		}
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.REPAYMENT_APPLY,
				TargetWay.PAYMENT, RepayStatus.OVERDUE.getCode(),
				payback.getContractCode());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 逾期还款冲抵业务逻辑 2016年1月7日 By zhangfeng
	 * @param payback
	 * @param msg
	 * @return none 门店冲抵old
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String overduePayback(Payback payback, String msg) {
		String contractVersion = "";
		BigDecimal	amount  = new BigDecimal("0.00");
		int overdueSum = 0; //冲抵逾期次数
		List<PaybackMonth> paybackMonthList = new ArrayList<PaybackMonth>();
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		PaybackMonth paybackMonth = new PaybackMonth();
		// 申请还期供金额
		BigDecimal applyAmountPayback = payback.getPaybackCharge().getApplyAmountPayback();
		applyAmountPayback = applyAmountPayback == null ? bgSum : applyAmountPayback;
		// 申请违约金总额
		BigDecimal applyAmountViolate = payback.getPaybackCharge().getApplyAmountViolate();
		applyAmountViolate = applyAmountViolate == null ? bgSum : applyAmountViolate;
		// 申请还罚息总额
		BigDecimal applyAmountPunish = payback.getPaybackCharge().getApplyAmountPunish();
		applyAmountPunish = applyAmountPunish == null ? bgSum : applyAmountPunish;
		// 查询需要冲抵的逾期期供
		paybackMonth.setContractCode(payback.getContractCode());
		paybackMonth.setDictMonthStatus("'" + PeriodStatus.OVERDUE.getCode() + "'," + "'" + PeriodStatus.REPLEVY.getCode() + "'");
		paybackMonthList = this.getOverduePaybackMonth(paybackMonth);
		
		// 冲抵期供
		if(applyAmountPayback.compareTo(bgSum) > 0){
			 amount = overDuePaybackMonth(payback, paybackMonthList, applyAmountPayback);
			// 更改主表蓝补金额
			payback.setPaybackBuleAmount(amount);
			payback.preUpdate();
			updateBlueMonths(payback);
			// 还款操作流水
			//normalPaybackHistory(payback);
			msg = YESNO.YES.getCode();
		}
		
		//冲抵违约金，罚息
		if(applyAmountViolate.compareTo(bgSum) > 0 || applyAmountPunish.compareTo(bgSum) > 0){
		    for (int i = 0; i < paybackMonthList.size(); i++) {
				PaybackMonth paybackMonthInfo = paybackMonthList.get(i);
				nullJudge(paybackMonthList.get(i));
				if (!ObjectHelper.isEmpty(payback.getContract())) {
					if (StringUtils.isNotEmpty(payback.getContract().getContractVersion())) {
						contractVersion = payback.getContract().getContractVersion();
					}
		     	}
				
			// 计算单期逾期违约金(滞纳金)及罚息金额
			paybackMonthInfo = this.calculateOneMonthAmount(paybackMonthList.get(i), contractVersion);
			if (paybackMonthInfo.getInterestNoPaybacked().compareTo(bgSum) > 0 && applyAmountPunish.compareTo(bgSum) > 0){
				BigDecimal updateInterestAmount = new BigDecimal("0.00");
				if(applyAmountPunish.compareTo(paybackMonthInfo
						.getInterestNoPaybacked()) >= 0){
					updateInterestAmount =  paybackMonthInfo.getInterestNoPaybacked();
					applyAmountPunish = applyAmountPunish.subtract(updateInterestAmount);
				}else{
					updateInterestAmount = applyAmountPunish;
					applyAmountPunish = bgSum;
				}
				paybackMonthInfo.setMonthInterestPunishactual(paybackMonthInfo.getMonthInterestPunishactual().add(updateInterestAmount));
				paybackMonthInfo.setModifyBy(UserUtils.getUser().getId());
				paybackMonthInfo.setModifyTime(new Date());
				paybackMonthInfo.preUpdate();
				//applyPaybackUseDao.updateMonthsAmount(paybackMonthInfo);
				updateMonthsAmount(paybackMonthInfo);
				
				// 冲抵加锁
				Map<String,String>  map = new HashMap<String, String>();
				map.put("reqId", payback.getId());
				Payback paybackReq = applyPaybackUseDao.getPaybackReq(map);
				if (ObjectHelper.isNotEmpty(paybackReq)) {
					// 对比蓝补金额是否一致，一致更新
					if (paybackReq.getPaybackBuleAmount().compareTo(payback.getPaybackBuleAmount()) == 0) {
						// 修改蓝补
				    	amount = payback.getPaybackBuleAmount().subtract(updateInterestAmount);
						payback.setPaybackBuleAmount(amount);
						payback.setModifyBy(UserUtils.getUser().getId());
						payback.setModifyTime(new Date());
						payback.preUpdate();
						updateBlueMonths(payback);
						
						//蓝补历史
						updateBlueMonthsHistory(paybackMonthInfo.getContractCode(), amount, paybackMonthInfo.getId(),
								TradeType.TURN_OUT.getCode(), AgainstContent.PUNISH.getCode(), updateInterestAmount,paybackMonthInfo);
					}else{
						throw new ServiceException("冲抵异常！");
					}
				}else{
					throw new ServiceException("冲抵异常！");
				}
			}
			// 1.3版本合同，冲抵违约金
			String dictDealUse = "门店";
			if (paybackMonthInfo.getPenaltyLateNoPaybacked().compareTo(bgSum) > 0 && applyAmountViolate.compareTo(bgSum) > 0) {
				BigDecimal updatePenaltyAmount = new BigDecimal("0.00");
				
				if(applyAmountViolate.compareTo(paybackMonthInfo
						.getPenaltyLateNoPaybacked()) >= 0){
					updatePenaltyAmount =  paybackMonthInfo.getPenaltyLateNoPaybacked();
					applyAmountViolate = applyAmountViolate.subtract(updatePenaltyAmount);
				}else{
					updatePenaltyAmount = applyAmountViolate;
					applyAmountViolate = bgSum;
				}
				if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4) {
					// 1.4版本合同 设置实还滞纳金
					paybackMonthInfo.setActualMonthLateFee(paybackMonthInfo.getActualMonthLateFee().add(updatePenaltyAmount));
					paybackBuleAmont.setDictDealUse(AgainstContent.SURCHARGE.getCode());// 冲抵内容
					dictDealUse += AgainstContent.SURCHARGE.getName();
				} else {
					// 1.3版本合同 设置实还违约金
					paybackMonthInfo.setMonthPenaltyActual(paybackMonthInfo
							.getMonthPenaltyActual().add(updatePenaltyAmount));
					paybackBuleAmont.setDictDealUse(AgainstContent.VIOLATE.getCode());// 冲抵内容
					dictDealUse += AgainstContent.VIOLATE.getName();
				}
				paybackMonthInfo.setModifyBy(UserUtils.getUser().getId());
				paybackMonthInfo.setModifyTime(new Date());
				paybackMonthInfo.preUpdate();
				//applyPaybackUseDao.updateMonthsAmount(paybackMonthInfo);
				updateMonthsAmount(paybackMonthInfo);

				// 修改蓝补
				amount = payback.getPaybackBuleAmount().subtract(updatePenaltyAmount);
				payback.setPaybackBuleAmount(amount);
				payback.setModifyBy(UserUtils.getUser().getId());
				payback.setModifyTime(new Date());
				payback.preUpdate();
				updateBlueMonths(payback);
				
				// 蓝补历史
				updateBlueMonthsHistory(paybackMonthInfo.getContractCode(),
						amount, paybackMonthInfo.getId(),
						TradeType.TURN_OUT.getCode(), dictDealUse,
						updatePenaltyAmount,paybackMonthInfo);
			}
			
			// 当期未还违约金(滞纳金)及罚息总额为0，则更新期供状态为已还款
			paybackMonthInfo = this.calculateOneMonthAmount(paybackMonthInfo, contractVersion);
			// 期供和违约金罚息还完修改期供状态  未还逾期期供总额+未还违约金(滞纳金)+未还罚息
			if (paybackMonthInfo.getMonthAmountSum().add(paybackMonthInfo.getPenaltyLateNoPaybacked()).add(
					paybackMonthInfo.getInterestNoPaybacked()).compareTo(bgSum) == 0) {
				paybackMonthInfo.setDictMonthStatus(PeriodStatus.PAID.getCode());
				paybackMonthInfo.setModifyBy(UserUtils.getUser().getId());
				paybackMonthInfo.setModifyTime(new Date());
				//applyPaybackUseDao.updateMonthsAmount(paybackMonthInfo);
				updateMonthsAmount(paybackMonthInfo);
				overdueSum++;
			}
			
			// 保存还款历史明细
			BigDecimal payAmount = paybackMonthInfo.getInterestNoPaybacked().add(
							paybackMonthInfo.getPenaltyLateNoPaybacked());
			centerDeductService.addPaybackHis(paybackMonthInfo.getId(),payAmount, paybackMonthInfo.getContractCode());
			msg = YESNO.YES.getCode();
			}
		    // 判断是否还完所有逾期的期供和违约金罚息，还完更新还款主表和借款表
 			if (StringUtils.equals(StringUtils.toStr(overdueSum), StringUtils.toStr(paybackMonthList.size()))) {
 				//逾期和还款中修改状态，提前结清则不用
 				if(StringUtils.equals(payback.getDictPayStatus(), RepayStatus.OVERDUE.getCode()) || StringUtils.equals(payback.getDictPayStatus(), RepayStatus.PEND_REPAYMENT.getCode())){
 					LoanInfo loanInfo = payback.getLoanInfo();
 	 				if(payback.getPaybackCurrentMonth() == payback.getContract().getContractMonths().intValue()){
 	 					// 最后一期 修改状态为结清待审核
 	 					// 如果最后一期期供状态为结清或者是提前结清，则修改状态为结清待审核 否则为还款中
 	 					Map<String,Object> param = new HashMap<String,Object>();
 	 					param.put("contractCode", payback.getContractCode());
 	 					PaybackMonth lastPayback = paybackMonthDao.findLastPaybackInfo(param);
 	 					if(StringUtils.equals(lastPayback.getDictMonthStatus(),RepayStatus.SETTLE.getCode()) || StringUtils.equals(lastPayback.getDictMonthStatus(),RepayStatus.PRE_SETTLE.getCode())){
 	 						loanInfo.setDictLoanStatus(LoanStatus.SETTLE_CONFIRM.getCode());
 	 						payback.setDictPayStatus(RepayStatus.SETTLE_CONFIRM.getCode());
 	 					}else{
 	 						loanInfo.setDictLoanStatus(LoanStatus.REPAYMENT.getCode());
 	 	 					payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
 	 					}
 	 				}else{
 	 					loanInfo.setDictLoanStatus(LoanStatus.REPAYMENT.getCode());
 	 					payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
 	 				}
 	 				
 	 				// 修改还款主表
 	 				payback.preUpdate();
 	 				paybackDao.updatePayback(payback);
 	 				
 	 				// 修改借款申请表
 	 				loanInfo.setModifyBy(UserUtils.getUser().getId());
 	 				loanInfo.setModifyTime(new Date());
 	 				loanInfo.preUpdate();
 	 				paybackDao.updateDictLoanStatus(payback);	
 				}
 			}
		}
		return msg;
	}

	/**
	 * 1.3版本合同 提前还款业务逻辑 2016年1月7日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String beforePayback(Payback payback, String msg) {
		List<PaybackMonth> paybackMonthList = new ArrayList<PaybackMonth>();
		// 查询需要冲抵的期供
		paybackMonthList = this.getPaybackMonth(payback);
		// 申请还期供金额
		BigDecimal applyAmountPayback = payback.getPaybackCharge()
				.getApplyAmountPayback();
		applyAmountPayback = applyAmountPayback == null ? bgSum
				: applyAmountPayback;

		// 提前还款冲抵
		if (ArrayHelper.isNotEmpty(paybackMonthList)) {
			for (int i = 0; i < paybackMonthList.size(); i++) {
				nullJudge(paybackMonthList.get(i));
			}
			// 冲抵金额
			BigDecimal amount = OffsetPaybackMonth(payback, paybackMonthList,
					applyAmountPayback);

			// 更改主表蓝补金额
			payback.setPaybackBuleAmount(amount);
			payback.preUpdate();
			updateBlueMonths(payback);
			msg = YESNO.YES.getCode();

		} else {
			// 没有期供
			msg = "此用户没有期供!";
		}
		return msg;
	}

	// /** 1.4版本合同
	// * 提前还款业务逻辑
	// * 2016年1月7日
	// * By zhangfeng
	// * @param payback
	// * @return none
	// */
	// @Transactional(readOnly = false, value = "loanTransactionManager")
	// public String beforePayback(Payback payback,String msg) {
	// List<PaybackMonth> paybackMonthList = new ArrayList<PaybackMonth>();
	//
	// // 查询需要冲抵的期供
	// paybackMonthList = this.getPaybackMonth(payback);
	//
	// // 申请还期供金额
	// BigDecimal applyAmountPayback =
	// payback.getPaybackCharge().getApplyAmountPayback();
	// applyAmountPayback = applyAmountPayback == null ? bgSum :
	// applyAmountPayback;
	//
	// // 提前还款冲抵
	// if (ArrayHelper.isNotEmpty(paybackMonthList)) {
	// // 冲抵金额
	// BigDecimal amount = OffsetPaybackMonth(payback, paybackMonthList,
	// applyAmountPayback);
	//
	// // 更改主表蓝补金额
	// payback.setPaybackBuleAmount(amount);
	// payback.preUpdate();
	// updateBlueMonths(payback);
	//
	// // 蓝补历史
	// updateBlueMonthsHistory(payback);
	//
	// // 操作历史
	// normalPaybackHistory(payback);
	// } else {
	// // 没有期供
	// msg = "此用户没有期供!";
	// }
	// return msg;
	// }
	//
	/**
	 * 逾期冲抵期供(本金和利息) 
	 * 2016年3月7日 By zhangfeng
	 * 
	 * @param payback
	 * @param paybackMonthList
	 * @param applyAmountPayback
	 * @return
	 */
	private BigDecimal overDuePaybackMonth(Payback payback,
			List<PaybackMonth> paybackMonthList, BigDecimal applyAmountPayback) {
		// 蓝补冲抵金额
		BigDecimal blueAmount = payback.getPaybackBuleAmount();
		Contract contract = payback.getContract();
		String contractVersion = "";
		if(!ObjectHelper.isEmpty(contract)){
			contractVersion = contract.getContractVersion();
		}
		int maxOverMonths=0;
		// 查询逾期或追回冲抵到哪一期(最大一个的期数)
		for(PaybackMonth paybackMonth : paybackMonthList){
			if(PeriodStatus.OVERDUE.getCode().equals(paybackMonth.getDictMonthStatus())){
				// 得到逾期的最大期数
				maxOverMonths = paybackMonth.getMonths();
			}
		}
		
		// 循环冲抵
		for (int i = 0; i < paybackMonthList.size(); i++) {
			if(bgSum.compareTo(applyAmountPayback) == 0){
				break;
			}
			PaybackMonth pm = paybackMonthList.get(i);
	   /*	逾期自动冲抵规则，只冲抵到当前期
	 * // 判断逾期冲抵时冲抵是否为当前期
			Date monthPayDay = pm.getMonthPayDay();
			if (DateUtils.getDate("yyyy-MM").equals(
					DateUtils.formatDate(monthPayDay, "yyyy-MM"))) {
				// 冲抵期为当前期，逾期冲抵时冲抵到当前期后，不再继续向下冲抵
				Calendar now = Calendar.getInstance();
				// 获取当前日期的日
				int paybackMonthDay = now.get(Calendar.DAY_OF_MONTH);
				// 账单日
				int paybackDay = payback.getPaybackDay();
				if(paybackMonthDay < paybackDay){
					// 如果当前日期小于账单日，当期不再继续进行冲抵
					break;
				}
			}
			*/
			BigDecimal blueAmountBack = payback.getPaybackBuleAmount();
			blueAmountBack = blueAmountBack == null ? bgSum : blueAmountBack;
			// 应还分期服务费
			BigDecimal monthFeeService = pm.getMonthFeeService();
			monthFeeService = (monthFeeService == null) ? bgSum
					: monthFeeService;
			// 实还分期服务费
			BigDecimal actualMonthFeeService = pm.getActualMonthFeeService();
			actualMonthFeeService = (actualMonthFeeService == null) ? bgSum
					: actualMonthFeeService;
			// 应还利息
			BigDecimal monthInterestBackshould = pm
					.getMonthInterestBackshould();
			monthInterestBackshould = monthInterestBackshould == null ? bgSum
					: monthInterestBackshould;
			// 实还利息
			BigDecimal monthInterestPayactual = pm.getMonthInterestPayactual();
			monthInterestPayactual = monthInterestPayactual == null ? bgSum
					: monthInterestPayactual;
			// 应还本金
			BigDecimal monthPayAmount = pm.getMonthPayAmount();
			monthPayAmount = (monthPayAmount == null) ? bgSum : monthPayAmount;
			// 实还本金
			BigDecimal monthCapitalPayactual = pm.getMonthCapitalPayactual();
			monthCapitalPayactual = monthCapitalPayactual == null ? bgSum
					: monthCapitalPayactual;
			// 该还分期服务费
			BigDecimal feeSrvice = monthFeeService
					.subtract(actualMonthFeeService);
			// 该还利息
			BigDecimal interest = monthInterestBackshould
					.subtract(monthInterestPayactual);
			// 该还本金
			BigDecimal principal = monthPayAmount
					.subtract(monthCapitalPayactual);
			// 期供状态
			String dictMonthStatus = pm.getDictMonthStatus();
			// 冲抵分期服务费
			if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4) {
				// 1.4版本合同
				if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
					if (feeSrvice.compareTo(bgSum) >= 0 && applyAmountPayback.compareTo(feeSrvice) >= 0 ) {
						// 实还分期服务费
						pm.setActualMonthFeeService(actualMonthFeeService
								.add(feeSrvice));
						System.out.println(feeSrvice.compareTo(bgSum));
						applyAmountPayback = applyAmountPayback.subtract(feeSrvice);
						if (blueAmount.compareTo(feeSrvice) >= 0) {
							blueAmount = blueAmount.subtract(feeSrvice);
						}
						// 蓝补交易明细
						updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
								pm.getId(), TradeType.TURN_OUT.getCode(),
								AgainstContent.MONTHSERVICEFEE.getCode(), feeSrvice,pm);
					} else {
						// 不够冲抵利息,直接冲入分期服务费中
						pm.setActualMonthFeeService(actualMonthFeeService
								.add(applyAmountPayback));
						blueAmount = BigDecimal.ZERO;
						applyAmountPayback = BigDecimal.ZERO;
						// 蓝补交易明细
						updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
								pm.getId(), TradeType.TURN_OUT.getCode(),
								AgainstContent.MONTHSERVICEFEE.getCode(),
								applyAmountPayback,pm);
					}
				  }
			}
			// 冲抵利息
			if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
				if (interest.compareTo(bgSum) >= 0 && applyAmountPayback.compareTo(interest) >= 0) {
					// 实还利息
					pm.setMonthInterestPayactual(monthInterestPayactual
							.add(interest));
					applyAmountPayback = applyAmountPayback.subtract(interest);
					if (blueAmount.compareTo(interest) >= 0) {
						blueAmount = blueAmount.subtract(interest);
					}
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHINTEREST.getCode(), interest,pm);
				} else {
					// 不够冲抵利息,直接冲入利息
					pm.setMonthInterestPayactual(monthInterestPayactual
							.add(applyAmountPayback));
					blueAmount = BigDecimal.ZERO;
					applyAmountPayback = BigDecimal.ZERO;
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHINTEREST.getCode(),
							applyAmountPayback,pm);
				}
			}
			// 冲抵本金
			if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
				if (interest.compareTo(bgSum)>= 0 && applyAmountPayback.compareTo(principal) >= 0) {
					// 实还本金
					pm.setMonthCapitalPayactual(monthCapitalPayactual
							.add(principal));
					if (PeriodStatus.OVERDUE.getCode().equals(dictMonthStatus)) {
						// 逾期还款冲抵后的操作
						// 如果期供状态为‘逾期’，将期供状态改为‘追回’
						pm.setDictMonthStatus(PeriodStatus.REPLEVY.getCode());
						if(maxOverMonths == pm.getMonths()){
							// 逾期冲抵的最后一期
							// 更新借款状态 为'还款中'
							LoanInfo loanInfo = payback.getLoanInfo();
							if (!ObjectHelper.isEmpty(loanInfo)) {
								loanInfo.setDictLoanStatus(LoanStatus.REPAYMENT
										.getCode());
							}
							loanInfo.setModifyBy(UserUtils.getUser().getId());
							loanInfo.setModifyTime(new Date());
							loanInfo.preUpdate();
							paybackDao.updateDictLoanStatus(payback);
							// 更新还款状态为‘还款中’
							payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT
									.getCode());
							payback.preUpdate();
							paybackDao.updatePayback(payback);
						}
					}else{
						// 期供状态为还款中，即提前还款，更新期供状态为‘已还款’
						pm.setDictMonthStatus(PeriodStatus.PAID.getCode());
					}
					// 判断是否最后一期
					if (pm.getMonths() == payback
							.getContract().getContractMonths().intValue()) {
						// 更改还款状态流转数据到结清页面
						payback.setDictPayStatus(RepayStatus.SETTLE_CONFIRM
								.getCode());
						payback.preUpdate();
						paybackDao.updatePayback(payback);
						// 更新借款状态为‘结清待确认’
						LoanInfo loanInfo = payback.getLoanInfo();
						if (!ObjectHelper.isEmpty(loanInfo)) {
							loanInfo.setDictLoanStatus(LoanStatus.SETTLE_CONFIRM
									.getCode());
						}
						loanInfo.setModifyBy(UserUtils.getUser().getId());
						loanInfo.setModifyTime(new Date());
						loanInfo.preUpdate();
						paybackDao.updateDictLoanStatus(payback);
					}else{
						// 更改主表当前期数
						payback.setPaybackCurrentMonth(payback
								.getPaybackCurrentMonth() + 1);
						payback.setModifyBy(UserUtils.getUser().getId());
						payback.setModifyTime(new Date());
						payback.preUpdate();
						updateBlueMonths(payback);
					}
					applyAmountPayback = applyAmountPayback.subtract(principal);
					if (blueAmount.compareTo(principal) >= 0) {
						blueAmount = blueAmount.subtract(principal);
					}
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHPRINCIPAL.getCode(), principal,pm);
				} else {
					// 不够冲抵本金,直接冲入本金
					pm.setMonthCapitalPayactual(monthCapitalPayactual
							.add(applyAmountPayback));
					if (blueAmount.compareTo(feeSrvice) >= 0) {
						blueAmount = blueAmount.subtract(applyAmountPayback);
					}
					applyAmountPayback = BigDecimal.ZERO;
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHPRINCIPAL.getCode(),
							applyAmountPayback,pm);
				}
			}
			// 更新期供
			// 设置期供表中的实际还款日
			pm.setMonthPayActualday(new Date());
			pm.preUpdate();
			updateMonthsAmount(pm);
			// 保存还款历史明细
			BigDecimal payAmount = blueAmountBack.subtract(blueAmount);
			centerDeductService.addPaybackHis(pm.getId(), payAmount,
					pm.getContractCode());
		}
		return blueAmount;
	}

	
	private BigDecimal OffsetPaybackMonth(Payback payback,
			List<PaybackMonth> paybackMonthList, BigDecimal applyAmountPayback) {
		// 蓝补冲抵金额
		BigDecimal blueAmount = payback.getPaybackBuleAmount();
		Contract contract = payback.getContract();
		String contractVersion = "";
		if(!ObjectHelper.isEmpty(contract)){
			contractVersion = contract.getContractVersion();
		}
		// 循环冲抵
		for (int i = 0; i < paybackMonthList.size(); i++) {
			PaybackMonth pm = paybackMonthList.get(i);
			BigDecimal blueAmountBack = payback.getPaybackBuleAmount();
			blueAmountBack = blueAmountBack == null ? bgSum : blueAmountBack;
			// 应还分期服务费
			BigDecimal monthFeeService = pm.getMonthFeeService();
			monthFeeService = (monthFeeService == null) ? bgSum
					: monthFeeService;
			// 实还分期服务费
			BigDecimal actualMonthFeeService = pm.getActualMonthFeeService();
			actualMonthFeeService = (actualMonthFeeService == null) ? bgSum
					: actualMonthFeeService;
			// 应还利息
			BigDecimal monthInterestBackshould = pm
					.getMonthInterestBackshould();
			monthInterestBackshould = monthInterestBackshould == null ? bgSum
					: monthInterestBackshould;
			// 实还利息
			BigDecimal monthInterestPayactual = pm.getMonthInterestPayactual();
			monthInterestPayactual = monthInterestPayactual == null ? bgSum
					: monthInterestPayactual;

			// 应还本金
			BigDecimal monthPayAmount = pm.getMonthPayAmount();
			monthPayAmount = (monthPayAmount == null) ? bgSum : monthPayAmount;
			// 实还本金
			BigDecimal monthCapitalPayactual = pm.getMonthCapitalPayactual();
			monthCapitalPayactual = monthCapitalPayactual == null ? bgSum
					: monthCapitalPayactual;
			// 该还分期服务费
			BigDecimal feeSrvice = monthFeeService
					.subtract(actualMonthFeeService);
			// 该还利息
			BigDecimal interest = monthInterestBackshould
					.subtract(monthInterestPayactual);
			// 该还本金
			BigDecimal principal = monthPayAmount
					.subtract(monthCapitalPayactual);
			// 冲抵分期服务费
			if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4){
				// 1.4版本合同
				if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
					if (applyAmountPayback.compareTo(feeSrvice) >= 0) {
						// 实还分期服务费
						pm.setActualMonthFeeService(actualMonthFeeService
								.add(feeSrvice));
						applyAmountPayback = applyAmountPayback.subtract(feeSrvice);
						if (blueAmount.compareTo(feeSrvice) >= 0) {
							blueAmount = blueAmount.subtract(feeSrvice);
						}
						// 蓝补交易明细
						updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
								pm.getId(), TradeType.TURN_OUT.getCode(),
								AgainstContent.MONTHSERVICEFEE.getCode(), feeSrvice,pm);
					} else {
						// 不够冲抵利息,直接冲入分期服务费中
						pm.setActualMonthFeeService(actualMonthFeeService
								.add(applyAmountPayback));
						blueAmount = BigDecimal.ZERO;
						applyAmountPayback = BigDecimal.ZERO;
						// 蓝补交易明细
						updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
								pm.getId(), TradeType.TURN_OUT.getCode(),
								AgainstContent.MONTHSERVICEFEE.getCode(),
								applyAmountPayback,pm);
					}
				  }
			}
			// 冲抵利息
			if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
				if (applyAmountPayback.compareTo(interest) >= 0) {
					// 实还利息
					pm.setMonthInterestPayactual(monthInterestPayactual
							.add(interest));

					applyAmountPayback = applyAmountPayback.subtract(interest);
					if (blueAmount.compareTo(interest) >= 0) {
						blueAmount = blueAmount.subtract(interest);
					}

					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHINTEREST.getCode(), interest,pm);
				} else {
					// 不够冲抵利息,直接冲入利息
					pm.setMonthInterestPayactual(monthInterestPayactual
							.add(applyAmountPayback));
					blueAmount = BigDecimal.ZERO;
					applyAmountPayback = BigDecimal.ZERO;

					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHINTEREST.getCode(),
							applyAmountPayback,pm);
				}

			}
			// 冲抵本金
			if (applyAmountPayback.compareTo(BigDecimal.ZERO) > 0) {
				if (applyAmountPayback.compareTo(principal) >= 0) {
					// 实还本金
					pm.setMonthCapitalPayactual(monthCapitalPayactual
							.add(principal));
					
					// 判断是否最后一期
					if (payback.getPaybackCurrentMonth() == payback
							.getContract().getContractMonths().intValue()) {
						// 更改状态流转数据到结清页面
						payback.setDictPayStatus(RepayStatus.SETTLE_CONFIRM
								.getCode());
						payback.preUpdate();
						paybackDao.updatePayback(payback);
						// 结清操作历史
						confirmPaybackHistory(payback);
					}else{
						// 更改主表当前期数
						payback.setPaybackCurrentMonth(payback
								.getPaybackCurrentMonth() + 1);
						payback.setModifyBy(UserUtils.getUser().getId());
						payback.setModifyTime(new Date());
						payback.preUpdate();
						updateBlueMonths(payback);
					}
					applyAmountPayback = applyAmountPayback.subtract(principal);
					if (blueAmount.compareTo(principal) >= 0) {
						blueAmount = blueAmount.subtract(principal);
					}
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHPRINCIPAL.getCode(), principal,pm);
				} else {
					// 不够冲抵本金,直接冲入本金
					pm.setMonthCapitalPayactual(monthCapitalPayactual
							.add(applyAmountPayback));
					if (blueAmount.compareTo(feeSrvice) >= 0) {
						blueAmount = blueAmount.subtract(applyAmountPayback);
					}
					applyAmountPayback = BigDecimal.ZERO;
					// 蓝补交易明细
					updateBlueMonthsHistory(pm.getContractCode(), blueAmount,
							pm.getId(), TradeType.TURN_OUT.getCode(),
							AgainstContent.MONTHPRINCIPAL.getCode(),
							applyAmountPayback,pm);
				}
			}
			// 更新期供
			// 设置期供表中的实际还款日
			pm.setMonthPayActualday(new Date());
			pm.preUpdate();
			updateMonthsAmount(pm);
			// 保存还款历史明细
			BigDecimal payAmount = blueAmountBack.subtract(blueAmount);
			centerDeductService.addPaybackHis(pm.getId(), payAmount,
					pm.getContractCode());
		}
		return blueAmount;
	}
	/**
	 * 
	 * 2016年4月13日 By zhaojinping
	 * 
	 * @param contractCode
	 * @param blueAmount
	 *            蓝补余额
	 * @param rPaybackMonthId
	 *            期供id
	 * @param TradeTypeCode
	 *            交易类型
	 * @param dictDealUseCode
	 *            冲抵内容
	 * @param tradeAmount
	 *            交易金额
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateBlueMonthsHistory(String contractCode,
			BigDecimal blueAmount, String rPaybackMonthId,
			String TradeTypeCode, String dictDealUseCode, BigDecimal tradeAmount,PaybackMonth month) {
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		paybackBuleAmount.setrMonthId(rPaybackMonthId);
		paybackBuleAmount.setDealTime(new Date());
		paybackBuleAmount.setTradeType(TradeTypeCode);
		paybackBuleAmount.setOperator(UserUtils.getUser().getId());
		paybackBuleAmount.setDictDealUse(dictDealUseCode);
		paybackBuleAmount.setTradeAmount(tradeAmount);
		paybackBuleAmount.setSurplusBuleAmount(blueAmount);
		paybackBuleAmount.setDictOffsetType(ChargeType.CHARGE_STORE.getCode());
		paybackBuleAmount.setContractCode(contractCode);
		paybackBuleAmount.setCreateBy(UserUtils.getUser().getId());
		paybackBuleAmount.setCreateTime(new Date());
		paybackBuleAmount.setModifyBy(UserUtils.getUser().getId());
		paybackBuleAmount.setModifyTime(new Date());
		paybackBuleAmount.setIsNewRecord(false);
		paybackBuleAmount.preInsert();
		paybackBlueAmountDao.insertPaybackBuleAmount(paybackBuleAmount);
		//给大金融推送冲抵记录
		if(month.getIsOverdue().equals("1"))//逾期
			paybackBuleAmount.setDictOffsetType("1");
		else 
			paybackBuleAmount.setDictOffsetType("0");
		earlyFinishService.sendDjrDeductData(paybackBuleAmount,month.getMonths());
	}

	/**
	 * 冲抵修改蓝补 2016年1月11日 By zhangfeng
	 * 
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateBlueMonths(Payback payback) {
		payback.preUpdate();
		paybackBlueAmountDao.updateBuleAmount(payback);
	}

	private void nullJudge(PaybackMonth paybackMonth) {
		if (!ObjectHelper.isEmpty(paybackMonth)) {
			// 应还分期服务费
			BigDecimal monthFeeService = paybackMonth.getMonthFeeService();
			monthFeeService = monthFeeService == null ? bgSum : monthFeeService;
			paybackMonth.setMonthFeeService(monthFeeService);
			// 实还分期服务费
			BigDecimal actualMonthFeeService = paybackMonth
					.getActualMonthFeeService();
			actualMonthFeeService = actualMonthFeeService == null ? bgSum
					: actualMonthFeeService;
			paybackMonth.setActualMonthFeeService(actualMonthFeeService);
			// 应还本金
			BigDecimal monthPayAmount = paybackMonth.getMonthPayAmount();
			monthPayAmount = monthPayAmount == null ? bgSum : monthPayAmount;
			paybackMonth.setMonthPayAmount(monthPayAmount);
			// 实还本金
			BigDecimal monthCapitalPayactual = paybackMonth
					.getMonthCapitalPayactual();
			monthCapitalPayactual = monthCapitalPayactual == null ? bgSum
					: monthCapitalPayactual;
			paybackMonth.setMonthCapitalPayactual(monthCapitalPayactual);
			// 应还利息
			BigDecimal monthInterestBackshould = paybackMonth
					.getMonthInterestBackshould();
			monthInterestBackshould = monthInterestBackshould == null ? bgSum
					: monthInterestBackshould;
			paybackMonth.setMonthInterestBackshould(monthInterestBackshould);
			// 实还利息
			BigDecimal monthInterestPayactual = paybackMonth
					.getMonthInterestPayactual();
			monthInterestPayactual = monthInterestPayactual == null ? bgSum
					: monthInterestPayactual;
			paybackMonth.setMonthInterestPayactual(monthInterestPayactual);
			// 应还违约金
			BigDecimal monthPenaltyShould = paybackMonth
					.getMonthPenaltyShould();
			monthPenaltyShould = monthPenaltyShould == null ? bgSum
					: monthPenaltyShould;
			paybackMonth.setMonthPenaltyShould(monthPenaltyShould);
			// 实还违约金
			BigDecimal monthPenaltyActual = paybackMonth
					.getMonthPenaltyActual();
			monthPenaltyActual = monthPenaltyActual == null ? bgSum
					: monthPenaltyActual;
			paybackMonth.setMonthPenaltyActual(monthPenaltyActual);
			// 减免违约金
			BigDecimal monthPenaltyReduction = paybackMonth
					.getMonthPenaltyReduction();
			monthPenaltyReduction = monthPenaltyReduction == null ? bgSum
					: monthPenaltyReduction;
			paybackMonth.setMonthPenaltyReduction(monthPenaltyReduction);
			// 应还罚息
			BigDecimal monthInterestPunishshould = paybackMonth
					.getMonthInterestPunishshould();
			monthInterestPunishshould = monthInterestPunishshould == null ? bgSum
					: monthInterestPunishshould;
			paybackMonth
					.setMonthInterestPunishshould(monthInterestPunishshould);
			// 实还罚息
			BigDecimal monthInterestPunishactual = paybackMonth
					.getMonthInterestPunishactual();
			monthInterestPunishactual = monthInterestPunishactual == null ? bgSum
					: monthInterestPunishactual;
			paybackMonth
					.setMonthInterestPunishactual(monthInterestPunishactual);
			// 减免罚息
			BigDecimal monthPunishReduction = paybackMonth
					.getMonthPunishReduction();
			monthPunishReduction = monthPunishReduction == null ? bgSum
					: monthPunishReduction;
			paybackMonth.setMonthPunishReduction(monthPunishReduction);
			// 应还滞纳金
			BigDecimal monthLateFee = paybackMonth.getMonthLateFee();
			monthLateFee = monthLateFee == null ? bgSum : monthLateFee;
			paybackMonth.setMonthLateFee(monthLateFee);
			// 实还滞纳金
			BigDecimal actualMonthLateFee = paybackMonth
					.getActualMonthLateFee();
			actualMonthLateFee = actualMonthLateFee == null ? bgSum
					: actualMonthLateFee;
			paybackMonth.setActualMonthLateFee(actualMonthLateFee);
			// 减免滞纳金
			BigDecimal monthLateFeeReduction = paybackMonth
					.getMonthLateFeeReduction();
			monthLateFeeReduction = monthLateFeeReduction == null ? bgSum
					: monthLateFeeReduction;
			paybackMonth.setMonthLateFeeReduction(monthLateFeeReduction);
		}
	}

	// /**
	// * 记录蓝补交易明细历史
	// * 2016年4月12日
	// * By zhaojinping
	// * @param paybackBuleAmont
	// */
	// @Transactional(readOnly = false, value = "loanTransactionManager")
	// public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont){
	// paybackBuleAmont.setIsNewRecord(false);
	// paybackBuleAmont.preInsert();
	// paybackBuleAmont.setOperator(UserUtils.getUser().getName());// 插入操作人
	// paybackBuleAmont.setTradeType(TradeType.CHARGE.getCode());// 交易类型
	// paybackBuleAmont.setDictOffsetType(ChargeType.CHARGE_STORE.getCode()); //
	// 冲抵类型
	// paybackBuleAmont.setModifyBy(UserUtils.getUser().getName());
	// earlyFinishConfirmDao.insertPaybackBuleAmont(paybackBuleAmont);
	// }
}
