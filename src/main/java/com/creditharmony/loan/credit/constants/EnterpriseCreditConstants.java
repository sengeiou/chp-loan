package com.creditharmony.loan.credit.constants;

/**
 * 企业征信常量类
 * @Class Name EnterpriseCreditConstants
 * @author zhanghu
 * @Create In 2016年1月4日
 */
public class EnterpriseCreditConstants {
	
	/**
	 * 企业征信_当前负债信息概要 信息概要 1
	 */
	public static final String CREDITCURRENTLIABILITYINFO_SUMMARY_1 = "由资产管理公司处置的债务";

	/**
	 * 企业征信_当前负债信息概要 信息概要 1
	 */
	public static final String CREDITCURRENTLIABILITYINFO_SUMMARY_2 = "欠息汇总";

	/**
	 * 企业征信_当前负债信息概要 信息概要 1
	 */
	public static final String CREDITCURRENTLIABILITYINFO_SUMMARY_3 = "垫款汇总";

	/**
	 * 企业征信_当前负债信息明细 信息概要 1
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_1 = "贷款";

	/**
	 * 企业征信_当前负债信息明细 信息概要 2
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_2 = "贸易融资";

	/**
	 * 企业征信_当前负债信息明细 信息概要 3
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_3 = "保理";

	/**
	 * 企业征信_当前负债信息明细 信息概要 4
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_4 = "票据贴现";

	/**
	 * 企业征信_当前负债信息明细 信息概要 5
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_5 = "银行承兑汇票";

	/**
	 * 企业征信_当前负债信息明细 信息概要 6
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_6 = "信用证";

	/**
	 * 企业征信_当前负债信息明细 信息概要 7
	 */
	public static final String CREDITCURRENTLIABILITYDETAIL_SUMMARY_7 = "保函";

	/**
	 * 企业征信_对外担保信息概要 信息概要 1
	 */
	public static final String CREDITEXTERNALSECURITYINFO_SUMMARY_1 = "保证汇总";

	/**
	 * 企业征信_对外担保信息概要 信息概要 2
	 */
	public static final String CREDITEXTERNALSECURITYINFO_SUMMARY_2 = "抵押汇总";

	/**
	 * 企业征信_对外担保信息概要 信息概要 3
	 */
	public static final String CREDITEXTERNALSECURITYINFO_SUMMARY_3 = "质押汇总";
	
	/**
	 * 企业征信_已结清信贷信息 1
	 */
	public static final String CREDITCREDITCLEAREDINFO_SUMMARY_1 = "由资产管理公司处置的债务";
	
	/**
	 * 企业征信_已结清信贷信息 2
	 */
	public static final String CREDITCREDITCLEAREDINFO_SUMMARY_2 = "被剥离负债汇总";
	
	/**
	 * 企业征信_已结清信贷信息 3
	 */
	public static final String CREDITCREDITCLEAREDINFO_SUMMARY_3 = "垫款汇总";
	
	/**
	 * 企业征信_已结清信贷明细 1
	 */
	public static final String CREDITCREDITCLEAREDDETAIL_SUMMARY_1 = "正常类汇总";
	
	/**
	 * 企业征信_已结清信贷明细 2
	 */
	public static final String CREDITCREDITCLEAREDDETAIL_SUMMARY_2 = "关注类汇总";
	
	/**
	 * 企业征信_已结清信贷明细 3
	 */
	public static final String CREDITCREDITCLEAREDDETAIL_SUMMARY_3 = "不良类汇总";
	
	/**
	 * 借款人类型 主借人 1
	 */
	public static final String LOAN_MAN_FLAG_1 = "0";
	
	/**
	 * 借款人类型 共借人 2
	 */
	public static final String LOAN_MAN_FLAG_2 = "1";
	
	/**
	 * 征信版本 0:详版
	 */
	public static final String CREDIT_VERSION_0 = "0";
	
	/**
	 * 征信版本 1:简版
	 */
	public static final String CREDIT_VERSION_1 = "1";
	
	/**
	 * 征信简版：是否发生过逾期
	 */
	public static final String ISOVERDUE_1 = "否";
	
	/**
	 * 征信来源：人行网站(非手工录入)
	 */
	public static final String CREDIT_SOURCE_PBC = "1";
	
	/**
	 * 贷款状态为逾期，参见数据字典type jk_credit_loaninfo_accountstatus
	 */
	public static final String LOAN_ACCOUNT_STATUS_OVERDUE = "2";

	/**
	 * 贷款状态为结清
	 */
	public static final String LOAN_ACCOUNT_STATUS_CLEAR = "1";
	
	/**
	 * 信用卡状态为逾期，参见数据字典type jk_credit_cardinfo_accountstatus
	 */
	public static final String CARD_ACCOUNT_STATUS_OVERDUE = "7";

	/**
	 * 信用卡状态为销户
	 */
	public static final String CARD_ACCOUNT_STATUS_CANCEL = "2";
	
	/**
	 * 信用卡币种为其他
	 */
	public static final String CARD_CURRENCY_OTHER = "9";
			
	/**
	 * 提示cookies异常情况
	 */
	public static final String COOKIES_EMPTY = "异常情况,请重新登录";
	
	/**
	 * (爬虫)人行征信网站登录成功之后cookie值放置到session中
	 */
	public static final String SESSION_PBC_LOGINOUT_COOKIE = "pbc_loginout_cookie";
	
}
