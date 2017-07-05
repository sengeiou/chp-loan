package com.creditharmony.loan.channel.jyj.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeRecove;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.ExportTemplateEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceHylEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceHylInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceTlEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceTlInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceZJInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.channel.jyj.dao.JyjUrgeServicesMoneyDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackBlueAmountDao;
import com.creditharmony.loan.common.service.PaymentSplitService;

/**
 * 放款当日待划扣列表
 * @Class Name GrantDeductsService
 * @author 朱静越
 * @Create In 2015年12月12日
 */
@Service("JyjGrantDeductsService")
public class JyjGrantDeductsService extends CoreManager<JyjUrgeServicesMoneyDao, UrgeServicesMoneyEx>{
	@Autowired
	private ProvinceCityManager cityManager;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;     
	@Autowired
	private PaybackBlueAmountDao paybackBlueAmountDao;
	@Autowired
	private PaymentSplitService paymentSplitService;
	/**
	 * 放款当日待划扣列表查询
	 * 2016年1月6日
	 * By 朱静越
	 * @param page
	 * @param urgeServicesMoneyEx
	 * @return 分页
	 */
	public Page<UrgeServicesMoneyEx> selectUrgeList(Page<UrgeServicesMoneyEx> page,UrgeServicesMoneyEx urgeServicesMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeServicesMoneyEx> pageList = (PageList<UrgeServicesMoneyEx>)dao.selectDeductsList(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 放款当日，以往待划扣列表，根据查询条件不分页的list
	 * 2016年2月20日
	 * By 朱静越
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 未分页的结果集
	 */
	public List<UrgeServicesMoneyEx> selectUrgeListNo(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selectDeductsList(urgeServicesMoneyEx);
	}
	
	/**
	 * 查询放款划扣已办列表
	 * 2016年2月18日
	 * By 朱静越
	 * @param page
	 * @param urgeServicesMoneyEx
	 * @return 放款划扣已办集合
	 */
	public Page<UrgeServicesMoneyEx> selectDeductsSuccess(Page<UrgeServicesMoneyEx> page,UrgeServicesMoneyEx urgeServicesMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeServicesMoneyEx> pageList = (PageList<UrgeServicesMoneyEx>)dao.selectDeductsSuccess(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询进行导出的单子
	 * 2016年2月23日
	 * By 朱静越
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 要导出的单子
	 */
	public List<UrgeServicesMoneyEx> selectDeductsSuccessNo(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selectDeductsSuccess(urgeServicesMoneyEx);
	}
	
	/**
	 * 线下导入之后，根据拆分表的更新，获得要更新的催收主表的信息
	 * 2016年2月19日
	 * By 朱静越
	 * @param remark 合同编号
	 * @return
	 */
	public UrgeServicesMoney selectSuccess(String remark){
		return dao.selSuccess(remark);
	}
	
	/**
	 * 根据拆分表id查询划扣平台
	 * 2016年1月12日
	 * By 朱静越
	 * @param id 拆分表id
	 * @return 拆分实体
	 */
	public UrgeServicesMoneyEx getDealType(String id){
		return dao.getDealType(id);
	}
	
	/**
	 * 插入催收服务费表
	 * 2016年1月8日
	 * By 朱静越
	 * @param urgeServicesMoney 催收实体
	 * @return 是否插入成功
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String insertUrge(UrgeServicesMoney urgeServicesMoney){
		urgeServicesMoney.preInsert();
		dao.insertUrge(urgeServicesMoney);
		String id = urgeServicesMoney.getId();
		return id;
	}
	
	/**
	 * 根据催收主表id查询催收主表的划扣回盘结果
	 * 2016年2月20日
	 * By 朱静越
	 * @param id 催收主表id
	 * @return 催收回盘结果
	 */
	public UrgeServicesMoney find(String id){
		return dao.find(id);
	}
	
	/**
	 * 根据催收id和回盘结果查询拆分表，返回list
	 * 2016年2月17日
	 * By 朱静越
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 集合
	 */
	public List<UrgeServicesMoneyEx> selProcess(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selProcess(urgeServicesMoneyEx);
	}
	
	/**
	 * 查询拆分表中回盘结果不为成功的单子
	 * 2016年3月15日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<UrgeServicesMoneyEx> selectToDealOnLine(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selToDealOnLine(urgeServicesMoneyEx);
	}
	
	/**
	 * 根据查询出来的拆分表中的集合进行删除
	 * 2016年2月17日
	 * By 朱静越
	 * @param urgeId 回盘结果为处理中的拆分表中的单子的id
	 * @return 删除结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int delProcess(String id){
		return dao.delProcess(id);
	}
	/**
	 * 更新催收主表
	 * 2016年2月17日
	 * By 朱静越
	 * @param urgeMoney 更新实体
	 * @return 更新结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateUrge(UrgeServicesMoney urgeMoney){
		urgeMoney.preUpdate();
		return dao.updateUrge(urgeMoney);
	}
	
	/**
	 * 根据合同编号对催收服务费主表进行更新
	 * 2016年3月9日
	 * By 朱静越
	 * @param urgeServicesMoney
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateUrgeByCont(UrgeServicesMoney urgeServicesMoney){
		urgeServicesMoney.preUpdate();
		return dao.updateUrgeByCont(urgeServicesMoney);
	}
	
	/**
	 * 更新拆分表
	 * 2016年1月6日
	 * By 朱静越
	 * @param urgeMoney 催收服务费扩展实体
	 * @return 更新拆分结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updUrgeSplit(UrgeServicesMoneyEx urgeMoney){
		urgeMoney.preUpdate();
		return dao.updUrgeSplit(urgeMoney);
	}
	
	/**
	 * 拆分时，改变拆分前的单子置为无效
	 * 2016年1月12日
	 * By 朱静越
	 * @param urgeMoneyEx
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updSplitStatus(UrgeServicesMoneyEx urgeMoneyEx){
		urgeMoneyEx.preUpdate();
		return dao.updSplitStatus(urgeMoneyEx);
	}
	
	/**
	 * 富友平台导出
	 * 2016年1月6日
	 * By 朱静越
	 * @param urgeMoneyEx 封装拆分表中的id和父id
	 * @return 富友平台需要的数据的集合
	 */
	public List<UrgeServiceFyEx> getDeductsFy(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsFy(urgeMoneyEx);
	}
	
	/**
	 * 好易联平台导出
	 * 2016年1月6日
	 * By 朱静越
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 好易联平台需要的数据
	 */
	public List<UrgeServiceHylEx> getDeductsHyl(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsHyl(urgeMoneyEx);
	}

	/**
	 * 通联平台导出
	 * 2016年3月2日
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 通联平台需要的数据
	 */
	public List<UrgeServiceTlEx> getDeductsTl(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsTl(urgeMoneyEx);
	}
	
	/**
	 * 通联平台导出
	 * 2016年3月2日
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 通联平台需要的数据
	 */
	public List<UrgeServiceZJEx> getDeductsZJ(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsZJ(urgeMoneyEx);
	}
	
	/**
	 * 将催收服务费主表拆分
	 * 2016年1月13日
	 * By 朱静越
	 * @param id 催收主键
	 * @return 要进行拆分的实体
	 */
	public List<PaybackApply> queryUrgeList(String id){
		return dao.queryUrgeList(id);
	}
	
	/**
	 * 催收服务费获得需要发送到批处理的单子
	 * 2016年2月24日
	 * By 朱静越
	 * @param urgeMoney
	 * @return
	 */
	public List<DeductReq> selectSendList(UrgeServicesMoneyEx urgeMoney){
		//　取得规则
		String rule = urgeMoney.getRule();
		List<DeductReq> list =  dao.selSendList(urgeMoney);
		for(DeductReq deductReq:list){
			// 设置划扣标志为代收
			deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			// 设置划扣规则，从前台接收过来的规则
			deductReq.setRule(rule);
			// 设置划扣备注
			deductReq.setRemarks(deductReq.getBusinessId()+"_综合费用");
			//  系统处理ID，设置为催收处理
			deductReq.setSysId(DeductWays.HJ_04.getCode());
		}
		return list;
	}
	
	
	/**
	 * 通联平台导出
	 * 2016年3月1日
	 * By 王彬彬
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 好易联平台需要的数据
	 */
	public List<ExportTemplateEx> getDeductsData(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsData(urgeMoneyEx);
	}
	
	/**
	 * 根据放款id查找放款记录表
	 * 2016年3月8日
	 * By 朱静越
	 * @param id
	 * @return
	 */
	public LoanGrant selGrant(String id){
		return dao.selGrant(id);
	}
	
	/**
	 * 根据合同编号进行查询催收主表中的id
	 * 2016年3月9日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public String selUrgeId(String contractCode){
		return dao.selUrgeId(contractCode);
	}
	
	/**
	 * 根据合同编号进行查询还款主表中的蓝补金额
	 * 2016年4月18日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public BigDecimal getBlueAmount(String contractCode){
		return dao.selBlueAmount(contractCode);
	}
	
	/**
	 * 更新蓝补金额
	 * 2016年4月18日
	 * By 朱静越
	 * @param payback
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updBlue(Payback payback){
		dao.updBlue(payback);
	}
	

	/**
	 * 对要进行导出的单子
	 * 将拆分表中划扣失败的单子重新合并成要拆分的集合，同时删除拆分表中划扣失败的单子 
	 * 2016年2月19日 By 朱静越
	 * @param delList 拆分表中存在的
	 * @return 要进行重新拆分的集合
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void delSplit(List<UrgeServicesMoneyEx> delList) {
		if (delList.size() > 0) {
			// 获得拆分list
			String splitId = null;
			StringBuilder parameterSplit = new StringBuilder();
			for (int i = 0; i < delList.size(); i++) {
				parameterSplit.append("'" + delList.get(i).getId() + "',");
			}
			splitId = parameterSplit.toString().substring(0,
					parameterSplit.lastIndexOf(","));
			// 删除存在的list
			dao.delProcess(splitId);
		}
	}
	
	/**
	 * 发送划扣，单条处理
	 * 2016年4月25日
	 * By 朱静越
	 * @param deductReqItem
	 * @param dictStep
	 * @param deductPlatCode
	 * @return
	 */
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String sendDeductDeal(DeductReq deductReqItem,
			RepaymentProcess dictStep, String deductPlatCode) {
			UrgeServicesMoneyEx urgeServices = new UrgeServicesMoneyEx();
			String message = "";
			DeResult t = TaskService.addTask(deductReqItem);
		try {
			if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
				// 将单子的划扣平台更新为要发送的平台，更新催收主表的划扣平台
				UrgeServicesMoney urge = new UrgeServicesMoney();
				// 设置催收主表的id
				urge.setId("'" + deductReqItem.getBatId() + "'");
				// 将催收主表中回盘结果设置为处理中，划扣日期没有更新，直接在批处理调用完成之后更新划扣时间
				urge.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
				urge.setDeductStatus(UrgeCounterofferResult.PROCESS.getCode());
				urge.setDictDealType(deductPlatCode);
				dao.updateUrge(urge);
				// 插入历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(deductReqItem.getBatId());
				paybackOpe.setDictLoanStatus(dictStep.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks(GrantCommon.SEND_DEDUCT);
				paybackOpe.setOperateResult(PaybackOperate.SEND_SUCCESS.getCode());
				paybackOpe.preInsert();

				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				loanStatusHisDao.insertPaybackOpe(paybackOpe);
				
				// 发送成功之后，获得将拆分表中处理状态为处理中（线下）和失败的单子进行删除
				urgeServices.setUrgeId("'"+deductReqItem.getBatId()+"'");
				List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeServices);
				// 删除
				if (delList.size() > 0) {
					for (int i = 0; i < delList.size(); i++) {
						dao.delProcess("'"+delList.get(i).getId()+"'");
					}
				}
				TaskService.commit(t.getDeductReq());
			}else{
				// 发送失败，添加到历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(deductReqItem.getBatId());
				paybackOpe.setDictLoanStatus(dictStep.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks(GrantCommon.SEND_DEDUCT+t.getReMge());
				paybackOpe.setOperateResult(PaybackOperate.SEND_FAILED.getCode());
				paybackOpe.preInsert();
				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				loanStatusHisDao.insertPaybackOpe(paybackOpe);
				TaskService.rollBack(t.getDeductReq());
				message = t.getReMge();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("催收服务费发送划扣失败",e);
			TaskService.rollBack(t.getDeductReq());
			throw new ServiceException("催收服务费发送划扣失败");
		}finally{
			return message;
		}
	}
	
	/**
	 * 放款以往待划扣列表追回，单笔进行处理
	 * 2016年4月27日
	 * By 朱静越
	 * @param ids
	 * @param backPlat
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String deductBackDeal(String ids,String backPlat) {
		String resultMessage = null;
		// 根据催收id查找催收实体。
		UrgeServicesMoney urgeServicesMoney = find(ids);
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		UrgeServicesMoney updUrge = new UrgeServicesMoney();
		UrgeServicesMoneyEx paybackSplit = new UrgeServicesMoneyEx();
		// 只有数据状态为划扣失败，查账退回的数据可以进行追回操作。
		if (urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.PAYMENT_FAILED.getCode())
				||urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.PREPAYMENT.getCode())
				||urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.ACCOUNT_RETURN.getCode())) {
			BigDecimal unDeductAmount = urgeServicesMoney.getUrgeMoeny().subtract(urgeServicesMoney.getUrgeDecuteMoeny()).subtract(urgeServicesMoney.getAuditAmount());
			// 判断该单子选择的划扣平台是否是【蓝补冲抵】，如果是蓝补冲抵，需要通过合同编号获得蓝补金额进行判断,
			if (TradeRecove.BLUE_CHARGE.getCode().equals(backPlat)) {
				BigDecimal blueAmount = getBlueAmount(urgeServicesMoney.getContractCode());
				// 蓝补金额小于未划金额时
				if (blueAmount.compareTo(unDeductAmount)==-1) {
					resultMessage = GrantCommon.DEDUCT_BANCK_FAIL;
					return resultMessage;
				}else{
					Payback updPayback = new Payback();
					updPayback.setContractCode(urgeServicesMoney.getContractCode());
					updPayback.setPaybackBuleAmount(blueAmount.subtract(unDeductAmount));
					// 冲抵成功之后，更新蓝补金额
					dao.updBlue(updPayback);
					// 添加蓝补更新历史
			    	paybackBuleAmount.setTradeType(TradeType.TURN_OUT.getCode());
			    	paybackBuleAmount.setTradeAmount(unDeductAmount);
			    	paybackBuleAmount.setSurplusBuleAmount(blueAmount.subtract(unDeductAmount));
			    	paybackBuleAmount.setContractCode(urgeServicesMoney.getContractCode());
			    	paybackBuleAmount.preInsert();
			    	paybackBlueAmountDao.insertPaybackBuleAmount(paybackBuleAmount);
					updUrge.setDictDealType(TradeRecove.BLUE_CHARGE.getCode());
					paybackSplit.setDictDealType(TradeRecove.BLUE_CHARGE.getCode());
				}
			}else{
				// 正常的划扣平台
				updUrge.setDictDealType(backPlat);
				paybackSplit.setDictDealType(backPlat);
			}
			// 更新拆分表
			paybackSplit.setUrgeId(ids);
			paybackSplit.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			paybackSplit.setSplitBackDate(new Date());
			dao.updUrgeSplit(paybackSplit);
			
			// 更新催收主表
			updUrge.setId("'" + ids+ "'");
			updUrge.setUrgeDecuteMoeny(unDeductAmount);
			updUrge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			updUrge.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			updUrge.setUrgeDecuteDate(new Date());
			dao.updateUrge(updUrge);
			
			// 插入还款操作历史表,催收主表id，操作步骤，关联类型，操作结果，备注
			PaybackOpe paybackOpe = new PaybackOpe();
			paybackOpe.setrPaybackApplyId(null);
			paybackOpe.setrPaybackId(ids);
			paybackOpe.setDictLoanStatus(RepaymentProcess.RETRIVE.getCode());
			paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
			paybackOpe.setRemarks("以往列表追回");
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			paybackOpe.preInsert();
			paybackOpe.setOperator(UserUtils.getUser().getId());
			paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
			paybackOpe.setOperateTime(new Date());
			loanStatusHisDao.insertPaybackOpe(paybackOpe);
		}else {
			resultMessage = "不符合追回条件";
		}
		return resultMessage;
	}
	
	/**
	 * 放款当日待划扣追回功能
	 * 1.当日待划扣的追回和以往待划扣的追回功能差不多
	 * 2.是否开启自动划扣，追回的按钮都会显示
	 * 3.允许追回的条件，自动划扣状态为手动，同时划扣回盘结果为待划扣，划扣失败，处理中（导出）的数据可以进行追回
	 * 4.记录历史
	 * 2016年10月18日
	 * By 朱静越
	 * @param ids
	 * @param backPlat
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String deductBackDealToday(String ids,String backPlat) {
		String resultMessage = null;
		// 根据催收id查找催收实体。
		UrgeServicesMoney urgeServicesMoney = find(ids);
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		UrgeServicesMoney updUrge = new UrgeServicesMoney();
		UrgeServicesMoneyEx paybackSplit = new UrgeServicesMoneyEx();
		// 只有数据状态为划扣失败，待划扣，处理中（导出）的数据可以进行追回操作。
		if ((urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.PAYMENT_FAILED.getCode())
				||urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.PREPAYMENT.getCode())
				||urgeServicesMoney.getDictDealStatus().equals(UrgeCounterofferResult.PROCESSED.getCode()))&&
				(YESNO.NO.getCode().equals(urgeServicesMoney.getAutoDeductFlag()))) {
			BigDecimal unDeductAmount = urgeServicesMoney.getUrgeMoeny().subtract(urgeServicesMoney.getUrgeDecuteMoeny()).subtract(urgeServicesMoney.getAuditAmount());
			// 判断该单子选择的划扣平台是否是【蓝补冲抵】，如果是蓝补冲抵，需要通过合同编号获得蓝补金额进行判断,
			if (TradeRecove.BLUE_CHARGE.getCode().equals(backPlat)) {
				BigDecimal blueAmount = getBlueAmount(urgeServicesMoney.getContractCode());
				// 蓝补金额小于未划金额时
				if (blueAmount.compareTo(unDeductAmount)==-1) {
					resultMessage = GrantCommon.DEDUCT_BANCK_FAIL;
					return resultMessage;
				}else{
					Payback updPayback = new Payback();
					updPayback.setContractCode(urgeServicesMoney.getContractCode());
					updPayback.setPaybackBuleAmount(blueAmount.subtract(unDeductAmount));
					// 冲抵成功之后，更新蓝补金额
					dao.updBlue(updPayback);
					// 添加蓝补更新历史
			    	paybackBuleAmount.setTradeType(TradeType.TURN_OUT.getCode());
			    	paybackBuleAmount.setTradeAmount(unDeductAmount);
			    	paybackBuleAmount.setSurplusBuleAmount(blueAmount.subtract(unDeductAmount));
			    	paybackBuleAmount.setContractCode(urgeServicesMoney.getContractCode());
			    	paybackBuleAmount.preInsert();
			    	paybackBlueAmountDao.insertPaybackBuleAmount(paybackBuleAmount);
					updUrge.setDictDealType(TradeRecove.BLUE_CHARGE.getCode());
					paybackSplit.setDictDealType(TradeRecove.BLUE_CHARGE.getCode());
				}
			}else{
				// 正常的划扣平台
				updUrge.setDictDealType(backPlat);
				paybackSplit.setDictDealType(backPlat);
			}
			// 更新拆分表
			paybackSplit.setUrgeId(ids);
			paybackSplit.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			paybackSplit.setSplitBackDate(new Date());
			dao.updUrgeSplit(paybackSplit);
			
			// 更新催收主表
			updUrge.setId("'" + ids+ "'");
			updUrge.setUrgeDecuteMoeny(unDeductAmount);
			updUrge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			updUrge.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			updUrge.setUrgeDecuteDate(new Date());
			dao.updateUrge(updUrge);
			
			// 插入还款操作历史表,催收主表id，操作步骤，关联类型，操作结果，备注
			PaybackOpe paybackOpe = new PaybackOpe();
			paybackOpe.setrPaybackApplyId(null);
			paybackOpe.setrPaybackId(ids);
			paybackOpe.setDictLoanStatus(RepaymentProcess.RETRIVE.getCode());
			paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
			paybackOpe.setRemarks("当日追回");
			paybackOpe.setOperateResult(PaybackOperate.DEDECT_SUCCEED.getCode());
			paybackOpe.preInsert();
			paybackOpe.setOperator(UserUtils.getUser().getId());
			paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
			paybackOpe.setOperateTime(new Date());
			loanStatusHisDao.insertPaybackOpe(paybackOpe);
		}else {
			resultMessage = "不符合追回条件";
		}
		return resultMessage;
	}
	
	
	/**
	 * 导出富友处理
	 * 2016年4月25日
	 * By 朱静越
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public UrgeServicesMoneyEx saveExportFY(StringBuilder parameter) throws Exception{
		List<PaybackApply> payList = new ArrayList<PaybackApply>();
		UrgeServicesMoneyEx urgeEx = new UrgeServicesMoneyEx();
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		
		String idstring = parameter.toString();
		idstring = idstring.substring(0, parameter.lastIndexOf(","));
		urgeEx.setUrgeId(idstring);
		urgeServicesMoney.setId(idstring);
		// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子
		List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeEx);
		// 删除拆分表中存在的list
		delSplit(delList);
		// 获得要拆分的list
		payList = queryUrgeList(idstring);
		// 根据催收主表id，将选中的单子的划扣平台更新为富友平台，同时将处理状态更新为处理中(线下)
		urgeServicesMoney.setDictDealType(DeductPlat.FUYOU.getCode());
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESSED.getCode());
		urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESSED.getCode());
		dao.updateUrge(urgeServicesMoney);
		// 进行拆分，插入到拆分表
		paymentSplitService.splitList(payList,
				TargetWay.SERVICE_FEE.getCode(), DeductTime.RIGHTNOW, DeductPlat.FUYOU);
		// 获得要导出的list,根据催收主表id
		urgeEx.setUrgeId(idstring);
		urgeEx.setDictRDeductType(DeductWays.HJ_04.getCode());
		urgeEx.setDictDealType(DeductPlat.FUYOU.getCode());
		urgeEx.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
		//urgeFyList = getDeductsFy(urgeEx);
		
		/*
		//设置序号，从0000开始
		if (ArrayHelper.isNotEmpty(urgeFyList)) { 
			int count = 1; 
			for (UrgeServiceFyEx splitdata : urgeFyList) { 
				splitdata.setIndex(String.format("%05d", count)); 
				exportData.add(splitdata); 
				count ++; 
			} 
		}*/
		return urgeEx;
	}
	
	/**
	 * 导出好易联
	 * 2016年4月26日
	 * By 朱静越
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public UrgeServicesMoneyEx saveExportHYL(StringBuilder parameter) throws Exception{
		String idstring = null;
		List<PaybackApply> payList = new ArrayList<PaybackApply>();
		UrgeServicesMoneyEx urgeEx = new UrgeServicesMoneyEx();
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		idstring = parameter.toString();
		idstring = idstring.substring(0, parameter.lastIndexOf(","));
		urgeEx.setUrgeId(idstring);
		urgeServicesMoney.setId(idstring);
		// 删除拆分表中已经存在的划扣失败的单子
		List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeEx);
		// 删除拆分表中存在的list
		delSplit(delList);
		// 获得要拆分的list
		payList = queryUrgeList(idstring);
		// 根据催收主表id，将选中的单子的划扣平台更新为富友平台，同时将处理状态更新为处理中(线下)
		urgeServicesMoney.setDictDealType(DeductPlat.HAOYILIAN.getCode());
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESSED
				.getCode());
		urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESSED
				.getCode());
		dao.updateUrge(urgeServicesMoney);
		// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
		paymentSplitService.splitList(payList,
				TargetWay.SERVICE_FEE.getCode(), DeductTime.RIGHTNOW, DeductPlat.HAOYILIAN);
		// 获得要导出的list,根据催收主表id
		urgeEx.setDictRDeductType(DeductWays.HJ_04.getCode());
        urgeEx.setDictDealType(DeductPlat.HAOYILIAN.getCode());
		return urgeEx;
	}
	
	/**
	 * 导出中金相关信息
	 * 2016年4月26日
	 * By 朱静越
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public UrgeServicesMoneyEx saveExportZJ(StringBuilder parameter) throws Exception{
		String idstring = parameter.toString();
		idstring = idstring.substring(0,parameter.lastIndexOf(","));
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		UrgeServicesMoneyEx urgeEx = new UrgeServicesMoneyEx();
		urgeEx.setUrgeId(idstring);
		urgeServicesMoney.setId(idstring);
		// 根据催收主表id，将选中的单子的划扣平台更新为富友平台，同时将处理状态更新为处理中(线下)
		urgeServicesMoney.setDictDealType(DeductPlat.ZHONGJIN.getCode());
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESSED.getCode());
		urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESSED.getCode());
		// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子
		List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeEx);
		// 删除拆分表中存在的list
		delSplit(delList);
		// 获得要拆分的list
		List<PaybackApply> payList = queryUrgeList(idstring);
		dao.updateUrge(urgeServicesMoney);
		// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
		paymentSplitService.splitList(payList,
				TargetWay.SERVICE_FEE.getCode(), DeductTime.RIGHTNOW, DeductPlat.ZHONGJIN);
		// 获得要导出的list,根据催收主表id
		urgeEx.setDictRDeductType(DeductWays.HJ_04.getCode());
        urgeEx.setDictDealType(DeductPlat.ZHONGJIN.getCode());
        //List<UrgeServiceZJEx> urgeZJList = getDeductsZJ(urgeEx);
		// 单子导出之后，将拆分表中的回盘结果更新为处理中
		/*if (ArrayHelper.isNotEmpty(urgeZJList)) {
			for (int i = 0; i < urgeZJList.size(); i++) {
				urgeEx.setId(urgeZJList.get(i).getId());
				urgeEx.setSplitResult(UrgeCounterofferResult.PROCESSED.getCode());
				dao.updSplitStatus(urgeEx);
			}
		}*/	
		return urgeEx;
	}
	
	/**
	 * 导出通联
	 * 2016年4月27日
	 * By 朱静越
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public UrgeServicesMoneyEx saveExportTL(StringBuilder parameter) throws Exception{
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		UrgeServicesMoneyEx urgeEx=new UrgeServicesMoneyEx();
		List<PaybackApply> payList = new ArrayList<PaybackApply>();
		String idstring  = parameter.toString();
		idstring = idstring.substring(0,parameter.lastIndexOf(","));
		urgeEx.setUrgeId(idstring);
		urgeServicesMoney.setId(idstring);
		// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子
		List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeEx);
		// 删除拆分表中存在的list
		delSplit(delList);
		// 获得要拆分的list
		payList = queryUrgeList(idstring);
		urgeServicesMoney.setDictDealType(DeductPlat.TONGLIAN.getCode());
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESSED.getCode());
		urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESSED.getCode());
		dao.updateUrge(urgeServicesMoney);
		// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
		paymentSplitService.splitList(payList,
				TargetWay.SERVICE_FEE.getCode(), DeductTime.RIGHTNOW, DeductPlat.TONGLIAN);
		// 获得要导出的list,根据催收主表id
		urgeEx.setDictRDeductType(DeductWays.HJ_04.getCode());
        urgeEx.setDictDealType(DeductPlat.TONGLIAN.getCode());
		return urgeEx;
	}
	
	/**
	 * 导入富友处理
	 * 2016年4月26日
	 * By 朱静越
	 * @param lst
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void importFY(List<UrgeServiceFyInputEx> lst){
	    String serialNo = null;
        String contractCode = null;
        String tracStatus = null;
		String remark = null;
		StringBuilder parameter = new StringBuilder();
		for (int i = 0; i < lst.size(); i++) {
			UrgeServicesMoneyEx urgeMoney = new UrgeServicesMoneyEx();
			// 获得表格中的交易状态
			serialNo = lst.get(i).getEnterpriseSerialno();
			if(StringUtils.isNotEmpty(serialNo)){
			    tracStatus = lst.get(i).getTradingStatus();
			    contractCode = serialNo.substring(0, serialNo.indexOf("_"));
			    urgeMoney.setEnterpriseSerialno(serialNo);
			    if(parameter.indexOf(contractCode)==-1){
			        parameter.append("'"+serialNo+"',");
			    }
			    if (tracStatus.equals("商户已复核,交易成功")) {
			        urgeMoney.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED
								.getCode());
			    } else {
			    	
			        urgeMoney.setSplitResult(UrgeCounterofferResult.PAYMENT_FAILED
								.getCode());
			        
			        urgeMoney.setSplitFailResult(lst.get(i).getReason());
			    }
			    // 更新拆分表
			    dao.updUrgeSplit(urgeMoney);
			}
		}
		// 更新催收主表
		remark = parameter.toString().substring(0,parameter.lastIndexOf(","));
		if(StringUtils.isNotEmpty(remark)){
		    String[] remarks = remark.split(",");
		    UrgeServicesMoney updUrgeServices = null;
		    for(String curRemark:remarks){
		        updUrgeServices = selectSuccess(curRemark);
		        updUrgeServices.setId("'" + updUrgeServices.getId() + "'");
		        if (updUrgeServices.getSuccessAmount() > 0) {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED
							.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_FAILED
							.getCode());
		        } else {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED
							.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED
							.getCode());
		        }
		        updUrgeServices.setUrgeDecuteDate(new Date());
		        updUrgeServices.setDeductYesno(YESNO.YES.getCode());
		        dao.updateUrge(updUrgeServices);
		    }
		}
	}
	
	/**
	 * 导入好易联
	 * 2016年4月27日
	 * By 朱静越
	 * @param lst
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveImportHYL(List<UrgeServiceHylInputEx> lst){
		StringBuilder parameter = new StringBuilder();
		for (int i = 0; i < lst.size(); i++) {
			UrgeServicesMoneyEx urgeMoneyEx = new UrgeServicesMoneyEx();
			// 获得表格中的交易状态
			String tracStatus = lst.get(i).getTradeResult();
			// 获得表格中的企业流水号
			String serialNo = lst.get(i).getEnterpriseSerialno();
			if (StringUtils.isNotEmpty(serialNo)) {
	            urgeMoneyEx.setEnterpriseSerialno(serialNo);
                if(parameter.indexOf(serialNo.substring(0, serialNo.indexOf("_")))==-1){
                    parameter.append("'"+serialNo+"',");
                }
				// 设置划扣标识
				urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
				// 获得原因
				urgeMoneyEx.setSplitFailResult(lst.get(i).getReason());
				if (tracStatus.equals("成功")) {
					urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
				} else {
					urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
				}
				// 更新拆分表
				dao.updUrgeSplit(urgeMoneyEx);
			}
		}
		String remark = parameter.toString().substring(0,parameter.lastIndexOf(","));
		if(StringUtils.isNotEmpty(remark)){
		    String[] remarks = remark.split(",");
		    UrgeServicesMoney updUrgeServices = null;
		    for(String curRemark:remarks){
		        updUrgeServices = selectSuccess(curRemark);
		        updUrgeServices.setId("'" + updUrgeServices.getId() + "'");
		        if (updUrgeServices.getSuccessAmount() > 0) {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		        } else {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		        }
		        updUrgeServices.setUrgeDecuteDate(new Date());
		        dao.updateUrge(updUrgeServices);
		    }
		}
	}
	
	/**
	 * 导入通联处理
	 * 2016年4月27日
	 * By 朱静越
	 * @param lst
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveImportTL(List<UrgeServiceTlInputEx> lst){
	    String serialNo = null;
	    String tracStatus =null;
		StringBuilder parameter = new StringBuilder();
		for (int i = 0; i < lst.size(); i++) {
			UrgeServicesMoneyEx urgeMoneyEx=new UrgeServicesMoneyEx();
			// 获得表格中的交易状态
			tracStatus=lst.get(i).getResult();
			// 获得表格中的保留字段
			serialNo = lst.get(i).getEnterpriseSerialno();
			if(StringUtils.isNotEmpty(serialNo)){
			    urgeMoneyEx.setEnterpriseSerialno(serialNo);
			    if(parameter.indexOf(serialNo.substring(0, serialNo.indexOf("_")))==-1){
			        parameter.append("'"+serialNo+"',");
			    }
			    // 设置划扣标识
			    urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
			    // 获得原因
			    urgeMoneyEx.setSplitFailResult(lst.get(i).getReason());
			    if (tracStatus.equals("成功")) {
			        urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			    }else {
			        urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
			    }
			    // 更新拆分表
			    dao.updUrgeSplit(urgeMoneyEx);
			}
		}
		String remark = parameter.toString().substring(0,parameter.lastIndexOf(","));
		if(StringUtils.isNotEmpty(remark)){
		    String[] remarks = remark.split(",");
		    UrgeServicesMoney updUrgeServices = null;
		    for(String curRemark:remarks){
		        updUrgeServices = selectSuccess(curRemark);
		        updUrgeServices.setId("'"+updUrgeServices.getId()+"'");
		        if (updUrgeServices.getSuccessAmount()>0) {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		        }else {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		        }
		        updUrgeServices.setUrgeDecuteDate(new Date());
		        updUrgeServices.setDeductYesno(YESNO.YES.getCode());

		        dao.updateUrge(updUrgeServices);
		    }
		}
	}
	
	/**
	 * 导入中金处理
	 * 2016年4月27日
	 * By 朱静越
	 * @param dataList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveImportZJ(List<UrgeServiceZJInputEx> dataList){
		StringBuilder parameter = new StringBuilder();
	    String serialNo = null;
        for(UrgeServiceZJInputEx urgeServiceItem : dataList){
        	UrgeServicesMoneyEx urgeMoneyEx = new UrgeServicesMoneyEx();
        	// 获得表格中的交易状态
        	String tracStatus = urgeServiceItem.getTransactionStauts();
        	// 获得表格中的备注信息
        	serialNo = urgeServiceItem.getEnterpriseSerialno();
        	if(StringUtils.isNotEmpty(serialNo)){
        		urgeMoneyEx.setEnterpriseSerialno(serialNo);
        		if(parameter.indexOf(serialNo.substring(0, serialNo.indexOf("_")))==-1){
					parameter.append("'" + serialNo + "',");
        		}
        		// 设置划扣标识
        		urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
        		// 获得银行响应消息
        		urgeMoneyEx.setSplitFailResult(urgeServiceItem.getBankRespondMsg());
        		if (tracStatus.equals(ResultConstants.SUCCESS_DESC)) {
        			urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
        		}else {
        			urgeMoneyEx.setSplitResult(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
        		}
        		// 更新拆分表
        		dao.updUrgeSplit(urgeMoneyEx);
        	}
        }
        String remark = parameter.toString().substring(0,parameter.lastIndexOf(","));
		if(StringUtils.isNotEmpty(remark)){
		    String[] remarks = remark.split(",");
		    UrgeServicesMoney updUrgeServices = null;
		    for(String curRemark:remarks){
		        updUrgeServices = selectSuccess(curRemark);
		        updUrgeServices.setId("'"+updUrgeServices.getId()+"'");
		        if (updUrgeServices.getSuccessAmount()>0) {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		        }else {
		            updUrgeServices.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		            updUrgeServices.setDeductStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
		        }
		        updUrgeServices.setDeductYesno(YESNO.YES.getCode());
		        updUrgeServices.setUrgeDecuteDate(new Date());
		        dao.updateUrge(updUrgeServices);
		    }
		}
	}
	
	/**
	 * 催收保证金待催收页面提交划扣
	 * 2016年4月28日
	 * By 朱静越
	 * @param deductReq
	 * @param urgeServicesMoney
	 * @param rPaybackId
	 * @return
	 */
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String sendUrgeDeduct(DeductReq deductReq,UrgeServicesMoney urgeServicesMoney,String rPaybackId){
		String flag = null;
		DeResult t = TaskService.addTask(deductReq);
		try {
			UrgeServicesMoneyEx urgeServices = new UrgeServicesMoneyEx();
			if(t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())){
				urgeServicesMoney.setId("'"+rPaybackId+"'");
				urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
				urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESS.getCode());
				urgeServicesMoney.setUrgeDecuteDate(new Date());
				dao.updateUrge(urgeServicesMoney);
				// 将拆分表中为失败的和处理中的单子删除
				urgeServices.setUrgeId("'" + rPaybackId + "'");
				// 发送成功之后，获得将拆分表中处理状态为处理中（导出）和失败的单子进行删除
				List<UrgeServicesMoneyEx> delList = selectToDealOnLine(urgeServices);
				// 删除
				if (delList.size()>0) {
					for (int i = 0; i < delList.size(); i++) {
						dao.delProcess("'"+delList.get(i).getId()+"'");
					}
				}
				flag = "success";
				
				// 插入历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(rPaybackId);
				paybackOpe.setDictLoanStatus(RepaymentProcess.SEND_DEDUCT.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks("(催收保证金)"+GrantCommon.SEND_DEDUCT);
				paybackOpe.setOperateResult(PaybackOperate.SEND_SUCCESS.getCode());
				paybackOpe.preInsert();

				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				loanStatusHisDao.insertPaybackOpe(paybackOpe);
				
				TaskService.commit(t.getDeductReq());
			}else {
				
				// 插入历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(rPaybackId);
				paybackOpe.setDictLoanStatus(RepaymentProcess.SEND_DEDUCT.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks("(催收保证金)"+GrantCommon.SEND_DEDUCT);
				paybackOpe.setOperateResult(PaybackOperate.SEND_FAILED.getCode());
				paybackOpe.preInsert();

				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				loanStatusHisDao.insertPaybackOpe(paybackOpe);
				
				flag = "划扣失败"+t.getReMge();
				TaskService.rollBack(t.getDeductReq());
			}
		} catch (Exception e) {
			e.printStackTrace();
			TaskService.rollBack(t.getDeductReq());
			logger.error("提交划扣失败，发生异常",e);
			throw new ServiceException("提交划扣失败");
		}finally{
			return flag;
		}
	}
	
}
