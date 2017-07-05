package com.creditharmony.loan.car.carExtend.event;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;

/**
 * 车借合同制作  处理流程回调
 * @Class Name CarExtendContractProEvent
 * @author 申诗阔
 * @Create In 2016年3月9日
 */
@Service("ex_hj_carExtendContractPro_CarLoanFlow")
public class CarExtendContractProEvent extends CarExtendWorkFlowCommonService implements ExEvent{

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
		if (response.equals(CarLoanResponses.TO_UPLOAD_CONTRACT.getCode())) { // 合同制作 通过
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_PRODUCED_CONTRACT_SUCCESS.getCode());
			flowView.setGrossFlag(YESNO.YES.getCode());
			//标红置顶处理(提交）
			BackOrCommitUtil.redTopCommit(workItem, flowView, CarLoanSteps.CONTRACT_PRODUCT.getName());
		} else if (response.equals(CarLoanResponses.BACK_SIGN.getCode())){ // 合同制作 退回到签署
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_PRODUCED_CONTRACT_BACK_SIGN.getCode());
			//标红置顶处理(退回）
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.CONTRACT_PRODUCT.getName());
		} else if (response.equals(CarLoanResponses.BACK_AUDIT_RATE.getCode())) { // 合同制作 退回到审核费率
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_PRODUCED_CONTRACT_BACK_INTEREST_RATE.getCode());
			//标红置顶处理(退回）
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.CONTRACT_PRODUCT.getName());
		}
		carContractHandle(flowView, stepName);
	}
}
