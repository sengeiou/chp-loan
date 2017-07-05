package com.creditharmony.loan.borrow.grant.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByFreezeInBean;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByResultInBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByFreezeOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultDetailOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;

public class GrantCallUtil {

	private static Logger logger = LoggerFactory.getLogger(ExportEntrustReflectHelper.class);
	/**
	 * 给呼叫中心推送数据
	 * @author huowenlong
	 * @param rInBean
	 */
	public static  void grantToCallUpdateRevisitStatus(String contractCode,String customerStatus) {
		// 给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
		try {
			CshLoanConfirmByResultOutBean routBean = getRevisitStatusInfo(contractCode);
			// 判断回访的状态和回访的次数
			List<CshLoanConfirmByResultDetailOutBean> outList = routBean.getItems();
			if (outList != null && outList.size() > 0) {
				CshLoanConfirmByResultDetailOutBean loanConfirm = outList.get(0);
				if (GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(loanConfirm.getRevisitStatus()) || (GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(loanConfirm.getRevisitStatus()) && loanConfirm.getRevisitNumber() < 3)) {
					CshLoanConfirmByFreezeInBean uInBean = new CshLoanConfirmByFreezeInBean();
					// 合同号
					uInBean.setContractCode(contractCode);
					// 客户状态
					uInBean.setCustomerStatus(customerStatus);
					ClientPoxy uService = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_FREEZE);
					CshLoanConfirmByFreezeOutBean resultOutBean = (CshLoanConfirmByFreezeOutBean) uService.callService(uInBean);
					if(resultOutBean != null){
						logger.error("给回访中心推送数据，返回状态：" + resultOutBean.getRetCode() + " " + resultOutBean.getRetMsg());
					}
				}
			}else{
				logger.error("给回访中心推送数据时，未查询到回访中心的数据，contractCode：" + contractCode);
			}
		} catch (Exception e) {
			logger.error("给回访中心推送数据时失败 ，合同号：" + contractCode);
		}
	}
	
	/**
	 * 根据合同编号，查询回访信息
	 * @param contractCode
	 * @return
	 */
	public static CshLoanConfirmByResultOutBean getRevisitStatusInfo(String contractCode) {
		CshLoanConfirmByResultInBean rInBean = new CshLoanConfirmByResultInBean();
		List<String> param = new ArrayList<String>();
		param.add(contractCode);
		rInBean.setContractCodeList(param);
		ClientPoxy service = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_RESULT);
		CshLoanConfirmByResultOutBean routBean = (CshLoanConfirmByResultOutBean) service.callService(rInBean);
		return routBean;
	}
	
	/**
	 * 根据合同编号，查询回访信息，判断是否客户放弃
	 * @param contractCode
	 * @return
	 */
	public static boolean isRevisitStatus(String contractCode) {
		try {
			CshLoanConfirmByResultOutBean routBean = getRevisitStatusInfo(contractCode);
			
			List<CshLoanConfirmByResultDetailOutBean> outList = routBean.getItems();
			if (outList != null && outList.size() > 0) {
				CshLoanConfirmByResultDetailOutBean loanConfirm = outList.get(0);
				if (GrantCommon.REVISIT_STATUS_FAIL.equals(loanConfirm.getRevisitStatus()) && loanConfirm.getRevisitNumber() == 3) {
					return true;
				}
			}else{
				logger.error("给回访中心推送数据时，未查询到回访中心的数据，contractCode：" + contractCode);
			}
		} catch (Exception e) {
			logger.error("查询回访信息，判断是否客户放弃时失败 ，合同号：" + contractCode);
			return false;
		}
		
		
		return false;
	}
	
}
