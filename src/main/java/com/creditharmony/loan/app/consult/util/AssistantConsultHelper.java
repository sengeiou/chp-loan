package com.creditharmony.loan.app.consult.util;


import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.loan.app.consult.view.AssistantConsultView;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.common.utils.DateUtil;

/**
 * 汇金ocr客户咨询帮助类
 * @Class Name AssistantConsultHelper
 * @author 张永生
 * @Create In 2016年6月13日
 */
public class AssistantConsultHelper {

	/**
	 * 将ocr客户咨询VIEW转换成客户基本信息对象
	 * 2016年6月13日
	 * By 张永生
	 * @param consultView
	 * @param customerBaseInfo
	 */
	public static void convertCustomerBaseInfo(AssistantConsultView consultView,
			CustomerBaseInfo customerBaseInfo) {
		customerBaseInfo.setCustomerCode(consultView.getCustomerId());
		customerBaseInfo.setCustomerName(consultView.getCustomerName());
		customerBaseInfo.setCustomerSex(consultView.getSex());
		customerBaseInfo.setCustomerMobilePhone(consultView.getMobilephone());
		customerBaseInfo.setDictCertType(consultView.getCertType());
		customerBaseInfo.setMateCertNum(consultView.getCertNum());
		if(!ObjectHelper.isEmpty(consultView.getIdStartDate())){
			String idStartDateStr = consultView.getIdStartDate();
			if(idStartDateStr.contains(".")){
				idStartDateStr = idStartDateStr.replace(".", "-");
			}
			customerBaseInfo.setIdStartDay(DateUtil.StringToDate(idStartDateStr, "yyyy-MM-dd"));
		}
		if(!ObjectHelper.isEmpty(consultView.getIdEndDate())){
			String idEndDateStr = consultView.getIdEndDate();
			if(idEndDateStr.contains(".")){
				idEndDateStr =	idEndDateStr.replace(".", "-");
			}
			customerBaseInfo.setIdEndDay(DateUtil.StringToDate(idEndDateStr, "yyyy-MM-dd"));
		}
		customerBaseInfo.setDictCompIndustry(consultView.getIndustry());
		customerBaseInfo.setAreaNo(consultView.getAreaNo());
		customerBaseInfo.setTelephoneNo(consultView.getTelephoneNo());
	}

	/**
	 * 包装客户咨询对象
	 * 2016年6月13日
	 * By 张永生
	 * @param consultView
	 * @param consult
	 * @param consultRecord
	 */
	public static void wrapperConsult(AssistantConsultView consultView, Consult consult,
			ConsultRecord consultRecord) {
		// 客户编码
		consult.setCustomerCode(consultView.getCustomerId());
		// 团队组织机构id 
		consult.setLoanTeamOrgId(consultView.getTeamOrgId());
		// 团队经理编号
		consult.setLoanTeamEmpcode(consultView.getTeamEmpcode());
		// 是否电销
		consult.setConsTelesalesFlag(consultView.getIsPhone());
		// 电销来源
		consult.setConsTelesalesSource(consultView.getPhoneSource());
		// 客户经理id
		consult.setManagerCode(consultView.getFinancingId());
	    // 门店id
		consult.setStoreOrgid(consultView.getStoreId());
		// 借款类型
		consult.setDictLoanType(consultView.getLoanType());
		// 借款金额
		consult.setLoanApplyMoney(consultView.getLoanPosition());
		// 借款用途
		consult.setDictLoanUse(consultView.getLoanUse());
		consult.setConsultRecord(consultRecord);
	}
	
	/**
	 * 包装客户咨询记录对象
	 * 2016年6月13日
	 * By 张永生
	 * @param consultView
	 * @param consultRecord
	 */
	public static void wrapperConsultRecord(AssistantConsultView consultView,
			ConsultRecord consultRecord) {
		// 沟通记录
		consultRecord.setConsLoanRecord(consultView.getLoanRecord());
		// 下一步操作状态
		consultRecord.setConsOperStatus(consultView.getLoanStatus());
		// 沟通时间
		consultRecord.setConsCommunicateDate(consultView.getCommunicateDate());
	}
	
}
