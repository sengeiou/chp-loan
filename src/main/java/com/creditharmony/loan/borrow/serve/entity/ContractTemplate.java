package com.creditharmony.loan.borrow.serve.entity;

import java.util.Date;

public class ContractTemplate {
	/** 模板ID */ 
	private String id; 
	/** 模板名称 */ 
	private String templateName; 
	/** 模板类型 0：首期；1：非首期 */ 
	private String templateType; 
	/** 模板内容 */ 
	private String templateContent; 
	/** 模板内容(缩略显示用) */ 
	private String templateContentDisp; 
	/** 模板状态 */ 
	private String status; 
	/** url */ 
	private String url; 
	/** 主题 */ 
	private String emailDescription; 
	/** 创建人 */ 
	private String createBy; 
	/** 创建时间 */ 
	private Date createTime; 
	/** 修改人 */ 
	private String modifyBy; 
	/** 修改时间 */ 
	private Date modifyTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getTemplateContentDisp() {
		return templateContentDisp;
	}
	public void setTemplateContentDisp(String templateContentDisp) {
		this.templateContentDisp = templateContentDisp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmailDescription() {
		return emailDescription;
	}
	public void setEmailDescription(String emailDescription) {
		this.emailDescription = emailDescription;
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
