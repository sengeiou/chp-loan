/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.HistoryService.java
 * @Create By 张进
 * @Create In 2015年12月1日 下午2:10:16
 */
package com.creditharmony.loan.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.EmailOpe;
import com.creditharmony.loan.common.entity.LoanStatusHis;

@Service
public class HistoryService {
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;

	@Autowired
	private LoanGrantDao loanGrantDao;

	/**
	 * 更新借款信息，插入历史记录 2016年2月18日 By 王彬彬
	 * 
	 * @param loanInfo
	 * @param operateStep
	 * @param operateResult
	 * @param remark
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveLoanStatus(LoanInfo loanInfo, String operateStep,
			String operateResult, String remark) {
		// 调用方法，更新状态
		loanGrantDao.updateStatusByLoanCode(loanInfo);
		// 更新历史
		saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
	}

	/**
	 * 添加操作历史 2016年2月17日 By 王彬彬
	 * 
	 * @param loaninfo
	 *            借款信息
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int saveLoanStatusHis(LoanInfo loaninfo, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(loaninfo.getApplyId());
		// 借款编号
		record.setLoanCode(loaninfo.getLoanCode());
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		// 操作借款状态code
		record.setDictLoanStatus(loaninfo.getDictLoanStatus());
		
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}

	/**
	 * 添加操作历史 2016年2月17日 By 王彬彬
	 * 
	 * @param applyId
	 *            工作流ID
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 * @return
	 */
	@Deprecated
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int saveLoanStatusHis(String applyId, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		// record.setLoanCode(loaninfo.getLoanCode());
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}

	/**
	 * 添加操作历史 2016年2月17日 By 王彬彬
	 * 
	 * @param applyId
	 *            工作流ID
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int saveLoanStatusHis(String applyId, String loanCode,
			String operateStep, String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int saveContractStatusHis(String applyId, String loanCode,
			String operateStep, String operateResult, String remark,String sysModel) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(sysModel);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}

	/**
	 * 分页查询历史数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param loanStatusHis
	 *            查询条件，获取appId
	 * @return 分页数据
	 */
	public Page<LoanStatusHis> findLoanStatusHisList(Page<LoanStatusHis> page,
			LoanStatusHis loanStatusHis) {
		loanStatusHis.setPage(page);
		loanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		page.setList(loanStatusHisDao.findPage(loanStatusHis));
		return page;
	}
	
	/**
	 * 分页查询历史数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param loanStatusHis
	 *            查询条件，获取loanCode
	 * @return 分页数据
	 */
	public Page<LoanStatusHis> findHisPageByLoanCode(Page<LoanStatusHis> page,
			LoanStatusHis loanStatusHis) {
		loanStatusHis.setPage(page);
		if(!loanStatusHis.isShowAll())
		{
			loanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		}
		page.setList(loanStatusHisDao.findHisPageByLoanCode(loanStatusHis));
		return page;
	}
	public List<LoanStatusHis> findByLoanCodeAndStatus(LoanStatusHis loanStatusHis){
	    return loanStatusHisDao.findByLoanCodeAndStatus(loanStatusHis);
	}
	/**
	 * 分页查询历史数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param loanStatusHis
	 *            查询条件，获取loanCode
	 * @return 分页数据
	 */
	public Page<LoanStatusHis> findContractHisPageByLoanCode(Page<LoanStatusHis> page,
			LoanStatusHis loanStatusHis) {
		loanStatusHis.setPage(page);
		page.setList(loanStatusHisDao.findHisPageByLoanCode(loanStatusHis));
		return page;
	}
	

	/**
	 * 查询最近的汇诚审核节点 by zhanghao Create In 2016-1-6
	 *
	 * @param key
	 *            dictSysFlag,applyId
	 * @return List<LoanStatusHis>
	 * 
	 */
	public List<LoanStatusHis> findLastApproveNote(Map<String, Object> param) {

		return loanStatusHisDao.findLastApproveNote(param);
	}

