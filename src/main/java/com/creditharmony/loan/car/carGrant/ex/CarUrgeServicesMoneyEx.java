package com.creditharmony.loan.car.carGrant.ex;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 催收服务费的扩展类，用来对催收服务费进行各种操作
 * 
 * @Class Name UrgeServicesMoneyEx
 * @Create In 2016年3月15日
 */
@SuppressWarnings("serial")
public class CarUrgeServicesMoneyEx extends DataEntity<CarUrgeServicesMoneyEx> {
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
	// 催收主表ids
	private String urgeIds;
	// 借款编码
	private String loanCode;
	// 拆分父id
	private String splitFatherId;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	// 证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 30)
	private String customerCertNum;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 170)
	private String dictLoanFlag;
	// 借款产品
	@ExcelField(title = "信借产品", type = 0, align = 2, sort = 50)
	private String productType;
	// 划扣平台
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 110,dictType="jk_deduct_plat")
	private String dictDealType;
	// 放款日期
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 140)
	private Date lendingTime;
	// 划扣日期
	@ExcelField(title = "最新划扣日期", type = 0, align = 2, sort = 150)
	private Date decuctTime;
	// 催收主表中的回盘结果
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 160,dictType="jk_counteroffer_result")
	private String splitBackResult;
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
	private String cardBank;
	// 回显开户行
	private String cardBankEcho;
	
	private String[] cardBankSearch;
	// 批借期限
	@ExcelField(title = "批借期限", type = 0, align = 2, sort = 120)
	private int contractMonths;
	// 回盘时间
	private Date splitBackDate;
	// 催收金额
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 80)
	private BigDecimal urgeMoeny;
	// 起始划扣金额(搜索条件)
	private BigDecimal urgeMoenyStart;
	// 结束划扣金额(搜索条件)
	private BigDecimal urgeMoenyEnd;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 60)
	private BigDecimal contractAmount;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 70)
	private BigDecimal grantAmount;
	// 借款类型
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 40)
	private String classType;
	// 是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 180)
	private String customerTelesalesFlag;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 190)
	private String name;
	// 应划扣金额
	private BigDecimal waitUrgeMoeny;
	// 放款失败退回标识
	private String returnLogo;
	// 查询起始时间
	private Date startDate;
	//查询结束时间
	private Date endDate;
	// 催收主表中的时间标识
	private String timeFlag;
	// 是否发送
	private String sendFlag;
	// 划扣标识
	private String paybackFlag;
	// 企业流水号
	private String enterpriseSerialno;
	// 已划金额
	private BigDecimal urgeDecuteMoeny;
	// 省份
	private String provinceCode;
	// 城市
	private String cityCode;
	// 规则，平台跳转规则
	private String rule;
	// P2P标识
	private String loanFlag;
	
	public String getSplitFatherId() {
		return splitFatherId;
	}

	public void setSplitFatherId(String splitFatherId) {
		this.splitFatherId = splitFatherId;
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

	
	public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}

	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
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


	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	
	public String getUrgeIds() {
		return urgeIds;
	}

	public void setUrgeIds(String urgeIds) {
		this.urgeIds = urgeIds;
	}

	public String getDictLoanFlag() {
		return dictLoanFlag;
	}

	public void setDictLoanFlag(String dictLoanFlag) {
		this.dictLoanFlag = dictLoanFlag;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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


	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getCardBankEcho() {
		return cardBankEcho;
	}

	public void setCardBankEcho(String cardBankEcho) {
		this.cardBankEcho = cardBankEcho;
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

	public BigDecimal getUrgeMoenyStart() {
		return urgeMoenyStart;
	}

	public void setUrgeMoenyStart(BigDecimal urgeMoenyStart) {
		this.urgeMoenyStart = urgeMoenyStart;
	}

	public BigDecimal getUrgeMoenyEnd() {
		return urgeMoenyEnd;
	}

	public void setUrgeMoenyEnd(BigDecimal urgeMoenyEnd) {
		this.urgeMoenyEnd = urgeMoenyEnd;
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

	public String[] getCardBankSearch() {
		if(StringUtils.isNotEmpty(cardBank)&&cardBank.split(",").length>0){
			return cardBank.split(",");
		}
		return cardBankSearch;
	}

	public void setCardBankSearch(String[] cardBankSearch) {
		this.cardBankSearch = cardBankSearch;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}