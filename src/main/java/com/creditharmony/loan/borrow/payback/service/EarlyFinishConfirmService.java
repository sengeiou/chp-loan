package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendDebtURPInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendSupplyInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendDebtURPOutBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendSupplyOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.sync.data.util.SyncDataTypeUtil;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.payback.dao.EarlyFinishConfirmDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.sync.data.fortune.ForuneSyncCreditorService;

/**
 * 风控提前结清确认列表业务Service
 * @Class Name EarlyFinishConfirmService
 * @author zhaojinping
 * @Create In 2015年12月24日
 */
@Service("EarlyFinishConfirmService")
public class EarlyFinishConfirmService extends  CoreManager<EarlyFinishConfirmDao, PaybackCharge> {
	
	public final static String MONTH_ZERO = "0.00";
	public final static String zeroFlag = "-1";
	public final static String BIG_FINANCE_STATUS = "3";
	@Autowired
	public CenterDeductService  centerDeductService;
	
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private ForuneSyncCreditorService foruneSyncCreditorService;
	@Autowired
	private ContractDao contractDao;
	/**
	 * 更改还款主表中的还款状态
	 * 2016年1月6日
	 * By zhaojinping
	 * @param page
	 * @param paybackCharge
	 * @return page
	 */
	public Page<PaybackCharge> findPayback(Page<PaybackCharge> page, PaybackCharge paybackCharge) {
		/*BigDecimal bgSum = new BigDecimal("0.00");*/
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("contractCode");
		PageList<PaybackCharge> pageList = (PageList<PaybackCharge>) dao.findPayback(pageBounds,paybackCharge);
		/*if (ArrayHelper.isNotEmpty(pageList)) {
			for (int i = 0; i < pageList.size(); i++) {
				    paybackCharge = pageList.get(i);
					PaybackMonth paybackMonth = paybackCharge.getPaybackMonth();
					if (!ObjectHelper.isEmpty(paybackMonth)) {
						// 减免金额 提前结清应还款总额- 申请蓝补金额
						// 提前结清应还款总额
						BigDecimal settleTotalAmount = paybackCharge.getSettleTotalAmount();
						settleTotalAmount = settleTotalAmount == null ? bgSum : settleTotalAmount;
						paybackCharge.setSettleTotalAmount(settleTotalAmount);
						// 申请蓝补金额
						BigDecimal applyBuleAmount = paybackCharge.getApplyBuleAmount();
						applyBuleAmount = applyBuleAmount == null ? bgSum : applyBuleAmount;
						paybackCharge.setApplyBuleAmount(applyBuleAmount);
						// 减免金额
						if (settleTotalAmount.compareTo(applyBuleAmount) > Integer.parseInt(YESNO.NO.getCode())) {
							paybackMonth.setCreditAmount(settleTotalAmount.subtract(applyBuleAmount));
						} else {
							paybackMonth.setCreditAmount(bgSum);
						}
						// 设置返款金额
						UrgeServicesMoney urgeServicesMoney = paybackCharge.getUrgeServicesMoney();
						if(ObjectHelper.isEmpty(urgeServicesMoney)){
							urgeServicesMoney = new UrgeServicesMoney();
							urgeServicesMoney.setUrgeMoeny(bgSum);
							urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
						}else{ 
							BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
							BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
							if(ObjectHelper.isEmpty(urgeMoney)){
								urgeServicesMoney.setUrgeMoeny(bgSum);
							}
							if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
								urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
							}
						}
						paybackCharge.setUrgeServicesMoney(urgeServicesMoney);
					}
			}
		}*/
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 提前结清确认列表页面的详情页面
	 * 2016年1月6日
	 * By zhaojinping
	 * @param payback
	 * @return paybackCharge
	 */
	public PaybackCharge findPayback(PaybackCharge paybackCharge){
		//BigDecimal bgSum = new BigDecimal("0.00");
		paybackCharge =  dao.findPaybackBy(paybackCharge);
		 /*PaybackMonth paybackMonth = paybackCharge.getPaybackMonth();
		 if (!ObjectHelper.isEmpty(paybackMonth)) {
			 // 一次性提前结清应还款额
			 BigDecimal monthBeforeFinishAmount = paybackMonth.getMonthBeforeFinishAmount();
			 monthBeforeFinishAmount = monthBeforeFinishAmount == null ? bgSum : monthBeforeFinishAmount;
			 paybackMonth.setMonthBeforeFinishAmount(monthBeforeFinishAmount);
				// 已还期供金额 实还分期服务费sum+实还本金sum + 实还利息sum
				BigDecimal actualMonthFeeServiceSum = paybackMonth.getActualMonthFeeServiceSum();
				BigDecimal monthCapitalPayactualSum = paybackMonth.getMonthCapitalPayactualSum();
				BigDecimal monthInterestPayactualSum = paybackMonth.getMonthInterestPayactualSum();
				actualMonthFeeServiceSum = actualMonthFeeServiceSum == null ? bgSum : actualMonthFeeServiceSum;
				monthCapitalPayactualSum = monthCapitalPayactualSum == null ? bgSum : monthCapitalPayactualSum;
				monthInterestPayactualSum = monthInterestPayactualSum == null ? bgSum : monthInterestPayactualSum;
				// 未还违约金(滞纳金)及罚息金额 应还滞纳金sum-实还滞纳金sum + 应还罚息sum-实还罚息sum - 减免滞纳金 -减免罚息
				BigDecimal monthLateFeeSum = paybackMonth.getMonthLateFeeSum();
				BigDecimal actualMonthLateFeeSum = paybackMonth.getActualMonthLateFeeSum();
				BigDecimal monthInterestPunishshouldSum = paybackMonth.getMonthInterestPunishshouldSum();
				BigDecimal monthInterestPunishactualSum = paybackMonth.getMonthInterestPunishactualSum();
				  // 减免滞纳金总额
				BigDecimal monthLateFeeReductionSum = paybackMonth.getMonthLateFeeReductionSum();
				// 减免罚息总额
				BigDecimal monthPunishReductionSum = paybackMonth.getMonthPunishReductionSum();
				BigDecimal monthPenaltyShouldSum = paybackMonth.getMonthPenaltyShouldSum();
				BigDecimal monthPenaltyActualSum = paybackMonth.getMonthPenaltyActualSum();
				// 减免违约金总额
				BigDecimal monthPenaltyReductionSum = paybackMonth.getMonthPenaltyReductionSum();
				monthLateFeeSum = monthLateFeeSum == null ? bgSum : monthLateFeeSum;
				actualMonthLateFeeSum = actualMonthLateFeeSum == null ? bgSum : actualMonthLateFeeSum;
				monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum : monthInterestPunishshouldSum;
				monthInterestPunishactualSum = monthInterestPunishactualSum == null ? bgSum : monthInterestPunishactualSum;
				monthLateFeeReductionSum = monthLateFeeReductionSum == null ? bgSum : monthLateFeeReductionSum;
				monthPunishReductionSum = monthPunishReductionSum == null ? bgSum : monthPunishReductionSum;
				monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum : monthPenaltyShouldSum;
				monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum : monthPenaltyActualSum;
				monthPenaltyReductionSum = monthPenaltyReductionSum == null ? bgSum : monthPenaltyReductionSum;
				Contract contract = paybackCharge.getContract();
					if (!ObjectHelper.isEmpty(contract)) {
						String contrctVersion = contract.getContractVersion();
						//if (StringUtils.isNotEmpty(contrctVersion)) {
							if (ContractVer.VER_ONE_FIVE.getCode().equals(contrctVersion)||ContractVer.VER_ONE_FOUR.getCode().equals(contrctVersion)) {
								// 1.4版本合同金额的计算
								paybackMonth.setMonthsAomuntPaybacked(actualMonthFeeServiceSum.add(monthCapitalPayactualSum).add(monthInterestPayactualSum));
								paybackMonth.setPenaltyInterest(monthLateFeeSum.subtract(actualMonthLateFeeSum).subtract(monthLateFeeReductionSum)
											.add(monthInterestPunishshouldSum.subtract(monthInterestPunishactualSum).subtract(monthPunishReductionSum)));
								// 已还违约金及罚息金额 实还滞纳金sum + 实还罚息sum
								paybackMonth.setOverDueAmontPaybackedSum(actualMonthLateFeeSum.add(monthInterestPunishactualSum));
							    // 累计减免金额总额
								paybackMonth.setCreditAmount(monthPunishReductionSum.add(monthLateFeeReductionSum));
							} else {
								// 1.3及1.3以前的合同版本金额计算
								paybackMonth.setMonthsAomuntPaybacked(monthCapitalPayactualSum.add(monthInterestPayactualSum));
								paybackMonth.setPenaltyInterest(monthPenaltyShouldSum.subtract(monthPenaltyActualSum).subtract(monthPenaltyReductionSum)
												.add(monthInterestPunishshouldSum.subtract(monthInterestPunishactualSum).subtract(monthPunishReductionSum)));
								// 已还违约金及罚息金额 实还滞纳金sum + 实还罚息sum
								paybackMonth.setOverDueAmontPaybackedSum(monthPenaltyActualSum.add(monthInterestPunishactualSum));
							    // 累计减免金额
								paybackMonth.setCreditAmount(monthPunishReductionSum.add(monthPenaltyReductionSum));
							}
//						} else {
//							logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount,合同版本号为空！");
//						}
					} else {
						logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount,合同为空");
					}
					// 设置减免的提前结清金额
					BigDecimal settleTotalAmount = paybackCharge.getSettleTotalAmount();
					BigDecimal applyBuleAmount = paybackCharge.getApplyBuleAmount();
					if(settleTotalAmount.compareTo(applyBuleAmount) > Integer.parseInt(YESNO.NO.getCode())){
							paybackMonth.setMonthBeforeReductionAmount(settleTotalAmount.subtract(applyBuleAmount));
					}else{
							paybackMonth.setMonthBeforeReductionAmount(new BigDecimal("0.00"));
					}
					// 设置返款金额
					UrgeServicesMoney urgeServicesMoney = paybackCharge.getUrgeServicesMoney();
					if(ObjectHelper.isEmpty(urgeServicesMoney)){
						urgeServicesMoney = new UrgeServicesMoney();
						urgeServicesMoney.setUrgeMoeny(bgSum);
						urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
					}else{
						BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
						BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
						if(ObjectHelper.isEmpty(urgeMoney)){
							urgeServicesMoney.setUrgeMoeny(bgSum);
						}
						if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
							urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
						}
					}
					paybackCharge.setUrgeServicesMoney(urgeServicesMoney);
				} else {
					logger.debug("invoke EarlyFinishConfirmService method: findPayback, paybackMonth is: "+ null);
				}*/
		return paybackCharge;
	}
	
	/**
	 * 获取提前结清的期供信息
	 * 2015年12月26日
	 * By zhaojinping
	 * @param payback
	 * @return paybackMonth
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackMonth findPaybackMonth(Payback payback){
		BigDecimal earlyFinishAmount = new BigDecimal(MONTH_ZERO);
		BigDecimal bgSum = new BigDecimal(MONTH_ZERO);
		// 减免人
		payback.setRemissionBy(UserUtils.getUser().getName());
		if (!ObjectHelper.isEmpty(payback)) {
			PaybackMonth paybackMonth = dao.findPaybackMonth(payback);
		if (!ObjectHelper.isEmpty(paybackMonth)) {
			// 已还期供金额 实还分期服务费sum+实还本金sum + 实还利息sum
			BigDecimal actualMonthFeeServiceSum = paybackMonth.getActualMonthFeeServiceSum();
			BigDecimal monthCapitalPayactualSum = paybackMonth.getMonthCapitalPayactualSum();
			BigDecimal monthInterestPayactualSum = paybackMonth.getMonthInterestPayactualSum();
			actualMonthFeeServiceSum = actualMonthFeeServiceSum == null ? bgSum : actualMonthFeeServiceSum;
			monthCapitalPayactualSum = monthCapitalPayactualSum == null ? bgSum : monthCapitalPayactualSum;
			monthInterestPayactualSum = monthInterestPayactualSum == null ? bgSum : monthInterestPayactualSum;
			// 未还违约金(滞纳金)及罚息金额 应还滞纳金sum-实还滞纳金sum + 应还罚息sum-实还罚息sum - 减免滞纳金 -减免罚息
			BigDecimal monthLateFeeSum = paybackMonth.getMonthLateFeeSum();
			BigDecimal actualMonthLateFeeSum = paybackMonth.getActualMonthLateFeeSum();
			BigDecimal monthInterestPunishshouldSum = paybackMonth.getMonthInterestPunishshouldSum();
			BigDecimal monthInterestPunishactualSum = paybackMonth.getMonthInterestPunishactualSum();
			BigDecimal monthLateFeeReductionSum = paybackMonth.getMonthLateFeeReductionSum();
			BigDecimal monthPunishReductionSum = paybackMonth.getMonthPunishReductionSum();
			BigDecimal monthPenaltyShouldSum = paybackMonth.getMonthPenaltyShouldSum();
			BigDecimal monthPenaltyActualSum = paybackMonth.getMonthPenaltyActualSum();
			BigDecimal monthPenaltyReductionSum = paybackMonth.getMonthPenaltyReductionSum();
			monthLateFeeSum = monthLateFeeSum == null ? bgSum : monthLateFeeSum;
			actualMonthLateFeeSum = actualMonthLateFeeSum == null ? bgSum : actualMonthLateFeeSum;
			monthInterestPunishshouldSum = monthInterestPunishshouldSum == null ? bgSum : monthInterestPunishshouldSum;
			monthInterestPunishactualSum = monthInterestPunishactualSum == null ? bgSum : monthInterestPunishactualSum;
			monthLateFeeReductionSum = monthLateFeeReductionSum == null ? bgSum : monthLateFeeReductionSum;
			monthPunishReductionSum = monthPunishReductionSum == null ? bgSum : monthPunishReductionSum;
			monthPenaltyShouldSum = monthPenaltyShouldSum == null ? bgSum : monthPenaltyShouldSum;
			monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum : monthPenaltyActualSum;
			monthPenaltyReductionSum = monthPenaltyReductionSum == null ? bgSum : monthPenaltyReductionSum;
				Contract contract = payback.getContract();
				if (!ObjectHelper.isEmpty(contract)) {
					String contrctVersion = contract.getContractVersion();
					if (StringUtils.isNotEmpty(contrctVersion)) {
						if (StringUtils.isNotEmpty(contrctVersion) && Integer.valueOf(contrctVersion) >=4) {
							// 1.4版本合同金额的计算
							paybackMonth.setMonthsAomuntPaybacked(actualMonthFeeServiceSum.add(monthCapitalPayactualSum).add(monthInterestPayactualSum));
							paybackMonth.setPenaltyInterest(monthLateFeeSum.subtract(actualMonthLateFeeSum).subtract(monthLateFeeReductionSum)
										.add(monthInterestPunishshouldSum.subtract(monthInterestPunishactualSum).subtract(monthPunishReductionSum)));
							// 已还违约金及罚息金额 实还滞纳金sum + 实还罚息sum
							paybackMonth.setOverDueAmontPaybackedSum(actualMonthLateFeeSum.add(monthInterestPunishactualSum));
						} else {
							// 1.3及1.3以前的合同版本金额计算
							paybackMonth.setMonthsAomuntPaybacked(monthCapitalPayactualSum.add(monthInterestPayactualSum));
							paybackMonth.setPenaltyInterest(monthPenaltyShouldSum.subtract(monthPenaltyActualSum).subtract(monthPenaltyReductionSum)
											.add(monthInterestPunishshouldSum.subtract(monthInterestPunishactualSum).subtract(monthPunishReductionSum)));
							// 已还违约金及罚息金额 实还滞纳金sum + 实还罚息sum
							paybackMonth.setOverDueAmontPaybackedSum(monthPenaltyActualSum.add(monthInterestPunishactualSum));
						}
					} else {
						logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount,合同版本号为空！");
					}
				} else {
					logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount,合同为空");
				}
				// 获取减免的提前结清金额 提前结清金额-蓝补金额
				if (!ObjectHelper.isEmpty(paybackMonth
						.getMonthBeforeFinishAmount())) {
					earlyFinishAmount = paybackMonth.getMonthBeforeFinishAmount().subtract(payback.getPaybackBuleAmount());
					earlyFinishAmount = (earlyFinishAmount.compareTo(bgSum) == Integer.parseInt((zeroFlag))) ? bgSum : earlyFinishAmount;
					paybackMonth.setMonthBeforeReductionAmount(earlyFinishAmount);
				}
				return paybackMonth;
			} else {
				logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount, paybackMonth is: "+ null);
			}
		} else {
			logger.debug("invoke EarlyFinishConfirmService method: getMonthBeforeReductionAmount, contarctCode is: "+ null);
		}
		return null;
	}
	
	/**
	 * 提前结清的冲抵方法
	 * 2015年12月26日
	 * By zhaojinping
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void chargeMonthAmount(Payback payback,BigDecimal creditAmount,PaybackCharge charge) {
		BigDecimal bgSum = BigDecimal.ZERO;
		BigDecimal creditSettleAmount = BigDecimal.ZERO;
		if(ObjectHelper.isNotEmpty(creditAmount))
		{
			creditSettleAmount = creditAmount;
		}
		// 获取当前期及当前期以后的所有期供
		List<PaybackMonth> paybackMonthList = dao.getAllPaybackMonth(payback);
		if (ArrayHelper.isNotEmpty(paybackMonthList)) {
			for (int i = 0; i < paybackMonthList.size(); i++) {
				if (ArrayHelper.isNotEmpty(paybackMonthList)) {
					// 更改第一期的实还本金(提前结清应还款额)
					PaybackMonth paybackMonth = paybackMonthList.get(i);
					// 一次性提前结清金额
					BigDecimal monthBeforeFinishAmount = paybackMonth
							.getMonthBeforeFinishAmount();
					//提前结清总金额
					BigDecimal settleTotalAmount = charge.getSettleTotalAmount();
					
					//实际提前结清金额
					BigDecimal realSettleAmount = settleTotalAmount.subtract(creditSettleAmount);
					
					if(i==0){
					// 更新申请提前结清的这一期的实还本金等于一次性提前结清金额
					BigDecimal monthCapitalPayactual =paybackMonth.getMonthCapitalPayactual();
						paybackMonth
								.setMonthCapitalPayactual(monthCapitalPayactual.add(realSettleAmount));
					}
					
					// 更新期供状态为‘已还款’
					paybackMonth
							.setDictMonthStatus(PeriodStatus.PAID.getCode());
					// 设置减免人
					if (creditSettleAmount.compareTo(BigDecimal.ZERO) > 0) {
						paybackMonth
								.setReductionBy(UserUtils.getUser().getId());
					}
					
					// 设置实际还款时间
					paybackMonth.setMonthPayActualday(new Date());
					paybackMonth.preUpdate();
					dao.updatePaybackMonth(paybackMonth);
					//最新期供信息发送给大金融
					sendDjrPaybackMonthData(paybackMonth);
					if(i==0){
						// 向还款蓝补交易明细表中插入记录
						PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
						paybackBuleAmont.setrMonthId(paybackMonth.getId());// 关连期供ID
						paybackBuleAmont.setTradeAmount(realSettleAmount);// 交易金额
						DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
						df.setMaximumFractionDigits(2);
						paybackBuleAmont
								.setDictDealUse(AgainstContent.CHARGE_AMOUNT
										.getName() + ":" + df.format(realSettleAmount));// 交易用途(冲抵内容)
																					// 冲提前结清违约金
						paybackBuleAmont.setSurplusBuleAmount(payback
								.getPaybackBuleAmount().subtract(
										realSettleAmount));// 蓝补余额
						paybackBuleAmont.setContractCode(payback.getContractCode());
						paybackBuleAmont.setDealTime(new Date());
						insertPaybackBuleAmont(paybackBuleAmont);
						//向大金融推送冲抵记录
						paybackBuleAmont.setDictOffsetType("2");//将冲抵类型改为提前结清
						paybackBuleAmont.setDictDealUse(AgainstContent.MONTHPRINCIPAL.getCode());
						sendDjrDeductData(paybackBuleAmont,paybackMonth.getMonths());
						// 修改蓝补金额
						BigDecimal paybackBuleAmount = payback
								.getPaybackBuleAmount();
						
						paybackBuleAmount = paybackBuleAmount == null ? bgSum
								: paybackBuleAmount;
						
						// 如果计算后的金额为小于0，就设置为0
						if (paybackBuleAmount.compareTo(bgSum) == -1) {
							paybackBuleAmount = bgSum;
						}
						
						payback.setPaybackBuleAmount(payback.getPaybackBuleAmount()
								.subtract(realSettleAmount));
						payback.setSettleMoney(realSettleAmount);
						// 更新主表中的当前第几期
						payback.setPaybackCurrentMonth(paybackMonth.getMonths());
					
						// 更新还款状态为‘提前结清’
						payback.setDictPayStatus(RepayStatus.PRE_SETTLE.getCode());
						payback.preUpdate();
						dao.updateBuleAmount(payback);
					}
					if (i == 0) {
						LoanInfo loan  =new LoanInfo();
						loan.setSettledDate(new Date());
						loan.setContractCode(paybackMonth.getContractCode());
						loan.setDictLoanStatus(LoanApplyStatus.EARLY_SETTLE.getCode());
						loan.preUpdate();
						dao.updateLoanInfoSettle(loan);
						// 保存还款历史明细
						centerDeductService.addPaybackHis(paybackMonth.getId(),
								monthBeforeFinishAmount,
								paybackMonth.getContractCode());
					}
				}
			}
		}

	}
	
	
	/**
	 * 修改冲抵申请表中的冲抵状态为已冲抵 3
	 * 2016年1月11日
	 * By zhaojinping
	 * @param paybackCharge
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateChargeStatus(PaybackCharge paybackCharge){
		dao.updateChargeStatus(paybackCharge);
	}
     /**
      * 向还款蓝补交易明细表中添加记录
      * 2015年12月26日
      * By zhaojinping
      * @param paybackBuleAmont
      * @return none
      */
	@Transactional(readOnly = false, value = "loanTransactionManager")
     public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont){
	    	paybackBuleAmont.setIsNewRecord(false);
			paybackBuleAmont.preInsert();
			paybackBuleAmont.setOperator(UserUtils.getUser().getId());// 插入操作人
			paybackBuleAmont.setTradeType(TradeType.TURN_OUT.getCode());// 交易类型
			paybackBuleAmont.setDictOffsetType(ChargeType.CHARGE_PRESETTLE.getCode());                        // 冲抵类型
    	    paybackBuleAmont.setModifyBy(UserUtils.getUser().getName());
			dao.insertPaybackBuleAmont(paybackBuleAmont);
     }
     
