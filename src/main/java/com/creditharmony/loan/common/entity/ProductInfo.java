/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityProductInfo.java
 * @Create By 王彬彬
 * @Create In 2015年12月26日 下午12:01:05
 */
package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 产品信息表
 * 
 * @Class Name ProductInfo
 * @author 王彬彬
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class ProductInfo extends DataEntity<ProductInfo> {
	/** 序号 */
	private String no;
	/** 产品类型 */
	private String productType;
	/** 产品编号 */
	private String productCode;
	/** 产品名称 */
	private String productName;
	/** 产品状态 */
	private String productStatus;
	/** 最低限额 */
	private BigDecimal limitLower;
	/** 最高限额 */
	private BigDecimal limitUpper;
	/** 产品利率上限 */
	private BigDecimal productLimitUpperraio;
	/** 产品利率下限 */
	private BigDecimal productLimitLowerraio;
	/** 期数 */
	private String productMonths;
	/** 折扣率 */
	private String discountRate;
	/** 启动日期 */
	private Date startDay;
	/** 结束日期 */
	private Date closeDay;
	/** 协议 */
	private String treaty;
	/** 公式 */
	private String formula;
	/** 业务类型 */
	private String classType;
	/** 利率 */
	private String interestRate;
	/** 所需材料 */
	private String material;
	/** 描述 */
	private String remark;

	public String getNo() {
		return no;
	}

	public String getProductType() {
		return productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public BigDecimal getLimitLower() {
		return limitLower;
	}

	public BigDecimal getLimitUpper() {
		return limitUpper;
	}

	public BigDecimal getProductLimitUpperraio() {
		return productLimitUpperraio;
	}

	public BigDecimal getProductLimitLowerraio() {
		return productLimitLowerraio;
	}

	public String getProductMonths() {
		return productMonths;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public Date getStartDay() {
		return startDay;
	}

	public Date getCloseDay() {
		return closeDay;
	}

	public String getTreaty() {
		return treaty;
	}

	public String getFormula() {
		return formula;
	}

	public String getClassType() {
		return classType;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public String getMaterial() {
		return material;
	}

	public String getRemark() {
		return remark;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public void setLimitLower(BigDecimal limitLower) {
		this.limitLower = limitLower;
	}

	public void setLimitUpper(BigDecimal limitUpper) {
		this.limitUpper = limitUpper;
	}

	public void setProductLimitUpperraio(BigDecimal productLimitUpperraio) {
		this.productLimitUpperraio = productLimitUpperraio;
	}

	public void setProductLimitLowerraio(BigDecimal productLimitLowerraio) {
		this.productLimitLowerraio = productLimitLowerraio;
	}

	public void setProductMonths(String productMonths) {
		this.productMonths = productMonths;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	public void setTreaty(String treaty) {
		this.treaty = treaty;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}