/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackList.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackChargeEx;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 还款冲抵申请表
 * @Class Name PaybackCharge
 * @author zhaojinping
 * @Create In 2016年1月4日
 */
@SuppressWarnings("serial")
public class PaybackCharge extends DataEntity<PaybackCharge>{
	// 合同编号
	private String contractCode;
	// 申请还期供总额
	private BigDecimal applyAmountPayback;
	// 申请违约金总额
    private BigDecimal applyAmountViolate;
    // 申请还罚息总额
    private BigDecimal applyAmountPunish;
    // 冲抵方式   
    private String dictOffsetType;
    // 冲抵方式 名称
    private String dictOffsetTypeLabel;
    // 未还违约金及罚息总金额
    private BigDecimal penaltyTotalAmount;
    // 提前结清应还总金额
    private BigDecimal  settleTotalAmount;
    // 申请蓝补金额
    private BigDecimal applyBuleAmount;
    // 逾期原因
    private String monthOverdueMes;
    // 冲抵状态
    private String chargeStatus;
    // 冲抵状态名称
    private String chargeStatusLabel;
    // 退回原因
    private String returnReason ;
	// 上传人
	private String uploadName;
	// 上传时间
	private Date uploadDate;
	// 上传文件名
	private String uploadFilename;
	// 上传文件路径
	private String uploadPath;
    // 关联主表
    private Payback payback;
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
 	// 借款客户咨询表
 	private Consult Consult;
	// 催收服务费信息表
 	private UrgeServicesMoney urgeServicesMoney;
 	// 催收服务费返款信息表
 	private UrgeServicesBackMoney urgeServicesBackMoney;
 	// 产品表
 	private JkProducts jkProducts;
 	// 放款记录表
 	private  LoanGrant loanGrant;
 	// 关联门店
	private PaybackChargeEx paybackChargeEx;
	//检索用门店名称
	private String[] orgList;
	
	// 隐藏名店id
	private String storeyc;
	// 门店id
	private String storeId;
	
	private String store;
	
	private String creditAmount; // 减免金额
	private Date beginDate; // 查询条件的开始时间
	private Date endDate; // 查询条件的结束时间
	
	private BigDecimal publishStart; // 未还违约金罚息区间-开始
	private BigDecimal publishEnd;   // 未还违约金罚息区间-终了
	
	   // 数据查询权限
	private String queryRight;
	private String idVals;
	
	
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public BigDecimal getApplyBuleAmount() {
		return applyBuleAmount;
	}
	public void setApplyBuleAmount(BigDecimal applyBuleAmount) {
		this.applyBuleAmount = applyBuleAmount;
	}
	public PaybackChargeEx getPaybackChargeEx() {
		return paybackChargeEx;
	}
	public void setPaybackChargeEx(PaybackChargeEx paybackChargeEx) {
		this.paybackChargeEx = paybackChargeEx;
	}
	public String[] getOrgList() {
		return orgList;
	}
	public void setOrgList(String[] orgList) {
		this.orgList = orgList;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public BigDecimal getApplyAmountPayback() {
		return applyAmountPayback;
	}
	public void setApplyAmountPayback(BigDecimal applyAmountPayback) {
		this.applyAmountPayback = applyAmountPayback;
	}
	public BigDecimal getApplyAmountViolate() {
		return applyAmountViolate;
	}
	public void setApplyAmountViolate(BigDecimal applyAmountViolate) {
		this.applyAmountViolate = applyAmountViolate;
	}
	public BigDecimal getApplyAmountPunish() {
		return applyAmountPunish;
	}
	public void setApplyAmountPunish(BigDecimal applyAmountPunish) {
		this.applyAmountPunish = applyAmountPunish;
	}
	public String getDictOffsetType() {
		return dictOffsetType;
	}
	public void setDictOffsetType(String dictOffsetType) {
		this.dictOffsetType = dictOffsetType;
	}
	public BigDecimal getPenaltyTotalAmount() {
		return penaltyTotalAmount;
	}
	public void setPenaltyTotalAmount(BigDecimal penaltyTotalAmount) {
		this.penaltyTotalAmount = penaltyTotalAmount;
	}
	public BigDecimal getSettleTotalAmount() {
		return settleTotalAmount;
	}
	public void setSettleTotalAmount(BigDecimal settleTotalAmount) {
		this.settleTotalAmount = settleTotalAmount;
	}
	public String getMonthOverdueMes() {
		return monthOverdueMes;
	}
	public void setMonthOverdueMes(String monthOverdueMes) {
		this.monthOverdueMes = monthOverdueMes;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public Payback getPayback() {
		return payback;
	}
	public void setPayback(Payback payback) {
		this.payback = payback;
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
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public PaybackMonth getPaybackMonth() {
		return paybackMonth;
	}
	public void setPaybackMonth(PaybackMonth paybackMonth) {
		this.paybackMonth = paybackMonth;
	}
	public UrgeServicesMoney getUrgeServicesMoney() {
		return urgeServicesMoney;
	}
	public void setUrgeServicesMoney(UrgeServicesMoney urgeServicesMoney) {
		this.urgeServicesMoney = urgeServicesMoney;
	}
	public UrgeServicesBackMoney getUrgeServicesBackMoney() {
		return urgeServicesBackMoney;
	}
	public void setUrgeServicesBackMoney(UrgeServicesBackMoney urgeServicesBackMoney) {
		this.urgeServicesBackMoney = urgeServicesBackMoney;
	}
	public JkProducts getJkProducts() {
		return jkProducts;
	}
	public void setJkProducts(JkProducts jkProducts) {
		this.jkProducts = jkProducts;
	}
	public LoanGrant getLoanGrant() {
		return loanGrant;
	}
	public void setLoanGrant(LoanGrant loanGrant) {
		this.loanGrant = loanGrant;
	}
	public Consult getConsult() {
		return Consult;
	}
	public void setConsult(Consult consult) {
		Consult = consult;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadFilename() {
		return uploadFilename;
	}
	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getStoreyc() {
		return storeyc;
	}
	public void setStoreyc(String storeyc) {
		this.storeyc = storeyc;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getDictOffsetTypeLabel() {
		return dictOffsetTypeLabel;
	}
	public void setDictOffsetTypeLabel(String dictOffsetTypeLabel) {
		this.dictOffsetTypeLabel = dictOffsetTypeLabel;
	}
	public String getChargeStatusLabel() {
		return chargeStatusLabel;
	}
	public void setChargeStatusLabel(String chargeStatusLabel) {
		this.chargeStatusLabel = chargeStatusLabel;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public BigDecimal getPublishStart() {
		return publishStart;
	}
	public void setPublishStart(BigDecimal publishStart) {
		this.publishStart = publishStart;
	}
	public BigDecimal getPublishEnd() {
		return publishEnd;
	}
	public void setPublishEnd(BigDecimal publishEnd) {
		this.publishEnd = publishEnd;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIdVals() {
		return idVals;
	}
	public void setIdVals(String idVals) {
		this.idVals = idVals;
	}
	
	public String[] getIdList(){
		if(this.idVals!=null && "".equals(this.idVals)==false)
			return this.idVals.split(",");
		else
			return null;
	}
	
	
}
