package com.creditharmony.loan.borrow.delivery.entity.ex;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 对应交割列表的实体类
 * @Class Name DeliveryViewEntity
 * @author lirui
 * @Create In 2015年12月3日
 */
@SuppressWarnings("serial")
public class DeliveryViewEx extends DataEntity<DeliveryViewEx> {
	private String id;						// 交割表ID
	private String loanCode;				// 借款编码
	private String loanCustomerName;		// 客户姓名
	private String contractCode;			// 合同编号
	private String orgName;					// 机构名
	private String teamManagerName;			// 团队经理姓名
	private String managerName;				// 客户经理姓名
	private String customerServiceName;		// 客服人员姓名
	private String outbountByName;			// 外访人员姓名
	private String orgCode;					// 机构编号
	private String teamManagerCode;			// 团队经理编号
	private String managerCode;				// 客户经理编号
	private String customerServiceCode;		// 客服人员编号
	private String outbountByCode;			// 外访人员编号
	private String dictLoanStatus;			// 借款状态编码
	private String dictLoanStatusLabel;		// 借款状态名称
	private String newOrgName;				// 新门店名
	private String newStoresId;                // 新门店Id
	private String newTeamManagerName;		// 新团队经理
	private String newTeamManagercode;        // 新团队经理ID
	private String newManagerName;			// 新客户经理
	private String newManagercode;            // 新客户经理Id
	private String newCustomerServiceName;	// 新客服人员
	private String newCustomerServicescode;    // 新客服Id
	private String newOutbountByName;		// 新外访人员
	private String newOutboundBy;         // 新外访人员Id
	private String deliveryReason;			// 交割原因
	private String entrustMan;                // 申请人
	private String entrustManName;          // 申请人姓名
	private Date deliveryApplyTime;         // 交割申请时间
	private String deliveryResult;          // 交割结果(状态)
	private String deliveryResultLabel;     // 交个结果名
	private String rejectedReason;          // 驳回原因
	private String auditCode;               // 审核人  
	private Date checkTime;                 // 审核时间
	private Date createTime;                // 创建时间
	private String createBy;                // 创建人
	private String modifyBy;                // 修改人
	private Date modifyTime;                // 修改时间
	private String imageUrl;                // 影像地址
	
   	
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
     * @return the newTeamManagercode
     */
    public String getNewTeamManagercode() {
        return newTeamManagercode;
    }
    /**
     * @param newTeamManagercode the String newTeamManagercode to set
     */
    public void setNewTeamManagercode(String newTeamManagercode) {
        this.newTeamManagercode = newTeamManagercode;
    }
    /**
     * @return the newManagercode
     */
    public String getNewManagercode() {
        return newManagercode;
    }
    /**
     * @param newManagercode the String newManagercode to set
     */
    public void setNewManagercode(String newManagercode) {
        this.newManagercode = newManagercode;
    }
    /**
     * @return the newCustomerServicescode
     */
    public String getNewCustomerServicescode() {
        return newCustomerServicescode;
    }
    /**
     * @param newCustomerServicescode the String newCustomerServicescode to set
     */
    public void setNewCustomerServicescode(String newCustomerServicescode) {
        this.newCustomerServicescode = newCustomerServicescode;
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
    public String getEntrustManName() {
		return entrustManName;
	}
	public void setEntrustManName(String entrustManName) {
		this.entrustManName = entrustManName;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public String getNewCustomerServiceName() {
		return newCustomerServiceName;
	}
	public void setNewCustomerServiceName(String newCustomerServiceName) {
		this.newCustomerServiceName = newCustomerServiceName;
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
	public String getEntrustMan() {
		return entrustMan;
	}
	public void setEntrustMan(String entrustMan) {
		this.entrustMan = entrustMan;
	}
	public Date getDeliveryApplyTime() {
		return deliveryApplyTime;
	}
	public void setDeliveryApplyTime(Date deliveryApplyTime) {
		this.deliveryApplyTime = deliveryApplyTime;
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
	public String getCustomerServiceName() {
		return customerServiceName;
	}
	public void setCustomerServiceName(String customerServiceName) {
		this.customerServiceName = customerServiceName;
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
	public String getAuditCode() {
		return auditCode;
	}
	public void setAuditCode(String auditCode) {
		this.auditCode = auditCode;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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
	public String getCustomerServiceCode() {
		return customerServiceCode;
	}
	public void setCustomerServiceCode(String customerServiceCode) {
		this.customerServiceCode = customerServiceCode;
	}
	public String getOutbountByCode() {
		return outbountByCode;
	}
	public void setOutbountByCode(String outbountByCode) {
		this.outbountByCode = outbountByCode;
	}
	
}
