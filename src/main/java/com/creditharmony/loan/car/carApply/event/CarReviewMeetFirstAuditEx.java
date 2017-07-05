package com.creditharmony.loan.car.carApply.event;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.carApply.view.ReviewMeetFirstAuditBusinessView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarAuditResultDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 车借面审初审事件
 * @Class Name CarReviewMeetFirstAuditEvent
 * @author 陈伟东
 * @Create In 2016年2月22日
 */
@Service("ex_hj_reviewMeet_firstAudit_CarLoanFlow")
public class CarReviewMeetFirstAuditEx implements ExEvent{

	@Override
	public String getBeanName() {
		return null;
	}
	
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	
	//车借面审信息
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
	
	// 客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
	
	//车借审核结果
	@Autowired
	private CarAuditResultDao carAuditResultDao;

	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		String response = workItem.getResponse();
		// 取出在controller 中放入的业务数据，需要向下转换
		ReviewMeetFirstAuditBusinessView flowView = (ReviewMeetFirstAuditBusinessView) workItem.getBv();
		//得到借款编码
		String loanCode = flowView.getLoanCode();
		String remark = flowView.getRemark();
		CarApplicationInterviewInfo carApplicationInterviewInfo = new CarApplicationInterviewInfo();
		CarAuditResult carAuditResult =new CarAuditResult();
		ReflectHandle.copy(flowView, carApplicationInterviewInfo);
		ReflectHandle.copy(flowView, carAuditResult);
		CarLoanInfo carLoanInfo = new CarLoanInfo();
		if(flowView.getAuditAmount() != null ){
			BigDecimal auditAmount = new BigDecimal(Double.toString(flowView.getAuditAmount()));
			carAuditResult.setAuditAmount(auditAmount);			
		}
		if(flowView.getGrossRate() != null){
			BigDecimal grossRate = new BigDecimal(Double.toString(flowView.getGrossRate()));
			carAuditResult.setGrossRate(grossRate);			
		}
		if(flowView.getFirstServiceTariffing() != null){
			BigDecimal firstServiceTariffing = new BigDecimal(Double.toString(flowView.getFirstServiceTariffing()));
			carAuditResult.setFirstServiceTariffing(firstServiceTariffing);	
			carLoanInfo.setFirstServiceTariffingRate(firstServiceTariffing);
		}
		//2.添加审核结果
		if(!CarLoanResponses.FIRST_AUDIT_CUSTOMER_GIVE_UP.getCode().equals(response)){
			CarLoanInfo info = carLoanInfoService.selectByLoanCode(loanCode);
			carAuditResult.setDictCheckType(CarLoanSteps.FIRST_AUDIT.getCode());
			carAuditResult.preInsert();
			carAuditResult.setFirstServiceTariffing(info.getFirstServiceTariffingRate());
			carAuditResultDao.insert(carAuditResult);							
		}
		if(CarLoanResponses.TO_SEC_AUDIT.getCode().equals(response)){
			//初审通过
			//添加借款信息表中首次初审通过时间信息
			if(null == carLoanInfo.getFirstEntryApprove()){
				carLoanInfo.setFirstEntryApprove(new Date());
			}
			//1.添加车借面审信息
			CarApplicationInterviewInfo applicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
			if(applicationInterviewInfo == null){
				carApplicationInterviewInfo.preInsert();
				carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);				
			}else{
				carApplicationInterviewInfo.preUpdate();
				carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
			}
			//2.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(),
					CarLoanOperateResult.FIRST_INSTANCE_TO_REVIEW.getCode(), 
					null,CarLoanStatus.PENDING_REVIEW.getCode());
			//添加初审借款类型 借款期限
			carLoanInfo.setDictProductType(flowView.getDictProductType());
			carLoanInfo.setLoanMonths(new BigDecimal(flowView.getAuditLoanMonths()));
			carLoanInfo.setDeviceUsedFee(flowView.getDeviceUsedFee());
			// 标红置顶提交下一步相关业务
			redTopCommit(workItem, flowView);
		}else if(CarLoanResponses.FIRST_AUDIT_REFUSED.getCode().equals(response)){
			//初审拒绝
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.FIRST_INSTANCE_STORE__GIVE_UP.getCode(),
					null,CarLoanStatus.FIRST_INSTANCE_REJECT.getCode());
		}else if(CarLoanResponses.BACK_ASSESS_ENTER.getCode().equals(response)){
			//退回评估师录入
			//1.添加车借面审信息
			CarApplicationInterviewInfo applicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
			if(applicationInterviewInfo == null){
				carApplicationInterviewInfo.preInsert();
				carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);				
			}else{
				carApplicationInterviewInfo.preUpdate();
				carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
			}
			//退回后咨询状态变更为继续追踪	修改客户咨询信息	开始
			if(loanCode != null){
				CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
				carCustomerConsultation.setLoanCode(loanCode);
				carCustomerConsultation.setDictOperStatus(flowView.getDictOperStatus());
				carCustomerConsultation.preUpdate();
				carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
			}
			//退回后咨询状态变更为继续追踪	修改客户咨询信息	结束
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.FIRST_INSTANCE_BACK_ASSESS_ENTER.getCode(),
					remark,CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			
			//标红置顶退回相关业务
			redTopBack(workItem, flowView); 
		}else if (CarLoanResponses.BACK_LOAN_APPLY.getCode().equals(response)) {
			//退回申请
			//1.添加车借面审信息
			CarApplicationInterviewInfo applicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
			if(applicationInterviewInfo == null){
				carApplicationInterviewInfo.preInsert();
				carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);				
			}else{
				carApplicationInterviewInfo.preUpdate();
				carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
			}
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.FIRST_INSTANCE_BACK_APPLICATION.getCode(), 
					remark,CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			
			//标红置顶退回相关业务
			redTopBack(workItem, flowView); 
			
		}else if (CarLoanResponses.FIRST_AUDIT_CUSTOMER_GIVE_UP.getCode().equals(response)){
			//客户放弃
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.FIRST_INSTANCE_CUSTOMER__GIVE_UP.getCode(), 
					null,CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}
		//1.修改借款信息
		carLoanInfo.preUpdate();
		carLoanInfo.setLoanCode(loanCode);
		carLoanInfo.setDictLoanStatus(flowView.getDictLoanStatus());
		carLoanInfoService.updateCarLoanInfo(carLoanInfo);
	}

	//标红置顶退回相关业务
	private void redTopBack(WorkItemView workItem,
			ReviewMeetFirstAuditBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
					|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
							.equals(flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(CarLoanSteps.FIRST_AUDIT
						.getName());
			}
			flowView.setOrderField("0," + sdf.format(new Date()));
		}
	}

	// 标红置顶提交下一步相关业务
	private void redTopCommit(WorkItemView workItem,
			ReviewMeetFirstAuditBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(
					CarLoanSteps.FIRST_AUDIT.getName(),
					CarLoanResponses.TO_SEC_AUDIT.getCode(),
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