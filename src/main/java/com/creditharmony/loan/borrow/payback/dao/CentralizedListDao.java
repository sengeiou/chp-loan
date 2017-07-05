package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 划扣回盘列表
 * @Class Name CentralizedListDao
 * @author 王彬彬
 * @Create In 2016年7月15日
 */
@LoanBatisDao
public interface CentralizedListDao extends CrudDao<CentralizedListDao> {
	/**
	 * 集中划扣已办详细数据 
	 * 2016年7月15日 
	 * By 王彬彬
	 * @param paybackApply
	 * @return
	 */
	public List<PaybackApply> centerDeductionList(PaybackApply paybackApply);
	
	/**
	 * 集中划扣检索件数
	 * 2016年7月15日 
	 * By 王彬彬
	 * @param paybackApply
	 * @return
	 */
	public Integer centerDeductionListCnt(PaybackApply paybackApply);

	/**
	 * 集中划扣
	 * 2016年7月15日
	 * By 王彬彬
	 * @param paybackApply
	 * @return
	 */
	public List<PaybackApply> centerDeductionAgencyList(PaybackApply paybackApply);
	
}
