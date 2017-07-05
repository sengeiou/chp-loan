/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.exContractConstant.java
 * @Create By 张灏
 * @Create In 2015年12月3日 下午2:05:30
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

/**
 * @Class Name ContractConstant
 * @author 张灏
 * @Create In 2015年12月3日
 */
public class ContractConstant {
     
    public static final String RATE_AUDIT="审核利率";
    
    public static final String MATE = "配偶";
    
    public static final String RATE_AUDIT_FLAG="RATE_AUDIT";
    
    public static final String CTR_CREATE_FLAG="CTR_CREATE";
    
    public static final String CTR_AUDIT_FLAG="CTR_AUDIT";
    
    public static final String CONFIRM_SIGN="确认签署";
    
    public static final String CUST_SERVICE_SIGN="合同签订";
    
    public static final String CTR_CREATE = "合同制作";
    
    public static final String CTR_AUDIT = "合同审核";
    
    public static final String[] OPERATE_STEP_TERMILATE={"客户放弃","拒绝"};
    
    public static final String[] LOAN_STATUS= {"利率审核","合同制作","合同审核"};
    
    public static final String OPERATE_STEP_GIVEUP = "客户放弃";
    
    public static final String OPERATE_STEP_REJECT = "客户放弃";
    
    public static final String[] OPERATE_STEP_RETURN = {"客户放弃"};
    
    public static final String GRANT_SURE = "放款确认";
    
    public static final String DIS_CARD = "分配卡号";
    
    public static final String GOLDCREDIT_RETURN = "金信债权退回";
    
    public static final String GRANT = "放款";
    
    public static final String GRANT_AUDIT = "放款审核";
    
    public static final String GRANT_BACK = "放款审核退回";
    
    public static final String KIND_ACCOUNT_OPEN = "金账户开户";
    
    public static final String RELATION_TYPE = "jk_relation_type";  // 关系类型选择
    
    public static final String[] SUB_RELATION_TYPE = {"jk_loan_family_relation","jk_loan_workmate_relation","jk_loan_other_relation"};
    
    public static final String BACK_FLAG = "1";                   // 退回标识
    public static final String CANCEL_BACK_FLAG = "0";            // 取消退回标识
    public static final String CONTRACT_SUBMIT = "2";             // 合同审核提交
    public static final String CONTRACT_SUBMIT_NAME = "成功";  
    public static final String CONTRACT_BACKUP = "0";             // 合同审核退回
    public static final String CONTRACT_BACKUP_NAME = "退回";
    public static final String CONTRACT_REJECT = "1";             // 合同审核拒绝
    public static final String CONTRACT_REJECT_NAME = "拒绝";
    public static final String CONTRACT_GIVEUP = "3";             // 合同审核客户放弃
    public static final String CONTRACT_GIVEUP_NAME = "客户放弃";
    public static final String REJECT_FROZEN="驳回申请";
    public static final String ADD_CHANNELFLAG = "添加${CHANNELFLAG}标识";
    public static final String CANCEL_CHANNELFLAG = "取消${CHANNELFLAG}标识";
    public static final int FIRST_DAY_OF_MONTH = 1;          // 1号
    public static final int SECOND_DAY_OF_MONTH = 2;         // 2号
    //***************费用计算常量*********************************************//
    
    public final static Integer SCALE = 8;                               // 精度
    
    public final static Double  FEEINFOSERVICE_RATE = 0.22;               // 信息服务费率
    
    public final static Double  FEESERVICE_RATE = 0.25;                  // 居间服务费率
    
    public final static Double  FEEAUDITAMOUNT_RATE = 0.01;              // 审核费率
    
    public final static Double  FEECONSULT_RATE = 0.52;                   // 咨询费率
    
    public final static Double  FEEURGEDSERVICE_RATE = 0.01;             // 催收服务费率
    
    public final static Double MONTH_MID_SERVICE_RATE = 0.5;             // 分期居间服务费
    
    public final static Double MONTH_CONSULT_RATE = 0.5;                 // 分期咨询费
    
    public final static Double DEFAULTFACTOR = 0.30;                     //违约系数
    
    public final static Integer ONE_HUNDRED = 100;                       // 一百
    
    public final static Integer TWO_HUNDRED = 200;                       // 二百
    
    public final static Integer THREE_HUNDRED = 300;                     // 三百
    
    public final static Integer FOUR_HUNDRED = 400;                      // 四百
    
    public final static Integer FIVE_HUNDRED = 500;                      // 五百
    
    public final static Integer FIFTY_KILOMETERS = 50;                   // 50公里
    
    public final static Integer ONE_HUNDRED_KILOMETERS = 100;            // 100公里
    
    public final static Integer ONE_HUNDRED_FIFTY_KILOMETERS = 150;      // 150公里
   
    public final static Integer ZERO = 0;
    
    public static final Integer SIX = 6;
    
    public static final Integer NEGTIVE_ONE = -1;
    
    public final static String ATTR_LOANFLAG = "loanFlag";               // 更改标识
    
    public final static String MONEY_FORMAT = "#,#00.00";                // 页面显示的金额格式
    
    public final static String URGE_FLAG = "1";                          // 加急标识  
    
    public final static Integer DIVIDE = 12;                             // 12个月
    
    public final static Integer ADD_FACTOR = 2;
    
    public final static Double MUTI_FACTOR = 0.007;
    
    public final static Double HUIJIN_FACTOR = 0.4;                      // 汇金费用比率
    
    public final static Double HUIMIN_FACTOR = 0.18;                     // 惠民费用比率
    
    public final static Integer TOP_FLAG = 1;                            // 置顶标识
    
    public final static String FY_PROVINCE_TYPE = "1";                   // 富友areaType 省
    
    public final static String FY_CITY_TYPE = "2";                       // 富友areaType 市
    
    public final static int PERCENT = 100;                               // 百分数
    
    public final static String APP_SIGN = "1";                            // app签字
    
    public final static String NOT_APP_SIGN = "0";                        // 非App签字
    
    public final static Float SCORE_MIN = 30.0f;                          // 公安验证分数下限
    public final static Float SCORE_MAX = 40.0f;                          // 公安验证分数上限
    public final static String ID_VALID_MESSAGE="公安验证分数为30-40之间请注意";  // 公安验证信息提示
    
    public static String POSTPONETIP_SUCCESS = "延期成功";
	
	public static String POSTPONETIP_FAILE = "延期失败";
	
	public static String POSTPONETIP_1 = "延期日期早于超时日期，请重新设置！";
	
	public static String ISSPLIT_0 = "0";                                     //联合放款标识   未拆分
			
	public static String ISSPLIT_1 = "1";                                     //联合放款标识    已拆分
	
	public static String ISSPLIT_2 = "2";                                     //联合放款标识     拆分后
	
	public static String SPLIT_ZCJ_FIX = "-1";                                //zcj合同编号后缀
	
	public static String SPLIT_JX_FIX = "-2";                                 //金信合同编号后缀
	
	public static String[] SPLIT_FIX = {"-1","-2"};
	
	public static String EFFECTIVE_0 = "0";                                     //是否有效标识  无效
	
	public static String EFFECTIVE_1 = "1";                                     //是否有效标识  有效
	
	public static String FLFLAG_0 = "0";                                     //拆分合同-费率审核  未提交
	
	public static String FLFLAG_1 = "1";                                     //拆分合同-费率审核  已提交

} 
