package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 催收服务费的扩展类，用来对催收服务费进行各种操作
 * 
 * @Class Name UrgeServicesMoneyEx
 * @author 朱静越
 * @Create In 2015年12月11日
 */
@SuppressWarnings("serial")
public class UrgeServicesMoneyEx extends DataEntity<UrgeServicesMoneyEx> {
	// 首次放款金额
		@ExcelField(title = "首次放款金额", type = 0, align = 2, sort = 72)
		private BigDecimal firstGrantAmount;
		// 费用总金额
		@ExcelField(title = "费用总金额", type = 0, align = 2, sort = 74)
		private BigDecimal allFee;
		// 前期综合服务费
		@ExcelField(title = "前期综合服务费", type = 0, align = 2, sort = 76)
		private BigDecimal feeCount;
		// 加急费
		@ExcelField(title = "加急费", type = 0, align = 2, sort = 78)
		private BigDecimal feeExpedited;
		// 尾款放款金额
		@ExcelField(title = "尾款放款金额", type = 0, align = 2, sort = 104)
		private BigDecimal lastGrantAmount;
		// 外访费
		@ExcelField(title = "外访费", type = 0, align = 2, sort = 104)
		private BigDecimal feePetition;

		public BigDecimal getAllFee() {
			return allFee;
		}

		public void setAllFee(BigDecimal allFee) {
			this.allFee = allFee;
		}


		public BigDecimal getLastGrantAmount() {
			return lastGrantAmount;
		}

		public void setLastGrantAmount(BigDecimal lastGrantAmount) {
			this.lastGrantAmount = lastGrantAmount;
		}

