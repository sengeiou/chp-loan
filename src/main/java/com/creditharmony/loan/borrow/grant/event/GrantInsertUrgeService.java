package com.creditharmony.loan.borrow.grant.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.constants.PlatNameConst;
import com.creditharmony.loan.borrow.grant.dao.GrantDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.type.LoanProductCode;

/**
 * @Class Name GrantChangeFlagEx
 * @author 朱静越
 * @Create In 2015年11月28日
 * 该方法用来修改单子的标识，放款过程中的修改单子的标识
 */
@Service("GrantInsertUrgeService")
public class GrantInsertUrgeService {
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private GrantDao grantDao;
	@Autowired
	private SystemSetMaterDao sysDao;
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
    private LoanBankDao loanBankDao;
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void urgeServiceInsertDeal(Map<String, String> map) {
	   String applyId=map.get("applyId");
	   String deductJumpRule = ""; // 跳转时的规则
	   String platString = ""; // 划扣平台
	   RepaymentProcess dealStep = null;
	   String loanFlag = map.get("loanFlag");
	   String model = map.get("model");
	   GrantEx grantEx=grantDao.getGrantList(applyId);
	   UrgeServicesMoney urgeMoney=new UrgeServicesMoney();
	   urgeMoney.setrGrantId(grantEx.getId());
	   urgeMoney.setContractCode(grantEx.getContractCode());
		// 插入银行数据
	   insertLoanBank(grantEx); 
		//  金账户的插入催收服务费,直接到已办；大金融放款成功之后，同步债权到CHP系统，同时将放款成功的数据，插入到催收费服务费已办中
	   if(LoanModel.TG.getName().equals(model)||ChannelFlag.ZCJ.getCode().equals(loanFlag)){
		    // 默认初始状态为划扣已办
			urgeMoney.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			urgeMoney.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			urgeMoney.setUrgeMoeny(grantEx.getFeeUrgedService());
			// 划扣金额
			urgeMoney.setUrgeDecuteMoeny(grantEx.getFeeUrgedService());
			// 查账金额
			urgeMoney.setAuditAmount(new BigDecimal(0.00));
			urgeMoney.setUrgeDecuteDate(new Date());
			urgeMoney.setTimeFlag(YESNO.NO.getCode());
			urgeMoney.setReturnLogo(YESNO.NO.getCode());
			urgeMoney.setDeductJumpRule(deductJumpRule);
			urgeMoney.setAutoDeductFlag(YESNO.NO.getCode());
			urgeMoney.setDictDealType(platString);
			urgeMoney.setUrgeType(GrantCommon.CS_UEGE_TYPE);
			urgeMoney.preInsert();
			urgeServicesMoneyDao.insertUrge(urgeMoney);
	   }else {
		   	// 默认初始状态为待划扣
			urgeMoney.setDictDealStatus(UrgeCounterofferResult.PREPAYMENT.getCode());
			urgeMoney.setDeductStatus(UrgeCounterofferResult.PREPAYMENT.getCode());
			if (LoanProductCode.PRO_NONG_XIN_JIE.equals(grantEx.getProductType())) { // 农信借的产品，催收服务费为：催收+外访+征信
				BigDecimal feeSum = new BigDecimal(grantEx.getFeeSum());
				urgeMoney.setUrgeMoeny(feeSum);
			}else {
				urgeMoney.setUrgeMoeny(grantEx.getFeeUrgedService());
			}
			// 划扣金额
			urgeMoney.setUrgeDecuteMoeny(new BigDecimal(0.00));
			// 查账金额
			urgeMoney.setAuditAmount(new BigDecimal(0.00));
			urgeMoney.setUrgeDecuteDate(new Date());
			urgeMoney.setTimeFlag(YESNO.NO.getCode());
			// 签约平台
			platString=loanGrantDao.selPlat(grantEx.getLoanCode());
			// 初始化划扣平台为签约平台
			urgeMoney.setDictDealType(platString);	
			
			SystemSetting rules = new SystemSetting();
			// 根据签约平台获得跳转规则,
			if (DeductPlat.ZHONGJIN.getCode().equals(platString)) {
				rules.setSysFlag(PlatNameConst.ZJ);
				rules = sysDao.get(rules);
				deductJumpRule = rules.getSysValue();
				dealStep = RepaymentProcess.SEND_ZHONGJIN;
			} else if (DeductPlat.FUYOU.getCode().equals(platString)) { // 签约平台为富友
				rules.setSysFlag(PlatNameConst.FY);
				rules = sysDao.get(rules);
				deductJumpRule = rules.getSysValue();
				dealStep = RepaymentProcess.SEND_FUYOU;
			} else if (DeductPlat.TONGLIAN.getCode().equals(platString)) {  // 签约平台为通联
				rules.setSysFlag(PlatNameConst.TL);
				rules = sysDao.get(rules);
				deductJumpRule = rules.getSysValue();
				dealStep = RepaymentProcess.SEND_TONGLIAN;
			} else {
				rules.setSysFlag(PlatNameConst.HYL);
				rules = sysDao.get(rules);
				deductJumpRule = rules.getSysValue();
				dealStep = RepaymentProcess.SEND_HYL;
			}
			
			SystemSetting param = new SystemSetting();
			param.setSysFlag(GrantCommon.SYS_AUTO_DEDUCTS);
			param = sysDao.get(param);
			// 如果自动划扣标识为是，更新催收主表中该单子的自动划扣标识
			if (YESNO.YES.getCode().equals(param.getSysValue())) {
				// 设置催收主表中自动划扣标识为是
				urgeMoney.setAutoDeductFlag(YESNO.YES.getCode());
			}else{
				urgeMoney.setAutoDeductFlag(YESNO.NO.getCode());
			}
			urgeMoney.setDeductJumpRule(deductJumpRule);
			urgeMoney.setReturnLogo(YESNO.NO.getCode());
			urgeMoney.setUrgeType(GrantCommon.CS_UEGE_TYPE);
			// 插入成功，获得催收主表id
			urgeMoney.preInsert();
			urgeServicesMoneyDao.insertUrge(urgeMoney);
			String id =urgeMoney.getId();
			// 插入成功之后，进行判断，如果当前系统处于自动划扣状态的，进行发送到批处理
			if (YESNO.YES.getCode().equals(param.getSysValue())) {
				sendList(id, deductJumpRule, dealStep);
			}
	   }
	}

