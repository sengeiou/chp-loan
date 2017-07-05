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
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.carExtend.view.CarExtendAppraiserView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 展期评估师录入
 * @Class Name CarExtendAppraiserEx
 * @author ganquan
 * @Create In 2016年3月7日
 */
@Service("ex_hj_Appraiser_LoanExtendFlow")
public class CarExtendAppraiserEx extends BaseService implements ExEvent {
	// 车辆详细信息service
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	//借款信息service
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
		
	@Override
	public void invoke(WorkItemView workItemView) {
		CarExtendAppraiserView bv = (CarExtendAppraiserView)workItemView.getBv();
		String response = workItemView.getResponse();
		String applyId = bv.getApplyId();
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		//得到车借借款code
		String loanCode = carLoanInfo.getLoanCode();
		//得到展期借款code
		String loanRawCode = carLoanInfo.getLoanRawcode();
		if(CarLoanResponses.CUSTOMER_GIVE_UP.getCode().equals(response)){//客户放弃
			//修改借款信息借款状态
			if(loanRawCode != null){
				carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
				carLoanInfo.preUpdate();
				carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			}
			//添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode(),
					CarLoanOperateResult.EXTEND_APPRAISER_CUSTOMER_GIVEUP.getCode(), 
					null,CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		} else {//到展期上传资料节点
			//得到车借时的车辆信息
			CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanRawCode);
			if (carVehicleInfo == null) {
				carVehicleInfo = new CarVehicleInfo();
			}
			ReflectHandle.copy(bv, carVehicleInfo);
			//添加展期是的车辆信息
			CarVehicleInfo carVehicleInfo2 = carVehicleInfoService.selectCarVehicleInfo(loanCode);
			carVehicleInfo.setLoanCode(loanCode);
			if(carVehicleInfo2 == null){
				carVehicleInfo.preInsert();
				carVehicleInfoService.savecarVehicleInfo(carVehicleInfo);
			}else{
				carVehicleInfo.preUpdate();
				carVehicleInfoService.update(carVehicleInfo);
			}
			//车借借款信息
			carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//标红置顶处理
			BackOrCommitUtil.redTopCommit(workItemView, bv,CarLoanSteps.APPRAISER.getName());
			//添加历史记录
			if(carVehicleInfo.getLoanCode() != null){
				carHistoryService.saveCarLoanStatusHis(carVehicleInfo.getLoanCode(),CarLoanSteps.APPRAISER.getCode() , 
						CarLoanOperateResult.EXTEND_APPRAISER_UPLOADED_FILE.getCode(), null,CarLoanStatus.PENDING_UPLOADED_FILE.getCode());
			}
		}
		
	}

}
