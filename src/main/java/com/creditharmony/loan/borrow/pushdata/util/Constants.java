/**
 *
 */
package com.creditharmony.loan.borrow.pushdata.util;

import com.creditharmony.core.approve.type.CheckType;
import com.creditharmony.core.approve.type.ChkResult;
import com.creditharmony.core.common.type.EffectiveFlag;
import com.creditharmony.core.common.type.EmailState;
import com.creditharmony.core.common.type.SmsState;
import com.creditharmony.core.common.type.UseableType;
import com.creditharmony.core.fortune.type.AppalyState;
import com.creditharmony.core.fortune.type.AreaType;
import com.creditharmony.core.fortune.type.ContractChangeType;
import com.creditharmony.core.fortune.type.CreditState;
import com.creditharmony.core.fortune.type.CustomerState;
import com.creditharmony.core.fortune.type.DeliveryStatus;
import com.creditharmony.core.fortune.type.DeliveryType;
import com.creditharmony.core.fortune.type.FilecpState;
import com.creditharmony.core.fortune.type.LendState;
import com.creditharmony.core.fortune.type.ProductType;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.FamilyRelation;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RelationType;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.type.LoanOrgType;

/**
 * @author Levn
 *
 */
public class Constants {
	
	public static final String LOAN_STATUS0  = "0";
	public static final String LOAN_STATUS4 = "4";
	
	/**
	 * 进件来源: 2、初审提报
	 */
	public static final String INTO_SOURCE = "2";
	
	/**
	 * 客户状态：有效
	 */
	public static final String CUST_STATUS = EffectiveFlag.yx.value;
	
	/**
	 * 数字常量
	 */
	public static final Integer NUMBER_ONE = 1;
	public static final Integer NUMBER_PENALTY = 1000;
	public static final Integer NUMBER_HUNDRED = 100;
	public static final double NUMBER_TEN_PERCENT = 0.1;
	public static final double NUMBER_PUNISH = 0.003;
	public static final int NUMBER_REPAYDAY = 3;
	public static final int NUMBER_ZERO = 0;
	
	public static final int NUMBER_PERIODS1 = 30;
	public static final int NUMBER_PERIODS2 = 90;
	public static final int NUMBER_PERIODS3 = 180;
	public static final int NUMBER_PERIODS4 = 270;
	public static final int NUMBER_PERIODS5 = 360;
	/**
	 * 首期费用=借款总额*2%
	 */
	public static final double DOUBLE_TWO = 0.02;
	
	/**
	 * 首期还款日期 = 签署合同日期 + 29 天
	 */
	public static final int NUMBER_FIRST = 29;
	
	
	public static final int NUMBER_FOUR = 4;
	/**
	 * 比较 -1：小于；0：等于；1：大于
	 */
	public static final Integer COMPARE_BIG = 1;
	public static final Integer COMPARE_EQUALS = 0;
	public static final Integer COMPARE_LESS = -1;
	/**
	 * 用户类型：团队经理
	 */
	public static final String TEAM_MANAGER = LoanRole.TEAM_MANAGER.id;
	
	/**
	 * 用户类型：理财经理
	 */
	public static final String CUST_MANAGER = LoanRole.FINANCING_MANAGER.id;

	/**
	 * 机构类型：汇金业务部
	 */
	public static final String BUISNESS_DEPT = LoanOrgType.BUISNESS_DEPT.key;

	/**
	 * 关联类型(0 主借人，1共借人，2配偶)
	 */
	public static final String DICTRCUSTOMTERTYPE_CUSTOMER = LoanManFlag.MAIN_LOAN.getCode();
	
	/**
	 * 查重判断 ：提前180天
	 */
	public static final int CUSTOMERINTOTIME_BEFOR = -180;
	/**
	 * 查重比较 0
	 */
	public static final int ZERO = 0;
	
	/**
	 * 查重比较 1
	 */
	public static final int ONE = 1;

	/**
	 * 运行Log保存路径	
	 */
	public static final String INFO_LOG_DIR = "D:/batchLogs/InfoLogs/";
	
	/**
	 * 异常Log保存路径
	 */
	public static final String ERROR_LOG_DIR = "D:/batchLogs/ErrorLogs/";
	
	/**
	 * 调试Log保存路径
	 */
	public static final String DEBUG_LOG_DIR = "D:/batchLogs/DebugLogs/";
	
	/**
	 * 运行LOG文件名
	 */
	public static final String INFO_LOG_NAME = "_InfoLog";
	
	/**
	 * 异常LOG文件名
	 */
	public static final String ERROR_LOG_NAME = "_ErrorLog";
	
	/**
	 * 调试LOG文件名
	 */
	public static final String DEBUG_LOG_NAME = "_DebugLog";
	/**
	 * 批处理操作人
	 */
	public static final String BATCH_NAME = "Batch";
	
	/**
	 * 换行符
	 */
	public static final String ENTER_MARK = "\r\n";
	
	/**
	 * 消息配置文件名
	 */
	public static final String MESSAHE_PROPERTY_NAME = "messages";
	
	/**
	 *生日提醒文件存放的路径在jobfile.properties的key值
	 */
	public static final String BIRTHDAY_FILE_PATH = "birthdayFile";
	/**
	 * 批处理中间文件路径管理配置文件（.properties）的文件名
	 */
	public static final String JOB_SETTING_PROPERTY_NAME = "job-setting";
	/**
	 *产品到期提醒文件存放的路径在jobfile.properties的key值
	 */
	public static final String PAYBACK_REMINDER_FILE_PATH = "paybackReminderFile";
	/**
	 * 定期产品提前10天通知
	 */	
	public static final int REMINDER_REGULAR = 10; 
	
	/**
	 * 非定期产品提前30天通知
	 */
	public static final int REMINDER_NONPERIODICAL = 30;  
	
