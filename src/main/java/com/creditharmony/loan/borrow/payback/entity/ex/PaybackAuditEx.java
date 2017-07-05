package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name PaybackAudit
 * @author 李强
 * @Create In 2015年12月8日
 */
@SuppressWarnings("serial")
public class PaybackAuditEx extends DataEntity<PaybackAuditEx> {

	private String ids;	
	
	private String outId;
	
	@ExcelField(title = "", type = 0, align = 2, sort = 10)
	private String num;
	
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;				// 合同编号
	
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName; 				// 客户姓名
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String orgName;						// 批借期数
	
	@ExcelField(title = "批借期数", type = 0, align = 2, sort = 40)
	private int contractMonths; 				// 批借期数
	
	@ExcelField(title = "首期还款日", type = 0, align = 2, sort = 50)
	private Date contractReplayDay; 			// 首期还款日
	
	@ExcelField(title = "还款状态", type = 0, align = 2, sort = 60,dictType="jk_repay_apply_status")
	private String dictPayStatus; 				// 还款状态
	private String dictPayStatusLabel; 				// 还款状态名称
	
	private String storesInAccount;   		//存入账号
	@ExcelField(title = "存入银行", type = 0, align = 2, sort = 70)
	private String storesInAccountname;   		//存入银行名称
	
	@ExcelField(title = "还款方式", type = 0, align = 2, sort = 80,dictType="jk_repay_way")
	private String dictRepayMethod; 			 // 还款方式
	private String dictRepayMethodLabel; 			 // 还款方式名称
	
	@ExcelField(title = "申请还款金额", type = 0, align = 2, sort = 90)
	private BigDecimal applyMoneyPayback;		 // 申请还款金额
	
