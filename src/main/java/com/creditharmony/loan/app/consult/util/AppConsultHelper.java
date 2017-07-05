package com.creditharmony.loan.app.consult.util;

import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.app.consult.view.AppConsultView;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.common.utils.DateUtil;


/**
 * app客户咨询帮助类
 * @Class Name AppConsultHelper
 * @author 张永生
 * @Create In 2016年6月13日
 */
public class AppConsultHelper {

	
	/**
	 * 将app客户咨询VIEW转换成客户base实体
	 * 2016年6月13日
	 * By 张永生
	 * @param consultView
	 * @return
	 */
	public static Consult convertBaseConsult(AppConsultView consultView){
		Consult consult = new Consult();
		consult.setCustomerCode(consultView.getCustomerId());
		consult.setManagerCode(consultView.getFinancingId()); // 客户经理id
		consult.setLoanApplyMoney(consultView.getLoanPosition()); // 计划借款金额
		consult.setDictLoanUse(consultView.getLoanUse()); // 借款用途
		consult.setDictLoanType("0"); // 借款类型，0默认为信借
		consult.setLoanTeamEmpcode(consultView.getTeamEmpcode()); // 团队经理id
		consult.setConsTeamEmpName(consultView.getTeamEmpName()); // 团队经理name
		consult.setLoanTeamOrgId(consultView.getTeamOrgId()); // 团队组织机构id
		consult.setConsTelesalesFlag(YESNO.NO.getCode()); // 是否电销
		consult.setStoreOrgid(consultView.getStoreId()); // 门店编码
		consult.setConsLoanRecord(consultView.getLoanRecord()); // 沟通记录
		consult.setConsOperStatus(consultView.getLoanStatus()); // 下一步操作状态
		consult.setConsCommunicateDate(consultView.getCommunicateDate()); // 沟通时间
		// 客户基本信息
		CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
		customerBaseInfo.setCustomerCode(consultView.getCustomerId()); // 客户编码
		customerBaseInfo.setCustomerName(consultView.getCustomerName()); // 客户姓名
		customerBaseInfo.setCustomerSex(consultView.getSex()); // 性别
		customerBaseInfo.setCustomerBirthday(consultView.getBirthday()); // 出生日期
		customerBaseInfo.setDictCertType(CertificateType.SFZ.getCode()); // 证件类型,默认为身份证
		customerBaseInfo.setMateCertNum(consultView.getCertNum()); // 证件号码
		customerBaseInfo.setIdStartDay(DateUtil.StringToDate(consultView.getIdStartDate(), "yyyy-MM-dd")); // 身份证开始日期
		customerBaseInfo.setIdEndDay(DateUtil.StringToDate(consultView.getIdEndDate(), "yyyy-MM-dd")); // 身份证结束日期
		customerBaseInfo.setCustomerMobilePhone(consultView.getMobilephone()); // 手机号码
		customerBaseInfo.setDictCompIndustry(consultView.getIndustry()); // 行业类型
		customerBaseInfo.setAreaNo(consultView.getAreaNo()); // 区号
		customerBaseInfo.setTelephoneNo(consultView.getTelephoneNo()); // 座机号
		
		consult.setCustomerBaseInfo(customerBaseInfo);
		// 咨询日志信息
		ConsultRecord consultRecord = new ConsultRecord();
		consultRecord.setConsLoanRecord(consultView.getLoanRecord()); // 沟通记录
		consultRecord.setConsOperStatus(consultView.getLoanStatus()); // 下一步操作状态
		consultRecord.setConsCommunicateDate(consultView.getCommunicateDate()); // 沟通时间
		consultRecord.setConsult(consult);
		consult.setConsultRecord(consultRecord);
		return consult;
	}
}