	/**
	 * 借款状态（还款中）
	 */
	public static final String LOAN_STATE = LoanStatus.REPAYMENT.getCode();

	/**
	 * 出借状态（待审批）
	 */
	public static final String FINAL_WAITE_AUDIT = LendState.DCJSP.value;
	/**
	 * 出借状态（已经出借）
	 */
	public static final String FINAL_ALREADY_LEND = LendState.HKCG.value;
	
	/**
	 * 产品类型（定期）
	 */
	public static final String PRODUCT_TYPE_PERIODICAL = ProductType.QXLCP.value;
	
	/**
	 * 产品类型（非定期）
	 */
	public static final String PRODUCT_TYPE_NON_PERIODICAL = ProductType.FQXLCP.value;
	
	/**
	 * 短信模板状态（可用）
	 */
	public static final String SMS_TEMP_STATUS = UseableType.YES;
	
	/**
	 * 产品名称 (当前为虚拟数据，需改动)
	 */
	public static final String PRODUCT_NAME = "月满盈";
	/**
	 * 短信模板ID(当前为虚拟数据，需改动)
	 */
	public static final String TEMP_ID = "1234";
	
	/**
	 * 业务人员所属的最高 组织机构级别
	 */
	public static final String ORG_TYPE = "4";
	
	/**
	 * 实时批量发送邮件  标识0：邮件  1：短信
	 */
	public static final String RECORD_FLAG = "0";
	
	/**
	 * 实时批量发送邮件状态 标识:待发送
	 */
	public static final String SEND_STATUS1 = EmailState.DFS.value;

	
	/**
	 * 实时批量发送邮件状态:发送成功
	 */
	public static final String SEND_STATUS2 = EmailState.FSCG.value;
	
	/**
	 * 邮件发送状态:发送失敗
	 */
	public static final String SEND_STATUS5 = SmsState.FSSB.value;
	
	/**
	 * 是否逾期  1：是；0：否
	 */
	public static final String IS_OVERDUE = YESNO.YES.getCode();
	
	/**
	 * 借款状态：结清
	 */
	public static final String FINISH_STATUS = LoanStatus.SETTLE.getCode();
	
	/**
	 * 借款状态：提前结清
	 */
	public static final String EARLY_STATUS = LoanStatus.EARLY_SETTLE.getCode();
	
	/**
	 * 操作类型：合同审核
	 */
	public static final String CONT_TYPE = "1";
	
	/**
	 * 期供状态 ：0 还款中、3 已还款、1 逾期、2 追回
	 */
	public static final String MONTH_STATUS = PeriodStatus.PAID.getCode();
	public static final String BACK_STATUS = PeriodStatus.REPLEVY.getCode();
	public static final String REPAYMENT = PeriodStatus.REPAYMENT.getCode();
	public static final String OVERDUE = PeriodStatus.OVERDUE.getCode();
	
	/**
	 * 区域类型(0省级，1市级，区）
	 */
	public static final String AREA_TYPE = AreaType.PROVINCE.value;
	public static final String AREA_TYPE2 = AreaType.CITY.value;
	
	/**
	 * 出借状态
	 */
	public static final String LEND_STATUS = LendState.SPTG.value;
	
	/**
	 * 回盘结果：成功
	 */
	public static final String RECEPIC_RESULT = CounterofferResult.PAYMENT_SUCCEED.getCode();
	
	/**
	 * 审核类型
	 */
	public static final String DICT_CHECK_TYPE = CheckType.XS_FIRST_CREDIT_AUDIT.getCode();
	
	/**
	 * 发送状态：未发送
	 */
	public static final String OPER_STATUS = SmsState.WFS.value;
	
	/**
	 * 提醒类型:定期
	 */
	public static final String REMINDER_TYPE_REGULAR = ProductType.QXLCP.value;
	
	/**
	 * 提醒类型:非定期
	 */
	public static final String REMINDER_TYPE_UNREGULAR = ProductType.FQXLCP.value;
	
	/**
	 * 一个月后的时间
	 */
	public static final int MONTH_LATER = -1;
	
	/**
	 * 一个月前的时间
	 */
	public static final int MONTH_BEFOR = 1;
	/**
	 * 二个月前的时间
	 */
	public static final int MONTHS_BEFOR = 2;
	
	/**
	 * 客户状态：已开户
	 */
	public static final String OPEN_STATUS = CustomerState.YKH.value;
	
	/**
	 * 可用天数
	 */
	public static final int AVAILABLE_DAYS = 0;
	
	/**
	 * 是否可用“冻结”
	 */
	public static final String FREE_FLAG = CreditState.DJ.value;
	
	/**
	 * 是否可用“可用”
	 */
	public static final String FREE_FLAG_YES = CreditState.KY.value;
	
	/**
	 * 是否可用“不可用”
	 */
	public static final String FREE_FLAG_NO = CreditState.BKY.value;
	
	/**
	 * 合同    操作类型
	 */
	public static final String DICT_OPERATE_TYPE = LoanApplyStatus.CONTRACT_MAKING.getCode();
	
	/**
	 * 月满盈天数更新: 关于日期的计算（可用日期 - 1）
	 */
	public static final int BATCH_DAYS = 1;
	
	/**
	 * 日期变化（提前一天）
	 */
	public static final int BACK_DAY = -1;
	/**
	 * 客户生日提醒（提前2天提醒）
	 */
	public static final int BIRTHDAY_REMINDER = 2;
	/**
	 * 还款日临近提醒（提前2天提醒）
	 */
	public static final int PAYBACK_REMINDER = 3;
	
	/**
	 * 比较
	 */
	public static final int COMPARE_TO = -1;
	
	/**
	 * 赋值1
	 */
	public static final int COMPARE_ONE_TO = 100;
	
