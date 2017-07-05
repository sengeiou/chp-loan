/**
 * 放款历史记录表，记录在放款节点的导出，导入时的情况
 */
package com.creditharmony.loan.borrow.grant.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款历史记录表
 * @Class Name LoanGrantHis
 * @author 朱静越
 * @Create In 2016年5月10日
 */
public class LoanGrantHis  extends DataEntity<LoanGrantHis>  {
  
	private static final long serialVersionUID = -5430079954844331150L;
	// 合同编号
	private String contractCode;
	// 申请放款金额
	private BigDecimal applyGrantAmount;
	// 成功金额
    private BigDecimal successAmount;
    // 失败金额
    private BigDecimal failAmount;
    // 回执结果
    private String grantRecepicResult;
    // 失败原因
    private String grantFailResult;
    // 退回原因
    private String grantBackMes;
    // 划扣标识
    private String grantDeductFlag;

    private String createBy;

    private Date createTime;

    private String modifyId;

    private Date modifyTime;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getApplyGrantAmount() {
		return applyGrantAmount;
	}

	public void setApplyGrantAmount(BigDecimal applyGrantAmount) {
		this.applyGrantAmount = applyGrantAmount;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public BigDecimal getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(BigDecimal failAmount) {
		this.failAmount = failAmount;
	}

	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}

	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}

	public String getGrantFailResult() {
		return grantFailResult;
	}

	public void setGrantFailResult(String grantFailResult) {
		this.grantFailResult = grantFailResult;
	}

	public String getGrantBackMes() {
		return grantBackMes;
	}

	public void setGrantBackMes(String grantBackMes) {
		this.grantBackMes = grantBackMes;
	}
	
	public String getGrantDeductFlag() {
		return grantDeductFlag;
	}

	public void setGrantDeductFlag(String grantDeductFlag) {
		this.grantDeductFlag = grantDeductFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}