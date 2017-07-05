package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackList;
import com.creditharmony.loan.borrow.payback.entity.ex.CenterDeductEx;
import com.creditharmony.loan.common.entity.LoanBank;

/**
 * 集中划扣申请dao
 * @Class Name CenterDeductDao
 * @author zhaojinping
 * @Create In 2015年12月17日
 */
@LoanBatisDao
public interface CenterDeductDao extends CrudDao<PaybackList> {
	
	/**
	 * 获取集中划扣申请列表
	 * 2015年12月11日
	 * By zhaojinping
	 * @param pageBounds
	 * @param paramMap
	 * @return 借款列表
	 */
	public List<PaybackList> getAllList(PageBounds pageBounds,Map<String,Object> paramMap);
	
	/**
	 * 获取客户的银行卡信息
	 * 2015年12月11日
	 * By zhaojinping
	 * @param contractCode
	 * @return 借款信息
	 */
    public List<LoanBank> getUserCardInfo(String contractCode);
    
    /**
     * 置顶借款人的银行卡信息
     * 2015年12月11日
     * By zhaojinping
     * @param id
     * @return none
     */
    public void updateCardInfo(String id);
    
    
    /**
     * 将PayBackApply对象插入到还款申请表中
     * 2015年12月11日
     * By zhaojinping
     * @param payBackApply
     * @return none
     */
    public void addCenterApply(DeductsPaybackApply payBackApply);
    
    /**
     * 修改待还款列表中的状态字段为'已申请'
     * 2015年12月11日
     * By zhaojinping
     * @param apply
     * @return none
     */
    public void updateState(DeductsPaybackApply apply);
    
	/**
	 * 保存还款还款历史明细
	 * 2015年12月23日
	 * By wengsi
	 * @param paybackHis 
	 * @return none
	 */
	public void addPaybackHis(PaybackHis paybackHis);
	
	
    /**
     * 导出集中划扣申请
     * 2015年12月25日
     * By wengsi
     * @param centerDeduct
     * @return 导出列表
     */
	public List<CenterDeductEx> getCenterDeductList(Map<String,Object> map);

	/**
	 * 查询要申请的数据
	 * 2016年3月25日
	 * By 翁私
	 * @param paramMap
	 * @return 申请的数据
	 */
	public List<DeductsPaybackApply> queryCenterApply(Map<String, Object> paramMap);

	/**
	 * 查询还款_待还款归档列表
	 * 2016年3月25日
	 * By 翁私
	 * @param apply
	 * @return paybackApply
	 */
	public PaybackApply queryListHis(DeductsPaybackApply apply);

	/**
	 * 插入还款_待还款归档列表
	 * 2016年3月25日
	 * By 翁私
	 * @param apply
	 * @return none
	 */
	public void insertListHis(PaybackApply apply);

	/**
	 * 插入还款_待还款归档列表
	 * 2016年4月5日
	 * By 翁私
	 * @param pay
	 * @return none
	 */
	public void updateListHis(PaybackApply pay);

	/**
	 * 将置顶改为0
	 * @param id
	 */
	public void updateCardTop(String id);

}
