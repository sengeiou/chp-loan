/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.constsSystemSetFlag.java
 * @Create By 王彬彬
 * @Create In 2016年2月28日 上午11:16:54
 */
package com.creditharmony.loan.common.consts;

/**
 * 系统设定标识
 * @Class Name SystemSetFlag
 * @author 王彬彬
 * @Create In 2016年2月28日
 */
public class SystemSetFlag {
	/**
	 * 车借服务费划扣设定
	 */
	public static String CAR_SERVICES_SETTING_FLAG = "carFeeSet";
	
	/**
	 * 车借服务费划扣设定名称
	 */
	public static String CAR_SERVICES_SETTING_NAME = "车借服务费自动划扣标识";
	
	/**
	 * 自动还款划扣设定
	 */
	public static String LOAN_PAYBACK_SETTING_FLAG = "paybackSet";
	
	/**
	 * 自动还款划扣设定名称
	 */
	public static String LOAN_PAYBACK_SETTING_NAME = "信借还款自动标识";
	
	/**
	 * 催收服务费平台规则(划扣)
	 */
	public static String URGE_FEE_PLAT_RULE = "urgeFeeRule";
	
	/**
	 * 催收服务费平台规则名称(划扣)
	 */
	public static String LURGE_FEE_PLAT_RULE_NAME = "催收服务费滚动规则";
	
	/**
	 * 催收服务费划扣设定(使用时设定值使用枚举YESNO)
	 */
	public static String URGE_SERVICES_SETTING_FLAG = "urgeFeeSet";
	
	/**
	 * 催收服务费划扣设定名称
	 */
	public static String URGE_SERVICES_SETTING_NAME = "催收服务费自动划扣标识";
	
	/**
	 * 合同签约超时时间
	 */
	public static String SYS_SIGN_TIME_OUT_FLAG = "SYS_SIGN_TIME_OUT";
	
	/**
	 * 合同签约超时时间
	 */
	public static String SYS_SIGN_TIME_OUT = "合同签约超时时间";
	
	/**
	 * 外访时时间
	 */
	public static String SYS_VISIT_TIME_OUT_FLAG = "SYS_VISIT_TIME_OUT";
	
	/**
	 * 外访超时
	 */
	public static String SYS_VISIT_TIME_OUT = "外访超时时间";
	
	/**
	 * 公安验证标记
	 */
	public static String SYS_CERT_VERIFY = "SYS_CERT_VERIFY";
	
	/**
	 * 公安验证标记分数
	 */
	public static String SYS_CERT_VERIFY_SCORE = "30";
	/**
	 * 图片质量不合格
	 */
	public static String SYS_CERT_VERIFY_FAILCODE_PIC = "9998";
	/**
	 * 验证身份信息不在查询范围内
	 */
	public static String SYS_CERT_VERIFY_FAILCODE_QUE = "2014";
	/**
	 * 姓名与身份证号一致，但身份证照片不存在
	 */
	public static String SYS_CERT_VERIFY_FAILCODE_NOPIC = "2012";
	/**
	 * 正常返回
	 */
	public static String SYS_CERT_VERIFY_FAILCODE_PER = "0000";
	/**
	 * 身份证号一致但姓名不一致
	 */
	public static String SYS_CERT_VERIFY_FAILCODE_NMA = "2011";
	/**
	 * 特征提取失败 2030
	 */
	public static String SYS_CERT_FEATURE_LOSE = "2030";
}
