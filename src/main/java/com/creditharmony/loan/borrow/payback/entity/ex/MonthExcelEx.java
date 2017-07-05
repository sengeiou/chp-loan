package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 
 * @Class Name ManualOperation
 * @author 李强
 * @Create In 2015年12月17日
 */
@SuppressWarnings("serial")
public class MonthExcelEx extends DataEntity<MonthExcelEx>  {
	
		private String id;
		// 合同编号
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
		private String contractCode;	
		// 客户姓名
		@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
		private String customerName;
		// 产品类型
		@ExcelField(title = "产品类型", type = 0, align = 2, sort = 30)
		private String productType;	
		// 账单日
		@ExcelField(title = "账单日", type = 0, align = 2, sort = 40)
		private Date payBack;
		// 期供金额、账单金额
		@ExcelField(title = "期供金额", type = 0, align = 2, sort = 50)
		private BigDecimal contractMonthRepayAmount;	
		// 当期已还金额
		@ExcelField(title = "当期已还金额", type = 0, align = 2, sort = 60)
		private BigDecimal hisOverpaybackMonthMoney;	
		// 当期未还金额
		@ExcelField(title = "当期未还金额", type = 0, align = 2, sort = 70)
		private BigDecimal notOverpaybackMonthMoney;	
		// 提前结清一次性金额
		@ExcelField(title = "提前结清一次性金额", type = 0, align = 2, sort = 80)
		private BigDecimal monthBeforeFinishAmount;	
		// 应还违约金及罚息
		@ExcelField(title = "应还违约金及罚息", type = 0, align = 2, sort = 90)
		private BigDecimal interestPenaltyPunishShould;	
		// 未还违约金及罚息
		@ExcelField(title = "未还违约金及罚息", type = 0, align = 2, sort = 100)
		private BigDecimal notPenaltyPunishShould;	
		// 减免违约金罚息总额
		@ExcelField(title = "减免违约金罚息总额", type = 0, align = 2, sort = 110)
		private BigDecimal penaltyPunishReductionSum;
		// 实还违约金罚息总额
		@ExcelField(title = "实还违约金罚息总额", type = 0, align = 2, sort = 120)
		private BigDecimal penaltyPunishActualSum;
		// 当期逾期天数
		@ExcelField(title = "当期逾期天数", type = 0, align = 2, sort = 130)
		private String monthOverdueDays;	
		// 最终期供状态
		@ExcelField(title = "最终期供状态", type = 0, align = 2, sort = 140,dictType="jk_period_status")
		private String dictMonthStatus;		
		// 标识
		@ExcelField(title = "标识", type = 0, align = 2, sort = 150,dictType="jk_channel_flag")
		private String loanMark;	
		// 实还本金
		private BigDecimal monthCapitalPayactual;
		// 实还利息
		private BigDecimal monthInterestPayactual;		
		// 应还本金
		private BigDecimal monthPayAmount;		
		// 应还利息
		private BigDecimal monthInterestBackshould;	
		// 应还违约金
		private BigDecimal monthPenaltyShould;		
		// 应还罚息
		private BigDecimal monthInterestPunishshould;	
		// 实还罚息
		private BigDecimal monthInterestPunishactual;	
		// 实还违约金
		private BigDecimal monthPenaltyActual;
		// 减免违约金
		private BigDecimal monthPenaltyReduction;
		// 减免罚息
		private BigDecimal monthPunishReduction;
		// 分期服务费   
		private BigDecimal monthFeeService;
		// 分期服务费实还金额 
		private BigDecimal actualmonthFeeService;
		// 滞纳金
		private BigDecimal monthLateFee;
		// 实还滞纳金
		private BigDecimal actualMonthLateFee;
		// 减免滞纳金
		private BigDecimal monthLateFeeReduction;
		// 合同版本号
		private String contractVersion;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
		public Date getPayBack() {
			return payBack;
		}
		public void setPayBack(Date payBack) {
			this.payBack = payBack;
		}
		public BigDecimal getContractMonthRepayAmount() {
			return contractMonthRepayAmount;
		}
		public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
			this.contractMonthRepayAmount = contractMonthRepayAmount;
		}
		public BigDecimal getHisOverpaybackMonthMoney() {
			return hisOverpaybackMonthMoney;
		}
		public void setHisOverpaybackMonthMoney(BigDecimal hisOverpaybackMonthMoney) {
			this.hisOverpaybackMonthMoney = hisOverpaybackMonthMoney;
		}
		public BigDecimal getNotOverpaybackMonthMoney() {
			return notOverpaybackMonthMoney;
		}
		public void setNotOverpaybackMonthMoney(BigDecimal notOverpaybackMonthMoney) {
			this.notOverpaybackMonthMoney = notOverpaybackMonthMoney;
		}
		public BigDecimal getMonthBeforeFinishAmount() {
			return monthBeforeFinishAmount;
		}
		public void setMonthBeforeFinishAmount(BigDecimal monthBeforeFinishAmount) {
			this.monthBeforeFinishAmount = monthBeforeFinishAmount;
		}
		public BigDecimal getInterestPenaltyPunishShould() {
			return interestPenaltyPunishShould;
		}
		public void setInterestPenaltyPunishShould(
				BigDecimal interestPenaltyPunishShould) {
			this.interestPenaltyPunishShould = interestPenaltyPunishShould;
		}
		public BigDecimal getNotPenaltyPunishShould() {
			return notPenaltyPunishShould;
		}
		public void setNotPenaltyPunishShould(BigDecimal notPenaltyPunishShould) {
			this.notPenaltyPunishShould = notPenaltyPunishShould;
		}
		public String getMonthOverdueDays() {
			return monthOverdueDays;
		}
		public void setMonthOverdueDays(String monthOverdueDays) {
			this.monthOverdueDays = monthOverdueDays;
		}
		public String getDictMonthStatus() {
			return dictMonthStatus;
		}
		public void setDictMonthStatus(String dictMonthStatus) {
			this.dictMonthStatus = dictMonthStatus;
		}
		public String getLoanMark() {
			return loanMark;
		}
		public void setLoanMark(String loanMark) {
			this.loanMark = loanMark;
		}
		public BigDecimal getMonthCapitalPayactual() {
			return monthCapitalPayactual;
		}
		public void setMonthCapitalPayactual(BigDecimal monthCapitalPayactual) {
			this.monthCapitalPayactual = monthCapitalPayactual;
		}
		public BigDecimal getMonthInterestPayactual() {
			return monthInterestPayactual;
		}
		public void setMonthInterestPayactual(BigDecimal monthInterestPayactual) {
			this.monthInterestPayactual = monthInterestPayactual;
		}
		public BigDecimal getMonthPayAmount() {
			return monthPayAmount;
		}
		public void setMonthPayAmount(BigDecimal monthPayAmount) {
			this.monthPayAmount = monthPayAmount;
		}
		public BigDecimal getMonthInterestBackshould() {
			return monthInterestBackshould;
		}
		public void setMonthInterestBackshould(BigDecimal monthInterestBackshould) {
			this.monthInterestBackshould = monthInterestBackshould;
		}
		public BigDecimal getMonthPenaltyShould() {
			return monthPenaltyShould;
		}
		public void setMonthPenaltyShould(BigDecimal monthPenaltyShould) {
			this.monthPenaltyShould = monthPenaltyShould;
		}
		public BigDecimal getMonthInterestPunishshould() {
			return monthInterestPunishshould;
		}
		public void setMonthInterestPunishshould(BigDecimal monthInterestPunishshould) {
			this.monthInterestPunishshould = monthInterestPunishshould;
		}
		public BigDecimal getMonthInterestPunishactual() {
			return monthInterestPunishactual;
		}
		public void setMonthInterestPunishactual(BigDecimal monthInterestPunishactual) {
			this.monthInterestPunishactual = monthInterestPunishactual;
		}
		public BigDecimal getMonthPenaltyActual() {
			return monthPenaltyActual;
		}
		public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
			this.monthPenaltyActual = monthPenaltyActual;
		}
		public BigDecimal getPenaltyPunishReductionSum() {
			return penaltyPunishReductionSum;
		}
		public void setPenaltyPunishReductionSum(BigDecimal penaltyPunishReductionSum) {
			this.penaltyPunishReductionSum = penaltyPunishReductionSum;
		}
		public BigDecimal getPenaltyPunishActualSum() {
			return penaltyPunishActualSum;
		}
		public void setPenaltyPunishActualSum(BigDecimal penaltyPunishActualSum) {
			this.penaltyPunishActualSum = penaltyPunishActualSum;
		}
		public BigDecimal getMonthPenaltyReduction() {
			return monthPenaltyReduction;
		}
		public void setMonthPenaltyReduction(BigDecimal monthPenaltyReduction) {
			this.monthPenaltyReduction = monthPenaltyReduction;
		}
		public BigDecimal getMonthPunishReduction() {
			return monthPunishReduction;
		}
		public void setMonthPunishReduction(BigDecimal monthPunishReduction) {
			this.monthPunishReduction = monthPunishReduction;
		}
		public BigDecimal getMonthFeeService() {
			return monthFeeService;
		}
		public void setMonthFeeService(BigDecimal monthFeeService) {
			this.monthFeeService = monthFeeService;
		}
		public BigDecimal getActualmonthFeeService() {
			return actualmonthFeeService;
		}
		public void setActualmonthFeeService(BigDecimal actualmonthFeeService) {
			this.actualmonthFeeService = actualmonthFeeService;
		}
		public BigDecimal getMonthLateFee() {
			return monthLateFee;
		}
		public void setMonthLateFee(BigDecimal monthLateFee) {
			this.monthLateFee = monthLateFee;
		}
		public BigDecimal getActualMonthLateFee() {
			return actualMonthLateFee;
		}
		public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
			this.actualMonthLateFee = actualMonthLateFee;
		}
		public BigDecimal getMonthLateFeeReduction() {
			return monthLateFeeReduction;
		}
		public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
			this.monthLateFeeReduction = monthLateFeeReduction;
		}
		public String getContractVersion() {
			return contractVersion;
		}
		public void setContractVersion(String contractVersion) {
			this.contractVersion = contractVersion;
		}	
		
}
