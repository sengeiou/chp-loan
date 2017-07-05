package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.ex.LoanServiceBureauEx;

/**
 * @Class Dao实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月9日
 */
@LoanBatisDao
public interface LoanServiceBureauDao extends CrudDao<LoanServiceBureauDao> {

	/**
	 * 集中划扣申请已办
	 * 2016年3月29日
	 * By zhaojinping
	 * @param pageBounds
	 * @param loanServiceBureau
	 * @return
	 */
     public List<LoanServiceBureauEx> centerApplyHaveToList(PageBounds pageBounds,
 			LoanServiceBureauEx loanServiceBureau); 
     
     /**
      * 借款人服务部待提前结清确认审核已办列表页面
      * 2016年3月29日
      * By zhaojinping
      * @param pageBounds
      * @param loanServiceBureau
      * @return
      */
     public List<LoanServiceBureauEx> earlyExamHavetoList(PageBounds pageBounds,LoanServiceBureauEx loanServiceBureau);
	/**
	 * 借款人服务部已办
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param loanServiceBureau
	 * @return
	 */
	/*public List<LoanServiceBureauEx> allLoanServiceBureauHavaToList(PageBounds pageBounds,
			LoanServiceBureauEx loanServiceBureau);
    */
	/**
	 * 门店已办
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param loanServiceBureau
	 * @return
	 */
	public List<LoanServiceBureauEx> allStoresAlreadyDoList(PageBounds pageBounds,
			LoanServiceBureauEx loanServiceBureau);

	/**
	 * 门店已办 单个查看(还款用途为：提前结清)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	public LoanServiceBureauEx seeStoresAlreadyDo(
			LoanServiceBureauEx loanServiceBureau);

	/**
	 * 门店已办 单个查看(还款用途为：非提前结清)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	public LoanServiceBureauEx seeStoresAlreadyDos(
			LoanServiceBureauEx loanServiceBureau);

	/**
	 * 门店已办 单个查看(还款用途为：非提前结清 汇款/转账还款信息)
	 * 2015年12月17日
	 * By 李强
	 * @param loanServiceBureau
	 * @return
	 */
	public List<PaybackTransferInfo> seePayBackTrans(String ids);
	
	/**
	 * 电催已办
	 * 2016年2月25日
	 * By 李强
	 * @param pageBounds
	 * @param loanServiceBureau
	 * @return
	 */
	public List<LoanServiceBureauEx> allStoresAlreadyDoListEl(PageBounds pageBounds,
			LoanServiceBureauEx loanServiceBureau);

	/**
	 * POS刷卡查账列表
	 * 2016年2月25日
	 * By 李强
	 * @param ids
	 * @return
	 */
	public List<PaybackTransferInfo> seePayBackTransPos(String ids);



}
