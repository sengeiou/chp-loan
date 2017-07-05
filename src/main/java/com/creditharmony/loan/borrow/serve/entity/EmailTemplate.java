package com.creditharmony.loan.borrow.serve.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 邮件模板
 * 
 * @Class Name EmailTemplateType
 * @author WJJ
 * @Create In 2016年12月7日
 */
public class EmailTemplate extends DataEntity<EmailTemplate> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String templateName;
	private String template_type;
	private String templateContent;
	private String status;
	private String emailDescription;
	private String url;
	
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
	public String getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmailDescription() {
		return emailDescription;
	}
	public void setEmailDescription(String emailDescription) {
		this.emailDescription = emailDescription;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