	/**
	 * 插入还款操作流水历史 2016年1月6日 By zhangfeng
	 * 
	 * @param PaybackOpeEx
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertPaybackOpe(PaybackOpeEx paybackOpeEx) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(paybackOpeEx.getrPaybackApplyId());
		paybackOpe.setrPaybackId(paybackOpeEx.getrPaybackId());
		paybackOpe.setDictLoanStatus(paybackOpeEx.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpeEx.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpeEx.getRemark());
		paybackOpe.setOperateResult(paybackOpeEx.getOperateResult());
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		paybackOpe.preInsert();
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}

	/**
	 * 获取还款操作流水（还款申请表ID或还款主表ID只要一个有值） 2016年2月18日 By 王彬彬
	 * 
	 * @param payBackApplyId
	 *            还款申请表ID
	 * @param payBackId
	 *            还款主表ID
	 * @param lisi 
	 * @return
	 */
	public Page<PaybackOpe> getPaybackOpe(String payBackApplyId,
			String payBackId,String lisi, TargetWay targetWay, Page<PaybackOpe> page) {

		Map<String, String> filter = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(payBackApplyId) && payBackApplyId.length()>0) {
			filter.put("rPaybackApplyId", payBackApplyId);
		}
		if (StringUtils.isNotEmpty(payBackId)) {
			filter.put("rPaybackId", payBackId);
		}
		/*if(!ObjectHelper.isEmpty(targetWay)){
			filter.put("dictRDeductType", targetWay.getCode());
		}*/

		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		
		List<PaybackOpe> opeList = loanStatusHisDao.getPaybackOpe(filter,
				pageBounds);
		for(PaybackOpe ope: opeList){
			if (StringUtils.isNotEmpty(ope.getOperateResult())) {
				if(PaybackOperate.parseByCode(ope.getOperateResult()) != null){
					ope.setOperateResult(PaybackOperate.parseByCode(ope.getOperateResult()).getName());
				}
			}
			if (StringUtils.isNotEmpty(ope.getDictLoanStatus())) {
				if(RepaymentProcess.parseByCode(ope.getDictLoanStatus()) != null){
			       ope.setDictLoanStatus(RepaymentProcess.parseByCode(ope.getDictLoanStatus()).getName());
			   }
			}
		}
		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>) opeList;
		PageUtil.convertPage(pageList, page);

		return page;
	}
	
	/**
	 * 获取还款操作流水（还款申请表ID或还款主表ID只要一个有值） 2016年2月18日 By zhangfeng
	 * 
	 * @param payBackApplyId
	 *            还款申请表ID
	 * @param payBackId
	 *            还款主表ID
	 * @return
	 */
	public Page<PaybackOpe> getPaybackOpeHistory(String payBackApplyId,
			String payBackId,String dictLoanStatus, TargetWay targetWay, Page<PaybackOpe> page) {

		Map<String, String> filter = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(payBackApplyId) && payBackApplyId.length()>0) {
			filter.put("rPaybackApplyId", payBackApplyId);
		}
		if (StringUtils.isNotEmpty(payBackId)) {
			filter.put("rPaybackId", payBackId);
		}
		if(StringUtils.isNotEmpty(dictLoanStatus)){
			filter.put("dictLoanStatus", dictLoanStatus);
		}
		if(!ObjectHelper.isEmpty(targetWay)){
			filter.put("dictRDeductType", targetWay.getCode());
		}

		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		
		List<PaybackOpe> opeList = loanStatusHisDao.getPaybackOpe(filter,
				pageBounds);
		for(PaybackOpe ope: opeList){
			if (StringUtils.isNotEmpty(ope.getOperateResult())) {
				if(PaybackOperate.parseByCode(ope.getOperateResult()) != null){
					ope.setOperateResult(PaybackOperate.parseByCode(ope.getOperateResult()).getName());
				}
				
			}
			if (StringUtils.isNotEmpty(ope.getDictLoanStatus())) {
				
				if(RepaymentProcess.parseByCode(ope.getDictLoanStatus())  != null){
					
					ope.setDictLoanStatus(RepaymentProcess.parseByCode(ope.getDictLoanStatus()).getName());
				}
			}
		}

		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>) opeList;
		PageUtil.convertPage(pageList, page);

		return page;
	}

	/**
	 * 查询拆分表数据  2016年2月18日 By 翁私
	 * @param rPaybackId
	 * @param payBackApplyId
	 * @param page 
	 */
	public Page<PaybackSplit> getPaybackSplit(String rPaybackId, String payBackApplyId, Page<PaybackSplit> page) {
		Map<String, String> filter = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(payBackApplyId)) {
			filter.put("paybackApplyId", payBackApplyId);
		}
		if (StringUtils.isNotEmpty(rPaybackId)) {
			filter.put("paybackApplyId", rPaybackId);
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		List<PaybackSplit> opeList = loanStatusHisDao.getPaybackSplit(filter,pageBounds);
		for (PaybackSplit paybackSplit : opeList) {
			paybackSplit.setSplitBackResult(DictCache.getInstance().getDictLabel(
					"jk_counteroffer_result", paybackSplit.getSplitBackResult()));
			paybackSplit.setDictDealType(DictCache.getInstance().getDictLabel(
					"jk_deduct_plat", paybackSplit.getDictDealType()));
		}
		PageList<PaybackSplit> pageList = (PageList<PaybackSplit>) opeList;
		PageUtil.convertPage(pageList, page);

		return page;
		
	}
	
	/**
	 * 退款操作历史
	 */
	public Page<PaybackHistory> showPaybackHistory(String contractCode,Page<PaybackHistory> page) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		List<PaybackHistory> opeList = loanStatusHisDao.showPaybackHistory(contractCode,pageBounds);
		PageList<PaybackHistory> pageList = (PageList<PaybackHistory>) opeList;
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 退款操作历史
	 */
	public Page<EmailOpe> showEmailOpe(Map<String,Object> map,Page<EmailOpe> page) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		List<EmailOpe> opeList = loanStatusHisDao.showEmailOpe(map,pageBounds);
		PageList<EmailOpe> pageList = (PageList<EmailOpe>) opeList;
		PageUtil.convertPage(pageList, page);
		return page;
	}
}