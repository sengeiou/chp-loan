package com.creditharmony.loan.deduct.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.util.DeductHelper;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;


/**
 * 放款处理类
 * @Class Name CentralizedPaymentHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
@Component
public class ReleaseMoneyHandler extends DeductResultHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ReleaseMoneyHandler.class);

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private LoanGrantDao loanGrantDao;
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void handleBusiness(LoanDeductEntity deduct) {
		updateGrantResult(deduct);
	}
	
	/**
	 * 放款、划扣结果更新
	 * 2016年5月20日
	 * By 张永生
	 * @param deductList
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updateGrantResult(LoanDeductEntity deduct) {
		String bakApplyId = deduct.getBatId();
		logger.info("放款回盘结果更新开始, applyId=" + bakApplyId);
		try {
			WorkItemView workItem = flowService.loadWorkItemViewForAdmin(bakApplyId);
			LoanGrantHis loanGrantHis = new LoanGrantHis();
			if (!ObjectHelper.isEmpty(workItem)) {
				GrantDealView grantDealView = new GrantDealView();
				grantDealView.setApplyId(bakApplyId);
				grantDealView.setContractCode(deduct.getBusinessId());
				
				// 记录放款历史表
				loanGrantHis.setContractCode(deduct.getBusinessId());
				loanGrantHis.setApplyGrantAmount(new BigDecimal(deduct.getApplyAmount()));
				loanGrantHis.setSuccessAmount(new BigDecimal(deduct.getDeductSucceedMoney()));
				loanGrantHis.setFailAmount(new BigDecimal(deduct.getApplyAmount()).subtract(new BigDecimal(deduct.getDeductSucceedMoney())));
				loanGrantHis.setGrantBackMes("");
				loanGrantHis.setCreateBy(deduct.getCreateBy());
				
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setContractCode(deduct.getBusinessId());
				LoanGrantEx loanGrantEx =  loanGrantDao.findGrant(loanGrant);
				BigDecimal grantFailAmount = loanGrantEx.getGrantFailAmount();
				String deductPlat = loanGrantEx.getDictLoanWay();
				if (PaymentWay.ZHONGJIN.getCode().equals(deductPlat)) {
					deductPlat = GrantCommon.ZHONG_JING;
				} else {
					deductPlat = GrantCommon.TONG_LIAN;
				}
				loanGrantHis.setGrantDeductFlag(deductPlat);
				// 接口的回盘结果为成功，进行流程处理
				if (deduct.getDeductSucceedMoney().equals(deduct.getApplyAmount())) {
					workItem.setResponse(LoanFlowRoute.DETAILCONFIRM);
					// 更新单子的借款状态，回盘结果，同时单子流转到待放款审核
					grantDealView.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY.getName());
					grantDealView.setDictLoanStatusCode(LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
					grantDealView.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					loanGrantHis.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getName());
					grantDealView.setGrantBackDate(new Date());
					// 如果失败金额不为0，更新失败金额为申请金额-成功金额，为0不进行更新
					if (grantFailAmount.compareTo(BigDecimal.ZERO) != 0) {
						grantDealView.setGrantFailAmount(DeductHelper
								.getDeductFailedMoney(deduct.getApplyAmount(),
										deduct.getDeductSucceedMoney()));
					}
					loanGrantHis.setGrantFailResult("");
					grantDealView.setLoanGrantHis(loanGrantHis);
					grantDealView.setUpdGrantHisFlag(YESNO.YES.getCode());
					workItem.setBv(grantDealView);
					workItem.setCheckDealUser(false);
					logger.info("工作流处理线上放款操作---->开始");
					flowService.dispatch(workItem);
					logger.info("工作流处理线上放款操作---->结束");
				} else {
					workItem.setResponse(LoanFlowRoute.DETAILCONFIRM);
					// 接口的回盘结果为失败，进行流程处理,更新单子的回盘结果，借款状态，失败原因，失败金额
					grantDealView.setGrantFailAmount(DeductHelper.getDeductFailedMoney(
							deduct.getApplyAmount(),
							deduct.getDeductSucceedMoney()));
					grantDealView.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
					grantDealView.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY.getName());
					grantDealView.setDictLoanStatusCode(LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
					loanGrantHis.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getName());
					grantDealView.setGrantBackDate(new Date());
					// 更新失败原因
					List<PaybackSplitEntityEx> splitList = deduct.getSplitData();
					for(PaybackSplitEntityEx paybackSplit : splitList){
						if (CounterofferResult.PAYMENT_FAILED.getCode()
								.equals(paybackSplit.getSplitBackResult())) {
							grantDealView.setGrantFailResult(paybackSplit.getSplitFailResult());
							loanGrantHis.setGrantFailResult(paybackSplit.getSplitFailResult());
						}
					}
					grantDealView.setOperateType(YESNO.YES.getCode());
					grantDealView.setLoanGrantHis(loanGrantHis);
					grantDealView.setUpdGrantHisFlag(YESNO.YES.getCode());
					workItem.setBv(grantDealView);
					workItem.setCheckDealUser(false);
					logger.info("工作流处理线上放款操作---->开始");
					flowService.dispatch(workItem);
					logger.info("工作流处理线上放款操作---->结束");
				}
			}
			logger.info("放款回盘结果更新成功applyId:" + bakApplyId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("放款回盘结果更新失败，请检查applyId:" + bakApplyId);
		}
		return false;
	}

}
