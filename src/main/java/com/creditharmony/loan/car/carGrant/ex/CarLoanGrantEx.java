/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.entity.LoanGrantEx.java
 * @Create By 张振强
 * @Create In 2015年12月1日 下午7:25:19
 */
/**
 * @Class Name LoanGrantEx
 * @author 张振强
 * @Create In 2015年12月1日
 * 借款记录表的扩展类
 */
package com.creditharmony.loan.car.carGrant.ex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class CarLoanGrantEx extends DataEntity<CarLoanGrantEx> {
	// 放款id
		private String id;
		// 流程id
		private String applyId;
		// 借款主表id
		private String loanInfoId;
		// 合同编号6
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 60)
		private String contractCode;
		// 借款编号
		private String loanCode;
		// 中间人id
		private String midId;
		// 
		private String dictLoanType;
		// 放款金额8
		@ExcelField(title = "放款金额", type = 0, align = 2, sort = 80)
		private BigDecimal grantAmount;
		// 放款时间17
		@ExcelField(title = "放款时间", type = 0, align = 2, sort = 170)
		private Date lendingTime;
		// 放款人员编号
		private String lendingUserId;
		// 放款回执结果
		private String grantRecepicResult;
		// 放款方式
		private String dictLoanWay;
	    // 失败原因
		private String grantFailResult;
	    // 退回原因
		private String grantBackMes;
	    // 审核专员18
		@ExcelField(title = "放款审核人员", type = 0, align = 2, sort = 180)
		private String checkEmpId;
		// 审核结果
		private String checkResult;
		// 审核时间
		private String checkTime;
		// 客户姓名1
		@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10)
		private String customerName;
		// 证件号码2
		@ExcelField(title = "证件号码", type = 0, align = 2, sort = 20)
		private String customerCertNum;
		// 标识19
		@ExcelField(title = "标识", type = 0, align = 2, sort = 190,dictType="jk_channel_flag")
		private String loanFlag;
		// 是否电销20，新
		@ExcelField(title = "是否电销", type = 0, align = 2, sort = 200,dictType="jk_telemarketing ")
		private String customerTelesalesFlag;	
		// 共借人
		private String coboName;
		// 中间人姓名13
		@ExcelField(title = "放款账户", type = 0, align = 2, sort = 130)
		private String middleName;
		// 中间人开户行14
		@ExcelField(title = "开户行", type = 0, align = 2, sort = 140)
		private String midBankName;
		// 中间人15
		@ExcelField(title = "账号", type = 0, align = 2, sort = 150)
		private String bankCardNo;
		// 催收服务费
		private BigDecimal urgeMoney;
		// 批借金额9,新
		@ExcelField(title = "批借金额", type = 0, align = 2, sort = 90)
		private BigDecimal auditAmount;
		// 信访费10，新
		@ExcelField(title = "信访费", type = 0, align = 2, sort = 100)
		private BigDecimal feePetition;	
		// 产品类型11
		@ExcelField(title = "产品类型", type = 0, align = 2, sort = 110)
		private String productType;	
		// 批借期限12
		@ExcelField(title = "批借期限", type = 0, align = 2, sort = 120)
		private String contractMonths;
		// 已划金额
		private BigDecimal urgeDecuteMoeny;
		// 合同金额7
		@ExcelField(title = "合同金额", type = 0, align = 2, sort = 70)
		private BigDecimal contractAmount;
		// 团队经理3
		@ExcelField(title = "团队经理", type = 0, align = 2, sort = 30)
		private String loanTeamManagercode;
		// 借款状态
		private String dictLoanStatus;
		// 借款状态集合
		private List<String> dictLoanStatusList = new ArrayList<String>();
		// 门店编码16
		@ExcelField(title = "机构", type = 0, align = 2, sort = 160)
		private String storesCode;
		// 借款类型5
		@ExcelField(title = "借款类型", type = 0, align = 2, sort = 50)
		private String classType;
		// 客服人员4
		@ExcelField(title = "客服人员", type = 0, align = 2, sort = 40)
		private String loanCustomerService;
		// 是否追加借
		private String dictIsAdditional;
		// 产品名称
		private String productName;
		// 产品Code
		private String productCode;
		
		// 规则
		private String rule;
	    // 放款批次
		private String grantPch;
		// 批次提交时间
		private Date submissionDate;
		// 客户开卡行
		private String cardBank;

		//合同版本号
		private String contractVersion;
		
		
		public String getLoanInfoId() {
			return loanInfoId;
		}

		public void setLoanInfoId(String loanInfoId) {
			this.loanInfoId = loanInfoId;
		}

		public String getApplyId() {
			return applyId;
		}

		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMidId() {
			return midId;
		}

		public void setMidId(String midId) {
			this.midId = midId;
		}

		public String getDictLoanType() {
			return dictLoanType;
		}

		public void setDictLoanType(String dictLoanType) {
			this.dictLoanType = dictLoanType;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getCustomerCertNum() {
			return customerCertNum;
		}

		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}

		public String getCustomerTelesalesFlag() {
			return customerTelesalesFlag;
		}

		public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
			this.customerTelesalesFlag = customerTelesalesFlag;
		}

		public BigDecimal getAuditAmount() {
			return auditAmount;
		}

		public void setAuditAmount(BigDecimal auditAmount) {
			this.auditAmount = auditAmount;
		}

		public BigDecimal getFeePetition() {
			return feePetition;
		}

		public void setFeePetition(BigDecimal feePetition) {
			this.feePetition = feePetition;
		}

		public String getLoanFlag() {
			return loanFlag;
		}

		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}

		public String getCoboName() {
			return coboName;
		}

		public void setCoboName(String coboName) {
			this.coboName = coboName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getMidBankName() {
			return midBankName;
		}

		public void setMidBankName(String midBankName) {
			this.midBankName = midBankName;
		}


		public String getBankCardNo() {
			return bankCardNo;
		}

		public void setBankCardNo(String bankCardNo) {
			this.bankCardNo = bankCardNo;
		}


		public String getLoanTeamManagercode() {
			return loanTeamManagercode;
		}

		public void setLoanTeamManagercode(String loanTeamManagercode) {
			this.loanTeamManagercode = loanTeamManagercode;
		}

		public String getDictLoanStatus() {
			return dictLoanStatus;
		}

		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}

		public List<String> getDictLoanStatusList() {
	        return dictLoanStatusList;
	    }

	    public void setDictLoanStatusList(List<String> dictLoanStatusList) {
	        this.dictLoanStatusList = dictLoanStatusList;
	    }

	    public String getStoresCode() {
			return storesCode;
		}

		public void setStoresCode(String storesCode) {
			this.storesCode = storesCode;
		}

		public String getClassType() {
			return classType;
		}

		public void setClassType(String classType) {
			this.classType = classType;
		}

		public String getLoanCustomerService() {
			return loanCustomerService;
		}

		public void setLoanCustomerService(String loanCustomerService) {
			this.loanCustomerService = loanCustomerService;
		}

		public String getDictIsAdditional() {
			return dictIsAdditional;
		}

		public void setDictIsAdditional(String dictIsAdditional) {
			this.dictIsAdditional = dictIsAdditional;
		}

		public BigDecimal getUrgeMoney() {
			return urgeMoney;
		}

		public void setUrgeMoney(BigDecimal urgeMoney) {
			this.urgeMoney = urgeMoney;
		}

		public String getProductType() {
			return productType;
		}

		public void setProductType(String productType) {
			this.productType = productType;
		}

		public BigDecimal getUrgeDecuteMoeny() {
			return urgeDecuteMoeny;
		}

		public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
			this.urgeDecuteMoeny = urgeDecuteMoeny;
		}

		public BigDecimal getContractAmount() {
			return contractAmount;
		}

		public void setContractAmount(BigDecimal contractAmount) {
			this.contractAmount = contractAmount;
		}

		public String getContractMonths() {
			return contractMonths;
		}

		public void setContractMonths(String contractMonths) {
			this.contractMonths = contractMonths;
		}

		public String getContractCode() {
			return contractCode;
		}

		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}

		public String getLoanCode() {
			return loanCode;
		}

		public void setLoanCode(String loanCode) {
			this.loanCode = loanCode;
		}

		public BigDecimal getGrantAmount() {
			return grantAmount;
		}

		public void setGrantAmount(BigDecimal grantAmount) {
			this.grantAmount = grantAmount;
		}

		public Date getLendingTime() {
			return lendingTime;
		}

		public void setLendingTime(Date lendingTime) {
			this.lendingTime = lendingTime;
		}

		public String getLendingUserId() {
			return lendingUserId;
		}

		public void setLendingUserId(String lendingUserId) {
			this.lendingUserId = lendingUserId;
		}

		public String getGrantRecepicResult() {
			return grantRecepicResult;
		}

		public void setGrantRecepicResult(String grantRecepicResult) {
			this.grantRecepicResult = grantRecepicResult;
		}

		public String getGrantFailResult() {
			return grantFailResult;
		}

		public void setGrantFailResult(String grantFailResult) {
			this.grantFailResult = grantFailResult;
		}

		public String getGrantBackMes() {
			return grantBackMes;
		}

		public void setGrantBackMes(String grantBackMes) {
			this.grantBackMes = grantBackMes;
		}

		public String getCheckEmpId() {
			return checkEmpId;
		}

		public void setCheckEmpId(String checkEmpId) {
			this.checkEmpId = checkEmpId;
		}

		public String getCheckResult() {
			return checkResult;
		}

		public void setCheckResult(String checkResult) {
			this.checkResult = checkResult;
		}

		public String getCheckTime() {
			return checkTime;
		}

		public void setCheckTime(String checkTime) {
			this.checkTime = checkTime;
		}

		public String getDictLoanWay() {
			return dictLoanWay;
		}

		public void setDictLoanWay(String dictLoanWay) {
			this.dictLoanWay = dictLoanWay;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}

		public String getRule() {
			return rule;
		}

		public void setRule(String rule) {
			this.rule = rule;
		}

	    public String getGrantPch() {
	        return grantPch;
	    }

	    public void setGrantPch(String grantPch) {
	        this.grantPch = grantPch;
	    }

	    public Date getSubmissionDate() {
	        return submissionDate;
	    }

	    public void setSubmissionDate(Date submissionDate) {
	        this.submissionDate = submissionDate;
	    }

		public String getCardBank() {
			return cardBank;
		}

		public void setCardBank(String cardBank) {
			this.cardBank = cardBank;
		}

		public String getContractVersion() {
			return contractVersion;
		}

		public void setContractVersion(String contractVersion) {
			this.contractVersion = contractVersion;
		}
}