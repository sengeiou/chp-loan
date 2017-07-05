package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 借款信息
 * @Class Name CarLoanInfo
 * @author 安子帅
 * @Create In 2016年1月21日
 */
public class CarLoanInfo extends DataEntity<CarLoanInfo> {
	
	private static final long serialVersionUID = 5613973793041438576L;
	
	
	 private String id;// 借款ID                                       
	 private String applyId;//APPLY_ID                                
	 private String loanCode;// 借款编码                              
	 private String loanCustomerName;//客户姓名                        
	 private String customerCode;// 客户编码                          
	 private BigDecimal loanApplyAmount;//申请金额                           
	 private String dictProductType;//申请产品类型                     
	 private BigDecimal loanMonths;//申请借款期限                             
	 private Date loanApplyTime;// 申请时间                            
	 private String mortgagee;//抵押权人                              
	 private String loanAuthorizer;// 授权人                           
	 private BigDecimal parkingFee;//停车费                                   
	 private BigDecimal facilityCharge;//设备费                               
	 private String dictSettleRelend;//结清再借                        
	 private String dictGpsRemaining;// GPS是否拆除                    
	 private String dictIsGatherFlowFee;// 是否收取平台流量费          
	 private BigDecimal flowFee;     //平台及流量费                                
	 private String dictLoanCommonRepaymentFlag;// 是否有共同还款人    
	 private String dictLoanUse;	//  借款用途        
	 private String loanUseDetail ; // 借款用途详细说明
	 private String loanBackTopStatus;// 退回时状态标识                
	 private String dictLoanStatus;// 借款状态                       
	 private String dictRepayMethod;// 还款方式                        
	 private String loanAdditionalApplyid;//展期借款ID                 
	 private String dictLoanFlag ;// 借款标识                          
	 private String outsideFlag;//外访标识                             
	 private String visitFlag;//暗访标识                               
	 private String loanCustomerService ;//客服编号     customerIntoTime               
	 private String managerCode;//客户经理CODE                         
	 private String consTeamManagercode;// 团队经理编号                
	 private String reviewMeet;//面审                                  
	 private String storesName;//团队编码                              
	 private String dictBackMestype ;//退回原因类型                    
	 private String remark;// 备注                                     
	 private String storeCode;//门店编码                               
	 private String storeName ;// 门店名称
	 private String conditionalThroughFlag;  //附条件通过标识
	 private String createBy;// 创建人                                 
	 private Date createTime;//创建时间                                
	 private String modifyBy;// 修改人
	 private Date modifyTime;// 修改时间 
	 private Date firstEntryApprove;//首次初审通过时间
	 private String isExtension; //是否展期
	 private String extensionReason;//展期原因
	 private String loanRawcode;//原借款编码
	 private Date customerIntoTime; //进件时间
	 private String dictSettleRelendName;//结清再借中文
	 private String loanFlag; //渠道标识
	 private Date settledDate; // 结清时间
	//初始处理人 
	 private String dealUser;
	 
	 private String dictsourcetype;// 来源版本(1:chp1.0 ;2:chp2. 0;3:chp3.0)
	 
	 private BigDecimal firstServiceTariffingRate;// 首期服务费率
	 
	 private BigDecimal deviceUsedFee; //GPS设备使用费
	 
