package com.creditharmony.loan.car.carExtend.event;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;

/**
 * 车借合同审核  处理流程回调
 * @Class Name CarExtendContractCheckEvent
 * @author 申诗阔
 * @Create In 2016年3月10日
 */
@Service("ex_hj_carExtendContractCheck_CarLoanFlow")
public class CarExtendContractCheckEvent extends CarExtendWorkFlowCommonService implements ExEvent{

	@Override
	public String getBeanName() {
		return null;
	}

	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		String response = workItem.getResponse();
		// 取出在controller 中放入的业务数据，需要向下转换
		CarCheckRateBusinessView flowView = (CarCheckRateBusinessView) workItem.getBv();
		String stepName = workItem.getStepName();
		if (response.equals(CarLoanResponses.TO_END.getCode())) { // 合同审核 通过
			flowView.setTimeOutFlag("0");
			updateLoanGrantInfo(flowView);
			flowView.setApplyStatusCode(CarLoanStatus.REPAYMENT_APPLICATION.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_CONTRACT_REVIEW_PASS.getCode());
		} else if (response.equals(CarLoanResponses.BACK_SIGN.getCode())) {
			flowView.setApplyStatusCode(CarLoanStatus.EXTEND_CONTRACT_CHECK_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_CONTRACT_REVIEW_BACK_CONFIRMED_SIGN.getCode());
			flowView.setTimeOutFlag("1");
			//标红置顶处理(退回）
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.CONTRACT_CHECK.getName());
		} else if (response.equals(CarLoanResponses.BACK_UPLOAD_CONTRACT.getCode())) {
			flowView.setApplyStatusCode(CarLoanStatus.EXTEND_CONTRACT_CHECK_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_CONTRACT_REVIEW_BACK_SIGNED_CONTRACT.getCode());
			flowView.setTimeOutFlag("1");
			//标红置顶处理(退回）
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.CONTRACT_CHECK.getName());
		}
		carContractHandle(flowView, stepName);
	}
}
