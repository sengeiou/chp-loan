/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityOrderFiled.java
 * @Create By 张灏
 * @Create In 2016年3月14日 下午2:05:47
 */
package com.creditharmony.loan.common.entity;

import java.util.HashMap;
import java.util.Map;


/**
 * 流程排序字段
 * @Class Name OrderFiled
 * @author 张灏
 * @Create In 2016年3月14日
 */
public enum OrderFiled {
  
    
    /***************待签订上传合同******************/
  //状态码 加急  退回
  CONTRACT_UPLOAD_FIRST("63-01-01","0000","待签订上传合同"),
  //状态码 加急  非退回
  CONTRACT_UPLOAD_SECOND("63-01-00","0001","待签订上传合同"),
  //状态码 非加急  退回
  CONTRACT_UPLOAD_THIRD("63-00-01","0002","待签订上传合同"),
  //状态码 非加急  非退回
  CONTRACT_UPLOAD_FOURTH("63-00-00","0003","待签订上传合同"),

  /***************合同制作中******************/
  //状态码 加急  退回
  CONTRACT_MAKING_FIRST("62-01-01","0000","合同制作中"),
  //状态码 加急  非退回
  CONTRACT_MAKING_SECOND("62-01-00","0001","合同制作中"),
  //状态码 非加急  退回
  CONTRACT_MAKING_THIRD("62-00-01","0002","合同制作中"),
  //状态码 非加急  非退回
  CONTRACT_MAKING_FOURTH("62-00-00","0003","合同制作中"),

  /***************待确认签署******************/
  //状态码 加急  退回
  SIGN_CONFIRM_FIRST("60-01-01","0000","待确认签署"),
  //状态码 加急  非退回
  SIGN_CONFIRM_SECOND("60-01-00","0001","待确认签署"),
  //状态码 非加急  退回
  SIGN_CONFIRM_THIRD("60-00-01","0002","待确认签署"),
  //状态码 非加急  非退回
  SIGN_CONFIRM_FOURTH("60-00-00","0003","待确认签署"),

  /***************复议退回门店******************/
  //状态码 加急  退回
  RECONSIDER_BACK_STORE_FIRST("41-01-01","0000","复议退回门店"),
  //状态码 非加急  退回
  RECONSIDER_BACK_STORE_SECOND("41-00-01","0002","复议退回门店"),

  /***************退回门店******************/
  //状态码 加急  退回
  BACK_STORE_FIRST("21-01-01","0000","退回门店"),
  //状态码 非加急  退回
  BACK_STORE_SECOND("21-00-01","0002","退回门店"),

  /***************进件引擎退回******************/
  //状态码 加急  退回
  APPLY_ENGINE_BACK_FIRST("82-01-01","0000","进件引擎退回"),
  //状态码 非加急  退回
  APPLY_ENGINE_BACK_SECOND("82-00-01","0002","进件引擎退回"),

  /***************待门店复核******************/
  // 状态码 加急 非退回
  STORE_REVERIFY_FIRST("1-01-00","0001","待门店复核"),
  // 状态码 非加急 非退回
  STORE_REVERIFY_SECOND("1-00-00","0003","待门店复核"),

  /***************待上传资料******************/
  //状态码 加急 非退回
  INFORMATION_UPLOAD_FIRST("0-01-00","0001","待上传资料"),
  //状态码 非加急 非退回
  INFORMATION_UPLOAD_SECOND("0-00-00","0003","待上传资料"), 
    
