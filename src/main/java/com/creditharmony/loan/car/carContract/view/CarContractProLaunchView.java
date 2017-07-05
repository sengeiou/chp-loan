package com.creditharmony.loan.car.carContract.view;

import java.util.ArrayList;
import java.util.List;

import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;

/**
 * 合同制作详情 加载数据
 * @Class Name CarContractProLaunchView
 * @author 李静辉
 * @Create In 2016年2月18日
 */
public class CarContractProLaunchView extends CarRateCheckLaunchView {

	// 银行账户信息
	private CarCustomerBankInfo carCustomerBankInfo;
	
	private CarCustomerBase carCustomerBase;
	
	// 合同费率
	private CarCheckRate carCheckRate;
	// 合同文件列表
    private List<CarContractFile> files = new ArrayList<CarContractFile>();
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	// 影像路径
	private String imageurl;

	public CarCustomerBankInfo getCarCustomerBankInfo() {
		return carCustomerBankInfo;
	}

	public void setCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		this.carCustomerBankInfo = carCustomerBankInfo;
	}

	public CarCheckRate getCarCheckRate() {
		return carCheckRate;
	}

	public void setCarCheckRate(CarCheckRate carCheckRate) {
		this.carCheckRate = carCheckRate;
	}

	public List<CarContractFile> getFiles() {
		return files;
	}

	public void setFiles(List<CarContractFile> files) {
		this.files = files;
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

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public CarCustomerBase getCarCustomerBase() {
		return carCustomerBase;
	}

	public void setCarCustomerBase(CarCustomerBase carCustomerBase) {
		this.carCustomerBase = carCustomerBase;
	}
	
}
