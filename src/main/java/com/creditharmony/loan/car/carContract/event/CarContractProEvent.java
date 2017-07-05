package com.creditharmony.loan.car.carContract.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 车借合同制作  处理流程回调
 * @Class Name CarContractProEvent
 * @author 李静辉
 * @Create In 2016年2月18日
 */
@Service("ex_hj_carContractPro_CarLoanFlow")
public class CarContractProEvent extends CarWorkFlowCommonService implements ExEvent{

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
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.PRODUCED_CONTRACT_SUCCESS.getCode());
			flowView.setGrossFlag(YESNO.YES.getCode());
			//标红置顶提交下一步相关业务
			redTopCommit(workItem, flowView);  

		} else if (response.equals(CarLoanResponses.BACK_SIGN.getCode())){ // 合同制作 退回到签署
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.PRODUCED_CONTRACT_BACK_CONFIRMED.getCode());
		    
			// 标红置顶退回相关业务
			redTopBack(workItem, flowView);
		} else if (response.equals(CarLoanResponses.BACK_AUDIT_RATE.getCode())) { // 合同制作 退回到审核费率
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.PRODUCED_CONTRACT_BACK_INTEREST_RATE.getCode());
			// 标红置顶退回相关业务
			redTopBack(workItem, flowView);
		}
		carContractHandle(flowView, stepName);
	}

	private void redTopBack(WorkItemView workItem,
			CarCheckRateBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
					|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
							.equals(flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(CarLoanSteps.CONTRACT_PRODUCT
						.getName());
			}
			flowView.setOrderField("0," + sdf.format(new Date()));
		}
	}

	private void redTopCommit(WorkItemView workItem,
			CarCheckRateBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(
					CarLoanSteps.CONTRACT_PRODUCT.getName(),
					CarLoanResponses.TO_UPLOAD_CONTRACT.getCode(),
					flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
				flowView.setOrderField("1," + sdf.format(new Date()));
			} else {
				if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
						|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
								.equals(flowProperties.getFirstBackSourceStep())) {
					flowView.setOrderField("1," + sdf.format(new Date()));
				} else {
					flowView.setOrderField("0," + sdf.format(new Date()));
				}
			}
		}
	}
}
