package com.creditharmony.loan.channel.bigfinance.ws;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveAgreementFileBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveAgreementFileInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveAgreementFileOutParam;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.zcj.service.ZcjService;
import com.creditharmony.loan.common.dao.GlBillHzDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.entity.GlBillHz;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.RepayPlanNew;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ReckonFeeNew;

@Service
public class DjrReceiveAgreementFileService extends DjrReceiveAgreementFileBaseService{
	private Logger logger = LoggerFactory.getLogger(DjrReceiveAgreementFileService.class);
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ZcjService zcjService;
	@Autowired
	private PaybackMonthDao paybackMonthDao;
	@Autowired
	private GlBillHzDao glBillHzDao;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private ContractFeeDao contractFeeDao;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	/**
	 * 1.接收大金融系统传送过来的实体；
	 * 2.获得实体中的合同编号，根据合同编号进行数据库中信息；
	 * 3.更新放款表中的放款金额，放款回执结果，放款时间；
	 * 4.通过给的放款账户，开户行，账号找到中间人的id，同时将id更新到放款表中;
	 * 5.放款成功的时候，插入到催收服务费表中，在已办中显示划扣成功的催收金额；
	 * 6.借款协议:大金融传送过来的docId,直接插入到合同文件表中；
	 */
	
	@Override
	public DjrReceiveAgreementFileOutParam doExec(DjrReceiveAgreementFileInParam paramBean) {
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd"); 
		DjrReceiveAgreementFileOutParam outBean = new DjrReceiveAgreementFileOutParam();
		String contractCode = paramBean.getContractCode();
		if (StringHelper.isEmpty(contractCode)) {
			outBean.setRetCode(ReturnConstant.ERROR);
			outBean.setRetMsg("参数解析出错！参数信息不能够为空。");
			return outBean;
		}
		logger.info("大金融同步到3.0系统修改的放款合同编号："+contractCode+",借款协议id为："+ paramBean.getDocId());
		try {
			Contract contract = contractDao.findByContractCode(contractCode);
			if (ObjectHelper.isEmpty(contract)) {
				throw new Exception("在数据库中没有找到该合同编号对应的单子信息");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanCode", contract.getLoanCode());
			LoanInfo loanInfo = applyLoanInfoDao.selectByLoanCode(map);
			if (ObjectHelper.isEmpty(loanInfo)) {
				throw new Exception("没有找到该合同编号对应的借款状态");
			}
			if (LoanApplyStatus.BIGFINANCE_GRANTING.getCode().equals(loanInfo.getDictLoanStatus())) {
				String bakApplyId = contract.getApplyId();
				Date lendingTime = sdf.parse(paramBean.getLendingTime());
				LoanInfo updLoanInfo = new LoanInfo();
				updLoanInfo.setApplyId(bakApplyId);
				updLoanInfo.setLoanCode(loanInfo.getLoanCode());
				
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setContractCode(contractCode);
				loanGrant.setLendingTime(lendingTime);
				// 获得中间人id
				String midId = getMidId(paramBean);
				loanGrant.setMidId(midId);
				
				BigDecimal succeedMoney = paramBean.getGrantAmount();
				logger.info("大金融放款债权推送的放款金额为："+paramBean.getGrantAccount());
				// 不会出现部分成功部分失败的数据
				if ((succeedMoney.compareTo(BigDecimal.ZERO)) > 0) {
					// 借款状态为【还款中】
					updLoanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT  
							.getCode());
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
							.getCode());
					loanGrant.setCheckResult(VerityStatus.PASS.getCode());
					logger.info("大金额放款回盘结果更新开始applyId:" + bakApplyId);
					if(!contract.getContractVersion().equals(ContractVer.VER_ONE_ONE_ZCJ.getCode()) && !contract.getContractVersion().equals(ContractVer.VER_ONE_ZERO_ZCJ.getCode())){
						//资产家1.2XYJ版本以上重新按照实际放款日期计算还款计划
						makeContractBefor(contract.getContractCode(),contract,contract.getLoanCode(),lendingTime);
					}
					zcjService.djrReceiveFileDeal(updLoanInfo, loanGrant, paramBean.getLender(), paramBean.getDocId());
					this.addArchives(contractCode);
					outBean.setRetCode(ReturnConstant.SUCCESS);
					outBean.setRetMsg("执行成功，大金融放款同步到CHP3.0系统更新完成");
					logger.info("大金额放款回盘结果更新成功applyId:" + bakApplyId);
				}
			}else {
				outBean.setRetCode(ReturnConstant.ERROR);
				outBean.setRetMsg("该数据的放款中的状态未同步，同步放款完成状态失败。");
				logger.info("该数据的放款中的状态未同步，同步放款完成状态失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			outBean.setRetCode(ReturnConstant.ERROR);
			outBean.setRetMsg("执行失败，大金融放款同步到CHP3.0系统更新失败，失败原因："+e.getMessage());
			logger.error("大金额放款回盘结果更新失败，请检查contractCode:" + contractCode+",原因为：",e);
		}
		return outBean;
	}
	
	// TODO 根据传送过来的放款账户，开户行，账号，查询中间人id
	public String getMidId(DjrReceiveAgreementFileInParam inBean){
		logger.info("大金融放款债权传送的中间人信息，银行账号：" + inBean.getBankCardNo()
				+ "中间人姓名：" + inBean.getGrantAccount() + "开户行："
				+ inBean.getBankName());
		MiddlePerson middlePerson = new MiddlePerson();
		String midId = null;
		middlePerson.setBankCardNo(inBean.getBankCardNo()); // 银行账号
		middlePerson.setMiddleName(inBean.getGrantAccount()); // 中间人姓名
		middlePerson.setMidBankName(inBean.getBankName()); // 开户行
		List<MiddlePerson> midList = middlePersonService.selectMiddlePerson(middlePerson);
		logger.info("查找出的中间人为："+midList);
		if (ObjectHelper.isNotEmpty(midList)) {
			midId = midList.get(0).getId();
		}
		return midId;
	}
	public void makeContractBefor(String contractCode, Contract contract, String loanCode,Date lendingTime) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("contractCode", contractCode);
		// 删除旧有的还款期供信息
		List<PaybackMonth> paybackMonths = paybackMonthDao.findByContractCode(param);
		if (!ObjectHelper.isEmpty(paybackMonths)) {
			paybackMonthDao.deleteByContractCode(param);
		}
		Payback payback = new Payback();
		Map<String, Object> paybackParam = new HashMap<String, Object>();
		paybackParam.put("contractCode", contractCode);
		Payback apply = paybackDao.selectpayBack(paybackParam);
		// 删除旧有的还款信息
		if (!ObjectHelper.isEmpty(apply)) {
			paybackDao.deletePayback(contractCode);
		}
		Contract tempContract = contractDao.findByContractCode(contractCode);
		ContractFee tempContractFee = contractFeeDao.findByContractCode(contractCode);
		Contract contractTemp=new Contract();
		//计算起始还款日期-实际放款日期
		Calendar calendar = this.getBillHzDay(lendingTime);
		tempContract.setContractReplayDay(calendar.getTime());
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.MONTH, contract.getContractMonths().intValue() - 1);
		Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (lastDayOfMonth < signDay) {
			calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			tempContract.setContractEndDay(calendar.getTime());
		} else {
			tempContract.setContractEndDay(calendar.getTime());
		}
		//更新起始还款日期
		contractTemp.setContractReplayDay(tempContract.getContractReplayDay());
		contractTemp.setContractEndDay(tempContract.getContractEndDay());
		contractTemp.setLoanCode(loanCode);
		contractTemp.preUpdate();
		contractDao.updateContract(contractTemp);
		
