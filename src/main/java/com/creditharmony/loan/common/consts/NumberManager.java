/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.constsNumberManager.java
 * @Create By 王彬彬
 * @Create In 2015年12月29日 上午11:30:37
 */
package com.creditharmony.loan.common.consts;

/**
 * 各种编号规则属性
 * 
 * @Class Name NumberManager
 * @author 王彬彬
 * @Create In 2015年12月29日
 */
public class NumberManager {

	/**
	 * 编号递增
	 */
	public static final int STEP = 1;
	
	/**
	 * 开始编号
	 */
	public static final int START = 1;

	/**
	 * 初始化周期值
	 */
	public static final String DATE_INIT = "00000000";

	/**
	 * 合同类(末尾增加门店code)
	 */
	public static final String CONTRACT_TYPE = "0";

	/**
	 * 信借类
	 */
	public static final String LOAN_TYPE = "1";
	
	/**
	 * 信用借款合同标识
	 */
	public static final String CONTRACT_NO_TYPE = "XYHTBH";

	/**
	 * 客户类
	 */
	public static final String CUSTOMER_TYPE = "2";

	/**
	 * 更新周期（默认）
	 */
	public static final String UPDATE_CYC = "0";

	/**
	 * 更新周期（年）
	 */
	public static final String UPDATE_CYC_YEAR = "1";

	/**
	 * 更新周期（月）
	 */
	public static final String UPDATE_CYC_MONTH = "2";

	/**
	 * 更新周期（日）
	 */
	public static final String UPDATE_CYC_DAY = "3";

	/**
	 * 有效标识（有效）
	 */
	public static final String EFFECTIVE = "0";

	/**
	 * 有效标识（无效）
	 */
	public static final String NO_EFFECTIVE = "1";
	
	/**
	 * 结清证明
	 */
	public static final String SETTLED_NO_TYPE = "JQZM";
	/**
	 * ZCJ结清证明
	 */
	public static final String ZCJ_SETTLED_NO_TYPE = "ZCJJQZM";
}
