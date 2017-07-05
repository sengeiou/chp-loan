package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;

/**
 * 划扣更新
 * @Class Name DeductUpdateDao
 * @author 王彬彬
 * @Create In 2016年2月3日
 */
@LoanBatisDao
public interface CarDeductUpdateDao extends CrudDao<LoanDeductEntity> {
	
}