		List<RepayPlanNew> repayPlan = ReckonFeeNew.createRepayPlanNewHz(tempContract, tempContractFee, glBillHzDao,lendingTime);
		PaybackMonth paybackMonth = null;
		param.put("loanCode", loanCode);
		LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
		String customerCode = loanCustomer.getCustomerCode();
		RepayPlanNew curPlan = repayPlan.get(0);
		payback.setContractCode(contractCode);
		payback.setCustomerCode(customerCode);
		payback.setPaybackCurrentMonth(curPlan.getPayBackCurrentMonth());
		payback.setPaybackMonthAmount(curPlan.getMonthPaySum());
		payback.setEffectiveFlag(YESNO.NO.getCode());
		payback.setPaybackBuleAmount(BigDecimal.ZERO);
//		Date contractfactDay = tempContract.getContractFactDay();//实际放款日期
		Calendar calendar1 = this.getBillHzDay(lendingTime);
		calendar1.setTime(lendingTime);
		GlBillHz glBillHz = new GlBillHz();
		glBillHz.setSignDay(calendar1.get(Calendar.DAY_OF_MONTH));
		GlBillHz tagGlBillHz = glBillHzDao.findBySignDay(glBillHz);
		Integer billDay = tagGlBillHz.getBillDay();
		payback.setPaybackDay(billDay);
		payback.preInsert();
		paybackDao.insertPayback(payback);
		for (RepayPlanNew curr : repayPlan) {
			paybackMonth = new PaybackMonth();
			// 合同编号
			paybackMonth.setContractCode(contractCode);
			// 总期数
			paybackMonth.setMonths(curr.getPayBackCurrentMonth());
			// 每期还款日
			paybackMonth.setMonthPayDay(curr.getMonthPayDay());
			// 每月还款总额
			paybackMonth.setMonthPayTotal(curr.getMonthPaySum());
			// 分期咨询费
			paybackMonth.setMonthFeeConsult(curr.getStageFeeConsult());
			// 分期居间服务费
			paybackMonth.setMonthMidFeeService(curr.getStageFeeService());
			// 分期服务费
			paybackMonth.setMonthFeeService(curr.getStageServiceFee());
			// 应还违约金(1.3系统单算罚息,1.4一次性还款违约罚金)
			paybackMonth.setMonthPenaltyShould(curr.getOncePayPenalty());
			// 应还利息
			paybackMonth.setMonthInterestBackshould(curr.getMonthInterestBackshould());
			// 应还本金
			paybackMonth.setMonthPayAmount(curr.getMonthPayAmount());
			// 一次性应还总额
			paybackMonth.setMonthBeforeFinishAmount(curr.getOncePaySum());
			// month_residue_payactual 还款后剩余本金
			paybackMonth.setMonthResiduePayactual(curr.getResidualPrincipal());
			paybackMonth.setDictMonthStatus(PeriodStatus.REPAYMENT.getCode());
			paybackMonth.preInsert();
			paybackMonthDao.insertPaybackMonth(paybackMonth);
		}

	}

	// 资产家还款日处理
	private Calendar getBillHzDay(Date contractfactDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contractfactDay);
		GlBillHz glBillhz = new GlBillHz();
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		glBillhz.setSignDay(signDay);
		GlBillHz tagGlBillhz = glBillHzDao.findBySignDay(glBillhz);
		Integer billDay = tagGlBillhz.getBillDay();		
		calendar.add(Calendar.MONTH, 1);
		Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (lastDayOfMonth < billDay) {
			calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, billDay);
		}

		return calendar;
	}
	public void addArchives(String contractCode){
		Map map1 = new HashMap();
		map1.put("contractCode", contractCode);
		map1.put("fileNum", "309110122|309110125");
		map1.put("fileName", "委托划扣|其它材料");
		contractDao.addArchives(map1);
	}
	
}
