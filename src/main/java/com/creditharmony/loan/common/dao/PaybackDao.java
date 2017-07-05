/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.dao
 * @Create By zhangfeng
 * @Create In 2015年12月11日 下午1:07:24
 */
package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 还款业务查询列表公共Dao
 * @Class Name PaybackDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface PaybackDao extends CrudDao<PaybackApply>{

	/**
	 * 查询还款申请信息(页面)
	 * 2015年12月3日
	 * By zhangfeng
	 * @param paybackApply
	 * @param pageBounds
	 * @return list
	 */
	public List<PaybackApply> findApplyPayback(PaybackApply paybackApply,PageBounds pageBounds);
	
	/**
	 * 门店待办(页面)
	 * 2015年12月3日
	 * By 于飞
	 * @param paybackApply
	 * @param pageBounds
	 * @return list
	 */
	public List<PaybackApply> findReturnApplyPayback(PaybackApply paybackApply,PageBounds pageBounds);

	/**
	 * 查询还款信息(页面)
	 * 2015年12月11日
	 * By zhangfeng
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public List<Payback> findPayback(Payback payback,PageBounds pageBounds);
	
	/**
	 * 检索还款冲抵列表
	 * 
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public List<Payback> selApplyPaybackUse(Payback payback, PageBounds pageBounds);
	public int selApplyPaybackUseCnt(Payback payback);

	/**
	 * 查询还款申请信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return list
	 */
	public List<PaybackApply> findApplyPayback(PaybackApply paybackApply);
	
	/**
	 * 查询还款申请信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return list
	 */
	public List<PaybackApply> findReturnApplyPayback(PaybackApply paybackApply);
	
	/**
	 * 查询还款信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param payback
	 * @return list
	 */
	public List<Payback> findPayback(Payback payback);
	
	/**
	 * 更新还款状态
	 * 2015年12月8日
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	public void updatePayback(Payback payback);
	
	/**
	 * 更新还款申请状态
	 * 2015年12月8日
	 * By zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	public void updatePaybackApply(PaybackApply paybackApply);
	
	/**
	 * 结清和提前结清更新借款状态
	 * 2015年12月22日
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	public void updateDictLoanStatus(Payback payback);

	/**
	 *新增还款主表信息
	 *2016年1月6日
	 *By zhanghao 
	 *@param payback 
	 *@return  none
	 * 
	 */
	public void insertPayback(Payback payback);
	
	/**
	 *删除还款主表信息 
	 *2016年1月6日
	 *By zhanghao
	 *@param contractCode
	 *@return none
	 * 
	 */
	public void deletePayback(String contractCode);
	
	/**
     *通过客户编号查询还款信息 
     *2016年1月6日
     *By zhanghao
     *@param payback
     *@return List
     * 
     */
	public List<Payback> findByCustomerCode(Payback payback);
	   
    /**
     *通过客户编号查询还款信息 
     *2016年1月6日
     *By zhanghao
     *@param param
     *@return List
     * 
     */
	public List<Payback> findByIdentityCode(Map<String,Object> param);
	/**
	 *通过指定的ContractCode查询还款信息 
	 *@author zhanghao
	 *@create In 2016年03月30日
	 *@param param contractCode 
	 *@return  Payback 
	 */
	public Payback selectpayBack(Map<String,Object> param);
	/**
     *查询客户是否有逾期
     *2016年1月6日
     *By zhanghao
     *@param payback
     *@return List
     * 
     */
	public List<Payback> findApplyByOverdue(Payback payback);

	/**
     *查询客户在门店代办中是否有待还款的POS刷卡记录
     *2016年1月6日
     *By zhanghao
     *@param payback
     *@return List
     * 
     */
	public List<PaybackApply> findApplyByDealt(PaybackApply paybackApply);

	
	/**
     *POS已匹配列表
     *2016年1月6日
     *By zhanghao
     *@param payback
     *@return List
     * 
     */
	public PageList<PaybackApply> findApplyPaybackInfo(
			PaybackApply paybackApply, PageBounds pageBounds);
	
	/**
	 * 查询还款申请信息(详细页面)
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackApply
	 * @return list
	 */
	public List<PaybackApply> findApplyPaybackInfo(PaybackApply paybackApply);

	/**
	 * 电催查询还款信息(页面)
	 * 2016年2月26
	 * By liushikang
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public List<Payback> findPaybackEletr(Payback payback);

	
	
	/**
	 * 电催查询还款申请信息(页面)
	 * 2016年2月25日
	 * By liushikang
	 * @param paybackApply
	 * @param pageBounds
	 * @return list
	 */
	public List<PaybackApply> findApplyElert(PaybackApply paybackApply,PageBounds pageBounds);
	
	/**
	 * 查询电催还款申请信息(页面)
	 * 2015年12月3日
	 * By zhaojunlei
	 * @param paybackApply
	 * @param pageBounds
	 * @return list
	 */
	public PageList<Payback> findElectricPayback(Payback payback,
			PageBounds pageBounds);

	/**
	 * 匹配查账加锁更新申请表
	 * @param pa
	 * @return int
	 */
	public int updatePaybackApplyReq(PaybackApply pa);

	public PaybackApply getApplyPaybackReq(Map<String, String> map);
	
	/**
	 * 通过合同编号查找还款信息
	 * @author 于飞
	 * @Create 2016年10月24日
	 * @param contractCode
	 * @return
	 */
	public Payback findPaybackByContractCode(String contractCode);
	
	/**
	 * 查询还款明细模块相关数据
	 * @author 于飞
	 * @Create 2017年3月1日
	 * @param apply
	 * @return
	 */
	public List<PaybackApply> findApplyPaybackList(PageBounds pageBounds,PaybackApply apply);
	
}
