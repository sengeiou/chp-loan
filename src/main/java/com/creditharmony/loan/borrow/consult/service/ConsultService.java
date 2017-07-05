package com.creditharmony.loan.borrow.consult.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.common.util.CommonUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.consult.constats.ConsultRuleResultCode;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultConstant;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.borrow.consult.view.ConsultRuleResultVO;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.payback.entity.Payback;

/**
 * 客户咨询Service
 * 
 * @Class Name ConsultService
 * @author 张平
 * @Create In 2015年12月3日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ConsultService extends CoreManager<ConsultDao, Consult> {

	@Autowired
	private CustomerBaseInfoDao customerBaseInfoDao;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;

	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;

	@Autowired
	private CustomerManagementService customerManagementService;

	/**
	 * 查询咨询列表
	 *
	 * @param page
	 *            Consult
	 * @return Page<Consult>
	 */
	public Page<Consult> findPage(Page<Consult> page, Consult consult) {
		return super.findPage(page, consult);
	}

	/**
	 * 保存客户咨询信息 2015年12月3日 By 张平
	 * 
	 * @param consult
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConsult(Consult consult) {

		if (StringUtils.isBlank(consult.getId())) {
			consult.setConsTelesalesFlag(YESNO.NO.getCode());
			consult.preInsert();
			dao.insert(consult);
		} else {
			consult.preUpdate();
			dao.update(consult);
		}
	}

	/**
	 * 保存客户信息 2015年12月3日 By 张平
	 * 
	 * @param consult
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveCustomerBaseInfo(Consult consult, boolean insert) {

		CustomerBaseInfo customerBaseInfo = consult.getCustomerBaseInfo();
		String customerCode = customerBaseInfo.getCustomerCode();
		if (insert) {
			customerBaseInfo.preInsert();
			customerBaseInfoDao.insertCustomerBaseInfo(customerBaseInfo);
		} else {
			customerBaseInfo.preUpdate();
			customerBaseInfoDao.update(customerBaseInfo);
		}
		consult.setCustomerCode(customerCode);
		// 保存客户咨询信息
		saveConsult(consult);
		// 保存客户沟通日志
		saveConsultRecord(consult);

	}

	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateConsult(Consult consult) {
		ConsultRecord record = new ConsultRecord();
		record.setConsCommunicateDate(consult.getConsCommunicateDate());
		record.setConsLoanRecord(consult.getConsLoanRecord());
		record.setConsOperStatus(consult.getConsOperStatus());
		consult.setConsultRecord(record);
		// 保存客户咨询信息
		saveConsult(consult);
		// 如果loan_info表中有记录且借款状态字段为空，则把它改成客户放弃（74）
		Map<String, String> map = new HashMap<String, String>();
		map.put("consultId", consult.getId());
		LoanInfo loanInfo = loanInfoDao.selectByConsultId(map);
		if (loanInfo != null && StringUtils.isEmpty(loanInfo.getDictLoanStatus())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("dictLoanStatus", "74");
			param.put("rId", consult.getId());
			loanInfoDao.updateLoanStatus(param);
		}
		// 保存客户沟通日志
		saveConsultRecord(consult);
	}

	/**
	 * 保存客户沟通日志表 2015年12月3日 By 张平
	 * 
	 * @param consult
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConsultRecord(Consult consult) {
		consult.getConsultRecord().preInsert();
		dao.insertConsultRecord(consult);

	}

	/**
	 * 数据转换 2015年12月3日 By 张平
	 * 
	 * @param consultSearchView
	 * @return consult数据转换
	 */
	public Consult dataConversion(ConsultSearchView consultSearchView) {

		Consult consult = new Consult();
		CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
		ConsultRecord consultRecord = new ConsultRecord();
		customerBaseInfo.setCustomerName(consultSearchView.getCustomerName());
		customerBaseInfo.setMateCertNum(consultSearchView.getMateCertNum());
		customerBaseInfo.setCustomerMobilePhone(consultSearchView.getCustomerMobilePhone());
		consultRecord.setConsCommunicateDate(consultSearchView.getConsCommunicateDate());
		consultRecord.setConsOperStatus(consultSearchView.getDictOperStatus());
		consultRecord.setConsLoanRecord(consultSearchView.getConsLoanRecord());
		consult.setConsultRecord(consultRecord);
		consult.setCustomerBaseInfo(customerBaseInfo);

		return consult;
	}

	/**
	 * 通过身份证号查询客户基本信息
	 *
	 * @param key mateCertNum
	 * @return ApplyInfoFlagEx
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public ApplyInfoFlagEx findByCertNum(Map<String, Object> param) {

		return customerBaseInfoDao.findByCertNum(param);
	}

	/**
	 * 通过身份证号查询客户基本信息 By 任志远 2017年3月15日
	 *
	 * @param certNum
	 *            身份证号码
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public ApplyInfoFlagEx findByCertNum(String certNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mateCertNum", certNum);
		param.put("certType", CertificateType.SFZ.getCode());
		return customerBaseInfoDao.findByCertNum(param);
	}

	/**
	 * 通过用户编号查询客户的结清状态 by zhanghao Create In 2016-01-09
	 *
	 * @param key
	 *            customerCode , payStatus 数组
	 * @return List<Payback>
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Payback> findUnsettledByCustomerCode(Map<String, Object> param) {

		return dao.findUnsettledByCustomerCode(param);
	}

	/**
	 * 咨询规则 
	 * By 任志远 2017年3月16日
	 *
	 * @param certNum 身份证号码
	 * @return
	 */
	public ConsultRuleResultVO checkConsultRule(String certNum) {

		logger.info("咨询规则校验开始====校验身份证号:" + certNum);
		// 客户基本信息校验
		ApplyInfoFlagEx applyInfo = this.findByCertNum(certNum);
		// 校验结果
		ConsultRuleResultVO consultRuleResultVO = this.checkConsultRule(certNum, applyInfo);
		// 如果校验成功，封装返回数据
		if (ConsultRuleResultCode.SUCCESS.getCode().equals(consultRuleResultVO.getCode()) && applyInfo != null) {
			consultRuleResultVO.setCustomerCode(applyInfo.getCustomerCode());
			consultRuleResultVO.setCustomerName(applyInfo.getCustomerName());
			consultRuleResultVO.setCustomerSex(applyInfo.getCustomerSex());
			consultRuleResultVO.setDictCompIndustry(applyInfo.getDictCompIndustry());
			if (!ObjectHelper.isEmpty(applyInfo.getIdStartDay())) {
				consultRuleResultVO.setIdStartDayStr(DateUtils.formatDate(applyInfo.getIdStartDay(), "yyyy-MM-dd"));
			}
			if (!ObjectHelper.isEmpty(applyInfo.getIdEndDay())) {
				consultRuleResultVO.setIdEndDayStr(DateUtils.formatDate(applyInfo.getIdEndDay(), "yyyy-MM-dd"));
			}
		}
		logger.info("咨询规则校验结束====校验身份证号:" + certNum + "====校验结果:" + consultRuleResultVO.getMsg());
		return consultRuleResultVO;
	}

	/**
	 * 咨询规则 
	 * By 任志远 2017年3月16日
	 *
	 * @param certNum 身份证号码
	 * @param applyInfo 客户基本信息
	 * @return
	 */
	public ConsultRuleResultVO checkConsultRule(String certNum, ApplyInfoFlagEx applyInfo) {

		// 客户基本信息校验====客户信息不存在则为新客户，允许咨询
		if (applyInfo == null || StringUtils.isEmpty(applyInfo.getCustomerCode())) {
			return checkConsultRuleResult(ConsultRuleResultCode.SUCCESS);
		}
		// 借款信息校验
		List<LoanInfo> loanInfos = applyLoanInfoService.findByIdentityCode(certNum);
		if (ObjectHelper.isNotEmpty(loanInfos)) {
			LoanInfo loanInfo = loanInfos.get(0);
			// 借款状态
			LoanApplyStatus loanApplyStatus = LoanApplyStatus.parseByCode(loanInfo.getDictLoanStatus());
			if (loanApplyStatus != null) {
				switch (loanApplyStatus) {
				// 这些完结状态随时可以进件
				case SETTLE:
				case EARLY_SETTLE:
				case CUSTOMER_GIVEUP:
				case STORE_VISIT_TIMEOUT:
				case BACK_TIMEOUT:
				case RECONSIDER_STORE_VISIT_TIMEOUT:
				case RECONSIDER_BACK_TIMEOUT:
				case SIGN_TIMEOUT:
				case FRAUD_AFFIRMED_GRAY:
				case APPLY_ENGINE_REFUSE:
				case DECSISON_ENGINE_REJECT:
				case APPLY_ENGINE_ABANDON:
					break;
				// 信审拒借====6个月内不允许重新进件
				case RECHECK_REJECT:
				case GROUP_CHECK_REJECT:
				case FINAL_CHECK_REJECT:
				case RECONSIDER_RECHECK_REJECT:
				case RECONSIDER_FINAL_CHECK_REJECT:
					if (DateUtils.dateEqualOrAfter(DateUtils.addMonths(loanInfo.getModifyTime(), ConsultConstant.AUDIT_REJECT_INTERVAL_MONTH), CommonUtils.newDate())) {
						return checkConsultRuleResult(ConsultRuleResultCode.AUDIT_REJECT_MESSAGE);
					}
					break;
				// 门店拒借====1个月内不允许重新进件
				case STORE_REJECT:
					if (DateUtils.dateEqualOrAfter(DateUtils.addMonths(loanInfo.getModifyTime(), ConsultConstant.STORE_REJECT_INTERVAL_MONTH), CommonUtils.newDate())) {
						return checkConsultRuleResult(ConsultRuleResultCode.STORE_REJECT_MESSAGE);
					}
					break;
				// 黑名单====不允许进件
				case FRAUD_AFFIRMED_BLACK:
					return checkConsultRuleResult(ConsultRuleResultCode.BLACK_LOG_MESSAGE);
				// 其他状态(借款进行中状态)====不允许进件
				default:
					return checkConsultRuleResult(ConsultRuleResultCode.PROCESSING_MESSAGE);
				}
			} else {
				// 状态为空，数据在申请====不允许进件
				return checkConsultRuleResult(ConsultRuleResultCode.PROCESSING_MESSAGE);
			}
		}

		// 咨询信息校验
		Consult storedConsult = this.findConsultMess(certNum);
		if (ObjectHelper.isNotEmpty(storedConsult)) {
			// 咨询时下一步状态
			RsStatus rsStatus = RsStatus.parseByCode(storedConsult.getConsOperStatus());
			if (rsStatus != null) {
				switch (rsStatus) {
				// 继续跟踪====7天内不允许重新进件，7天后自动释放
				case CONTINUE_CONFIRM:
					return checkConsultRuleResult(ConsultRuleResultCode.CONSULT_MESSAGE);
				// 未取单====不允许重新进件
				case NO_GET_ORDER:
					return checkConsultRuleResult(ConsultRuleResultCode.NO_GET_ORDER_MESSAGE);
				// 已取单====不允许重新进件
				case GET_ORDER:
					return checkConsultRuleResult(ConsultRuleResultCode.GET_ORDER_MESSAGE);
				// 其他状态允许进件
				default:
					break;
				}
			}
		}

		// 查询客户是否参与过其他未结清借款
		List<Map<String, Integer>> customerUnSettleMapList = applyLoanInfoService.selectUnSettleData(certNum);
		Map<String, Integer> customerUnSettleMap = null;
		for (Map<String, Integer> map : customerUnSettleMapList) {
			if (map.get("count") > 0 && map.get("role") != 1) {
				customerUnSettleMap = map;
				break;
			}
		}
		// 咨询客户是尚未结清的主借人配偶====不允许进件
		if (customerUnSettleMap != null && customerUnSettleMap.get("role") == 2) {
			return checkConsultRuleResult(ConsultRuleResultCode.CUSTOMER_IN_UNSETTLE_MATE_DATA);
		}
		// 咨询客户是尚未结清的共借人====不允许进件
		if (customerUnSettleMap != null && customerUnSettleMap.get("role") == 3) {
			return checkConsultRuleResult(ConsultRuleResultCode.CUSTOMER_IN_UNSETTLE_COBO_DATA);
		}
		// 咨询客户是尚未结清的最优自然人保证人====不允许进件
		if (customerUnSettleMap != null && customerUnSettleMap.get("role") == 4) {
			return checkConsultRuleResult(ConsultRuleResultCode.CUSTOMER_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA);
		}

		return checkConsultRuleResult(ConsultRuleResultCode.SUCCESS);
	}

	/**
	 * 咨询规则返回信息 
	 * By 任志远 2017年3月16日
	 *
	 * @param resultCode 错误结果
	 * @return
	 */
	private ConsultRuleResultVO checkConsultRuleResult(ConsultRuleResultCode resultCode) {
		ConsultRuleResultVO resultView = new ConsultRuleResultVO(resultCode.getCode(), resultCode.getMsg());
		return resultView;
	}

	/**
	 * 查询已经咨询但是未进件的咨询数据 
	 * By 任志远 2017年3月15日
	 *
	 * @param certNum 身份证号码
	 * @return
	 */
	private Consult findConsultMess(String certNum) {

		Map<String, Object> param = new HashMap<>();
		List<String> telStatusList = new ArrayList<String>();
		telStatusList.add(RsStatus.CONTINUE_CONFIRM.getCode());
		telStatusList.add(RsStatus.NO_GET_ORDER.getCode());
		telStatusList.add(RsStatus.GET_ORDER.getCode());
		param.put("notTelsaleFlag", YESNO.NO.getCode());
		param.put("notTelOperStatus", NextStep.CONTINUE_CONFIRM.getCode());
		param.put("telsaleFlag", YESNO.YES.getCode());
		param.put("telOperStatusList", telStatusList);
		param.put("customerCertNum", certNum);

		return customerManagementService.findConsultMess(param);
	}

}
