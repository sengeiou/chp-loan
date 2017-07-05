package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.CenterDeductDao;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackHis;
import com.creditharmony.loan.borrow.payback.entity.PaybackList;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.CenterDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 集中划扣service
 * @Class Name CenterDeductService
 * @author zhaojinping
 * @Create In 2015年12月17日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CenterDeductService extends CoreManager<CenterDeductDao, PaybackList>{

	public static final String ZERO = "0";
	public static final String FIRST = "1";
	public static final String FOUR = "4";
	public static final String FIVE = "5";
	@Autowired
	private HistoryService historyService;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	/**
	 * 获取待还款列表数据
	 * 2015年12月11日
	 * By zhaojinping
	 * @param page
	 * @param paramMap
	 * @return 分页对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackList> getAllJzApplyList(Page<PaybackList> page,Map<String,Object> paramMap){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackList> pageList = (PageList<PaybackList>)dao.getAllList(pageBounds,paramMap);
	   /* for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i));
		}*/
		List<Dict> dictList = DictCache.getInstance().getList();
		for (PaybackList paybackList : pageList) {
			for(Dict dict:dictList){
				
				if("jk_open_bank".equals(dict.getType())&&dict.getValue().equals(paybackList.getApplyBankName())){
					paybackList.setApplyBankNameLabel(dict.getLabel());
				}
				if(paybackList.getLoanInfo() != null){
				if("jk_loan_status".equals(dict.getType())&&dict.getValue().equals(paybackList.getLoanInfo().getDictLoanStatus())){
					paybackList.getLoanInfo().setDictLoanStatusLabel(dict.getLabel());
				}
				}
				if("jk_period_status".equals(dict.getType())&&dict.getValue().equals(paybackList.getDictMonthStatus())){
					paybackList.setDictMonthStatusLabel(dict.getLabel());
				}
				if("jk_channel_flag".equals(dict.getType())&&dict.getValue().equals(paybackList.getMark())){
					paybackList.setMarkLabel(dict.getLabel());
				}
				if("jk_deduct_plat".equals(dict.getType())&&dict.getValue().equals(paybackList.getDictDealType())){
					paybackList.setDictDealTypeLabel(dict.getLabel());
				}
				if("jk_loan_model".equals(dict.getType())&&dict.getValue().equals(paybackList.getModel())){
					paybackList.setModelLabel(dict.getLabel());
				}
			}
		}
		PageUtil.convertPage(pageList, page);	
		return page;
	}
	
	/**
	 * 获取用户的银行卡信息
	 * 2015年12月11日
	 * By zhaojinping
	 * @param contractCode
	 * @return 借款信息列表
	 */
	@Transactional(value="loanTransactionManager", readOnly = true)
	public List<LoanBank> getUserCardInfo(String contractCode){
		List<LoanBank> list = dao.getUserCardInfo(contractCode);
		return list;
	}
	
	/**
	 * 置顶选中的卡号信息
	 * 2015年12月11日
	 * By zhaojinping
	 * @param id
	 * @return none 
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateCardInfo(String id){
		dao.updateCardTop(id);
		dao.updateCardInfo(id);
	}
	
	/**
	 * 保存还款历史明细
	 * 2015年12月23日
	 * By wengsi
	 * @param paybackMonthId 期供id
	 * @param money 还款金额
	 * @param contractCode 合同编号
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager",readOnly = false)
	public void addPaybackHis(String paybackMonthId,BigDecimal money,String contractCode){
		PaybackHis paybackHis = new PaybackHis();
		paybackHis.setContractCode(contractCode);
		paybackHis.setrMonthId(paybackMonthId);
		paybackHis.setPaymentAmount(money);
		paybackHis.setPaymentDay(new Date());
		paybackHis.setIsNewRecord(false);
		paybackHis.preInsert();
		dao.addPaybackHis(paybackHis);
	}
	
	
    /**
     * 查询导出数据
     * 2015年12月25日
     *  By wengsi
     * @param centerDeduct
     * @return 导出列表
     */
	@Transactional(value = "loanTransactionManager",readOnly = true)
	public List<CenterDeductEx> getCenterDeductList(Map<String,Object> map) {
		return dao.getCenterDeductList(map);
	}

	 /**
     * 集中划扣申请
     * 2015年3月28日
     *  By wengsi
     * @param paramMap
     * @return 返回信息
     */
	@Transactional(value = "loanTransactionManager",readOnly = false)
	public void deductionApply(DeductsPaybackApply apply) {
	           String platform = apply.getDictDealType();
			   // apply.setApplyPayDay(new Date());
			   apply.setLanuchBy(UserUtils.getUser().getId());
			   apply.setDictDealType(platform);
			   if(UserUtils.getUser().getDepartment() != null){
			   apply.setOrgCode(UserUtils.getUser().getDepartment().getId());
			   }
			   apply.setDictBackResult(CounterofferResult.PREPAYMENT.getCode());
			   apply.preInsert();
			   apply.setId(apply.getRlistId());
			   dao.addCenterApply(apply);
			   // 修改已经提交了集中划扣申请的数据的状态为 ‘已申请’
			   apply.preUpdate();
			   dao.updateState(apply);
			   // 查询待还款归档列表
			  // PaybackApply  pay = dao.queryListHis(apply);
			   PaybackApply pay = new PaybackApply();
			   if(Integer.parseInt(apply.getStoresMark()) == 0){
				   pay = new PaybackApply();
				   pay.setId(apply.getRlistId());
				   pay.setDictDealType(platform);
				   User user = UserUtils.getUser();
					if (StringUtils.isNotBlank(user.getId())){
						  pay.setCreateBy(user.getId());
						  pay.setModifyBy(user.getId());
					}
				   // 插入
				   dao.insertListHis(pay);
			   }else{
				   // 更新
				   pay.setId(apply.getRlistId());
				   pay.setDictPaybackStatus(CounterofferResult.PREPAYMENT.getCode());
				   pay.setDictDealType(platform);
				   dao.updateListHis(pay);
			   }
		    	// 向还款操作流水记录表中插入记录
			   PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getRlistId(),
					   apply.getPaybackId(), RepaymentProcess.REPAYMENT_APPLY,
						TargetWay.PAYMENT,
						PaybackOperate.APPLY_SUCEED.getCode(), "");
			    PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(paybackOpes.getrPaybackApplyId());
				paybackOpe.setrPaybackId(paybackOpes.getrPaybackId());
				paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
				paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
				paybackOpe.setRemarks(paybackOpes.getRemark());
				paybackOpe.setOperateResult(paybackOpes.getOperateResult());
				paybackOpe.preInsert();
				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}

		public List<DeductsPaybackApply> queryCenterApply(
				Map<String, Object> paramMap) {
			return dao.queryCenterApply(paramMap);
		}
}
