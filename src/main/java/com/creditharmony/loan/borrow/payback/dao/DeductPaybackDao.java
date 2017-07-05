package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.BankRule;
import com.creditharmony.loan.borrow.payback.entity.DeductCondition;
import com.creditharmony.loan.borrow.payback.entity.DeductPayback;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.common.entity.BalanceInfo;

/**
 * 待还款划扣Dao
 * @Class Name DeductPaybackDao
 * @author zhangfeng
 * @Create In 2015年12月29日
 */
@LoanBatisDao
public interface DeductPaybackDao extends CrudDao<DeductPayback>{

	/**
	 * 查询待还款划扣列表
	 * 2016年1月6日
	 * By wengsi
	 * @param map
	 * @return list
	 */
	public List<PaybackSplitFyEx> getDeductPaybackList(PaybackApply apply);
	
    /**
     * 更加导入结果 更新 还款申请表的状态 
	 * @author wengsi
	 * @Create In 2015年12月29日
     * @param deductPayback
     * @return none
     */
	public void updatePaybackStatus(PaybackApply paybackApply);

	/**
	 * 待还款划扣列表：线上划扣
	 * 2015年12月29日
	 * By 李强
	 * @param paybackSplit
	 * @return none
	 */
	public void insertBuckleOnLine(PaybackSplit paybackSplit);
	
	/**
	 * 待还款划扣已办：线上批量划扣后修改申请状态为"已拆分"
	 * 2015年12月30日
	 * By 李强
	 * @param paybackSplits
	 * @return none
	 */
	public void updateRepayApplyStatus(PaybackSplit paybackSplits);

	/**
	 * 更新拆分标的划扣标示  2015年1月6日 By wengsi
	 * @param map
	 * @return none
	 */
	public void updatePaybackSplit(Map<String, String> map);
	
	/**
	 * 更新申请表的申请状态为已拆分  2015年1月6日 By wengsi
	 * @param map
	 * @return none
	 */
	void updatePaybackApply(Map<String, String> map);
	
	/**
	 * 好易联导出方法  2015年1月6日 By wengsi
	 * @param map
	 * @return 导出列表
	 */
	List<PaybackSplitHylExport> getDeductPaybackListHyl(PaybackApply apply);
	
    /**
	 * 根据交易状态  更新还款拆分表 的回盘结果 2015年1月6日 By wengsi
	 * @param split
	 * @return none
	 *
     */
	public void updateSplitResultList(PaybackSplit split);

	/**
	 * 增加蓝补流水
	 * @author wengsi
	 * @Create In 2015年1月14日
	 * @param paybackBuleAmont
	 * @return none
	 */
	public void addBackBuleAmont(PaybackBuleAmont paybackBuleAmont);

	/**
	 * 查询要划扣的记录
	 * @author 翁私
	 * @Create In 2015年2月3日
	 * @param paramMap
	 * @return 划扣记录 
	 */
	public List<DeductReq> queryDeductReqList(Map<String, Object> paramMap);

	/**
	 * 将申请表的数据改为 划扣中 （线上）
	 * 2015年2月3日
	 * By wengsi
	 * @param eductReqList
	 * @return none
	 */
	public int updateApplyStatus(DeductReq deductReq);
	
	/**
	 * 将申请表的数据改为 划扣中 (线下)
	 * 2015年2月3日
	 * By wengsi
	 * @param apply
	 * @return none
	 */
	public void updateApplyStatusOffline(List<PaybackApply> apply);

	 /**
     * 查询要拆分的数据
	 * 2015年1月8日
	 * By wengsi
     * @param map
     * @return 申请列表
     */
	public List<PaybackApply> queryApplyList(Map<String, Object> map);

	 /**
	  * 删除拆分表的数据
	  * 2016年2月4日
	  * By wengsi
	  * @param paybackListExcle
	  * @return none
	  */
	public void deleteSplit(PaybackApply paybackListExcle);

	/**
	  * 删除拆分表的数据
	  * 2016年2月4日
	  * By wengsi
	  * @param deductReqListExcel
	  * @return none
	  */
	public void deleteSplitDeductReq(List<DeductReq> deductReqListExcel);