	/**
	 * 放款成功之后，向银行表中插入数据，插入的银行号为还款账号
	 * 2017年1月23日
	 * By 朱静越
	 * @param grantEx 参数
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void insertLoanBank(GrantEx grantEx) {
		LoanBank queryParam = new LoanBank();
        queryParam.setLoanCode(grantEx.getLoanCode());
        queryParam.setRepaymentFlag(YESNO.YES.getCode());
        queryParam.setBankTopFlag(new Integer(1));
        List<LoanBank> savedBanks = loanBankDao.findAllList(queryParam);
        if(ObjectHelper.isEmpty(savedBanks)){
            LoanBank savedBank = loanBankDao.selectByLoanCode(grantEx.getLoanCode());
            savedBank.setRepaymentFlag(YESNO.YES.getCode());
            savedBank.preInsert();
            savedBank.setDictMaintainType(MaintainType.ADD.getCode());
            loanBankDao.insert(savedBank);
        }else {
        	 LoanBank savedBank = loanBankDao.selectByLoanCode(grantEx.getLoanCode());
             savedBank.setRepaymentFlag(YESNO.YES.getCode());
             savedBank.preUpdate();
             loanBankDao.updateByLoanCode(savedBank);
		}
	}
	
	/**
	 * 发送到批处理
	 * 2016年5月9日
	 * By 朱静越
	 * @param id
	 * @param ruleString
	 * @param dealStep
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendList(String id,String ruleString,RepaymentProcess dealStep){
		UrgeServicesMoneyEx urgeMoneyEx = new UrgeServicesMoneyEx();
		urgeMoneyEx.setUrgeId("'"+id+"'");
		// 获得要传送过去的单子的list
		List<DeductReq> list =  urgeServicesMoneyDao.selSendList(urgeMoneyEx);
		if (ArrayHelper.isNotEmpty(list)) {
			for(DeductReq deductReq:list){
				// 设置划扣标志为代收
				deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
				// 设置划扣规则，从前台接收过来的规则
				deductReq.setRule(ruleString);
				//  系统处理ID，设置为催收处理
				deductReq.setSysId(DeductWays.HJ_04.getCode());
				
				deductReq.setRemarks(deductReq.getBusinessId()+"_"+"催收服务费");
			}
			// 发送到批处理之后，如果发送成功，更改状态为处理中
			DeResult t = TaskService.addTask(list.get(0));
			try {
				if(t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())){
					UrgeServicesMoney urgeMoneyUpd = new UrgeServicesMoney();
					urgeMoneyUpd.setId("'"+id+"'");
					urgeMoneyUpd.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
					urgeMoneyUpd.setDeductStatus(UrgeCounterofferResult.PROCESS.getCode());
					urgeMoneyUpd.preUpdate();
					urgeServicesMoneyDao.updateUrge(urgeMoneyUpd);
					// 插入历史
					PaybackOpe paybackOpe = new PaybackOpe();
					paybackOpe.setrPaybackId(id);
					paybackOpe.setDictLoanStatus(dealStep.getCode());
					paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
					paybackOpe.setRemarks("发送划扣");
					paybackOpe.setOperateResult(PaybackOperate.SEND_SUCCESS.getCode());
					paybackOpe.preInsert();
					
					paybackOpe.setOperator(UserUtils.getUser().getUserCode());
					paybackOpe.setOperateCode(UserUtils.getUser().getId());
					paybackOpe.setOperateTime(new Date());

					loanStatusHisDao.insertPaybackOpe(paybackOpe);
					TaskService.commit(t.getDeductReq());
				}else{
					PaybackOpe paybackOpe = new PaybackOpe();
					paybackOpe.setrPaybackId(id);
					paybackOpe.setDictLoanStatus(dealStep.getCode());
					paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
					paybackOpe.setRemarks("发送划扣"+t.getReMge());
					paybackOpe.setOperateResult(PaybackOperate.SEND_FAILED.getCode());
					paybackOpe.preInsert();
					
					paybackOpe.setOperator(UserUtils.getUser().getUserCode());
					paybackOpe.setOperateCode(UserUtils.getUser().getId());
					paybackOpe.setOperateTime(new Date());

					loanStatusHisDao.insertPaybackOpe(paybackOpe);
					TaskService.rollBack(t.getDeductReq());
				}
			} catch (Exception e) {
				e.printStackTrace();
				TaskService.rollBack(t.getDeductReq());
			}
		}
	}

}