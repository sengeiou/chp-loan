package com.creditharmony.loan.borrow.payback.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.EarlySettlementDao;
import com.creditharmony.loan.borrow.payback.entity.ex.EarlySettlementEx;

/**
 * 提前结清已办
 * 
 * @Class Name EarlySettlementHaveTodoService
 * @author 李强
 * @Create In 2015年11月日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class EarlySettlementService {
	@Autowired
	private EarlySettlementDao earlySettlementHaveTodoDao;

	/**
	 * 查询提前结清已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param earlySettlement
	 * @return 提前结清已办List
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<EarlySettlementEx> allEarlySettlementList(Page<EarlySettlementEx> page,EarlySettlementEx earlySettlement) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<EarlySettlementEx> pageList = (PageList<EarlySettlementEx>)earlySettlementHaveTodoDao.allEarlySettlementList(pageBounds,earlySettlement);
		PageUtil.convertPage(pageList, page);
		return page;
	};

	/**
	 * 查看 提前结清已办信息
	 * 2015年12月17日
	 * By 李强
	 * @param earlySettlement
	 * @return 结清已办单个对象数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public EarlySettlementEx seeEarlySettlement(
			EarlySettlementEx earlySettlement) {
		BigDecimal bgSum = new BigDecimal("0.00");
		EarlySettlementEx earlySettlements = earlySettlementHaveTodoDao.seeEarlySettlement(earlySettlement);
		EarlySettlementEx earlySettlementsInfo = getPunishPenaltySum(earlySettlements.getContractCode());
        if(!ObjectHelper.isEmpty(earlySettlementsInfo)){
		// 实还分期服务费总额
		BigDecimal actualMonthFeeServiceSum = earlySettlementsInfo
				.getActualMonthFeeServiceSum();
		// 实还滞纳金总额
		BigDecimal actualMonthLateFeeSum = earlySettlementsInfo
				.getActualMonthLateFeeSum();
		// 实还本金总额
		BigDecimal monthCapitalPayactualSum = earlySettlementsInfo
				.getMonthCapitalPayactualSum();
		// 实还利息总额
		BigDecimal monthInterestPayactualSum = earlySettlementsInfo
				.getMonthInterestPayactualSum();
		// 实还利息总额
		BigDecimal monthInterestPunishActualSum = earlySettlementsInfo
				.getMonthInterestPunishActualSum();
		// 减免滞纳金总额
		BigDecimal monthLateFeeReductionSum = earlySettlementsInfo
				.getMonthLateFeeReductionSum();
		// 实还违约金总额
		BigDecimal monthPenaltyActualSum = earlySettlementsInfo
				.getMonthPenaltyActualSum();
		// 减免罚息总和
		BigDecimal monthPunishReductionSum = earlySettlementsInfo
				.getMonthPunishReductionSum();
		// 减免违约金总额
		BigDecimal monthPenaltyReductionSum = earlySettlementsInfo
				.getMonthPenaltyReductionSum();

		actualMonthFeeServiceSum = actualMonthFeeServiceSum == null ? bgSum
				: actualMonthFeeServiceSum;
		actualMonthLateFeeSum = actualMonthLateFeeSum == null ? bgSum
				: actualMonthLateFeeSum;
		monthCapitalPayactualSum = monthCapitalPayactualSum == null ? bgSum
				: monthCapitalPayactualSum;
		monthInterestPayactualSum = monthInterestPayactualSum == null ? bgSum
				: monthInterestPayactualSum;
		monthInterestPunishActualSum = monthInterestPunishActualSum == null ? bgSum
				: monthInterestPunishActualSum;
		monthLateFeeReductionSum = monthLateFeeReductionSum == null ? bgSum
				: monthLateFeeReductionSum;
		monthPenaltyActualSum = monthPenaltyActualSum == null ? bgSum
				: monthPenaltyActualSum;
		monthPunishReductionSum = monthPunishReductionSum == null ? bgSum
				: monthPunishReductionSum;
		monthPenaltyReductionSum = monthPenaltyReductionSum == null ? bgSum
				: monthPenaltyReductionSum;
			String contractVersion = earlySettlementsInfo.getContractVersion();
			//if(StringUtils.isNotEmpty(contractVersion)){
				if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
		        //1.4版本合同
					// 已还期供金额 = 实还本金+实还利息+实还分期服务费
					BigDecimal hisOverpaybackMonthMoney = monthCapitalPayactualSum.add(monthInterestPayactualSum).add(actualMonthFeeServiceSum);
					earlySettlements.setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
					// 已还违约金及罚息金额 = 实还违约金+实还罚息+实还滞纳金
					earlySettlements.setHisOverLiquidatedPenalty(monthPenaltyActualSum.add(monthInterestPunishActualSum).add(actualMonthLateFeeSum));
		            // 减免违约金及罚息总额 
				    earlySettlements.setMonthPenaltyPunishReductionSum(monthPunishReductionSum.add(monthPenaltyReductionSum).add(monthLateFeeReductionSum));
		        }else{
			   // 1.3及之前版本合同
			   // 已还期供金额 = 实还本金+实还利息
			   BigDecimal hisOverpaybackMonthMoney = monthCapitalPayactualSum.add(monthInterestPayactualSum);
			   earlySettlements.setHisOverpaybackMonthMoney(hisOverpaybackMonthMoney);
			   // 已还违约金及罚息金额 = 实还违约金+实还罚息
			   earlySettlements.setHisOverLiquidatedPenalty(monthPenaltyActualSum.add(monthInterestPunishActualSum));
			// 减免违约金总额
			   earlySettlements.setMonthPenaltyPunishReductionSum(monthPunishReductionSum.add(monthPenaltyReductionSum));
		      }
			//}
        }
		return earlySettlements;
	}
	/**
	 * 获取减免的违约金罚息总金额
	 * 2016年3月5日
	 * By zhaojinping
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public EarlySettlementEx getPunishPenaltySum(String contractCode){
		return earlySettlementHaveTodoDao.getPunishPenaltySum(contractCode);
	}
	
	/**
	 * 查询提前结清已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param earlySettlement
	 * @return 提前结清已办List
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<EarlySettlementEx> allEarlySettlementList(EarlySettlementEx earlySettlement) {
		List<EarlySettlementEx> pageList = earlySettlementHaveTodoDao.allEarlySettlementList(earlySettlement);
		return pageList;
	};
}
