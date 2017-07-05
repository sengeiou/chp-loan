package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;

/**
 * 结清确认业务处理Dao
 * @Class Name ConfirmPaybackDao
 * @author zhangfeng
 * @Create In 2016年1月6日
 */
@LoanBatisDao
public interface ConfirmPaybackDao extends CrudDao<PaybackApply>{
	
	 List<Payback> findConfirm(Payback payback);
	
	 List<Payback> findConfirm(PageBounds pageBounds,Payback payback);
	
	/**
	 * 结清页面查询期供信息
	 * 2015年12月15日
	 * By zhangfeng
	 * @param paybackMonth
	 * @return PaybackMonth
	 */
	public PaybackMonth findPaybackMonth(PaybackMonth paybackMonth);
	
	/**
	 * 获取保存的返款信息记录
	 * 2016年3月2日
	 * By zhaojinping
	 * @param ContractCode
	 * @return
	 */
	public UrgeServicesBackMoney getUrgeBackAmount(String ContractCode);
	
	/**
	 * 查询结清确认时输入的审核意见
	 * 2016年3月3日
	 * By zhaojinping
	 * @param paybackOpe
	 * @return
	 */
	public List<PaybackOpe> getConfirmedRemark (PaybackOpe paybackOpe);
	
}
