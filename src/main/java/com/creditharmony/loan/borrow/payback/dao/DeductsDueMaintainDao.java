package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.BankFlatMaintain;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeductsDue;

/**
 * 预约银行及时间维护列表业务Dao
 * @Class Name DeductsDueMaintainDao
 * @author zhaojinping
 * @Create In 2015年12月16日
 */
@LoanBatisDao
public interface DeductsDueMaintainDao extends CrudDao<PaybackDeductsDue> {
	
	/**
	 * 获取预约划扣列表
	 * 2015年12月12日
	 * @param pageBounds
	 * @param pabackDeductsDue
	 * By zhaojinping
	 * @return list
	 */
	public List<BankFlatMaintain> getDeductsDue(PageBounds pageBounds,BankFlatMaintain pabackDeductsDue);
	
	/**
	 * 将划扣方式由实时改为批量
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return none
	 */
	public void realBatch(String id);
	
	/**
	 * 将划扣方式由批量改为实时
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return none
	 */
	public void batchReal(String id);

	/**
	 * 批量更新
	 * 2015年3月8日
	 * By wengsi
	 * @param id
	 * @return none
	 */
	public void batchUpdate(BankFlatMaintain bank);
}
