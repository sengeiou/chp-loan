package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.moneyaccount.entity.MoneyAccountInfo;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitAllEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.common.entity.SystemSetting;

/**
 * 还款拆分dao
 * @Class Name PaybackSplitDao
 * @author zhaojinping
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface PaybackSplitDao extends CrudDao<PaybackSplit> {

	/**
	 * 获取集中划扣已拆分列表数据 2015年12月11日 By zhaojinping
	 * @param pageBounds
	 * @param paramMap
	 * @return 拆分列表
	 */
	public List<PaybackSplit> getAllList(PageBounds pageBounds,Map<String, Object> paramMap);
	
	/**
	 * 获取集中划扣已拆分列表数据非分页
	 * 2016年3月13日
	 * By 朱杰
	 * @param paramMap
	 * @return
	 */
	public List<PaybackSplit> getAllList(Map<String, Object> paramMap);

	/**
	 * 修改划扣平台
	 * 2015年12月11日
	 * By zhaojinping
	 * @param paramMap
	 * @return none
	 */
	public void updatePlat(Map<String, Object> paramMap);

	/**
	 * 批量退回
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param paramMap
	 * @return none
	 */
	public void backApply(Map<String, Object> paramMap);

	/**
	 * 根据申请id,获取合同编号
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param id
	 * @return 合同编号
	 */
	public String getContractCode(String id);

	/**
	 * 更改还款主表中的还款状态为还款退回 
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param contractCode
	 * @return none
	 */
	public void updateStatus(PaybackApply paybackApply);

	/**
	 * 根据拆分表ID获取PaybackDeduct对象 
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param id
	 * @return 划扣对象
	 */
	public PaybackDeducts getPaybackDeducts(String id);

	/**
	 * 根据还款申请id获取还款主表id
	 *  2015年12月11日 
	 *  By zhaojinping
	 * @param id
	 * @return 还款主表id
	 */
	public String getMainId(String id);

	/**
	 * 根据还款主表Id,查询历史 
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param pageBounds
	 * @param mainId
	 * @return 操作历史列表
	 */
	public List<PaybackOpe> getAllHirstory(PageBounds pageBounds,String mainId);

	/**
	 * 批量插入拆分数据 
	 * 2015年12月23日
	 *  By 王彬彬
	 * @param splitList
	 * @return 批量处理的数量
	 */
	public int batchInsertSplitData(List<PaybackSplit> splitList);
	
	/**
	 * 更新拆分表的 是否批量 划扣平台 ，回盘结果
	 * 2015年12月24日
	 * By wengsi
	 * @param paybackSplit
	 * @retrun none
	 */
	public void updatePaybackStatus(List<DeductReq> eductReqList);
	
	/**
	 * 集中划扣已拆分列表 导出
	 * 2015年12月26日
	 * By wengsi
	 * @param map
	 * @reutrn 导出列表
	 */
	public List<PaybackSplitFyEx> getPaybackSplitList(PaybackApply apply);
	
	/**
	 * 更新回盘结果，划扣平台，回盘时间
     * 2015年12月28日
	 * By wengsi
	 * @param paybackSplit
	 * @return none
	 */
	public int updateSplitLineStatus(PaybackSplit paybackSplit);
	
   /**
     * 根据拆分表的id查询要保持的划扣记录数据
     * 2015年12月28日
     * By wengsi
     * @param paybackSplit
     * @return 划扣对象
    */
	public PaybackDeducts queryPaybackDeductsBean(PaybackSplit paybackSplit);
	
	/**
	 * 保存划扣记录表
	 * 2015年12月28日
	 * By wengsi
	 * @param paybackDeducts
	 * @return none
	 */
	public void addPaybackDeducts(PaybackDeducts paybackDeducts);  
	
    /**
      * 查询要划扣数据
     * 2015年12月30日
	 * By wengsi
	 * @param paybackSplit
	 * @return  申请列表
     */
	public List<DeductReq> queryDeductReqList(Map<String,Object> map);
	
	
	/**
	 * 集中划扣已拆分列表导出（好易联）
	 * 2015年12月30日
	 * By wengsi
	 * @param map
	 * @return 导出列表
	 */
	public List<PaybackSplitHylExport> getPaybackSplitListHyl(PaybackApply apply);
	/**
     * 查询要拆分的数据
	 * 2015年1月8日
	 * By wengsi
     * @param paybackSplit
     * @return 申请列表
     */
	public List<PaybackApply> queryApplyList(Map<String,Object> map);
	/**
	 * 更新所有拆分过的数据 将数据置为无效
	 * 2015年1月8日
	 * By wengsi
	 * @param paybackSplit
	 * return none
	 */
	public void updatePaybackAllStatus(PaybackSplit paybackSplit);
    /**
     * 更新部分拆分过的数据 将数据置为无效
     * 2015年1月8日
	 * By wengsi
	 * @param paybackSplit
	 * return none
     */
	public void updateSplitStatus(PaybackSplit paybackSplit);

	/**
	 * 
     * 将拆分以后的数据置为线下
     * 2015年1月8日
	 * By wengsi
	 * @param paybackSplit
	 * return none
    */
	public void updateSplitAllStatus(Object paybackSplit);

    /**
	 * 全部导出
	 * 2016年1月8日
	 * By wengsi
	 * @return 列表显示的所有数据
	 */
	public List<PaybackSplitAllEx> exportExcelAll();
	
	/**
	 *  批量插入划扣结果
	 * 2016年2月2日
	 * By 王彬彬 
	 * @param listPaybackSplit 批量插入的拆分结果
	 * @return 插入数据数量
	 */
	public int batchInsertDeductSplit(List<PaybackSplitEntityEx> listPaybackSplit);

	/**
	 * 更新委托提现信息
	 * 2016年3月10日
	 * By 王浩
	 * @param trusteeImport 委托提现信息封装
	 * @return 更新数据数量
	 */
	public int updateTrustRecharge(TrusteeImportEx trusteeImport);
	
	/**
	 * 获取线上划拨用数据
	 * 2016年3月12日
	 * By 朱杰
	 * @return
	 */
	public MoneyAccountInfo getMoneyAccountInfo(Map<String, Object> param);
	
	/**
	 * 更新划拨状态
	 * 2016年3月12日
	 * By 朱杰
	 * @param param
	 */
	public void updateBackResultByTrust(Map<String, Object> param);


	/**
	 * 更新还待还款归档列表
	 * 2016年4月5日
	 * By 翁私
	 * @param paybackApply
	 * @return  none
	 */
	public void updateHisStatus(PaybackApply paybackApply);

	/**
	 * 发送接口的时候锁住这条数据
	 * @param paybackSplit
	 * @return
	 */
	public PaybackSplit queryPaybackSplit(PaybackSplit paybackSplit);

	/**
	 * 根据企业流水号查询拆分的数据
	 * @param fysplit
	 * @return 拆分实体
	 */
	public PaybackSplit querySplitByno(PaybackSplit split);

	public void updatePaybackBlue(PaybackSplit paybackSplit);

	public List<PaybackSplitFyEx> queryApply();

	/**
	 * 查询申请表的数据 2016年5月3日 By 翁私
	 * @param paybackSplit
	 * @return PaybackApply
	 */
	public PaybackApply queryPaybackApply(PaybackSplit paybackSplit);

	/**
	 * 更新申请表的状态
	 * @param apply
	 */
	public void updatePaybackApply(PaybackApply apply);

	public void updateApplyStatusSigle(PaybackApply paybackApply);

	/**
	 * 查询中金导出的数据
	 * @param returnApply
	 * @return list
	 */
	public List<PaybackSplitZjEx> getDeductPaybackListZj(
			PaybackApply returnApply);

	/**
	 * 查询通联导出数据
	 * @param returnApply
	 * @return list
	 */
	public List<PaybackSplitTlEx> getDeductPaybackListTl(
			PaybackApply returnApply);

	/**
	 * 查询滚动划扣标志
	 * @param sys
	 * @return
	 */
	public SystemSetting getSystemSetting(SystemSetting sys);

	/**
	 * 为这条数据加锁
	 * @param deductReq
	 * @return
	 */
	public PaybackSplit getApply(DeductReq deductReq);

	/**
	 * 查询逾期天数
	 * @param split
	 * @return
	 */
	public PaybackSplit queryOverdueDays(PaybackSplit split);

	public Long getCnt(Map<String, Object> paramMap);

	public PaybackMonth queryPaybackMonth(PaybackSplit split);

	public PaybackSplit queryOverdueCount(PaybackSplit split);

//	public PaybackApply queryApply(String string);
	
}