	/**
	 * 查询导出的数据 把数据整合成划扣实体
	   * 2016年2月17日
	  * By wengsi
	  * @param none
	  * @return List<LoanDeductEntity>
	 */
	public List<LoanDeductEntity> queryLoanDeductList(Map<String,String> map);

	/**
	 * 跳转待还款划扣页面 2016年2月24日 By 翁私
	 * 
	 * @param page
	 * @param paybackApply
	 * @return none
	 */
	public PageList<PaybackApply> findApplyPayback(Map<String, Object> map,
			PageBounds pageBounds);


	/**
	 * 查询中金导出数据 2016年3月1日 By 翁私
	 * 
	 * @param apply
	 * @return list
	 */
	public List<PaybackSplitZjEx> getDeductPaybackListZj(PaybackApply apply);

	/**
	 * 查询通联导出数据 2016年3月2日 By 翁私
	 * 
	 * @param apply
	 * @return list
	 */
	public List<PaybackSplitTlEx> getDeductPaybackListTl(PaybackApply apply);

	/**
	 * 更新委托充值信息
	 * 2016年3月10日
	 * By 王浩
	 * @param trusteeImport
	 * @return 
	 */
	public int updateTrustRecharge(TrusteeImportEx trusteeImport);

	/**
	 * 查询待划款列表数据
	 * 2016年3月13日
	 * By 王浩
	 * @param map
	 * @return 
	 */
	public List<PaybackApply> findApplyPayback(Map<String, Object> map);

	/**
	 * 更新集中划扣申请表
	 *  2016年4月7日 翁私
	 * @param req
	 */
	public int updateApplyHisStatus(DeductReq req);

	/**
	  * 根据关联id 删除拆分表
	  * 2016年2月4日
	  * By wengsi
	  * @param deductReqListExcel
	  * @return none
	  */
	public void deleteDeductReq(DeductReq req);

	/**
	 * 待还款 锁住要跟新的数据  2016年4月28日 by 翁私
	 * @param req
	 * @return req
	 */
	public DeductReq queryForupdate(DeductReq req);

	/**
	 * 集中划扣 锁住要跟新的数据  2016年4月28日 by 翁私
	 * @param req
	 * @return req
	 */
	public DeductReq queryForupdateDeduct(DeductReq req);

	/**
	 * 更新蓝补金额  2016年5月4日 by 翁私
	 * @param split
	 * @return none
	 */
	public void updatePaybackBlue(PaybackSplit split);

	/**
	 * 查询申请表 2016年5月4日 by 翁私
	 * @param split
	 * @return
	 */
	public PaybackApply queryPaybackApply(PaybackSplit split);

	/**
	 * 更新还款申请表2016年5月4日 by 翁私
	 * @param apply
	 * @return
	 */
	public void updatePaybackApplyById(PaybackApply apply);

	/**
	 * 更新申请表的回盘结果 2016年5月4日 by 翁私
	 * @param paybackApply
	 * @return none
	 */
	public void updateApplyStatusSigle(PaybackApply paybackApply);

	/**
	 * 根据申请id和回盘结果删除拆分表
	 * @param apply
	 */
	public void deleteSplitByApply(PaybackApply apply);

	/**
	 * 为该条数据加锁
	 * @param deductReq
	 * @return
	 */
	public PaybackApply queryDeductReq(DeductReq deductReq);

	/**
	 * 查询余额不足是否超过俩次
	 * @param deductReq
	 */
	public BalanceInfo queryBalanceInfo(DeductReq deductReq);

	/**
	 * 查询当前用户的角色
	 * @param userId
	 * @return
	 */
	public int selectRoleCount(String userId);

   /**
    * 查询是否有没有发送的数据
    * @return
    */
	public String queryDeductLimit();
	/**
	    * 查询自动划扣规则
	    * @return
	 */
	public List<BankRule> queryBankRule(Map<String,String> paramMap);

	/**
	 * 查询划扣条件
	 * @return
	 */
	public List<DeductCondition> queryDeductCondition();

	/**
	 * 查询划扣统计信息
	 * @param map
	 * @return
	 */
	public List<DeductStatistics> queryDeductStatistics(Map<String, String> map);

	/**
	 * 如果这条数据的没有符合业务配置条件，则给这个数据数据一个标志
	 * @param req
	 */
	public void updateLimitFlag(DeductReq req);

	}