  /*************************合同审核页面排序*****************************/
	//状态码 加急 冻结 退回
	BIGFINAN_REFUSE_ONE("104-01-01-01","0008","大金融拒绝"),
	// 状态码  加急 冻结 退回
	PAYMENT_BACK_FIRST("75-01-01-01","0010", "放款确认退回"),  
	// 状态码  加急 冻结 退回
	GOLDCREDIT_RETURN_FIRST("95-01-01-01","0012","金信退回"),  
	//状态码 加急 冻结 退回
	BIGFINANCE_BACK_ONE("105-01-01-01","0014","大金融退回"),
	// 状态码  加急 冻结 退回
	CONTRACT_AUDIFY_FIRST("64-01-01-01","0016", "待审核合同"),           
	//状态码  加急 冻结 非退回
	CONTRACT_AUDIFY_SECOND("64-01-01-00","0018", "待审核合同"), 
	// 状态码  加急 冻结 退回
	LOAN_SEND_RETURN_FIRST("72-01-01-01","0020","放款退回"),
	//状态码 加急 非冻结 退回
	BIGFINAN_REFUSE_TWO("104-01-00-01","0022","大金融拒绝"),
	// 状态码  加急 非冻结 退回
	PAYMENT_BACK_SECOND("75-01-00-01","0024", "放款确认退回"), 
	// 状态码  加急 非冻结 退回
	GOLDCREDIT_RETURN_SECOND("95-01-00-01","0026","金信退回"),
	//状态码 加急 非冻结 退回
	BIGFINANCE_BACK_TWO("105-01-00-01","0028","大金融退回"),
	// 状态码  加急  非冻结  退回
	CONTRACT_AUDIFY_THIRD("64-01-00-01","0030","待审核合同"),
	// 状态码  加急  非冻结 退回
	LOAN_SEND_RETURN_SECOND("72-01-00-01","0032","放款退回"),
	// 状态码  加急  非冻结 非退回
	CONTRACT_AUDIFY_FOURTH("64-01-00-00","0034","待审核合同"),
	
	//状态码 非加急 冻结 退回
	BIGFINAN_REFUSE_THR("104-00-01-01","0036","大金融拒绝"),
	// 状态码  非加急  冻结 退回
	PAYMENT_BACK_THIRD("75-00-01-01","0038", "放款确认退回"),
	// 状态码  非加急  冻结 退回
	GOLDCREDIT_RETURN_THIRD("95-00-01-01","0040","金信退回"),
	//状态码 非加急 冻结 退回
	BIGFINANCE_BACK_THR("105-00-01-01","0042","大金融退回"),
	// 状态码  非加急 冻结 退回
	CONTRACT_AUDIFY_FIFTH("64-00-01-01","0044", "待审核合同"),           
	// 状态码  非加急 冻结  非退回
	CONTRACT_AUDIFY_SIXTH("64-00-01-00","0046", "待审核合同"),
	// 状态码  非加急 冻结 退回
	LOAN_SEND_RETURN_THIRD("72-00-01-01","0048","放款退回"),
	//状态码 非加急 非冻结 退回
	BIGFINAN_REFUSE_FOU("104-00-00-01","0050","大金融拒绝"),
	// 状态码 非加急  非冻结  退回
	PAYMENT_BACK_FOURTH("75-00-00-01","0052", "放款确认退回"), 
	// 状态码  非加急 非冻结 退回
	GOLDCREDIT_RETURN_FOURTH("95-00-00-01","0054","金信退回"),
	//状态码 非加急 非冻结 退回
	BIGFINANCE_BACK_FOU("105-00-00-01","0056","大金融退回"),	
	// 状态码  非加急 非冻结  退回
	CONTRACT_AUDIFY_SEVENTH("64-00-00-01","0058","待审核合同"),
	// 状态码 非加急 非冻结 退回
	LOAN_SEND_RETURN_FOURTH("72-00-00-01","0060","放款退回"),
	// 状态码  非加急  非冻结 非退回
	CONTRACT_AUDIFY_EIGHTH("64-00-00-00","0062","待审核合同"),
	
