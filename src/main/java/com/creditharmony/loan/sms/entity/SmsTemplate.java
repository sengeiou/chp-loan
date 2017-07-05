package com.creditharmony.loan.sms.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 短信模板Bean
 * 
 * @Class Name SmsTemplate
 * @author 朱杰
 * @Create In 2016年3月8日
 */
public class SmsTemplate extends DataEntity<SmsTemplate> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -8667820469262243972L;
	/**
	 * 模板类型
	 */
	private String templateType;
	/**
	 * 模板代码
	 */
	private String templateCode;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 模板内容
	 */
	private String templateContent;
	/**
	 * 模板状态
	 */
	private String templateStatus;
	/**
	 * 描述
	 */
	private String templateDescription;
	/**
	 * URL
	 */
	private String url;

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
