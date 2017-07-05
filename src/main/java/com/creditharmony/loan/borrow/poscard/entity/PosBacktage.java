/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.PaybackMonth.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.poscard.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * pos后台数据表
 * 
 * @Class Name PosBacktage
 * @author 管洪昌
 * @Create In 2016年1月20日
 */
@SuppressWarnings("serial")
public class PosBacktage  extends DataEntity<PosBacktage>{
	
	    // 序号
		private String index;
		// 合同编号
		@ExcelField(title = "合同编号", type = 0, align = 2, sort = 8,groups ={1,2})
		private String contractCode;
		// 参考编号
		@ExcelField(title = "参考号", type = 0, align = 2, sort = 2,groups ={1,2})
		private String referCode;
		//pos订单编号
		@ExcelField(title = "pos机订单编号", type = 0, align = 2, sort = 3,groups ={1,2})
		private String posOrderNumber;
		//查账日期
		@ExcelField(title = "查账日期", type = 0, align = 2, sort = 9,groups ={1,2})
		private Date auditDate;
		//到账日期
		@ExcelField(title = "到账日期", type = 0, align = 2, sort = 4,groups ={1,2})
		private Date paybackDate;
		//存入账户
		@ExcelField(title = "存入账户", type = 0, align = 2, sort = 5,groups ={1,2})
		private  String depositedAccount;
		//匹配状态
		@ExcelField(title = "匹配状态", type = 0, align = 2, sort = 7,groups ={1,2},dictType="jk_matching")
		private String matchingState;
		//匹配状态名称
		private String matchingStateLabel;
		//金额
		@ExcelField(title = "金额", type = 0, align = 2, sort = 6,groups ={1,2})
		private BigDecimal applyReallyAmount;
	//------------------------------------------------------------------------------------	
		//到账日期开始
		private Date paybackBeginDate;
		//到账日期结束
		private Date paybackEndDate;
		//查账日期开始
		private Date beginDate;
		//查账日期结束
		private Date endDate;
		//POS登录密码
		private String posOldPassword;
		//登录名称
		private String loginName;
		//新密码
		private String posNewPassword;
		//id
		private String id;
		//还款申请id
		private String payBackApplyId;
		//POS已匹配成功列表用
		//订单编号
		private String posBillCode;
	
		//客户姓名
		private String customerName;
		//合同到期日
		private Date contractEndDay;
		//门店名称
		private String loanTeamOrgId;
		//借款状态
		private String dictLoanStatus;
		//借款状态名称
		private String dictLoanStatusLabel;
		//还款日
		private String paybackDay;
		//申请还款金额
		private BigDecimal applyAmount;
		//还款类型
		private String dictPayUse;
		//还款类型名称
		private String dictPayUseLabel;
		//还款类型
		private String dictPayStatus;
		//还款类型名称
		private String dictPayStatusLabel;
		//回盘结果
		private String dictPayResult;
		//回盘结果名称
		private String dictPayResultLabel;
		//失败原因
		private String applyBackMes;
		//标识
		private String loanFlag;
		//标识名称
		private String loanFlagLabel;
		//是否电销售
		private String customerTelesalesFlag;
		//是否电销售名称
		private String customerTelesalesFlagLabel;
		//POS平台
		private String dictPosType;
		//蓝补金额
		private String paybackBuleAmount;
		//来源系统
		private String dictSourceType;
		//标识
		private String loanMark;
		//查账日期
		private Date auditAate;
		//还款方式
		private String dictRepayMethod;
		//标识
		private String pospwd;
		private String ids;
		private String sumNumber;
		private String sumAmont;
				
		public String getSumNumber() {
			return sumNumber;
		}
		public void setSumNumber(String sumNumber) {
			this.sumNumber = sumNumber;
		}
		public String getSumAmont() {
			return sumAmont;
		}
		public void setSumAmont(String sumAmont) {
			this.sumAmont = sumAmont;
		}
		public String getIds() {
					return ids;
				}
				public void setIds(String ids) {
					this.ids = ids;
				}
		public String getPospwd() {
					return pospwd;
				}
				public void setPospwd(String pospwd) {
					this.pospwd = pospwd;
				}
				
				
		public String getDictRepayMethod() {
					return dictRepayMethod;
				}
				public void setDictRepayMethod(String dictRepayMethod) {
					this.dictRepayMethod = dictRepayMethod;
				}
		public Date getAuditAate() {
			return auditAate;
		}
		public void setAuditAate(Date auditAate) {
			this.auditAate = auditAate;
		}
		public String getLoanMark() {
			return loanMark;
		}
		public void setLoanMark(String loanMark) {
			this.loanMark = loanMark;
		}
		public String getDictSourceType() {
			return dictSourceType;
		}
		public void setDictSourceType(String dictSourceType) {
			this.dictSourceType = dictSourceType;
		}
		public String getPosBillCode() {
			return posBillCode;
		}
		public void setPosBillCode(String posBillCode) {
			this.posBillCode = posBillCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public Date getContractEndDay() {
			return contractEndDay;
		}
		public void setContractEndDay(Date contractEndDay) {
			this.contractEndDay = contractEndDay;
		}

		public String getLoanTeamOrgId() {
			return loanTeamOrgId;
		}
		public void setLoanTeamOrgId(String loanTeamOrgId) {
			this.loanTeamOrgId = loanTeamOrgId;
		}
		public String getDictLoanStatus() {
			return dictLoanStatus;
		}
		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}
	
