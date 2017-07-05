package com.creditharmony.loan.borrow.outvisit.enity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 外访_外访任务详情
 * @Class Name LoanOutsideTaskInfo
 * @author 张进
 * @Create In 2015年12月26日
 */
public class LoanOutsideTaskInfo extends DataEntity<LoanOutsideTaskInfo> {
    
	private static final long serialVersionUID = 1L;

	private String id;

    private String taskId;

    private String dictCustomerType;

    private String checkJson;

    private String liveProvince;

    private String liveCity;

    private String liveArea;

    private String liveAddress;

    private String houseProvince;

    private String houseCity;

    private String houseArea;

    private String houseAddress;

    private String workInitProvince;
    
    private String workUnitProvince;

    private String workUnitCity;

    private String workUnitArea;

    private String workUnitAddress;

    private String operationProvince;

    private String operationCity;

    private String operationArea;

    private String businessAddress;
    
    private String operationAddress;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String customerName;

    private String telephone;

    private String remark;
    
    private String visitWorkRemark;
    
    private String visitType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getDictCustomerType() {
        return dictCustomerType;
    }

    public void setDictCustomerType(String dictCustomerType) {
        this.dictCustomerType = dictCustomerType == null ? null : dictCustomerType.trim();
    }

    public String getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(String checkJson) {
        this.checkJson = checkJson == null ? null : checkJson.trim();
    }

    public String getLiveProvince() {
        return liveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        this.liveProvince = liveProvince == null ? null : liveProvince.trim();
    }

    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity == null ? null : liveCity.trim();
    }

    public String getLiveArea() {
        return liveArea;
    }

    public void setLiveArea(String liveArea) {
        this.liveArea = liveArea == null ? null : liveArea.trim();
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress == null ? null : liveAddress.trim();
    }

    public String getHouseProvince() {
        return houseProvince;
    }

    public void setHouseProvince(String houseProvince) {
        this.houseProvince = houseProvince == null ? null : houseProvince.trim();
    }

    public String getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(String houseCity) {
        this.houseCity = houseCity == null ? null : houseCity.trim();
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea == null ? null : houseArea.trim();
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    public String getWorkInitProvince() {
        return workInitProvince;
    }

    public void setWorkInitProvince(String workInitProvince) {
        this.workInitProvince = workInitProvince == null ? null : workInitProvince.trim();
    }
    
    public String getWorkUnitProvince() {
		return workUnitProvince;
	}

	public void setWorkUnitProvince(String workUnitProvince) {
		this.workUnitProvince = workUnitProvince;
	}

    public String getWorkUnitCity() {
        return workUnitCity;
    }

    public void setWorkUnitCity(String workUnitCity) {
        this.workUnitCity = workUnitCity == null ? null : workUnitCity.trim();
    }

    public String getWorkUnitArea() {
        return workUnitArea;
    }

    public void setWorkUnitArea(String workUnitArea) {
        this.workUnitArea = workUnitArea == null ? null : workUnitArea.trim();
    }

    public String getWorkUnitAddress() {
        return workUnitAddress;
    }

    public void setWorkUnitAddress(String workUnitAddress) {
        this.workUnitAddress = workUnitAddress == null ? null : workUnitAddress.trim();
    }

    public String getOperationProvince() {
        return operationProvince;
    }

    public void setOperationProvince(String operationProvince) {
        this.operationProvince = operationProvince == null ? null : operationProvince.trim();
    }

    public String getOperationCity() {
        return operationCity;
    }

    public void setOperationCity(String operationCity) {
        this.operationCity = operationCity == null ? null : operationCity.trim();
    }

    public String getOperationArea() {
        return operationArea;
    }

    public void setOperationArea(String operationArea) {
        this.operationArea = operationArea == null ? null : operationArea.trim();
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }
    
    public String getOperationAddress() {
		return operationAddress;
	}
    
	public void setOperationAddress(String operationAddress) {
		this.operationAddress = operationAddress;
	}

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getVisitWorkRemark() {
		return visitWorkRemark;
	}

	public void setVisitWorkRemark(String visitWorkRemark) {
		this.visitWorkRemark = visitWorkRemark;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
}