package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 产品表
 * @Class Name JkProducts
 * @author zhaojinping
 * @Create In 2015年12月22日
 */
@SuppressWarnings("serial")
public class JkProducts extends DataEntity<JkProducts>{
    // 产品表主键
	private String id;
    // 产品编号
    private String productCode;
    // 产品名称
    private String productName;
    // 产品类型
    private String productType;
    // 产品状态
    private String productStatus;
    // 最低限额
    private BigDecimal limitLower;
    // 最高限额
    private BigDecimal limitUpper;
    // 产品利率上限
    private BigDecimal productLimitUpperraio;
    // 产品利率下限
    private BigDecimal productLimitLowerraio;
    // 期数
    private int productMonths;
    // 折扣率
    private String productRate;
    // 启动日期
    private Date startDay;
    // 结束日期
    private Date closeDay;
    // 协议
    private String treaty;
    // 公式
    private Short formula;
    // 业务类型
    private String classtype;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public BigDecimal getLimitLower() {
		return limitLower;
	}
	public void setLimitLower(BigDecimal limitLower) {
		this.limitLower = limitLower;
	}
	public BigDecimal getLimitUpper() {
		return limitUpper;
	}
	public void setLimitUpper(BigDecimal limitUpper) {
		this.limitUpper = limitUpper;
	}
	public BigDecimal getProductLimitUpperraio() {
		return productLimitUpperraio;
	}
	public void setProductLimitUpperraio(BigDecimal productLimitUpperraio) {
		this.productLimitUpperraio = productLimitUpperraio;
	}
	public BigDecimal getProductLimitLowerraio() {
		return productLimitLowerraio;
	}
	public void setProductLimitLowerraio(BigDecimal productLimitLowerraio) {
		this.productLimitLowerraio = productLimitLowerraio;
	}
	public int getProductMonths() {
		return productMonths;
	}
	public void setProductMonths(int productMonths) {
		this.productMonths = productMonths;
	}
	public String getProductRate() {
		return productRate;
	}
	public void setProductRate(String productRate) {
		this.productRate = productRate;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getCloseDay() {
		return closeDay;
	}
	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}
	public String getTreaty() {
		return treaty;
	}
	public void setTreaty(String treaty) {
		this.treaty = treaty;
	}
	public Short getFormula() {
		return formula;
	}
	public void setFormula(Short formula) {
		this.formula = formula;
	}
	public String getClasstype() {
		return classtype;
	}
	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}
}