	/**
	 * 保存减免的提前结清金额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateMonthBeforeReductionAmount(PaybackCharge paybackCharge){
		if (!ObjectHelper.isEmpty(paybackCharge)) {
			PaybackMonth paybackMonth = paybackCharge.getPaybackMonth();
		if (!ObjectHelper.isEmpty(paybackMonth)) {
			paybackMonth.preUpdate();
//			paybackMonth.setModifyBy(UserUtils.getUser().get);
//			paybackMonth.setModifyTime(new Date());
			dao.updateMonthBeforeReductionAmount(paybackMonth);
			}
		}
	}
	
	/**
	 * 保存提前结清应还款总额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(PaybackApply paybackApply){
		if(!ObjectHelper.isEmpty(paybackApply)){
			dao.updatePaybackApply(paybackApply);
		}
	}
	
	/**
	 * 保存减免的提前结清金额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param payback
	 * @return string
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String penaltyTotalAmount(Payback payback){
		BigDecimal bgSum = new BigDecimal(MONTH_ZERO);
		if(!ObjectHelper.isEmpty(payback)){
			PaybackMonth paybackMonth = payback.getPaybackMonth();
			if(!ObjectHelper.isEmpty(paybackMonth)){
				BigDecimal monthPenaltySum = paybackMonth.getMonthPenaltySum();//页面中输入的减免违约金罚息金额
			    BigDecimal monthPenaltyShould = paybackMonth.getMonthPenaltyShould();// 应还违约金
				BigDecimal monthInterestPunishshould = paybackMonth.getMonthInterestPunishshould();// 应还罚息

				monthPenaltySum = monthPenaltySum == null ? bgSum :monthPenaltySum;
				monthPenaltyShould = monthPenaltyShould == null ? bgSum :monthPenaltyShould;
				monthInterestPunishshould = monthInterestPunishshould == null ? bgSum :monthInterestPunishshould;
				if(monthPenaltySum.compareTo(monthPenaltyShould) == DeductedConstantEx.CONPARETO || monthPenaltySum.compareTo(monthPenaltyShould) == DeductedConstantEx.INIT_ZERO){
					// 输入的减免金额小于或等于应还违约金
					try {
						paybackMonth.preUpdate();
						dao.updateMonthPenaltyReduction(paybackMonth);
					} catch (Exception e) {
						e.printStackTrace();
						return DeductedConstantEx.CENTRALIZED_DUCTION;
					}
				}else{
						// 输入的减免金额大于应还违约金
					paybackMonth.setMonthPunishReduction(monthPenaltySum.subtract(monthPenaltyShould));
					paybackMonth.preUpdate();
						try {
							dao.updateMonthPunishReduction(paybackMonth);
						} catch (Exception e) {
							e.printStackTrace();
							return DeductedConstantEx.CENTRALIZED_DUCTION;
						}
						if(monthPenaltySum.compareTo(monthPenaltyShould.add(monthInterestPunishshould)) == DeductedConstantEx.ONE){ // 剩下的金额存入蓝补里面
							// 剩余的减免金额
							BigDecimal penaltyInterestPunishShould = monthPenaltyShould.add(monthInterestPunishshould);
							BigDecimal surplusAmount = monthPenaltySum.subtract(penaltyInterestPunishShould);
							// 查询蓝补金额
							BigDecimal paybackBuleAmount = payback.getPaybackBuleAmount().add(surplusAmount);
							try {
								payback.setPaybackBuleAmount(paybackBuleAmount);
								// 将剩余的减免金额存入蓝补金额
								dao.updatePaybackBuleAmount(payback);
							} catch (Exception e) {
								e.printStackTrace();
								return DeductedConstantEx.CENTRALIZED_DUCTION;
							}
						}
				}
			
			}
		}
		return DeductedConstantEx.DUCTION_TODO;
	}
	
	/**
	 * 保存返款金额，向催收服务费返款信息表中插入记录
	 * 2016年1月7日
	 * By zhaojinping
	 * @param urgeServicesBackmoney
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertBackAmount(UrgeServicesBackMoney urgeServicesBackmoney){
		urgeServicesBackmoney.setIsNewRecord(false);
		urgeServicesBackmoney.preInsert();
		dao.insertBackAmount(urgeServicesBackmoney);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public  String earlyFininshConfirm(PaybackCharge paybackCharge,Payback payback,String valBack,String applyBackMes){
		String msg = "";
		BigDecimal bgSum = new BigDecimal("0.0");
		// 在提前结清确认时，判断结清时的还款状态，借款状态，申请时的蓝补金额
		String dictLoanStatus = paybackCharge.getLoanInfo().getDictLoanStatus();
		String dictPayStatus = paybackCharge.getPayback().getDictPayStatus();
		BigDecimal applyBuleAmount = paybackCharge.getApplyBuleAmount();
		BigDecimal paybackBuleAmount = payback.getPaybackBuleAmount();
		BigDecimal paybackAmount = paybackCharge.getUrgeServicesBackMoney()
				.getPaybackBackMoney();
	    paybackAmount = paybackAmount == null ? bgSum : paybackAmount;
		applyBuleAmount = applyBuleAmount == null ? bgSum : applyBuleAmount;
		paybackBuleAmount = paybackBuleAmount == null ? bgSum : paybackBuleAmount;
		if(!(LoanApplyStatus.REPAYMENT.getCode().equals(dictLoanStatus))){
			msg = "2";
			return msg;
		}
		if(!(RepayStatus.PRE_SETTLE_CONFIRM.getCode().equals(dictPayStatus))){
			msg = "3";
			return msg;
		}
		if(applyBuleAmount.compareTo(paybackBuleAmount) > 0){
			msg = "4";
			return msg;
		}
		// 保存数据(保存减免提前结清金额)
		updateMonthBeforeReductionAmount(paybackCharge);
		// 冲抵期供
		chargeMonthAmount(payback,paybackCharge.getPaybackMonth().getMonthBeforeReductionAmount(),paybackCharge);
		// 保存返款金额 催收金额要大于0
		if(YESNO.NO.getCode().equals(valBack) && paybackAmount.compareTo(bgSum)> 0){
			UrgeServicesBackMoney urgeServicesBackmoney = paybackCharge
					.getUrgeServicesBackMoney();
			urgeServicesBackmoney.setrPaybackId(payback.getId());
			urgeServicesBackmoney.setContractCode(paybackCharge
					.getContractCode());
			urgeServicesBackmoney.setDictPayResult(UrgeRepay.REPAY_TO_APPLY
					.getCode());
			urgeServicesBackmoney.setSettlementTime(new Date());
			urgeServicesBackmoney.setPaybackBackMoney(paybackAmount);
			urgeServicesBackmoney.setCreateBy(paybackCharge
					.getPaybackMonth().getModifyBy());
			urgeServicesBackmoney.setDictPayStatus(UrgeRepay.REPAY_TO_APPLY
					.getCode());
			urgeServicesBackmoney.setCreateTime(new Date());
			insertBackAmount(urgeServicesBackmoney);
		}
		// 向还款操作流水中插入记录
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackCharge.getId(), payback.getId(),
				RepaymentProcess.EARLY_SETTLED, TargetWay.REPAYMENT, PaybackOperate.CONFIRM_SUCCEED.getCode() ,
				applyBackMes);
		historyService.insertPaybackOpe(paybackOpes);
		// 更新借款状态为‘提前结清’
		LoanInfo loanInfo = payback.getLoanInfo();
		if(ObjectHelper.isEmpty(loanInfo)){
			loanInfo = new LoanInfo();
		}
		loanInfo.setDictLoanStatus(LoanStatus.EARLY_SETTLE.getCode());
		loanInfo.setModifyBy(UserUtils.getUser().getId());// 获取当前登录用户
		loanInfo.setModifyTime(new Date());
		payback.setLoanInfo(loanInfo);
		paybackService.updateDictLoanStatus(payback);
		// 更新冲抵状态为‘已冲抵’ 3
		paybackCharge.preUpdate();
		paybackCharge.setChargeStatus(AgainstStatus.AIAINST.getCode());
		paybackCharge.setReturnReason(applyBackMes);
		paybackCharge.setModifyBy(UserUtils.getUser().getId());// 获取当前登录用户
		paybackCharge.setModifyTime(new Date());
		updateChargeStatus(paybackCharge);
		boolean flagUpdate = true;
		String loanMark = loanInfo.getLoanFlag();
		if(ChannelFlag.P2P.getCode().equals(loanMark)){
			FinancialBusiness financialBusiness = new FinancialBusiness();
			String loanCode = paybackCharge.getContract().getLoanCode();
			financialBusiness.setLoanCode(loanCode);
			financialBusiness.setCreditType(BIG_FINANCE_STATUS);
			financialBusiness.setModifyBy(UserUtils.getUser().getId());
			financialBusiness.setModifyTime(new Date());
			financialBusiness.preUpdate();
			flagUpdate = dao.updateBigFinance(financialBusiness);
		    if (!flagUpdate) {
			throw new ServiceException("同步大金融数据异常！");
			}
		}
		else if (ChannelFlag.CHP.getCode().equals(loanMark)) {
			boolean flag = tranInfoCf(payback.getId());
			if (flag) {
				msg = "1";
			}else{
				throw new ServiceException("与财富通讯接口异常！");
			}
		}
		else
		{
			msg = "1";
		}
		//提前结清后如果是金信数据或者是财富需推送给金信的数据则更新推送标识
		updateSendFlag(payback.getContractCode(),loanMark);
     	return msg;
	}
	
	/**
	 * 提前结清后如果是金信数据或者是财富需推送给金信的数据则更新推送标识
	 * @author 于飞
	 * @Create 2017年1月11日
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public void updateSendFlag(String contractCode,String loanMark) {
		boolean flag = false;
		//如果是财富数据，看是否是要推送金信的数据
		if (ChannelFlag.CHP.getCode().equals(loanMark)) {
			String data=dao.findDataByContractCode(contractCode);
			if(data!=null && !data.equals("")){
				flag=true;
			}
		}
		if(ChannelFlag.JINXIN.getCode().equals(loanMark) || flag){
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setContractCode(contractCode);
			loanInfo.setSendFlag("1");
			dao.updateSendFlag(loanInfo);
		}
	}
	
	/**
	 * 提前结清发送数据到财富
	 * 2016年1月18日
	 * By zhaojinping
	 * @param id
	 * @return syncClaim
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public boolean tranInfoCf(String id) {
		List<SyncClaim> paybackSyncList = dao.tranInfoCf(id);
		SyncClaim paybackSync = null ;
		if(null != paybackSyncList && paybackSyncList.size() >0 ) {
			paybackSync = paybackSyncList.get(0);
		} else {
			logger.error("提前结清推送债券查询数据出现异常。");
			return false;
		}
		logger.info("调用财富同步接口-->开始");
		paybackSync.setSyncTableName(SyncClaim.SYNC_TABLE_NAME_BORROW);
		paybackSync.setSyncType(SyncDataTypeUtil.VALUE_MODIFY);
		//paybackSync.setLoanCode("JK2016050900000055");
		// 剩余借款期数
		//paybackSync.setLoanMonthsSurplus(0);
		logger.info("同步数据的借款人ID：" + paybackSync.getLoanId() + ",姓名:"
				+ paybackSync.getLoanName());
		// 计算年利率
		if (StringUtils.isNotEmpty(paybackSync.getLoanMonthRate())) {
			paybackSync.setLoanValueYear(String.valueOf((Float
					.valueOf(paybackSync.getLoanMonthRate()) * 12)));
			logger.info("计算年利率" + paybackSync.getLoanValueYear());
		} else {
			paybackSync.setLoanMonthRate(SyncClaim.PAYBACK_LOAN_RATE);
			paybackSync.setLoanValueYear(SyncClaim.PAYBACK_LOAN_RATE);
		}
		Date loanBackmoneyFirday = paybackSync.getLoanBackmoneyFirday();
		if (!ObjectHelper.isEmpty(loanBackmoneyFirday)) {
			String loanBackmoneyFirdayStr = DateUtils
					.formatDateTime(loanBackmoneyFirday);
			// 设置首期还款日 String
			paybackSync.setLoanBackmoneyFirdayStr(loanBackmoneyFirdayStr);
		}
		paybackSync.setLoanCreditValue(paybackSync.getLoanQuota());
		paybackSync.setLoanAvailabeValue(paybackSync.getLoanQuota());
		paybackSync.setDictLoanFreeFlag(SyncClaim.DICT_LOAN_FREE_FLAG_FREEZE);
		paybackSync.setLoanTrusteeFlag(SyncClaim.LOAN_TRUSTEE_FLAG_NOT_DEPOSIT);
		paybackSync.setLoanMonthgainFlag(SyncClaim.LOAN_MONTHGAIN_FLAG_NOT_FULL);
		paybackSync.setLoanFreezeDay(new Date());//冻结时间 
		logger.info("打印要进行同步的数据------>开始");
		printSync(paybackSync);
		logger.info("打印要进行同步的数据------>结束");
		logger.info("调用财富同步接口-->结束");
		return foruneSyncCreditorService
				.executeSyncEarlySettlement(paybackSync);
	}

	/**
	 * 打印同步到财富端的数据 2016年1月18日 By zhaojinping
	 * 
	 * @param syncClaim
	 * @return none
	 */
	public void printSync(SyncClaim syncClaim) {
		logger.info("借款编号"+syncClaim.getLoanCode());
		logger.info("表名" + syncClaim.getSyncTableName());
		logger.info("同步数据类型" + syncClaim.getSyncType());
		logger.info("身份证号：" + syncClaim.getLoanIdcard());
		logger.info("职业：" + syncClaim.getLoanJob());
		logger.info("借款类型：" + syncClaim.getDictLoanType());
		logger.info("借款产品：" + syncClaim.getLoanProduct());
		logger.info("借款用途：" + syncClaim.getLoanPurpose());
		logger.info("借款期数：" + syncClaim.getLoanMonths());
		logger.info("借款利率：" + syncClaim.getLoanMonthRate());
		logger.info("借款年利率：" + syncClaim.getLoanValueYear());
		logger.info("是否可用：" + syncClaim.getDictLoanFreeFlag());
		logger.info("中间人：" + syncClaim.getLoanMiddleMan());
		logger.info("原始债权价值:" + syncClaim.getLoanQuota());
		logger.info("资金托管标识：" + syncClaim.getLoanTrusteeFlag());
		logger.info("月满盈标识：" + syncClaim.getLoanMonthgainFlag());
	}
	