	/**
	 * 赋值2
	 */	
	public static final double COMPARE_TWO_TO = 0.1 ;
	
	/**
	 * 赋值3
	 */	
	public static final double COMPARE_Three_TO = 0.003 ;
	
	
	/**
	 * 财富/共通/汇金  短信发送状态：0 待发送
	 * 
	 */
	public static final String SMSSENDSTATUS_WAIT = SmsState.DFS.value;
	
	/**
	 * 财富/共通/汇金  短信发送状态：1 已发送
	 * 
	 */
	public static final String SMSSENDSTATUS_SEND = SmsState.YFS.value;
	
	/**
	 * 财富/共通/汇金  短信发送状态：2 发送成功
	 * 
	 */
	public static final String SMSSENDSTATUS_SUCCESS = SmsState.FSCG.value;
	/**
	 * 财富/共通/汇金  短信发送状态：3 发送失败
	 * 
	 */
	public static final String SMSSENDSTATUS_FAIL = SmsState.FSSB.value;
	/**
	 * 客户生日提醒短信编号
	 * 
	 */
	public static final String SMS_CODE = "01";
	
	/**
	 * 客户到期提醒短信编号
	 * 
	 */
	public static final String PAYBACK_REMINDER_SMS_CODE = SmsType.REMIND.getCode();
	
	/**
	 * 逾期M1
	 */
	public static final int OVER_DUE = 1;
	
	/**
	 * 逾期M2
	 */
	public static final int OVER_DUE2 = 2;
	
	/**
	 * 逾期M3
	 */
	public static final int OVER_DUE3 = 3;
	
	/**
	 * 岗位名称：团队经理
	 */
	public static final String TEAM_NAMAGER = LoanRole.TEAM_MANAGER.id;
	
	/**
	 * 岗位名称：客服
	 */
	public static final String CUST_SERVICE = LoanRole.CUSTOMER_SERVICE.id;
	
	/**
	 * 绩效考核数据-0-6分子
	 */
	public static final int JXSIX_MEMBER = 6;
	
	/**
	 * 绩效考核数据-0-12分子
	 */
	public static final int JXTWELVE_MEMBER = 12;
	
	/**
	 * 汇金合同文件URLID
	 */
	public static final String LOAN_RPT_WORD_URLID1 = "loanUrl1";
	
	public static final String LOAN_RPT_WORD_URLID2 = "loanUrl2";
	
	public static final String LOAN_RPT_WORD_URLID3 = "loanUrl3";
	
	public static final String LOAN_RPT_WORD_URLID4 = "loanUrl4";
	
	public static final String LOAN_RPT_WORD_URLID5 = "loanUrl5";
	
	public static final String LOAN_RPT_WORD_URLID6 = "loanUrl6";
	
	public static final String LOAN_RPT_WORD_URLID7 = "loanUrl7";
	
	public static final String LOAN_RPT_WORD_URLID8 = "loanUrl8";
	
	/**
	 * 《借款协议》终止通知书URLID
	 */
	public static final String LOAN_PDF_URLID = "url05";
	
	public static final String LOAN_WORD_URLID = "url06";
	
	
	/**
	 * TG合同制作url
	 */
	public static final String LOAN_PDF_URLID10 = "loanUrl10";
	
	public static final String LOAN_PDF_URLID11 = "loanUrl11";
	
	public static final String LOAN_PDF_URLID12 = "loanUrl12";
	
	public static final String LOAN_PDF_URLID13 = "loanUrl13";
	
	public static final String LOAN_PDF_URLID14 = "loanUrl14";
	
	
	public static final String LOAN_PDF_URLID15 = "loanUrl15";
	
	public static final String LOAN_PDF_URLID16 = "loanUrl16";
	
	
	public static final String LOAN_PDF_URLID17 = "loanUrl17";
	
	public static final String LOAN_PDF_URLID18 = "loanUrl18";
	
	
	
	/**
	 * 金信合同制作url
	 */
	
	public static final String LOAN_PDF_URLID20 = "loanUrl20";
	
	public static final String LOAN_PDF_URLID21 = "loanUrl21";
	
	public static final String LOAN_PDF_URLID22 = "loanUrl22";
	
	
	
	
	
	/**
	 * 汇金合同文件名id
	 */
	public static final String LOAN_RPT_FILE01 = "rptfile01";
	
	public static final String LOAN_RPT_FILE02 = "rptfile002";
	
	public static final String LOAN_RPT_FILE03 = "rptfile03";
	
	public static final String LOAN_RPT_FILE04 = "rptfile04";
	
	public static final String LOAN_RPT_FILE05 = "rptfile05";
	
	public static final String LOAN_RPT_FILE055 = "rptfile055";
	
	public static final String LOAN_RPT_FILE06 = "rptfile06";
	
	public static final String LOAN_RPT_FILE07 = "rptfile07";
	
	public static final String LOAN_RPT_FILE08 = "rptfile08";
	
	public static final String LOAN_RPT_FILE09 = "rptfile09";

	
	/**
	 * TG文件制作名称
	 */
	public static final String LOAN_RPT_FILE10 = "loanfile10";
	
	public static final String LOAN_RPT_FILE011 = "loanfile11";
	
	public static final String LOAN_RPT_FILE012 = "loanfile12";
	
	public static final String LOAN_RPT_FILE013 = "loanfile13";
	
	public static final String LOAN_RPT_FILE014 = "loanfile14";
	
	public static final String LOAN_RPT_FILE0144 = "loanfile144";
	
	
	public static final String LOAN_RPT_FILE015 = "loanfile15";
	
	public static final String LOAN_RPT_FILE016 = "loanfile16";
	
	
	public static final String LOAN_RPT_FILE017 = "loanfile17";
	
