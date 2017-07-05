package com.creditharmony.loan.common.utils;

import java.math.BigDecimal;

/**
 *常量类
 * @Class Name ApplyInFoConstant
 * @author 张平
 * @Create In 2015年12月4日
 */
public class ApplyInfoConstant {
	
	
	//自然人保证人校验
	public static final String APPLY_INFO_PERSION = "44";

	//身份证22-55校验
	public static final String APPLY_INFO = "22-55";
	
	public static final String APPLY_INFO_CUSTOMER = "1";
	public static final String APPLY_INFO_MATE = "2";
	public static final String APPLY_INFO_LOANINFO = "3";
	public static final String APPLY_INFO_COBORROWER = "4";
	public static final String APPLY_INFO_CREDITINFO = "5";
	public static final String APPLY_INFO_COMPANY = "6";
	public static final String APPLY_INFO_HOUSE = "7";
	public static final String APPLY_INFO_CONTACT = "8";
	public static final String APPLY_INFO_BANK = "9";
	
	public static final String NEW_APPLY_INFO_CUSTOMER = "1";
	public static final String NEW_APPLY_INFO_LOANINFO = "2";
	public static final String NEW_APPLY_INFO_COMPANY = "3";
	public static final String NEW_APPLY_INFO_CONTACT = "4";
	public static final String NATURAL_GUARANTOR = "5";
	public static final String NEW_APPLY_INFO_HOUSE = "6";
	public static final String NEW_APPLY_INFO_MANAGER = "7";
	public static final String NEW_APPLY_INFO_CERTIFICATE = "8";
	public static final String NEW_APPLY_INFO_BANK = "9";

	public static final String NEW_APPLY_INFO_MATE = "10";
	public static final String NATURAL_GUARANTOR_CERT_AGE = "11";
	
	public static final String NOT_MARRIED = "0";
	public static final String MARRIED = "1";
	public static final String[] VIEWS_NAME= {"_loanFlowCustomer","_loanFlowMate","_loanFlowApplyInfo","_loanFlowCoborrower","_loanFlowCreditInfo","_loanFlowCompany","_loanFlowHouse","_loanFlowContact","_loanFlowBank"};
	public static final String[] VIEWS_NAME_NEW= {"_loanFlowCustomer_new","_loanFlowApplyInfo_new","_loanFlowCompany_new","_loanFlowContact_new","_loanFlowNaturalGuarantor_new","_loanFlowHouse_new","_loanFlowManager_new","_loanFlowCertificate_new","_loanFlowBank_new"};
	public static final long DAY_OF_YEAR = 365;
	public static final long LIMIT_OF_YEAR = 50;
	public static final long LIMIT_IN_YEAR = 22;
	public static final String MAIN_BORROWER_NEEDED = "请填写主借人信息";
	public static final String MATE_NEEDED = "主借人已婚，需填写配偶信息";
	public static final String APPLYINFO_NEEDED = "没有填写申请信息";
	public static final String PRODUCT_NEEDED = "请选择需要借款的产品";
	public static final String COMPANY_NEEDED = "请填写企业信息";
	public static final String HOUSE_NEEDED = "请填写房产信息";
	public static final String CONTACT_NEEDED = "请填写联系人信息";
	public static final String COBORROWER_NEEDED = "借款产品为农信借申请金额大于等于六万，请填写自然人保证人";
	public static final String CUSTOMER_FIFTY_YEAR_OLD_COBORROWER_NEEDED = "主借人年龄大于50岁，请填写自然人保证人";
	
	public static final String SUCCESS_MESSAGE = "所填资料合乎条件，是否确认提交";
	public static final String CONTACT_COUNT_MESSAGE = "至少填写3个联系人";
	public static final String HOUSE_COMMON_NOT_SPOUSE = "主借人为未婚状况，房屋不能是夫妻共有";
	public static final String NOT_NEED_MATE = "主借人的婚姻状况不是已婚状态， 请先删除配偶信息或更改婚姻状况";
	public static final String REPEAT_SUBMIT_MESSAGE="请求已发送，不要频繁操作";
	
	public static final String REPEAT_PERSION="申请金额大于15W，自然人保证人和配偶身份证号相同，请更换自然人保证人";
	
	public static final String COBORROWER_NEEDED_PERSION = "保证人年龄不在22周岁（含）至50周岁（含），建议更换保证人";
	
	public static final String CUSTOMER_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY = "主借人证件有效期距进件日期小于60天，请重新提供身份证件。";
	public static final String NATURALGUARANTOR_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY = "自然人保证人证件有效期距进件日期小于60天，请重新提供身份证件。";
	public static final String CORPORATEREPRESENT_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY = "法定代表人证件有效期距进件日期小于60天，请重新提供身份证件。";
	
