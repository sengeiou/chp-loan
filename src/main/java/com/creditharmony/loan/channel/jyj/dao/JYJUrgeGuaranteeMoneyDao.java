package com.creditharmony.loan.channel.jyj.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;

@LoanBatisDao
public interface JYJUrgeGuaranteeMoneyDao extends CrudDao<UrgeServicesMoneyEx> {

    /**
     * 催收服务费待催收
     * 2015年12月30日
     * By 张振强
     * @param urgeServicesMoneyEx
     * @param pageBounds
     * @return 催收实体集合
     */
	public List<UrgeServicesMoneyEx> selectGuaranteeMoneyList(PageBounds pageBounds,UrgeServicesMoneyEx urgeServicesMoneyEx);
	
	/**
	 * 查询催收保证金待催收
	 * 2016年3月11日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<UrgeServicesMoneyEx> selectGuaranteeMoneyList(UrgeServicesMoneyEx urgeServicesMoneyEx);
    
	/**
	 * 根据催收服务费id查询失败的拆分数据。
	 * 2015年12月15日
	 * By 张振强
	 * @param paybackSplit
	 * @return 拆分实体集合
	 */
	 public List<PaybackSplit> selectpaybackSplit(PaybackSplit paybackSplit);
	 
	 /**
	 * 更改拆分表的的发送状态为是（"0"）
	 * 2015年12月15日
	 * By 张振强
	 * @param paybackSplit 拆分实体
	 * @return none
	 */
	public void updatepaybackSplit(PaybackSplit paybackSplit);
	
	/**
	 * 查询催收服务费查账匹配列表
	 * 2016年3月1日
	 * By 朱静越
	 * @param pageBounds
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<UrgeServicesMoneyEx> selCheckInfo(PageBounds pageBounds,UrgeServicesMoneyEx urgeServicesMoneyEx);
	
	/**
	 * 查询不带分页的催收服务费查账匹配列表
	 * 2016年5月24日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<UrgeServicesMoneyEx> selCheckInfo(UrgeServicesMoneyEx urgeServicesMoneyEx);

	/**
	 * 
	 * 2016年3月1日
	 * By zhangfeng
	 * @param urgeServicesCheckApply
	 * @return none
	 */
	public void saveUrgeApply(UrgeServicesCheckApply urgeServicesCheckApply);

	/**
	 * 保存催收服务费汇款信息
	 * 2016年3月1日
	 * By zhangfeng
	 * @param urgeServicesMoneyEx
	 */
	public void savePayBackTransferInfo(UrgeServicesMoneyEx urgeServicesMoneyEx);
	
	/**
	 * 查询查账账款列表
	 * 2015年12月2日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 * @return list
	 */
	public List<PaybackTransferInfo> findUrgeTransfer(PaybackTransferInfo paybackTransferInfo);
	
	/**
	 * 查询流水数据
	 * 2015年12月24日
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getAuditedList(PaybackTransferOut payBackTransferOut);
	
	/**
	 * 更新汇款上传列表数据状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 */
	public int updateInfoStatus(PaybackTransferInfo paybackTransferInfo);
	
	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 */
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut);
	
	/**
	 * 更新导入数据查账状态
	 * 2016年1月5日
	 * By zhangfeng
	 * @param paybackTransferOut
	 */
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut);

	/**
	 * 更新催收服务费查账申请表的实际到账金额
	 * 2016年3月4日
	 * By zhangfeng
	 * @param urgeApply
	 */
	public int updateUrgeApply(UrgeServicesCheckApply urgeApply);

	/**
	 * 根据申请ID查询催收主表信息
	 * 2016年3月7日
	 * By zhangfeng
	 * @param urgeApply
	 * @return 
	 */
	public UrgeServicesCheckApply getUrgeApplyById(UrgeServicesCheckApply urgeApply);
	/**
	 * 根据id和状态对info表进行查询，查询出来modify_time作为乐观锁更新使用
	 * 2016年8月20日
	 * By 朱静越
	 * @param map
	 * @return
	 */
	public PaybackTransferInfo getstransferInfoUrge(Map<String, String> map);
	
	/**
	 * 根据申请表的id和查账状态进行查询，查询的时候，查找出来修改时间，进行加锁控制
	 * 2016年8月18日
	 * By 朱静越
	 * @param map
	 * @return
	 */
	public UrgeServicesCheckApply getApplyUrgeReq(Map<String, String> map);
	
	/**
	 * 删除info信息表
	 * 2016年3月11日
	 * By 朱静越
	 * @param paybackTransferInfo
	 */
	public void deletePaybackTransferInfo(PaybackTransferInfo paybackTransferInfo);

	/**
	 * 查询所有未查账的催收服务费 by zhangfeng
	 * @param urgeApply
	 * @return list
	 */
	public List<UrgeServicesCheckApply> findUrgeApplyList(UrgeServicesCheckApply urgeApply);
	
}
