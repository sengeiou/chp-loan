package com.creditharmony.loan.car.carApply.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.common.entity.CityInfo;

/**
 * 面审申请业务view
 * @Class Name ReviewMeetApplyBusinessView
 * @author 陈伟东
 * @Create In 2016年2月3日
 */
public class ReviewMeetApplyBusinessView extends BaseBusinessView {
		
		//id
		private String id;
		//流程id
		private String applyId;
		//借款编码
		private String loanCode;
		//客户编码
		private String customerCode;
		//客户姓名
		private String customerName;
		//性别
		private String customerSex;                
		//证件类型
		private String dictCertType;
		//证件号码
		private String customerCertNum;
		//证件开始日期
		private Date idStartDay;
		//证件结束日期
		private Date idEndDay;
		//是否长期
		private String isLongTerm;                
		//手机号码
		private String customerMobilePhone;
		//出生日期
		private Date customerBirthday;               
		//暂住证
		private String customerTempPermit;          
		//住房性质
		private String customerHouseProperty;     	 
		//户籍省
		private String customerRegisterProvince;     
		//户籍市
		private String customerRegisterCity;         
		//户籍区
		private String customerRegisterArea;         
		//户籍地址
		private String customerRegisterAddress;      
		//居住省
		private String customerLiveProvince;     			 
		//居住市
		private String customerLiveCity;     				 
		//居住区
		private String customerLiveArea;    				 
		//详细地址
		private String customerAddress;    		     
		//本市电话
		private String cityPhone;                   
		//信用额度
		private Double creditLine;                     
		//起始居住时间
		private Date customerFirstLivingDay;        
		//初到本市年份
		private Date customerFirtArriveYear;      
		//供养亲属人数
		private Integer customerFamilySupport;          
		//婚姻状况
		private String dictMarryStatus;
		//婚姻状况编码
		private String dictMarryStatusCode;
		//学历编码
		private String dictEducationCode;
		//学历
		private String dictEducation;
		//电子邮箱
		private String customerEmail;     			 
		//有无子女
		private String customerHaveChildren;
		//月租金
		private Double jydzzmRentMonth;
		// 初始化省份显示
	    private List<CityInfo> provinceList = new ArrayList<CityInfo>(); 
		
	    //银行卡/折 户名
	    private String bankAccountName;
	    //开卡省
	    private String bankProvince;
	    //开卡市
	    private String bankCity;
	    //开户行
	    private String cardBank;
	    //开户支行
	    private String applyBankName;
	    //银行卡号
	    private String bankCardNo;
	    //客户来源
	    private String dictCustomerSource2;
	    //客户人法查询结果
	    private String queryResult;
	    //借款状态
	    private String dictLoanStatus;
	    //下一步咨询状态
	    private String dictOperStatus;
	    private String israre;
	   
	    //产品类型编码
	    private String borrowProductCode;
	    //产品类型名字
	    private String borrowProductName;
	    //初审姓名
	    private String firstCheckName;
	    //借款期限
	    private int loanMonths;
	    //共借人姓名
	    private String coborrowerName;
	    //管辖省分
	    private String addrProvince;
	    //所属城市
	    private String addrCity;
	    //邮箱是否修改
	    private String isEmailModify;
	    private String customerHouseHoldProperty;
	    //是否结清再借
	    private String cycleBorrowingFlag;
	    //手机是否修改
	    private String isTelephoneModify;
	    //是否展期
	    private String extensionFlag;
		//排序字段
		private String orderField;
		//第一次退回的源节点名称--退回标红置顶业务所需
		private String firstBackSourceStep;
		//影像地址
		private String imageUrl;
	    
