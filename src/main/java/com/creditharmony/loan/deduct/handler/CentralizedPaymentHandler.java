package com.creditharmony.loan.deduct.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.deduct.util.DeductWrapperUtils;


/**
 * 集中回款处理类
 * @Class Name CentralizedPaymentHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
@Component
public class CentralizedPaymentHandler extends DeductResultHandler {

	@Autowired
	private DeductUpdateDao deductUpdateDao;
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void handleBusiness(LoanDeductEntity deduct) {
		deductUpdateDao.batchInsertDeductSplit(deduct.getSplitData());
		updateDeductInfo(deduct);
		PaybackOpe paybackOperate = DeductWrapperUtils.wrapperPaybackOpe(deduct);
		deductUpdateDao.singleInsertHis(paybackOperate); 
	}
	
	/**
	 * 划扣回盘结果更新用（无业务处理,单纯转存划扣结果）
	 * 2016年5月20日
	 * By 张永生
	 * @param deduct
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDeductInfo(LoanDeductEntity deduct) {
		PaybackApply paybackApply = new PaybackApply();
		String deductWay = deduct.getDeductSysIdType();
		if (deduct.getDeductSucceedMoney().equals(deduct.getApplyAmount())) {
			paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
			paybackApply.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
		} else {
			// 集中划扣不更新到已办列表，当天处理完成后流转到已办（系统批处理）
			if (DeductWays.HJ_01.getCode().equals(deductWay)) {
				paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE.getCode());
				paybackApply.setFailReason(deduct.getFailReason());
			} else if (DeductWays.HJ_02.getCode().equals(deductWay)) {
				// 非集中划扣处理完成后直接流转到集中划扣已办。
				paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
				paybackApply.setFailReason(deduct.getFailReason());
				paybackApply.setDictPaybackStatus(RepayApplyStatus.DEDUCTT_FAILED.getCode());
			} else {
				paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
			}
		}
		paybackApply.setDictDealType(deduct.getSplitData().get(0).getDealType());
		paybackApply.setApplyReallyAmount(new BigDecimal(deduct.getDeductSucceedMoney()));
		paybackApply.setSplitFlag(YESNO.YES.getCode());
		paybackApply.setId(deduct.getBatId());
		if (DeductWays.HJ_01.getCode().equals(deductWay)) {
			updateDeductsPaybackApply(paybackApply);
			updatePaybackListHis(paybackApply);
			// 如果回盘结果成功 然后 插入 还款_集中划扣还款申请归档表
			if (CounterofferResult.PAYMENT_SUCCEED.getCode().equals(paybackApply.getSplitBackResult())) {
				insertDeductsPaybackApplyHis(paybackApply);
				// 如果成功将集中划扣该数据删除
				deleteDeductsPaybackApply(paybackApply);
			}
		} else {
			updatePaybackApply(paybackApply);
		}
		paybackApply.setRequestId(deduct.getRequestId());
		// 划扣记录
		addPaybackDeducts(paybackApply);
		// 还款主表
		Payback payback = new Payback();
		payback.preUpdate();
		payback.setId(deduct.getBusinessId());
		payback.setPaybackBuleAmount(new BigDecimal(deduct.getDeductSucceedMoney()));
		// 更新蓝补金额
		deductUpdateDao.updateBuleAmount(payback);
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("id", deduct.getBusinessId());
		List<Payback> paybackList = deductUpdateDao.getPayback(queryParams); // 获取还款主表
		payback = paybackList.get(0);
		// 蓝补历史更新
		addPaybackBuleAmont(deduct.getRefId(), new BigDecimal(
				deduct.getDeductSucceedMoney()),
				payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
				null, ChargeType.CHARGE_PRESETTLE);
	}
	
	/**
	 * 更新集中划扣还款申请表
	 * 2016年5月20日
	 * By 张永生
	 * @param paybackApply
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDeductsPaybackApply(PaybackApply paybackApply) {
		User user = UserUtils.getUser();
		if (user != null) {
			if (StringUtils.isNotBlank(user.getId())) {
				paybackApply.setModifyBy(user.getId());
			} else {
				paybackApply.setModifyBy("FinanceBatch");
			}
		} else {
			paybackApply.setModifyBy("FinanceBatch");
		}
		paybackApply.setModifyTime(new Date());
		deductUpdateDao.updateDeductsPaybackApply(paybackApply);
	}

	/**
	 * 更新 还款_待还款归档列表
	 * 2016年5月21日
	 * By 张永生
	 * @param paybackApply
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePaybackListHis(PaybackApply paybackApply) {
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
		deductUpdateDao.updatePaybackListHis(paybackApply);
	}
	
	/**
	 * 插入 还款_集中划扣还款申请归档表 
	 * 2016年5月21日
	 * By 张永生
	 * @param paybackApply
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void insertDeductsPaybackApplyHis(PaybackApply paybackApply) {
		deductUpdateDao.insertDeductsPaybackApplyHis(paybackApply);
	}
	
	/**
	 * 删除集中划扣数据
	 * 2016年5月21日
	 * By 张永生
	 * @param paybackApply
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void deleteDeductsPaybackApply(PaybackApply paybackApply) {
		deductUpdateDao.deleteDeductsPaybackApply(paybackApply);
	}
	
	/**
	 * 更新还款申请表
	 * 2016年5月21日
	 * By 张永生
	 * @param paybackApply
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
		deductUpdateDao.updatePaybackApply(paybackApply);
	}
	
	/**
	 * 增加划扣记录
	 * 2016年5月21日
	 * By 张永生
	 * @param paybackApply
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackDeducts(PaybackApply paybackApply) {
		PaybackDeducts deducts = new PaybackDeducts();
		deducts.setDeductAmount(paybackApply.getApplyReallyAmount());
		deducts.setrId(paybackApply.getId());
		deducts.setDictBackResult(paybackApply.getSplitBackResult());
		deducts.preInsert();
		deducts.setDecuctTime(new Date());
		deductUpdateDao.addPaybackDeducts(deducts);
	}
	
	/**
	 * 保存还款蓝补交易明细 
	 * 2016年5月21日
	 * By 张永生
	 * @param rMonthId
	 * @param tradeAmount
	 * @param surplusBuleAmount
	 * @param tradeType
	 * @param againstContent
	 * @param chargeType
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackBuleAmont(String rMonthId, BigDecimal tradeAmount,
			BigDecimal surplusBuleAmount, TradeType tradeType,
			AgainstContent againstContent, ChargeType chargeType) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();
		paybackBuleAmont.setrMonthId(rMonthId);							// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());				// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);					// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);		// 蓝补余额
		paybackBuleAmont.setOperator(UserUtils.getUser().getId());		// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());	// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());		// 冲抵类型
		paybackBuleAmont.setDealTime(new Date());						// 交易时间
		deductUpdateDao.addBackBuleAmont(paybackBuleAmont);
	}
	
}