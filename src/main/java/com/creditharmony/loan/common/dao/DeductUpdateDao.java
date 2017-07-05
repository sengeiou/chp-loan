package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.channel.jyj.entity.JyjUrgeServicesMoneyEx;
import com.creditharmony.loan.common.entity.BalanceInfo;
import com.creditharmony.loan.common.entity.KinnobuBalanceIns;

/**
 * 划扣更新
 * @Class Name DeductUpdateDao
 * @author 王彬彬
 * @Create In 2016年2月3日
 */
@LoanBatisDao
public interface DeductUpdateDao extends CrudDao<LoanDeductEntity> {

	/**
	 * 批量插入划扣结果 2016年2月2日 By 王彬彬
	 * 
	 * @param listPaybackSplit
	 *            批量插入的拆分结果
	 * @return 插入数据数量
	 */
	public int batchInsertDeductSplit(
			List<PaybackSplitEntityEx> listPaybackSplit);

	/**
	 * 更新还款申请表 2016年2月3日 By 王彬彬
	 * 
	 * @param paybackApply
	 *            还款申请更新信息
	 */
	public void updatePaybackApply(PaybackApply paybackApply);

	/**
	 * 更新还款主表蓝补金额 2016年2月3日 By 王彬彬
	 * 
	 * @param payback
	 *            还款申请更新信息
	 */
	public void updateBuleAmount(Payback payback);
	
	/**
	 * 更新还款主表蓝补金额 2016年2月3日 By 于飞
	 * 
	 * @param payback
	 *            还款申请更新信息
	 */
	public void updateBuleAmountByContractCode(Payback payback);

	/**
	 * 更新还款主表详细信息 2016年2月16日 By 王彬彬
	 * 
	 * @param payback
	 */
	public void updatePayBack(Payback payback);

	/**
	 * 更新还款主表 2016年2月3日 By 王彬彬
	 * 
	 * @param mapParam
	 *            还款申请更新信息
	 */
	public List<Payback> getPayback(Map<String, String> mapParam);

	/**
	 * 获取期供表数据（最早一期未还） 2016年2月15日 By 王彬彬
	 * 
	 * @param mapParam
	 *            查询条件（合同编号，期供状态数组）
	 * @return
	 */
	public PaybackMonth getNotPayBack(Map<String, String> mapParam);

	/**
	 * 获取对应期供信息 2016年2月15日 By 王彬彬
	 * 
	 * @param mapParam
	 *            查询条件（期供，合同编号）
	 * @return 期供信息
	 */
	public PaybackMonth getPayBackByMonth(Map<String, Object> mapParam);

	/**
	 * 更新期供信息 2016年2月15日 By 王彬彬
	 * 
	 * @param paybackMonth
	 */
	public void updatePaybackMonth(PaybackMonth paybackMonth);

	/**
	 * 根据合同code查询当期期供表 2016年2月15日 By 王彬彬
	 * 
	 * @param map
	 *            查询条件（合同编号）
	 * @return 期供数据
	 */
	public PaybackMonth getPaybackMonth(Map<String, String> map);

	/**
	 * 保存还款还款历史明细 2016年2月15日 By 王彬彬
	 * 
	 * @param paybackHis
	 * @return none
	 */
	public void addPaybackHis(PaybackHis paybackHis);

	/**
	 * 保存蓝补信息 22016年2月15日 By 王彬彬
	 * 
	 * @param paybackBuleAmont
	 * @return none
	 */
	public void addBackBuleAmont(PaybackBuleAmont paybackBuleAmont);

	/**
	 * 更借款信息表的借款状态 2016年2月15日 By wengsi
	 * 
	 * @param loanInfo
	 *            借款信息实体
	 * @return none
	 */
	public void updateLoanInfo(LoanInfo loanInfo);

	/**
	 * 催收服务费2016年2月24日 By 翁私
	 * 
	 * @param iteratorSplit
	 * @return none
	 */
	public void updataUrgeServices(UrgeServicesMoney urgeServicesMoney);

