package com.creditharmony.loan.car.carContract.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 车借利率审核  处理流程回调
 * @Class Name CarInterestRateAudit
 * @author 李静辉
 * @Create In 2016年2月17日
 */
@Service("ex_hj_carInterestRateAudit_CarLoanFlow")
public class CarInterestRateAuditEvent extends CarWorkFlowCommonService implements ExEvent{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

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
		if (response.equals(CarLoanResponses.TO_SIGN.getCode())) { // 审核费率 通过
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.INTEREST_RATE_PASS.getCode());
			//flowView.setContractVersion(CarCommonUtil.getNewContractVer());
			//设置轮询检测的超时日期。第一次通过费率审核时，以当前日期后的第8个自然日为超时日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			//流程当前超时日期
			Integer timeoutCheckStage = flowView.getTimeoutCheckStage();
			try {
				//第一费率审核通过,设置超时日期，并开启轮询检测
				if(timeoutCheckStage==null||timeoutCheckStage == 0 ){
					Date currDate = sdf.parse(sdf.format(new Date()));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currDate);
					calendar.add(Calendar.DAY_OF_MONTH, 8);
					flowView.setTimeOutFlag("1");	//开启合同超时chp-roll轮询检测
					flowView.setTimeOutPoint(calendar.getTime());
					flowView.setTimeoutCheckStage(1);
					// 添加合同到期提醒日期
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					flowView.setContractExpirationDate(calendar.getTime());
				}
				
				//标红置顶提交下一步相关业务
		        redTopCommit(workItem, flowView);  
				
			} catch (ParseException e) {
				logger.error("设置合同超时作废日期失败",e);
				throw new RuntimeException(e);
			}
		} else if (response.equals(CarLoanResponses.BACK_END_AUDIT.getCode())){ // 审核费率 退回
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_AUDIT_INTEREST_RATE_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.INTEREST_RATE_BACK.getCode());
			//标红置顶退回相关业务
	        redTopBack(workItem, flowView);  
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
		    	   flowView.setFirstBackSourceStep(CarLoanSteps.RATE_CHECK.getName());
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
		        if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.RATE_CHECK.getName(), 
		                CarLoanResponses.TO_SIGN.getCode(), flowProperties.getFirstBackSourceStep())){
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
