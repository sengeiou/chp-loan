package com.creditharmony.loan.borrow.delivery.entity.ex;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 显示的字段 type的值为0，导入导出都可以，type为2，仅导入
* @ClassName: DeliveryViewExNew
* @Description: TODO(交割表实体)
* @author meiqingzhang
* @date 2017年3月27日
 */
@SuppressWarnings("serial")
public class DeliveryViewExNew extends DataEntity<DeliveryViewExNew> {
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1)
	private Integer index;
	private String id;						// 交割表ID
	private String loanCode;				// 借款编码
	@ExcelField(title = "客户姓名(必填)", type = 0, align = 2, sort = 2)
	private String loanCustomerName;		// 客户姓名
	@ExcelField(title = "合同编号(必填)", type = 0, align = 2, sort = 3)
	private String contractCode;			// 合同编号
	private String orgName;					// 机构名
	@ExcelField(title = "交割前团队经理(交割团队经理会同时交割所属团队)", type = 0, align = 2, sort = 5)
	private String teamManagerName;			// 团队经理姓名
	@ExcelField(title = "交割前客户经理", type = 0, align = 2, sort = 7)
	private String managerName;				// 客户经理姓名
	@ExcelField(title = "交割前客服人员", type = 0, align = 2, sort = 9)
	private String customerServicesName;	// 客服人员姓名
	private String outbountByName;			// 外访人员姓名
	private String orgCode;					// 机构编号
	@ExcelField(title = "交割前团队经理员工号(交割团队经理会同时交割所属团队)", type = 0, align = 2, sort = 6)
	private String teamManagerCode;			// 团队经理编号
	@ExcelField(title = "交割前客户经理员工号", type = 0, align = 2, sort = 8)
	private String managerCode;				// 客户经理编号
	@ExcelField(title = "交割前客服人员员工号", type = 0, align = 2, sort = 10)
	private String customerServicesCode;	// 客服人员编号
	private String outbountByCode;			// 外访人员编号
	private String dictLoanStatus;			// 借款状态编码
	private String dictLoanStatusLabel;		// 借款状态名称
	private String newOrgName;				// 新门店名
	private String newStoresId;             // 新门店Id
	@ExcelField(title = "交割后团队经理(交割团队经理会同时交割所属团队)", type = 0, align = 2, sort = 12)
	private String newTeamManagerName;		// 新团队经理
	@ExcelField(title = "交割后团队经理员工号(交割团队经理会同时交割所属团队)", type = 0, align = 2, sort = 13)
	private String newTeamManagerCode;        // 新团队经理ID
	@ExcelField(title = "交割后客户经理", type = 0, align = 2, sort = 14)
	private String newManagerName;			// 新客户经理
	@ExcelField(title = "交割后客户经理员工号", type = 0, align = 2, sort = 15)
	private String newManagerCode;            // 新客户经理Id
	@ExcelField(title = "交割后客服人员", type = 0, align = 2, sort = 16)
	private String newCustomerServicesName;	// 新客服人员
	@ExcelField(title = "交割后客服人员员工号", type = 0, align = 2, sort = 17)
	private String newCustomerServicesCode;    // 新客服Id
	private String newOutbountByName;		// 新外访人员
	private String newOutboundBy;         // 新外访人员Id
	private String deliveryReason;			// 交割原因
	@ExcelField(title = "交割状态", type = 0, align = 2, sort = 18)
	private String deliveryResult;          // 交割结果(状态)
	private String deliveryResultLabel;     // 交个结果名
	@ExcelField(title = "失败原因", type = 0, align = 2, sort = 19)
	private String rejectedReason;          // 驳回原因
	private Date createTime;                // 创建时间
	private String createBy;                // 创建人
	private String modifyBy;                // 修改人
	private Date modifyTime;                // 修改时间
	//新加
	private String teamName;                //团队名称
	@ExcelField(title = "交割前所属门店( 门店名称)", type = 0, align = 2, sort = 4)
	private String storesName;				//门店名
	@ExcelField(title = "交割后所属门店( 门店名称)", type = 0, align = 2, sort = 11)
	private String newStoresName;           //新门店名
	private String loanStoreOrgId;          //门店id
	private String loanTeamOrgId;           //团队机构
	private Date deliveryTime;              //交割时间
	
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getLoanTeamOrgId() {
		return loanTeamOrgId;
	}
	public void setLoanTeamOrgId(String loanTeamOrgId) {
		this.loanTeamOrgId = loanTeamOrgId;
	}
	public String getLoanStoreOrgId() {
		return loanStoreOrgId;
	}
	public void setLoanStoreOrgId(String loanStoreOrgId) {
		this.loanStoreOrgId = loanStoreOrgId;
	}
	public String getNewStoresName() {
		return newStoresName;
	}
	public void setNewStoresName(String newStoresName) {
		this.newStoresName = newStoresName;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
     * @return the newStoresId
     */
    public String getNewStoresId() {
        return newStoresId;
    }
    /**
     * @param newStoresId the String newStoresId to set
     */
    public void setNewStoresId(String newStoresId) {
        this.newStoresId = newStoresId;
    }
    
    /**
     * @return the newManagercode
     */
    public String getNewManagerCode() {
        return newManagerCode;
    }
    /**
     * @param newManagercode the String newManagercode to set
     */
    public void setNewManagerCode(String newManagerCode) {
        this.newManagerCode = newManagerCode;
    }
    
    public String getNewTeamManagerCode() {
		return newTeamManagerCode;
	}
	public void setNewTeamManagerCode(String newTeamManagerCode) {
		this.newTeamManagerCode = newTeamManagerCode;
	}
	public String getNewCustomerServicesCode() {
		return newCustomerServicesCode;
	}
	public void setNewCustomerServicesCode(String newCustomerServicesCode) {
		this.newCustomerServicesCode = newCustomerServicesCode;
	}
	/**
     * @return the newOutboundBy
     */
    public String getNewOutboundBy() {
        return newOutboundBy;
    }
    /**
     * @param newOutboundBy the String newOutboundBy to set
     */
    public void setNewOutboundBy(String newOutboundBy) {
        this.newOutboundBy = newOutboundBy;
    }
	public String getDeliveryResultLabel() {
		return deliveryResultLabel;
	}
	public void setDeliveryResultLabel(String deliveryResultLabel) {
		this.deliveryResultLabel = deliveryResultLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	public String getNewOrgName() {
		return newOrgName;
	}
	public void setNewOrgName(String newOrgName) {
		this.newOrgName = newOrgName;
	}
	public String getNewTeamManagerName() {
		return newTeamManagerName;
	}
	public void setNewTeamManagerName(String newTeamManagerName) {
		this.newTeamManagerName = newTeamManagerName;
	}
	public String getNewManagerName() {
		return newManagerName;
	}
	public void setNewManagerName(String newManagerName) {
		this.newManagerName = newManagerName;
	}
	public String getNewCustomerServicesName() {
		return newCustomerServicesName;
	}
	public void setNewCustomerServicesName(String newCustomerServicesName) {
		this.newCustomerServicesName = newCustomerServicesName;
	}
	public String getNewOutbountByName() {
		return newOutbountByName;
	}
	public void setNewOutbountByName(String newOutbountByName) {
		this.newOutbountByName = newOutbountByName;
	}
	public String getDeliveryReason() {
		return deliveryReason;
	}
	public void setDeliveryReason(String deliveryReason) {
		this.deliveryReason = deliveryReason;
	}
	public String getDeliveryResult() {
		return deliveryResult;
	}
	public void setDeliveryResult(String deliveryResult) {
		this.deliveryResult = deliveryResult;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTeamManagerName() {
		return teamManagerName;
	}
	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getCustomerServicesName() {
		return customerServicesName;
	}
	public void setCustomerServicesName(String customerServicesName) {
		this.customerServicesName = customerServicesName;
	}
	public String getOutbountByName() {
		return outbountByName;
	}
	public void setOutbountByName(String outbountByName) {
		this.outbountByName = outbountByName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getTeamManagerCode() {
		return teamManagerCode;
	}
	public void setTeamManagerCode(String teamManagerCode) {
		this.teamManagerCode = teamManagerCode;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getCustomerServicesCode() {
		return customerServicesCode;
	}
	public void setCustomerServicesCode(String customerServicesCode) {
		this.customerServicesCode = customerServicesCode;
	}
	public String getOutbountByCode() {
		return outbountByCode;
	}
	public void setOutbountByCode(String outbountByCode) {
		this.outbountByCode = outbountByCode;
	}
	
}
