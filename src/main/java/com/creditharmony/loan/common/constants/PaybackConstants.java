package com.creditharmony.loan.common.constants;

/**
 * 还款通用常量
 * @Class Name BpmConstants
 * @author zhangfeng
 * @Create In 2016年4月22日
 */
public interface PaybackConstants {

	/**
	 * 存款人验证
	 */
	public static final String[] VALIDATION_DEPOSITNAME = { "存现", "现金", "转帐",
			"转款", "支付宝", "无", "支行", "建行", "电子汇入", "电子汇出", "营业部", "分行", "核算",
			"银行卡总中心" };
	
	// 匹配关闭
	public static final String NOMATCHING = "0";
	// 自动匹配
	public static final String AUTOMATCHING = "1";
	// 实时匹配
	public static final String REALMATCHING = "2";
}
