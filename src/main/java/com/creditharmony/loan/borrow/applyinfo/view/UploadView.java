package com.creditharmony.loan.borrow.applyinfo.view;

import java.util.ArrayList;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.common.entity.LoanCustomer;
/**
 * 
 * @Class Name UploadView
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
public class UploadView extends BaseBusinessView {
	
	// 客户详细信息
	private  LoanCustomer loanCustomer;
    // 共借人基本信息
	private List<LoanCoborrower> coborrowers = new ArrayList<LoanCoborrower>();
	//借款信息
	private LoanInfo loanInfo;
	// 操作步骤
	private String operateStep;
	// 操作结果
	private String operateResult;
	// 备注
	private String remark;
	
	private String agentCode;
	
	private String agentName;
	// 审核状态
	private String dictLoanStatus;
	private String dictLoanStatusCode;
	// 影像地址
	private String imageUrl;	
	// 排序字段
    private String orderField;
    // 按钮使能
    private String isStoreAssistant;
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getOperateStep() {
		return operateStep;
	}

	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDictLoanStatus() {
        return dictLoanStatus;
    }

    public void setDictLoanStatus(String dictLoanStatus) {
        this.dictLoanStatus = dictLoanStatus;
    }

    public String getDictLoanStatusCode() {
		return dictLoanStatusCode;
	}

	public void setDictLoanStatusCode(String dictLoanStatusCode) {
		this.dictLoanStatusCode = dictLoanStatusCode;
	}

	public LoanCustomer getLoanCustomer() {
		return loanCustomer;
	}

	public void setLoanCustomer(LoanCustomer loanCustomer) {
		this.loanCustomer = loanCustomer;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public List<LoanCoborrower> getCoborrowers() {
		return coborrowers;
	}

	public void setCoborrowers(List<LoanCoborrower> coborrowers) {
		this.coborrowers = coborrowers;
	}

    /**
     * @return the orderField
     */
    public String getOrderField() {
        return orderField;
    }

    /**
     * @param orderField the String orderField to set
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    /**
     * @return the isStoreAssistant
     */
    public String getIsStoreAssistant() {
        return isStoreAssistant;
    }

    /**
     * @param isStoreAssistant the String isStoreAssistant to set
     */
    public void setIsStoreAssistant(String isStoreAssistant) {
        this.isStoreAssistant = isStoreAssistant;
    }

	
}