	 private String firstServiceChargeId; //首期服务费率表主键
	 
	 
	public BigDecimal getDeviceUsedFee() {
		return deviceUsedFee;
	}
	public void setDeviceUsedFee(BigDecimal deviceUsedFee) {
		this.deviceUsedFee = deviceUsedFee;
	}
	public String getFirstServiceChargeId() {
		return firstServiceChargeId;
	}
	public void setFirstServiceChargeId(String firstServiceChargeId) {
		this.firstServiceChargeId = firstServiceChargeId;
	}
	public String getDictSettleRelendName() {
		return dictSettleRelendName;
	}
	public void setDictSettleRelendName(String dictSettleRelendName) {
		this.dictSettleRelendName = dictSettleRelendName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public Date getFirstEntryApprove() {
		return firstEntryApprove;
	}
	public void setFirstEntryApprove(Date firstEntryApprove) {
		this.firstEntryApprove = firstEntryApprove;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	public BigDecimal getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(BigDecimal loanMonths) {
		this.loanMonths = loanMonths;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getMortgagee() {
		return mortgagee;
	}
	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	public String getLoanAuthorizer() {
		return loanAuthorizer;
	}
	public void setLoanAuthorizer(String loanAuthorizer) {
		this.loanAuthorizer = loanAuthorizer;
	}
	public BigDecimal getParkingFee() {
		return parkingFee;
	}
	public void setParkingFee(BigDecimal parkingFee) {
		this.parkingFee = parkingFee;
	}
	public BigDecimal getFacilityCharge() {
		return facilityCharge;
	}
	public void setFacilityCharge(BigDecimal facilityCharge) {
		this.facilityCharge = facilityCharge;
	}
	public String getDictSettleRelend() {
		return dictSettleRelend;
	}
	public void setDictSettleRelend(String dictSettleRelend) {
		this.dictSettleRelend = dictSettleRelend;
	}
	public String getDictGpsRemaining() {
		return dictGpsRemaining;
	}
	public void setDictGpsRemaining(String dictGpsRemaining) {
		this.dictGpsRemaining = dictGpsRemaining;
	}
	public String getDictIsGatherFlowFee() {
		return dictIsGatherFlowFee;
	}
	public void setDictIsGatherFlowFee(String dictIsGatherFlowFee) {
		this.dictIsGatherFlowFee = dictIsGatherFlowFee;
	}
	public BigDecimal getFlowFee() {
		return flowFee;
	}
	public void setFlowFee(BigDecimal flowFee) {
		this.flowFee = flowFee;
	}
	public String getDictLoanCommonRepaymentFlag() {
		return dictLoanCommonRepaymentFlag;
	}
	public void setDictLoanCommonRepaymentFlag(String dictLoanCommonRepaymentFlag) {
		this.dictLoanCommonRepaymentFlag = dictLoanCommonRepaymentFlag;
	}
	public String getDictLoanUse() {
		return dictLoanUse;
	}
	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
	}
	public String getLoanBackTopStatus() {
		return loanBackTopStatus;
	}
	public void setLoanBackTopStatus(String loanBackTopStatus) {
		this.loanBackTopStatus = loanBackTopStatus;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getDictRepayMethod() {
		return dictRepayMethod;
	}
	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	public String getLoanAdditionalApplyid() {
		return loanAdditionalApplyid;
	}
	public void setLoanAdditionalApplyid(String loanAdditionalApplyid) {
		this.loanAdditionalApplyid = loanAdditionalApplyid;
	}
	public String getDictLoanFlag() {
		return dictLoanFlag;
	}
	public void setDictLoanFlag(String dictLoanFlag) {
		this.dictLoanFlag = dictLoanFlag;
	}
	public String getOutsideFlag() {
		return outsideFlag;
	}
	public void setOutsideFlag(String outsideFlag) {
		this.outsideFlag = outsideFlag;
	}
	public String getVisitFlag() {
		return visitFlag;
	}
	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}
	public String getLoanCustomerService() {
		return loanCustomerService;
	}
	public void setLoanCustomerService(String loanCustomerService) {
		this.loanCustomerService = loanCustomerService;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getConsTeamManagercode() {
		return consTeamManagercode;
	}
	public void setConsTeamManagercode(String consTeamManagercode) {
		this.consTeamManagercode = consTeamManagercode;
	}
	public String getReviewMeet() {
		return reviewMeet;
	}
	public void setReviewMeet(String reviewMeet) {
		this.reviewMeet = reviewMeet;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public String getDictBackMestype() {
		return dictBackMestype;
	}
	public void setDictBackMestype(String dictBackMestype) {
		this.dictBackMestype = dictBackMestype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	public String getIsExtension() {
		return isExtension;
	}
	public void setIsExtension(String isExtension) {
		this.isExtension = isExtension;
	}
	public String getExtensionReason() {
		return extensionReason;
	}
	public void setExtensionReason(String extensionReason) {
		this.extensionReason = extensionReason;
	}
	public String getLoanRawcode() {
		return loanRawcode;
	}
	public void setLoanRawcode(String loanRawcode) {
		this.loanRawcode = loanRawcode;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public Date getSettledDate() {
		return settledDate;
	}
	public void setSettledDate(Date settledDate) {
		this.settledDate = settledDate;
	}
	public String getDealUser() {
		return dealUser;
	}
	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}
	public String getLoanUseDetail() {
		return loanUseDetail;
	}
	public void setLoanUseDetail(String loanUseDetail) {
		this.loanUseDetail = loanUseDetail;
	}
	public String getDictsourcetype() {
		return dictsourcetype;
	}
	public void setDictsourcetype(String dictsourcetype) {
		this.dictsourcetype = dictsourcetype;
	}
	public BigDecimal getFirstServiceTariffingRate() {
		return firstServiceTariffingRate;
	}
	public void setFirstServiceTariffingRate(BigDecimal firstServiceTariffingRate) {
		this.firstServiceTariffingRate = firstServiceTariffingRate;
	}
	
	                              
}