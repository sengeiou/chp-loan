/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoPaperLessDao.java
 * @Create By 王彬彬
 * @Create In 2016年4月18日 下午10:47:56
 */
package com.creditharmony.loan.borrow.contract.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.view.PaperLessView;

/**
 * @Class Name PaperLessDao
 * @author 王彬彬
 * @Create In 2016年4月18日
 */
@LoanBatisDao
public interface PaperLessDao {
	/**
	 * 更新主借人验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @param map
	 */
	public void updateCustomerPinByLoanCode(PaperLessView paperView);
	
	/**
	 * 更新共借人借人验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @param map
	 */
	public void updateCustomerPinById(PaperLessView paperView);
	
	/**
	 * 更新车借共借人验证码
	 * 2016年5月6日
	 * By 葛志超
	 * @param map
	 */
	public void updateCarCustomerPinById(PaperLessView paperView);
	
}
