package com.creditharmony.loan.car.carApply.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carApply.view.UploadView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 上传资料业务处理
 * @Class Name CarUploadDataEx
 * @author ganquan
 * @Create In 2016年2月19日
 */
@Service("ex_hj_UploadData_CarLoanFlow")
public class CarUploadDataEx extends BaseService implements ExEvent {

	//车借借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	// 客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
	
	@Override
	public void invoke(WorkItemView workItem) {
		UploadView bv = (UploadView)workItem.getBv();
		String loanCode = bv.getLoanCode();
		CarLoanInfo carLoanInfo = new CarLoanInfo();
		String response = workItem.getResponse();
		String remark = bv.getRemark();
		if(CarLoanResponses.TO_FRIST_AUDIT.getCode().equals(response)){
			//走初审节点的数据库操作
			ReflectHandle.copy(bv, carLoanInfo);
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//记录历史 
			carHistoryService.saveCarLoanStatusHis(loanCode,CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.UPLOADED_TO_FIRST_INSTANCE.getCode(), 
					null,CarLoanStatus.PENDING_APPLICATION.getCode());
			
		   //标红置顶提交下一步相关业务
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
           Object object = workItem.getFlowProperties();
           if(object != null){
                FlowProperties flowProperties = (FlowProperties)object;
                if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.UPLOAD_MATERIAL.getName(), 
                        CarLoanResponses.TO_FRIST_AUDIT.getCode(), flowProperties.getFirstBackSourceStep())){
                    bv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
                    bv.setOrderField("1," + sdf.format(new Date()));
                }else{
                    if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
                        bv.setOrderField("1," + sdf.format(new Date()));
                    }else{
                        bv.setOrderField("0," + sdf.format(new Date()));
                    }
                }
           }
		}else if(CarLoanResponses.CUSTOMER_GIVE_UP.getCode().equals(response)){
			//走上传资料客户放弃数据库操作
			ReflectHandle.copy(bv, carLoanInfo);
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//记录历史
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.UPLOADED_CUSTOMER_GIVE_UP.getCode(), 
					null,CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}else if(CarLoanResponses.BACK_ASSESS_ENTER.getCode().equals(response)){
			//退回评估师录入
			ReflectHandle.copy(bv, carLoanInfo);
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			
			//退回后咨询状态变更为继续追踪	修改客户咨询信息	开始
			if(loanCode != null){
				CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
				carCustomerConsultation.setLoanCode(loanCode);
				carCustomerConsultation.setDictOperStatus(bv.getDictOperStatus());
				carCustomerConsultation.preUpdate();
				carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
			}
			//退回后咨询状态变更为继续追踪	修改客户咨询信息	结束
			//记录历史
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.UPLOADED_BACK_ASSESS_ENTER.getCode(), 
					remark,CarLoanStatus.CONTINUE_TRACKING.getCode());
			
		   backRedTop(workItem, bv);
			 
			
		}else if(CarLoanResponses.BACK_LOAN_APPLY.getCode().equals(response)){
			//退回车借申请
			ReflectHandle.copy(bv, carLoanInfo);
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//记录历史
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.UPLOADED_BACK_APPLICATION.getCode(), 
					remark,CarLoanStatus.PENDING_APPLICATION.getCode());
			
			backRedTop(workItem, bv);
		}
	}

	//标红置顶退回相关业务
	private void backRedTop(WorkItemView workItem, UploadView bv) {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
       Object object = workItem.getFlowProperties();
       if(object != null){
           FlowProperties flowProperties = (FlowProperties)object;
           if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
        		   flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
        	   bv.setFirstBackSourceStep(CarLoanSteps.UPLOAD_MATERIAL.getName());
		   }
           bv.setOrderField("0," + sdf.format(new Date()));
       }
	}

}