	public static final String COBORROWER_NEEDED_COPMO = "申请金额大于30w，借款类型为老板借或小微企业借，此字段请必填";
	
	public static final Integer CONTACT_MIN_COUNT = 3;
	// 企业借
	public static final String BORROW_COMPANY = "A011";  
	// 小微企业借
	public static final String BORROW_MINI_COMPANY = "A006";
	// 老板借
	public static final String BORROW_BOSS = "A005";  
	// 楼易借
	public static final String BORROW_BUILDING = "A004";
	// 优房借
	public static final String BORROW_GREATHOUSE = "A008";
	// 农信借
	public static final String BORROW_NONGXINJIE = "A021";
	// 1表示已婚
	public static final String MARRAY_STATE = "1"; 
	// 主借人
	public static final String MAIN_BORROWER = "mainBorrower";
	// 共借人号码查重
	public static final String COBO_BORROWER = "coboBorrower";
	//自然人证件号码查重
	
	public static final String COBO_BORROWER_PERSON = "coboBorrowerPerson";
	//职业信息
	public static final String COBO_LOANFLOW_COMPANY = "loanFlowCompanyCheck";
	//法人
	public static final String COBO_EGALPERSON = "egalperson";
	
	
	public static final String COMLEGAL_MAN = "comLegalMan";
	public static final String COMLEGAL_MANNUM = "comLegalManNum";
	public static final String COMLEGAL_SMANMOBLIE = "comLegalManMoblie";
	
	public static final String COMLEGALMANCOME_MAIL = "comLegalManComEmail";
	
	
	
    // 主借人的联系电话查重
	public static final String MAIN_CONTACT = "mainContact";
    // 共借人的联系电话查重
	public static final String COBO_CONTACT = "coboContact";
	// 配偶号码查重
	public static final String MATE = "mate";
	
	public static final String MAIN_BORROWER_PHONE_MSG = "跟个人资料页中的电话号码相同";
	
	public static final String MAIN_BORROWER_CERTNUM_MSG = "跟个人资料页中的证件类型、证件号码相同";
	
	public static final String COBO_BORROWER_PHONE_MSG = "跟共同借款人页中的电话号码相同";
	
	public static final String COBO_BORROWER_CERTNUM_MSG = "跟共同借款人页中的证件类型、证件号码相同";
	
	public static final String COBO_CONTACT_PHONE_MSG = "跟共同借款人页中的联系人的电话号码相同";
	
	public static final String MAIN_CONTACT_PHONE_MSG = "跟联系人资料页中的电话号码相同";
	
	public static final String MATE_PHONE_MSG = "跟配偶资料页中的电话号码相同";
	
	public static final String MATE_CERTNUM_MSG = "跟配偶资料页中的证件类型、证件号码相同";
	
	public static final String MAIN_BORROWER_CERTNUM_MSG_NEW = "跟个人基本信息页中的证件类型、证件号码相同";
	
	public static final String CONTACT_CERTNUM_MSG_NEW = "跟联系人页中的配偶证件类型、证件号码相同";
	
	public static final String NATURAL_GUARANTOR_CERTNUM_MSG_NEW = "跟自然人保证人页中的证件类型、证件号码相同";
	
	public static final String MAIN_BORROWER_PHONE_MSG_NEW = "跟个人基本信息页中的手机号码相同";
	
	public static final String NATURAL_GUARANTOR_PHONE_MSG = "跟自然人保证人页中的手机号码相同";
	
	public static final String NATURAL_GUARANTOR_CONTACT_PHONE_MSG = "跟自然人保证人页中的联系人的手机号码相同";
	
	public static final String MAIN_CONTACT_PHONE_MSG_NEW = "跟联系人信息页中的手机号码相同";
	
	public static final String MAIN_CONTACT_MATE_PHONE_MSG_NEW = "跟联系人信息页中的配偶的手机号码相同";
	
	public static final String STORE_APPLY_FROZEN = "门店申请冻结";
	// 冻结
	public static final String FROZEN_FLAG = "1";  
	// 未冻结
    public static final String UNFROZEN_FLAG = "0"; 
    
    public static final String OTHER1 = "其它";
    
    public static final String OTHER2 = "其他";
    //申请单 版本号日期 8月20版本 1.0
    public static final String ONEEDITION = "2026-08-20 06:08:22";
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面,默认为0,0表示跳到旧版,为空或1表示跳到新版
    public static final String LOANINFO_OLDORNEW_FLAG_OLD="0";
    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面,默认为0,0表示跳到旧版,为空或1表示跳到新版
    public static final String LOANINFO_OLDORNEW_FLAG_NEW="1";

