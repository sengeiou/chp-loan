package com.creditharmony.loan.common.constants;

/**
 * 企业征信业务类别
 * @Class Name EnterpriseCreditBusinessType
 * @author 王浩
 * @Create In 2016年2月24日
 */
public interface CreditBusinessType {
	
	/**
	 * 贷款
	 */
	public static final String LOAN = "1"; 
	
	/**
	 * 贸易融资
	 */
	public static final String TRADE_FINANCING = "2"; 
	
	/**
	 * 保理 
	 */
	public static final String FACTORING = "3"; 
	
	/**
	 * 票据贴现
	 */
	public static final String NOTES_DISCOUNTED = "4"; 
	
	/**
	 * 银行承兑汇票
	 */
	public static final String BANK_ACCEPTANCE = "5"; 
	
	/**
	 * 信用证
	 */
	public static final String LETTER_CREDIT = "6"; 
	
	/**
	 * 保函
	 */
	public static final String LETTER_GUARANTEE = "7"; 
	
}
