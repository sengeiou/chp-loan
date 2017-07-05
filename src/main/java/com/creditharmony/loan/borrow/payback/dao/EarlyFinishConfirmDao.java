package com.creditharmony.loan.borrow.payback.dao;

import java.math.BigDecimal;
import java.util.List;

import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;

/**
 * 风控部提前结清待确认业务Dao
 * @Class Name EarlyFinishConfirmDao
 * @author zhaojinping
 * @Create In 2016年1月6日
 */
@LoanBatisDao
public interface EarlyFinishConfirmDao extends CrudDao<PaybackCharge>{
        
/**
 * 查询提前结清列表页面中的数据
 * 2016年1月6日
 * By zhaojinping
 * @param pageBounds
 * @param payabckCharge
 * @return list
 */
  List<PaybackCharge> findPayback(PageBounds pageBounds,PaybackCharge payabckCharge);
  
  
  List<PaybackCharge> findPayback(PaybackCharge payabckCharge);
  
  /**
   * 查询提前结清的详情页面
   * 2015年12月24日
   * By zhaojinping
   * @param paybackCharge
   * @return paybackCharge
   */
  PaybackCharge findPaybackBy(PaybackCharge paybackCharge);
  
  /**
   *  获取提前结清的期供信息
   * 2015年12月26日
   * By zhaojinping
   * @param payback
   * @return paybackMonth
   */
  PaybackMonth findPaybackMonth(Payback payback);
  
  /**
   * 获取提前结清的当前期及以后所有期期供信息
   * 2015年12月26日
   * By zhaojinping
   * @param payback
   * @return list
   */
  List<PaybackMonth> getAllPaybackMonth(Payback payback);
  
  /**
   * 修改期供表中的提前结清应还违约金
   * 2016年4月7日
   * By zhaojinping
   * @param paybackMonth
   */
  public void updateMonthPenalty(PaybackMonth paybackMonth);
  
  /**
   * 修改期供的实还本金和实还利息 
   * 2015年12月26日
   * By zhaojinping
   * @param paybackMonth
   * @return none
   */
  public void updatePaybackMonth(PaybackMonth paybackMonth);
  
  /**
   * 修改期供的状态
   * 2015年12月26日
   * By zhaojinping
   * @param paybackMonth
   * @return none
   */
  //public void updateMonthStatus(PaybackMonth paybackMonth);
  
  /**
   * 修改冲抵申请表中的冲抵状态为已冲抵 3
   * 2016年1月11日
   * By zhaojinping
   * @param paybackCharge
   * @return none
   */
  public void updateChargeStatus(PaybackCharge paybackCharge);
  
  /**
   * 修改蓝补金额 
   * 2016年1月6日
   * By zhaojinping
   * @param payback
   * @return none
   */
  public void updateBuleAmount(Payback payback);
  
  /**
   * 向蓝补交易明细记录表中插入数据
   * 2016年1月6日
   * By zhaojinping
   * @param paybackBuleAmont
   * @return none
   */
  public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont);
  
  /**
   * 保存提前结清减免金额
   * 2016年1月6日
   * By zhaojinping
   * @param paybackMonth
   * @return none
   */
  public void updateMonthBeforeReductionAmount(PaybackMonth paybackMonth);
  
 /**
  * 保存提前结清应还款总额
  * 2016年1月6日
  * By zhaojinping
  * @param paybackApply
  * @return none
  */
  public void updatePaybackApply(PaybackApply paybackApply);
  
  /**
   * 修改减免金额(输入的减免金额小于或等于应还违约金)
   * 2015年12月25日
   * By zhaojinping
   * @param paybackMonth
   * @return none
   */
  public void updateMonthPenaltyReduction(PaybackMonth paybackMonth);
  
  /**
   * 修改减免金额(输入的减免金额大于应还违约金)
   * 2015年12月25日
   * By zhaojinping
   * @param paybackMonth
   * @return none
   */
  public void updateMonthPunishReduction(PaybackMonth paybackMonth);
  
  /**
   * 将多余的减免金额更新到蓝补中
   * 2015年12月25日
   * By zhaojinping
   * @param payback
   * @return none
   */
  public void updatePaybackBuleAmount(Payback payback);
  
  /**
   * 保存返款信息表，向催收服务费返款信息表中添加记录
   * 2016年1月7日
   * By zhaojinping
   * @param urgeServicesBackmoney
   * @return none
   */
  public void insertBackAmount(UrgeServicesBackMoney urgeServicesBackmoney);
  
  /**
   * 提前结清发送数据到财富
   * 2016年1月18日
   * By zhaojinping
   * @param id
   * @return syncClaim
   */
  public  List<SyncClaim> tranInfoCf(String id);
  
  /**
   * 提前结清更新标识为p2p的，t_jk_big_finance 的credit_type状态为3
   */
  public boolean updateBigFinance(FinancialBusiness  financialBusiness);
  
  /**
   * 更新借款表提前结清时间状态
   * 2016年7月5日
   * By 王彬彬
   * @param paramMap
   */
  public void updateLoanInfoSettle(LoanInfo loaninfo);
  
  /**
   * 根据合同编号查找对应的客户信息
   * @author 于飞
   * @Create 2016年10月10日
   * @param contractCode
   * @return
   */
  public Customer findCustomerByContractCode(String contractCode);
  
  /**
	 * 查询剩余未还本金
	 * @author 于飞
	 * @Create 2016年11月23日
	 * @param contractCode
	 * @return
	 */
	public BigDecimal findResiduePayactual(String contractCode);
	
	/**
	 * 判断该合同编号是否是财富需要推送给金信的数据
	 * @author 于飞
	 * @Create 2017年1月11日
	 * @param contractCode
	 * @return
	 */
	public String findDataByContractCode(String contractCode);
	
	/**
	 * 更新推送标识
	 * @author 于飞
	 * @Create 2017年1月13日
	 * @param loanInfo
	 */
	public void updateSendFlag(LoanInfo loanInfo);
}
