/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackDeductsDue.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name PaybackDeductsDue
 * @author zhaojinping
 * @Create In 2015年12月10日
 */

@SuppressWarnings("serial")
public class PaybackDeductsDue extends DataEntity<PaybackDeductsDue>{
        // ID
	    private String id;
	    private List<String> ids;
        // 预约银行 
	    private String dueBank;
	    // 预约银行 
	    private String dueBankLabel;
        // 预约时间
	    private Date dueTime;
	    private String dueTimeStr;
	    // 划扣平台
	    private String dictDealType;
        // 划扣方式(批量或实时)
	    private String dictDeductsType;
        // 创建日期
	    private Date createDay;
	    private String createDayStr;
        // 是否有效
	    private String effectiveFlag;
	    // 银行
	    private String bank;
	    // 银行id
	    private String bankId;
	    private List<String> bankIds;
	    
	    private String isExecute;
	    
	    private String  modeWay; // 方式判断 委托充值 或者划拨
	    
	    public String getDueBankLabel() {
			return dueBankLabel;
		}

		public void setDueBankLabel(String dueBankLabel) {
			this.dueBankLabel = dueBankLabel;
		}

		public String getDueBank() {
			return dueBank;
		}

		public void setDueBank(String dueBank) {
			this.dueBank = dueBank;
		}

		public Date getDueTime() {
			return dueTime;
		}

		public void setDueTime(Date dueTime) {
			this.dueTime = dueTime;
		}

		public String getDictDealType() {
			return dictDealType;
		}

		public void setDictDealType(String dictDealType) {
			this.dictDealType = dictDealType;
		}

		public String getDictDeductsType() {
			return dictDeductsType;
		}

		public void setDictDeductsType(String dictDeductsType) {
			this.dictDeductsType = dictDeductsType;
		}

		public Date getCreateDay() {
			return createDay;
		}

		public void setCreateDay(Date createDay) {
			this.createDay = createDay;
		}

		public String getEffectiveFlag() {
			return effectiveFlag;
		}

		public void setEffectiveFlag(String effectiveFlag) {
			this.effectiveFlag = effectiveFlag;
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

		private Date createTime;

	    private String modifyBy;

	    private Date modifyTime;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

		public String getBank() {
			return bank;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public String getBankId() {
			return bankId;
		}

		public void setBankId(String bankId) {
			this.bankId = bankId;
		}

		public String getCreateDayStr() {
			return createDayStr;
		}

		public void setCreateDayStr(String createDayStr) {
			this.createDayStr = createDayStr;
		}

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		public List<String> getBankIds() {
			return bankIds;
		}

		public void setBankIds(List<String> bankIds) {
			this.bankIds = bankIds;
		}

		public String getDueTimeStr() {
			return dueTimeStr;
		}

		public void setDueTimeStr(String dueTimeStr) {
			this.dueTimeStr = dueTimeStr;
		}

		public String getIsExecute() {
			return isExecute;
		}

		public void setIsExecute(String isExecute) {
			this.isExecute = isExecute;
		}

		public String getModeWay() {
			return modeWay;
		}

		public void setModeWay(String modeWay) {
			this.modeWay = modeWay;
		}
	  
}
