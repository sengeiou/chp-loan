package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 审核结果
 * @Class Name CarAuditResult
 * @author 安子帅
 * @Create In 2016年1月22日
 */
public class CarAuditResult extends DataEntity<CarAuditResult> {

	private static final long serialVersionUID = 6874955908777445756L;

	  private String id;       //ID
	  private String loanCode;       // 借款编码
	  private String rStatusHisId;       //关联ID(变更历史表)
	  private String singleTastId ;       //关联ID（分单表)
	  private String auditResult;       //审批结果
	  private String dictProductType;       //审批产品类型
	  private String dictAuditMonths;       // 审批分期
	  private BigDecimal finalEvaluatedPrice;       //终审评估价格
	  private BigDecimal auditAmount;       // 批复金额
	  private BigDecimal grossRate ;       //总费率
	  private BigDecimal firstServiceTariffing;       // 首期服务费率
	  private String auditCheckExamine;       // 审批意见
	  private String dictCheckType;       //审核类型(初审、复审、终审)
	  private Date createTimeTimestamp;       //创建时间
	  private String createBy;       //创建人
	  private Date modifyTime;       // 修改时间
	  private String modifyBy;       // 修改人
	  private String returnType;       //退回类型（补传资料，追加共借人，修改数据，其它）
	  private String auditJson;       // JSON
	  private String productTypeName;	// 产品类型名称
	  private BigDecimal outVisitDistance;//外访距离
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
	public String getrStatusHisId() {
		return rStatusHisId;
	}
	public void setrStatusHisId(String rStatusHisId) {
		this.rStatusHisId = rStatusHisId;
	}
	public String getSingleTastId() {
		return singleTastId;
	}
	public void setSingleTastId(String singleTastId) {
		this.singleTastId = singleTastId;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	public String getDictAuditMonths() {
		return dictAuditMonths;
	}
	public void setDictAuditMonths(String dictAuditMonths) {
		this.dictAuditMonths = dictAuditMonths;
	}
	public BigDecimal getFinalEvaluatedPrice() {
		return finalEvaluatedPrice;
	}
	public void setFinalEvaluatedPrice(BigDecimal finalEvaluatedPrice) {
		this.finalEvaluatedPrice = finalEvaluatedPrice;
	}
	public BigDecimal getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}
	
	public BigDecimal getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(BigDecimal grossRate) {
		this.grossRate = grossRate;
	}
	public BigDecimal getFirstServiceTariffing() {
		return firstServiceTariffing;
	}
	public void setFirstServiceTariffing(BigDecimal firstServiceTariffing) {
		this.firstServiceTariffing = firstServiceTariffing;
	}
	public String getAuditCheckExamine() {
		return auditCheckExamine;
	}
	public void setAuditCheckExamine(String auditCheckExamine) {
		this.auditCheckExamine = auditCheckExamine;
	}
	public String getDictCheckType() {
		return dictCheckType;
	}
	public void setDictCheckType(String dictCheckType) {
		this.dictCheckType = dictCheckType;
	}
	public Date getCreateTimeTimestamp() {
		return createTimeTimestamp;
	}
	public void setCreateTimeTimestamp(Date createTimeTimestamp) {
		this.createTimeTimestamp = createTimeTimestamp;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getAuditJson() {
		return auditJson;
	}
	public void setAuditJson(String auditJson) {
		this.auditJson = auditJson;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public BigDecimal getOutVisitDistance() {
		return outVisitDistance;
	}
	public void setOutVisitDistance(BigDecimal outVisitDistance) {
		this.outVisitDistance = outVisitDistance;
	}
	
	
}
