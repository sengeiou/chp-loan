package com.creditharmony.loan.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantHisDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.event.GrantInsertUrgeService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.zhongjin.service.ZhongJinService;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;
import com.creditharmony.loan.car.carRefund.service.CarRefundAuditService;
import com.creditharmony.loan.channel.jyj.entity.JyjUrgeServicesMoneyEx;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.BalanceInfo;
import com.creditharmony.loan.common.entity.CityInfo;

/**
 * 划扣放款使用服务
 * @Class Name DeductUpdateService
 * @author 王彬彬
 * @Create In 2016年2月2日
 */
@Service
public class FinanceUpdateServiceImpl implements FinanceUpdateService {

	protected Logger logger = LoggerFactory
			.getLogger(FinanceUpdateServiceImpl.class);

	@Autowired
	private DeductUpdateDao dao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private LoanGrantHisDao loanGrantHisDao;

	@Autowired
	private LoanGrantDao loanGrantDao;
	
	@Autowired
	private CityInfoService cityService;

	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantInsertUrgeService grantInsertUrgeService;

	@Autowired
	private UrgeServicesMoneyDao urgeDao;
	@Autowired
	private CarGrantRecepicService carGrantRecepicService;
	@Autowired
	private CarRefundAuditService carRefundAuditService;
	@Autowired
	private SystemSetMaterDao systemDao;
	@Autowired
	private BPMLoginService bpmLoginService;

	@Autowired
	private LoanGrantService loanGrantService;
	
	@Autowired
	private ZhongJinService zhongJinService;

