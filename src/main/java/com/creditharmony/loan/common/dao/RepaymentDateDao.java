package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.GlBill;
@LoanBatisDao
public interface RepaymentDateDao extends CrudDao<Map<String,String>> {
	public List<GlBill> getRepaymentDate();
}
