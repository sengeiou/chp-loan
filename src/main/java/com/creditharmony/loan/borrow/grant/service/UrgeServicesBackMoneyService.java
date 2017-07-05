package com.creditharmony.loan.borrow.grant.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendBackmoneyInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendBackmoneyOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesBackMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeHistory;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesBackMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.refund.entity.Refund;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 催收服务费返款中的各种操作
 * @Class Name UrgeServicesBackMoneyServic
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@Service("urgeServicesBackMoneyService")
public class UrgeServicesBackMoneyService extends CoreManager<UrgeServicesBackMoneyDao, UrgeServicesBackMoneyEx>{
	@Autowired
	private PaybackDao paybackDao;
	
	@Autowired
	private ContractDao contractDao;
	/**
	 * 催收服务费返款申请列表查询
	 * 2016年1月11日
	 * By 朱静越
	 * @param page
	 * @param urgeServicesBackMoneyEx
	 * @return 分页对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public  Page<UrgeServicesBackMoneyEx>  selectBackMoneyApply(Page<UrgeServicesBackMoneyEx> page,UrgeServicesBackMoneyEx urgeServicesBackMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeServicesBackMoneyEx> pageList = (PageList<UrgeServicesBackMoneyEx>)dao.selectBackMoneyApply(pageBounds, urgeServicesBackMoneyEx);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (UrgeServicesBackMoneyEx urgeServices : pageList) {
			urgeServices.setBankNameLabel(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, urgeServices.getBankName()));
			urgeServices.setDictLoanStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, urgeServices.getDictLoanStatus()));
			urgeServices.setDictPayStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.URGE_REPAY_STATUS, urgeServices.getDictPayStatus()));
			urgeServices.setDictPayResultLabel(DictUtils.getLabel(dictMap,LoanDictType.PAYBACK_FEE_RESULT, urgeServices.getDictPayResult()));
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询不带分页的列表
	 * 2016年2月23日
	 * By 朱静越
	 * @param urgeServicesBackMoneyEx 查询条件
	 * @return 列表
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeServicesBackMoneyEx> selectBackMoneyApplyNo(UrgeServicesBackMoneyEx urgeServicesBackMoneyEx){
		return dao.selectBackMoneyApply(urgeServicesBackMoneyEx);
	}
	
	/**
	 * 根据催收返款主表id进行查询实体
	 * 2016年2月23日
	 * By 朱静越
	 * @param id 催收返款id
	 * @return 查询实体
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public UrgeServicesBackMoneyEx selSendApply(String id){
		return dao.selSendApply(id);
	}
	
	/**
	 * 查询催收服务费返款操作历史列表
	 * 2016年1月11日
	 * By 朱静越
	 * @param page
	 * @param urgeHistory
	 * @return 催收服务费返款历史集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<UrgeHistory> selectUrgeHistory(Page<UrgeHistory> page,
			UrgeHistory urgeHistory) {
    	PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeHistory> pageList = (PageList<UrgeHistory>)dao.selectUrgeHistory(pageBounds, urgeHistory);
		PageUtil.convertPage(pageList, page);
    	return page;
    }
    
	
	/**
	 * 更新催收返款表
	 * 2016年1月11日
	 * By 朱静越
	 * @param urgeBack
	 * @return 操作更新结果o
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateUrgeBack(UrgeServicesBackMoney urgeBack){
		urgeBack.preUpdate();
		return dao.updateUrgeBack(urgeBack);
	}
	
	/**
	 * 插入催收返款表
	 * 2015年12月16日
	 * By 朱静越
	 * @param urgeBack
	 * @return 插入操作结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insertUrgeBack(UrgeServicesBackMoney urgeBack){
		return dao.insertUrgeBack(urgeBack);
	}
	
	/**
	 * 插入催收服务费历史
	 * 2016年1月11日
	 * By 朱静越
	 * @param urgeHistory
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertUrgeHistory(UrgeHistory urgeHistory){
		User user = UserUtils.getUser();
		if (ObjectHelper.isNotEmpty(user)) {
			urgeHistory.setOperator(user.getName());
		}
		urgeHistory.setOperateTime(new Date());
		urgeHistory.preInsert();
		dao.insertUrgeHistory(urgeHistory);
	}
	
	/**
	 * 保存返款申请数据和返款申请历史数据
	 * 2016年4月21日
	 * By 张永生
	 * @param urgeBackResult
	 * @param urgeHistory
	 * @param urgeBack
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveBackApplyAndHis(UrgeServicesBackMoneyEx urgeBackResult,
			UrgeHistory urgeHistory, UrgeServicesBackMoney urgeBack,String channelFlag) {
		updateUrgeBack(urgeBack);
		urgeHistory.setDictayPayResult("申请成功");
		urgeHistory.setRemarks("申请成功");
		urgeHistory.setOperateStep("催收服务费返还申请");
		urgeHistory.setrUrgeId(urgeBackResult.getId());
		User user = UserUtils.getUser();
		if (ObjectHelper.isNotEmpty(user)) {
			urgeHistory.setOperator(user.getName());
		}
		urgeHistory.setOperateTime(new Date());
		insertUrgeHistory(urgeHistory);
		//给大金融推送数据
		if(channelFlag.equals(ChannelFlag.ZCJ.getCode())){
			try{
			urgeBackResult = dao.getObjectById(urgeBack.getId());
			sendDjrRefundData(urgeBackResult);
			}catch(Exception e){
				logger.info("-----催收服务费发送大金融失败,合同编号："+urgeBackResult.getContractCode()+"--------");
			}
		}
	}
	
	/**
	 * 终审后的退款信息推送给大金融
	 * @author 于飞
	 * @Create 2016年10月10日
	 */
	public void sendDjrRefundData(UrgeServicesBackMoneyEx urgeBack){
		logger.info("------催收服务费退款开始推送大金融，合同编号"+urgeBack.getContractCode()+"-----");
		Contract contract = contractDao.findByContractCode(urgeBack.getContractCode());
		if(!contract.getChannelFlag().equals(ChannelFlag.ZCJ.getCode()))
			return;
		//发送冲抵记录信息----------------
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_BACKMONEY_SERVICE);
		DjrSendBackmoneyInBean inParam = new DjrSendBackmoneyInBean(); 
		inParam.setContractCode(urgeBack.getContractCode());
		inParam.setApplyTime(urgeBack.getCreateTime());
		inParam.setBackMoney(urgeBack.getPaybackBackAmount());
		inParam.setOrderId(urgeBack.getId());
		//1:蓝补退款  2：催收服务费退款
		inParam.setType("2");
		inParam.setDataTransferId(System.currentTimeMillis()+"");
		//查询还款信息，是否已结清
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contractCode", urgeBack.getContractCode());
		Payback pay = paybackDao.selectpayBack(map);
		String isSettle="1";
		if(pay.getDictPayStatus().equals(RepayStatus.SETTLE.getCode()) ||
				pay.getDictPayStatus().equals(RepayStatus.PRE_SETTLE.getCode()))
			isSettle="0";
		inParam.setIsSettle(isSettle);
			
		DjrSendBackmoneyOutBean outParam = (DjrSendBackmoneyOutBean) service.callService(inParam); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("------催收服务费退款推送大金融成功，合同编号"+urgeBack.getContractCode()+"-----");
		} else { 
			// TODO 失败 
			logger.info("------催收服务费退款推送大金融失败，合同编号"+urgeBack.getContractCode()+"-----");
		} 
		
		
	}
}
