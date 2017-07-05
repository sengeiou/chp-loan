package com.creditharmony.loan.webservice.infodisclosure.bean;

import java.util.List;

import com.creditharmony.loan.webservice.infodisclosure.entity.InfoDisclosure;

public class ResultBean {

	private InfoDisclosure infoDisclosure;
	
	private List<InfoDisclosure> infoDisclosureList;
	
	private int isSuccess;//是否成功
	
	private String errorInfo;//错误信息

	public InfoDisclosure getInfoDisclosure() {
		return infoDisclosure;
	}

	public void setInfoDisclosure(InfoDisclosure infoDisclosure) {
		this.infoDisclosure = infoDisclosure;
	}

	public List<InfoDisclosure> getInfoDisclosureList() {
		return infoDisclosureList;
	}

	public void setInfoDisclosureList(List<InfoDisclosure> infoDisclosureList) {
		this.infoDisclosureList = infoDisclosureList;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