	/**
	 * 更改还款主表中的还款状态
	 * 2016年1月6日
	 * By zhaojinping
	 * @param page
	 * @param paybackCharge
	 * @return page
	 */
	public List<PaybackCharge> findAllPayback(PaybackCharge paybackCharge) {
		List<PaybackCharge> pageList = dao.findPayback(paybackCharge);
		return pageList;
	}
	
	
	/**
	 * 每次冲抵将期供信息发送给大金融
	 * @author 于飞
	 * @Create 2016年9月24日
	 */
	public void sendDjrPaybackMonthData(PaybackMonth month){
		//查看合同是否是大金融数据
		Contract contract = findContractByCode(month.getContractCode());
		if(!contract.getChannelFlag().equals(ChannelFlag.ZCJ.getCode()))
			return;
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_DEBTURP_SERVICE);
		DjrSendDebtURPInBean inParam = new DjrSendDebtURPInBean();
		inParam.setDataTransferId(System.currentTimeMillis()+"");
		inParam.setContractCode(month.getContractCode());
		Customer cm = dao.findCustomerByContractCode(month.getContractCode());
		inParam.setCounstorName(cm.getCustomerName());
		inParam.setPaybackMonthId(month.getId());
		inParam.setMonths(String.valueOf(month.getMonths()));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		inParam.setPayDay(sdf.format(month.getMonthPayDay()));
		if(month.getMonthPayActualday()!=null)
			inParam.setPayActualday(sdf.format(month.getMonthPayActualday()));
		else
			inParam.setPayActualday(sdf.format(new Date()));
		inParam.setPayAmount(month.getMonthPayAmount());
		inParam.setInterestBackshould(month.getMonthInterestBackshould());
		inParam.setCapitalPayactual(month.getMonthCapitalPayactual());
		if(month.getMonthInterestPayactual()!=null)
			inParam.setInterestPayactual(String.valueOf(month.getMonthInterestPayactual()));
		if(month.getIsOverdue()==null)
			inParam.setIsOverdue("0");
		else
			inParam.setIsOverdue(month.getIsOverdue());
		//一次性提前结清应还总额
		if(month.getMonthBeforeFinishAmount()!=null && month.getMonthBeforeReductionAmount()!=null)
			inParam.setBeforeFinishAmount(month.getMonthBeforeFinishAmount().subtract(month.getMonthBeforeReductionAmount()));
		else if(month.getMonthBeforeFinishAmount()!=null && month.getMonthBeforeReductionAmount()==null)
			inParam.setBeforeFinishAmount(month.getMonthBeforeFinishAmount());
		inParam.setInterestPunishshould(month.getMonthInterestPunishshould());
		inParam.setInterestPunishactual(month.getMonthInterestPunishactual());
		inParam.setPunishReduction(month.getMonthPunishReduction());
		inParam.setPenaltyShould(month.getMonthPenaltyShould());
		inParam.setPenaltyActual(month.getMonthPenaltyActual());
		inParam.setPenaltyReduction(month.getMonthPenaltyReduction());
		inParam.setOverdueDays(String.valueOf(month.getMonthOverdueDays()));
		//给大金融推送的期供状态数据：0还款中；1已还款；2逾期/追回；3提前结清（实还本金大于应还本金）
		String status = month.getDictMonthStatus();
		if(status.equals(PeriodStatus.REPAYMENT.getCode()))
			inParam.setMonthStatus("0");
		else if(status.equals(PeriodStatus.OVERDUE.getCode()) || status.equals(PeriodStatus.REPLEVY.getCode()))
			inParam.setMonthStatus("2");
		else if(status.equals(PeriodStatus.PAID.getCode())){
			if(month.getMonthCapitalPayactual().compareTo(month.getMonthPayAmount())>0)
				inParam.setMonthStatus("3");
			else 
				inParam.setMonthStatus("1");
		}
			
