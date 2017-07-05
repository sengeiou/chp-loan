/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipConstant.java
 * @Create By 王浩
 * @Create In 2015年2月27日 下午2:05:30
 */
package com.creditharmony.loan.borrow.trusteeship.entity.ex;


/**
 * 资金托管用到的常量
 * @Class Name TrusteeshipConstant
 * @author 王浩
 * @Create In 2016年2月27日
 */
public class TrusteeshipConstant {
    // 金账户-退回
    public static final String ACCOUNT_UNDO = "ACCOUNT_UNDO";
    // 金账户-开户成功    
    public static final String ACCOUNT_SUCCESS = "ACCOUNT_SUCCESS";
    // 金账户-开户失败
    public static final String ACCOUNT_ERROR = "ACCOUNT_ERROR";    
    // 金账户-自动开户
    public static final String ACCOUNT_TRY_OPEN = "ACCOUNT_TRY_OPEN";
    
    
    // 金账户待放款-确认放款
    public static final String LEND_CONFIRM = "LEND_CONFIRM";    
    // 金账户待放款-取消TG标识
    public static final String LEND_CLEAR_TG_FLAG = "LEND_CLEAR_TG_FLAG";
    // 金账户待放款-确认退回
    public static final String LEND_UNDO = "LEND_UNDO";
    
    
}