	private BigDecimal applyAmountPayback;		 // 申请还期总额
	private BigDecimal applyAmountViolate;		 // 申请违约金总额
	private BigDecimal applyAmountPunish; 		 // 申请还罚息总额
	
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 100)
	private BigDecimal reallyAmount;			 // 实还金额(实际到账金额)
	private BigDecimal sunReallyAmount;			 // 实际到账总额
	@ExcelField(title = "还款类型", type = 0, align = 2, sort = 110,dictType="jk_repay_type")
	private String dictPayUse;					 // 还款类型
	private String dictPayUseLabel;					 // 还款类型名称
	
	@ExcelField(title = "查账日期", type = 0, align = 2, sort = 120)
	private Date outTimeCheckAccount;			 // 查账日期
	
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 130)
	private Date createTime; 					 // 还款日
	
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 140,dictType="jk_loan_status")
	private String dictLoanStatus;				 // 借款状态
	private String dictLoanStatusLabel;				 // 借款状态名称
	
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 150,dictType="jk_counteroffer_result")
	private String dictPayResult;				 // 回盘结果 
	private String dictPayResultLabel;				 // 回盘结果名称
	
	private String applyBackMes; 				 // 失败原因
	
	@ExcelField(title = "蓝补金额", type = 0, align = 2, sort = 170)
	private BigDecimal paybackBuleAmount;		 // 蓝补金额
	private String dictSourceType;				 // 来源系统
	private String rPaybackId;					 // 还款主表ID
	private String payBackId;					 // 还款申请ID
	private Date beginDate;
	private Date endDate;
	// 实还金额search1
	private BigDecimal beginOutReallyAmount;
	private BigDecimal endOutReallyAmount;
	
	private String customerCertNum;				 // 证件号码
	private String productType; 				 // 产品类型
	private BigDecimal contractAmount; 			 // 合同金额
	private BigDecimal contractMonthRepayAmount; // 期供金额
	private String loanMark; 					 // 标识
	private String loanMarkLabel; 					 // 标识名称
	private Date applyPayDay;					 // 还款申请日期
	private String dictDeposit; 				 // 存款方式
	private Date tranDepositTime;				 // 存款时间
	private String depositName; 				 // 实际存款人
	private String uploadPath;					 // JPG(上传文件路径)
	private String uploadFileName;				 // 上传文件名称
	private String uploadName;					 // 上传人
	private Date uploadDate; 					 // 上传时间
	private String enumOne;
	private String enumTwo;
	private String repaymentDate;	
	private String storeName;
	private String storeId;
	private String store;
	private String dictDealType;            //划扣平台
	private String dictDealTypeLable;    //划扣平台展示
	private String applyAccountName;   //账号姓名
	private String applyDeductAccount;  //划扣账号
	private String applyBankName;   //开户行全称
	private String dictPosType;   //POS平台
	private String dictPosTypeLable;   //POS平台
	private String posAccount;   //POS账户
	private String posAccountLable;   //POS账户
	//POS列表
	private String referCode;  //  参考号
	private String posOrderNumber; //  POS订单号
	private String dictDepositPosCard; // 存款方式
	private Date paybackDate;  //  到账日期
	private BigDecimal applyReallyAmountPosCard; // 实际到账金额
	private String uploadNamePosCard;  //  上传人 
	private Date uploadDatePosCard; //   上传时间
	private String uploadPathPosCard;  //  上传路径
	private String operateRole;//操作角色，1为中和人员操作
	
	
	
	public String getUploadPathPosCard() {
		return uploadPathPosCard;
	}
	public void setUploadPathPosCard(String uploadPathPosCard) {
		this.uploadPathPosCard = uploadPathPosCard;
	}
	public String getReferCode() {
		return referCode;
	}
	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}
	public String getPosOrderNumber() {
		return posOrderNumber;
	}
	public void setPosOrderNumber(String posOrderNumber) {
		this.posOrderNumber = posOrderNumber;
	}
	public String getDictDepositPosCard() {
		return dictDepositPosCard;
	}
	public void setDictDepositPosCard(String dictDepositPosCard) {
		this.dictDepositPosCard = dictDepositPosCard;
	}
	public Date getPaybackDate() {
		return paybackDate;
	}
	public void setPaybackDate(Date paybackDate) {
		this.paybackDate = paybackDate;
	}
	public BigDecimal getApplyReallyAmountPosCard() {
		return applyReallyAmountPosCard;
	}
	public void setApplyReallyAmountPosCard(BigDecimal applyReallyAmountPosCard) {
		this.applyReallyAmountPosCard = applyReallyAmountPosCard;
	}
	public String getUploadNamePosCard() {
		return uploadNamePosCard;
	}
	public void setUploadNamePosCard(String uploadNamePosCard) {
		this.uploadNamePosCard = uploadNamePosCard;
	}
	public Date getUploadDatePosCard() {
		return uploadDatePosCard;
	}
	public void setUploadDatePosCard(Date uploadDatePosCard) {
		this.uploadDatePosCard = uploadDatePosCard;
	}
	public String getPosAccount() {
		return posAccount;
	}
	public void setPosAccount(String posAccount) {
		this.posAccount = posAccount;
	}
	public String getPosAccountLable() {
		return posAccountLable;
	}
	public void setPosAccountLable(String posAccountLable) {
		this.posAccountLable = posAccountLable;
	}
	public String getDictPosType() {
		return dictPosType;
	}
	public void setDictPosType(String dictPosType) {
		this.dictPosType = dictPosType;
	}
	public String getDictPosTypeLable() {
		return dictPosTypeLable;
	}
	public void setDictPosTypeLable(String dictPosTypeLable) {
		this.dictPosTypeLable = dictPosTypeLable;
	}
	public String getApplyAccountName() {
		return applyAccountName;
	}
	public void setApplyAccountName(String applyAccountName) {
		this.applyAccountName = applyAccountName;
	}
	public String getApplyDeductAccount() {
		return applyDeductAccount;
	}
	public void setApplyDeductAccount(String applyDeductAccount) {
		this.applyDeductAccount = applyDeductAccount;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public String getDictDealTypeLable() {
		return dictDealTypeLable;
	}
	public void setDictDealTypeLable(String dictDealTypeLable) {
		this.dictDealTypeLable = dictDealTypeLable;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getEnumOne() {
		return enumOne;
	}
	public void setEnumOne(String enumOne) {
		this.enumOne = enumOne;
	}
	public String getEnumTwo() {
		return enumTwo;
	}
	public void setEnumTwo(String enumTwo) {
		this.enumTwo = enumTwo;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getPayBackId() {
		return payBackId;
	}
	public void setPayBackId(String payBackId) {
		this.payBackId = payBackId;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public BigDecimal getBeginOutReallyAmount() {
		return beginOutReallyAmount;
	}
	public void setBeginOutReallyAmount(BigDecimal beginOutReallyAmount) {
		this.beginOutReallyAmount = beginOutReallyAmount;
	}
	public BigDecimal getEndOutReallyAmount() {
		return endOutReallyAmount;
	}
	public void setEndOutReallyAmount(BigDecimal endOutReallyAmount) {
		this.endOutReallyAmount = endOutReallyAmount;
	}
	public String getrPaybackId() {
		return rPaybackId;
	}
	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	public Date getContractReplayDay() {
		return contractReplayDay;
	}
	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getDictRepayMethod() {
		return dictRepayMethod;
	}
	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	
	public BigDecimal getSunReallyAmount() {
		return sunReallyAmount;
	}
	public void setSunReallyAmount(BigDecimal sunReallyAmount) {
		this.sunReallyAmount = sunReallyAmount;
	}
	public BigDecimal getApplyMoneyPayback() {
		return applyMoneyPayback;
	}
	public void setApplyMoneyPayback(BigDecimal applyMoneyPayback) {
		this.applyMoneyPayback = applyMoneyPayback;
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
	public BigDecimal getReallyAmount() {
		return reallyAmount;
	}
	public void setReallyAmount(BigDecimal reallyAmount) {
		this.reallyAmount = reallyAmount;
	}
	public String getDictPayUse() {
		return dictPayUse;
	}
	public void setDictPayUse(String dictPayUse) {
		this.dictPayUse = dictPayUse;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getStoresInAccountname() {
		return storesInAccountname;
	}
	public void setStoresInAccountname(String storesInAccountname) {
		this.storesInAccountname = storesInAccountname;
	}
	public Date getOutTimeCheckAccount() {
		return outTimeCheckAccount;
	}
	public void setOutTimeCheckAccount(Date outTimeCheckAccount) {
		this.outTimeCheckAccount = outTimeCheckAccount;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getDictPayResult() {
		return dictPayResult;
	}
	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}
	public String getApplyBackMes() {
		return applyBackMes;
	}
	public void setApplyBackMes(String applyBackMes) {
		this.applyBackMes = applyBackMes;
	}
	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}
	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}
	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}
	public String getLoanMark() {
		return loanMark;
	}
	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}
	public Date getApplyPayDay() {
		return applyPayDay;
	}
	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}
	public String getDictDeposit() {
		return dictDeposit;
	}
	public void setDictDeposit(String dictDeposit) {
		this.dictDeposit = dictDeposit;
	}
	public Date getTranDepositTime() {
		return tranDepositTime;
	}
	public void setTranDepositTime(Date tranDepositTime) {
		this.tranDepositTime = tranDepositTime;
	}
	public String getDepositName() {
		return depositName;
	}
	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public String getDictRepayMethodLabel() {
		return dictRepayMethodLabel;
	}
	public void setDictRepayMethodLabel(String dictRepayMethodLabel) {
		this.dictRepayMethodLabel = dictRepayMethodLabel;
	}
	public String getDictPayUseLabel() {
		return dictPayUseLabel;
	}
	public void setDictPayUseLabel(String dictPayUseLabel) {
		this.dictPayUseLabel = dictPayUseLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getDictPayResultLabel() {
		return dictPayResultLabel;
	}
	public void setDictPayResultLabel(String dictPayResultLabel) {
		this.dictPayResultLabel = dictPayResultLabel;
	}
	public String getStoresInAccount() {
		return storesInAccount;
	}
	public void setStoresInAccount(String storesInAccount) {
		this.storesInAccount = storesInAccount;
	}
	public String getLoanMarkLabel() {
		return loanMarkLabel;
	}
	public void setLoanMarkLabel(String loanMarkLabel) {
		this.loanMarkLabel = loanMarkLabel;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getOperateRole() {
		return operateRole;
	}
	public void setOperateRole(String operateRole) {
		this.operateRole = operateRole;
	}
	
}