  /*************************合同审核页面排序*****************************/
   
    
/**********************合同制作排序编码***************************/
	   // 状态码 加急 退回 不复议
    CONTRACT_MAKE_FIRST("61-01-01-00","0070","待制作合同"),
    // 状态码 加急 退回 复议
    CONTRACT_MAKE_FIRST1("61-01-01-01","0073","待制作合同"),
    // 状态码 加急 非退回 复议
    CONTRACT_MAKE_SECOND("61-01-00-01","0075","待制作合同"),
    // 状态码 加急 非退回 不复议
    CONTRACT_MAKE_SECOND1("61-01-00-00","0076","待制作合同"),
    // 状态码  非加急 退回 不复议
    CONTRACT_MAKE_THIRD("61-00-01-00","0079","待制作合同"),
    // 状态码  非加急 退回 复议
    CONTRACT_MAKE_THIRD1("61-00-01-01","0082","待制作合同"),
    // 状态码   非加急 非退回 复议
    CONTRACT_MAKE_FOURTH("61-00-00-01","0084","待制作合同"),
    // 状态码   非加急 非退回 不复议
    CONTRACT_MAKE_FOURTH1("61-00-00-00","0085","待制作合同"),
    
/**********************利率审核排序编码***************************/   
    // 状态码  第一单 
    RATE_TO_VERIFY_FIRST("58-01","0078","待审核利率"),

    
    // 状态码 加急 退回  非第一单 不复议
    RATE_TO_VERIFY_FIFTH("58-01-01-00-00","0084","待审核利率"),
    // 状态码 加急 退回  非第一单 复议
    RATE_TO_VERIFY_FIFTH1("58-01-01-00-01","0086","待审核利率"),
    // 状态码 加急 非退回  非第一单  复议
    RATE_TO_VERIFY_SIXTH1("58-01-00-00-01","0087","待审核利率"),
    // 状态码 加急 非退回  非第一单  不复议
    RATE_TO_VERIFY_SIXTH("58-01-00-00-00","0088","待审核利率"),
    // 状态码 非加急 退回  非第一单  不复议
    RATE_TO_VERIFY_SEVENTH("58-00-01-00-00","0090","待审核利率"),
    // 状态码 非加急 退回  非第一单  复议
    RATE_TO_VERIFY_SEVENTH1("58-00-01-00-01","0092","待审核利率"),
    // 状态码 非加急 非退回  非第一单  复议
    RATE_TO_VERIFY_SEVENTH2("58-00-00-00-01","0093","待审核利率"),
    // 状态码 非加急 非退回  非第一单  不复议
    RATE_TO_VERIFY_EIGHTH("58-00-00-00-00","0094","待审核利率"),
  
    
/**********************放款明细确认排序编码（金信）***************************/      
  /*  // 状态码 加急 冻结  退回
    LOAN_SEND_CONFIRM_FIRST("65-01-01-01","0090","待放款确认"),
    // 状态码 加急 冻结  非退回
    LOAN_SEND_CONFIRM_SECOND("65-01-01-00","0091","待放款确认"),
    // 状态码 加急 非冻结  退回
    LOAN_SEND_CONFIRM_THIRD("65-01-00-01","0093","待放款确认"),
    // 状态码 加急 非冻结  非退回
    LOAN_SEND_CONFIRM_SIXTHS("65-01-00-00","0094","待放款确认"),
    // 状态码 非加急 冻结 退回
    LOAN_SEND_CONFIRM_FOURTH("65-00-01-01","0095","待放款确认"),
    // 状态码  非加急  冻结  非退回
    LOAN_SEND_CONFIRM_FIFTH("65-00-01-00","0096","待放款确认"),
    // 状态码  非加急  非冻结 非退回
    LOAN_SEND_CONFIRM_SIXTH("65-00-00-01","0097","待放款确认"),
    // 状态码  非加急  非冻结 非退回
    LOAN_SEND_CONFIRM_SENVEN("65-00-00-00","1097","待放款确认"),*/
    
