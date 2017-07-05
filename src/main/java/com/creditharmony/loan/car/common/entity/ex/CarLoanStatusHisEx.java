package com.creditharmony.loan.car.common.entity.ex;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.google.common.collect.Lists;

/**
 * 已办信息
 * 
 * @Class Name CarLoanStatusHisEx
 * @author 陈伟东
 * @Create In 2016年2月26日
 */
public class CarLoanStatusHisEx extends CarLoanStatusHis {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String ids;
	private String storeId;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 2)
	private String loanCustomerName;
	// 停车费
	private Double parkingFee;
	// 平台流量费
	private Double flowFee;
	// 申请金额
	private BigDecimal loanApplyAmount;
	// 申请借款期限
	@ExcelField(title = "期数", type = 0, align = 2, sort = 7)
	private String loanMonths;
	// 初审批借期限
	private String firstAuditMonths;
	// 复审批借期限
	private String secondAuditMonths;
	// 终审批借期限
	private String finalAuditMonths;
	// 申请产品类型(产品类型，均使用这个字段作为检索条件)
	private String productType;
	// 申请产品类型(产品类型，均使用这个字段作为检索条件) 中文名称
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 13)
	private String productName;
	// 初审产品类型
	private String firstProductType;
	// 复审产品类型
	private String secondProductType;
	// 终审产品类型
	private String finalProductType;
	// 门店评估金额
	private Double storeAssessAmount;
	// 初审批借金额
	private Double firstAuditAmount;
	// 复审批借金额
	private Double secondAuditAmount;
	// 终审批借金额/ 合同金额
	private Double finalAuditAmount;
	// 申请时间
	private Date applyTime;
	// 申请时间开始
	private Date applyTimeStrat;
	// 申请时间结束
	private Date applyTimeEnd;
	// 所属省份
	private String provinceName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 3)
	private String storeName;
	// 门店编码(检索条件)，用于接收页面数值
	private String storeCode;
	// 门店编码（检索条件），用于sql查询，因为传入的格式为1,2,3，需要改为List
	private List<String> storeCodeList = Lists.newArrayList();;
	// 团队经理姓名(检索条件)
	private String teamManagerName;
	// 客户经理姓名(检索条件）
	private String costumerManagerName;
	// 面审人员姓名
	private String reviewMeetName;
	// 咨询状态
	private String dictOperStatus;
	// 借款状态编码
	private String loanStatusCode;
	// 借款状态编码中文名称
	private String loanStatusCodeName;
	// 利率审核日期
	private Date rateCheckDate;
	// 车牌号码
	@ExcelField(title = "车牌号", type = 0, align = 2, sort = 12)
	private String plateNumbers;
	// 是否电销（检索条件）
	private String telesalesFlag;
	// 检索条件（身份证号）
	private String certNum;
	// 用于查询，若 设值，则为 个人已办，否则为 所有人已办
	private String filterUser;
	// in前是否加not，若为1，则不加，若为0则加not
	private String isIn;
	// 是否查询全部，若为1，则查询全部状态数据，若为0，则不是
	private String isQueryAll;
	// 操作节点列值，见CarLoanSteps
	private List<String> nodeValList;
	// 是否只筛选通过的记录，为1，则是，否则为不是
	private String grossFlag;
	// 放款开始日期
	private String lendingTimeStart;
	// 放款结束日期
	private String lendingTimeEnd;
	// 开户行
	private String midBankName;
	// 放款账号
	private String bankCardNo;
	// 中间人Id
	private String midId;
	// 中间人姓名/账号姓名
	private String middleName;
	// 放款时间
	private Date lendingTime;
	// 操作人员编号
	private String lendingUserId;
	// 签约日期
	private Date contractFactDay;
	// 流程id
	private String applyId;
	// (合同)借款产品
	private String productTypeContract;
	// (合同)批借期限
	private Integer contractMonths;
	// 应划扣金额
	private BigDecimal urgeMoeny;
	// 实划扣金额
	private BigDecimal urgeDecuteMoeny;
	// 划扣日期
	private Date urgeDecuteDate;
	// 划扣日期（开始）
	private Date urgeDecuteDateStart;
	// 划扣日期（结束）
	private Date urgeDecuteDateEnd;
	// 回盘结果(划扣)
	private String dictDealStatus;
	// 回盘原因
	private String dictDealReason;
	// 划扣平台
	private String dictDealType;
	// 附条件通过标识
	private String conditionalThroughFlag;
	// 标识
	private String borrowTursteeFlag;
	// 回盘原因(划扣)
	private String splitFailResult;
	// 是否全部导出
	private String isAllData;
	// 回盘结果(审核退回)
	private String auditStatus;
	// 回盘原因(审核退回)
	private String auditRefuseReason;
	// 划扣总金额
	private Double totalUrgeDecuteAmount;
	// 放款总金额
	private Double totalGrantAmount;
	// 是否展期(展期审请)
	private String isExtendsion;
	// 原合同编码
	private String originalContractCode;
	// 降额
	@ExcelField(title = "展期降额", type = 0, align = 2, sort = 11)
	private BigDecimal derate;
	// 已展期次数
	@ExcelField(title = "第几次展期", type = 0, align = 2, sort = 10)
	private int extendNum;
	// 共借人
	private String cobos;
	// 合同金额
	@ExcelField(title = "展期合同金额", type = 0, align = 2, sort = 4)
	private BigDecimal contractAmount;
	private Double grantAmount;
	// 展期费用
	private Double extensionFee;
	// 合同版本号
	private String contractVersion;
	// 预计到店时间start
	private Date planArrivalTimeStart;
	// 预计到店时间end
	private Date planArrivalTimeend;
	// 预计到店时间
	private Date planArrivalTime;
	// 车辆型号
	private String vehicleBrandModel;
	// 操作步骤
	private String operateStep;
	// 放款账号（已放款）
	private String creditBankCardNo;
	// 开户行/存入账户(已放款)
	private String creditMiDBankName;
	// 来源系统
	private String sourceType;

	private String grantRecepicResult;

	//客户经理
	@ExcelField(title = "员工编号", type = 0, align = 2, sort = 6)
	private String managerCode;
	//客户经理员工编号
	@ExcelField(title = "客户经理", type = 0, align = 2, sort = 5)
	private String managerName;
	
	// 实还金额
	private Double reallyAmount;
	// 最小实还金额
	private Double reallyAmountMin;
	// 最大实还金额
	private Double reallyAmountMax;
	// 查账日期
	private String checkDate;
	// 查账开始日期
	private String checkDateStart;
	// 查账结束日期
	private String checkDateEnd;
	// 退款标识
	private String returnFlag;
	// 回盘结果(查账)
	private String matchingResult;
	// 存入账号
	private String storesInAccount;
	//协议类型(0、拒绝 1、电子协议待审核 2、发送)
	private String agreementType;

	// 未划金额
	private Double unUrgeDecuteMoeny;
	// 展期应还总金额
	private Double extendRepayMoney;
	// 申请查账日期
	private String applyPayDay;

	private String coborrowerName;

	// 标识P2P
	private String loanFlag;

	// 签约合同个数
	private String signcount;
	/*
	 * 签章成功与否标志 0:失败 1:成功 空：未进行过签章
	 */
	private String signFlag;

	private List<String> applyIdList;

	@ExcelField(title = "展期合同起始日期", type = 0, align = 2, sort = 8)
	private Date contractReplayDay;
	@ExcelField(title = "展期合同截止日期", type = 0, align = 2, sort = 9)
	private Date contractTipDay;

	private String paperLessFlag;

	private String customerEmail;

	private String coboEmail;

	private String dictSourceType;
	private String customerCertNum;
	private Date contractEndDay;
	private Date settledDate;
	// 紧急程度
	private String urgentDegree;
	// 申请理由
	private String applyReason;
	// 文件ID
	private String docId;
	// 签约文件ID
	private String signDocId;
	// 文件名称
	private String fileName;
	// 电子协议借款状态
	private String agrDictLoanStatus;

	// 利息
	private BigDecimal interestAmount;
	// 服务费
	private Double serviceMoeny;
	// 长期产品服务费
	private Double longProductMoeny;
	// 渠道费
	private Double channelMoeny;
	// 外放费
	private Double outerVisitMoeny;
	// 停车费
	private Double parkMoeny;
	// GPS设备费安装费
	private Double GPSInstallMoeny;
	// GPS使用费
	private Double GPSUseMoeny;
	// 平台流量费
	private Double platformFlowMoeny;
	//外访费
	private Double outVisitFee;

	// 首期服务费
	private BigDecimal firstServiceTariffingRate;
	
	//回访状态
	private String visitState;

	//回访失败原因
	private String revisitReason;

	public String getVisitState() {
		return visitState;
	}

	public void setVisitState(String visitState) {
		this.visitState = visitState;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Double getExtendRepayMoney() {
		return extendRepayMoney;
	}

	public void setExtendRepayMoney(Double extendRepayMoney) {
		this.extendRepayMoney = extendRepayMoney;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Double getReallyAmountMin() {
		return reallyAmountMin;
	}

	public void setReallyAmountMin(Double reallyAmountMin) {
		this.reallyAmountMin = reallyAmountMin;
	}

	public Double getReallyAmountMax() {
		return reallyAmountMax;
	}

	public void setReallyAmountMax(Double reallyAmountMax) {
		this.reallyAmountMax = reallyAmountMax;
	}

	public String getMatchingResult() {
		return matchingResult;
	}

	public void setMatchingResult(String matchingResult) {
		this.matchingResult = matchingResult;
	}

	public String getApplyPayDay() {
		return applyPayDay;
	}

	public void setApplyPayDay(String applyPayDay) {
		this.applyPayDay = applyPayDay;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Double getUnUrgeDecuteMoeny() {
		return unUrgeDecuteMoeny;
	}

	public void setUnUrgeDecuteMoeny(Double unUrgeDecuteMoeny) {
		this.unUrgeDecuteMoeny = unUrgeDecuteMoeny;
	}

	public Double getReallyAmount() {
		return reallyAmount;
	}

	public void setReallyAmount(Double reallyAmount) {
		this.reallyAmount = reallyAmount;
	}

	public String getCheckDateStart() {
		return checkDateStart;
	}

	public void setCheckDateStart(String checkDateStart) {
		this.checkDateStart = checkDateStart;
	}

	public String getCheckDateEnd() {
		return checkDateEnd;
	}

	public void setCheckDateEnd(String checkDateEnd) {
		this.checkDateEnd = checkDateEnd;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getStoresInAccount() {
		return storesInAccount;
	}

	public void setStoresInAccount(String storesInAccount) {
		this.storesInAccount = storesInAccount;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCreditBankCardNo() {
		return creditBankCardNo;
	}

	public void setCreditBankCardNo(String creditBankCardNo) {
		this.creditBankCardNo = creditBankCardNo;
	}

	public String getCreditMiDBankName() {
		return creditMiDBankName;
	}

	public void setCreditMiDBankName(String creditMiDBankName) {
		this.creditMiDBankName = creditMiDBankName;
	}

	public Double getTotalGrantAmount() {
		return totalGrantAmount;
	}

	public void setTotalGrantAmount(Double totalGrantAmount) {
		this.totalGrantAmount = totalGrantAmount;
	}

	public String getOperateStep() {
		return operateStep;
	}

	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}

	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}

	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}

	public Date getPlanArrivalTime() {
		return planArrivalTime;
	}

	public void setPlanArrivalTime(Date planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}

	public Date getPlanArrivalTimeStart() {
		return planArrivalTimeStart;
	}

	public void setPlanArrivalTimeStart(Date planArrivalTimeStart) {
		this.planArrivalTimeStart = planArrivalTimeStart;
	}

	public Date getPlanArrivalTimeend() {
		return planArrivalTimeend;
	}

	public void setPlanArrivalTimeend(Date planArrivalTimeend) {
		this.planArrivalTimeend = planArrivalTimeend;
	}

	public String getDictOperStatus() {
		return dictOperStatus;
	}

	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public Double getExtensionFee() {
		return extensionFee;
	}

	public void setExtensionFee(Double extensionFee) {
		this.extensionFee = extensionFee;
	}

	public BigDecimal getContractAmount() {
		if(contractAmount != null){
			contractAmount = contractAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}else{
			contractAmount = BigDecimal.ZERO.setScale(2);
		}
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getTotalUrgeDecuteAmount() {
		return totalUrgeDecuteAmount;
	}

	public void setTotalUrgeDecuteAmount(Double totalUrgeDecuteAmount) {
		this.totalUrgeDecuteAmount = totalUrgeDecuteAmount;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public BigDecimal getLoanApplyAmount() {
		return loanApplyAmount;
	}

	public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}

	public String getLoanMonths() {
		return loanMonths;
	}

	public void setLoanMonths(String loanMonths) {
		this.loanMonths = loanMonths;
	}

	public String getFirstAuditMonths() {
		return firstAuditMonths;
	}

	public void setFirstAuditMonths(String firstAuditMonths) {
		this.firstAuditMonths = firstAuditMonths;
	}

	public String getSecondAuditMonths() {
		return secondAuditMonths;
	}

	public void setSecondAuditMonths(String secondAuditMonths) {
		this.secondAuditMonths = secondAuditMonths;
	}

	public String getFinalAuditMonths() {
		return finalAuditMonths;
	}

	public void setFinalAuditMonths(String finalAuditMonths) {
		this.finalAuditMonths = finalAuditMonths;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFirstProductType() {
		return firstProductType;
	}

	public void setFirstProductType(String firstProductType) {
		this.firstProductType = firstProductType;
	}

	public String getSecondProductType() {
		return secondProductType;
	}

	public void setSecondProductType(String secondProductType) {
		this.secondProductType = secondProductType;
	}

	public String getFinalProductType() {
		return finalProductType;
	}

	public void setFinalProductType(String finalProductType) {
		this.finalProductType = finalProductType;
	}

	public Double getStoreAssessAmount() {
		return storeAssessAmount;
	}

	public void setStoreAssessAmount(Double storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}

	public Double getFirstAuditAmount() {
		return firstAuditAmount;
	}

	public void setFirstAuditAmount(Double firstAuditAmount) {
		this.firstAuditAmount = firstAuditAmount;
	}

	public Double getSecondAuditAmount() {
		return secondAuditAmount;
	}

	public void setSecondAuditAmount(Double secondAuditAmount) {
		this.secondAuditAmount = secondAuditAmount;
	}

	public Double getFinalAuditAmount() {
		return finalAuditAmount;
	}

	public void setFinalAuditAmount(Double finalAuditAmount) {
		this.finalAuditAmount = finalAuditAmount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public List<String> getStoreCodeList() {
		return storeCodeList;
	}

	public void setStoreCodeList(List<String> storeCodeList) {
		this.storeCodeList = storeCodeList;
	}

	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

	public String getCostumerManagerName() {
		return costumerManagerName;
	}

	public void setCostumerManagerName(String costumerManagerName) {
		this.costumerManagerName = costumerManagerName;
	}

	public String getReviewMeetName() {
		return reviewMeetName;
	}

	public void setReviewMeetName(String reviewMeetName) {
		this.reviewMeetName = reviewMeetName;
	}

	public String getLoanStatusCode() {
		return loanStatusCode;
	}

	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}

	public String getLoanStatusCodeName() {
		return loanStatusCodeName;
	}

	public void setLoanStatusCodeName(String loanStatusCodeName) {
		this.loanStatusCodeName = loanStatusCodeName;
	}

	public Date getRateCheckDate() {
		return rateCheckDate;
	}

	public void setRateCheckDate(Date rateCheckDate) {
		this.rateCheckDate = rateCheckDate;
	}

	public String getPlateNumbers() {
		return plateNumbers;
	}

	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}

	public String getTelesalesFlag() {
		return telesalesFlag;
	}

	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getFilterUser() {
		return filterUser;
	}

	public void setFilterUser(String filterUser) {
		this.filterUser = filterUser;
	}

	public String getIsIn() {
		return isIn;
	}

	public void setIsIn(String isIn) {
		this.isIn = isIn;
	}

	public String getIsQueryAll() {
		return isQueryAll;
	}

	public void setIsQueryAll(String isQueryAll) {
		this.isQueryAll = isQueryAll;
	}

	public List<String> getNodeValList() {
		return nodeValList;
	}

	public void setNodeValList(List<String> nodeValList) {
		this.nodeValList = nodeValList;
	}

	public String getLendingTimeStart() {
		return lendingTimeStart;
	}

	public void setLendingTimeStart(String lendingTimeStart) {
		this.lendingTimeStart = lendingTimeStart;
	}

	public String getLendingTimeEnd() {
		return lendingTimeEnd;
	}

	public void setLendingTimeEnd(String lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getLendingUserId() {
		return lendingUserId;
	}

	public void setLendingUserId(String lendingUserId) {
		this.lendingUserId = lendingUserId;
	}

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getProductTypeContract() {
		return productTypeContract;
	}

	public void setProductTypeContract(String productTypeContract) {
		this.productTypeContract = productTypeContract;
	}

	public Integer getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}

	public BigDecimal getUrgeMoeny() {
		return urgeMoeny;
	}

	public void setUrgeMoeny(BigDecimal urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}

	public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}

	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}

	public Date getUrgeDecuteDate() {
		return urgeDecuteDate;
	}

	public void setUrgeDecuteDate(Date urgeDecuteDate) {
		this.urgeDecuteDate = urgeDecuteDate;
	}

	public Date getUrgeDecuteDateStart() {
		return urgeDecuteDateStart;
	}

	public void setUrgeDecuteDateStart(Date urgeDecuteDateStart) {
		this.urgeDecuteDateStart = urgeDecuteDateStart;
	}

	public Date getUrgeDecuteDateEnd() {
		return urgeDecuteDateEnd;
	}

	public void setUrgeDecuteDateEnd(Date urgeDecuteDateEnd) {
		this.urgeDecuteDateEnd = urgeDecuteDateEnd;
	}

	public String getDictDealStatus() {
		return dictDealStatus;
	}

	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
	}

	public String getDictDealReason() {
		return dictDealReason;
	}

	public void setDictDealReason(String dictDealReason) {
		this.dictDealReason = dictDealReason;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}

	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}

	public String getSplitFailResult() {
		return splitFailResult;
	}

	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}

	public String getMidId() {
		return midId;
	}

	public void setMidId(String midId) {
		this.midId = midId;
	}

	public String getIsAllData() {
		return isAllData;
	}

	public void setIsAllData(String isAllData) {
		this.isAllData = isAllData;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditRefuseReason() {
		return auditRefuseReason;
	}

	public void setAuditRefuseReason(String auditRefuseReason) {
		this.auditRefuseReason = auditRefuseReason;
	}

	public String getGrossFlag() {
		return grossFlag;
	}

	public void setGrossFlag(String grossFlag) {
		this.grossFlag = grossFlag;
	}

	public Date getApplyTimeStrat() {
		return applyTimeStrat;
	}

	public void setApplyTimeStrat(Date applyTimeStrat) {
		this.applyTimeStrat = applyTimeStrat;
	}

	public Date getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public String getIsExtendsion() {
		return isExtendsion;
	}

	public void setIsExtendsion(String isExtendsion) {
		this.isExtendsion = isExtendsion;
	}

	public String getOriginalContractCode() {
		return originalContractCode;
	}

	public void setOriginalContractCode(String originalContractCode) {
		this.originalContractCode = originalContractCode;
	}

	public BigDecimal getDerate() {
		if(derate != null){
			derate = derate.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}else{
			derate = BigDecimal.ZERO.setScale(2);
		}
		return derate;
	}

	public void setDerate(BigDecimal derate) {
		this.derate = derate;
	}

	public int getExtendNum() {
		return extendNum;
	}

	public void setExtendNum(int extendNum) {
		this.extendNum = extendNum;
	}

	public String getCobos() {
		return cobos;
	}

	public void setCobos(String cobos) {
		this.cobos = cobos;
	}

	public String getBorrowTursteeFlag() {
		return borrowTursteeFlag;
	}

	public void setBorrowTursteeFlag(String borrowTursteeFlag) {
		this.borrowTursteeFlag = borrowTursteeFlag;
	}

	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}

	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getSigncount() {
		return signcount;
	}

	public void setSigncount(String signcount) {
		this.signcount = signcount;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	public Double getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(Double grantAmount) {
		this.grantAmount = grantAmount;
	}

	public List<String> getApplyIdList() {
		return applyIdList;
	}

	public void setApplyIdList(List<String> applyIdList) {
		this.applyIdList = applyIdList;
	}

	public Double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(Double parkingFee) {
		this.parkingFee = parkingFee;
	}

	public Double getFlowFee() {
		return flowFee;
	}

	public void setFlowFee(Double flowFee) {
		this.flowFee = flowFee;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public Date getContractTipDay() {
		return contractTipDay;
	}

	public void setContractTipDay(Date contractTipDay) {
		this.contractTipDay = contractTipDay;
	}

	public String getPaperLessFlag() {
		return paperLessFlag;
	}

	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCoboEmail() {
		return coboEmail;
	}

	public void setCoboEmail(String coboEmail) {
		this.coboEmail = coboEmail;
	}

	public String getDictSourceType() {
		return dictSourceType;
	}

	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}

	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public Date getContractEndDay() {
		return contractEndDay;
	}

	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}

	public Date getSettledDate() {
		return settledDate;
	}

	public void setSettledDate(Date settledDate) {
		this.settledDate = settledDate;
	}

	public String getUrgentDegree() {
		return urgentDegree;
	}

	public void setUrgentDegree(String urgentDegree) {
		this.urgentDegree = urgentDegree;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getSignDocId() {
		return signDocId;
	}

	public void setSignDocId(String signDocId) {
		this.signDocId = signDocId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAgrDictLoanStatus() {
		return agrDictLoanStatus;
	}

	public void setAgrDictLoanStatus(String agrDictLoanStatus) {
		this.agrDictLoanStatus = agrDictLoanStatus;
	}

	public BigDecimal getFirstServiceTariffing() {
		return firstServiceTariffingRate;
	}

	public void setFirstServiceTariffingRate(
			BigDecimal firstServiceTariffingRate) {
		this.firstServiceTariffingRate = firstServiceTariffingRate;
	}

	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Double getServiceMoeny() {
		return serviceMoeny;
	}

	public void setServiceMoeny(Double serviceMoeny) {
		this.serviceMoeny = serviceMoeny;
	}

	public Double getLongProductMoeny() {
		return longProductMoeny;
	}

	public void setLongProductMoeny(Double longProductMoeny) {
		this.longProductMoeny = longProductMoeny;
	}

	public Double getChannelMoeny() {
		return channelMoeny;
	}

	public void setChannelMoeny(Double channelMoeny) {
		this.channelMoeny = channelMoeny;
	}

	public Double getParkMoeny() {
		return parkMoeny;
	}

	public void setParkMoeny(Double parkMoeny) {
		this.parkMoeny = parkMoeny;
	}

	public Double getGPSInstallMoeny() {
		return GPSInstallMoeny;
	}

	public void setGPSInstallMoeny(Double gPSInstallMoeny) {
		GPSInstallMoeny = gPSInstallMoeny;
	}

	public Double getGPSUseMoeny() {
		return GPSUseMoeny;
	}

	public void setGPSUseMoeny(Double gPSUseMoeny) {
		GPSUseMoeny = gPSUseMoeny;
	}

	public Double getPlatformFlowMoeny() {
		return platformFlowMoeny;
	}

	public void setPlatformFlowMoeny(Double platformFlowMoeny) {
		this.platformFlowMoeny = platformFlowMoeny;
	}

	public BigDecimal getFirstServiceTariffingRate() {
		return firstServiceTariffingRate;
	}

	public Double getOutVisitFee() {
		return outVisitFee;
	}

	public void setOutVisitFee(Double outVisitFee) {
		this.outVisitFee = outVisitFee;
	}

}
