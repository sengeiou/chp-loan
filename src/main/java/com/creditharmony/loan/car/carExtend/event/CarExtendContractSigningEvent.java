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
 * 车借合同签约上传  处理流程回调
 * @Class Name CarExtendContractProEvent
 * @author 申诗阔
 * @Create In 2016年3月9日
 */
@Service("ex_hj_carExtendContractSigning_CarLoanFlow")
public class CarExtendContractSigningEvent extends CarExtendWorkFlowCommonService implements ExEvent{

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
		if (response.equals(CarLoanResponses.TO_AUDIT_CONTRACT.getCode())) { // 签订上传合同 通过
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_CONTRACT_REVIEW.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_SIGNED_CONTRACT_PASS.getCode());
			//关闭合同超时chp-roll轮询检测
			flowView.setTimeOutFlag("0");
			//标红置顶处理(提交)
			BackOrCommitUtil.redTopCommit(workItem, flowView, CarLoanSteps.CONTRACT_UPLOAD.getName());
		} else if (CarLoanResponses.EXTEND_GIVE_UP.getCode().equals(response)) {
			flowView.setApplyStatusCode(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_CUSTOMER_GIVE_UP.getCode());
		} else if (CarLoanResponses.BACK_SIGN_CONTRACT.getCode().equals(response)) {
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_SIGNED_CONTRAC_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_BACK.getCode());
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.CONTRACT_UPLOAD.getName());
		}
		carContractHandle(flowView, stepName);
	}

}