	/**
	 * 查询2016年3月4日 By 翁私
	 * 
	 * @param iteratorSplit
	 * @return none
	 */
	public List<Payback> queryPayback();

	/**
	 * 催收服务费催收列表 2016年3月6日 By 王彬彬
	 * 
	 * @param urgeMoney
	 *            查询条件
	 * @return
	 */
	public List<DeductReq> selSendList(UrgeServicesMoney urgeMoney);

	/**
	 * 批量插入操作历史记录 2016年3月9日 By wengsi
	 * 
	 * @param hisData
	 *            批量插入的历史记录
	 * @return none
	 */
	public void batchInsertHis(List<PaybackOpe> hisData);

	/**
	 * 更新还款申请表 2016年2月16日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	public void updateDeductsPaybackApply(PaybackApply paybackApply);

	/**
	 * 更新待还款归档列表 2016年4月6日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	public void updatePaybackListHis(PaybackApply paybackApply);
	
	/**
	 * 更新待还款归档列表根据集中划扣归档表
	 * @author 于飞
	 * @Create 2016年11月22日
	 * @param paybackApply
	 */
	public void updatePaybackListHisByApplyHis(PaybackApply paybackApply);

	/**
	 * 插入 还款_集中划扣还款申请归档表  2016年4月7日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	public void insertDeductsPaybackApplyHis(PaybackApply paybackApply);

	/**
	 *  2016年4月12日 By 翁私
	 * 
	 * @param deducts
	 *  增加划扣记录
	 * @return none
	 */
	public void addPaybackDeducts(PaybackDeducts deducts);
	

	/**
	 * 插入操作历史记录 2016年3月9日 By 翁私
	 * 
	 * @param ope
	 *            插入的历史记录
	 * @return none
	 */
	public void singleInsertHis(PaybackOpe ope);
	
	/** 
	 * 删除集中划扣数据 2016年4月24日 By 翁私
	 * @param paybackApply 
	 * @return none
	 */
	public void deleteDeductsPaybackApply(PaybackApply paybackApply);

	/**
	 * 查询集中划扣申请 2016年5月23日 By 翁私
	 * @param paybackApply
	 * @return PaybackApply
	 */
	public PaybackApply queryPaybackApply(PaybackApply paybackApply);

	/**
	 * 更新集中划扣日志表  2016年5月23日 By 翁私
	 * @param paybackApply
	 * @return none
	 */
	public void updateDeductsPaybackApplyHis(PaybackApply paybackApply);

	/**
	 * 2016年6月3日
	 * By 尚军伟
	 * @param payback 
	 */
	public void updateHbBuleAmount(Payback payback);

	/**
	 * 2016年6月13日
	 * by 翁私
	 * @return list
	 */
	public List<KinnobuBalanceIns> queryKinBank();

	/**
	 * 2016年6月21日
	 * 保存中金月不足的账户
	 * by 翁私
	 * @return list
	 */
	public void saveBalanceInfo(BalanceInfo info);
	
	/**
	 * 根据集中划扣申请id获取的期供id和申请金额
	 * @author 于飞
	 * @Create 2016年9月27日
	 * @param apply
	 * @return
	 */
	public DeductsPaybackApply getDeductsPaybackApply(DeductsPaybackApply apply);
	
	/**
	 * 根据非集中划扣申请id获取的期供id和申请金额
	 * @author 于飞
	 * @Create 2016年9月27日
	 * @param apply
	 * @return
	 */
	public PaybackApply getPaybackApply(PaybackApply apply);

	/**
	 * 简易借查询查账是否成功
	 * @author 翁私
	 * @Create 2017年5月6日
	 * @param apply
	 * @return
	 */	
	public List<JyjUrgeServicesMoneyEx> queryJyjData(JyjUrgeServicesMoneyEx jyj);

}
