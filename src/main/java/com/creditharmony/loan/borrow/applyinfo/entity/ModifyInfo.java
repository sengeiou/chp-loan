package com.creditharmony.loan.borrow.applyinfo.entity;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 修改信息详细记录
 * @Class Name ModifyInfo
 * @author lirui
 * @Create In 2016年2月18日
 */
@SuppressWarnings("serial")
public class ModifyInfo extends DataEntity<ModifyInfo> {
	// id
	private String id;
	// 借款编码
	private String loanCode;
	// 修改类型
	private String modifyType;
	// 关联id
	private String rId;
	// 借款状态
	private String dictLoanStatus;
	// 原始字段
	private String primitiveColumn;
	// 原始值
	private String primitiveValue;
	// 修改后字段
	private String modifyColumn;
	// 修改后值
	private String modifyValue;
	// 创建人
	private String createBy;
	// 创建时间
	private Date createTime;
	// 修改人
	private String modifyBy;
	// 修改时间
	private Date modifyTime;
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
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getPrimitiveColumn() {
		return primitiveColumn;
	}
	public void setPrimitiveColumn(String primitiveColumn) {
		this.primitiveColumn = primitiveColumn;
	}
	public String getPrimitiveValue() {
		return primitiveValue;
	}
	public void setPrimitiveValue(String primitiveValue) {
		this.primitiveValue = primitiveValue;
	}
	public String getModifyColumn() {
		return modifyColumn;
	}
	public void setModifyColumn(String modifyColumn) {
		this.modifyColumn = modifyColumn;
	}
	public String getModifyValue() {
		return modifyValue;
	}
	public void setModifyValue(String modifyValue) {
		this.modifyValue = modifyValue;
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
	
	
}