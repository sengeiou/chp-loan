package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitAllEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylEx;

/**
 * 还款拆分dao
 * @Class Name PaybackSplitDao
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface CarPaybackSplitDao extends CrudDao<PaybackSplit> {

	/**
	 * 获取集中划扣已拆分列表数据 2015年12月11日 
	 * @param pageBounds
	 * @param paramMap
	 * @return 拆分列表
	 */
	public List<PaybackSplit> getAllList(PageBounds pageBounds,Map<String, Object> paramMap);

	/**
	 * 修改划扣平台
	 * 2015年12月11日
	 * @param paramMap
	 * @return none
	 */
	public void updatePlat(Map<String, Object> paramMap);

	/**
	 * 批量退回
	 * 2015年12月11日 
	 * @param paramMap
	 * @return none
	 */
	public void backApply(Map<String, Object> paramMap);

	/**
	 * 根据申请id,获取合同编号
	 * 2015年12月11日 
	 * @param id
	 * @return 合同编号
	 */
	public String getContractCode(String id);

	/**
	 * 更改还款主表中的还款状态为还款退回 
	 * 2015年12月11日 
	 * @param contractCode
	 * @return none
	 */
	public void updateStatus(PaybackApply paybackApply);

	/**
	 * 根据拆分表ID获取PaybackDeduct对象 
	 * 2015年12月11日 
	 * @param id
	 * @return 划扣对象
	 */
	public PaybackDeducts getPaybackDeducts(String id);

	/**
	 * 根据还款申请id获取还款主表id
	 *  2015年12月11日 
	 * @param id
	 * @return 还款主表id
	 */
	public String getMainId(String id);

	/**
	 * 根据还款主表Id,查询历史 
	 * 2015年12月11日 
	 * @param pageBounds
	 * @param mainId
	 * @return 操作历史列表
	 */
	public List<PaybackOpe> getAllHirstory(PageBounds pageBounds,String mainId);

	/**
	 * 批量插入拆分数据 
	 * 2015年12月23日
	 * @param splitList
	 * @return 批量处理的数量
	 */
	public int batchInsertSplitData(List<PaybackSplit> splitList);
	
	/**
	 * 更新拆分表的 是否批量 划扣平台 ，回盘结果
	 * 2015年12月24日
	 * @param paybackSplit
	 * @retrun none
	 */
	public void updatePaybackStatus(List<DeductReq> eductReqList);
	
	/**
	 * 集中划扣已拆分列表 导出
	 * 2015年12月26日
	 * @param map
	 * @reutrn 导出列表
	 */
	public List<PaybackSplitFyEx> getPaybackSplitList(PaybackApply apply);
	
	/**
	 * 更新回盘结果，划扣平台，回盘时间
     * 2015年12月28日
	 * @param paybackSplit
	 * @return none
	 */
	public void updateSplitLineStatus(PaybackSplit paybackSplit);
	
   /**
     * 根据拆分表的id查询要保持的划扣记录数据
     * 2015年12月28日
     * @param paybackSplit
     * @return 划扣对象
    */
	public PaybackDeducts queryPaybackDeductsBean(PaybackSplit paybackSplit);
	
	/**
	 * 保存划扣记录表
	 * 2015年12月28日
	 * @param paybackDeducts
	 * @return none
	 */
	public void addPaybackDeducts(PaybackDeducts paybackDeducts);  
	
    /**
      * 查询要划扣数据
     * 2015年12月30日
	 * @param paybackSplit
	 * @return  申请列表
     */
	public List<DeductReq> queryDeductReqList(Map<String,Object> map);
	
	 /**
     * 查询所有的拆分实体
     * 2015年12月30日
     * @param paybackSplit
     * @return 申请列表
     */
	public List<PaybackApply> queryAllList(PaybackSplit paybackSplit);
	
	/**
	 * 集中划扣已拆分列表导出（好易联）
	 * 2015年12月30日
	 * @param map
	 * @return 导出列表
	 */
	public List<PaybackSplitHylEx> getPaybackSplitListHyl(PaybackApply apply);
	/**
     * 查询要拆分的数据
	 * 2015年1月8日
     * @param paybackSplit
     * @return 申请列表
     */
	public List<PaybackApply> queryApplyList(Map<String,Object> map);
	/**
	 * 更新所有拆分过的数据 将数据置为无效
	 * 2015年1月8日
	 * @param paybackSplit
	 * return none
	 */
	public void updatePaybackAllStatus(PaybackSplit paybackSplit);
    /**
     * 更新部分拆分过的数据 将数据置为无效
     * 2015年1月8日
	 * @param paybackSplit
	 * return none
     */
	public void updateSplitStatus(PaybackSplit paybackSplit);

	/**
	 * 
     * 将拆分以后的数据置为线下
     * 2015年1月8日
	 * @param paybackSplit
	 * return none
    */
	public void updateSplitAllStatus(Object paybackSplit);

    /**
	 * 全部导出
	 * 2016年1月8日
	 * @return 列表显示的所有数据
	 */
	public List<PaybackSplitAllEx> exportExcelAll();

	/**
	 * 更新申请表的数据为划扣中
	 * 2016年2月3日
	 * @param deductReqList
	 * return none
	 */
	public void updateApplyStatus(List<PaybackApply> deductReqList);

	 /**
	  * 删除拆分表的数据
	  * 2016年2月3日
	  * @param applyListExel
	  */
	public void deleteSplit(List<PaybackApply> applyListExel);
	
	
	/**
	 *  批量插入划扣结果
	 * 2016年2月2日
	 * @param listPaybackSplit 批量插入的拆分结果
	 * @return 插入数据数量
	 */
	public int batchInsertDeductSplit(Map<String, Object> map);
	
}
