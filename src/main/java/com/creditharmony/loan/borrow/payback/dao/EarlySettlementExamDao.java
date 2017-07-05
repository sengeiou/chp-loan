package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;

/**
 * 提前结清待审核列表业务Dao
 * @Class Name EarlySettlementExamDao
 * @author zhaojinping
 * @Create In 2016年1月6日
 */
@LoanBatisDao
public interface EarlySettlementExamDao extends CrudDao<Payback> {
	
	/**
	 * 获取提前结清待审核列表
	 * 2016年1月6日
	 * By zhaojinping
	 * @param pageBounds
	 * @param paybackCharge
	 * @return list
	 */
	public List<PaybackCharge> getAllList(PageBounds pageBounds,PaybackCharge paybackCharge);
	
	/**
	 * 获取该借款人的应还违约金及罚息总额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param contractCode
	 * @return list
	 */
    public List<PaybackMonth> getMonthInfo(String contractCode);
    
   /**
    * 获取该借款人的申请还款总额
    * 2016年1月6日
    * By zhaojinping
    * @param id
    * @return paybackApply
    */
    public PaybackApply getApplyAmount(String id);
    
   /**
    * 根据id更改冲抵申请表中的冲抵申请状态为 “还款退回”
    * 2016年1月13日
    * By zhaojinping
    * @param paybackCharge
    * @return none
    */
    public void updatePaybackChargeStatus(PaybackCharge paybackCharge);
    
    /**
     * 根据id,查询提前结清的申请信息进行审核
     * 2016年1月13日
     * By zhaojinping
     * @param chargeId
     * @return paybackCharge
     */
    public PaybackCharge getEarlyBackApply(PaybackCharge paybackCharge);
   
    /**
     * 获取提前结清的减免金额
     * 2016年1月9日
     * By zhaojinping
     * @param contractCode
     * @return paybackMonth
     */
     public PaybackMonth getPenaltyPunishSum(String contractCode);
     
    /**
     * 更改还款主表中的还款状态
     * 2016年1月6日
     * By zhaojinping
     * @param payback
     * @return none
     */
    public void updatePaybackStatus(Payback payback);
    
}