	public static final String LOAN_RPT_FILE018 = "loanfile18";
	
	
	/**
	 * 金信文件制作名称
	 */
	public static final String LOAN_RPT_FILE020 = "loanfile20";
	
	public static final String LOAN_RPT_FILE021 = "loanfile21";
	
	public static final String LOAN_RPT_FILE022 = "loanfile22";
	
	
	public static final String LOAN_RPT_FILE023 = "loanfile23";
	
	public static final String LOAN_RPT_FILE024 = "loanfile24";
	
	
	
	
	/**
	 * 借款文件名id
	 */
	public static final String Loan_FILE01 = "loanfile01";
	
	/**
	 * 默认字符编码
	 */
	public static final String UTF8 = "UTF-8";
	
	/**
	 * 财富非首期债权文件名id
	 */
	public static final String RPT_FILE02 = "rptfile02";
	
	/**
	 * 财富非首期债权文件URLID
	 */
	public static final String FROTUNE_RPT_PDF_URLID = "url02";
	
	/**
	 * 财富非首期债权文件URLID
	 */
	public static final String FROTUNE_RPT_WORD_URLID = "url04";
	
	/**
	 * 邮件主题
	 */
	public static final String MAILSUBJECT = "mailsubject";
	
	/**
	 * 邮件code
	 */
	public static final String MAILCODE = "mailcode";
	
	/**
	 * 邮件待发送文件存放路径
	 */
	public static final String MAILLISTFILE = "mailListFile";
	
	/**
	 * 外访超时状态天数，超过这个天数就需要更新
	 */
	public static final int OUTSIDE_CHECK_OVER_DAYS=7;
	/**
	 * 签约超时天数，超过这个天数就需要更新
	 */
	public static final int CONTRACT_SIGN_OVER_DAYS=7;
	/**
	 * 初审回退超时天数，超过这个天数就需要更新
	 */
	public static final int AUDIT_BACK_OVER_DAYS=10;
	/**
	 * 外访状态----外访超时
	 */
	public static final String OUTSIDE_CHECK_STUTUS=LoanApplyStatus.STORE_VISIT_TIMEOUT.getCode();
	/**
	 * 借款状态----签约超时
	 */
	public static final String LOAN_STUTUS1="签约超时";
	
	/**
	 * 借款状态----回退超时
	 */
	public static final String LOAN_STUTUS2=LoanApplyStatus.BACK_TIMEOUT.getCode();

	/**
	 * 初审结果----拒绝
	 */
	public static final String AUDIT_RESULT=LoanApplyStatus.CHECK_REJECT.getCode();
	/**
	 * 最新借款状态----已结清
	 */
	public static final String CURRENT_STATUS=LoanStatus.SETTLE.getCode();
	
	/**
	 * 查询放款明细列表前今天条数，这里是1天
	 */
	public static final int LOAN_DETAIL_DAYS=1;

	/**
	 * 汇金合同存放路径
	 */
	public static final String CONTRACTFILE = "contractFile";
	
	/**
	 * 合同审核状态   待制作合同
	 */
	public static final String DICTCHECKSTATUS = LoanApplyStatus.CONTRACT_MAKE.getCode();
	
	/**
	 * 字典表是"还款状态"的Type
	 */
	public static final String JK_REPAY_STATUS = "jk_repay_status";
	
	/**
	 * 反欺诈触犯内容
	 */
	public static final String OFFEND_MSG = "1";
	
	/**
	 * 汇金业绩统计报表查询提前的天数 1天
	 */
	public static final int LOAN_PERFORMANCE_DAY = 1;
	
	/**
	 * 划扣明细查询的天数
	 */
	public static final int DEDUCT_DETAIL_DAY=1;
	
	/**
	 * 得到合同审核错误类型列表前几天的天数
	 */
	public static final int CONT_AUDIT_ERROR_TYPE_DAYS=1;
	
	/**
	 * 得到对应的所有合同使用情况列表前几天的天数
	 */
	public static final int CONT_USED_STATUS=1;
	
	/**
	 * 批处理用富友单笔代收flag
	 */
	public static final Integer FY_DS = 1;
	
	/**
	 * 批处理用好易联单笔代收flag
	 */
	public static final Integer HYL_DS = 2;
	
	/**
	 * 批处理用中金单笔代收flag
	 */
	public static final Integer ZJ_DS = 3;
	
	/**
	 * 批处理用富友单笔代付flag
	 */
	public static final Integer FY_DF = 4;
	
	/**
	 * 日期格式化可选项
	 */
	public static final String[] DATAFORMAT = {"yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMddHHmmssSSS", "HH:mm","HH","MM","yyyy-MM","yyyy/MM","dd","yyyyMM","yyyyMMddHHmmss","yyyy","HH:mm:ss"};
	
	/**
	 * 批次号（判断是否先进行划扣操作依据，汇金用）
	 */
	public static final String SPLITPCH = "1";
	
	/**
	 * 第三方平台接口响应码（成功）
	 */
	public static final String RETCODE_OK = "0";
	
	/**
	 * 反欺诈规则
	 */
	public static final String OFFEND_C001 = "C001";
	
	/**
	 * 反欺诈规则内容C001
	 */
	public static final String OFFEND_MSG_C001 = "申请人重复进件（证件号码相同且姓名相同），本次进件的户籍地址与前次进件的户籍地址不同";
	
	/**
	 * 反欺诈规则内容R003
	 */
	public static final String OFFEND_R003 = "R003";
	
	/**
	 * 反欺诈规则内容R003
	 */
	public static final String OFFEND_MSG_R003 = "申请人证件号码不同，且姓名不同，申请人手机号码与系统中已有申请人配偶的手机号码相同，且姓名相同，且大于等于1";
	
	/**
	 * 反欺诈规则内容R004
	 */
	public static final String OFFEND_R004 = "R004";
	
