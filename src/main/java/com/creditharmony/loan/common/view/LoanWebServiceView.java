package com.creditharmony.loan.common.view;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 生成合同编号返回工作流实体
 * 
 * @Class Name LoanWebServiceView
 * @author xiaoniu.hu
 * @Create In 2016年3月3日
 */
@XmlType(name = "", propOrder = { "contractCode", "channelCode", "channelName",
		"paperLessFlag", "orderField", "contractVersion", "model" })
@XmlRootElement
public class LoanWebServiceView {
	private String contractCode; // 合同编号
	private String channelCode; // 渠道编码
	private String channelName; // 渠道名称
	private String paperLessFlag = "1"; // 无纸化标识
	private String orderField; // 合同排序字段
	private String contractVersion; // 合同版本号
	private String model; // 模式：金信或者TG，参见LoanModel

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPaperLessFlag() {
		return paperLessFlag;
	}

	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
