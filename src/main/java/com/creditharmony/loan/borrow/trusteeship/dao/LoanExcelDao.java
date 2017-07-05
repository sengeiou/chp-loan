package com.creditharmony.loan.borrow.trusteeship.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel1;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel2;

@LoanBatisDao
public interface LoanExcelDao extends CrudDao<GrantExcel1> {
	
	public List<GrantExcel1> getDataRows(Map<String, Object> map,
			PageBounds page);

	public List<GrantExcel1> getDataRows(Map<String, Object> map);
	
	public List<GrantExcel2> getDataRows2(Map<String, Object> map);
	
	public HashMap<String, Object> getLoanInfo(String id);
	
	public void updateTrustCash(String loanCode, String trustCashMoney);
}
