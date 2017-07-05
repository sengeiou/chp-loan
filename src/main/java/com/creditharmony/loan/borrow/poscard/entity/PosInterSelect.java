package com.creditharmony.loan.borrow.poscard.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * pos催收 查询接口实现类
 * 
 * @Class Name PosInterSelect
 * @author wzq
 * @Create In 2016年2月23日
 */
@SuppressWarnings("serial")
public class PosInterSelect extends DataEntity<PosInterSelect> {

    /** 序号. */
    private String id;
    /** 登陆工号. */
    private String loginName;
    /** Pos 机编号. */
    private String posSn;
    /** 单位编码. */
    private String companyCode;
    /** 单位电话. */
    private String companyTel;
    /** 单位地址. */
    private String companyAddr;
    /** 订单号. */
    private String orderNo;
    /** 值必须同 OrderNo. */
    private String receiverOrderNo;
    /** 收件人姓名. */
    private String receiverName;
    /** 收件人地址. */
    private String rceiverAddr;
    /** 收件人电话. */
    private String rceiverTel;
    /** 订单金额（精确到分）. */
    private String amount;
    /** 订单状态. */
    private String orderStatus;
    /** 订单状态中文描述. */
    private String orderStatusMsg;
    /** 还款结果. */
    private String payResult;
    /** 还款状态. */
    private String dictPaybackStatus;
    /** 还款方式. */
    private String dictRepayMethod;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPosSn() {
        return posSn;
    }
    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    public String getCompanyTel() {
        return companyTel;
    }
    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }
    public String getCompanyAddr() {
        return companyAddr;
    }
    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getReceiverOrderNo() {
        return receiverOrderNo;
    }
    public void setReceiverOrderNo(String receiverOrderNo) {
        this.receiverOrderNo = receiverOrderNo;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getRceiverAddr() {
        return rceiverAddr;
    }
    public void setRceiverAddr(String rceiverAddr) {
        this.rceiverAddr = rceiverAddr;
    }
    public String getRceiverTel() {
        return rceiverTel;
    }
    public void setRceiverTel(String rceiverTel) {
        this.rceiverTel = rceiverTel;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrderStatusMsg() {
        return orderStatusMsg;
    }
    public void setOrderStatusMsg(String orderStatusMsg) {
        this.orderStatusMsg = orderStatusMsg;
    }
    public String getPayResult() {
        return payResult;
    }
    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
    
    public String getDictPaybackStatus() {
        return dictPaybackStatus;
    }
    public void setDictPaybackStatus(String dictPaybackStatus) {
        this.dictPaybackStatus = dictPaybackStatus;
    }
    public String getDictRepayMethod() {
        return dictRepayMethod;
    }
    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod;
    }
   
}
