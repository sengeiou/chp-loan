package com.creditharmony.loan.car.carGrant.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 划扣费用待追回
 * 
 * @Class Name CarDeductCostRecoverEx
 * @author 李静辉
 * @Create In 2016年2月29日
 */
public class CarDeductCostRecoverEx extends DataEntity<CarDeductCostRecoverEx> {
	
	private static final long serialVersionUID = 1L;
	// 拆分表中的划扣状态，划扣失败
	public static final String FAILED = "1";
	// 拆分表中的划扣状态，处理中
	public static final String DEAL = "3";
	// 拆分表中的划扣状态，待划扣
	public static final String PRE_DEDUCTS = "0";
	// 关联类型,催收服务费
	public static final String DEDUCT_TYPE = "1";
	// 拆分表id
	private String id;
	// 催收表id
	private String urgeId;
	// 借款编码
	private String loanCode;
	// 催收表id集合，导出时使用
	private String[] urgeIds;
	// 查账申请id
	private String checkApplyId;
	// 借款状态
	private String dictLoanStatus;
	// 借款状态
	private String dictLoanStatusLabel;
	// 合同编号
	private String contractCode;
	// 客户姓名
	private String customerName;
	// 证件号码
	private String customerCertNum;
	// 模式
	private String model;
	// 模式名称
	private String modelName;
	// 标识
	private String loanFlag;
	// 标识(名称)
	private String loanFlagLabel;
	// 借款产品
	private String productName;
	// 划扣平台
	private String dictDealType;
	// 放款日期
	private Date lendingTime;
	// 划扣日期
	private Date decuctTime;
	// 催收主表中的回盘结果
	private String splitBackResult;
	// 催收主表中的回盘结果(名称)
	private String splitBackResultLabel;
	// 拆分表表的回盘结果
	private String splitResult;
	// 回盘原因
	private String splitFailResult;
	// 关联类型
	private String dictRDeductType;
	// 产品类型
	private String carProductsType;
	// 拆分表中的处理状态
	private String dictDealStatus;
	// 开户行
	private String bankName;
	// 开户行(名称)
	private String bankNameLabel;
	// 账户姓名
	private String bankAccountName;
	// 银行卡号
	private String bankAccount;
	// 批借期限
	private int contractMonths;
	// 回盘时间
	private Date splitBackDate;
	// 催收金额
	private BigDecimal urgeMoeny;
	// 合同金额
	private BigDecimal contractAmount;
	// 放款金额
	private BigDecimal grantAmount;
	// 借款类型
	private String classType;
	// 是否电销
	private String customerTelesalesFlag;
	// 是否电销(名称)
	private String customerTelesalesFlagLabel;
	// 门店名称
	private String name;
	// 待催收金额
	private BigDecimal waitUrgeMoeny;
	// 放款划扣已办中的总金额
	private BigDecimal sumDeductAmount;
	// 自动划扣标识
	private String autoDeductFlag;
	// 放款失败退回标识
	private String returnLogo;
	// 查询起始时间
	private Date startDate;
	// 查询结束时间
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

	// 催收服务费查账申请表，使用车借的
	private CarUrgeServicesCheckApply urgeServicesCheckApply;

	// 查账账款表，使用车借的
	private CarPaybackTransferInfo PaybackTransferInfo;

	// 银行流水表，和信借使用的是一个
	private PaybackTransferOut PaybackTransferOut;

	// 中间人表,信借和车借使用的是同一个表，所以使用的是信借的实体
	private MiddlePerson middlePerson;

	private String queryRight;

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

	public CarUrgeServicesCheckApply getUrgeServicesCheckApply() {
		return urgeServicesCheckApply;
	}

	public void setUrgeServicesCheckApply(
			CarUrgeServicesCheckApply urgeServicesCheckApply) {
		this.urgeServicesCheckApply = urgeServicesCheckApply;
	}

	public CarPaybackTransferInfo getPaybackTransferInfo() {
		return PaybackTransferInfo;
	}

	public void setPaybackTransferInfo(CarPaybackTransferInfo paybackTransferInfo) {
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
	 * @param model
	 *            the String model to set
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
	 * @param modelName
	 *            the String modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCarProductsType() {
		return carProductsType;
	}

	public void setCarProductsType(String carProductsType) {
		this.carProductsType = carProductsType;
	}
}