 /***************************信借待放款确认列表************************/   
    // 状态码 加急 冻结 退回   金信拒绝（门店申请冻结） 状态-加急-门店申请冻结-退回标识
    GOLDCREDIT_REJECT_FIRST("94-01-01-01","0098","金信拒绝"),
    // 状态码 加急 冻结 退回   待放款确认（门店申请冻结）
    LOAN_SEND_CONFIRM_SEVENTH("65-01-01-01","0099","待放款确认"),
    // 金信取消标识 加急 冻结
    LOAN_SEND_CONFIRM_JINGXIN_FROZEN("65-11-01-00","0100","待放款确认"),
    // 状态码 加急 冻结 非退回 
    LOAN_SEND_CONFIRM_EIGHT("65-01-01-00","0101","待放款确认"),
    // 金信债权到款项确认 加急 非冻结
    //LOAN_REUTRN_CONFIRM__FROZEN("65-11-00-00","0102","待放款确认"),
    // 状态码 加急 非冻结 退回
    GOLDCREDIT_REJECT_SECOND("94-01-00-01","0103","金信拒绝"),
    // 状态码 加急 非冻结 退回
    LOAN_SEND_CONFIRM_NINTH("65-01-00-01","0104","待放款确认"),
    // 金信取消标识 加急 非冻结
    LOAN_SEND_CONFIRM_JINGXIN_NOFROZEN("65-11-00-00","0105","待放款确认"),
    // 金信债权到款项确认 加急 非冻结
   // LOAN_REUTRN_CONFIRM_NOFROZEN("65-21-00-00","0106","待放款确认"),
    // 状态码 加急 非冻结 非退回
    LOAN_SEND_CONFIRM_TEENTH("65-01-00-00","0107","待放款确认"),
    // 状态码 非加急 冻结 退回  状态-加急-门店申请冻结-退回标识
    GOLDCREDIT_REJECT_THIRD("94-00-01-01","0108","金信拒绝"),
    // 状态码 非加急 冻结 退回
    LOAN_SEND_CONFIRM_ELEVENTH("65-00-01-01","0109","待放款确认"),
 // 金信取消标识 非加急 冻结
    LOAN_SEND_CONFIRM_NO_NOFROZEN("65-10-01-00","0110","待放款确认"),
    // 状态码 非加急 冻结 非退回
    LOAN_SEND_CONFIRM_TWELFTH("65-00-01-00","0112","待放款确认"),
    // 状态码 非加急 非冻结 退回
    LOAN_SEND_CONFIRM_TEN("94-00-00-01","0113","金信拒绝"),
    // 状态码 非加急 非冻结 退回
    LOAN_SEND_CONFIRM_ELV("65-00-00-01","0114","待放款确认"),
 // 金信债权到款项确认 非加急 非冻结
    LOAN_REUTRN_NO_CONFIRM__NOFROZEN("65-10-00-00","0115","待放款确认"),
    // 状态码 非加急 非冻结  非退回
    GOLDCREDIT_REJECT_FOURTH("65-00-00-00","0117","待放款确认"),
    
    
    /***********************大金融待款项确认*****************************/
    //状态码 加急 冻结 退回 大金融
    BIGFINAN_CONFIRM_1("65-01-01-01-05","0100","待放款确认"),
    //状态码 加急 冻结 非退回 大金融
    BIGFINAN_CONFIRM_2("65-01-01-00-05","0103","待放款确认"),
    //状态码 加急 非冻结 退回 大金融
    BIGFINAN_CONFIRM_3("65-01-00-01-05","0106","待放款确认"),
    //状态码 加急 非冻结 非退回 大金融
    BIGFINAN_CONFIRM_4("65-01-00-00-05","0109","待放款确认"),
    //状态码 非加急 冻结 退回 大金融
    BIGFINAN_CONFIRM_5("65-00-01-01-05","0112","待放款确认"),
    //状态码 非加急 冻结 非退回 大金融
    BIGFINAN_CONFIRM_6("65-00-01-00-05","0115","待放款确认"),
    //状态码 非加急 非冻结 退回 大金融
    BIGFINAN_CONFIRM_7("65-00-00-01-05","0118","待放款确认"),
    //状态码 非加急 非冻结 非退回 大金融
    BIGFINAN_CONFIRM_8("65-00-00-00-05","0121","待放款确认"),
    