	    public String getExtensionFlag() {
			return extensionFlag;
		}
		public void setExtensionFlag(String extensionFlag) {
			this.extensionFlag = extensionFlag;
		}
		public String getCycleBorrowingFlag() {
			return cycleBorrowingFlag;
		}
		public void setCycleBorrowingFlag(String cycleBorrowingFlag) {
			this.cycleBorrowingFlag = cycleBorrowingFlag;
		}
		public String getCustomerHouseHoldProperty() {
			return customerHouseHoldProperty;
		}
		public void setCustomerHouseHoldProperty(String customerHouseHoldProperty) {
			this.customerHouseHoldProperty = customerHouseHoldProperty;
		}
		public String getDictOperStatus() {
			return dictOperStatus;
		}
		public void setDictOperStatus(String dictOperStatus) {
			this.dictOperStatus = dictOperStatus;
		}
		public String getDictLoanStatus() {
			return dictLoanStatus;
		}
		public void setDictLoanStatus(String dictLoanStatus) {
			this.dictLoanStatus = dictLoanStatus;
		}
		public String getBankAccountName() {
			return bankAccountName;
		}
		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
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
		public String getCardBank() {
			return cardBank;
		}
		public void setCardBank(String cardBank) {
			this.cardBank = cardBank;
		}
		public String getApplyBankName() {
			return applyBankName;
		}
		public void setApplyBankName(String applyBankName) {
			this.applyBankName = applyBankName;
		}
		public String getBankCardNo() {
			return bankCardNo;
		}
		public void setBankCardNo(String bankCardNo) {
			this.bankCardNo = bankCardNo;
		}
		public String getDictCustomerSource2() {
			return dictCustomerSource2;
		}
		public void setDictCustomerSource2(String dictCustomerSource2) {
			this.dictCustomerSource2 = dictCustomerSource2;
		}
		public String getQueryResult() {
			return queryResult;
		}
		public String getDictMarryStatusCode() {
			return dictMarryStatusCode;
		}
		public void setDictMarryStatusCode(String dictMarryStatusCode) {
			this.dictMarryStatusCode = dictMarryStatusCode;
		}
		public String getDictEducationCode() {
			return dictEducationCode;
		}
		public void setDictEducationCode(String dictEducationCode) {
			this.dictEducationCode = dictEducationCode;
		}
		public void setQueryResult(String queryResult) {
			this.queryResult = queryResult;
		}
		public List<CityInfo> getProvinceList() {
			return provinceList;
		}
		public void setProvinceList(List<CityInfo> provinceList) {
			this.provinceList = provinceList;
		}
		public Double getJydzzmRentMonth() {
			return jydzzmRentMonth;
		}
		public void setJydzzmRentMonth(Double jydzzmRentMonth) {
			this.jydzzmRentMonth = jydzzmRentMonth;
		}
		public String getApplyId() {
			return applyId;
		}
		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}
		public String getLoanCode() {
			return loanCode;
		}
		public void setLoanCode(String loanCode) {
			this.loanCode = loanCode;
		}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCustomerSex() {
			return customerSex;
		}
		public void setCustomerSex(String customerSex) {
			this.customerSex = customerSex;
		}
		public String getDictCertType() {
			return dictCertType;
		}
		public void setDictCertType(String dictCertType) {
			this.dictCertType = dictCertType;
		}
		public String getCustomerCertNum() {
			return customerCertNum;
		}
		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}
		public Date getIdStartDay() {
			return idStartDay;
		}
		public void setIdStartDay(Date idStartDay) {
			this.idStartDay = idStartDay;
		}
		public Date getIdEndDay() {
			return idEndDay;
		}
		public void setIdEndDay(Date idEndDay) {
			this.idEndDay = idEndDay;
		}
		public String getIsLongTerm() {
			return isLongTerm;
		}
		public void setIsLongTerm(String isLongTerm) {
			this.isLongTerm = isLongTerm;
		}
		public String getCustomerMobilePhone() {
			return customerMobilePhone;
		}
		public void setCustomerMobilePhone(String customerMobilePhone) {
			this.customerMobilePhone = customerMobilePhone;
		}
		public Date getCustomerBirthday() {
			return customerBirthday;
		}
		public void setCustomerBirthday(Date customerBirthday) {
			this.customerBirthday = customerBirthday;
		}
		public String getCustomerTempPermit() {
			return customerTempPermit;
		}
		public void setCustomerTempPermit(String customerTempPermit) {
			this.customerTempPermit = customerTempPermit;
		}
		public String getCustomerHouseProperty() {
			return customerHouseProperty;
		}
		public void setCustomerHouseProperty(String customerHouseProperty) {
			this.customerHouseProperty = customerHouseProperty;
		}
		public String getCustomerRegisterProvince() {
			return customerRegisterProvince;
		}
		public void setCustomerRegisterProvince(String customerRegisterProvince) {
			this.customerRegisterProvince = customerRegisterProvince;
		}
		public String getCustomerRegisterCity() {
			return customerRegisterCity;
		}
		public void setCustomerRegisterCity(String customerRegisterCity) {
			this.customerRegisterCity = customerRegisterCity;
		}
		public String getCustomerRegisterArea() {
			return customerRegisterArea;
		}
		public void setCustomerRegisterArea(String customerRegisterArea) {
			this.customerRegisterArea = customerRegisterArea;
		}
		public String getCustomerRegisterAddress() {
			return customerRegisterAddress;
		}
		public void setCustomerRegisterAddress(String customerRegisterAddress) {
			this.customerRegisterAddress = customerRegisterAddress;
		}
		public String getCustomerLiveProvince() {
			return customerLiveProvince;
		}
		public void setCustomerLiveProvince(String customerLiveProvince) {
			this.customerLiveProvince = customerLiveProvince;
		}
		public String getCustomerLiveCity() {
			return customerLiveCity;
		}
		public void setCustomerLiveCity(String customerLiveCity) {
			this.customerLiveCity = customerLiveCity;
		}
		public String getCustomerLiveArea() {
			return customerLiveArea;
		}
		public void setCustomerLiveArea(String customerLiveArea) {
			this.customerLiveArea = customerLiveArea;
		}
		public String getCustomerAddress() {
			return customerAddress;
		}
		public void setCustomerAddress(String customerAddress) {
			this.customerAddress = customerAddress;
		}
		public String getCityPhone() {
			return cityPhone;
		}
		public void setCityPhone(String cityPhone) {
			this.cityPhone = cityPhone;
		}
		public Double getCreditLine() {
			return creditLine;
		}
		public void setCreditLine(Double creditLine) {
			this.creditLine = creditLine;
		}
		public Date getCustomerFirstLivingDay() {
			return customerFirstLivingDay;
		}
		public void setCustomerFirstLivingDay(Date customerFirstLivingDay) {
			this.customerFirstLivingDay = customerFirstLivingDay;
		}
		public Date getCustomerFirtArriveYear() {
			return customerFirtArriveYear;
		}
		public void setCustomerFirtArriveYear(Date customerFirtArriveYear) {
			this.customerFirtArriveYear = customerFirtArriveYear;
		}
		public Integer getCustomerFamilySupport() {
			return customerFamilySupport;
		}
		public void setCustomerFamilySupport(Integer customerFamilySupport) {
			this.customerFamilySupport = customerFamilySupport;
		}
		public String getDictMarryStatus() {
			return dictMarryStatus;
		}
		public void setDictMarryStatus(String dictMarryStatus) {
			this.dictMarryStatus = dictMarryStatus;
		}
		public String getDictEducation() {
			return dictEducation;
		}
		public void setDictEducation(String dictEducation) {
			this.dictEducation = dictEducation;
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public String getCustomerHaveChildren() {
			return customerHaveChildren;
		}
		public void setCustomerHaveChildren(String customerHaveChildren) {
			this.customerHaveChildren = customerHaveChildren;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getBorrowProductCode() {
			return borrowProductCode;
		}
		public void setBorrowProductCode(String borrowProductCode) {
			this.borrowProductCode = borrowProductCode;
		}
		public String getBorrowProductName() {
			return borrowProductName;
		}
		public void setBorrowProductName(String borrowProductName) {
			this.borrowProductName = borrowProductName;
		}
		public String getFirstCheckName() {
			return firstCheckName;
		}
		public void setFirstCheckName(String firstCheckName) {
			this.firstCheckName = firstCheckName;
		}
		public int getLoanMonths() {
			return loanMonths;
		}
		public void setLoanMonths(int loanMonths) {
			this.loanMonths = loanMonths;
		}
	
		public String getCoborrowerName() {
			return coborrowerName;
		}
		public void setCoborrowerName(String coborrowerName) {
			this.coborrowerName = coborrowerName;
		}
		public String getAddrProvince() {
			return addrProvince;
		}
		public void setAddrProvince(String addrProvince) {
			this.addrProvince = addrProvince;
		}
		public String getAddrCity() {
			return addrCity;
		}
		public void setAddrCity(String addrCity) {
			this.addrCity = addrCity;
		}
		public String getOrderField() {
			return orderField;
		}
		public void setOrderField(String orderField) {
			this.orderField = orderField;
		}
		public String getFirstBackSourceStep() {
			return firstBackSourceStep;
		}
		public void setFirstBackSourceStep(String firstBackSourceStep) {
			this.firstBackSourceStep = firstBackSourceStep;
		}
		public String getIsrare() {
			return israre;
		}
		public void setIsrare(String israre) {
			this.israre = israre;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public String getIsTelephoneModify() {
			return isTelephoneModify;
		}
		public void setIsTelephoneModify(String isTelephoneModify) {
			this.isTelephoneModify = isTelephoneModify;
		}
		public String getIsEmailModify() {
			return isEmailModify;
		}
		public void setIsEmailModify(String isEmailModify) {
			this.isEmailModify = isEmailModify;
		}     	 
		
}
