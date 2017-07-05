package com.creditharmony.loan.deduct.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.dao.DeductUpdateDao;


/**
 * 服务费催收
 * @Class Name CentralizedPaymentHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
@Component
public class ServiceChargeCollectHandler extends DeductResultHandler {

	@Autowired
	private DeductUpdateDao deductUpdateDao;
	
	@Autowired
	private UrgeServicesMoneyDao urgeDao;
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void handleBusiness(LoanDeductEntity deduct) {
		deductUpdateDao.batchInsertDeductSplit(deduct.getSplitData());
		updataUrgeServices(deduct);
		PaybackOpe ope = urgeFeeHis(deduct);
		deductUpdateDao.singleInsertHis(ope);
	}
	
	/**
	 * 催收服务费回盘结果更新
	 * 2016年5月21日
	 * By 张永生
	 * @param deduct
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updataUrgeServices(LoanDeductEntity deduct) {
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		UrgeServicesMoney urgeMoneys = urgeDao.find(deduct.getBatId());
		BigDecimal urgeDeduct = urgeMoneys.getUrgeDecuteMoeny();
		List<PaybackSplitEntityEx> splitList = deduct.getSplitData();
		if (deduct.getDeductSucceedMoney().equals(deduct.getApplyAmount())) {
			urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED.getCode());
			urgeServicesMoney.setDeductStatus(CounterofferResult.PAYMENT_SUCCEED.getCode());
			// 划扣成功之后更新划扣平台
			for(PaybackSplitEntityEx paybackSplit : splitList){
				if (CounterofferResult.PAYMENT_SUCCEED.getCode().equals(paybackSplit.getSplitBackResult())) {
					urgeServicesMoney.setDictDealType(paybackSplit.getDealType());
				}
			}
		} else {
			urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_FAILED.getCode());
			urgeServicesMoney.setDeductStatus(CounterofferResult.PAYMENT_FAILED.getCode());
			// 划扣失败之后更新划扣平台
			for(PaybackSplitEntityEx paybackSplit : splitList){
				if (CounterofferResult.PAYMENT_FAILED.getCode().equals(paybackSplit.getSplitBackResult())) {
					urgeServicesMoney.setDictDealType(paybackSplit.getDealType());
				}
			}
		}
		urgeServicesMoney.setUrgeDecuteMoeny((new BigDecimal(deduct.getDeductSucceedMoney())).add(urgeDeduct));
		urgeServicesMoney.setId(deduct.getBatId());
		urgeServicesMoney.setUrgeDecuteDate(new Date());
		deductUpdateDao.updataUrgeServices(urgeServicesMoney);
	}
	
	/**
	 * 返回数据写历史催收服务费 
	 * 2016年5月21日
	 * By 张永生
	 * @param deduct
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public PaybackOpe urgeFeeHis(LoanDeductEntity deduct) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(deduct.getBatId());
		paybackOpe.setrPaybackId(deduct.getBatId());
		paybackOpe.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
		paybackOpe.setDictRDeductType(deduct.getDeductSysIdType());
		if (deduct.getDeductSucceedMoney().equals(deduct.getApplyAmount())) {
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			BigDecimal deductMoney = new BigDecimal(deduct.getDeductSucceedMoney());
			paybackOpe.setRemarks("回盘：划扣成功" + ":" + deductMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			paybackOpe.setRemarks("回盘：划扣失败");
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_FAILED.getCode());
		}
		paybackOpe.preInsert();
		paybackOpe.setOperator("FinanceBatch");
		paybackOpe.setOperateCode("FinanceBatch");
		paybackOpe.setOperateTime(new Date());
		return paybackOpe;
	} 

}
