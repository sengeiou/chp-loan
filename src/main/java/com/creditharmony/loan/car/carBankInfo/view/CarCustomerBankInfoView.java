package com.creditharmony.loan.car.carBankInfo.view;

import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.common.entity.CityInfo;

import java.util.Date;
import java.util.List;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 车借还款账号
 * @Class Name CarCustomerBankInfoView
 * @Create In 2016年1月22日
 */
public class CarCustomerBankInfoView extends CarCustomerBankInfo {

	private String ids;
	private String orgCode;
    private String contractCode;//合同编号 
	private String contractVersion;	//合同版本号
	private String customerId; // 借款人ID
	private String loanflag; // 渠道 
	private Date contractEndDay;	//合同到期日
	private Date firstRepayDay;		//首期还款日
	private String storeName;		//门店名称
	private String loanStatus;		//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private Integer repayDay;		//还款日
    private String customerName;//客户姓名
    private String customerEmail;//客户邮箱地址
    private String customerCertNum;//身份证号
    private String customerPhoneFirst;//手机号码
    private String customerPhoneFirstEnc;//手机号码
	private String queryRight;  // 数据权限
	private String maintainStatusArray; // 维护状态（数组格式）
    private String dictMaintainTypeName;//维护类型：0:新增、1:修改 
    private String dictMaintainStatusName;//维护状态：0.待审核、1.拒绝、2.已维护
    private String bankSigningPlatformName;//划扣平台中文名称
    private String provinceName;//开卡省中文名称
    private String cityName;//开卡市中文名称
	private String cardBankName; // 开卡行中文名称
	private String updateTypeName; // 修改类型中文名称
	private String coboId; // 是否有共借人
	private String coboFlag; // 是否有共借人
	private String coboName;		//共借人姓名
	private String coboCertNum;		//共借人身份证号
	private String coboMobile;		//共借人手机号
	private String coboMobileEnc;		//共借人手机号
	private String coboEmail;		//共借人邮箱
	
	private String oldCoboName;		//原共借人姓名
	private String oldCoboCertNum;		//原共借人身份证号
	private String oldCoboMobile;		//原共借人手机号
	private String oldCoboEmail;		//原共借人邮箱

	private String oldApplyBankName; // 原支行
	private String oldBankSigningPlatform; // 原签约平台
	private String oldBankCardNo; // 银行帐号
	private String oldPhone; //原手机号
	private String oldEmail; //原邮箱地址
	
	//添加历史
	private String operateStep;		//操作步骤
	private String oldData;			//原数据
	private String newData;			//新数据
	private String remark;
	private List<String> conditions;
	
	
	private List<CityInfo> cityList;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoanflag() {
		return loanflag;
	}

	public void setLoanflag(String loanflag) {
		this.loanflag = loanflag;
	}