		public String getPaybackDay() {
			return paybackDay;
		}
		public void setPaybackDay(String paybackDay) {
			this.paybackDay = paybackDay;
		}
		public BigDecimal getApplyAmount() {
			return applyAmount;
		}
		public void setApplyAmount(BigDecimal applyAmount) {
			this.applyAmount = applyAmount;
		}
		public String getDictPayUse() {
			return dictPayUse;
		}
		public void setDictPayUse(String dictPayUse) {
			this.dictPayUse = dictPayUse;
		}
		public String getDictPayStatus() {
			return dictPayStatus;
		}
		public void setDictPayStatus(String dictPayStatus) {
			this.dictPayStatus = dictPayStatus;
		}
		public String getDictPayResult() {
			return dictPayResult;
		}
		public void setDictPayResult(String dictPayResult) {
			this.dictPayResult = dictPayResult;
		}
		public String getApplyBackMes() {
			return applyBackMes;
		}
		public void setApplyBackMes(String applyBackMes) {
			this.applyBackMes = applyBackMes;
		}
		public String getLoanFlag() {
			return loanFlag;
		}
		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}
		public String getCustomerTelesalesFlag() {
			return customerTelesalesFlag;
		}
		public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
			this.customerTelesalesFlag = customerTelesalesFlag;
		}
		public String getDictPosType() {
			return dictPosType;
		}
		public void setDictPosType(String dictPosType) {
			this.dictPosType = dictPosType;
		}
		public String getPaybackBuleAmount() {
			return paybackBuleAmount;
		}
		public void setPaybackBuleAmount(String paybackBuleAmount) {
			this.paybackBuleAmount = paybackBuleAmount;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getPosNewPassword() {
			return posNewPassword;
		}
		public void setPosNewPassword(String posNewPassword) {
			this.posNewPassword = posNewPassword;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPosOldPassword() {
			return posOldPassword;
		}
		public void setPosOldPassword(String posOldPassword) {
			this.posOldPassword = posOldPassword;
		}
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public String getReferCode() {
			return referCode;
		}
		public void setReferCode(String referCode) {
			this.referCode = referCode;
		}
		public String getPosOrderNumber() {
			return posOrderNumber;
		}
		public void setPosOrderNumber(String posOrderNumber) {
			this.posOrderNumber = posOrderNumber;
		}
		public Date getAuditDate() {
			return auditDate;
		}
		public void setAuditDate(Date auditDate) {
			this.auditDate = auditDate;
		}
		public Date getPaybackDate() {
			return paybackDate;
		}
		public void setPaybackDate(Date paybackDate) {
			this.paybackDate = paybackDate;
		}
		public String getDepositedAccount() {
			return depositedAccount;
		}
		public void setDepositedAccount(String depositedAccount) {
			this.depositedAccount = depositedAccount;
		}
		public String getMatchingState() {
			return matchingState;
		}
		public void setMatchingState(String matchingState) {
			this.matchingState = matchingState;
		}
		public BigDecimal getApplyReallyAmount() {
			return applyReallyAmount;
		}
		public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
			this.applyReallyAmount = applyReallyAmount;
		}
		public Date getPaybackBeginDate() {
			return paybackBeginDate;
		}
		public void setPaybackBeginDate(Date paybackBeginDate) {
			this.paybackBeginDate = paybackBeginDate;
		}
		public Date getPaybackEndDate() {
			return paybackEndDate;
		}
		public void setPaybackEndDate(Date paybackEndDate) {
			this.paybackEndDate = paybackEndDate;
		}
		public Date getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(Date beginDate) {
			this.beginDate = beginDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
        public String getPayBackApplyId() {
            return payBackApplyId;
        }
        public void setPayBackApplyId(String payBackApplyId) {
            this.payBackApplyId = payBackApplyId;
        }
		public String getDictLoanStatusLabel() {
			return dictLoanStatusLabel;
		}
		public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
			this.dictLoanStatusLabel = dictLoanStatusLabel;
		}
		public String getDictPayUseLabel() {
			return dictPayUseLabel;
		}
		public void setDictPayUseLabel(String dictPayUseLabel) {
			this.dictPayUseLabel = dictPayUseLabel;
		}
		public String getDictPayStatusLabel() {
			return dictPayStatusLabel;
		}
		public void setDictPayStatusLabel(String dictPayStatusLabel) {
			this.dictPayStatusLabel = dictPayStatusLabel;
		}
		public String getDictPayResultLabel() {
			return dictPayResultLabel;
		}
		public void setDictPayResultLabel(String dictPayResultLabel) {
			this.dictPayResultLabel = dictPayResultLabel;
		}
		public String getLoanFlagLabel() {
			return loanFlagLabel;
		}
		public void setLoanFlagLabel(String loanFlagLabel) {
			this.loanFlagLabel = loanFlagLabel;
		}
		public String getCustomerTelesalesFlagLabel() {
			return customerTelesalesFlagLabel;
		}
		public void setCustomerTelesalesFlagLabel(String customerTelesalesFlagLabel) {
			this.customerTelesalesFlagLabel = customerTelesalesFlagLabel;
		}
		public String getMatchingStateLabel() {
			return matchingStateLabel;
		}
		public void setMatchingStateLabel(String matchingStateLabel) {
			this.matchingStateLabel = matchingStateLabel;
		}
        
        
	

}
