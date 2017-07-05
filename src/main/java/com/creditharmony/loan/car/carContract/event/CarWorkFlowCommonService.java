package com.creditharmony.loan.car.carContract.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarCheckRateDao;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.util.CarLoanPreUpdateUtil;

/**
 * 工作流service 共用部分 
 * @Class Name CarWorkFlowCommonService
 * @author 申诗阔
 * @Create In 2016年1月28日
 */
@Service
public class CarWorkFlowCommonService {
	@Autowired
	protected CarLoanInfoDao carLoanInfoDao;
	@Autowired
	protected CarContractDao carContractDao;
	@Autowired
	private CarCheckRateDao carCheckRateDao;
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	@Autowired
	private CarLoanGrantDao loanGrantDao; // 放款Dao
	
	/**
	 * 车借--审核费率\制作合同\合同签约上传\合同审核
	 * 2016年2月18日
	 * By 申诗阔
	 * @param flowView 审批所需字段 实体
	 * @param stepName 审批步骤名称
	 * @return Map集合 用于后期相关处理逻辑
	 */
	protected Map<String, String> carContractHandle(CarCheckRateBusinessView flowView, String stepName) {
		String loanCode = flowView.getLoanCode();
		String checkResult = flowView.getCheckResult();
		String operResultName = flowView.getOperResultName();
		String loanStatus = flowView.getDictLoanStatus();
		if (CarLoanResponses.TO_SIGN.getCode().equals(checkResult)) {
			CarCheckRate oldCarCheckRate = carCheckRateDao.selectByLoanCode(loanCode);
			// 获取合同费率信息
			CarCheckRate carCheckRate = flowView.getCarCheckRate();
			if (oldCarCheckRate != null) {
				carCheckRate.setLoanCode(loanCode);
				carCheckRate.preUpdate();
				carCheckRateDao.update(carCheckRate);
			} else {
				carCheckRate.preInsert();
				carCheckRate.setLoanCode(loanCode);
				carCheckRateDao.insertCarCheckRate(carCheckRate);
			}
		}
		
		String centerUser = flowView.getCenterUser();
		if (centerUser != null) {
			// 工具类封装合同信息，用于更改表中中间人的值
			CarContract carContract = CarLoanPreUpdateUtil.UpdateCarContract(loanCode, centerUser);
			carContractDao.update(carContract);
		}
		// 工具类封装借款信息，用于更改表中借款状态的值
		CarLoanInfo carLoanInfo = CarLoanPreUpdateUtil.UpdateCarLoanInfo(loanCode, loanStatus, centerUser);
		carLoanInfoDao.update(carLoanInfo);
		
		// 插入状态变更历史表
		// 通过工具类把借款状态变更历史信息封装
		String backReason = flowView.getBackReason();
		String grossFlag = flowView.getGrossFlag();
		if (grossFlag != null && !"".equals(grossFlag)) {
			backReason = flowView.getGrossFlag();
		}
		// 审批步骤
		String stepNode = CarLoanSteps.parseByName(stepName).getCode();
		CarLoanStatusHis carLoanStatusHis = CarLoanPreUpdateUtil.updateStatusChangeRecord(loanCode, loanStatus, stepNode, operResultName, backReason);
		carLoanStatusHisDao.insert(carLoanStatusHis);

		// 用于封装所需信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("loanCode", loanCode);
		return map;
	}
	
	protected void updateLoanGrantInfo(CarCheckRateBusinessView flowView) {
		String contractNum = flowView.getContractCode();
		CarLoanGrant loanGrant = new CarLoanGrant();
		loanGrant.setContractCode(contractNum);
		CarLoanGrantEx loanGrantEx = loanGrantDao.findGrant(loanGrant);
		if (loanGrantEx == null) {
			String loanCode = flowView.getLoanCode();
			CarCheckRate carCheckRate = carCheckRateDao.selectByLoanCode(loanCode);
			String applyId = flowView.getApplyId();
			loanGrant.setLoanCode(loanCode);
			loanGrant.setEnterpriseSerialno(applyId);
			loanGrant.preInsert();
			loanGrant.setGrantAmount(carCheckRate.getGrantAmount());
			loanGrantDao.insertGrant(loanGrant);
		}
		
	}
}
