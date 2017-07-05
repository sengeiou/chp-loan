package com.creditharmony.loan.yunwei.overtime.entity;

import java.util.Date;

import com.creditharmony.bpm.frame.view.WorkItemView;

public class LoanWorkItemView extends WorkItemView {
	private Date timeOutPoint;

	public Date getTimeOutPoint() {
		return timeOutPoint;
	}

	public void setTimeOutPoint(Date timeOutPoint) {
		this.timeOutPoint = timeOutPoint;
	}
 
	
}
