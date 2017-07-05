package com.creditharmony.loan.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.common.entity.ex.ChargeData;

/**
 * 划扣放款使用服务
 * 
 * @Class Name DeductUpdateService
 * @author 王彬彬
 * @Create In 2016年2月2日
 */
@Service
public class DeductUpdateService {

	protected Logger logger = LoggerFactory.getLogger(DeductUpdateService.class);

	@Autowired
	private DeductUpdateDao dao;

	@Autowired
	private LoanGrantDao loanGrantDao;

	/**
	 * 冲抵用（基本信息） 2016年2月16日 By 王彬彬
	 * 
	 * @param contractCode
	 *            合同编号
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCharge(String contractCode) {
		Map<String, String> contractMap = new HashMap<String, String>();
		contractMap.put("contractCode", contractCode);

		List<Payback> paybackList = dao.getPayback(contractMap); // 获取还款主表
		Payback payback = paybackList.get(0);

		// 冲抵后剩余金额,未充抵前等于蓝补金额
		BigDecimal remainderAmount = payback.getPaybackBuleAmount();

		// 根据合同编号查询期供表
		Map<String, String> mapParam = new HashMap<String, String>();
		String dictMonthStatus = PeriodStatus.REPAYMENT.getCode();// 还款中状态

		mapParam.put("contractCode", contractCode);
		mapParam.put("dictMonthStatus", dictMonthStatus);

		PaybackMonth paybackMonth = dao.getNotPayBack(mapParam);
		// 当月应还期(正常还款的场合应该还款到第几期)
		Integer months = paybackMonth.getMonths();

		ChargeData charge = new ChargeData();

		Map<String, String> paybackInfoMap = new HashMap<String, String>();
		paybackInfoMap.put("contractCode", contractCode);
		PaybackMonth paybackInfo = dao.getPaybackMonth(paybackInfoMap);

		charge.setrPaybackId(payback.getId());// 还款主表ID
		charge.setMonthId(paybackInfo.getId()); // 期供表id
		charge.setContractCode(contractCode);// 合同号
		charge.setThisMonth(months);// 当月应还期(正常还款的场合应该还款到第几期)
		charge.setRemainderAmount(remainderAmount); // 冲抵后剩余金额（及冲抵前蓝补金额）
		charge.setOldRRemainderAmount(remainderAmount); // 冲抵开始前金额
		charge.setCurrentMonth(paybackInfo.getMonths());// 还款当前期数（实际已经还到第几期）
		charge.setMaxMonth(paybackInfo.getMaxMonths());// 还款最大一期

		// 开始冲抵（期供）
		updateMonthInfo(charge);

	}

	/**
	 * 更新还款表, 期供表的数据, 新增还款蓝补交易明细, 还款操作流水 2016年2月16日 By 王彬彬
	 * 
	 * @param chargedata
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateMonthInfo(ChargeData chargedata) {
		// 冲抵后剩余金额,未充抵前等于蓝补金额
		BigDecimal remainderAmount = chargedata.getRemainderAmount();

		Map<String, Object> contractMap = new HashMap<String, Object>();
		contractMap.put("contractCode", chargedata.getContractCode());
		contractMap.put("months", chargedata.getCurrentMonth());

		// 根据合同编号查询期供表（获取当月期供(本次处理要还款信息))
		PaybackMonth currentPaybackMonth = dao.getPayBackByMonth(contractMap);

		// 记录当前要处理的期供信息表id
		chargedata.setMonthId(currentPaybackMonth.getId());

		String isOverdue = currentPaybackMonth.getIsOverdue(); // 是否逾期

		BigDecimal interesTPayAmount = currentPaybackMonth
				.getMonthInterestBackshould();// 应还利息
		BigDecimal monthPayAmount = currentPaybackMonth.getMonthPayAmount();// 应还本金

		BigDecimal monthInterestPayactual = currentPaybackMonth
				.getMonthInterestPayactual() == null ? new BigDecimal(0)
				: currentPaybackMonth.getMonthInterestPayactual();// 实还利息(已还)
		BigDecimal monthCapitalPayactual = currentPaybackMonth
				.getMonthCapitalPayactual() == null ? new BigDecimal(0)
				: currentPaybackMonth.getMonthCapitalPayactual();// 实还本金(已还)

		// 交易金额
		BigDecimal tradeAmount = interesTPayAmount
				.subtract(monthInterestPayactual);
		if (remainderAmount.compareTo(tradeAmount) >= 0) {
			// 还利息
			monthInterestPayactual = interesTPayAmount;
			// 蓝补金额减去 应该还的金额
			remainderAmount = remainderAmount.subtract(tradeAmount);
			// 更新蓝补交易明细
			addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
					remainderAmount, TradeType.TURN_OUT,
					AgainstContent.MONTHINTEREST, ChargeType.CHARGE_SYSTEM);

			// 还本金
			tradeAmount = monthPayAmount
					.subtract(monthCapitalPayactual == null ? new BigDecimal(0)
							: monthCapitalPayactual);
			if (remainderAmount.compareTo(tradeAmount) >= 0) {
				monthCapitalPayactual = monthPayAmount;
				// 蓝补剩余金额
				remainderAmount = remainderAmount.subtract(tradeAmount);

				// 更新蓝补交易明细
				addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
						remainderAmount, TradeType.TURN_OUT,
						AgainstContent.MONTHPRINCIPAL, ChargeType.CHARGE_SYSTEM);

			} else if (remainderAmount.compareTo(tradeAmount) < 0) {
				monthCapitalPayactual = monthCapitalPayactual
						.add(remainderAmount);

				// 期供表
				updatePaybackMonth(chargedata.getContractCode(),
						chargedata.getCurrentMonth(), monthCapitalPayactual,
						monthInterestPayactual, null);

				// 更新蓝补交易明细
				addPaybackBuleAmont(currentPaybackMonth.getId(),
						remainderAmount, BigDecimal.ZERO,
						TradeType.TURN_OUT,
						AgainstContent.MONTHPRINCIPAL, ChargeType.CHARGE_SYSTEM);
				remainderAmount = BigDecimal.ZERO;
				// 结束更新主表
				updatePayBack(remainderAmount, chargedata.getContractCode(),
						null, chargedata.getCurrentMonth());

				// 还款历史信息表更新
				addPaybackHis(chargedata.getMonthId(), chargedata
						.getOldRRemainderAmount().subtract(remainderAmount),
						chargedata.getContractCode());

				return;
			}

			chargedata.setRemainderAmount(remainderAmount);
			chargedata.setCurrentMonth(chargedata.getCurrentMonth() + 1);

			if (YESNO.YES.getCode().equals(isOverdue)) {
				// 期供表(更新期供状态追回)
				updatePaybackMonth(chargedata.getContractCode(),
						chargedata.getCurrentMonth() - 1, monthPayAmount,
						monthInterestPayactual, PeriodStatus.REPLEVY);

				// 冲违约金 和罚息
				chargedata = updateShouldActual(chargedata);
				remainderAmount = chargedata.getRemainderAmount();
				// 更新主表（如果继续冲抵期为当月期则更新主表状态为还款中）
				if (chargedata.getCurrentMonth() >= chargedata.getThisMonth()) {
					updatePayBack(remainderAmount,
							chargedata.getContractCode(),
							RepayStatus.PEND_REPAYMENT,
							chargedata.getCurrentMonth());

					// 更新借款信息表的借款状态
					updateLoanInfo(chargedata.getContractCode(),
							RepayStatus.PEND_REPAYMENT.getCode());
				} else {
					updatePayBack(remainderAmount,
							chargedata.getContractCode(), null,
							chargedata.getCurrentMonth());
				}

				// 还款历史信息表更新
				addPaybackHis(chargedata.getMonthId(), chargedata
						.getOldRRemainderAmount().subtract(remainderAmount),
						chargedata.getContractCode());

				chargedata.setOldRRemainderAmount(remainderAmount);
				// 继续更新下一期
				updateMonthInfo(chargedata);
			} else {
				// 更新期供
				updatePaybackMonth(chargedata.getContractCode(),
						chargedata.getCurrentMonth() - 1, monthPayAmount,
						monthInterestPayactual, PeriodStatus.PAID);

				// 结束更新主表
				if (chargedata.getCurrentMonth() - 1 >= chargedata
						.getMaxMonth()) {
					updatePayBack(remainderAmount,
							chargedata.getContractCode(),
							RepayStatus.SETTLE_CONFIRM,
							chargedata.getMaxMonth());
				} else {
					updatePayBack(remainderAmount,
							chargedata.getContractCode(), null,
							chargedata.getCurrentMonth());
				}

				// 还款历史信息表更新
				addPaybackHis(chargedata.getMonthId(), chargedata
						.getOldRRemainderAmount().subtract(remainderAmount),
						chargedata.getContractCode());

				// 正常冲抵完成，结束处理
				return;
			}
		} else if (remainderAmount.compareTo(tradeAmount) < 0) {// 金额不足直接冲利息
			monthInterestPayactual = monthInterestPayactual
					.add(remainderAmount);
			remainderAmount = BigDecimal.ZERO;

			// 更新蓝补交易明细
			addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
					remainderAmount, TradeType.TURN_OUT,
					AgainstContent.MONTHINTEREST, ChargeType.CHARGE_SYSTEM);

			// 更新期供
			updatePaybackMonth(chargedata.getContractCode(),
					chargedata.getCurrentMonth(), monthPayAmount,
					monthInterestPayactual, null);

			// 结束更新主表
			updatePayBack(remainderAmount, chargedata.getContractCode(), null,
					chargedata.getCurrentMonth());

			// 还款历史信息表更新
			addPaybackHis(chargedata.getMonthId(), chargedata
					.getOldRRemainderAmount().subtract(remainderAmount),
					chargedata.getContractCode());

			return;
		}
	}

	/**
	 * 冲违约金 和罚息 2016年2月15日 By 翁私
	 * 
	 * @param chargedata
	 *            冲抵信息
	 * @return none
	 */

