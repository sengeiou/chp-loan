/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entity.exFlowStep.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午6:03:39
 */
package com.creditharmony.loan.common.entity.ex;

import java.util.HashMap;
import java.util.Map;


/**
 * @Class Name FlowStep
 * @author 张灏
 * @Create In 2016年3月15日
 */
public enum FlowStep {
    
    LANUCH_RE("0","发起复议"),
  
    FILE_UPLOAD("1","资料上传"),
    
    STORE_RECHECK("2","门店复核"),
    
    RECONSIDER_BACK("3","复议退回"),
    
    AUDIT_RATE("4","审核利率"),
    
    CONFIRM_SIGN("5","确认签署"),
    
    CONTRACT_CREATE("6","合同制作"),
    
    CONTRACT_SIGN("7","合同签订"),
    
    CONTRACT_AUDIT_CONTRACT("8","合同审核"),
    
    LOAN_CONFIRM("9","放款确认"),
    
    DISCARD("10","分配卡号"),
    
    LOAN("11","放款"),
    
    LOAN_AUDIT("12","放款明细确认"),
    
    LOAN_PROCESSED("13","信借已办"),
    
    TRANSLATE_APPLY("14","交割申请"),
    
    TRANSLATE_WORKITEM("15","交割待办"),
    
    SECRET_SHOOTING("16","暗访"),
    
    OUT_VISIT("17","外访"), 
    
    LAGE_AMOUNT_VIEW("18","大额查看"),
    
    PROTOCOL_VIEW("19","协议查看"),
    
    GOLDCREDIT_RETURN("20","金信债权退回"),
   
    IMAGE_VIEW("21","影像查看"),
    
    TEAM_MANAGER_IMAGE_VIEW("22","团队经理影像查看");
    
    
    private static Map<String,FlowStep> nameMap = new HashMap<String, FlowStep>(
            100);
    private static Map<String,FlowStep> codeMap = new HashMap<String, FlowStep>(
            100);

    static {
        FlowStep[] allValues = FlowStep.values();
        for (FlowStep obj : allValues) {
            nameMap.put(obj.getName(), obj);
            codeMap.put(obj.getCode(), obj);
         }
    }
    private String name;
    private String code;
    
    private FlowStep(String code,String name){
        this.code = code;
        this.name = name;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the String name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the String code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    public static FlowStep parseByName(String name) {
        return nameMap.get(name);
    }

    public static FlowStep parseByCode(String code) {
        return codeMap.get(code);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
