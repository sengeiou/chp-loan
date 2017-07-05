package com.creditharmony.loan.car.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;
import com.creditharmony.loan.car.carRefund.service.CarRefundAuditService;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.dao.CarDeductUpdateDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.BalanceInfo;
import com.creditharmony.loan.car.common.service.CarBPMLoginService;
import com.creditharmony.loan.car.common.service.CarCityInfoService;
import com.creditharmony.loan.car.common.service.CarFinanceUpdateService;
import com.creditharmony.loan.car.common.service.CarHistoryService;

/**
 * 划扣放款使用服务
 * 
 * @Class Name DeductUpdateService
 * @author 王彬彬
 * @Create In 2016年2月2日
 */
@Service@SuppressWarnings("unused")
public class CarFinanceUpdateServiceImpl implements CarFinanceUpdateService {

	protected Logger logger = LoggerFactory
			.getLogger(CarFinanceUpdateServiceImpl.class);

	@Autowired
	private CarDeductUpdateDao dao;

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	// @Autowired
	// private LoanGrantDao loanGrantDao;

	@Autowired
	private CarCityInfoService cityService;

	@Autowired
	private CarHistoryService historyService;

	// @Autowired
	// private UrgeServicesMoneyDao urgeDao;
	@Autowired
	private CarGrantRecepicService carGrantRecepicService;
	@Autowired
	private CarRefundAuditService carRefundAuditService;
	@Autowired
	private SystemSetMaterDao systemDao;
	@Autowired
	private CarBPMLoginService bpmLoginService;

	// @Autowired
	// private GrantSureService grantSureService;

	// @Autowired
	// private ZhongJinService zhongJinService;

	/**
	 * 集中划扣更新 2016年2月2日 By 王彬彬
	 * 
	 * @param deductList
	 *            划扣返回结果
	 */
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

					if (ResultType.POXY_SUCCESS.getCode().equals(// 结果为成功修改
																	// 回盘结果为成功，否则都为失败
							iteratorSplit.getSplitData().get(i)
									.getSplitBackResult())) {
						iteratorSplit
								.getSplitData()
								.get(i)
								.setSplitBackResult(
										CounterofferResult.PAYMENT_SUCCEED
												.getCode());
					} else {
						iteratorSplit
								.getSplitData()
								.get(i)
								.setSplitBackResult(
										CounterofferResult.PAYMENT_FAILED
												.getCode());
						// TODO 需要重构
						iteratorSplit.setFailReason(iteratorSplit
								.getSplitData().get(i).getSplitFailResult());
					}

				}
				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_01.getCode())) {
					logger.info("MQ----------------车借放款处理" + iteratorSplit.getBusinessId());
					// 车借放款
					carGrantRecepicService.grantReceic(
							iteratorSplit.getBusinessId(),
							iteratorSplit.getDeductFailMoney());
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_02.getCode())) {
					logger.info("MQ----------------车借催收服务费处理" + iteratorSplit.getBusinessId());
					// 车借催收服务费划扣
					carGrantRecepicService.deductReceic(iteratorSplit);
				}

				if (iteratorSplit.getDeductSysIdType().equals(
						CarDeductWays.CJ_03.getCode())) {
					logger.info("MQ----------------车借退款处理" + iteratorSplit.getBusinessId());
					// 车借服务费退款
					carRefundAuditService.refundDealBack(iteratorSplit);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("回盘结果更新一场：请求ID"
						+ iteratorSplit.getRequestId()
						+ "\r\n 业务类型"
						+ CarDeductWays.parseByCode(
								iteratorSplit.getDeductSysIdType()).getName());
			}
		}
	}



	/**
	 * 发送划扣处理（催收服务费） 2016年3月6日 By 王彬彬
	 * 
	 * @param deductReqList
	 */
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
	 * 返回数据写历史 2016年4月16日 By 翁私
	 * 
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
		if (iteratorSplit.getDeductSucceedMoney() == null
				|| "".equals(iteratorSplit.getDeductSucceedMoney())) {
			succeedMoney = new BigDecimal(0);
		} else {
			succeedMoney = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
		}
		if (iteratorSplit.getApplyAmount() == null
				|| "".equals(iteratorSplit.getApplyAmount())) {
			applyAmount = new BigDecimal(0);
		} else {
			applyAmount = new BigDecimal(iteratorSplit.getApplyAmount());
		}
		if ((succeedMoney.compareTo(applyAmount)) >= 0) {
			paybackOpe
					.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			BigDecimal b = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
			paybackOpe.setRemarks("划扣成功" + ":"
					+ b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			paybackOpe.setRemarks("划扣失败");
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED.getCode());
		}
		// paybackOpe.preInsert();
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
	 * 返回数据写历史催收服务费 2016年4月16日 By 朱静越
	 * 
	 * @param iteratorSplit
	 * @return
	 */
	private PaybackOpe urgeFeeHis(LoanDeductEntity iteratorSplit) {
		PaybackOpe paybackOpe = new PaybackOpe();
		List<PaybackSplitEntityEx> splitList = iteratorSplit.getSplitData();
		paybackOpe.setrPaybackApplyId(iteratorSplit.getBatId());
		paybackOpe.setrPaybackId(iteratorSplit.getBatId());
		paybackOpe.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
		paybackOpe.setDictRDeductType(iteratorSplit.getDeductSysIdType());
		BigDecimal succeedMoney = null;
		BigDecimal applyAmount = null;
		if (iteratorSplit.getDeductSucceedMoney() == null
				|| "".equals(iteratorSplit.getDeductSucceedMoney())) {
			succeedMoney = new BigDecimal(0);
		} else {
			succeedMoney = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
		}
		if (iteratorSplit.getApplyAmount() == null
				|| "".equals(iteratorSplit.getApplyAmount())) {
			applyAmount = new BigDecimal(0);
		} else {
			applyAmount = new BigDecimal(iteratorSplit.getApplyAmount());
		}
		if ((succeedMoney.compareTo(applyAmount)) >= 0) {
			paybackOpe
					.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			BigDecimal b = new BigDecimal(iteratorSplit.getDeductSucceedMoney());
			paybackOpe.setRemarks("回盘：划扣成功" + ":"
					+ b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			// 记录划扣失败原因
			String remark = "";
			for (int i = 0; i < splitList.size(); i++) {
				if (CounterofferResult.PAYMENT_FAILED.getCode().equals(
						splitList.get(i).getSplitBackResult())) {
					remark = splitList.get(i).getSplitFailResult();
				}
			}
			paybackOpe.setRemarks("回盘：划扣失败," + remark);
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED.getCode());
		}
		paybackOpe.setId(IdGen.uuid());
		paybackOpe.setCreateBy("系统处理");
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
			paybackOpe.setOperator("系统处理");
			paybackOpe.setOperateCode("");
		}
		paybackOpe.setOperateTime(new Date());
		return paybackOpe;
	}

}
