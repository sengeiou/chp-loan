package com.creditharmony.loan.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveRefundInfoInParam;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.AppStatus;
import com.creditharmony.core.loan.type.AppType;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PayFeeResult;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesBackMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeHistory;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.refund.dao.RefundDao;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.borrow.refund.entity.Refund;
import com.creditharmony.loan.common.dao.DeductUpdateDao;

/** 
* @author 作者 于飞 
* @version 创建时间：2016年10月11日 上午11:30:50 
* 退款处理完成后，同步放款结果到CHP系统。
*/
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ReceiveRefundInfoService{

	protected Logger logger = LoggerFactory
			.getLogger(ReceiveRefundInfoService.class);

	@Autowired
	private RefundDao dao;
	@Autowired
	private UrgeServicesBackMoneyDao urgeDao;
	@Autowired
	private DeductUpdateDao deductDao;
	
	/**
	 * 划扣的返回结果更新申请表、蓝补、蓝补对账单信息
	 * @author 于飞
	 * @Create 2016年9月27日
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updateRefundInfo(DjrReceiveRefundInfoInParam inParam) {
		try{
			//蓝补退款
			if(inParam.getType().equals(AppType.BLUE_RETURN.getCode())){
				//更新退款结果
				Refund refund = new Refund();
				if(inParam.getResult()==1)
					refund.setAppStatus(AppStatus.HAS_RETURN.getCode());
				else 
					refund.setAppStatus(AppStatus.FAIL_RETURN.getCode());
				refund.setRemark(inParam.getRemarks());
				refund.setId(inParam.getOrderId());
				refund.preUpdate();
				refund.setModifyBy("系统管理");
				dao.update(refund);
				//退款失败更新蓝补
				if(inParam.getResult()==0){
					updateBlueAmount(inParam);
				}
				//操作历史
				PaybackHistory history = new PaybackHistory();
				history.setContractCode(inParam.getContractCode());
				history.setId(IdGen.uuid());
				if(inParam.getResult()==1){
					history.setOperName("大金融蓝补退款成功");
					history.setOperNotes("大金融蓝补退款成功");
					history.setOperResult("退款列表(大金融成功)");
				}else{
					history.setOperName("大金融蓝补退款失败");
					history.setOperNotes("大金融蓝补退款失败");
					history.setOperResult("退款列表(大金融失败)");
				}
				history.preInsert();
				dao.insertHistory(history);
				logger.info("-----大金融蓝补退款更新成功，时间是"+new Date()+";合同编号是"+inParam.getContractCode()+"------");
			}else{//催收服务费返款
				logger.info("-----开始进行催收服务费退款，时间是"+new Date()+";合同编号是"+inParam.getContractCode()
					+"；urgeDao是否为空呢"+urgeDao+"------");
				//更新催收服务费返款
				UrgeServicesBackMoney urgeBack = new UrgeServicesBackMoney();
				
				urgeBack.setBackApplyPayTime(new Date());
				urgeBack.setBackApplyBy("系统处理");
				urgeBack.setBackApplyDepartment("系统");
				urgeBack.setRemark(inParam.getRemarks());
				urgeBack.setId(inParam.getOrderId());
				if(inParam.getResult()==1){
					urgeBack.setDictPayStatus(UrgeRepay.REPAIED.getCode());
					urgeBack.setDictPayResult(PayFeeResult.SUCCESS.getCode());
				}else{
					urgeBack.setDictPayStatus(UrgeRepay.REPAY_TO.getCode());
					urgeBack.setDictPayResult(PayFeeResult.FAILED.getCode());
				}
				urgeBack.setBackTime(new Date());
				urgeBack.setModifyBy("系统处理");
				urgeBack.setModifyTime(new Date());
				logger.info("即将更新催收状态");
				urgeDao.updateUrgeBack(urgeBack);
				logger.info("催收状态更新成功");
				
				//增加操作历史
				UrgeHistory urgeHistory = new UrgeHistory();
				if(inParam.getResult()==1){
					urgeHistory.setDictayPayResult("大金融催收服务费退款成功");
					urgeHistory.setRemarks("大金融催收服务费退款成功");
					urgeHistory.setOperateStep("催收服务费（大金融成功）");
				}else{
					urgeHistory.setDictayPayResult("大金融催收服务费退款失败");
					urgeHistory.setRemarks("大金融催收服务费退款失败");
					urgeHistory.setOperateStep("催收服务费（大金融失败）");
				}
				
				urgeHistory.setrUrgeId(inParam.getOrderId());
				urgeHistory.setOperator("系统处理");
				urgeHistory.setOperateTime(new Date());
				urgeHistory.setId(IdGen.uuid());
				urgeHistory.setCreateBy("系统处理");
				urgeHistory.setModifyBy("系统处理");
				urgeHistory.setCreateTime(new Date());
				urgeHistory.setModifyTime(new Date());
				urgeDao.insertUrgeHistory(urgeHistory);
				logger.info("-----大金融催收服务费退款更新成功，时间是"+new Date()+";合同编号是"+inParam.getContractCode()+"------");
			}
			return true;
		}catch(Exception e){
			logger.error("处理大金融退款结果失败："+e.getMessage());
			return false;
		}
	}
	
	public void updateBlueAmount(DjrReceiveRefundInfoInParam inParam){
		Payback payback = new Payback();
		payback.preUpdate();
		payback.setContractCode(inParam.getContractCode());
		payback.setPaybackBuleAmount(inParam.getMoney());
		deductDao.updateBuleAmountByContractCode(payback);
		//增加蓝补历史------------------
		Map<String, String> contractMap = new HashMap<String, String>();
		contractMap.put("contractCode", inParam.getContractCode());
		//根据合同编号获取还款主表
		List<Payback> paybackList = deductDao.getPayback(contractMap); 
		payback = paybackList.get(0);
		String deductContent = null;
		deductContent="大金融蓝补退款失败返还蓝补金额";
		
		// 蓝补历史更新-----------------------
		addPaybackBuleAmont(null,inParam.getMoney(),
				payback.getPaybackBuleAmount(), TradeType.TRANSFERRED,
				deductContent, ChargeType.CHARGE_PRESETTLE,payback.getContractCode());
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
			String againstContent, ChargeType chargeType,String contractCode) {
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
		paybackBuleAmont.setDictDealUse(againstContent);// 冲抵内容
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间
		paybackBuleAmont.setContractCode(contractCode);

		deductDao.addBackBuleAmont(paybackBuleAmont);
	}
}
