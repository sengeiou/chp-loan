package com.creditharmony.loan.telesales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.telesales.dao.TelesalesConsultDAO;
import com.creditharmony.loan.telesales.entity.TelesalesConsultInfo;

/**
 * 电销客户咨询Service层
 * 
 * @Class Name TelesalesConsultService
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class TelesalesConsultService extends CoreManager<TelesalesConsultDAO, TelesalesConsultInfo> {

	@Autowired
	private TelesalesConsultDAO telesalesConsultDao;

	@Autowired
	private CustomerBaseInfoDao customerBaseInfoDao;

	@Autowired
	private ConsultService consultService;

	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;

	@Autowired
	private CustomerManagementService customerManagementService;

	/**
	 * 保存电销客户信息 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 * @param insert
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveTelesalesConsultInfo(TelesalesConsultInfo telesalesConsult, boolean insert) {
		CustomerBaseInfo customerBaseInfo = telesalesConsult.getCustomerBaseInfo();
		String customerCode = customerBaseInfo.getCustomerCode();
		// 客户基本信息保存
		if (insert) {
			customerBaseInfo.preInsert();
			customerBaseInfoDao.insertCustomerBaseInfo(customerBaseInfo);
		} else {
			customerBaseInfo.preUpdate();
			// 改成电销
			customerBaseInfo.setConsTelesalesFlag("1");
			customerBaseInfoDao.update(customerBaseInfo);
		}
		telesalesConsult.setCustomerCode(customerCode);
		// 保存客户咨询信息
		saveTelesalesCustomer(telesalesConsult);
		// 保存客户沟通记录及下一步操作
		saveConsultRecord(telesalesConsult);
	}

	/**
	 * 保存客户借款信息 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveTelesalesCustomer(TelesalesConsultInfo telesalesConsult) {
		if (StringUtils.isEmpty(telesalesConsult.getId())) {
			telesalesConsult.preInsert();
			dao.insert(telesalesConsult);
		} else {
			telesalesConsult.preUpdate();
			dao.update(telesalesConsult);
		}
	}

	/**
	 * 保存客户沟通日志 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConsultRecord(TelesalesConsultInfo telesalesConsult) {
		telesalesConsult.getConsultRecord().preInsert();
		dao.insertConsultRecord(telesalesConsult);
	}

	/**
	 * 门店客服取单 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getTelesaleOrder(TelesalesConsultInfo telesalesConsult) {
		if (!StringUtils.isEmpty(telesalesConsult.getId())) {
			updateTelesalesConsultStatus(telesalesConsult);
			saveConsultRecord(telesalesConsult);
		}
	}

	/**
	 * 更新电销咨询状态 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTelesalesConsultStatus(TelesalesConsultInfo telesalesConsult) {
		dao.updateConsultOrderStatus(telesalesConsult);
	}

	/**
	 * 获取单个电销客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param consultId
	 * @param customerCode
	 * @return TelesalesConsultInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public TelesalesConsultInfo getSingleTelesalesConsultInfo(String consultId, String customerCode) {
		CustomerBaseInfo customerBaseInfo = customerBaseInfoDao.selectByPrimaryKey(customerCode);
		TelesalesConsultInfo telesalesConsultInfo = dao.findSingleTelesalesConsultInfo(consultId);
		ConsultRecord consultRecord = new ConsultRecord();
		String consRecordString = telesalesConsultInfo.getConsLoanRecord() == null ? "" : telesalesConsultInfo.getConsLoanRecord();
		consultRecord.setConsLoanRecord(consRecordString);
		String statusString = telesalesConsultInfo.getConsOperStatus() == null ? "" : telesalesConsultInfo.getConsOperStatus();
		// 如果是门店退回则将下一步状态修改为继续跟踪
		if (statusString.equals(RsStatus.STORE_BACK.getCode())) {
			statusString = RsStatus.CONTINUE_CONFIRM.getCode();
		}
		consultRecord.setConsOperStatus(statusString);
		if (!ObjectHelper.isEmpty(customerBaseInfo)) {
			telesalesConsultInfo.setCustomerBaseInfo(customerBaseInfo);
			telesalesConsultInfo.setConsultRecord(consultRecord);
		}
		return telesalesConsultInfo;
	}

	/**
	 * 更改客户咨询信息 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTelesalesConsultInfo(TelesalesConsultInfo telesalesConsult) {
		CustomerBaseInfo customerBaseInfo = telesalesConsult.getCustomerBaseInfo();
		// 更新客户基本信息
		customerBaseInfo.preUpdate();
		customerBaseInfoDao.update(customerBaseInfo);
		// 更新客户的借款信息
		saveTelesalesCustomer(telesalesConsult);
		// 记录日志信息
		saveConsultRecord(telesalesConsult);
	}

	/**
	 * 取消订单发送 2016年3月11日 By 周怀富
	 * 
	 * @param telesalesConsult
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void cancleTelesalesConsult(TelesalesConsultInfo telesalesConsult) {
		updateTelesalesConsultStatus(telesalesConsult);
		saveConsultRecord(telesalesConsult);
	}

	/**
	 * 7天未取单的视为门店放弃 2016年3月11日 By 周怀富
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateStoreGiveUpList() {
		telesalesConsultDao.updateStoreGiveUpStatus();
	}

	/**
	 * 查询最新的电销客户咨询列表 2016年3月16日 By 周怀富
	 * 
	 * @param telesalesConsult
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<TelesalesConsultInfo> findEarliestTelesalesConsult(TelesalesConsultInfo telesalesConsult) {
		return telesalesConsultDao.findTelesalesConsultList(telesalesConsult);
	}

	/**
	 * 根据组织机构ID、角色ID查询 2016年3月17日 By 周怀富
	 * 
	 * @param telesalesConsult
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public TelesalesConsultInfo findUserIdByOrgAndRole(String orgId, String roleId) {
		TelesalesConsultInfo telesalesCustomer = new TelesalesConsultInfo();
		telesalesCustomer.setTeleSalesOrgid(orgId);
		telesalesCustomer.setRoleId(roleId);
		return telesalesConsultDao.findUserIdByOrgIdAndRoleId(telesalesCustomer);
	}

}
