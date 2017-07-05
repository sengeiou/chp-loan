package com.creditharmony.loan.borrow.refund.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendBackmoneyInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendBackmoneyOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ListUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.AppStatus;
import com.creditharmony.core.loan.type.AppType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.refund.dao.RefundDao;
import com.creditharmony.loan.borrow.refund.entity.AlreadyRefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.ExamineExportExcel;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.borrow.refund.entity.Refund;
import com.creditharmony.loan.borrow.refund.entity.RefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.RefundImportExcel;
import com.creditharmony.loan.borrow.refund.entity.RefundServiceFee;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 退款
 * @Class Name GrantDeductsService
 * @author 朱静越
 * @Create In 2015年4月20日
 */
@Service("longRefundService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class LongRefundService extends CoreManager<RefundDao, Refund>{
	@Autowired
	private PaybackService applyPayService;
	
	@Autowired 
	private PaybackBlueAmountService blusAmountService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private PaybackDao paybackDao;
	
	@Autowired
	private ContractDao contractDao;
	/**
	 * 退款(列表、数据管理部)
	 * 2016年4月20日
	 * @param page
	 * @return 分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Refund> selectRefundList(Page<Refund> page,Refund refund){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<Refund> pageList = (PageList<Refund>)dao.selectRefundList(pageBounds, refund);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	public Refund selectRefundTotal(Refund refund){
		return dao.selectRefundListForSumAndCount(refund);
	}
	
	/**
	 * 查询夏总账号
	 */
	public List<String> selectMiddlePerson(String middleName){
		 return dao.selectMiddlePerson(middleName);
	}
	/**
	 * 查询催收服务费退款数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<RefundServiceFee> getRfundServiceFeeList(Page<RefundServiceFee> page,RefundServiceFee refundServiceFee){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("r_charge_id");
		PageList<RefundServiceFee> pageList = (PageList<RefundServiceFee>)dao.getRfundServiceFeeList(pageBounds, refundServiceFee);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 退票
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void editRefundTicket(Refund refund){
		refund.setContractCode(refund.getContractCodeA());
		dao.updateRefundTicket(refund);
		User user = UserUtils.getUser();
		if(refund.getAppTypeA().equals(AppType.BLUE_RETURN.getCode())){
			dao.updatePaybackBule(refund);
			PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
			paybackBuleAmount.setId(IdGen.uuid());
			paybackBuleAmount.setContractCode(refund.getContractCode());
	    	paybackBuleAmount.setTradeType(TradeType.TRANSFERRED.getCode());
	    	paybackBuleAmount.setTradeAmount(refund.getRefundMoney());
	    	paybackBuleAmount.setDictDealUse("蓝补退款退回"); // 退款内容
	    	paybackBuleAmount.setSurplusBuleAmount(dao.getPaybackBule(refund.getContractCode()));
	    	paybackBuleAmount.setCreateBy(user.getId());
	    	paybackBuleAmount.setCreateTime(new Date());
	    	paybackBuleAmount.setModifyBy(user.getId());
	    	paybackBuleAmount.setModifyTime(new Date());
			blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		}
		PaybackHistory history = new PaybackHistory();
		history.setContractCode(refund.getContractCode());
		history.setId(IdGen.uuid());
		history.setOperName("退票");
		history.setOperNotes("退票(数据管理部)");
		history.setOperResult("退票列表(退票)");
		history.setCreateBy(user.getId());
		history.setModifyBy(user.getId());
		dao.insertHistory(history);
	}

	/**
	 * 批量退回
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void batchReturn(Refund refund,List<Refund> list){
		User user = UserUtils.getUser();
		if(ListUtils.isNotEmptyList(list)){
			for(Refund r : list){
				r.setAppStatus(AppStatus.FAIL_RETURN.getCode());
				r.setRemark(refund.getRemarkA());
				dao.updateRefundTicket(r);
				if(r.getAppType().equals(AppType.BLUE_RETURN.getCode())){
					dao.updatePaybackBule(r);
					PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
					paybackBuleAmount.setId(IdGen.uuid());
					paybackBuleAmount.setContractCode(r.getContractCode());
			    	paybackBuleAmount.setTradeType(TradeType.TRANSFERRED.getCode());
			    	paybackBuleAmount.setTradeAmount(r.getRefundMoney());
			    	paybackBuleAmount.setDictDealUse("蓝补退款批量退回"); // 退款内容
			    	paybackBuleAmount.setSurplusBuleAmount(dao.getPaybackBule(r.getContractCode()));
			    	
			    	paybackBuleAmount.setCreateBy(user.getId());
			    	paybackBuleAmount.setCreateTime(new Date());
			    	paybackBuleAmount.setModifyBy(user.getId());
			    	paybackBuleAmount.setModifyTime(new Date());
					blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
				}
				PaybackHistory history = new PaybackHistory();
				history.setContractCode(r.getContractCode());
				history.setId(IdGen.uuid());
				history.setOperName("批量退回");
				history.setOperNotes(refund.getRemarkA());
				history.setOperResult("退票列表(批量退回)");
				history.setCreateBy(user.getId());
				history.setModifyBy(user.getId());
				dao.insertHistory(history);
			}
		}
	}
	
	/**
	 * 批量成功
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveRefund(Refund refund,List<Refund> list){
		User user = UserUtils.getUser();
		if(ListUtils.isNotEmptyList(list)){
			for(Refund r : list){
				//r.setRefundBank(refund.getRefundBank());
				//r.setRefundTime(refund.getRefundTime());
				//r.setAppStatus(AppType.HAS_RETURN.getCode());
				refund.setAppStatus(AppStatus.HAS_RETURN.getCode());
				refund.setId(r.getId());
				refund.setModifyBy(user.getId());
				refund.setModifyTime(new Date());
				dao.updateRefundTicket(refund);
				PaybackHistory history = new PaybackHistory();
				history.setContractCode(r.getContractCode());
				history.setId(IdGen.uuid());
				history.setOperName("批量成功");
				history.setOperNotes("批量成功");
				history.setOperResult("退款列表(批量成功)");
				history.setCreateBy(user.getId());
				history.setModifyBy(user.getId());
				dao.insertHistory(history);
				if(AppType.URGE_RETURN.getCode().equals(r.getAppType())){
					//删除t_jk_service_charge_return表中数据
					//dao.deleteRfundServiceFee(r.getUrgeid());
					if(r.getUrgeid()!=null&&!"".equals(r.getUrgeid())){
						dao.udateRfundServiceFee(FeeReturn.RETURNED.getCode(), new Date(), r.getUrgeid());
					}
				}
			}
		}
	}
	
	/**
	 * 退款
	 * 上传回执结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void importExcel(List<RefundImportExcel> list){
		User user = UserUtils.getUser();
		for(RefundImportExcel r : list){
			Refund re = new Refund();
			re.setRemark(r.getRemark());
			re.setAppStatus(AppStatus.HAS_RETURN.getCode());
			re.setModifyTime(new Date());
			re.setRefundBank(r.getRefundBank());
			re.setBackDate(new Date(r.getRefundTime()));
			/*if(r.getRefundTime()!=null && !"".equals(r.getRefundTime())){
				Calendar c = new GregorianCalendar(1900,0,-1);  
		        Date d = c.getTime();  
		        Date currentDate = DateUtils.addDays(d, Integer.parseInt(r.getRefundTime()));  
				re.setBackDate(currentDate);
			}*/
			re.setId(r.getRefundId());
			dao.updateRefundTicket(re);
			PaybackHistory history = new PaybackHistory();
			history.setContractCode(r.getContractCode());
			history.setCreateBy(user.getId());
			history.setId(IdGen.uuid());
			history.setOperName("上传回执结果成功");
			history.setOperNotes("上传回执结果");
			history.setOperResult("退票列表(上传回执结果)");
			history.setModifyBy(user.getId());
			dao.insertHistory(history);
			if(AppType.URGE_RETURN.getCode().equals(re.getAppType())){
				//删除t_jk_service_charge_return表中数据
				//dao.deleteRfundServiceFee(r.getUrgeid());
				if(re.getUrgeid()!=null&&!"".equals(re.getUrgeid())){
					dao.udateRfundServiceFee(FeeReturn.RETURNED.getCode(), new Date(), re.getUrgeid());
				}
			}
		}
	}
	
	/**
	 * 蓝补退款
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean refundPaybackBlue(Payback payback, BigDecimal paybackBuleAmountLast, String paybackBuleReson, Refund refund) {
		// 修改蓝补
		User user = UserUtils.getUser();
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		BigDecimal blueAmount = payback.getPaybackBuleAmount();
		
		payback.setContractCode(payback.getContractCode());
//		payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
		payback.setPaybackBuleAmount(paybackBuleAmountLast);
		payback.setModifyTime(new Date());
		payback.setModifyBy(user.getId());
		payback.preUpdate();
		applyPayService.updatePayback(payback);
		paybackBuleAmount.setId(IdGen.uuid());
		paybackBuleAmount.setContractCode(payback.getContractCode());
    	paybackBuleAmount.setTradeType(TradeType.TURN_OUT.getCode());
    	paybackBuleAmount.setTradeAmount(blueAmount);
    	paybackBuleAmount.setDictDealUse("蓝补退款"); // 退款内容
    	paybackBuleAmount.setSurplusBuleAmount(payback.getPaybackBuleAmount());
    	paybackBuleAmount.setCreateBy(user.getId());
    	paybackBuleAmount.setCreateTime(new Date());
    	paybackBuleAmount.setModifyBy(user.getId());
    	paybackBuleAmount.setModifyTime(new Date());
		blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		
		String paybackApplyId = null;
		if (payback.getPaybackApply() != null) {
			paybackApplyId = payback.getPaybackApply().getId();
		}

		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.CONFIRM,
				TargetWay.PAYMENT, "成功", "");
		
		historyService.insertPaybackOpe(paybackOpes);
		
		dao.insert(refund);
		PaybackHistory history = new PaybackHistory();
		history.setContractCode(refund.getContractCode());
		history.setCreateBy(user.getId());
		history.setId(IdGen.uuid());
		history.setOperName("蓝补退款");
		history.setOperNotes("待审核");
		history.setOperResult("蓝补退款已提交，待审核");
		history.setModifyBy(user.getId());
		dao.insertHistory(history);
		
		return true;
	}

	/**
	 * 催收服务费退款
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean refundServiceFee(Refund refund) {
		User user = UserUtils.getUser();
		//修改催收服务费状态
		dao.insert(refund);
		PaybackHistory history = new PaybackHistory();
		history.setContractCode(refund.getContractCode());
		history.setCreateBy(user.getId());
		history.setId(IdGen.uuid());
		history.setOperName("催收服务费退款");
		history.setOperNotes("待审核");
		history.setOperResult("催收服务费退款已提交，待审核");
		history.setModifyBy(user.getId());
		dao.insertHistory(history);
		
		return true;
		
	}
	
	/**
	 * 催收服务费退款
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updateRefundServiceFee(Refund refund) {
		User user = UserUtils.getUser();
		//修改催收服务费状态
		dao.update(refund);
		PaybackHistory history = new PaybackHistory();
		history.setContractCode(refund.getContractCode());
		history.setCreateBy(user.getId());
		history.setId(IdGen.uuid());
		if(refund.getAppType().equals(AppType.BLUE_RETURN.getCode())){
			history.setOperName("蓝补退款");
			history.setOperResult("蓝补退款已提交，待审核");
		}else{
			history.setOperName("催收服务费退款");
			history.setOperResult("催收服务费退款已提交，待审核");
		}
		history.setOperNotes("待审核");
		history.setModifyBy(user.getId());
		dao.insertHistory(history);
		
		if(refund.getAppType().equals(AppType.BLUE_RETURN.getCode())){
			Payback payback = new Payback();
			payback.setContractCode(refund.getContractCode());
			PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
			List<Payback> paybackList = dao.findByContractCode(payback);
			BigDecimal paybackBuleAmountLast = new BigDecimal("0.00");
			if(!paybackList.isEmpty()){
				paybackBuleAmountLast = paybackList.get(0).getPaybackBuleAmount();
			}
			payback.setContractCode(refund.getContractCode());
//			payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
			payback.setPaybackBuleAmount(paybackBuleAmountLast.subtract(refund.getRefundMoney()));
			payback.preUpdate();
			applyPayService.updatePayback(payback);
			paybackBuleAmount.setId(IdGen.uuid());
			paybackBuleAmount.setContractCode(payback.getContractCode());
	    	paybackBuleAmount.setTradeType(TradeType.TURN_OUT.getCode());
	    	paybackBuleAmount.setTradeAmount(refund.getRefundMoney());
	    	paybackBuleAmount.setDictDealUse("蓝补退款"); // 退款内容
	    	paybackBuleAmount.setSurplusBuleAmount(paybackBuleAmountLast.subtract(refund.getRefundMoney()));
	    	paybackBuleAmount.setCreateBy(user.getId());
	    	paybackBuleAmount.setCreateTime(new Date());
	    	paybackBuleAmount.setModifyBy(user.getId());
	    	paybackBuleAmount.setModifyTime(new Date());
	    	blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		}
		
		return true;
		
	}
	
	/** 查询退款人信息 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,Object>> getInfo(String contractCode){
		return dao.getInfo(contractCode);
	}
	
	/** 查询退款数据 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Refund> selectRefundById(Refund refund){
		return dao.selectRefundById(refund);
	}
	
	/** 审核 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void update(Refund refund,String type) throws Exception {
		User user = UserUtils.getUser();
			List info = dao.getInfoByModifyTime(refund.getId(), refund.getMt());
			if(!info.isEmpty()){
				Refund r = new Refund();
				r.setIdArray(new String[]{refund.getId()});
				//refund.setId(refund.getId());
				dao.update(refund);
				List<Refund> list = dao.selectRefundById(r);
				BigDecimal backAmount = new BigDecimal("0.00");
				String appType = null;
				String contractCode = null;
				if(!list.isEmpty()){
					backAmount = list.get(0).getRefundMoney();
					appType = list.get(0).getAppType();
					contractCode = list.get(0).getContractCode();
				}
				PaybackHistory history = new PaybackHistory();
				if("end".equals(type)){
					history.setContractCode(contractCode);
					history.setCreateBy(user.getId());
					history.setModifyBy(user.getId());
					history.setId(IdGen.uuid());
					
					if("1".equals(refund.getZcResult())){
						
						history.setOperName("终审");
						history.setOperNotes(refund.getZcRemark());
						history.setOperResult("终审通过，待退款");
						//将退款申请信息推送给大金融
						sendDjrRefundData(refund);
					}else{
						history.setOperName("终审");
						history.setOperNotes(refund.getZcRemark());
						history.setOperResult("终审退回");
						if(AppType.BLUE_RETURN.getCode().equals(appType)){
							updatePaybackBlue(contractCode, backAmount, refund.getZcRemark());
						}
					}
					history.setModifyBy(refund.getModifyBy());
				}else{
					history.setContractCode(contractCode);
					history.setCreateBy(user.getId());
					history.setId(IdGen.uuid());
					if("1".equals(refund.getFkResult())){
						history.setOperName("初审");
						history.setOperNotes(refund.getFkRemark());
						history.setOperResult("初审通过，待终审");
					}else{
						history.setOperName("初审");
						history.setOperNotes(refund.getFkRemark());
						history.setOperResult("初审退回");
						if(AppType.BLUE_RETURN.getCode().equals(appType)){
							updatePaybackBlue(contractCode, backAmount, refund.getFkRemark());
						}
					}
					history.setModifyBy(refund.getModifyBy());
				}
				dao.insertHistory(history);
			}else{
				System.out.println("已被审核");
			}
	}

	/**
	 * 修改蓝补金额
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updatePaybackBlue(String contractCode, BigDecimal backAmount, String paybackBuleReson) {
		// 修改蓝补
		User user = UserUtils.getUser();
		Payback payback = new Payback();
		payback.setContractCode(contractCode);
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		List<Payback> paybackList = dao.findByContractCode(payback);
		BigDecimal paybackBuleAmountLast = new BigDecimal("0.00");
		if(!paybackList.isEmpty()){
			paybackBuleAmountLast = paybackList.get(0).getPaybackBuleAmount();
		}
		payback.setContractCode(contractCode);
//		payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
		payback.setPaybackBuleAmount(backAmount.add(paybackBuleAmountLast));
		payback.preUpdate();
		applyPayService.updatePayback(payback);
		paybackBuleAmount.setId(IdGen.uuid());
		paybackBuleAmount.setContractCode(payback.getContractCode());
    	paybackBuleAmount.setTradeType(TradeType.TRANSFERRED.getCode());
    	paybackBuleAmount.setTradeAmount(backAmount);
    	paybackBuleAmount.setDictDealUse("蓝补退款退回"); // 退款内容
    	paybackBuleAmount.setSurplusBuleAmount(backAmount.add(paybackBuleAmountLast));
    	paybackBuleAmount.setCreateBy(user.getId());
    	paybackBuleAmount.setCreateTime(new Date());
    	paybackBuleAmount.setModifyBy(user.getId());
    	paybackBuleAmount.setModifyTime(new Date());
    	blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		return true;
	}

	/**
	 * 退款导出Excel
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<RefundExportExcel> refundExportList(Refund refund){
		List<RefundExportExcel> l= dao.refundExportList(refund);
		return l;
	}
	
	/**
	 * 退款导出Excel
	 */
	public  List <ExamineExportExcel> examineExportExcelList(Refund refund){
		return dao.examineExportExcelList(refund);
	}
	
	/**
	 * 已退款导出Excel
	 */
	public List <AlreadyRefundExportExcel> alreadyRefundDataExcel(Refund refund){
		return dao.alreadyRefundDataExcel(refund);
	}
	
	/**
	 * 查询还款信息
	 */
	public List<Map<String,Object>> findPayback(String contractCode,String modifyTime){
		return dao.findPayback(contractCode, modifyTime);
	}
	
	/**
	 * 查询是否有刚提交过的催收服务费退款
	 */
	public List<Map<String,Object>> getServiceFeeInfo(String contractCode,String appType,String appStatus){
		return dao.getServiceFeeInfo(contractCode, appType, appStatus);
	}
	
	/**
	 * 查询还款日当天,退款金额=蓝补余额-当期期供未还金额-未还违约金及罚息总额
	 */
	public BigDecimal getTkMoney(String contractCode) {
		return dao.getTkMoney(contractCode);
	};
	
	/**
	 * 查询非还款日当天,退款金额 = 蓝补金额-逾期期供金额-未还违约金及罚息总额
	 */
	public BigDecimal getTkMoney2(String contractCode) {
		return dao.getTkMoney2(contractCode);
	};
	
	/**
	 * 终审后的退款信息推送给大金融
	 * @author 于飞
	 * @Create 2016年10月10日
	 */
	public void sendDjrRefundData(Refund refund){
		logger.info("------开始推送大金融退款，合同编号"+refund.getContractCode()+"-----");
		Contract contract = contractDao.findByContractCode(refund.getContractCode());
		if(!contract.getChannelFlag().equals(ChannelFlag.ZCJ.getCode()))
			return;
		//发送冲抵记录信息----------------
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_BACKMONEY_SERVICE);
		DjrSendBackmoneyInBean inParam = new DjrSendBackmoneyInBean(); 
		inParam.setContractCode(refund.getContractCode());
		inParam.setApplyTime(refund.getCreateTimes());
		inParam.setBackMoney(refund.getRefundMoney());
		inParam.setOrderId(refund.getId());
		//1:蓝补退款  2：催收服务费退款
		String type="1";
		if(refund.getAppType()!=null && !"".equals(refund.getAppType())
				&& refund.getAppType().equals("1"))
			type="2";
		inParam.setType(type);
		inParam.setDataTransferId(System.currentTimeMillis()+"");
		//查询还款信息，是否已结清
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contractCode", refund.getContractCode());
		Payback pay = paybackDao.selectpayBack(map);
		String isSettle="1";
		if(pay.getDictPayStatus().equals(RepayStatus.SETTLE.getCode()) ||
				pay.getDictPayStatus().equals(RepayStatus.PRE_SETTLE.getCode()))
			isSettle="0";
		inParam.setIsSettle(isSettle);
			
		DjrSendBackmoneyOutBean outParam = (DjrSendBackmoneyOutBean) service.callService(inParam); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("------退款推送大金融成功，合同编号"+refund.getContractCode()+"-----");
		} else { 
			// TODO 失败 
			logger.info("------退款推送大金融失败，合同编号"+refund.getContractCode()+"-----");
		} 
		
		
	}
}
