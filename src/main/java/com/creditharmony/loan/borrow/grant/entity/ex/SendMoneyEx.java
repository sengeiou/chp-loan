package com.creditharmony.loan.borrow.grant.entity.ex;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 待放款审核节点中的导出列表显示的字段 type的值为0，导入导出都可以，type为2，仅导入
 * 
 * @Class Name GrantAuditEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class SendMoneyEx extends DataEntity<SendMoneyEx> {
	private static final long serialVersionUID = 3962172465594535962L;
	// 放款id,
	private String id;
	// 流程id
	private String applyId;

	private String storesId;
	@ExcelField(title = "放款时间", type = 0, align = 2, sort = 1)
	private Date lendingTime;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 2)
	private Integer index;
	@ExcelField(title = "地区", type = 0, align = 2, sort = 3)
	private String storesName;
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 4)
	private String contractCode;
	@ExcelField(title = "账户名", type = 0, align = 2, sort = 5)
	private String customerName;
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 6)
	private String customerCertNum;
	@ExcelField(title = "期数", type = 0, align = 2, sort = 7)
	private Integer contractMonths;
	@ExcelField(title = "借款利率", type = 0, align = 2, sort = 8)
	private Integer loanRoat;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 9)
	private String contractAmount;
	@ExcelField(title = "实放金额", type = 0, align = 2, sort = 10)
	private String grantAmount;
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 11)
	private String feeUrgedService;
	@ExcelField(title = "账户", type = 0, align = 2, sort = 12)
	private String bankAccount;
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 13, dictType = "jk_open_bank")
	private String bankName;
	@ExcelField(title = "支行名称", type = 0, align = 2, sort = 14)
	private String bankBranch;
	@ExcelField(title = "标识", type = 0, align = 2, sort = 15)
	private String loanFlag;
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 16)
	private String contractVersion;
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 17, dictType = "jk_urgent_flag")
	private String loanUrgentFlag;
	@ExcelField(title = "是否无纸化", type = 0, align = 2, sort = 18)
	private String paperlessFlag;
	@ExcelField(title = "是否有保证人", type = 0, align = 2, sort = 19)
	private String ensureFlag;
	@ExcelField(title = "回访状态", type = 0, align = 2, sort = 20)
	private String revisitStatus;
	// 冻结原因
	private String frozenCode;

	// 信用咨询及管理服务协议 1.5版本新增字段  begin
	private String collectioneServiceFee; // 催收服务费
	private String preConsultationFee; // 前期咨询费
	private String preAuditFee; // 前期审核费
	private String preHouseServiceFee; // 前期居间服务费
	private String preInfoServiceFee; // 前期信息服务费
	private String preInfoTotalServiceFee; // 前期综合服务费
	private String installmentConsultationFee; // 分期咨询费
	private Date paymentDateStart; // 还款起止日期开始时间
	private Date paymentDateEnd; // 还款起止日期结束时间
	private String paymentMode; // 还本付息方式
	private String monthConsultationFee; // 分期服务费下:每期支付咨询费
	private String loanEmail; // 甲方邮件
	private String guarantorName; // 丙方保证人姓名
    private String guarantorPeople; // 法定代表人
    private String guarantorAddress; // 法定代表地址
    private String guarantorEmail; // 丙方email
    private String guarantorTel; // 丙方电话
    private String sysIdentify = "3.0";  // 区分债权来自2.0还是3.0的标识
    



	private String unSociCreditCode; // 签章标识：统一社会信用代码
	private String feePetition; // 外访费
	private String itemDistance; // 外访距离
	private String guaranteeIdnum; // 丙方法人身份证
	
	


	private String loanCode;
	
	
	private String companyName;//公司名
	
	
	private String naturalPersonName;       //自然人姓名
	private String naturalPersonCardNumber; //身份证号码
	private String naturalPersonAddress;    //现住址    
	// 信用咨询及管理服务协议 1.5版本新增字段  end
	
	




	



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

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public Integer getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getFeeUrgedService() {
		return feeUrgedService;
	}

	public void setFeeUrgedService(String feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getStoresId() {
		return storesId;
	}

	public void setStoresId(String storesId) {
		this.storesId = storesId;
	}

	public String getFrozenCode() {
		return frozenCode;
	}

	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}

	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}

	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}

	public Integer getLoanRoat() {
		return loanRoat;
	}

	public void setLoanRoat(Integer loanRoat) {
		this.loanRoat = loanRoat;
	}

	public String getPaperlessFlag() {
		return paperlessFlag;
	}

	public void setPaperlessFlag(String paperlessFlag) {
		this.paperlessFlag = paperlessFlag;
	}

	public String getEnsureFlag() {
		return ensureFlag;
	}

	public void setEnsureFlag(String ensureFlag) {
		this.ensureFlag = ensureFlag;
	}
	
	
	public String getCollectioneServiceFee() {
		return collectioneServiceFee;
	}

	public void setCollectioneServiceFee(String collectioneServiceFee) {
		this.collectioneServiceFee = collectioneServiceFee;
	}

	public String getPreConsultationFee() {
		return preConsultationFee;
	}

	public void setPreConsultationFee(String preConsultationFee) {
		this.preConsultationFee = preConsultationFee;
	}

	public String getPreAuditFee() {
		return preAuditFee;
	}

	public void setPreAuditFee(String preAuditFee) {
		this.preAuditFee = preAuditFee;
	}

	public String getPreHouseServiceFee() {
		return preHouseServiceFee;
	}

	public void setPreHouseServiceFee(String preHouseServiceFee) {
		this.preHouseServiceFee = preHouseServiceFee;
	}

	public String getPreInfoServiceFee() {
		return preInfoServiceFee;
	}

	public void setPreInfoServiceFee(String preInfoServiceFee) {
		this.preInfoServiceFee = preInfoServiceFee;
	}

	public String getPreInfoTotalServiceFee() {
		return preInfoTotalServiceFee;
	}

	public void setPreInfoTotalServiceFee(String preInfoTotalServiceFee) {
		this.preInfoTotalServiceFee = preInfoTotalServiceFee;
	}

	public String getInstallmentConsultationFee() {
		return installmentConsultationFee;
	}

	public void setInstallmentConsultationFee(String installmentConsultationFee) {
		this.installmentConsultationFee = installmentConsultationFee;
	}

	public Date getPaymentDateStart() {
		return paymentDateStart;
	}

	public void setPaymentDateStart(Date paymentDateStart) {
		this.paymentDateStart = paymentDateStart;
	}

	public Date getPaymentDateEnd() {
		return paymentDateEnd;
	}

	public void setPaymentDateEnd(Date paymentDateEnd) {
		this.paymentDateEnd = paymentDateEnd;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getMonthConsultationFee() {
		return monthConsultationFee;
	}

	public void setMonthConsultationFee(String monthConsultationFee) {
		this.monthConsultationFee = monthConsultationFee;
	}

	public String getLoanEmail() {
		return loanEmail;
	}

	public void setLoanEmail(String loanEmail) {
		this.loanEmail = loanEmail;
	}



	public String getGuarantorName() {
		return guarantorName;
	}



	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}



	public String getGuarantorPeople() {
		return guarantorPeople;
	}



	public void setGuarantorPeople(String guarantorPeople) {
		this.guarantorPeople = guarantorPeople;
	}



	public String getGuarantorAddress() {
		return guarantorAddress;
	}



	public void setGuarantorAddress(String guarantorAddress) {
		this.guarantorAddress = guarantorAddress;
	}



	public String getGuarantorEmail() {
		return guarantorEmail;
	}



	public void setGuarantorEmail(String guarantorEmail) {
		this.guarantorEmail = guarantorEmail;
	}



	public String getGuarantorTel() {
		return guarantorTel;
	}



	public void setGuarantorTel(String guarantorTel) {
		this.guarantorTel = guarantorTel;
	}
	public String getSysIdentify() {
		return sysIdentify;
	}



	public void setSysIdentify(String sysIdentify) {
		this.sysIdentify = sysIdentify;
	}



	public String getUnSociCreditCode() {
		return unSociCreditCode;
	}



	public void setUnSociCreditCode(String unSociCreditCode) {
		this.unSociCreditCode = unSociCreditCode;
	}
	public String getFeePetition() {
		return feePetition;
	}



	public void setFeePetition(String feePetition) {
		this.feePetition = feePetition;
	}



	public String getItemDistance() {
		return itemDistance;
	}



	public void setItemDistance(String itemDistance) {
		this.itemDistance = itemDistance;
	}
	public String getLoanCode() {
		return loanCode;
	}



	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	

	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGuaranteeIdnum() {
		return guaranteeIdnum;
	}



	public void setGuaranteeIdnum(String guaranteeIdnum) {
		this.guaranteeIdnum = guaranteeIdnum;
	}
	public String getNaturalPersonName() {
		return naturalPersonName;
	}



	public void setNaturalPersonName(String naturalPersonName) {
		this.naturalPersonName = naturalPersonName;
	}



	public String getNaturalPersonCardNumber() {
		return naturalPersonCardNumber;
	}



	public void setNaturalPersonCardNumber(String naturalPersonCardNumber) {
		this.naturalPersonCardNumber = naturalPersonCardNumber;
	}



	public String getNaturalPersonAddress() {
		return naturalPersonAddress;
	}



	public void setNaturalPersonAddress(String naturalPersonAddress) {
		this.naturalPersonAddress = naturalPersonAddress;
	}






	public String getRevisitStatus() {
		return revisitStatus;
	}






	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}


}