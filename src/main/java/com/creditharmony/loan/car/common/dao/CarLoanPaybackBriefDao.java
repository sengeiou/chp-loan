package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carContract.ex.CarLoanPaybackBrief;

@LoanBatisDao
public interface CarLoanPaybackBriefDao extends CrudDao<CarLoanPaybackBrief>{
	

	public int insertPaybackBrief(CarLoanPaybackBrief carLoanPaybackDetail);

	public void deletePaybackBrief(String loanCode);
    
	
	
}