	/**
	 * 反欺诈规则内容R004
	 */
	public static final String OFFEND_MSG_R004 = "申请人证件号码不同，且姓名不同，申请人手机号码与系统中已有申请人配偶的手机号码相同，且姓名不同，且大于等于2";
	
	/**
	 * 反欺诈规则内容R005
	 */
	public static final String OFFEND_R005 = "R005";
	
	/**
	 * 反欺诈规则内容R005
	 */
	public static final String OFFEND_MSG_R005 = "申请人证件号码不同，且姓名不同，申请人的证件号码与系统中已有申请人配偶的证件号码相同，且姓名相同，手机号相同，且大于等于1";
	
	/**
	 * 反欺诈规则内容R006
	 */
	public static final String OFFEND_R006 = "R006";
	
	/**
	 * 反欺诈规则内容R006
	 */
	public static final String OFFEND_MSG_R006 = "申请人证件号码不同，且姓名不同，申请人的证件号码与系统中已有申请人配偶的证件号码相同，姓名不同，手机号相同，且大于等于1";
	
	/**
	 * 汇金集中划扣截止时间
	 */
	public static final String LOAN_BATCH_STOP_TIME = "loanBatchStopTime";
	
	/**
	 * 借款人类型：主借人
	 */
	public static final String CUSTOMER_TYPE_MAIN = LoanManFlag.MAIN_LOAN.getCode();
	
	/**
	 * 借款人类型：共借人
	 */
	public static final String CUSTOMER_TYPE_CO = LoanManFlag.COBORROWE_LOAN.getCode();

	/**
	 * 异常类型：手机
	 */
	public static final String CONTRAST_EXCEPTION_TYPE_PHONE = "手机";
	
	/**
	 * 异常类型：单位名称
	 */
	public static final String CONTRAST_EXCEPTION_TYPE_UNIT = "单位名称";
	
	/**
	 * 异常类型：家庭固话
	 */
	public static final String CONTRAST_EXCEPTION_TYPE_FAMILY_TEL = "家庭固话";
	
	/**
	 * 异常类型：家庭联系人姓名和电话
	 */
	public static final String CONTRAST_EXCEPTION_TYPE_CONTACT_AND_TEL = "家庭联系人姓名和电话";
	
	/**
	 * 异常类型：居住地址
	 */
	public static final String CONTRAST_EXCEPTION_TYPE_ADDRESS = "居住地址";
	
	/**
	 * 申请信息历史对比异常点：手机
	 */
	public static final String CONTRAST_EXCEPTION_REASON_PHONE = "客户本次提供的手机号码之前未出现。前次申请本人号码为：";
	
	/**
	 * 申请信息历史对比异常点：单位名称
	 */
	public static final String CONTRAST_EXCEPTION_REASON_UNIT = "前次为";
	
	/**
	 * 申请信息历史对比异常点：家庭固话
	 */
	public static final String CONTRAST_EXCEPTION_REASON_FAMILY_TEL = "家庭固话有变更，前次家庭固话为：";
	
	/**
	 * 申请信息历史对比异常点：家庭联系人姓名和电话
	 */
	public static final String CONTRAST_EXCEPTION_REASON_CONTACT_AND_TEL = "本次的家庭联系人号码与前次的工作证明人号码相同姓名不同，前次为";
	
	/**
	 * 申请信息历史对比异常点：居住地址
	 */
	public static final String CONTRAST_EXCEPTION_REASON_ADDRESS = "本次的居住地址与之前的不同，之前的居住地址为";	

	/**
	 * 反欺诈：触犯类型
	 */
	public static final String DICTOFFENDTYPE = "查重";
	
	/**
	 * 反欺诈：与本次借款人关系
	 */
	public static final String REPEATRELATION = "本人";
	
	/**
	 * 反欺诈规则C002
	 */
	public static final String OFFEND_C002 = "C002";

	/**
	 * 反欺诈规则内容C002
	 */
	public static final String OFFEND_MSG_C002 = "180天内，申请人重复进件（证件号码相同且姓名相同），本次进件手机号码与前次进件手机号码不同";

	/**
	 * 反欺诈规则R001
	 */
	public static final String OFFEND_R001 = "R001";
	
	/**
	 * 反欺诈规则内容R001
	 */
	public static final String OFFEND_MSG_R001 = "申请人证件号码不同，姓名相同，手机号码与系统中已有申请人手机号码相同，且大于等于1";

	/**
	 * 反欺诈规则R002
	 */
	public static final String OFFEND_R002 = "R002";

	/**
	 * 反欺诈规则内容R002
	 */
	public static final String OFFEND_MSG_R002 = "申请人证件号码不同，且姓名不同，申请人手机号码与系统中已有申请人手机号码相同，且大于等于1";

	/**
	 * 反欺诈规则内容C003
	 */
	public static final String OFFEND_C003 = "C003";

	/**
	 * 反欺诈规则内容C003
	 */
	public static final String OFFEND_MSG_C003 = "180天内，申请人重复进件（证件号码相同且姓名相同），单位名称不同，入职时间交叠";

	/**
	 * 反欺诈规则内容C004
	 */
	public static final String OFFEND_C004 = "C004";

	/**
	 * 反欺诈规则内容C004
	 */
	public static final String OFFEND_MSG_C004 = "180天内，申请人重复进件（证件号码相同且姓名相同），单位名称不同，但单址与单位固话相同";

	/**
	 * 反欺诈规则内容C005
	 */
	public static final String OFFEND_C005 = "C005";

	/**
	 * 反欺诈规则内容C005
	 */
	public static final String OFFEND_MSG_C005 = "180天内，申请人重复进件（证件号码相同且姓名相同），单位固话不同，但单位名称与单址相同";

