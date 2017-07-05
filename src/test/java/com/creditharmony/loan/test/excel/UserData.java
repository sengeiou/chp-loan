/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.testUserData.java
 * @Create By 王彬彬
 * @Create In 2015年12月19日 下午4:24:26
 */
package com.creditharmony.loan.test.excel;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name UserData
 * @author 王彬彬
 * @Create In 2015年12月19日
 */
@SuppressWarnings("serial")
public class UserData extends DataEntity<UserData> {

	/**
	 * 注解说明 type（ 0：导出导入；1：仅导出；2：仅导入） align（0：自动；1：靠左；2：居中；3：靠右）
	 * sort（导出字段字段排序（升序）） dictType（如果是字典类型，请设置字典的type值）
	 */
	@ExcelField(title = "批次号", type = 0, align = 2, sort = 10)
	private String sersNum;

	@ExcelField(title = "上传时间", type = 0, align = 2, sort = 20)
	private String uploadTime;

	@ExcelField(title = "转账金额", type = 0, align = 2, sort = 30)
	private String chargeAmount;

	@ExcelField(title = "收款方银行", type = 0, align = 0, sort = 40)
	private String bankName;

	@ExcelField(title = "收款方户名", type = 0, align = 0, sort = 50)
	private String name;

	@ExcelField(title = "收款方帐号", type = 0, align = 0, sort = 60)
	private String bankCode;

	@ExcelField(title = "审核状态", type = 0, align = 0, sort = 70)
	private String checkStatus;

	@ExcelField(title = "审核人员", type = 0, align = 0, sort = 80)
	private String checkName;
	
	@ExcelField(title = "审核人三员", type = 0, align = 0, sort = 80)
	private String checksName;

	public String getSersNum() {
		return sersNum;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public String getName() {
		return name;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setSersNum(String sersNum) {
		this.sersNum = sersNum;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getChecksName() {
		return checksName;
	}

	public void setChecksName(String checksName) {
		this.checksName = checksName;
	}

}
