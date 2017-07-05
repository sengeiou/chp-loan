/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.entityExContractEx.java
 * @Create By 张灏
 * @Create In 2015年11月30日 上午10:28:47
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 查看合同审核阶段是否有退回的情况
 * 是否为新门店的第一单
 * @Class Name ContractEx
 * @author 张灏
 * @Create In 2015年11月30日
 * 
 */
@SuppressWarnings("serial")
public class ContractFlagEx extends DataEntity<ContractFlagEx> {
   
    // 申请ID
	private String applyId ; 
	// 是否有退回(1 有 0 无)
	private String hasBack;  
	// 上一个借款状态
	private String lastStatus;  
	// 是否为门店的第一单进件(1是 0 否)
	private String isOld;  
	
	// 最早到达待利率审核节点时间
	private Date earlyPassTime;
	// 历史记录中的借款编号
	private String loanCode;
	// 历史表中的状态
	private String hisLoanStatus;
	// 申请创建时间
	private Date applyCreateTime;
	// 申请表中的状态
	private String curLoanStatus;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getHasBack() {
		return hasBack;
	}

	public void setHasBack(String hasBack) {
		this.hasBack = hasBack;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getIsOld() {
		if(isOld!=null){
			Integer intOld = Integer.parseInt(isOld);
			 if(intOld>0){
				 return "1";
			 }else{
				 return "0";
			 }
		}
		return "0";
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

    public Date getEarlyPassTime() {
        return earlyPassTime;
    }

    public void setEarlyPassTime(Date earlyPassTime) {
        this.earlyPassTime = earlyPassTime;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getHisLoanStatus() {
        return hisLoanStatus;
    }

    public void setHisLoanStatus(String hisLoanStatus) {
        this.hisLoanStatus = hisLoanStatus;
    }

    public Date getApplyCreateTime() {
        return applyCreateTime;
    }

    public void setApplyCreateTime(Date applyCreateTime) {
        this.applyCreateTime = applyCreateTime;
    }

    public String getCurLoanStatus() {
        return curLoanStatus;
    }

    public void setCurLoanStatus(String curLoanStatus) {
        this.curLoanStatus = curLoanStatus;
    }
}
