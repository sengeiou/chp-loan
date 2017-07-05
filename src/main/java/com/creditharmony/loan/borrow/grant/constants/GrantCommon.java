package com.creditharmony.loan.borrow.grant.constants;





/**
 * 放款共用的常量
 * @Class Name GrantCommon
 * @author 朱静越
 * @Create In 2015年12月10日
 */
public class GrantCommon {
     
    public static final String GRANT_DETAIL_SURE="GRANT_DETAIL_SURE";
    
    public static final String DIS_CARD="DIS_CARD";
    
    public static final String GRANT="GRANT";
    
    public static final String GRANT_SURE="GRANT_SURE";
    
    public static final String EXPORT_ZJ = "ZJ";  // 中金导出标识
    
    public static final String EXPORT_TL = "TL";  // 通联导出标识
    
    public static final String TL_FIRST = "TL01" ; // 通联模板类型1
   
    public static final String TL_SECOND = "TL02" ; // 通联模板类型2
    
    public static final String ACCOUNT_TYPE_ENTERPRISE = "企业";
    
    public static final String ACCOUNT_TYPE_PERSON = "个人";
    
    public static final String FALSE="失败";
    
    public static final String SUCCESS="成功";
    
    public static final String GRANT_OVER="待还款";
    
    public static final String GRANT_DONE="已放款";
    
    public static final String ZJ_WAY="中金放款";
    
    public static final String YH_WAY="银行放款";
    
    public static final String GRANT_SURE_NAME="放款确认";
    
    public static final String DIS_CARD_NAME = "分配卡号";
    
    public static final String DIS_CARD_BACK = "分配卡号退回";
    
    public static final String GRANT_NAME = "放款";
    
    public static final String BIG_FIN_GRANT = "大金融放款";
    
    public static final String GRANT_AUDIT_NAME = "放款明细确认";
    
    public static final String GRANT_BACK="放款退回";
    
    public static final String GRANT_AUDIT_BACK="放款失败";
    
    public static final String GRANT_AUDIT = "放款中";
    
    public static final String UPDATE_HANG ="修改放款行";
    
    public static final String SEND_DEDUCT ="发送划扣";
    
    public static final String BA_YAN_ZHUO = "巴彦淖尔盟";
    
    public static final String WU_LAN_CHA = "乌兰察布盟";
    
    public static final String URGE_DEAL = "deal";
    
    public static final String CHECK_DEAL ="checkDeal";
    
    public static final int SERVEN=7;
    
    public static final int SIX=6;
    
    public static final int FIVE=5;
    
    public static final int ONE=-1;
    
    public static final String PASS="通过";
    
    public static final String NOT_PASS="退回";
    
    public static final String TEMPLATE_ERROR = "导入的数据格式有误";
    
    public static final String BASELETTER = "信借";
    
    public static final String BASEURGENTFLAG = "加急";
    
    public static final int BASEINDEX = 1;
    // 调用流程的saveData方法
    public static final String METHOD_TYPE_SAVEDATE = "0";
    // 分配卡号的时候使用的，进行放款途径的确认,中金平台
    public static final String ZHONG_JING = "中金放款";
    // 通联平台
    public static final String TONG_LIAN = "通联放款";
    // 自动划扣标识
    public static final String SYS_AUTO_DEDUCTS = "SYS_AUTO_DEDUCTS";
    // 简易借的自动划扣标识
    public static final String JYJ_SYS_AUTO_DEDUCTS = "JYJ_SYS_AUTO_DEDUCTS";
    
    public static final String CAR_AUTO_DEDUCTS = "carFeeSet";
    // 划扣平台
    public static final String SYS_DEDUCTS_PLATFORM = "DEDUCTS_PLATFORM";
    // 简易借的自动划扣平台
    public static final String JYJ_DEDUCTS_PLATFORM = "JYJ_DEDUCTS_PLATFORM";
    // 自动匹配标识
    public static final String SYS_AUTO_MATCHINGS = "SYS_AUTO_MATCHINGS";
    // 判断金额的正则表达
    public static final String PATTERN_STRING = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
    // 注册失败 
    public static final String REGISTER_FAILED = "保证人注册失败";
    // CA签章失败 
    public static final String SIGN_FAILED = "CA签章失败 ";
    //企业工商注册号
    public static final String COMPANY_REGISTER_NO = "企业工商注册号";
    
    public static final String XIA_JING = "夏靖";
    
    public static final String REVISIT_STATUS_SUCCESS = "成功";
    
    public static final String REVISIT_STATUS_FAIL = "失败";
    
    public static final String REVISIT_STATUS_WAIT = "待回访";
    
    public static final String REVISIT_STATUS_SUCCESS_CODE = "1";
    
    public static final String REVISIT_STATUS_FAIL_CODE = "-1";
    
    public static final String REVISIT_STATUS_WAIT_CODE = "0";
    
    public static final String REVISIT_STATUS_NULL_CODE = "kong";
    
    //0：放款前   1：冻结/信息变更
    public static final String GRANTCALL_CUSTOMER_STATUS_MODIFY = "1";
    
    public static final String GRANTCALL_CUSTOMER_STATUS_GRANTBEFORE = "0";
    
    public static final String REVISIT_STATUS_INFO_TIP = "不允许上传待回访的数据,姓名和合同编号如下:";
    
    public static final String DEDUCT_BANCK_FAIL = "蓝补金额小于催收服务费金额，无法冲抵";
    
    public static final String ISSPLIT_DATA_CODE = "0";
    public static final String ISSPLIT_DATA_MESSAGE = "不允许退回已经拆分的数据,姓名和合同编号如下:";
    
    // 简易借的催收的类型
    public static final String JYJ_URGE_TYPE = "1";
    // 催收服务费的类型
    public static final String CS_UEGE_TYPE = "0";
}
