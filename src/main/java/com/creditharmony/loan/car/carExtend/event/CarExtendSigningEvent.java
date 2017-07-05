package com.creditharmony.loan.car.carExtend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.carExtend.view.CarExtendSigningSubmitView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 展期签署  处理流程回调
 * @Class Name CarSigningCheckEvent
 * @author 张振强
 * @Create In 2016年3月12日
 */
@Service("ex_hj_carExtendSigning_CarLoanFlow")
public class CarExtendSigningEvent implements ExEvent{

	@Autowired
	private CarCustomerBankInfoDao carCustomerBankInfoDao;
	@Autowired
	private CarContractDao carContractDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao; 
	@Autowired
	private CarHistoryService carHistoryService;
	
	@Override
	public String getBeanName() {
		return null;
	}

	/**
	 * 流程转向前  回调   更新业务数据  设置工作流程转向路由
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		// 取出在controller 中放入的业务数据，需要向下转换
		CarExtendSigningSubmitView  flowView = (CarExtendSigningSubmitView)workItem.getBv();
		//更新借款信息表状态
		CarLoanInfo carLoanInfo = new CarLoanInfo();
		carLoanInfo.setLoanCode(flowView.getLoanCode());

		if(CarLoanResponses.EXTEND_GIVE_UP.getCode().equals(workItem.getResponse())){

			carLoanInfo.setDictLoanStatus(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(),
					CarLoanOperateResult.EXTEND_SIGN_CUSTOMER_GIVEUP.getCode(), "", CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		}else if(CarLoanResponses.TO_MAKE_CONTRACT.getCode().equals(workItem.getResponse())){
			
			// 更新预约签署日期
			CarContract carContract = new CarContract();
			carContract.setLoanCode(flowView.getLoanCode());
			carContract.setContractDueDay(flowView.getContractDueDay());
			carContract.setContractFactDay(flowView.getContractDueDay());
			carContractDao.update(carContract);

			// 保存银行卡信息
			CarCustomerBankInfo carCustomerBankInfo = new CarCustomerBankInfo();
			ReflectHandle.copy(flowView, carCustomerBankInfo);
		
			carCustomerBankInfoDao.update(carCustomerBankInfo);
			
			//标红置顶处理(提交)
			BackOrCommitUtil.redTopCommit(workItem, flowView, CarLoanSteps.SIGN.getName());

			carLoanInfo.setDictLoanStatus(CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(), 
					CarLoanOperateResult.EXTEND_SIGN_PASS.getCode(), "", CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
		}else if(CarLoanResponses.TO_FRIST_AUDIT.getCode().equals(workItem.getResponse())){
			//待确认签署退回
			carLoanInfo.setDictLoanStatus(CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
			carLoanInfo.setRemark(flowView.getRemark());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(),
					CarLoanOperateResult.EXTEND_SIGN_BACK.getCode(), "", CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
			
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.SIGN.getName());
		}
		
		carLoanInfoDao.update(carLoanInfo);
		
	}

}
