package com.creditharmony.loan.car.carGrant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
/**
 * 查询放款已办
 * @Class Name CarGrantDoneService
 * @Create In 2016年2月16日
 */
@Service("carGrantDoneService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarGrantDoneService extends CoreManager<CarLoanGrantDao, CarLoanGrantEx>{
	


	/**
	 * 根据合同编号查询applyId
	 * 2016年2月16日
	 * @param contractCode 合同编号
	 * @return applyId
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String selApplyId(String contractCode){
		return dao.selectCarApplyId(contractCode);
	}
}
