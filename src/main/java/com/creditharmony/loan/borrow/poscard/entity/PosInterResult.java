package com.creditharmony.loan.borrow.poscard.entity;

/**
 * pos催收返回结果code-name对应值
 * 
 * @Class Name PosInterResult
 * @author wzq
 * @Create In 2016年2月23日
 */
public enum PosInterResult {

    LOGIN_RESULT_SUCCESS("2","成功"),
    LOGIN_RESULT_FAIL("10","用户名或密码错误"),
    SELECT_RESULT_SUCCESS("2","成功接收"),
    SELECT_ORDER_STATUS_CWCD("20","查无此单"),
    SELECT_ORDER_STATUS_WZFWQS("23","未支付，未签收"),
    PAY__RESULT_SUCCESS("2","接收成功");
    
    
    public final String id;
    public final String name;
    
    private PosInterResult(String id,String name){
        this.id = id;
        this.name = name;
    }
}
