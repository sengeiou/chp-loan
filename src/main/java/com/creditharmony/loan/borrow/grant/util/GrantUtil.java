package com.creditharmony.loan.borrow.grant.util;

import org.apache.commons.lang.StringUtils;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.car.carGrant.ex.CarDeductCostRecoverEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.utils.EncryptTableCol;
import com.creditharmony.loan.utils.InnerBean;
import com.creditharmony.loan.utils.PhoneSecretSerivice;

/**
 * 放款工具类
 * 抽取controller和service中公共的方法
 * @Class Name GrantUtil
 * @author 张永生
 * @Create In 2016年4月25日
 */
public class GrantUtil {
	
	private final static String nullString = "null";

	/**
	 * 设置门店ID
	 * 2016年4月25日
	 * By 张永生
	 * @param urgeServicesMoneyEx
	 */
	public static void setStoreId(UrgeServicesMoneyEx urgeServicesMoneyEx) {
		if (ObjectHelper.isEmpty(urgeServicesMoneyEx.getStoreId()) || urgeServicesMoneyEx.getStoreId()[0].equals("")) {	
			urgeServicesMoneyEx.setStoreId(null);
		}
		if (ObjectHelper.isEmpty(urgeServicesMoneyEx.getLoanFlagId()) || urgeServicesMoneyEx.getLoanFlagId()[0].equals("")) {	
			urgeServicesMoneyEx.setLoanFlagId(null);
		}
	}
	
	
	/**
	 * 设置门店机构Id
	 * 2016年4月25日
	 * By 张永生
	 * @param carDeductCostRecoverEx 车借   
	 */
	public static void setStoreIdCar(CarDeductCostRecoverEx carDeductCostRecoverEx) {
		if (ObjectHelper.isEmpty(carDeductCostRecoverEx.getStoreId()) || carDeductCostRecoverEx.getStoreId()[0].equals("")) {	
			carDeductCostRecoverEx.setStoreId(null);
		}
	}
	
	/**
	 * 设置门店机构Id
	 * 2016年4月25日
	 * By 张永生
	 * @param loanGrantEx
	 */
	public static void setStoreOrgId(LoanGrantEx loanGrantEx) {
		if (ObjectHelper.isEmpty(loanGrantEx.getStoreOrgId()) || loanGrantEx.getStoreOrgId()[0].equals("")) {	
			loanGrantEx.setStoreOrgId(null);
		}
	}
	
	/**
	 * 设置门店机构Id
	 * 2016年4月25日
	 * By 张永生
	 * @param loanGrantEx
	 */
	public static void setStoreOrgIdQuery(LoanFlowQueryParam loanFlowQueryParam) {
		if (ObjectHelper.isEmpty(loanFlowQueryParam.getStoreOrgIds()) || loanFlowQueryParam.getStoreOrgIds()[0].equals("")) {	
			loanFlowQueryParam.setStoreOrgIds(null);
		}
		if (ObjectHelper.isEmpty(loanFlowQueryParam.getRevisitStatus()) || loanFlowQueryParam.getRevisitStatus()[0].equals("")) {	
			loanFlowQueryParam.setRevisitStatus(null);
		}
		if (ObjectHelper.isEmpty(loanFlowQueryParam.getDepositBanks()) || loanFlowQueryParam.getDepositBanks()[0].equals("")) {	
			loanFlowQueryParam.setDepositBanks(null);
		}
		if (ObjectHelper.isEmpty(loanFlowQueryParam.getCautionerDepositBanks()) || loanFlowQueryParam.getCautionerDepositBanks()[0].equals("")) {	
			loanFlowQueryParam.setCautionerDepositBanks(null);
		}
	}
	
	/**
	 * 是否放款成功
	 * 2016年4月25日
	 * By 张永生
	 * @param returnCode
	 * @return
	 */
	public static boolean isLentMoneySuccess(String returnCode) {
		return returnCode.indexOf(ResultConstants.SUCCESS_DESC) > -1
				|| returnCode.equals(ResultConstants.SUCCESS_CODE);
	}
	
	/**
	 * loan_customer的手机号解密
	 * 2016年12月10日
	 * By 朱静越
	 * @param sendNum
	 * @return
	 */
	public static String getNum(String sendNum){
		InnerBean innerBean = new InnerBean();
		innerBean.setTableName(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getTable());
		innerBean.setCol(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getCol());
		innerBean.setMobileNums(sendNum);
		String customerPhoneFirst = PhoneSecretSerivice.disDecryptStatic(innerBean);
		if (StringUtils.isEmpty(customerPhoneFirst)||nullString.equals(customerPhoneFirst)) {
			customerPhoneFirst = sendNum;
		}
		return customerPhoneFirst;
	}
}
