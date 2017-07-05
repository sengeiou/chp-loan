package com.creditharmony.loan.common.dao;
/**
 * 
 */
import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.common.entity.EmailOpe;
import com.creditharmony.loan.common.entity.LoanStatusHis;
@LoanBatisDao
public interface LoanStatusHisDao extends CrudDao<LoanStatusHis>{
	/**
	 * 插入整条记录 
	 * @param record 插入实体
	 * @return 
	 */
    int insertSelective(LoanStatusHis record);
    
    /**
     * 分页查询操作历史数据列表
     * @param loanStatusHis 查询条件，applyId及分页信息
     * @return
     */
    PageList<LoanStatusHis> findPage(LoanStatusHis loanStatusHis);
    
    /**
     * 根据loancode查询历史信息
     * 2016年3月8日
     * By 王彬彬
     * @param loanStatusHis
     * @return 历史信息
     */
    PageList<LoanStatusHis> findHisPageByLoanCode(LoanStatusHis loanStatusHis); 
    
    
    List<LoanStatusHis> findByLoanCodeAndStatus(LoanStatusHis loanStatusHis);
    /**
     *查询汇成最后最近一个审核节点 
     *param key dict_sys_flag 系统标示 applyId 流程ID
     */
    public List<LoanStatusHis> findLastApproveNote(Map<String,Object> param);
   
    /**
     *通过指定的loanCode 跟节点名字找到指定的历史记录 
     *@author zhanghao
     *@create In 2016年03月14日
     *@param  loanStatusHis 
     *@return List<LoanStatusHis>
     */
    public List<LoanStatusHis> findWantedNoteByLoanCode(LoanStatusHis loanStatusHis);
    
    /**
     *通过指定的loanCode 跟节点名字找到指定的历史记录 
     *@author zhanghao
     *@create In 2016年03月14日
     *@param  loanStatusHis 
     *@return List<LoanStatusHis>
     */
    public List<LoanStatusHis> findWantedNoteByLoanCode2(LoanStatusHis loanStatusHis); 
    /**
     *通过指定的applyId 跟节点名字找到指定的历史记录 
     *@author zhanghao
     *@create In 2016年03月14日
     *@param  loanStatusHis 
     *@return List<LoanStatusHis>
     */
    public List<LoanStatusHis> findWantedNoteByApplyId(LoanStatusHis loanStatusHis); 
    /**
	 * 插入还款操作流水记录
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackOpe
	 * @return none
	 */
	public void insertPaybackOpe(PaybackOpe paybackOpe);
	
	/**
	 * 获取还款操作流水记录
	 * 2016年2月18日
	 * By 王彬彬
	 * @param mapParam
	 * @return 操作记录
	 */
	public List<PaybackOpe> getPaybackOpe(Map<String,String> mapParam,
			PageBounds pageBounds);

	/**
	 * 查询拆分记录 2016年2月21日
	 * @param filter
	 * @param pageBounds
	 * @return 操作记录
	 */
	public List<PaybackSplit> getPaybackSplit(Map<String, String> filter,
			PageBounds pageBounds);

	/**
	 * 退款操作历史
	 */
	public List<PaybackHistory> showPaybackHistory(String contractCode,
			PageBounds pageBounds);
	
	/**
	 * 根据loanCode查询所有字段
	·* 2017年2月27日
	·* by Huowenlong
	 * @param loanCode
	 * @return
	 */
	public List<LoanStatusHis> searchAllFieldByLoanCode(String loanCode);
	/**
	 * 获取邮件历史
	 * @author 于飞
	 * @Create 2017年3月8日
	 * @param paybackMonthId
	 * @return
	 */
	public List<EmailOpe> showEmailOpe(Map<String,Object> map,PageBounds pageBounds);

	
	/**
	 * 插入邮件历史
	 * @author 于飞
	 * @Create 2017年3月8日
	 * @param emailOpe
	 */
	public void insertEmailOpe(EmailOpe emailOpe);
}