	@Transactional(value = "loanTransactionManager", readOnly = false)
	public ChargeData updateShouldActual(ChargeData chargedata) {
		// 冲抵后剩余金额,未充抵前等于蓝补金额
		BigDecimal remainderAmount = chargedata.getRemainderAmount();
		Map<String, Object> contractMap = new HashMap<String, Object>();
		contractMap.put("contractCode", chargedata.getContractCode());
		contractMap.put("months", chargedata.getCurrentMonth() - 1);

		// 根据合同编号查询期供表（获取当月期供(本次处理要还款信息))
		PaybackMonth currentPaybackMonth = dao.getPayBackByMonth(contractMap);

		BigDecimal monthInterestPunishshould = currentPaybackMonth
				.getMonthInterestPunishshould();// 应还罚息
		BigDecimal monthPenaltyShould = currentPaybackMonth
				.getMonthPenaltyShould();// 应还违约金

		BigDecimal monthInterestPunishactual = currentPaybackMonth
				.getMonthInterestPunishactual() == null ? new BigDecimal(0)
				: currentPaybackMonth.getMonthInterestPunishactual();// 实还罚息(已还)
		BigDecimal monthPenaltyActual = currentPaybackMonth
				.getMonthPenaltyActual() == null ? new BigDecimal(0)
				: currentPaybackMonth.getMonthPenaltyActual();// 实还违约金(已还)

		// 交易金额
		BigDecimal tradeAmount = monthPenaltyShould
				.subtract(monthPenaltyActual);
		if (remainderAmount.compareTo(tradeAmount) >= 0) {
			// 还违约金
			monthPenaltyActual = monthPenaltyShould;
			// 蓝补金额减去 应该还的金额
			remainderAmount = remainderAmount.subtract(tradeAmount);
			// 更新蓝补交易明细
			addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
					remainderAmount, TradeType.TURN_OUT,
					AgainstContent.VIOLATE, ChargeType.CHARGE_SYSTEM);

			// 还罚息
			tradeAmount = monthInterestPunishshould
					.subtract(monthInterestPunishactual);
			if (remainderAmount.compareTo(tradeAmount) >= 0) {
				monthInterestPunishactual = monthInterestPunishshould;
				// 蓝补剩余金额
				remainderAmount = remainderAmount.subtract(tradeAmount);

				// 更新蓝补交易明细
				addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
						remainderAmount, TradeType.TURN_OUT,
						AgainstContent.PUNISH, ChargeType.CHARGE_SYSTEM);

				updatePaybackMonth(chargedata.getContractCode(),
						chargedata.getCurrentMonth() - 1,
						monthInterestPunishactual, monthPenaltyActual);
				// 还款历史信息表更新
				/*
				 * addPaybackHis(chargedata.getMonthId(),
				 * oldAmount.subtract(remainderAmount),
				 * chargedata.getContractCode());
				 */
				chargedata.setRemainderAmount(remainderAmount);
				return chargedata;

			} else if (remainderAmount.compareTo(tradeAmount) < 0) {
				monthInterestPunishactual = monthInterestPunishactual
						.add(remainderAmount);
				// 更新蓝补交易明细
				addPaybackBuleAmont(currentPaybackMonth.getId(),
						remainderAmount, BigDecimal.ZERO,
						TradeType.TURN_OUT, AgainstContent.PUNISH,
						ChargeType.CHARGE_SYSTEM);
				remainderAmount = BigDecimal.ZERO;
				// 结束更新主表
				updatePayBack(remainderAmount, chargedata.getContractCode(),
						null, chargedata.getCurrentMonth());

				// 还款历史信息表更新
				addPaybackHis(chargedata.getMonthId(), chargedata
						.getOldRRemainderAmount().subtract(remainderAmount),
						chargedata.getContractCode());
				updatePaybackMonth(chargedata.getContractCode(),
						chargedata.getCurrentMonth() - 1,
						monthInterestPunishactual, monthPenaltyActual);
				// 还款历史信息表更新
				/*
				 * addPaybackHis(chargedata.getMonthId(),
				 * oldAmount.subtract(remainderAmount),
				 * chargedata.getContractCode());
				 */
				chargedata.setRemainderAmount(remainderAmount);
				return chargedata;
			}

		} else {// 金额不足直接冲违约金
			monthPenaltyActual = monthPenaltyActual.add(remainderAmount);
			remainderAmount = BigDecimal.ZERO;
			// 更新蓝补交易明细
			addPaybackBuleAmont(currentPaybackMonth.getId(), tradeAmount,
					remainderAmount, TradeType.TURN_OUT,
					AgainstContent.VIOLATE, ChargeType.CHARGE_SYSTEM);
			// 还款历史信息表更新
			addPaybackHis(chargedata.getMonthId(), chargedata
					.getOldRRemainderAmount().subtract(remainderAmount),
					chargedata.getContractCode());

			updatePaybackMonth(chargedata.getContractCode(),
					chargedata.getCurrentMonth() - 1,
					monthInterestPunishactual, monthPenaltyActual);
			chargedata.setRemainderAmount(remainderAmount);
			// 还款历史信息表更新
			/*
			 * addPaybackHis(chargedata.getMonthId(),
			 * oldAmount.subtract(remainderAmount),
			 * chargedata.getContractCode());
			 */
			return chargedata;
		}

		return chargedata;
	}

	/**
	 * 修改期供表的 实还本金，实还利息 2016年2月15日 By 王彬彬
	 * 
	 * @param contractCode
	 *            合同号
	 * @param contractCode
	 *            期供
	 * @param monthCapitalPayactual
	 *            实还本金
	 * @param monthInterestPayactual
	 *            实还利息
	 * @param periodStatus
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePaybackMonth(String contractCode, int months,
			BigDecimal monthCapitalPayactual,
			BigDecimal monthInterestPayactual, PeriodStatus periodStatus) {
		PaybackMonth paybackMonth = new PaybackMonth();
		paybackMonth.setContractCode(contractCode);
		paybackMonth.setMonths(months);
		paybackMonth.setMonthCapitalPayactual(monthCapitalPayactual);
		paybackMonth.setMonthInterestPayactual(monthInterestPayactual);
		paybackMonth.preUpdate();

		if (periodStatus != null) {
			paybackMonth.setDictMonthStatus(periodStatus.getCode());
		}
		dao.updatePaybackMonth(paybackMonth);

	}

	/**
	 * 修改还款主表的蓝补金额 2016年2月6日 By 王彬彬
	 * 
	 * @param payBackBuleAmount
	 *            蓝补金额
	 * @param contractCode
	 *            合同号
	 * @param dictPayStatus
	 *            还款状态
	 * @param paybackCurrentMonth
	 *            还款当前期
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePayBack(BigDecimal payBackBuleAmount,
			String contractCode, RepayStatus dictPayStatus,
			int paybackCurrentMonth) {
		Payback payback = new Payback();
		payback.setPaybackBuleAmount(payBackBuleAmount);
		payback.setContractCode(contractCode);
		if (dictPayStatus != null) {
			payback.setDictPayStatus(dictPayStatus.getCode());
		}
		payback.setPaybackCurrentMonth(paybackCurrentMonth);
		dao.updatePayBack(payback);
	}
	
	
	/**
	 * 保存还款蓝补交易明细 2016年2月15日 By 王彬彬
	 * 
	 * @param rMonthId
	 *            关联ID（期供ID）
	 * @param tradeAmount
	 *            交易金额
	 * @param surplusBuleAmount
	 *            蓝补余额
	 * @param tradeType
	 *            交易类型
	 * @param againstContent
	 *            冲抵内容
	 * @param chargeType
	 *            冲抵类型
	 */

	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackBuleAmont(String rMonthId, BigDecimal tradeAmount,
			BigDecimal surplusBuleAmount, TradeType tradeType,
			AgainstContent againstContent, ChargeType chargeType, String contractCode) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();
		paybackBuleAmont.setContractCode(contractCode);
		paybackBuleAmont.setrMonthId(rMonthId);// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);// 蓝补余额

		paybackBuleAmont.setOperator(UserUtils.getUser().getId());// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间

		dao.addBackBuleAmont(paybackBuleAmont);
	}

	/**
	 * 保存还款蓝补交易明细 2016年2月15日 By 王彬彬
	 * 
	 * @param rMonthId
	 *            关联ID（期供ID）
	 * @param tradeAmount
	 *            交易金额
	 * @param surplusBuleAmount
	 *            蓝补余额
	 * @param tradeType
	 *            交易类型
	 * @param againstContent
	 *            冲抵内容
	 * @param chargeType
	 *            冲抵类型
	 */

	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackBuleAmont(String rMonthId, BigDecimal tradeAmount,
			BigDecimal surplusBuleAmount, TradeType tradeType,
			AgainstContent againstContent, ChargeType chargeType) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();

		paybackBuleAmont.setrMonthId(rMonthId);// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);// 蓝补余额

		paybackBuleAmont.setOperator(UserUtils.getUser().getId());// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间

		dao.addBackBuleAmont(paybackBuleAmont);
	}

	/**
	 * 保存还款历史明细 2016年2月15日 By 王彬彬
	 * 
	 * @param paybackMonthId
	 *            期供ID
	 * @param money
	 *            金额
	 * @param contractCode
	 *            合同编号
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackHis(String paybackMonthId, BigDecimal money,
			String contractCode) {
		PaybackHis paybackHis = new PaybackHis();
		paybackHis.preInsert();

		paybackHis.setContractCode(contractCode);
		paybackHis.setrMonthId(paybackMonthId);
		paybackHis.setPaymentAmount(money);
		paybackHis.setPaymentDay(new Date());

		dao.addPaybackHis(paybackHis);
	}

	/**
	 * 更借款信息表的借款状态 2016年2月15日 By 王彬彬
	 * 
	 * @param contractCode
	 *            合同编号
	 * @param dictLoanStatus
	 *            借款状态
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateLoanInfo(String contractCode, String dictLoanStatus) {
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.preUpdate();

		loanInfo.setLoanCode(contractCode);
		loanInfo.setDictLoanStatus(dictLoanStatus);
		dao.updateLoanInfo(loanInfo);
	}

	/**
	 * 修改期供表的 实还违约金，实还罚息 2016年2月15日 By 王彬彬
	 * 
	 * @param contractCode
	 *            合同号
	 * @param contractCode
	 *            期供
	 * @param monthPenaltyShould
	 *            实还违约金
	 * @param monthInterestPunishshould
	 *            实还罚息
	 * @param periodStatus
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePaybackMonth(String contractCode, int months,
			BigDecimal monthInterestPunishactual, BigDecimal monthPenaltyActual) {
		PaybackMonth paybackMonth = new PaybackMonth();
		paybackMonth.setContractCode(contractCode);
		paybackMonth.setMonths(months);
		paybackMonth.setMonthInterestPunishactual(monthInterestPunishactual);
		paybackMonth.setMonthPenaltyActual(monthPenaltyActual);
		paybackMonth.preUpdate();
		dao.updatePaybackMonth(paybackMonth);

	}
	
	/**
	 * 划扣回盘结果更新用（无业务处理,单纯转存划扣结果） 
	 * 2016年2月3日 By 王彬彬
	 * @param iteratorSplit 单条回盘结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDeductInfo(LoanDeductEntity iteratorSplit) {
		PaybackApply paybackApply = new PaybackApply();
		BigDecimal succeedMoney = null;
		BigDecimal applyAmount = null;
		if(iteratorSplit.getDeductSucceedMoney() == null || "".equals(iteratorSplit.getDeductSucceedMoney())){
			succeedMoney = new BigDecimal(0);
		}else{
			succeedMoney = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
		}
		if(iteratorSplit.getApplyAmount() == null || "".equals(iteratorSplit.getApplyAmount())){
			applyAmount = new BigDecimal(0);
		}else{
			applyAmount = new BigDecimal(iteratorSplit.getApplyAmount());
		}
		if ((succeedMoney.compareTo(applyAmount)) >= 0) {
			paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
					.getCode());
		} else {
			// 集中划扣不更新到已办列表，当天处理完成后流转到已办（系统批处理）
			if (iteratorSplit.getDeductSysIdType().equals(DeductWays.HJ_01.getCode())) {
				paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE.getCode());
			} else if (iteratorSplit.getDeductSysIdType().equals(
					DeductWays.HJ_02.getCode())) {
				// 非集中划扣处理完成后直接流转到集中划扣已办。
				paybackApply
						.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
								.getCode());
			} else {
				paybackApply
						.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
								.getCode());
			}
		}

		paybackApply.setApplyReallyAmount(new BigDecimal(iteratorSplit
				.getDeductSucceedMoney()));
		paybackApply.setSplitFlag(YESNO.YES.getCode());
		paybackApply.setId(iteratorSplit.getBatId());
		paybackApply.preUpdate();
		if (iteratorSplit.getDeductSysIdType().equals(
				DeductWays.HJ_01.getCode())) {
			updateDeductsPaybackApply(paybackApply);
			updatePaybackListHis(paybackApply);
			// 如果回盘结果成功 然后 插入 还款_集中划扣还款申请归档表
			if (CounterofferResult.PAYMENT_SUCCEED.getCode().equals(
					paybackApply.getSplitBackResult())) {
				insertDeductsPaybackApplyHis(paybackApply);
				deleteDeductsPaybackApply(paybackApply);
			}
		} else {
			paybackApply.setFailReason(iteratorSplit.getFailReason());
			updatePaybackApply(paybackApply);
		}

		// 划扣记录
		// addPaybackDeducts(paybackApply);

		// 还款主表
	  if(succeedMoney.compareTo(BigDecimal.ZERO) == 1){
			Payback payback = new Payback();
			payback.preUpdate();
			payback.setId(iteratorSplit.getBusinessId());
			payback.setPaybackBuleAmount(new BigDecimal(iteratorSplit
					.getDeductSucceedMoney()));
			// 更新蓝补金额
			dao.updateBuleAmount(payback);
			Map<String, String> contractMap = new HashMap<String, String>();
			contractMap.put("id", iteratorSplit.getBusinessId());
	
			List<Payback> paybackList = dao.getPayback(contractMap); // 获取还款主表
			payback = paybackList.get(0);
			// 蓝补历史更新
			addPaybackBuleAmont(iteratorSplit.getRefId(), new BigDecimal(
					iteratorSplit.getDeductSucceedMoney()),
					payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
					AgainstContent.GOLD_AMOUNT, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
			}
	}
	
	/**
	 * 更新 还款_待还款归档列表 
	 * 2016年2月16日 By 翁私
	 * @param paybackApply 还款申请信息
	 */
	private void updatePaybackListHis(PaybackApply paybackApply) {
		dao.updatePaybackListHis(paybackApply);
	}

	/**
	 * 更新集中划扣还款申请表2016年2月16日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateDeductsPaybackApply(PaybackApply paybackApply) {
		dao.updateDeductsPaybackApply(paybackApply);
	}

	/**
	 * 插入 还款_集中划扣还款申请归档表 2016年4月7日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	private void insertDeductsPaybackApplyHis(PaybackApply paybackApply) {
		dao.insertDeductsPaybackApplyHis(paybackApply);
	}
	
	/**
	 * 更新还款申请表 2016年2月16日 By 王彬彬
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(PaybackApply paybackApply) {
		User user = UserUtils.getUser();
		if(user != null){
			if (StringUtils.isNotBlank(user.getId())){
				paybackApply.setModifyBy(user.getId());
			}else{
				paybackApply.setModifyBy("FinanceBatch");
			}
		}else{
			paybackApply.setModifyBy("FinanceBatch");
		}
		paybackApply.setModifyTime(new Date());
		dao.updatePaybackApply(paybackApply);
	}
	
	/**
	 * 集中划扣更新 （线下） 2016年2月17日 By 翁私
	 * 
	 * @param deductList
	 *            划扣返回结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplitOffline(List<LoanDeductEntity> deductList) {
		// 划扣回盘
		Iterator<LoanDeductEntity> iterator = deductList.iterator();

		// 集中/非集中划扣回盘（更新主表和申请表，此处不冲抵）
		while (iterator.hasNext()) {
			LoanDeductEntity iteratorSplit = iterator.next();
			// 结果回盘更新，记录更新数据
			updateDeductInfo(iteratorSplit);
		}
	}
	
	/** 
	 * 删除集中划扣数据 2016年4月24日 By 翁私
	 * @param paybackApply 
	 * @return none
	 */
	private void deleteDeductsPaybackApply(PaybackApply paybackApply) {
		dao.deleteDeductsPaybackApply(paybackApply);
	}
}
