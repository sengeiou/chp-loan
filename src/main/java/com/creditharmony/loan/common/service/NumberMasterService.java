/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceNumberMasterService.java
 * @Create By 王彬彬
 * @Create In 2015年12月29日 上午11:00:47
 */
package com.creditharmony.loan.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.common.consts.NumberManager;
import com.creditharmony.loan.common.dao.NumberMasterDao;
import com.creditharmony.loan.common.entity.NumberMaster;
import com.creditharmony.loan.common.utils.IdentifierRule;

/**
 * 编号规则
 * 
 * @Class Name NumberMasterService
 * @author 王彬彬
 * @Create In 2015年12月29日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class NumberMasterService extends
		CoreManager<NumberMasterDao, NumberMaster> {

	/**
	 * 取得编号规则信息 2015年12月29日 By 王彬彬
	 * 
	 * @param numbaerMaster
	 * @return 现有编号
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public synchronized int getNumberMaster(NumberMaster numbaerMaster) {
		NumberMaster numberData = dao.get(numbaerMaster);
		int count = 0;

		if (numberData != null) {
			count = numberData.getSerialNo() + NumberManager.STEP;
			numberData.setSerialNo(count);
			dao.update(numberData);
		} else {
			count = 1;
			numbaerMaster.preInsert();
			numbaerMaster.setSerialNo(count);
			numbaerMaster.setSerialNo(NumberManager.START);
			dao.insert(numbaerMaster);
		}

		return count;
	}

	/**
	 * 合同编号生成 
	 * 2016年1月11日 By 王彬彬
	 * 
	 * @param loaninfo 借款信息
	 * @param serialNoType 编号生成类型
	 * @param oldContractNo
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getContractNumber(LoanInfo loaninfo,
			SerialNoType serialNoType, String oldContractNo) {
		NumberMaster numberMaster = new NumberMaster();

		numberMaster.setDealDate(NumberManager.DATE_INIT);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC);
		numberMaster.setDealPart(NumberManager.CONTRACT_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int count = getNumberMaster(numberMaster);

		if (SerialNoType.CONTRACT.equals(serialNoType)
				|| SerialNoType.RECONSIDE.equals(serialNoType)
				|| SerialNoType.CHANGE.equals(serialNoType)
				|| SerialNoType.RAISE.equals(serialNoType)) {
			return IdentifierRule.getFullContract(loaninfo, count,
					serialNoType, oldContractNo);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 借款编号生成 
	 * 2016年1月11日 By 王彬彬
	 * 
	 * @param serialNoType
	 *            编号类型
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getLoanNumber(SerialNoType serialNoType) {
		NumberMaster numberMaster = new NumberMaster();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateFlag = sdf.format(new Date());
		
		numberMaster.setDealDate(dateFlag);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(NumberManager.LOAN_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int count = getNumberMaster(numberMaster);

		if (SerialNoType.LOAN.equals(serialNoType)) {
			return IdentifierRule.getLoanCode(count,dateFlag,serialNoType);
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 客户编号生成 (JKKH+年月日+6位流水号)
	 * 2016年1月11日 By 王彬彬
	 * 
	 * @param serialNoType
	 *            编号生成类型
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getCustomerNumber(SerialNoType serialNoType) {
		NumberMaster numberMaster = new NumberMaster();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateFlag = sdf.format(new Date());
		
		numberMaster.setDealDate(dateFlag);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(NumberManager.CUSTOMER_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int count = getNumberMaster(numberMaster);

		if (SerialNoType.CUSTOMER.equals(serialNoType)) {
			return IdentifierRule.getCustomerCode(count, dateFlag,serialNoType);
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 结清证明协议编号(年月日+JQZM+3位流水)
	 * 2016年1月11日 By 王彬彬
	 * 
	 * @param serialNoType
	 *            编号生成类型
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getJQZMNumber() {
		NumberMaster numberMaster = new NumberMaster();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateFlag = sdf.format(new Date());
		
		numberMaster.setDealDate(dateFlag);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_MONTH);
		numberMaster.setDealPart(NumberManager.SETTLED_NO_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int count = getNumberMaster(numberMaster);
		String serialNo = dateFlag + NumberManager.SETTLED_NO_TYPE + String.format("%03d", count);
		
		return serialNo;
	}
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getZCJJQZMNumber() {
		NumberMaster numberMaster = new NumberMaster();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateFlag = sdf.format(new Date());
		
		numberMaster.setDealDate(dateFlag);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_MONTH);
		numberMaster.setDealPart(NumberManager.ZCJ_SETTLED_NO_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);
		
		int count = getNumberMaster(numberMaster);
		String serialNo = dateFlag + NumberManager.ZCJ_SETTLED_NO_TYPE + String.format("%03d", count);
		
		return serialNo;
	}
}
