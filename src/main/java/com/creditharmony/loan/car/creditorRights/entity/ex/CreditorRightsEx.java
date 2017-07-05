package com.creditharmony.loan.car.creditorRights.entity.ex;

import java.util.Date;

import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;

public class CreditorRightsEx extends CreditorRights {
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = -142613351965950427L;
	
	//起始录入日期
    private Date entryDayStart;
    //结束录入日期
    private Date entryDayEnd;
    
	public Date getEntryDayStart() {
		return entryDayStart;
	}
	public void setEntryDayStart(Date entryDayStart) {
		this.entryDayStart = entryDayStart;
	}
	public Date getEntryDayEnd() {
		return entryDayEnd;
	}
	public void setEntryDayEnd(Date entryDayEnd) {
		this.entryDayEnd = entryDayEnd;
	}
    
}
