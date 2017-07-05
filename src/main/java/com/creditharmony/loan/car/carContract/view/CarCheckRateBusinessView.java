package com.creditharmony.loan.car.carContract.view;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.loan.car.carExtend.view.CarExtendBaseBusinessView;
import com.creditharmony.loan.car.common.entity.CarCheckRate;

/**
 * 利率审核对应view，用于接收页面填写值作相应业务逻辑处理
 * @Class Name CarCheckRateBusinessView
 * @author 申诗阔
 * @Create In 2016年2月19日
 */
public class CarCheckRateBusinessView extends CarExtendBaseBusinessView implements
		Serializable {

	private static final long serialVersionUID = 7047874176750558854L;

	private String loanCode;					// 借款编号
	
	private String dictLoanStatus;				// 借款状态--车借

	private CarCheckRate carCheckRate;			// 合同费率信息
	
	private String centerUser;					// 中间人
	
	private String checkResult;					// 审核结果
	
	private String backReason;					// 退回原因
	
	private String backNode;					// 合同审核 退回节点
	
	private String dictBackMestype;				// 合同审核 审核退回原因
	
	private String contractCode;				// 合同编号
	
	private String contractVersion;				// 合同版本号
	
	private Double grantAmount;					//放款金额 
	
	private Double deductsAmount;				//划扣金额 
	
	private String operResultName;				// 操作结果，中文
	 
	private String grossFlag;					// 通过标识,1为通过，0为不通过
	
	private String conditionThroughFlag;		// 记录是否为附条件通过
	
	private Double extensionFee;				// 展期费用
	private String applyStatusCode;				// 借款状态--展期
	private Double contractAmount;			// 合同金额--展期
	private Double derate;					// 降额
	
	private Double comprehensiveServiceFee;//  综合服务费
	
	private Date contractExpirationDate;       //合同到期提醒
	
	private String timeOutFlag;	//轮询程序（chp-roll）合同超时作废轮询检测标志，1为检测，0为不检测
	private Date timeOutPoint;	//轮询程序（chp-roll）合同超时作废日期
	private Integer timeoutCheckStage;	//超时检测阶段  0：没有进行利率审核； 1：完成第一次利率审核
//	//排序字段
//	private String orderField;
//	//第一次退回的源节点名称--退回标红置顶业务所需
//	private String firstBackSourceStep;
	
	private String signingPlatformName;//签约平台
	
	private String loanFlag;
	
	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}

	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}

	public Double getComprehensiveServiceFee() {
		return comprehensiveServiceFee;
	}

	public void setComprehensiveServiceFee(Double comprehensiveServiceFee) {
		this.comprehensiveServiceFee = comprehensiveServiceFee;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public CarCheckRate getCarCheckRate() {
		return carCheckRate;
	}

	public void setCarCheckRate(CarCheckRate carCheckRate) {
		this.carCheckRate = carCheckRate;
	}

	public String getCenterUser() {
		return centerUser;
	}

	public void setCenterUser(String centerUser) {
		this.centerUser = centerUser;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getBackNode() {
		return backNode;
	}

	public void setBackNode(String backNode) {
		this.backNode = backNode;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public Double getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(Double grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getOperResultName() {
		return operResultName;
	}

	public void setOperResultName(String operResultName) {
		this.operResultName = operResultName;
	}

	public String getGrossFlag() {
		return grossFlag;
	}

	public void setGrossFlag(String grossFlag) {
		this.grossFlag = grossFlag;
	}

	public Double getExtensionFee() {
		return extensionFee;
	}

	public void setExtensionFee(Double extensionFee) {
		this.extensionFee = extensionFee;
	}

	public String getApplyStatusCode() {
		return applyStatusCode;
	}

	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getDerate() {
		return derate;
	}

	public void setDerate(Double derate) {
		this.derate = derate;
	}

	public String getConditionThroughFlag() {
		return conditionThroughFlag;
	}

	public void setConditionThroughFlag(String conditionThroughFlag) {
		this.conditionThroughFlag = conditionThroughFlag;
	}

	public String getTimeOutFlag() {
		return timeOutFlag;
	}

	public void setTimeOutFlag(String timeOutFlag) {
		this.timeOutFlag = timeOutFlag;
	}

	public Date getTimeOutPoint() {
		return timeOutPoint;
	}
	public void setTimeOutPoint(Date timeOutPoint) {
		this.timeOutPoint = timeOutPoint;
	}

	public Integer getTimeoutCheckStage() {
		return timeoutCheckStage;
	}

	public void setTimeoutCheckStage(Integer timeoutCheckStage) {
		this.timeoutCheckStage = timeoutCheckStage;
	}


	public Double getDeductsAmount() {
		return deductsAmount;
	}

	public void setDeductsAmount(Double deductsAmount) {
		this.deductsAmount = deductsAmount;
	}

	public String getSigningPlatformName() {
		return signingPlatformName;
	}

	public void setSigningPlatformName(String signingPlatformName) {
		this.signingPlatformName = signingPlatformName;
	}

	public String getDictBackMestype() {
		return dictBackMestype;
	}

	public void setDictBackMestype(String dictBackMestype) {
		this.dictBackMestype = dictBackMestype;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
