package com.creditharmony.loan.borrow.blue.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.JkProducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PosCardInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 还款蓝补表
 * 
 * @Class Name PaybackBlue
 * @author 侯志斌
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class PaybackBlue extends DataEntity<PaybackBlue> {
	// 置顶静态
		public static final int TOP_FLAG_NO = 0;
		public static final int TOP_FLAG = 1;

		// 冲抵期供查询静态变量
		public static final String REPAYMENT_FLAG = "2";
		public static final String OVERDUE_FLAG = "3";

		// 合同编号
		private String contractCode;
		// 客户编码
		private String customerCode;
		private String customerName;
		// 当前第几期
		private int paybackCurrentMonth;
		// 期供
		private BigDecimal paybackMonthAmount;
		// 蓝补金额
		private BigDecimal paybackBuleAmount;
		// 是否有效
		private String effectiveFlag;
		// 还款状态
		private String dictPayStatus;
		// 账单日
		private String paybackDay;
		// 账单日查询
		private String paybackDayNum;
		// 最长逾期天数
		private int paybackMaxOverduedays;
		// 返款金额
		private BigDecimal paybackBackAmount;
		// 减免人
		private String remissionBy;
		// 合同表
		private Contract contract;
		// 客户信息表
		private LoanCustomer loanCustomer;
		// 借款信息表
		private LoanInfo loanInfo;
		// 借款账户信息表
		private LoanBank loanBank;
		// 期供表
		private PaybackMonth paybackMonth;
		/*// 银行账款信息表
		private PaybackTransferInfo paybackTransferInfo;*/
		/*// 还款申请表
		private PaybackApply paybackApply;*/
		// 放款表
		private LoanGrant loanGrant;
		// 催收服务费
		private UrgeServicesMoney urgeServicesMoney;
		// 产品表
		private JkProducts jkProducts;
		// 冲抵申请表
		private PaybackCharge paybackCharge;
		// POS机刷卡查账从表
		private PosCardInfo posCardInfo;
		// 门店名
		private String orgName;	
		private String orgId;
		private String[] orgList;
		
		private String paybackDate;
		private String[] paybackDateList;
		private String loanStatus;
		private String loanFlag;
		
		private Integer limit;
		private Integer offset;
		private Integer cnt;

		public PosCardInfo getPosCardInfo() {
			return posCardInfo;
		}

		public void setPosCardInfo(PosCardInfo posCardInfo) {
			this.posCardInfo = posCardInfo;
		}

		public JkProducts getJkProducts() {
			return jkProducts;
		}

		public void setJkProducts(JkProducts jkProducts) {
			this.jkProducts = jkProducts;
		}

		public String getContractCode() {
			return contractCode;
		}

		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}

		public String getCustomerCode() {
			return customerCode;
		}

		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}

		public int getPaybackCurrentMonth() {
			return paybackCurrentMonth;
		}

		public void setPaybackCurrentMonth(int paybackCurrentMonth) {
			this.paybackCurrentMonth = paybackCurrentMonth;
		}

		public BigDecimal getPaybackMonthAmount() {
			return paybackMonthAmount;
		}

		public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
			this.paybackMonthAmount = paybackMonthAmount;
		}

		public BigDecimal getPaybackBuleAmount() {
			return paybackBuleAmount;
		}

		public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
			this.paybackBuleAmount = paybackBuleAmount;
		}

		public String getPaybackDayNum() {
			return paybackDayNum;
		}

		public void setPaybackDayNum(String paybackDayNum) {
			this.paybackDayNum = paybackDayNum;
		}

		public String getEffectiveFlag() {
			return effectiveFlag;
		}

		public void setEffectiveFlag(String effectiveFlag) {
			this.effectiveFlag = effectiveFlag;
		}

		public String getDictPayStatus() {
			return dictPayStatus;
		}

		public void setDictPayStatus(String dictPayStatus) {
			this.dictPayStatus = dictPayStatus;
		}

		public String getPaybackDay() {
			return paybackDay;
		}

		public void setPaybackDay(String paybackDay) {
			this.paybackDay = paybackDay;
		}

		public int getPaybackMaxOverduedays() {
			return paybackMaxOverduedays;
		}

		public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
			this.paybackMaxOverduedays = paybackMaxOverduedays;
		}

		public BigDecimal getPaybackBackAmount() {
			return paybackBackAmount;
		}

		public void setPaybackBackAmount(BigDecimal paybackBackAmount) {
			this.paybackBackAmount = paybackBackAmount;
		}

		public String getRemissionBy() {
			return remissionBy;
		}

		public void setRemissionBy(String remissionBy) {
			this.remissionBy = remissionBy;
		}

		public Contract getContract() {
			return contract;
		}

		public void setContract(Contract contract) {
			this.contract = contract;
		}

		public LoanCustomer getLoanCustomer() {
			return loanCustomer;
		}

		public void setLoanCustomer(LoanCustomer loanCustomer) {
			this.loanCustomer = loanCustomer;
		}

		public LoanInfo getLoanInfo() {
			return loanInfo;
		}

		public void setLoanInfo(LoanInfo loanInfo) {
			this.loanInfo = loanInfo;
		}

		public LoanBank getLoanBank() {
			return loanBank;
		}

		public void setLoanBank(LoanBank loanBank) {
			this.loanBank = loanBank;
		}

		public PaybackMonth getPaybackMonth() {
			return paybackMonth;
		}

		public void setPaybackMonth(PaybackMonth paybackMonth) {
			this.paybackMonth = paybackMonth;
		}

		/*public PaybackTransferInfo getPaybackTransferInfo() {
			return paybackTransferInfo;
		}

		public void setPaybackTransferInfo(PaybackTransferInfo paybackTransferInfo) {
			this.paybackTransferInfo = paybackTransferInfo;
		}

		public PaybackApply getPaybackApply() {
			return paybackApply;
		}

		public void setPaybackApply(PaybackApply paybackApply) {
			this.paybackApply = paybackApply;
		}*/

		public LoanGrant getLoanGrant() {
			return loanGrant;
		}

		public void setLoanGrant(LoanGrant loanGrant) {
			this.loanGrant = loanGrant;
		}

		public UrgeServicesMoney getUrgeServicesMoney() {
			return urgeServicesMoney;
		}

		public void setUrgeServicesMoney(UrgeServicesMoney urgeServicesMoney) {
			this.urgeServicesMoney = urgeServicesMoney;
		}

		public PaybackCharge getPaybackCharge() {
			return paybackCharge;
		}

		public void setPaybackCharge(PaybackCharge paybackCharge) {
			this.paybackCharge = paybackCharge;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getOrgId() {
			return orgId;
		}

		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}

		public String getPaybackDate() {
			return paybackDate;
		}

		public void setPaybackDate(String paybackDate) {
			this.paybackDate = paybackDate;
		}

		public String getLoanStatus() {
			return loanStatus;
		}

		public void setLoanStatus(String loanStatus) {
			this.loanStatus = loanStatus;
		}

		public String getLoanFlag() {
			return loanFlag;
		}

		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}

		public String[] getOrgList() {
			return orgList;
		}

		public void setOrgList(String[] orgList) {
			this.orgList = orgList;
		}

		public String[] getPaybackDateList() {
			return paybackDateList;
		}

		public void setPaybackDateList(String[] paybackDateList) {
			this.paybackDateList = paybackDateList;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public Integer getCnt() {
			return cnt;
		}

		public void setCnt(Integer cnt) {
			this.cnt = cnt;
		}
		
		
}