    /***********************大金融待款项确认*****************************/
    
/*********************金信债权退回(初审拒绝)**************************/
    /**金信债权退回(初审拒绝) 状态码 加急 冻结 */
    GOLDCREDIT_RETURN_2_FIRST("99-01-01-01","0125","初审拒绝"),
    /**金信债权退回(初审拒绝) 状态码 加急 非冻结 */
    GOLDCREDIT_RETURN_2_SECOND("99-01-00-01","0127","初审拒绝"),
    /**金信债权退回(初审拒绝) 状态码 非加急 冻结*/
    GOLDCREDIT_RETURN_2_THIRD("99-00-01-01","0129","初审拒绝"),
    /**金信债权退回(初审拒绝) 状态码  非加急  非冻结 */
    GOLDCREDIT_RETURN_2_FOURTH("99-00-00-01","0131","初审拒绝"),
    

/*********************金信债权退回(复审拒绝)**************************/
    /**金信债权退回(复审拒绝) 状态码 加急 冻结 */
    GOLDCREDIT_RETURN_3_FIRST("100-01-01-01","0126","复审拒绝"),
    /**金信债权退回(复审拒绝) 状态码 加急 非冻结  */
    GOLDCREDIT_RETURN_3_SECOND("100-01-00-01","0128","复审拒绝"),
    /**金信债权退回(复审拒绝) 状态码 非加急 冻结*/
    GOLDCREDIT_RETURN_3_THIRD("100-00-01-01","0130","复审拒绝"),
    /**金信债权退回(复审拒绝) 状态码  非加急  非冻结 */
    GOLDCREDIT_RETURN_3_FOURTH("100-00-00-01","0132","复审拒绝"),

/*********************金信债权退回节点(大金融拒绝)**************************/
    /**大金融债权退回(大金融拒绝) 状态码 加急 冻结  非退回 */
    BIGFINAN_RETURN_3_FIRST("104-01-01-00","0186","大金融拒绝"),
    /**大金融债权退回(大金融拒绝) 状态码 加急 非冻结   非退回*/
    BIGFINAN_RETURN_3_SECOND("104-01-00-00","0187","大金融拒绝"),
    /**大金融债权退回(大金融拒绝) 状态码 非加急 冻结  非退回*/
    BIGFINAN_RETURN_3_THIRD("104-00-01-00","0188","大金融拒绝"),
    /**大金融债权退回(大金融拒绝) 状态码  非加急  非冻结  非退回*/
    BIGFINAN_RETURN_3_FOURTH("104-00-00-00","0189","大金融拒绝"),     
    
/*********************待放款列表**************************/
    // 状态码 加急 冻结  退回
    LOAN_PENDING_CONFIRM_FIRST("67-01-01-01","0143","待放款确认"),
    // 状态码 加急 冻结  非退回
    LOAN_PENDING_CONFIRM_SECOND("67-01-01-00","0144","待放款确认"),
    // 状态码 加急 非冻结 退回
    LOAN_PENDING_CONFIRM_THIRD("67-01-00-01","0145","待放款确认"),
    // 状态码 加急 非冻结  非退回
    LOAN_PENDING_CONFIRM_FOURTH("67-01-00-00","0146","待放款确认"),
    // 状态码  非加急  冻结  退回
    LOAN_PENDING_CONFIRM_FIFTH("67-00-01-01","0147","待放款确认"),
    // 状态码  非加急  冻结 非退回
    LOAN_PENDING_CONFIRM_SIXTH("67-00-01-00","0148","待放款确认"),
    // 状态码  非加急  非冻结  退回
    LOAN_PENDING_CONFIRM_SEVEN("67-00-00-01","0149","待放款确认"),
    // 状态码  非加急  非冻结 非退回
    LOAN_PENDING_CONFIRM_EIGHT("67-00-00-00","0150","待放款确认"),
/*********************金信分配卡号列表**************************/
    // 状态码 加急 冻结  退回
    LOAN_DISCARD_FIRST("66-01-01-01","0161","金信分配卡号"),
    // 状态码 加急 冻结  非退回
    LOAN_DISCARD_SECOND("66-01-01-00","0162","金信分配卡号"),
    // 状态码 加急 非冻结 退回
    LOAN_DISCARD_THIRD("66-01-00-01","0163","金信分配卡号"),
    // 状态码 加急 非冻结  非退回
    LOAN_DISCARD_FOURTH("66-01-00-00","0164","金信分配卡号"),
    // 状态码  非加急  冻结  退回
    LOAN_DISCARD_FIFTH("66-00-01-01","0165","金信分配卡号"),
    // 状态码  非加急  冻结 非退回
    LOAN_DISCARD_SIXTH("66-00-01-00","0166","金信分配卡号"),
    // 状态码  非加急  非冻结  退回
    LOAN_DISCARD_SEVEN("66-00-00-01","0167","金信分配卡号"),
    // 状态码  非加急  非冻结 非退回
    LOAN_DISCARD_EIGHT("66-00-00-00","0168","金信分配卡号");
    
   /* // 状态码 加急 冻结
    LOAN_DISCARD_FIRST("66-01-01-00","0131","金信分配卡号"),
    // 状态码 非加急 冻结 
    LOAN_DISCARD_SECOND("66-00-01-00","0132","金信分配卡号"),
    // 状态码 非加急 非冻结 
    LOAN_DISCARD_THIRD("66-00-00-00","0133","金信分配卡号");*/
  
    /*********************大金融债权退回到合同审核**************************/
   // LOAN_BIGFINANCE_ONE("105-00-00-01","0173","大金融退回"),
   // LOAN_BIGFINANCE_TWO("105-00-01-01","0172","大金融退回"),
   // LOAN_BIGFINANCE_THR("105-01-00-01","0171","大金融退回"),
   // LOAN_BIGFINANCE_FOU("105-01-01-01","0170","大金融退回");
  
    private static Map<String,OrderFiled> codeMap = new HashMap<String, OrderFiled>(
            100);
    static {
        OrderFiled[] allValues = OrderFiled.values();
        for (OrderFiled obj : allValues) {
            codeMap.put(obj.getCode(), obj);
           }
    }

    private String name;
    private String code;
    private String orderId;

    private OrderFiled(String code,String orderId, String name) {
        this.name = name;
        this.orderId = orderId;
        this.code = code;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static OrderFiled parseByCode(String code) {
        return codeMap.get(code);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
