package com.creditharmony.loan.borrow.zhongjin.view;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 中金划扣
 * @Class Name PaybackCpcnModel
 * @author 吴建佳
 * @Create In 2016年3月3日
 */
public class PaybackCpcnModel 
{
	private String   id;
	private String serialnum;
	private String accountNum;
	private String accountName;
	private BigDecimal  beginMoney;
	private BigDecimal  endMoney;
	private String banknum;
	private String contractCode;
	private String   createTime;
	private String   opearTime;
	private String   status;
	private String   nostatus;
	private String   backResult;
	private String   beginBackTime;
	private String   endBackTime;
	private String[] checkIds;
	private Boolean   backSuccess;
	private String appoint;

	private String deductType;//划扣方式
	private String deductDate;//预约日期
	private String[] deductTime;//预约时间点
	private int pageNo;
	private int pageSize;
	//操作类型
	private String operateType;
	//操作类型
	private String operateTypeName;
	private String deductStatus;//数据状态

	//银行ID
	private String bankNo;
	//开户行名称
	private String bankName;
	private String[] bankIds;
	//预约开始时间
	private String deductBeginTime;
	//预约结束时间
	private String deductEndTime;

	private String orderType;
	private Long createById;
	
	private BigDecimal  applyReallyAmount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	
	public BigDecimal getBeginMoney() {
		return beginMoney;
	}
	public void setBeginMoney(BigDecimal beginMoney) {
		this.beginMoney = beginMoney;
	}
	public BigDecimal getEndMoney() {
		return endMoney;
	}
	public void setEndMoney(BigDecimal endMoney) {
		this.endMoney = endMoney;
	}
	
	public String getBanknum() {
		return banknum;
	}
	public void setBanknum(String banknum) {
		this.banknum = banknum;
	}
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(String[] checkIds) {
		this.checkIds = checkIds;
	}
	public String getBackResult() {
		return backResult;
	}
	public void setBackResult(String backResult) {
		this.backResult = backResult;
	}
	public String getBeginBackTime() {
		return beginBackTime;
	}
	public void setBeginBackTime(String beginBackTime) {
		this.beginBackTime = beginBackTime;
	}
	public String getEndBackTime() {
		return this.endBackTime;
	}
	public void setEndBackTime(String endBackTime) {
		this.endBackTime = endBackTime;
	}
	public String getNostatus() {
		return nostatus;
	}
	public void setNostatus(String nostatus) {
		this.nostatus = nostatus;
	}
	public String getOpearTime() {
		return opearTime;
	}
	public void setOpearTime(String opearTime) {
		this.opearTime = opearTime;
	}
	public Boolean getBackSuccess() {
		return backSuccess;
	}
	public void setBackSuccess(Boolean backSuccess) {
		this.backSuccess = backSuccess;
	}


	public String getDeductType() {
		return deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}

	public String getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}

	public String[] getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(String[] deductTime) {
		this.deductTime = deductTime;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}


	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		this.deductStatus = deductStatus;
	}

	public String getOperateTypeName() {
		return operateTypeName;
	}

	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDeductBeginTime() {
		return deductBeginTime;
	}

	public void setDeductBeginTime(String deductBeginTime) {
		this.deductBeginTime = deductBeginTime;
	}

	public String getDeductEndTime() {
		return deductEndTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setDeductEndTime(String deductEndTime) {
		this.deductEndTime = deductEndTime;

	}
	public String getAppoint() {
		return appoint;
	}
	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}
	public Long getCreateById() {
		return createById;
	}
	public void setCreateById(Long createById) {
		this.createById = createById;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String[] getBankIds() {
		return bankIds;
	}
	public void setBankIds(String[] bankIds) {
		this.bankIds = bankIds;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	
}
