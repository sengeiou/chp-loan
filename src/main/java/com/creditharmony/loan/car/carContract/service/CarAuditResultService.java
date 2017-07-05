package com.creditharmony.loan.car.carContract.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarAuditResultDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;

/**
 * 审批记录
 * @Class Name CarAuditResultService
 * @author 申诗阔
 * @Create In 2016年2月18日
 */
@Service
public class CarAuditResultService extends CoreManager<CarAuditResultDao, CarAuditResult> {
   
	@Autowired
	private CarAuditResultDao carAuditResultDao;
	
	/**
	 * 根据借款编号获取 最终终审 通过的记录
	 * 2016年2月18日
	 * By 申诗阔
	 * @param loanCode
	 * @return 最终终审 通过的记录
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public CarAuditResult getLastThroughRecord(String loanCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanCode", loanCode);
		map.put("checkType", CarLoanSteps.FINAL_AUDIT.getCode());
		List<String> codes = new ArrayList<String>();
		codes.add(CarLoanResult.THROUGH.getCode());
		codes.add(CarLoanResult.CONDITIONAL_THROUGH.getCode());
		map.put("throughCodes", codes);
		return carAuditResultDao.getLastThroughRecord(map);
	}
	
}
