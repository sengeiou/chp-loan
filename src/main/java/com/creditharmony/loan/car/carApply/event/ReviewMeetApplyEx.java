package com.creditharmony.loan.car.carApply.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.view.ReviewMeetApplyBusinessView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 面审申请提交业务处理时间
 * @Class Name ReviewMeetApplyEx
 * @author 陈伟东
 * @Create In 2016年2月3日
 */
@Service("ex_hj_reviewMeetApply_CarLoanFlow")
public class ReviewMeetApplyEx implements ExEvent {
	
	//车借借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@Autowired
	CarCustomerConsultationService carCustomerConsultationService;
	
	@Autowired
	CarCustomerBankInfoService carCustomerBankInfoService;
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	// 调用flowService.dispatch(workItemView);做了两件事：
	// ①更新工作流属性之前，走这个方法invoke，可以做你的业务逻辑，比如，插入借款状态变更历史表、更新借款信息表的借款状态
	// ②设置工作流属性的值
	@Override
	public void invoke(WorkItemView workItemView) {
		if (CarLoanResponses.TO_UPLOAD_FILE.getCode().equals(workItemView.getResponse())) {
			//此处处理面审申请提交时保存业务数据到业务表等操作
			ReviewMeetApplyBusinessView bv = (ReviewMeetApplyBusinessView)workItemView.getBv();
			
			//标红置顶处理
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            Object object = workItemView.getFlowProperties();
            if(object != null){
                FlowProperties flowProperties = (FlowProperties)object;
                if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.FACE_AUDIT_APPLY.getName(), 
                        CarLoanResponses.TO_UPLOAD_FILE.getCode(), flowProperties.getFirstBackSourceStep())){
                    bv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
                    bv.setOrderField("1," + sdf.format(new Date()));
                }else{
                    if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
                    		 flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
                        bv.setOrderField("1," + sdf.format(new Date()));
                    }else{
                        bv.setOrderField("0," + sdf.format(new Date()));
                    }
                }
            }
			
			String loanCode = bv.getLoanCode();
//			CarCustomerBankInfo carCustomerBankInfo=new CarCustomerBankInfo();
//			ReflectHandle.copy(bv, carCustomerBankInfo);
//			String id = carCustomerBankInfo.getId();
//			if ("".equals(id) || id == null) {
//				carCustomerBankInfo.setIsNewRecord(false);
//				carCustomerBankInfo.preInsert();
//				carCustomerBankInfo.setTop("1");
//				carCustomerBankInfo.setBankSigningPlatform(DeductPlatType.HYL.getCode());//默认签约平台好易联
//				carCustomerBankInfoService.saveCarCustomerBankInfo(carCustomerBankInfo);
//			}else{
//				carCustomerBankInfoService.upadteCarCustomerBankInfo(carCustomerBankInfo);
//			}
			//添加历史记录CarLoanStatus
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FACE_AUDIT_APPLY.getCode(), CarLoanOperateResult.APPLICATION_TO_UPLOADED.getCode(),
					"",CarLoanStatus.PENDING_UPLOADED_FILE.getCode());
		} else if (CarLoanResponses.CUSTOMER_GIVE_UP.getCode().equals(workItemView.getResponse())) {
			//记录历史
			ReviewMeetApplyBusinessView bv = (ReviewMeetApplyBusinessView)workItemView.getBv();
			String loanCode = bv.getLoanCode();
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FACE_AUDIT_APPLY.getCode(), CarLoanOperateResult.APPLICATION_CUSTOMER_GIVE_UP.getCode(), 
					null,CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			// 更新借款信息表的借款状态
			CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
			carLoanInfo.setDictLoanStatus(bv.getDictLoanStatus());
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
			carCustomerConsultation.setLoanCode(loanCode);
			carCustomerConsultation.setDictOperStatus(bv.getDictOperStatus());
			carCustomerConsultation.preUpdate();
			carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
		}
	}
	@Override
	public String getBeanName() {
		return null;
	}
}