	public static final BigDecimal SIXTY_K = new BigDecimal("60000");

    public static final BigDecimal EIGHTY_K = new BigDecimal("100000");
    
    public static final BigDecimal ONE_HUNDRED_AND_FIFTY_K = new BigDecimal(150000);
    
    public static final BigDecimal THIRTY_K = new BigDecimal(300000);
    
    public static final String APPLYINFO_NEEDED_NEW = "没有填写借款意愿";
    
    public static final String COMPANY_MANAGE_NEEDED = "请填写经营信息";
    
    public static final String COMPANY_MANAGER_NEEDED = "申请金额大于30万，借款类型为老板借或小微企业借，法人保证人必须填写";
    
    public static final String COMPANY_CREDITCODE_AND_ORGCODE_NEEDED = "申请金额大于30万，信用代码和组织机构码不能同时为空";
    
    public static final String COMPANY_NEEDED_NEW = "请填写工作信息";
    
    public static final String LOAN_PERSONAL_CERTIFICATE_NEEDED = "请填写证件信息";
    
    public static final Integer CONTACT_MIN_COUNT_NEW = 4;
    public static final String CONTACT_COUNT_MESSAGE_NEW = "至少填写4个联系人";
    public static final Integer CONTACT_MIN_COUNT_NEW_ISBORROW = 3;
    public static final String CONTACT_COUNT_MESSAGE_NEW_ISBORROW = "至少填写3个联系人";
    
    public static final String BANK_NEEDED = "请填写银行卡信息";
    
    public static final String BANK_NEED_SAVE = "请保存银行卡信息";
    
    public static final String CERTIFICATE_WEDDING_TIME_AND_LICENSEISSUING_AGENCY_NOT_NEED = "主借人未婚，结婚日期和发证机构必须为空";

	public static final String BANK_ACCOUNTNAME_DIFFERENCE = "为生僻字，借款人姓名与银行户名一致，请修改";
	
	public static final String BANK_ERROR = "银行卡配置修改，请重新选择开户行。";

	public static final String BANK_ACCOUNTNAME_NO_DIFFERENCE = "借款人姓名与银行户名不一致，请修改";
	
	public static final String LOAN_CUSTOMER_CREDIT_EMPTY = "请填写主借人征信信息";
	
	public static final String NATURAL_GUARANTOR_CREDIT_EMPTY = "请填写自然人保证人征信信息";
	
	public static final String MATE_IN_UNSETTLE_CUSTOMER_DATA = "进件人的配偶不能是尚未结清的主借人，请重新更换进件人配偶";
	public static final String MATE_IN_UNSETTLE_MATE_DATA = "进件人的配偶不能是尚未结清的主借人配偶，请重新更换进件人配偶";
	public static final String MATE_IN_UNSETTLE_COBO_DATA = "进件人的配偶不能是尚未结清的共借人，请重新更换进件人配偶";
	public static final String MATE_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA = "进件人的配偶不能是尚未结清的最优自然人保证人，请重新更换进件人配偶";
	
	public static final String COBO_IN_UNSETTLE_CUSTOMER_DATA = "进件人的自然人保证人不能是尚未结清的主借人，请重新更换进件人自然人保证人";
	public static final String COBO_IN_UNSETTLE_MATE_DATA = "进件人的自然人保证人不能是尚未结清的主借人配偶，请重新更换进件人自然人保证人";
	public static final String COBO_IN_UNSETTLE_COBO_DATA = "进件人的自然人保证人不能是尚未结清的共借人，请重新更换进件人自然人保证人";
	public static final String COBO_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA = "进件人的自然人保证人不能是尚未结清的最优自然人保证人，请重新更换进件人自然人保证人";
	
	public static final String COBO_MATE_IN_UNSETTLE_CUSTOMER_DATA = "进件人的自然人保证人配偶不能是尚未结清的主借人，请重新更换进件人自然人保证人配偶";
	public static final String COBO_MATE_IN_UNSETTLE_MATE_DATA = "进件人的自然人保证人配偶不能是尚未结清的主借人配偶，请重新更换进件人自然人保证人配偶";
	public static final String COBO_MATE_IN_UNSETTLE_COBO_DATA = "进件人的自然人保证人配偶不能是尚未结清的共借人，请重新更换进件人自然人保证人配偶";
	public static final String COBO_MATE_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA = "进件人的自然人保证人配偶不能是尚未结清的最优自然人保证人，请重新更换进件人自然人保证人配偶";
	
	public static final String BUSINESS_LICENSE_IN_UNSETTLE_DATA = "此营业执照号在系统中已关联一笔未结清的借款 ，请更换营业执照号";
}
