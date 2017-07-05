package com.creditharmony.loan.borrow.zhongjin.view;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.car.common.entity.CarGrossSpread;


/**
 * 中金划扣
 * @author WJJ
 *
 */
public class PaybackCpcn extends DataEntity<PaybackCpcn> {
	
	private static final long serialVersionUID = -5390327835208559463L;
	
	private String id;
	private String   cpcnId;
	private String serialNum;
	private String accountNum;
	private String accountName;
	private BigDecimal  dealMoney;
	private String bankName;
	private String bankNum;
	private String accountType;
	private String accountTypeName;
	private String accounProviceNum;
	private String accounProvice;
	private String accounCityNum;
	private String accounCity;
	private String certTypeName;
	private String certType;
	private String certNum;
	private String contractCode;
	private String note;
	private String appoint;
	private String   creatTime;
	private Date   createTime;
	private String   operateTime;
	private String   creatuserId;
	private String   status;
	private Date   backTime;
	private String backTimes;
	private String backResult;
	private String backReason;

	private String deductType;//预约划扣方式(1实时,2批量)
	
	private String nowDate;
	private Date   opearTime;
	private String batchSN;
	
	private BigDecimal  applyReallyAmount;
	public final static Map<String,String> certtypeMap = new HashMap<String,String>();
	static{
		certtypeMap.put("0", "身份证");
		certtypeMap.put("1", "户口薄");
		certtypeMap.put("2", "护照");
		certtypeMap.put("3", "军官证");
		certtypeMap.put("4", "士兵证");
		certtypeMap.put("5", "港澳居民来往内地通行证");
		certtypeMap.put("6", "台湾居民来往内地通行证");
		certtypeMap.put("7", "临时身份证");
		certtypeMap.put("8", "外国人居留证");
		certtypeMap.put("9", "警官证");
		certtypeMap.put("X", "其他证件");
		
		certtypeMap.put("A", "组织机构代码证");
		certtypeMap.put("B", "营业执照号码");
		certtypeMap.put("C", "登记证书");
		certtypeMap.put("D", "国税登记证号");
		certtypeMap.put("E", "地税登记证号");
		certtypeMap.put("F", "开户许可证");
		certtypeMap.put("G", "事业单位编号");
		certtypeMap.put("H", "其他证件");
		certtypeMap.put("I", "金融许可证编号");
		
	}
	public String getCpcnId() {
		return cpcnId;
	}
	public void setCpcnId(String cpcnId) {
		this.cpcnId = cpcnId;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
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
	public BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccounProvice() {
		return accounProvice;
	}
	public void setAccounProvice(String accounProvice) {
		this.accounProvice = accounProvice;
	}
	public String getAccounCity() {
		return accounCity;
	}
	public void setAccounCity(String accounCity) {
		this.accounCity = accounCity;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAppoint() {
		return appoint;
	}
	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getCreatuserId() {
		return creatuserId;
	}
	public void setCreatuserId(String creatuserId) {
		this.creatuserId = creatuserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public static Map<String, String> getCerttypemap() {
		return certtypeMap;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBackResult() {
		return backResult;
	}
	public void setBackResult(String backResult) {
		this.backResult = backResult;
	}
	public Date getOpearTime() {
		return opearTime;
	}
	public void setOpearTime(Date opearTime) {
		this.opearTime = opearTime;
	}
	public String getCertTypeName() {
		return certtypeMap.get(this.certType);
	}
	public String getAccountTypeName() {
		if("11".equals(this.accountType))
			return "个人账户";
		else if("12".equals(this.accountType))
			return "企业账户";
		else
			return "";
	}
	public String getAccounProviceNum() {
		return accounProviceNum;
	}
	public void setAccounProviceNum(String accounProviceNum) {
		this.accounProviceNum = accounProviceNum;
	}
	public String getAccounCityNum() {
		return accounCityNum;
	}
	public void setAccounCityNum(String accounCityNum) {
		this.accounCityNum = accounCityNum;
	}
	public String getBatchSN() {
		return batchSN;
	}
	public void setBatchSN(String batchSN) {
		this.batchSN = batchSN;
	}
	public String getBackTimes() {
		return backTimes;
	}
	public void setBackTimes(String backTimes) {
		this.backTimes = backTimes;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	
	
}
