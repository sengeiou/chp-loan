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
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 车借合同审核  处理流程回调
 * @Class Name CarContractCheckEvent
 * @author 李静辉
 * @Create In 2016年2月18日
 */
@Service("ex_hj_carContractCheck_CarLoanFlow")
public class CarContractCheckEvent extends CarWorkFlowCommonService implements ExEvent{

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
		if (response.equals(CarLoanResponses.TO_GRANT_CONFIRM.getCode())) { // 合同审核 通过
			flowView.setTimeOutFlag("0");
			updateLoanGrantInfo(flowView);
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_LOAN_CONFIRMATION.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.CONTRACT_REVIEW_PASS.getCode());
			//标红置顶提交下一步相关业务
		   redTopCommit(workItem, flowView);  
		} else if (response.equals(CarLoanResponses.BACK_SIGN.getCode())) {
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode());
			flowView.setOperResultName(CarLoanOperateResult.CONTRACT_REVIEW_BACK_CONFIRMED_SIGN.getCode());
			//标红置顶退回相关业务
			flowView.setTimeOutFlag("1");
	        redTopBack(workItem, flowView);
		} else if (response.equals(CarLoanResponses.BACK_UPLOAD_CONTRACT.getCode())) {
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode());
			flowView.setOperResultName(CarLoanOperateResult.CONTRACT_REVIEW_BACK_SIGNED_CONTRACT.getCode());
			//标红置顶退回相关业务
	        redTopBack(workItem, flowView);
	        flowView.setTimeOutFlag("1");
		}
		
		carContractHandle(flowView, stepName);
	}

	private void redTopBack(WorkItemView workItem,
			CarCheckRateBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		   Object object = workItem.getFlowProperties();
		   if(object != null){
		       FlowProperties flowProperties = (FlowProperties)object;
		       if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
		    		   flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
		    	   flowView.setFirstBackSourceStep(CarLoanSteps.CONTRACT_CHECK.getName());
		          }
		       flowView.setOrderField("0," + sdf.format(new Date()));
		   }
	}

	private void redTopCommit(WorkItemView workItem,
			CarCheckRateBusinessView flowView) {
	
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            Object object = workItem.getFlowProperties();
            if(object != null){
                FlowProperties flowProperties = (FlowProperties)object;
                if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.CONTRACT_CHECK.getName(), 
                        CarLoanResponses.TO_GRANT_CONFIRM.getCode(), flowProperties.getFirstBackSourceStep())){
                	flowView.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
                	flowView.setOrderField("1," + sdf.format(new Date()));
                }else{
                    if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
                    		 flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
                    	flowView.setOrderField("1," + sdf.format(new Date()));
                    }else{
                    	flowView.setOrderField("0," + sdf.format(new Date()));
                    }
                }
            }
	}
}
