package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款确认列表中的汇总表导出
 * 
 * @Class Name GrantSumEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class GrantSumEx extends DataEntity<GrantSumEx> {
	private static final long serialVersionUID = 481954112360679835L;
	private NumberFormat n2f = new DecimalFormat("#,##0.00");
	private NumberFormat n3f = new DecimalFormat("#,##0.000");
	// applyId
	private String applyId;
	// 放款id,
	private String id;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = { 1, 2 })
	private Integer sequenceNumber;
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 2)
	private String contractCode;
	@ExcelField(title = "借款人姓名(共借人)", type = 0, align = 2, sort = 3)
	private String customerName;
	@ExcelField(title = "批借金额", type = 0, align = 2, sort = 4)
	private String auditAmount;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 5)
	private String contractAmount;
	@ExcelField(title = "实放金额", type = 0, align = 2, sort = 6)
	private String feePaymentAmount;
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 7)
	private String feePetition;
	@ExcelField(title = "前期咨询费", type = 0, align = 2, sort = 8)
	private String feeConsult;
	@ExcelField(title = "前期审核费", type = 0, align = 2, sort = 9)
	private String feeAuditAmount;
	@ExcelField(title = "前期居间服务费", type = 0, align = 2, sort = 10)
	private String feeService;
	@ExcelField(title = "前期信息服务费", type = 0, align = 2, sort = 11)
	private String feeInfoService;
	@ExcelField(title = "前期综合服务费", type = 0, align = 2, sort = 12)
	private String feeCount;
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 13)
	private String feeUrgedService;
	@ExcelField(title = "产品种类", type = 0, align = 2, sort = 14)
	private String productType;
	@ExcelField(title = "期数", type = 0, align = 2, sort = 15)
	private int contractMonths;
	@ExcelField(title = "风险等级", type = 0, align = 2, sort = 16)
	private String riskLevel;
	@ExcelField(title = "总费率（%）", type = 0, align = 2, sort = 17)
	private String feeAllRaio;
	@ExcelField(title = "月利率（%）", type = 0, align = 2, sort = 18)
	private String feeMonthRate;
	@ExcelField(title = "合同日期", type = 0, align = 2, sort = 19)
	private Date contractFactDay;
	@ExcelField(title = "加急费", type = 0, align = 2, sort = 20)
	private String feeExpedited;
	@ExcelField(title = "标识", type = 0, align = 2, sort = 21, dictType = "jk_channel_flag")
	private String loanFlag;
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 22)
	private String contractVersion;
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 23, dictType = "jk_urgent_flag")
	private String loanUrgentFlag;
	// 门店名称
	// @ExcelField(title = "门店名称", type = 0, align = 2, sort = 20)
	private String storeName;
	// 共借人2
	// @ExcelField(title = "共借人", type = 0, align = 2, sort = 20)
	private String coboName;

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

	public int getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getFeeUrgedService() {
		return feeUrgedService;
	}

	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = number2Format(feeUrgedService);
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = number2Format(auditAmount);
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = number2Format(contractAmount);
	}

	public String getFeePaymentAmount() {
		return feePaymentAmount;
	}

	public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
		this.feePaymentAmount = number2Format(feePaymentAmount);
	}

	public String getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = number2Format(feePetition);
	}

	public String getFeeConsult() {
		return feeConsult;
	}

	public void setFeeConsult(BigDecimal feeConsult) {
		this.feeConsult = number2Format(feeConsult);
	}

	public String getFeeAuditAmount() {
		return feeAuditAmount;
	}

	public void setFeeAuditAmount(BigDecimal feeAuditAmount) {
		this.feeAuditAmount = number2Format(feeAuditAmount);
	}

	public String getFeeService() {
		return feeService;
	}

	public void setFeeService(BigDecimal feeService) {
		this.feeService = number2Format(feeService);
	}

	public String getFeeInfoService() {
		return feeInfoService;
	}

	public void setFeeInfoService(BigDecimal feeInfoService) {
		this.feeInfoService = number2Format(feeInfoService);
	}

	public String getFeeCount() {
		return feeCount;
	}

	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = number2Format(feeCount);
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFeeAllRaio() {
		return feeAllRaio;
	}

	public void setFeeAllRaio(BigDecimal feeAllRaio) {
		this.feeAllRaio = number3Format(feeAllRaio);
	}

	public String getFeeMonthRate() {
		return feeMonthRate;
	}

	public void setFeeMonthRate(BigDecimal feeMonthRate) {
		this.feeMonthRate = number3Format(feeMonthRate);
	}

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}

	public String getFeeExpedited() {
		return feeExpedited;
	}

	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = number2Format(feeExpedited);
	}

	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}

	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}

	public String getStoreName() {
		return storeName;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	private String number2Format (BigDecimal target) {
		return n2f.format(target);
	}
	private String number3Format(BigDecimal target) {
		return n3f.format(target);
	}
	
}