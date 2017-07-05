package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表富友平台导出
 * @Class Name UrgeServiceFyEx
 * @author 朱静越
 * @Create In 2016年1月6日
 */
@SuppressWarnings("serial")
public class UrgeServiceFyEx extends DataEntity<UrgeServiceFyEx> {
	// 拆分表id
	private String id;
	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1)
	private String index;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 10)
	private String bankName;
	
	// 扣款人银行账号，
	@ExcelField(title = "扣款人银行账号", type = 0, align = 2, sort = 20)
	private String bankAccount;
	
	// 户名
	@ExcelField(title = "扣款人银行账号", type = 0, align = 2, sort = 30)
	private String bankAccountName;
	
	// 金额(单位:元)
	@ExcelField(title = "金额(单位:元)", type = 0, align = 2, sort = 40)
	private String splitAmount;
	
	// 企业流水账号 
	@ExcelField(title = "企业流水账号", type = 0, align = 2, sort = 50)
	private String enterpriseSerialno;
	
	// 备注为合同编号_催收服务费
	@ExcelField(title = "备注", type = 0, align = 2, sort = 60)
	private String remarks;
	
	// 手机号
	@ExcelField(title = "手机号", type = 0, align = 2, sort = 70)
	private String customerPhoneFirst;
	
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 80)
	private String dictertType;
	
	// 证件号
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 90)
	private String customerCertNum;
	
	private String dictDealType;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
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
	
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public String getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(String splitAmount) {
		this.splitAmount = splitAmount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
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
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}
}