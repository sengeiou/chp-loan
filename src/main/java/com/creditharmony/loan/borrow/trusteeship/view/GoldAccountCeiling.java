package com.creditharmony.loan.borrow.trusteeship.view;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 用于查询和和展示金账户上限数据
 * 
 * @author 张建雄
 * @Create In 2016年2月23日
 */
public class GoldAccountCeiling extends DataEntity<GoldAccountCeiling> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3305697747329626017L;
	// 借款编码
	private String applyId;
	private String applyIds;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 1)
	private String storesName;
	private String storeOrgId;
	// 推介日期
	@ExcelField(title = "推介日期", type = 0, align = 2, sort = 2)
	private Date referralsDate;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 3)
	private String customerName;
	// 身份证号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort =4)
	private String customerNum;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 5)
	private String bankName;
	// 银行账号
	@ExcelField(title = "银行账号", type = 0, align = 2, sort = 6)
	private String bankAccount;
	// 批借金额
	@ExcelField(title = "批借金额", type = 0, align = 2, sort = 7)
	private BigDecimal loanAuditAmount;
	// 外访费
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 8)
	private BigDecimal feePetition;
	// 加急费
	@ExcelField(title = "加急费", type = 0, align = 2, sort = 9)
	private BigDecimal feeExpedited;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 10)
	private BigDecimal grantAmount;
	// 期限
	@ExcelField(title = "期限", type = 0, align = 2, sort = 11)
	private Integer loanMonths;

	// 借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 12)
	private String loanStatus;
	// 放款日期
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 13)
	private Date grantDate;
	private Date gtantEndDate;
	// 数据状态
	@ExcelField(title = "数据状态", type = 0, align = 2, sort = 14)
	private String dataStatus;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 15)
	private String logo;
	private String id ;
	private String loanCode;
	private String createBy;
	private String modifyBy;
	private String channel;
	public String getStoresName() {
		return storesName;
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

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public Date getGtantEndDate() {
		return gtantEndDate;
	}

	public void setGtantEndDate(Date gtantEndDate) {
		this.gtantEndDate = gtantEndDate;
	}

	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
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

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getApplyIds() {
		return applyIds;
	}

	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}

}