	/**
	 * 反欺诈规则内容C006
	 */
	public static final String OFFEND_C006 = "C006";

	/**
	 * 反欺诈规则内容C006
	 */
	public static final String OFFEND_MSG_C006 = "180天内，申请人重复进件（证件号码相同且姓名相同），单址不同，但单位名称与单位固话相同";

	/**
	 * 反欺诈规则内容C011
	 */
	public static final String OFFEND_C011 = "C011";

	/**
	 * 反欺诈规则内容C011
	 */
	public static final String OFFEND_MSG_C011 = "申请人重复进件（证件号码相同且姓名相同），前一次提供的配偶证件号码与本次提供的配偶证件号码相同，配偶姓名相同，但配偶手机号码不同";

	/**
	 * 反欺诈规则内容C012
	 */
	public static final String OFFEND_C012 = "C012";

	/**
	 * 反欺诈规则内容C012
	 */
	public static final String OFFEND_MSG_C012 = "申请人重复进件（证件号码相同且姓名相同），前一次提供的配偶证件号码与本次提供的配偶证件号码不同，配偶姓名不同，但手机号码相同";

	/**
	 * 反欺诈规则内容C013
	 */
	public static final String OFFEND_C013 = "C013";

	/**
	 * 反欺诈规则内容C013
	 */
	public static final String OFFEND_MSG_C013 = "申请人重复进件（证件号码相同且姓名相同），前一次提供的配偶证件号码与本次提供的配偶证件号码不同，配偶姓名不同，且手机号码也不同";

	/**
	 * 反欺诈规则内容C026
	 */
	public static final String OFFEND_C026 = "C026";

	/**
	 * 反欺诈规则内容C026
	 */
	public static final String OFFEND_MSG_C026 = "申请人重复进件（证件号码相同且姓名相同），为老板借或企业借，前一次进件的单址与宅址相同，但本次进件的单址与宅址不同，单位名称相同";
	
	/**
	 * 反欺诈规则内容C027
	 */
	public static final String OFFEND_C027 = "C027";

	/**
	 * 反欺诈规则内容C027
	 */
	public static final String OFFEND_MSG_C027 = "申请人重复进件（证件号码相同且姓名相同），为老板借或企业借，前一次进件的单址与宅址不同，本次进件的单址与宅址相同，单位名称相同";
	
	/**
	 * 反欺诈规则内容C028
	 */
	public static final String OFFEND_C028 = "C028";

	/**
	 * 反欺诈规则内容C028
	 */
	public static final String OFFEND_MSG_C028 = "申请人重复进件（证件号码相同且姓名相同），为老板借或企业借，前一次进件的单址与宅址相同，与本次进件的单址与宅址相同，但单位名称不同";

	/**
	 * 产品名称：企业借
	 */
	public static final String PRODUCT_NAME1 = "企业借";
	
	/**
	 * 产品名称：老板借
	 */
	public static final String PRODUCT_NAME2 = "老板借";

	/**
	 * 反欺诈处理结果：加黑
	 */
	public static final String DICTCASERESULT = "加黑";

	/**
	 * 反欺诈规则内容R024
	 */
	public static final String OFFEND_R024 = "R024";
	
	/**
	 * 反欺诈规则内容R024
	 */
	public static final String OFFEND_MSG_R024 = "60天内，申请人证件号码不同，且姓名不同，但单位名称相同，单位固话相同，且大于等于4";

	/**
	 * 反欺诈规则内容R025
	 */
	public static final String OFFEND_R025 = "R025";

	/**
	 * 反欺诈规则内容R025
	 */
	public static final String OFFEND_MSG_R025 = "90天内，申请人证件号码不同，且姓名不同，但单位名称相同，单位固话不同，且大于等于3";

	/**
	 * 反欺诈规则内容R026
	 */
	public static final String OFFEND_R026 = "R026";
	
	/**
	 * 反欺诈规则内容R026
	 */
	public static final String OFFEND_MSG_R026 = "90天内，申请人证件号码不同，且姓名不同，但单位名称不同，单位固话相同，且大于等于3";

	/**
	 * 反欺诈规则内容C022
	 */
	public static final String OFFEND_C022 = "C022";

	/**
	 * 反欺诈规则内容C022
	 */
	public static final String OFFEND_MSG_C022 = "申请人重复进件（证件号码相同且姓名相同），其他联系人的手机号码与前一次进件提供的其他联系人号码不同，但姓名相同";

	/**
	 * 联系人和本人关系
	 */
	public static final String CONTACTRELATIONTWO = RelationType.OTHER_CONTACTS.getCode();

	/**
	 * 反欺诈规则内容C021
	 */
	public static final String OFFEND_C021 = "C021";

	/**
	 * 反欺诈规则内容C021
	 */
	public static final String OFFEND_MSG_C021 = "申请人重复进件（证件号码相同且姓名相同），其他联系人的手机号码与前一次进件提供的其他联系人号码相同，但姓名不同";

	/**
	 * 联系人和本人关系
	 */
	public static final String CONTACTRELATIONTHREE = RelationType.WORK_VOUCHER.getCode();

	/**
	 * 反欺诈规则内容C020
	 */
	public static final String OFFEND_C020 = "C020";

	/**
	 * 反欺诈规则内容C020
	 */
	public static final String OFFEND_MSG_C020 = "申请人重复进件（证件号码相同且姓名相同），工作证明人的手机号码与前一次进件提供的工作证明人号码不同，但姓名相同";

	/**
	 * 反欺诈规则内容C019
	 */
	public static final String OFFEND_C019 = "C019";

	/**
	 * 反欺诈规则内容C019
	 */
	public static final String OFFEND_MSG_C019 = "申请人重复进件（证件号码相同且姓名相同），工作证明人的手机号码与前一次进件提供的工作证明人号码相同，但姓名不同";

