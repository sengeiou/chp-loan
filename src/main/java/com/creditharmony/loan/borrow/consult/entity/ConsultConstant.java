/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.consult.entityConsultConstant.java
 * @Create By 张灏
 * @Create In 2016年2月1日 下午5:02:13
 */
package com.creditharmony.loan.borrow.consult.entity;

/**
 * 咨询常量
 * @Class Name ConsultConstant
 * @author 张灏
 * @Create In 2016年2月1日
 */
public interface ConsultConstant {

    public static final String UNSETTLED = "0";
    
    public static final String SETTLED = "1";
    
    public static final String SHOW_INFO = "2";
    
    public static final String UNSETTLED_MESSAGE="借款未结清，不允许借款";
    
    public static final Integer AUDIT_REJECT_INTERVAL_MONTH = 6;
    
    public static final String AUDIT_REJECT_MESSAGE = "该客户在信审阶段被拒绝，6个月内不允许进件";
    
    public static final Integer STORE_REJECT_INTERVAL_MONTH = 1;
    
    public static final String STORE_REJECT_MESSAGE = "该客户被门店拒绝，1个月内不允许进件";
    
    public static final String BLACK_LOG_MESSAGE = "黑名单客户，不允许进件";
    
    public static final String PROCESSING_MESSAGE = "该客户的借款没有结束，不允许重新进件";
    
    public static final Integer ZERO = 0;
    
    public static final Integer SEVEN_DAY = 7;
    
    public static final String CONSULT_MESSAGE = "该客户已经咨询，不允许再次咨询";
    
    public static final String NO_GET_ORDER_MESSAGE = "汇金门店未取单,请取单";
    
    public static final String GET_ORDER_MESSAGE = "电销已取单,请录入申请";
    
    public static final String SEVEN_DAY_MESSAGE = "该身份证无法录入dayAndHoursStr后再尝试";
    
    public static final String STORE_BACK_MESSAGE = "电销已录入咨询,请等待电销发送至门店";
    
    public static final String CUSTOMER_INTO_MESSAGE = "信借未结清不可以继续借款";
    
    public static final String CUSTOMER_IN_UNSETTLE_MATE_DATA = "身份证和系统中存在的尚未结清的主借人配偶相同,请结清后进行借款";
	public static final String CUSTOMER_IN_UNSETTLE_COBO_DATA = "身份证和系统中存在的尚未结清的共借人相同,请结清后进行借款";
	public static final String CUSTOMER_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA = "身份证和系统中存在的尚未结清的最优自然人保证人相同,请结清后进行借款";
    
}
