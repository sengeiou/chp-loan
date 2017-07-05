package com.creditharmony.loan.common.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveTransferResutInParam;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;

/** 
* @author 作者 于飞 
* @version 创建时间：2016年9月27日 下午2:44:50 
* 大金融划扣完成后，接收划扣结果，业务处理
*/
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ReceiveTransferService{

	protected Logger logger = LoggerFactory
			.getLogger(ReceiveTransferService.class);

	@Autowired
	private DeductUpdateDao dao;
	
	@Autowired
	private LoanStatusHisDao hisDao;
	
	@Autowired
	private PaybackDao paybackDao;
	
	/**
	 * 划扣的返回结果更新申请表、蓝补、蓝补对账单信息
	 * @author 于飞
	 * @Create 2016年9月27日
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updateDeductData(DjrReceiveTransferResutInParam inParam) {
		try{
			//冲抵内容
			AgainstContent againstContent = null;
			//期供id
			String rMonthId = null;
			//集中划扣
			DeductsPaybackApply deductApply = new DeductsPaybackApply();
			//非集中划扣
			PaybackApply paybackApply = new PaybackApply();
			//成功返回划扣金额
			BigDecimal succeedMoney = null;
			//申请划扣金额
			BigDecimal applyAmount = null;
			//集中划扣
		    if (inParam.getType().equals("1")) {
		    	againstContent = AgainstContent.CENTERDEDUCT;
		    	//获取期供id
		    	deductApply.setId(inParam.getApplyid());
		    	deductApply = dao.getDeductsPaybackApply(deductApply);
		    	rMonthId = deductApply.getMonthId();
		    	applyAmount = deductApply.getApplyDeductAmount();
		    	paybackApply.setDictDealType(deductApply.getDictDealType());
			}else if(inParam.getType().equals("2")){//非集中划扣
				paybackApply.setId(inParam.getApplyid());
				paybackApply = dao.getPaybackApply(paybackApply);
				againstContent = AgainstContent.PAYBACK_DEDUCT;
				applyAmount = paybackApply.getApplyAmount();
			}
			
			if(inParam.getMoney() == null || "".equals(inParam.getMoney())){
				succeedMoney = new BigDecimal(0);
			}else{
				succeedMoney = inParam.getMoney();
			}
			//划扣成功
			if ((succeedMoney.compareTo(applyAmount)) >= 0 && inParam.getResult()==1) {
				paybackApply.setApplyReallyAmount(succeedMoney);
				paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
				paybackApply.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
			} else {
				// 集中划扣不更新到已办列表，当天处理完成后流转到已办（系统批处理）
				if (inParam.getType().equals("1")) {
					paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					paybackApply.setFailReason(inParam.getRemarks());
					paybackApply.setDictPaybackStatus(RepayApplyStatus.DEDUCTT_FAILED.getCode());
				} else if (inParam.getType().equals("2")) {
					// 非集中划扣处理完成后直接流转到集中划扣已办。
					paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					paybackApply.setFailReason(inParam.getRemarks());
					paybackApply.setDictPaybackStatus(RepayApplyStatus.DEDUCTT_FAILED.getCode());
				} else {
					paybackApply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
				}
			}
			paybackApply.setDictDealType(DeductPlat.ZCJ.getCode());
			paybackApply.setSplitFlag(YESNO.NO.getCode());
			paybackApply.setId(inParam.getApplyid());
			paybackApply.setContractCode(inParam.getContractCode());
			//更新集中划扣申请表信息
			if (inParam.getType().equals("1")) {
				//updateDeductsPaybackApply(paybackApply);
				PaybackApply apply = dao.queryPaybackApply(paybackApply);
				if(apply == null){
					paybackApply.preUpdate();
					dao.updateDeductsPaybackApplyHis(paybackApply);
				}
				// 如果回盘结果成功 然后 插入 还款_集中划扣还款申请归档表
				if (CounterofferResult.PAYMENT_SUCCEED.getCode().equals(paybackApply.getSplitBackResult())) {
					// 因为回盘有时候会很久，这个时候集中划扣的数据已经被删除，需要更新还款_集中划扣还款申请归档里面的数据。
					if(apply != null){
						dao.insertDeductsPaybackApplyHis(paybackApply);
						// 如果成功将集中划扣该数据删除
						//dao.deleteDeductsPaybackApply(paybackApply);
					}
				}
				//更新划扣申请归档表记录
				updatePaybackListHis(paybackApply);
			} else {//更新非集中划扣申请表信息
				updatePaybackApply(paybackApply);
				
			}
			//插入历史记录
			insertPaybackOpe(paybackApply,inParam.getType());
			//增加划扣记录
			//addPaybackDeducts(paybackApply);
			
			//根据合同编号更新蓝补余额----------------
			if(inParam.getResult()==1){
				Payback payback = new Payback();
				payback.preUpdate();
				payback.setContractCode(inParam.getContractCode());
				payback.setPaybackBuleAmount(inParam.getMoney());
				dao.updateBuleAmountByContractCode(payback);
				//增加蓝补历史------------------
				Map<String, String> contractMap = new HashMap<String, String>();
				contractMap.put("contractCode", inParam.getContractCode());
				//根据合同编号获取还款主表
				List<Payback> paybackList = dao.getPayback(contractMap); 
				payback = paybackList.get(0);
				
				// 蓝补历史更新-----------------------
				addPaybackBuleAmont(rMonthId,inParam.getMoney(),
						payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
						againstContent, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 保存还款蓝补交易明细 2016年9月27日 By 于飞
	 * 
	 * @param rMonthId
	 *            关联ID（期供ID）
	 * @param tradeAmount
	 *            交易金额
	 * @param surplusBuleAmount
	 *            蓝补余额
	 * @param tradeType
	 *            交易类型
	 * @param againstContent
	 *            冲抵内容
	 * @param chargeType
	 *            冲抵类型
	 */

	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackBuleAmont(String rMonthId, BigDecimal tradeAmount,
			BigDecimal surplusBuleAmount, TradeType tradeType,
			AgainstContent againstContent, ChargeType chargeType,String contractCode) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();
		paybackBuleAmont.setCreateBy("batch");
		paybackBuleAmont.setOperator("batch");
		paybackBuleAmont.setModifyBy("batch");
		paybackBuleAmont.setrMonthId(rMonthId);// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);// 蓝补余额
		paybackBuleAmont.setOperator("系统处理");// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间
		paybackBuleAmont.setContractCode(contractCode);

		dao.addBackBuleAmont(paybackBuleAmont);
	}
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2016年9月28日
	 * @param paybackApply
	 */
	private void updateDeductsPaybackApply(PaybackApply paybackApply) {
		try {
			User user = UserUtils.getUser();
			if (user != null) {
				if (StringUtils.isNotBlank(user.getId())) {
					paybackApply.setModifyBy(user.getId());
				} else {
					paybackApply.setModifyBy("系统处理");
				}
			} else {
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
		
		dao.updateDeductsPaybackApply(paybackApply);
	}
	
	/**
	 * 更新还款申请表 2016年2月16日 By 王彬彬
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(PaybackApply paybackApply) {
		try {
			User user = UserUtils.getUser();
			if(user != null){
				if (StringUtils.isNotBlank(user.getId())){
					paybackApply.setModifyBy(user.getId());
				}else{
					paybackApply.setModifyBy("系统处理");
				}
			}else{
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			e.printStackTrace();
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
		dao.updatePaybackApply(paybackApply);
	}
	
	/**
	 * 2016年4月12日 By 翁私
	 * 
	 * @param paybackApply
	 *            增加划扣记录
	 * @return none
	 */
	private void addPaybackDeducts(PaybackApply paybackApply) {
		PaybackDeducts deducts = new PaybackDeducts();
		deducts.setDeductAmount(paybackApply.getApplyReallyAmount());
		deducts.setrId(paybackApply.getId());
		deducts.setDictBackResult(paybackApply.getSplitBackResult());
		// deducts.setDictRDeductType(TargetWay.PAYMENT.getCode())
		deducts.preInsert();
		deducts.setDecuctTime(new Date());
		dao.addPaybackDeducts(deducts);

	}
	
	/**
	 * 增加划扣记录
	 * @author 于飞
	 * @Create 2016年10月24日
	 * @param paybackApply
	 * @param type 1:非集中划扣 2：集中划扣
	 */
	private void insertPaybackOpe(PaybackApply paybackApply,String type){
		Payback payback = paybackDao.findPaybackByContractCode(paybackApply.getContractCode());
		PaybackOpe ope = new PaybackOpe();
		ope.setrPaybackApplyId(paybackApply.getId());
		ope.setrPaybackId(payback.getId());
		ope.setDictLoanStatus(RepaymentProcess.DEDECT.getCode());
		if(type.equals("2"))
			ope.setDictRDeductType(TargetWay.REPAYMENT.getCode());
		else
			ope.setDictRDeductType(TargetWay.PAYMENT.getCode());
		
		//划扣成功
		if(paybackApply.getSplitBackResult().equals(CounterofferResult.PAYMENT_SUCCEED.getCode())){
			ope.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			ope.setRemarks("合同编号："+paybackApply.getContractCode()+",划扣金额："+paybackApply.getApplyReallyAmount());
		}else{
			ope.setOperateResult(PaybackOperate.DEDECT_FAILED.getCode());
			ope.setRemarks(paybackApply.getFailReason());
		}
		ope.setCreateBy("系统处理");
		ope.setModifyBy("系统处理");
		ope.preInsert();
		ope.setOperator("系统处理");
		ope.setOperateCode("系统处理");
		ope.setOperateTime(new Date());
		//判断插入的历史记录操作时间是否比最近的一条历史记录之后
		PaybackOpe maxOpe = getMaxOpe(paybackApply.getId());
		if(maxOpe!=null){
			if(ope.getOperateTime().compareTo(maxOpe.getOperateTime())<=0){
				Date createTime = maxOpe.getCreateTime();
				Date modifyTime = maxOpe.getModifyTime();
				Date operateTime = maxOpe.getOperateTime();
				Calendar cal = Calendar.getInstance();  
				//创建时间增加10秒
		        cal.setTime(createTime);   
		        cal.add(Calendar.SECOND, 10);// 时间增加十秒   
		        createTime = cal.getTime(); 
		        //修改时间增加10秒
		        cal.setTime(modifyTime);   
		        cal.add(Calendar.SECOND, 10);// 时间增加十秒   
		        modifyTime = cal.getTime();
		        //申请时间增加10秒
		        cal.setTime(operateTime);   
		        cal.add(Calendar.SECOND, 10);// 时间增加十秒   
		        operateTime = cal.getTime();
		        //操作历史时间赋值
		        ope.setCreateTime(createTime);
		        ope.setModifyTime(modifyTime);
		        ope.setOperateTime(operateTime);
			}
		}
		hisDao.insertPaybackOpe(ope);
	}
	
	/**
	 * 更新 还款_待还款归档列表 2016年2月16日 By 翁私
	 * 
	 * @param paybackApply
	 *            还款申请信息
	 */
	private void updatePaybackListHis(PaybackApply paybackApply) {
		try {
			User user = UserUtils.getUser();
			if (user != null) {
				if (StringUtils.isNotBlank(user.getId())) {
					paybackApply.setModifyBy(user.getId());
				} else {
					paybackApply.setModifyBy("系统处理");
				}
			} else {
				paybackApply.setModifyBy("系统处理");
			}
		} catch (Exception e) {
			e.printStackTrace();
			paybackApply.setModifyBy("系统处理");
		}
		paybackApply.setModifyTime(new Date());
		dao.updatePaybackListHisByApplyHis(paybackApply);
	}
	
	/**
	 * 获取最近的一条历史记录
	 * @author 于飞
	 * @Create 2016年11月28日
	 * @param rPaybackApplyId
	 * @return
	 */
	private PaybackOpe getMaxOpe(String rPaybackApplyId){
		Map<String, String> filter = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(rPaybackApplyId) && rPaybackApplyId.length()>0) {
			filter.put("rPaybackApplyId", rPaybackApplyId);
		}
		PageBounds pageBounds = new PageBounds(1,1);
		List<PaybackOpe> opeList = hisDao.getPaybackOpe(filter,
				pageBounds);
		if(opeList.size()>0)
			return opeList.get(0);
		else 
			return null;
	}
	
	
}
