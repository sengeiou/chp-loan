package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.loan.borrow.payback.entity.Decrypt;
/**
 * 集中划扣已拆分导出列表（富有）
 * @Class Name PaybackSplitFyEx
 * @author wengsi
 * @Create In 2015年12月26日
 */
@SuppressWarnings("serial")
public class PaybackSplitFyEx extends Decrypt<PaybackSplitFyEx>{
		// 开户行
		@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
		private String num;
		// 开户行
		@ExcelField(title = "开户行", type = 0, align = 2, sort = 20,dictType="jk_open_bank")
		private String bankName;
		//扣款人银行账号
		@ExcelField(title = "扣款人银行账号", type = 0, align = 2, sort = 30)
		private String  bankAccount;
		//户名
		@ExcelField(title = "户名", type = 0, align = 2, sort = 40)
		private String  bankAccountName;
		//金额(单位:元)
		@ExcelField(title = "金额(单位:元)", type = 0, align = 2, sort = 50)
		private BigDecimal splitAmount;
		//企业流水账号 暂时用id代替
		@ExcelField(title = "企业流水账号", type = 0, align = 2, sort = 60)
		private String enterpriseSerialno;
		//备注
		@ExcelField(title = "备注", type = 0, align = 2, sort = 70)
		private String remarks;
		//手机号
		@ExcelField(title = "手机号", type = 0, align = 2, sort = 80)
		private String customerPhoneFirst;
		//证件类型
		@ExcelField(title = "证件类型", type = 0, align = 2, sort = 90, dictType="com_certificate_type")
		private String dictertType;
		@ExcelField(title = "证件号", type = 0, align = 2, sort = 100)
		private String customerCertNum;
		//划扣平台
		private String dictDealType;
		//开户行所在省
		private String  bankProvince;
		//开户行所在市
		private String  bankCity;
		
		private String id;
		
		private String bankCode;
		
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getBankAccount() {
			return bankAccount;
		}
		public void setBankAccount(String bankAccount) {
			this.bankAccount = bankAccount;
		}
		public String getBankAccountName() {
			return bankAccountName;
		}
		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
		}
		public BigDecimal getSplitAmount() {
			return splitAmount;
		}
		public void setSplitAmount(BigDecimal splitAmount) {
			this.splitAmount = splitAmount;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getCustomerPhoneFirst() {
			return decrypt(customerPhoneFirst);
		}
		public void setCustomerPhoneFirst(String customerPhoneFirst) {
			this.customerPhoneFirst = customerPhoneFirst;
		}
		public String getDictertType() {
			return dictertType;
		}
		public void setDictertType(String dictertType) {
			this.dictertType = dictertType;
		}
		public String getCustomerCertNum() {
			return customerCertNum;
		}
		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}
		public String getDictDealType() {
			return dictDealType;
		}
		public void setDictDealType(String dictDealType) {
			this.dictDealType = dictDealType;
		}
		public String getBankProvince() {
			return bankProvince;
		}
		public void setBankProvince(String bankProvince) {
			this.bankProvince = bankProvince;
		}
		public String getBankCity() {
			return bankCity;
		}
		public void setBankCity(String bankCity) {
			this.bankCity = bankCity;
		}
		public String getEnterpriseSerialno() {
			return enterpriseSerialno;
		}
		public void setEnterpriseSerialno(String enterpriseSerialno) {
			this.enterpriseSerialno = enterpriseSerialno;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		
		
}