	/**
	 * 联系人和本人关系
	 */
	public static final String CONTACTRELATIONFOUR = RelationType.FAMILY_CONTACTS.getCode();

	/**
	 * 反欺诈规则内容C018
	 */
	public static final String OFFEND_C018 = "C018";

	/**
	 * 反欺诈规则内容C018
	 */
	public static final String OFFEND_MSG_C018 = "申请人重复进件（证件号码相同且姓名相同），家庭联系人的手机号码与前一次进件提供的家庭联系人号码不同，但姓名相同";

	/**
	 * 反欺诈规则内容C017
	 */
	public static final String OFFEND_C017 = "反欺诈规则内容C017";

	/**
	 * 反欺诈规则内容C017
	 */
	public static final String OFFEND_MSG_C017 = "申请人重复进件（证件号码相同且姓名相同），家庭联系人的手机号码与前一次进件提供的家庭联系人号码相同，但姓名不同";

	
	/**
	 * 内网审核_查重信息表内容01
	 */
	public static final String INNER_REPEAT01 = "主借款人手机号码";
	
	/**
	 * 内网审核_查重信息表内容02
	 */
	public static final String INNER_REPEAT02 = "手机号码";
	
	/**
	 * 内网审核_查重信息表内容03
	 */
	public static final String INNER_REPEAT03 = "家庭联系人手机号码";
	
	/**
	 * 家庭联系人---直系亲属(父母)
	 */
	public static final String INNER_REPEAT04 = FamilyRelation.PARENTS.getCode();
	
	/**
	 * 家庭联系人---直系亲属
	 */
	public static final String INNER_REPEAT06 = FamilyRelation.CHILDREN.getCode();
	
	/**
	 * 家庭联系人---直系亲属
	 */
	public static final String INNER_REPEAT07 = FamilyRelation.MATES.getCode();
	
	/**
	 * 家庭联系人---直系亲属
	 */
	public static final String INNER_REPEAT08 = FamilyRelation.OTHER_RELATION.getCode();
	
	/**
	 * 内网审核_查重信息表内容09
	 */
	public static final String INNER_REPEAT09 = "工作证明人联系号码";
	
	/**
	 * 内网审核_查重信息表内容10
	 */
	public static final String INNER_REPEAT10 = "其他联系人联系号码";
	
	/**
	 * 内网审核_查重信息表内容11
	 */
	public static final String INNER_REPEAT11 = "家庭电话";
	
	/**
	 * 内网审核_查重信息表内容12
	 */
	public static final String INNER_REPEAT12 = "单位电话";
	
	/**
	 * 内网审核_查重信息表内容13
	 */
	public static final String INNER_REPEAT13 = "家庭地址";
	
	/**
	 * 内网审核_查重信息表内容14
	 */
	public static final String INNER_REPEAT14 = "单位地址";
	
	/**
	 * 内网审核_查重信息表内容15
	 */
	public static final String INNER_REPEAT15 = "单位名称";
	
	/**
	 * 公共池标识
	 */
	public static final String DICT_CUSTOMER_MARK = "公共池";
	
	/**
	 * 第三方支付平台返回码（正常）
	 */
	public static final String PAY_STATE_INFO_OK = "0000";

	/**
	 * 是否电销：0：否 1：是
	 */
	public static final String CUSTOMERTELESALESFLAG = YESNO.YES.getCode();
	//public static final String CUSTOMERTELESALESFLAG = "1";

	/**
	 * 审核结果 ：0:拒借
	 */
	public static final Object AUDITRESULT = ChkResult.REFUSE_TO_BORROW.getCode();

	/**
	 * 派发状态：0：已派发
	 */
	public static final String DISTSTATUS = AppalyState.YPF.value;

	/**
	 * 变更类型：0：作废
	 */
	public static final String DICTCHANGETYPE1 = ContractChangeType.ZF.value;

	/**
	 * 变更类型：3：遗失
	 */
	public static final String DICTCHANGETYPE2 = ContractChangeType.YS.value;
	
	/**
	 * 审核结果：0：通过
	 */
	public static final String FLAG = ChkResult.THROUGH.getCode();
	/**
	 * 出借合同申请审核结果：0：通过
	 */
	public static final String LEN_AUDIT_PASS = ChkResult.THROUGH.getCode();
	/**
	 * 出借合同申请审核结果：1：拒绝
	 */
	public static final String LEN_AUDIT_REFUSE = ChkResult.REFUSE_TO_BORROW.getCode();
	
	/**
	 * 当期扣款（元）:20
	 */
	public static final int RMB1 = 20;
	
	/**
	 * 当期扣款（元）:100
	 */
	public static final int RMB2 = 100;
	
	/**
	 * 当期扣款（元）:100
	 */
	public static final double RMB3 = 0.05;
	
	/**
	 * 文件制作状态：待制作
	 */
	public static final String DICT_MATCHING_FILE_STATUS = FilecpState.WHC.value;
	
	/**
	 * 文件制作状态：制作成功
	 */
	public static final String DICT_MATCHING_FILE_STATUS1 = FilecpState.HCCG.value;
	
	/**
	 * 文件制作状态：制作失敗
	 */
	public static final String DICT_MATCHING_FILE_STATUS2 = FilecpState.HCSB.value;
	
	/**
	 * 文件所属表
	 */
	public static final String ATTAFILEOWNER = "待债权推荐信息表";
	
	/**
	 * zrsq：债权转让
	 */
	public static final String ZRSQ = "zrsq";
	
	/**
	 * 文件类型：pdf
	 */
	public static final String PDF = "pdf";
	
	/**
	 * 文件类型：word
	 */
	public static final String WORD = "word";
	
	/**
	 * 汇金合同名称
	 */
	public static final String LOAN_WORD_NAME1 = "借款协议（带保证人）";
	