	public Date getContractEndDay() {
		return contractEndDay;
	}

	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}

	public Date getFirstRepayDay() {
		return firstRepayDay;
	}

	public void setFirstRepayDay(Date firstRepayDay) {
		this.firstRepayDay = firstRepayDay;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public Integer getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}

	public List<CityInfo> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getCustomerPhoneFirst() {
        if(customerPhoneFirst != null){
        	customerPhoneFirst = decryptPhones(customerPhoneFirst,"T_JK_LOAN_CUSTOMER","customer_phone_first");
        }
		return customerPhoneFirst;
	}

	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		if(customerPhoneFirst != null && customerPhoneFirst.length() == 11){
			customerPhoneFirst = encryptPhones(customerPhoneFirst,"T_JK_LOAN_CUSTOMER","customer_phone_first");
		}
		this.customerPhoneFirst = customerPhoneFirst;
		this.customerPhoneFirstEnc = customerPhoneFirst;
	}

	
	public String getCustomerPhoneFirstEnc() {
		return customerPhoneFirstEnc;
	}

	public String getMaintainStatusArray() {
		return maintainStatusArray;
	}

	public void setMaintainStatusArray(String maintainStatusArray) {
		this.maintainStatusArray = maintainStatusArray;
	}

	public String getQueryRight() {
		return queryRight;
	}

	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrgCode() {
		if (ids != null){
			String[] strArray = ids.split(",");
			StringBuffer sb = new StringBuffer();
			for (String str : strArray){
				sb.append("'" + str + "',");
			}
			this.orgCode = sb.toString().substring(0, sb.toString().length() - 1);
		}
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDictMaintainTypeName() {
		return dictMaintainTypeName;
	}

	public void setDictMaintainTypeName(String dictMaintainTypeName) {
		this.dictMaintainTypeName = dictMaintainTypeName;
	}

	public String getDictMaintainStatusName() {
		return dictMaintainStatusName;
	}

	public void setDictMaintainStatusName(String dictMaintainStatusName) {
		this.dictMaintainStatusName = dictMaintainStatusName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCardBankName() {
		return cardBankName;
	}

	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getUpdateTypeName() {
		return updateTypeName;
	}

	public void setUpdateTypeName(String updateTypeName) {
		this.updateTypeName = updateTypeName;
	}

	public String getOperateStep() {
		return operateStep;
	}

	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getConditions() {
		return conditions;
	}

	public void setConditions(List<String> conditions) {
		this.conditions = conditions;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getCoboId() {
		return coboId;
	}

	public void setCoboId(String coboId) {
		this.coboId = coboId;
	}

	public String getCoboFlag() {
		return coboFlag;
	}

	public void setCoboFlag(String coboFlag) {
		this.coboFlag = coboFlag;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getCoboCertNum() {
		return coboCertNum;
	}

	public void setCoboCertNum(String coboCertNum) {
		this.coboCertNum = coboCertNum;
	}

	public String getCoboMobile() {
		if(coboMobile != null){
			coboMobile = decryptPhones(coboMobile,"t_cj_customer_bank_info_add","cobo_mobile");
		}
		return coboMobile;
	}

	public String getCoboMobileEnc() {
		return coboMobileEnc;
	}

	public void setCoboMobile(String coboMobile) {
		if(coboMobile != null && coboMobile.length() == 11){
			coboMobile = encryptPhones(coboMobile,"t_cj_customer_bank_info_add","cobo_mobile");
		}
		this.coboMobile = coboMobile;
		this.coboMobileEnc = coboMobile;
	}

	public String getCoboEmail() {
		return coboEmail;
	}

	public void setCoboEmail(String coboEmail) {
		this.coboEmail = coboEmail;
	}

	public String getOldApplyBankName() {
		return oldApplyBankName;
	}

	public void setOldApplyBankName(String oldApplyBankName) {
		this.oldApplyBankName = oldApplyBankName;
	}

	public String getOldBankSigningPlatform() {
		return oldBankSigningPlatform;
	}

	public void setOldBankSigningPlatform(String oldBankSigningPlatform) {
		this.oldBankSigningPlatform = oldBankSigningPlatform;
	}

	public String getOldBankCardNo() {
		return oldBankCardNo;
	}

	public void setOldBankCardNo(String oldBankCardNo) {
		this.oldBankCardNo = oldBankCardNo;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getBankSigningPlatformName() {
		return bankSigningPlatformName;
	}

	public void setBankSigningPlatformName(String bankSigningPlatformName) {
		this.bankSigningPlatformName = bankSigningPlatformName;
	}

	public String getOldCoboName() {
		return oldCoboName;
	}

	public void setOldCoboName(String oldCoboName) {
		this.oldCoboName = oldCoboName;
	}

	public String getOldCoboCertNum() {
		return oldCoboCertNum;
	}

	public void setOldCoboCertNum(String oldCoboCertNum) {
		this.oldCoboCertNum = oldCoboCertNum;
	}

	public String getOldCoboMobile() {
		return oldCoboMobile;
	}

	public void setOldCoboMobile(String oldCoboMobile) {
		this.oldCoboMobile = oldCoboMobile;
	}

	public String getOldCoboEmail() {
		return oldCoboEmail;
	}

	public void setOldCoboEmail(String oldCoboEmail) {
		this.oldCoboEmail = oldCoboEmail;
	}
}
