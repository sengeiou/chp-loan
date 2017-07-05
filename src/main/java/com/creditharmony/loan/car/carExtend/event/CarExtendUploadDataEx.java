package com.creditharmony.loan.car.carExtend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.carExtend.view.CarExtendUploadDataView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;

/**
 * 展期上传资料
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年3月8日
 */
@Service("ex_hj_UploadData_LoanExtendFlow")
public class CarExtendUploadDataEx extends BaseService implements ExEvent {
	
	//借款信息service
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	@Override
	public void invoke(WorkItemView workItemView) {
		CarExtendUploadDataView bv = (CarExtendUploadDataView)workItemView.getBv();
		String response = workItemView.getResponse();
		String applyId = bv.getApplyId();
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		if(CarLoanResponses.CUSTOMER_GIVE_UP.getCode().equals(response)){
			//展期放弃
			//修改展期借款信息
			carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.EXTEND_UPLOADED_CUSTOMER_GIVEUP.getCode(), 
					null,CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		}else if(CarLoanResponses.TO_FRIST_AUDIT.getCode().equals(response)){
			//到初审节点
			//修改展期借款信息
			carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.EXTEND_UPLOADED_TO_FRIST_AUDIT.getCode(), 
					null,CarLoanStatus.PENDING_FIRST_INSTANCE.getCode());
			//标红置顶处理(提交)
			BackOrCommitUtil.redTopCommit(workItemView, bv, CarLoanSteps.UPLOAD_MATERIAL.getName());
		}else if (CarLoanResponses.BACK_ASSESS_ENTER.getCode().equals(response)){
			//返回到评估师录入节点
			//修改展期借款信息
			carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
			String remark = bv.getRemark();
			if(remark != null && "".equals(remark)){
				carLoanInfo.setRemark(remark);
			}
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.UPLOAD_MATERIAL.getCode(), 
					CarLoanOperateResult.EXTEND_UPLOADED_BACK_APPRAISER.getCode(), 
					remark,CarLoanStatus.APPRAISER.getCode());
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItemView, bv, CarLoanSteps.UPLOAD_MATERIAL.getName());
		}
	}

}
