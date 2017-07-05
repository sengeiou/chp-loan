package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.DeductPlatBank;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.EarlyFinishConfirmDao;
import com.creditharmony.loan.borrow.payback.dao.OverdueManageDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.OverdueManageEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 逾期管理
 * @Class Name OverdueManageService
 * @author 李强
 * @Create In 2015年12月14日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class OverdueManageService {
	BigDecimal bgSum = new BigDecimal("0.00");
	@Autowired
	private OverdueManageDao daoOm;
	@Autowired
    private HistoryService historyService;
	@Autowired
	private EarlyFinishConfirmDao earlyFinishConfirmDao;
	/**
	 * 查询逾期管理列表
	 * 2015年12月14日
	 * By 李强
	 * @param page
	 * @param overdueManageEx
	 * @return 期供逾期数据集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<OverdueManageEx> allOverdueManageList(
		Page<OverdueManageEx> page,
		OverdueManageEx selParam
	) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		selParam.setIsNewRecord(false);
		PageList<OverdueManageEx> pageList = (PageList<OverdueManageEx>)daoOm.allOverdueManageList(pageBounds, selParam);
		
		List<Dict> dictList = DictCache.getInstance().getList();
		
		if (ArrayHelper.isNotEmpty(pageList)) {
			for (int i = 0; i< pageList.size(); i++) {
				OverdueManageEx rec = pageList.get(i);
								
				for(Dict dict:dictList){
					if("jk_open_bank".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getBankName())){
						rec.setBankNameLabel(dict.getLabel());
					}
					if("jk_loan_apply_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getDictLoanStatus())){
						rec.setDictLoanStatusLabel(dict.getLabel());
					}
					if("jk_period_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getDictMonthStatus())){
						rec.setDictMonthStatusLabel(dict.getLabel());
					}
					if("jk_telemarketing".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getCustomerTelesalesFlag())){
						rec.setCustomerTelesalesFlagLabel(dict.getLabel());
					}
					if("jk_channel_flag".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getLoanMark())){
						rec.setLoanMarkLabel(dict.getLabel());
					}
					if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(rec.getModel())){
						rec.setModelLabel(dict.getLabel());
					}
				}
				
				String contractVersion = rec.getContractVersion();
				if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >= 4 ) {
					//1.4版本合同
					// 新违约金(滞纳金)及罚息总额(应还滞纳金 + 应还罚息)
					BigDecimal penaltyAndShould = rec.getMonthLateFee().add(rec.getMonthInterestPunishshould()); 
					rec.setPenaltyAndShould(penaltyAndShould);
					// 实还期供金额=实还分期服务费+实还本金 + 实还利息
					BigDecimal alsocontractMonthRepay = rec.getActualMonthFeeService().
						add(rec.getMonthCapitalPayactual()).
						add(rec.getMonthInterestPayactual());
					rec.setAlsocontractMonthRepay(alsocontractMonthRepay); 
					// 逾期期供金额(期供金额-实还期供金额)
					BigDecimal contractMonthRepayAmountLate = rec.getContractMonthRepayAmount().subtract(alsocontractMonthRepay);
					if(contractMonthRepayAmountLate.compareTo(bgSum) < 0){
						contractMonthRepayAmountLate = bgSum;
					}
					rec.setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
					// 实还滞纳金罚息金额=实还滞纳金 + 实还罚息
					BigDecimal alsoPenaltyInterest = rec.getActualMonthLateFee().add(rec.getMonthInterestPunishactual());
					rec.setAlsoPenaltyInterest(alsoPenaltyInterest);
					// 新减免违约金(滞纳金)罚息(减免滞纳金+减免罚息)
					BigDecimal reductionAmount = rec.getMonthLateFeeReduction().add(rec.getMonthPunishReduction());
					rec.setReductionAmount(reductionAmount);
					// 新未还违约金(滞纳金)及罚息金额(应还滞纳金-实还滞纳金 + 应还罚息-实还罚息 - 减免违约金(滞纳金)罚息) 
					BigDecimal noPenaltyInterest = rec.getMonthLateFee()
						.subtract(rec.getActualMonthLateFee())
						.add(rec.getMonthInterestPunishshould())
						.subtract(rec.getMonthInterestPunishactual())
						.subtract(rec.getMonthLateFeeReduction())
						.subtract(rec.getMonthPunishReduction());
					if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
						noPenaltyInterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())
					) {
						rec.setNoPenaltyInterest(bgSum);
					}
					if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
						noPenaltyInterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())
					) {
						rec.setNoPenaltyInterest(noPenaltyInterest);
					}
				} else {
					//1.3及之前版本合同
					// 违约金及罚息总额=应还违约金 + 应还罚息
					BigDecimal penaltyAndShould = rec.getMonthPenaltyShould().add(rec.getMonthInterestPunishshould());
					rec.setPenaltyAndShould(penaltyAndShould);
					// 实还期供金额=实还本金+实还利息
					BigDecimal alsocontractMonthRepay = rec.getMonthCapitalPayactual().add(rec.getMonthInterestPayactual());
					rec.setAlsocontractMonthRepay(alsocontractMonthRepay);
					// 逾期期供金额(期供金额-实还期供金额)
					BigDecimal contractMonthRepayAmountLate = rec.getContractMonthRepayAmount().subtract(alsocontractMonthRepay);
					if(contractMonthRepayAmountLate.compareTo(bgSum) < 0){
						contractMonthRepayAmountLate = bgSum;
					}
					rec.setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
					// 实还违约金罚息金额=实还违约金 + 实还罚息
					BigDecimal alsoPenaltyInterest = rec.getMonthPenaltyActual().add(rec.getMonthInterestPunishactual());
					rec.setAlsoPenaltyInterest(alsoPenaltyInterest);
					// 减免金额=减免违约金 + 减免罚息
					BigDecimal reductionAmount = rec.getMonthPenaltyReduction().add(rec.getMonthPunishReduction());
					rec.setReductionAmount(reductionAmount);
					  // 未还违约金及罚息
					BigDecimal noPenaltyInterest = rec.getPenaltyAndShould().
						subtract(rec.getAlsoPenaltyInterest()).
						subtract(rec.getReductionAmount());
					if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
						noPenaltyInterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())
					) {
						rec.setNoPenaltyInterest(bgSum);
					}
					if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
						noPenaltyInterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())
					) {
						rec.setNoPenaltyInterest(noPenaltyInterest);
					}
				}
			}
		}
		
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	
//	/**
//	 * 查询逾期管理列表
//	 * 2015年12月14日
//	 * By 李强
//	 * @param page
//	 * @param overdueManageEx
//	 * @return 期供逾期数据集合
//	 */
//	@Transactional(readOnly = true, value = "loanTransactionManager")
//	public List<OverdueManageEx> allOverdueManageList(OverdueManageEx overdueManageEx){
//		List<OverdueManageEx> list = daoOm.allOverdueManageList(overdueManageEx);
////		nullJudgment(list,overdueManageEx);
//		if(ArrayHelper.isNotEmpty(list)){
//			for(int i = 0;i< list.size(); i++){
//				String contractVersion = list.get(i).getContractVersion();
//				if(ContractVer.VER_ONE_FIVE.getCode().equals(contractVersion)||ContractVer.VER_ONE_FOUR.getCode().equals(contractVersion)){
//					// 新违约金(滞纳金)及罚息总额(应还滞纳金 + 应还罚息)
//					BigDecimal penaltyAndShould = list.get(i).getMonthLateFee().add(list.get(i).getMonthInterestPunishshould()); 
//					list.get(i).setPenaltyAndShould(penaltyAndShould);
//					
//					// 实还期供金额=实还分期服务费+实还本金 + 实还利息
//					BigDecimal alsocontractMonthRepay = list.get(i).getMonthFeeService().add(list.get(i).getMonthCapitalPayactual()).add(list.get(i).getMonthInterestPayactual());
//					list.get(i).setAlsocontractMonthRepay(alsocontractMonthRepay);
//					
//					// 逾期期供金额(期供金额-实还期供金额)
//					BigDecimal contractMonthRepayAmountLate = list.get(i).getContractMonthRepayAmount().subtract(alsocontractMonthRepay);
//					if(contractMonthRepayAmountLate.compareTo(bgSum) < 0){
//						contractMonthRepayAmountLate = bgSum;
//					}
//					list.get(i).setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
//					// 已还违约金(滞纳金)及罚息金额(实还滞纳金 + 实还罚息)
//					BigDecimal alsoPenaltyInterest = list.get(i).getActualMonthLateFee().add(list.get(i).getMonthInterestPunishactual());
//					list.get(i).setAlsoPenaltyInterest(alsoPenaltyInterest);
//					// 新减免违约金(滞纳金)罚息(减免滞纳金+减免罚息)
//					BigDecimal reductionAmount = list.get(i).getMonthLateFeeReduction().add(list.get(i).getMonthPunishReduction());
//					list.get(i).setReductionAmount(reductionAmount);
//					// 新未还违约金(滞纳金)及罚息金额(应还滞纳金-实还滞纳金 + 应还罚息-实还罚息 - 减免违约金(滞纳金)罚息)
//					BigDecimal noPenaltyInterest = list.get(i).getMonthLateFee()
//					.subtract(list.get(i).getActualMonthLateFee())
//					.add(list.get(i).getMonthInterestPunishshould())
//					.subtract(list.get(i).getMonthInterestPunishactual())
//					.subtract(list.get(i).getMonthLateFeeReduction())
//					.subtract(list.get(i).getMonthPunishReduction());
//					list.get(i).setNoPenaltyInterest(noPenaltyInterest);
//				}else{
//					// 违约金及罚息总额=应还违约金 + 应还罚息
//					BigDecimal penaltyAndShould = list.get(i).getMonthPenaltyShould().add(list.get(i).getMonthInterestPunishshould());
//                    list.get(i).setPenaltyAndShould(penaltyAndShould);
//					// 实还期供金额=实还本金 + 实还利息
//					BigDecimal alsocontractMonthRepay = list.get(i).getMonthCapitalPayactual().add(list.get(i).getMonthInterestPayactual());
//					list.get(i).setAlsocontractMonthRepay(alsocontractMonthRepay);
//					// 逾期期供金额(期供金额-实还期供金额)
//					BigDecimal contractMonthRepayAmountLate = list.get(i).getContractMonthRepayAmount().subtract(alsocontractMonthRepay);
//					if(contractMonthRepayAmountLate.compareTo(bgSum) < 0){
//						contractMonthRepayAmountLate = bgSum;
//					}
//					list.get(i).setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
//					// 已还违约金(滞纳金)及罚息金额(实还违约金 + 实还罚息)
//					BigDecimal alsoPenaltyInterest = list.get(i).getMonthPenaltyActual().add(list.get(i).getMonthInterestPunishactual());
//					list.get(i).setAlsoPenaltyInterest(alsoPenaltyInterest);
//					// 新减免违约金(滞纳金)罚息(减免违约金+减免罚息)
//					BigDecimal reductionAmount = list.get(i).getMonthPenaltyReduction().add(list.get(i).getMonthPunishReduction());
//					list.get(i).setReductionAmount(reductionAmount);
//					// 新未还违约金(滞纳金)及罚息金额(应还违约金-实还违约金 + 应还罚息-实还罚息 - 减免违约金(滞纳金)罚息) 
//					BigDecimal noPenaltyInterest = list.get(i).getMonthPenaltyShould()
//					.subtract(list.get(i).getMonthPenaltyActual())
//					.add(list.get(i).getMonthInterestPunishshould())
//					.subtract(list.get(i).getMonthInterestPunishactual())
//					.subtract(list.get(i).getMonthPenaltyReduction())
//					.subtract(list.get(i).getMonthPunishReduction());
//					list.get(i).setNoPenaltyInterest(noPenaltyInterest);
//				}
//			}
//		}
//		return list;
//	}
	
	/**
	 * 查询逾期管理 调整页面信息
	 * 2015年12月14日
	 * By 李强
	 * @param id
	 * @return 单条逾期数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OverdueManageEx queryOverdueManage(String id){
		OverdueManageEx overdueManage = daoOm.queryOverdueManage(id);
		SimpleDateFormat formatter = new SimpleDateFormat(DeductedConstantEx.DATES);
		String dateString = formatter.format(overdueManage.getMonthPayDay());
		overdueManage.setDateString(dateString);
		
		// 设置银行名称
		if(!ObjectHelper.isEmpty(overdueManage.getBankName())){
			overdueManage.setBankName(DeductPlatBank.getNameByCode(overdueManage.getBankName()));
		}
		List<OverdueManageEx> olist = new ArrayList<OverdueManageEx>();
		olist.add(overdueManage);
		if(overdueManage.getMonthLateFee()==null){
			overdueManage.setMonthLateFee(bgSum);
		}
		if(overdueManage.getMonthInterestPunishshould()==null){
			overdueManage.setMonthInterestPunishshould(bgSum);
		}
		if(overdueManage.getMonthInterestPunishactual()==null){
			overdueManage.setMonthInterestPunishactual(bgSum);
		}
		if(overdueManage.getMonthLateFeeReduction()==null){
			overdueManage.setMonthLateFeeReduction(bgSum);
		}
		if(overdueManage.getMonthPunishReduction()==null){
			overdueManage.setMonthPunishReduction(bgSum);
		}
		if(overdueManage.getActualMonthLateFee()==null){
			overdueManage.setActualMonthLateFee(bgSum);
		}
		if(overdueManage.getMonthPayAmount()==null){
			overdueManage.setMonthPayAmount(bgSum);
		}
		if(overdueManage.getMonthCapitalPayactual()==null){
			overdueManage.setMonthCapitalPayactual(bgSum);
		}
		if(overdueManage.getMonthInterestBackshould()==null){
			overdueManage.setMonthInterestBackshould(bgSum);
		}
		if(overdueManage.getMonthInterestPayactual()==null){
			overdueManage.setMonthInterestPayactual(bgSum);
		}
		if(overdueManage.getMonthFeeService()==null){
			overdueManage.setMonthFeeService(bgSum);
		}
		if(overdueManage.getActualMonthFeeService()==null){
			overdueManage.setActualMonthFeeService(bgSum);
		}
		if(overdueManage.getMonthPenaltyShould()==null){
			overdueManage.setMonthPenaltyShould(bgSum);
		}
		if(overdueManage.getMonthPenaltyActual()==null){
			overdueManage.setMonthPenaltyActual(bgSum);
		}
		if(overdueManage.getMonthPenaltyReduction()==null){
			overdueManage.setMonthPenaltyReduction(bgSum);
		}
		String contractVersion = overdueManage.getContractVersion();
		if(null != contractVersion && Integer.valueOf(contractVersion) >= 4){
			//1.4版本合同
			// 新违约金(滞纳金)及罚息总额(应还滞纳金 + 应还罚息)
			BigDecimal penaltyAndShould = overdueManage.getMonthLateFee().add(overdueManage.getMonthInterestPunishshould()); 
			overdueManage.setPenaltyAndShould(penaltyAndShould);
			// 已还违约金(滞纳金)及罚息金额(实还滞纳金 + 实还罚息)
			BigDecimal alsoPenaltyInterest = overdueManage.getActualMonthLateFee().add(overdueManage.getMonthInterestPunishactual());
			overdueManage.setAlsoPenaltyInterest(alsoPenaltyInterest);
			// 实还期供金额(实还本金+实还利息+实还分期服务费)
			BigDecimal alsocontractMonthRepay = overdueManage.getMonthCapitalPayactual().add(overdueManage.getMonthInterestPayactual().add(overdueManage.getActualMonthFeeService()));
			overdueManage.setAlsocontractMonthRepay(alsocontractMonthRepay);
			// 逾期期供金额(未还本金+未还利息+未还分期服务费)
			overdueManage.setContractMonthRepayAmountLate(overdueManage
					.getMonthPayAmount()
					.subtract(overdueManage.getMonthCapitalPayactual())
					.add(overdueManage.getMonthInterestBackshould())
					.subtract(overdueManage.getMonthInterestPayactual())
					.add(overdueManage.getMonthFeeService())
					.subtract(overdueManage.getActualMonthFeeService()));
			
			// 新未还违约金(滞纳金)及罚息金额(应还滞纳金-实还滞纳金 + 应还罚息-实还罚息 - 减免违约金(滞纳金)罚息) */
			BigDecimal noPenaltyinterest = overdueManage.getMonthLateFee()
					.subtract(overdueManage.getActualMonthLateFee())
					.add(overdueManage.getMonthInterestPunishshould())
					.subtract(overdueManage.getMonthInterestPunishactual())
					.subtract(overdueManage.getMonthLateFeeReduction())
					.subtract(overdueManage.getMonthPunishReduction());
			
			if(!ObjectHelper.isEmpty(noPenaltyinterest) && noPenaltyinterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())){
				overdueManage.setNoPenaltyInterest(bgSum);
		   }
			if(!ObjectHelper.isEmpty(noPenaltyinterest) && noPenaltyinterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())){
				overdueManage.setNoPenaltyInterest(noPenaltyinterest);
			}
		}else{
			//1.3版本合同
			//违约金及罚息总额=应还违约金 + 应还罚息
			BigDecimal penaltyAndShould = overdueManage.getMonthPenaltyShould().add(overdueManage.getMonthInterestPunishshould());
			overdueManage.setPenaltyAndShould(penaltyAndShould);
			
			// 实还违约金及罚息
			BigDecimal penaltyAndShouldAul = overdueManage.getMonthPenaltyActual().add(overdueManage.getMonthInterestPunishactual());
			overdueManage.setAlsoPenaltyInterest(penaltyAndShouldAul);
			// 实还期供金额(实还本金+实还利息)
			BigDecimal alsocontractMonthRepay = overdueManage.getMonthCapitalPayactual().add(overdueManage.getMonthInterestPayactual());
			overdueManage.setAlsocontractMonthRepay(alsocontractMonthRepay);
			// 逾期期供金额(未还本金+未还利息)
			overdueManage.setContractMonthRepayAmountLate(overdueManage
					.getMonthPayAmount()
					.subtract(overdueManage.getMonthCapitalPayactual())
					.add(overdueManage.getMonthInterestBackshould()
					.subtract(overdueManage.getMonthInterestPayactual())));
			
			// 减免违约金
		    BigDecimal monthPenaltyReduction = overdueManage.getMonthPenaltyReduction();
			// 减免罚息
			BigDecimal monthPunishReduction = overdueManage.getMonthPunishReduction();
			// 未还违约金及罚息
			BigDecimal noPenaltyinterest = penaltyAndShould.subtract(penaltyAndShouldAul).subtract(monthPenaltyReduction).subtract(monthPunishReduction);
			if(!ObjectHelper.isEmpty(noPenaltyinterest) && noPenaltyinterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())){
				overdueManage.setNoPenaltyInterest(bgSum);
		   }
			if(!ObjectHelper.isEmpty(noPenaltyinterest) && noPenaltyinterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())){
				overdueManage.setNoPenaltyInterest(noPenaltyinterest);
			}
		}
		return overdueManage;
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOverdueDay(OverdueManageEx overdueManageEx,PaybackOpe paybackOpe){
		try {
			daoOm.updateOverdueManage(overdueManageEx);// 淇敼鍑忓厤澶╂暟
		} catch (Exception e) {
			throw new RuntimeException(DeductedConstantEx.CENTRALIZED_DUCTION);
		}
		try {
			daoOm.insertPaybackOpe(paybackOpe);// 鎻掑叆鎿嶄綔娴佹按璁板綍
		} catch (Exception e) {
			throw new RuntimeException(DeductedConstantEx.ALSO_AMOUNT);
		}
	}
	
	/**
	 * 修改逾期管理天数
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOverdueManage(OverdueManageEx overdueManageEx){
		daoOm.updateOverdueManage(overdueManageEx);
	}
	
	/**
	 * 修改减免金额(输入的减免金额小于或等于应还违约金)
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateMonthPenaltyReduction(OverdueManageEx overdueManageEx,BigDecimal reductionAmount,String reductionReson){
		daoOm.updateMonthPenaltyReduction(overdueManageEx);
		// 向还款操作流水中插入记录
		String remark = "合同编号："+ overdueManageEx.getContractCode()+"的客户的第"+overdueManageEx.getMonths()+"期，减免金额为："+reductionAmount+"减免原因"+overdueManageEx.getReductionReson();
		PaybackOpeEx paybackOpes = new PaybackOpeEx(null, overdueManageEx.getrPaybackId(),
								RepaymentProcess.OVERDUE_CUT, TargetWay.REPAYMENT,  PaybackOperate.DERATE_SUCCEED.getName(),
									remark);
	    historyService.insertPaybackOpe(paybackOpes);
		// 判断是否全部减免，如果全部减免，则更新还款状态。 
	    BigDecimal reductionMoney =  daoOm.selectIfAllReduction(overdueManageEx.getId());
	    if(null != reductionMoney 
	    	&& reductionMoney.compareTo(bgSum) == Integer.parseInt(YESNO.NO.getCode())) {
	    	// 更新期供状态
			PaybackMonth pm = new PaybackMonth();
			pm.setId(overdueManageEx.getId());
			pm.setDictMonthStatus(PeriodStatus.PAID.getCode());
			earlyFinishConfirmDao.updatePaybackMonth(pm);
	    }
	}
	
	/**
	 * 修改减免金额((输入的减免金额大于应还违约金且小于应还违约金罚息))
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateMonthPunishReduction(OverdueManageEx overdueManageEx){
		daoOm.updateMonthPunishReduction(overdueManageEx);
	}
	
	/**
	 * 查询蓝补金额
	 * 2015年12月16日
	 * By 李强
	 * @param overdueManageEx
	 * @return 还款主表中蓝补余额数
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OverdueManageEx queryPaybackBuleAmount(OverdueManageEx overdueManageEx){
		return daoOm.queryPaybackBuleAmount(overdueManageEx);
	}
	
	/**
	 * 剩余的减免金额添加到蓝补金额
	 * 2015年12月16日
	 * By 李强
	 * @param overdueManageEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackBuleAmount(OverdueManageEx overdueManageEx){
		daoOm.updatePaybackBuleAmount(overdueManageEx);
	}
	
	/**
	 * 修改逾期天数后插入还款操作流水记录表
	 * 2015年12月15日
	 * By 李强
	 * @param paybackOpe
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertPaybackOpe(PaybackOpe paybackOpe){
		daoOm.insertPaybackOpe(paybackOpe);;
	}
	
	/**
	 * 查询客户姓名公共方法
	 * 2016年1月22日
	 * By 李强
	 * @param loanTeamOrgid
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OverdueManageEx queryCustomerName(OverdueManageEx overdueManageEx){
		return daoOm.queryCustomerName(overdueManageEx);
	}
	
	/**
	 * 批量减免
	 * 2016年3月5日
	 * By zhaojinping
	 * @param id
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String batchReduce(String  pmId,BigDecimal bgSum,String batchReson){
		try {
			List<OverdueManageEx> overdueManageExList = new ArrayList<OverdueManageEx>();
			OverdueManageEx overdueManageEx = new OverdueManageEx();
			if (StringUtils.isNotEmpty(pmId)) {
				String[] pmIdStr = pmId.split(",");
				for (int i = 0; i < pmIdStr.length; i++) {
					String pmIds = pmIdStr[i];
					if(StringUtils.isNotEmpty(pmIds)){
						if(i == 0){
							pmId = "'" + pmIds + "'";
						}else{
							pmId = pmId + ",'" + pmIds + "'";
						}
					}
				}
				overdueManageEx.setId(pmId);
			} else {
				overdueManageEx.setMonthOverdueDayMax(3);
			}
			// 查询出所有逾期天数少于3天的逾期的数据
			overdueManageExList = daoOm
					.allOverdueManageList(overdueManageEx);
//			nullJudgment(overdueManageExList, null);
			for (OverdueManageEx overdueManage : overdueManageExList) {
				// 如果批量减免少于逾期天数少于3天的数据中有借款状态为‘结清或提前结清的不进行减免’
				if(LoanApplyStatus.SETTLE.getCode().equals(overdueManage.getDictLoanStatus()) || LoanApplyStatus.EARLY_SETTLE.getCode().equals(overdueManage.getDictLoanStatus())){
					continue;
				}
				// 未还罚息(应还罚息-实还罚息-减免罚息)
				BigDecimal punishInterestNoPay = overdueManage
						.getMonthInterestPunishshould()
						.subtract(overdueManage.getMonthInterestPunishactual())
						.subtract(overdueManage.getMonthPunishReduction());
				BigDecimal penaltyNoPay = new BigDecimal("0.00");
				if (StringUtils.isNotEmpty(overdueManage.getContractVersion()) && Integer.valueOf(overdueManage.getContractVersion()) >= 4) {
					// 1.4版本合同
					// 未还滞纳金
					penaltyNoPay = overdueManage.getMonthLateFee()
							.subtract(overdueManage.getActualMonthLateFee())
							.subtract(overdueManage.getMonthLateFeeReduction());
					overdueManage.setMonthLateFeeReduction(penaltyNoPay);
				} else {
					// 未还违约金
					penaltyNoPay = overdueManage.getMonthPenaltyShould()
							.subtract(overdueManage.getMonthPenaltyActual())
							.subtract(overdueManage.getMonthPenaltyReduction());
					overdueManage.setMonthPenaltyReduction(penaltyNoPay);
				}
				overdueManage.setMonthPunishReduction(punishInterestNoPay);
				overdueManage.setReductionBy(UserUtils.getUser().getId());
				overdueManage.preUpdate();
				daoOm.batchReduce(overdueManage);
				// 向还款操作流水中插入记录
				String remark = "合同编号："+ overdueManage.getContractCode()+"的客户的第"+overdueManage.getMonths()+"期，减免金额为："+punishInterestNoPay.add(penaltyNoPay)+"减免原因："+batchReson;
				PaybackOpeEx paybackOpes = new PaybackOpeEx(null, overdueManage.getrPaybackId(),
									RepaymentProcess.OVERDUE_CUT, TargetWay.REPAYMENT,  PaybackOperate.DERATE_SUCCEED.getCode(),
									remark);
				historyService.insertPaybackOpe(paybackOpes);
				// 向还款蓝补交易明细中添加减免历史记录
				 PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
				 paybackBuleAmont.setrMonthId(overdueManage.getId());// 关连期供ID
				 paybackBuleAmont.setTradeAmount(punishInterestNoPay.add(penaltyNoPay));// 交易金额
				 paybackBuleAmont.setSurplusBuleAmount(overdueManage.getPaybackBuleAmount());// 蓝补余额
				 paybackBuleAmont.setContractCode(overdueManage.getContractCode());
				 insertPaybackBuleAmont(paybackBuleAmont);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
    public void insertPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont){
	    	paybackBuleAmont.setIsNewRecord(false);
			paybackBuleAmont.preInsert();
			paybackBuleAmont.setOperator(UserUtils.getUser().getId());// 插入操作人
			paybackBuleAmont.setTradeType(TradeType.TURN_OUT.getCode());// 交易类型
   	        paybackBuleAmont.setModifyBy(UserUtils.getUser().getName());
   	        earlyFinishConfirmDao.insertPaybackBuleAmont(paybackBuleAmont);
    }
	
	
//	/**
//	 * 金额运算时非空判断
//	 * @param loanServiceBureau
//	 * @return none
//	 */
//	public void nullJudgment(List<OverdueManageEx> slist,OverdueManageEx overdueManageEx) {
//		if (ArrayHelper.isNotEmpty(slist)) {
//			for (int i = 0; i < slist.size(); i++) {
//				// 应还本金
//				if (slist.get(i).getMonthPayAmount() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthPayAmount(bigDecimals);
//				}
//				// 应还利息
//				if (slist.get(i).getMonthInterestBackshould() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthInterestBackshould(bigDecimals);
//				}
//				// 应还罚息
//				if (slist.get(i).getMonthInterestPunishshould() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthInterestPunishshould(bigDecimals);
//				}
//				// 应还违约金
//				if (slist.get(i).getMonthPenaltyShould() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthPenaltyShould(bigDecimals);
//				}
//				// 应还滞纳金
//				if (slist.get(i).getMonthLateFee() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthLateFee(bigDecimals);
//				}
//				// 应还分期服务费
//				if (slist.get(i).getMonthFeeService() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthFeeService(bigDecimals);
//				}
//			
//				// 实还本金
//				if (slist.get(i).getMonthCapitalPayactual() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthCapitalPayactual(bigDecimals);
//				}
//				// 实还利息
//				if (slist.get(i).getMonthInterestPayactual() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthInterestPayactual(bigDecimals);
//				}
//				// 实还违约金
//				if (slist.get(i).getMonthPenaltyActual() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthPenaltyActual(bigDecimals);
//				}
//				// 实还罚息
//				if (slist.get(i).getMonthInterestPunishactual() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthInterestPunishactual(bigDecimals);
//				}
//				// 实还滞纳金
//				if (slist.get(i).getActualMonthLateFee() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setActualMonthLateFee(bigDecimals);
//				}
//				// 实还分期服务费
//				if (slist.get(i).getActualMonthFeeService() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setActualMonthFeeService(bigDecimals);
//				}
//				// 减免违约金
//				if (slist.get(i).getMonthPenaltyReduction() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthPenaltyReduction(bigDecimals);
//				}
//				// 减免罚息
//				if (slist.get(i).getMonthPunishReduction() == null) {
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthPunishReduction(bigDecimals);
//				}
//				// 减免滞纳金
//				if(slist.get(i).getMonthLateFeeReduction() == null){
//					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setMonthLateFeeReduction(bigDecimals);
//				}
//				// 期供金额
//				if(slist.get(i).getContractMonthRepayAmount() == null){
//					BigDecimal contractMonthRepayAmount = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//					slist.get(i).setContractMonthRepayAmount(contractMonthRepayAmount);
//				}
//			}
//		}
//		if(!ObjectHelper.isEmpty(overdueManageEx)){
//		// 应还罚息
//		if (overdueManageEx.getMonthInterestPunishshould() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthInterestPunishshould(bigDecimals);
//		}
//		// 应还违约金
//		if (overdueManageEx.getMonthPenaltyShould() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthPenaltyShould(bigDecimals);
//		}
//		// 应还滞纳金
//		if (overdueManageEx.getMonthLateFee() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthLateFee(bigDecimals);
//		}
//		// 期供金额
//		if(overdueManageEx.getContractMonthRepayAmount() == null){
//			BigDecimal contractMonthRepayAmount = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setContractMonthRepayAmount(contractMonthRepayAmount);
//		}
//		// 实还罚息
//		if (overdueManageEx.getMonthInterestPunishactual() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthInterestPunishactual(bigDecimals);
//		}
//		// 实还滞纳金
//		if (overdueManageEx.getActualMonthLateFee() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setActualMonthLateFee(bigDecimals);
//		}
//		// 减免罚息
//		if (overdueManageEx.getMonthPunishReduction() == null) {
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthPunishReduction(bigDecimals);
//		}
//		// 减免滞纳金
//		if(overdueManageEx.getMonthLateFeeReduction() == null){
//			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
//			overdueManageEx.setMonthLateFeeReduction(bigDecimals);
//		}
//		}
//	}
}
