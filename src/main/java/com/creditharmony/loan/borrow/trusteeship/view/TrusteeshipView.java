package com.creditharmony.loan.borrow.trusteeship.view;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 金账户修改流程view
 * 
 * @Class Name TrusteeshipView
 * @author 王浩
 * @Create In 2016年2月27日
 */
public class TrusteeshipView extends BaseBusinessView {
	// 借款编号
	private String loanCode;
	// 借款状态名称
	private String dictLoanStatus;
	// 借款状态代码
	private String dictLoanStatusCode;
	// 金账户开户状态
	private String kingStatus;
	// 金账户退回原因
	private String kingBackReason;
	// 金账户协议库返回
	private String kingProctolBackReason;
	// 
	private String trustCash;
	//
	private String trustRecharge;
	// 注册标识
    private String registFlag;
    //操作类型
    private String operateType;
    //排序字段
    private String orderField;
    // 是否加盖失败
    private String signUpFlag;
	public String getKingStatus() {
		return kingStatus;
	}

	public void setKingStatus(String kingStatus) {
		this.kingStatus = kingStatus;
	}

	public String getKingBackReason() {
		return kingBackReason;
	}

	public void setKingBackReason(String kingBackReason) {
		this.kingBackReason = kingBackReason;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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

	public String getKingProctolBackReason() {
		return kingProctolBackReason;
	}

	public void setKingProctolBackReason(String kingProctolBackReason) {
		this.kingProctolBackReason = kingProctolBackReason;
	}

	public String getTrustCash() {
		return trustCash;
	}

	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}

	public String getTrustRecharge() {
		return trustRecharge;
	}

	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
	}

    /**
     * @return the registFlag
     */
    public String getRegistFlag() {
        return registFlag;
    }

    /**
     * @param registFlag the String registFlag to set
     */
    public void setRegistFlag(String registFlag) {
        this.registFlag = registFlag;
    }

    /**
     * @return the operateType
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * @param operateType the String operateType to set
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
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
     * @return the signUpFlag
     */
    public String getSignUpFlag() {
        return signUpFlag;
    }

    /**
     * @param signUpFlag the String signUpFlag to set
     */
    public void setSignUpFlag(String signUpFlag) {
        this.signUpFlag = signUpFlag;
    }		
	
}
