package com.creditharmony.loan.borrow.payback.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.RepaymentReminderDao;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;

/**
 * @Class Service实现支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年11月23日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class RepaymentReminderService {
	@Autowired
	private RepaymentReminderDao repaymentReminderDao;

	/**
	 * 查询发起还款提醒列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param repaymentReminder
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<RepaymentReminderEx> allRepaymentReminderList(Page<RepaymentReminderEx> page,RepaymentReminderEx repaymentReminder) {
		BigDecimal bgSum = new BigDecimal("0.00");
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<RepaymentReminderEx> pageList = (PageList<RepaymentReminderEx>)repaymentReminderDao.allRepaymentReminderList(pageBounds,repaymentReminder);
		for (int i = 0; i < pageList.size(); i++) {
		
			BigDecimal monthFeeService =   pageList.get(i).getMonthFeeService();
			BigDecimal monthPayAmount =   pageList.get(i).getMonthPayAmount();
			BigDecimal monthInterestBackshould =   pageList.get(i).getMonthInterestBackshould();
			monthFeeService = monthFeeService == null ? bgSum : monthFeeService;
			monthPayAmount = monthPayAmount == null ? bgSum : monthPayAmount;
			monthInterestBackshould = monthInterestBackshould == null ? bgSum : monthInterestBackshould;
			
			BigDecimal actualMonthFeeService = pageList.get(i).getActualMonthFeeService();
			BigDecimal monthCapitalPayactual = pageList.get(i).getMonthCapitalPayactual();
			BigDecimal monthInterestPayactual = pageList.get(i).getMonthInterestPayactual();
			actualMonthFeeService = actualMonthFeeService == null ? bgSum : actualMonthFeeService;
			monthCapitalPayactual = monthCapitalPayactual == null ? bgSum : monthCapitalPayactual;
			monthInterestPayactual = monthInterestPayactual == null ? bgSum : monthInterestPayactual;
		
			String contractVersion = pageList.get(i).getContractVersion();
			if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
				BigDecimal completeAmount = actualMonthFeeService.add(monthInterestPayactual).add(monthCapitalPayactual);
				
				pageList.get(i).setCompleteAmount(completeAmount);
			    // 当期应还期供金额
				pageList.get(i).setPayMoney(monthFeeService.add(monthPayAmount).add(monthInterestBackshould).subtract(completeAmount));
			}else{
				BigDecimal completeAmount = monthInterestPayactual.add(monthCapitalPayactual);
				// 已还期供金额
				pageList.get(i).setCompleteAmount(completeAmount);
				 // 当期应还期供金额
				pageList.get(i).setPayMoney(monthPayAmount.add(monthInterestBackshould).subtract(completeAmount));
			}
			bgSum = bgSum.add(pageList.get(i).getPayMoney());
		}
		if(!pageList.isEmpty()){
		    Map<String,Object> sumAMount = this.selectSumAmount(repaymentReminder);
		pageList.get(0).setSumAmont((BigDecimal) sumAMount.get("pay_amount"));}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询需要发送的全部期供id
	 * 2016年5月25日
	 * By 王彬彬
	 * @param page
	 * @param repaymentReminder
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<String> fingAllId(RepaymentReminderEx repaymentReminder) {
		List<String> pageList = repaymentReminderDao
				.fingAllId(repaymentReminder);
		return pageList;
	}

	/**
	 * 查询短信推送所需要的数据
	 * 2015年12月17日
	 * By 李强
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public RepaymentReminderEx queryRepaymentReminderList(RepaymentReminderEx repaymentReminders) {
		return repaymentReminderDao.queryRepaymentReminderList(repaymentReminders);
	}

	/**
	 * 将短信推送的数据添加到表(汇金短信提醒_还款表)
	 * 2015年12月17日
	 * By 李强
	 * @param repaymentReminder
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveShortMessage(RepaymentReminderEx repaymentReminder) {
		repaymentReminderDao.insertShortMessagePayback(repaymentReminder);
	}

	/**
	 * 将短信推送的数据添加到表(借款人服务部提醒信息)
	 * 2015年12月17日
	 * By 李强
	 * @param repaymentReminder
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveShortMessageServicesRemind(
			RepaymentReminderEx repaymentReminder) {
		repaymentReminderDao
				.insertShortMessageServicesRemind(repaymentReminder);
	}

	/**
	 * 推送成功后把待还款的 提醒标识变成已提醒
	 * 2015年12月17日
	 * By 李强
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String updateOverdueManager(RepaymentReminderEx repaymentReminder) {
		return repaymentReminderDao.updateRepaymentReminder(repaymentReminder);
	}
	
	/**
	 * 导出发起还款提醒列表中的数据
	 * 2016年3月24日
	 * By zhaojinping
	 * @param repaymentReminder
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager" )
    public List<RepaymentReminderEx> exportRemindExcel(RepaymentReminderEx repaymentReminder){
    	return repaymentReminderDao.exportRemindExcel(repaymentReminder);
    }
	
	/**
	 * 发送提醒短信 2016年3月24日
	 *  By zhaojinping
	 * 
	 * @param repaymentReminder
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendRemindMsg(RepaymentReminderEx repaymentReminder) {
		//if (ArrayHelper.isNotEmpty(lstId)) {
		///	for (int i = 0; i < lstId.size(); i++) {
				//RepaymentReminderEx repaymentReminder = new RepaymentReminderEx();
				//String id = lstId.get(i);
				/*if (StringUtils.isEmpty(id)) {
					continue;
				}*/
		        String id = repaymentReminder.getId();
				//repaymentReminder = queryRepaymentReminderList(repaymentReminders);
				try {
					repaymentReminder.setMonthId(id);
					repaymentReminder.setIsNewRecord(false);
					repaymentReminder.preInsert();
					repaymentReminder
							.setRemindLogo(DeductedConstantEx.CENTRALIZED_DUCTION);
					saveShortMessage(repaymentReminder);
					repaymentReminder.preUpdate();
					repaymentReminder
							.setRemindLogo(DeductedConstantEx.DUCTION_TODO);
					repaymentReminder.setId(id);
					updateOverdueManager(repaymentReminder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			//}
		//}
	}
	public Map<String,Object> selectSumAmount(RepaymentReminderEx repaymentReminder){
	       return repaymentReminderDao.selectSumAmount(repaymentReminder);
	}
	
	public List<RepaymentReminderEx> allRepaymentReminderList(RepaymentReminderEx repaymentReminder){
		return repaymentReminderDao.allRepaymentReminderList(repaymentReminder);
	}

}

