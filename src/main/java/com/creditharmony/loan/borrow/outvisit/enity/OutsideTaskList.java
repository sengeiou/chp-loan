package com.creditharmony.loan.borrow.outvisit.enity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 外访_外访任务清单
 * @Class Name OutsideCheckList
 * @author 王彬彬
 * @Create In 2016年02月24日
 */
@SuppressWarnings("serial")
public class OutsideTaskList extends DataEntity<OutsideTaskList>{

	private String loanCode; 			// 借款编码
	private String surveyEmpId; 		// 外访人员ID
	private BigDecimal itemDistance; 	// 外访距离
	private Date surveyStartTime; 		// 外访发起时间
	private Date surveyEndTime; 		// 外访结束时间
	private String dictSurveyStatus; 	// 外访状态
	private String dictCheckType; 		// 类型(初审，信审初审，复议初审)
	private String rId;					// 关联ID(变更历史表)
	
	public String getLoanCode() {
		return loanCode;
	}
	
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	
	public String getSurveyEmpId() {
		return surveyEmpId;
	}
	
	public void setSurveyEmpId(String surveyEmpId) {
		this.surveyEmpId = surveyEmpId;
	}
	
	public BigDecimal getItemDistance() {
		return itemDistance;
	}
	
	public void setItemDistance(BigDecimal itemDistance) {
		this.itemDistance = itemDistance;
	}
	
	public Date getSurveyStartTime() {
		return surveyStartTime;
	}
	
	public void setSurveyStartTime(Date surveyStartTime) {
		this.surveyStartTime = surveyStartTime;
	}
	
	public Date getSurveyEndTime() {
		return surveyEndTime;
	}
	
	public void setSurveyEndTime(Date surveyEndTime) {
		this.surveyEndTime = surveyEndTime;
	}
	
	public String getDictSurveyStatus() {
		return dictSurveyStatus;
	}
	
	public void setDictSurveyStatus(String dictSurveyStatus) {
		this.dictSurveyStatus = dictSurveyStatus;
	}
	
	public String getDictCheckType() {
		return dictCheckType;
	}
	
	public void setDictCheckType(String dictCheckType) {
		this.dictCheckType = dictCheckType;
	}
	
	public String getrId() {
		return rId;
	}
	
	public void setrId(String rId) {
		this.rId = rId;
	}
	
}
