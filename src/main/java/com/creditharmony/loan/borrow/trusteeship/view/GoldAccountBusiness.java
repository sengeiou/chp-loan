package com.creditharmony.loan.borrow.trusteeship.view;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 用于查询和和展示金账户数据信息
 * 
 * @author 张建雄
 *
 */
public class GoldAccountBusiness extends DataEntity<GoldAccountBusiness> {
	private static final long serialVersionUID = 5105987721741295650L;
	private String applyId;
	private String applyIds;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 60)
	private String storesName;
	// 推介日期
	@ExcelField(title = "推介日期", type = 0, align = 2, sort = 60)
	private Date referralsDate;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 60)
	private String customerName;
	// 身份证号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 60)
	private String customerNum;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 60)
	private String bankName;
	// 银行账号
	@ExcelField(title = "银行账号", type = 0, align = 2, sort = 60)
	private String bankAccount;
	// 批借金额
	@ExcelField(title = "批借金额", type = 0, align = 2, sort = 60)
	private BigDecimal loanAuditAmount;
	// 外访费
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 60)
	private BigDecimal feePetition;
	// 加急费
	@ExcelField(title = "加急费", type = 0, align = 2, sort = 60)
	private BigDecimal feeExpedited;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 60)
	private BigDecimal grantAmount;
	// 期限
	@ExcelField(title = "期限", type = 0, align = 2, sort = 60)
	private Integer loanMonths;
	// 渠道
	@ExcelField(title = "渠道", type = 0, align = 2, sort = 60)
	private String channel;
	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 60)
	private String loanStatus;
	// 放款日期
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 60)
	private Date grantDate;
	private String storeOrgId;
	private String model;
	
	//2016-05-12 start
	//是否无纸化
	private String yesNO;
	//是否有保证人
	private String warrantor;
	//合同版本号
	private String contractVersion;
	//合同版本号名称
	private String contractVersionLabel;
	//合同金额
	private BigDecimal contractAmount;
	//2016-05-12 end

	//渠道名称
	private String channelLabel;
	//模式名称
	private String modelLabel;
	//借款编号
	private String loanCode;

	public String getChannelLabel() {
		return channelLabel;
	}

	public void setChannelLabel(String channelLabel) {
		this.channelLabel = channelLabel;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}

	public String getStoresName() {
		return storesName;
	}

	public String getContractVersionLabel() {
		return contractVersionLabel;
	}

	public void setContractVersionLabel(String contractVersionLabel) {
		this.contractVersionLabel = contractVersionLabel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public Date getReferralsDate() {
		return referralsDate;
	}

	public void setReferralsDate(Date referralsDate) {
		this.referralsDate = referralsDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getLoanAuditAmount() {
		return loanAuditAmount;
	}

	public void setLoanAuditAmount(BigDecimal loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}

	public BigDecimal getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}

	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}

	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getApplyIds() {
		return applyIds;
	}

	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}

	public String getYesNO() {
		return yesNO;
	}

	public void setYesNO(String yesNO) {
		this.yesNO = yesNO;
	}

	public String getWarrantor() {
		return warrantor;
	}

	public void setWarrantor(String warrantor) {
		this.warrantor = warrantor;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	
	

}
