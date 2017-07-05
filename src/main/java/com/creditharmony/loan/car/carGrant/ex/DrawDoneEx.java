package com.creditharmony.loan.car.carGrant.ex;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 客户信息
 * 
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年3月2日
 */
public class DrawDoneEx implements Serializable {
	// 流程id
	private String applyId;
	// 划扣日期开始
	private Date urgeDecuteDateStart;
	// 划扣日期结束
	private Date urgeDecuteDateEnd;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String loanCustomerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String storeName;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 40)
	private String midBankName;
	// 银行卡号
	@ExcelField(title = "银行卡号", type = 0, align = 2, sort = 50)
	private String bankCardNo;
	// 借款产品
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 60)
	private String finalProductType;
	// 应划扣金额
	@ExcelField(title = "应划扣金额", type = 0, align = 2, sort = 70)
	private Double urgeMoeny;
	// 利息  金额
	@ExcelField(title = "利息", type = 0, align = 2, sort = 71)
	private Double interestMoeny;
	// 服务费
	@ExcelField(title = "服务费", type = 0, align = 2, sort = 72)
	private Double serviceMoeny;
	// 长期产品服务费
	@ExcelField(title = "长期产品服务费", type = 0, align = 2, sort = 73)
	private Double longProductMoeny;
	// 渠道费
	@ExcelField(title = "渠道费", type = 0, align = 2, sort = 74)
	private Double channelMoeny;
	// 外放费
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 75)
	private Double outerVisitMoeny;
	// 停车费
	@ExcelField(title = "停车费", type = 0, align = 2, sort = 76)
	private Double parkMoeny;
	// GPS设备费安装费
	@ExcelField(title = "GPS设备费安装费", type = 0, align = 2, sort = 77)
	private Double GPSInstallMoeny;
	// GPS使用费
	@ExcelField(title = "GPS使用费", type = 0, align = 2, sort = 78)
	private Double GPSUseMoeny;
	// 平台流量费
	@ExcelField(title = "平台流量费", type = 0, align = 2, sort = 79)
	private Double platformFlowMoeny;
	// 应划扣金额
	@ExcelField(title = "应划扣首期服务费率", type = 0, align = 2, sort = 80)
	private String firstServiceRate;

	public String getFirstServiceRate() {
		return firstServiceRate;
	}

	public void setFirstServiceRate(String firstServiceRate) {
		this.firstServiceRate = firstServiceRate;
	}

	// 划扣日期
	@ExcelField(title = "划扣日期", type = 0, align = 2, sort = 90)
	private Date urgeDecuteDate;
	// 回盘结果
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 100)
	private String dictDealStatus;
	// 回盘原因
	@ExcelField(title = "回盘原因", type = 0, align = 2, sort = 110)
	private String splitFailResult;
	// 划扣平台
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 120)
	private String dictDealType;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 130)
	private String loanFlag;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Date getUrgeDecuteDateStart() {
		return urgeDecuteDateStart;
	}

	public void setUrgeDecuteDateStart(Date urgeDecuteDateStart) {
		this.urgeDecuteDateStart = urgeDecuteDateStart;
	}

	public Date getUrgeDecuteDateEnd() {
		return urgeDecuteDateEnd;
	}

	public void setUrgeDecuteDateEnd(Date urgeDecuteDateEnd) {
		this.urgeDecuteDateEnd = urgeDecuteDateEnd;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getFinalProductType() {
		return finalProductType;
	}

	public void setFinalProductType(String finalProductType) {
		this.finalProductType = finalProductType;
	}

	public Double getUrgeMoeny() {
		return urgeMoeny;
	}

	public void setUrgeMoeny(Double urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}

	public Date getUrgeDecuteDate() {
		return urgeDecuteDate;
	}

	public void setUrgeDecuteDate(Date urgeDecuteDate) {
		this.urgeDecuteDate = urgeDecuteDate;
	}

	public String getDictDealStatus() {
		return dictDealStatus;
	}

	public void setDictDealStatus(String dictDealStatus) {
		this.dictDealStatus = dictDealStatus;
	}

	public String getSplitFailResult() {
		return splitFailResult;
	}

	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Double getInterestMoeny() {
		return interestMoeny;
	}

	public void setInterestMoeny(Double interestMoeny) {
		this.interestMoeny = interestMoeny;
	}

	public Double getServiceMoeny() {
		return serviceMoeny;
	}

	public void setServiceMoeny(Double serviceMoeny) {
		this.serviceMoeny = serviceMoeny;
	}

	public Double getLongProductMoeny() {
		return longProductMoeny;
	}

	public void setLongProductMoeny(Double longProductMoeny) {
		this.longProductMoeny = longProductMoeny;
	}

	public Double getOuterVisitMoeny() {
		return outerVisitMoeny;
	}

	public void setOuterVisitMoeny(Double outerVisitMoeny) {
		this.outerVisitMoeny = outerVisitMoeny;
	}

	public Double getParkMoeny() {
		return parkMoeny;
	}

	public void setParkMoeny(Double parkMoeny) {
		this.parkMoeny = parkMoeny;
	}

	public Double getGPSInstallMoeny() {
		return GPSInstallMoeny;
	}

	public void setGPSInstallMoeny(Double gPSInstallMoeny) {
		GPSInstallMoeny = gPSInstallMoeny;
	}

	public Double getGPSUseMoeny() {
		return GPSUseMoeny;
	}

	public void setGPSUseMoeny(Double gPSUseMoeny) {
		GPSUseMoeny = gPSUseMoeny;
	}

	public Double getPlatformFlowMoeny() {
		return platformFlowMoeny;
	}

	public void setPlatformFlowMoeny(Double platformFlowMoeny) {
		this.platformFlowMoeny = platformFlowMoeny;
	}

	public Double getChannelMoeny() {
		return channelMoeny;
	}

	public void setChannelMoeny(Double channelMoeny) {
		this.channelMoeny = channelMoeny;
	}
}
