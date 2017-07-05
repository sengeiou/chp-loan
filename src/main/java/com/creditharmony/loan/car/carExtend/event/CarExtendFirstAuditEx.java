package com.creditharmony.loan.car.carExtend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.carExtend.view.CarExtendFirstAuditView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarAuditResultDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;

/**
 * 展期初审
 * @Class Name CarExtendFirstAuditEx
 * @author ganquan
 * @Create In 2016年3月9日
 */
@Service("ex_hj_FirstAudit_LoanExtendFlow")
public class CarExtendFirstAuditEx extends BaseService implements ExEvent {
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	//客户信息
	@Autowired
	private CarCustomerService carCustomerService;
		
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
		
	//车借面审信息
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
		
	//车借审核结果
	@Autowired
	private CarAuditResultDao carAuditResultDao;
	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		String response = workItem.getResponse();
		// 取出在controller 中放入的业务数据，需要向下转换
		CarExtendFirstAuditView bv = (CarExtendFirstAuditView) workItem.getBv();
		//得到借款编码
		String applyId = bv.getApplyId();
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		//1.修改借款信息
		carLoanInfo.setDictLoanStatus(bv.getApplyStatusCode());
		carLoanInfo.preUpdate();
		carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		//2.添加审核结果
		CarAuditResult carAuditResult = bv.getCarAuditResult();
		if (carAuditResult == null) {
			carAuditResult = new CarAuditResult();
		}
		carAuditResult.setDictCheckType(CarLoanSteps.FIRST_AUDIT.getCode());
		carAuditResult.preInsert();
		carAuditResult.setLoanCode(loanCode);
		if(CarLoanResponses.TO_SEC_AUDIT.getCode().equals(response)){
			carAuditResult.setDictProductType(bv.getAuditBorrowProductCode());
			carAuditResult.setDictAuditMonths(bv.getAuditLoanMonths() + "");
		} else {
			carAuditResult.setDictProductType(null);
			carAuditResult.setDictAuditMonths(null);
			carAuditResult.setAuditAmount(null);
			carAuditResult.setGrossRate(null);
			carAuditResult.setFirstServiceTariffing(null);
		}
		
		if(CarLoanResponses.TO_SEC_AUDIT.getCode().equals(response)){
			//初审通过
			//1.添加车借面审信息
			CarApplicationInterviewInfo carApplicationInterviewInfo = bv.getCarApplicationInterviewInfo();
			carApplicationInterviewInfo.setLoanCode(loanCode);
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
					CarLoanOperateResult.EXTEND_FIRST_INSTANCE_PASS.getCode(), 
					"展期初审通过",CarLoanStatus.PENDING_REVIEW.getCode());
			
			//标红置顶处理(提交)
			BackOrCommitUtil.redTopCommit(workItem, bv, CarLoanSteps.FIRST_AUDIT.getName());
			
		}else if(CarLoanResponses.BACK_ASSESS_ENTER.getCode().equals(response)){
			//退回评估师录入
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.EXTEND_FIRST_INSTANCE_BACK_APPRAISER.getCode(),
					bv.getRemark(),CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItem, bv, CarLoanSteps.FIRST_AUDIT.getName());
			
		}else if (CarLoanResponses.BACK_UPLOAD_FILE.getCode().equals(response)) {
			//退回上传资料
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.EXTEND_FIRST_INSTANCE_BACK_UPLOADED.getCode(), 
					bv.getRemark(),CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItem, bv, CarLoanSteps.FIRST_AUDIT.getName());
		}else if (CarLoanResponses.FIRST_AUDIT_CUSTOMER_GIVE_UP.getCode().equals(response)){
			//展期放弃
			bv.setAuditResult(CarLoanResult.CUSTOMER_GIVE_UP.getCode());
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(), 
					CarLoanOperateResult.EXTEND_FIRST_INSTANCE_CUSTOMER_GIVEUP.getCode(), 
					"",CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		}else if (CarLoanResponses.FIRST_AUDIT_REFUSED.getCode().equals(response)) {
			//展期拒绝
			//1.添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.FIRST_AUDIT.getCode(),
					CarLoanOperateResult.EXTEND_FIRST_INSTANCE_REFUSED.getCode(), 
					"",CarLoanStatus.FIRST_INSTANCE_REJECT.getCode());
		}
		carAuditResult.setAuditResult(bv.getAuditResult());
		carAuditResultDao.insert(carAuditResult);
	}

}
