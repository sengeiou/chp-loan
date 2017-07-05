package com.creditharmony.loan.borrow.poscard.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

/**
 * pos催收付款交易接口实现类
 * 
 * @Class Name PosInterPay
 * @author wzq
 * @Create In 2016年2月23日
 */
@SuppressWarnings("serial")
public class PosInterPay extends DataEntity<PosInterPay> {

    /** 序号. */
    private String id;
    /** 主表id. */
    private String applyId;
    /** 登陆工号. */
    private String employeeID;
    /** Pos 机编号. */
    private String posSn;
    /** 订单号. */
    private String orderNo;
    /** 金额（格式为：77777.77）. */
    private BigDecimal amount;
    /** 交易参考号. */
    private String referNo;
    /** 合同编号. */
    private String contractCode;
    /** 存入账户. */
    private String dictDepositAccount;
    /**  回盘结果. */
    private String splitBackResult;
    /**  还款状态. */
    private String dictPaybackStatus;
    /** 蓝补金额. */
    private BigDecimal paybackBuleAmount;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getDictDepositAccount() {
        return dictDepositAccount;
    }

    public void setDictDepositAccount(String dictDepositAccount) {
        this.dictDepositAccount = dictDepositAccount;
    }

    public String getSplitBackResult() {
        return splitBackResult;
    }

    public void setSplitBackResult(String splitBackResult) {
        this.splitBackResult = splitBackResult;
    }

    public String getDictPaybackStatus() {
        return dictPaybackStatus;
    }

    public void setDictPaybackStatus(String dictPaybackStatus) {
        this.dictPaybackStatus = dictPaybackStatus;
    }

    public BigDecimal getPaybackBuleAmount() {
        return paybackBuleAmount;
    }

    public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
        this.paybackBuleAmount = paybackBuleAmount;
    }

}
