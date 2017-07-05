package com.creditharmony.loan.car.carExtend.view;

import java.util.List;

import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;


/**
 * 展期上传资料
 * @Class Name CarExtendUploadDataView
 * @author ganquan
 * @Create In 2016年3月8日
 */
public class CarExtendUploadDataView  extends CarExtendBaseBusinessView {
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//车牌号码
	private String plateNumbers;
	//借款状态
	private String applyStatusCode;
	//退回原因
	private String remark;
	
	private String customerSex;
	private String customerCertNum;
	
	private List<CarLoanCoborrower> carLoanCoborrowers;
	
	private String imageUrl;
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
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	
	
}