	// 拆分表中的划扣状态，划扣失败
	public  static final String FAILED="1";
	// 拆分表中的划扣状态，处理中
	public  static final String DEAL="3";
	// 拆分表中的划扣状态，待划扣
	public static final String PRE_DEDUCTS="0";
	// 关联类型,催收服务费
	public static final String DEDUCT_TYPE="1";
	// 拆分表id
	private String id;
	// 催收表id
	private String urgeId;
	// 催收表id集合，导出时使用
	private String[] urgeIds;
	// 查账申请id
	private String checkApplyId;
	// 借款状态
	private String dictLoanStatus;
	// 借款状态
	private String dictLoanStatusLabel;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	// 证件号码
	private String customerCertNum;
	// 模式
	private String model;
	// 模式名称
	private String modelName;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 170)
	private String loanFlag;
	// 标识id
	private String[] loanFlagId;
	// 标识(名称)
	private String loanFlagLabel;
	// 借款产品
	@ExcelField(title = "信借产品", type = 0, align = 2, sort = 50)
	private String productName;
	// 划扣平台
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 110,dictType="jk_deduct_plat")
	private String dictDealType;
	// 签约平台
	private String bankSigningPlatform;
	// 放款日期
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 140)
	private Date lendingTime;
	// 划扣日期
	@ExcelField(title = "最新划扣日期", type = 0, align = 2, sort = 150)
	private Date decuctTime;
	// 催收主表中的回盘结果
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 160,dictType="jk_counteroffer_result")
	private String splitBackResult;
	// 催收主表中的回盘结果(名称)
	private String splitBackResultLabel;
	// 拆分表表的回盘结果
	private String splitResult;
	// 回盘原因
	private String splitFailResult;
	// 关联类型
	private String dictRDeductType;
	// 拆分表中的处理状态
	private String dictDealStatus;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 130,dictType="jk_open_bank")
	private String bankName;
	//开户行数组
	private String[] bankNameArray;
	// 开户行(名称)
	private String bankNameLabel;
	// 账户姓名
	private String bankAccountName;
	// 银行卡号
	private String bankAccount;
	// 批借期限
	@ExcelField(title = "批借期限", type = 0, align = 2, sort = 120)
	private int contractMonths;
	// 回盘时间
	private Date splitBackDate;
	// 催收金额
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 80)
	private BigDecimal urgeMoeny;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 60)
	private BigDecimal contractAmount;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 70)
	private BigDecimal grantAmount;
	// 放款批次
	private String grantBatch;
	// 借款类型
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 40)
	private String classType;
	// 是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 180)
	private String customerTelesalesFlag;
	// 是否电销(名称)
	private String customerTelesalesFlagLabel;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 190)
	private String name;
	// 待催收金额
	@ExcelField(title = "未划金额", type = 0, align = 2, sort = 90)
	private BigDecimal waitUrgeMoeny;
	// 放款划扣已办中的总金额
	private BigDecimal sumDeductAmount;
	// 征信费
	private BigDecimal feeCredit;
	// 费用总计：催收费+信访费+征信费
	private BigDecimal feeSum;
	// 自动划扣标识
	private String autoDeductFlag;
	// 放款失败退回标识
	private String returnLogo;
	// 催收服务费划扣失败的失败原因
	private String deductFailReason;
	// 查询起始时间
	private Date startDate;
	//查询结束时间
	private Date endDate;
	// 划扣开始日期
	private Date deductStartDate;
	// 划扣结束日期
	private Date deductEndDate;
	// 催收主表中的时间标识
	private String timeFlag;
	// 是否发送
	private String sendFlag;
	// 拆分表中划扣标识：线上线下
	private String paybackFlag;
	// 企业流水号,唯一标识
	private String enterpriseSerialno;
	// 拆分金额,已划金额
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 100)
	private BigDecimal splitAmount;
	// 省份
	private String provinceCode;
	// 城市
	private String cityCode;
	// 规则，平台跳转规则
	private String rule;
	// 门店名称2
	private String storeName;
	// 门店id
	private String[] storeId;
	// 区分标识
	private String flag;
	// 退款标识
	private String refundFlag;
	
	// 匹配结果
	private String dictPayResult; 
	
	private String productCode;
	
	// 催收服务费查账申请表 
	private UrgeServicesCheckApply urgeServicesCheckApply;
	
	// 查账账款表
	private PaybackTransferInfo PaybackTransferInfo;
	
	// 银行流水表
	private PaybackTransferOut PaybackTransferOut;
	
	// 还款主表
	private Payback payback;

	// 中间人表
	private MiddlePerson middlePerson;
	
	private String queryRight;
	// 畅捷是否签约
	private String cjSign;
	
	// 费用总金额
	private BigDecimal costTatol;
    // 催收服务费
	private BigDecimal feeUrgedService;
	
	public String getQueryRight() {
		return queryRight;
	}

	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}

	public String getCheckApplyId() {
		return checkApplyId;
	}

	public void setCheckApplyId(String checkApplyId) {
		this.checkApplyId = checkApplyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSplitBackDate() {
		return splitBackDate;
	}

	public void setSplitBackDate(Date splitBackDate) {
		this.splitBackDate = splitBackDate;
	}

	public String getDictRDeductType() {
		return dictRDeductType;
	}

	public void setDictRDeductType(String dictRDeductType) {
		this.dictRDeductType = dictRDeductType;
	}

	
	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public void setWaitUrgeMoeny(BigDecimal waitUrgeMoeny) {
		this.waitUrgeMoeny = waitUrgeMoeny;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getWaitUrgeMoeny() {
		return waitUrgeMoeny;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrgeId() {
		return urgeId;
	}

	public void setUrgeId(String urgeId) {
		this.urgeId = urgeId;
	}

	public String[] getUrgeIds() {
		return urgeIds;
	}

	public void setUrgeIds(String[] urgeIds) {
		this.urgeIds = urgeIds;
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

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public Date getDecuctTime() {
		return decuctTime;
	}

	public void setDecuctTime(Date decuctTime) {
		this.decuctTime = decuctTime;
	}

	public String getSplitBackResult() {
		return splitBackResult;
	}

	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult;
	}

	public String getSplitFailResult() {
		return splitFailResult;
	}

	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}

	public BigDecimal getUrgeMoeny() {
		return urgeMoeny;
	}

	public void setUrgeMoeny(BigDecimal urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	public BigDecimal getSumDeductAmount() {
		return sumDeductAmount;
	}

	public void setSumDeductAmount(BigDecimal sumDeductAmount) {
		this.sumDeductAmount = sumDeductAmount;
	}

	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}

	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getDictDealStatus() {
		return dictDealStatus;
	}

	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getDeductFailReason() {
		return deductFailReason;
	}

	public void setDeductFailReason(String deductFailReason) {
		this.deductFailReason = deductFailReason;
	}

	public String getPaybackFlag() {
		return paybackFlag;
	}

	public void setPaybackFlag(String paybackFlag) {
		this.paybackFlag = paybackFlag;
	}

	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSplitResult() {
		return splitResult;
	}

	public void setSplitResult(String splitResult) {
		this.splitResult = splitResult;
	}

	public String getReturnLogo() {
		return returnLogo;
	}

	public void setReturnLogo(String returnLogo) {
		this.returnLogo = returnLogo;
	}
	
	public String getGrantBatch() {
		return grantBatch;
	}

	public void setGrantBatch(String grantBatch) {
		this.grantBatch = grantBatch;
	}

	public UrgeServicesCheckApply getUrgeServicesCheckApply() {
		return urgeServicesCheckApply;
	}

	public void setUrgeServicesCheckApply(
			UrgeServicesCheckApply urgeServicesCheckApply) {
		this.urgeServicesCheckApply = urgeServicesCheckApply;
	}

	public PaybackTransferInfo getPaybackTransferInfo() {
		return PaybackTransferInfo;
	}

	public void setPaybackTransferInfo(PaybackTransferInfo paybackTransferInfo) {
		PaybackTransferInfo = paybackTransferInfo;
	}

	public PaybackTransferOut getPaybackTransferOut() {
		return PaybackTransferOut;
	}

	public void setPaybackTransferOut(PaybackTransferOut paybackTransferOut) {
		PaybackTransferOut = paybackTransferOut;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String[] getStoreId() {
		return storeId;
	}

	public void setStoreId(String[] storeId) {
		this.storeId = storeId;
	}

	public Payback getPayback() {
		return payback;
	}

	public void setPayback(Payback payback) {
		this.payback = payback;
	}

	public String getDictPayResult() {
		return dictPayResult;
	}

	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}

	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
	}

	public String getSplitBackResultLabel() {
		return splitBackResultLabel;
	}

	public void setSplitBackResultLabel(String splitBackResultLabel) {
		this.splitBackResultLabel = splitBackResultLabel;
	}

	public String getBankNameLabel() {
		return bankNameLabel;
	}

	public void setBankNameLabel(String bankNameLabel) {
		this.bankNameLabel = bankNameLabel;
	}

	public String[] getLoanFlagId() {
		return loanFlagId;
	}

	public void setLoanFlagId(String[] loanFlagId) {
		this.loanFlagId = loanFlagId;
	}

	public String getCustomerTelesalesFlagLabel() {
		return customerTelesalesFlagLabel;
	}

	public void setCustomerTelesalesFlagLabel(String customerTelesalesFlagLabel) {
		this.customerTelesalesFlagLabel = customerTelesalesFlagLabel;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}

	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public Date getDeductStartDate() {
		return deductStartDate;
	}

	public void setDeductStartDate(Date deductStartDate) {
		this.deductStartDate = deductStartDate;
	}

	public Date getDeductEndDate() {
		return deductEndDate;
	}

	public void setDeductEndDate(Date deductEndDate) {
		this.deductEndDate = deductEndDate;
	}

	public String getAutoDeductFlag() {
		return autoDeductFlag;
	}

	public void setAutoDeductFlag(String autoDeductFlag) {
		this.autoDeductFlag = autoDeductFlag;
	}

	public MiddlePerson getMiddlePerson() {
		return middlePerson;
	}

	public void setMiddlePerson(MiddlePerson middlePerson) {
		this.middlePerson = middlePerson;
	}

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the String modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

	public String[] getBankNameArray() {
		return bankNameArray;
	}

	public void setBankNameArray(String[] bankNameArray) {
		this.bankNameArray = bankNameArray;
	}

	public String getCjSign() {
		return cjSign;
	}

	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getFirstGrantAmount() {
		return firstGrantAmount;
	}

	public BigDecimal getCostTatol() {
		return costTatol;
	}

	public BigDecimal getFeeCount() {
		return feeCount;
	}

	public BigDecimal getFeePetition() {
		return feePetition;
	}

	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}

	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}

	public void setFirstGrantAmount(BigDecimal firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}

	public void setCostTatol(BigDecimal costTatol) {
		this.costTatol = costTatol;
	}

	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = feeCount;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}

	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}

	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}

	public BigDecimal getFeeCredit() {
		return feeCredit;
	}

	public void setFeeCredit(BigDecimal feeCredit) {
		this.feeCredit = feeCredit;
	}

	public BigDecimal getFeeSum() {
		return feeSum;
	}

	public void setFeeSum(BigDecimal feeSum) {
		this.feeSum = feeSum;
	}
}