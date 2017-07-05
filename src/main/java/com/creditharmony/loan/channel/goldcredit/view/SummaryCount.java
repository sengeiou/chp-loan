package com.creditharmony.loan.channel.goldcredit.view;

import com.creditharmony.core.persistence.DataEntity;


@SuppressWarnings("serial")
public class SummaryCount extends DataEntity<SummaryCount>{
	
	private String id;
	private String createDate;
	private Integer exportNumber;
	public String getId() {
		return id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public Integer getExportNumber() {
		return exportNumber;
	}
	public void setExportNumber(Integer exportNumber) {
		this.exportNumber = exportNumber;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