	/**
	 * 集中划扣更新 2016年2月2日 By 王彬彬
	 * 
	 * @param deductList
	 *            划扣返回结果
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplit(List<LoanDeductEntity> deductList) {
		// 划扣回盘
		Iterator<LoanDeductEntity> iterator = deductList.iterator();
		// 集中/非集中划扣回盘（更新主表和申请表，此处不冲抵）
		while (iterator.hasNext()) {
			LoanDeductEntity iteratorSplit = iterator.next();
			try {
				// 拆分记录
				for (int i = 0; i < iteratorSplit.getSplitData().size(); i++) {
					
					if (ResultType.POXY_SUCCESS.getCode().equals(
							iteratorSplit.getSplitData().get(i)
									.getSplitBackResult())) {
						iteratorSplit
								.getSplitData()
								.get(i)
								.setSplitBackResult(
										CounterofferResult.PAYMENT_SUCCEED
												.getCode());
						 //如果成功将失败原因设置为空
						 iteratorSplit.getSplitData().get(i).setSplitFailResult("");
						
					} else {
						iteratorSplit
								.getSplitData()
								.get(i)
								.setSplitBackResult(
										CounterofferResult.PAYMENT_FAILED
												.getCode());
						iteratorSplit.setFailReason(iteratorSplit.getSplitData().get(i).getSplitFailResult());
						// 中金平台-广发银行，余额不足时，银行响应代码为A99，银行响应消息为空白
						if("A99".equals(iteratorSplit.getSplitData().get(i).getSplitBackResult())){
							iteratorSplit.getSplitData().get(i).setSplitFailResult("余额不足");
							iteratorSplit.setFailReason("余额不足");
						}
					}
					if (iteratorSplit.getDeductSysIdType().equals(DeductWays.HJ_01.getCode())){
						String backRes = iteratorSplit.getSplitData().get(i).getSplitFailResult();
						String plat  = iteratorSplit.getSplitData().get(i).getDealType();
						    // 返回为空的时候为余额不足
							if(DeductPlat.ZHONGJIN.getCode().equals(plat)){
								if(!StringUtils.isEmpty(backRes)){
										if((backRes.indexOf("不足") > - 1)  
												|| (backRes.indexOf("没有足够可用资金")> -1)
												|| (backRes.indexOf("交易透支")>-1)
												|| (backRes.indexOf("金额不能为零")>-1)
												|| (backRes.indexOf("不够支付")>-1)){
										    int num = 1 + iteratorSplit.getDeductNum();
											iteratorSplit.setDeductNum(num);
											//break;
								    }	
								}
				            }
							if(DeductPlat.TONGLIAN.getCode().equals(plat)){
								if(!StringUtils.isEmpty(backRes)){
										if((backRes.indexOf("不足") > - 1)  
												|| (backRes.indexOf("没有足够可用资金")> -1)
												|| (backRes.indexOf("交易透支")>-1)
												|| (backRes.indexOf("金额不能为零")>-1)
												|| (backRes.indexOf("不够支付")>-1)){
											int num = 1 + iteratorSplit.getTlDeductNum();
											iteratorSplit.setTlDeductNum(num);
											//break;
								    }	
								}
				            }
					}
					if(iteratorSplit.getDeductSysIdType().equals(DeductWays.HJ_02.getCode())){
						String  accountNo  =  iteratorSplit.getAccountNo();
						String plat  = iteratorSplit.getSplitData().get(i).getDealType();
						String backRes = iteratorSplit.getSplitData().get(i).getSplitFailResult();
						if(DeductPlat.ZHONGJIN.getCode().equals(plat)){
							if(!StringUtils.isEmpty(backRes)){
								if((backRes.indexOf("不足") > - 1)  
									|| (backRes.indexOf("没有足够可用资金")> -1)
									|| (backRes.indexOf("交易透支")>-1)
									|| (backRes.indexOf("金额不能为零")>-1)
									|| (backRes.indexOf("不够支付")>-1)){
										   BalanceInfo  info = new BalanceInfo();
										   info.setAccountNo(accountNo);
										   info.preInsert();
										   saveBalanceInfo(info);
										  // break;
							  }
						    }
						}
					}
					
				}
				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_01.getCode())
						|| iteratorSplit.getDeductSysIdType().equals(
								DeductWays.HJ_02.getCode())
						|| iteratorSplit.getDeductSysIdType().equals(
								DeductWays.HJ_04.getCode())) {

					if (iteratorSplit.getSplitData() != null) {
						if (iteratorSplit.getSplitData().size() > 0) {
							dao.batchInsertDeductSplit(iteratorSplit
									.getSplitData());// 批量插入集中划扣拆分列表
						}
					}
				}

				// 结果回盘更新，记录更新数据(还款划扣)
				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_01.getCode())
						|| iteratorSplit.getDeductSysIdType().equals(
								DeductWays.HJ_02.getCode())) {
					updateDeductInfo(iteratorSplit);
					PaybackOpe ope = historicalWater(iteratorSplit, iteratorSplit);
					dao.singleInsertHis(ope);// 批量插入操作历史表
				}

				// 放款回盘结果处理
				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_03.getCode())) {
					boolean isok = updGrantResult(iteratorSplit);
				}

				// 结果回盘更新，记录更新数据(催收服务费)
				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_04.getCode())) {
					updataUrgeServices(iteratorSplit);
					PaybackOpe ope = urgeFeeHis(iteratorSplit);
					dao.singleInsertHis(ope);// 批量插入操作历史表
				}

				/*if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.CJ_01.getCode())) {
					// 车借放款
					carGrantRecepicService.grantReceic(
							iteratorSplit.getBusinessId(),
							iteratorSplit.getDeductFailMoney());
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.CJ_02.getCode())) {
					// 车借催收服务费划扣
					carGrantRecepicService.deductReceic(iteratorSplit);
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.CJ_03.getCode())) {
					// 车借服务费退款
					carRefundAuditService.refundDealBack(iteratorSplit);
				}*/
				if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.ZJ_01.getCode())) {
					// 插入历史
					if (iteratorSplit.getSplitData() != null) {
						if (iteratorSplit.getSplitData().size() > 0) {
							dao.batchInsertDeductSplit(iteratorSplit
									.getSplitData());// 批量插入集中划扣拆分列表
						}
					}
					// 中金预约划扣回盘
					zhongJinService.updateDeductResult(iteratorSplit);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("回盘结果更新一场：请求ID"
						+ iteratorSplit.getRequestId()
						+ "\r\n 业务类型"
						+ DeductWays.parseByCode(
								iteratorSplit.getDeductSysIdType()).getName());
			}
		}
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
	 * 划扣回盘结果更新用（无业务处理,单纯转存划扣结果） 2016年2月3日 By 王彬彬
	 * 
	 * @param iteratorSplit
	 *            单条回盘结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDeductInfo(LoanDeductEntity iteratorSplit) {
		/** 更新还款申请表START */
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
			paybackApply.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
		} else {
			// 集中划扣不更新到已办列表，当天处理完成后流转到已办（系统批处理）
			if (iteratorSplit.getDeductSysIdType().equals(
					DeductWays.HJ_01.getCode())) {
				paybackApply
						.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE
								.getCode());
				paybackApply.setFailReason(iteratorSplit.getFailReason());
			} else if (iteratorSplit.getDeductSysIdType().equals(
					DeductWays.HJ_02.getCode())) {
				// 非集中划扣处理完成后直接流转到集中划扣已办。
				paybackApply
						.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
								.getCode());
				paybackApply.setFailReason(iteratorSplit.getFailReason());
				paybackApply.setDictPaybackStatus(RepayApplyStatus.DEDUCTT_FAILED.getCode());
			} else {
				paybackApply
						.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
								.getCode());
			}
		}
		paybackApply.setDictDealType(iteratorSplit.getSplitData().get(0).getDealType());
		paybackApply.setApplyReallyAmount(succeedMoney);
		paybackApply.setSplitFlag(YESNO.YES.getCode());
		paybackApply.setId(iteratorSplit.getBatId());
		if (iteratorSplit.getDeductSysIdType().equals(
				DeductWays.HJ_01.getCode())) {
			paybackApply.setCpcnCount(iteratorSplit.getDeductNum());
			paybackApply.setTlCount(iteratorSplit.getTlDeductNum());
			updateDeductsPaybackApply(paybackApply);
			updatePaybackListHis(paybackApply);
			// 如果回盘结果成功 然后 插入 还款_集中划扣还款申请归档表
		if (CounterofferResult.PAYMENT_SUCCEED.getCode().equals(
					paybackApply.getSplitBackResult())) {
				// 因为回盘有时候会很久，这个时候集中划扣的数据已经被删除，需要更新还款_集中划扣还款申请归档里面的数据。
				PaybackApply apply = dao.queryPaybackApply(paybackApply);
				if(apply == null){
					paybackApply.preUpdate();
					dao.updateDeductsPaybackApplyHis(paybackApply);
				 }else{
					insertDeductsPaybackApplyHis(paybackApply);
					// 如果成功将集中划扣该数据删除
					deleteDeductsPaybackApply(paybackApply);
				}
			}else{
				// 因为回盘有时候会很久，这个时候集中划扣的数据已经被删除，需要更新还款_集中划扣还款申请归档里面的数据。
				PaybackApply apply = dao.queryPaybackApply(paybackApply);
				if(apply == null){
					paybackApply.preUpdate();
					dao.updateDeductsPaybackApplyHis(paybackApply);
				}
			}
		} else {
			updatePaybackApply(paybackApply);
		}
		  /** 更新还款申请表END */
		 paybackApply.setRequestId(iteratorSplit.getRequestId());
		 // 划扣记录
		 addPaybackDeducts(paybackApply);
	   if(succeedMoney.compareTo(new BigDecimal(0)) == 1){
		// 还款主表
			Payback payback = new Payback();
			payback.preUpdate();
			payback.setId(iteratorSplit.getBusinessId());
			payback.setPaybackBuleAmount(succeedMoney);
			// 更新蓝补金额
			dao.updateBuleAmount(payback);
			Map<String, String> contractMap = new HashMap<String, String>();
			contractMap.put("id", iteratorSplit.getBusinessId());
			List<Payback> paybackList = dao.getPayback(contractMap); // 获取还款主表
			payback = paybackList.get(0);
			   if (iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_01.getCode())) {
					// 蓝补历史更新
					addPaybackBuleAmont(iteratorSplit.getRefId(),succeedMoney,
							payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
							AgainstContent.CENTERDEDUCT, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
				}
				if(iteratorSplit.getDeductSysIdType().equals(
						DeductWays.HJ_02.getCode())){
					// 蓝补历史更新
					addPaybackBuleAmont(iteratorSplit.getRefId(),succeedMoney,
							payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
							AgainstContent.PAYBACK_DEDUCT, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
				}
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

	/**
	 * 2016年4月12日 By 翁私
	 * 
	 * @param paybackApply
	 *            增加划扣记录
	 * @return none
	 */
	private void addPaybackDeducts(PaybackApply paybackApply) {
		PaybackDeducts deducts = new PaybackDeducts();
		deducts.setDeductAmount(paybackApply.getApplyReallyAmount());
		deducts.setrId(paybackApply.getId());
		deducts.setDictBackResult(paybackApply.getSplitBackResult());
		// deducts.setDictRDeductType(TargetWay.PAYMENT.getCode())
		deducts.preInsert();
		deducts.setDecuctTime(new Date());
		dao.addPaybackDeducts(deducts);

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
	 * 更新 还款_待还款归档列表 2016年2月16日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	private void updatePaybackListHis(PaybackApply paybackApply) {
		try {
			User user = UserUtils.getUser();
			if (user != null) {
				if (StringUtils.isNotBlank(user.getId())) {
					paybackApply.setModifyBy(user.getId());
				} else {
					paybackApply.setModifyBy("系统处理");
				}
			} else {
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			e.printStackTrace();
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
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
		try {
			User user = UserUtils.getUser();
			if (user != null) {
				if (StringUtils.isNotBlank(user.getId())) {
					paybackApply.setModifyBy(user.getId());
				} else {
					paybackApply.setModifyBy("系统处理");
				}
			} else {
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
		
		dao.updateDeductsPaybackApply(paybackApply);
	}

	/**
	 * 更新还款申请表 2016年2月16日 By 王彬彬
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(PaybackApply paybackApply) {
		try {
			User user = UserUtils.getUser();
			if(user != null){
				if (StringUtils.isNotBlank(user.getId())){
					paybackApply.setModifyBy(user.getId());
				}else{
					paybackApply.setModifyBy("系统处理");
				}
			}else{
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			e.printStackTrace();
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
		dao.updatePaybackApply(paybackApply);
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
			AgainstContent againstContent, ChargeType chargeType,String contractCode) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();
		paybackBuleAmont.setCreateBy("batch");
		paybackBuleAmont.setOperator("batch");
		paybackBuleAmont.setModifyBy("batch");
		paybackBuleAmont.setrMonthId(rMonthId);// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);// 蓝补余额
		paybackBuleAmont.setOperator("系统处理");// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间
		paybackBuleAmont.setContractCode(contractCode);

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
	 * 催收服务费回盘结果更新 2016年2月24日 By 翁私
	 * 
	 * @param iteratorSplit
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updataUrgeServices(LoanDeductEntity iteratorSplit) {
		/** 更新催收服务费信息表START */
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		UrgeServicesMoney urgeMoneys = urgeDao.find(iteratorSplit.getBatId());
		BigDecimal urgeDeduct = urgeMoneys.getUrgeDecuteMoeny();
		List<PaybackSplitEntityEx> splitList = iteratorSplit
				.getSplitData();
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
			urgeServicesMoney
					.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED
							.getCode());
			urgeServicesMoney
					.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED
							.getCode());
			// 划扣成功之后更新划扣平台
			for (int i = 0; i < splitList.size(); i++) {
				if (splitList.get(i).getSplitBackResult().equals(CounterofferResult.PAYMENT_SUCCEED.getCode())) {
					urgeServicesMoney.setDictDealType(splitList.get(i).getDealType());
				}
			}
			
			// 查询改数据是不是简易借金额
			JyjUrgeServicesMoneyEx jyj = new JyjUrgeServicesMoneyEx();
			jyj.setId(iteratorSplit.getBatId());
			//查询查账是是否成功
			List<JyjUrgeServicesMoneyEx>  list = dao.queryJyjData(jyj);
			if(list.size()>0){
				JyjUrgeServicesMoneyEx ex = list.get(0);
				Payback payback = new Payback();
				payback.preUpdate();
				payback.setContractCode(ex.getContractCode());
				payback.setPaybackBuleAmount(ex.getMoney());
				// 更新蓝补金额
				dao.updateBuleAmountByContractCode(payback);
				BigDecimal paybackBuleAmount;
				if(ex.getPaybackBuleAmount() == null || "".equals(ex.getPaybackBuleAmount())){
					paybackBuleAmount = new BigDecimal(0);
				}else{
					paybackBuleAmount = ex.getPaybackBuleAmount();
				}
				paybackBuleAmount  = ex.getMoney().add(paybackBuleAmount);
				// 蓝补历史更新
				addPaybackBuleAmont(iteratorSplit.getRefId(),ex.getMoney(),
						paybackBuleAmount, TradeType.TRANSFERRED,
						AgainstContent.COMPREHENSIVE_SERVICE, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
			}
		} else {
			urgeServicesMoney
					.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED
							.getCode());
			urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PAYMENT_FAILED
					.getCode());
			// 划扣失败之后更新划扣平台 ,失败原因
			for (int i = 0; i < splitList.size(); i++) {
				if (splitList.get(i).getSplitBackResult().equals(CounterofferResult.PAYMENT_FAILED.getCode())) {
					urgeServicesMoney.setDictDealType(splitList.get(i).getDealType());
					urgeServicesMoney.setDeductFailReason(splitList.get(i).getSplitFailResult());
				}
			}			
		}
		urgeServicesMoney.setUrgeDecuteMoeny((new BigDecimal(iteratorSplit
				.getDeductSucceedMoney())).add(urgeDeduct));
		urgeServicesMoney.setId(iteratorSplit.getBatId());
		urgeServicesMoney.setUrgeDecuteDate(new Date());
		urgeServicesMoney.preUpdate();
		urgeServicesMoney.setDeductYesno(GrantCommon.JYJ_URGE_TYPE);
		dao.updataUrgeServices(urgeServicesMoney);
		
	
		/** 更新催收服务费信息表END */

		/** 对于为滚动划扣的单子，获得回盘结果为失败时，自动再次进行提交划扣start，8点之前自动进行滚动划扣 **/
		
		 /* Calendar calendar = Calendar.getInstance(); 
		  calendar.setTime(new Date()); 
		  Integer hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		  // 从数据库中查找该单子是否是滚动划扣，
		UrgeServicesMoney autoUrge = urgeDao.find(iteratorSplit.getBatId());

		if (CounterofferResult.PAYMENT_FAILED.getCode().equals(
				autoUrge.getDeductStatus())
				&& StringUtils.isNotEmpty(deductRule)) {

			if (YESNO.YES.getCode().equals(autoUrge.getAutoDeductFlag())) {
				deductRule = autoUrge.getDeductJumpRule();
			}
			if (hourOfDay < 20) {
				autoDeduct(urgeServicesMoney, deductRule);
			}
		}*/
		 
		/** 对于为滚动划扣的单子，获得回盘结果为失败时，自动再次进行提交划扣end **/
	}

	/**
	 * 滚动划扣时进行判断
	 * 2016年4月20日
	 * By 朱静越
	 * @param autoUrge
	 * @param deductRule
	 */
	public void autoDeduct(UrgeServicesMoney autoUrge, String deductRule) {
		if (CounterofferResult.PAYMENT_FAILED.getCode().equals(
				autoUrge.getDeductStatus())
				&& StringUtils.isNotEmpty(deductRule)) {
			UrgeServicesMoneyEx urgeDeduct = new UrgeServicesMoneyEx();
			urgeDeduct.setUrgeId("'" + autoUrge.getId() + "'");
			List<DeductReq> list = urgeDao.selSendList(urgeDeduct);
			for (DeductReq deductReq : list) {
				// 设置划扣标志为代收
				deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
				// 设置划扣规则，从前台接收过来的规则
				deductReq.setRule(deductRule);
				// 系统处理ID，设置为催收处理
				deductReq.setSysId(DeductWays.HJ_04.getCode());
			}
			DeResult t = TaskService.addTask(list.get(0));
			if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
				// 将单子的划扣平台更新为要发送的平台，更新催收主表的划扣平台
				UrgeServicesMoney urge = new UrgeServicesMoney();
				// 设置催收主表的id
				urge.setId("'" + autoUrge.getId() + "'");
				// 将催收主表中回盘结果设置为处理中，划扣日期没有更新，直接在批处理调用完成之后更新划扣时间
				urge.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
				urge.setDeductStatus(UrgeCounterofferResult.PROCESS.getCode());
				urgeDao.updateUrge(urge);
				PaybackOpeEx paybackOpeEx = new PaybackOpeEx(null, list.get(0)
						.getBatId(), RepaymentProcess.DEDECT,
						TargetWay.SERVICE_FEE,
						PaybackOperate.SEND_SUCCESS.getCode(), "定时发送划扣");
				historyService.insertPaybackOpe(paybackOpeEx);
				// 发送成功之后，获得将拆分表中处理状态为失败的单子进行删除
				urgeDeduct
						.setSplitBackResult(UrgeCounterofferResult.PAYMENT_FAILED
								.getCode());
				List<UrgeServicesMoneyEx> delList = urgeDao
						.selProcess(urgeDeduct);
				// 删除
				if (delList.size() > 0) {
					for (int i = 0; i < delList.size(); i++) {
						urgeDao.delProcess("'" + delList.get(i).getId() + "'");
					}
				}
			}

		}

	}

	/**
	 * 放款、划扣结果更新 2016年2月29日 By 王彬彬
	 * 1.更新借款状态
	 * 2.更新放款表信息
	 * 3.添加放款划扣回盘结果信息
	 * 4.插入催收服务费
	 * 5.插入历史
	 * 
	 * @param deductList
	 *            回盘数据
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updGrantResult(LoanDeductEntity deductList) {
		String bakApplyId = deductList.getBatId();
		String strResult = null;
		logger.info("放款回盘结果更新开始applyId=" + bakApplyId);
		try {
			LoanGrant loanGrant = new LoanGrant();
			loanGrant.setContractCode(deductList.getBusinessId());
			LoanGrantEx loanGrantEx =  loanGrantDao.findGrant(loanGrant);
			if (!ObjectHelper.isEmpty(loanGrantEx)) {
				LoanGrantHis loanGrantHis = new LoanGrantHis();
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setApplyId(bakApplyId);
				loanInfo.setLoanCode(loanGrantEx.getLoanCode());
				loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY
								.getCode());
				// 记录放款历史表
				loanGrantHis.setContractCode(deductList.getBusinessId());
				loanGrantHis.setApplyGrantAmount(new BigDecimal(deductList.getApplyAmount()));
				loanGrantHis.setSuccessAmount(new BigDecimal(deductList.getDeductSucceedMoney()));
				loanGrantHis.setFailAmount(new BigDecimal(deductList.getApplyAmount()).subtract(new BigDecimal(deductList.getDeductSucceedMoney())));
				loanGrantHis.setGrantBackMes("");
				loanGrantHis.setCreateBy(deductList.getCreateBy());
				
				BigDecimal grantFailAmount = loanGrantEx.getGrantFailAmount();
				String deductPlat = loanGrantEx.getDictLoanWay();

				if (PaymentWay.ZHONGJIN.getCode().equals(deductPlat)) {
					deductPlat = GrantCommon.ZHONG_JING;
				} else {
					deductPlat = GrantCommon.TONG_LIAN;
				}
				loanGrantHis.setGrantDeductFlag(deductPlat);
				// 接口的回盘结果为成功，进行流程处理
				BigDecimal succeedMoney = null;
				BigDecimal applyAmount = null;
				if(deductList.getDeductSucceedMoney() == null || "".equals(deductList.getDeductSucceedMoney())){
					succeedMoney = new BigDecimal(0);
				}else{
					succeedMoney = new BigDecimal(deductList.getDeductSucceedMoney());
				}
				if(deductList.getApplyAmount() == null || "".equals(deductList.getApplyAmount())){
					applyAmount = new BigDecimal(0);
				}else{
					applyAmount = new BigDecimal(deductList.getApplyAmount());
				}
				if ((succeedMoney.compareTo(applyAmount)) >= 0) {
					// 更新单子的借款状态，回盘结果，同时单子流转到待放款审核
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
							.getCode());
					loanGrantHis.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
							.getName());
					loanGrant.setGrantBackDate(new Date());
					// 如果失败金额不为0，更新失败金额为申请金额-成功金额，为0不进行更新
					if (grantFailAmount.compareTo(BigDecimal.ZERO)!=0) {
						loanGrant.setGrantFailAmount(applyAmount.subtract(succeedMoney));
					}
					loanGrantHis.setGrantFailResult("");
					strResult = GrantCommon.SUCCESS;
				} else {
					// 接口的回盘结果为失败，进行流程处理,更新单子的回盘结果，借款状态，失败原因，失败金额
					loanGrant.setGrantFailAmount(applyAmount.subtract(succeedMoney));
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED
							.getCode());
					loanGrantHis.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED
							.getName());
					loanGrant.setGrantBackDate(new Date());
					// 更新失败原因
					List<PaybackSplitEntityEx> splitList = deductList
							.getSplitData();
					for (int j = 0; j < splitList.size(); j++) {
						if (splitList
								.get(j)
								.getSplitBackResult()
								.equals(CounterofferResult.PAYMENT_FAILED
										.getCode())) {
							loanGrant.setGrantFailResult(splitList.get(j)
									.getSplitFailResult());
							loanGrantHis.setGrantFailResult(splitList.get(j).getSplitFailResult());
							
						}
					}
					strResult = GrantCommon.FALSE;
				}
				LoanInfo loanInfoSel = applyLoanInfoDao.findStatusByLoanCode(loanGrantEx.getLoanCode());
				if (!LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(loanInfoSel.getDictLoanStatus())) {
					// 放款成功插入催收服务费信息
					Map<String, String> map = new HashMap<String, String>();
					map.put("applyId", bakApplyId);
					map.put("loanFlag", ChannelFlag.CHP.getCode());
					grantInsertUrgeService.urgeServiceInsertDeal(map);
				}
				// 更新借款表
				applyLoanInfoDao.updateLoanInfo(loanInfo);
				// 更新放款表
				loanGrantDao.updateLoanGrant(loanGrant);
				// 添加划扣历史
				loanGrantHis.preInsert();
				loanGrantHisDao.insertGrantHis(loanGrantHis);
				// 插入历史
				historyService.saveLoanStatusHis(loanInfo,"放款", strResult,"");
			}
			logger.info("放款回盘结果更新成功applyId:" + bakApplyId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("放款回盘结果更新失败，请检查applyId:" + bakApplyId);
		}
		return false;
	}

	/**
	 * 催收服务费要发送的数据获取 2016年3月6日 By 王彬彬
	 * 
	 * @param urgeMoney
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<DeductReq> selSendList(UrgeServicesMoney urgeMoney) {
		// 默认滚动划扣
		// TODO 获取系统设的平台规则（修正）
		String rule = DeductPlatType.HYL.getCode() + ":"
				+ DeductType.REALTIME.getCode() + ","

				+ DeductPlatType.FY.getCode() + ":"
				+ DeductType.REALTIME.getCode() + ","

				+ DeductPlatType.ZJ.getCode() + ":"
				+ DeductType.REALTIME.getCode() + ","

				+ DeductPlatType.TL.getCode() + ":"
				+ DeductType.REALTIME.getCode();

		// 取得发送数据规则
		List<DeductReq> list = dao.selSendList(urgeMoney);
		for (DeductReq deductReq : list) {
			// 设置划扣标志为代收
			deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			// 系统处理ID，设置为催收处理
			deductReq.setSysId(DeductWays.HJ_04.getCode());

			// 设置划扣规则，从前台接收过来的规则
			if (StringUtils.isEmpty(rule)) {
				rule = deductReq.getRule() + ":"
						+ DeductType.REALTIME.getCode();
			}
			deductReq.setRule(rule);

			// 设置省，中文
			if (!StringUtils.isEmpty(deductReq.getBankProv())) {

				CityInfo c = cityService.findAreaName(deductReq.getBankProv());
				if (!ObjectHelper.isEmpty(c)) {
					deductReq.setBankProv(c.getAreaName());
				}
			}
			// 设置市，中文
			if (!StringUtils.isEmpty(deductReq.getBankCity())) {
				CityInfo c = cityService.findAreaName(deductReq.getBankProv());
				if (!ObjectHelper.isEmpty(c)) {
					deductReq.setBankCity(c.getAreaName());
				}
			}
		}
		return list;
	}

	/**
	 * 发送划扣处理（催收服务费） 2016年3月6日 By 王彬彬
	 * 
	 * @param deductReqList
	 */
	@SuppressWarnings("unused")
	private void updateUrgeInfo(List<DeductReq> deductReqList) {

		/*
		 * if (ArrayHelper.isNotEmpty(deductReqList)) { String idString =
		 * StringUtils.EMPTY; RepaymentProcess dictStep = null; for (DeductReq e
		 * : deductReqList) { idString = idString + "," +
		 * e.getBatId().replaceFirst(",", ""); }
		 * 
		 * // 发送到批处理 DeResult t = TaskService.addTask(deductReqList);
		 * 
		 * if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) { //
		 * 将单子的划扣平台更新为要发送的平台，更新催收主表的划扣平台 UrgeServicesMoney urge = new
		 * UrgeServicesMoney(); urge.setId(idString);
		 * 
		 * // 将催收主表中回盘结果设置为处理中
		 * urge.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
		 * urge.setUrgeDecuteDate(new Date()); urge.preUpdate();
		 * urgeDao.updateUrge(urge);
		 * 
		 * // 同时插入历史,获得划扣平台 // TODO 划扣日志 dictStep = RepaymentProcess.SEND_FUYOU;
		 * 
		 * for (int i = 0; i < deductReqList.size(); i++) { PaybackOpeEx
		 * paybackOpeEx = new PaybackOpeEx(null,
		 * deductReqList.get(i).getBatId(), dictStep, TargetWay.SERVICE_FEE,
		 * "成功", "发送划扣"); historyService.insertPaybackOpe(paybackOpeEx); }
		 * 
		 * // 发送成功之后，获得将拆分表中处理状态为处理中（线下）的单子进行删除 UrgeServicesMoneyEx urgeServices
		 * = new UrgeServicesMoneyEx();
		 * urgeServices.setSplitResult(UrgeCounterofferResult.PROCESSED
		 * .getCode()); List<UrgeServicesMoneyEx> delList = urgeDao
		 * .selProcess(urgeServices); // 删除 if (delList.size() > 0) { for (int i
		 * = 0; i < delList.size(); i++) {
		 * urgeDao.delProcess(delList.get(i).getId()); } } } }
		 */
	}

	public List<PaybackOpe> historicalWater(List<PaybackSplitEntityEx> list,
			LoanDeductEntity entity) {
		List<PaybackOpe> opelist = new ArrayList<PaybackOpe>();

		for (PaybackSplitEntityEx deductReq : list) {
			PaybackOpe paybackOpe = new PaybackOpe();
			/*
			 * if(DeductPlat.FUYOU.getCode().equals(deductReq.getDealType())){
			 * dictLoanStatus =RepaymentProcess.SEND_FUYOU.getCode(); // 发送富有
			 * }else
			 * if(DeductPlat.HAOYILIAN.getCode().equals(deductReq.getDealType
			 * ())){ dictLoanStatus =RepaymentProcess.SEND_HYL.getCode(); // 发送
			 * 好易联 }else
			 * if(DeductPlat.ZHONGJIN.getCode().equals(deductReq.getDealType
			 * ())){ dictLoanStatus =RepaymentProcess.SEND_ZHONGJIN.getCode();
			 * // 发送中金 }else
			 * if(DeductPlat.TONGLIAN.getCode().equals(deductReq.getDealType
			 * ())){ dictLoanStatus =RepaymentProcess.SEND_TONGLIAN.getCode();
			 * // 发送通联 }
			 */
			// contractMap.put("contractCode", entity.getBusinessId());
			// List<Payback> paybackList = dao.getPayback(contractMap); //
			// 获取还款主表
			// Payback Popek = paybackList.get(0);
			paybackOpe.setrPaybackApplyId(entity.getBatId());
			paybackOpe.setrPaybackId(entity.getBusinessId());
			paybackOpe.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
			paybackOpe.setDictRDeductType(deductReq.getDeductType());
			if (deductReq.getSplitBackResult().equals(
					ResultType.POXY_SUCCESS.getCode())) {
				paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED
						.getCode());
				paybackOpe.setRemarks("划扣成功" + ":"
						+ deductReq.getSplitAmount().toString());
			} else {
				paybackOpe.setRemarks("划扣失败" + ":"
						+ deductReq.getSplitAmount().toString());
				paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED
						.getCode());
			}
			paybackOpe.setId(IdGen.uuid());
			paybackOpe.setCreateTime(new Date());
			paybackOpe.setModifyTime(new Date());
			paybackOpe.setCreateBy("系统处理");
			paybackOpe.setModifyBy("系统处理");
			paybackOpe.setOperator("系统处理");
			paybackOpe.setOperateCode("");
			paybackOpe.setOperateTime(new Date());
			opelist.add(paybackOpe);
		}
		return opelist;
	}
	
	/**
	 * 返回数据写历史   2016年4月16日 By 翁私
	 * @param iteratorSplit
	 * @param iteratorSplit2
	 * @return
	 */
	 private PaybackOpe historicalWater(LoanDeductEntity iteratorSplit,
	 		LoanDeductEntity iteratorSplit2) {
			PaybackOpe paybackOpe = new PaybackOpe();
			paybackOpe.setrPaybackApplyId(iteratorSplit.getBatId());
			paybackOpe.setrPaybackId(iteratorSplit.getBusinessId());
			paybackOpe.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
			paybackOpe.setDictRDeductType(iteratorSplit.getDeductSysIdType());
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
				paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED
						.getCode());
				BigDecimal b = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
				paybackOpe.setRemarks("划扣成功" + ":"+ b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			} else {
				String remark = "";
				List<PaybackSplitEntityEx> splitList = iteratorSplit
						.getSplitData();
				for (int i = 0; i < splitList.size(); i++) {
					if (CounterofferResult.PAYMENT_FAILED.getCode().equals(splitList.get(i).getSplitBackResult())) {
						remark = splitList.get(i).getSplitFailResult();
					}
				}
				paybackOpe.setRemarks("划扣失败:"+remark);
				paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED
						.getCode());
			}
			//paybackOpe.preInsert();
			paybackOpe.setId(IdGen.uuid());
			paybackOpe.setCreateTime(new Date());
			try {
				String userCode = UserUtils.getUser().getUserCode();
				String userId = UserUtils.getUser().getId();
				if (StringUtils.isNotEmpty(userId)) {
					paybackOpe.setOperator(userId);
					paybackOpe.setOperateCode(userCode);
				} else {
					paybackOpe.setOperator("系统处理");
					paybackOpe.setOperateCode("系统处理");
				}
			} catch (Exception e) {
				e.printStackTrace();
				paybackOpe.setOperator("系统处理");
				paybackOpe.setOperateCode("");
			}
			paybackOpe.setOperateTime(new Date());
		    return paybackOpe;
	 }
	 
		/**
		 * 返回数据写历史催收服务费   2016年4月16日 By 朱静越
		 * @param iteratorSplit
		 * @return
		 */
		 private PaybackOpe urgeFeeHis(LoanDeductEntity iteratorSplit) {
				PaybackOpe paybackOpe = new PaybackOpe();
				List<PaybackSplitEntityEx> splitList = iteratorSplit
						.getSplitData();
				paybackOpe.setrPaybackApplyId(iteratorSplit.getBatId());
				paybackOpe.setrPaybackId(iteratorSplit.getBatId());
				paybackOpe.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
				paybackOpe.setDictRDeductType(iteratorSplit.getDeductSysIdType());
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
					paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED
							.getCode());
					BigDecimal b = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
					paybackOpe.setRemarks("回盘：划扣成功" + ":"+ b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					// 记录划扣失败原因
					String remark = "";
					for (int i = 0; i < splitList.size(); i++) {
						if (CounterofferResult.PAYMENT_FAILED.getCode().equals(splitList.get(i).getSplitBackResult())) {
							remark = splitList.get(i).getSplitFailResult();
						}
					}
					paybackOpe.setRemarks("回盘：划扣失败,"+remark);
					paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED
							.getCode());
				}
				paybackOpe.setId(IdGen.uuid());
				paybackOpe.setCreateBy("系统处理");
				paybackOpe.setCreateTime(new Date());
				try {
					String userCode = UserUtils.getUser().getUserCode();
					String userId = UserUtils.getUser().getId();
					if(StringUtils.isNotEmpty(userId)){
						paybackOpe.setOperator(userId);
						paybackOpe.setOperateCode(userCode);
					}else{
						paybackOpe.setOperator("系统处理");
						paybackOpe.setOperateCode("系统处理");
					}
				} catch (Exception e) {
					paybackOpe.setOperator("系统处理");
					paybackOpe.setOperateCode("");
				}
				paybackOpe.setOperateTime(new Date());
			    return paybackOpe;
		 }	 
		 
	 /**
	  * 保存余额不足的数据
	  * @param info
	  */
	 @Transactional(readOnly = false, value = "loanTransactionManager")
	 public void  saveBalanceInfo(BalanceInfo info){
		 dao.saveBalanceInfo(info);
	 }
	 
	 /**
	  * 设置划扣次数
	  */
	 public void deductNum(){
		 
	 }
}
