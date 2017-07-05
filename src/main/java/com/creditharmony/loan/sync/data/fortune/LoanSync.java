package com.creditharmony.loan.sync.data.fortune;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.persistence.DataEntity;

public class LoanSync extends DataEntity<LoanSync> {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 债权价值id
	private String creditValueId;
	// 借款CODE
	private String loanCode;
	// 借款人id
	private String loanId;
	// 同步表的名称
	protected String syncTableName;            
	// 同步数据类型：参见SyncDataType:TYPE_ADD,TYPE_DELETE,TYPE_MODIFY
	protected Integer syncType;
	// 借款人姓名
	private String loanName;
	// 借款人身份证号
	private String loanIdcard;
	// 借款人职业
	private String loanJob;
	// 借款类型
	private String dictLoanType;
	// 借款方式 借款产品类型
	private String loanProduct;
	// 借款用途
	private String loanPurpose;
	// 放款日
	private Date loanOutmoneyDay;
	// 首次还款日
	private Date loanBackmoneyFirday;
	// 还款日
	private Integer loanBakcmoneyDay;
	// 最后一期还款日
	private Date loanBackmoneyLastday;
	// 借款期数
	private Integer loanMonths;
	// 剩余借款期数
	private Integer loanMonthsSurplus;
	// 月利率
	private BigDecimal loanMonthRate;
	// 年预计债权收益
	private BigDecimal loanValueYear;
	// 是否可用，1可用，0不可用，2冻结
	private String dictLoanFreeFlag;
	// 最后编辑时间
	private Date loanModifiedDay;
	// 借款抵押物，只有借款类型为 房借和车借的时候有值 房借为：抵押房，车借为：车牌号
	private String loanPledge;
	// 借款剩余天数，只有在拆分后，才会计算出该数，没有经过拆分的债权价值此字段为空
	private Integer loanDaySurplus;
	// 中间人
	private String loanMiddleMan;
	// 冻结时间
	private Date loanFreezeDay;
	// 月满盈标志，1月满盈，0非月满盈
	private String loanMonthgainFlag;
	// 资金托管标识，1托管，空非托管
	private String loanTrusteeFlag;

	private String createBy;

	private Date createTime;

	private String modifyBy;

	private Date modifyTime;
	
	// 原始债权价值 --月满盈表与可用债权表
	private BigDecimal loanCreditValue;

	@SuppressWarnings("unused")
	private String loanBackmoneyFirdayStr;// 还款起始日期
	
	// 原始债权价值  -- 可用债权表 借款金额
	private BigDecimal loanQuota;
	// 可用债价值  --月满盈可用债权表 
	private BigDecimal loanAvailabeValue;

	public String getLoanBackmoneyFirdayStr() {
		if (loanBackmoneyFirday != null)
			return DateUtils.formatDate(loanBackmoneyFirday, "yyyy-MM-dd");
		else {
			return "";
		}
	}

	public String getCreditValueId() {
		return creditValueId;
	}

	public void setCreditValueId(String creditValueId) {
		this.creditValueId = creditValueId == null ? null : creditValueId
				.trim();
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode == null ? null : loanCode.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName == null ? null : loanName.trim();
	}

	public String getLoanIdcard() {
		return loanIdcard;
	}

	public void setLoanIdcard(String loanIdcard) {
		this.loanIdcard = loanIdcard == null ? null : loanIdcard.trim();
	}

	public String getLoanJob() {
		return loanJob;
	}

	public void setLoanJob(String loanJob) {
		this.loanJob = loanJob == null ? null : loanJob.trim();
	}

	public String getDictLoanType() {
		return dictLoanType;
	}

	public void setDictLoanType(String dictLoanType) {
		this.dictLoanType = dictLoanType == null ? null : dictLoanType.trim();
	}