		/** 分期咨询费. */
	    inParam.setFeeConsult(month.getMonthFeeConsult());;
	    /** 分期居间服务费. */
	    inParam.setMidFeeService(month.getMonthMidFeeService());
	    inParam.setFeeService(month.getMonthFeeService());
	    /** 分期服务费实还金额. */
	    inParam.setActualFeeService(month.getActualMonthFeeService());
	    
	    inParam.setLateFee(month.getMonthLateFee());
	    inParam.setActualLateFee(month.getActualMonthLateFee());
	    inParam.setLateFeeReduction(month.getMonthLateFeeReduction());
	    inParam.setPayTotal(month.getMonthPayTotal());
	  //剩余本金总和 = 应还本金总和-已还本金总和 ，当剩余本金总和为负数时是提前结清，则将剩余本金设置为0
	    /*BigDecimal residuePayactual = dao.findResiduePayactual(month.getContractCode());
	    if(residuePayactual.compareTo(BigDecimal.ZERO)<0)
	    	residuePayactual=BigDecimal.ZERO;
	    inParam.setResiduePayactual(residuePayactual);*/
	    inParam.setResiduePayactual(month.getMonthResiduePayactual());
	    inParam.setModifyTime(month.getModifyTime());
	    
	    DjrSendDebtURPOutBean outParam = (DjrSendDebtURPOutBean) service.callService(inParam); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("------给大金融推送期供记录成功"+new Date()+"，合同编号是"+month.getContractCode()+"------");
		} else { 
			// TODO 失败 
			logger.info("------给大金融推送期供记录失败"+new Date()+"，合同编号是"+month.getContractCode()+"------");
		} 
	}
	
	/**
	 * 每次冲抵记录发送给大金融
	 * @author 于飞
	 * @Create 2016年9月23日
	 */
	public void sendDjrDeductData(PaybackBuleAmont blue,Integer months){
		//查看合同是否是大金融数据
		Contract contract = findContractByCode(blue.getContractCode());
		if(!contract.getChannelFlag().equals(ChannelFlag.ZCJ.getCode()))
			return;
		//发送冲抵记录信息----------------
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_SUPPLY_SERVICE);
		DjrSendSupplyInBean inParam = new DjrSendSupplyInBean(); 
		inParam.setDataTransferId(System.currentTimeMillis()+"");
		inParam.setContractCode(blue.getContractCode());
		inParam.setSupplyTime(blue.getDealTime());
		inParam.setSupplyMonths(String.valueOf(months));
		inParam.setTotalMoney(blue.getTradeAmount());
		//冲本金
		if(blue.getDictDealUse().equals(AgainstContent.MONTHPRINCIPAL.getCode()))
			inParam.setCapital(blue.getTradeAmount());
		//冲利息
		else if(blue.getDictDealUse().equals(AgainstContent.MONTHINTEREST.getCode()))
			inParam.setProfit(blue.getTradeAmount());
		//冲服务费
		else if(blue.getDictDealUse().equals(AgainstContent.MONTHSERVICEFEE.getCode()))
			inParam.setService(blue.getTradeAmount());
		//冲违约金
		else if(blue.getDictDealUse().equals(AgainstContent.VIOLATE.getCode()))
			inParam.setPenal(blue.getTradeAmount());
		//冲罚息
		else if(blue.getDictDealUse().equals(AgainstContent.PUNISH.getCode()))
			inParam.setInterest(blue.getTradeAmount());
		//冲滞纳金
		else if(blue.getDictDealUse().equals(AgainstContent.SURCHARGE.getCode()) 
				|| blue.getDictDealUse().equals("门店"+AgainstContent.SURCHARGE.getName()))
			inParam.setLateFee(blue.getTradeAmount());
		inParam.setLanbu(String.valueOf(blue.getSurplusBuleAmount()));
		inParam.setSupplyType(blue.getDictOffsetType());
			
		DjrSendSupplyOutBean outParam = (DjrSendSupplyOutBean) service.callService(inParam); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("------给大金融推送冲抵记录成功"+new Date()+"，合同编号是"+blue.getContractCode()+"------");
		} else { 
			// TODO 失败 
			logger.info("------给大金融推送冲抵记录失败"+new Date()+"，合同编号是"+blue.getContractCode()+"------");
		} 
	}
	/**
	 * 根据合同编号查找合同
	 * @author 于飞
	 * @Create 2016年10月21日
	 * @param contractCode
	 * @return
	 */
	public Contract findContractByCode(String contractCode){
		return contractDao.findByContractCode(contractCode);
	}
}
