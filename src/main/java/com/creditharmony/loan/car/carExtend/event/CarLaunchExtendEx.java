package com.creditharmony.loan.car.carExtend.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carExtend.view.CarExtendApplyView;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;

@Service("ex_hj_Launch_Car_Extend_Loan_Flow")
public class CarLaunchExtendEx extends BaseService implements ExEvent{
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@Override
	public void invoke(WorkItemView workItemView) {
		CarExtendApplyView bv = (CarExtendApplyView)workItemView.getBv();
		String applyId = bv.getApplyId();
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
			String loanCode = carLoanInfo.getLoanCode();
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		bv.setOrderField("1," + sdf.format(new Date()));
		
		//录入历史信息
		carHistoryService.saveCarLoanStatusHis(loanCode,CarLoanSteps.FACE_AUDIT_APPLY.getCode() , 
			CarLoanOperateResult.TO_APPRAISER.getCode(), null,CarLoanStatus.APPRAISER.getCode());
	}
}