	public String getLoanProduct() {
		return loanProduct;
	}

	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct == null ? null : loanProduct.trim();
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose == null ? null : loanPurpose.trim();
	}

	public Date getLoanOutmoneyDay() {
		return loanOutmoneyDay;
	}

	public void setLoanOutmoneyDay(Date loanOutmoneyDay) {
		this.loanOutmoneyDay = loanOutmoneyDay;
	}

	public Date getLoanBackmoneyFirday() {
		return loanBackmoneyFirday;
	}

	public void setLoanBackmoneyFirday(Date loanBackmoneyFirday) {
		this.loanBackmoneyFirday = loanBackmoneyFirday;
	}

	public Integer getLoanBakcmoneyDay() {
		return loanBakcmoneyDay;
	}

	public void setLoanBakcmoneyDay(Integer loanBakcmoneyDay) {
		this.loanBakcmoneyDay = loanBakcmoneyDay;
	}

	public Date getLoanBackmoneyLastday() {
		return loanBackmoneyLastday;
	}

	public void setLoanBackmoneyLastday(Date loanBackmoneyLastday) {
		this.loanBackmoneyLastday = loanBackmoneyLastday;
	}

	public Integer getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}

	public Integer getLoanMonthsSurplus() {
		return loanMonthsSurplus;
	}

	public void setLoanMonthsSurplus(Integer loanMonthsSurplus) {
		this.loanMonthsSurplus = loanMonthsSurplus;
	}

	public BigDecimal getLoanQuota() {
		return loanQuota;
	}

	public void setLoanQuota(BigDecimal loanQuota) {
		this.loanQuota = loanQuota;
	}

	public BigDecimal getLoanCreditValue() {
		return loanCreditValue;
	}

	public void setLoanCreditValue(BigDecimal loanCreditValue) {
		this.loanCreditValue = loanCreditValue;
	}

	public BigDecimal getLoanMonthRate() {
		return loanMonthRate;
	}

	public void setLoanMonthRate(BigDecimal loanMonthRate) {
		this.loanMonthRate = loanMonthRate;
	}

	public BigDecimal getLoanValueYear() {
		return loanValueYear;
	}

	public void setLoanValueYear(BigDecimal loanValueYear) {
		this.loanValueYear = loanValueYear;
	}

	public String getDictLoanFreeFlag() {
		return dictLoanFreeFlag;
	}

	public void setDictLoanFreeFlag(String dictLoanFreeFlag) {
		this.dictLoanFreeFlag = dictLoanFreeFlag == null ? null
				: dictLoanFreeFlag.trim();
	}

	public Date getLoanModifiedDay() {
		return loanModifiedDay;
	}

	public void setLoanModifiedDay(Date loanModifiedDay) {
		this.loanModifiedDay = loanModifiedDay;
	}

	public String getLoanPledge() {
		return loanPledge;
	}

	public void setLoanPledge(String loanPledge) {
		this.loanPledge = loanPledge == null ? null : loanPledge.trim();
	}

	public Integer getLoanDaySurplus() {
		return loanDaySurplus;
	}

	public void setLoanDaySurplus(Integer loanDaySurplus) {
		this.loanDaySurplus = loanDaySurplus;
	}

	public String getLoanMiddleMan() {
		return loanMiddleMan;
	}

	public void setLoanMiddleMan(String loanMiddleMan) {
		this.loanMiddleMan = loanMiddleMan == null ? null : loanMiddleMan
				.trim();
	}

	public Date getLoanFreezeDay() {
		return loanFreezeDay;
	}

	public void setLoanFreezeDay(Date loanFreezeDay) {
		this.loanFreezeDay = loanFreezeDay;
	}

	public String getLoanMonthgainFlag() {
		return loanMonthgainFlag;
	}

	public void setLoanMonthgainFlag(String loanMonthgainFlag) {
		this.loanMonthgainFlag = loanMonthgainFlag == null ? null
				: loanMonthgainFlag.trim();
	}

	public String getLoanTrusteeFlag() {
		return loanTrusteeFlag;
	}

	public void setLoanTrusteeFlag(String loanTrusteeFlag) {
		this.loanTrusteeFlag = loanTrusteeFlag == null ? null : loanTrusteeFlag
				.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSyncTableName() {
		return syncTableName;
	}

	public void setSyncTableName(String syncTableName) {
		this.syncTableName = syncTableName;
	}

	public void setLoanBackmoneyFirdayStr(String loanBackmoneyFirdayStr) {
		this.loanBackmoneyFirdayStr = loanBackmoneyFirdayStr;
	}

	public Integer getSyncType() {
		return syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public BigDecimal getLoanAvailabeValue() {
		return loanAvailabeValue;
	}

	public void setLoanAvailabeValue(BigDecimal loanAvailabeValue) {
		this.loanAvailabeValue = loanAvailabeValue;
	}
} 