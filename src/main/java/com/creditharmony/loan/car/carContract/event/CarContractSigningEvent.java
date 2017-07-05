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
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 车借合同签约上传  处理流程回调
 * @Class Name CarContractProEvent
 * @author 李静辉
 * @Create In 2016年2月18日
 */
@Service("ex_hj_carContractSigning_CarLoanFlow")
public class CarContractSigningEvent extends CarWorkFlowCommonService implements ExEvent{

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
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_CONTRACT_REVIEW.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_PASS.getCode());
			//关闭合同超时chp-roll轮询检测
			flowView.setTimeOutFlag("0");
			
			//标红置顶提交下一步相关业务
			redTopCommit(workItem, flowView,CarLoanResponses.TO_AUDIT_CONTRACT.getCode());
	        
		} else if (response.equals(CarLoanResponses.SUPPLY_SEC_AUDIT.getCode())) {
			flowView.setDictLoanStatus(CarLoanStatus.SUPPLY_PENDING_REVIEW.getCode());
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_PASS_SUPPLY_PENDING_REVIEW.getCode());
			//标红置顶提交下一步相关业务
			redTopCommit(workItem, flowView,CarLoanResponses.SUPPLY_SEC_AUDIT.getCode());
			
		} else if (response.equals(CarLoanResponses.BACK_SIGN_CONTRACT.getCode())) {
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_BACK.getCode());
			
			//保存退回原因
			CarLoanInfo carLoanInfo = new CarLoanInfo();
			carLoanInfo.setLoanCode(flowView.getLoanCode());
			carLoanInfo.setRemark(flowView.getBackReason());
			carLoanInfoDao.update(carLoanInfo);
			
		   //标红置顶退回相关业务
           redTopBack(workItem, flowView); 
	           
		} else if (response.equals(CarLoanResponses.UPLOAD_CONTRACT_ABANDON.getCode())) {
			flowView.setOperResultName(CarLoanOperateResult.SIGNED_CONTRACT_CUSTOMER_GIVE_UP.getCode());
		} else if (response.equals(CarLoanResponses.UPLOAD_CONTRACT_STORE_REFUSE.getCode())) {
			flowView.setOperResultName("签订上传合同门店拒绝");
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
            	       flowView.setFirstBackSourceStep(CarLoanSteps.CONTRACT_UPLOAD.getName());
                  }
               flowView.setOrderField("0," + sdf.format(new Date()));
           }
	}

	private void redTopCommit(WorkItemView workItem,
			CarCheckRateBusinessView flowView,String response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(
					CarLoanSteps.CONTRACT_UPLOAD.getName(),
					response,
					flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
				flowView.setOrderField("1," + sdf.format(new Date()));
			} else {
				if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
						|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
								.equals(flowProperties
										.getFirstBackSourceStep())) {
					flowView.setOrderField("1," + sdf.format(new Date()));
				} else {
					flowView.setOrderField("0," + sdf.format(new Date()));
				}
			}
		}
	}

}
