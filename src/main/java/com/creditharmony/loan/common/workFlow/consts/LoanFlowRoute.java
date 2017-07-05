/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.constsWorkFlowConst.java
 * @Create By 王彬彬
 * @Create In 2015年12月29日 下午3:13:02
 */
package com.creditharmony.loan.common.workFlow.consts;

/**
 * 信借流程路由
 * @Class Name WorkFlowConst
 * @author 王彬彬
 * @Create In 2015年12月29日
 */
public class LoanFlowRoute {
    
    /**
     * 退回门店
     */
    public static final String STOREBACKCHECK = "STORE_BACK_CHECK";
    /**
     * 返回门店
     */
    public static final String BACKSTORE = "TO_STORE";
    /**
     * 复议返回门店
     */
    public static final String RECONSIDERBACKSTORE = "RECONSIDER_TO_STORE";
    /**
     * 退回信审终审组
     */
    public static final String FINALJUDGTEAM = "TO_GROUPCHECK";
    /**
     * 退回信审复审 
     */
    public static final String LETTERREVIEW = "TO_RECHECK";
    /**
     * 退回信审终审 
     */
    public static final String FINALJUDG = "TO_FINALCHECK";
    /**
     * 退回复议复审 
     */
    public static final String RECONSIDERREVIEW = "TO_RECONSIDER_RECHECK";
    /**
     * 退回复议终审 
     */
    public static final String RECONSIDERFINALJUDG = "TO_RECONSIDER_FINALCHECK";
    /**
     * 利率审核
     */
    public static final String RATECHECK = "TO_RATE_CHECK";
	/**
	 * 合同签订
	 */
	public static final String CONTRACTSIGN = "TO_CONTRACT_SIGN";
	/**
	 * 确认签署
	 */
	public static final String CONFIRMSIGN = "TO_CONFIRM_SIGN";
	/**
	 * 合同制作 
	 */
	public static final String CONTRACTMAKE = "TO_CONTRACT_MAKE";
	
	/**
	 * 合同审核
	 */
	public static final String CONTRACTCHECK = "TO_CONTRACT_CHECK";
	/**
	 * 结束（客户放弃）
	 */
	public static final String GIVEUP = "TO_GIVEUP";
	/**
	 * 结束（门店拒绝）
	 */
	public static final String REJECT = "TO_REJECT";
	
	/**
	 * 外访完成
	 */
	public static final String COMPLETEOUTVISIT = "TO_COMPLETE_OUTVISIT";
	/**
	 * 放款确认
	 */
	public static final String PAYCONFIRM = "TO_PAY_CONFIRM";
	/**
	 * 签约确定
	 */
	public static final String CINFIRMSIGN = "TO_CINFIRM_SIGN";

	/**
	 * 分配卡号
	 */
	public static final String DELIVERYCARD = "TO_DELIVERY_CARD";
	/**
	 * 放款明细确认
	 */
	public static final String DETAILCONFIRM = "TO_DETAIL_CONFIRM";
	/**
	 * 结束
	 */
	public static final String END = "TO_END";
	/**
	 * 放款
	 */
	public static final String PAYMENT = "TO_PAYMENT";
	/**
	 * 金信债权退回
	 */
	public static final String LIABILITIES_RETURN = "TO_LIABILITIES_RETURN";
	/**
	 * 金账户退回
	 */
	public static final String KING_OPEN = "TO_KING_OPEN";
	/**
	 * 从分配卡号发出金信退回
	 */
	public static final String GOLDCREDIT_RETURN = "TO_GOLDCREDIT_RETURN";
	
	/**
	 * 金信放款结束
	 */
	public static final String GOLDCREDIT_END = "TO_GOLDCREDIT_END";
	
	/**
	 * 从放款发出金信退回
	 */
	public static final String GOLDCREDIT_TO_CONTRACT_FROM_PAY = "TO_CONTRACT_FROM_PAY";
	/**
	 * 金信债权退回到合同审核
	 */
	public static final String GOLDCREDIT_TO_CONTRACT_CHECK = "TO_CONTRACT_CHECK";
	/**
	 * 金信债权到返款待确认
	 */
	public static final String GOLDCREDIT_TO_CONFIRM = "TO_PAY_CONFIRM";
	
	/**
	 * 大金融债权退回
	 */
	public static final String TO_ZCJ_REJECT = "TO_ZCJ_REJECT";
	 /**
	  * 字典-回退原因
	  */
	 public static final String BACKLOAN_REASON = "jk_backloan_reason";
	 /**
		 * 待上传合同 建议放弃、拒绝
		 */
		public static final String TO_PROPOSE_OUT2 = "TO_PROPOSE_OUT2";
		/**
		 * 确认签署 建议放弃
		 */
		public static final String TO_PROPOSE_OUT1 = "TO_PROPOSE_OUT1";
}