	public static final String LOAN_WORD_NAME2 = "借款协议";
	
	public static final String LOAN_WORD_NAME3 = "还款管理服务说明确认";
	
	public static final String LOAN_WORD_NAME4 = "借款人委托扣款授权书";
	
	public static final String LOAN_WORD_NAME5 = "信用咨询及管理服务协议";
	
	public static final String LOAN_WORD_NAME55 = "信用咨询及管理服务协议（带保证人）";
	
	public static final String LOAN_WORD_NAME6 = "催收服务费收取通知书";
	
	public static final String LOAN_WORD_NAME7 = "《借款协议》终止通知书";
	
	public static final String LOAN_WORD_NAME8 = "隐私保护声明";
	
	public static final String LOAN_WORD_NAME9 = "富友-信和财富专用账户协议";
	
	
	
	public static final String LOAN_PDF_NAME10 = "借款协议（带保证人）_TG合同";
	
	public static final String LOAN_PDF_NAME11 = "借款协议 _TG合同";
	
	public static final String LOAN_PDF_NAME12 = "还款管理服务说明确认书(借款人)_TG合同";
	
	public static final String LOAN_PDF_NAME13 = "借款人委托扣款授权书_TG合同";
	
	public static final String LOAN_PDF_NAME14= "信用咨询及管理服务协议_TG合同";
	
	public static final String LOAN_PDF_NAME144= "信用咨询及管理服务协议（带保证人）_TG合同";
	
	
	public static final String LOAN_PDF_NAME15 = "免责声明（保证人）_TG合同";
	
	public static final String LOAN_PDF_NAME16= "免责声明_TG合同";
	
	
	public static final String LOAN_PDF_NAME17 = "免责声明（保证人）";
	
	public static final String LOAN_PDF_NAME18= "免责声明";
	
	
	/**
	 * 金信合同名称
	 */
	public static final String LOAN_PDF_NAME20 = "委托扣款授权书-金信";
	
	public static final String LOAN_PDF_NAME21 = "免责声明（保证人）-金信";
	
	public static final String LOAN_PDF_NAME22= "免责声明-金信";
	
	
	
	public static final String LOAN_PDF_NAME23= "信用咨询及管理服务协议-金信";
	
	public static final String LOAN_PDF_NAME24= "信用咨询及管理服务协议（保证人）-金信";
	
	
	
	
	/**
	 * CE财富登录名字
	 */
	public static final String CE_CF_BATCH_NAME = "cf10";
	/**
	 * CE财富登录密码
	 */
	// public static final String CE_CF_BATCH_PASSWORD = "admin";
	
	/**
	 * 财富邮件主题
	 */
	public static final String EMAILSUBJECT1 = "非首期债权文件";
	
	/**
	 * 待交割
	 */
	public static final String TODELIVERY = DeliveryStatus.DJG.value;
	
	/**
	 * 交割完成
	 */
	public static final String DELIVERYED = DeliveryStatus.JGWC.value;
	
	/**
	 * 没交割过
	 */
	public static final String NODELIVERYED = DeliveryStatus.WJGG.value;
	
	/**
	 * 客户交割
	 */
	public static final String CUSTOMER_DELIVERYED = DeliveryType.KHJG.value;
	
	/**
	 * 业绩交割
	 */
	public static final String ACHIEVEMENT_DELIVERYED = DeliveryType.YWJG.value;
	
	/**
	 * 保留小数点后两位
	 */
	public static final int TWO = 2;
	public static final int FIVE = 5;
	/**
	 * 汇金CE登录名
	 */
	public static final String CE_HJ_BATCH_NAME = "ce_hj_batch_user";

	/**
	 * 汇金CE登录密码
	 */
	public static final String CE_HJ_BATCH_PASSWORD = "ce_hj_batch_password";

	/**
	 * 逾期天数 30天
	 */
	public static final Integer OVERDUEDAY1 = 30;

	/**
	 * 逾期天数 90天
	 */
	public static final Integer OVERDUEDAY2 = 90;
	
	/**
	 * 中金接口批量模糊查询分页最大记录数
	 */
	public static final String ZJ_ADAPTER_MAX = "zjAdapterMax";

	/**
	 * 审批类型： 初审
	 */
	public static final String DICTCHECKTYPE = CheckType.XS_FIRST_CREDIT_AUDIT.getCode();

	/**
	 * 财富债权文件命名
	 */
	public static final String CREDITFILENAME1 = "债权转让及受让协议_";
	
	/**
	 * 财富债权文件命名
	 */
	public static final String CREDITFILENAME2 = "_SRXY";
	
	/**
	 * 财富债权文件命名
	 */
	public static final String CREDITFILENAME3 = ".pdf";
	
	/**
	 * 财富债权文件命名
	 */
	public static final String CREDITFILENAME4 = ".doc";

	/**
	 * 电催文件命名
	 */
	public static final String ELECTRICURL = ".csv";
	
	/**
	 * 电催文件命名
	 */
	public static final String ELECTRICURL1 = "逾期借款人信息";
	
	/**
	 * 电催文件命名
	 */
	public static final String ELECTRICURL2 = "逾期借款信息";
	
	/**
	 * 电催文件命名
	 */
	public static final String ELECTRICURL3 = "逾期账单信息";

	/**
	 * 派发记录类型（子）
	 */
	public static final String DISTTYPE = "2";

	/**
	 * 文件是否废弃“1”为废弃，
	 */
	public static final String ISDISCARD1 = EffectiveFlag.wx.value;
	
	/**
	 * 文件是否废弃“0”为未废弃，
	 */
	public static final String ISDISCARD0 = EffectiveFlag.yx.value;
	
	public static final String JOBGROUPNAME = "loan";
	
}
